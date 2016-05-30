package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "residencia")
@JsonRootName(value = "residencia")
public class ResidenciaBean extends ETLTBean {
	private static final long serialVersionUID = 74L;
	private String codigo;
	private String nombre;
	private String descripcion;
	private String ciudad;
	private String municipioCod;
	private MunicipioBean municipio;
	
	public ResidenciaBean() {
		super(ResidenciaBean.class);
	}

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

	public MunicipioBean getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioBean municipio) {
		this.municipio = municipio;
	}

}
