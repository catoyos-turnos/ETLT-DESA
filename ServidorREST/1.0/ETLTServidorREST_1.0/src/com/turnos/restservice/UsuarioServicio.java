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

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.UsuarioHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.RespuestaBean;
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
		RespuestaBean<UsuarioBean> respuesta = null;
		
		if(usuario == null) {
			respuesta = new RespuestaBean<UsuarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<UsuarioBean>(usuario);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoUsuario(UsuarioBean userRaw) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.insertUsuario(null, userRaw, errorBean);
		RespuestaBean<UsuarioBean> respuesta = null;

		if(usuario == null) {
			respuesta = new RespuestaBean<UsuarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<UsuarioBean>(usuario);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
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
		RespuestaBean<UsuarioBean> respuesta = null;
		
		if(usuario == null) {
			respuesta = new RespuestaBean<UsuarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<UsuarioBean>(usuario);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@DELETE
	@Path(WebServUtils.COD_USER_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraUsuario(@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		/*
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = UsuarioHandler.deleteUsuario(null, codUser, errorBean);
		RespuestaBean<UsuarioBean> respuesta;
		
		if(borrado) {
			respuesta = new RespuestaBean<UsuarioBean>();
			respuesta.setHtmlStatus(Status.ACCEPTED);
		} else {
			respuesta = new RespuestaBean<UsuarioBean>(errorBean);
		}

		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	*/
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

}
