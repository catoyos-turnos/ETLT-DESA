package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "turno")
@JsonRootName(value = "turno")
public class TurnoBean {

	public enum TipoTurno {
		ORDINARIO, RESERVA, DESCANSO
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
		return tipo.toString();
	}

	public void setTipo(String tipo) {
		this.tipo = TipoTurno.valueOf(tipo);
	}

}
