package com.turnos.datos;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

	private static final int iterations = 4;
	private static final int saltLen = 40;
	private static final int desiredKeyLen = 128;

	public static String getSaltedHash(String pass) throws Exception {
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
		return Base64.getEncoder().encodeToString(salt) + "$" + hash(pass, salt);
	}

	public static boolean check(String pass, String guardada)
			throws Exception {
		String[] saltPass = guardada.split("\\$");
		if (saltPass.length != 2) {
			throw new IllegalStateException( "La contraseña guardada no tiene la forma 'salt$hash'");
		}
		String hashOfInput = hash(pass, Base64.getDecoder().decode(saltPass[0]));
		return hashOfInput.equals(saltPass[1]);
	}

	private static String hash(String pass, byte[] salt) throws Exception {
		if (pass == null || pass.length() == 0) throw new IllegalArgumentException( "Contraseña no puede estar vacia");
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = f.generateSecret(new PBEKeySpec(pass.toCharArray(), salt, iterations, desiredKeyLen));
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}