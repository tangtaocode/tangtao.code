package net.risesoft.approve.entity.base;

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
	private static int loginFailTimes;//登录失败次数
	private String mima;
	private String source;//数据来源
	private String wsbsdtuid;//网厅uid  用于单点登录
	private String certId;	//CA认证证书oid
	private String loginType;	//用户凭证标识   01 用户名口令  02 证书登录
	private Integer certLoginCount = 0;	//证书登录次数
	private Integer identifystatus;//是否实名认证
	
	
	@Column(length=20)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Transient
	public String getMima() {
		return mima;
	}

	public void setMima(String mima) {
		this.mima = mima;
	}

	@Column(length=50)
	public String getWsbsdtuid() {
		return wsbsdtuid;
	}

	public void setWsbsdtuid(String wsbsdtuid) {
		this.wsbsdtuid = wsbsdtuid;
	}

	public String getCertId() {
		return certId;
	}

	@Column(length=50)
	public void setCertId(String certId) {
		this.certId = certId;
	}

	@Column(length=2)
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Column
	public Integer getIdentifystatus() {
		return identifystatus;
	}

	public void setIdentifystatus(Integer identifystatus) {
		this.identifystatus = identifystatus;
	}

	@Column
	public Integer getCertLoginCount() {
		return certLoginCount;
	}

	public void setCertLoginCount(Integer certLoginCount) {
		this.certLoginCount = certLoginCount;
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

	@Transient
	public int getLoginFailTimes() {
		return loginFailTimes;
	}

	public void setLoginFailTimes(int loginFailTimes) {
		this.loginFailTimes = loginFailTimes;
	}


	
	
}
