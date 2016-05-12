package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;

//75xxxx
public class TurnoTrabajadorDiaHandler extends GenericHandler {
	private static final String QUERY_GET_LISTA_TURNOS = 
			"SELECT tr.codigo as codTrab, tr.nombre as nomTrab, tr.apellidos as apeTrab, tu.codigo as codTurno, tu.tipo as tipoTurno, "
				+ "se.id_servicio as idServ, se.hora_pres as horaPresServ, se.hora_ret as horaRetServ, se.tiempo_toma as tiempoToma, se.tiempo_deje as tiempoDeje, "
				+ "se.margen_antes as margenAntes, se.margen_despues as margenDespues, se.descripcion as descServ "				
			+ "FROM residencia res "
				+ "JOIN trabajador tr ON res.id_residencia=tr.id_residencia "
				+ "JOIN turno_trabajador tt ON tr.id_trabajador=tt.id_trabajador "
				+ "JOIN turno tu ON tt.id_turno=tu.id_turno "
				+ "JOIN servicio se ON tt.id_turno=se.id_turno "
				+ "JOIN servicio_tipodia sd ON se.id_servicio=sd.id_servicio "
			+ "WHERE res.codigo=? AND tt.fecha=? "
				+ "AND (FIND_IN_SET(?, sd.dia) OR sd.dia='TODOS') "
				+ "AND (sd.festivo=? OR sd.festivo='CUALQUIERA') "
				+ "AND (sd.vispera_festivo=? OR sd.vispera_festivo='CUALQUIERA') "
			+ "ORDER BY se.hora_pres, se.hora_ret, tr.codigo";
			
	private static final String QUERY_GET_TURNO_TRABAJADOR_DIA = 
		"SELECT tr.codigo as codTrab, tr.nombre as nomTrab, tr.apellidos as apeTrab, tu.codigo as codTurno, tu.tipo as tipoTurno, "
			+ "se.id_servicio as idServ, se.hora_pres as horaPresServ, se.hora_ret as horaRetServ, se.tiempo_toma as tiempoToma, se.tiempo_deje as tiempoDeje, "
			+ "se.margen_antes as margenAntes, se.margen_despues as margenDespues, se.descripcion as descServ "
		+ "FROM residencia res "
			+ "JOIN trabajador tr ON res.id_residencia=tr.id_residencia "
			+ "JOIN turno_trabajador tt ON tr.id_trabajador=tt.id_trabajador "
			+ "JOIN turno tu ON tt.id_turno=tu.id_turno "
			+ "JOIN servicio se ON tt.id_turno=se.id_turno "
			+ "JOIN servicio_tipodia sd ON se.id_servicio=sd.id_servicio "
		+ "WHERE res.codigo=? AND tr.codigo=? AND tt.fecha=? "
			+ "AND (FIND_IN_SET(?, sd.dia) OR sd.dia='TODOS') "
			+ "AND (sd.festivo=? OR sd.festivo='CUALQUIERA') "
			+ "AND (sd.vispera_festivo=? OR sd.vispera_festivo='CUALQUIERA') "
		+ "ORDER BY se.hora_pres, se.hora_ret, tr.codigo";
	

