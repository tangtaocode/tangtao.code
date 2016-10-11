package net.risesoft.beans.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SMS_CONFIG")
public class MessageConfig implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -2271690338609523524L;
	private String guid;  //主键
	private String type;  //短信业务类型
	private String smsname;  //短信环节名称
	private String receiver;  //接收对象
	private String remark;  //发送条件及简要说明
	private String smscontent;  //格式内容
	private String sendtype;  //发送方式。1固定格式，2可编辑......
	private Date uptime;  //更新日期
	private String upperson;  //更新人员主键
	private String smsno;  //短信接口类标识，大写字母，在代码中匹配选取
	private String ori_tel;  //短信机长号
	private String workday;  //工作日。比如网上大厅提交事项后，向窗口人员发送信息提示几个工作日后发出。
	private String flag;  //是否启动。1启动，0关闭
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column
	public String getSmsname() {
		return smsname;
	}
	public void setSmsname(String smsname) {
		this.smsname = smsname;
	}
	@Column
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column
	public String getSmscontent() {
		return smscontent;
	}
	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	@Column
	public String getSendtype() {
		return sendtype;
	}
	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}
	@Column
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	@Column
	public String getUpperson() {
		return upperson;
	}
	public void setUpperson(String upperson) {
		this.upperson = upperson;
	}
	@Column
	public String getSmsno() {
		return smsno;
	}
	public void setSmsno(String smsno) {
		this.smsno = smsno;
	}
	@Column
	public String getOri_tel() {
		return ori_tel;
	}
	public void setOri_tel(String ori_tel) {
		this.ori_tel = ori_tel;
	}
	@Column
	public String getWorkday() {
		return workday;
	}
	public void setWorkday(String workday) {
		this.workday = workday;
	}
	@Column
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
