package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.turnos.datos.handlers.MensajeHandler;
import com.turnos.datos.handlers.MensajeHandler.RolUsuario;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.MensajeBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Mensaje")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_USER_PATH + WebServUtils.COD_USER_PATH + WebServUtils.PREF_MENSAJE_PATH)
public class MensajeServicio extends GenericServicio{
	
	protected MensajeServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected MensajeServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------GET-----------------------------------------------
	
	@GET
	@Valid
	public Response listaMensajes(
			@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser, // (usuario a buscar)
			@QueryParam(WebServUtils.Q_PARAM_ROL_EN_MSG) @DefaultValue("DESTINATARIO") String rolStr, // (rol de usuario a buscar: REMITENTE/DESTINATARIO)
			@QueryParam(WebServUtils.Q_PARAM_INC_MSG_ORIGINAL) @DefaultValue("false") boolean original, // (incluye mensaje original en respuestas)
			@QueryParam(WebServUtils.Q_PARAM_LISTA_MSG_LEIDOS) @DefaultValue("true") boolean incLeidos, // (lista mensajes ya leidos)
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite, // (cantidad a devolver)
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset, // (pagina)
			@QueryParam(WebServUtils.Q_PARAM_PROF_RESPUESTAS) @DefaultValue("0") int profRespuestas // (niveles de respuesta a devolver)
			) {
		ErrorBean errorBean = new ErrorBean();
		boolean aut = MensajeHandler.autenticar(null, null);
		RolUsuario rol = RolUsuario.safeValueOf(rolStr);
		if(rol == null) {
			rol = RolUsuario.DESTINATARIO;
		}
		RespuestaBean<MensajeBean> respuesta;
		ArrayList<MensajeBean> listaMensajes = MensajeHandler.listMensajesUser(null, codUser, rol, incLeidos, profRespuestas, original, limite, offset, aut, errorBean);
		
		if(listaMensajes == null) {
			respuesta = new RespuestaBean<MensajeBean>(listaMensajes);
		} else {
			respuesta = new RespuestaBean<MensajeBean>(listaMensajes);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Path(WebServUtils.COD_MENSAJE_PATH + WebServUtils.PREF_MSG_RESPUESTA_PATH)
	@Valid
	public Response listaRespuestas(
			@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser, // (usuario a buscar)
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) long codMensaje, // (mensaje original)
			@QueryParam(WebServUtils.Q_PARAM_INC_MSG_ORIGINAL) @DefaultValue("false") boolean original, // (incluye mensaje original en respuestas)
			@QueryParam(WebServUtils.Q_PARAM_LISTA_MSG_LEIDOS) @DefaultValue("true") boolean incLeidos, // (lista mensajes ya leidos)
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite, // (cantidad a devolver)
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset, // (pagina)
			@QueryParam(WebServUtils.Q_PARAM_PROF_RESPUESTAS) @DefaultValue("0") int profRespuestas // (niveles de respuesta a devolver)
			) {
		ErrorBean errorBean = new ErrorBean();
		boolean aut = MensajeHandler.autenticar(null, null);
		
		RespuestaBean<MensajeBean> respuesta;
		ArrayList<MensajeBean> listaMensajes = MensajeHandler.listRespuestasMensaje(null, codMensaje, profRespuestas, limite, offset, aut, errorBean);
		
		if(listaMensajes == null) {
			respuesta = new RespuestaBean<MensajeBean>(listaMensajes);
		} else {
			respuesta = new RespuestaBean<MensajeBean>(listaMensajes);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Path(WebServUtils.COD_MENSAJE_PATH)
	@Valid
	public Response getMensaje(
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser,
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) int codMensaje,
			@QueryParam(WebServUtils.Q_PARAM_PROF_RESPUESTAS) @DefaultValue("1") int profRespuestas,
			@QueryParam(WebServUtils.Q_PARAM_INC_MSG_ORIGINAL) @DefaultValue("false") boolean original) {
		ErrorBean errorBean = new ErrorBean();
		RespuestaBean<MensajeBean> respuesta = null;
		boolean aut = MensajeHandler.autenticar(null, null);
		MensajeBean privado = MensajeHandler.getMensaje(null, codMensaje, profRespuestas, original, aut, errorBean);
		
		if (privado == null) {
			respuesta = new RespuestaBean<MensajeBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<MensajeBean>(privado);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoMensaje(MensajeBean privadoRaw,
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		RespuestaBean<MensajeBean> respuesta = null;
		boolean aut = MensajeHandler.autenticar(null, null);
		MensajeBean privado = MensajeHandler.insertMensaje(null, privadoRaw, aut, errorBean);

		if(privado == null) {
			respuesta = new RespuestaBean<MensajeBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<MensajeBean>(privado);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	//TODO leido get/post/put/delete

}
