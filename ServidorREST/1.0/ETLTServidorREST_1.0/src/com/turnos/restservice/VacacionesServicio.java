package com.turnos.restservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

import com.turnos.datos.handlers.VacacionesHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.VacacionesBean;

//PathParams: codRes, codTrab
@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH
		+ WebServUtils.PREF_TRAB_PATH + WebServUtils.COD_TRAB_PATH
		+ WebServUtils.PREF_VACS_PATH)
public class VacacionesServicio {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response listaVacaciones (
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin) {
		ErrorBean errorBean = new ErrorBean();
		
		Date fecha_ini = null;
		Date fecha_fin = null;
		try {
			if (time_ini > 0) {
				fecha_ini = new Date(time_ini * 1000l);
			}
			if (time_fin > 0) {
				fecha_fin = new Date(time_fin * 1000l);
			}
			if(fecha_ini == null && fecha_fin == null) {
				fecha_ini = Calendar.getInstance().getTime();
			}
			
		} catch (Exception e) {
			//TODO error
			e.printStackTrace();
		}
		
		ArrayList<VacacionesBean> listaVacaciones;
		listaVacaciones = VacacionesHandler.listVacaciones(null, codRes, codTrab, fecha_ini, fecha_fin, errorBean);
		
		if(listaVacaciones == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(listaVacaciones).build();
		}
	}
	
	@GET
	@Path(WebServUtils.COD_VACS_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response getVacaciones (
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@PathParam(WebServUtils.P_PARAM_COD_VACS) String codVacs) {
		ErrorBean errorBean = new ErrorBean();
		VacacionesBean vacaciones = VacacionesHandler.getVacaciones(null, codVacs, errorBean);
		if(vacaciones == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.OK).entity(vacaciones).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response nuevoVacaciones(VacacionesBean vacsRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		VacacionesBean vacaciones = VacacionesHandler.insertVacaciones(null, codRes, codTrab, vacsRaw, errorBean);
		
		if(vacaciones == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.CREATED).entity(vacaciones).build();
		}
	}
	
	@PUT
	@Path(WebServUtils.COD_VACS_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response modVacaciones(VacacionesBean vacsRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@PathParam(WebServUtils.P_PARAM_COD_VACS) String codVacs) {
		ErrorBean errorBean = new ErrorBean();
		VacacionesBean vacaciones = VacacionesHandler.updateVacaciones(null, codVacs, vacsRaw, errorBean);
		
		if(vacaciones == null) {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		} else {
			return Response.status(Status.ACCEPTED).entity(vacaciones).build();
		}
	}
	
	@DELETE
	@Path(WebServUtils.COD_VACS_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public static Response borraVacaciones(
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@PathParam(WebServUtils.P_PARAM_COD_VACS) String codVacs) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = VacacionesHandler.deleteVacaciones(null, codVacs, errorBean);
		
		if(borrado) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(errorBean.getHttpCode()).entity(errorBean).build();
		}
	}
}
