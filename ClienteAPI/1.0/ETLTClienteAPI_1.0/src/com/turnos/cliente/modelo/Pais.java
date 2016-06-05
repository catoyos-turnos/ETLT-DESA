package com.turnos.cliente.modelo;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.PaisBean;
import com.turnos.datos.vo.ResidenciaBean;
public class Pais implements Serializable {
	private static final long serialVersionUID = 47L;
	private PaisBean bean;

	private Pais(PaisBean beanOriginal) {
		this.bean = beanOriginal;
	}

	public static List<Pais> listaPaises(int limite, int offset, Sesion sesion) {
		List<PaisBean> listBeans = ClienteREST.paisListaPaises(limite, offset, sesion);
		List<Pais> list = new LinkedList<Pais>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (PaisBean bean : listBeans) {
				list.add(Pais.genera(bean));
			}
		}
		return list;
	}

	public static Pais getPais(String codPais, Sesion sesion) {
		PaisBean bean = ClienteREST.paisGetPais(codPais, sesion);
		Pais res = Pais.genera(bean);
		return res;
	}

	public static List<Festivo> getFestivosPais(String codPais, Date time_ini, Date time_fin, int limite, int offset, Sesion sesion) {
		List<FestivoBean> listBeans = ClienteREST.paisGetFestivosPais(codPais, time_ini, time_fin, false, limite, offset, sesion);
		List<Festivo> list = new LinkedList<Festivo>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (FestivoBean bean : listBeans) {
				list.add(Festivo.genera(bean));
			}
		}
		return list;
	}
	public List<Festivo> getFestivos(Date time_ini, Date time_fin, int limite, int offset, Sesion sesion) {
		return Pais.getFestivosPais(bean.getPaisCod(), time_ini, time_fin, limite, offset, sesion);
	}

	public static List<Residencia> getResidenciasPais(String codPais, int limite, int offset, Sesion sesion) {
		List<ResidenciaBean> listBeans = ClienteREST.paisGetResidenciasPais(codPais, false, limite, offset, sesion);
		List<Residencia> list = new LinkedList<Residencia>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (ResidenciaBean bean : listBeans) {
				list.add(Residencia.genera(bean));
			}
		}
		return list;
	}
	public List<Residencia> getResidencias(int limite, int offset, Sesion sesion) {
		return Pais.getResidenciasPais(bean.getPaisCod(), limite, offset, sesion);
	}

	public String getPaisCod() {
		return bean.getPaisCod();
	}

	public String getPaisNombre() {
		return bean.getPaisNombre();
	}
	
	public static Pais genera(PaisBean bean) {
		if (bean == null) return null;
		else return new Pais(bean);
	}
}
