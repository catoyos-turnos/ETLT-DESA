package com.turnos.restservice;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.handlers.MensajeHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.MensajeBean;
import com.turnos.datos.vo.RespuestaBean;

@Path(WebServUtils.PREF_USER_PATH + WebServUtils.COD_USER_PATH + WebServUtils.PREF_MENSAJE_PATH)
public class MensajeService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaMensajes(
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser,
			@QueryParam(WebServUtils.Q_PARAM_COD_OTRO_USER) @DefaultValue("-1") int codOtroUser,
			@QueryParam(WebServUtils.Q_PARAM_ROL_EN_MSG) String rolStr,
			@QueryParam(WebServUtils.Q_PARAM_MSG_ES_RESP) @DefaultValue("-1") boolean esRespuesta,
			@QueryParam(WebServUtils.Q_PARAM_MSG_RESP_A) @DefaultValue("-1") int codOGMensaje,
			@QueryParam(WebServUtils.Q_PARAM_LISTA_MSG_LEIDOS) @DefaultValue("true") boolean incLeidos,
			@QueryParam(WebServUtils.Q_PARAM_LISTA_MSG_NO_LEIDOS) @DefaultValue("true") boolean incNoLeidos,
			@QueryParam(WebServUtils.Q_PARAM_LISTA_RESPUESTAS) @DefaultValue("true") boolean incResps,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI) @DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN) @DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_PROF_RESPUESTAS) @DefaultValue("0") int profRespuestas) {
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Path(WebServUtils.COD_MENSAJE_PATH + WebServUtils.PREF_MSG_RESPUESTA_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaRespuestas(
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser,
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) int codMensaje,
			@QueryParam(WebServUtils.Q_PARAM_LISTA_MSG_LEIDOS) @DefaultValue("true") boolean incLeidos,
			@QueryParam(WebServUtils.Q_PARAM_LISTA_MSG_NO_LEIDOS) @DefaultValue("true") boolean incNoLeidos,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI) @DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN) @DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_PROF_RESPUESTAS) @DefaultValue("1") int profRespuestas) {
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Path(WebServUtils.COD_MENSAJE_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getMensaje(
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser,
			@PathParam(WebServUtils.P_PARAM_COD_MENSAJE) int codMensaje,
			@QueryParam(WebServUtils.Q_PARAM_PROF_RESPUESTAS) @DefaultValue("1") int profRespuestas) {
		ErrorBean errorBean = new ErrorBean();
		RespuestaBean<MensajeBean> respuesta = null;
		boolean aut = MensajeHandler.autenticar(null);
		MensajeBean privado = MensajeHandler.getMensaje(null, codMensaje, profRespuestas, aut, errorBean);
		
		if (privado == null) {
			respuesta = new RespuestaBean<MensajeBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<MensajeBean>(privado);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoMensaje(MensajeBean privadoRaw,
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		RespuestaBean<MensajeBean> respuesta = null;
		boolean aut = MensajeHandler.autenticar(null);
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
