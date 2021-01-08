package com.kenrou.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

public class MD5Utils {

	/**
	 * 
	 * @Title: MD5Utils.java
	 * @Package com.imooc.utils
	 * @Description: 对字符串进行md5加密
	 */
	public static String getMD5Str(String strValue) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		return newstr;
	}

	public static void main(String[] args) {
		try {
			String tokenStr = "1610080482" + "&mp" + "1" + "&mp" + "m*68+098_q1";
//			String md5 = getMD5Str( "1610080482" + "&mp" + "1" + "&mp" + "m*68+098_q1");
			String md5 = DigestUtils.md5DigestAsHex(tokenStr.getBytes());
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
