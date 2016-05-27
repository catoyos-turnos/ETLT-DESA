package com.turnos.cliente.conexion;

import com.turnos.cliente.modelo.Usuario;
import com.turnos.datos.CriptoUtils;
import com.turnos.datos.vo.SesionBean;

public class Sesion {
	public final Aplicacion aplicacion;
	private String usuario = null;
	private String pass = null;
	
	private String tokenSesion;
	private long abierto;
	private long tokenCaduca;
	private Usuario usuarioLogeado;
	
	private Sesion(SesionBean bean, Aplicacion aplicacion) {
		this(bean.getTokenSesion(), bean.getAbierto(),  bean.getTokenCaduca(), Usuario.genera(bean.getUsuario()), aplicacion);
	}

	private Sesion(String tokenSesion, long abierto, long tokenCaduca, Usuario usuarioLogeado, Aplicacion aplicacion) {
		this.tokenSesion = tokenSesion;
		this.abierto = abierto;
		this.tokenCaduca = tokenCaduca;
		this.usuarioLogeado = usuarioLogeado;
		this.aplicacion = aplicacion;
	}

	public String getTokenSesion() {
		if((tokenCaduca-System.currentTimeMillis()) < (60*1000)) refresca();
		return tokenSesion;
	}

	public long getAbierto() {
		return abierto;
	}

	public long getTokenCaduca() {
		return tokenCaduca;
	}

	public Usuario getUsuarioLogeado() {
		return usuarioLogeado;
	}
	
	public void refresca() {
		SesionBean bean = getSesionBean(this.usuario, this.pass, this.aplicacion);
		if (bean != null) {
			this.tokenSesion = bean.getTokenSesion();
			this.abierto = bean.getAbierto();
			this.tokenCaduca = bean.getTokenCaduca();
			this.usuarioLogeado = Usuario.genera(bean.getUsuario());
		}
	}
	
	public static Sesion genera(String usuario, String pass) {
		return genera(usuario, pass, Aplicacion.defaultApp());
	}
	
	public static Sesion genera(String usuario, String pass, Aplicacion aplicacion) {
		SesionBean bean = Sesion.getSesionBean(usuario,pass,aplicacion);
		if (bean != null) {
			Sesion s = new Sesion(bean, aplicacion);
			s.usuario = usuario;
			s.pass = pass;
			return s;
		} else return null;
	}
	
	private static SesionBean getSesionBean(String usuario, String pass, Aplicacion aplicacion) {
		if(aplicacion != null && usuario != null && pass != null && !"".equals(usuario) && !"".equals(pass)) {
			String tokenLogin = crearTokenLogin(usuario, pass, aplicacion.secretKey);
			return ClienteREST.login(tokenLogin, aplicacion);
		} else return null;
	}
	
	private static SesionBean getSesionBean(String tokenLogin, Aplicacion aplicacion) {
		if(aplicacion != null && usuario != null && pass != null && !"".equals(usuario) && !"".equals(pass)) {
			String[] userPass = usuarioPassDesdeToken(tokenLogin, aplicacion.secretKey);
			if(userPass != null && userPass.lenght > 1) {
				String usuario = userPass[0];
				String pass = userPass[1];
				return getSesionBean(usuario, pass, aplicacion);
			}
		} else return null;
	}
	
	private static String crearTokenLogin(String usuario, String pass, String secretKey) {
		String[] fields = new String[4];

		fields[0] = CriptoUtils.generaRandomHexString(8);
		
		fields[1] = "" + System.currentTimeMillis();
		fields[2] = usuario.trim();
		fields[3] = pass.trim();

		String desncr = String.join("@", fields);
		String tokenLogin = null;
		try {
			tokenLogin = CriptoUtils.encripta(desncr, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tokenLogin;
	}

	private static String[] usuarioPassDesdeToken(String tokenLogin, String secretKey) {
		String desncr = null;
		try {
			desncr = CriptoUtils.desencripta(tokenLogin, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (desncr != null) {
			String[] fields = desncr.split("@");
			if (fields.length > 3) {
				String[] userPass = new String[2];
				long time = -1;
				try {
					time = Long.parseLong(fields[1]);
				} catch (NumberFormatException e) { ; }
				userPass[0] = fields[2];
				userPass[1] = fields[3];
				
				return userPass;
			} else {
				// TODO ERROR
			}
		} else {
			// TODO ERROR
		}

		return null;
	}
	 
	
}
