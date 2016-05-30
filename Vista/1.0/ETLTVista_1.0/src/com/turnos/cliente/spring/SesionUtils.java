package com.turnos.cliente.spring;

import com.turnos.cliente.modelo.Usuario;
import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.UsuarioBean;

public class SesionUtils {

	public static Usuario getUsuarioLogeado() {
		UsuarioBean bean = new UsuarioBean();
		bean.setIdUsuario(34234);
		bean.setNombre("Moran");
		ResidenciaBean residencia = getResidencia();
		TrabajadorBean trabajador = getTrabajador();
		bean.setCodRes(residencia.getCodigo());
		bean.setCodTrab(trabajador.getCodigo());
		bean.setResidencia(residencia);
		bean.setTrabajador(trabajador);
		bean.setNivel("ADMIN");
		bean.setActivado(true);
		bean.setUser("MAQ_FEVE_AST_6173");

		Usuario u = Usuario.genera(bean);
		return u;
	}
	
	private static TrabajadorBean getTrabajador() {

		TrabajadorBean trabajador = new TrabajadorBean();
		trabajador.setCodigo("6173");
		trabajador.setNombre("Juan Jose");
		trabajador.setApellidos("Riera Moran");
		trabajador.setCodResidencia("OVIEDO_URIA");
		return trabajador;
	}
	
	private static ResidenciaBean getResidencia() {

		ResidenciaBean residencia = new ResidenciaBean();
		residencia.setCodigo("OVIEDO_URIA");
		residencia.setCiudad("Oviedo");
		residencia.setNombre("Oviedo");
		residencia.setDescripcion("Estación Uria");
		residencia.setMunicipioCod( "ES330447");
		
		MunicipioBean municipio = new MunicipioBean();
		municipio.setMunicipioCod( "ES330447");
		municipio.setMunicipioNombre("Oviedo");
		municipio.setProvinciaCod("ES33");
		municipio.setProvinciaNombre("Asturias");
		municipio.setPaisCod("ES");
		municipio.setPaisNombre("España");
		municipio.setTz("Europe/Madrid");
		residencia.setMunicipio(municipio);
		
		return residencia;
	}

}
