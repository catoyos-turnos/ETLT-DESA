package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.ResidenciaBean;

public class ResidenciaHandler extends GenericHandler {

	private static final int LOC_H = 70;
	
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

	public static boolean existeResidencia(Connection conexion, String codigo, ErrorBean errorBean) {
		int LOC_M = 1;
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
				int[] loc = {LOC_H,LOC_M,1};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
			
			return false;
		}
	}

	public static ResidenciaBean getResidencia(Connection conexion, String codigo, boolean includeGeo, ErrorBean errorBean) {
		int LOC_M = 2;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
//		boolean auth = autenticar(usuarioLog, HttpMethod.GET);

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
						MunicipioBean muni = GeoHandler.getMunicipio(nconexion, res.getMunicipioCod(), false, errorBean);
						res.setMunicipio(muni);
					}
				} else {
					int[] loc = {LOC_H,LOC_M,3};
					ErrorBeanFabrica.generaErrorBean(errorBean, Status.NOT_FOUND, "h48", loc, "no encotrada residencia con codigo " + codigo);
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
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "debe incluir codigo");
		}
		return res;
	}

	public static ArrayList<ResidenciaBean> listResidencias(Connection conexion,
			TipoBusqueda tipo, String[] busqueda, boolean includeGeo, int limite, int offset,
			ErrorBean errorBean) {
		int LOC_M = 3;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
//		boolean auth = autenticar(usuarioLog, HttpMethod.GET);
		
		ArrayList<ResidenciaBean> listaRess = new ArrayList<ResidenciaBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			switch (tipo) {
			case MUNICIPIO:
				ps = nconexion.prepareStatement(anadeLimiteOffset(QUERY_GET_LISTA_RESIDENCIAS_MUNI, limite, offset));
				ps.setString(1, busqueda[0]);
				break;
			case PROVINCIA:
				ps = nconexion.prepareStatement(anadeLimiteOffset(QUERY_GET_LISTA_RESIDENCIAS_PROV, limite, offset));
				ps.setString(1, busqueda[0]);
				break;
			case PAIS:
				ps = nconexion.prepareStatement(anadeLimiteOffset(QUERY_GET_LISTA_RESIDENCIAS_PAIS, limite, offset));
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
							muni = GeoHandler.getMunicipio(nconexion, res.getMunicipioCod(), false, errorBean);
							if (muni != null) tablaMunis.put(codMuni, muni);
						} else {
							muni = tablaMunis.get(codMuni);
						}
						res.setMunicipio(muni);
					}
					listaRess.add(res);
				}
			} else {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "'tipo' desconocido");
			}
		} catch (SQLException e) {
			int[] loc = {LOC_H,LOC_M,1};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaRess;
	}

	public static ResidenciaBean insertResidencia(Connection conexion, ResidenciaBean resRaw, ErrorBean errorBean) {
		int LOC_M = 4;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ResidenciaBean res = null;
		if (resRaw != null) {
			int i = 0;
			String codigo;
			do {
				codigo = generaNuevoResCodigo(resRaw, i++);
			} while (existeResidencia(nconexion, codigo, errorBean) && i < 10);
			if(i >= 10) {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "nombre demasiado com�n, intenta otro nombre");
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
						int[] loc = {LOC_H,LOC_M,4};
						ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h96", loc, "no insertada (?)");
					}
				}
			} catch (SQLException e) {
				int[] loc = {LOC_H,LOC_M,3};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
				return null;
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			int[] loc = {LOC_H,LOC_M,1};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "debe incluir datos residencia");
		}
			
		return res;
	}

	public static ResidenciaBean updateResidencia(Connection conexion,
			String codigo, ResidenciaBean resRaw,ErrorBean errorBean) {
		int LOC_M = 5;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
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
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "Sin parametros para cambiar");
			} else {
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
							int[] loc = {LOC_H,LOC_M,4};
							ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h96", loc, "???");
						}
					} else {
						int[] loc = {LOC_H,LOC_M,3};
						ErrorBeanFabrica.generaErrorBean(errorBean, Status.NOT_FOUND, "h48", loc, "no encotrada residencia con codigo " + codigo);
					}
				} catch (SQLException e) {
					int[] loc = {LOC_H,LOC_M,2};
					ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
					e.printStackTrace();
					return null;
				} finally {
					terminaOperacion(nconexion, cierraConexion);
				}
			}
		} else {
			int[] loc = {LOC_H,LOC_M,1};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "debe incluir datos residencia");
		}
			
		return res;
	}

	public static boolean deleteResidencia(Connection conexion, String codigo, ErrorBean errorBean) {
		int LOC_M = 6;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		try {
			PreparedStatement ps = nconexion.prepareStatement(UPDATE_DELETE_RESIDENCIA);
			ps.setString(1, codigo);
			int c = ps.executeUpdate();
			if (c > 0) {
				return true;
			} else {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.NOT_FOUND, "h48", loc, "no encotrada residencia con codigo " + codigo);
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
