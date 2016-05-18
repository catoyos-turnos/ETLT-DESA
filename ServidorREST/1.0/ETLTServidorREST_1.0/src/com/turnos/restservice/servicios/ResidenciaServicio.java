package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.handlers.FestivoHandler;
import com.turnos.datos.handlers.ResidenciaHandler;
import com.turnos.datos.handlers.ResidenciaHandler.TipoBusqueda;
import com.turnos.datos.handlers.TurnoTrabajadorDiaHandler;
import com.turnos.datos.handlers.VacacionesHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.datos.vo.VacacionesBean;

@Api(value = "Residencia")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_RES_PATH)
public class ResidenciaServicio extends GenericServicio{
	
	protected ResidenciaServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected ResidenciaServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------GET-----------------------------------------------

	@GET
	@ApiOperation(value = "Lista residencias en un pais, provincia, o municipio dados",
	    response = ResidenciaBean.class,
	    responseContainer = "List")
	@Valid
	public Response listaResidencias (
			@ApiParam(value = "pais a buscar")
			@QueryParam(WebServUtils.Q_PARAM_COD_PAIS) @DefaultValue("") String pais,
			
			@ApiParam(value = "provincia a buscar")
			@QueryParam(WebServUtils.Q_PARAM_COD_PROV) @DefaultValue("") String provincia,
			
			@ApiParam(value = "municipio a buscar")
			@QueryParam(WebServUtils.Q_PARAM_COD_MUNI) @DefaultValue("") String municipio,
			
			@ApiParam(value = "incluir informacion geografica")
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO) @DefaultValue("false") boolean includeGeo,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {		
		ErrorBean eb = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset);
		limite = limiteOffset[0]; offset = limiteOffset[1];
		
