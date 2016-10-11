package net.risesoft.beans.onlineservice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: ApproveItemProvince
  * @Description: 审批事项省扩展信息
  * @author Comsys-zhangkun
  * @date Jun 13, 2013 4:41:54 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_PROVINCE")
public class ApproveItemProvince implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -6477459411951294342L;
	private String itemid;  //事项ID
	private String administrativeservicesubject;  //行政审批改革事项
	private String authoritylevel;  //事权级别(代码值)：参照数据字典【行政审批-事权级别】
	private String mainunittype;  //办理主体类型(代码值)：参照数据字典【行政审批-办理主体类型】
	private String serviceobject;  //办理对象
	private String windowworkflow;  //窗口办理流程
	private String powerworkflow;  //权力运行流程
	private String windowaddress;  //办事窗口信息:办理该服务事项的办事窗口信息，包括窗口名称、地址、联系电	话、交通指引、地图等。"
	private String complaintshotline;  //监督电话
	private String faq;  //常见问题解答
	private String onlineapplyingservice;  //在线申办服务：0否、1是
	private String onlineapplyingwebsite;  //在线申办服务网址
	private String businessconsultingservice;  //业务咨询服务：0否、1是
	private String businessconsultingwebsite;  //业务咨询服务网址
	private String resultinquiryingservice;  //结果查询服务：0否、1是
	private String resultinquiryingwebsite;  //结果查询服务网址
	private String progressinquiryingservice;  //进度查询服务：0否、1是
	private String progressinquiryingwebsite;  //进度查询服务网址
	private String payfeesonlineservice;  //网上缴费服务：0否、1是
	private String foreignservice;  //是否涉外：0否、1是
	private String authenticationservice;  //（网上办事服务情况）是否需要身份验证：0否、1是
	private String caauthenticationservice;  //（网上办事服务情况）身份验证是否使用CA：0否、1是
	private String isinvest;  //是否投资审批相关事项：0否、1是
	private String investtype;  //项目类型：包括企业投资和政府投资两种 ：参照数据字典【行政审批-项目类型】
	private String investstepcatalogcode1;  //投资审批阶段目录代码1
	private String investstepcatalogname1;  //投资审批阶段目录名称1
	private String investtachecatalogcode1;  //投资审批环节目录代码1
	private String investtachecatalogname1;  //投资审批环节目录名称1
	private String basepscatalogcode1;  //基本公共服务目录代码1
	private String basepscatalogname1;  //基本公共服务目录名称1
	private String superiorservicesubjectcode;  //对应上级规定事项
	private String superiorserviceorgid;  //上级主管部门
	private String onlineservicedepth;  //网上服务深度:1 级：向主厅提供完整、准确、及时的办事指南、表格下载、业务咨询相关内容和功能。2 级：在1 级基础上，向主厅提供在线申办和结果反馈相关功	能，提供在线申办信息、办事结果信息。3 级：在2 级基础上，向主厅提供进度查询相关功能，提供办	事进度信息。4 级：在3 级基础上，面向公众实现网上全程办结。"
	private String onlinewholecomplete;  //网上全程办结：0否、1是
	@Id
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	@Column
	public String getAdministrativeservicesubject() {
		return administrativeservicesubject;
	}
	public void setAdministrativeservicesubject(String administrativeservicesubject) {
		this.administrativeservicesubject = administrativeservicesubject;
	}
	@Column
	public String getAuthoritylevel() {
		return authoritylevel;
	}
	public void setAuthoritylevel(String authoritylevel) {
		this.authoritylevel = authoritylevel;
	}
	@Column
	public String getMainunittype() {
		return mainunittype;
	}
	public void setMainunittype(String mainunittype) {
		this.mainunittype = mainunittype;
	}
	@Column
	public String getServiceobject() {
		return serviceobject;
	}
	public void setServiceobject(String serviceobject) {
		this.serviceobject = serviceobject;
	}
	@Column
	public String getWindowworkflow() {
		return windowworkflow;
	}
	public void setWindowworkflow(String windowworkflow) {
		this.windowworkflow = windowworkflow;
	}
	@Column
	public String getPowerworkflow() {
		return powerworkflow;
	}
	public void setPowerworkflow(String powerworkflow) {
		this.powerworkflow = powerworkflow;
	}
	@Column
	public String getWindowaddress() {
		return windowaddress;
	}
	public void setWindowaddress(String windowaddress) {
		this.windowaddress = windowaddress;
	}
	@Column
	public String getComplaintshotline() {
		return complaintshotline;
	}
	public void setComplaintshotline(String complaintshotline) {
		this.complaintshotline = complaintshotline;
	}
	@Column
	public String getFaq() {
		return faq;
	}
	public void setFaq(String faq) {
		this.faq = faq;
	}
	@Column
	public String getOnlineapplyingservice() {
		return onlineapplyingservice;
	}
	public void setOnlineapplyingservice(String onlineapplyingservice) {
		this.onlineapplyingservice = onlineapplyingservice;
	}
	@Column
	public String getOnlineapplyingwebsite() {
		return onlineapplyingwebsite;
	}
	public void setOnlineapplyingwebsite(String onlineapplyingwebsite) {
		this.onlineapplyingwebsite = onlineapplyingwebsite;
	}
	@Column
	public String getBusinessconsultingservice() {
		return businessconsultingservice;
	}
	public void setBusinessconsultingservice(String businessconsultingservice) {
		this.businessconsultingservice = businessconsultingservice;
	}
	@Column
	public String getBusinessconsultingwebsite() {
		return businessconsultingwebsite;
	}
	public void setBusinessconsultingwebsite(String businessconsultingwebsite) {
		this.businessconsultingwebsite = businessconsultingwebsite;
	}
	@Column
	public String getResultinquiryingservice() {
		return resultinquiryingservice;
	}
	public void setResultinquiryingservice(String resultinquiryingservice) {
		this.resultinquiryingservice = resultinquiryingservice;
	}
	@Column
	public String getResultinquiryingwebsite() {
		return resultinquiryingwebsite;
	}
	public void setResultinquiryingwebsite(String resultinquiryingwebsite) {
		this.resultinquiryingwebsite = resultinquiryingwebsite;
	}
	@Column
	public String getProgressinquiryingservice() {
		return progressinquiryingservice;
	}
	public void setProgressinquiryingservice(String progressinquiryingservice) {
		this.progressinquiryingservice = progressinquiryingservice;
	}
	@Column
	public String getProgressinquiryingwebsite() {
		return progressinquiryingwebsite;
	}
	public void setProgressinquiryingwebsite(String progressinquiryingwebsite) {
		this.progressinquiryingwebsite = progressinquiryingwebsite;
	}
	@Column
	public String getPayfeesonlineservice() {
		return payfeesonlineservice;
	}
	public void setPayfeesonlineservice(String payfeesonlineservice) {
		this.payfeesonlineservice = payfeesonlineservice;
	}
	@Column
	public String getForeignservice() {
		return foreignservice;
	}
	public void setForeignservice(String foreignservice) {
		this.foreignservice = foreignservice;
	}
	@Column
	public String getAuthenticationservice() {
		return authenticationservice;
	}
	public void setAuthenticationservice(String authenticationservice) {
		this.authenticationservice = authenticationservice;
	}
	@Column
	public String getCaauthenticationservice() {
		return caauthenticationservice;
	}
	public void setCaauthenticationservice(String caauthenticationservice) {
		this.caauthenticationservice = caauthenticationservice;
	}
	@Column
	public String getIsinvest() {
		return isinvest;
	}
	public void setIsinvest(String isinvest) {
		this.isinvest = isinvest;
	}
	@Column
	public String getInvesttype() {
		return investtype;
	}
	public void setInvesttype(String investtype) {
		this.investtype = investtype;
	}
	@Column
	public String getInveststepcatalogcode1() {
		return investstepcatalogcode1;
	}
	public void setInveststepcatalogcode1(String investstepcatalogcode1) {
		this.investstepcatalogcode1 = investstepcatalogcode1;
	}
	@Column
	public String getInveststepcatalogname1() {
		return investstepcatalogname1;
	}
	public void setInveststepcatalogname1(String investstepcatalogname1) {
		this.investstepcatalogname1 = investstepcatalogname1;
	}
	@Column
	public String getInvesttachecatalogcode1() {
		return investtachecatalogcode1;
	}
	public void setInvesttachecatalogcode1(String investtachecatalogcode1) {
		this.investtachecatalogcode1 = investtachecatalogcode1;
	}
	@Column
	public String getInvesttachecatalogname1() {
		return investtachecatalogname1;
	}
	public void setInvesttachecatalogname1(String investtachecatalogname1) {
		this.investtachecatalogname1 = investtachecatalogname1;
	}
	@Column
	public String getBasepscatalogcode1() {
		return basepscatalogcode1;
	}
	public void setBasepscatalogcode1(String basepscatalogcode1) {
		this.basepscatalogcode1 = basepscatalogcode1;
	}
	@Column
	public String getBasepscatalogname1() {
		return basepscatalogname1;
	}
	public void setBasepscatalogname1(String basepscatalogname1) {
		this.basepscatalogname1 = basepscatalogname1;
	}
	@Column
	public String getSuperiorservicesubjectcode() {
		return superiorservicesubjectcode;
	}
	public void setSuperiorservicesubjectcode(String superiorservicesubjectcode) {
		this.superiorservicesubjectcode = superiorservicesubjectcode;
	}
	@Column
	public String getSuperiorserviceorgid() {
		return superiorserviceorgid;
	}
	public void setSuperiorserviceorgid(String superiorserviceorgid) {
		this.superiorserviceorgid = superiorserviceorgid;
	}
	@Column
	public String getOnlineservicedepth() {
		return onlineservicedepth;
	}
	public void setOnlineservicedepth(String onlineservicedepth) {
		this.onlineservicedepth = onlineservicedepth;
	}
	@Column
	public String getOnlinewholecomplete() {
		return onlinewholecomplete;
	}
	public void setOnlinewholecomplete(String onlinewholecomplete) {
		this.onlinewholecomplete = onlinewholecomplete;
	}

}
