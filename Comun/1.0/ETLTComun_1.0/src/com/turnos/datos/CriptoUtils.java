package com.turnos.datos;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

public class CriptoUtils {

	public static final Charset CHARSET = Charset.forName("UTF-8");
	public static final String	ALGORITHM = "AES";
	public static final String	TRANSFORMATION = "AES/ECB/PKCS5Padding";
//	public static final String	PROVIDER = "BC";

	public static String encripta(String str, String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] strBytes = str.getBytes(CHARSET);
		byte[] keyBytes = Arrays.copyOfRange(key.getBytes(CHARSET), 0, 16);
		SecretKeySpec sks = new SecretKeySpec(keyBytes, ALGORITHM);
//		Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, sks);

		byte[] cipherText = new byte[cipher.getOutputSize(strBytes.length)];
		int ctLength = cipher.update(strBytes, 0, strBytes.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		String cipherTextStr = new BigInteger(cipherText).toString(32).toUpperCase();
//		String cipherTextStr = new String(cipherText);
//		System.out.println(cipherTextStr);
//		System.out.println(new String(cipherText));

		return cipherTextStr;
	}

	public static String desencripta(String str, String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
//		System.out.println(str);
//		byte[] strBytes = str.getBytes(CHARSET);
		byte[] strBytes = new BigInteger(str, 32).toByteArray();
//		System.out.println(new String(str));
		
		byte[] keyBytes = Arrays.copyOfRange(key.getBytes(CHARSET), 0, 16);
		SecretKeySpec sks = new SecretKeySpec(keyBytes, ALGORITHM);
//		Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);

		int ctLength = strBytes.length; // ???
		cipher.init(Cipher.DECRYPT_MODE, sks);
		byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
		int ptLength = cipher.update(strBytes, 0, ctLength, plainText, 0);
		ptLength += cipher.doFinal(plainText, ptLength);
		String plainTextStr = new String(plainText);
//		System.out.println(plainTextStr);
//		System.out.println(ptLength);

		return plainTextStr;
	}
}
