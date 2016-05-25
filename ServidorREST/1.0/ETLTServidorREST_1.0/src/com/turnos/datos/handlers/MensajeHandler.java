package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.MensajeBean;
import com.turnos.datos.vo.UsuarioBean;

public class MensajeHandler extends GenericHandler {

	private static final int LOC_H = 95;

	public static enum RolUsuario{
		REMITENTE, DESTINATARIO;
		public static RolUsuario safeValueOf(String arg) {
			try{return valueOf(arg);}
			catch(Exception e){return null;}
		}
	};
	
	private static final String WHSN_QUERY_MENSAJE_USER_REMITENTE = "msg.id_remitente=?";
	private static final String WHSN_QUERY_MENSAJE_USER_DESTINO = "msg.id_destinatario=?";
	@SuppressWarnings("unused")
	private static final String WHSN_QUERY_MENSAJE_LEIDO = "msg.leido=true";
	private static final String WHSN_QUERY_MENSAJE_NO_LEIDO = "msg.leido=false";
	private static final String WHSN_QUERY_MENSAJE_ORIGINAL = "msg.respuesta_a=?";
	@SuppressWarnings("unused")
	private static final String WHSN_QUERY_MENSAJE_ES_RESP = "msg.respuesta_a IS NOT NULL";
	@SuppressWarnings("unused")
	private static final String WHSN_QUERY_MENSAJE_NO_ES_RESP = "msg.respuesta_a IS NULL";
	
	private static final String QUERY_NUM_MENSAJES_NO_LEIDOS =
			"SELECT COUNT(1) FROM mensaje_privado msg "
			+ "WHERE msg.id_destinatario=? AND msg.leido=false";
			
	private static final String QUERY_LISTA_MENSAJE =
			"SELECT msg.id_privado as idMensaje, msg.id_remitente as idRemitente, "
				+ "msg.id_destinatario as idDestinatario, msg.respuesta_a as idMsgOriginal, "
				+ "msg.hora as hora, msg.texto as texto, msg.leido as leido "
			+ "FROM mensaje_privado msg WHERE %s "
			+ "ORDER BY msg.hora DESC";

	private static final String QUERY_GET_MENSAJE_COD =
			"SELECT msg.id_privado as idMensaje, msg.id_remitente as idRemitente, "
				+ "msg.id_destinatario as idDestinatario, msg.respuesta_a as idMsgOriginal, "
				+ "msg.hora as hora, msg.texto as texto, msg.leido as leido "
			+ "FROM mensaje_privado msg "
			+ "WHERE msg.id_privado=?";

	private static final String UPDATE_INSERT_NUEVO_MENSAJE = "INSERT INTO mensaje_privado (id_remitente, id_destinatario, hora, texto) VALUES (?,?,?,?)";
	private static final String UPDATE_INSERT_NUEVA_RESPUESTA =
			"INSERT INTO mensaje_privado (id_remitente, id_destinatario, respuesta_a, hora, texto) "
			+ "SELECT ?, msg.id_remitente, msg.id_privado, ?, ? FROM mensaje_privado msg WHERE msg.id_privado=?";

	private static final String UPDATE_UPDATE_MENSAJE_LEIDO = "UPDATE mensaje_privado SET leido=? WHERE id_privado=?";


