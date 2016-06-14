package com.turnos.cliente.spring;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.turnos.cliente.SesionUtils;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.cliente.modelo.Residencia;
import com.turnos.cliente.modelo.Trabajador;
import com.turnos.cliente.modelo.Usuario;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

//		System.out.println("preHandle:");
		String s = request.getContextPath();
//		System.out.println(s);
		Sesion sesionAPI = SesionUtils.getSesionFromRequest(request);
		boolean b = sesionAPI!=null && sesionAPI.isActiva();
		if(b) {
			Usuario u = sesionAPI.getUsuarioLogeado();
			request.setAttribute("usuario", u);
			if(u != null) {
//				Residencia r = Residencia.getResidencia(u.getCodRes(), sesionAPI);
				Residencia r = u.getResidencia(true, sesionAPI);
				request.setAttribute("residencia", r);
				Trabajador t = null;
				if (u.getCodTrab() != null) {
//					System.out.println("u.getCodTrab():"+u.getCodTrab());
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
