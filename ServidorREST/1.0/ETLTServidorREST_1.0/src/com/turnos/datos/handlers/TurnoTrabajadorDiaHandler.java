package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.ServicioBean;
import com.turnos.datos.vo.TrabajadorBean;
import com.turnos.datos.vo.TurnoBean;
import com.turnos.datos.vo.TurnoTrabajadorDiaBean;

public class TurnoTrabajadorDiaHandler extends GenericHandler {
	private static final String QUERY_GET_LISTA_TURNOS = 
			"SELECT tr.codigo as codTrab, tr.nombre as nomTrab, tr.apellidos as apeTrab, tu.codigo as codTurno, tu.tipo as tipoTurno, "
					+ "se.id_servicio as idServ, se.hora_pres as horaPresServ, se.hora_ret as horaRetServ, se.tiempo_toma as tiempoToma, se.tiempo_deje as tiempoDeje, "
					+ "se.margen_antes as margenAntes, se.margen_despues as margenDespues, se.descripcion as descServ "
				+ "FROM residencia res, servicio se, trabajador tr, turno tu, servicio_tipodia sd, turno_trabajador tt "
				+ "WHERE res.codigo=? AND res.id_residencia=tr.id_residencia AND tr.id_trabajador=tt.id_trabajador AND tt.fecha=? "
					+ "AND tt.id_turno=se.id_turno AND se.id_servicio=sd.id_servicio "
					+ "AND (FIND_IN_SET(?, sd.dia) OR sd.dia='TODOS') "
					+ "AND (sd.festivo=? OR sd.festivo='CUALQUIERA') "
					+ "AND (sd.vispera_festivo=? OR sd.vispera_festivo='CUALQUIERA') "
					+ "AND tt.id_turno=tu.id_turno "
				+ "ORDER BY se.hora_pres, se.hora_ret, tr.codigo";
	
	private static final String QUERY_GET_TURNOS_TRABAJADOR = 
			"SELECT tr.codigo as codTrab, tr.nombre as nomTrab, tr.apellidos as apeTrab, tu.codigo as codTurno, tu.tipo as tipoTurno, "
					+ "se.id_servicio as idServ, se.hora_pres as horaPresServ, se.hora_ret as horaRetServ, se.tiempo_toma as tiempoToma, se.tiempo_deje as tiempoDeje, "
					+ "se.margen_antes as margenAntes, se.margen_despues as margenDespues, se.descripcion as descServ "
				+ "FROM residencia res, servicio se, trabajador tr, turno tu, servicio_tipodia sd, turno_trabajador tt "
				+ "WHERE res.codigo=? AND res.id_residencia=tr.id_residencia "
					+ "AND tr.codigo=? AND tr.id_trabajador=tt.id_trabajador AND tt.fecha=? "
					+ "AND tt.id_turno=se.id_turno AND se.id_servicio=sd.id_servicio "
					+ "AND (FIND_IN_SET(?, sd.dia) OR sd.dia='TODOS') "
					+ "AND (sd.festivo=? OR sd.festivo='CUALQUIERA') "
					+ "AND (sd.vispera_festivo=? OR sd.vispera_festivo='CUALQUIERA') "
					+ "AND tt.id_turno=tu.id_turno "
				+ "ORDER BY se.hora_pres, se.hora_ret, tr.codigo";
	
	
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
				if (cierraConexion && nconexion != null){
					try {
						nconexion.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return listaTurnos;
	}


	public static TurnoTrabajadorDiaBean getTurnoTrabajadorDia(Connection conexion, String codRes, String codTrab, Date fecha, ErrorBean errorBean) {
		Connection nconexion = GenericHandler.aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);
		
		TurnoTrabajadorDiaBean ttd = null;
		java.sql.Date sqldate = javaDateToSQLDate(fecha);
		String[] infoDia = GenericHandler.getInfoDia(nconexion, codRes, sqldate, errorBean);
		PreparedStatement ps;
		ResultSet rs;
		if(infoDia != null && infoDia.length == 3) {
			try {
				ps = nconexion.prepareStatement(QUERY_GET_TURNOS_TRABAJADOR);
				ps.setString(1, codRes);
				ps.setString(2, codTrab);
				ps.setDate(3, sqldate);
				ps.setString(4, infoDia[0]);
				ps.setString(5, infoDia[1]);
				ps.setString(6, infoDia[2]);
				rs = ps.executeQuery();
				
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
					
					ttd.setFecha(fecha);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (cierraConexion && nconexion != null){
					try {
						nconexion.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return ttd;
	}


	public static TurnoTrabajadorDiaBean getTurnosTrabajadorRango(
			Connection conexion, String codRes, String codTrab, Date fecha_ini,
			Date fecha_fin, ErrorBean errorBean) {
		// TODO Auto-generated method stub
		return null;
	}
}
