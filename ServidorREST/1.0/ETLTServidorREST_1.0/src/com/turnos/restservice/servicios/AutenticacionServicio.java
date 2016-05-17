package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.CriptoUtils;
import com.turnos.datos.WebServUtils;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.restservice.filtros.AutenticacionFiltro;

@Api(value = "Autenticacion")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_AUTH_PATH)
public class AutenticacionServicio extends GenericServicio{
	private String secretKey;
	private String servidorKey;
	
	protected AutenticacionServicio(UsuarioBean usuarioLog, String secretKey, String servidorKey) {
		super(usuarioLog);
		this.secretKey = secretKey;
		this.servidorKey = servidorKey;
	}
	
	protected AutenticacionServicio(@Context ContainerRequestContext request) {
		super(request);
		Object secretKObj = request.getProperty(AutenticacionFiltro.REQUEST_PARAM_APP_SECRET);
		this.secretKey = secretKObj == null ? null : (String) secretKObj;
		Object servKObj = request.getProperty(AutenticacionFiltro.REQUEST_PARAM_SERVIDOR_KEY);
		this.servidorKey = servKObj == null ? null : (String) servKObj;

		System.out.println(secretKObj + "///" +  servKObj);
		System.out.println(secretKey + "///" +  servidorKey);
	}

	// ---------------------GET-----------------------------------------------
	
	@GET
	@Path(WebServUtils.PREF_LOGIN_PATH)
	public Response login() {

		if(usuarioLog == null) {
			System.out.println("usuarioLog == null");
		} else {
			System.out.println(usuarioLog.getIdUsuario()
					+","+usuarioLog.getUser()+","+usuarioLog.getNombre()
					+","+usuarioLog.getNivel()+","+usuarioLog.isActivado());
		}
		
		
		String tokenSesion = null;
		if(usuarioLog!=null && secretKey!=null && servidorKey!=null) {
			tokenSesion = generaTokenSesion(usuarioLog, secretKey, servidorKey);
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
		fields[0] = CriptoUtils.generaRandomHexString(8); //sal
		fields[1] = "" + System.currentTimeMillis();
		try {
			fields[2] = CriptoUtils.encripta(desncrB, secretKey);
			String desncrA = String.join("@", fields);
			String tokenSesion = null;
			try {
				tokenSesion = CriptoUtils.encripta(desncrA, servidorKey);
				// System.out.println("tokenSesion: " + tokenSesion);
				return tokenSesion;
			} catch (Exception e) {
			// TODO error
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO error
			e.printStackTrace();
		}
		return null;
	}
}
