package com.turnos.restservice;

import java.util.Calendar;
import java.util.Date;

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

import com.turnos.datos.handlers.TurnoTrabajadorDiaHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;

@Path("/res" + ResidenciaServicio.COD_RES_PATH + "/trab")
public class TrabajadorServicio {
	public static final String COD_TRAB_PATH = "/{codTrab: [A-Z0-9_]{3,32}}";
//	public static final String CODS_TRABS_PATH = "/{codsTrabs: ([A-Z0-9_]{3,32},)*[A-Z0-9_]{3,32}}";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaTrabajadores (@PathParam("codRes") String codRes) {
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).build();
	}
	
	@GET
	@Path(COD_TRAB_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getTrabajador (@PathParam("codRes") String codRes,
			@PathParam("codTrab") String codTrab) {
		//TODO get trabajador por ids
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTrab).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoTrabajador(TrabajadorBean trabRaw,
			@PathParam("codRes") String codRes) {
		// TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).build();
	}
	
	@PUT
	@Path(COD_TRAB_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modTrabajador(TrabajadorBean trabRaw,
			@PathParam("codRes") String codRes,
			@PathParam("codTrab") String codTrab) {
		// TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTrab).build();
	}
	
	@DELETE
	@Path(COD_TRAB_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraTrabajador (@PathParam("codRes") String codRes,
			@PathParam("codTrab") String codTrab) {
		//TODO borra trabajador
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).entity(codTrab).build();
	}
	
	
	@GET
	@Path(COD_TRAB_PATH + "/dia")
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public Response getHorarioTrabajadorDia(@PathParam("codRes") String codRes,
			@PathParam("codTrab") String codTrab,
			@DefaultValue("-1") @QueryParam("fecha") int time) {
		Date fecha = null;
		try {
			if (time > 0) {
				fecha = new Date(time * 1000l);
			} else {
				fecha = Calendar.getInstance().getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(fecha == null) return Response.status(Status.BAD_REQUEST).entity("momento ("+time+") no parseable, o algo").build();

		ErrorBean eb = new ErrorBean();
		TurnoTrabajadorDiaBean turnos = TurnoTrabajadorDiaHandler.getTurnoTrabajadorDia(null, codRes, codTrab, fecha, eb);
		if(turnos == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(turnos).build();
		}
	}

	@GET
	@Path(COD_TRAB_PATH + "/horario")
	@Produces(MediaType.TEXT_PLAIN)
	@Valid
	public Response getHorarioTrabajadorRango(@PathParam("codRes") String codRes,
			@PathParam("codTrab") String codTrab,
			@DefaultValue("-1") @QueryParam("fecha_ini") int time_ini,
			@DefaultValue("-1") @QueryParam("fecha_fin") int time_fin) {
		Date fecha_ini = null;
		Date fecha_fin = null;
		try {
			if (time_ini > 0) {
				fecha_ini = new Date(time_ini * 1000l);
			}
			if (time_fin > 0) {
				fecha_fin = new Date(time_fin * 1000l);
			}
			if(fecha_ini == null && fecha_fin == null) {
				fecha_ini = Calendar.getInstance().getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ErrorBean eb = new ErrorBean();
		TurnoTrabajadorDiaBean turnos = TurnoTrabajadorDiaHandler.getTurnosTrabajadorRango(null, codRes, codTrab, fecha_ini, fecha_fin, eb);
		if(turnos == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(turnos).build();
		}
	}

}
