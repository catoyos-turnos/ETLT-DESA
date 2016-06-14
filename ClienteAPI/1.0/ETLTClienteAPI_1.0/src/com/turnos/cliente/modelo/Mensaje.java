package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.MensajeBean;
import com.turnos.datos.vo.UsuarioBean;

public class Mensaje implements Serializable {
	private static final long serialVersionUID = 47L;
	private MensajeBean beanOriginal;
	private MensajeBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;

	private Mensaje(MensajeBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new MensajeBean();
	}

	public static Mensaje getMensaje(long arg0, long arg1, int arg2,
			boolean arg3, Sesion sesion) {
		MensajeBean bean = ClienteREST.mensajeGetMensaje(arg0, arg1, arg2,
				arg3, sesion);
		Mensaje res = Mensaje.genera(bean);
		return res;
	}

	public static int getNumMensajes(long codUser, boolean incLeidos, Sesion sesion) {
		int res = ClienteREST.mensajeGetNumMensajes(codUser, incLeidos, sesion);
		return res;
	}

	public static int getNumRespuestas(long arg0, long arg1, boolean arg2, Sesion sesion) {
		int res = ClienteREST.mensajeGetNumRespuestas(arg0, arg1, arg2, sesion);
		return res;
	}

	public int getNumRespuestas( long arg1, boolean arg2, Sesion sesion) {
		return Mensaje.getNumRespuestas(beanOriginal.getId_mensaje(), arg1, arg2, sesion);
	}

	public static List<Mensaje> listaMensajes(long arg0, String arg1,
			boolean arg2, boolean arg3, int arg4, int arg5, int arg6,
			Sesion sesion) {
		List<MensajeBean> listBeans = ClienteREST.mensajeListaMensajes(arg0,
				arg1, arg2, arg3, arg4, arg5, arg6, sesion);
		List<Mensaje> list = new LinkedList<Mensaje>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (MensajeBean bean : listBeans) {
				list.add(Mensaje.genera(bean));
			}
		}
		return list;
	}

	public static List<Mensaje> listaRespuestas(long arg0, long arg1,
			boolean arg2, boolean arg3, int arg4, int arg5, int arg6,
			Sesion sesion) {
		List<MensajeBean> listBeans = ClienteREST.mensajeListaRespuestas(arg0,
				arg1, arg2, arg3, arg4, arg5, arg6, sesion);
		List<Mensaje> list = new LinkedList<Mensaje>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (MensajeBean bean : listBeans) {
				list.add(Mensaje.genera(bean));
			}
		}
		return list;
	}

	public List<Mensaje> listaRespuestas(long arg1, boolean arg2,
			boolean arg3, int arg4, int arg5, int arg6, Sesion sesion) {
		return Mensaje.listaRespuestas(beanOriginal.getId_mensaje(), arg1, arg2, arg3, arg4, arg5,
				arg6, sesion);
	}

	public static Mensaje nuevoMensaje(MensajeBean arg0, long arg1, Sesion sesion) {
		MensajeBean bean = ClienteREST.mensajeNuevoMensaje(arg0, arg1, sesion);
		Mensaje res = Mensaje.genera(bean);
		return res;
	}
	
	public static boolean setMensajeLeido(long arg0, long arg1, boolean arg2, Sesion sesion) {
		boolean res = ClienteREST.mensajeSetMensajeLeido(arg0, arg1, arg2, sesion);
		return res;
	}

	public boolean setLeido(long arg1, boolean arg2, Sesion sesion) {
		return Mensaje.setMensajeLeido(beanOriginal.getId_mensaje(), arg1, arg2, sesion);
	}

	public static boolean setMensajeNoLeido(long arg0, long arg1, Sesion sesion) {
		return Mensaje.setMensajeLeido(arg0, arg1, false, sesion);
	}

	public boolean setNoLeido(long arg1, Sesion sesion) {
		return Mensaje.setMensajeNoLeido(beanOriginal.getId_mensaje(), arg1, sesion);
	}

	public static boolean setMensajeSiLeido(long arg0, long arg1, Sesion sesion) {
		return Mensaje.setMensajeLeido(arg0, arg1, true, sesion);
	}

	public boolean setSiLeido(long arg1, Sesion sesion) {
		return Mensaje.setMensajeSiLeido(beanOriginal.getId_mensaje(), arg1, sesion);
	}

	public long getId_mensaje() {
		if (flagBorrada)
			return -1;
		else if (flagNueva)
			return -1;
		else
			return beanOriginal.getId_mensaje();
	}

	public Usuario getRemitente() {
		UsuarioBean mb = null;
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			mb = beanOriginal.getRemitente();
		return Usuario.genera(mb);
	}

	public Usuario getDestinatario() {
		UsuarioBean mb = null;
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			mb = beanOriginal.getDestinatario();
		return Usuario.genera(mb);
	}

	public MensajeBean getMsgOriginal() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getMsgOriginal();
	}

	public int getNumRespuestas() {
		if (flagBorrada)
			return -1;
		else if (flagNueva)
			return -1;
		else
			return beanOriginal.getNumRespuestas();
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
			return null;
		else
			return beanOriginal.getTexto();
	}

	public boolean isLeido() {
		if (flagBorrada)
			return false;
		else if (flagNueva)
			return false;
		else
			return beanOriginal.isLeido();
	}

	public void graba(Sesion sesion) {
		Mensaje aux = null;
		if (flagNueva)
			aux = Mensaje.nuevoMensaje(beanOriginal, beanOriginal.getId_remitente(), sesion);
		else if (flagModificada);
		if (aux != null) {
			this.beanOriginal = aux.beanOriginal;
			this.beanAux = aux.beanAux;
		}
		flagNueva = false;
		flagModificada = false;
		flagBorrada = false;
	}

	public static Mensaje genera(MensajeBean bean) {
		if (bean == null)
			return null;
		else
			return new Mensaje(bean, false);
	}

	public static Mensaje nuevo() {
		return new Mensaje(new MensajeBean(), true);
	}

}
