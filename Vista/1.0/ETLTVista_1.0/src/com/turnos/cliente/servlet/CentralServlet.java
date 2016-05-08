package com.turnos.cliente.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.TimeZone;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.ResidenciaBean;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;

/**
 * Servlet implementation class CentralServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/front" })
public class CentralServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdfEx = new SimpleDateFormat("EEE MM/dd");

	@Override
	public void init() throws ServletException {
		super.init();
		getServletContext().setAttribute("sdfIn", sdfIn);
		getServletContext().setAttribute("sdfEx", sdfEx);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		
		TrabajadorBean trabajador = new TrabajadorBean();
		trabajador.setCodigo("6173");
		trabajador.setNombre("Juan Jose");
		trabajador.setApellidos("Riera Moran");
		trabajador.setCodResidencia("OVIEDO_URIA");
		sesion.setAttribute("trabajador", trabajador);
		
		ResidenciaBean residencia = new ResidenciaBean();
		residencia.setCodigo("OVIEDO_URIA");
		residencia.setCiudad("Oviedo");
		residencia.setNombre("Oviedo");
		residencia.setDescripcion("Estación Uria");
		residencia.setMunicipioCod( "ES330447");
		
		MunicipioBean municipio = new MunicipioBean();
		municipio.setMunicipioCod( "ES330447");
		municipio.setMunicipioNombre("Oviedo");
		municipio.setProvinciaCod("ES33");
		municipio.setProvinciaNombre("Asturias");
		municipio.setPaisCod("ES");
		municipio.setPaisNombre("España");
		municipio.setTz("Europe/Madrid");
		residencia.setMunicipio(municipio);
		
		sesion.setAttribute("residencia", residencia);
		
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone(residencia.getMunicipio().getTz()));
		c.set(2016, 4, 13);
		sesion.setAttribute("hoy", c.getTime().clone());

		LinkedList<TurnoTrabajadorDiaBean> servicios = new LinkedList<TurnoTrabajadorDiaBean>();
		TurnoTrabajadorDiaBean ttdAux;
		ServicioBean servAux;
		TurnoBean turnoAux;
		
		c.add(Calendar.DATE, 1);
		ttdAux = new TurnoTrabajadorDiaBean();
		ttdAux.setTrabajador(trabajador);
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

		sesion.setAttribute("servicios", servicios);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/central.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
