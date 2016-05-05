package com.turnos.restservice;

import java.util.ArrayList;
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

import com.turnos.datos.handlers.TrabajadorHandler;
import com.turnos.datos.handlers.TurnoTrabajadorDiaHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;

@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH + WebServUtils.PREF_TRAB_PATH)
public class TrabajadorServicio {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaTrabajadores (@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<TrabajadorBean> listaTrabajadores = TrabajadorHandler.listTrabajadores(null, codTrab, errorBean);
		
		if(listaTrabajadores == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(listaTrabajadores).build();
		}
	}
	
	@GET
	@Path(WebServUtils.COD_TRAB_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getTrabajador (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		TrabajadorBean trabajador = TrabajadorHandler.getTrabajador(null, codRes, codTrab, errorBean);
		
		if(trabajador == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(trabajador).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoTrabajador(TrabajadorBean trabRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean errorBean = new ErrorBean();
		TrabajadorBean trabajador = TrabajadorHandler.insertTrabajador(null, codRes, trabRaw, errorBean);
		
		if(trabajador == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.CREATED).entity(trabajador).build();
		}
	}
	
	@PUT
	@Path(WebServUtils.COD_TRAB_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modTrabajador(TrabajadorBean trabRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		TrabajadorBean trabajador = TrabajadorHandler.updateTrabajador(null, codRes, codTrab, trabRaw, errorBean);
		
		if(trabajador == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.ACCEPTED).entity(trabajador).build();
		}
	}
	
	@DELETE
	@Path(WebServUtils.COD_TRAB_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraTrabajador (@PathParam("codRes") String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = TrabajadorHandler.deleteTrabajador(null, codRes, codTrab, errorBean);
		
		if(borrado) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		}
	}
	
	
	@GET
	@Path(WebServUtils.COD_TRAB_PATH + WebServUtils.PREF_DIA_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public Response getHorarioTrabajadorDia(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@QueryParam(WebServUtils.Q_PARAM_FECHA)
			@DefaultValue("-1") int time) {
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
	@Path(WebServUtils.COD_TRAB_PATH + WebServUtils.PREF_HORARIO_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	@Valid
	public Response getHorarioTrabajadorRango(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin) {
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
