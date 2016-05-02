package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "servicio")
@JsonRootName(value = "servicio")
@JsonInclude(Include.NON_NULL)
public class ServicioBean {
	private int id_servicio;
	private String hora_pres;
	private String hora_ret;
	private int tiempo_toma;
	private int tiempo_deje;
	private int margen_antes;
	private int margen_despues;
	private String descripcion;


	public void setId_servicio(int id_servicio) {
		this.id_servicio = id_servicio;
	}

	public int getId_servicio() {
		return id_servicio;
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

}
