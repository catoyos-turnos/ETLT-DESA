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
import com.turnos.datos.handlers.TurnoHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoBean.TipoTurno;
import com.turnos.datos.vo.UsuarioBean;

@Api(value = "Turno")
@Produces(MediaType.APPLICATION_JSON)
@Path(WebServUtils.PREF_RES_PATH + WebServUtils.COD_RES_PATH + WebServUtils.PREF_TURNO_PATH)
public class TurnoServicio extends GenericServicio{
	
	protected TurnoServicio(UsuarioBean usuarioLog) {
		super(usuarioLog);
	}
	
	protected TurnoServicio(@Context ContainerRequestContext request) {
		super(request);
	}
	
	// ---------------------GET-----------------------------------------------

	@GET
	@Valid
	public Response listaTurnos (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@QueryParam(WebServUtils.Q_PARAM_TIPO_TURNO) String tipoStr,
			@QueryParam(WebServUtils.Q_PARAM_INC_SERVS) @DefaultValue("false") boolean includeServs) {
		ErrorBean errorBean = new ErrorBean();
		ArrayList<TurnoBean> listaTurnos;
			
		if (tipoStr == null) {
			listaTurnos = TurnoHandler.listTodosTurnos(null, codRes, includeServs, errorBean);
		} else {
			TipoTurno tipo = TipoTurno.safeValueOf(tipoStr);
			listaTurnos = TurnoHandler.listTurnosTipo(null, codRes, tipo, includeServs, errorBean);
		}

		return creaRespuestaGenericaGETLista(listaTurnos, errorBean);
	}
	
	@GET
	@Path(WebServUtils.COD_TURNO_PATH)
	@Valid
	public Response getTurno (@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno,
			@QueryParam(WebServUtils.Q_PARAM_INC_SERVS) @DefaultValue("false") boolean includeServs) {
		ErrorBean errorBean = new ErrorBean();
		TurnoBean turno = TurnoHandler.getTurno(null, codRes, codTurno, includeServs, errorBean);

		return creaRespuestaGenericaGET(turno, errorBean);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response nuevoTurno(TurnoBean turnoRaw, 
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes) {
		ErrorBean errorBean = new ErrorBean();
		TurnoBean turno = TurnoHandler.insertTurno(null, codRes, turnoRaw, errorBean);

		return creaRespuestaGenericaPOST(turno, errorBean);
	}
	
	@PUT
	@Path(WebServUtils.COD_TURNO_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response modTurno(TurnoBean turnoRaw,
			@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		TurnoBean turno = TurnoHandler.updateTurno(null, codRes, codTurno, turnoRaw, errorBean);

		return creaRespuestaGenericaPUT(turno, errorBean);
	}
	
	@DELETE
	@Path(WebServUtils.COD_TURNO_PATH)
	@Valid
	public Response borraTurno(@PathParam(WebServUtils.P_PARAM_COD_RES) String codRes,
			@PathParam(WebServUtils.P_PARAM_COD_TURNO) String codTurno) {
		ErrorBean errorBean = new ErrorBean();
		boolean borrado = TurnoHandler.deleteTurno(null, codRes, codTurno, errorBean);
		
		return creaRespuestaGenericaDELETE(borrado, ResidenciaBean.class, errorBean);
	}
	
}
