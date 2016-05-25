package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.UsuarioHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Usuario")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_USER_PATH)
public class UsuarioServicio extends GenericServicio{
	
	protected UsuarioServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected UsuarioServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------GET-----------------------------------------------
	
	@GET
	@Path(WebServUtils.COD_USER_PATH)
	@Valid
	public Response getUsuario(@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser,
			boolean incTrabRes) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.getUsuario(null, codUser, incTrabRes, errorBean);

		return creaRespuestaGenericaGET(usuario, errorBean);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoUsuario(UsuarioBean userRaw) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.insertUsuario(null, userRaw, errorBean);

		return creaRespuestaGenericaPOST(usuario, errorBean);
	}
	
	@PUT
	@Path(WebServUtils.COD_USER_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modUsuario(UsuarioBean userRaw,
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = null;
//		UsuarioBean usuario = UsuarioHandler.updateUsuario(null, codUser, userRaw, usuarioLog, errorBean);
		//TODO mod params uno a uno

		return creaRespuestaGenericaPUT(usuario, errorBean);
	}
	
	@PUT
	@Path(WebServUtils.COD_USER_PATH + WebServUtils.PREF_USER_NIVEL_PATH)
	@Valid
	public Response cambiaNivelUsuario(@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser,
			@QueryParam(WebServUtils.Q_PARAM_USER_NIVEL) String nivelStr) {
		ErrorBean errorBean = new ErrorBean();
		/* TODO */
		/*
		UsuarioBean usuario = UsuarioHandler.updateUsuario(null, codUser, userRaw, errorBean);
		return creaRespuestaGenericaPUT(usuario, errorBean);
		*/
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
	
	/*
	@GET
	@Valid
	public Response listaUsuarios () {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
	
	@DELETE
	@Path(WebServUtils.COD_USER_PATH)
	@Valid
	public Response borraUsuario(@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
	*/

}
