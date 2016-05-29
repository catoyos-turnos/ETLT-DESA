package com.turnos.cliente.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.turnos.cliente.conexion.Sesion;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String s = request.getContextPath();
		System.out.println(s);
		Object sAux = request.getSession().getAttribute("SesionAPI");
		boolean b = false;
		Sesion sesionAPI = null;
		if(sAux != null && sAux instanceof Sesion) {
			sesionAPI = (Sesion) sAux;
			if(sesionAPI.isActiva()) {
				b = true;
			} else {
				sesionAPI.refresca();
				if(sesionAPI.isActiva()) {
					b = true;
				}
			}
		}
		if(b) {
			request.setAttribute("usuario", sesionAPI.getUsuarioLogeado());
			request.setAttribute("mensaje", "yeaahhh biiaatch");
		} else {
			response.sendRedirect(request.getContextPath()+"/login");
		}
		return b;
	}

	
}
