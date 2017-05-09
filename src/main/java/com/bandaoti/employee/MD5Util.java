package com.bandaoti.employee;

import java.security.MessageDigest;
/**
 * Md5工具
 * @author XiRuiQiang
 *
 */
public class MD5Util {
	public static final int ENCODING_NUM=10;
	/***
	 * MD5加码 生成32位大写md5码
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();

	}

	public static String charEnCoding(String value){
		String kValue="";
		for(int i=0;i<value.length();i++){
			char c=value.charAt(i);
			c+=ENCODING_NUM;
			kValue+=c;
		}
		return kValue;
	}
	public static String charDeCoding(String value){
		String cc="";
		for(int i=0;i<value.length();i++){
			char c=value.charAt(i);
			c-=ENCODING_NUM;
			cc+=c;
		}
		return cc;
	}
}