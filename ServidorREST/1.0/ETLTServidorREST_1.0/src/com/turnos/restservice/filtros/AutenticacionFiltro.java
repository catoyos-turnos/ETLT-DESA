package com.turnos.restservice.filtros;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.turnos.datos.CriptoUtils;
import com.turnos.datos.PasswordUtils;
import com.turnos.datos.WebServUtils;
import com.turnos.datos.fabricas.ErrorBeanFabrica;
import com.turnos.datos.handlers.AutenticacionHandler;
import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.UsuarioBean;


@PreMatching
@Provider
public class AutenticacionFiltro implements ContainerRequestFilter {
	public static final long MARGEN_LOGIN = 300000; // 5min.
	public static final long MARGEN_SESION = 10000000; // ~2h 45min.
	public static final String REQUEST_PARAM_USUARIO = "USUARIO";
	public static final String REQUEST_PARAM_APP_SECRET = "APP_SECRET";
	public static final String REQUEST_PARAM_SERVIDOR_KEY = "SERVIDOR_KEY";

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		// System.out.println("hola buenas");
		UsuarioBean usuario = null;
		ErrorBean errorBean = new ErrorBean();
		List<String> publicKeyL = requestContext.getHeaders().get("publicKey");
		List<String> tokenSesionL = requestContext.getHeaders().get("tokenSesion");
		List<String> tokenLoginL = requestContext.getHeaders().get("tokenLogin");
		List<String> necesitaAutenticarL = requestContext.getHeaders().get("necesitaAutenticar");
		List<String> saltaAutenticacionL = requestContext.getHeaders().get("saltaAutenticacion");
		String publicKey = null;
		String tokenSesion = null;
		String tokenLogin = null;
		String secretKey = null;
		String servidorKey = null;

		boolean necesitaAutenticar = (necesitaAutenticarL == null || !necesitaAutenticarL.isEmpty())
				? false : Boolean.parseBoolean(necesitaAutenticarL.get(0));
		boolean saltaAutenticacion = (saltaAutenticacionL == null || !saltaAutenticacionL.isEmpty())
				? false : Boolean.parseBoolean(saltaAutenticacionL.get(0));
		if (!saltaAutenticacion) {
			long ahora = System.currentTimeMillis();

			if (publicKeyL != null && !publicKeyL.isEmpty()) {
				publicKey = publicKeyL.get(0);
				if (tokenLoginL != null && !tokenLoginL.isEmpty()) {
					tokenLogin = tokenLoginL.get(0);
				}
				if (tokenSesionL != null && !tokenSesionL.isEmpty()) {
					tokenSesion = tokenSesionL.get(0);
				}

				if (tokenSesion != null || tokenLogin != null) {
					String[] keys = AutenticacionHandler.getAuthKeys(null, publicKey, errorBean);
					if (keys != null && keys.length >= 2) {
						secretKey = keys[0];
						servidorKey = keys[1];

						if (tokenLogin != null) {
							usuario = usuarioDesdeLogin(tokenLogin, secretKey, servidorKey, ahora, errorBean);

						} else if (tokenSesion != null) {
							usuario = usuarioDesdeSesion(tokenSesion, secretKey, servidorKey, ahora, errorBean);
						}
					}
				}
			}

			requestContext.setProperty(REQUEST_PARAM_USUARIO, usuario);
			if (!saltaAutenticacion && necesitaAutenticar && usuario == null) {
				if (errorBean == null || errorBean.getHttpCode() == Status.OK) {
					errorBean = ErrorBeanFabrica.generaUNAUTHORIZEDErrorBean(errorBean, "x000001", "necesita usuario autenticado", null);
				}
				requestContext.abortWith(Response.status(errorBean.getHttpCode()).entity(errorBean).build());
			} else {
				String loginPath = (WebServUtils.PREF_AUTH_PATH + WebServUtils.PREF_LOGIN_PATH);
				if (loginPath.equalsIgnoreCase("/" + requestContext.getUriInfo().getPath())) {
					requestContext.setProperty(REQUEST_PARAM_APP_SECRET, secretKey);
					requestContext.setProperty(REQUEST_PARAM_SERVIDOR_KEY, servidorKey);
				}
			}
		}
	}

	private static UsuarioBean usuarioDesdeLogin(String tokenLogin,
			String secretKey, String servidorKey, long ahora,
			ErrorBean errorBean) {

		String desncr = null;
		try {
			desncr = CriptoUtils.desencripta(tokenLogin, secretKey);
		} catch (Exception e) {
			// TODO ERROR
			e.printStackTrace(); // TODO borrar
		}

		if (desncr != null) {
			String[] fields = desncr.split("@");
			if (fields.length > 3) {
				long time = -1;
				try {
					time = Long.parseLong(fields[1]);
				} catch (NumberFormatException e) { ; }

				if (time < 0) {
					// TODO ERROR
				} else if ((ahora - time) < 0) {
					errorBean = ErrorBeanFabrica.generaUNAUTHORIZEDErrorBean(errorBean, "x000102", "token de login incorrecto", null);
				} else if ((ahora - time) > MARGEN_LOGIN) {
					errorBean = ErrorBeanFabrica.generaUNAUTHORIZEDErrorBean(errorBean, "x000103", "token de login ha caducado", null);
				} else {
					String user = fields[2];
					String pass = fields[3];
					try {
						System.out.println(pass);
						System.out.println(PasswordUtils.getSaltedHash(pass));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return AutenticacionHandler.getUsuarioPorUserPass(null, user, pass, errorBean);
				}
			} else {
				// TODO ERROR
			}
		} else {
			// TODO ERROR
		}

		return null;
	}

	private static UsuarioBean usuarioDesdeSesion(String tokenSesion,
			String secretKey, String servidorKey, long ahora,
			ErrorBean errorBean) {
		String desncrA = null;
		try {
			desncrA = CriptoUtils.desencripta(tokenSesion, servidorKey);
		} catch (Exception e) {
			// TODO ERROR
			e.printStackTrace(); // TODO borrar
		}

		if (desncrA != null) {
			String[] fields = desncrA.split("@");
			long time = -1;
			try {
				time = Long.parseLong(fields[1]);
			} catch (NumberFormatException e) { ; }

			if (time < 0) {
				// TODO ERROR
			} else if ((ahora - time) < 0) {
				errorBean = ErrorBeanFabrica.generaUNAUTHORIZEDErrorBean(errorBean, "x000202", "token de sesion incorrecto", null);
			} else if ((ahora - time) > MARGEN_SESION) {
				errorBean = ErrorBeanFabrica.generaUNAUTHORIZEDErrorBean(errorBean, "x000203", "token de sesion ha caducado", null);
			} else {
				String desncrB = null;
				try {
					desncrB = CriptoUtils.desencripta(fields[2].trim(),
							secretKey).trim();
				} catch (Exception e) {
					// TODO ERROR
					e.printStackTrace(); // TODO borrar
				}
				if (desncrB != null) {
					String[] fieldsB = desncrB.split(":");
					if (fieldsB.length > 3) {
						UsuarioBean res = new UsuarioBean();
						res.setIdUsuario(Long.parseLong(fieldsB[0]));
						res.setUser("".equalsIgnoreCase(fieldsB[1]) ? null : fieldsB[1]);
						res.setNivel("".equalsIgnoreCase(fieldsB[2]) ? null : fieldsB[2]);
						res.setActivado(Boolean.parseBoolean(fieldsB[3]));
						return res;
					} else {
						// TODO ERROR
					}
				} else {
					// TODO ERROR
				}
			}
		} else {
			// TODO ERROR
		}

		return null;
	}

}
