package com.turnos.datos.handlers;

import java.sql.Connection;
import java.util.ArrayList;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TrabajadorBean;

//71xxxx
public class TrabajadorHandler extends GenericHandler {

	private static final String QUERY_EXISTE_TRABAJADOR =
			"SELECT count(*) as existe "
			+ "FROM trabajador trab JOIN residencia res ON trab.id_residencia=res.id_residencia"
			+ "WHERE trab.codigo=? AND res.codigo=?";

	private static final String QUERY_LISTA_TRABAJADORES_RES = 
		"SELECT trab.codigo as codTrab, res.codigo as codRes,"
			+ "trab.nombre as nombre, trab.apellidos as apellidos"
		+ "FROM trabajador trab JOIN residencia res ON trab.id_residencia=res.id_residencia"
		+ "WHERE res.codigo=?";

	private static final String QUERY_GET_TRABAJADOR_COD = 
			"SELECT trab.codigo as codTrab, res.codigo as codRes,"
				+ "trab.nombre as nombre, trab.apellidos as apellidos"
			+ "FROM trabajador trab JOIN residencia res ON trab.id_residencia=res.id_residencia"
			+ "WHERE trab.codigo AND res.codigo=?";

	private static final String UPDATE_INSERT_NUEVO_TRABAJADOR = "INSERT INTO trabajador (codigo, id_residencia, nombre, apellidos) "
					+ "SELECT ?, res.id_residencia, ?, ? FROM residencia res WHERE res.codigo=?";

	private static final String UPDATE_UPDATE_TRABAJADOR = 
			"UPDATE trabajador trab "
				+ "JOIN residencia res ON trab.id_residencia=res.id_residencia "
			+ "SET %s WHERE trab.codigo=? AND res.codigo=?";
	
	private static final String UPDATE_DELETE_TRABAJADOR = 
			"DELETE trab FROM trabajador trab "
				+ "JOIN residencia res ON trab.id_residencia=res.id_residencia "
			+ "WHERE trab.codigo=? AND res.codigo=?";
	
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
