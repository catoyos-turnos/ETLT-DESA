package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoBean.TipoTurno;

public class Turno implements Serializable {
	private static final long serialVersionUID = 47L;
	private TurnoBean beanOriginal;
	private TurnoBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;
	
	private Turno(TurnoBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new TurnoBean();
	}
	
	public static boolean borraTurno(String codRes, String codTurno, Sesion sesion) {
		boolean res = ClienteREST.turnoBorraTurno(codRes, codTurno, sesion);
		return res;
	}

	public static Turno getTurno (String codRes, String codTurno, Sesion sesion) {
		TurnoBean bean = ClienteREST.turnoGetTurno(codRes, codTurno, true, sesion);
		Turno res = Turno.genera(bean);
		return res;
	}
	
	public static List<Turno> listaTurnos(String codRes, TipoTurno tipo, boolean inc_servs, Sesion sesion) {
		List<TurnoBean> listBeans = ClienteREST.turnoListaTurnos(codRes, tipo==null?null:tipo.name(), inc_servs, sesion);
		List<Turno> list = new LinkedList<Turno>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (TurnoBean bean : listBeans) {
				list.add(Turno.genera(bean));
			}
		}
		return list;
	}

	public static Turno modTurno(TurnoBean rawBean, String codRes, String codTurno, Sesion sesion) {
		TurnoBean bean = ClienteREST.turnoModTurno(rawBean, codRes, codTurno, sesion);
		Turno res = Turno.genera(bean);
		return res;
	}

	public static Turno nuevoTurno(TurnoBean arg0, String arg1, Sesion sesion) {
		TurnoBean bean = ClienteREST.turnoNuevoTurno(arg0, arg1, sesion);
		Turno res = Turno.genera(bean);
		return res;
	}
	
	public List<Servicio> getServicios (boolean force, Sesion sesion) {
		if(force || beanOriginal.getServicios() == null) {
			return Servicio.listaServicios(getCodResidencia(), getCodTurno(), -1, -1, sesion);
		} else {
			List<Servicio> res = new LinkedList<>();
			for (ServicioBean sb : beanOriginal.getServicios()) {
				res.add(Servicio.genera(sb));
			}
			return res;
		}
	}
	
	
	public String getCodTurno() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getCodTurno();
		else if(flagModificada && beanAux.getCodTurno()!=null)
			return beanAux.getCodTurno();
		else return beanOriginal.getCodTurno();
	}

	public String getCodResidencia() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getCodResidencia();
		else if(flagModificada && beanAux.getCodResidencia()!=null)
			return beanAux.getCodResidencia();
		else return beanOriginal.getCodResidencia();
	}

	public TipoTurno getTipo() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return TipoTurno.safeValueOf(beanOriginal.getTipo());
		else if(flagModificada && beanAux.getTipo()!=null)
			return TipoTurno.safeValueOf(beanAux.getTipo());
		else return TipoTurno.safeValueOf(beanOriginal.getTipo());
	}
	public void setTipo(TipoTurno tipo) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setTipo(tipo.name());
		else {
			flagModificada = true;
			beanAux.setTipo(tipo.name());
		}
	}


	public void graba(Sesion sesion) {
		Turno aux = null;
		if(flagNueva)
			aux = Turno.nuevoTurno(beanOriginal, beanOriginal.getCodResidencia(), sesion);
		else if(flagModificada)
			aux = Turno.modTurno(beanAux, beanOriginal.getCodResidencia(), beanOriginal.getCodTurno(), sesion);//TODO  codigo
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
			b = Turno.borraTurno(beanOriginal.getCodResidencia(), beanOriginal.getCodTurno(), sesion);//TODO  codigo
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


	public static Turno genera(TurnoBean bean) {
		if (bean == null) {
			return null;
		} else return new Turno(bean, false);
	}

	public static Turno nuevo() {
		return new Turno(new TurnoBean(), true);
	}

}
