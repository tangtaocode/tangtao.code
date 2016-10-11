package net.risesoft.common.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
	public static String MD5(String str) throws NoSuchAlgorithmException {
		if (str == null || str.length() == 0) {
			return "";
		}
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(str.getBytes());
		byte[] md5Bytes = messageDigest.digest();
		return byte2hex(md5Bytes);
	}

	public static String SHA1(String str) throws NoSuchAlgorithmException {
		if (str == null || str.length() == 0) {
			return "";
		}
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		messageDigest.update(str.getBytes());
		byte[] sha1Bytes = messageDigest.digest();
		return byte2hex(sha1Bytes);
	}

	public static String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

}
