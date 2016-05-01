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

import com.turnos.datos.vo.ServicioBean;

@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH
		+ WebServUtils.PREF_TURNO_PATH + WebServUtils.COD_TURNO_PATH
		+ WebServUtils.PREF_SERV_PATH)
public class ServicioServicio {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaTurnos (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).build();
	}
	
	@GET
	@Path(WebServUtils.COD_SERV_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getTurno (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) String codServ) {
		//TODO get trabajador por ids
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).entity(codServ).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoTurno(ServicioBean servicioRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		// TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).build();
	}
	
	@PUT
	@Path(WebServUtils.COD_SERV_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modTurno(ServicioBean servicioRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) String codServ) {
		// TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).entity(codServ).build();
	}
	
	@DELETE
	@Path(WebServUtils.COD_SERV_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraTurno(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) String codServ) {
		//TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).entity(codServ).build();
	}

}
