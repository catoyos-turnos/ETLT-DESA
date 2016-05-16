package com.turnos.restservice.servicios;

@Api(value = "Propuesta de cambio")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_PROP_CAMBIO_PATH + WebServUtils.COD_PROP_CAMBIO_PATH
		+ WebServUtils.PREF_COMENTARIO_PATH)
public class ComentarioPropCambioServicio {
	private UsuarioBean usuarioLog;
	
	@Context
	private ComentarioPropCambioServicio(HttpServletRequest request) {
		Object usrObj = request==null?null:request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_USUARIO);
		if(usrObj != null && usrObj  instanceof UsuarioBean) {
			this.usuarioLog = (UsuarioBean) usrObj;
		}
	}

	@GET
	@Valid
	public Response listaComentarios(@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<ComentarioBean> listaComentarios;
		RespuestaBean<ComentarioBean> respuesta = null;

		listaComentarios = ComentarioPropCambioHandler.listComentarioPropuesta(null, codRes, codCambio, errorBean); //TODO
		
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
		ComentarioBean comentario = ComentarioPropCambioHandler.getComentarioPropuesta(null, codUser, errorBean); //TODO
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
		ComentarioBean comentario = ComentarioPropCambioHandler.insertComentarioPropuesta(null, userRaw, errorBean); //TODO
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
		ComentarioBean comentario = ComentarioPropCambioHandler.updateComentarioPropuesta(null, codUser, userRaw, errorBean); //TODO
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
		boolean borrado = ComentarioPropCambioHandler.deleteComentarioPropuesta(null, codCambio, codCom, errorBean); //TODO
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
