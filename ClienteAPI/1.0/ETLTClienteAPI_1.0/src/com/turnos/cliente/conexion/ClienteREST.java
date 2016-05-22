package com.turnos.cliente.conexion;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.turnos.datos.WebServUtils;
import com.turnos.datos.vo.ETLTBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.SesionBean;

public class ClienteREST {

	public static enum MetodoHTTP{GET, POST, PUT, DELETE, OPTIONS, HEAD};

	public static SesionBean login(String tokenLogin, Aplicacion aplicacion) {
		Hashtable<String, String> headerParams = new Hashtable<String, String>(1);
		headerParams.put("tokenLogin", tokenLogin);
		RespuestaBean<SesionBean> respuesta = llamada(aplicacion, new SesionBean(),
				WebServUtils.PREF_AUTH_PATH + WebServUtils.PREF_LOGIN_PATH,
				MetodoHTTP.GET, null, null, headerParams, null);
		if (respuesta != null && respuesta.getResultado() != null) {
			return respuesta.getResultado();
		} else {
			//TODO ( probablemente lanzar excepcion (??) )
			return null;
		}
	}

	public static List<ResidenciaBean> residenciaListaResidencias(String pais, String provincia, String municipio, boolean incGeo, int limite, int offset, Sesion sesion) {
		Hashtable<String, String> queryParams = new Hashtable<String, String>(6);
		if (pais != null) {
			queryParams.put(WebServUtils.Q_PARAM_COD_PAIS, pais);
		}
		if (provincia != null) {
			queryParams.put(WebServUtils.Q_PARAM_COD_PROV, provincia);
		}
		if (municipio != null) {
			queryParams.put(WebServUtils.Q_PARAM_COD_MUNI, municipio);
		}
		if (limite > 0) {
			queryParams.put(WebServUtils.Q_PARAM_LIMITE, Integer.toString(limite));
		}
		if (offset > 0) {
			queryParams.put(WebServUtils.Q_PARAM_OFFSET, Integer.toString(offset));
		}
		queryParams.put(WebServUtils.Q_PARAM_INC_GEO, Boolean.toString(incGeo));

		RespuestaBean<ResidenciaBean> respuesta = llamada(sesion, new ResidenciaBean(),
				WebServUtils.PREF_RES_PATH,
				MetodoHTTP.GET, queryParams, null, null, null);
		if (respuesta != null && respuesta.getResultado() != null) {			
			return respuesta.getListaResultados();
		} else {
			//TODO ( probablemente lanzar excepcion (??) )
			return null;
		}
	}

	public static ResidenciaBean residenciaGetResidencia(String codRes, boolean incGeo, Sesion sesion) {
		Hashtable<String, String> queryParams = new Hashtable<String, String>(1);
		queryParams.put(WebServUtils.Q_PARAM_INC_GEO, Boolean.toString(incGeo));
		RespuestaBean<ResidenciaBean> respuesta = llamada(sesion, new ResidenciaBean(),
				WebServUtils.PREF_RES_PATH + '/' + codRes,
				MetodoHTTP.GET, queryParams, null, null, null);
		if (respuesta != null && respuesta.getResultado() != null) {			
			return respuesta.getResultado();
		} else {
			//TODO ( probablemente lanzar excepcion (??) )
			return null;
		}
	}
		
	
	private static <T extends ETLTBean> RespuestaBean<T> llamada(
			Aplicacion aplicacion, T tipo, String recurso, MetodoHTTP metodo,
			Map<String, String> queryParams, Map<String, String> postParams,
			Map<String, String> headerParams, String jsonBody) {
		
		System.out.println(" ***** LLAMANDO *** (" + recurso + ", " + metodo + ", " + jsonBody + ") *****");
		
		RespuestaBean<T> res = new RespuestaBean<T>();
		Client client = null;
		
		try {
			client = ClientBuilder.newClient().register(JacksonJaxbJsonProvider.class);
			WebTarget target = client.target(aplicacion.baseURL).path(recurso);
			
			if(queryParams != null && !queryParams.isEmpty()) {
				for(Entry<String, String> entry: queryParams.entrySet()) {
					target = target.queryParam(entry.getKey(), entry.getValue());
				}
			}
			
			Builder b = target.request(MediaType.APPLICATION_JSON_TYPE);
			if (headerParams == null) {
				headerParams = new Hashtable<String, String>(1);
			}
			headerParams.put("publicKey", aplicacion.publicKey);
			b = b.headers(new MultivaluedHashMap<String, Object>(headerParams));
			
			/*
			Entity e = null;
			if(postParams != null && !postParams.isEmpty()) {
				Form form = new Form();
				for(Entry<String, String> entry: postParams.entrySet()) {
					form.param(entry.getKey(), entry.getValue());
				}
				e = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
			}
			*/
			
			//------------------------------.
			switch(metodo) {
				case GET: res = b.get().readEntity(res.getClass());
					break;
				case POST: res = b.post(Entity.entity(jsonBody, MediaType.APPLICATION_JSON_TYPE), res.getClass());
					break;
				case PUT: res = b.put(Entity.entity(jsonBody, MediaType.APPLICATION_JSON_TYPE), res.getClass());
					break;
				case DELETE: res = b.delete(res.getClass());
					break;
				default: return null;
			}

		} finally {
			if(client != null) client.close();
		}
		return res;	
	}

	public static <T extends ETLTBean> RespuestaBean<T> llamada(Sesion sesion,
		T tipo, String recurso, MetodoHTTP metodo,
		Map<String, String> queryParams, Map<String, String> postParams,
		Map<String, String> headerParams, String jsonBody) {
		
		if (headerParams == null) {
			headerParams = new Hashtable<String, String>(1);
		}
		headerParams.put("tokenSesion", sesion.getTokenSesion());
		return llamada(sesion.aplicacion, tipo, recurso, metodo, queryParams, postParams, headerParams, jsonBody);
	}
}