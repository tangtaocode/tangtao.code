package net.risesoft.approve.entity.jpa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SMS_CONFIG")
public class SmsConfig{
	private String guid;
	private String type;
	private String smsname;
	private String receiver;
	private String remark;
	private String smscontent;
	private String sendtype;
	private Date uptime;
	private String upperson;
	private String smsno;
	private String ori_tel;
	private String workday;
	private String flag;
	
	@Id
	@Column(name="guid",unique=true,nullable=false, length=38)
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column(name="flag", length=5)
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name="workday", length=5)
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
	}
	@Column(name="type", length=5)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="smsname", length=200)
	public String getSmsname() {
		return smsname;
	}
	public void setSmsname(String smsname) {
		this.smsname = smsname;
	}
	@Column(name="receiver", length=200)
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	@Column(name="remark", length=2000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="smscontent", length=2000)
	public String getSmscontent() {
		return smscontent;
	}
	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	@Column(name="sendtype", length=5)
	public String getSendtype() {
		return sendtype;
	}
	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}
	@Column(name="uptime")
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	@Column(name="upperson", length=38)
	public String getUpperson() {
		return upperson;
	}
	public void setUpperson(String upperson) {
		this.upperson = upperson;
	}
	@Column(name="smsno", length=200)
	public String getSmsno() {
		return smsno;
	}
	public void setSmsno(String smsno) {
		this.smsno = smsno;
	}
	@Column(name="ori_tel", length=200)
	public String getOri_tel() {
		return ori_tel;
	}
	public void setOri_tel(String ori_tel) {
		this.ori_tel = ori_tel;
	}
	
}
