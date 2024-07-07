package com.ace.exames.core.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class HashUtils {
	public static String toMD5(String text) {
		return DigestUtils.md5Hex(text).toUpperCase();
	}
	
	public static boolean textEqualsMD5Hash(String text, String hash) {
		String textHash =  HashUtils.toMD5(text);
		
		return textHash.equals(hash);
	}
}