	public static ArrayList<MensajeBean> listMensajesUser(Connection conexion,
			long usrMensaje, RolUsuario rol, boolean listaLeidos,
			int profRespuestas, boolean original, int limite, int offset, ErrorBean errorBean) {
		int LOC_M = 1;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<MensajeBean> listaMsgs = new ArrayList<MensajeBean>();
		PreparedStatement ps = null;
		ResultSet rs;

		try {
			String where = "";
			switch (rol) {
			case DESTINATARIO:
				if (listaLeidos) {
					where = WHSN_QUERY_MENSAJE_USER_DESTINO;
				} else {
					where = WHSN_QUERY_MENSAJE_USER_DESTINO + " AND " + WHSN_QUERY_MENSAJE_NO_LEIDO;
				}
				break;
			case REMITENTE:
				where = WHSN_QUERY_MENSAJE_USER_REMITENTE;
				break;
			default:
				where = "false";
				break;
			}

			String lmt = "";
			if (limite > 0) {
				if (offset > 0) {
					lmt = LMSN_QUERY_LIMIT_OFFSET;
				} else {
					lmt = LMSN_QUERY_LIMIT_NO_OFFSET;
				}
			} else {
				if (offset > 0) {
					lmt = LMSN_QUERY_LIMIT_DF_OFFSET;
				} else {
					lmt = LMSN_QUERY_LIMIT_DF_NO_OFFSET;
				}
			}
			ps = nconexion.prepareStatement(String.format(QUERY_LISTA_MENSAJE, where) + lmt);
			int i = 1;
			ps.setLong(i++, usrMensaje);
			if (limite > 0) ps.setInt(i++, limite);
			if (offset > 0) ps.setInt(i++, offset);
			rs = ps.executeQuery();
			
			MensajeBean msg;
			long idusr;
			UsuarioBean user;
			ArrayList<MensajeBean> respuestas;
			Hashtable<String, UsuarioBean> tablaUsers = new Hashtable<String, UsuarioBean>();
			while (rs.next()) {
				msg = new MensajeBean();
				msg.setId_mensaje(rs.getLong("idMensaje"));
				msg.setHora(sqlDateToJavaDate(rs.getDate("hora")));
				msg.setIdMsgOriginal(rs.getLong("idMsgOriginal"));
				msg.setLeido(rs.getBoolean("leido"));
				msg.setTexto(rs.getString("texto"));

				idusr = rs.getLong("idRemitente");
				if (!tablaUsers.containsKey(""+idusr)) {
					user = UsuarioHandler.getUsuario(nconexion, idusr, false, errorBean);
					if (user != null) tablaUsers.put(""+idusr, user);
				} else {
					user = tablaUsers.get(""+idusr);
				}
				if (user != null) {
					msg.setRemitente(user);
				}

				idusr = rs.getLong("idDestinatario");
				if (!tablaUsers.containsKey(""+idusr)) {
					user = UsuarioHandler.getUsuario(nconexion, idusr, false, errorBean);
					if (user != null) tablaUsers.put(""+idusr, user);
				} else {
					user = tablaUsers.get(""+idusr);
				}
				if (user != null) {
					msg.setDestinatario(user);
				}

				if (profRespuestas > 0) {
					respuestas = MensajeHandler.listRespuestasMensaje(
							nconexion, msg.getId_mensaje(), profRespuestas - 1, 0, 0, errorBean);
					msg.setRespuestas(respuestas);
				}
				
				if (original) {
					msg.setMsgOriginal(getMensaje(nconexion, msg.getId_msg_original(), 0, false, errorBean));
				}
				
				listaMsgs.add(msg);
			}
		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69910101");
			errorBean.updateMsg(e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}

		return listaMsgs;
	}

	public static ArrayList<MensajeBean> listRespuestasMensaje(
			Connection conexion, long codMensaje, int profRespuestas,
			int limite, int offset,  ErrorBean errorBean) {
		int LOC_M = 2;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<MensajeBean> listaMsgs = new ArrayList<MensajeBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			String lmt = "";
			if (limite > 0) {
				if (offset > 0) {
					lmt = LMSN_QUERY_LIMIT_OFFSET;
				} else {
					lmt = LMSN_QUERY_LIMIT_NO_OFFSET;
				}
			} else {
				if (offset > 0) {
					lmt = LMSN_QUERY_LIMIT_DF_OFFSET;
				} else {
					lmt = LMSN_QUERY_LIMIT_DF_NO_OFFSET;
				}
			}
			ps = nconexion.prepareStatement(String.format(QUERY_LISTA_MENSAJE, WHSN_QUERY_MENSAJE_ORIGINAL) + lmt);
			ps.setLong(1, codMensaje);
			rs = ps.executeQuery();

			MensajeBean msg;
			long idusr;
			UsuarioBean user;
			ArrayList<MensajeBean> respuestas;
			Hashtable<String, UsuarioBean> tablaUsers = new Hashtable<String, UsuarioBean>();
			while (rs.next()) {
				msg = new MensajeBean();
				msg.setId_mensaje(rs.getLong("idMensaje"));
				msg.setHora(sqlDateToJavaDate(rs.getDate("hora")));
				msg.setIdMsgOriginal(rs.getLong("idMsgOriginal"));
				msg.setLeido(rs.getBoolean("leido"));
				msg.setTexto(rs.getString("texto"));

				idusr = rs.getLong("idRemitente");
				if (!tablaUsers.containsKey("" + idusr)) {
					user = UsuarioHandler.getUsuario(nconexion, idusr, false, errorBean);
					if (user != null) tablaUsers.put("" + idusr, user);
				} else {
					user = tablaUsers.get("" + idusr);
				}
				if (user != null) {
					msg.setRemitente(user);
				}

				idusr = rs.getLong("idDestinatario");
				if (!tablaUsers.containsKey("" + idusr)) {
					user = UsuarioHandler.getUsuario(nconexion, idusr, false, errorBean);
					if (user != null) tablaUsers.put("" + idusr, user);
				} else {
					user = tablaUsers.get("" + idusr);
				}
				if (user != null) {
					msg.setDestinatario(user);
				}

				if (profRespuestas > 0) {
					respuestas = MensajeHandler.listRespuestasMensaje(
							nconexion, msg.getId_mensaje(), profRespuestas - 1, 0, 0,  errorBean);
					msg.setRespuestas(respuestas);
				}

				listaMsgs.add(msg);
			}
		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69910101");
			errorBean.updateMsg(e.getMessage());
			listaMsgs = null;
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaMsgs;
	}

	public static MensajeBean getMensaje(Connection conexion, long codMensaje,
			int profRespuestas, boolean original,  
			ErrorBean errorBean) {
		int LOC_M = 3;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		MensajeBean msg = null;

		if (codMensaje >= 0) {
			try {
				PreparedStatement ps = nconexion.prepareStatement(QUERY_GET_MENSAJE_COD);
				ps.setLong(1, codMensaje);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					msg = new MensajeBean();
					msg.setId_mensaje(rs.getLong("idMensaje"));
					msg.setHora(sqlDateToJavaDate(rs.getDate("hora")));
					msg.setIdMsgOriginal(rs.getLong("idMsgOriginal"));
					msg.setLeido(rs.getBoolean("leido"));
					msg.setTexto(rs.getString("texto"));

					long idusr = rs.getLong("idRemitente");
					UsuarioBean user = UsuarioHandler.getUsuario(nconexion, idusr, false, errorBean);
					if (user != null) {
						msg.setRemitente(user);
					}

					idusr = rs.getLong("idDestinatario");
					user = UsuarioHandler.getUsuario(nconexion, idusr, false, errorBean);
					if (user != null) {
						msg.setDestinatario(user);
					}

					if (profRespuestas > 0) {
						ArrayList<MensajeBean> respuestas = MensajeHandler.listRespuestasMensaje(
								nconexion, codMensaje, profRespuestas - 1, 0, 0, errorBean);
						msg.setRespuestas(respuestas);
					}
				} else {
					errorBean.setHttpCode(Status.NOT_FOUND);
					errorBean.updateErrorCode("69910202");
					errorBean.updateMsg("no encotrada mensaje con codigo " + codMensaje);
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69910201");
				errorBean.updateMsg(e.getMessage());
				msg = null;
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69910200");
			errorBean.updateMsg("debe incluir correcto");
		}

		return msg;
	}

	public static MensajeBean insertMensaje(Connection conexion,
			MensajeBean privadoRaw, ErrorBean errorBean) {
		int LOC_M = 4;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		MensajeBean msg = null;
		if (privadoRaw != null) {
			try {
				PreparedStatement ps;
				if (privadoRaw.getId_msg_original() == -1) {
					ps = nconexion.prepareStatement(UPDATE_INSERT_NUEVO_MENSAJE, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, privadoRaw.getRemitente().getIdUsuario());
					ps.setLong(2, privadoRaw.getDestinatario().getIdUsuario());
					ps.setDate(3, javaDateToSQLDate(privadoRaw.getHora()));
					ps.setString(4, privadoRaw.getTexto());
				} else {
					ps = nconexion.prepareStatement(UPDATE_INSERT_NUEVA_RESPUESTA, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, privadoRaw.getRemitente().getIdUsuario());
					ps.setDate(2, javaDateToSQLDate(privadoRaw.getHora()));
					ps.setString(3, privadoRaw.getTexto());
					ps.setLong(4, privadoRaw.getId_msg_original());
				}
				int c = ps.executeUpdate();
				if (c > 0 && ps.getGeneratedKeys().next()) {
					long codMensaje = ps.getGeneratedKeys().getLong(1);
					msg = getMensaje(nconexion, codMensaje, 1, false, errorBean);
					if (msg == null) {
						int[] loc = {LOC_H,LOC_M,3};
						ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h96", loc, "no insertado (?)");
					}
				}	
			} catch (SQLException e) {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
				return null;
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		}else {
			int[] loc = {LOC_H,LOC_M,1};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "debe incluir datos mensaje");
			return null;
		}

		return msg;
	}

	public static boolean marcaMensajeLeido(Connection conexion,
			long codMensaje, boolean leido,
			ErrorBean errorBean) {
		int LOC_M = 5;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		try {
			PreparedStatement ps = nconexion.prepareStatement(UPDATE_UPDATE_MENSAJE_LEIDO);
			ps.setBoolean(1, leido);
			ps.setLong(2, codMensaje);

			int c = ps.executeUpdate();
			if (c > 0) {
				return true;
			} else {
				int[] loc = { LOC_H, LOC_M, 3 };
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.NOT_FOUND, "h48", loc, "no encotrado mensaje con codigo " + codMensaje);
			}
		} catch (SQLException e) {
			int[] loc = {LOC_H,LOC_M,2};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}

		return false;
	}
	
	public static int numNoLeidosUser(Connection conexion,
			long usrMensaje, int limite, int offset, ErrorBean errorBean) {
		return 0;
	}


}
