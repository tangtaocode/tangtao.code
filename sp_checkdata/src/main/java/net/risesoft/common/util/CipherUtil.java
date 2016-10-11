package net.risesoft.common.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class CipherUtil {
	
	public static String generatePassword(String inputStr){
		return encodeByMD5(inputStr);
	}
	
	public static boolean validatePassword(String pw, String inputStr){
		if(pw == null)
			return false;
		return pw.equals(encodeByMD5(inputStr));
	}
	
	private static String encodeByMD5(String originString){
		if(originString != null){
			try{
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte [] results = md.digest(originString.getBytes());
				return Base64.encodeBase64String(results);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return null;
	}

}
