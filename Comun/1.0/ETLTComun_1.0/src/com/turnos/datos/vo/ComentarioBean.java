package com.turnos.datos.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "comentario")
@JsonRootName(value = "comentario")
public abstract class ComentarioBean extends ETLTBean {
	private long id_comentario = -1;
	private long id_prop_cambio = -1;
	private long id_usuario = -1;
	private String nombreUsuario;
	private Date hora;
	private String texto;

	public long getId_comentario() {
		return id_comentario;
	}

	public void setId_comentario(long id_comentario) {
		this.id_comentario = id_comentario;
	}

	public long getId_prop_cambio() {
		return id_prop_cambio;
	}

	public void setId_prop_cambio(long id_prop_cambio) {
		this.id_prop_cambio = id_prop_cambio;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}