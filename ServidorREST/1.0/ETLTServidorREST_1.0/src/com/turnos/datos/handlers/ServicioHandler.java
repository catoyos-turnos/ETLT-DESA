package com.turnos.datos.handlers;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.UsuarioBean;

public class ServicioHandler extends GenericHandler {

	private static final int LOC_H = 74;
	
	private static final String QUERY_LISTA_TIPODIA_SERVICIO_TURNO_COD =
			"SELECT s_td.id_servicio as id_servicio, s_td.id_turno as id_turno, "
				+ "s_td.dia as dia, s_td.festivo as festivo, "
				+ "s_td.vispera_festivo as vispera "
			+ "FROM servicio_tipodia s_td WHERE s_td.id_turno=?";
	
	private static final String QUERY_LISTA_TIPODIA_SERVICIO_TURNO_RES =
			"SELECT s_td.id_servicio as id_servicio, s_td.id_turno as id_turno, "
				+ "s_td.dia as dia, s_td.festivo as festivo, "
				+ "s_td.vispera_festivo as vispera, "
				+ "turno.codigo as codTurno, res.codigo as codRes "
			+ "FROM servicio_tipodia s_td "
				+ "JOIN turno ON s_td.id_turno=turno.id_turno "
				+ "JOIN residencia res ON res.id_residencia=turno.id_residencia "
			+ "WHERE turno.codigo=? AND res.codigo=?";
	
	private static final String QUERY_LISTA_SERVICIOS_TURNO_COD =
			"SELECT serv.id_servicio as id_servicio, serv.hora_pres as hora_pres, "
				+ "serv.hora_ret as hora_ret, serv.tiempo_toma as tiempo_toma, "
				+ "serv.tiempo_deje as tiempo_deje, serv.margen_antes as margen_antes, "
				+ "serv.margen_despues as margen_despues, serv.descripcion as descripcion "
			+ "FROM servicio serv WHERE serv.id_turno=?";
	
	private static final String QUERY_LISTA_SERVICIOS_TURNO_RES =
			"SELECT serv.id_servicio as id_servicio, serv.hora_pres as hora_pres, "
					+ "serv.hora_ret as hora_ret, serv.tiempo_toma as tiempo_toma, "
					+ "serv.tiempo_deje as tiempo_deje, serv.margen_antes as margen_antes, "
					+ "serv.margen_despues as margen_despues, serv.descripcion as descripcion, "
					+ "turno.codigo as codTurno, res.codigo as codRes "
			+ "FROM servicio serv "
				+ "JOIN turno ON serv.id_turno=turno.id_turno "
				+ "JOIN residencia res ON res.id_residencia=turno.id_residencia "
			+ "WHERE turno.codigo=? AND res.codigo=?";
	
	private static final String QUERY_GET_SERVICIO_COD = 
			"SELECT serv.id_servicio as id_servicio, serv.hora_pres as hora_pres, "
					+ "serv.hora_ret as hora_ret, serv.tiempo_toma as tiempo_toma, "
					+ "serv.tiempo_deje as tiempo_deje, serv.margen_antes as margen_antes, "
					+ "serv.margen_despues as margen_despues, serv.descripcion as descripcion "
			+ "FROM servicio serv WHERE serv.codigo=?";

	private static final String UPDATE_INSERT_NUEVO_SERVICIO_TIPODIA =
			"INSERT INTO servicio_tipodia (id_servicio, id_turno, dia, festivo, vispera_festivo) "
			+ "SELECT serv.id_servicio, serv.id_turno, ?, ?, ? FROM servicio serv WHERE serv.id_servicio=?";

	private static final String UPDATE_DELETE_SERVICIO_TIPODIA = "DELETE FROM servicio_tipodia WHERE id_servicio_tipodia=?";

	
	private static final String UPDATE_INSERT_NUEVO_SERVICIO = "INSERT INTO servicio "
			+ "(id_turno, hora_pres, hora_ret, tiempo_toma, tiempo_deje, margen_antes, margen_despues, descripcion) "
			+ "VALUES ?,?,?,?,?,?,?,?";

	private static final String UPDATE_UPDATE_SERVICIO = "UPDATE servicio SET %s WHERE id_servicio=?";

	private static final String UPDATE_DELETE_SERVICIO = "DELETE FROM servicio WHERE id_servicio=?";

	public static boolean existeServicio(Connection conexion, int codServ, UsuarioBean usuarioLog, ErrorBean errorBean) {
		int LOC_M = 1;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		if(codServ >= 0) {
			return false;
		} else {
			try {
				// TODO Auto-generated method stub
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		}
		return false;
	}

	public static ArrayList<ServicioBean> listServicios(Connection conexion, String codRes, String codTurno,
			int limite, int offset, UsuarioBean usuarioLog, ErrorBean errorBean) {
		int LOC_M = 2;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<ServicioBean> listaServs = new ArrayList<ServicioBean>();
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return listaServs;
	}

	public static ServicioBean getServicio(Connection conexion, int codServ, UsuarioBean usuarioLog, ErrorBean errorBean) {
		int LOC_M = 3;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
	
		ServicioBean serv = null;
		if (codServ >= 0) {
			try {
				// TODO Auto-generated method stub
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		}
		return serv;
	}

	public static ServicioBean insertServicio(Connection conexion, String codRes, ServicioBean servRaw, UsuarioBean usuarioLog, ErrorBean errorBean) {
		int LOC_M = 4;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean auth = autenticar(usuarioLog, HttpMethod.POST, null, codRes);
		if(!auth) {
			int[] loc = {LOC_H,LOC_M,0};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.FORBIDDEN, "h57", loc, "Sin autenticar");
			terminaOperacion(nconexion, cierraConexion);
			return null;
		}
		
		ServicioBean serv = null;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return serv;
	}

	public static ServicioBean updateServicio(Connection conexion, String codRes, int codServ, ServicioBean servRaw, UsuarioBean usuarioLog, ErrorBean errorBean) {
		int LOC_M = 5;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean auth = autenticar(usuarioLog, HttpMethod.PUT, null, codRes);
		if(!auth) {
			int[] loc = {LOC_H,LOC_M,0};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.FORBIDDEN, "h57", loc, "Sin autenticar");
			terminaOperacion(nconexion, cierraConexion);
			return null;
		}
		
		ServicioBean serv = null;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return serv;
	}

	public static boolean deleteServicio(Connection conexion, String codRes, int codServ, UsuarioBean usuarioLog, ErrorBean errorBean) {
		int LOC_M = 6;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean auth = autenticar(usuarioLog, HttpMethod.DELETE, null, codRes);
		if(!auth) {
			int[] loc = {LOC_H,LOC_M,0};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.FORBIDDEN, "h57", loc, "Sin autenticar");
			terminaOperacion(nconexion, cierraConexion);
			return false;
		}
		
		boolean res = false;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return res;
	}

}
