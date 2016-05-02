package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "residencia")
@JsonRootName(value = "residencia")
@JsonInclude(Include.NON_NULL)
public class ResidenciaBean {
	private String codigo;
	private String nombre;
	private String descripcion;
	private String ciudad;
	private String municipioCod;
	private String municipioNombre;
	private String provinciaCod;
	private String provinciaNombre;
	private String paisCod;
	private String paisNombre;
	private String tz;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getMunicipioCod() {
		return municipioCod;
	}

	public void setMunicipioCod(String municipioCod) {
		this.municipioCod = municipioCod;
	}

	public String getMunicipioNombre() {
		return municipioNombre;
	}

	public void setMunicipioNombre(String municipioNombre) {
		this.municipioNombre = municipioNombre;
	}

	public String getProvinciaCod() {
		return provinciaCod;
	}

	public void setProvinciaCod(String provinciaCod) {
		this.provinciaCod = provinciaCod;
	}

	public String getProvinciaNombre() {
		return provinciaNombre;
	}

	public void setProvinciaNombre(String provinciaNombre) {
		this.provinciaNombre = provinciaNombre;
	}

	public String getPaisCod() {
		return paisCod;
	}

	public void setPaisCod(String paisCod) {
		this.paisCod = paisCod;
	}

	public String getPaisNombre() {
		return paisNombre;
	}

	public void setPaisNombre(String paisNombre) {
		this.paisNombre = paisNombre;
	}

	public String getTZ() {
		return tz;
	}

	public void setTZ(String tz) {
		this.tz = tz;
	}

	@Override
	public String toString() {
		return "ResidenciaBean [codigo=" + codigo + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", ciudad=" + ciudad
				+ ", municipioCod=" + municipioCod + ", municipioNombre="
				+ municipioNombre + ", provinciaCod=" + provinciaCod
				+ ", provinciaNombre=" + provinciaNombre + ", paisCod="
				+ paisCod + ", paisNombre=" + paisNombre + ", tz=" + tz + "]";
	}

}
