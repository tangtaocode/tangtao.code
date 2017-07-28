package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SpmApproveitem entity.
 * 行政审批事项基础信息表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SPM_APPROVEITEM")
public class SpmApproveitem  implements Serializable {



	// Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String approveitemguid;
     private String bureauguid;
     private String approveitemsn;
     private String approveitemname;
     private Integer approveitempriority;
     private Integer timelimit;
     private String timelimitdesc;
     private String declareunittype;
     private String approveitemtype;
     private Integer approveitemtabindex;
     private String approveitemstatus;
     private Clob declarecondition;
     private Clob declaredesc;
     private Blob declarepic;
     private Clob chargeinfo;
     private Clob superviseunit;
     private String bureautype;
     private String approveitemscope;
     private String flag;
     private String workflowguid;
     private Integer debug;
     private String thefilename;
     private String workflowguid2;
     private Date lastmodifytime;
     private String lastmodifyman;
     private String taskguid;
     private String ftcode;
     private String ftsubcode;
     private String insystem;
     private String orderpermission;
     private Integer ordernum;
     private String orderday;
     private Integer orderdaynum;
     private String bureauorstreet;
     private String streetname;
     private String bguid;
     private Integer ifNeedLogin;
     private String departmentguid;
     private String classifyguid;
     private Integer isproject;
     private Integer isexternal;
     private String approveitemdesc;
     private String classify;
     private String consultationperson;
     private String onlinedeclareaddr;
     private String banshizhinan;
     private String isonline;
     private Integer category1;
     private Integer category2;
     private Integer category3;
     private String approveitemcontent;
     private String falvyiju;
     private String jiguan;
     private String zhengjianmingcheng;
     private String xiaoli;
     private String nianshen;
     private String type;
     private String accepttime;
     private String acceptaddress;
     private String linktype;
     private String acceptjiguan;
     private String tabledescription;
     private String forwardurl;
     private Integer servicetype;
     private String parentapproveitemguid;
     private Integer childapproveitemnum;
     private Integer approveplace;
     private String doctypeguid;
     private String status;
     private String windowtypeguid;
     private String isinhall;
     private String superviseguid;
     private Integer legaltimelimit;
     private String iszz;
     private String twoflow;
     private String twoflowdes;
     private String threeflow;
     private String threeflowdes;
     private String oneflow;
     private String oneflowdes;
     private String adminbureauguid;
     private String sxbm;
     private String sealtype;


    // Constructors

    /** default constructor */
    public SpmApproveitem() {
    }

	/** minimal constructor */
    public SpmApproveitem(String approveitemguid, String approveitemname, String declareunittype) {
        this.approveitemguid = approveitemguid;
        this.approveitemname = approveitemname;
        this.declareunittype = declareunittype;
    }
    
   
    // Property accessors
    @Id 
    @Column(name="APPROVEITEMGUID", unique=true, nullable=false, length=38)

    public String getApproveitemguid() {
        return this.approveitemguid;
    }
    
    public void setApproveitemguid(String approveitemguid) {
        this.approveitemguid = approveitemguid;
    }
    
    @Column(name="BUREAUGUID", length=38)

    public String getBureauguid() {
        return this.bureauguid;
    }
    
    public void setBureauguid(String bureauguid) {
        this.bureauguid = bureauguid;
    }
    
    @Column(name="APPROVEITEMSN", length=1000)

    public String getApproveitemsn() {
        return this.approveitemsn;
    }
    
    public void setApproveitemsn(String approveitemsn) {
        this.approveitemsn = approveitemsn;
    }
    
    @Column(name="APPROVEITEMNAME", nullable=false, length=2500)

    public String getApproveitemname() {
        return this.approveitemname;
    }
    
    public void setApproveitemname(String approveitemname) {
        this.approveitemname = approveitemname;
    }
    
    @Column(name="APPROVEITEMPRIORITY", precision=3, scale=0)

    public Integer getApproveitempriority() {
        return this.approveitempriority;
    }
    
    public void setApproveitempriority(Integer approveitempriority) {
        this.approveitempriority = approveitempriority;
    }
    
    @Column(name="TIMELIMIT", precision=3, scale=0)

    public Integer getTimelimit() {
        return this.timelimit;
    }
    
    public void setTimelimit(Integer timelimit) {
        this.timelimit = timelimit;
    }
    
    @Column(name="TIMELIMITDESC", length=2000)

