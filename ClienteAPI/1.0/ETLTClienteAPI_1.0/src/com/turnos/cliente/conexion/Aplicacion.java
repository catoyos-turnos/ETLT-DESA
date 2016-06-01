package com.turnos.cliente.conexion;


public class Aplicacion {

	public final String baseURL;
	public final String publicKey;
	public final String secretKey;

	public Aplicacion(String baseURL, String publicKey, String secretKey) {
		this.baseURL = baseURL;
		this.publicKey = publicKey;
		this.secretKey = secretKey;
	}

	public static Aplicacion generaApp(String baseURL, String publicKey, String secretKey) {
		return new Aplicacion(baseURL, publicKey, secretKey);
	}
}
