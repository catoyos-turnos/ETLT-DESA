package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.restservice.filtros.AutenticacionFiltro;

@Api(value = "Autenticacion")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_AUTH_PATH)
public class AutenticacionServicio {
	private UsuarioBean usuarioLog;
	private String secretKey;
	private String servidorKey;
	
	@Context
	private AutenticacionServicio(HttpServletRequest request) {
		if(request!=null) {
			Object usrObj = request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_USUARIO);
			if(usrObj != null && usrObj  instanceof UsuarioBean) {
				this.usuarioLog = (UsuarioBean) usrObj;
			}
			
			Object secretKObj = request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_APP_SECRET);
			this.secretKey = (String) secretKObj;
			
			Object servKObj = request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_SERVIDOR_KEY);
			this.servidorKey = (String) servKObj;
		}
		
	}
	
	@GET
	@Path(WebServUtils.PREF_LOGIN_PATH)
	public Response login() {
		UsuarioBean usuario = null;
		String secretKey = null;
		String servidorKey = null;
		if(request!=null) {
			Object usrObj =
					request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_USUARIO);
			if(usrObj != null && usrObj  instanceof UsuarioBean) {
				usuario = (UsuarioBean) usrObj;
			}
			
			Object secretKObj =
					request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_APP_SECRET);
			secretKey = (String)secretKObj;
			
			Object servKObj =
					request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_SERVIDOR_KEY);
			servidorKey = (String)servKObj;
		} else {
			// TODO error
		}
		String tokenSesion = null;
		if(usuario!=null && secretKey!=null && servidorKey!=null) {
			tokenSesion = generaTokenSesion(usuario, secretKey, servidorKey);
		}
		
		if(tokenSesion == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		} else {
			return Response.status(Status.OK).entity(tokenSesion).build();
		}
	}
	
	private static String generaTokenSesion(UsuarioBean res, String secretKey, String servidorKey){
		String[] fieldsB = new String[4];
		fieldsB[0] = "" + res.getIdUsuario();
		fieldsB[1] = res.getUser();
		fieldsB[2] = res.getNivel();
		fieldsB[3] = "" + res.isActivado();
		String desncrB = String.join(":", fieldsB);
		String[] fields = new String[3];
		fields[0] = CipherUtils.generaRandomHexString(8); //sal
		fields[1] = "" + System.currentTimeMillis();
		try {
			fields[2] = CriptoUtils.encripta(desncrB, secretKey);
			String desncrA = String.join("@", fields);
			String tokenSesion = null;
			try {
				tokenSesion = CriptoUtils.encripta(desncrA, servidorKey);
				// System.out.println("tokenSesion: " + tokenSesion);
				return tokenSesion;
			} catch (InvalidKeyException | UnsupportedEncodingException
					| NoSuchAlgorithmException | NoSuchProviderException
					| NoSuchPaddingException | ShortBufferException
					| IllegalBlockSizeException | BadPaddingException e) {
			// TODO error
				e.printStackTrace();
			}
		} catch (InvalidKeyException | UnsupportedEncodingException
				| NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException | ShortBufferException
				| IllegalBlockSizeException | BadPaddingException e1) {
			// TODO error
			e1.printStackTrace();
		}
		return null;
	}
}
