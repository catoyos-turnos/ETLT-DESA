package com.turnos.cliente.modelo;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.ProvinciaBean;
import com.turnos.datos.vo.ResidenciaBean;
public class Provincia implements Serializable {
	private static final long serialVersionUID = 47L;
	private ProvinciaBean bean;

	private Provincia(ProvinciaBean beanOriginal) {
		this.bean = beanOriginal;
	}

	public static List<Provincia> listaProvincias(String codPais, int limite, int offset, Sesion sesion) {
		List<ProvinciaBean> listBeans = ClienteREST.provinciaListaProvincias(codPais, limite, offset, sesion);
		List<Provincia> list = new LinkedList<Provincia>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (ProvinciaBean bean : listBeans) {
				list.add(Provincia.genera(bean));
			}
		}
		return list;
	}
	
	public static Provincia getProvincia(String codPais, String codProvincia, Sesion sesion) {
		ProvinciaBean bean = ClienteREST.provinciaGetProvincia(codPais, codProvincia, sesion);
		Provincia res = Provincia.genera(bean);
		return res;
	}
	
	public static List<Festivo> getFestivosProvincia(String codPais, String codProvincia, Date time_ini, Date time_fin, boolean completo, int limite, int offset, Sesion sesion) {
		List<FestivoBean> listBeans = ClienteREST.provinciaGetFestivosProvincia(codPais, codProvincia, time_ini, time_fin, completo, false, limite, offset, sesion);
		List<Festivo> list = new LinkedList<Festivo>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (FestivoBean bean : listBeans) {
				list.add(Festivo.genera(bean));
			}
		}
		return list;
	}
	public List<Festivo> getFestivos(Date time_ini, Date time_fin, boolean completo, int limite, int offset, Sesion sesion) {
		return Provincia.getFestivosProvincia(bean.getPaisCod(), bean.getProvinciaCod(), time_ini, time_fin, completo, limite, offset, sesion);
	}

	public static List<Residencia> getResidenciasProvincia(String codPais, String codProvincia, int limite, int offset, Sesion sesion) {
		List<ResidenciaBean> listBeans = ClienteREST.provinciaGetResidenciasProvincia(codPais, codProvincia, false, limite, offset, sesion);
		List<Residencia> list = new LinkedList<Residencia>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (ResidenciaBean bean : listBeans) {
				list.add(Residencia.genera(bean));
			}
		}
		return list;
	}
	public List<Residencia> getResidencias(int limite, int offset, Sesion sesion) {
		return Provincia.getResidenciasProvincia(bean.getPaisCod(), bean.getProvinciaCod(), limite, offset, sesion);
	}

	public String getProvinciaCod() {
		return bean.getProvinciaCod();
	}

	public String getProvinciaNombre() {
		return bean.getProvinciaNombre();
	}

	public String getPaisCod() {
		return bean.getPaisCod();
	}

	public String getPaisNombre() {
		return bean.getPaisNombre();
	}

	public String getTz() {
		return bean.getTz();
	}

	public static Provincia genera(ProvinciaBean bean) {
		if (bean == null) return null;
		else return new Provincia(bean);
	}

}
