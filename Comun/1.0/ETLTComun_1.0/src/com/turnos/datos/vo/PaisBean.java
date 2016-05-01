package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "pais")
@JsonRootName(value = "pais")
public class PaisBean {
	private String paisCod;
	private String paisNombre;
	private String tz_estandar;

	public String getPaisCod() {
		return paisCod;
	}

	public void setPaisCod(String paisCod) {
		this.paisCod = paisCod;
	}

	public String getPaisNombre() {
		return paisNombre;
	}

	public void setPaisNombre(String paisNombre) {
		this.paisNombre = paisNombre;
	}

	public String getTz_estandar() {
		return tz_estandar;
	}

	public void setTz_estandar(String tz_estandar) {
		this.tz_estandar = tz_estandar;
	}

}
