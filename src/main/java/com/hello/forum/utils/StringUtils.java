package com.hello.forum.utils;

public class StringUtils {

	public static boolean correctPasswordFormat(final String str) {
		if (isEmpty(str)) {
			return false;
		}
		
		String passwordFormat = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
		return str.matches(passwordFormat);
	}
	
	public static String replaceTagSymbols(final String str) {
		return nullToValue(str, "").replace(">", "&gt;")
					.replace("<", "&lt;")
					.replace(" ", "&nbsp;");
	}
	
	public static String nullToValue(final String str, final String nullValue) {
		if (isEmpty(str)) {
			return nullValue;
		}
		
		return str;
	}
	
	public static boolean isEmpty(final String str) {
		return str == null || str.trim().length() == 0;
	}
	
	public static boolean isEnough(final String str, final int minLength) {
		if (isEmpty(str)) {
			return false;
		}
		
		return trim(str).length() >= minLength;
	}
	
	public static String trim(final String str) {
		if (isEmpty(str)) {
			return null;
		}
		
		return str.trim();
	}
	
	public static boolean isEmailFormat(final String str) {
		if (isEmpty(str)) {
			return false;
		}
		
		String emailPattern = "/[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/g";
		return str.matches(emailPattern);
	}
	
	public static boolean isPhoneFormat(final String str) {
		if (isEmpty(str)) {
			return false;
		}
		
		String phoneFormat = "\\d{2,4}( |-|\\.)(\\d{4}( |-|\\.)\\d{4})";
		return str.matches(phoneFormat);
	}
	
}
