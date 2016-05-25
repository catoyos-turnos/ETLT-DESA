package com.turnos.cliente.spring;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;
@Controller
public class CentralControlador {

	private static final SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdfEx = new SimpleDateFormat("EEE MM/dd");
	
	@RequestMapping({"/front", "/"})
	public ModelAndView posicionCentral() {

		System.out.println("HOLA");
		
		TrabajadorBean trabajador = getTrabajador();
		ResidenciaBean residencia = getResidencia();
		
		ModelAndView model = new ModelAndView("central");
		
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone(residencia.getMunicipio().getTz()));
		c.set(2016, 5, 23);
		model.addObject("hoy", c.getTime().clone());
		List<TurnoTrabajadorDiaBean> servicios = getServicios(trabajador, residencia, c);
		model.addObject("trabajador", trabajador);
		model.addObject("residencia", residencia);
		model.addObject("servicios", servicios);
		
		
		model.addObject("sdfIn", sdfIn);//TODO sacar a session
		model.addObject("sdfEx", sdfEx);//TODO sacar a session
		
		return model;
	}

	@RequestMapping({"/trab/{codTrab}/dia"})
	public ModelAndView getDia(@PathVariable("codTrab") String codTrab, @RequestParam("fecha") String fecha) {
		System.out.println(fecha);
		System.out.println(codTrab);
		ModelAndView model = new ModelAndView("central");
		model.addObject("trabajador", codTrab);
		return model;
	}
	



	private TrabajadorBean getTrabajador() {

		TrabajadorBean trabajador = new TrabajadorBean();
		trabajador.setCodigo("6173");
		trabajador.setNombre("Juan Jose");
		trabajador.setApellidos("Riera Moran");
		trabajador.setCodResidencia("OVIEDO_URIA");
		return trabajador;
	}
	
	private ResidenciaBean getResidencia() {

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
	
	private List<TurnoTrabajadorDiaBean> getServicios(TrabajadorBean trabajador, ResidenciaBean residencia, Calendar c) {

		LinkedList<TurnoTrabajadorDiaBean> servicios = new LinkedList<TurnoTrabajadorDiaBean>();
		TurnoTrabajadorDiaBean ttdAux;
		ServicioBean servAux;
		TurnoBean turnoAux;

		c.add(Calendar.DATE, 1);
		ttdAux = new TurnoTrabajadorDiaBean();
		ttdAux.setTrabajador(trabajador);
		ttdAux.setFecha(c.getTime());
		
		servAux = new ServicioBean();
		servAux.setId_servicio(1);
		servAux.setHora_pres("17:54");
		servAux.setHora_ret("23:54");
		servAux.setTiempo_toma(15);
		servAux.setTiempo_deje(15);
		servAux.setDescripcion("70812 - 70677 - 70874 - 70683 - 70880 - Apartado");
		ttdAux.setServicio(servAux);
		
		turnoAux = new TurnoBean();
		turnoAux.setCodTurno("1");
		turnoAux.setCodResidencia(residencia.getCodigo());
		turnoAux.setTipo("ORDINARIO");
		ttdAux.setTurno(turnoAux);
		servicios.add(ttdAux);

		return servicios;

	}
}
