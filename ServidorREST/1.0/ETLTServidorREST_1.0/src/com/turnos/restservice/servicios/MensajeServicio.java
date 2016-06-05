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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.MensajeHandler;
import com.turnos.datos.handlers.MensajeHandler.RolUsuario;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.MensajeBean;
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
	@Path(WebServUtils.PREF_NUM_PATH)
	@Valid
	public Response getNumMensajes(
			@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser,
			@QueryParam(WebServUtils.Q_PARAM_LISTA_MSG_LEIDOS) @DefaultValue("true") boolean incLeidos
			) {
		ErrorBean errorBean = new ErrorBean();
		int numMensajes = MensajeHandler.numMensajesUser(null, codUser, incLeidos, errorBean);

		GenericEntity<Integer> numEntity = new GenericEntity<Integer>(new Integer(numMensajes), Integer.class);
		return Response.status(errorBean.getHttpCode()).entity(numEntity).build();
	}
	
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
		RolUsuario rol = RolUsuario.safeValueOf(rolStr);
		if(rol == null) {
			rol = RolUsuario.DESTINATARIO;
		}
		ArrayList<MensajeBean> listaMensajes = MensajeHandler.listMensajesUser(null, codUser, rol, incLeidos, profRespuestas, original, limite, offset, errorBean);

		return creaRespuestaGenericaGETLista(listaMensajes, errorBean, limite, offset);
	}
	
	@GET
	@Path(WebServUtils.COD_MENSAJE_PATH + WebServUtils.PREF_MSG_RESPUESTA_PATH + WebServUtils.PREF_NUM_PATH)
	@Valid
	public Response getNumRespuestas(
			@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser,
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) long codMensaje,
			@QueryParam(WebServUtils.Q_PARAM_LISTA_MSG_LEIDOS) @DefaultValue("true") boolean incLeidos
			) {
		ErrorBean errorBean = new ErrorBean();
		int numMensajes = MensajeHandler.numRespuestasMensaje(null, codMensaje, incLeidos, errorBean);

		GenericEntity<Integer> numEntity = new GenericEntity<Integer>(new Integer(numMensajes), Integer.class);
		return Response.status(errorBean.getHttpCode()).entity(numEntity).build();
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
		ArrayList<MensajeBean> listaMensajes = MensajeHandler.listRespuestasMensaje(null, codMensaje, profRespuestas, limite, offset,  errorBean);
		
		return creaRespuestaGenericaGETLista(listaMensajes, errorBean, limite, offset);
	}

	@GET
	@Path(WebServUtils.COD_MENSAJE_PATH)
	@Valid
	public Response getMensaje(
			@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser,
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) long codMensaje,
			@QueryParam(WebServUtils.Q_PARAM_PROF_RESPUESTAS) @DefaultValue("1") int profRespuestas,
			@QueryParam(WebServUtils.Q_PARAM_INC_MSG_ORIGINAL) @DefaultValue("false") boolean original) {
		ErrorBean errorBean = new ErrorBean();
		MensajeBean privado = MensajeHandler.getMensaje(null, codMensaje, profRespuestas, original, errorBean);
		
		return creaRespuestaGenericaGET(privado, errorBean);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoMensaje(MensajeBean privadoRaw,
			@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser) {
		ErrorBean errorBean = new ErrorBean();
		MensajeBean privado = MensajeHandler.insertMensaje(null, privadoRaw, errorBean);

		return creaRespuestaGenericaPOST(privado, errorBean);
	}

	@POST
	@Path(WebServUtils.COD_MENSAJE_PATH + WebServUtils.PREF_MSG_LEIDO_PATH)
	@Valid
	public Response setMensajeLeido(
			@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser,
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) long codMensaje,
			@QueryParam(WebServUtils.Q_PARAM_LEIDO) @DefaultValue("true") boolean leido) {
		ErrorBean errorBean = new ErrorBean();
		boolean res = MensajeHandler.marcaMensajeLeido(null, codMensaje, leido, errorBean);

		GenericEntity<Boolean> numEntity = new GenericEntity<Boolean>(new Boolean(res), Boolean.class);
		return Response.status(errorBean.getHttpCode()).entity(numEntity).build();
	}

	@PUT
	@Path(WebServUtils.COD_MENSAJE_PATH + WebServUtils.PREF_MSG_LEIDO_PATH)
	@Valid
	public Response setMensajeSiLeido(@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser,
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) long codMensaje) {
		
		return setMensajeLeido(codUser, codMensaje, true);
	}

	@DELETE
	@Path(WebServUtils.COD_MENSAJE_PATH + WebServUtils.PREF_MSG_LEIDO_PATH)
	@Valid
	public Response setMensajeNoLeido(@PathParam(WebServUtils.P_PARAM_COD_USER) long codUser,
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) long codMensaje) {
		
		return setMensajeLeido(codUser, codMensaje, false);
	}

}
