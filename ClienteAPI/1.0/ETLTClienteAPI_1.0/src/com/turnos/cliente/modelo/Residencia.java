package com.turnos.cliente.modelo;

import java.util.List;

import com.turnos.datos.vo.ResidenciaBean;

public class Residencia {
	private ResidenciaBean residencia;
	
	public Residencia(ResidenciaBean residencia) {
		this.residencia = residencia;
	}
	
	public static List<Residencia> listaResidencias() {
		//TODO
		return null;
	}

	public static Residencia getResidencia(String codRes) {
		//TODO
		return null;
	}

	public static Residencia nuevaResidencia(ResidenciaBean resRaw) {
		//TODO
		return null;
	}

	public static Residencia modResidencia(ResidenciaBean resRaw, String codRes) {
		//TODO
		return null;
	}

	public static Residencia borraResidencia(String codRes) {
		//TODO
		return null;
	}

	public static List<DiaFestivo> getDiasFestivos(String codRes, int time_ini, int time_fin,  int limit) {
		//TODO
		return null;
	}

	public static List<TurnoTrabajadorDia> getHorarioCompletoDia(String codRes, int time) {
		//TODO
		return null;
	}

	public String getCodigo() {
		return residencia.getCodigo();
	}

	public void setCodigo(String codigo) {
		residencia.setCodigo(codigo);
	}

	public String getNombre() {
		return residencia.getNombre();
	}

	public void setNombre(String nombre) {
		residencia.setNombre(nombre);
	}

	public String getDescripcion() {
		return residencia.getDescripcion();
	}

	public void setDescripcion(String descripcion) {
		residencia.setDescripcion(descripcion);
	}

	public String getCiudad() {
		return residencia.getCiudad();
	}

	public void setCiudad(String ciudad) {
		residencia.setCiudad(ciudad);
	}

	public String getMunicipioCod() {
		return residencia.getMunicipioCod();
	}

	public void setMunicipioCod(String municipioCod) {
		residencia.setMunicipioCod(municipioCod);
	}

	public String getMunicipioNombre() {
		return residencia.getMunicipioNombre();
	}

	public String getProvinciaCod() {
		return residencia.getProvinciaCod();
	}

	public String getProvinciaNombre() {
		return residencia.getProvinciaNombre();
	}

	public String getPaisCod() {
		return residencia.getPaisCod();
	}

	public String getPaisNombre() {
		return residencia.getPaisNombre();
	}

	public String getTZ() {
		return residencia.getTZ();
	}

	
}
