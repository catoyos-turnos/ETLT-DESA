package com.turnos.cliente.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turnos.cliente.SesionUtils;
import com.turnos.cliente.conexion.Sesion;
import com.turnos.cliente.modelo.Mensaje;
import com.turnos.cliente.modelo.Usuario;

@Controller
@RequestMapping({ "/ajax" })
public class AjaxControlador {

	@RequestMapping(value = "/contadores", method = RequestMethod.GET)
	public @ResponseBody String getTime(HttpServletRequest request) {
		Sesion sesionAPI = SesionUtils.getSesionFromRequest(request);
		boolean b = sesionAPI != null && sesionAPI.isActiva();
		int r = -1;
		if(b) {
			Usuario	usuario = sesionAPI.getUsuarioLogeado();
			if (usuario != null) {
				r = Mensaje.getNumMensajes(usuario.getIdUsuario(), false, sesionAPI);
			}
		}
		return (r>0) ? ""+r : "";
	}
}
