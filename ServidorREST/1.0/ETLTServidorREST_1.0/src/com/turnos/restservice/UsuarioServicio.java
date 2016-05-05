package com.turnos.restservice;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.handlers.UsuarioHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;

@Path(WebServUtils.PREF_USER_PATH)
public class UsuarioServicio {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaUsuarios () {
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
	
	@GET
	@Path(WebServUtils.COD_USER_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getUsuario(@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.getUsuario(null, codUser, errorBean);
		
		if(usuario == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(usuario).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoUsuario(UsuarioBean userRaw) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.insertUsuario(null, userRaw, errorBean);
		
		if(usuario == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.CREATED).entity(usuario).build();
		}
	}
	
	@PUT
	@Path(WebServUtils.COD_USER_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modUsuario(UsuarioBean userRaw,
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.updateUsuario(null, codUser, userRaw, errorBean);
		
		if(usuario == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.ACCEPTED).entity(usuario).build();
		}
	}
	
	@DELETE
	@Path(WebServUtils.COD_USER_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraUsuario(@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = UsuarioHandler.deleteUsuario(null, codUser, errorBean);
		
		if(borrado) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		}
	}

}
