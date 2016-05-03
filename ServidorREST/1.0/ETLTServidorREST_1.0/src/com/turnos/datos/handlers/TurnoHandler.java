package com.turnos.datos.handlers;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoBean.TipoTurno;

//73xxxx
public class TurnoHandler extends GenericHandler {

	//00xx
	public static boolean existeTurno(Connection conexion, String codRes, String codTurno, ErrorBean errorBean) {
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
	
	//01xx
	public static ArrayList<TurnoBean> listTurnos(Connection conexion, String codRes, TipoTurno tipo, ErrorBean errorBean) {
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
	
	//02xx
	public static TurnoBean getTurno(Connection conexion, String codRes, String codTurno, ErrorBean errorBean) {Connection nconexion = aseguraConexion(conexion);
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
	
	//03xx
	public static TurnoBean insertTurno(Connection conexion, String codRes, TurnoBean turnoRaw, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean aut = autenticar(nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57730300");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}
		
		TurnoBean turno = null;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return turno;
	}
	
	//04xx
	public static TurnoBean updateTurno(Connection conexion, String codRes, String codTurno, TurnoBean turnoRaw, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean aut = autenticar(nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57730400");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}
		
		TurnoBean turno = null;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return turno;
	}
	
	//05xx
	public static boolean deleteTurno(Connection conexion, String codRes, String codTurno, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		boolean aut = autenticar(nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57730500");
			errorBean.updateMsg("Sin autenticar");
			return false;
		}
		
		boolean res = false;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return res;
	}
}
