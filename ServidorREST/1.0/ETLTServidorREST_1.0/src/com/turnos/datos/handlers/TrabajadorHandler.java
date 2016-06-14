package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TrabajadorBean;

public class TrabajadorHandler extends GenericHandler {

	private static final int LOC_H = 71;

	private static final String QUERY_EXISTE_TRABAJADOR =
			"SELECT count(*) as existe "
			+ "FROM trabajador trab JOIN residencia res ON trab.id_residencia=res.id_residencia "
			+ "WHERE res.codigo=? AND trab.codigo=?";

	private static final String QUERY_LISTA_TRABAJADORES_RES = 
		"SELECT trab.codigo as codTrab, res.codigo as codRes, "
			+ "trab.nombre as nombre, trab.apellidos as apellidos, u.id_usuario "
		+ "FROM trabajador trab JOIN residencia res ON trab.id_residencia=res.id_residencia "
		+ "LEFT JOIN app_usuario u ON trab.id_trabajador=u.id_trabajador "
		+ "WHERE res.codigo=?";

	private static final String QUERY_GET_TRABAJADOR_COD = 
			"SELECT trab.codigo as codTrab, res.codigo as codRes,"
				+ "trab.nombre as nombre, trab.apellidos as apellidos, u.id_usuario "
			+ "FROM trabajador trab JOIN residencia res ON trab.id_residencia=res.id_residencia "
			+ "LEFT JOIN app_usuario u ON trab.id_trabajador=u.id_trabajador "
			+ "WHERE res.codigo=? AND trab.codigo=?";

	private static final String UPDATE_INSERT_NUEVO_TRABAJADOR =
			"INSERT INTO trabajador (codigo, id_residencia, nombre, apellidos) "
					+ "SELECT ?, res.id_residencia, ?, ? FROM residencia res WHERE res.codigo=?";

	private static final String UPDATE_UPDATE_TRABAJADOR = 
			"UPDATE trabajador trab "
				+ "JOIN residencia res ON trab.id_residencia=res.id_residencia "
			+ "SET %s WHERE trab.codigo=? AND res.codigo=?";
	
	private static final String UPDATE_DELETE_TRABAJADOR = 
			"DELETE trab FROM trabajador trab "
				+ "JOIN residencia res ON trab.id_residencia=res.id_residencia "
			+ "WHERE trab.codigo=? AND res.codigo=?";

	public static boolean existeTrabajador(Connection conexion, String codRes, String codTrab, ErrorBean errorBean) {
		int LOC_M = 1;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		if (codRes != null && !"".equals(codRes) && codTrab != null && !"".equals(codTrab)) {
			return false;
		} else {
			try {
				PreparedStatement ps = nconexion.prepareStatement(QUERY_EXISTE_TRABAJADOR);
				ps.setString(1, codRes);
				ps.setString(2, codTrab);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getBoolean("existe");
				}
			} catch (SQLException e) {
				int[] loc = {LOC_H,LOC_M,1};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
			
			return false;
		}
	}

	public static ArrayList<TrabajadorBean> listTrabajadores(Connection conexion, String codRes, int limite, int offset, ErrorBean errorBean) {
		int LOC_M = 2;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		ArrayList<TrabajadorBean> listaRes = new ArrayList<TrabajadorBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			if (codRes != null && !"".equals(codRes)) {
				ps = nconexion.prepareStatement(anadeLimiteOffset(QUERY_LISTA_TRABAJADORES_RES, limite, offset));
				ps.setString(1, codRes);
				rs = ps.executeQuery();
				TrabajadorBean res;
				
				while (rs.next()) {
					res = new TrabajadorBean();
					res.setCodigo(rs.getString("codTrab"));
					res.setCodResidencia(rs.getString("codRes"));
					res.setNombre(rs.getString("nombre"));
					res.setApellidos(rs.getString("apellidos"));
					res.setId_usuario(rs.getLong("id_usuario"));
					listaRes.add(res);
				}
			} else {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "debe incluir codigo residencia a buscar");
			}
		} catch (SQLException e) {
			int[] loc = {LOC_H,LOC_M,1};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaRes;
	}

	public static TrabajadorBean getTrabajador(Connection conexion, String codRes, String codTrab, ErrorBean errorBean) {
		int LOC_M = 3;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		TrabajadorBean res = null;
		if (codRes != null && !"".equals(codRes) && codTrab != null && !"".equals(codTrab)) {
			try {
				PreparedStatement ps = nconexion.prepareStatement(QUERY_GET_TRABAJADOR_COD);
				ps.setString(1, codRes);
				ps.setString(2, codTrab);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					res = new TrabajadorBean();
					res.setCodigo(rs.getString("codTrab"));
					res.setCodResidencia(rs.getString("codRes"));
					res.setNombre(rs.getString("nombre"));
					res.setApellidos(rs.getString("apellidos"));
					res.setId_usuario(rs.getLong("id_usuario"));
				} else {
					int[] loc = {LOC_H,LOC_M,3};
					ErrorBeanFabrica.generaErrorBean(errorBean, Status.NOT_FOUND, "h48", loc, "no encotrado trabajador con codigo " + codTrab);
				}
			} catch (SQLException e) {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
				return null;
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			int[] loc = {LOC_H,LOC_M,1};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "debe incluir codigos");
		}
		return res;

	}

	public static TrabajadorBean insertTrabajador(Connection conexion, String codRes,
			TrabajadorBean trabRaw, ErrorBean errorBean) {
		int LOC_M = 4;
		// TODO Auto-generated method stub
		return null;
	}

	public static TrabajadorBean updateTrabajador(Connection conexion, String codRes,
			String codTrab, TrabajadorBean trabRaw, ErrorBean errorBean) {
		int LOC_M = 5;
		// TODO Auto-generated method stub
		return null;
	}
	
	public static boolean deleteTrabajador(Connection conexion, String codRes,
			String codTrab, ErrorBean errorBean) {
		int LOC_M = 6;
		// TODO Auto-generated method stub
		return false;
	}

}
