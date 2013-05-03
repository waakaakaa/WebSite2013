package cn.zx.website.util;

public class StringUtil {
	public static boolean empty(String str) {
		if (str == null || "".equals(str) || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
}