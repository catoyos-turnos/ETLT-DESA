package com.turnos.cliente.modelo;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.ServicioTipoDiaBean;
public class Servicio implements Serializable {
	private static final long serialVersionUID = 47L;
	private ServicioBean beanOriginal;
	private ServicioBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;

	private Servicio(ServicioBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new ServicioBean();
	}

	public static boolean borraServicio(String codRes, String codTurno, long idServ, Sesion sesion) {
		boolean res = ClienteREST.servicioBorraServicio(codRes, codTurno, idServ, sesion);
		return res;
	}

	public static Servicio getServicio(String codRes, String codTurno, long idServ, Sesion sesion) {
		ServicioBean bean = ClienteREST.servicioGetServicio(codRes, codTurno, idServ, sesion);
		Servicio res = Servicio.genera(bean);
		return res;
	}

	public static List<Servicio> listaServicios(String codRes, String codTurno, int limite, int offset, Sesion sesion) {
		List<ServicioBean> listBeans = ClienteREST.servicioListaServicios(codRes, codTurno, limite, offset, sesion);
		List<Servicio> list = new LinkedList<Servicio>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (ServicioBean bean : listBeans) {
				list.add(Servicio.genera(bean));
			}
		}
		return list;
	}

	public static Servicio modServicio(ServicioBean rawBean, String codRes, String codTurno, long idServ, Sesion sesion) {
		ServicioBean bean = ClienteREST.servicioModServicio(rawBean, codRes, codTurno, idServ, sesion);
		Servicio res = Servicio.genera(bean);
		return res;
	}

	public static Servicio nuevoServicio(ServicioBean rawBean, String codRes, String codTurno, Sesion sesion) {
		ServicioBean bean = ClienteREST.servicioNuevoServicio(rawBean, codRes, codTurno, sesion);
		Servicio res = Servicio.genera(bean);
		return res;
	}


	public long getId_servicio() {
		if(flagBorrada)return -1;
		else if(flagNueva)return -1;
		else return beanOriginal.getId_servicio();
	}

	public String getCodTurno() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getCodTurno();
		else if(flagModificada && beanAux.getCodTurno()!=null)
			return beanAux.getCodTurno();
		else return beanOriginal.getCodTurno();
	}

	public String getCodRes() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getCodRes();
	}

	public String getHora_pres() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getHora_pres();
		else if(flagModificada && beanAux.getHora_pres()!=null)
			return beanAux.getHora_pres();
		else return beanOriginal.getHora_pres();
	}
	public void setHora_pres(String hora_pres) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setHora_pres(hora_pres);
		else {
			flagModificada = true;
			beanAux.setHora_pres(hora_pres);
		}
	}

	public String getHora_ret() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getHora_ret();
		else if(flagModificada && beanAux.getHora_ret()!=null)
			return beanAux.getHora_ret();
		else return beanOriginal.getHora_ret();
	}
	public void setHora_ret(String hora_ret) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setHora_ret(hora_ret);
		else {
			flagModificada = true;
			beanAux.setHora_ret(hora_ret);
		}
	}

	public int getTiempo_toma() {
		if(flagBorrada)return -1;
		else if(flagNueva)return beanOriginal.getTiempo_toma();
		else if(flagModificada && beanAux.getTiempo_toma()>0)
			return beanAux.getTiempo_toma();
		else return beanOriginal.getTiempo_toma();
	}
	public void setTiempo_toma(int tiempo_toma) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setTiempo_toma(tiempo_toma);
		else {
			flagModificada = true;
			beanAux.setTiempo_toma(tiempo_toma);
		}
	}

	public int getTiempo_deje() {
		if(flagBorrada)return -1;
		else if(flagNueva)return beanOriginal.getTiempo_deje();
		else if(flagModificada && beanAux.getTiempo_deje()>0)
			return beanAux.getTiempo_deje();
		else return beanOriginal.getTiempo_deje();
	}
	public void setTiempo_deje(int tiempo_deje) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setTiempo_deje(tiempo_deje);
		else {
			flagModificada = true;
			beanAux.setTiempo_deje(tiempo_deje);
		}
	}

	public int getMargen_antes() {
		if(flagBorrada)return -1;
		else if(flagNueva)return beanOriginal.getMargen_antes();
		else if(flagModificada && beanAux.getMargen_antes()>0)
			return beanAux.getMargen_antes();
		else return beanOriginal.getMargen_antes();
	}
	public void setMargen_antes(int margen_antes) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setMargen_antes(margen_antes);
		else {
			flagModificada = true;
			beanAux.setMargen_antes(margen_antes);
		}
	}

	public int getMargen_despues() {
		if(flagBorrada)return -1;
		else if(flagNueva)return beanOriginal.getMargen_despues();
		else if(flagModificada && beanAux.getMargen_despues()>0)
			return beanAux.getMargen_despues();
		else return beanOriginal.getMargen_despues();
	}
	public void setMargen_despues(int margen_despues) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setMargen_despues(margen_despues);
		else {
			flagModificada = true;
			beanAux.setMargen_despues(margen_despues);
		}
	}

	public String getDescripcion() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getDescripcion();
		else if(flagModificada && beanAux.getDescripcion()!=null)
			return beanAux.getDescripcion();
		else return beanOriginal.getDescripcion();
	}
	public void setDescripcion(String descripcion) {
		if(flagBorrada);
		else if(flagNueva)beanOriginal.setDescripcion(descripcion);
		else {
			flagModificada = true;
			beanAux.setDescripcion(descripcion);
		}
	}
	
	public List<ServicioTipoDia> getTiposDia () {
		if(beanOriginal.getTiposDia() == null) {
			return null;
		} else {
			List<ServicioTipoDia> res = new LinkedList<>();
			for (ServicioTipoDiaBean sb : beanOriginal.getTiposDia()) {
				res.add(ServicioTipoDia.genera(sb));
			}
			return res;
		}
	}


	public void graba(Sesion sesion) {
		Servicio aux = null;
		if(flagNueva)
			aux = Servicio.nuevoServicio(beanOriginal, beanOriginal.getCodRes(), beanOriginal.getCodTurno(), sesion);
		else if(flagModificada)
			aux = Servicio.modServicio(beanAux, beanOriginal.getCodRes(), beanOriginal.getCodTurno(), beanOriginal.getId_servicio(), sesion);//TODO  codigo
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
			b = Servicio.borraServicio(beanOriginal.getCodRes(), beanOriginal.getCodTurno(), beanOriginal.getId_servicio(), sesion);
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
	
	public static Servicio genera(ServicioBean bean) {
		if (bean == null) return null;
		else return new Servicio(bean, false);
	}

	public static Servicio nuevo() {
		return new Servicio(new ServicioBean(), true);
	}

}
