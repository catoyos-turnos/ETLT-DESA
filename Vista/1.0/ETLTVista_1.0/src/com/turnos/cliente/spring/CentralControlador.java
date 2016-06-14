package com.turnos.cliente.spring;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.turnos.cliente.SesionUtils;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.cliente.modelo.Residencia;
import com.turnos.cliente.modelo.Trabajador;
import com.turnos.cliente.modelo.TurnoTrabajadorDia;
@Controller
@RequestMapping({"/app"})
public class CentralControlador {

	@RequestMapping({"/", "/front"})
	public ModelAndView posicionCentral(HttpServletRequest request) {

		Sesion sesionAPI = SesionUtils.getSesionFromRequest(request);
		Residencia residencia = (Residencia) request.getAttribute("residencia");
		Trabajador trabajador = (Trabajador) request.getAttribute("trabajador");
		Date hoy = (Date) request.getAttribute("hoy");
		
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone(residencia.getTz()));
		c.setTime(hoy);
		List<TurnoTrabajadorDia> servicios = getServicios(trabajador, residencia, c, sesionAPI);
		
		ModelAndView model = new ModelAndView("central");
		model.addObject("servicios", servicios);
		
		return model;
	}

	@RequestMapping({"/trab/{codTrab}/dia"})
	public ModelAndView getDia(@PathVariable("codTrab") String codTrab, @RequestParam("fecha") String fecha, HttpServletRequest request) {
		System.out.println(fecha);
		System.out.println(codTrab);
		ModelAndView model = new ModelAndView("central");
		model.addObject("trabajador", codTrab);
		return model;
	}
	
	
	private List<TurnoTrabajadorDia> getServicios(Trabajador trabajador, Residencia residencia, Calendar c, Sesion sesionAPI) {
		Date fecha_ini, fecha_fin;
		fecha_ini = c.getTime();
		c.roll(Calendar.DATE, 7);
		fecha_fin = c.getTime();
		List<TurnoTrabajadorDia> servicios = trabajador.getHorarioTrabajadorRango(fecha_ini, fecha_fin, sesionAPI);
		
		/*
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
		for (int i = 0; i < 10; i++) {
			servicios.add(ttdAux);
		}
*/
		return servicios;

	}
}
