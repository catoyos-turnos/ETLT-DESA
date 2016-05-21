package com.turnos.cliente.conexion;

import com.turnos.cliente.Propiedades;

public class Aplicacion {

	public final String baseURL;
	public final String publicKey;
	public final String secretKey;

	public Aplicacion(String baseURL, String publicKey, String secretKey) {
		this.baseURL = baseURL;
		this.publicKey = publicKey;
		this.secretKey = secretKey;
	}

	public static Aplicacion defaultApp() {
		String baseURL = Propiedades.getString("Aplicacion.baseURL"); //$NON-NLS-1$
		String publicKey = Propiedades.getString("Aplicacion.publicKey"); //$NON-NLS-1$
		String secretKey = Propiedades.getString("Aplicacion.secretKey"); //$NON-NLS-1$
		return generaApp(baseURL, publicKey, secretKey);
	}

	public static Aplicacion generaApp(String baseURL, String publicKey, String secretKey) {
		return new Aplicacion(baseURL, publicKey, secretKey);
	}
}
