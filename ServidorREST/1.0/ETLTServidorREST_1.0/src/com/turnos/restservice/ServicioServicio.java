package com.turnos.restservice;

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

import com.turnos.datos.handlers.ServicioHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.ServicioBean;

@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH
		+ WebServUtils.PREF_TURNO_PATH + WebServUtils.COD_TURNO_PATH
		+ WebServUtils.PREF_SERV_PATH)
public class ServicioServicio {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaServicios (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<ServicioBean> listaServicios = ServicioHandler.listServicios(null, codRes, codTurno, errorBean);
		
		if(listaServicios == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(listaServicios).build();
		}
	}
	
	@GET
	@Path(WebServUtils.COD_SERV_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getServicio (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) int codServ) {
		ErrorBean errorBean = new ErrorBean();
		ServicioBean servicio = ServicioHandler.getServicio(null, codServ, errorBean);
		
		if(servicio == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(servicio).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoServicio(ServicioBean servicioRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		ServicioBean servicio = ServicioHandler.insertServicio(null, servicioRaw, errorBean);
		
		if(servicio == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.CREATED).entity(servicio).build();
		}
	}
	
	@PUT
	@Path(WebServUtils.COD_SERV_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modServicio(ServicioBean servicioRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) int codServ) {
		ErrorBean errorBean = new ErrorBean();
		ServicioBean servicio = ServicioHandler.updateServicio(null, codServ, servicioRaw, errorBean);
		
		if(servicio == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.ACCEPTED).entity(servicio).build();
		}
	}
	
	@DELETE
	@Path(WebServUtils.COD_SERV_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraServicio(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@PathParam(WebServUtils.P_PARAM_COD_SERV) int codServ) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = ServicioHandler.deleteServicio(null, codServ, errorBean);
		
		if(borrado) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		}
	}

}
