package net.risesoft.common.util;

public class Util {
	
	  /***
	   * @author wjm
	   * @param obj
	   * @return
	   */
	  
	  public final static String filterNull(Object obj){
		  if(obj == null){
			  return "";
		  }
		  String str = "" + obj;
		  if("null".equals(str)){
			  return "";
		  }
		  return str;
	  }
	  
}
