package com.turnos.cliente.spring;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.turnos.cliente.modelo.Residencia;
import com.turnos.cliente.modelo.Trabajador;
import com.turnos.cliente.modelo.Usuario;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;
@Controller
@RequestMapping({"/app"})
public class CentralControlador {

	@RequestMapping({"/", "/front"})
	public ModelAndView posicionCentral() {

		System.out.println("HOLA");
		Usuario usuario = SesionUtils.getUsuarioLogeado();

		Residencia residencia = usuario.getResidencia();
		Trabajador trabajador = usuario.getTrabajador();
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone(residencia.getTZ()));
		c.set(2016, 5, 23);
		List<TurnoTrabajadorDiaBean> servicios = getServicios(trabajador, residencia, c);
		
		ModelAndView model = new ModelAndView("central");
//		model.addObject("usuario", usuario);
		model.addObject("hoy", c.getTime().clone());
		model.addObject("trabajador", trabajador);
		model.addObject("residencia", residencia);
		model.addObject("servicios", servicios);
		
		
//		model.addObject("sdfIn", sdfIn);//TODO sacar a session
//		model.addObject("sdfEx", sdfEx);//TODO sacar a session
		
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
	
	
	private List<TurnoTrabajadorDiaBean> getServicios(Trabajador trabajador, Residencia residencia, Calendar c) {

		LinkedList<TurnoTrabajadorDiaBean> servicios = new LinkedList<TurnoTrabajadorDiaBean>();
		TurnoTrabajadorDiaBean ttdAux;
		ServicioBean servAux;
		TurnoBean turnoAux;

		c.add(Calendar.DATE, 1);
		ttdAux = new TurnoTrabajadorDiaBean();
//		ttdAux.setTrabajador(trabajador);
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
