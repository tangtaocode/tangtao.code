package net.risesoft.lhsms.webservice;

public class Smsresult {
	private String msgid;
	private String status;
	private String msgstatus;
	private String resultmsg;
	private String senttime;
	private String reserve;
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsgstatus() {
		return msgstatus;
	}
	public void setMsgstatus(String msgstatus) {
		this.msgstatus = msgstatus;
	}
	public String getResultmsg() {
		return resultmsg;
	}
	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}
	public String getSenttime() {
		return senttime;
	}
	public void setSenttime(String senttime) {
		this.senttime = senttime;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
}
