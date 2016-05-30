package com.turnos.cliente.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.turnos.cliente.conexion.Sesion;
@Controller
public class LoginControlador {

	@RequestMapping(value={"/login", "/"}, method = RequestMethod.GET)
	public ModelAndView loginForm() {
		ModelAndView model = new ModelAndView("login");
		return model;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST )
	public RedirectView loginAccion(
			@RequestParam("user") String user,
			@RequestParam("pass") String pass,
			HttpServletRequest request) {
		Sesion s = Sesion.genera(user, pass);
		if(s.isActiva()) {
			request.getSession().setAttribute("SesionAPI", s);
			return new RedirectView(request.getContextPath()+"/app/front");
		} else {
			return new RedirectView(request.getContextPath()+"/login");
		}
	}

	@RequestMapping(value={"/logout"}, method = RequestMethod.GET)
	public ModelAndView logoutAccion(HttpServletRequest request) {
		request.getSession().setAttribute("SesionAPI", null);
		ModelAndView model = new ModelAndView("login");
		return model;
	}
}
