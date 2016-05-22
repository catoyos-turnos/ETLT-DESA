package com.turnos.datos.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "usuario")
@JsonRootName(value = "usuario")
public class UsuarioBean extends ETLTBean {

	public enum NivelUsuario {
		USUARIO, ADMIN, SUPERADMIN, BANEADO;
		public static NivelUsuario safeValueOf(String arg) {
			try {
				return valueOf(arg);
			} catch (Exception e) {
				return null;
			}
		}
	};

	private long idUsuario = -1;
	private String user;
	private String nombre;
	private String codTrab;
	private TrabajadorBean trabajador;
	private String codRes;
	private ResidenciaBean residencia;
	private NivelUsuario nivel = NivelUsuario.USUARIO;
	private boolean activado = false;
	
	public UsuarioBean() {
		super(UsuarioBean.class);
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodTrab() {
		return codTrab;
	}

	public void setCodTrab(String codTrab) {
		this.codTrab = codTrab;
	}

	public TrabajadorBean getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(TrabajadorBean trabajador) {
		this.trabajador = trabajador;
	}

	public String getCodRes() {
		return codRes;
	}

	public void setCodRes(String codRes) {
		this.codRes = codRes;
	}

	public ResidenciaBean getResidencia() {
		return residencia;
	}

	public void setResidencia(ResidenciaBean residencia) {
		this.residencia = residencia;
	}

	@ApiModelProperty(allowableValues = "USUARIO,ADMIN,SUPERADMIN,BANEADO")
	public String getNivel() {
		if (nivel == null)
			return null;
		return nivel.name();
	}

	@ApiModelProperty(allowableValues = "USUARIO,ADMIN,SUPERADMIN,BANEADO")
	public void setNivel(String nivel) {
		this.nivel = NivelUsuario.safeValueOf(nivel);
	}

	public boolean checkNivel(NivelUsuario nivel) {
		return this.nivel == nivel;
	}

	public boolean checkNivel(String nivelStr) {
		return this.nivel == NivelUsuario.safeValueOf(nivelStr);
	}

	public boolean isActivado() {
		return activado;
	}

	public void setActivado(boolean activado) {
		this.activado = activado;
	}

}
