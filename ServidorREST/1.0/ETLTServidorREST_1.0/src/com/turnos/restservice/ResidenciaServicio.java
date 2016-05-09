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
import com.turnos.datos.handlers.VacacionesHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;
import com.turnos.datos.vo.VacacionesBean;

@Path(WebServUtils.PREF_RES_PATH)
public class ResidenciaServicio {
	
	// ---------------------GET-----------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaResidencias (
			@QueryParam(WebServUtils.Q_PARAM_COD_PAIS) @DefaultValue("") String pais,
			@QueryParam(WebServUtils.Q_PARAM_COD_PROV) @DefaultValue("") String provincia,
			@QueryParam(WebServUtils.Q_PARAM_COD_MUNI) @DefaultValue("") String municipio,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO) @DefaultValue("false") boolean includeGeo) {
		ErrorBean eb = new ErrorBean();
		ArrayList<ResidenciaBean> listaResidencias = null;
		RespuestaBean<ResidenciaBean> respuesta;
		
		if (!"".equals(municipio)) {
			String[] busqueda = { municipio };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.MUNICIPIO, busqueda, includeGeo, eb);
		} else if (!"".equals(provincia)) {
			String[] busqueda = { provincia };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.PROVINCIA, busqueda, includeGeo, eb);
		} else if (!"".equals(pais)) {
			String[] busqueda = { pais };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.PAIS, busqueda, includeGeo, eb);
		} else {
			eb.setHttpCode(Status.BAD_REQUEST);
			eb.updateErrorCode("48700000");
			eb.updateMsg("debe incluir parametros de busqueda: " + WebServUtils.Q_PARAM_COD_PAIS + ", "
					+ WebServUtils.Q_PARAM_COD_PROV + ", o " + WebServUtils.Q_PARAM_COD_MUNI);
		}

		if(listaResidencias == null) {
			respuesta = new RespuestaBean<ResidenciaBean>(eb);
		} else {
			respuesta = new RespuestaBean<ResidenciaBean>(listaResidencias);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@GET
	@Path(WebServUtils.COD_RES_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getResidencia(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.getResidencia(null, codRes, true, eb);
		RespuestaBean<ResidenciaBean> respuesta;

		if(residencia == null) {
			respuesta = new RespuestaBean<ResidenciaBean>(eb);
		} else {
			respuesta = new RespuestaBean<ResidenciaBean>(residencia);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	// ---------------------POST----------------------------------------------

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevaResidencia(ResidenciaBean resRaw) {
		ErrorBean eb = new ErrorBean();
		boolean aut = ResidenciaHandler.autenticar(null);
		ResidenciaBean residencia = ResidenciaHandler.insertResidencia(null, resRaw, aut, eb);
		RespuestaBean<ResidenciaBean> respuesta;
		
		if(residencia == null) {
			respuesta = new RespuestaBean<ResidenciaBean>(eb);
		} else {
			respuesta = new RespuestaBean<ResidenciaBean>(residencia);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	// ---------------------PUT-----------------------------------------------

	@PUT
	@Path(WebServUtils.COD_RES_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modResidencia(ResidenciaBean resRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean eb = new ErrorBean();
		boolean aut = ResidenciaHandler.autenticar(null);
		ResidenciaBean residencia = ResidenciaHandler.updateResidencia(null, codRes, resRaw, aut, eb);
		RespuestaBean<ResidenciaBean> respuesta;
		
		if(residencia == null) {
			respuesta = new RespuestaBean<ResidenciaBean>(eb);
		} else {
			respuesta = new RespuestaBean<ResidenciaBean>(residencia);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}

		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	// ---------------------DELETE--------------------------------------------

	@DELETE
	@Path(WebServUtils.COD_RES_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraResidencia(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean eb = new ErrorBean();
		boolean aut = ResidenciaHandler.autenticar(null);
		boolean borrado = ResidenciaHandler.deleteResidencia(null, codRes, aut, eb);
		RespuestaBean<ResidenciaBean> respuesta;
		
		if(borrado) {
			respuesta = new RespuestaBean<ResidenciaBean>();
			respuesta.setHtmlStatus(Status.ACCEPTED);
		} else {
			respuesta = new RespuestaBean<ResidenciaBean>(eb);
		}

		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	// ---------------------misc.---------------------------------------------

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
		RespuestaBean<FestivoBean> respuesta = null;
		ArrayList<FestivoBean> listaFestivos = null;
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

			if (limit < 0 || limit > 20) {
				limit = 20;
			}
			
		} catch (Exception e) {
			eb.setHttpCode(Status.BAD_REQUEST);
			eb.updateErrorCode("48700500");
			eb.updateMsg("momentos ("+time_ini+","+time_fin+") no parseable, o algo");
			eb.updateMsg(e.getMessage());
		}

		listaFestivos = FestivoHandler.getFestivosResidencia(null, codRes, fecha_ini, fecha_fin, limit, false, eb);
		
		if(listaFestivos == null) {
			respuesta = new RespuestaBean<FestivoBean>(eb);
		} else {
			respuesta = new RespuestaBean<FestivoBean>(listaFestivos);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@GET
	@Path(WebServUtils.COD_RES_PATH + WebServUtils.PREF_VACS_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getVacacionesDia(
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@QueryParam(WebServUtils.Q_PARAM_FECHA)
			@DefaultValue("-1") int time) {
		ErrorBean eb = new ErrorBean();
		RespuestaBean<VacacionesBean> respuesta = null;
		ArrayList<VacacionesBean> listaVacaciones = null;
		Date fecha = null;
		try {
			if (time > 0) {
				fecha = new Date(time * 1000l);
			} else {
				fecha = Calendar.getInstance().getTime();
			}
		} catch (Exception e) {
			eb.setHttpCode(Status.BAD_REQUEST);
			eb.updateErrorCode("48700600");
			eb.updateMsg("momento ("+time+") no parseable, o algo");
			eb.updateMsg(e.getMessage());
		}
		
		if(fecha != null) {
			listaVacaciones = VacacionesHandler.listVacacionesResDia(null, codRes, fecha, eb);
		}
		
		if(listaVacaciones == null) {
			respuesta = new RespuestaBean<VacacionesBean>(eb);
		} else {
			respuesta = new RespuestaBean<VacacionesBean>(listaVacaciones);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@GET
	@Path(WebServUtils.COD_RES_PATH + WebServUtils.PREF_HORARIO_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getHorarioCompletoDia(
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@QueryParam(WebServUtils.Q_PARAM_FECHA)
			@DefaultValue("-1") int time) {
		ErrorBean eb = new ErrorBean();
		RespuestaBean<TurnoTrabajadorDiaBean> respuesta = null;
		ArrayList<TurnoTrabajadorDiaBean> listaTurnos = null;
		Date fecha = null;
		try {
			if (time > 0) {
				fecha = new Date(time * 1000l);
			} else {
				fecha = Calendar.getInstance().getTime();
			}
		} catch (Exception e) {
			eb.setHttpCode(Status.BAD_REQUEST);
			eb.updateErrorCode("48700700");
			eb.updateMsg("momento ("+time+") no parseable, o algo");
			eb.updateMsg(e.getMessage());
		}
		
		if(fecha != null) {
			listaTurnos = TurnoTrabajadorDiaHandler.getTodosTurnosDia(null, codRes, fecha, eb);
		}
		
		if(listaTurnos == null) {
			respuesta = new RespuestaBean<TurnoTrabajadorDiaBean>(eb);
		} else {
			respuesta = new RespuestaBean<TurnoTrabajadorDiaBean>(listaTurnos);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
}
