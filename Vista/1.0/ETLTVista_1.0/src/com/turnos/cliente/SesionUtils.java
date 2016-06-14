package com.turnos.cliente;

import javax.servlet.http.HttpServletRequest;

import com.turnos.cliente.conexion.Aplicacion;
import com.turnos.cliente.conexion.Sesion;

public class SesionUtils {

	public static Aplicacion  getAplicacion() {
		return Aplicacion.generaApp(Propiedades.getString("Aplicacion.baseURL"),
				Propiedades.getString("Aplicacion.publicKey"),
				Propiedades.getString("Aplicacion.secretKey")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	public static Sesion getSesionFromRequest(HttpServletRequest request) {
		Object sAux = request.getSession().getAttribute("SesionAPI");
		Sesion sesionAPI = null;
		if(sAux != null && sAux instanceof Sesion) {
			sesionAPI = (Sesion) sAux;
			if(!sesionAPI.isActiva()) {
				sesionAPI.refresca();
			}
		}
		return sesionAPI;
	}
}
