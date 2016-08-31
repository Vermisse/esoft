package com.soft.util;

import java.security.*;

/**
 * 文本处理工具
 * @author vermisse
 */
public class Text {

	/**
	 * 转换为Unicode
	 * @param text
	 * @return
	 */
	public static String convertUnicode(String text) {
		StringBuffer sb = new StringBuffer();
		for (char t : text.toCharArray())
			sb.append("\\u").append(Integer.toHexString(t));
		return sb.toString();
	}

	/**
	 * 转换为Ascii
	 * 
	 * @param text
	 * @return
	 */
	public static String convertAscii(String text) {
		StringBuffer sb = new StringBuffer();
		for (int t : text.toCharArray())
			sb.append("&#").append(t).append(";");
		return sb.toString();
	}

	/**
	 * 创建随机字符串(包含数字及大小写字母)
	 * 
	 * @param length
	 * @return
	 */
	public static String randomText(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int tmp = (int) (Math.random() * 62);
			sb.append((char) (tmp < 26 ? tmp + 65 : tmp < 52 ? tmp + 71 : tmp - 4));
		}
		return sb.toString();
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 转换字符串
	 */
	private static String encode(String algorithm, String str) {
		if (str == null)
			return null;

		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 转换规则
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);

		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * 提取MD5校验码
	 */
	public static String md5(String str) {
		return encode("MD5", str);
	}

	/**
	 * 提取SHA1校验码
	 */
	public static String sha1(String str) {
		return encode("SHA1", str);
	}

	/**
	 * 提取CRC32校验码
	 */
	public static String crc32(String str) {
		java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
		crc32.update(str.getBytes());
		return Long.toHexString(crc32.getValue());
	}
}