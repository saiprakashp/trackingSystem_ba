package com.egil.pts.util;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.xerces.impl.dv.util.Base64;

/**
 * This class uses DES encryption method to encrypt a given String input.
 * 
 */

public class DesEncrypter {

	private static Cipher ecipher;
	private static Cipher dcipher;

	// 8-byte Salt
	static byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3,
			(byte) 0x03 };

	// Iteration count
	private static int iterationCount = 19;
	public static final String DEFAULT_PASSHRASE = "obfuscation";
	private static String passPhrase = "obfuscation";

	// Default Constructor, uses default pass phrase
	public DesEncrypter() {
		DesEncrypter.passPhrase = DEFAULT_PASSHRASE;
		createCipher();
	}

	public DesEncrypter(String passPhrase) {
		DesEncrypter.passPhrase = passPhrase;
		createCipher();
	}

	private static void createCipher() {
		try {
			// Create the key
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			// Create the ciphers
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	private static void createCipher(String userPassPhrase) {
		try {
			// Create the key
			KeySpec keySpec = null;
			if (userPassPhrase != null && !userPassPhrase.isEmpty()) {
				keySpec = new PBEKeySpec(userPassPhrase.toCharArray(), salt, iterationCount);
			} else {
				keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			}

			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			// Create the ciphers
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	public synchronized static String encrypt(String str) {
		try {
			if (str == null || str.isEmpty()) {
				return null;
			}
			createCipher();
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return Base64.encode(enc);
			// return new sun.misc.BASE64Encoder().encode(enc);
		} catch (Throwable th) {
			// th.printStackTrace();
		}

		return null;
	}

	public synchronized static String encrypt(String str, String userPassPhrase) {
		try {
			if (str == null || str.isEmpty()) {
				return null;
			}
			createCipher(userPassPhrase);
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return Base64.encode(enc);
			// return new sun.misc.BASE64Encoder().encode(enc);
		} catch (Throwable th) {
			th.printStackTrace();
		}

		return null;
	}

	public synchronized static String decrypt(String str) {
		try {
			if (str == null || str.isEmpty()) {
				return null;
			}
			createCipher();
			// Decode base64 to get bytes
			// byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			byte[] dec = Base64.decode(str);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (Throwable th) {
			// th.printStackTrace();
			if (th instanceof IllegalArgumentException)
				return str;

		}

		return null;
	}

	public synchronized static String decrypt(String str, String userPassPhrase) {
		try {
			if (str == null || str.isEmpty()) {
				return null;
			}

			if (userPassPhrase != null && !userPassPhrase.isEmpty()) {
				createCipher(userPassPhrase);
			} else {
				createCipher();
			}
			// Decode base64 to get bytes
			// byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			byte[] dec = Base64.decode(str);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (Throwable th) {
			// th.printStackTrace();
		}

		return null;
	}

}
