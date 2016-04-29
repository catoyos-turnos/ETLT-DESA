package com.turnos.datos;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AccesoBD {
	private static DataSource pool = null;

	private static DataSource getDataSource() {
		if (pool == null) {
			try {
				pool = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/turnos");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return pool;
	}

	public static Connection getConexion() {
		if(AccesoBD.getDataSource() != null)
			try {
				Connection cn = pool.getConnection();
				return cn;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return null;
	}
}
