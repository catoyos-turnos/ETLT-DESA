package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;

//23xxxx
public class AutenticacionHandler extends GenericHandler {

	private static final String GET_APP_Y_SERV_KEYS = "SELECT app.llave_secreta as secretKey, serv.valor as servidorKey FROM aplicacion app, config_servidor serv WHERE app.llave_publica=? AND serv.config='SERVIDOR_KEY'";
	
	public static String[] getAuthKeys(Connection conexion, String publicKey, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		String[] keys = new String[2];
		if (publicKey != null && !"".equals(publicKey)) {			
			PreparedStatement ps = null;
			ResultSet rs;
			try {
				ps = nconexion.prepareStatement(GET_APP_Y_SERV_KEYS);
				ps.setString(1, publicKey);
				rs = ps.executeQuery();
				if (rs.next()) {
					keys[0] = rs.getString("secretKey");
					keys[1] = rs.getString("servidorKey");
				} else {
					errorBean.setHttpCode(Status.BAD_REQUEST);
					errorBean.updateErrorCode("95230100");
					errorBean.updateMsg("clave publica no encontrada");
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("95230101");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("95230100");
			errorBean.updateMsg("debe incluir clave publica");
		}
		return keys;
	}

	public static UsuarioBean getUsuarioPorUserPass(Connection conexion, String user, String pass, ErrorBean errorBean) {
		System.out.println(pass);
		return UsuarioHandler.getUsuario(conexion, user, pass, errorBean);
	}
}
