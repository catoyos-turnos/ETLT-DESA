package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.FestivoBean;
import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.ResidenciaBean;

public class Municipio implements Serializable {
	private static final long serialVersionUID = 47L;
	private MunicipioBean bean;
	
	private Municipio(MunicipioBean beanOriginal) {
		this.bean = beanOriginal;
	}

	/* * ** ** * */
	public static List<Municipio> listaMunicipios(String codPais, String codProvincia, int limite, int offset, Sesion sesion) {
		List<MunicipioBean> listBeans = ClienteREST.municipioListaMunicipios(codPais, codProvincia, limite, offset, sesion);
		List<Municipio> list = new LinkedList<Municipio>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (MunicipioBean bean : listBeans) {
				list.add(Municipio.genera(bean));
			}
		}
		return list;
	}
	
	public static Municipio getMunicipio(String codPais, String codProvincia, String codMunicipio, Sesion sesion) {
		MunicipioBean bean = ClienteREST.municipioGetMunicipio(codPais, codProvincia, codMunicipio, sesion);
		Municipio res = Municipio.genera(bean);
		return res;
	}

	public static List<Festivo> getFestivosMunicipio(String codPais, String codProvincia, String codMunicipio, Date time_ini, Date time_fin, boolean completo, int limite, int offset, Sesion sesion) {
		List<FestivoBean> listBeans = ClienteREST.municipioGetFestivosMunicipio(codPais, codProvincia, codMunicipio, time_ini, time_fin, completo, false, limite, offset, sesion);
		List<Festivo> list = new LinkedList<Festivo>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (FestivoBean bean : listBeans) {
				list.add(Festivo.genera(bean));
			}
		}
		return list;
	}
	public List<Festivo> getFestivos(Date time_ini, Date time_fin, boolean completo, int limite, int offset, Sesion sesion) {
		return Municipio.getFestivosMunicipio(bean.getPaisCod(), bean.getProvinciaCod(), bean.getMunicipioCod(), time_ini, time_fin, completo, limite, offset, sesion);
	}

	public static List<Residencia> getResidenciasMunicipio(String codPais, String codProvincia, String codMunicipio, int limite, int offset, Sesion sesion) {
		List<ResidenciaBean> listBeans = ClienteREST.municipioGetResidenciasMunicipio(codPais, codProvincia, codMunicipio, false, limite, offset, sesion);
		List<Residencia> list = new LinkedList<Residencia>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (ResidenciaBean bean : listBeans) {
				list.add(Residencia.genera(bean));
			}
		}
		return list;
	}
	public List<Residencia> getResidencias(int limite, int offset, Sesion sesion) {
		return Municipio.getResidenciasMunicipio(bean.getPaisCod(), bean.getProvinciaCod(), bean.getMunicipioCod(), limite, offset, sesion);
	}

	public String getMunicipioCod() {
		return bean.getMunicipioCod();
	}

	public String getMunicipioNombre() {
		return bean.getMunicipioNombre();
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

	public static Municipio genera(MunicipioBean bean) {
		if (bean == null) return null;
		else return new Municipio(bean);
	}

}
