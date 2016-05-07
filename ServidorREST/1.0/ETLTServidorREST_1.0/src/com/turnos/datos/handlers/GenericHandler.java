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

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.AccesoBD;
import com.turnos.datos.vo.ErrorBean;
//69xxxx
public abstract class GenericHandler {
	private static final SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
	private static final String QUERY_GET_INFO_DIA = "SELECT get_info_residencia_dia(?, ?) AS infodia";
	private static final String QUERY_GET_INFO_RANGO_DIAS = "SELECT get_info_residencia_rango_dias(?, ?, ?) AS infodias";

	
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

	protected static boolean autenticar(Connection conexion) {
		return true;
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
			e.printStackTrace();
			errorBean.setHttpCode(500);
			errorBean.updateErrorCode("72690001");
			errorBean.updateMsg(e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			errorBean.setHttpCode(500);
			errorBean.updateErrorCode("72690002");
			errorBean.updateMsg(e.getMessage());
			return null;
		} finally {
			GenericHandler.terminaOperacion(nconexion, cierraConexion);
		}
		return infoDia;
	}
	
	protected static Hashtable<String, String[]> getInfoRangoDias(Connection conexion,
			String codRes, java.sql.Date sqldate, int rango,
			ErrorBean errorBean) {
		Connection nconexion = GenericHandler.aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		PreparedStatement ps;
		ResultSet rs;
		Hashtable<String, String[]> infoDias = new Hashtable<String, String[]>(rango);
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
			e.printStackTrace();
			errorBean.setHttpCode(500);
			errorBean.updateErrorCode("72690101");
			errorBean.updateMsg(e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			errorBean.setHttpCode(500);
			errorBean.updateErrorCode("72690102");
			errorBean.updateMsg(e.getMessage());
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
