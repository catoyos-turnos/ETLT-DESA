package com.turnos.datos.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "propuesta_servicio")
@JsonRootName(value = "propuesta_servicio")
public class PropuestaCambioSimple extends PropuestaCambio {
	public static enum EstadoPCS {
		PROPUESTO,ACEPTADO,RECHAZADO,CONFIRMADO,ANULADO,CADUCADO;
		public static EstadoPCS safeValueOf(String arg) {
			try{return valueOf(arg);}
			catch(Exception e){return null;}
		}
	};

	private long id_propuesto;
	private EstadoPCS estado;
	private String mensaje;
	
	
}
