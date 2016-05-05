@Path(WebServUtils.PREF_USER_PATH)
public class UsuarioServicio {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaUsuarios () {
		//TODO Listar trabajadores de una residencia filtrar por xxx
		return Response.status(Status.NOT_IMPLEMENTED).entity(codRes).build();
	}
	
	@GET
	@Path(WebServUtils.COD_USER_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getUsuario(@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.getUsuario(null, codUser, errorBean);
		
		if(usuario == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(usuario).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoUsuario(UsuarioBean userRaw) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.insertUsuario(null, userRaw, errorBean);
		
		if(usuario == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.CREATED).entity(usuario).build();
		}
	}
	
	@PUT
	@Path(WebServUtils.COD_USER_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modUsuario(UsuarioBean userRaw,
			@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		UsuarioBean usuario = UsuarioHandler.updateUsuario(null, codUser, userRaw, errorBean);
		
		if(usuario == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.ACCEPTED).entity(usuario).build();
		}
	}
	
	@DELETE
	@Path(WebServUtils.COD_USER_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraUsuario(@PathParam(WebServUtils.P_PARAM_COD_USER) int codUser) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = UsuarioHandler.deleteUsuario(null, codUser, errorBean);
		
		if(borrado) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		}
	}

}
