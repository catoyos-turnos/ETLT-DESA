package com.turnos.cliente;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Propiedades {
	private static final String BUNDLE_NAME_DEFAULT = "com.turnos.cliente.config"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE_DEFAULT = ResourceBundle.getBundle(BUNDLE_NAME_DEFAULT);
	private static final String BUNDLE_NAME_USER = "config"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE_USER = ResourceBundle.getBundle(BUNDLE_NAME_USER);

	private Propiedades() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE_USER.getString(key);
		} catch (MissingResourceException e) {
			try {
				return RESOURCE_BUNDLE_DEFAULT.getString(key);
			} catch (MissingResourceException e1) {
				return '!' + key + '!';
			}
		}
	}
}
