package com.turnos.datos.handlers;

public class ComentarioPropCambioHandler extends GenericHandler{

	private static final int LOC_H = 92;
	
	private static final String QUERY_LISTA_COMENTARIOS_PROP =
		"SELECT com.id_comentario_pc, com.id_prop_cambio, "
		+ "com.id_usuario, GET_NOMBRE_USUARIO(com.id_usuario) as nombre, "
		+ "com.hora, com.texto "
		+ "FROM comentario_prop_cambio com "
		+ "WHERE com.id_prop_cambio=? "
		+ "ORDER BY com.hora ";
		
	private static final String QUERY_GET_COMENTARIO_COD =
		"SELECT com.id_comentario_pc, com.id_prop_cambio, "
		+ "com.id_usuario, GET_NOMBRE_USUARIO(com.id_usuario) as nombre, "
		+ "com.hora, com.texto "
		+ "FROM comentario_prop_cambio com "
		+ "WHERE com.id_comentario_pc=?";
	
	private static final String UPDATE_INSERT_COMENTARIO = 
		"INSERT INTO comentario_prop_cambio "
		+ "(id_prop_cambio, id_usuario, hora, texto) "
		+ "VALUES ?,?,?,?";
	private static final String UPDATE_DELETE_COMENTARIO = 
		"DELETE FROM comentario_prop_cambio com"
		+ "WHERE com.id_comentario_pc=?";
		
	public static ArrayList<ComentarioBean> listComentarioPropuesta(Connection conexion, 
			long propCod, int limite, int offset, ErrorBean errorBean) {
		int LOC_M = 1;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		ArrayList<ComentarioBean> listaComs = new ArrayList<ComentarioBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			ps = nconexion.prepareStatement(anadeLimiteOffset(QUERY_LISTA_COMENTARIOS_PROP, limite, offset));
			ps.setInt(1, propCod);		
			rs = ps.executeQuery();
			ComentarioBean com;
			while (rs.next()) {
				com = new ComentarioBean();
				com.setId_comentario(rs.getLong("id_comentario_pc"));
				com.setId_prop_cambio(rs.getLong("id_prop_cambio"));
				com.setId_usuario(rs.getLong("id_usuario"));					
				com.setNombreUsuario(rs.getString("nombre"));
				com.setHora(sqlDateToJavaDate(rs.getDate("hora")));
				com.setTexto(rs.getString("texto"));
				listaComs.add(com);
			}
			
		} catch (SQLException e) {
			int[] loc = {LOC_H,LOC_M,1};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
			e.printStackTrace();
			return null;
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return listaComs;	
	}
		
	public static ComentarioBean getComentarioPropuesta(Connection conexion, 
			long codComentario, ErrorBean errorBean) {
		int LOC_M = 2;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		ComentarioBean com = null;
		if (codComentario > -1) {
			try {
				PreparedStatement ps = nconexion.prepareStatement(QUERY_GET_COMENTARIO_COD);
				ps.setString(1, codComentario);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					com = new ComentarioBean();
					com.setId_comentario(rs.getLong("id_comentario_pc"));
					com.setId_prop_cambio(rs.getLong("id_prop_cambio"));
					com.setId_usuario(rs.getLong("id_usuario"));					
					com.setNombreUsuario(rs.getString("nombre"));
					com.setHora(sqlDateToJavaDate(rs.getDate("hora")));
					com.setTexto(rs.getString("texto"));
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
		
	public static ComentarioBean insertComentario(Connection conexion,
			ComentarioBean comentarioRaw, ErrorBean errorBean) {
		int LOC_M = 3;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ComentarioBean com = null;
		if (comentarioRaw != null) {
			try {
				PreparedStatement ps = nconexion.prepareStatement(UPDATE_INSERT_COMENTARIO, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, comentarioRaw.getId_prop_cambio());
				ps.setLong(2, comentarioRaw.getId_usuario());
				ps.setDate(3, javaDateToSqlDate(comentarioRaw.getHora()));
				ps.setString(4, comentarioRaw.getTexto());

				int c = ps.executeUpdate();
				if (c > 0 && ps.getGeneratedKeys().next()) {
					long codComentario = ps.getGeneratedKeys().getLong(1);
					com = getComentarioPropuesta(nconexion, codMensaje, errorBean);
					if (com == null) {
						int[] loc = {LOC_H,LOC_M,4};
						ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h96", loc, "no insertado (?)");
					}
				}
			} catch (SQLException e) {
				int[] loc = {LOC_H,LOC_M,3};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.INTERNAL_SERVER_ERROR, "h69", loc, e.getMessage(), null);
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			int[] loc = {LOC_H,LOC_M,1};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "h22", loc, "debe incluir datos comentario");
		}
			
		return res;
	}

	
	public static boolean deleteComentario(Connection conexion,
			long codComentario, ErrorBean errorBean) {
		int LOC_M = 4;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		try {
			PreparedStatement ps = nconexion.prepareStatement(UPDATE_DELETE_COMENTARIO);
			ps.setString(1, codComentario);
			int c = ps.executeUpdate();
			if (c > 0) {
				return true;
			} else {
				int[] loc = {LOC_H,LOC_M,2};
				ErrorBeanFabrica.generaErrorBean(errorBean, Status.NOT_FOUND, "h48", loc, "no encontrado comentario con codigo " + codComentario);
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
