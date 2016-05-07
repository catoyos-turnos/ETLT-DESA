package com.turnos.datos.handlers;

import java.sql.Connection;
import java.util.ArrayList;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TrabajadorBean;

//71xxxx
public class TrabajadorHandler extends GenericHandler {

	//00xx
	public static boolean existeTrabajador(Object object,
			String codTrab, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//01xx
	public static ArrayList<TrabajadorBean> listTrabajadores(Object object,
			String codTrab, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	//02xx
	public static TrabajadorBean getTrabajador(Connection conexion, String codRes,
			String codTrab, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	//03xx
	public static TrabajadorBean insertTrabajador(Connection conexion, String codRes,
			TrabajadorBean trabRaw, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	//04xx
	public static TrabajadorBean updateTrabajador(Connection conexion, String codRes,
			String codTrab, TrabajadorBean trabRaw, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}

	//05xx
	public static boolean deleteTrabajador(Connection conexion, String codRes,
			String codTrab, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return false;
	}

}
