package com.turnos.restservice;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.turnos.datos.handlers.TurnoHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoBean.TipoTurno;

@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH + WebServUtils.PREF_TURNO_PATH)
public class TurnoServicio {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaTurnos (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@QueryParam(WebServUtils.Q_PARAM_TIPO_TURNO)
			String tipoStr) {
		ErrorBean errorBean = new ErrorBean();
		TipoTurno tipo = TipoTurno.safeValueOf(tipoStr);
		ArrayList<TurnoBean> listaTurnos = TurnoHandler.listTurnos(null, codRes, tipo, errorBean);
		
		if(listaTurnos == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(listaTurnos).build();
		}
	}
	
	@GET
	@Path(WebServUtils.COD_TURNO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getTurno (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		TurnoBean turno = TurnoHandler.getTurno(null, codRes, codTurno, errorBean);
		if(turno == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(turno).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoTurno(TurnoBean turnoRaw, 
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean errorBean = new ErrorBean();
		TurnoBean turno = TurnoHandler.insertTurno(null, codRes, turnoRaw, errorBean);
		
		if(turno == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.CREATED).entity(turno).build();
		}
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
		TurnoBean turno = TurnoHandler.updateTurno(null, codRes, codTurno, turnoRaw, errorBean);
		
		if(turno == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.ACCEPTED).entity(turno).build();
		}
	}
	
	@DELETE
	@Path(WebServUtils.COD_TURNO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraTurno(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = TurnoHandler.deleteTurno(null, codRes, codTurno, errorBean);
		
		if(borrado) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		}
	}
	
}
