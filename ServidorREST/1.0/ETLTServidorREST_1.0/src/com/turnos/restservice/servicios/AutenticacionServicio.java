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
	@Context
	private HttpServletRequest request;
	
	@GET
	public Response login() {
		Object usrObj = request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_USUARIO);
		UsuarioBean usuario = null;
		if(usrObj != null && usrObj  instanceof UsuarioBean) {
			usuario = (UsuarioBean) usrObj;
		}
		return Response.status(Status.OK).entity(usuario).build();
	}
}
