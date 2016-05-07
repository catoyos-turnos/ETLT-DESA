package com.turnos.datos.handlers;

import java.sql.Connection;
import java.util.ArrayList;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TrabajadorBean;
//61xxxx
public class TrabajadorHandler extends GenericHandler {


	public static ArrayList<TrabajadorBean> listTrabajadores(Object object,
			String codTrab, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static TrabajadorBean getTrabajador(Connection conexion, String codRes,
			String codTrab, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TrabajadorBean insertTrabajador(Connection conexion, String codRes,
			TrabajadorBean trabRaw, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TrabajadorBean updateTrabajador(Connection conexion, String codRes,
			String codTrab, TrabajadorBean trabRaw, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean deleteTrabajador(Connection conexion, String codRes,
			String codTrab, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return false;
	}

}
