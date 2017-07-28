package net.risesoft.approve.risefile.commons;

import java.util.Iterator;
import java.util.List;

public class StringUtil {
	
	/**
	 * 不""转化成null
	 * @param origString
	 * @return
	 */
	public static String parseNull(String origString){
		if(origString==null||origString.equals("")){
			return null;
		}else{
			return origString;
		}
		
	}
	
	public static String list2String(List l){
		String s="";
		for (Iterator iter = l.iterator(); iter.hasNext();) {
			Object o = (Object) iter.next();
			s=s+o.toString()+";";
		}
		if(!s.equals("")){
			s=s.substring(0,s.length()-1);
		}
		return s;
	}
}
