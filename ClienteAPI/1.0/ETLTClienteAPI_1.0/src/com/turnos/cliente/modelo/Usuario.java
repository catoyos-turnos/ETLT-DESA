package com.turnos.cliente.modelo;

import java.io.Serializable;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.UsuarioBean;
import com.turnos.datos.vo.UsuarioBean.NivelUsuario;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 47L;

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

	public String getCodRes() {
		return bean.getCodRes();
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

	public Residencia getResidencia(boolean force, Sesion sesion) {
		ResidenciaBean beanB = null;
		if(bean != null) beanB = bean.getResidencia();
		if((sesion != null && sesion.isActiva()) && (force || beanB == null)) {
			if(bean != null && bean.getCodRes() != null) {
				return Residencia.getResidencia(bean.getCodRes(), sesion);
			} else {
				return null;
			}
		}
		return Residencia.genera(beanB);
	}

	public Trabajador getTrabajador(boolean force, Sesion sesion) {
		TrabajadorBean beanB = null;
		if(bean != null) beanB = bean.getTrabajador();
		if((sesion != null && sesion.isActiva()) && (force || beanB == null)) {
			if(bean != null && bean.getCodRes() != null && bean.getCodTrab() != null) {
				return Trabajador.getTrabajador(bean.getCodRes(), bean.getCodTrab(), sesion);
			} else {
				return null;
			}
		}
		return Trabajador.genera(beanB);
	}
	
	public Residencia getResidencia() {
		return this.getResidencia(false, null);
	}

	public Trabajador getTrabajador() {
		return this.getTrabajador(false, null);
	}

	public int[] getContadores() {
		return new int[3];
	}

	public static Usuario genera(UsuarioBean usuario) {
		if (usuario == null)
			return null;
		return new Usuario(usuario);
	}

}
