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

import com.turnos.datos.handlers.FestivoHandler;
import com.turnos.datos.handlers.ResidenciaHandler;
import com.turnos.datos.handlers.ResidenciaHandler.TipoBusqueda;
import com.turnos.datos.handlers.TurnoTrabajadorDiaHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;

@Path(WebServUtils.PREF_RES_PATH)
public class ResidenciaServicio {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaResidencias (
			@QueryParam(WebServUtils.Q_PARAM_COD_PROV) @DefaultValue("") String provincia,
			@QueryParam(WebServUtils.Q_PARAM_COD_MUNI) @DefaultValue("") String municipio,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO) @DefaultValue("false") boolean includeGeo) {
		ErrorBean eb = new ErrorBean();
		ArrayList<ResidenciaBean> listaResidencias;
		if (!"".equals(municipio)) {
			String[] busqueda = { municipio };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.MUNICIPIO, busqueda, includeGeo, eb);
		} else if (!"".equals(provincia)) {
			String[] busqueda = { provincia };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.PROVINCIA, busqueda, includeGeo, eb);
		} else {
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.TODOS, null, includeGeo, eb);
		}
		if(listaResidencias == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(listaResidencias).build();
		}
	}
	
	@GET
	@Path(WebServUtils.COD_RES_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getResidencia(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.getResidencia(null, codRes, true, eb);
		if(residencia == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(residencia).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevaResidencia(ResidenciaBean resRaw) {
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.insertResidencia(null, resRaw, eb);
		
		if(residencia == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.CREATED).entity(residencia).build();
		}
	}
	
	@PUT
	@Path(WebServUtils.COD_RES_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modResidencia(ResidenciaBean resRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.updateResidencia(null, codRes, resRaw, eb);
		
		if(residencia == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.ACCEPTED).entity(residencia).build();
		}
	}
	
	@DELETE
	@Path(WebServUtils.COD_RES_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraResidencia(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean eb = new ErrorBean();
		boolean borrado = ResidenciaHandler.deleteResidencia(null, codRes, eb);
		
		if(borrado) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		}
	}

	
	
	@GET
	@Path(WebServUtils.COD_RES_PATH + WebServUtils.PREF_FEST_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getDiasFestivos(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE)
			@DefaultValue("-1") int limit) {

		ErrorBean eb = new ErrorBean();
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

			if (limit < 0 || limit > 25) {
				limit = 25;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<FestivoBean> listaFestivos = FestivoHandler.getFestivosResidencia(null, codRes, fecha_ini, fecha_fin, limit, false, eb);
		if(listaFestivos == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(listaFestivos).build();
		}
	}
	
	@GET
	@Path(WebServUtils.COD_RES_PATH + WebServUtils.PREF_HORARIO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getHorarioCompletoDia(
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
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
		ArrayList<TurnoTrabajadorDiaBean> listaTurnos = TurnoTrabajadorDiaHandler.getTodosTurnosDia(null, codRes, fecha, eb);
		if(listaTurnos == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(listaTurnos).build();
		}
	}
}
