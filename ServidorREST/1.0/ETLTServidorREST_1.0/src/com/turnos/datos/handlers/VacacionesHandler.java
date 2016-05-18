package com.turnos.datos.handlers;

import java.util.ArrayList;
import java.util.Date;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.datos.vo.VacacionesBean;

public class VacacionesHandler extends GenericHandler {

	private static final int LOC_H = 76;
	
	// TODO
	private static final String QUERY_LISTA_VACACIONES_TRAB = "";
	// TODO
	private static final String QUERY_LISTA_VACACIONES_RES_DIA = "";
	// TODO
	private static final String QUERY_GET_VACACIONES_COD = "";
	// TODO
	private static final String UPDATE_INSERT_NUEVO_VACACIONES = "";
	// TODO
	private static final String UPDATE_UPDATE_VACACIONES = "";
	// TODO
	private static final String UPDATE_DELETE_VACACIONES = ""; 

	//00xx
	public static ArrayList<VacacionesBean> listVacaciones(Object object,
			String codRes, String codTrab, Date fecha_ini, Date fecha_fin, UsuarioBean usuarioLog, ErrorBean errorBean) {
		int LOC_M = 1;
		// TODO Auto-generated method stub
		return null;
	}

	//01xx
	public static VacacionesBean getVacaciones(Object object, String codVacs,
			UsuarioBean usuarioLog, ErrorBean errorBean) {
		int LOC_M = 2;
		// TODO Auto-generated method stub
		return null;
	}

	//02xx
	public static ArrayList<VacacionesBean> listVacacionesResDia(Object object,
			String codRes, Date fecha, int limite, int offset, UsuarioBean usuarioLog, ErrorBean eb) {
		int LOC_M = 3;
		// TODO Auto-generated method stub
		return null;
	}

	//03xx
	public static VacacionesBean insertVacaciones(Object object, String codRes,
			String codTrab, VacacionesBean vacsRaw, ErrorBean errorBean) {
		int LOC_M = 4;
		// TODO Auto-generated method stub
		return null;
	}

	//04xx
	public static VacacionesBean updateVacaciones(Object object,
			String codVacs, VacacionesBean vacsRaw, ErrorBean errorBean) {
		int LOC_M = 5;
		// TODO Auto-generated method stub
		return null;
	}

	//05xx
	public static boolean deleteVacaciones(Object object, String codVacs,
			ErrorBean errorBean) {
		int LOC_M = 6;
		// TODO Auto-generated method stub
		return false;
	}

}
