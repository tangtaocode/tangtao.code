package net.risesoft.beans.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * @用户基础信息
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
@Entity
@Table(name="SP_APPROVEWEB_USER")
public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2725298141537888805L;
	private String guid;  //用户GUID主键
	private String loginname;  //登陆名称
	private String password;  //密码
	private String usertype;  //用户类型，1个人用户，2企业用户，3机关事业单位
	private String cardid;  //证件号码
	private String mobile;  //手机号码
	private Date creattime;  //注册时间
	private Integer state;  //账户状态  0表示正常状态  1冻结状态
	private String nameremark;  //帐号说明
	private String sxlx;  //申请事项类型1普通事项，2资金扶持类型
	private String cardtype;  //1身份证号，2护照号码,3组织机构代码
	private CompanyUser companyUser;
	private PersonUser personUser;
	private String typeValue;//用户类型，具体值
	private String cardValue;//证件类型，具体值
	
	
	
	private String comuser;
	
	
	@Transient
	public String getComuser() {
		return comuser;
	}
	public void setComuser(String comuser) {
		this.comuser = comuser;
	}
	
	
	@Transient
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	@Transient
	public String getCardValue() {
		return cardValue;
	}
	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}
	@Transient
	public CompanyUser getCompanyUser() {
		return companyUser;
	}
	public void setCompanyUser(CompanyUser companyUser) {
		this.companyUser = companyUser;
	}
	@Transient
	public PersonUser getPersonUser() {
		return personUser;
	}
	public void setPersonUser(PersonUser personUser) {
		this.personUser = personUser;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	@Column
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	@Column
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	@Column
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Column
	public String getNameremark() {
		return nameremark;
	}
	public void setNameremark(String nameremark) {
		this.nameremark = nameremark;
	}
	@Column
	public String getSxlx() {
		return sxlx;
	}
	public void setSxlx(String sxlx) {
		this.sxlx = sxlx;
	}
	@Column
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}


	
	
}
