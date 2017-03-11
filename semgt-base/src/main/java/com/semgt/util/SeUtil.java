package com.semgt.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SeUtil {

	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else {
			StringBuilder sb = new StringBuilder(s);
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
			return sb.toString();
		}
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else {
			StringBuilder sb = new StringBuilder(s);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		}
	}

	// 判断不为null或空串
	public static boolean isNullOrEmpty(Object obj) {
		if (obj instanceof Object[]) {
			Object[] o = (Object[]) obj;
			for (int i = 0; i < o.length; i++) {
				Object object = o[i];
				if (object instanceof Date) {
					if (object.equals(new Date(0)))
						return true;
				} else if ((object == null) || (("").equals(object))) {
					return true;
				}
			}
		} else {
			if (obj instanceof Date) {
				if (obj.equals(new Date(0))) {
					return true;
				}
			} else if ((obj == null) || (("").equals(obj))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNullOrEmpty(String inStr) {
		return (inStr == null || inStr.trim().length() == 0);
	}
	
	// 补零
	// inStr: 需要补零的字符串
	// length: 设置返回串的总位数
	// forward: true-向前补零 false-向后补零
	public static String zeroize(String inStr, int length, boolean forward) {
		int strLength = inStr.length();
		if (length > strLength) {
			for (int i = 0; i < length-strLength; i++) {
				if(forward) {
					inStr = "0" + inStr;
				} else {
					inStr = inStr + "0";
				}
			}
			return inStr;
		} else {
			return inStr;
		}
	}
	
	// 字符串日期转换格式
	// 如yyyy-MM-dd 转 yyyyMMdd
	// date: 字符串日期
	// oldPattern: date字符串的日期格式
	// newPattern: 要转换成的日期格式
	public static String dateFormat(String date, String oldPattern, String newPattern) throws ParseException {
		SimpleDateFormat oldFormat = new SimpleDateFormat(oldPattern);
		SimpleDateFormat newFormat = new SimpleDateFormat(newPattern);
		java.util.Date d = oldFormat.parse(date);
		newFormat.format(d);
		return newFormat.format(d);
	}
	
	// 特殊yyyy-MM-dd转yyyyMMdd 或 yyyyMMdd转yyyy-MM-dd
	public static String dateFormatSpecial(String date) {
		if(date.contains("-") || date.length()<8) {
			date = date.replace("-", "");
			date = SeUtil.zeroize(date, 8, false);
		} else {
			StringBuffer sb = new StringBuffer(date);
			sb = sb.insert(4, "-").insert(7, "-");
			date = sb.toString().replace("-00", "");
		}
		return date;
	}
}
