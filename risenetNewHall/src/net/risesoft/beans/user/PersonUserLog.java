package net.risesoft.beans.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
/**
 * @个人用户
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
@Entity
@Table(name="T_OUT_PERSONUSER_LOG")
public class PersonUserLog implements Serializable{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -1859891185680040423L;
	/**
	 * 
	 */
	private String user_guid;  //主键
	private String logonguid;  //用户登录信息GUID
	private String true_name;  //真实姓名
	private String sex;  //性别
	private String age;  //年龄
	private String native_add;  //现居住地址
	private String nation;  //民族
	private String metier;  //职业
	private Date birth_date;  //出生日期
	private String polity;  //政治面貌
	private String ifmarry;  //婚姻状况
	private String idcard_type;  //证件类型
	private String idcard_code;  //证件号码
	private String grad_chool;  //毕业学校
	private Date grad_date;  //毕业时间
	private String schoolage;  //学历
	private String reg_add;  //户口所在地
	private String home_add;  //籍贯
	private String mobile;  //手机号码
	private String postcode;  //邮编
	private String email;  //邮箱
	private String work_company;  //工作单位
	private String work_add;  //单位地址
	private String work_title;  //职称
	private Date register_date;  //创建时间
	private String work_tel;  //办公电话
	private String contact_tel;  //联系电话
	private String contact_add;  //联系地址
	private String modifyreason;  //修改理由
	private String bankname;  //开户银行
	private String banknum;  //银行帐号
	private String accountname;  //账户名称
	private String work_duty;
	private String logguid;//日志主键
	private Date logdate;//日志时间
	
	@Id
	public String getLogguid() {
		return logguid;
	}
	public void setLogguid(String logguid) {
		this.logguid = logguid;
	}
	@Column
	public Date getLogdate() {
		return logdate;
	}
	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}
	@Column
	public String getWork_duty() {
		return work_duty;
	}
	public void setWork_duty(String work_duty) {
		this.work_duty = work_duty;
	}
	@Column
	public String getUser_guid() {
		return user_guid;
	}
	public void setUser_guid(String user_guid) {
		this.user_guid = user_guid;
	}
	@Column
	public String getLogonguid() {
		return logonguid;
	}
	public void setLogonguid(String logonguid) {
		this.logonguid = logonguid;
	}
	@Column
	public String getTrue_name() {
		return true_name;
	}
	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}
	@Column
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Column
	public String getNative_add() {
		return native_add;
	}
	public void setNative_add(String native_add) {
		this.native_add = native_add;
	}
	@Column
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	@Column
	public String getMetier() {
		return metier;
	}
	public void setMetier(String metier) {
		this.metier = metier;
	}
	@Column
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	@Column
	public String getPolity() {
		return polity;
	}
	public void setPolity(String polity) {
		this.polity = polity;
	}
	@Column
	public String getIfmarry() {
		return ifmarry;
	}
	public void setIfmarry(String ifmarry) {
		this.ifmarry = ifmarry;
	}
	@Column
	public String getIdcard_type() {
		return idcard_type;
	}
	public void setIdcard_type(String idcard_type) {
		this.idcard_type = idcard_type;
	}
	@Column
	public String getIdcard_code() {
		return idcard_code;
	}
	public void setIdcard_code(String idcard_code) {
		this.idcard_code = idcard_code;
	}
	@Column
	public String getGrad_chool() {
		return grad_chool;
	}
	public void setGrad_chool(String grad_chool) {
		this.grad_chool = grad_chool;
	}
	@Column
	public Date getGrad_date() {
		return grad_date;
	}
	public void setGrad_date(Date grad_date) {
		this.grad_date = grad_date;
	}
	@Column
	public String getSchoolage() {
		return schoolage;
	}
	public void setSchoolage(String schoolage) {
		this.schoolage = schoolage;
	}
	@Column
	public String getReg_add() {
		return reg_add;
	}
	public void setReg_add(String reg_add) {
		this.reg_add = reg_add;
	}
	@Column
	public String getHome_add() {
		return home_add;
	}
	public void setHome_add(String home_add) {
		this.home_add = home_add;
	}
	@Column
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column
	public String getWork_company() {
		return work_company;
	}
	public void setWork_company(String work_company) {
		this.work_company = work_company;
	}
	@Column
	public String getWork_add() {
		return work_add;
	}
	public void setWork_add(String work_add) {
		this.work_add = work_add;
	}
	@Column
	public String getWork_title() {
		return work_title;
	}
	public void setWork_title(String work_title) {
		this.work_title = work_title;
	}
	@Column
	public Date getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}
	@Column
	public String getWork_tel() {
		return work_tel;
	}
	public void setWork_tel(String work_tel) {
		this.work_tel = work_tel;
	}
	@Column
	public String getContact_tel() {
		return contact_tel;
	}
	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}
	@Column
	public String getContact_add() {
		return contact_add;
	}
	public void setContact_add(String contact_add) {
		this.contact_add = contact_add;
	}
	@Lob 
	@Basic(fetch = FetchType.EAGER) 
	@Column(name="MODIFYREASON", columnDefinition="CLOB", nullable=true) 
	public String getModifyreason() {
		return modifyreason;
	}
	public void setModifyreason(String modifyreason) {
		this.modifyreason = modifyreason;
	}
	@Column
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	@Column
	public String getBanknum() {
		return banknum;
	}
	public void setBanknum(String banknum) {
		this.banknum = banknum;
	}
	@Column
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	
}
