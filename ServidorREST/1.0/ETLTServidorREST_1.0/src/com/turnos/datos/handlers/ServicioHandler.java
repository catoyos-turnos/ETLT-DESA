package com.turnos.datos.handlers;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.ServicioBean;

//74xxxx
public class ServicioHandler extends GenericHandler {
	// TODO
	private static final String QUERY_LISTA_SERVICIOS_TURNO = "";
	// TODO
	private static final String QUERY_GET_SERVICIO_COD = "";
	// TODO
	private static final String UPDATE_INSERT_NUEVO_SERVICIO = "";
	// TODO
	private static final String UPDATE_UPDATE_SERVICIO = "";
	// TODO
	private static final String UPDATE_DELETE_SERVICIO = "";
	
	//00xx
	public static boolean existeServicio(Connection conexion, int codServ, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		if(codServ >= 0) {
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
	public static ArrayList<ServicioBean> listServicios(Connection conexion, String codRes, String codTurno, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<ServicioBean> listaServs = new ArrayList<ServicioBean>();
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return listaServs;
	}
	
	//02xx
	public static ServicioBean getServicio(Connection conexion, int codServ, ErrorBean errorBean) {Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
	
		ServicioBean serv = null;
		if (codServ >= 0) {
			try {
				// TODO Auto-generated method stub
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		}
		return serv;
	}
	
	//03xx
	public static ServicioBean insertServicio(Connection conexion, ServicioBean servRaw, boolean aut, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57740300");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}
		
		ServicioBean serv = null;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return serv;
	}
	
	//04xx
	public static ServicioBean updateServicio(Connection conexion, int codServ, ServicioBean servRaw, boolean aut, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57740400");
			errorBean.updateMsg("Sin autenticar");
			return null;
		}
		
		ServicioBean serv = null;
		try {
			// TODO Auto-generated method stub
		} finally {
			terminaOperacion(nconexion, cierraConexion);
		}
		return serv;
	}
	
	//05xx
	public static boolean deleteServicio(Connection conexion, int codServ, boolean aut, ErrorBean errorBean) {
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		if(!aut) {
			errorBean.setHttpCode(Status.FORBIDDEN);
			errorBean.updateErrorCode("57740500");
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
