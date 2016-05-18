package com.turnos.datos.handlers;

public class ComentarioPropCambioHandler extends GenericHandler{

	private static final int LOC_H = 92;
	
	private static final String QUERY_LISTA_COMENTARIOS_PROP =
		"SELECT com.id_comentario_pc, com.id_prop_cambio, "
		+ "com.id_usuario, GET_NOMBRE_USUARIO(com.id_usuario) "
		+ "FROM comentario_prop_cambio com "
		+ "WHERE com.id_prop_cambio=? "
		+ "ORDER BY com.hora ";
	
	private static final String UPDATE_INSERT_COMENTARIO = 
		"INSERT INTO comentario_prop_cambio "
		+ "(id_prop_cambio, id_usuario, hora, texto) "
		+ "VALUES ?,?,?,?";
	private static final String UPDATE_DELETE_COMENTARIO = 
		"DELETE FROM comentario_prop_cambio com"
		+ "WHERE com.id_comentario_pc=?";
}
