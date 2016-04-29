package com.turnos.restservice;

import java.util.Calendar;
import java.util.Date;

public class WebServUtils {

	public static Date getFecha(int anio, int mes, int dia) {
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		Date d = null;
		try {
			c.set(anio, mes - 1, dia);
			d = c.getTime();
		} catch (IllegalArgumentException e) {
			return null;
		}
		return d;
	}
}
