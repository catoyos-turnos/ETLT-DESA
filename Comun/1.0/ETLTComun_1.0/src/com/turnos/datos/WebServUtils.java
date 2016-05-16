package com.turnos.datos;

import java.util.Calendar;
import java.util.Date;

public class WebServUtils {

	public static final String PREF_AUTH_PATH = "/auth";
	
	public static final String PREF_RES_PATH = "/res";
	public static final String COD_RES_PATH  = "/{codRes: [A-Z0-9_]{3,32}}";
	public static final String P_PARAM_COD_RES  = "codRes";
	
	public static final String PREF_TRAB_PATH = "/trab";
	public static final String COD_TRAB_PATH  = "/{codTrab: [A-Z0-9_]{3,32}}";
	public static final String P_PARAM_COD_TRAB  = "codTrab";
	
	public static final String PREF_VACS_PATH = "/vacs";
	public static final String COD_VACS_PATH  = "/{codVacs: [0-9]{1,11}}";
	public static final String P_PARAM_COD_VACS  = "codVacs";
	
	public static final String PREF_TURNO_PATH = "/turno";
	public static final String COD_TURNO_PATH  = "/{codTurno: [A-Z0-9_]{3,32}}";
	public static final String P_PARAM_COD_TURNO  = "codTurno";
	public static final String Q_PARAM_TIPO_TURNO = "tipo_fiesta";
	public static final String Q_PARAM_INC_SERVS = "inc_servs";
	
	public static final String PREF_SERV_PATH = "/serv";
	public static final String COD_SERV_PATH  = "/{codServ: [0-9]{1,11}}";
	public static final String P_PARAM_COD_SERV  = "codServ";
	
	public static final String PREF_GEO_PATH  = "/geo";
	public static final String PREF_PAIS_PATH = "/pais";
	public static final String COD_PAIS_PATH  = "/{codPais: [A-Z0-9_]{2}}";
	public static final String PREF_PROV_PATH = "/prov";
	public static final String COD_PROV_PATH  = "/{codProvincia: [A-Z0-9_]{4}}";
	public static final String PREF_MUNI_PATH = "/muni";
	public static final String COD_MUNI_PATH  = "/{codMunicipio: [A-Z0-9_]{8}}";
	public static final String P_PARAM_COD_PAIS  = "codPais";
	public static final String P_PARAM_COD_PROV  = "codProvincia";
	public static final String P_PARAM_COD_MUNI  = "codMunicipio";
	public static final String Q_PARAM_COD_PAIS  = "cod_pais";
	public static final String Q_PARAM_COD_PROV  = "cod_provincia";
	public static final String Q_PARAM_COD_MUNI  = "cod_municipio";
	
	public static final String PREF_HORARIO_PATH = "/horario";
	public static final String PREF_DIA_PATH = "/dia";
	
	public static final String PREF_FEST_PATH = "/fest";
	public static final String COD_FEST_PATH  = "/{codFest: [0-9]{1,11}}";
	public static final String P_PARAM_COD_FEST  = "codFest";
	public static final String Q_PARAM_TIPO_FIESTA = "tipo_fiesta";

	public static final String PREF_USER_PATH = "/usuario";
	public static final String COD_USER_PATH  = "/{codUser: [0-9]{1,11}}";
	public static final String P_PARAM_COD_USER = "codUser";
	public static final String Q_PARAM_COD_USER = "cod_user";
	public static final String Q_PARAM_COD_OTRO_USER = "cod_user_2";
	public static final String Q_PARAM_USER = "user";

	public static final String PREF_MENSAJE_PATH = "/msg";
	public static final String COD_MENSAJE_PATH  = "/{codMsg: [0-9]{1,11}}";
	public static final String P_PARAM_COD_MENSAJE = "codMsg";
	public static final String PREF_MSG_RESPUESTA_PATH = "/respuestas";

	public static final String Q_PARAM_ROL_EN_MSG = "msg_rol"; // (rol de usuario a buscar)
	public static final String Q_PARAM_MSG_ORIGINAL = "original"; // (mensajes original [para devolver respuestas])
	public static final String Q_PARAM_INC_MSG_ORIGINAL = "inc_original"; // (incluye mensaje original en respuestas)
	public static final String Q_PARAM_LISTA_MSG_NO_LEIDOS = "no_leidos"; // (lista mensajes no leidos)
	public static final String Q_PARAM_LISTA_MSG_LEIDOS = "leidos"; // (lista mensajes ya leidos)
	public static final String Q_PARAM_LISTA_RESPUESTAS = "respuestas"; // (lista mensajes en respuesta a otros junto a mensajes originales)
	public static final String Q_PARAM_PROF_RESPUESTAS = "prof_respuestas"; // (niveles de respuesta a devolver)

	public static final String Q_PARAM_FECHA = "fecha"; // (fecha a mostrar)
	public static final String Q_PARAM_TIEMPO_INI = "tiempo_ini"; // (momento inicial para listar)
	public static final String Q_PARAM_TIEMPO_FIN = "tiempo_fin"; // (momento final para listar)
	public static final String Q_PARAM_LIMITE = "limite"; // (cantidad a devolver)
	public static final String Q_PARAM_OFFSET = "offset"; // (pagina)
	public static final String Q_PARAM_COMPLETO = "completo";
	public static final String Q_PARAM_INC_GEO = "inc_geo"; // (incluye info geografica en respuesta)
	
	
	public static Date getFecha(int anio, int mes, int dia) {
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		Date d = null;
		try {
			c.set(anio, mes - 1, dia);
			d = c.getTime();
		} catch (IllegalArgumentException e) {
			return null;
		}
		return d;
	}
}
