package com.easyjet.ei.commercials.claims.common;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;


public class AESEncryption {
	private static Logger logger = Logger.getLogger(AESEncryption.class);
	private AESEncryption(){
		
	}

	public static String decryptText(String byteCipherText, SecretKey secKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.DECRYPT_MODE, secKey);
		byte[] bytePlainText = aesCipher.doFinal(Base64.getDecoder().decode(byteCipherText.getBytes()));
		return new String(bytePlainText).trim();
	}

	public static String encryptText(String plainText, SecretKey secKey) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
		Cipher aesCipher = Cipher.getInstance("AES");

		aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
		byte[] byteCipherText = Base64.getEncoder().encode(aesCipher.doFinal(plainText.getBytes()));
		return new String(byteCipherText);

	}

	public static SecretKeySpec getAesKey(byte[] secKey) {

		SecretKeySpec secretKey = new SecretKeySpec(secKey, "AES");

		return secretKey;
	}
	public static void main(String argv[]){
		
		try {
			//System.out.println(encryptText("Vd12@Jet", getAesKey(Base64.getDecoder().decode("U2VjcmV0UHJvZEBFSTEyMw==".getBytes()))));
			System.out.println(decryptText("COov08tf7fnc7oDtpaL4fg==", getAesKey(Base64.getDecoder().decode("U2VjcmV0UHJvZEBFSTEyMw==".getBytes()))));
			
		} catch (InvalidKeyException  e) {
			//logger.error(message);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}catch ( NoSuchAlgorithmException e) {
			//logger.error(message);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}catch (NoSuchPaddingException  e) {
			//logger.error(message);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}catch ( IllegalBlockSizeException e) {
			//logger.error(message);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}catch ( BadPaddingException e) {
			//logger.error(message);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

}
