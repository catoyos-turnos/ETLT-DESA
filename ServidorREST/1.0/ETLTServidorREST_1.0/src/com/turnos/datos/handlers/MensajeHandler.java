package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.MensajeBean;
import com.turnos.datos.vo.UsuarioBean;

//91xxxx
public class MensajeHandler extends GenericHandler {

	private static final String WHSN_QUERY_MENSAJE_USER_REMITENTE = "msg.id_remitente=?";
	private static final String WHSN_QUERY_MENSAJE_USER_DESTINO = "msg.id_destinatario=?";
	private static final String WHSN_QUERY_MENSAJE_LEIDO = "msg.leido=?";
	private static final String WHSN_QUERY_MENSAJE_ORIGINAL = "msg.respuesta_a=?";
	private static final String WHSN_QUERY_MENSAJE_ES_RESP = "msg.respuesta_a IS NOT NULL";
	private static final String WHSN_QUERY_MENSAJE_NO_ES_RESP = "msg.respuesta_a IS NULL";

	private static final String QUERY_LISTA_MENSAJE =
			"SELECT msg.id_privado as idMensaje, msg.id_remitente as idRemitente, "
				+ "msg.id_destinatario as idDestinatario, msg.respuesta_a as idMsgOriginal, "
				+ "msg.hora as hora, msg.texto as texto, msg.leido as leido "
			+ "FROM mensaje_privado msg "
			+ "WHERE %s "
			+ "ORDER BY msg.hora ASC";

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
	
	//00xx
	public static ArrayList<MensajeBean> listMensajesUser(Connection conexion,
			long usrMensaje, int profRespuestas, boolean aut, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//01xx
	public static ArrayList<MensajeBean> listRespuestasMensaje(
			Connection conexion, long codMensaje, int profRespuestas, boolean aut,
			ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57910100");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}

		ArrayList<MensajeBean> listaMsgs = new ArrayList<MensajeBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			ps = nconexion.prepareStatement(String.format(QUERY_LISTA_MENSAJE, WHSN_QUERY_MENSAJE_ORIGINAL));
			ps.setLong(1, codMensaje);
			rs = ps.executeQuery();

			MensajeBean msg;
			long idusr;
			UsuarioBean user;
			ArrayList<MensajeBean> respuestas;
			Hashtable<String, UsuarioBean> tablaUsers = new Hashtable<String, UsuarioBean>();
			while (rs.next()) {
				msg = new MensajeBean();
				msg.setIdMensaje(rs.getLong("idMensaje"));
				msg.setHora(sqlDateToJavaDate(rs.getDate("hora")));
				msg.setIdMsjOriginal(rs.getLong("idMsgOriginal"));
				msg.setLeido(rs.getBoolean("leido"));
				msg.setTexto(rs.getString("texto"));

				idusr = rs.getLong("idRemitente");
				if (!tablaUsers.containsKey(""+idusr)) {
					user = UsuarioHandler.getUsuario(nconexion, idusr, errorBean);
					if (user != null) tablaUsers.put(""+idusr, user);
				} else {
					user = tablaUsers.get(""+idusr);
				}
				if (user != null) {
					msg.setRemitente(user);
				}

				idusr = rs.getLong("idDestinatario");
				if (!tablaUsers.containsKey(""+idusr)) {
					user = UsuarioHandler.getUsuario(nconexion, idusr, errorBean);
					if (user != null) tablaUsers.put(""+idusr, user);
				} else {
					user = tablaUsers.get(""+idusr);
				}
				if (user != null) {
					msg.setDestinatario(user);
				}

				if (profRespuestas > 0) {
					respuestas = MensajeHandler.listRespuestasMensaje(nconexion, codMensaje, profRespuestas - 1, aut, errorBean);
					msg.setRespuestas(respuestas);
				}
			}
		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69910101");
			errorBean.updateMsg(e.getMessage());
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaMsgs;
	}

	//02xx
	public static MensajeBean getMensaje(Connection conexion, long codMensaje,
			int profRespuestas, boolean aut, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57910200");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}
		MensajeBean msg = null;

		if (codMensaje >= 0) {
			try {
				PreparedStatement ps = nconexion.prepareStatement(QUERY_GET_MENSAJE_COD);
				ps.setLong(1, codMensaje);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					msg = new MensajeBean();
					msg.setIdMensaje(rs.getLong("idMensaje"));
					msg.setHora(sqlDateToJavaDate(rs.getDate("hora")));
					msg.setIdMsjOriginal(rs.getLong("idMsgOriginal"));
					msg.setLeido(rs.getBoolean("leido"));
					msg.setTexto(rs.getString("texto"));

					long idusr = rs.getLong("idRemitente");
					UsuarioBean user = UsuarioHandler.getUsuario(nconexion, idusr, errorBean);
					if (user != null) {
						msg.setRemitente(user);
					}

					idusr = rs.getLong("idDestinatario");
					user = UsuarioHandler.getUsuario(nconexion, idusr, errorBean);
					if (user != null) {
						msg.setDestinatario(user);
					}

					if (profRespuestas > 0) {
						ArrayList<MensajeBean> respuestas = MensajeHandler.listRespuestasMensaje(nconexion, codMensaje, profRespuestas - 1, aut, errorBean);
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

	//02xx
	public static MensajeBean insertMensaje(Connection conexion, MensajeBean privadoRaw, boolean aut, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57910300");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}
		MensajeBean msg = null;

		if (privadoRaw != null) {
			try {
				PreparedStatement ps;

				if (privadoRaw.getIdMsjOriginal() == -1) {
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
					ps.setLong(4, privadoRaw.getIdMsjOriginal());
				}
				int c = ps.executeUpdate();
				if (c > 0 && ps.getGeneratedKeys().next()) {
					long codMensaje = ps.getGeneratedKeys().getLong(1);					
					msg = getMensaje(nconexion, codMensaje, 1, aut, errorBean);
					if(msg == null) {
						errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
						errorBean.updateErrorCode("69910203");
						errorBean.updateMsg("no insertado (?)");
					}
				}

			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69910202");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69910201");
			errorBean.updateMsg("debe incluir datos mensaje");
		}
			
		return msg;

	}

	//03xx
	public static boolean marcaMensajeLeido(Connection conexion,
			long codMensaje, boolean leido, boolean aut, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57910300");
			errorBean.updateMsg("Sin autenticar");
			return false;
		}

		try {
			PreparedStatement ps = nconexion.prepareStatement(UPDATE_UPDATE_MENSAJE_LEIDO);
			ps.setBoolean(1, leido);
			ps.setLong(2, codMensaje);

			int c = ps.executeUpdate();
			if (c > 0) {
				return true;
			}
		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69910301");
			errorBean.updateMsg(e.getMessage());
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
			
			
		return false;
	}

}
