package com.turnos.restservice;

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
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH)
	@Valid
	public static Response listaProvincias(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH)
	@Valid
	public static Response getProvincia(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + WebServUtils.PREF_MUNI_PATH)
	@Valid
	public static Response listaMunicipios(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH)
	@Valid
	public static Response getMunicipio(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@PathParam(WebServUtils.P_PARAM_COD_MUNI) String codMunicipio) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	// ---------------------POST----------------------------------------------

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH)
	@Valid
	public static Response nuevoPais(PaisBean paisRaw) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH)
	@Valid
	public static Response nuevaProvincia(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			ProvinciaBean provinciaRaw) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + WebServUtils.PREF_MUNI_PATH)
	@Valid
	public static Response nuevoMunicipio(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			MunicipioBean municipioRaw) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	// ---------------------PUT-----------------------------------------------

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH)
	@Valid
	public static Response modPais(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			PaisBean paisRaw) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH)
	@Valid
	public static Response modProvincia(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			ProvinciaBean provinciaRaw) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH)
	@Valid
	public static Response modMunicipio(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@PathParam(WebServUtils.P_PARAM_COD_MUNI) String codMunicipio,
			MunicipioBean municipioRaw) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	// ---------------------DELETE--------------------------------------------

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH)
	@Valid
	public static Response borraPais(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH)
	@Valid
	public static Response borraProvincia(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH)
	@Valid
	public static Response borraMunicipio(@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@PathParam(WebServUtils.P_PARAM_COD_MUNI) String codMunicipio) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	// ---------------------misc.---------------------------------------------
	// -------------festivos--------------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + "/festivos")
	@Valid
	public static Response getFestivosPais(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin,
			@QueryParam(WebServUtils.Q_PARAM_LIMITE)
			@DefaultValue("-1") int limit) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, "", "", TipoFiesta.NACIONAL, time_ini, time_fin, limit, true);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + "/festivos")
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
			@DefaultValue("false") boolean completo) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, codProvincia, "", TipoFiesta.AUTONOMICA, time_ini, time_fin, limit, completo);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH + "/festivos")
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
			@DefaultValue("false") boolean completo) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, codProvincia, codMunicipio, TipoFiesta.LOCAL, time_ini, time_fin, limit, completo);
	}
	
	// -------------residencias-----------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + "/res")
	@Valid
	public static Response getResidenciasProvincia(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia) {
		return ResidenciaServicio.listaResidencias(codProvincia, "");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(WebServUtils.PREF_PAIS_PATH + WebServUtils.COD_PAIS_PATH + WebServUtils.PREF_PROV_PATH + WebServUtils.COD_PROV_PATH + WebServUtils.PREF_MUNI_PATH + WebServUtils.COD_MUNI_PATH + "/res")
	@Valid
	public static Response getResidenciasMunicipio(
			@PathParam(WebServUtils.P_PARAM_COD_PAIS) String codPais,
			@PathParam(WebServUtils.P_PARAM_COD_PROV) String codProvincia,
			@PathParam(WebServUtils.P_PARAM_COD_MUNI) String codMunicipio) {
		return ResidenciaServicio.listaResidencias(codProvincia, codMunicipio);
	}

}
