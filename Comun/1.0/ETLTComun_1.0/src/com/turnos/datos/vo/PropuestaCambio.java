package com.turnos.datos.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "propuesta")
@JsonRootName(value = "propuesta")
public abstract class PropuestaCambio extends ETLTBean {
	private long id_propuesta;
	private long id_proponente;
	private Date dia;
	private Date hora_propuesta;
	private Date hora_actualizada;
}
