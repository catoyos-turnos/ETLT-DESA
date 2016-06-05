package com.turnos.cliente.modelo;
import java.io.Serializable;

import com.turnos.datos.vo.ServicioTipoDiaBean;
import com.turnos.datos.vo.ServicioTipoDiaBean.SelectorTipoDia;
public class ServicioTipoDia implements Serializable {
	private static final long serialVersionUID = 47L;
	private ServicioTipoDiaBean beanOriginal;
	private ServicioTipoDiaBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;

	private ServicioTipoDia(ServicioTipoDiaBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new ServicioTipoDiaBean();
	}


	public String getDia() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getDia();
		else if(flagModificada && beanAux.getDia()!=null)
			return beanAux.getDia();
		else return beanOriginal.getDia();
	}

	public SelectorTipoDia getFestivo() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getFestivo();
		else if(flagModificada && beanAux.getFestivo()!=null)
			return beanAux.getFestivo();
		else return beanOriginal.getFestivo();
	}
	
	public SelectorTipoDia getVispera() {
		if(flagBorrada)return null;
		else if(flagNueva)return beanOriginal.getVispera();
		else if(flagModificada && beanAux.getVispera()!=null)
			return beanAux.getVispera();
		else return beanOriginal.getVispera();
	}
	
	public static ServicioTipoDia genera(ServicioTipoDiaBean bean) {
		if (bean == null) return null;
		else return new ServicioTipoDia(bean, false);
	}

	public static ServicioTipoDia nuevo() {
		return new ServicioTipoDia(new ServicioTipoDiaBean(), true);
	}

}
