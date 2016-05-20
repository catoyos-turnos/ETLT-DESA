package com.turnos.datos.handlers;

import java.sql.Connection;
import java.util.ArrayList;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoBean.TipoTurno;

public class TurnoHandler extends GenericHandler {

	private static final int LOC_H = 73;
	
	private static final String QUERY_LISTA_TURNOS_RES =
			"SELECT turno.codigo as codTurno, res.codigo as codRes, turno.tipo as tipo "
			+ "FROM turno JOIN residencia res ON turno.id_residencia=res.id_residencia "
			+ "WHERE res.codigo=?";
	
	private static final String QUERY_GET_TURNO_COD =
			"SELECT turno.codigo as codTurno, res.codigo as codRes, turno.tipo as tipo "
			+ "FROM turno JOIN residencia res ON turno.id_residencia=res.id_residencia "
			+ "WHERE res.codigo=? AND turno.codigo=?";

	private static final String UPDATE_INSERT_NUEVO_TURNO =
			"INSERT INTO turno(codigo, id_residencia, tipo) "
			+ "SELECT ?, res.id_residencia, ? FROM residencia res WHERE res.codigo=?";

	private static final String UPDATE_UPDATE_TURNO =
			"UPDATE turno "
				+ "JOIN residencia res ON turno.id_residencia=res.id_residencia "
			+ "SET %s WHERE turno.codigo=? AND res.codigo=?";

	private static final String UPDATE_DELETE_TURNO =
			"DELETE turno FROM turno "
				+ "JOIN residencia res ON turno.id_residencia=res.id_residencia "
			+ "WHERE turno.codigo=? AND res.codigo=?";


	public static boolean existeTurno(Connection conexion, String codRes,
			String codTurno, ErrorBean errorBean) {
		int LOC_M = 1;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		if (codTurno != null && !"".equals(codTurno)) {
			return false;
		} else {
			try {
				// TODO Auto-generated method stub
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		}
		return false;
	}

	public static ArrayList<TurnoBean> listTodosTurnos(Connection conexion,
			String codRes, boolean includeServs, ErrorBean errorBean) {
		int LOC_M = 2;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<TurnoBean> listaTurnos = new ArrayList<TurnoBean>();
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return listaTurnos;
	}

	public static ArrayList<TurnoBean> listTurnosTipo(Connection conexion,
			String codRes, TipoTurno tipo, boolean includeServs,
			ErrorBean errorBean) {
		int LOC_M = 3;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<TurnoBean> listaTurnos = new ArrayList<TurnoBean>();
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return listaTurnos;
	}

	public static TurnoBean getTurno(Connection conexion, String codRes,
			String codTurno, boolean includeServs, ErrorBean errorBean) {
		int LOC_M = 4;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		TurnoBean turno = null;
		if (codTurno != null && !"".equals(codTurno)) {
			try {
				// TODO Auto-generated method stub
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		}
		return turno;
	}

	public static TurnoBean insertTurno(Connection conexion, String codRes,
			TurnoBean turnoRaw, ErrorBean errorBean) {
		int LOC_M = 5;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		TurnoBean turno = null;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return turno;
	}

	public static TurnoBean updateTurno(Connection conexion, String codRes,
			String codTurno, TurnoBean turnoRaw,  ErrorBean errorBean) {
		int LOC_M = 6;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		TurnoBean turno = null;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return turno;
	}

	public static boolean deleteTurno(Connection conexion, String codRes,
			String codTurno, ErrorBean errorBean) {
		int LOC_M = 7;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		boolean res = false;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return res;
	}
}
