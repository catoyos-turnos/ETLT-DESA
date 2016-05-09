package com.turnos.datos.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "servicio")
@JsonRootName(value = "servicio")
public class ServicioBean extends ETLTBean {
	private long id_servicio = -1;
	private String codTurno;
	private String codRes;
	private String hora_pres;
	private String hora_ret;
	private int tiempo_toma;
	private int tiempo_deje;
	private int margen_antes;
	private int margen_despues;
	private String descripcion;
	private ArrayList<ServicioTipoDia> tiposDia;


	public void setId_servicio(long id_servicio) {
		this.id_servicio = id_servicio;
	}

	public long getId_servicio() {
		return id_servicio;
	}

	public String getCodTurno() {
		return codTurno;
	}

	public void setCodTurno(String codTurno) {
		this.codTurno = codTurno;
	}

	public String getCodRes() {
		return codRes;
	}

	public void setCodRes(String codRes) {
		this.codRes = codRes;
	}
	
	public String getHora_pres() {
		return hora_pres;
	}

	public void setHora_pres(String hora_pres) {
		this.hora_pres = hora_pres;
	}

	public String getHora_ret() {
		return hora_ret;
	}

	public void setHora_ret(String hora_ret) {
		this.hora_ret = hora_ret;
	}

	public int getTiempo_toma() {
		return tiempo_toma;
	}

	public void setTiempo_toma(int tiempo_toma) {
		this.tiempo_toma = tiempo_toma;
	}

	public int getTiempo_deje() {
		return tiempo_deje;
	}

	public void setTiempo_deje(int tiempo_deje) {
		this.tiempo_deje = tiempo_deje;
	}

	public int getMargen_antes() {
		return margen_antes;
	}

	public void setMargen_antes(int margen_antes) {
		this.margen_antes = margen_antes;
	}

	public int getMargen_despues() {
		return margen_despues;
	}

	public void setMargen_despues(int margen_despues) {
		this.margen_despues = margen_despues;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<ServicioTipoDia> getTiposDia() {
		return tiposDia;
	}

	public void setTiposDia(ArrayList<ServicioTipoDia> tiposDia) {
		this.tiposDia = tiposDia;
	}

}
