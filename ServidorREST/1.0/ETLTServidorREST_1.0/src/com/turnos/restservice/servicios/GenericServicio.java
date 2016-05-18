package com.turnos.restservice.servicios;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ETLTBean;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.restservice.filtros.AutenticacionFiltro;

public abstract class GenericServicio {
	
	public static final int LIMITE_LISTAS_DEFAULT = 50;
	
	protected UsuarioBean usuarioLog;

	protected GenericServicio(UsuarioBean usuarioLog) {
		this.usuarioLog = usuarioLog;
	}
	
	protected GenericServicio(ContainerRequestContext request) {
		Object usrObj = (request == null) ? null : request.getProperty(AutenticacionFiltro.REQUEST_PARAM_USUARIO);
		if(usrObj != null && usrObj instanceof UsuarioBean) {
			this.usuarioLog = (UsuarioBean) usrObj;
		} else {
			this.usuarioLog = null;
		}
	}
	
	protected static int[] calculaLimiteOffsetCorrectos(int limite, int offset) {
		return calculaLimiteOffsetCorrectos(limite, offset, LIMITE_LISTAS_DEFAULT);
	}
	
	protected static int[] calculaLimiteOffsetCorrectos(int limite, int offset, int maxLimite) {
		int[] res = new int[2];
		if(limite <= 0 || limite > maxLimite) res[0] = maxLimite;
		else res[0] = limite;
		
		if(offset < 0) res[1] = 0;
		else res[1] = offset;
		
		return res;
	}
	
	protected static <T extends ETLTBean> Response creaRespuestaGenericaGETLista(List<T> lista, ErrorBean eb) {
		return creaRespuestaGenericaGETLista(lista, eb, 0, 0);
	}
	
	protected static <T extends ETLTBean> Response creaRespuestaGenericaGETLista(List<T> lista, ErrorBean eb, int limite, int offset) {
		RespuestaBean<T> respuesta;
		if(lista == null) {
			respuesta = new RespuestaBean<T>(eb);
		} else {
			respuesta = new RespuestaBean<T>(lista);
			respuesta.setHtmlStatus(Status.OK);
		}
		setParamsRespuesta(respuesta, limite, offset);
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	protected static <T extends ETLTBean> Response creaRespuestaGenericaGET(T bean, ErrorBean eb) {
		RespuestaBean<T> respuesta;
		if(bean == null) {
			respuesta = new RespuestaBean<T>(eb);
		} else {
			respuesta = new RespuestaBean<T>(bean);
			respuesta.setHtmlStatus(Status.OK);
		}
		setParamsRespuesta(respuesta);
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	protected static <T extends ETLTBean> Response creaRespuestaGenericaPOST(T bean, ErrorBean eb) {
		RespuestaBean<T> respuesta;
		if(bean == null) {
			respuesta = new RespuestaBean<T>(eb);
		} else {
			respuesta = new RespuestaBean<T>(bean);
			respuesta.setHtmlStatus(Status.CREATED);
		}
		setParamsRespuesta(respuesta);
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	protected static <T extends ETLTBean> Response creaRespuestaGenericaPUT(T bean, ErrorBean eb) {
		RespuestaBean<T> respuesta;
		if(bean == null) {
			respuesta = new RespuestaBean<T>(eb);
		} else {
			respuesta = new RespuestaBean<T>(bean);
			respuesta.setHtmlStatus(Status.ACCEPTED);
		}
		setParamsRespuesta(respuesta);
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	protected static <T extends ETLTBean> Response creaRespuestaGenericaDELETE(boolean borrado, Class<T> clase, ErrorBean eb) {
		RespuestaBean<T> respuesta;
		if(borrado) {
			respuesta = new RespuestaBean<T>();
			respuesta.setHtmlStatus(Status.ACCEPTED);
		} else {
			respuesta = new RespuestaBean<T>(eb);
		}
		setParamsRespuesta(respuesta);
		return Response.status(respuesta.getHtmlStatus()).entity(respuesta).build();
	}

	
	private static <T extends ETLTBean> void setParamsRespuesta(RespuestaBean<T> respuesta) {
		setParamsRespuesta(respuesta, System.currentTimeMillis(), 0, 0);
	}
	
	private static <T extends ETLTBean> void setParamsRespuesta(RespuestaBean<T> respuesta, int limite, int offset) {
		setParamsRespuesta(respuesta, System.currentTimeMillis(), limite, offset);
	}
	
	private static <T extends ETLTBean> void setParamsRespuesta(RespuestaBean<T> respuesta, long time, int limite, int offset) {
		respuesta.setTimestamp(time);
		respuesta.setLimite(limite);
		respuesta.setOffset(offset);
	}
}
