package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.FestivoBean.TipoFiesta;

//80xxxx
public class FestivoHandler extends GenericHandler {


	private static final String QUERY_GET_LISTA_FESTIVOS_X_COD_RESIDENCIA = 
			"SELECT fest.cod_festivo as codigo, fest.festivo as fiesta, fest.notas as notas, "
					+ "fest.fecha as fecha, fest.tipo as tipo, "
					+ "muni.id_municipio as municipioCod, muni.nombre as municipioNombre, "
					+ "prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
					+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
				+ "FROM residencia res, geo_municipio muni, geo_provincia prov, geo_pais pais, dia_festivo fest "
				+ "WHERE res.codigo=? AND muni.id_municipio=res.id_municipio "
					+ "AND muni.id_provincia=prov.id_provincia AND muni.cod_pais=pais.cod_pais "
					+ "AND ( (fest.tipo='LOCAL' AND fest.id_municipio=muni.id_municipio) "
						+ "OR (fest.tipo='AUTONOMICA' AND fest.id_provincia=muni.id_provincia) "
						+ "OR (fest.tipo='NACIONAL' AND fest.cod_pais=muni.cod_pais)) "
					+ "%s "
				+ "ORDER BY fest.fecha, fest.tipo "
				+ "LIMIT ?";
	
	//00xx
	public static ArrayList<FestivoBean> getFestivosResidencia(Connection conexion,
			String codigo, Date fecha_ini, Date fecha_fin, int limit,
			ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<FestivoBean> listaFests = new ArrayList<FestivoBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			int codpar = 0;
			String rang = "";
			if(fecha_ini != null){
				rang += "AND fest.fecha>=? ";
				codpar++;
			}
			if(fecha_fin != null){
				rang += "AND fest.fecha<? ";
				codpar+=2;
			}

			ps = nconexion.prepareStatement(String.format(QUERY_GET_LISTA_FESTIVOS_X_COD_RESIDENCIA, rang));
			ps.setString(1, codigo);
			switch (codpar) {
			default:
			case 0:
				ps.setInt(2, limit);
				break;
			case 1:
				ps.setDate(2, new java.sql.Date(fecha_ini.getTime()));
				ps.setInt(3, limit);
				break;
			case 2:
				ps.setDate(2, new java.sql.Date(fecha_fin.getTime()));
				ps.setInt(3, limit);
				break;
			case 3:
				ps.setDate(2, new java.sql.Date(fecha_ini.getTime()));
				ps.setDate(3, new java.sql.Date(fecha_fin.getTime()));
				ps.setInt(4, limit);
				break;
			}

			rs = ps.executeQuery();
			FestivoBean fest;
			while (rs.next()) {
				fest = new FestivoBean();
				fest.setCodigo(rs.getInt("codigo"));
				fest.setFiesta(rs.getString("fiesta"));
				fest.setNotas(rs.getString("notas"));
				fest.setFecha(rs.getDate("fecha"));
				fest.setTipo(rs.getString("tipo"));
				fest.setMunicipioCod(rs.getString("municipioCod"));
				fest.setMunicipioNombre(rs.getString("municipioNombre"));
				fest.setProvinciaCod(rs.getString("provinciaCod"));
				fest.setProvinciaNombre(rs.getString("provinciaNombre"));
				fest.setPaisCod(rs.getString("paisCod"));
				fest.setPaisNombre(rs.getString("paisNombre"));
				fest.setTZ(rs.getString("tz"));
				listaFests.add(fest);
			}

		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69700200");
			errorBean.updateMsg(e.getMessage());
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaFests;
	}

	public static ArrayList<FestivoBean> getFestivosMunicipio(Connection conexion, String codMunicipio, TipoFiesta tipo,
			Date fecha_ini, Date fecha_fin, int limit, boolean completo, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<FestivoBean> getFestivosProvincia(Connection conexion, String codProvincia, TipoFiesta tipo,
			Date fecha_ini, Date fecha_fin, int limit, boolean completo, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<FestivoBean> getFestivosPais(Connection conexion, String codProvincia, TipoFiesta tipo,
			Date fecha_ini, Date fecha_fin, int limit, boolean completo, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static FestivoBean getFestivo(Connection conexion, String codFest, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
