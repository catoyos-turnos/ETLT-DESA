package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.TrabajadorBean;

public class Trabajador implements Serializable {
	private static final long serialVersionUID = 47L;
	
	private TrabajadorBean beanOriginal;
	private TrabajadorBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;
	
	public Trabajador(TrabajadorBean bean, boolean nueva) {
		this.beanOriginal = bean;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new TrabajadorBean();
	}

	public static List<Trabajador> listaTrabajadores (String codRes) {
		return null;
//		List<TrabajadorBean> listBeans = ClienteREST.trabajadorListaTrabajadores(pais, provincia, municipio, false, limite, offset, sesion);
//		List<Trabajador> list = new LinkedList<Trabajador>();
//		if (listBeans != null && !listBeans.isEmpty()) {
//			for (TrabajadorBean trabBean : listBeans) {
//				list.add(Trabajador.genera(trabBean));
//			}
//		}
//		return list;
	}

	public static Trabajador getTrabajador (String codRes, String codTrab, Sesion sesion) {
		TrabajadorBean bean = ClienteREST.trabajadorGetTrabajador(codRes, codTrab, sesion);
		Trabajador res = Trabajador.genera(bean);
		
		return res;
	}

	public static Trabajador nuevoTrabajador(TrabajadorBean trabRaw, String codRes) {
		//TODO
		return null;
	}

	public static Trabajador modTrabajador(TrabajadorBean trabRaw, String codRes, String codTrab) {
		//TODO
		return null;
	}

	public static Trabajador borraTrabajador (String codRes, String codTrab) {
		//TODO
		return null;
	}
/*
	public TurnoTrabajadorDia getHorarioTrabajadorDia(String codRes, String codTrab, int time) {
		//TODO
		return null;
	}

	public List<TurnoTrabajadorDia> getHorarioTrabajadorRango(String codRes, String codTrab, int time_ini, int time_fin) {
		//TODO
		return null;
	}
*/
	public static Trabajador genera(TrabajadorBean bean) {
		if (bean == null) {
			return null;
		} else return new Trabajador(bean, false);
	}

	public static Trabajador nuevo() {
		return new Trabajador(new TrabajadorBean(), true);
	}

}
