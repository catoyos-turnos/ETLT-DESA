package com.turnos.datos.handlers;

import java.sql.Connection;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;

//90xxxx
public class UsuarioHandler {
	
	// TODO
	private static final String QUERY_GET_USER_COD = "";
	// TODO
	private static final String UPDATE_INSERT_NUEVO_USER = "";
	// TODO
	private static final String UPDATE_UPDATE_USER = "";
	// TODO
	private static final String UPDATE_DELETE_USER = ""; 

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

	public static boolean deleteUsuario(Connection conexion, long codUser,
			ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return false;
	}

}
