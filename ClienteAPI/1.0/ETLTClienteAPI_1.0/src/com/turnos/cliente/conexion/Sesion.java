package com.turnos.cliente.conexion;

import com.turnos.cliente.modelo.Usuario;
import com.turnos.datos.CriptoUtils;
import com.turnos.datos.vo.SesionBean;

public class Sesion {
	public final Aplicacion aplicacion;
	private String usuario = null;
	private String contrase�a = null;
	
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
		SesionBean bean = getSesionBean(this.usuario, this.contrase�a, this.aplicacion);
		if (bean != null) {
			this.tokenSesion = bean.getTokenSesion();
			this.abierto = bean.getAbierto();
			this.tokenCaduca = bean.getTokenCaduca();
			this.usuarioLogeado = Usuario.genera(bean.getUsuario());
		}
	}
	
	public static Sesion genera(String usuario, String contrase�a) {
		return genera(usuario, contrase�a, Aplicacion.defaultApp());
	}
	
	public static Sesion genera(String usuario, String contrase�a, Aplicacion aplicacion) {
		SesionBean bean = Sesion.getSesionBean(usuario,contrase�a,aplicacion);
		if (bean != null) {
			Sesion s = new Sesion(bean, aplicacion);
			s.usuario = usuario;
			s.contrase�a = contrase�a;
			return s;
		} else return null;
	}
	
	private static SesionBean getSesionBean(String usuario, String contrase�a, Aplicacion aplicacion) {
		if(aplicacion != null && usuario != null && contrase�a != null && !"".equals(usuario) && !"".equals(contrase�a)) {
			String tokenLogin = crearTokenLogin(usuario, contrase�a, aplicacion.secretKey);
			return ClienteREST.login(tokenLogin, aplicacion);
		} else return null;
	}
	
	private static String crearTokenLogin(String usuario, String contrase�a, String secretKey) {
		String[] fields = new String[4];

		fields[0] = CriptoUtils.generaRandomHexString(8);
		
		fields[1] = "" + System.currentTimeMillis();
		fields[2] = usuario.trim();
		fields[3] = contrase�a.trim();

		String desncr = String.join("@", fields);
		String tokenLogin = null;
		try {
			tokenLogin = CriptoUtils.encripta(desncr, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tokenLogin;
	}
	 
	
}
