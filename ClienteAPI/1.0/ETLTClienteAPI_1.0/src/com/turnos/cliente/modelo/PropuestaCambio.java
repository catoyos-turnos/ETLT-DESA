package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.ComentarioBean;
import com.turnos.datos.vo.PropuestaCambioBean;
import com.turnos.datos.vo.PropuestaCambioBean.EstadoPCS;
import com.turnos.datos.vo.TrabajadorBean;

public class PropuestaCambio implements Serializable {
	private static final long serialVersionUID = 47L;
	private PropuestaCambioBean beanOriginal;
	private PropuestaCambioBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;

	private PropuestaCambio(PropuestaCambioBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new PropuestaCambioBean();
	}
	
	public static boolean borraPropuestaCambio(long id_cambio, Sesion sesion) {
		boolean res = ClienteREST.propuestacambioBorraPropuestaCambio(id_cambio, sesion);
		return res;
	}

	public static PropuestaCambio getPropuestaCambio(long id_cambio, Sesion sesion) {
		PropuestaCambioBean bean = ClienteREST.propuestacambioGetPropuestaCambio(id_cambio, true, sesion);
		PropuestaCambio res = PropuestaCambio.genera(bean);
		return res;
	}
	
	public static List<PropuestaCambio> listaPropuestaCambio(EstadoPCS estado, Sesion sesion) {
		List<PropuestaCambioBean> listBeans = ClienteREST.propuestacambioListaPropuestaCambios(estado==null?null:estado.name(), sesion);
		List<PropuestaCambio> list = new LinkedList<PropuestaCambio>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (PropuestaCambioBean bean : listBeans) {
				list.add(PropuestaCambio.genera(bean));
			}
		}
		return list;
	}

	public static PropuestaCambio modPropuestaCambio(PropuestaCambioBean rawBean, long id_cambio, Sesion sesion) {
		PropuestaCambioBean bean = ClienteREST.propuestacambioModPropuestaCambio(rawBean, id_cambio, sesion);
		PropuestaCambio res = PropuestaCambio.genera(bean);
		return res;
	}

	public static PropuestaCambio nuevoPropuestaCambio(PropuestaCambioBean rawBean, Sesion sesion) {
		PropuestaCambioBean bean = ClienteREST.propuestacambioNuevoPropuestaCambio(rawBean, sesion);
		PropuestaCambio res = PropuestaCambio.genera(bean);
		return res;
	}

	public long getId_propuesta() {
		if (flagBorrada)
			return -1;
		else if (flagNueva)
			return -1;
		else
			return beanOriginal.getId_propuesta();
	}

	public String getCodTrabProponente() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getCodTrabProponente();
	}

	public Trabajador getTrabajadorProponente() {
		TrabajadorBean mb = null;
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			mb = beanOriginal.getTrabajadorProponente();
		return Trabajador.genera(mb);
	}

	public String getCodTrabPropuesto() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getCodTrabPropuesto();
	}

	public Trabajador getTrabajadorPropuesto() {
		TrabajadorBean mb = null;
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			mb = beanOriginal.getTrabajadorPropuesto();
		return Trabajador.genera(mb);
	}

	public String getCodResidencia() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getCodResidencia();
	}

	public Date getDia() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getDia();
	}

	public EstadoPCS getEstado() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return EstadoPCS.safeValueOf(beanOriginal.getEstado());
	}

	public String getMensaje() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getMensaje();
	}

	public int getNumComentarios() {
		if (flagBorrada)
			return -1;
		else if (flagNueva)
			return -1;
		else
			return beanOriginal.getNumComentarios();
	}
	
	public List<Comentario> getComentarios () {
		List<Comentario> res = new LinkedList<>();
		if(beanOriginal.getComentarios() == null)
			return res;
		
		for (ComentarioBean sb : beanOriginal.getComentarios()) {
			res.add(Comentario.genera(sb));
		}
		return res;
	}

	public Date getHora_propuesta() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getHora_propuesta();
	}

	public Date getHora_actualizada() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return null;
		else
			return beanOriginal.getHora_actualizada();
	}

	public void graba(Sesion sesion) {
		PropuestaCambio aux = null;
		if (flagNueva)
			aux = PropuestaCambio.nuevoPropuestaCambio(beanOriginal, sesion);
		else if (flagModificada)
			aux = PropuestaCambio.modPropuestaCambio(beanAux, beanOriginal.getId_propuesta(), sesion);// TODO codigo
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
			b = PropuestaCambio.borraPropuestaCambio(beanOriginal.getId_propuesta(), sesion);// TODO codigo
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

	public static PropuestaCambio genera(PropuestaCambioBean bean) {
		if (bean == null)
			return null;
		else
			return new PropuestaCambio(bean, false);
	}

	public static PropuestaCambio nuevo() {
		return new PropuestaCambio(new PropuestaCambioBean(), true);
	}

}
