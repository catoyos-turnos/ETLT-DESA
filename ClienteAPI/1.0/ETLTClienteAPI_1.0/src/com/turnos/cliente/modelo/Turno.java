package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.List;

import com.turnos.datos.vo.TurnoBean;

public class Turno implements Serializable {
	private static final long serialVersionUID = 47L;


	public static List<Turno> listaTurnos (String codRes) {
		//TODO
		return null;
	}

	public static Turno getTurno (String codRes, String codTurno) {
		//TODO
		return null;
	}

	public static Turno nuevoTurno(TurnoBean turnoRaw, String codRes) {
		// TODO
		return null;
	}

	public static Turno modTurno(TurnoBean turnoRaw, String codRes, String codTurno) {
		// TODO
		return null;
	}

	public static Turno borraTurno(String codRes, String codTurno) {
		//TODO
		return null;
	}

	public static List<Servicio> getServicios (String codRes, String codTurno) {
		//TODO
		return null;
	}
	
}
