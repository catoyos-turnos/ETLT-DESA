package com.turnos.datos.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "festivo")
@JsonRootName(value = "festivo")
public class FestivoBean {
	public enum TipoFiesta {
		NACIONAL, AUTONOMICA, LOCAL
	};

	private int codigo;
	private String fiesta;
	private String notas;
	private Date fecha;
	private TipoFiesta tipo;
	private String municipioCod;
	private String municipioNombre;
	private String provinciaCod;
	private String provinciaNombre;
	private String paisCod;
	private String paisNombre;
	private String tz;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getFiesta() {
		return fiesta;
	}

	public void setFiesta(String fiesta) {
		this.fiesta = fiesta;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo.toString();
	}

	public void setTipo(String tipo) {
		this.tipo = TipoFiesta.valueOf(tipo);
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

}
