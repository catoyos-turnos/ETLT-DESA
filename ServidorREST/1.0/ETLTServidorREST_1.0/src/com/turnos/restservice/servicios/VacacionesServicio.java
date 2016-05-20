package com.turnos.restservice.servicios;

import io.swagger.annotations.Api;

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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.WebServUtils;
import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.handlers.VacacionesHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.datos.vo.VacacionesBean;

@Api(value = "Trabajador")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH
		+ WebServUtils.PREF_TRAB_PATH + WebServUtils.COD_TRAB_PATH
		+ WebServUtils.PREF_VACS_PATH)
public class VacacionesServicio extends GenericServicio{
	
	protected VacacionesServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected VacacionesServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------GET-----------------------------------------------

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public Response listaVacaciones (
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
			int[] loc = {76,0,0};
			String msg = "momentos (%s, %s) no parseable, o algo [["+e.getMessage()+"]]";
			String[] params = {String.valueOf(time_ini), String.valueOf(time_fin)};
			ErrorBeanFabrica.generaErrorBean(errorBean, Status.BAD_REQUEST, "s48", loc, msg, params);
		}
		ArrayList<VacacionesBean> listaVacaciones = VacacionesHandler.listVacaciones(null, codRes, codTrab, fecha_ini, fecha_fin,  errorBean);


		return creaRespuestaGenericaGETLista(listaVacaciones, errorBean, -1, -1);
	}
	
	@GET
	@Path(WebServUtils.COD_VACS_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public Response getVacaciones (
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@PathParam(WebServUtils.P_PARAM_COD_VACS) String codVacs) {
		ErrorBean errorBean = new ErrorBean();
		VacacionesBean vacaciones = VacacionesHandler.getVacaciones(null, codVacs, errorBean);

		return creaRespuestaGenericaGET(vacaciones, errorBean);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoVacaciones(VacacionesBean vacsRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		VacacionesBean vacaciones = VacacionesHandler.insertVacaciones(null, codRes, codTrab, vacsRaw, errorBean);

		return creaRespuestaGenericaPOST(vacaciones, errorBean);
	}
	
	@PUT
	@Path(WebServUtils.COD_VACS_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public Response modVacaciones(VacacionesBean vacsRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@PathParam(WebServUtils.P_PARAM_COD_VACS) String codVacs) {
		ErrorBean errorBean = new ErrorBean();
		VacacionesBean vacaciones = VacacionesHandler.updateVacaciones(null, codVacs, vacsRaw, errorBean);

		return creaRespuestaGenericaPUT(vacaciones, errorBean);
	}
	
	@DELETE
	@Path(WebServUtils.COD_VACS_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public Response borraVacaciones(
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@PathParam(WebServUtils.P_PARAM_COD_VACS) String codVacs) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = VacacionesHandler.deleteVacaciones(null, codVacs, errorBean);

		return creaRespuestaGenericaDELETE(borrado, VacacionesBean.class, errorBean);
	}
}
