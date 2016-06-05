package com.turnos.cliente.conexion;

import java.util.Date;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.turnos.datos.WebServUtils;
import com.turnos.datos.vo.ComentarioBean;
import com.turnos.datos.vo.ETLTBean;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.MensajeBean;
import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.PaisBean;
import com.turnos.datos.vo.PropuestaCambioBean;
import com.turnos.datos.vo.ProvinciaBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.RespuestaBean;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.SesionBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;
import com.turnos.datos.vo.UsuarioBean;

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
/*
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

	public static TrabajadorBean trabajadorGetTrabajador(String codRes, String codTrab, Sesion sesion) {
		RespuestaBean<TrabajadorBean> respuesta = llamada(sesion, new TrabajadorBean(),
				WebServUtils.PREF_RES_PATH + '/' + codRes + WebServUtils.PREF_TRAB_PATH + '/' + codTrab,
				MetodoHTTP.GET, null, null, null, null);
		if (respuesta != null && respuesta.getResultado() != null) {			
			return respuesta.getResultado();
		} else {
			//TODO ( probablemente lanzar excepcion (??) )
			return null;
		}
	}
	
	//*/
	
	/* ************************************ */
	
	public static MensajeBean mensajeGetMensaje(long arg0, long arg1, int arg2, boolean arg3, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static int mensajeGetNumMensajes(long arg0, boolean arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return -1;
	}

	public static int mensajeGetNumRespuestas(long arg0, long arg1, boolean arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return -1;
	}

	public static List<MensajeBean> mensajeListaMensajes(long arg0, String arg1, boolean arg2, boolean arg3, int arg4, int arg5, int arg6, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<MensajeBean> mensajeListaRespuestas(long arg0, long arg1, boolean arg2, boolean arg3, int arg4, int arg5, int arg6, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static MensajeBean mensajeNuevoMensaje(MensajeBean arg0, long arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static boolean mensajeSetMensajeLeido(long arg0, long arg1, boolean arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static boolean mensajeSetMensajeNoLeido(long arg0, long arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static boolean mensajeSetMensajeSiLeido(long arg0, long arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static UsuarioBean usuarioCambiaNivelUsuario(int arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static UsuarioBean usuarioGetUsuario(int arg0, boolean arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static UsuarioBean usuarioModUsuario(UsuarioBean arg0, int arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static UsuarioBean usuarioNuevoUsuario(UsuarioBean arg0, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static boolean turnoBorraTurno(String arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static TurnoBean turnoGetTurno(String arg0, String arg1, boolean arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<TurnoBean> turnoListaTurnos(String arg0, String arg1, boolean arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static TurnoBean turnoModTurno(TurnoBean arg0, String arg1, String arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static TurnoBean turnoNuevoTurno(TurnoBean arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}


public static List<FestivoBean> municipioGetFestivosMunicipio(String codPais, String codProvincia, String codMunicipio, Date time_ini, Date time_fin, boolean completo, boolean inc_geo, int limite, int offset, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static MunicipioBean municipioGetMunicipio(String arg0, String arg1, String arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

public static List<ResidenciaBean> municipioGetResidenciasMunicipio(String arg0, String arg1, String arg2, boolean arg3, int arg4, int arg5, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<MunicipioBean> municipioListaMunicipios(String arg0, String arg1, int arg2, int arg3, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static boolean residenciaBorraResidencia(String arg0, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static List<FestivoBean> residenciaGetDiasFestivos(String arg0, Date time_ini, Date time_fin, int arg3, int arg4, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<TurnoTrabajadorDiaBean> residenciaGetHorarioCompletoDia(String arg0, Date time, int arg2, int arg3, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
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

	public static ResidenciaBean residenciaModResidencia(ResidenciaBean arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static ResidenciaBean residenciaNuevaResidencia(ResidenciaBean arg0, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static boolean servicioBorraServicio(String arg0, String arg1, long arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static ServicioBean servicioGetServicio(String arg0, String arg1, long arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<ServicioBean> servicioListaServicios(String arg0, String arg1, int arg2, int arg3, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static ServicioBean servicioModServicio(ServicioBean arg0, String arg1, String arg2, long arg3, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static ServicioBean servicioNuevoServicio(ServicioBean arg0, String arg1, String arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static boolean festivoBorraDiaFestivo(long arg0, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static FestivoBean festivoGetDiaFestivo(long arg0, boolean arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<FestivoBean> festivoListaDiasFestivos(String codPais, String codProvincia, String codMunicipio,
			String tipoStr, Date time_ini, Date time_fin, boolean completo, boolean incGeo,
			int limite, int offset, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static FestivoBean festivoModDiaFestivo(FestivoBean arg0, long arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static FestivoBean festivoNuevoDiaFestivo(FestivoBean arg0, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static boolean comentarioBorraComentario(long arg1, long arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static ComentarioBean comentarioNuevoComentario(ComentarioBean arg0, long arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

public static List<FestivoBean> paisGetFestivosPais(String codPais, Date time_ini, Date time_fin, boolean inc_geo, int limite, int offset, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static PaisBean paisGetPais(String arg0, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

public static List<ResidenciaBean> paisGetResidenciasPais(String arg0, boolean arg1, int arg2, int arg3, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<PaisBean> paisListaPaises(int arg0, int arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}
	
public static List<FestivoBean> provinciaGetFestivosProvincia(String codPais, String codProvincia, Date time_ini, Date time_fin, boolean completo, boolean inc_geo, int limite, int offset, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static ProvinciaBean provinciaGetProvincia(String arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

public static List<ResidenciaBean> provinciaGetResidenciasProvincia(String arg0, String arg1, boolean arg2, int arg3, int arg4, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<ProvinciaBean> provinciaListaProvincias(String arg0, int arg1, int arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static boolean trabajadorBorraTrabajador(String arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return false;
	}

	public static TurnoTrabajadorDiaBean trabajadorGetHorarioTrabajadorDia(String arg0, String arg1, int arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static List<TurnoTrabajadorDiaBean> trabajadorGetHorarioTrabajadorRango(String arg0, String arg1, int arg2, int arg3, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static int trabajadorGetNumPropuestasCambio(String arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return -1;
	}

public static List<PropuestaCambioBean> trabajadorGetPropuestasCambio(String arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static TrabajadorBean trabajadorGetTrabajador(String codRes, String codTrab, Sesion sesion) {
		RespuestaBean<TrabajadorBean> respuesta = llamada(sesion, new TrabajadorBean(),
				WebServUtils.PREF_RES_PATH + '/' + codRes + WebServUtils.PREF_TRAB_PATH + '/' + codTrab,
				MetodoHTTP.GET, null, null, null, null);
		if (respuesta != null && respuesta.getResultado() != null) {			
			return respuesta.getResultado();
		} else {
			//TODO ( probablemente lanzar excepcion (??) )
			return null;
		}
	}

	public static List<TrabajadorBean> trabajadorListaTrabajadores(String arg0, int arg1, int arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static TrabajadorBean trabajadorModTrabajador(TrabajadorBean arg0, String arg1, String arg2, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static TrabajadorBean trabajadorNuevoTrabajador(TrabajadorBean arg0, String arg1, Sesion sesion) {
		//-----------------
		//---- TODO -------
		//-----------------
		return null;
	}

	public static PropuestaCambioBean propuestacambioNuevoPropuestaCambio(
			PropuestaCambioBean rawBean, Sesion sesion) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PropuestaCambioBean propuestacambioModPropuestaCambio(
			PropuestaCambioBean rawBean, long id_cambio,
			Sesion sesion) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<PropuestaCambioBean> propuestacambioListaPropuestaCambios(String estado, Sesion sesion) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PropuestaCambioBean propuestacambioGetPropuestaCambio(long id_cambio, boolean b, Sesion sesion) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean propuestacambioBorraPropuestaCambio(long id_cambio, Sesion sesion) {
		// TODO Auto-generated method stub
		return false;
	}

	/* ************************************ */
	
	private static <T extends ETLTBean> RespuestaBean<T> llamada(
			Aplicacion aplicacion, T tipo, String recurso, MetodoHTTP metodo,
			Map<String, String> queryParams, Map<String, String> postParams,
			Map<String, String> headerParams, String jsonBody) {
		
		System.out.println(" ***** LLAMANDO *** (" + recurso + ", " + metodo + ", " + queryParams + ", " + jsonBody + ") *****");
		
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
			Response rp;
			switch(metodo) {
				case GET:  rp = b.get();
					break;
				case POST: rp = b.post(Entity.entity(jsonBody, MediaType.APPLICATION_JSON_TYPE));
					break;
				case PUT: rp = b.put(Entity.entity(jsonBody, MediaType.APPLICATION_JSON_TYPE));
					break;
				case DELETE: rp = b.delete();
					break;
				default: return null;
			}
			if (rp.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
				res = rp.readEntity(RespuestaBean.class);
			} else {
				//TODO
//				System.out.println("****** MAL ******" + rp);
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