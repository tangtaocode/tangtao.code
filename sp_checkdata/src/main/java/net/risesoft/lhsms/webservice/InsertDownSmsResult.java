package net.risesoft.lhsms.webservice;

import java.util.ArrayList;
import java.util.List;

/***
 * 
 * @author liujun
 * 短信下发状态类
 */
public class InsertDownSmsResult {
	private String code;
	private String message;
	private List<String> msgid = new ArrayList<String>();
	public List<String> getMsgid() {
		return msgid;
	}
	public void setMsgid(List<String> msgid) {
		this.msgid = msgid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMsgids(){
		StringBuffer msgids  = new StringBuffer();
		for(String ms : msgid){
			msgids.append(ms).append("&");
		}
		if(msgids.length()>0){
			msgids.deleteCharAt(msgids.length()-1);
		}
		return msgids.toString();
	}
}
