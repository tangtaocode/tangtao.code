package net.risesoft.approve.entity.jpa;

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
 * 企业用户表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_OUT_COMPANYUSER")
public class TOutCompanyuser  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String guid;			//"GUID" VARCHAR2(38 BYTE) NOT NULL ,				用户信息GUID
     private String logonguid;		//"LOGONGUID" VARCHAR2(38 BYTE) NOT NULL ,			用户登录信息GUID
     private String name;			//	"NAME" VARCHAR2(200 BYTE) NULL ,				用户登录名称
     private String ename;			//	"ENAME" VARCHAR2(200 BYTE) NULL ,				单位名称/组织名称
     private String address;		//	"ADDRESS" VARCHAR2(200 BYTE) NULL ,				注册地址
     private String kind;			//	"KIND" VARCHAR2(100 BYTE) NULL ,				企业性质/组织性质
     private String regtype;		//	"REGTYPE" VARCHAR2(30 BYTE) NULL ,				注册账号类型
     private String industry;		//	"INDUSTRY" VARCHAR2(30 BYTE) NULL ,				传真
     private String orgcode;		//	"ORGCODE" VARCHAR2(30 BYTE) NULL ,				组织机构代码
     private String lawperson;		//	"LAWPERSON" VARCHAR2(50 BYTE) NULL ,			法定代表人 (负责人）
     private String regcode;		//	"REGCODE" VARCHAR2(30 BYTE) NULL ,				登记证号/注册号
     private Date regdate;			//	"REGDATE" DATE NULL ,							成立时间
     private String regmoney;		//	"REGMONEY" VARCHAR2(22 BYTE) NULL ,				注册资金
     private String limit;			//	"LIMIT" VARCHAR2(4000 BYTE) NULL ,				经营范围
     private String scope;			//	"SCOPE" VARCHAR2(500 BYTE) NULL ,
     private Double totalmag;		//	"TOTALMAG" NUMBER(20,2) NULL ,
     private Double totalmid;		//	"TOTALMID" NUMBER(20,2) NULL ,
     private String contactadd;		//	"CONTACTADD" VARCHAR2(200 BYTE) NULL,			是否总部企业
     private String contactper;		//	"CONTACTPER" VARCHAR2(200 BYTE) NULL,			是否金融企业
     private String contactphone;	//	"CONTACTPHONE" VARCHAR2(20 BYTE) NULL,			联系人电话
     private String contactmobile;	//	"CONTACTMOBILE" VARCHAR2(20 BYTE) NULL ,		联系人手机号码
     private String email;			//	"EMAIL" VARCHAR2(100 BYTE) NULL ,				联系人邮箱
     private String fax;			//	"FAX" VARCHAR2(20 BYTE) NULL ,
     private String postcode;		//	"POSTCODE" VARCHAR2(20 BYTE) NULL ,				邮编
     private Date registerDate;		//	"REGISTER_DATE" DATE DEFAULT sysdate  NULL ,	注册时间
     private String totalpeop;		//	"TOTALPEOP" VARCHAR2(22 BYTE) NULL ,			公司人数/机构人数
     private String modifyreason;	//	"MODIFYREASON" VARCHAR2(4000 BYTE) NULL ,		修改原因
     private String lawperphone;	//	"LAWPERPHONE" VARCHAR2(20 BYTE) NULL ,			法人联系人手机
     private String truename;		//	"TRUENAME" VARCHAR2(30 BYTE) NULL ,				联系人姓名
     private String openadd;		//	"OPENADD" VARCHAR2(500 BYTE) NULL ,				营业地址
     private String lawphone;		//	"LAWPHONE" VARCHAR2(20 BYTE) NULL ,				法人电话
     private String lawperemail;	//	"LAWPEREMAIL" VARCHAR2(50 BYTE) NULL ,			法人邮箱
     private String dztotalpeop;	//	"DZTOTALPEOP" VARCHAR2(20 BYTE) NULL ,			大专以上人数
     private String nationaltax;	//	"NATIONALTAX" VARCHAR2(50 BYTE) NULL ,			国税纳税编码
     private String taxnum;			//	"TAXNUM" VARCHAR2(50 BYTE) NULL ,	  			税务登记编号（纳税人识别号）	
     private String localtax;		//	"LOCALTAX" VARCHAR2(50 BYTE) NULL ,  			地税纳税编码
     private String area;			//	"AREA" VARCHAR2(30 BYTE) NULL ,					 营业面积
     private String companyabout;	//	"COMPANYABOUT" VARCHAR2(4000 BYTE) NULL ,		公司简介/机构简介
     private String companyaptitudes;//	"COMPANYAPTITUDES" VARCHAR2(4000 BYTE) NULL ,	公司资质/机构资质
     private String awards;			//	"AWARDS" VARCHAR2(4000 BYTE) NULL ,				获奖情况
     private String bankname;		//	"BANKNAME" VARCHAR2(500 BYTE) NULL ,			开户银行
     private String banknum;		//	"BANKNUM" VARCHAR2(30 BYTE) NULL ,				银行帐号
     private String accountname;	//	"ACCOUNTNAME" VARCHAR2(100 BYTE) NULL , 		账户名称
     private String lowcardid;		//	"LOWCARDID" VARCHAR2(50 BYTE) NULL ,			法人身份证
     private String personcardid;	//	"PERSONCARDID" VARCHAR2(50 BYTE) NULL , 		联系人身份证
     private String zgbm;			//	"ZGBM" VARCHAR2(200 BYTE) NULL ,				主管部门
     private String commoneyguid;	//	"COMMONEYGUID" VARCHAR2(38 BYTE) NULL , 		上年度收入等信息外键
     private String ischange;		//	"ISCHANGE" CHAR(1 BYTE) DEFAULT 0  NULL， 		是否交换，0未交换，1已交换


   
    // Property accessors

    @Id
 	@Column(name = "GUID", length = 38, nullable = false)
 	@GeneratedValue(generator = "uuid")
 	@GenericGenerator(name = "uuid", strategy = "assigned")
    public String getGuid() {
        return this.guid;
    }
    
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Column(name="LOGONGUID", nullable=false, length=38)
    public String getLogonguid() {
        return this.logonguid;
    }
    
    public void setLogonguid(String logonguid) {
        this.logonguid = logonguid;
    }

    @Column(name="NAME", length=200)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="ENAME", length=200)

    public String getEname() {
        return this.ename;
    }
    
    public void setEname(String ename) {
        this.ename = ename;
    }

    @Column(name="ADDRESS", length=200)

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name="KIND", length=100)

    public String getKind() {
        return this.kind;
    }
    
    public void setKind(String kind) {
        this.kind = kind;
    }

    @Column(name="REGTYPE", length=30)

    public String getRegtype() {
        return this.regtype;
    }
    
    public void setRegtype(String regtype) {
        this.regtype = regtype;
    }

    @Column(name="INDUSTRY", length=30)

    public String getIndustry() {
        return this.industry;
    }
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Column(name="ORGCODE", length=30)

    public String getOrgcode() {
        return this.orgcode;
    }
    
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    @Column(name="LAWPERSON", length=50)

    public String getLawperson() {
        return this.lawperson;
    }
    
    public void setLawperson(String lawperson) {
        this.lawperson = lawperson;
    }

    @Column(name="REGCODE", length=30)

    public String getRegcode() {
        return this.regcode;
    }
    
    public void setRegcode(String regcode) {
        this.regcode = regcode;
    }
