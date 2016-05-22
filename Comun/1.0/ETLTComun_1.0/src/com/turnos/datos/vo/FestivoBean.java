package com.turnos.datos.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "festivo")
@JsonRootName(value = "festivo")
public class FestivoBean extends ETLTBean {
	public enum TipoFiesta {
		NACIONAL, AUTONOMICA, LOCAL;
		public static TipoFiesta safeValueOf(String arg) {
			try {
				return valueOf(arg);
			} catch (Exception e) {
				return null;
			}
		}
	};

	private long codigo = -1;
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

	public FestivoBean() {
		super(FestivoBean.class);
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
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

	@ApiModelProperty(allowableValues = "NACIONAL, AUTONOMICA, LOCAL")
	public String getTipo() {
		if (tipo == null)
			return null;
		return tipo.name();
	}

	@ApiModelProperty(allowableValues = "NACIONAL, AUTONOMICA, LOCAL")
	public void setTipo(String tipo) {
		this.tipo = TipoFiesta.safeValueOf(tipo);
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

}