		ArrayList<ResidenciaBean> listaResidencias = null;
		if (!"".equals(municipio)) {
			String[] busqueda = { municipio };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.MUNICIPIO, busqueda, includeGeo, limite, offset, usuarioLog, eb);
		} else if (!"".equals(provincia)) {
			String[] busqueda = { provincia };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.PROVINCIA, busqueda, includeGeo, limite, offset, usuarioLog, eb);
		} else if (!"".equals(pais)) {
			String[] busqueda = { pais };
			listaResidencias = ResidenciaHandler
					.listResidencias(null, TipoBusqueda.PAIS, busqueda, includeGeo, limite, offset, usuarioLog, eb);
		} else {
			int[] loc = {70,0,0};
			String msg = "debe incluir parametros de busqueda: "
					+ WebServUtils.Q_PARAM_COD_PAIS + ", "
					+ WebServUtils.Q_PARAM_COD_PROV + ", o "
					+ WebServUtils.Q_PARAM_COD_MUNI;
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s45", loc, msg, null);
		}

		return creaRespuestaGenericaGETLista(listaResidencias, eb, limite, offset);
	}
	
	@GET
	@ApiOperation(value = "Devuelve residencia por codigo",
	    response = ResidenciaBean.class)
	@Path(WebServUtils.COD_RES_PATH)
	@Valid
	public Response getResidencia(
			@ApiParam(value = "codigo de la residencia")
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			
			@ApiParam(value = "incluir informacion geografica")
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO) @DefaultValue("true") boolean includeGeo) {
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.getResidencia(null, codRes, includeGeo, usuarioLog, eb);

		return creaRespuestaGenericaGET(residencia, eb);
	}
	
	// ---------------------POST----------------------------------------------

	@POST
	@ApiOperation(value = "Inserta nueva residencia",
		response = ResidenciaBean.class)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevaResidencia(ResidenciaBean resRaw) {
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.insertResidencia(null, resRaw, usuarioLog, eb);

		return creaRespuestaGenericaPOST(residencia, eb);
	}
	
	// ---------------------PUT-----------------------------------------------

	@PUT
	@ApiOperation(value = "Modifica residencia existente",
		notes = "Campos modificables: nombre, descripcion, ciudad, municipioCod",
		response = ResidenciaBean.class)
	@Path(WebServUtils.COD_RES_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modResidencia(ResidenciaBean resRaw,
			@ApiParam(value = "codigo de la residencia")
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean eb = new ErrorBean();
		ResidenciaBean residencia = ResidenciaHandler.updateResidencia(null, codRes, resRaw, usuarioLog, eb);

		return creaRespuestaGenericaPUT(residencia, eb);
	}
	
	// ---------------------DELETE--------------------------------------------

	@DELETE
	@ApiOperation(value = "Elimina residencia existente",
		response = ResidenciaBean.class)
	@Path(WebServUtils.COD_RES_PATH)
	@Valid
	public Response borraResidencia(
			@ApiParam(value = "codigo de la residencia")
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean eb = new ErrorBean();
		boolean borrado = ResidenciaHandler.deleteResidencia(null, codRes, usuarioLog, eb);
		
		return creaRespuestaGenericaDELETE(borrado, ResidenciaBean.class, eb);
	}
	
	// ---------------------misc.---------------------------------------------

	@GET
	@ApiOperation(value = "Lista dias festivos en el municipio de la residencia dada",
		response = FestivoBean.class,
		responseContainer = "List")
	@Path(WebServUtils.COD_RES_PATH + WebServUtils.PREF_FEST_PATH)
	@Valid
	public Response getDiasFestivos(
			@ApiParam(value = "codigo de la residencia")
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,

			@ApiParam(value = "fecha de inicio (timestamp en ms.)", defaultValue="fecha actual")
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") long time_ini,
			
			@ApiParam(value = "fecha de fin (timestamp en ms.)")
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") long time_fin,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean eb = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset, 20);
		limite = limiteOffset[0]; offset = limiteOffset[1];

		ArrayList<FestivoBean> listaFestivos = null;
		Date fecha_ini = null;
		Date fecha_fin = null;
		try {
			if (time_ini > 0) {
				fecha_ini = new Date(time_ini);
			} else {
				fecha_ini = Calendar.getInstance().getTime();
			}
			
			if (time_fin > 0) {
				fecha_fin = new Date(time_fin);
			}
			
		} catch (Exception e) {
			int[] loc = {70,5,0};
			String msg = "momentos (%s, %s) no parseables, o algo [["+e.getMessage()+"]]";
			String[] params = {String.valueOf(time_ini), String.valueOf(time_fin)};
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s48", loc, msg, params);
		}
		listaFestivos = FestivoHandler.getFestivosResidencia(null, codRes, fecha_ini, fecha_fin, limite, offset, usuarioLog, eb);
		
		return creaRespuestaGenericaGETLista(listaFestivos, eb, limite, offset);
	}
	
	@GET
	@ApiOperation(value = "Lista trabajadores de residencia dada en vacaciones en cierta fecha",
		response = FestivoBean.class,
		responseContainer = "List")
	@Path(WebServUtils.COD_RES_PATH + WebServUtils.PREF_VACS_PATH)
	@Valid
	public Response getVacacionesDia(
			@ApiParam(value = "codigo de la residencia")
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,

			@ApiParam(value = "fecha a buscar (timestamp en ms.)", defaultValue="fecha actual")
			@QueryParam(WebServUtils.Q_PARAM_FECHA)
			@DefaultValue("-1") int time,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean eb = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset, 1000);
		limite = limiteOffset[0]; offset = limiteOffset[1];
		
		ArrayList<VacacionesBean> listaVacaciones = null;
		Date fecha = null;
		try {
			if (time > 0) {
				fecha = new Date(time * 1000l);
			} else {
				fecha = Calendar.getInstance().getTime();
			}
		} catch (Exception e) {
			int[] loc = {70,6,0};
			String msg = "momento (%s) no parseable, o algo [["+e.getMessage()+"]]";
			String[] params = {String.valueOf(time)};
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s48", loc, msg, params);
		}
		
		if(fecha != null) {
			listaVacaciones = VacacionesHandler.listVacacionesResDia(null, codRes, fecha, limite, offset, usuarioLog, eb);
		}
		
		return creaRespuestaGenericaGETLista(listaVacaciones, eb, limite, offset);
	}
	
	@GET
	@ApiOperation(value = "Lista turnos de trabajadores en residencia en cierta fecha",
		response = TurnoTrabajadorDiaBean.class,
		responseContainer = "List")
	@Path(WebServUtils.COD_RES_PATH + WebServUtils.PREF_HORARIO_PATH)
	@Valid
	public Response getHorarioCompletoDia(
			@ApiParam(value = "codigo de la residencia")
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,

			@ApiParam(value = "fecha a buscar (timestamp en ms.)", defaultValue="fecha actual")
			@QueryParam(WebServUtils.Q_PARAM_FECHA)
			@DefaultValue("-1") long time,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean eb = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset, 1000);
		limite = limiteOffset[0]; offset = limiteOffset[1];

		ArrayList<TurnoTrabajadorDiaBean> listaTurnos = null;
		Date fecha = null;
		try {
			if (time > 0) {
				fecha = new Date(time);
			} else {
				fecha = Calendar.getInstance().getTime();
			}
		} catch (Exception e) {
			int[] loc = {70,7,0};
			String msg = "momento (%s) no parseable, o algo [["+e.getMessage()+"]]";
			String[] params = {String.valueOf(time)};
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s48", loc, msg, params);
		}
		
		if(fecha != null) {
			listaTurnos = TurnoTrabajadorDiaHandler.getTodosTurnosDia(null, codRes, fecha, limite, offset, usuarioLog, eb);
		}
		
		return creaRespuestaGenericaGETLista(listaTurnos, eb, limite, offset);
	}
}
