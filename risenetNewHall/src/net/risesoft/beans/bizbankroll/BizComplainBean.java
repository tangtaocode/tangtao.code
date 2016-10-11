package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="ZJFC_COMPLAIN")
public class BizComplainBean implements Serializable{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 2261192945856372872L;
	private String guid;  //GUID主键
	private String username;  //投诉人名称
	private String telephone;  //联系电话
	private String mobile;  //联系手机
	private String email;  //邮箱
	private String title;  //标题
	private String content;  //内容
	private Date complaindate;  //投诉时间
	private String complaintype;  //0网上投诉；1电话投诉；2来信投诉；3来访投诉；
	private String projectguid;  //项目GUID
	private String isxf;  //是否下放，0未下放，1下放
	private Date xftime;  //下放时间
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public Date getComplaindate() {
		return complaindate;
	}
	public void setComplaindate(Date complaindate) {
		this.complaindate = complaindate;
	}
	@Column
	public String getComplaintype() {
		return complaintype;
	}
	public void setComplaintype(String complaintype) {
		this.complaintype = complaintype;
	}
	@Column
	public String getProjectguid() {
		return projectguid;
	}
	public void setProjectguid(String projectguid) {
		this.projectguid = projectguid;
	}
	@Transient
	public String getIsxf() {
		return isxf;
	}
	public void setIsxf(String isxf) {
		this.isxf = isxf;
	}
	@Transient
	public Date getXftime() {
		return xftime;
	}
	public void setXftime(Date xftime) {
		this.xftime = xftime;
	}

}
