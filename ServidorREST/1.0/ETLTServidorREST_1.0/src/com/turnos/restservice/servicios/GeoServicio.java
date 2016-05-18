package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.GeoHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.FestivoBean.TipoFiesta;
import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.PaisBean;
import com.turnos.datos.vo.ProvinciaBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Geografía")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_GEO_PATH)
public class GeoServicio extends GenericServicio{
	
	protected GeoServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected GeoServicio(@Context ContainerRequestContext request) {
		super(request);
	}

	// ---------------------GET-----------------------------------------------

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH)
	@Valid
	public Response listaPaises(
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean errorBean = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset);
		limite = limiteOffset[0]; offset = limiteOffset[1];
		ArrayList<PaisBean> listaPaises = GeoHandler.listPaises(null, limite, offset, errorBean);
		RespuestaBean<PaisBean> respuesta = null;

		if(listaPaises == null) {
			respuesta = new RespuestaBean<PaisBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<PaisBean>(listaPaises);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH)
	@Valid
	public Response getPais(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais) {
		ErrorBean errorBean = new ErrorBean();
		PaisBean pais = GeoHandler.getPais(null, codPais, errorBean);
		RespuestaBean<PaisBean> respuesta = null;

		if(pais == null) {
			respuesta = new RespuestaBean<PaisBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<PaisBean>(pais);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH)
	@Valid
	public Response listaProvincias(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean errorBean = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset);
		limite = limiteOffset[0]; offset = limiteOffset[1];
		ArrayList<ProvinciaBean> listaProvincias = GeoHandler.listProvincias(null, codPais, limite, offset, errorBean);
		RespuestaBean<ProvinciaBean> respuesta = null;

		if(listaProvincias == null) {
			respuesta = new RespuestaBean<ProvinciaBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ProvinciaBean>(listaProvincias);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH)
	@Valid
	public Response getProvincia(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia) {
		ErrorBean errorBean = new ErrorBean();
		ProvinciaBean provincia = GeoHandler.getProvincia(null, codProvincia, errorBean);
		RespuestaBean<ProvinciaBean> respuesta = null;

		if(provincia == null) {
			respuesta = new RespuestaBean<ProvinciaBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ProvinciaBean>(provincia);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH)
	@Valid
	public Response listaMunicipios(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		ErrorBean errorBean = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset);
		limite = limiteOffset[0]; offset = limiteOffset[1];
		ArrayList<MunicipioBean> listaMunicipios = GeoHandler.listMunicipios(null, codProvincia, limite, offset, errorBean);
		RespuestaBean<MunicipioBean> respuesta = null;

		if(listaMunicipios == null) {
			respuesta = new RespuestaBean<MunicipioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<MunicipioBean>(listaMunicipios);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH)
	@Valid
	public Response getMunicipio(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@PathParam(WebServUtils.P_PARAM_COD_MUNI) String codMunicipio) {
		ErrorBean errorBean = new ErrorBean();
		MunicipioBean municipio = GeoHandler.getMunicipio(null, codMunicipio, errorBean);
		RespuestaBean<MunicipioBean> respuesta = null;
		
		if(municipio == null) {
			respuesta = new RespuestaBean<MunicipioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<MunicipioBean>(municipio);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	// ---------------------misc.---------------------------------------------
	// -------------festivos--------------------------------------------------

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_FEST_PATH)
	@Valid
	public Response getFestivosPais(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE)
			@DefaultValue("-1") int limit,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		return (new DiaFestivoServicio(usuarioLog)).listaDiasFestivos(codPais, "", "", TipoFiesta.NACIONAL.name(), time_ini, time_fin, true, incGeo, limite, offset);
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_FEST_PATH)
	@Valid
	public Response getFestivosProvincia(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE)
			@DefaultValue("-1") int limit,
			@QueryParam(WebServUtils.Q_PARAM_COMPLETO)
			@DefaultValue("false") boolean completo,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		return (new DiaFestivoServicio(usuarioLog)).listaDiasFestivos(codPais, codProvincia, "", TipoFiesta.AUTONOMICA.name(), time_ini, time_fin, completo, incGeo, limite, offset);
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH
			+ WebServUtils.PREF_FEST_PATH)
	@Valid
	public Response getFestivosMunicipio(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@PathParam(WebServUtils.P_PARAM_COD_MUNI) String codMunicipio,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE)
			@DefaultValue("-1") int limit,
			@QueryParam(WebServUtils.Q_PARAM_COMPLETO)
			@DefaultValue("false") boolean completo,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		return (new DiaFestivoServicio(usuarioLog)).listaDiasFestivos(codPais, codProvincia, codMunicipio, TipoFiesta.LOCAL.name(), time_ini, time_fin, completo, incGeo, limite, offset);
	}
	
	// -------------residencias-----------------------------------------------

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_RES_PATH)
	@Valid
	public Response getResidenciasPais(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		return (new ResidenciaServicio(usuarioLog)).listaResidencias(codPais, null, null, incGeo, limite, offset);
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_RES_PATH)
	@Valid
	public Response getResidenciasProvincia(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		return (new ResidenciaServicio(usuarioLog)).listaResidencias(codPais, codProvincia, null, incGeo, limite, offset);
	}

	@GET
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH
			+ WebServUtils.PREF_RES_PATH)
	@Valid
	public Response getResidenciasMunicipio(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@PathParam(WebServUtils.P_PARAM_COD_MUNI) String codMunicipio,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {
		return (new ResidenciaServicio(usuarioLog)).listaResidencias(codPais, codProvincia, codMunicipio, incGeo, limite, offset);
	}

}
