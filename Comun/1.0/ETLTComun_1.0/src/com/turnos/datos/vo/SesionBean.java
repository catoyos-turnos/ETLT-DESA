package com.turnos.datos.vo;

public class SesionBean {
	private String tokenSesion;
	private long abierto;
	private UsuarioBean usuario;

	public String getTokenSesion() {
		return tokenSesion;
	}

	public void setTokenSesion(String tokenSesion) {
		this.tokenSesion = tokenSesion;
	}

	public long getAbierto() {
		return abierto;
	}

	public void setAbierto(long abierto) {
		this.abierto = abierto;
	}

	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}

}
