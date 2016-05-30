package com.turnos.datos.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "vacaciones")
@JsonRootName(value = "vacaciones")
public class VacacionesBean extends ETLTBean {
	private static final long serialVersionUID = 74L;
	private long id_vacaciones = -1;
	private String codTrab;
	private String codRes;
	private Date fechaInicio;
	private Date fechaFin;
	
	public VacacionesBean() {
		super(VacacionesBean.class);
	}
	
	public long getId_vacaciones() {
		return id_vacaciones;
	}

	public void setId_vacaciones(long id_vacaciones) {
		this.id_vacaciones = id_vacaciones;
	}

	public String getCodTrab() {
		return codTrab;
	}

	public void setCodTrab(String codTrab) {
		this.codTrab = codTrab;
	}

	public String getCodRes() {
		return codRes;
	}

	public void setCodRes(String codRes) {
		this.codRes = codRes;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
