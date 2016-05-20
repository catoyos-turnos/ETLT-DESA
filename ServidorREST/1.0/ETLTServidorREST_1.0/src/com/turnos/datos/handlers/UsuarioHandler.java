package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.PasswordUtils;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.datos.vo.UsuarioBean.NivelUsuario;

public class UsuarioHandler extends GenericHandler {

	private static final int LOC_H = 90;

	private static final String QUERY_GET_USER_COD = "SELECT usr.id_usuario as idUsuario, get_nombre_usuario(usr.id_usuario) as nombre,"
			+ "usr.user, trab.codigo as codTrab, res.codigo as codRes, usr.activado, usr.nivel "
			+ "FROM app_usuario usr "
			+ "LEFT JOIN trabajador trab ON usr.id_trabajador=trab.id_trabajador "
			+ "LEFT JOIN residencia res ON usr.id_residencia=res.id_residencia "
			+ "WHERE usr.id_usuario=?";

	private static final String QUERY_GET_USER_USER_PASS = "SELECT usr.id_usuario as idUsuario, usr.user, usr.pass_hash, usr.activado, usr.nivel "
			+ "FROM app_usuario usr WHERE usr.user=?";

	private static final String UPDATE_INSERT_NUEVO_USER_ = "INSERT INTO app_usuario(id_usuario, user, pass_hash, id_trabajador, id_residencia, activado) "
			+ "VALUES ?,?,?,?,?,?"; // TODO ?

	private static final String UPDATE_UPDATE_USER = "UPDATE app_usuario SET %s WHERE id_usuario=?";

	private static final String UPDATE_DELETE_USER = "DELETE FROM app_usuario WHERE id_usuario=?";

	public static UsuarioBean getUsuario(Connection conexion, long codUser,
			ErrorBean errorBean) {
		int LOC_M = 1;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		UsuarioBean usuario = null;
		if (codUser >= 0) {
			PreparedStatement ps = null;
			ResultSet rs;
			try {
				ps = nconexion.prepareStatement(QUERY_GET_USER_COD);
				ps.setLong(1, codUser);
				rs = ps.executeQuery();
				if (rs.next()) {
					usuario = new UsuarioBean();
					usuario.setIdUsuario(rs.getLong("idUsuario"));
					usuario.setUser(rs.getString("user"));
					usuario.setNombre(rs.getString("nombre"));
					usuario.setCodTrab(rs.getString("codTrab"));
					usuario.setCodRes(rs.getString("codRes"));
					usuario.setActivado(rs.getBoolean("activado"));
					usuario.setNivel(rs.getString("nivel"));
				} else {
					errorBean.setHttpCode(Status.BAD_REQUEST);
					errorBean.updateErrorCode("91900102");
					errorBean.updateMsg("no encontrado usuario con codigo: "
							+ codUser);
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("91900101");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("91900100");
			errorBean.updateMsg("debe incluir codigo correcto");
		}
		return usuario;
	}

	public static UsuarioBean getUsuario(Connection conexion, String user,
			String pass, ErrorBean errorBean) {
		int LOC_M = 2;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		UsuarioBean usuario = null;
		if (user != null && !"".equals(user) && pass != null
				&& !"".equals(pass)) {
			PreparedStatement ps = null;
			ResultSet rs;
			try {
				ps = nconexion.prepareStatement(QUERY_GET_USER_USER_PASS);
				ps.setString(1, user);
				rs = ps.executeQuery();
				if (rs.next()) {
					boolean check = false;
					try {
						check = PasswordUtils.check(pass,
								rs.getString("pass_hash"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (check) {
						usuario = new UsuarioBean();
						usuario.setIdUsuario(rs.getLong("idUsuario"));
						usuario.setUser(rs.getString("user"));
						usuario.setActivado(rs.getBoolean("activado"));
						usuario.setNivel(rs.getString("nivel"));
					} else {
						errorBean.setHttpCode(Status.FORBIDDEN);
						errorBean.updateErrorCode("91900103");
						errorBean.updateMsg("no coincide contraseña");
					}
				} else {
					errorBean.setHttpCode(Status.BAD_REQUEST);
					errorBean.updateErrorCode("91900102");
					errorBean.updateMsg("no encontrado usuario: " + user);
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("91900101");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("91900100");
			errorBean.updateMsg("debe incluir codigo");
		}
		return usuario;
	}

	public static UsuarioBean insertUsuario(Connection conexion, UsuarioBean userRaw,
			ErrorBean errorBean) {
		int LOC_M = 3;
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean modUserUsuario(Connection conexion, long codUser,
			String user, ErrorBean errorBean) {
		int LOC_M = 4;
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean modPassUsuario(Connection conexion, long codUser,
			String pass, ErrorBean errorBean) {
		int LOC_M = 5;
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean modNombreUsuario(Connection conexion,
			long codUser, String nombre, ErrorBean errorBean) {
		int LOC_M = 6;
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean activarUsuario(Connection conexion, long codUser,
			ErrorBean errorBean) {
		int LOC_M = 7;
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean desactivarUsuario(Connection conexion,
			long codUser, ErrorBean errorBean) {
		int LOC_M = 8;
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean modNivelUsuario(Connection conexion,
			long codUser, NivelUsuario nivel, ErrorBean errorBean) {
		int LOC_M = 9;
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean deleteUsuario(Connection conexion, long codUser,
			ErrorBean errorBean) {
		int LOC_M = 10;
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean autenticar(UsuarioBean usuarioLog, String metodo,
			long idUsuarioRelevante) {
		if (metodo.equals(HttpMethod.GET) || metodo.equals(HttpMethod.HEAD)
				|| metodo.equals(HttpMethod.OPTIONS)) {
			return true;
		} else {
			if (usuarioLog != null && usuarioLog.getIdUsuario() != -1
					&& usuarioLog.isActivado()) {
				NivelUsuario nivel = NivelUsuario.safeValueOf(usuarioLog
						.getNivel());
				if (nivel == null)
					return false;
				else if (nivel == NivelUsuario.SUPERADMIN) {
					return true;
				} else if (nivel == NivelUsuario.BANEADO) {
					return false;
				} else {
					return (usuarioLog.getIdUsuario() == idUsuarioRelevante);
				}
			} else
				return false;
		}
	}

	// @Override
	public static boolean autenticar(UsuarioBean usuarioLog, String metodo,
			String codTrabRelevante, String codResRelevante) {
		return false;
	}

	// @Override
	public static boolean autenticar(UsuarioBean usuarioLog, String metodo) {
		return false;
	}

}
