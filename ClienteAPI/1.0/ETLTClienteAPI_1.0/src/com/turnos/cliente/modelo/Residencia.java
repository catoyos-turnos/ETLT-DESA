package com.turnos.cliente.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.turnos.cliente.conexion.ClienteREST;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.ResidenciaBean;

public class Residencia implements Serializable {
	private static final long serialVersionUID = 47L;
	
	private ResidenciaBean beanOriginal;
	private ResidenciaBean beanAux;
	private boolean flagNueva;
	private boolean flagModificada;
	private boolean flagBorrada;
	
	private Residencia(ResidenciaBean residencia, boolean nueva) {
		this.beanOriginal = residencia;
		this.flagNueva = nueva;
		this.flagModificada = false;
		this.flagBorrada = false;
		this.beanAux = new ResidenciaBean();
	}
	
	public static List<Residencia> listaResidencias(String pais, String provincia, String municipio, int limite, int offset, Sesion sesion) {
		List<ResidenciaBean> listBeans = ClienteREST.residenciaListaResidencias(pais, provincia, municipio, false, limite, offset, sesion);
		List<Residencia> list = new LinkedList<Residencia>();
		if (listBeans != null && !listBeans.isEmpty()) {
			for (ResidenciaBean residenciaBean : listBeans) {
				list.add(Residencia.genera(residenciaBean));
			}
		}
		return list;
	}

	public static Residencia getResidencia(String codRes, Sesion sesion) {
		ResidenciaBean bean = ClienteREST.residenciaGetResidencia(codRes, true, sesion);
		Residencia res = Residencia.genera(bean);
		
		return res;
	}

	public static Residencia nuevaResidencia(ResidenciaBean resRaw) {
		//TODO
		return null;
	}

	public static Residencia modResidencia(ResidenciaBean resRaw, String codRes) {
		//TODO
		return null;
	}

	public static boolean borraResidencia(String codRes) {
		//TODO
		return false;
	}

	public static List<DiaFestivo> getDiasFestivos(String codRes, int time_ini, int time_fin,  int limit) {
		//TODO
		return null;
	}

	public static List<TurnoTrabajadorDia> getHorarioCompletoDia(String codRes, int time) {
		//TODO
		return null;
	}
	
	public String getCodigo() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return null;
		else return beanOriginal.getCodigo();
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

	public String getDescripcion() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getDescripcion();
		else if(flagModificada && beanAux.getDescripcion()!=null)
			return beanAux.getDescripcion();
		else return beanOriginal.getDescripcion();
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

	public String getMunicipioCod() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getMunicipioCod();
		else if(flagModificada && beanAux.getMunicipioCod()!=null)
			return beanAux.getMunicipioCod();
		else return beanOriginal.getMunicipioCod();
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
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getNombre();
		else if (beanOriginal.getMunicipio() == null) {
			return null;
		}
		return beanOriginal.getMunicipio().getMunicipioNombre();
	}

	public String getProvinciaCod() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getNombre();
		else if (beanOriginal.getMunicipio() == null) {
			return null;
		}
		return beanOriginal.getMunicipio().getProvinciaCod();
	}

	public String getProvinciaNombre() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getNombre();
		else if (beanOriginal.getMunicipio() == null) {
			return null;
		}
		return beanOriginal.getMunicipio().getProvinciaNombre();
	}

	public String getPaisCod() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getNombre();
		else if (beanOriginal.getMunicipio() == null) {
			return null;
		}
		return beanOriginal.getMunicipio().getPaisCod();
	}

	public String getPaisNombre() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getNombre();
		else if (beanOriginal.getMunicipio() == null) {
			return null;
		}
		return beanOriginal.getMunicipio().getPaisNombre();
	}

	public String getTZ() {
		if(flagBorrada)
			return null;
		else if(flagNueva)
			return beanOriginal.getNombre();
		else if (beanOriginal.getMunicipio() == null) {
			return null;
		}
		return beanOriginal.getMunicipio().getTz();
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

	public List<DiaFestivo> getDiasFestivos(int time_ini, int time_fin, int limit) {
		//TODO
		return null;
	}

	public List<TurnoTrabajadorDia> getHorarioCompletoDia(int time) {
		//TODO
		return null;
	}


	public void graba() {
		Residencia aux = null;
		if(flagNueva)
			aux = Residencia.nuevaResidencia(beanOriginal);
		else if(flagModificada)
			aux = Residencia.modResidencia(beanAux, getCodigo());
		
		if (aux != null) {
			this.beanOriginal = aux.beanOriginal;
			this.beanAux = aux.beanAux;
		}
		flagNueva = false;
		flagModificada = false;
		flagBorrada = false;
	}

	public void borra() {
		boolean b = false;
		if(!flagBorrada && !flagNueva)
			b = Residencia.borraResidencia(getCodigo());
		
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

	@Override
	public String toString() {
		return "Residencia ["
				
				+ "getCodigo()=" + getCodigo() + ", getNombre()="
				+ getNombre() + ", getDescripcion()=" + getDescripcion()
				+ ", getCiudad()=" + getCiudad() + ", getMunicipioCod()="
				+ getMunicipioCod() + ", getMunicipio()=" + getMunicipio()
				+ ", getMunicipioNombre()=" + getMunicipioNombre()
				+ ", getProvinciaCod()=" + getProvinciaCod()
				+ ", getProvinciaNombre()=" + getProvinciaNombre()
				+ ", getPaisCod()=" + getPaisCod() + ", getPaisNombre()="
				+ getPaisNombre() + ", getTZ()=" + getTZ()

				+ ", flagNueva=" + flagNueva + ", flagModificada="
				+ flagModificada + ", flagBorrada=" + flagBorrada
				
				+ "]";
	}
	
	
	
}
