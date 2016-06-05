package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.FestivoBean.TipoFiesta;
public class Festivo implements Serializable {
	private static final long serialVersionUID = 47L;
	private FestivoBean beanOriginal;
	private FestivoBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;

	private Festivo(FestivoBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new FestivoBean();
	}

	public static boolean borraFestivo(long arg0, Sesion sesion) {
		boolean res = ClienteREST.festivoBorraDiaFestivo(arg0, sesion);
		return res;
	}

	public static Festivo getFestivo(long arg0, boolean arg1, Sesion sesion) {
		FestivoBean bean = ClienteREST.festivoGetDiaFestivo(arg0, arg1, sesion);
		Festivo res = Festivo.genera(bean);
		return res;
	}

	public static List<Festivo> listaFestivos(String codPais, String codProvincia, String codMunicipio,
			String tipoStr, Date time_ini, Date time_fin, boolean completo, boolean incGeo,
			int limite, int offset, Sesion sesion) {
		List<FestivoBean> listBeans = ClienteREST.festivoListaDiasFestivos(codPais, codProvincia, codMunicipio, tipoStr, time_ini, time_fin, completo, incGeo, limite, offset, sesion);
		List<Festivo> list = new LinkedList<Festivo>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (FestivoBean bean : listBeans) {
				list.add(Festivo.genera(bean));
			}
		}
		return list;
	}

	public static Festivo modFestivo(FestivoBean rawBean, long codFest, Sesion sesion) {
		FestivoBean bean = ClienteREST.festivoModDiaFestivo(rawBean, codFest, sesion);
		Festivo res = Festivo.genera(bean);
		return res;
	}

	public static Festivo nuevoFestivo(FestivoBean rawBean, Sesion sesion) {
		FestivoBean bean = ClienteREST.festivoNuevoDiaFestivo(rawBean, sesion);
		Festivo res = Festivo.genera(bean);
		return res;
	}


	public String getFiesta() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getFiesta();
	}

	public String getNotas() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getNotas();
	}

	public Date getFecha() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getFecha();
	}

	public TipoFiesta getTipo() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return TipoFiesta.safeValueOf(beanOriginal.getTipo());
	}

	public String getMunicipioCod() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getMunicipioCod();
	}

	public String getMunicipioNombre() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getMunicipioNombre();
	}

	public String getProvinciaCod() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getProvinciaCod();
	}

	public String getProvinciaNombre() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getProvinciaNombre();
	}

	public String getPaisCod() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getPaisCod();
	}

	public String getPaisNombre() {
		if(flagBorrada)return null;
		else if(flagNueva)return null;
		else return beanOriginal.getPaisNombre();
	}


	public void graba(Sesion sesion) {
		Festivo aux = null;
		if(flagNueva)
			aux = Festivo.nuevoFestivo(beanOriginal, sesion);
		else if(flagModificada)
			aux = Festivo.modFestivo(beanAux, beanOriginal.getCodigo(), sesion);//TODO  codigo
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
			b = Festivo.borraFestivo(beanOriginal.getCodigo(), sesion);//TODO  codigo
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
	
	public static Festivo genera(FestivoBean bean) {
		if (bean == null) return null;
		else return new Festivo(bean, false);
	}

	public static Festivo nuevo() {
		return new Festivo(new FestivoBean(), true);
	}

}
