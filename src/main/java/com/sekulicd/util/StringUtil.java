package com.sekulicd.util;

public class StringUtil {
	public static boolean isStringNullOrEmpty(String str)
	{
		if(str == null || str.isEmpty())
		{
			return true;
		}
		return false;
	}
}
