package net.risesoft.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	public final static int includeChartCount(String source, String part){
		if(source == null || part == null){
			return 0;
		}
		String str = source.replaceAll(part, "");
		return source.length() - str.length();
	}
	
	public final static boolean isMobileNumber(Object obj){
		if(obj == null){
			return false;
		}
		String str = "" + obj;
		String regExp = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);  

		return m.find();
	}

}
