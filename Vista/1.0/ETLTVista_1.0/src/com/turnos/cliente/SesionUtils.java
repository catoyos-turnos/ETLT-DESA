package com.turnos.cliente;

import com.turnos.cliente.conexion.Aplicacion;

public class SesionUtils {

	public static Aplicacion  getAplicacion() {
		return Aplicacion.generaApp(Propiedades.getString("Aplicacion.baseURL"),
				Propiedades.getString("Aplicacion.publicKey"),
				Propiedades.getString("Aplicacion.secretKey")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
