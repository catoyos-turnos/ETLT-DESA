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
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;

public class Residencia implements Serializable {
	private static final long serialVersionUID = 47L;
	
	private ResidenciaBean beanOriginal;
	private ResidenciaBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;
	
	private Residencia(ResidenciaBean beanOriginal, boolean nueva) {
		this.beanOriginal = beanOriginal;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new ResidenciaBean();
	}
	
	public static List<Residencia> listaResidencias(String pais, String provincia, String municipio, int limite, int offset, Sesion sesion) {
		List<ResidenciaBean> listBeans = ClienteREST.residenciaListaResidencias(pais, provincia, municipio, false, limite, offset, sesion);
		List<Residencia> list = new LinkedList<Residencia>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (ResidenciaBean bean : listBeans) {
				list.add(Residencia.genera(bean));
			}
		}
		return list;
	}

	public static Residencia getResidencia(String codRes, Sesion sesion) {
		ResidenciaBean bean = ClienteREST.residenciaGetResidencia(codRes, true, sesion);
		Residencia res = Residencia.genera(bean);
		
		return res;
	}

	public static Residencia nuevoResidencia(ResidenciaBean rawBean, Sesion sesion) {
		ResidenciaBean bean = ClienteREST.residenciaNuevaResidencia(rawBean, sesion);
		Residencia res = Residencia.genera(bean);
		return res;
	}

	public static Residencia modResidencia(ResidenciaBean rawBean, String codRes, Sesion sesion) {
		ResidenciaBean bean = ClienteREST.residenciaModResidencia(rawBean, codRes, sesion);
		Residencia res = Residencia.genera(bean);
		return res;
	}

	public static boolean borraResidencia(String codRes, Sesion sesion) {
		boolean res = ClienteREST.residenciaBorraResidencia(codRes, sesion);
		return res;
	}

	public static List<Festivo> getDiasFestivos(String codRes, Date time_ini, Date time_fin, int limit, int offset, Sesion sesion) {
		List<FestivoBean> listBeans = ClienteREST.residenciaGetDiasFestivos(codRes, time_ini, time_fin, limit, offset, sesion);
		List<Festivo> list = new LinkedList<Festivo>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (FestivoBean bean : listBeans) {
				list.add(Festivo.genera(bean));
			}
		}
		return list;
	}


	public List<Festivo> getDiasFestivos(Date time_ini, Date time_fin, int limit, int offset, Sesion sesion) {
		return Residencia.getDiasFestivos(this.getCodigo(), time_ini, time_fin, limit, offset, sesion);
	}

	public static List<TurnoTrabajadorDia> getHorarioCompletoDia(String codRes, Date time, int limit, int offset, Sesion sesion) {
		List<TurnoTrabajadorDiaBean> listBeans = ClienteREST.residenciaGetHorarioCompletoDia(codRes, time, limit, offset, sesion);
		List<TurnoTrabajadorDia> list = new LinkedList<TurnoTrabajadorDia>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (TurnoTrabajadorDiaBean bean : listBeans) {
				list.add(TurnoTrabajadorDia.genera(bean));
			}
		}
		return list;
	}
	
	public List<TurnoTrabajadorDia> getHorarioCompletoDia(Date time, int limit, int offset, Sesion sesion) {
		return Residencia.getHorarioCompletoDia(this.getCodigo(), time, limit, offset, sesion);
	}
	
	public String getCodigo() {
		if (flagBorrada)
			return null;
		else if (flagNueva)
			return beanOriginal.getCodigo();
		else if (flagModificada && beanAux.getCodigo() != null)
			return beanAux.getCodigo();
		else
			return beanOriginal.getCodigo();
	}

	public String getNombre() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getNombre();
		else if(flagModificada && beanAux.getNombre()!=null)
			return beanAux.getNombre();
		else return beanOriginal.getNombre();
	}


	public void setNombre(String nombre) {
		if(flagBorrada)
			;
		else if(flagNueva)
			beanOriginal.setNombre(nombre);
		else {
			flagModificada = true;
			beanAux.setNombre(nombre);
		}
	}

	public String getDescripcion() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getDescripcion();
		else if(flagModificada && beanAux.getDescripcion()!=null)
			return beanAux.getDescripcion();
		else return beanOriginal.getDescripcion();
	}

	public void setDescripcion(String descripcion) {
		if(flagBorrada)
			;
		else if(flagNueva)
			beanOriginal.setDescripcion(descripcion);
		else {
			flagModificada = true;
			beanAux.setDescripcion(descripcion);
		}
	}

	public String getCiudad() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getCiudad();
		else if(flagModificada && beanAux.getCiudad()!=null)
			return beanAux.getCiudad();
		else return beanOriginal.getCiudad();
	}

	public void setCiudad(String ciudad) {
		if(flagBorrada)
			;
		else if(flagNueva)
			beanOriginal.setCiudad(ciudad);
		else {
			flagModificada = true;
			beanAux.setCiudad(ciudad);
		}
	}

	public String getMunicipioCod() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getMunicipioCod();
		else if(flagModificada && beanAux.getMunicipioCod()!=null)
			return beanAux.getMunicipioCod();
		else return beanOriginal.getMunicipioCod();
	}

	public void setMunicipioCod(String municipioCod) {
		if(flagBorrada)
			;
		else if(flagNueva)
			beanOriginal.setMunicipioCod( municipioCod);
		else {
			flagModificada = true;
			beanAux.setMunicipioCod( municipioCod);
		}
	}

	public Municipio getMunicipio() {
		MunicipioBean mb = null;
		if(flagBorrada)
			return null;
		else if(flagNueva)
			mb = beanOriginal.getMunicipio();
		else if(flagModificada && beanAux.getMunicipio()!=null)
			mb = beanAux.getMunicipio();
		else mb = beanOriginal.getMunicipio();
		
		return Municipio.genera(mb);
	}

	public String getMunicipioNombre() {
		Municipio m = getMunicipio();
		return m==null ? null : m.getMunicipioNombre();
	}

	public String getProvinciaCod() {
		Municipio m = getMunicipio();
		return m==null ? null : m.getProvinciaCod();
	}

	public String getProvinciaNombre() {
		Municipio m = getMunicipio();
		return m==null ? null : m.getProvinciaNombre();
	}

	public String getPaisCod() {
		Municipio m = getMunicipio();
		return m==null ? null : m.getPaisCod();
	}

	public String getPaisNombre() {
		Municipio m = getMunicipio();
		return m==null ? null : m.getPaisNombre();
	}

	public String getTz() {
		Municipio m = getMunicipio();
		return m==null ? null : m.getTz();
	}


	public void graba(Sesion sesion) {
		Residencia aux = null;
		if(flagNueva)
			aux = Residencia.nuevoResidencia(beanOriginal, sesion);
		else if(flagModificada)
			aux = Residencia.modResidencia(beanAux, beanOriginal.getCodigo(), sesion);
		
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
			b = Residencia.borraResidencia(beanOriginal.getCodigo(), sesion);//TODO  codigo
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

	public static Residencia genera(ResidenciaBean bean) {
		if (bean == null) {
			return null;
		} else return new Residencia(bean, false);
	}

	public static Residencia nuevo() {
		return new Residencia(new ResidenciaBean(), true);
	}

	
}
