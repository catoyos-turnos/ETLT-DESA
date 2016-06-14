package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.turnos.datos.CriptoUtils;
import com.turnos.datos.WebServUtils;
import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.handlers.UsuarioHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.SesionBean;
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
	}

	// ---------------------GET-----------------------------------------------
	
	@GET
	@Path(WebServUtils.PREF_LOGIN_PATH)
	public Response login() {
//		System.out.println("+++");
		ErrorBean errorBean = new ErrorBean();
		long time = System.currentTimeMillis();
		String tokenSesion = null;
		UsuarioBean usuario = null;
		SesionBean sesion = null;
		if(usuarioLog!=null && secretKey!=null && servidorKey!=null) {
			tokenSesion = generaTokenSesion(usuarioLog, secretKey, servidorKey, time);
			usuario = UsuarioHandler.getUsuario(null, usuarioLog.getIdUsuario(), true, errorBean);
			if(tokenSesion!=null && usuario!=null) {
				sesion = new SesionBean();
				sesion.setTokenSesion(tokenSesion);
				sesion.setUsuario(usuario);
				sesion.setAbierto(time);
				sesion.setTokenCaduca(AutenticacionFiltro.MARGEN_SESION + time);
			} else {
				ErrorBeanFabrica.generaUNAUTHORIZEDErrorBean(errorBean, "xx999999999", "no autorizado", null);
			}
		}

		return creaRespuestaGenericaGET(sesion, errorBean);
	}
	
	private static String generaTokenSesion(UsuarioBean res, String secretKey, String servidorKey, long time){
		String[] fieldsB = new String[4];
		fieldsB[0] = "" + res.getIdUsuario();
		fieldsB[1] = res.getUser();
		fieldsB[2] = res.getNivel();
		fieldsB[3] = "" + res.isActivado();
		String desncrB = String.join(":", fieldsB);
		String[] fields = new String[3];
		fields[0] = CriptoUtils.generaRandomHexString(8); //sal
		fields[1] = "" + time;
		try {
			fields[2] = CriptoUtils.encripta(desncrB, secretKey);
			String desncrA = String.join("@", fields);
			String tokenSesion = null;
			try {
				tokenSesion = CriptoUtils.encripta(desncrA, servidorKey);
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