    public String getTimelimitdesc() {
        return this.timelimitdesc;
    }
    
    public void setTimelimitdesc(String timelimitdesc) {
        this.timelimitdesc = timelimitdesc;
    }
    
    @Column(name="DECLAREUNITTYPE", nullable=false, length=6)

    public String getDeclareunittype() {
        return this.declareunittype;
    }
    
    public void setDeclareunittype(String declareunittype) {
        this.declareunittype = declareunittype;
    }
    
    @Column(name="APPROVEITEMTYPE", length=150)

    public String getApproveitemtype() {
        return this.approveitemtype;
    }
    
    public void setApproveitemtype(String approveitemtype) {
        this.approveitemtype = approveitemtype;
    }
    
    @Column(name="APPROVEITEMTABINDEX", precision=6, scale=0)

    public Integer getApproveitemtabindex() {
        return this.approveitemtabindex;
    }
    
    public void setApproveitemtabindex(Integer approveitemtabindex) {
        this.approveitemtabindex = approveitemtabindex;
    }
    
    @Column(name="APPROVEITEMSTATUS", length=4)

    public String getApproveitemstatus() {
        return this.approveitemstatus;
    }
    
    public void setApproveitemstatus(String approveitemstatus) {
        this.approveitemstatus = approveitemstatus;
    }
    
    @Column(name="DECLARECONDITION")

    public Clob getDeclarecondition() {
        return this.declarecondition;
    }
    
    public void setDeclarecondition(Clob declarecondition) {
        this.declarecondition = declarecondition;
    }
    
    @Column(name="DECLAREDESC")

    public Clob getDeclaredesc() {
        return this.declaredesc;
    }
    
    public void setDeclaredesc(Clob declaredesc) {
        this.declaredesc = declaredesc;
    }
    
    @Column(name="DECLAREPIC")

    public Blob getDeclarepic() {
        return this.declarepic;
    }
    
    public void setDeclarepic(Blob declarepic) {
        this.declarepic = declarepic;
    }
    
    @Column(name="CHARGEINFO")

    public Clob getChargeinfo() {
        return this.chargeinfo;
    }
    
    public void setChargeinfo(Clob chargeinfo) {
        this.chargeinfo = chargeinfo;
    }
    
    @Column(name="SUPERVISEUNIT")

    public Clob getSuperviseunit() {
        return this.superviseunit;
    }
    
    public void setSuperviseunit(Clob superviseunit) {
        this.superviseunit = superviseunit;
    }
    
    @Column(name="BUREAUTYPE", length=140)

    public String getBureautype() {
        return this.bureautype;
    }
    
    public void setBureautype(String bureautype) {
        this.bureautype = bureautype;
    }
    
    @Column(name="APPROVEITEMSCOPE", length=1100)

    public String getApproveitemscope() {
        return this.approveitemscope;
    }
    
    public void setApproveitemscope(String approveitemscope) {
        this.approveitemscope = approveitemscope;
    }
    
    @Column(name="FLAG", length=1)

    public String getFlag() {
        return this.flag;
    }
    
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    @Column(name="WORKFLOWGUID", length=38)

    public String getWorkflowguid() {
        return this.workflowguid;
    }
    
    public void setWorkflowguid(String workflowguid) {
        this.workflowguid = workflowguid;
    }
    
    @Column(name="DEBUG", precision=1, scale=0)

    public Integer getDebug() {
        return this.debug;
    }
    
    public void setDebug(Integer debug) {
        this.debug = debug;
    }
    
    @Column(name="THEFILENAME", length=1000)

    public String getThefilename() {
        return this.thefilename;
    }
    
    public void setThefilename(String thefilename) {
        this.thefilename = thefilename;
    }
    
    @Column(name="WORKFLOWGUID2", length=38)

    public String getWorkflowguid2() {
        return this.workflowguid2;
    }
    
    public void setWorkflowguid2(String workflowguid2) {
        this.workflowguid2 = workflowguid2;
    }
@Temporal(TemporalType.DATE)
    @Column(name="LASTMODIFYTIME", length=7)

    public Date getLastmodifytime() {
        return this.lastmodifytime;
    }
    
   public void setLastmodifytime(Date lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }
    
    @Column(name="LASTMODIFYMAN", length=500)

    public String getLastmodifyman() {
        return this.lastmodifyman;
    }
    
