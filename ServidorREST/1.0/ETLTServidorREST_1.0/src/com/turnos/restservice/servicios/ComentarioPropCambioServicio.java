package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.ComentarioPropCambioHandler;
import com.turnos.datos.vo.ComentarioBean;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Propuesta de cambio")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH + WebServUtils.PREF_PROP_CAMBIO_PATH + WebServUtils.COD_PROP_CAMBIO_PATH
		+ WebServUtils.PREF_COMENTARIO_PATH)
public class ComentarioPropCambioServicio extends GenericServicio{
	
	protected ComentarioPropCambioServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected ComentarioPropCambioServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------POST----------------------------------------------

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoComentario(ComentarioBean comRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) long codCambio) {
		ErrorBean eb = new ErrorBean();
		boolean auth = ComentarioPropCambioHandler.autenticar(usuarioLog, HttpMethod.POST, comRaw.getId_usuario());
		ComentarioBean comentario = null;
		if(auth)
			comentario = ComentarioPropCambioHandler.insertComentario(null, comRaw,  eb);
		else {;}//TODO

		return creaRespuestaGenericaPOST(comentario, eb);
	}
	
	// ---------------------DELETE--------------------------------------------

	@DELETE
	@ApiOperation(value = "Elimina comentario",
		response = ComentarioBean.class)
	@Path(WebServUtils.COD_COMENTARIO_PATH)
	@Valid
	public Response borraComentario(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) long codCambio,
			@PathParam(WebServUtils.P_PARAM_COD_COMENTARIO) long codCom) {
		ErrorBean eb = new ErrorBean();
		boolean auth = ComentarioPropCambioHandler.autenticar(usuarioLog, HttpMethod.DELETE);//TODO sacar id comentario
		boolean borrado = false;
		if(auth)
			borrado = ComentarioPropCambioHandler.deleteComentario(null, codCom,  eb);
		else {;}//TODO

		return creaRespuestaGenericaDELETE(borrado, ComentarioBean.class, eb);
	}
	
	// ---------------------PUT-----------------------------------------------

	/*
	@PUT
	@Path(WebServUtils.COD_COMENTARIO_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modComentario(ComentarioBean rawCom,
			@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio,
			@PathParam(WebServUtils.P_PARAM_COD_COMENTARIO) String codCom) {
		ErrorBean errorBean = new ErrorBean();
		ComentarioBean comentario = null;
//		ComentarioBean comentario = ComentarioPropCambioHandler.updateComentarioPropuesta(null, codUser, userRaw, errorBean); 
		RespuestaBean<ComentarioBean> respuesta = null;
		
		if(comentario == null) {
			respuesta = new RespuestaBean<ComentarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ComentarioBean>(comentario);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	*/
	
	// ---------------------GET-----------------------------------------------
	/*
	@GET
	@Valid
	public Response listaComentarios(@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<ComentarioBean> listaComentarios = null;
//		listaComentarios = ComentarioPropCambioHandler.listComentarioPropuesta(null, codRes, codCambio, errorBean); 
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
//		ComentarioBean comentario = ComentarioPropCambioHandler.getComentarioPropuesta(null, codUser, errorBean);
		RespuestaBean<ComentarioBean> respuesta = null;
		
		if(comentario == null) {
			respuesta = new RespuestaBean<ComentarioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ComentarioBean>(comentario);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	*/

}
