package com.sctuopuyi.echo.utils.security;


import com.sctuopuyi.echo.utils.JsonType;

import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author chendi
 *
 */
public class EncryptUtils {
	private static final String TAG ="EncryptUtils";
	private static final String privateKey = "www.wkllme.com";

	public static String decrypt(String encryptedPwd) {
		try {
			DESKeySpec keySpec = new DESKeySpec(privateKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			byte[] encryptedWithoutB64 = Base64Utils.decode(encryptedPwd);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
			return new String(plainTextPwdBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedPwd;
	}
	
	public static String encrypt(Map<String, String> mapContent) {
		try {
			String jsonStr = JsonType.map2JsonStr(mapContent);

			DESKeySpec keySpec = new DESKeySpec(privateKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			String encrypedPwd = Base64Utils.encode(cipher.doFinal(jsonStr.getBytes("UTF-8")));
			return encrypedPwd;
		} catch (Exception e) {
		}
		return null;
	}

	public static String encrypt(Map<String, String> mapContent,
			String courseTypeJson) {
		try {
			String jsonStr = JsonType.map2JsonStr(mapContent);
			jsonStr = (jsonStr.substring(0, jsonStr.length() - 1) + ',' + courseTypeJson
					.substring(1, courseTypeJson.length()));
			DESKeySpec keySpec = new DESKeySpec(privateKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			String encrypedPwd = Base64Utils.encode(cipher.doFinal(jsonStr
					.getBytes("UTF-8")));
			return encrypedPwd;
		} catch (Exception e) {
		}
		return null;
	}

	public static String encrypt(String clearText) {
		try {
			DESKeySpec keySpec = new DESKeySpec(privateKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			String encrypedPwd = Base64Utils.encode(cipher.doFinal(clearText
					.getBytes("UTF-8")));
			// .encodeToString(cipher.doFinal(clearText.getBytes("UTF-8")),
			// Base64.DEFAULT);
			return encrypedPwd;
		} catch (Exception e) {
		}
		return null;
	}
}