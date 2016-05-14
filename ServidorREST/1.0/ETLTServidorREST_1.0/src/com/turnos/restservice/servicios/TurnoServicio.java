package com.turnos.restservice.servicios;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.TurnoHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoBean.TipoTurno;

@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH + WebServUtils.PREF_TURNO_PATH)
public class TurnoServicio {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaTurnos (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@QueryParam(WebServUtils.Q_PARAM_TIPO_TURNO) String tipoStr,
			@QueryParam(WebServUtils.Q_PARAM_INC_SERVS) @DefaultValue("false") boolean includeServs) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<TurnoBean> listaTurnos;
		RespuestaBean<TurnoBean> respuesta = null;
		
		if (tipoStr == null) {
			listaTurnos = TurnoHandler.listTodosTurnos(null, codRes, includeServs, errorBean);
		} else {
			TipoTurno tipo = TipoTurno.safeValueOf(tipoStr);
			listaTurnos = TurnoHandler.listTurnosTipo(null, codRes, tipo, includeServs, errorBean);
		}
		
		if(listaTurnos == null) {
			respuesta = new RespuestaBean<TurnoBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<TurnoBean>(listaTurnos);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@GET
	@Path(WebServUtils.COD_TURNO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getTurno (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@QueryParam(WebServUtils.Q_PARAM_INC_SERVS) @DefaultValue("false") boolean includeServs) {
		ErrorBean errorBean = new ErrorBean();
		TurnoBean turno = TurnoHandler.getTurno(null, codRes, codTurno, includeServs, errorBean);
		RespuestaBean<TurnoBean> respuesta = null;

		if(turno == null) {
			respuesta = new RespuestaBean<TurnoBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<TurnoBean>(turno);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoTurno(TurnoBean turnoRaw, 
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean errorBean = new ErrorBean();
		boolean aut = TurnoHandler.autenticar(null);
		TurnoBean turno = TurnoHandler.insertTurno(null, codRes, turnoRaw, aut, errorBean);
		RespuestaBean<TurnoBean> respuesta = null;

		if(turno == null) {
			respuesta = new RespuestaBean<TurnoBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<TurnoBean>(turno);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@PUT
	@Path(WebServUtils.COD_TURNO_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modTurno(TurnoBean turnoRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		boolean aut = TurnoHandler.autenticar(null);
		TurnoBean turno = TurnoHandler.updateTurno(null, codRes, codTurno, turnoRaw, aut, errorBean);
		RespuestaBean<TurnoBean> respuesta = null;
		
		if(turno == null) {
			respuesta = new RespuestaBean<TurnoBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<TurnoBean>(turno);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@DELETE
	@Path(WebServUtils.COD_TURNO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraTurno(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		boolean aut = TurnoHandler.autenticar(null);
		boolean borrado = TurnoHandler.deleteTurno(null, codRes, codTurno, aut, errorBean);
		RespuestaBean<TurnoBean> respuesta = null;
		
		if(borrado) {
			respuesta = new RespuestaBean<TurnoBean>();
			respuesta.setHtmlStatus(Status.ACCEPTED);
		} else {
			respuesta = new RespuestaBean<TurnoBean>(errorBean);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
}
