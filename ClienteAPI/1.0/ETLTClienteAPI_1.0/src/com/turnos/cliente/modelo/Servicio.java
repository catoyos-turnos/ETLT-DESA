package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.List;

import com.turnos.datos.vo.ServicioBean;


public class Servicio implements Serializable {
	private static final long serialVersionUID = 47L;

	public static List<Servicio> listaTurnos (String codRes, String codTurno) {
		//TODO
		return null;
	}

	public static Servicio getTurno (String codRes, String codTurno, String codServ) {
		//TODO
		return null;
	}

	public static Servicio nuevoTurno(ServicioBean servicioRaw, String codRes, String codTurno) {
		//TODO
		return null;
	}

	public static Servicio modTurno(ServicioBean servicioRaw, String codRes, String codTurno, String codServ) {
		//TODO
		return null;
	}

	public static Servicio borraTurno(String codRes, String codTurno, String codServ) {
		//TODO
		return null;
	}

}
