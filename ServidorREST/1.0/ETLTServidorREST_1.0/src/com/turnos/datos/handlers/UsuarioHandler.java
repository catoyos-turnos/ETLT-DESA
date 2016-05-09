package com.turnos.datos.handlers;

import java.sql.Connection;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;

//90xxxx
public class UsuarioHandler {
	
	private static final String QUERY_GET_USER =
			"SELECT usr.id_usuario, usr.user, trab.codigo as codTrab, "
				+ "res.codigo as codRes, user.activado "
			+ "FROM app_usuario usr "
				+ "LEFT JOIN trabajador trab ON usr.id_trabajador=trab.id_trabajador "
				+ "LEFT JOIN residencia res ON usr.id_residencia=res.id_residencia "
			+ "WHERE usr.user=?";

	private static final String QUERY_GET_USER_COD =
			"SELECT usr.id_usuario, usr.user, trab.codigo as codTrab, "
				+ "res.codigo as codRes, user.activado "
			+ "FROM app_usuario usr "
				+ "LEFT JOIN trabajador trab ON usr.id_trabajador=trab.id_trabajador "
				+ "LEFT JOIN residencia res ON usr.id_residencia=res.id_residencia "
			+ "WHERE usr.id_usuario=?";

	private static final String UPDATE_INSERT_NUEVO_USER_ =
			"INSERT INTO app_usuario(id_usuario, user, pass_hash, id_trabajador, id_residencia, activado) "
			+ "VALUES ?,?,?,?,?,?"; // TODO ?
	
	private static final String UPDATE_UPDATE_USER = 
			"UPDATE app_usuario SET %s WHERE id_usuario=?";
	
	private static final String UPDATE_DELETE_USER = "DELETE FROM app_usuario WHERE id_usuario=?";


	public static UsuarioBean getUsuario(Connection conexion, long codUser,
			ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean insertUsuario(Connection conexion, UsuarioBean userRaw,
			ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean updateUsuario(Connection conexion, long codUser,
			UsuarioBean userRaw, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean activarUsuario(Connection conexion, long codUser, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static UsuarioBean desactivarUsuario(Connection conexion, long codUser, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean deleteUsuario(Connection conexion, long codUser,
			ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return false;
	}

}
