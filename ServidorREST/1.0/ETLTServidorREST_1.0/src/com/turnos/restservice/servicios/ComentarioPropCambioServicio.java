package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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
import com.turnos.datos.vo.ComentarioBean;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Propuesta de cambio")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_PROP_CAMBIO_PATH + WebServUtils.COD_PROP_CAMBIO_PATH
		+ WebServUtils.PREF_COMENTARIO_PATH)
public class ComentarioPropCambioServicio extends GenericServicio{
	
	protected ComentarioPropCambioServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected ComentarioPropCambioServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------GET-----------------------------------------------
	
	@GET
	@Valid
	public Response listaComentarios(@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<ComentarioBean> listaComentarios = null;
//		listaComentarios = ComentarioPropCambioHandler.listComentarioPropuesta(null, codRes, codCambio, errorBean); //TODO
		RespuestaBean<ComentarioBean> respuesta = null;
		
		if(listaComentarios == null) {
			respuesta = new RespuestaBean<ComentarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ComentarioBean>(listaComentarios);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@GET
	@Path(WebServUtils.COD_COMENTARIO_PATH)
	@Valid
	public Response getComentario(@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio,
			@PathParam(WebServUtils.P_PARAM_COD_COMENTARIO) String codCom) {
		ErrorBean errorBean = new ErrorBean();
		ComentarioBean comentario = null;
//		ComentarioBean comentario = ComentarioPropCambioHandler.getComentarioPropuesta(null, codUser, errorBean); //TODO
		RespuestaBean<ComentarioBean> respuesta = null;
		
		if(comentario == null) {
			respuesta = new RespuestaBean<ComentarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ComentarioBean>(comentario);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoComentario(ComentarioBean rawCom,
			@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio) {
		ErrorBean errorBean = new ErrorBean();
		ComentarioBean comentario = null;
//		ComentarioBean comentario = ComentarioPropCambioHandler.insertComentarioPropuesta(null, userRaw, errorBean); //TODO
		RespuestaBean<ComentarioBean> respuesta = null;

		if(comentario == null) {
			respuesta = new RespuestaBean<ComentarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ComentarioBean>(comentario);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@PUT
	@Path(WebServUtils.COD_COMENTARIO_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modComentario(ComentarioBean rawCom,
			@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio,
			@PathParam(WebServUtils.P_PARAM_COD_COMENTARIO) String codCom) {
		ErrorBean errorBean = new ErrorBean();
		ComentarioBean comentario = null;
//		ComentarioBean comentario = ComentarioPropCambioHandler.updateComentarioPropuesta(null, codUser, userRaw, errorBean); //TODO
		RespuestaBean<ComentarioBean> respuesta = null;
		
		if(comentario == null) {
			respuesta = new RespuestaBean<ComentarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ComentarioBean>(comentario);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@DELETE
	@Path(WebServUtils.COD_COMENTARIO_PATH)
	@Valid
	public Response borraComentario(@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio,
			@PathParam(WebServUtils.P_PARAM_COD_COMENTARIO) String codCom) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado =  false;
//		boolean borrado = ComentarioPropCambioHandler.deleteComentarioPropuesta(null, codCambio, codCom, errorBean); //TODO
		RespuestaBean<ComentarioBean> respuesta = null;
		
		if(borrado) {
			respuesta = new RespuestaBean<ComentarioBean>();
			respuesta.setHtmlStatus(Status.ACCEPTED);
		} else {
			respuesta = new RespuestaBean<ComentarioBean>(errorBean);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

}
