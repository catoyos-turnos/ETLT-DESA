package com.turnos.cliente;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Propiedades {
	//private static final String BUNDLE_NAME_DEFAULT;
	//private static final ResourceBundle RESOURCE_BUNDLE_DEFAULT;
	private static final String BUNDLE_NAME_USER;
	private static final ResourceBundle RESOURCE_BUNDLE_USER;

	static {
		ResourceBundle aux = null;
		//BUNDLE_NAME_DEFAULT = "resources.config"; //$NON-NLS-1$
		//try {
		//	aux = ResourceBundle.getBundle(BUNDLE_NAME_DEFAULT);
		//} catch(Exception e) {
		//	aux = null;
		//	e.printStackTrace();
		//}
		//RESOURCE_BUNDLE_DEFAULT = aux;
		
		/* **** SE DEBE INCLUIR UN FICHERO config.properties EN LA CARPETA 
				EN LA QUE SE SITUE EL JAR, CON AL MENOS LA DIRECCION BASE
				Y LAS CLAVES PÚBLICA Y SECRETA DE LA APLICACIÓN. P.EJ:
				
				
				Aplicacion.baseURL=http://localhost:9090/ETLTServidorREST/api
				Aplicacion.publicKey=8A21F0C7ADF164DD10CD
				Aplicacion.secretKey=F6E8EE83AD21DF2B09F8
				
				
				**** */
		BUNDLE_NAME_USER = "config"; //$NON-NLS-1$
		try {
			aux = ResourceBundle.getBundle(BUNDLE_NAME_USER);
		} catch(Exception e) {
			aux = null;
			e.printStackTrace();
		}
		RESOURCE_BUNDLE_USER = aux;
	}

	public static String getString(String key) {
//		System.out.println("user");
		if(RESOURCE_BUNDLE_USER != null && RESOURCE_BUNDLE_USER.containsKey(key)) {
//			System.out.println(RESOURCE_BUNDLE_USER.getString(key));
			return RESOURCE_BUNDLE_USER.getString(key);
		}

//		System.out.println("default");
		try {
//			System.out.println(RESOURCE_BUNDLE_DEFAULT.getString(key));
			return RESOURCE_BUNDLE_DEFAULT.getString(key);
		} catch (MissingResourceException e1) {
			return '!' + key + '!';
		}
	}
}
