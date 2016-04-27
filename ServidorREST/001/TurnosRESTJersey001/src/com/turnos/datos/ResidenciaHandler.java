package com.turnos.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.ResidenciaBean;

//70xxxx
public class ResidenciaHandler extends GenericHandler {

	public static enum TipoBusqueda{MUNICIPIO, PROVINCIA, TODOS};

	private static final String QUERY_RESIDENCIA_EXISTS_COD = "SELECT count(*) as existe FROM residencia res WHERE res.codigo=?";

	private static final String QUERY_GET_RESIDENCIA_COD = "SELECT res.codigo, res.nombre, res.descripcion, res.ciudad, "
					+ "muni.id_municipio as municipioCod, muni.nombre as municipioNombre, "
					+ "prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
					+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
				+ "FROM residencia res, geo_municipio muni, geo_provincia prov, geo_pais pais "
				+ "WHERE res.codigo=? AND muni.id_municipio=res.id_municipio "
					+ "AND muni.id_provincia=prov.id_provincia AND prov.cod_pais=pais.cod_pais";
	
	private static final String QUERY_GET_LISTA_RESIDENCIAS_MUNI = 
			"SELECT res.codigo, res.nombre, res.descripcion, res.ciudad, "
					+ "muni.id_municipio as municipioCod, muni.nombre as municipioNombre, "
					+ "prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
					+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
				+ "FROM residencia res, geo_municipio muni, geo_provincia prov, geo_pais pais "
				+ "WHERE muni.id_municipio=? AND muni.id_municipio=res.id_municipio "
					+ "AND muni.id_provincia=prov.id_provincia AND prov.cod_pais=pais.cod_pais "
				+ "ORDER BY muni.id_municipio, res.codigo";
	
	private static final String QUERY_GET_LISTA_RESIDENCIAS_PROV = 
			"SELECT res.codigo, res.nombre, res.descripcion, res.ciudad, "
					+ "muni.id_municipio as municipioCod, muni.nombre as municipioNombre, "
					+ "prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
					+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
				+ "FROM residencia res, geo_municipio muni, geo_provincia prov, geo_pais pais "
				+ "WHERE prov.id_provincia=? AND muni.id_municipio=res.id_municipio "
					+ "AND muni.id_provincia=prov.id_provincia AND prov.cod_pais=pais.cod_pais "
				+ "ORDER BY muni.id_municipio, res.codigo";
	
	private static final String QUERY_GET_LISTA_RESIDENCIAS = 
			"SELECT res.codigo, res.nombre, res.descripcion, res.ciudad, "
					+ "muni.id_municipio as municipioCod, muni.nombre as municipioNombre, "
					+ "prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
					+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
				+ "FROM residencia res, geo_municipio muni, geo_provincia prov, geo_pais pais "
				+ "WHERE muni.id_municipio=res.id_municipio "
					+ "AND muni.id_provincia=prov.id_provincia AND prov.cod_pais=pais.cod_pais "
				+ "ORDER BY muni.id_municipio, res.codigo";

	private static final String UPDATE_INSERT_NUEVA_RESIDENCIA = "INSERT INTO residencia "
				+ "(codigo, nombre, descripcion, ciudad, id_municipio) "
				+ "VALUES (?,?,?,?,?)";

	private static final String UPDATE_UPDATE_RESIDENCIA = "UPDATE residencia SET %s WHERE codigo=?";
	private static final String UPDATE_DELETE_RESIDENCIA = "DELETE FROM residencia WHERE codigo=?";

