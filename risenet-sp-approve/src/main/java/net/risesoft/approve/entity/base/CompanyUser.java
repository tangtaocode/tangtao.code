package net.risesoft.approve.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * @ 企业/组织用户
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
@Entity
@Table(name="T_OUT_COMPANYUSER")
public class CompanyUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -311461653079550227L;
	private String guid;  //用户信息GUID（Q/Z）
	private String logonguid;  //用户登录信息GUID（Q/Z）
	private String name;  //用户登录名称（Q/Z）
	private String ename;  //单位名称/组织名称（Q/Z）
	private String address;  //注册地址（Q/Z）
	private String kind;  //企业性质/组织性质（Q/Z）（Q1.全民所有制企业,2.集体所有制企业,3.有限责任公司,
							//4.股份有限公司,5.中外合资企业,6.中外合作企业,7.外资企业,8.合伙企业,
							//9.个人独资企业,10.私营企业'）（Z1.国家机关2.事业单位3.社会团体4.其他机构	）
	private String regtype;  //注册账号类型2企业，3其它社会组织机构（Q/Z）
	private String industry;  //传真（Q/Z）
	private String orgcode;  //组织机构代码（Q/Z）
	private String lawperson;  //法定代表人 (负责人）（Q/Z）
	private String regcode;  //登记证号/注册号（Q/Z）
	private Date regdate;  //成立时间（Q/Z）
	private String regmoney;  //注册资金
	private String limit;  //经营范围
	private String contactphone;  //联系人电话（Q/Z）
	private String contactmobile;  //联系人手机号码（Q/Z）
	private String email;  //联系人邮箱（Q/Z）
	private String postcode;  //邮编
	private Date register_date;  //注册时间（Q/Z）
	private String totalpeop;  //公司人数/机构人数（Q/Z）
	private String modifyreason;  //修改原因（Q/Z）
	private String lawperphone;  //法人联系人手机
	private String truename;  //联系人姓名（Q/Z）
	private String openadd;  //营业地址
	private String lawphone;  //法人电话（Q/Z）
	private String lawperemail;  //法人邮箱（Q/Z）
	private String dztotalpeop;  //大专以上人数
	private String nationaltax;  //国税纳税编码
	private String taxnum;  //税务登记编号（纳税人识别号）
	private String localtax;  //地税纳税编码
	private String area;  //营业面积
	private String companyabout;  //公司简介/机构简介（Q/Z）
	private String companyaptitudes;  //公司资质/机构资质（Q/Z）
	private String awards;  //获奖情况（Q/Z）
	private String bankname;  //开户银行
	private String banknum;  //银行帐号
	private String accountname;  //账户名称
	private String lowcardid;  //法人身份证（Q/Z）
	private String personcardid;  //联系人身份证（Q/Z）
	private String zgbm;  //主管部门
	private String contactadd;
	private String contactper;
	private String commoneyguid;//上年度收入等信息外键
	private String dataSource; //数据类型 （表示数据来源 织网工程、注册信息、信息中心）
	@Column
	public String getCommoneyguid() {
		return commoneyguid;
	}
	public void setCommoneyguid(String commoneyguid) {
		this.commoneyguid = commoneyguid;
	}
	
	@Column
	public String getContactadd() {
		return contactadd;
	}
	public void setContactadd(String contactadd) {
		this.contactadd = contactadd;
	}
	@Column
	public String getContactper() {
		return contactper;
	}
	public void setContactper(String contactper) {
		this.contactper = contactper;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getLogonguid() {
		return logonguid;
	}
	public void setLogonguid(String logonguid) {
		this.logonguid = logonguid;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	@Column
	public String getRegtype() {
		return regtype;
	}
	public void setRegtype(String regtype) {
		this.regtype = regtype;
	}
	@Column
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	@Column
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	@Column
	public String getLawperson() {
		return lawperson;
	}
	public void setLawperson(String lawperson) {
		this.lawperson = lawperson;
	}
	@Column
	public String getRegcode() {
		return regcode;
	}
	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	@Column
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Column
	public String getRegmoney() {
		return regmoney;
	}
	public void setRegmoney(String regmoney) {
		this.regmoney = regmoney;
	}
	@Column
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	@Column
	public String getContactphone() {
		return contactphone;
	}
	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}
	@Column
	public String getContactmobile() {
		return contactmobile;
	}
	public void setContactmobile(String contactmobile) {
		this.contactmobile = contactmobile;
	}
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	@Column
	public Date getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}
	@Column
	public String getTotalpeop() {
		return totalpeop;
	}
	public void setTotalpeop(String totalpeop) {
		this.totalpeop = totalpeop;
	}
	@Column
	public String getModifyreason() {
		return modifyreason;
	}
	public void setModifyreason(String modifyreason) {
		this.modifyreason = modifyreason;
	}
	@Column
	public String getLawperphone() {
		return lawperphone;
	}
	public void setLawperphone(String lawperphone) {
		this.lawperphone = lawperphone;
	}
	@Column
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	@Column
	public String getOpenadd() {
		return openadd;
	}
	public void setOpenadd(String openadd) {
		this.openadd = openadd;
	}
	@Column
	public String getLawphone() {
		return lawphone;
	}
	public void setLawphone(String lawphone) {
		this.lawphone = lawphone;
	}
	@Column
	public String getLawperemail() {
		return lawperemail;
	}
	public void setLawperemail(String lawperemail) {
		this.lawperemail = lawperemail;
	}
	@Column
	public String getDztotalpeop() {
		return dztotalpeop;
	}
	public void setDztotalpeop(String dztotalpeop) {
		this.dztotalpeop = dztotalpeop;
	}
	@Column
	public String getNationaltax() {
		return nationaltax;
	}
	public void setNationaltax(String nationaltax) {
		this.nationaltax = nationaltax;
	}
	@Column
	public String getTaxnum() {
		return taxnum;
	}
	public void setTaxnum(String taxnum) {
		this.taxnum = taxnum;
	}
	@Column
	public String getLocaltax() {
		return localtax;
	}
	public void setLocaltax(String localtax) {
		this.localtax = localtax;
	}
	@Column
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Column
	public String getCompanyabout() {
		return companyabout;
	}
	public void setCompanyabout(String companyabout) {
		this.companyabout = companyabout;
	}
	@Column
	public String getCompanyaptitudes() {
		return companyaptitudes;
	}
	public void setCompanyaptitudes(String companyaptitudes) {
		this.companyaptitudes = companyaptitudes;
	}
	@Column
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
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
	@Column
	public String getLowcardid() {
		return lowcardid;
	}
	public void setLowcardid(String lowcardid) {
		this.lowcardid = lowcardid;
	}
	@Column
	public String getPersoncardid() {
		return personcardid;
	}
	public void setPersoncardid(String personcardid) {
		this.personcardid = personcardid;
	}
	@Column
	public String getZgbm() {
		return zgbm;
	}
	public void setZgbm(String zgbm) {
		this.zgbm = zgbm;
	}
	@Transient
	public String getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	
}
