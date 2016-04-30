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

@Path("/festivos")
public class DiaFestivoServicio {
	public static final String COD_FESTIVO_PATH = "/{codFest: [0-9]{1,11}}";

	// ---------------------GET-----------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static String listaDiasFestivos(String codPais, String codProvincia,
			String codMunicipio, TipoFiesta tipo, int time_ini, int time_fin,
			int limit, boolean completo) {
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(COD_FESTIVO_PATH)
	@Valid
	public static String getDiaFestivo(String codFest) {
		return null;
	}

	// ---------------------POST----------------------------------------------

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public static String nuevoDiaFestivo(String[] festRaw) {
		return null;
	}

	// ---------------------PUT-----------------------------------------------

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(COD_FESTIVO_PATH)
	@Valid
	public static String modDiaFestivo(String codFest, String[] festRaw) {
		return null;
	}

	// ---------------------DELETE--------------------------------------------

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(COD_FESTIVO_PATH)
	@Valid
	public static String borraDiaFestivo(String codFest) {
		return null;
	}

	// ---------------------misc.---------------------------------------------

}
