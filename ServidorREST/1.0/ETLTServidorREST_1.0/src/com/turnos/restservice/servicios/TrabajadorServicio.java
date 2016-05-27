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
import com.turnos.datos.handlers.TrabajadorHandler;
import com.turnos.datos.handlers.TurnoTrabajadorDiaHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Trabajador")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH
		+ WebServUtils.PREF_TRAB_PATH)
public class TrabajadorServicio extends GenericServicio{
	
	protected TrabajadorServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected TrabajadorServicio(@Context ContainerRequestContext request) {
		super(request);
	}

	@GET
	@Valid
	public Response listaTrabajadores (@PathParam(WebServUtils.P_PARAM_COD_RES) String codTrab,
			
			@QueryParam(WebServUtils.Q_PARAM_LIMITE) @DefaultValue("-1") int limite,
			@QueryParam(WebServUtils.Q_PARAM_OFFSET) @DefaultValue("-1") int offset) {		
		ErrorBean errorBean = new ErrorBean();
		int[] limiteOffset = calculaLimiteOffsetCorrectos(limite, offset);
		limite = limiteOffset[0]; offset = limiteOffset[1];
		ArrayList<TrabajadorBean> listaTrabajadores = TrabajadorHandler.listTrabajadores(null, codTrab, errorBean);

		return creaRespuestaGenericaGETLista(listaTrabajadores, errorBean, limite, offset);
	}
	
	@GET
	@Path(WebServUtils.COD_TRAB_PATH)
	@Valid
	public Response getTrabajador (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		TrabajadorBean trabajador = TrabajadorHandler.getTrabajador(null, codRes, codTrab, errorBean);

		return creaRespuestaGenericaGET(trabajador, errorBean);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoTrabajador(TrabajadorBean trabRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean errorBean = new ErrorBean();
		TrabajadorBean trabajador = TrabajadorHandler.insertTrabajador(null, codRes, trabRaw, errorBean);

		return creaRespuestaGenericaPOST(trabajador, errorBean);
	}
	
	@PUT
	@Path(WebServUtils.COD_TRAB_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modTrabajador(TrabajadorBean trabRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		TrabajadorBean trabajador = TrabajadorHandler.updateTrabajador(null, codRes, codTrab, trabRaw, errorBean);

		return creaRespuestaGenericaPUT(trabajador, errorBean);
	}
	
	@DELETE
	@Path(WebServUtils.COD_TRAB_PATH)
	@Valid
	public Response borraTrabajador (@PathParam("codRes") String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = TrabajadorHandler.deleteTrabajador(null, codRes, codTrab, errorBean);

		return creaRespuestaGenericaDELETE(borrado, TrabajadorBean.class, errorBean);
	}
	
	
	@GET
	@Path(WebServUtils.COD_TRAB_PATH + WebServUtils.PREF_DIA_PATH)
	@Valid
	public Response getHorarioTrabajadorDia(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@QueryParam(WebServUtils.Q_PARAM_FECHA)
			@DefaultValue("-1") int time) {
		Date fecha = null;
		ErrorBean eb = new ErrorBean();
		try {
			if (time > 0) {
				fecha = new Date(time * 1000l);
			} else {
				fecha = Calendar.getInstance().getTime();
			}
		} catch (Exception e) {
			int[] loc = {61,5,0};
			String msg = "momento (%s) no parseable, o algo [["+e.getMessage()+"]]";
			String[] params = {String.valueOf(time)};
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s48", loc, msg, params);
		}

		TurnoTrabajadorDiaBean turno = TurnoTrabajadorDiaHandler.getTurnoTrabajadorDia(null, codRes, codTrab, fecha, eb);

		return creaRespuestaGenericaGET(turno, eb);
	}

	@GET
	@Path(WebServUtils.COD_TRAB_PATH + WebServUtils.PREF_HORARIO_PATH)
	@Valid
	public Response getHorarioTrabajadorRango(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin) {
		Date fecha_ini = null;
		Date fecha_fin = null;
		ErrorBean eb = new ErrorBean();
		RespuestaBean<TurnoTrabajadorDiaBean> respuesta = null;
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
			int[] loc = {61,6,0};
			String msg = "momentos (%s, %s) no parseable, o algo [["+e.getMessage()+"]]";
			String[] params = {String.valueOf(time_ini), String.valueOf(time_fin)};
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s48", loc, msg, params);
		}
		
		ArrayList<TurnoTrabajadorDiaBean> listaTurnos = TurnoTrabajadorDiaHandler.getTurnosTrabajadorRango(null, codRes, codTrab, fecha_ini, fecha_fin,  eb);

		return creaRespuestaGenericaGETLista(listaTurnos, eb, -1, -1 );
	}

	@GET
	@Path(WebServUtils.COD_TRAB_PATH + WebServUtils.PREF_CAMBIO_PATH)
	@Valid
	public Response getHorarioTrabajadorRango(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_INI)
			@DefaultValue("-1") int time_ini,
			@QueryParam(WebServUtils.Q_PARAM_TIEMPO_FIN)
			@DefaultValue("-1") int time_fin) {
		Date fecha_ini = null;
		Date fecha_fin = null;
		ErrorBean eb = new ErrorBean();
		RespuestaBean<PropuestaCambioBean> respuesta = null;
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
			int[] loc = {61,6,0};
			String msg = "momentos (%s, %s) no parseable, o algo [["+e.getMessage()+"]]";
			String[] params = {String.valueOf(time_ini), String.valueOf(time_fin)};
			ErrorBeanFabrica.generaErrorBean(eb, Status.BAD_REQUEST, "s48", loc, msg, params);
		}
		
		ArrayList<PropuestaCambioBean> listaCambios = TurnoTrabajadorDiaHandler.getTurnosTrabajadorRango(null, codRes, codTrab, fecha_ini, fecha_fin,  eb);

		return creaRespuestaGenericaGETLista(listaTurnos, eb, -1, -1 );
	}

}
