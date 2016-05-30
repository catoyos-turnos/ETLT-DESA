package com.turnos.cliente;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Propiedades {
	private static final String BUNDLE_NAME_DEFAULT;
	private static final ResourceBundle RESOURCE_BUNDLE_DEFAULT;
	private static final String BUNDLE_NAME_USER;
	private static final ResourceBundle RESOURCE_BUNDLE_USER;

	static {
		ResourceBundle aux = null;
		BUNDLE_NAME_DEFAULT = "resources.config"; //$NON-NLS-1$
		try {
			aux = ResourceBundle.getBundle(BUNDLE_NAME_DEFAULT);
		} catch(Exception e) {
			aux = null;
			e.printStackTrace();
		}
		RESOURCE_BUNDLE_DEFAULT = aux;
		
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
