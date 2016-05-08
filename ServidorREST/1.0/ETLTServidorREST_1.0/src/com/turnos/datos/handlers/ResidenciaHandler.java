package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.ResidenciaBean;

//70xxxx
public class ResidenciaHandler extends GenericHandler {

	public static enum TipoBusqueda{MUNICIPIO, PROVINCIA, PAIS};

	private static final String QUERY_RESIDENCIA_EXISTS_COD =
			"SELECT count(*) as existe FROM residencia res WHERE res.codigo=?";

	private static final String QUERY_GET_RESIDENCIA_COD =
		"SELECT res.codigo, res.nombre, res.descripcion, res.ciudad, "
		+ "res.id_municipio as municipioCod "
		+ "FROM residencia res WHERE res.codigo=?";
	
	private static final String QUERY_GET_LISTA_RESIDENCIAS_MUNI = 
		"SELECT res.codigo, res.nombre, res.descripcion, res.ciudad, "
		+ "res.id_municipio as municipioCod "
		+ "FROM residencia res WHERE res.id_municipio=? "
		+ "ORDER BY res.id_municipio, res.codigo";
	
	private static final String QUERY_GET_LISTA_RESIDENCIAS_PROV = 
		"SELECT res.codigo, res.nombre, res.descripcion, res.ciudad, "
		+ "res.id_municipio as municipioCod "
		+ "FROM residencia res "
			+ "INNER JOIN geo_municipio muni ON res.id_municipio=muni.id_municipio "
			+ "INNER JOIN geo_provincia prov ON muni.id_provincia=prov.id_provincia "
		+ "WHERE prov.id_provincia=? "
		+ "ORDER BY res.id_municipio, res.codigo";
	
	private static final String QUERY_GET_LISTA_RESIDENCIAS_PAIS = 
		"SELECT res.codigo, res.nombre, res.descripcion, res.ciudad, "
			+ "res.id_municipio as municipioCod "
		+ "FROM residencia res "
			+ "INNER JOIN geo_municipio muni ON res.id_municipio=muni.id_municipio "
			+ "INNER JOIN geo_provincia prov ON muni.id_provincia=prov.id_provincia "
			+ "INNER JOIN geo_pais pais ON muni.cod_pais=pais.cod_pais "
		+ "WHERE pais.cod_pais=? "
		+ "ORDER BY res.id_municipio, res.codigo";

	private static final String UPDATE_INSERT_NUEVA_RESIDENCIA =
			"INSERT INTO residencia "
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
	public static ResidenciaBean getResidencia(Connection conexion, String codigo, boolean includeGeo, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ResidenciaBean res = null;
		if (codigo != null && !"".equals(codigo)) {
			try {
				PreparedStatement ps = nconexion.prepareStatement(QUERY_GET_RESIDENCIA_COD);
				ps.setString(1, codigo);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					res = new ResidenciaBean();
					res.setCodigo(rs.getString("codigo"));
					res.setNombre(rs.getString("nombre"));
					res.setDescripcion(rs.getString("descripcion"));
					res.setCiudad(rs.getString("ciudad"));
					res.setMunicipioCod(rs.getString("municipioCod"));
					if(includeGeo) {
						MunicipioBean muni = GeoHandler.getMunicipio(nconexion, res.getMunicipioCod(), errorBean);
						res.setMunicipio(muni);
					}
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
			TipoBusqueda tipo, String[] busqueda, boolean includeGeo,
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
			case PAIS:
				ps = nconexion.prepareStatement(QUERY_GET_LISTA_RESIDENCIAS_PAIS);
				ps.setString(1, busqueda[0]);
				break;
			default:
				ps = null;
				break;

			}
			if (ps != null) {
				rs = ps.executeQuery();
				ResidenciaBean res;
				String codMuni;
				MunicipioBean muni;
				Hashtable<String, MunicipioBean> tablaMunis = new Hashtable<String, MunicipioBean>();
				while (rs.next()) {
					res = new ResidenciaBean();
					res.setCodigo(rs.getString("codigo"));
					res.setNombre(rs.getString("nombre"));
					res.setDescripcion(rs.getString("descripcion"));
					res.setCiudad(rs.getString("ciudad"));
					codMuni = rs.getString("municipioCod");
					res.setMunicipioCod(codMuni);
					if(includeGeo) {
						if (!tablaMunis.containsKey(codMuni)) {
							muni = GeoHandler.getMunicipio(nconexion, res.getMunicipioCod(), errorBean);
							if (muni != null) tablaMunis.put(codMuni, muni);
						} else {
							muni = tablaMunis.get(codMuni);
						}
						res.setMunicipio(muni);
					}
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
	public static ResidenciaBean insertResidencia(Connection conexion, ResidenciaBean resRaw, boolean aut, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
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
					res = getResidencia(nconexion, codigo, true, errorBean);
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
			String codigo, ResidenciaBean resRaw, boolean aut,
			ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
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
			} else if (ResidenciaHandler.existeResidencia(nconexion, codigo, errorBean)) {
				strs[params++] = codigo;
				try {
					PreparedStatement ps = nconexion.prepareStatement(String.format(UPDATE_UPDATE_RESIDENCIA, upd));
					for (int i = 0; i < params; i++) {
						ps.setString(i+1, strs[i]);
					}

					int c = ps.executeUpdate();
					if (c > 0) {
						res = getResidencia(nconexion, codigo, true, errorBean);
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
	public static boolean deleteResidencia(Connection conexion, String codigo, boolean aut, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57700500");
			errorBean.updateMsg("Sin autenticar");
			return false;
		}
		
		if (ResidenciaHandler.existeResidencia(nconexion, codigo, errorBean)) {
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
			errorBean.updateErrorCode("69700501");
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
