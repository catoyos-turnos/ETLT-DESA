package com.turnos.cliente.modelo;

import java.util.List;

import com.turnos.datos.vo.TrabajadorBean;

public class Trabajador {

	public static List<Trabajador> listaTrabajadores (String codRes) {
		//TODO
		return null;
	}

	public static Trabajador getTrabajador (String codRes, String codTrab) {
		//TODO
		return null;
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

	public TurnoTrabajadorDia getHorarioTrabajadorDia(String codRes, String codTrab, int time) {
		//TODO
		return null;
	}

	public List<TurnoTrabajadorDia> getHorarioTrabajadorRango(String codRes, String codTrab, int time_ini, int time_fin) {
		//TODO
		return null;
	}

}