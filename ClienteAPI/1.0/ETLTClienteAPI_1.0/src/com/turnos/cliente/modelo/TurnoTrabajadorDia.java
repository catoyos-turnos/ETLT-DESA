package com.turnos.cliente.modelo;
import java.io.Serializable;
import java.util.Date;

import com.turnos.datos.vo.TurnoTrabajadorDiaBean;
public class TurnoTrabajadorDia implements Serializable {
	private static final long serialVersionUID = 47L;
	private TurnoTrabajadorDiaBean bean;

	private TurnoTrabajadorDia(TurnoTrabajadorDiaBean beanOriginal) {
		this.bean = beanOriginal;
	}

	public Trabajador getTrabajador() {
		return Trabajador.genera(bean.getTrabajador());
	}

	public Servicio getServicio() {
		return Servicio.genera(bean.getServicio());
	}

	public Turno getTurno() {
		return Turno.genera(bean.getTurno());
	}

	public Date getFecha() {
		return bean.getFecha();
	}
	
	public static TurnoTrabajadorDia genera(TurnoTrabajadorDiaBean bean) {
		if (bean == null) return null;
		else return new TurnoTrabajadorDia(bean);
	}

}
