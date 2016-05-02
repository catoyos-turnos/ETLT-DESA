package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "turno")
@JsonRootName(value = "turno")
@JsonInclude(Include.NON_NULL)
public class TurnoBean {

	public enum TipoTurno {
		ORDINARIO, RESERVA, DESCANSO;
		public static TipoTurno safeValueOf(String arg) {
			try{return valueOf(arg);}
			catch(Exception e){return null;}
		}
	}

	private String codTurno;
	private String codResidencia;
	private TipoTurno tipo;

	public String getCodTurno() {
		return codTurno;
	}

	public void setCodTurno(String codTurno) {
		this.codTurno = codTurno;
	}

	public String getCodResidencia() {
		return codResidencia;
	}

	public void setCodResidencia(String codResidencia) {
		this.codResidencia = codResidencia;
	}

	public String getTipo() {
		if (tipo == null) return null;
		return tipo.name();
	}

	public void setTipo(String tipo) {
		this.tipo = TipoTurno.safeValueOf(tipo);
	}

}
