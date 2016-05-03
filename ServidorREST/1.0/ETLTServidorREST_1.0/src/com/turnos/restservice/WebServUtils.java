package com.turnos.restservice;

import java.util.Calendar;
import java.util.Date;

public class WebServUtils {

	public static final String PREF_RES_PATH = "/res";
	public static final String COD_RES_PATH  = "/{codRes: [A-Z0-9_]{3,32}}";
	public static final String P_PARAM_COD_RES  = "codRes";
	
	public static final String PREF_TRAB_PATH = "/trab";
	public static final String COD_TRAB_PATH  = "/{codTrab: [A-Z0-9_]{3,32}}";
	public static final String P_PARAM_COD_TRAB  = "codTrab";
	
	public static final String PREF_VACS_PATH = "/turno";
	public static final String COD_VACS_PATH = "/{vacsServ: [0-9]{1,11}}";
	public static final String P_PARAM_COD_VACS  = "vacsServ";
	
	public static final String PREF_TURNO_PATH = "/turno";
	public static final String COD_TURNO_PATH = "/{codTurno: [A-Z0-9_]{3,32}}";
	public static final String P_PARAM_COD_TURNO  = "codTurno";
	public static final String Q_PARAM_TIPO_TURNO = "tipo_fiesta";
	
	public static final String PREF_SERV_PATH = "/serv";
	public static final String COD_SERV_PATH = "/{codServ: [0-9]{1,11}}";
	public static final String P_PARAM_COD_SERV  = "codServ";
	
	public static final String PREF_GEO_PATH = "/geo";
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

	public static final String Q_PARAM_FECHA = "fecha";
	public static final String Q_PARAM_TIEMPO_INI = "tiempo_ini";
	public static final String Q_PARAM_TIEMPO_FIN = "tiempo_fin";
	public static final String Q_PARAM_LIMITE = "limite";
	public static final String Q_PARAM_COMPLETO = "completo";
	public static final String Q_PARAM_INC_GEO = "inc_geo";
	
	
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
