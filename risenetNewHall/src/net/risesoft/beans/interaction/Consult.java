package net.risesoft.beans.interaction;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: Consult
  * @Description: 咨询
  * @author Comsys-zhangkun
  * @date Jun 21, 2013 6:51:36 PM
  *
 */
@Entity
@Table(name="SPM_CONSULTATION")
public class Consult implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -8615038574998866152L;
	private String consultationguid;  //主键PK
	private String phone;  //咨询人电话
	private String mobile;  //咨询人手机号
	private String email;  //咨询人email
	private String address;  //咨询人地址
	private String title;  //咨询标题
	private String content;  //咨询内容
	private String username;  //咨询人姓名
	private Date consultationdate;  //咨询时间
	private String resstatus;  //回复状态
	private String recontent;  //回复内容
	private String bureau;  //部门编号
	private String bureauname;  //部门名称
	private String approveitem;  //事项编号
	private Date responsedate;  //回复时间
	private String responseperson;  //回复人
	@Id
	public String getConsultationguid() {
		return consultationguid;
	}
	public void setConsultationguid(String consultationguid) {
		this.consultationguid = consultationguid;
	}
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column
	public Date getConsultationdate() {
		return consultationdate;
	}
	public void setConsultationdate(Date consultationdate) {
		this.consultationdate = consultationdate;
	}
	@Column
	public String getResstatus() {
		return resstatus;
	}
	public void setResstatus(String resstatus) {
		this.resstatus = resstatus;
	}
	@Column
	public String getRecontent() {
		return recontent;
	}
	public void setRecontent(String recontent) {
		this.recontent = recontent;
	}
	@Column
	public String getBureau() {
		return bureau;
	}
	public void setBureau(String bureau) {
		this.bureau = bureau;
	}
	@Column
	public String getBureauname() {
		return bureauname;
	}
	public void setBureauname(String bureauname) {
		this.bureauname = bureauname;
	}
	@Column
	public String getApproveitem() {
		return approveitem;
	}
	public void setApproveitem(String approveitem) {
		this.approveitem = approveitem;
	}
	@Column
	public Date getResponsedate() {
		return responsedate;
	}
	public void setResponsedate(Date responsedate) {
		this.responsedate = responsedate;
	}
	@Column
	public String getResponseperson() {
		return responseperson;
	}
	public void setResponseperson(String responseperson) {
		this.responseperson = responseperson;
	}

}
