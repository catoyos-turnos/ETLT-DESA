package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "usuario")
@JsonRootName(value = "usuario")
public class UsuarioBean extends ETLTBean {
	private long idUsuario = -1;
	private String user;
	private String codTrab;
	private TrabajadorBean trabajador;
	private String codRes; 
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

	public String getCodTrab() {
		return codTrab;
	}

	public void setCodTrab(String codTrab) {
		this.codTrab = codTrab;
	}

	public TrabajadorBean getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(TrabajadorBean trabajador) {
		this.trabajador = trabajador;
	}

	public String getCodRes() {
		return codRes;
	}

	public void setCodRes(String codRes) {
		this.codRes = codRes;
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
