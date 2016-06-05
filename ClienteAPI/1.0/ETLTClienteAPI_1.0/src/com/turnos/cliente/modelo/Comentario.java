package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.Date;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.ComentarioBean;

public class Comentario implements Serializable {
	private static final long serialVersionUID = 47L;
	private ComentarioBean beanOriginal;
	private ComentarioBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;

	private Comentario(ComentarioBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new ComentarioBean();
	}

	public static boolean borraComentario(long arg1, long arg2, Sesion sesion) {
		boolean res = ClienteREST.comentarioBorraComentario(arg1, arg2, sesion);
		return res;
	}

	public static Comentario nuevoComentario(ComentarioBean arg0, long arg2, Sesion sesion) {
		ComentarioBean bean = ClienteREST.comentarioNuevoComentario(arg0, arg2, sesion);
		Comentario res = Comentario.genera(bean);
		return res;
	}

	public long getId_comentario() {
		if (flagBorrada)
			return -1;
		else if (flagNueva)
			return -1;
		else
			return beanOriginal.getId_comentario();
	}

	public long getId_prop_cambio() {
		if (flagBorrada)
			return -1;
		else if (flagNueva)
			return -1;
		else
			return beanOriginal.getId_prop_cambio();
	}

	public long getId_usuario() {
		if (flagBorrada)
			return -1;
		else if (flagNueva)
			return -1;
		else
			return beanOriginal.getId_usuario();
	}

	public String getNombreUsuario() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getNombreUsuario();
	}

	public Date getHora() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getHora();
	}

	public String getTexto() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return beanOriginal.getTexto();
		else if (flagModificada && beanAux.getTexto() != null)
			return beanAux.getTexto();
		else
			return beanOriginal.getTexto();
	}

	public void graba(Sesion sesion) {
		Comentario aux = null;
		if (flagNueva)
			aux = Comentario.nuevoComentario(beanOriginal, beanOriginal.getId_prop_cambio(), sesion);
		else if (flagModificada);
		if (aux != null) {
			this.beanOriginal = aux.beanOriginal;
			this.beanAux = aux.beanAux;
		}
		flagNueva = false;
		flagModificada = false;
		flagBorrada = false;
	}

	public void borra(Sesion sesion) {
		boolean b = false;
		if (!flagBorrada && !flagNueva)
			b = Comentario.borraComentario(beanOriginal.getId_prop_cambio(), beanOriginal.getId_comentario(), sesion);// TODO  codigo
		else
			b = true;
		if (b) {
			this.beanOriginal = null;
			this.beanAux = null;
			flagNueva = false;
			flagModificada = false;
			flagBorrada = true;
		}
	}

	public static Comentario genera(ComentarioBean bean) {
		if (bean == null)
			return null;
		else
			return new Comentario(bean, false);
	}

	public static Comentario nuevo() {
		return new Comentario(new ComentarioBean(), true);
	}

}
