package com.mkyong.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClienteREST {

	public static final String BASE_URL = "https://raspberrypi:8081/ServidorREST-1.0/rest-api"
	public static enum MetodoHTTP{GET, POST, PUT, DELETE, OPTIONS, HEAD};

	public static RespuestaBean<T> llama(T tipo, String recurso, MetodoHTTP metodo, 
			Map<String, String> queryParams, Map<String, String> postParams, JSONObject jsonBody) {
		RespuestaBean<T> res = null;
		 
		Client client = ClientBuilder.newClient().register(JacksonJaxbJsonProvider.class);
		WebTarget target = client.target(BASE_URL).path(recurso);
		
		//------------------------------
		Form form = null;
		if(postParams != null && !postParams.isEmpty()) {
			form = new Form();
			for(Entry<String, String> entry: postParams.entrySet()) {
				form.param(entry.key, entry.value);
			}
		}
		//------------------------------
		
		//Request request = target.request(MediaType.APPLICATION_JSON_TYPE);
		switch(metodo) {
			case GET: res = target.request(MediaType.APPLICATION_JSON_TYPE).get(RespuestaBean<T>.class);
				break;
			case POST: res = target.request(MediaType.APPLICATION_JSON_TYPE).post(RespuestaBean<T>.class);
				break;
			case PUT: res = target.request(MediaType.APPLICATION_JSON_TYPE).put(RespuestaBean<T>.class);
				break;
			case DELETE: res = target.request(MediaType.APPLICATION_JSON_TYPE).delete(RespuestaBean<T>.class);
				break;
			default: return null;
		}

		return res;		
	}
}