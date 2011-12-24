package com.rossio.search.util;

public class StringHelper {
	
	private StringHelper() {
	}

	public static String trim(final String str) {
		if (str == null) {
			return null;
		}
		return str.trim();
	}

	public static String trimToNull(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		if ("".equals(str)) {
			return null;
		}
		return str;
	}

	public static boolean isBlank(String target) {
		int strLen;
		if (target == null || (strLen = target.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(target.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static String emptyAndNull2Replacement(final String src,
			final String replacement) {
		if (isBlank(src)) {
			return replacement;
		}
		return src;
	}

	public static String nl2br(String input) {
		if (input == null)
			return null;
		return input.replaceAll("\n", "<br>");
	}

	public static String replace(String src, String regex, String replacement) {
		if (isBlank(src) || (regex == null) || (replacement == null)) {
			return "";
		}
		int i = src.indexOf(regex);
		while (i != -1) {
			StringBuffer sBuffer = new StringBuffer(src);
			sBuffer.replace(i, i + regex.length(), replacement);
			src = sBuffer.toString();
			i = src.indexOf(regex, i + replacement.length());
		}
		return src;
	}

	public static String replace(String src, String regex, int replacement) {
		return replace(src, regex, String.valueOf(replacement));
	}

	public static String replace(String src, String regex, long replacement) {
		return replace(src, regex, String.valueOf(replacement));
	}

	public static boolean isNull(String src) {
		if (src == null)
			return true;
		else
			return false;
	}

	public static String substring(String src, int beginIndex, int endIndex) {
		if (isNull(src))
			return null;
		if (beginIndex < 0)
			return src.substring(0, endIndex);

		if (endIndex > src.length())
			return src.substring(beginIndex, src.length());

		return src.substring(beginIndex, endIndex);
	}
}
