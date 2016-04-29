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

@Path("/res")
public class ResidenciaServicio {
	public static final String COD_RES_PATH = "/{codRes: [A-Z0-9_]{3,32}}";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaResidencias (
			@QueryParam("cod_muni") @DefaultValue("") String municipio,
			@QueryParam("cod_prov") @DefaultValue("") String provincia) {
		ErrorBean eb = new ErrorBean();
		ArrayList<ResidenciaBean> listaResidencias;
		if (!"".equals(municipio)) {
			String[] busqueda = { municipio };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.MUNICIPIO, busqueda, eb);
		} else if (!"".equals(provincia)) {
			String[] busqueda = { provincia };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.PROVINCIA, busqueda, eb);
		} else {
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.TODOS, null, eb);
		}
		if(listaResidencias == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(listaResidencias).build();
		}
	}
	
	@GET
	@Path(COD_RES_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getResidencia(@PathParam("codRes") String codRes) {
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.getResidencia(null, codRes, eb);
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
	@Path(COD_RES_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modResidencia(ResidenciaBean resRaw, @PathParam("codRes") String codRes) {
		System.out.println(resRaw);
		System.out.println(codRes);
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.updateResidencia(null, codRes, resRaw, eb);
		
		if(residencia == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.ACCEPTED).entity(residencia).build();
		}
	}
	
	@DELETE
	@Path(COD_RES_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraResidencia(@PathParam("codRes") String codRes) {
		ErrorBean eb = new ErrorBean();
		boolean borrado = ResidenciaHandler.deleteResidencia(null, codRes, eb);
		
		if(borrado) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		}
	}

	
	
	@GET
	@Path(COD_RES_PATH + "/festivos")
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getDiasFestivos(@PathParam("codRes") String codRes,
			@DefaultValue("-1") @QueryParam("fecha_ini") int time_ini,
			@DefaultValue("-1") @QueryParam("fecha_fin") int time_fin, 
			@DefaultValue("-1") @QueryParam("limite") int limit) {

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
		
		ArrayList<FestivoBean> listaFestivos = FestivoHandler.getFestivos(null, codRes, fecha_ini, fecha_fin, limit, eb);
		if(listaFestivos == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(listaFestivos).build();
		}
	}
	
	@GET
	@Path(COD_RES_PATH + "/horario")
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getHorarioCompletoDia(@PathParam("codRes") String codRes,
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
		ArrayList<TurnoTrabajadorDiaBean> listaTurnos = TurnoTrabajadorDiaHandler.getTodosTurnosDia(null, codRes, fecha, eb);
		if(listaTurnos == null) {
			return Response.status(eb.getHttpCode()).entity(eb).build();
		} else {
			return Response.status(Status.OK).entity(listaTurnos).build();
		}
	}
}
