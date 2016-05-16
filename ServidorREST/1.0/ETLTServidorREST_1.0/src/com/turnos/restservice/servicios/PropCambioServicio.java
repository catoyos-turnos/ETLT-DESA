package com.turnos.restservice.servicios;

@Api(value = "Propuesta de cambio")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_PROP_CAMBIO_PATH
public class PropCambioServicio {
	private UsuarioBean usuarioLog;
	
	@Context
	private PropCambioServicio(HttpServletRequest request) {
		Object usrObj = request==null?null:request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_USUARIO);
		if(usrObj != null && usrObj  instanceof UsuarioBean) {
			this.usuarioLog = (UsuarioBean) usrObj;
		}
	}

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

}
