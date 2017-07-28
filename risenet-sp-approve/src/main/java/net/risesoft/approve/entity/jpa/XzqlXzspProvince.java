package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * AbstractXzqlXzspProvince entity provides the base persistence definition of
 * the XzqlXzspProvince entity.
 * 行政审批事项信息表（省里要求多余部分）
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XZQL_XZSP_PROVINCE")
public  class XzqlXzspProvince  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	 private String itemid;							//	"ITEMID" VARCHAR2(50 BYTE) NOT NULL ,						事项ID
     private String administrativeservicesubject;	//	"ADMINISTRATIVESERVICESUBJECT" VARCHAR2(500 BYTE) NULL ,	行政审批改革事项
     private String authoritylevel;					//	"AUTHORITYLEVEL" VARCHAR2(50 BYTE) NOT NULL ,				事权级别(代码值)：参照数据字典【行政审批-事权级别】
     private String mainunittype;					//	"MAINUNITTYPE" VARCHAR2(50 BYTE) NULL ,						办理主体类型(代码值)：参照数据字典【行政审批-办理主体类型】
     private String serviceobject;					//	"SERVICEOBJECT" VARCHAR2(2000 BYTE) NULL ,					办理对象
     private String windowworkflow;					//	"WINDOWWORKFLOW" VARCHAR2(2000 BYTE) NOT NULL ,				窗口办理流程
     private String powerworkflow;					//	"POWERWORKFLOW" VARCHAR2(2000 BYTE) NULL ,					权力运行流程
     private String windowaddress;					//	"WINDOWADDRESS" VARCHAR2(2000 BYTE) NULL ,					办事窗口信息:办理该服务事项的办事窗口信息，包括窗口名称、地址、联系电话、交通指引、地图等。
     private String complaintshotline;				//	"COMPLAINTSHOTLINE" VARCHAR2(200 BYTE) NOT NULL ,			监督电话
     private String faq;							//	"FAQ" VARCHAR2(2000 BYTE) NULL ,							常见问题解答
     private String onlineapplyingservice;			//	"ONLINEAPPLYINGSERVICE" CHAR(1 BYTE) NULL ,					在线申办服务：0否、1是	
     private String onlineapplyingwebsite;			//	"ONLINEAPPLYINGWEBSITE" VARCHAR2(256 BYTE) NULL ,			在线申办服务网址
     private String businessconsultingservice;		//	"BUSINESSCONSULTINGSERVICE" CHAR(1 BYTE) NULL ,				业务咨询服务：0否、1是
     private String businessconsultingwebsite;		//	"BUSINESSCONSULTINGWEBSITE" VARCHAR2(256 BYTE) NULL ,		业务咨询服务网址
     private String resultinquiryingservice;		//	"RESULTINQUIRYINGSERVICE" CHAR(1 BYTE) NULL ,				结果查询服务：0否、1是
     private String resultinquiryingwebsite;		// 	"RESULTINQUIRYINGWEBSITE" VARCHAR2(256 BYTE) NULL ,			结果查询服务网址
     private String progressinquiryingservice;		// 	"PROGRESSINQUIRYINGSERVICE" CHAR(1 BYTE) NULL ,				进度查询服务：0否、1是
     private String progressinquiryingwebsite;		// 	"PROGRESSINQUIRYINGWEBSITE" VARCHAR2(256 BYTE) NULL ,		进度查询服务网址
     private String payfeesonlineservice;			// 	"PAYFEESONLINESERVICE" CHAR(1 BYTE) NULL ,					网上缴费服务：0否、1是
     private String foreignservice;					// 	"FOREIGNSERVICE" CHAR(1 BYTE) NULL ,						是否涉外：0否、1是
     private String authenticationservice;			// 	"AUTHENTICATIONSERVICE" CHAR(1 BYTE) NULL ,				         （网上办事服务情况）是否需要身份验证：0否、1是
     private String caauthenticationservice;		// 	"CAAUTHENTICATIONSERVICE" CHAR(1 BYTE) NULL ,			          （网上办事服务情况）身份验证是否使用CA：0否、1是
     private String isinvest;						// 	"ISINVEST" CHAR(1 BYTE) NOT NULL ,							是否投资审批相关事项：0否、1是
     private String investtype;						// 	"INVESTTYPE" VARCHAR2(50 BYTE) NULL ,						项目类型：包括企业投资和政府投资两种 ：参照数据字典【行政审批-项目类型】
     private String investstepcatalogcode1;			// 	"INVESTSTEPCATALOGCODE1" VARCHAR2(100 BYTE) NULL ,			投资审批阶段目录代码1
     private String investstepcatalogname1;			// 	"INVESTSTEPCATALOGNAME1" VARCHAR2(400 BYTE) NULL ,			投资审批阶段目录名称1
     private String investtachecatalogcode1;		// 	"INVESTTACHECATALOGCODE1" VARCHAR2(100 BYTE) NULL ,			投资审批环节目录代码1
     private String investtachecatalogname1;		// 	"INVESTTACHECATALOGNAME1" VARCHAR2(400 BYTE) NULL ,			投资审批环节目录名称1
     private String basepscatalogcode1;				// 	"BASEPSCATALOGCODE1" VARCHAR2(100 BYTE) NULL ,				基本公共服务目录代码1
     private String basepscatalogname1;				// 	"BASEPSCATALOGNAME1" VARCHAR2(400 BYTE) NULL ,				基本公共服务目录名称1
     private String superiorservicesubjectcode;		// 	"SUPERIORSERVICESUBJECTCODE" VARCHAR2(50 BYTE) NULL ,		对应上级规定事项
     private String superiorserviceorgid;			// 	"SUPERIORSERVICEORGID" VARCHAR2(50 BYTE) NULL ,				上级主管部门
     private String onlineservicedepth;				// 	"ONLINESERVICEDEPTH" VARCHAR2(50 BYTE) NULL ,				网上服务深度
     private String onlinewholecomplete;			// 	"ONLINEWHOLECOMPLETE" CHAR(1 BYTE) NULL ,					网上全程办结：0否、1是
     private String scenenumber;					// 	"SCENENUMBER" VARCHAR2(50 BYTE) NULL ,						到现场次数
     private String beizhu;							// 	"BEIZHU" VARCHAR2(2000 BYTE) NULL ,							备注
     private String isprovince;						// 	"ISPROVINCE" CHAR(1 BYTE) NULL ,							是否进驻罗湖网上分厅:0否、1是
     private String approveplace;					// 	"APPROVEPLACE" CHAR(1 BYTE) NULL ,							是否在本系统审批：0罗湖审批平台，1其他系统
     private String systemlevel;					// 	"SYSTEMLEVEL" CHAR(1 BYTE) NULL ,							系统级别:部门、区级、市级、省级、部级
     private String systemname;						// 	"SYSTEMNAME" VARCHAR2(50 BYTE) NULL ,						系统名称
     private String abutment;						// 	"ABUTMENT" CHAR(1 BYTE) NULL 								是否与罗湖审批平台对接:0否、1是


 
    // Property accessors
    @Id
 	@Column(name = "ITEMID", length = 50, nullable = false)
 	@GeneratedValue(generator = "uuid")
 	@GenericGenerator(name = "uuid", strategy = "assigned")
   
    public String getItemid() {
        return this.itemid;
    }
    
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }
    
    @Column(name="ADMINISTRATIVESERVICESUBJECT", length=500)

    public String getAdministrativeservicesubject() {
        return this.administrativeservicesubject;
    }
    
    public void setAdministrativeservicesubject(String administrativeservicesubject) {
        this.administrativeservicesubject = administrativeservicesubject;
    }
    
    @Column(name="AUTHORITYLEVEL", nullable=false, length=50)

    public String getAuthoritylevel() {
        return this.authoritylevel;
    }
    
    public void setAuthoritylevel(String authoritylevel) {
        this.authoritylevel = authoritylevel;
    }
    
    @Column(name="MAINUNITTYPE", length=50)

    public String getMainunittype() {
        return this.mainunittype;
    }
    
    public void setMainunittype(String mainunittype) {
        this.mainunittype = mainunittype;
    }
    
    @Column(name="SERVICEOBJECT", length=2000)

    public String getServiceobject() {
        return this.serviceobject;
    }
    
    public void setServiceobject(String serviceobject) {
        this.serviceobject = serviceobject;
    }
    
    @Column(name="WINDOWWORKFLOW", nullable=false, length=2000)

    public String getWindowworkflow() {
        return this.windowworkflow;
    }
    
    public void setWindowworkflow(String windowworkflow) {
        this.windowworkflow = windowworkflow;
    }
    
    @Column(name="POWERWORKFLOW", length=2000)

    public String getPowerworkflow() {
        return this.powerworkflow;
    }
    
    public void setPowerworkflow(String powerworkflow) {
        this.powerworkflow = powerworkflow;
    }
    
    @Column(name="WINDOWADDRESS", length=2000)

    public String getWindowaddress() {
        return this.windowaddress;
    }
    
    public void setWindowaddress(String windowaddress) {
        this.windowaddress = windowaddress;
    }
    
    @Column(name="COMPLAINTSHOTLINE", nullable=false, length=200)

    public String getComplaintshotline() {
        return this.complaintshotline;
    }
    
    public void setComplaintshotline(String complaintshotline) {
        this.complaintshotline = complaintshotline;
    }
    
    @Column(name="FAQ", length=2000)

    public String getFaq() {
        return this.faq;
    }
    
    public void setFaq(String faq) {
        this.faq = faq;
    }
    
    @Column(name="ONLINEAPPLYINGSERVICE", length=1)

    public String getOnlineapplyingservice() {
        return this.onlineapplyingservice;
    }
    
    public void setOnlineapplyingservice(String onlineapplyingservice) {
        this.onlineapplyingservice = onlineapplyingservice;
    }
    
    @Column(name="ONLINEAPPLYINGWEBSITE", length=256)

    public String getOnlineapplyingwebsite() {
        return this.onlineapplyingwebsite;
    }
    
    public void setOnlineapplyingwebsite(String onlineapplyingwebsite) {
        this.onlineapplyingwebsite = onlineapplyingwebsite;
    }
    
    @Column(name="BUSINESSCONSULTINGSERVICE", length=1)

    public String getBusinessconsultingservice() {
        return this.businessconsultingservice;
    }
    
    public void setBusinessconsultingservice(String businessconsultingservice) {
        this.businessconsultingservice = businessconsultingservice;
    }
    
    @Column(name="BUSINESSCONSULTINGWEBSITE", length=256)

    public String getBusinessconsultingwebsite() {
        return this.businessconsultingwebsite;
    }
    
    public void setBusinessconsultingwebsite(String businessconsultingwebsite) {
        this.businessconsultingwebsite = businessconsultingwebsite;
    }
    
    @Column(name="RESULTINQUIRYINGSERVICE", length=1)

    public String getResultinquiryingservice() {
        return this.resultinquiryingservice;
    }
    
    public void setResultinquiryingservice(String resultinquiryingservice) {
        this.resultinquiryingservice = resultinquiryingservice;
    }
    
    @Column(name="RESULTINQUIRYINGWEBSITE", length=256)

    public String getResultinquiryingwebsite() {
        return this.resultinquiryingwebsite;
    }
    
    public void setResultinquiryingwebsite(String resultinquiryingwebsite) {
        this.resultinquiryingwebsite = resultinquiryingwebsite;
    }
    
    @Column(name="PROGRESSINQUIRYINGSERVICE", length=1)

    public String getProgressinquiryingservice() {
        return this.progressinquiryingservice;
    }
    
    public void setProgressinquiryingservice(String progressinquiryingservice) {
        this.progressinquiryingservice = progressinquiryingservice;
    }
    
    @Column(name="PROGRESSINQUIRYINGWEBSITE", length=256)

    public String getProgressinquiryingwebsite() {
        return this.progressinquiryingwebsite;
    }
    
    public void setProgressinquiryingwebsite(String progressinquiryingwebsite) {
        this.progressinquiryingwebsite = progressinquiryingwebsite;
    }
    
    @Column(name="PAYFEESONLINESERVICE", length=1)

    public String getPayfeesonlineservice() {
        return this.payfeesonlineservice;
    }
    
    public void setPayfeesonlineservice(String payfeesonlineservice) {
        this.payfeesonlineservice = payfeesonlineservice;
    }
    
    @Column(name="FOREIGNSERVICE", length=1)

    public String getForeignservice() {
        return this.foreignservice;
    }
    
    public void setForeignservice(String foreignservice) {
        this.foreignservice = foreignservice;
    }
    
    @Column(name="AUTHENTICATIONSERVICE", length=1)

    public String getAuthenticationservice() {
        return this.authenticationservice;
    }
    
    public void setAuthenticationservice(String authenticationservice) {
        this.authenticationservice = authenticationservice;
    }
    
    @Column(name="CAAUTHENTICATIONSERVICE", length=1)

    public String getCaauthenticationservice() {
        return this.caauthenticationservice;
    }
    
    public void setCaauthenticationservice(String caauthenticationservice) {
        this.caauthenticationservice = caauthenticationservice;
    }
    
    @Column(name="ISINVEST", nullable=false, length=1)

    public String getIsinvest() {
        return this.isinvest;
    }
    
    public void setIsinvest(String isinvest) {
        this.isinvest = isinvest;
    }
    
    @Column(name="INVESTTYPE", length=50)

    public String getInvesttype() {
        return this.investtype;
    }
    
    public void setInvesttype(String investtype) {
        this.investtype = investtype;
    }
    
    @Column(name="INVESTSTEPCATALOGCODE1", length=100)

    public String getInveststepcatalogcode1() {
        return this.investstepcatalogcode1;
    }
    
    public void setInveststepcatalogcode1(String investstepcatalogcode1) {
        this.investstepcatalogcode1 = investstepcatalogcode1;
    }
    
    @Column(name="INVESTSTEPCATALOGNAME1", length=400)

    public String getInveststepcatalogname1() {
        return this.investstepcatalogname1;
    }
    
    public void setInveststepcatalogname1(String investstepcatalogname1) {
        this.investstepcatalogname1 = investstepcatalogname1;
    }
    
    @Column(name="INVESTTACHECATALOGCODE1", length=100)

    public String getInvesttachecatalogcode1() {
        return this.investtachecatalogcode1;
    }
    
    public void setInvesttachecatalogcode1(String investtachecatalogcode1) {
        this.investtachecatalogcode1 = investtachecatalogcode1;
    }
    
    @Column(name="INVESTTACHECATALOGNAME1", length=400)

    public String getInvesttachecatalogname1() {
        return this.investtachecatalogname1;
    }
    
    public void setInvesttachecatalogname1(String investtachecatalogname1) {
        this.investtachecatalogname1 = investtachecatalogname1;
    }
    
    @Column(name="BASEPSCATALOGCODE1", length=100)

    public String getBasepscatalogcode1() {
        return this.basepscatalogcode1;
    }
    
    public void setBasepscatalogcode1(String basepscatalogcode1) {
        this.basepscatalogcode1 = basepscatalogcode1;
    }
    
    @Column(name="BASEPSCATALOGNAME1", length=400)

    public String getBasepscatalogname1() {
        return this.basepscatalogname1;
    }
    
    public void setBasepscatalogname1(String basepscatalogname1) {
        this.basepscatalogname1 = basepscatalogname1;
    }
    
    @Column(name="SUPERIORSERVICESUBJECTCODE", length=50)

    public String getSuperiorservicesubjectcode() {
        return this.superiorservicesubjectcode;
    }
    
    public void setSuperiorservicesubjectcode(String superiorservicesubjectcode) {
        this.superiorservicesubjectcode = superiorservicesubjectcode;
    }
    
    @Column(name="SUPERIORSERVICEORGID", length=50)

    public String getSuperiorserviceorgid() {
        return this.superiorserviceorgid;
    }
    
    public void setSuperiorserviceorgid(String superiorserviceorgid) {
        this.superiorserviceorgid = superiorserviceorgid;
    }
    
    @Column(name="ONLINESERVICEDEPTH", length=50)

    public String getOnlineservicedepth() {
        return this.onlineservicedepth;
    }
    
    public void setOnlineservicedepth(String onlineservicedepth) {
        this.onlineservicedepth = onlineservicedepth;
    }
    
    @Column(name="ONLINEWHOLECOMPLETE", length=1)

    public String getOnlinewholecomplete() {
        return this.onlinewholecomplete;
    }
    
    public void setOnlinewholecomplete(String onlinewholecomplete) {
        this.onlinewholecomplete = onlinewholecomplete;
    }
    
    @Column(name="SCENENUMBER", length=50)

    public String getScenenumber() {
        return this.scenenumber;
    }
    
    public void setScenenumber(String scenenumber) {
        this.scenenumber = scenenumber;
    }
    
    @Column(name="BEIZHU", length=2000)

    public String getBeizhu() {
        return this.beizhu;
    }
    
    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
    
    @Column(name="ISPROVINCE", length=1)

    public String getIsprovince() {
        return this.isprovince;
    }
    
    public void setIsprovince(String isprovince) {
        this.isprovince = isprovince;
    }
    
    @Column(name="APPROVEPLACE", length=1)

    public String getApproveplace() {
        return this.approveplace;
    }
    
    public void setApproveplace(String approveplace) {
        this.approveplace = approveplace;
    }
    
    @Column(name="SYSTEMLEVEL", length=1)

    public String getSystemlevel() {
        return this.systemlevel;
    }
    
    public void setSystemlevel(String systemlevel) {
        this.systemlevel = systemlevel;
    }
    
    @Column(name="SYSTEMNAME", length=50)

    public String getSystemname() {
        return this.systemname;
    }
    
    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }
    
    @Column(name="ABUTMENT", length=1)

    public String getAbutment() {
        return this.abutment;
    }
    
    public void setAbutment(String abutment) {
        this.abutment = abutment;
    }

	@Override
	public String toString() {
		return "XzqlXzspProvince [itemid=" + itemid
				+ ", administrativeservicesubject="
				+ administrativeservicesubject + ", authoritylevel="
				+ authoritylevel + ", mainunittype=" + mainunittype
				+ ", serviceobject=" + serviceobject + ", windowworkflow="
				+ windowworkflow + ", powerworkflow=" + powerworkflow
				+ ", windowaddress=" + windowaddress + ", complaintshotline="
				+ complaintshotline + ", faq=" + faq
				+ ", onlineapplyingservice=" + onlineapplyingservice
				+ ", onlineapplyingwebsite=" + onlineapplyingwebsite
				+ ", businessconsultingservice=" + businessconsultingservice
				+ ", businessconsultingwebsite=" + businessconsultingwebsite
				+ ", resultinquiryingservice=" + resultinquiryingservice
				+ ", resultinquiryingwebsite=" + resultinquiryingwebsite
				+ ", progressinquiryingservice=" + progressinquiryingservice
				+ ", progressinquiryingwebsite=" + progressinquiryingwebsite
				+ ", payfeesonlineservice=" + payfeesonlineservice
				+ ", foreignservice=" + foreignservice
				+ ", authenticationservice=" + authenticationservice
				+ ", caauthenticationservice=" + caauthenticationservice
				+ ", isinvest=" + isinvest + ", investtype=" + investtype
				+ ", investstepcatalogcode1=" + investstepcatalogcode1
				+ ", investstepcatalogname1=" + investstepcatalogname1
				+ ", investtachecatalogcode1=" + investtachecatalogcode1
				+ ", investtachecatalogname1=" + investtachecatalogname1
				+ ", basepscatalogcode1=" + basepscatalogcode1
				+ ", basepscatalogname1=" + basepscatalogname1
				+ ", superiorservicesubjectcode=" + superiorservicesubjectcode
				+ ", superiorserviceorgid=" + superiorserviceorgid
				+ ", onlineservicedepth=" + onlineservicedepth
				+ ", onlinewholecomplete=" + onlinewholecomplete
				+ ", scenenumber=" + scenenumber + ", beizhu=" + beizhu
				+ ", isprovince=" + isprovince + ", approveplace="
				+ approveplace + ", systemlevel=" + systemlevel
				+ ", systemname=" + systemname + ", abutment=" + abutment + "]";
	}
   
   


}