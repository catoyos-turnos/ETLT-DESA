package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "sesion")
@JsonRootName(value = "sesion")
public class SesionBean extends ETLTBean {
	private static final long serialVersionUID = 74L;
	private String tokenSesion;
	private long abierto;
	private long tokenCaduca;
	private UsuarioBean usuario;

	public SesionBean() {
		super(SesionBean.class);
	}

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

	public long getTokenCaduca() {
		return tokenCaduca;
	}

	public void setTokenCaduca(long tokenCaduca) {
		this.tokenCaduca = tokenCaduca;
	}

	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}


    public static Class<?> getBeanClass() {
    	return SesionBean.class;
    }
}
