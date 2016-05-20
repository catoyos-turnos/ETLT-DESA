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
import javax.ws.rs.HttpMethod;
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
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.FestivoBean.TipoFiesta;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Día Festivo")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_FEST_PATH)
public class DiaFestivoServicio extends GenericServicio{
	
	protected DiaFestivoServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected DiaFestivoServicio(@Context ContainerRequestContext request) {
		super(request);
	}

	// ---------------------GET-----------------------------------------------

	@GET
	@ApiOperation(value = "Lista dias festivos segun parametros",
	    response = FestivoBean.class,
	    responseContainer = "List")
	@Valid
	public Response listaDiasFestivos(
			@ApiParam(value = "", required = false) 
			@QueryParam(WebServUtils.Q_PARAM_COD_PAIS) @DefaultValue("") String codPais,
			
			@ApiParam(value = "", required = false) 
			@QueryParam(WebServUtils.Q_PARAM_COD_PROV) @DefaultValue("") String codProvincia,
			
			@ApiParam(value = "", required = false) 
			@QueryParam(WebServUtils.Q_PARAM_COD_MUNI) @DefaultValue("") String codMunicipio,
			
			@ApiParam(value = "", required = false) 
			@QueryParam(WebServUtils.Q_PARAM_TIPO_FIESTA) String tipoStr,
			
			@ApiParam(value = "", required = false) 
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI) @DefaultValue("-1") int time_ini,
			
			@ApiParam(value = "", required = false) 
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN) @DefaultValue("-1") int time_fin,
			
			@ApiParam(value = "", required = false) 
			@QueryParam(WebServUtils.Q_PARAM_COMPLETO) @DefaultValue("false") boolean completo,
			
			@ApiParam(value = "", required = false) 
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO) @DefaultValue("false") boolean incGeo,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean eb = new ErrorBean();
		TipoFiesta tipo = TipoFiesta.safeValueOf(tipoStr);
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset, 20);
		limite = limiteOffset[0]; offset = limiteOffset[1];
		
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
			int[] loc = {80,0,0};
			String msg = "momentos (%s, %s) no parseables, o algo [["+e.getMessage()+"]]";
			String[] params = {String.valueOf(time_ini), String.valueOf(time_fin)};
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s48", loc, msg, params);
		}

		ArrayList<FestivoBean> listaFestivos = null;
		if (!"".equals(codMunicipio)) {
			listaFestivos = FestivoHandler.getFestivosMunicipio(null, codMunicipio, tipo, fecha_ini, fecha_fin, completo, incGeo, limite, offset,  eb);
		} else if (!"".equals(codProvincia)) {
			listaFestivos = FestivoHandler.getFestivosProvincia(null, codProvincia, tipo, fecha_ini, fecha_fin, completo, incGeo, limite, offset,  eb);
		} else if (!"".equals(codPais)) {
			listaFestivos = FestivoHandler.getFestivosPais(null, codPais, tipo, fecha_ini, fecha_fin, completo, incGeo, limite, offset,  eb);
		} else {
			int[] loc = {80,0,1};
			String msg = "debe incluir parametros de busqueda: "
					 + WebServUtils.Q_PARAM_COD_PAIS + ", " + WebServUtils.Q_PARAM_COD_PROV + ", o " + WebServUtils.Q_PARAM_COD_MUNI;
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s45", loc, msg, null);
		}

		return creaRespuestaGenericaGETLista(listaFestivos, eb, limite, offset);
	}

	@GET
	@Path(WebServUtils.COD_FEST_PATH)
	@ApiOperation(value = "Devuelve día festivo por ID",
    	response = FestivoBean.class)
	@Valid
	public Response getDiaFestivo(@PathParam(WebServUtils.P_PARAM_COD_FEST) int codFest,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo) {
		ErrorBean eb = new ErrorBean();
		FestivoBean festivo = FestivoHandler.getFestivo(null, codFest, incGeo,  eb);

		return creaRespuestaGenericaGET(festivo, eb);
	}

	// ---------------------POST----------------------------------------------

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Inserta nuevo día festivo",
		response = FestivoBean.class )
	@Valid
	public Response nuevoDiaFestivo(FestivoBean festRaw) {
		ErrorBean eb = new ErrorBean();
		FestivoBean festivo = null;
		boolean auth = FestivoHandler.autenticar(usuarioLog, HttpMethod.POST);
		if(!auth) {
			int[] loc = {80,3,0};
			ErrorBeanFabrica.generaErrorBean(eb, Status.FORBIDDEN, "h57", loc, "Sin autenticar");
		} else {
			festivo = FestivoHandler.insertFestivo(null, festRaw, eb);
		}

		return creaRespuestaGenericaPOST(festivo, eb);
	}

	// ---------------------PUT-----------------------------------------------

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.COD_FEST_PATH)
	@ApiOperation(value = "Modifica día festivo existente",
		notes = "Campos modificables: fiesta, notas, tipo, municipioCod, provinciaCod, paisCod",
		response = FestivoBean.class)
	@Valid
	public Response modDiaFestivo(FestivoBean festRaw,
			@PathParam(WebServUtils.P_PARAM_COD_FEST) int codFest) {
		ErrorBean eb = new ErrorBean();
		FestivoBean festivo = null;
		boolean auth = FestivoHandler.autenticar(usuarioLog, HttpMethod.PUT);
		if(!auth) {
			int[] loc = {80,4,0};
			ErrorBeanFabrica.generaErrorBean(eb, Status.FORBIDDEN, "h57", loc, "Sin autenticar");
		} else {
			festivo = FestivoHandler.updateFestivo(null, codFest, festRaw, eb);
		}

		return creaRespuestaGenericaPUT(festivo, eb);
	}

	// ---------------------DELETE--------------------------------------------

	@DELETE
	@Path(WebServUtils.COD_FEST_PATH)
	@ApiOperation(value = "Elimina día festivo existente")
	@Valid
	public Response borraDiaFestivo(@PathParam(WebServUtils.P_PARAM_COD_FEST) int codFest) {
		ErrorBean eb = new ErrorBean();
		boolean borrado = false;
		boolean auth = FestivoHandler.autenticar(usuarioLog, HttpMethod.POST);
		if(!auth) {
			int[] loc = {80,3,0};
			ErrorBeanFabrica.generaErrorBean(eb, Status.FORBIDDEN, "h57", loc, "Sin autenticar");
		} else {
			borrado = FestivoHandler.deleteFestivo(null, codFest, eb);
		}

		return creaRespuestaGenericaDELETE(borrado, FestivoBean.class, eb);
	}

	// ---------------------misc.---------------------------------------------

	

	
	
	
}