	//00xx
	public static boolean existeResidencia(Connection conexion, String codigo, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		if(codigo == null || "".equals(codigo)) {
			return false;
		} else {
			try {
				PreparedStatement ps = nconexion.prepareStatement(QUERY_RESIDENCIA_EXISTS_COD);
				ps.setString(1, codigo);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getBoolean("existe");
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69700000");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
			
			return false;
		}
	}

	//01xx
	public static ResidenciaBean getResidencia(Connection conexion, String codigo, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ResidenciaBean res = null;
		if (codigo != null && !"".equals(codigo)) {
			try {
				PreparedStatement ps = nconexion.prepareStatement(QUERY_GET_RESIDENCIA_COD);
				ps.setString(1, codigo);
				ResultSet rs;
				rs = ps.executeQuery();

				if (rs.next()) {
					res = new ResidenciaBean();
					res.setCodigo(rs.getString("codigo"));
					res.setNombre(rs.getString("nombre"));
					res.setDescripcion(rs.getString("descripcion"));
					res.setCiudad(rs.getString("ciudad"));
					res.setMunicipioCod(rs.getString("municipioCod"));
					res.setMunicipioNombre(rs.getString("municipioNombre"));
					res.setProvinciaCod(rs.getString("provinciaCod"));
					res.setProvinciaNombre(rs.getString("provinciaNombre"));
					res.setPaisCod(rs.getString("paisCod"));
					res.setPaisNombre(rs.getString("paisNombre"));
					res.setTZ(rs.getString("tz"));
				} else {
					errorBean.setHttpCode(Status.NOT_FOUND);
					errorBean.updateErrorCode("69700102");
					errorBean.updateMsg("no encotrada residencia con codigo "+codigo);
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69700101");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69700100");
			errorBean.updateMsg("debe incluir codigo");
		}
		return res;
	}

	//02xx
	public static ArrayList<ResidenciaBean> listResidencias(Connection conexion,
			TipoBusqueda tipo, String[] busqueda,
			ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<ResidenciaBean> listaRess = new ArrayList<ResidenciaBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			switch (tipo) {
			case MUNICIPIO:
				ps = nconexion.prepareStatement(QUERY_GET_LISTA_RESIDENCIAS_MUNI);
				ps.setString(1, busqueda[0]);
				break;
			case PROVINCIA:
				ps = nconexion.prepareStatement(QUERY_GET_LISTA_RESIDENCIAS_PROV);
				ps.setString(1, busqueda[0]);
				break;
			case TODOS:
				ps = nconexion.prepareStatement(QUERY_GET_LISTA_RESIDENCIAS);
				break;
			default:
				ps = null;
				break;

			}
			if (ps != null) {
				rs = ps.executeQuery();
				ResidenciaBean res;
				while (rs.next()) {
					res = new ResidenciaBean();
					res.setCodigo(rs.getString("codigo"));
					res.setNombre(rs.getString("nombre"));
					res.setDescripcion(rs.getString("descripcion"));
					res.setCiudad(rs.getString("ciudad"));
					res.setMunicipioCod(rs.getString("municipioCod"));
					res.setMunicipioNombre(rs.getString("municipioNombre"));
					res.setProvinciaCod(rs.getString("provinciaCod"));
					res.setProvinciaNombre(rs.getString("provinciaNombre"));
					res.setPaisCod(rs.getString("paisCod"));
					res.setPaisNombre(rs.getString("paisNombre"));
					res.setTZ(rs.getString("tz"));
					listaRess.add(res);
				}
			} else {
				errorBean.setHttpCode(Status.BAD_REQUEST);
				errorBean.updateErrorCode("69700201");
				errorBean.updateMsg("'tipo' desconocido");
			}
		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69700200");
			errorBean.updateMsg(e.getMessage());
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaRess;
	}

	//03xx
	public static ResidenciaBean insertResidencia(Connection conexion, ResidenciaBean resRaw, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean aut = autenticar(conexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57700300");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}
		
		
		ResidenciaBean res = null;
		if (resRaw != null) {
			int i = 0;
			String codigo;
			do {
				codigo = generaNuevoResCodigo(resRaw, i++);
			} while (existeResidencia(nconexion, codigo, errorBean) && i < 10);
			if(i >= 10) {
				errorBean.setHttpCode(Status.BAD_REQUEST);
				errorBean.updateErrorCode("69700302");
				errorBean.updateMsg("nombre demasiado común, intenta otro nombre");
				return null;
			}

			try {
				PreparedStatement ps = nconexion.prepareStatement(UPDATE_INSERT_NUEVA_RESIDENCIA);
				ps.setString(1, codigo);
				ps.setString(2, resRaw.getNombre());
				ps.setString(3, resRaw.getDescripcion());
				ps.setString(4, resRaw.getCiudad());
				ps.setString(5, resRaw.getMunicipioCod());
				int c = ps.executeUpdate();
				if (c > 0) {
					res = getResidencia(conexion, codigo, errorBean);
					if(res == null) {
						errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
						errorBean.updateErrorCode("69700304");
						errorBean.updateMsg("no insertada (?)");
					}
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69700303");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69700301");
			errorBean.updateMsg("debe incluir datos residencia");
		}
			
		return res;
	}

	//04xx
	public static ResidenciaBean updateResidencia(Connection conexion,
			String codigo, ResidenciaBean resRaw,
			ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean aut = autenticar(conexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57700400");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}
		
		ResidenciaBean res = null;
		if (resRaw != null) {
			int params = 0;
			String[]strs = new String[5];
			String upd = "";
			if(resRaw.getNombre() != null) {
				upd += "nombre=?";
				strs[params++] = resRaw.getNombre();
			}
			if(resRaw.getDescripcion() != null) {
				if(params > 0) upd += ", ";
				upd += "descripcion=?";
				strs[params++] = resRaw.getDescripcion();
			}
			if(resRaw.getCiudad() != null) {
				if(params > 0) upd += ", ";
				upd += "ciudad=?";
				strs[params++] = resRaw.getCiudad();
			}
			if(resRaw.getMunicipioCod() != null) {
				if(params > 0) upd += ", ";
				upd += "id_municipio=?";
				strs[params++] = resRaw.getMunicipioCod();
			}
			
			
			if (params == 0) {
				errorBean.setHttpCode(Status.BAD_REQUEST);
				errorBean.updateErrorCode("69700401");
				errorBean.updateMsg("Sin parametros para cambiar");
			} else if (ResidenciaHandler.existeResidencia(conexion, codigo, errorBean)) {
				strs[params++] = codigo;
				try {
					PreparedStatement ps = nconexion.prepareStatement(String.format(UPDATE_UPDATE_RESIDENCIA, upd));
					for (int i = 0; i < params; i++) {
						ps.setString(i+1, strs[i]);
					}

					int c = ps.executeUpdate();
					if (c > 0) {
						res = getResidencia(conexion, codigo, errorBean);
						if(res == null) {
							errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
							errorBean.updateErrorCode("69700404");
							errorBean.updateMsg("???");
						}
					}
				} catch (SQLException e) {
					errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
					errorBean.updateErrorCode("69700403");
					errorBean.updateMsg(e.getMessage());
					e.printStackTrace();
				} finally {
					terminaOperacion(nconexion, cierraConexion);
				}
			} else {
				errorBean.setHttpCode(Status.BAD_REQUEST);
				errorBean.updateErrorCode("69700402");
				errorBean.updateMsg("no encontrada residencia con codigo "+codigo);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69700401");
			errorBean.updateMsg("debe incluir datos residencia");
		}
			
		return res;
	}
	
	//05xx
	public static boolean deleteResidencia(Connection conexion, String codigo, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean aut = autenticar(conexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57700500");
			errorBean.updateMsg("Sin autenticar");
			return false;
		}
		
		if (ResidenciaHandler.existeResidencia(conexion, codigo, errorBean)) {
			try {
				PreparedStatement ps = nconexion.prepareStatement(UPDATE_DELETE_RESIDENCIA);
				ps.setString(1, codigo);

				int c = ps.executeUpdate();
				if (c > 0) {
					return true;
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69700502");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69700402");
			errorBean.updateMsg("no encontrada residencia con codigo "+codigo);
		}
		
		return false;
	}
	
	public static String generaNuevoResCodigo(ResidenciaBean resRaw, int ord) {
		String ncod = (resRaw.getCiudad() == null)?"":(resRaw.getCiudad()+"_") + resRaw.getNombre();
		if (ord > 0) {
			ncod = quitaAcentos(resRaw.getCiudad() + "_" + resRaw.getNombre(), 32, ord, 2);
		} else {
			ncod = quitaAcentos(resRaw.getCiudad() + "_" + resRaw.getNombre(), 32);
		}
		return ncod;
	}
}
