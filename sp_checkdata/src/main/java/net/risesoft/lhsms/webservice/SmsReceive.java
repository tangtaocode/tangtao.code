package net.risesoft.lhsms.webservice;
import java.util.Date;


/**
 * 
 * @author liujun
 * 短信发送
 */
public class SmsReceive {
	private String guid;
	private Integer receiveState;//接收状态 0 短信已下发移动MSA机器 -1 接收失败 1短信已成功送达手机
	private Date receiveTime;//接收时间
	//private String senderGuid;//发送方guid
	private String msgid;//
//	private String mobile;
//	private String msgstatus;
//	private String resultmsg;
//	private String senttime;
//	private String reserve;
//	private String content;
//	private String senderName;
//	private Date sendTime;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Integer getReceiveState() {
		return receiveState;
	}
	public void setReceiveState(Integer receiveState) {
		this.receiveState = receiveState;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
//	public String getSenderGuid() {
//		return senderGuid;
//	}
//	public void setSenderGuid(String senderGuid) {
//		this.senderGuid = senderGuid;
//	}
//	public String getMobile() {
//		return mobile;
//	}
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//	public String getMsgstatus() {
//		return msgstatus;
//	}
//	public void setMsgstatus(String msgstatus) {
//		this.msgstatus = msgstatus;
//	}
//	public String getResultmsg() {
//		return resultmsg;
//	}
//	public void setResultmsg(String resultmsg) {
//		this.resultmsg = resultmsg;
//	}
//	public String getSenttime() {
//		return senttime;
//	}
//	public void setSenttime(String senttime) {
//		this.senttime = senttime;
//	}
//	public String getReserve() {
//		return reserve;
//	}
//	public void setReserve(String reserve) {
//		this.reserve = reserve;
//	}
//	public String getContent() {
//		return content;
//	}
//	public void setContent(String content) {
//		this.content = content;
//	}
//	public String getSenderName() {
//		return senderName;
//	}
//	public void setSenderName(String senderName) {
//		this.senderName = senderName;
//	}
//	public Date getSendTime() {
//		return sendTime;
//	}
//	public void setSendTime(Date sendTime) {
//		this.sendTime = sendTime;
//	}
	
}
