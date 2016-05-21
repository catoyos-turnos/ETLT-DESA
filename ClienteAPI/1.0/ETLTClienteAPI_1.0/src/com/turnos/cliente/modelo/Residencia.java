package com.turnos.cliente.modelo;

import java.util.List;

import com.turnos.datos.vo.ResidenciaBean;

public class Residencia {
	private ResidenciaBean residencia;
	private ResidenciaBean residenciaAux;
	private boolean flagNueva;
	private boolean flagModificada;
	
	protected Residencia(ResidenciaBean residencia, boolean nueva) {
		this.residencia = residencia;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.residenciaAux = new ResidenciaBean();
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
		//TODO cambia municipio (null?)
	}

	//TODO public Municipio getMunicipio() {}
	
	public String getMunicipioNombre() {
		if (residencia.getMunicipio() == null) {
			return null;
		}
		return residencia.getMunicipio().getMunicipioNombre();
	}

	public String getProvinciaCod() {
		if (residencia.getMunicipio() == null) {
			return null;
		}
		return residencia.getMunicipio().getProvinciaCod();
	}

	public String getProvinciaNombre() {
		if (residencia.getMunicipio() == null) {
			return null;
		}
		return residencia.getMunicipio().getProvinciaNombre();
	}

	public String getPaisCod() {
		if (residencia.getMunicipio() == null) {
			return null;
		}
		return residencia.getMunicipio().getPaisCod();
	}

	public String getPaisNombre() {
		if (residencia.getMunicipio() == null) {
			return null;
		}
		return residencia.getMunicipio().getPaisNombre();
	}

	public String getTZ() {
		if (residencia.getMunicipio() == null) {
			return null;
		}
		return residencia.getMunicipio().getTz();
	}


	public void graba() {
		//TODO
	}

	public void borra() {
		//TODO
	}

	public List<DiaFestivo> getDiasFestivos(int time_ini, int time_fin, int limit) {
		//TODO
		return null;
	}

	public List<TurnoTrabajadorDia> getHorarioCompletoDia(int time) {
		//TODO
		return null;
	}

	public static Residencia genera(ResidenciaBean residencia2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
