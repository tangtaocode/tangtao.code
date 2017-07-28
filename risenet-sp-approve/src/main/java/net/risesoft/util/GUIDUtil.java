/**
 * Copyright (c) 2003 RiseSoft Co.,Ltd
 * $Header$
 */
package net.risesoft.util;

/**
 * @author 赵斌 2003-7-6
 * @version $Revision$
 */


public final class GUIDUtil {
//  private static org.apache.log4j.Logger log = net.risesoft.commons.log.LogFactory.getLog(GUIDUtil.class);
  private GUIDUtil() {
  }

  /**
   * 判断是否为合法的GUID
   * @param guid
   * @return boolean
   */
  public static boolean inValidate(String guid) {
    if (guid == null || guid.length() != 38) {
      return true;
    }
    //log.debug("GUID错误！", new Exception());
    return false;
  }

  /**
   * 将32位格式的GUID格式化为38位标准格式
   * 609538D772EAD70180BB0050BAE77EF6  --> {609538D7-72EA-D701-80BB-0050BAE77EF6}
   * @param s String
   * @return String
   */
  public static String formatGUID32To38(String s) {
    if (s == null || s.length() != 32) {
      return s;
    }
    StringBuffer sb = new StringBuffer("{");
    sb.append(s.substring(0, 8));
    sb.append("-");
    sb.append(s.substring(8, 12));
    sb.append("-");
    sb.append(s.substring(12, 16));
    sb.append("-");
    sb.append(s.substring(16, 20));
    sb.append("-");
    sb.append(s.substring(20));
    sb.append("}");

    return sb.toString();
  }

  public static String formatGUID38To32(String s) {
    if (inValidate(s)) {
      return s;
    }
    StringBuffer sb = new StringBuffer("");
    sb.append(s.substring(1, 9));
    sb.append(s.substring(10, 14));
    sb.append(s.substring(15, 19));
    sb.append(s.substring(20, 24));
    sb.append(s.substring(25,37));

    return sb.toString();
  }
  
  /**
   * 尽量格式化为38位GUID
   * @param s
   * @return String
   */
  public static String formatTo38(String s) {
    if (s == null)
      return null;
    
    if (inValidate(s) && s.length() == 32){
      return formatGUID32To38(s);
    }
    else{
      return s;
    }
  }
}
