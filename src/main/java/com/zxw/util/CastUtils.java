package com.zxw.util;

import org.apache.commons.lang3.StringUtils;

public final class CastUtils {
	public static String castString(Object obj) {
		return CastUtils.castString(obj, "");
	}

	private static String castString(Object obj, String defaultValue) {
		return obj != null ? String.valueOf(obj) : defaultValue;
	}

	public static int castInt(Object obj) {
		return CastUtils.castInt(obj, 0);
	}

	private static int castInt(Object obj, int defaultValue) {
		int value = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtils.isNotEmpty(strValue)) {
				value = Integer.parseInt(strValue);
			}
		}
		return value;
	}

	public static boolean castBoolean(Object obj) {
		return CastUtils.castBoolean(obj, false);
	}

	private static boolean castBoolean(Object obj, boolean defaultValue) {
		boolean value = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtils.isNotEmpty(strValue)) {
				value = Boolean.parseBoolean(strValue);
			}
		}
		return value;
	}
}
