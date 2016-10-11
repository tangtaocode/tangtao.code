package net.risesoft.common;

import java.util.List;
import java.util.TreeMap;

import net.risesoft.beans.base.CodeMap;
/**
 * @常量
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
public class Common {
	public static final String sessionLoginUserID = "loginUser"; //登录用户信息SessionId
	public static TreeMap<String,TreeMap<String,String>> tmKeyCodeMap; //codeMap 存储Map;
	public static TreeMap<String,List<CodeMap>> tmKeyCodeList; //codeMap 存储list;
	public static final int maxResult = 15; //JDBC分页默认每页显示条数
	public static final String sessionCheckCode = "validateCode";
	public static final String windowsPrefix = "windowsPrefix";
	public static final String linuxPrefix = "linuxPrefix";
	public static final String bizApplyModule = "bizApply";
	public static final String approveItemModule = "approveItem";
	public static final String civilApplyModule = "civilApply";
	public static final String mobileCheckCode = "mobileCheckCode";
	public static final String skipToPageUrl =  "skipToPageUrl";
	public static final String loginSuUrl =  "loginSuccessSkipUrl";
	public static final String userMobile = "userMobile";
	public static final String lxrName = "lxrName";
	public static final String subFlag = "subFlag";
	/**下载文件后缀**/
	public static final String sql = "FileDownSql";
	
	public static final String fileMode = "ModePrefix";
	
	public static String error(String message){
		return "<font color='red'>"+message+"</font>";
	}
	public static String success(String message){
		return "<font color='green'>"+message+"</font>";
	}
	
	
}
