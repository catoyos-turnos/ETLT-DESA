package com.turnos.cliente.modelo;

import com.turnos.datos.vo.UsuarioBean;
import com.turnos.datos.vo.UsuarioBean.NivelUsuario;

public class Usuario {
	private UsuarioBean bean;

	private Usuario(UsuarioBean bean) {
		this.bean = bean;
	}

	public long getIdUsuario() {
		return bean.getIdUsuario();
	}

	public String getUser() {
		return bean.getUser();
	}

	public String getNombre() {
		return bean.getNombre();
	}

	public String getCodTrab() {
		return bean.getCodTrab();
	}

	public Trabajador getTrabajador() {
		return Trabajador.genera(bean.getTrabajador());
	}

	public String getCodRes() {
		return bean.getCodRes();
	}

	public Residencia getResidencia() {
		return Residencia.genera(bean.getResidencia());
	}

	public String getNivel() {
		return bean.getNivel();
	}

	public boolean checkNivel(NivelUsuario nivel) {
		return bean.checkNivel(nivel);
	}

	public boolean checkNivel(String nivelStr) {
		return bean.checkNivel(nivelStr);
	}

	public boolean isActivado() {
		return bean.isActivado();
	}

	public static Usuario genera(UsuarioBean usuario) {
		if (usuario == null) return null;
		return new Usuario(usuario);
	}

}