	//00xx
	public static ArrayList<TurnoTrabajadorDiaBean> getTodosTurnosDia(Connection conexion, String codRes, Date fecha, ErrorBean errorBean) {
		Connection nconexion = GenericHandler.aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		ArrayList<TurnoTrabajadorDiaBean> listaTurnos = null;
		java.sql.Date sqldate = javaDateToSQLDate(fecha);
		String[] infoDia = GenericHandler.getInfoDia(nconexion, codRes, sqldate, errorBean);
		PreparedStatement ps;
		ResultSet rs;
		if(infoDia != null && infoDia.length == 3) {
			listaTurnos = new ArrayList<TurnoTrabajadorDiaBean>();
			try {
				ps = nconexion.prepareStatement(QUERY_GET_LISTA_TURNOS);
				ps.setString(1, codRes);
				ps.setDate(2, sqldate);
				ps.setString(3, infoDia[0]);
				ps.setString(4, infoDia[1]);
				ps.setString(5, infoDia[2]);
				rs = ps.executeQuery();

				TurnoTrabajadorDiaBean ttd;
				TurnoBean turno;
				ServicioBean serv;
				TrabajadorBean trab;
				while (rs.next()) {
					ttd = new TurnoTrabajadorDiaBean();
					turno = new TurnoBean();
					turno.setCodTurno(rs.getString("codTurno"));
					turno.setCodResidencia(codRes);
					turno.setTipo(rs.getString("tipoTurno"));
					ttd.setTurno(turno);
					
					serv = new ServicioBean();
					serv.setId_servicio(rs.getInt("idServ"));
					serv.setHora_pres(rs.getTime("horaPresServ").toString());
					serv.setHora_ret(rs.getTime("horaRetServ").toString());
					serv.setTiempo_toma(rs.getInt("tiempoToma"));
					serv.setTiempo_deje(rs.getInt("tiempoDeje"));
					serv.setMargen_antes(rs.getInt("margenAntes"));
					serv.setMargen_despues(rs.getInt("margenDespues"));
					serv.setDescripcion(rs.getString("descServ"));
					ttd.setServicio(serv);
					
					trab = new TrabajadorBean();
					trab.setCodigo(rs.getString("codTrab"));
					trab.setCodResidencia(codRes);
					trab.setNombre(rs.getString("nomTrab"));
					trab.setApellidos(rs.getString("apeTrab"));
					ttd.setTrabajador(trab);
					
					ttd.setFecha(fecha);

					listaTurnos.add(ttd);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				GenericHandler.terminaOperacion(nconexion, cierraConexion);
			}
		}
		return listaTurnos;
	}


	//01xx
	public static TurnoTrabajadorDiaBean getTurnoTrabajadorDia(Connection conexion,
			String codRes, String codTrab, Date fecha, ErrorBean errorBean) {
		Connection nconexion = GenericHandler.aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		TurnoTrabajadorDiaBean ttd = null;
		java.sql.Date sqlFecha = javaDateToSQLDate(fecha);
		String[] infoDia = GenericHandler.getInfoDia(nconexion, codRes, sqlFecha, errorBean);
		if(infoDia != null && infoDia.length == 3) {
			try {
				ttd = recuperaTurnoTrabajadorDia(nconexion, codRes, codTrab, sqlFecha, infoDia, errorBean);
			} catch (SQLException e) {
				ttd = null;
				e.printStackTrace();
			} finally {
				GenericHandler.terminaOperacion(nconexion, cierraConexion);
			}
		}
		return ttd;
	}

	//02xx
	public static ArrayList<TurnoTrabajadorDiaBean> getTurnosTrabajadorRango(
			Connection conexion, String codRes, String codTrab, Date fecha_ini,
			Date fecha_fin, ErrorBean errorBean) {
		Connection nconexion = GenericHandler.aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		ArrayList<TurnoTrabajadorDiaBean> listaTtd = new ArrayList<TurnoTrabajadorDiaBean>();
		TurnoTrabajadorDiaBean ttd;
		if (fecha_ini.getTime() > fecha_fin.getTime()) {
			Date fecha_aux = fecha_ini;
			fecha_ini = fecha_fin;
			fecha_fin = fecha_aux;
		}
		java.sql.Date sqlFechaIni = javaDateToSQLDate(fecha_ini);
		java.sql.Date sqlFechaFin = javaDateToSQLDate(fecha_fin);
		String datestr;
		Hashtable<String, String[]> infoDiaTabla = GenericHandler.getInfoRangoDias(nconexion, codRes, sqlFechaIni, sqlFechaFin, errorBean);
		String[] infoDia;
		Calendar c = Calendar.getInstance();
		c.setTime(fecha_ini);

		try {
			while (c.before(fecha_fin)) {
				datestr = sdfIn.format(c.getTime());
				infoDia = infoDiaTabla.get(datestr);
				if (infoDia != null && infoDia.length == 3) {
					ttd = recuperaTurnoTrabajadorDia(nconexion, codRes,
							codTrab, javaDateToSQLDate(c.getTime()), infoDia,
							errorBean);
					listaTtd.add(ttd);
				}
				c.roll(Calendar.DATE, true);
			}
		} catch (SQLException e) {
			listaTtd = null;
			e.printStackTrace();
		} finally {
			GenericHandler.terminaOperacion(nconexion, cierraConexion);
		}
		
		return listaTtd;
	}
	
	private static TurnoTrabajadorDiaBean recuperaTurnoTrabajadorDia(Connection nconexion,
			String codRes, String codTrab, java.sql.Date sqlFecha, String[] infoDia, ErrorBean errorBean)
			throws SQLException{
		PreparedStatement ps;
		ResultSet rs;
		ps = nconexion.prepareStatement(QUERY_GET_TURNO_TRABAJADOR_DIA);
		ps.setString(1, codRes);
		ps.setString(2, codTrab);
		ps.setDate(3, sqlFecha);
		ps.setString(4, infoDia[0]);
		ps.setString(5, infoDia[1]);
		ps.setString(6, infoDia[2]);
		rs = ps.executeQuery();

		TurnoTrabajadorDiaBean ttd = new TurnoTrabajadorDiaBean();
		TurnoBean turno;
		ServicioBean serv;
		TrabajadorBean trab;
		if (rs.next()) {
			ttd = new TurnoTrabajadorDiaBean();
			turno = new TurnoBean();
			turno.setCodTurno(rs.getString("codTurno"));
			turno.setCodResidencia(codRes);
			turno.setTipo(rs.getString("tipoTurno"));
			ttd.setTurno(turno);
			
			serv = new ServicioBean();
			serv.setId_servicio(rs.getInt("idServ"));
			serv.setHora_pres(rs.getTime("horaPresServ").toString());
			serv.setHora_ret(rs.getTime("horaRetServ").toString());
			serv.setTiempo_toma(rs.getInt("tiempoToma"));
			serv.setTiempo_deje(rs.getInt("tiempoDeje"));
			serv.setMargen_antes(rs.getInt("margenAntes"));
			serv.setMargen_despues(rs.getInt("margenDespues"));
			serv.setDescripcion(rs.getString("descServ"));
			ttd.setServicio(serv);
			
			trab = new TrabajadorBean();
			trab.setCodigo(rs.getString("codTrab"));
			trab.setCodResidencia(codRes);
			trab.setNombre(rs.getString("nomTrab"));
			trab.setApellidos(rs.getString("apeTrab"));
			ttd.setTrabajador(trab);
			
			ttd.setFecha(sqlFecha);
		}
		return ttd;
	}

}
