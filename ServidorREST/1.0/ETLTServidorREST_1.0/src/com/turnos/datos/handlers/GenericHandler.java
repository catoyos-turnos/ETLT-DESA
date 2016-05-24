package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.AccesoBD;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.datos.vo.UsuarioBean.NivelUsuario;
//69xxxx
public abstract class GenericHandler {
	
	private static final String QUERY_GET_INFO_DIA = "SELECT get_info_residencia_dia(?, ?) AS infodia";
	private static final String QUERY_GET_INFO_RANGO_DIAS = "SELECT get_info_residencia_rango_dias(?, ?, ?) AS infodias";
	
	protected static final SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
	protected static final String LMSN_QUERY_LIMIT_NO_OFFSET = " LIMIT ?";
	protected static final String LMSN_QUERY_LIMIT_OFFSET = " LIMIT ? OFFSET ?";
	protected static final String LMSN_QUERY_LIMIT_DF_NO_OFFSET = " LIMIT 60";
	protected static final String LMSN_QUERY_LIMIT_DF_OFFSET = " LIMIT 60 OFFSET ?";
	protected static final String LMSN_QUERY_NO_LIMIT_NO_OFFSET = "";
	protected static final String LMSN_QUERY_NO_LIMIT_OFFSET = " LIMIT 123456789 OFFSET ?";

	
	protected static java.sql.Date javaDateToSQLDate(Date fecha) {
		if(fecha == null) return null;
		return new java.sql.Date(fecha.getTime());
	}
	
	protected static Date sqlDateToJavaDate(java.sql.Date fecha) {
		if(fecha == null) return null;
		return new Date(fecha.getTime());
	}
	
	protected static Connection aseguraConexion(Connection conexion) {
		boolean nuevaConexion = (conexion == null);
		if (!nuevaConexion) {
			try {
				nuevaConexion = conexion.isClosed();
			} catch (SQLException e) {
				nuevaConexion = true;
				e.printStackTrace();
			}
		}
		return nuevaConexion ? AccesoBD.getConexion() : conexion;
	}

	public static boolean autenticar(UsuarioBean usuarioLog, String metodo, String codTrabRelevante, String codResRelevante) {
		if (codResRelevante==null) {
			return autenticar(usuarioLog, metodo);
		}
		
		if(usuarioLog != null && usuarioLog.getIdUsuario() != -1 && usuarioLog.isActivado()) {
			if(usuarioLog.getCodRes() == null || usuarioLog.getCodTrab() == null) {
				UsuarioBean aux = UsuarioHandler.getUsuario(null, usuarioLog.getIdUsuario(), new ErrorBean());
				if(aux == null) {
					usuarioLog.setIdUsuario(-1);
				} else {
					usuarioLog.setCodRes(aux.getCodRes());
					usuarioLog.setCodTrab(aux.getCodTrab());
				}
			}

			boolean resRel = (codResRelevante.equals(usuarioLog.getCodRes())) ? true : false;
			boolean trabRel = resRel;
			trabRel &= (codTrabRelevante == null || codTrabRelevante.equals(usuarioLog.getCodTrab())) ? true : false;
			
			NivelUsuario nivel = NivelUsuario.safeValueOf(usuarioLog.getNivel());
			if (nivel == null) return false;
			else {
				switch (nivel) {
				case USUARIO:
					switch (metodo) {
					case HttpMethod.GET:
					case HttpMethod.HEAD:
					case HttpMethod.OPTIONS:
						if (resRel) {
							return true;
						} else {
							return false;
						}
	
					case HttpMethod.POST:
					case HttpMethod.PUT:
					case HttpMethod.DELETE:
						if (trabRel) {
							return true;
						} else {
							return false;
						}
						
					default:
						return false;
					}
	
				case ADMIN:
					if (resRel) {
						return true;
					} else {
						return false;
					}
					
				case SUPERADMIN:
					return true;
	
				case BANEADO:
				default:
					return false;
				}
			}
		}
		
		return false;
	}

	public static boolean autenticar(UsuarioBean usuarioLog, String metodo) {
		if(usuarioLog!=null && usuarioLog.getIdUsuario()!=-1 && usuarioLog.isActivado()) {
			NivelUsuario nivel = NivelUsuario.safeValueOf(usuarioLog.getNivel());
			if (nivel == null) return false;
			else
				switch (nivel) {
				case USUARIO:
					switch (metodo) {
					case HttpMethod.GET:
					case HttpMethod.HEAD:
					case HttpMethod.OPTIONS:
						return true;
	
					case HttpMethod.POST:
					case HttpMethod.PUT:
					case HttpMethod.DELETE:
					default:
						return false;
					}
	
				case ADMIN:
				case SUPERADMIN:
					return true;
	
				case BANEADO:
				default:
					return false;
				}
			
		} else return false;
		
	}
	
