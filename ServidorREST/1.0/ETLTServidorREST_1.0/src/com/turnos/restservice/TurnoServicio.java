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

import com.turnos.datos.vo.TurnoBean;

@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH + WebServUtils.PREF_TURNO_PATH)
public class TurnoServicio {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaTurnos (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).build();
	}
	
	@GET
	@Path(WebServUtils.COD_TURNO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getTurno (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		//TODO get trabajador por ids
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoTurno(TurnoBean turnoRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		// TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).build();
	}
	
	@PUT
	@Path(WebServUtils.COD_TURNO_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modTurno(TurnoBean turnoRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		// TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).build();
	}
	
	@DELETE
	@Path(WebServUtils.COD_TURNO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraTurno(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		//TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).build();
	}
	
}
