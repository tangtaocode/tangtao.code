package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
  * @ClassName: BizUnitBaseInfoBean
  * @Description: 科技创新扶持申请单位基本信息表
  * @author Comsys-zhangkun
  * @date Apr 10, 2013 4:30:46 PM
  *
 */
@Entity
@Table(name="ZJFC_KJ_BASIC")
public class BizUnitBaseInfoBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 308897603480391943L;
	private String guid;  //主键
	private String departmentname;  //单位名称
	private String corporationcode;  //法人代码
	private String departmentadd;  //单位地址
	private String bankname;  //基本户开户银行
	private Date founddate;  //单位成立时间
	private String businesssphere;  //经营范围（按营业执照）
	private String qualification;  //企业资质（□国家级高新技术企业      □市级高新技术企业       □市级软件企业    □互联网企业      □新一代信息技术企业）
	private String lawper;  //法人代表
	private String nationaltax;  //国税登记证号
	private String localtax;  //地税登记证号
	private String regcode;  //营业执照注册号
	private String bankcode;  //帐号
	private String regmoney;  //注册资本
	private String totalpeop;  //员（职）工总数
	private String dztotalpeop;  //其中大专以上人员人数及占数百分比
	private String researchpeop;  //其中研究开发人员人数及占总数百分比
	private String patentnum;  //专利申请	总数
	private String patentinvent;  //发明（专利申请）
	private String patentsurface;  //外观设计	（专利申请）"
	private String empowernum;  //专利授权总数	"""
	private String empowerinvent;  //发明（专利授权）
	private String empowerpractical;  //实用新型	（专利授权）"
	private String empowersurface;  //外观设计（专利授权）"
	private String softwarepower;  //软件著作权
	private String icdevisepower;  //（IC）布图设计专有权
	private String speciespower;  //植物新品种权
	private String patentpractical;  //实用新型（专利申请）"
	private String appguid;  //申请扶持项目GUID
	private Date createtime;  //提交时间
	private String userguid;  //操作用户
	private List<BizShareholderBean> shareholderList;
	private List<BizHistorySubsidize> historySubsidizeList;
	@Transient
	public List<BizShareholderBean> getShareholderList() {
		return shareholderList;
	}
	public void setShareholderList(List<BizShareholderBean> shareholderList) {
		this.shareholderList = shareholderList;
	}
	@Transient
	public List<BizHistorySubsidize> getHistorySubsidizeList() {
		return historySubsidizeList;
	}
	public void setHistorySubsidizeList(
			List<BizHistorySubsidize> historySubsidizeList) {
		this.historySubsidizeList = historySubsidizeList;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	@Column
	public String getCorporationcode() {
		return corporationcode;
	}
	public void setCorporationcode(String corporationcode) {
		this.corporationcode = corporationcode;
	}
	@Column
	public String getDepartmentadd() {
		return departmentadd;
	}
	public void setDepartmentadd(String departmentadd) {
		this.departmentadd = departmentadd;
	}
	@Column
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	@Column
	public Date getFounddate() {
		return founddate;
	}
	public void setFounddate(Date founddate) {
		this.founddate = founddate;
	}
	@Column
	public String getBusinesssphere() {
		return businesssphere;
	}
	public void setBusinesssphere(String businesssphere) {
		this.businesssphere = businesssphere;
	}
	@Column
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	@Column
	public String getLawper() {
		return lawper;
	}
	public void setLawper(String lawper) {
		this.lawper = lawper;
	}
	@Column
	public String getNationaltax() {
		return nationaltax;
	}
	public void setNationaltax(String nationaltax) {
		this.nationaltax = nationaltax;
	}
	@Column
	public String getLocaltax() {
		return localtax;
	}
	public void setLocaltax(String localtax) {
		this.localtax = localtax;
	}
	@Column
	public String getRegcode() {
		return regcode;
	}
	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	@Column
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	@Column
	public String getRegmoney() {
		return regmoney;
	}
	public void setRegmoney(String regmoney) {
		this.regmoney = regmoney;
	}
	@Column
	public String getTotalpeop() {
		return totalpeop;
	}
	public void setTotalpeop(String totalpeop) {
		this.totalpeop = totalpeop;
	}
	@Column
	public String getDztotalpeop() {
		return dztotalpeop;
	}
	public void setDztotalpeop(String dztotalpeop) {
		this.dztotalpeop = dztotalpeop;
	}
	@Column
	public String getResearchpeop() {
		return researchpeop;
	}
	public void setResearchpeop(String researchpeop) {
		this.researchpeop = researchpeop;
	}
	@Column
	public String getPatentnum() {
		return patentnum;
	}
	public void setPatentnum(String patentnum) {
		this.patentnum = patentnum;
	}
	@Column
	public String getPatentinvent() {
		return patentinvent;
	}
	public void setPatentinvent(String patentinvent) {
		this.patentinvent = patentinvent;
	}
	@Column
	public String getPatentsurface() {
		return patentsurface;
	}
	public void setPatentsurface(String patentsurface) {
		this.patentsurface = patentsurface;
	}
	@Column
	public String getEmpowernum() {
		return empowernum;
	}
	public void setEmpowernum(String empowernum) {
		this.empowernum = empowernum;
	}
	@Column
	public String getEmpowerinvent() {
		return empowerinvent;
	}
	public void setEmpowerinvent(String empowerinvent) {
		this.empowerinvent = empowerinvent;
	}
	@Column
	public String getEmpowerpractical() {
		return empowerpractical;
	}
	public void setEmpowerpractical(String empowerpractical) {
		this.empowerpractical = empowerpractical;
	}
	@Column
	public String getEmpowersurface() {
		return empowersurface;
	}
	public void setEmpowersurface(String empowersurface) {
		this.empowersurface = empowersurface;
	}
	@Column
	public String getSoftwarepower() {
		return softwarepower;
	}
	public void setSoftwarepower(String softwarepower) {
		this.softwarepower = softwarepower;
	}
	@Column
	public String getIcdevisepower() {
		return icdevisepower;
	}
	public void setIcdevisepower(String icdevisepower) {
		this.icdevisepower = icdevisepower;
	}
	@Column
	public String getSpeciespower() {
		return speciespower;
	}
	public void setSpeciespower(String speciespower) {
		this.speciespower = speciespower;
	}
	@Column
	public String getPatentpractical() {
		return patentpractical;
	}
	public void setPatentpractical(String patentpractical) {
		this.patentpractical = patentpractical;
	}
	@Column
	public String getAppguid() {
		return appguid;
	}
	public void setAppguid(String appguid) {
		this.appguid = appguid;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column
	public String getUserguid() {
		return userguid;
	}
	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

}
