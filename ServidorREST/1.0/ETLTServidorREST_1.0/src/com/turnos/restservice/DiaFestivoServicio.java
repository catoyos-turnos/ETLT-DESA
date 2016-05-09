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

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.FestivoHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.FestivoBean.TipoFiesta;
import com.turnos.datos.vo.RespuestaBean;

@Path(WebServUtils.PREF_FEST_PATH)
public class DiaFestivoServicio {

	// ---------------------GET-----------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaDiasFestivos(
			@QueryParam(WebServUtils.Q_PARAM_COD_PAIS)
			@DefaultValue("") String codPais,
			@QueryParam(WebServUtils.Q_PARAM_COD_PROV)
			@DefaultValue("") String codProvincia,
			@QueryParam(WebServUtils.Q_PARAM_COD_MUNI)
			@DefaultValue("") String codMunicipio,
			@QueryParam(WebServUtils.Q_PARAM_TIPO_FIESTA)
			String tipoStr,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE)
			@DefaultValue("-1") int limit,
			@QueryParam(WebServUtils.Q_PARAM_COMPLETO)
			@DefaultValue("false") boolean completo,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo) {
		
		ErrorBean eb = new ErrorBean();
		TipoFiesta tipo = TipoFiesta.safeValueOf(tipoStr);
		RespuestaBean<FestivoBean> respuesta = null;
		
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
			eb.updateErrorCode("48800000");
			eb.updateMsg("momentos ("+time_ini+","+time_fin+") no parseable, o algo");
			eb.updateMsg(e.getMessage());
		}

		ArrayList<FestivoBean> listaFestivos = null;
		if (!"".equals(codMunicipio)) {
			listaFestivos = FestivoHandler.getFestivosMunicipio(null, codMunicipio, tipo, fecha_ini, fecha_fin, limit, completo, incGeo, eb);
		} else if (!"".equals(codProvincia)) {
			listaFestivos = FestivoHandler.getFestivosProvincia(null, codProvincia, tipo, fecha_ini, fecha_fin, limit, completo, incGeo, eb);
		} else if (!"".equals(codPais)) {
			listaFestivos = FestivoHandler.getFestivosPais(null, codPais, tipo, fecha_ini, fecha_fin, limit, completo, incGeo, eb);
		} else {
			eb.setHttpCode(Status.BAD_REQUEST);
			eb.updateErrorCode("48800001");
			eb.updateMsg("debe incluir parametros de busqueda: " + WebServUtils.Q_PARAM_COD_PAIS + ", "
					+ WebServUtils.Q_PARAM_COD_PROV + ", o " + WebServUtils.Q_PARAM_COD_MUNI);
		
		}
		
		if(listaFestivos == null) {
			respuesta = new RespuestaBean<FestivoBean>(eb);
		} else {
			respuesta = new RespuestaBean<FestivoBean>(listaFestivos);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.COD_FEST_PATH)
	@Valid
	public static Response getDiaFestivo(@PathParam(WebServUtils.P_PARAM_COD_FEST) int codFest,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo) {
		ErrorBean eb = new ErrorBean();
		FestivoBean festivo = FestivoHandler.getFestivo(null, codFest, incGeo, eb);
		RespuestaBean<FestivoBean> respuesta;

		if(festivo == null) {
			respuesta = new RespuestaBean<FestivoBean>(eb);
		} else {
			respuesta = new RespuestaBean<FestivoBean>(festivo);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	// ---------------------POST----------------------------------------------

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoDiaFestivo(FestivoBean festRaw) {
		ErrorBean eb = new ErrorBean();
		boolean aut = FestivoHandler.autenticar(null);
		FestivoBean festivo = FestivoHandler.insertFestivo(null, festRaw, aut, eb);
		RespuestaBean<FestivoBean> respuesta;

		if(festivo == null) {
			respuesta = new RespuestaBean<FestivoBean>(eb);
		} else {
			respuesta = new RespuestaBean<FestivoBean>(festivo);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	// ---------------------PUT-----------------------------------------------

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.COD_FEST_PATH)
	@Valid
	public static Response modDiaFestivo(FestivoBean festRaw,
			@PathParam(WebServUtils.P_PARAM_COD_FEST) int codFest) {
		ErrorBean eb = new ErrorBean();
		boolean aut = FestivoHandler.autenticar(null);
		FestivoBean festivo = FestivoHandler.updateFestivo(null, codFest, festRaw, aut, eb);
		RespuestaBean<FestivoBean> respuesta;

		if(festivo == null) {
			respuesta = new RespuestaBean<FestivoBean>(eb);
		} else {
			respuesta = new RespuestaBean<FestivoBean>(festivo);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	// ---------------------DELETE--------------------------------------------

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.COD_FEST_PATH)
	@Valid
	public static Response borraDiaFestivo(@PathParam(WebServUtils.P_PARAM_COD_FEST) int codFest) {
		ErrorBean eb = new ErrorBean();
		boolean aut = FestivoHandler.autenticar(null);
		boolean borrado = FestivoHandler.deleteFestivo(null, codFest, aut, eb);
		RespuestaBean<FestivoBean> respuesta;

		if(borrado) {
			respuesta = new RespuestaBean<FestivoBean>();
			respuesta.setHtmlStatus(Status.ACCEPTED);
		} else {
			respuesta = new RespuestaBean<FestivoBean>(eb);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	// ---------------------misc.---------------------------------------------

	

	
	
	
}
