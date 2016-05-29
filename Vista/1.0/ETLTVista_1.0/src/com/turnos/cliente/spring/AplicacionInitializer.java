package com.turnos.cliente.spring;

import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

public class AplicacionInitializer implements WebApplicationInitializer {

	private static final SimpleDateFormat sdfIn = new SimpleDateFormat( "yyyy-MM-dd");
	private static final SimpleDateFormat sdfEx = new SimpleDateFormat( "EEE MM/dd");

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		servletContext.setAttribute("sdfIn", sdfIn);
		servletContext.setAttribute("sdfEx", sdfEx);
	}

}
