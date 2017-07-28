package net.risesoft.approve.entity.jpa;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * TOutCompanyuserId entity.
 * 个人用户表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_out_personuser")
public class TOutPersonuser  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="USER_GUID",length=38,nullable=false)
	private String userGuid; //主键
	@Column(name="LOGONGUID",length=38,nullable=false)
	private String logonGuid;//用户guid
	@Column(name="TRUE_NAME",length=38)
	private String trueName;//真实姓名
	@Column(name="NATIVE_ADD",length=2000)
	private String nativeAdd;//现居住地址
	@Column(name="IDCARD_TYPE",length=30)
	private String idcardType;//证件类型
	@Column(name="IDCARD_CODE",length=30)
	private String idcardCode;//证件号码
	@Column(name="METIER",length=38)
	private String mobile;//手机号码
	@Column(name="POSTCODE",length=10)
	private String postCode;//邮编
	@Column(name="EMAIL",length=50)
	private String email;//邮箱
	@Column(name="WORK_COMPANY",length=100)
	private String workCompany;//工作单位
	@Column(name="WORK_ADD",length=100)
	private String workAdd;//工作地址
	@Column(name="WORK_DUTY",length=30)
	private String workDuty;//居住证号码
	@Column(name="CONTACT_TEL",length=30)
	private String contactTel;//联系电话
	@Column(name="CONTACT_ADD",length=38)
	private String contactAdd;//联系地址
	public String getUserGuid() {
		return userGuid;
	}
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	public String getLogonGuid() {
		return logonGuid;
	}
	public void setLogonGuid(String logonGuid) {
		this.logonGuid = logonGuid;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getNativeAdd() {
		return nativeAdd;
	}
	public void setNativeAdd(String nativeAdd) {
		this.nativeAdd = nativeAdd;
	}
	public String getIdcardType() {
		return idcardType;
	}
	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}
	public String getIdcardCode() {
		return idcardCode;
	}
	public void setIdcardCode(String idcardCode) {
		this.idcardCode = idcardCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWorkCompany() {
		return workCompany;
	}
	public void setWorkCompany(String workCompany) {
		this.workCompany = workCompany;
	}
	public String getWorkAdd() {
		return workAdd;
	}
	public void setWorkAdd(String workAdd) {
		this.workAdd = workAdd;
	}
	public String getWorkDuty() {
		return workDuty;
	}
	public void setWorkDuty(String workDuty) {
		this.workDuty = workDuty;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getContactAdd() {
		return contactAdd;
	}
	public void setContactAdd(String contactAdd) {
		this.contactAdd = contactAdd;
	}
	
	
	

}