package com.turnos.cliente.spring;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.turnos.cliente.conexion.Sesion;
import com.turnos.cliente.modelo.Residencia;
import com.turnos.cliente.modelo.Trabajador;
import com.turnos.cliente.modelo.Usuario;

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
			Usuario u = sesionAPI.getUsuarioLogeado();
			request.setAttribute("usuario", u);
			if(u != null) {
//				Residencia r = Residencia.getResidencia(u.getCodRes(), sesionAPI);
				Residencia r = u.getResidencia(true, sesionAPI);
				request.setAttribute("residencia", r);
				Trabajador t = null;
				if (u.getCodTrab()!=null) {
//					t = Trabajador.getTrabajador(u.getCodRes(), u.getCodTrab(), sesionAPI);
					t = u.getTrabajador(true, sesionAPI);
				}
				request.setAttribute("trabajador", t);
				request.setAttribute("hoy", new Date(System.currentTimeMillis()));
				request.setAttribute("mensaje", "ok");
			} else {
				request.setAttribute("mensaje", "MAL");
			}
		} else {
			response.sendRedirect(request.getContextPath()+"/login");
		}
		return b;
	}
	
}