@Temporal(TemporalType.DATE)
    @Column(name="REGDATE", length=7)

    public Date getRegdate() {
        return this.regdate;
    }
    
    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    @Column(name="REGMONEY", length=22)

    public String getRegmoney() {
        return this.regmoney;
    }
    
    public void setRegmoney(String regmoney) {
        this.regmoney = regmoney;
    }

    @Column(name="LIMIT", length=4000)

    public String getLimit() {
        return this.limit;
    }
    
    public void setLimit(String limit) {
        this.limit = limit;
    }

    @Column(name="SCOPE", length=500)

    public String getScope() {
        return this.scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Column(name="TOTALMAG", precision=20)

    public Double getTotalmag() {
        return this.totalmag;
    }
    
    public void setTotalmag(Double totalmag) {
        this.totalmag = totalmag;
    }

    @Column(name="TOTALMID", precision=20)

    public Double getTotalmid() {
        return this.totalmid;
    }
    
    public void setTotalmid(Double totalmid) {
        this.totalmid = totalmid;
    }

    @Column(name="CONTACTADD", length=200)

    public String getContactadd() {
        return this.contactadd;
    }
    
    public void setContactadd(String contactadd) {
        this.contactadd = contactadd;
    }

    @Column(name="CONTACTPER", length=200)

    public String getContactper() {
        return this.contactper;
    }
    
    public void setContactper(String contactper) {
        this.contactper = contactper;
    }

    @Column(name="CONTACTPHONE", length=20)

    public String getContactphone() {
        return this.contactphone;
    }
    
    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    @Column(name="CONTACTMOBILE", length=20)

    public String getContactmobile() {
        return this.contactmobile;
    }
    
    public void setContactmobile(String contactmobile) {
        this.contactmobile = contactmobile;
    }

    @Column(name="EMAIL", length=100)

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="FAX", length=20)

    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name="POSTCODE", length=20)

    public String getPostcode() {
        return this.postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
@Temporal(TemporalType.DATE)
    @Column(name="REGISTER_DATE", length=7)

    public Date getRegisterDate() {
        return this.registerDate;
    }
    
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Column(name="TOTALPEOP", length=22)

    public String getTotalpeop() {
        return this.totalpeop;
    }
    
    public void setTotalpeop(String totalpeop) {
        this.totalpeop = totalpeop;
    }

    @Column(name="MODIFYREASON", length=4000)

    public String getModifyreason() {
        return this.modifyreason;
    }
    
    public void setModifyreason(String modifyreason) {
        this.modifyreason = modifyreason;
    }

    @Column(name="LAWPERPHONE", length=20)

    public String getLawperphone() {
        return this.lawperphone;
    }
    
    public void setLawperphone(String lawperphone) {
        this.lawperphone = lawperphone;
    }

    @Column(name="TRUENAME", length=30)

    public String getTruename() {
        return this.truename;
    }
    
    public void setTruename(String truename) {
        this.truename = truename;
    }

    @Column(name="OPENADD", length=500)

    public String getOpenadd() {
        return this.openadd;
    }
    
    public void setOpenadd(String openadd) {
        this.openadd = openadd;
    }

    @Column(name="LAWPHONE", length=20)

    public String getLawphone() {
        return this.lawphone;
    }
    
    public void setLawphone(String lawphone) {
        this.lawphone = lawphone;
    }

    @Column(name="LAWPEREMAIL", length=50)

    public String getLawperemail() {
        return this.lawperemail;
    }
    
    public void setLawperemail(String lawperemail) {
        this.lawperemail = lawperemail;
    }

    @Column(name="DZTOTALPEOP", length=20)

    public String getDztotalpeop() {
        return this.dztotalpeop;
    }
    
    public void setDztotalpeop(String dztotalpeop) {
        this.dztotalpeop = dztotalpeop;
    }

    @Column(name="NATIONALTAX", length=50)

    public String getNationaltax() {
        return this.nationaltax;
    }
    
    public void setNationaltax(String nationaltax) {
        this.nationaltax = nationaltax;
    }

    @Column(name="TAXNUM", length=50)

    public String getTaxnum() {
        return this.taxnum;
    }
    
    public void setTaxnum(String taxnum) {
        this.taxnum = taxnum;
    }

    @Column(name="LOCALTAX", length=50)

    public String getLocaltax() {
        return this.localtax;
    }
    
    public void setLocaltax(String localtax) {
        this.localtax = localtax;
    }

    @Column(name="AREA", length=30)

    public String getArea() {
        return this.area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }

    @Column(name="COMPANYABOUT", length=4000)

    public String getCompanyabout() {
        return this.companyabout;
    }
    
    public void setCompanyabout(String companyabout) {
        this.companyabout = companyabout;
    }

    @Column(name="COMPANYAPTITUDES", length=4000)

    public String getCompanyaptitudes() {
        return this.companyaptitudes;
    }
    
    public void setCompanyaptitudes(String companyaptitudes) {
        this.companyaptitudes = companyaptitudes;
    }

    @Column(name="AWARDS", length=4000)

    public String getAwards() {
        return this.awards;
    }
    
    public void setAwards(String awards) {
        this.awards = awards;
    }

    @Column(name="BANKNAME", length=500)

    public String getBankname() {
        return this.bankname;
    }
    
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    @Column(name="BANKNUM", length=30)

    public String getBanknum() {
        return this.banknum;
    }
    
    public void setBanknum(String banknum) {
        this.banknum = banknum;
    }

    @Column(name="ACCOUNTNAME", length=100)

    public String getAccountname() {
        return this.accountname;
    }
    
    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    @Column(name="LOWCARDID", length=50)

    public String getLowcardid() {
        return this.lowcardid;
    }
    
    public void setLowcardid(String lowcardid) {
        this.lowcardid = lowcardid;
    }

    @Column(name="PERSONCARDID", length=50)

    public String getPersoncardid() {
        return this.personcardid;
    }
    
    public void setPersoncardid(String personcardid) {
        this.personcardid = personcardid;
    }

    @Column(name="ZGBM", length=200)

    public String getZgbm() {
        return this.zgbm;
    }
    
    public void setZgbm(String zgbm) {
        this.zgbm = zgbm;
    }

    @Column(name="COMMONEYGUID", length=38)

    public String getCommoneyguid() {
        return this.commoneyguid;
    }
    
    public void setCommoneyguid(String commoneyguid) {
        this.commoneyguid = commoneyguid;
    }

    @Column(name="ISCHANGE", length=1)

    public String getIschange() {
        return this.ischange;
    }
    
    public void setIschange(String ischange) {
        this.ischange = ischange;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TOutCompanyuser) ) return false;
		 TOutCompanyuser castOther = ( TOutCompanyuser ) other; 
         
		 return ( (this.getGuid()==castOther.getGuid()) || ( this.getGuid()!=null && castOther.getGuid()!=null && this.getGuid().equals(castOther.getGuid()) ) )
 && ( (this.getLogonguid()==castOther.getLogonguid()) || ( this.getLogonguid()!=null && castOther.getLogonguid()!=null && this.getLogonguid().equals(castOther.getLogonguid()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getEname()==castOther.getEname()) || ( this.getEname()!=null && castOther.getEname()!=null && this.getEname().equals(castOther.getEname()) ) )
 && ( (this.getAddress()==castOther.getAddress()) || ( this.getAddress()!=null && castOther.getAddress()!=null && this.getAddress().equals(castOther.getAddress()) ) )
 && ( (this.getKind()==castOther.getKind()) || ( this.getKind()!=null && castOther.getKind()!=null && this.getKind().equals(castOther.getKind()) ) )
 && ( (this.getRegtype()==castOther.getRegtype()) || ( this.getRegtype()!=null && castOther.getRegtype()!=null && this.getRegtype().equals(castOther.getRegtype()) ) )
 && ( (this.getIndustry()==castOther.getIndustry()) || ( this.getIndustry()!=null && castOther.getIndustry()!=null && this.getIndustry().equals(castOther.getIndustry()) ) )
 && ( (this.getOrgcode()==castOther.getOrgcode()) || ( this.getOrgcode()!=null && castOther.getOrgcode()!=null && this.getOrgcode().equals(castOther.getOrgcode()) ) )
 && ( (this.getLawperson()==castOther.getLawperson()) || ( this.getLawperson()!=null && castOther.getLawperson()!=null && this.getLawperson().equals(castOther.getLawperson()) ) )
 && ( (this.getRegcode()==castOther.getRegcode()) || ( this.getRegcode()!=null && castOther.getRegcode()!=null && this.getRegcode().equals(castOther.getRegcode()) ) )
 && ( (this.getRegdate()==castOther.getRegdate()) || ( this.getRegdate()!=null && castOther.getRegdate()!=null && this.getRegdate().equals(castOther.getRegdate()) ) )
 && ( (this.getRegmoney()==castOther.getRegmoney()) || ( this.getRegmoney()!=null && castOther.getRegmoney()!=null && this.getRegmoney().equals(castOther.getRegmoney()) ) )
 && ( (this.getLimit()==castOther.getLimit()) || ( this.getLimit()!=null && castOther.getLimit()!=null && this.getLimit().equals(castOther.getLimit()) ) )
 && ( (this.getScope()==castOther.getScope()) || ( this.getScope()!=null && castOther.getScope()!=null && this.getScope().equals(castOther.getScope()) ) )
 && ( (this.getTotalmag()==castOther.getTotalmag()) || ( this.getTotalmag()!=null && castOther.getTotalmag()!=null && this.getTotalmag().equals(castOther.getTotalmag()) ) )
 && ( (this.getTotalmid()==castOther.getTotalmid()) || ( this.getTotalmid()!=null && castOther.getTotalmid()!=null && this.getTotalmid().equals(castOther.getTotalmid()) ) )
 && ( (this.getContactadd()==castOther.getContactadd()) || ( this.getContactadd()!=null && castOther.getContactadd()!=null && this.getContactadd().equals(castOther.getContactadd()) ) )
 && ( (this.getContactper()==castOther.getContactper()) || ( this.getContactper()!=null && castOther.getContactper()!=null && this.getContactper().equals(castOther.getContactper()) ) )
 && ( (this.getContactphone()==castOther.getContactphone()) || ( this.getContactphone()!=null && castOther.getContactphone()!=null && this.getContactphone().equals(castOther.getContactphone()) ) )
 && ( (this.getContactmobile()==castOther.getContactmobile()) || ( this.getContactmobile()!=null && castOther.getContactmobile()!=null && this.getContactmobile().equals(castOther.getContactmobile()) ) )
 && ( (this.getEmail()==castOther.getEmail()) || ( this.getEmail()!=null && castOther.getEmail()!=null && this.getEmail().equals(castOther.getEmail()) ) )
 && ( (this.getFax()==castOther.getFax()) || ( this.getFax()!=null && castOther.getFax()!=null && this.getFax().equals(castOther.getFax()) ) )
 && ( (this.getPostcode()==castOther.getPostcode()) || ( this.getPostcode()!=null && castOther.getPostcode()!=null && this.getPostcode().equals(castOther.getPostcode()) ) )
 && ( (this.getRegisterDate()==castOther.getRegisterDate()) || ( this.getRegisterDate()!=null && castOther.getRegisterDate()!=null && this.getRegisterDate().equals(castOther.getRegisterDate()) ) )
 && ( (this.getTotalpeop()==castOther.getTotalpeop()) || ( this.getTotalpeop()!=null && castOther.getTotalpeop()!=null && this.getTotalpeop().equals(castOther.getTotalpeop()) ) )
 && ( (this.getModifyreason()==castOther.getModifyreason()) || ( this.getModifyreason()!=null && castOther.getModifyreason()!=null && this.getModifyreason().equals(castOther.getModifyreason()) ) )
 && ( (this.getLawperphone()==castOther.getLawperphone()) || ( this.getLawperphone()!=null && castOther.getLawperphone()!=null && this.getLawperphone().equals(castOther.getLawperphone()) ) )
 && ( (this.getTruename()==castOther.getTruename()) || ( this.getTruename()!=null && castOther.getTruename()!=null && this.getTruename().equals(castOther.getTruename()) ) )
 && ( (this.getOpenadd()==castOther.getOpenadd()) || ( this.getOpenadd()!=null && castOther.getOpenadd()!=null && this.getOpenadd().equals(castOther.getOpenadd()) ) )
 && ( (this.getLawphone()==castOther.getLawphone()) || ( this.getLawphone()!=null && castOther.getLawphone()!=null && this.getLawphone().equals(castOther.getLawphone()) ) )
 && ( (this.getLawperemail()==castOther.getLawperemail()) || ( this.getLawperemail()!=null && castOther.getLawperemail()!=null && this.getLawperemail().equals(castOther.getLawperemail()) ) )
 && ( (this.getDztotalpeop()==castOther.getDztotalpeop()) || ( this.getDztotalpeop()!=null && castOther.getDztotalpeop()!=null && this.getDztotalpeop().equals(castOther.getDztotalpeop()) ) )
 && ( (this.getNationaltax()==castOther.getNationaltax()) || ( this.getNationaltax()!=null && castOther.getNationaltax()!=null && this.getNationaltax().equals(castOther.getNationaltax()) ) )
 && ( (this.getTaxnum()==castOther.getTaxnum()) || ( this.getTaxnum()!=null && castOther.getTaxnum()!=null && this.getTaxnum().equals(castOther.getTaxnum()) ) )
 && ( (this.getLocaltax()==castOther.getLocaltax()) || ( this.getLocaltax()!=null && castOther.getLocaltax()!=null && this.getLocaltax().equals(castOther.getLocaltax()) ) )
 && ( (this.getArea()==castOther.getArea()) || ( this.getArea()!=null && castOther.getArea()!=null && this.getArea().equals(castOther.getArea()) ) )
 && ( (this.getCompanyabout()==castOther.getCompanyabout()) || ( this.getCompanyabout()!=null && castOther.getCompanyabout()!=null && this.getCompanyabout().equals(castOther.getCompanyabout()) ) )
 && ( (this.getCompanyaptitudes()==castOther.getCompanyaptitudes()) || ( this.getCompanyaptitudes()!=null && castOther.getCompanyaptitudes()!=null && this.getCompanyaptitudes().equals(castOther.getCompanyaptitudes()) ) )
 && ( (this.getAwards()==castOther.getAwards()) || ( this.getAwards()!=null && castOther.getAwards()!=null && this.getAwards().equals(castOther.getAwards()) ) )
 && ( (this.getBankname()==castOther.getBankname()) || ( this.getBankname()!=null && castOther.getBankname()!=null && this.getBankname().equals(castOther.getBankname()) ) )
 && ( (this.getBanknum()==castOther.getBanknum()) || ( this.getBanknum()!=null && castOther.getBanknum()!=null && this.getBanknum().equals(castOther.getBanknum()) ) )
 && ( (this.getAccountname()==castOther.getAccountname()) || ( this.getAccountname()!=null && castOther.getAccountname()!=null && this.getAccountname().equals(castOther.getAccountname()) ) )
 && ( (this.getLowcardid()==castOther.getLowcardid()) || ( this.getLowcardid()!=null && castOther.getLowcardid()!=null && this.getLowcardid().equals(castOther.getLowcardid()) ) )
 && ( (this.getPersoncardid()==castOther.getPersoncardid()) || ( this.getPersoncardid()!=null && castOther.getPersoncardid()!=null && this.getPersoncardid().equals(castOther.getPersoncardid()) ) )
 && ( (this.getZgbm()==castOther.getZgbm()) || ( this.getZgbm()!=null && castOther.getZgbm()!=null && this.getZgbm().equals(castOther.getZgbm()) ) )
 && ( (this.getCommoneyguid()==castOther.getCommoneyguid()) || ( this.getCommoneyguid()!=null && castOther.getCommoneyguid()!=null && this.getCommoneyguid().equals(castOther.getCommoneyguid()) ) )
 && ( (this.getIschange()==castOther.getIschange()) || ( this.getIschange()!=null && castOther.getIschange()!=null && this.getIschange().equals(castOther.getIschange()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGuid() == null ? 0 : this.getGuid().hashCode() );
         result = 37 * result + ( getLogonguid() == null ? 0 : this.getLogonguid().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getEname() == null ? 0 : this.getEname().hashCode() );
         result = 37 * result + ( getAddress() == null ? 0 : this.getAddress().hashCode() );
         result = 37 * result + ( getKind() == null ? 0 : this.getKind().hashCode() );
         result = 37 * result + ( getRegtype() == null ? 0 : this.getRegtype().hashCode() );
         result = 37 * result + ( getIndustry() == null ? 0 : this.getIndustry().hashCode() );
         result = 37 * result + ( getOrgcode() == null ? 0 : this.getOrgcode().hashCode() );
         result = 37 * result + ( getLawperson() == null ? 0 : this.getLawperson().hashCode() );
         result = 37 * result + ( getRegcode() == null ? 0 : this.getRegcode().hashCode() );
         result = 37 * result + ( getRegdate() == null ? 0 : this.getRegdate().hashCode() );
         result = 37 * result + ( getRegmoney() == null ? 0 : this.getRegmoney().hashCode() );
         result = 37 * result + ( getLimit() == null ? 0 : this.getLimit().hashCode() );
         result = 37 * result + ( getScope() == null ? 0 : this.getScope().hashCode() );
         result = 37 * result + ( getTotalmag() == null ? 0 : this.getTotalmag().hashCode() );
         result = 37 * result + ( getTotalmid() == null ? 0 : this.getTotalmid().hashCode() );
         result = 37 * result + ( getContactadd() == null ? 0 : this.getContactadd().hashCode() );
         result = 37 * result + ( getContactper() == null ? 0 : this.getContactper().hashCode() );
         result = 37 * result + ( getContactphone() == null ? 0 : this.getContactphone().hashCode() );
         result = 37 * result + ( getContactmobile() == null ? 0 : this.getContactmobile().hashCode() );
         result = 37 * result + ( getEmail() == null ? 0 : this.getEmail().hashCode() );
         result = 37 * result + ( getFax() == null ? 0 : this.getFax().hashCode() );
         result = 37 * result + ( getPostcode() == null ? 0 : this.getPostcode().hashCode() );
         result = 37 * result + ( getRegisterDate() == null ? 0 : this.getRegisterDate().hashCode() );
         result = 37 * result + ( getTotalpeop() == null ? 0 : this.getTotalpeop().hashCode() );
         result = 37 * result + ( getModifyreason() == null ? 0 : this.getModifyreason().hashCode() );
         result = 37 * result + ( getLawperphone() == null ? 0 : this.getLawperphone().hashCode() );
         result = 37 * result + ( getTruename() == null ? 0 : this.getTruename().hashCode() );
         result = 37 * result + ( getOpenadd() == null ? 0 : this.getOpenadd().hashCode() );
         result = 37 * result + ( getLawphone() == null ? 0 : this.getLawphone().hashCode() );
         result = 37 * result + ( getLawperemail() == null ? 0 : this.getLawperemail().hashCode() );
         result = 37 * result + ( getDztotalpeop() == null ? 0 : this.getDztotalpeop().hashCode() );
         result = 37 * result + ( getNationaltax() == null ? 0 : this.getNationaltax().hashCode() );
         result = 37 * result + ( getTaxnum() == null ? 0 : this.getTaxnum().hashCode() );
         result = 37 * result + ( getLocaltax() == null ? 0 : this.getLocaltax().hashCode() );
         result = 37 * result + ( getArea() == null ? 0 : this.getArea().hashCode() );
         result = 37 * result + ( getCompanyabout() == null ? 0 : this.getCompanyabout().hashCode() );
         result = 37 * result + ( getCompanyaptitudes() == null ? 0 : this.getCompanyaptitudes().hashCode() );
         result = 37 * result + ( getAwards() == null ? 0 : this.getAwards().hashCode() );
         result = 37 * result + ( getBankname() == null ? 0 : this.getBankname().hashCode() );
         result = 37 * result + ( getBanknum() == null ? 0 : this.getBanknum().hashCode() );
         result = 37 * result + ( getAccountname() == null ? 0 : this.getAccountname().hashCode() );
         result = 37 * result + ( getLowcardid() == null ? 0 : this.getLowcardid().hashCode() );
         result = 37 * result + ( getPersoncardid() == null ? 0 : this.getPersoncardid().hashCode() );
         result = 37 * result + ( getZgbm() == null ? 0 : this.getZgbm().hashCode() );
         result = 37 * result + ( getCommoneyguid() == null ? 0 : this.getCommoneyguid().hashCode() );
         result = 37 * result + ( getIschange() == null ? 0 : this.getIschange().hashCode() );
         return result;
   }

   			@Override
   			public String toString() {
   			return "TOutCompanyuser [guid=" + guid + ", logonguid=" + logonguid
   						+ ", name=" + name + ", ename=" + ename + ", address=" + address
   						+ ", kind=" + kind + ", regtype=" + regtype + ", industry="
   						+ industry + ", orgcode=" + orgcode + ", lawperson=" + lawperson
						+ ", regcode=" + regcode + ", regdate=" + regdate + ", regmoney="
						+ regmoney + ", limit=" + limit + ", scope=" + scope
						+ ", totalmag=" + totalmag + ", totalmid=" + totalmid
						+ ", contactadd=" + contactadd + ", contactper=" + contactper
						+ ", contactphone=" + contactphone + ", contactmobile="
						+ contactmobile + ", email=" + email + ", fax=" + fax
						+ ", postcode=" + postcode + ", registerDate=" + registerDate
						+ ", totalpeop=" + totalpeop + ", modifyreason=" + modifyreason
						+ ", lawperphone=" + lawperphone + ", truename=" + truename
						+ ", openadd=" + openadd + ", lawphone=" + lawphone
						+ ", lawperemail=" + lawperemail + ", dztotalpeop=" + dztotalpeop
						+ ", nationaltax=" + nationaltax + ", taxnum=" + taxnum
						+ ", localtax=" + localtax + ", area=" + area + ", companyabout="
						+ companyabout + ", companyaptitudes=" + companyaptitudes
						+ ", awards=" + awards + ", bankname=" + bankname + ", banknum="
						+ banknum + ", accountname=" + accountname + ", lowcardid="
						+ lowcardid + ", personcardid=" + personcardid + ", zgbm=" + zgbm
						+ ", commoneyguid=" + commoneyguid + ", ischange=" + ischange + "]";
}   

   



}