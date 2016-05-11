package com.turnos.datos.handlers;

//91xxxx
public class PropCambioHandler extends GenericHandler{
	private static final String QUERY_LISTA_PROP_CAMBIOS_USUARIO =
		"SELECT com.id_comentario_pc, com.id_prop_cambio, "
		+ "com.id_usuario, GET_NOMBRE_USUARIO(com.id_usuario) "
		+ "FROM comentario_prop_cambio com "
		+ "WHERE com.id_prop_cambio=? "
		+ "ORDER BY com.hora ";

	private static final String UPDATE_INSERT_PROP_CAMBIOS = 
		"INSERT INTO comentario_prop_cambio "
		+ "(id_prop_cambio, id_usuario, hora, texto) "
		+ "VALUES ?,?,?,?";
	private static final String UPDATE_UPDATE_PROP_CAMBIOS = 
			"UPDATE propuesta_cambio "
			+ "(id_prop_cambio, id_usuario, hora, texto) "
			+ "VALUES ?,?,?,?";
	private static final String UPDATE_DELETE_PROP_CAMBIOS = 
		"DELETE FROM comentario_prop_cambio com"
		+ "WHERE com.id_comentario_pc=?";
}
