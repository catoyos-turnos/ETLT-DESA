package com.turnos.cliente.conexion;

import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.turnos.datos.vo.ETLTBean;
import com.turnos.datos.vo.RespuestaBean;

public class ClienteREST {

	public static final String BASE_URL = "https://raspberrypi:8081/ServidorREST-1.0/api";
	public static enum MetodoHTTP{GET, POST, PUT, DELETE, OPTIONS, HEAD};

	public static <T extends ETLTBean> RespuestaBean<T> llamada(T tipo, String recurso, MetodoHTTP metodo, 
			Map<String, String> queryParams, Map<String, String> postParams, String jsonBody) {
		RespuestaBean<T> res = null;
		 
		Client client = ClientBuilder.newClient().register(JacksonJaxbJsonProvider.class);
		WebTarget target = client.target(BASE_URL).path(recurso);
		
		//------------------------------
		Form form = null;
		if(postParams != null && !postParams.isEmpty()) {
			form = new Form();
			for(Entry<String, String> entry: postParams.entrySet()) {
				form.param(entry.getKey(), entry.getValue());
			}
		}
		//------------------------------
		
		// TODO
		switch(metodo) {
			case GET: res = target.request(MediaType.APPLICATION_JSON_TYPE).get(res.getClass());
				break;
			case POST: res = target.request(MediaType.APPLICATION_JSON_TYPE).post(null, res.getClass());
				break;
			case PUT: res = target.request(MediaType.APPLICATION_JSON_TYPE).put(null, res.getClass());
				break;
			case DELETE: res = target.request(MediaType.APPLICATION_JSON_TYPE).delete(res.getClass());
				break;
			default: return null;
		}

		return res;		
	}
}