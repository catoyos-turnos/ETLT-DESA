package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Propuesta de cambio")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_PROP_CAMBIO_PATH)
public class PropCambioServicio extends GenericServicio{
	
	protected PropCambioServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected PropCambioServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------GET-----------------------------------------------
/*
	@GET
	@Valid
	public Response listaPropCambios();
	
	@GET
	@Path(WebServUtils.COD_CAMBIO_PATH)
	@Valid
	public Response getPropCambio(@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoPropCambio(PropuestaCambioBean rawCambio);
	
	@PUT
	@Path(WebServUtils.COD_CAMBIO_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modPropCambio(PropuestaCambioBean rawCambio,
			@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio);
	
	@DELETE
	@Path(WebServUtils.COD_CAMBIO_PATH)
	@Valid
	public Response borraPropCambio(@PathParam(WebServUtils.P_PARAM_COD_PROP_CAMBIO) String codCambio);
*/
}