    public void setLastmodifyman(String lastmodifyman) {
        this.lastmodifyman = lastmodifyman;
    }
    
    @Column(name="TASKGUID", length=38)

    public String getTaskguid() {
        return this.taskguid;
    }
    
    public void setTaskguid(String taskguid) {
        this.taskguid = taskguid;
    }
    
    @Column(name="FTCODE", length=180)

    public String getFtcode() {
        return this.ftcode;
    }
    
    public void setFtcode(String ftcode) {
        this.ftcode = ftcode;
    }
    
    @Column(name="FTSUBCODE", length=3)

    public String getFtsubcode() {
        return this.ftsubcode;
    }
    
    public void setFtsubcode(String ftsubcode) {
        this.ftsubcode = ftsubcode;
    }
    
    @Column(name="INSYSTEM", length=100)

    public String getInsystem() {
        return this.insystem;
    }
    
    public void setInsystem(String insystem) {
        this.insystem = insystem;
    }
    
    @Column(name="ORDERPERMISSION", length=10)

    public String getOrderpermission() {
        return this.orderpermission;
    }
    
    public void setOrderpermission(String orderpermission) {
        this.orderpermission = orderpermission;
    }
    
    @Column(name="ORDERNUM", precision=22, scale=0)

    public Integer getOrdernum() {
        return this.ordernum;
    }
    
    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }
    
    @Column(name="ORDERDAY", length=20)

    public String getOrderday() {
        return this.orderday;
    }
    
    public void setOrderday(String orderday) {
        this.orderday = orderday;
    }
    
    @Column(name="ORDERDAYNUM", precision=22, scale=0)

    public Integer getOrderdaynum() {
        return this.orderdaynum;
    }
    
    public void setOrderdaynum(Integer orderdaynum) {
        this.orderdaynum = orderdaynum;
    }
    
    @Column(name="BUREAUORSTREET", length=1)

    public String getBureauorstreet() {
        return this.bureauorstreet;
    }
    
    public void setBureauorstreet(String bureauorstreet) {
        this.bureauorstreet = bureauorstreet;
    }
    
    @Column(name="STREETNAME", length=1200)

    public String getStreetname() {
        return this.streetname;
    }
    
    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }
    
    @Column(name="BGUID", length=38)

    public String getBguid() {
        return this.bguid;
    }
    
    public void setBguid(String bguid) {
        this.bguid = bguid;
    }
    
    @Column(name="IF_NEED_LOGIN", precision=22, scale=0)

    public Integer getIfNeedLogin() {
        return this.ifNeedLogin;
    }
    
    public void setIfNeedLogin(Integer ifNeedLogin) {
        this.ifNeedLogin = ifNeedLogin;
    }
    
    @Column(name="DEPARTMENTGUID", length=38)

    public String getDepartmentguid() {
        return this.departmentguid;
    }
    
    public void setDepartmentguid(String departmentguid) {
        this.departmentguid = departmentguid;
    }
    
    @Column(name="CLASSIFYGUID", length=38)

    public String getClassifyguid() {
        return this.classifyguid;
    }
    
    public void setClassifyguid(String classifyguid) {
        this.classifyguid = classifyguid;
    }
    
    @Column(name="ISPROJECT", precision=1, scale=0)

    public Integer getIsproject() {
        return this.isproject;
    }
    
    public void setIsproject(Integer isproject) {
        this.isproject = isproject;
    }
    
    @Column(name="ISEXTERNAL", precision=1, scale=0)

    public Integer getIsexternal() {
        return this.isexternal;
    }
    
    public void setIsexternal(Integer isexternal) {
        this.isexternal = isexternal;
    }
    
    @Column(name="APPROVEITEMDESC", length=4000)

    public String getApproveitemdesc() {
        return this.approveitemdesc;
    }
    
    public void setApproveitemdesc(String approveitemdesc) {
        this.approveitemdesc = approveitemdesc;
    }
    
    @Column(name="CLASSIFY", length=500)

    public String getClassify() {
        return this.classify;
    }
    
    public void setClassify(String classify) {
        this.classify = classify;
    }
    
    @Column(name="CONSULTATIONPERSON", length=100)

    public String getConsultationperson() {
        return this.consultationperson;
    }
    
    public void setConsultationperson(String consultationperson) {
        this.consultationperson = consultationperson;
    }
    
    @Column(name="ONLINEDECLAREADDR", length=20)

    public String getOnlinedeclareaddr() {
        return this.onlinedeclareaddr;
    }
    
    public void setOnlinedeclareaddr(String onlinedeclareaddr) {
        this.onlinedeclareaddr = onlinedeclareaddr;
    }
    
    @Column(name="BANSHIZHINAN", length=4000)

    public String getBanshizhinan() {
        return this.banshizhinan;
    }
    
    public void setBanshizhinan(String banshizhinan) {
        this.banshizhinan = banshizhinan;
    }
    
    @Column(name="ISONLINE", length=10)

    public String getIsonline() {
        return this.isonline;
    }
    
    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }
    
    @Column(name="CATEGORY1", precision=2, scale=0)

    public Integer getCategory1() {
        return this.category1;
    }
    
    public void setCategory1(Integer category1) {
        this.category1 = category1;
    }
    
    @Column(name="CATEGORY2", precision=2, scale=0)

    public Integer getCategory2() {
        return this.category2;
    }
    
    public void setCategory2(Integer category2) {
        this.category2 = category2;
    }
    
    @Column(name="CATEGORY3", precision=2, scale=0)

    public Integer getCategory3() {
        return this.category3;
    }
    
    public void setCategory3(Integer category3) {
        this.category3 = category3;
    }
    
    @Column(name="APPROVEITEMCONTENT", length=4000)

    public String getApproveitemcontent() {
        return this.approveitemcontent;
    }
    
    public void setApproveitemcontent(String approveitemcontent) {
        this.approveitemcontent = approveitemcontent;
    }
    
    @Column(name="FALVYIJU", length=4000)

    public String getFalvyiju() {
        return this.falvyiju;
    }
    
    public void setFalvyiju(String falvyiju) {
        this.falvyiju = falvyiju;
    }
    
    @Column(name="JIGUAN", length=4000)

    public String getJiguan() {
        return this.jiguan;
    }
    
    public void setJiguan(String jiguan) {
        this.jiguan = jiguan;
    }
    
    @Column(name="ZHENGJIANMINGCHENG", length=4000)

    public String getZhengjianmingcheng() {
        return this.zhengjianmingcheng;
    }
    
    public void setZhengjianmingcheng(String zhengjianmingcheng) {
        this.zhengjianmingcheng = zhengjianmingcheng;
    }
    
    @Column(name="XIAOLI", length=4000)

    public String getXiaoli() {
        return this.xiaoli;
    }
    
    public void setXiaoli(String xiaoli) {
        this.xiaoli = xiaoli;
    }
    
    @Column(name="NIANSHEN", length=1000)

    public String getNianshen() {
        return this.nianshen;
    }
    
    public void setNianshen(String nianshen) {
        this.nianshen = nianshen;
    }
    
    @Column(name="TYPE", length=1000)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="ACCEPTTIME", length=2000)

    public String getAccepttime() {
        return this.accepttime;
    }
    
    public void setAccepttime(String accepttime) {
        this.accepttime = accepttime;
    }
    
    @Column(name="ACCEPTADDRESS", length=2000)

    public String getAcceptaddress() {
        return this.acceptaddress;
    }
    
    public void setAcceptaddress(String acceptaddress) {
        this.acceptaddress = acceptaddress;
    }
    
    @Column(name="LINKTYPE", length=2000)

    public String getLinktype() {
        return this.linktype;
    }
    
    public void setLinktype(String linktype) {
        this.linktype = linktype;
    }
    
    @Column(name="ACCEPTJIGUAN", length=3000)

    public String getAcceptjiguan() {
        return this.acceptjiguan;
    }
    
    public void setAcceptjiguan(String acceptjiguan) {
        this.acceptjiguan = acceptjiguan;
    }
    
    @Column(name="TABLEDESCRIPTION", length=4000)

    public String getTabledescription() {
        return this.tabledescription;
    }
    
    public void setTabledescription(String tabledescription) {
        this.tabledescription = tabledescription;
    }
    
    @Column(name="FORWARDURL", length=100)

    public String getForwardurl() {
        return this.forwardurl;
    }
    
    public void setForwardurl(String forwardurl) {
        this.forwardurl = forwardurl;
    }
    
    @Column(name="SERVICETYPE", precision=1, scale=0)

    public Integer getServicetype() {
        return this.servicetype;
    }
    
    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }
    
    @Column(name="PARENTAPPROVEITEMGUID", length=38)

    public String getParentapproveitemguid() {
        return this.parentapproveitemguid;
    }
    
    public void setParentapproveitemguid(String parentapproveitemguid) {
        this.parentapproveitemguid = parentapproveitemguid;
    }
    
    @Column(name="CHILDAPPROVEITEMNUM", precision=22, scale=0)

    public Integer getChildapproveitemnum() {
        return this.childapproveitemnum;
    }
    
    public void setChildapproveitemnum(Integer childapproveitemnum) {
        this.childapproveitemnum = childapproveitemnum;
    }
    
    @Column(name="APPROVEPLACE", precision=22, scale=0)

    public Integer getApproveplace() {
        return this.approveplace;
    }
    
    public void setApproveplace(Integer approveplace) {
        this.approveplace = approveplace;
    }
    
    @Column(name="DOCTYPEGUID", length=500)

    public String getDoctypeguid() {
        return this.doctypeguid;
    }
    
    public void setDoctypeguid(String doctypeguid) {
        this.doctypeguid = doctypeguid;
    }
    
    @Column(name="STATUS", length=1)

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Column(name="WINDOWTYPEGUID", length=38)

    public String getWindowtypeguid() {
        return this.windowtypeguid;
    }
    
    public void setWindowtypeguid(String windowtypeguid) {
        this.windowtypeguid = windowtypeguid;
    }
    
    @Column(name="ISINHALL", length=1)

    public String getIsinhall() {
        return this.isinhall;
    }
    
    public void setIsinhall(String isinhall) {
        this.isinhall = isinhall;
    }
    
    @Column(name="SUPERVISEGUID", length=4000)

    public String getSuperviseguid() {
        return this.superviseguid;
    }
    
    public void setSuperviseguid(String superviseguid) {
        this.superviseguid = superviseguid;
    }
    
    @Column(name="LEGALTIMELIMIT", precision=3, scale=0)

    public Integer getLegaltimelimit() {
        return this.legaltimelimit;
    }
    
    public void setLegaltimelimit(Integer legaltimelimit) {
        this.legaltimelimit = legaltimelimit;
    }
    
    @Column(name="ISZZ", length=5)

    public String getIszz() {
        return this.iszz;
    }
    
    public void setIszz(String iszz) {
        this.iszz = iszz;
    }
    
    @Column(name="TWOFLOW", length=1)

    public String getTwoflow() {
        return this.twoflow;
    }
    
    public void setTwoflow(String twoflow) {
        this.twoflow = twoflow;
    }
    
    @Column(name="TWOFLOWDES", length=2000)

    public String getTwoflowdes() {
        return this.twoflowdes;
    }
    
    public void setTwoflowdes(String twoflowdes) {
        this.twoflowdes = twoflowdes;
    }
    
    @Column(name="THREEFLOW", length=1)

    public String getThreeflow() {
        return this.threeflow;
    }
    
    public void setThreeflow(String threeflow) {
        this.threeflow = threeflow;
    }
    
    @Column(name="THREEFLOWDES", length=2000)

    public String getThreeflowdes() {
        return this.threeflowdes;
    }
    
    public void setThreeflowdes(String threeflowdes) {
        this.threeflowdes = threeflowdes;
    }
    
    @Column(name="ONEFLOW", length=1)

    public String getOneflow() {
        return this.oneflow;
    }
    
    public void setOneflow(String oneflow) {
        this.oneflow = oneflow;
    }
    
    @Column(name="ONEFLOWDES", length=2000)

    public String getOneflowdes() {
        return this.oneflowdes;
    }
    
    public void setOneflowdes(String oneflowdes) {
        this.oneflowdes = oneflowdes;
    }
    
    @Column(name="ADMINBUREAUGUID", length=76)

    public String getAdminbureauguid() {
        return this.adminbureauguid;
    }
    
    public void setAdminbureauguid(String adminbureauguid) {
        this.adminbureauguid = adminbureauguid;
    }
    
    @Column(name="SXBM", length=50)

    public String getSxbm() {
        return this.sxbm;
    }
    
    public void setSxbm(String sxbm) {
        this.sxbm = sxbm;
    }
   
    @Column(name="SEALTYPE", length=2)

    public String getSealType() {
        return this.sealtype;
    }
    
    public void setSealType(String sealtype) {
        this.sealtype = sealtype;
    }







}