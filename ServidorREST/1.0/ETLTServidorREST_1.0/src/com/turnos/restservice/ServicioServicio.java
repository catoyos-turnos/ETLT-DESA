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

@Path("/res" + ResidenciaServicio.COD_RES_PATH + "/turno" + TurnoServicio.COD_TURNO_PATH + "/serv")
public class ServicioServicio {
	public static final String COD_TURNO_PATH = "/{codServ: [0-9]{1,10}}";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaTurnos (@PathParam("codRes") String codRes,
			@PathParam("codTurno") String codTurno) {
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).build();
	}
	
	@GET
	@Path(COD_TURNO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getTurno (@PathParam("codRes") String codRes,
			@PathParam("codTurno") String codTurno,
			@PathParam("codServ") String codServ) {
		//TODO get trabajador por ids
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).entity(codServ).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoTurno(ServicioBean servicioRaw,
			@PathParam("codRes") String codRes,
			@PathParam("codTurno") String codTurno) {
		// TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).build();
	}
	
	@PUT
	@Path(COD_TURNO_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modTurno(ServicioBean servicioRaw,
			@PathParam("codRes") String codRes,
			@PathParam("codTurno") String codTurno,
			@PathParam("codServ") String codServ) {
		// TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).entity(codServ).build();
	}
	
	@DELETE
	@Path(COD_TURNO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraTurno(@PathParam("codRes") String codRes,
			@PathParam("codTurno") String codTurno,
			@PathParam("codServ") String codServ) {
		//TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTurno).entity(codServ).build();
	}

}
