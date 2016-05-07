package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "provincia")
@JsonRootName(value = "provincia")
public class ProvinciaBean extends ETLTBean {

	private String provinciaCod;
	private String provinciaNombre;
	private String paisCod;
	private String paisNombre;
	private String tz;

	public String getProvinciaCod() {
		return provinciaCod;
	}

	public void setProvinciaCod(String provinciaCod) {
		this.provinciaCod = provinciaCod;
	}

	public String getProvinciaNombre() {
		return provinciaNombre;
	}

	public void setProvinciaNombre(String provinciaNombre) {
		this.provinciaNombre = provinciaNombre;
	}

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

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

}
