package com.turnos.datos.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "comentario")
@JsonRootName(value = "comentario")
public abstract class PropuestaCambioBean extends ETLTBean {
	private long id_comentario = -1;
	private long id_prop_cambio = -1;
	private long id_usuario = -1;
	private Date hora;
	private String texto;
}