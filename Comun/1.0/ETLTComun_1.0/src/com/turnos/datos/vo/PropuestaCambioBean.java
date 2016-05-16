package com.turnos.datos.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "propuestaCambio")
@JsonRootName(value = "propuestaCambio")
public abstract class PropuestaCambioBean extends ETLTBean {
	public static enum EstadoPCS {
		PROPUESTO,ACEPTADO,RECHAZADO,CONFIRMADO,ANULADO,CADUCADO;
		public static EstadoPCS safeValueOf(String arg) {
			try{return valueOf(arg);}
			catch(Exception e){return null;}
		}
	};
	
	private long id_propuesta = -1;
	private long id_trab_proponente = -1;
	private long id_trab_propuesto = -1;
	private Date dia;
	private EstadoPCS estado;
	private String mensaje;
	private int numComentarios = -1;
	private Date hora_propuesta;
	private Date hora_actualizada;
}
