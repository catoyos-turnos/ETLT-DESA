package com.turnos.restservice.servicios;

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

import com.turnos.datos.WebServUtils;
import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.handlers.TrabajadorHandler;
import com.turnos.datos.handlers.TurnoTrabajadorDiaHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;

@Api(value = "Trabajador")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH
		+ WebServUtils.PREF_TRAB_PATH)
public class TrabajadorServicio {
	private UsuarioBean usuarioLog;
	
	@Context
	private TrabajadorServicio(HttpServletRequest request) {
		Object usrObj = request==null?null:request.getAttribute(AutenticacionFiltro.REQUEST_PARAM_USUARIO);
		if(usrObj != null && usrObj  instanceof UsuarioBean) {
			this.usuarioLog = (UsuarioBean) usrObj;
		}
	}


	@GET
	@Valid
	public Response listaTrabajadores (@PathParam(WebServUtils.P_PARAM_COD_RES) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<TrabajadorBean> listaTrabajadores = TrabajadorHandler.listTrabajadores(null, codTrab, errorBean);
		RespuestaBean<TrabajadorBean> respuesta = null;
		
		if(listaTrabajadores == null) {
			respuesta = new RespuestaBean<TrabajadorBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<TrabajadorBean>(listaTrabajadores);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@GET
	@Path(WebServUtils.COD_TRAB_PATH)
	@Valid
	public Response getTrabajador (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		TrabajadorBean trabajador = TrabajadorHandler.getTrabajador(null, codRes, codTrab, errorBean);
		RespuestaBean<TrabajadorBean> respuesta = null;

		if(trabajador == null) {
			respuesta = new RespuestaBean<TrabajadorBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<TrabajadorBean>(trabajador);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoTrabajador(TrabajadorBean trabRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean errorBean = new ErrorBean();
		TrabajadorBean trabajador = TrabajadorHandler.insertTrabajador(null, codRes, trabRaw, errorBean);
		RespuestaBean<TrabajadorBean> respuesta = null;

		if(trabajador == null) {
			respuesta = new RespuestaBean<TrabajadorBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<TrabajadorBean>(trabajador);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
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
		RespuestaBean<TrabajadorBean> respuesta = null;
		
		if(trabajador == null) {
			respuesta = new RespuestaBean<TrabajadorBean>(errorBean);
		} else {
			respuesta = new RespuestaBean<TrabajadorBean>(trabajador);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	@DELETE
	@Path(WebServUtils.COD_TRAB_PATH)
	@Valid
	public Response borraTrabajador (@PathParam("codRes") String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = TrabajadorHandler.deleteTrabajador(null, codRes, codTrab, errorBean);
		RespuestaBean<TrabajadorBean> respuesta = null;
		
		if(borrado) {
			respuesta = new RespuestaBean<TrabajadorBean>();
			respuesta.setHtmlStatus(Status.ACCEPTED);
		} else {
			respuesta = new RespuestaBean<TrabajadorBean>(errorBean);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}
	
	
	@GET
	@Path(WebServUtils.COD_TRAB_PATH + WebServUtils.PREF_DIA_PATH)
	@Valid
	public Response getHorarioTrabajadorDia(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TRAB) String codTrab,
			@QueryParam(WebServUtils.Q_PARAM_FECHA)
			@DefaultValue("-1") int time) {
		Date fecha = null;
		RespuestaBean<TurnoTrabajadorDiaBean> respuesta = null;
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
		if(turno == null) {
			respuesta = new RespuestaBean<TurnoTrabajadorDiaBean>(eb);
		} else {
			respuesta = new RespuestaBean<TurnoTrabajadorDiaBean>(turno);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
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
		
		ArrayList<TurnoTrabajadorDiaBean> listaTurnos = TurnoTrabajadorDiaHandler.getTurnosTrabajadorRango(null, codRes, codTrab, fecha_ini, fecha_fin, eb);
		if(listaTurnos == null) {
			respuesta = new RespuestaBean<TurnoTrabajadorDiaBean>(eb);
		} else {
			respuesta = new RespuestaBean<TurnoTrabajadorDiaBean>(listaTurnos);
		}
		
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

}
