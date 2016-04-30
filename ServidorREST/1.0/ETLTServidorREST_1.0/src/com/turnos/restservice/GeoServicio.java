package com.turnos.restservice;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.turnos.datos.vo.FestivoBean.TipoFiesta;

@Path("/geo")
public class GeoServicio {
	public static final String COD_PAIS_PATH = "/{codPais: [A-Z0-9_]{2}}";
	public static final String COD_PROV_PATH = "/{codProvincia: [A-Z0-9_]{4}}";
	public static final String COD_MUNI_PATH = "/{codMunicipio: [A-Z0-9_]{8}}";

	// ---------------------GET-----------------------------------------------
	
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais")
	 @Valid
	public static String listaPaises() {
		return null;
	}
	
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH)
	 @Valid
	public static String getPais(String codPais) {
		return null;
	}

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov")
	 @Valid
	public static String listaProvincias(String codPais) {
		return null;
	}

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH)
	 @Valid
	public static String getProvincia(String codPais, String codProvincia) {
		return null;
	}

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/muni")
	 @Valid
	public static String listaMunicipios(String codPais, String codProvincia) {
		return null;
	}

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/muni" + COD_MUNI_PATH)
	 @Valid
	public static String getMunicipio(String codPais, String codProvincia, String codMunicipio) {
		return null;
	}
	
//---------------------POST----------------------------------------------

	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/pais")
	 @Valid
	public static String nuevoPais(String[] paisRaw) {
		return null;
	}

	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov")
	 @Valid
	public static String nuevaProvincia(String codPais, String[] provinciaRaw) {
		return null;
	}

	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/muni")
	 @Valid
	public static String nuevoMunicipio(String codPais, String codProvincia, String[] municipioRaw) {
		return null;
	}
	
	// ---------------------PUT-----------------------------------------------
	
	 @PUT
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH)
	 @Valid
	public static String modPais(String codPais, String[] paisRaw) {
		return null;
	}

	 @PUT
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH)
	 @Valid
	public static String modProvincia(String codPais, String codProvincia, String[] provinciaRaw) {
		return null;
	}

	 @PUT
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/muni" + COD_MUNI_PATH)
	 @Valid
	public static String modMunicipio(String codPais, String codProvincia, String codMunicipio, String[] municipioRaw) {
		return null;
	}

	// ---------------------DELETE--------------------------------------------
	
	 @DELETE
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH)
	 @Valid
	public static String borraPais(String codPais) {
		return null;
	}

	 @DELETE
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH)
	 @Valid
	public static String borraProvincia(String codPais, String codProvincia) {
		return null;
	}

	 @DELETE
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/muni" + COD_MUNI_PATH)
	 @Valid
	public static String borraMunicipio(String codPais, String codProvincia, String codMunicipio) {
		return null;
	}

	// ---------------------misc.---------------------------------------------
	// -------------festivos--------------------------------------------------

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/festivos")
	 @Valid
	public static String getFestivosPais(String codPais, int time_ini, int time_fin, int limit) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, "", "", TipoFiesta.NACIONAL, time_ini, time_fin, limit, true);
	}

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/festivos")
	 @Valid
	public static String getFestivosProvincia(String codPais, String codProvincia, int time_ini, int time_fin, int limit, boolean completo) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, codProvincia, "", TipoFiesta.AUTONOMICA, time_ini, time_fin, limit, completo);
	}

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/muni" + COD_MUNI_PATH + "/festivos")
	 @Valid
	public static String getFestivosMunicipio(String codPais, String codProvincia, String codMunicipio, int time_ini, int time_fin, int limit, boolean completo) {
		return DiaFestivoServicio.listaDiasFestivos(codPais, codProvincia, codMunicipio, TipoFiesta.LOCAL, time_ini, time_fin, limit, completo);
	}
	
	// -------------residencias-----------------------------------------------

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/residencias")
	 @Valid
	public static String getResidenciasProvincia(String codPais, String codProvincia) {
		// return ResidenciaServicio.listaResidencia("", codProvincia);
		return null;
	}

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/pais" + COD_PAIS_PATH + "/prov" + COD_PROV_PATH + "/muni" + COD_MUNI_PATH + "/residencias")
	 @Valid
	public static String getResidenciasMunicipio(String codPais, String codProvincia, String codMunicipio) {
		// return ResidenciaServicio.listaResidencia(codMunicipio, codProvincia);
		return null;
	}

}