	protected static void terminaOperacion(Connection nconexion, boolean cierraConexion) {
		if (cierraConexion && nconexion != null) {
			try {
//				nconexion.commit();
				nconexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected static String anadeLimiteOffset(String query, int limite, int offset){
		String lim = null;
		if(limite <= 0) {
			return query;
		} else if(offset > 0) {
			lim = " LIMIT %d OFFSET %d";
			lim = String.format(lim, limite, offset);
		} else {
			lim = " LIMIT %s";
			lim = String.format(lim, limite);
		}
		
		StringBuffer sb = new StringBuffer(query);
		int id = -1;
		if ((id = sb.lastIndexOf(";")) > 0) {
			sb.insert(id, lim);
		} else {
			sb.append(lim);
		}
		
		return sb.toString(); 
	}
	
	protected static String[] getInfoDia(Connection conexion,
			String codRes, java.sql.Date sqldate,
			ErrorBean errorBean) {
		Connection nconexion = GenericHandler.aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		PreparedStatement ps;
		ResultSet rs;
		String[] infoDia = null;
		try {
			ps = nconexion.prepareStatement(QUERY_GET_INFO_DIA);
			ps.setString(1, codRes);
			ps.setDate(2, sqldate);
			rs = ps.executeQuery();
			rs.first();
			infoDia = rs.getString("infodia").split(";");
			if (infoDia.length != 3 ) {
				infoDia = null;
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("72690000");
				errorBean.updateMsg("infoDia.length != 3 (infodia="+String.join(";", infoDia)+")");
			}
		} catch (SQLException e) {
				int[] loc = {LOC_H,LOC_M,1};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
			return null;
		} catch (Exception e) {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
			return null;
		} finally {
			GenericHandler.terminaOperacion(nconexion, cierraConexion);
		}
		return infoDia;
	}
	
	protected static Hashtable<String, String[]> getInfoRangoDias(Connection conexion,
			String codRes, java.sql.Date sqlFechaIni, java.sql.Date sqlFechaFin,
			ErrorBean errorBean) {
		int rango = (int) (sqlFechaFin.getTime()-sqlFechaIni.getTime())/(24*60*60*1000);
		return getInfoRangoDias(conexion, codRes, sqlFechaIni, rango, errorBean);
	}

	
	protected static Hashtable<String, String[]> getInfoRangoDias(Connection conexion,
			String codRes, java.sql.Date sqldate, int rango,
			ErrorBean errorBean) {
		Connection nconexion = GenericHandler.aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		PreparedStatement ps;
		ResultSet rs;
		Hashtable<String, String[]> infoDias = new Hashtable<String, String[]>(rango);
		if(rango < 0)
			return infoDias;
		String[] auxInfoDia = null;
		try {
			ps = nconexion.prepareStatement(QUERY_GET_INFO_RANGO_DIAS);
			ps.setString(1, codRes);
			ps.setDate(2, sqldate);
			ps.setInt(3, rango);
			rs = ps.executeQuery();
			rs.first();
			String str = rs.getString("infodias");
			String[] rows = str.split("|");
			String datestr, infostr;
			for (String row : rows) {
				datestr = sdfIn.format(sdfIn.parse(row.split(":")[0]));
				if (datestr.equalsIgnoreCase(row.split(":")[0])) {
					infostr = row.split(":")[1];
					auxInfoDia = infostr.split(";");
					if (auxInfoDia.length == 3) {
						infoDias.put(datestr, auxInfoDia);
					} else {
						System.out.println(auxInfoDia.length + "<"  + 3);
					}
					
				} else {
					System.out.println(datestr + "!=" + row.split(":")[0]);
				}
			}

		} catch (SQLException e) {
				int[] loc = {LOC_H,LOC_M,1};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
			return null;
		} catch (Exception e) {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h68", loc, e.getMessage(), null);
				e.printStackTrace();
			return null;
		} finally {
			GenericHandler.terminaOperacion(nconexion, cierraConexion);
		}
		return infoDias;
	}
	

	public static String quitaAcentos(String txt, int maxlenght) {
		return quitaAcentos(txt, maxlenght, 0, 0);
	}
	public static String quitaAcentos(String txt, int maxlenght, int ord) {
		return quitaAcentos(txt, maxlenght, ord, 0);
	}
	public static String quitaAcentos(String txt, int maxlenght, int ord, int zeropad) {
		txt = txt.toUpperCase();
		Pattern pattern = Pattern.compile("[ _,;:()]+");
		txt = pattern.matcher(txt).replaceAll("_");		
		String norm = Normalizer.normalize(txt, Normalizer.Form.NFD);
	    pattern = Pattern.compile("[^A-Z0-9_]+");
	    norm = pattern.matcher(norm).replaceAll("");

	    if(ord > 0) {
	    	String numstring = "_" + String.format("%0"+zeropad+"d", ord);
	    	if(norm.length() > (maxlenght - numstring.length())) {
		    	norm = norm.substring(0, maxlenght - numstring.length());
	    	}
	    	norm += numstring;
	    }
	    
	    return norm;
	}
		
	

}
