package com.turnos.datos.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "turno")
@JsonRootName(value = "turno")
public class TurnoBean extends ETLTBean {

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
	private List<ServicioBean> servicios;

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

	@ApiModelProperty(allowableValues = "ORDINARIO,RESERVA,DESCANSO")
	public String getTipo() {
		if (tipo == null) return null;
		return tipo.name();
	}

	@ApiModelProperty(allowableValues = "ORDINARIO,RESERVA,DESCANSO")
	public void setTipo(String tipo) {
		this.tipo = TipoTurno.safeValueOf(tipo);
	}


	public List<ServicioBean> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServicioBean> servicios) {
		this.servicios = servicios;
	}

}
