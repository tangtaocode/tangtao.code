package net.risesoft.lhsms.webservice;

import java.util.Date;

/**
 * 
 * @author liujun
 * 短信发送
 */

public class SmsSender {
	private String guid;
	private Date sendTime;
	private Integer sendState;//调用下发短信接口状态  0表示成功 -1表示失败
	private String content ;//短信内容
	private String sendUserId;//发送人的guid
	private String msgid;
	private String sendbody;
	private String resultXml;
	private String receiveGuid;
	private String sendStateDes;
	private String batch;
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getSendState() {
		return sendState;
	}
	public void setSendState(Integer sendState) {
		this.sendState = sendState;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getSendbody() {
		return sendbody;
	}
	public void setSendbody(String sendbody) {
		this.sendbody = sendbody;
	}
	public String getResultXml() {
		return resultXml;
	}
	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
	}
	
	public String getReceiveGuid() {
		return receiveGuid;
	}
	public void setReceiveGuid(String receiveGuid) {
		this.receiveGuid = receiveGuid;
	}
	public String getSendStateDes() {
		switch (sendState) {
		case -1:
			sendStateDes = "发送失败";
			break;
		case 0:
			sendStateDes = "短信已下达MAS机";
			break;	
		case 1:
			sendStateDes = "短信已下发接收者手机";
			break;	
		default:
			sendStateDes = "未知状态";
			break;
		}
		return sendStateDes;
	}
	public void setSendStateDes(String sendStateDes) {
		this.sendStateDes = sendStateDes;
	}
}
