package com.turnos.cliente.modelo;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.PropuestaCambioBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;
public class Trabajador implements Serializable {
	private static final long serialVersionUID = 47L;
	private TrabajadorBean beanOriginal;
	private TrabajadorBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;

	private Trabajador(TrabajadorBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new TrabajadorBean();
	}

	public static List<Trabajador> listaTrabajadores(String arg0, int arg1, int arg2, Sesion sesion) {
		List<TrabajadorBean> listBeans = ClienteREST.trabajadorListaTrabajadores(arg0, arg1, arg2, sesion);
		List<Trabajador> list = new LinkedList<Trabajador>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (TrabajadorBean bean : listBeans) {
				list.add(Trabajador.genera(bean));
			}
		}
		return list;
	}

	public static Trabajador getTrabajador(String arg0, String arg1, Sesion sesion) {
		TrabajadorBean bean = ClienteREST.trabajadorGetTrabajador(arg0, arg1, sesion);
		Trabajador res = Trabajador.genera(bean);
		return res;
	}

	public static Trabajador nuevoTrabajador(TrabajadorBean arg0, String arg1, Sesion sesion) {
		TrabajadorBean bean = ClienteREST.trabajadorNuevoTrabajador(arg0, arg1, sesion);
		Trabajador res = Trabajador.genera(bean);
		return res;
	}

	public static Trabajador modTrabajador(TrabajadorBean arg0, String arg1, String arg2, Sesion sesion) {
		TrabajadorBean bean = ClienteREST.trabajadorModTrabajador(arg0, arg1, arg2, sesion);
		Trabajador res = Trabajador.genera(bean);
		return res;
	}

	public static boolean borraTrabajador(String arg0, String arg1, Sesion sesion) {
		boolean res = ClienteREST.trabajadorBorraTrabajador(arg0, arg1, sesion);
		return res;
	}

	public static TurnoTrabajadorDia getHorarioTrabajadorDia(String arg0, String arg1, int arg2, Sesion sesion) {
		TurnoTrabajadorDiaBean bean = ClienteREST.trabajadorGetHorarioTrabajadorDia(arg0, arg1, arg2, sesion);
		TurnoTrabajadorDia res = TurnoTrabajadorDia.genera(bean);
		return res;
	}
	public TurnoTrabajadorDia getHorarioTrabajadorDia(int arg2, Sesion sesion) {
		return Trabajador.getHorarioTrabajadorDia(beanOriginal.getCodResidencia(), beanOriginal.getCodigo(), arg2, sesion);
	}

	public static List<TurnoTrabajadorDia> getHorarioTrabajadorRango(String arg0, String arg1, int arg2, int arg3, Sesion sesion) {
		List<TurnoTrabajadorDiaBean> listBeans = ClienteREST.trabajadorGetHorarioTrabajadorRango(arg0, arg1, arg2, arg3, sesion);
		List<TurnoTrabajadorDia> list = new LinkedList<TurnoTrabajadorDia>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (TurnoTrabajadorDiaBean bean : listBeans) {
				list.add(TurnoTrabajadorDia.genera(bean));
			}
		}
		return list;
	}
	public List<TurnoTrabajadorDia> getHorarioTrabajadorRango(int arg2, int arg3, Sesion sesion) {
		return Trabajador.getHorarioTrabajadorRango(beanOriginal.getCodResidencia(), beanOriginal.getCodigo(), arg2, arg3, sesion);
	}

	public static int getNumPropuestasCambio(String arg0, String arg1, Sesion sesion) {
		int res = ClienteREST.trabajadorGetNumPropuestasCambio(arg0, arg1, sesion);
		return res;
	}
	public int getNumPropuestasCambio(Sesion sesion) {
		return Trabajador.getNumPropuestasCambio(beanOriginal.getCodResidencia(), beanOriginal.getCodigo(), sesion);
	}

	public static List<PropuestaCambio> getPropuestasCambio(String arg0, String arg1, Sesion sesion) {
		List<PropuestaCambioBean> listBeans = ClienteREST.trabajadorGetPropuestasCambio(arg0, arg1, sesion);
		List<PropuestaCambio> list = new LinkedList<PropuestaCambio>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (PropuestaCambioBean bean : listBeans) {
				list.add(PropuestaCambio.genera(bean));
			}
		}
		return list;
	}
	public List<PropuestaCambio> getPropuestasCambio(Sesion sesion) {
		return Trabajador.getPropuestasCambio(beanOriginal.getCodResidencia(), beanOriginal.getCodigo(), sesion);
	}


	public String getCodigo() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getCodigo();
		else if(flagModificada && beanAux.getCodigo()!=null)
			return beanAux.getCodigo();
		else return beanOriginal.getCodigo();
	}
	public void setCodigo(String codigo) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setCodigo(codigo);
		else {
			flagModificada = true;
			beanAux.setCodigo(codigo);
		}
	}

	public String getCodResidencia() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getCodResidencia();
		else if(flagModificada && beanAux.getCodResidencia()!=null)
			return beanAux.getCodResidencia();
		else return beanOriginal.getCodResidencia();
	}
	public void setCodResidencia(String codResidencia) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setCodResidencia(codResidencia);
		else {
			flagModificada = true;
			beanAux.setCodResidencia(codResidencia);
		}
	}

	public String getNombre() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getNombre();
		else if(flagModificada && beanAux.getNombre()!=null)
			return beanAux.getNombre();
		else return beanOriginal.getNombre();
	}
	public void setNombre(String nombre) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setNombre(nombre);
		else {
			flagModificada = true;
			beanAux.setNombre(nombre);
		}
	}

	public String getApellidos() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getApellidos();
		else if(flagModificada && beanAux.getApellidos()!=null)
			return beanAux.getApellidos();
		else return beanOriginal.getApellidos();
	}
	public void setApellidos(String apellidos) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setApellidos(apellidos);
		else {
			flagModificada = true;
			beanAux.setApellidos(apellidos);
		}
	}

	public long getId_usuario() {
		if(flagBorrada)return -1;
		else if(flagNueva)return -1;
		else return beanOriginal.getId_usuario();
	}


	public void graba(Sesion sesion) {
		Trabajador aux = null;
		if(flagNueva)
			aux = Trabajador.nuevoTrabajador(beanOriginal, beanOriginal.getCodResidencia(), sesion);
		else if(flagModificada)
			aux = Trabajador.modTrabajador(beanAux, beanOriginal.getCodResidencia(), beanOriginal.getCodigo(), sesion);
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
		if(!flagBorrada && !flagNueva)
			b = Trabajador.borraTrabajador(beanOriginal.getCodResidencia(), beanOriginal.getCodigo(), sesion);
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
	
	public static Trabajador genera(TrabajadorBean bean) {
		if (bean == null) return null;
		else return new Trabajador(bean, false);
	}

	public static Trabajador nuevo() {
		return new Trabajador(new TrabajadorBean(), true);
	}

}
