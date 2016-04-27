package com.turnos.restservice;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.ResidenciaHandler;
import com.turnos.datos.vo.ResidenciaBean;

@Path("/test")
public class TestServicio {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response test (@QueryParam("ciudad") String ciudad, @QueryParam("nombre") String nombre) {
		ResidenciaBean resRaw = new ResidenciaBean();
		resRaw.setCiudad(ciudad);
		resRaw.setNombre(nombre);
		int i = 0;
		String codigo;
		do {
			codigo = ResidenciaHandler.generaNuevoResCodigo(resRaw, i++);
		} while (ResidenciaHandler.existeResidencia(null, codigo, null));
		
		return Response.status(Status.OK).entity(codigo).build();
	}
			
}
