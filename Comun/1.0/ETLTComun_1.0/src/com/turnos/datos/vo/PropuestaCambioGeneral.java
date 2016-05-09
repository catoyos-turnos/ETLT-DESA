package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.turnos.datos.vo.PropuestaCambioSimple.EstadoPCS;

@XmlRootElement(name = "propuesta_general")
@JsonRootName(value = "propuesta_general")
public class PropuestaCambioGeneral extends PropuestaCambio {
	public static enum EstadoPCG {
		PROPUESTO,ACEPTADO,ANULADO,CADUCADO;
		public static EstadoPCG safeValueOf(String arg) {
			try{return valueOf(arg);}
			catch(Exception e){return null;}
		}
	};

	private long id_residencia;
	private EstadoPCG estado;
	private String descripcion;
	
}
