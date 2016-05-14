package com.turnos.restservice.servicios;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

@Path(WebServUtils.PREF_GEO_PATH)
public class GeoServicio {

	// ---------------------GET-----------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH)
	@Valid
	public static Response listaPaises() {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<PaisBean> listaPaises = GeoHandler.listPaises(null, errorBean);
		RespuestaBean<PaisBean> respuesta = null;

		if(listaPaises == null) {
			respuesta = new RespuestaBean<PaisBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<PaisBean>(listaPaises);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH)
	@Valid
	public static Response getPais(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais) {
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
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH)
	@Valid
	public static Response listaProvincias(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<ProvinciaBean> listaProvincias = GeoHandler.listProvincias(null, codPais, errorBean);
		RespuestaBean<ProvinciaBean> respuesta = null;

		if(listaProvincias == null) {
			respuesta = new RespuestaBean<ProvinciaBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ProvinciaBean>(listaProvincias);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH)
	@Valid
	public static Response getProvincia(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
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
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH)
	@Valid
	public static Response listaMunicipios(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<MunicipioBean> listaMunicipios = GeoHandler.listMunicipios(null, codProvincia, errorBean);
		RespuestaBean<MunicipioBean> respuesta = null;

		if(listaMunicipios == null) {
			respuesta = new RespuestaBean<MunicipioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<MunicipioBean>(listaMunicipios);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH)
	@Valid
	public static Response getMunicipio(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
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
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_FEST_PATH)
	@Valid
	public static Response getFestivosPais(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE)
			@DefaultValue("-1") int limit,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, "", "", TipoFiesta.NACIONAL.name(), time_ini, time_fin, limit, true, incGeo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_FEST_PATH)
	@Valid
	public static Response getFestivosProvincia(
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
			@DefaultValue("false") boolean incGeo) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, codProvincia, "", TipoFiesta.AUTONOMICA.name(), time_ini, time_fin, limit, completo, incGeo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH
			+ WebServUtils.PREF_FEST_PATH)
	@Valid
	public static Response getFestivosMunicipio(
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
			@DefaultValue("false") boolean incGeo) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, codProvincia, codMunicipio, TipoFiesta.LOCAL.name(), time_ini, time_fin, limit, completo, incGeo);
	}
	
	// -------------residencias-----------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_RES_PATH)
	@Valid
	public static Response getResidenciasPais(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo) {
		return ResidenciaServicio.listaResidencias(codPais, null, null, incGeo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_RES_PATH)
	@Valid
	public static Response getResidenciasProvincia(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo) {
		return ResidenciaServicio.listaResidencias(codPais, codProvincia, null, incGeo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH
			+ WebServUtils.PREF_RES_PATH)
	@Valid
	public static Response getResidenciasMunicipio(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@PathParam(WebServUtils.P_PARAM_COD_MUNI) String codMunicipio,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo) {
		return ResidenciaServicio.listaResidencias(codPais, codProvincia, codMunicipio, incGeo);
	}

}
