package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "serv_tipo_dia")
@JsonRootName(value = "serv_tipo_dia")
public class ServicioTipoDiaBean extends ETLTBean {
	private static final long serialVersionUID = 74L;
	public enum SelectorTipoDia {
		SI, NO, CUALQUIERA;
		public static SelectorTipoDia safeValueOf(String arg) {
			try {
				return valueOf(arg);
			} catch (Exception e) {
				return null;
			}
		}
	};

	private String dia;
	private SelectorTipoDia festivo;
	private SelectorTipoDia vispera;
	
	public ServicioTipoDiaBean() {
		super(ServicioTipoDiaBean.class);
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public SelectorTipoDia getFestivo() {
		return festivo;
	}

	public void setFestivo(SelectorTipoDia festivo) {
		this.festivo = festivo;
	}

	public SelectorTipoDia getVispera() {
		return vispera;
	}

	public void setVispera(SelectorTipoDia vispera) {
		this.vispera = vispera;
	}

}
