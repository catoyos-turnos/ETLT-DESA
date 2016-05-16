package com.turnos.restservice.servicios;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.handlers.ServicioHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.ServicioBean;

@Api(value = "Turno")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH
		+ WebServUtils.PREF_TURNO_PATH + WebServUtils.COD_TURNO_PATH
		+ WebServUtils.PREF_SERV_PATH)
public class ServicioServicio {
	private UsuarioBean usuarioLog;
	
	@Context
	private ServicioServicio(HttpServletRequest request) {
		Object usrObj = request==null?null:request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_USUARIO);
		if(usrObj != null && usrObj  instanceof UsuarioBean) {
			this.usuarioLog = (UsuarioBean) usrObj;
		}
	}

	
	@GET
	@Valid
	public Response listaServicios (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<ServicioBean> listaServicios = ServicioHandler.listServicios(null, codRes, codTurno, errorBean);
		RespuestaBean<ServicioBean> respuesta = null;

		if (listaServicios == null) {
			respuesta = new RespuestaBean<ServicioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ServicioBean>(listaServicios);
		}

		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@GET
	@Path(WebServUtils.COD_SERV_PATH)
	@Valid
	public Response getServicio (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) int codServ) {
		ErrorBean errorBean = new ErrorBean();
		ServicioBean servicio = ServicioHandler.getServicio(null, codServ, errorBean);
		RespuestaBean<ServicioBean> respuesta = null;

		if(servicio == null) {
			respuesta = new RespuestaBean<ServicioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ServicioBean>(servicio);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoServicio(ServicioBean servicioRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		boolean aut = ServicioHandler.autenticar(null);
		ServicioBean servicio = ServicioHandler.insertServicio(null, servicioRaw, aut, errorBean);
		RespuestaBean<ServicioBean> respuesta = null;

		if(servicio == null) {
			respuesta = new RespuestaBean<ServicioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ServicioBean>(servicio);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@PUT
	@Path(WebServUtils.COD_SERV_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modServicio(ServicioBean servicioRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) int codServ) {
		ErrorBean errorBean = new ErrorBean();
		boolean aut = ServicioHandler.autenticar(null);
		ServicioBean servicio = ServicioHandler.updateServicio(null, codServ, servicioRaw, aut, errorBean);
		RespuestaBean<ServicioBean> respuesta = null;
		
		if(servicio == null) {
			respuesta = new RespuestaBean<ServicioBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<ServicioBean>(servicio);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@DELETE
	@Path(WebServUtils.COD_SERV_PATH)
	@Valid
	public Response borraServicio(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) int codServ) {
		ErrorBean errorBean = new ErrorBean();
		boolean aut = ServicioHandler.autenticar(null);
		boolean borrado = ServicioHandler.deleteServicio(null, codServ, aut, errorBean);
		RespuestaBean<ServicioBean> respuesta = null;

		if(borrado) {
			respuesta = new RespuestaBean<ServicioBean>();
			respuesta.setHtmlStatus(Status.ACCEPTED);
		} else {
			respuesta = new RespuestaBean<ServicioBean>(errorBean);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

}
