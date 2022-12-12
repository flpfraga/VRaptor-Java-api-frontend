package br.com.smartcrono.teste.persistencia;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfiguracoesBancoDeDados {
	private static final String BUNDLE_NAME = "bd";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private ConfiguracoesBancoDeDados() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
