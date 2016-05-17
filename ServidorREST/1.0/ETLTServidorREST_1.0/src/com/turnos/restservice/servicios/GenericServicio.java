package com.turnos.restservice.servicios;

import javax.ws.rs.container.ContainerRequestContext;

import com.turnos.datos.vo.UsuarioBean;
import com.turnos.restservice.filtros.AutenticacionFiltro;

public abstract class GenericServicio {
	
	protected UsuarioBean usuarioLog;

	protected GenericServicio(UsuarioBean usuarioLog) {
		this.usuarioLog = usuarioLog;
	}
	
	protected GenericServicio(ContainerRequestContext request) {
		Object usrObj = (request == null) ? null : request.getProperty(AutenticacionFiltro.REQUEST_PARAM_USUARIO);

		if(usrObj != null && usrObj instanceof UsuarioBean) {
			this.usuarioLog = (UsuarioBean) usrObj;
		} else {
			this.usuarioLog = null;
		}

	}
}
