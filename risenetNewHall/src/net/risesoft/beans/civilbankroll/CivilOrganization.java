package net.risesoft.beans.civilbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MSXM_ORGANIZATION")
public class CivilOrganization implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -3125177273962805637L;
	private String guid;  //
	private String name;  //单位名称
	private String address;  //单位地址
	private String bankcode;  //开户账号
	private String accountname;  //户名
	private String bankname;  //开户银行
	private String head;  //负责人
	private String telephone;  //办公电话
	private String mobilephone;  //手机号码
	private String mail;  //电子邮箱
	private String zipcode;  //邮编
	private String organizationcode;  //机构代码
	private String companydes;  //单位简介
	private String oldproject;  //参与项目
	private String organizationtype;  //1全日制大学或学院，2公办、民办研究机构，3评估机构
	private Date createdate;  //创建时间
	private Date updatedate;  //更新时间
	private String status;  //0退回，1暂存，2已提交（办公室待审），3送领导小组审批，4审批通过，5审批不通过
	private String password;  //密码
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	@Column
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	@Column
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	@Column
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	@Column
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	@Column
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Column
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	@Column
	public String getOrganizationcode() {
		return organizationcode;
	}
	public void setOrganizationcode(String organizationcode) {
		this.organizationcode = organizationcode;
	}
	@Column
	public String getCompanydes() {
		return companydes;
	}
	public void setCompanydes(String companydes) {
		this.companydes = companydes;
	}
	@Column
	public String getOldproject() {
		return oldproject;
	}
	public void setOldproject(String oldproject) {
		this.oldproject = oldproject;
	}
	@Column
	public String getOrganizationtype() {
		return organizationtype;
	}
	public void setOrganizationtype(String organizationtype) {
		this.organizationtype = organizationtype;
	}
	@Column
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
