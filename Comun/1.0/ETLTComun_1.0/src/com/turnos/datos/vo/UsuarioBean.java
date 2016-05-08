package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "usuario")
@JsonRootName(value = "usuario")
public class UsuarioBean extends ETLTBean {
	private long idUsuario = -1;
	private String user;
	private TrabajadorBean trabajador;
	private ResidenciaBean residencia;
	private boolean activado;

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public TrabajadorBean getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(TrabajadorBean trabajador) {
		this.trabajador = trabajador;
	}

	public ResidenciaBean getResidencia() {
		return residencia;
	}

	public void setResidencia(ResidenciaBean residencia) {
		this.residencia = residencia;
	}

	public boolean isActivado() {
		return activado;
	}

	public void setActivado(boolean activado) {
		this.activado = activado;
	}

}
