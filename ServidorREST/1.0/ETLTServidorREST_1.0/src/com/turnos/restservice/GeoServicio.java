package com.turnos.restservice;

import javax.validation.Valid;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.FestivoBean.TipoFiesta;

@Path(WebServUtils.PREF_GEO_PATH)
public class GeoServicio {

	// ---------------------GET-----------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH)
	@Valid
	public static Response listaPaises() {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH)
	@Valid
	public static Response getPais(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH)
	@Valid
	public static Response listaProvincias(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH)
	@Valid
	public static Response getProvincia(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_MUNI_PATH)
	@Valid
	public static Response listaMunicipios(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
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
		return Response.status(Status.NOT_IMPLEMENTED).build();
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
			+ WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH
			+ WebServUtils.PREF_RES_PATH)
	@Valid
	public static Response getResidenciasProvincia(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@QueryParam(WebServUtils.Q_PARAM_INC_GEO)
			@DefaultValue("false") boolean incGeo) {
		return ResidenciaServicio.listaResidencias(codProvincia, "", incGeo);
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
		return ResidenciaServicio.listaResidencias(codProvincia, codMunicipio, incGeo);
	}

}
