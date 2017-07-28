package net.risesoft.approve.risefile.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 通过app传给Filter的上下文参数
 * 参数仅限appInstanceGUID和String类型的参数
 * 因为参数需要传到客户端，以供刷新文件控件时使用。
 * 如果有非String的Object那么就需要序列化，考虑到传入对象的不定性（有可能会传入重量级对象），所以只接受String类型的参数。
 */
public class RiseFileContext {
	
	public static String RiseFileContextConst="RiseFileContext";
	
	private static String KEYMARK="<fcKey>";
	
	private static String VALUEMARK="<fcValue>";

	private String appInstanceGUID;
	
	private String appInstanceGUIDs; //多个会议中用到(福田二期金平宣加)
	
	private String fileboxName;
	
	private String where; //通用附加条件(福田二期金平宣加)
	
	private int majorVersion;
	
	private Map paraMap=new HashMap();
	
	public RiseFileContext(String appInstanceGUID,int majorVersion){
		this.appInstanceGUID=appInstanceGUID;
		this.majorVersion=majorVersion;
	}
	
	public void setParameter(String key,String value){
		// 验证
		// 因为toString要拼成字符串传到客户端，所以key或value中有下列关键字会引起混谣
		if (key.indexOf(KEYMARK) > 0 || key.indexOf(VALUEMARK) > 0) {
			throw new IllegalArgumentException("key:"+key+"中不能包括关键字："+KEYMARK+"或"+VALUEMARK);
		}
		if(value!=null){
			if(value.indexOf(KEYMARK)>0||value.indexOf(VALUEMARK)>0){
				throw new IllegalArgumentException("value:"+value+"中不能包括关键字"+KEYMARK+"或"+VALUEMARK);
			}
			if(value.equals("null")){
				throw new IllegalArgumentException("value:的值不能等于字符串'null'");
			}
		}
		paraMap.put(key,value);
	}
	
	public String getParameter(String key){
		return (String)paraMap.get(key);
	}
	
	/* 
	 * 生成String格式context
	 */
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append(KEYMARK).append("appInstanceGUID").append(VALUEMARK).append(getAppInstanceGUID());
		sb.append(KEYMARK).append("majorVersion").append(VALUEMARK).append(getMajorVersion());
		sb.append(KEYMARK).append("fileboxName").append(VALUEMARK).append(getFileboxName());
		
		String key=null;
		String value=null;
		
		for (Iterator iter = paraMap.keySet().iterator(); iter.hasNext();) {
			key = (String) iter.next();
			value=(String)paraMap.get(key);
			sb.append(KEYMARK).append(key).append(VALUEMARK).append(value);
		}
		return sb.toString();
	}
	

	public static RiseFileContext getFileContext(String contextString){
		RiseFileContext context=null;
		
		String[] contextArray=contextString.split(KEYMARK);
		String appInstanceGUID=contextArray[1].split(VALUEMARK)[1];
		int majorVersion=Integer.parseInt(contextArray[2].split(VALUEMARK)[1]);
		String fileboxName=contextArray[3].split(VALUEMARK)[1];
		
		context=new RiseFileContext(appInstanceGUID,majorVersion);
		context.setFileboxName(fileboxName);
		
		for (int i = 4; i < contextArray.length; i++) {
			String[] ss=contextArray[i].split(VALUEMARK);
			if(ss[1].equals("null")){
				context.setParameter(ss[0],null);
			}else{
				context.setParameter(ss[0],ss[1]);
			}
		}
		return context;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public String getAppInstanceGUID() {
		return appInstanceGUID;
	}

	public void setAppInstanceGUID(String appInstanceGUID) {
		this.appInstanceGUID = appInstanceGUID;
	}
	


	public String getFileboxName() {
		return fileboxName;
	}

	public void setFileboxName(String fileboxName) {
		this.fileboxName = fileboxName;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getWhere() {
		return where;
	}

	public void setAppInstanceGUIDs(String appInstanceGUIDs) {
		this.appInstanceGUIDs = appInstanceGUIDs;
	}

	public String getAppInstanceGUIDs() {
		return appInstanceGUIDs;
	}
	
	


	
}
