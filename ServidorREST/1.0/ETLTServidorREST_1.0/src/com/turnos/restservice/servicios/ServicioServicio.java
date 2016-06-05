package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

import java.util.ArrayList;

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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.ServicioHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Turno")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH
		+ WebServUtils.PREF_TURNO_PATH + WebServUtils.COD_TURNO_PATH
		+ WebServUtils.PREF_SERV_PATH)
public class ServicioServicio extends GenericServicio{
	
	protected ServicioServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected ServicioServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------GET-----------------------------------------------

	@GET
	@Valid
	public Response listaServicios (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {		
		ErrorBean errorBean = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset);
		limite = limiteOffset[0]; offset = limiteOffset[1];
		ArrayList<ServicioBean> listaServicios = ServicioHandler.listServicios(null, codRes, codTurno, limite, offset, errorBean);
		
		return creaRespuestaGenericaGETLista(listaServicios, errorBean, limite, offset);
	}
	
	@GET
	@Path(WebServUtils.COD_SERV_PATH)
	@Valid
	public Response getServicio (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) long codServ) {
		ErrorBean errorBean = new ErrorBean();
		ServicioBean servicio = ServicioHandler.getServicio(null, codServ, errorBean);

		return creaRespuestaGenericaGET(servicio, errorBean);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoServicio(ServicioBean servicioRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		ServicioBean servicio = ServicioHandler.insertServicio(null, codRes, servicioRaw, errorBean);

		return creaRespuestaGenericaPOST(servicio, errorBean);
	}
	
	@PUT
	@Path(WebServUtils.COD_SERV_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modServicio(ServicioBean servicioRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) long codServ) {
		ErrorBean errorBean = new ErrorBean();
		ServicioBean servicio = ServicioHandler.updateServicio(null, codRes, codServ, servicioRaw, errorBean);

		return creaRespuestaGenericaPUT(servicio, errorBean);
	}
	
	@DELETE
	@Path(WebServUtils.COD_SERV_PATH)
	@Valid
	public Response borraServicio(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) long codServ) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = ServicioHandler.deleteServicio(null, codRes, codServ, errorBean);

		return creaRespuestaGenericaDELETE(borrado, ServicioBean.class, errorBean);
	}

}
