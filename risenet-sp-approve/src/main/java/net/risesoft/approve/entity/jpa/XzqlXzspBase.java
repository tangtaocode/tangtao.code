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
 * AbstractXzqlXzspBase entity provides the base persistence definition of the
 * XzqlXzspBase entity.
 * 事项基础表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XZQL_XZSP_BASE")

public  class XzqlXzspBase  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
     private String itemid;				//	"ITEMID" VARCHAR2(50 BYTE) NOT NULL ,				事项ID:用于串联整个业务
     private String approveitemname;	//	"APPROVEITEMNAME" VARCHAR2(500 BYTE) NULL , 		行政审批事项名称
     private Long orderno;				//	"ORDERNO" NUMBER NULL ,								序号:用于显示排序
     private String departmentid;		//	"DEPARTMENTID" VARCHAR2(50 BYTE) NULL ,				部门ID(暂时不用，有关系表关联)
     private String approveitembm;		//	"APPROVEITEMBM" VARCHAR2(50 BYTE) NULL ,			行政审批编码:机构代码+种类码+三位行政审批事项编号(种类码：1=行政许可，2=非行政许可，4=行政服务；事项编码：3位，如001号、002号等)	
     private String approveitemcontent;	//	"APPROVEITEMCONTENT" VARCHAR2(4000 BYTE) NULL ,		对审批事项的具体细化，体现其实质内容。如无特别内容，可填写该事项名称。
     private String approveitembasis;	//	"APPROVEITEMBASIS" VARCHAR2(4000 BYTE) NULL ,		行政审批设定依据
     private String quantitylimit;		//	"QUANTITYLIMIT" VARCHAR2(4000 BYTE) NULL ,			行政审批数量及方式:注明行政审批有无数量限制，同时注明申请审批的方式。有数量限制的，应当采用公开、公平的方式予以审批。
     private String declarecondition;	//	"DECLARECONDITION" VARCHAR2(4000 BYTE) NULL ,		行政审批条件：填写行政机关准予申请审批的法定条件或标准
     private String rereq;				//	"REREQ" VARCHAR2(4000 BYTE) NULL ,					申请材料：填写说明：1、材料类别分为：A格式化数据、B电子版材料、C纸质材料、D外部数据;2、数据关联是指：材料类别为外部数据时，应注明来源于何种系统。
     private String reform;				//	"REFORM" BLOB NULL ,  								申请表格:附件，多个表格整成一个word附件
     private String acceptjiguan;		// 	"ACCEPTJIGUAN" VARCHAR2(4000 BYTE) NULL ,			申请受理机关
     private String decideorg;			// 	"DECIDEORG" VARCHAR2(4000 BYTE) NULL ,				决定机关
     private String process;			// 	"PROCESS" VARCHAR2(4000 BYTE) NULL ,				行政审批程序:填写申请和作出行政审批决定的主要步骤、环节。采用告知承诺制方式或网上审批方式实施审批的，应在此予以说明。
     private String innerflow;			// 	"INNERFLOW" VARCHAR2(4000 BYTE) NULL ,				行政审批内部流程
     private String flowcharts;			// 	"FLOWCHARTS" BLOB NULL ,							申请流程图
     private String declaredesc;		// 	"DECLAREDESC" VARCHAR2(2000 BYTE) NULL ,			申办流程图说明。(现改为网上办理流程)
     private String timelimit;			// 	"TIMELIMIT" VARCHAR2(50 BYTE) NULL ,				法定办理时限
     private String promiselimittime;	// 	"PROMISELIMITTIME" VARCHAR2(50 BYTE) NULL ,			承诺办理时限	
     private String zhengjianmingcheng; // 	"ZHENGJIANMINGCHENG" VARCHAR2(1000 BYTE) NULL ,		行政审批证件及有效期限
     private String xiaoli;				// 	"XIAOLI" VARCHAR2(4000 BYTE) NULL ,					行政审批的法律效力	
     private String legaltype;			// 	"LEGALTYPE" VARCHAR2(50 BYTE) NULL ,				法规类型：法律、国家部位、地方法规
     private String chargeinfo;			// 	"CHARGEINFO" VARCHAR2(500 BYTE) NULL ,				收费
     private String nianshen;			// 	"NIANSHEN" VARCHAR2(4000 BYTE) NULL ,				年审
     private String legalremedy;		// 	"LEGALREMEDY" VARCHAR2(500 BYTE) NULL ,   			法律救济
     private String bureautype;			// 	"BUREAUTYPE" VARCHAR2(50 BYTE) NULL ,				事项类型：行政许可、非行政许可、行政服务
     private String approveobject;		// 	"APPROVEOBJECT" VARCHAR2(50 BYTE) NULL ,			行政审批对象：个人、企业、团体
     private String approveitemtype;	// 	"APPROVEITEMTYPE" VARCHAR2(50 BYTE) NULL ,			行政审批类型：002承诺件（承诺一定时间办完）、001即办件（即时办完），003上报件，004联报件
     private String remark;				// 	"REMARK" VARCHAR2(100 BYTE) NULL ,					备注
     private Date handletime;			// 	"HANDLETIME" DATE NULL ,							操作时间
     private String handleperson;		// 	"HANDLEPERSON" VARCHAR2(50 BYTE) NULL ,				操作人
     private String status;				// 	"STATUS" VARCHAR2(50 BYTE) NULL ,					状态：1有效；0无效；2待确认
     private String handlestate;		// 	"HANDLESTATE" CHAR(1 BYTE) NULL ,					操作状态：0作废；1已提交；2暂存；
     private String reformname;			// 	"REFORMNAME" VARCHAR2(200 BYTE) NULL ,				申请表格附件名称
     private String flowchartsname;		// 	"FLOWCHARTSNAME" VARCHAR2(200 BYTE) NULL ,			申请流程图附件名称
     private String doctypeguid;		// 	"DOCTYPEGUID" CLOB NULL ,							材料对应证照guid串。1对多关系。对应t_bdex_doctype表
     private String doctypename;		// 	"DOCTYPENAME" CLOB NULL ,							材料对应证照NAME串。1对多关系。对应t_bdex_doctype表
     private String ischangetoxzsp;		// 	"ISCHANGETOXZSP" CHAR(1 BYTE) DEFAULT 0  NULL ,		是否搬迁至行政审批；0未搬迁，1搬迁成功；2搬迁失败；
     private String ischangetospjc;		// 	"ISCHANGETOSPJC" CHAR(1 BYTE) DEFAULT 0  NULL , 	是否搬迁至审批监察前置机；0未搬迁，1搬迁成功；2搬迁失败
     private String basisfile;			// 	"BASISFILE" BLOB NULL ,								权力依据附件
     private String basisname;			// 	"BASISNAME" VARCHAR2(200 BYTE) NULL ,				权力依据附件名称
     private String handlepersonguid;	// 	"HANDLEPERSONGUID" VARCHAR2(50 BYTE) NULL ,			操作人GUID
     private String itemclass;			// 	"ITEMCLASS" VARCHAR2(4000 BYTE) NULL ,				事项类别：生育（个人）、土地房产（个人）等
     private String address;			// 	"ADDRESS" VARCHAR2(2000 BYTE) NULL ,				审批办理地点
     private String accepttime;			// 	"ACCEPTTIME" VARCHAR2(500 BYTE) NULL ,				受理时间段
     private String linktype;			// 	"LINKTYPE" VARCHAR2(500 BYTE) NULL ,				联系方式
     private String catalogsguid;		// 	"CATALOGSGUID" VARCHAR2(50 BYTE) NULL ,				父节点大类GUID，如果为空则直接对应部门下
     private String adminorgid;			// 	"ADMINORGID" VARCHAR2(50 BYTE) NULL ,				主管部门
     private String isexchangebsdt;		// 	"ISEXCHANGEBSDT" CHAR(1 BYTE) DEFAULT 0  NULL ,		是否同步至网上办事大厅中间库：0未同步、1同步成功、2同步失败
     private String management;			// 	"MANAGEMENT" VARCHAR2(50 BYTE) NULL ,				管理方式。0直接保留、1服务前移、2部分放权、3部分委托、4全部放权、5全部委托、6代市受理、7代市管理	
     private String timelimittype;		// 	"TIMELIMITTYPE" VARCHAR2(50 BYTE) NULL ,			法定时限类型，0工作日或者1自然日
     private String promiselimittimetype;// 	"PROMISELIMITTIMETYPE" VARCHAR2(50 BYTE) NULL ,	承诺时限类型，0工作日或者1自然日
     private String directory;			// 	"DIRECTORY" VARCHAR2(50 BYTE) NULL ,				所属行政目录，0罗湖行政审批改革目录、1罗湖服务事项改革目录、2其他
     private String accountability;		// 	"ACCOUNTABILITY" VARCHAR2(50 BYTE) NULL ,			事项所属负责制，0单位领导负责制、1科长负责制、2首办负责制
     private String isdelegate;			// 	"ISDELEGATE" VARCHAR2(50 BYTE) NULL ,				是否属下放事项，1是，0否(参照市规范新加)
     private String delegatetype;		// 	"DELEGATETYPE" VARCHAR2(50 BYTE) NULL ,				下放类型，1市里下放到区里，2区里下放到街道社区
     private String needCharge;			// 	"NEED_CHARGE" VARCHAR2(1 BYTE) NULL ,				是否收费0：否，1：是(参照市规范新加)
     private String chargeName;			// 	"CHARGE_NAME" VARCHAR2(2000 BYTE) NULL ,			收费名称(参照市规范新加)
     private String chargeStandard;		// 	"CHARGE_STANDARD" VARCHAR2(4000 BYTE) NULL ,		收费标准(参照市规范新加)
     private String chargeBasis;		// 	"CHARGE_BASIS" VARCHAR2(4000 BYTE) NULL ,			收费依据(参照市规范新加)
     private String supervisionFlag;	// 	"SUPERVISION_FLAG" VARCHAR2(1 BYTE) NULL ,			是否纳入监察0 ：否1：是
     private String suitOnline;			// 	"SUIT_ONLINE" VARCHAR2(1 BYTE) NULL ,				是否适合网上办理。1：适合，0：不适合
     private String onlineReason;		// 	"ONLINE_REASON" VARCHAR2(4000 BYTE) NULL ,			不适合网上办理原因(当suit_online为0时才报送该内容)	
     private String needAnnual;			// 	"NEED_ANNUAL" VARCHAR2(1 BYTE) NULL ,				是否年检0：否，1：是
     private String annualDate;			// 	"ANNUAL_DATE" VARCHAR2(50 BYTE) NULL ,				年检时间
     private String annualType;			// 	"ANNUAL_TYPE" VARCHAR2(1 BYTE) NULL ,				年检方式：1：定期，2：滚动，3：网上
     private String annual;				// 	"ANNUAL" VARCHAR2(2000 BYTE) NULL ,					年检依据
     private String isbsdtjc;			// 	"ISBSDTJC" VARCHAR2(1 BYTE) DEFAULT 0  NULL ,		交换到办事大厅监察，0未交换，1交换成功，2交换失败
     private String timelimitdesc;		// 	"TIMELIMITDESC" VARCHAR2(2000 BYTE) NULL ,			法定时限依据
     private String promiselimittimedesc;// 	"PROMISELIMITTIMEDESC" VARCHAR2(2000 BYTE) NULL,承诺时限依据
     private String specialProgramFlag;	// 	"SPECIAL_PROGRAM_FLAG" VARCHAR2(1 BYTE) NULL 		是否有特别程序


    // Constructors

  

   
    // Property accessors
    @Id
 	@Column(name = "ITEMID", length = 38, nullable = false)
 	@GeneratedValue(generator = "uuid")
 	@GenericGenerator(name = "uuid", strategy = "assigned") 

    public String getItemid() {
        return this.itemid;
    }
    
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }
    
    @Column(name="APPROVEITEMNAME", length=500)

    public String getApproveitemname() {
        return this.approveitemname;
    }
    
    public void setApproveitemname(String approveitemname) {
        this.approveitemname = approveitemname;
    }
    
    @Column(name="ORDERNO", precision=22, scale=0)

    public Long getOrderno() {
        return this.orderno;
    }
    
    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }
    
    @Column(name="DEPARTMENTID", length=50)

    public String getDepartmentid() {
        return this.departmentid;
    }
    
    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }
    
    @Column(name="APPROVEITEMBM", length=50)

    public String getApproveitembm() {
        return this.approveitembm;
    }
    
    public void setApproveitembm(String approveitembm) {
        this.approveitembm = approveitembm;
    }
    
    @Column(name="APPROVEITEMCONTENT", length=4000)

    public String getApproveitemcontent() {
        return this.approveitemcontent;
    }
    
    public void setApproveitemcontent(String approveitemcontent) {
        this.approveitemcontent = approveitemcontent;
    }
    
    @Column(name="APPROVEITEMBASIS", length=4000)

    public String getApproveitembasis() {
        return this.approveitembasis;
    }
    
    public void setApproveitembasis(String approveitembasis) {
        this.approveitembasis = approveitembasis;
    }
    
    @Column(name="QUANTITYLIMIT", length=4000)

    public String getQuantitylimit() {
        return this.quantitylimit;
    }
    
    public void setQuantitylimit(String quantitylimit) {
        this.quantitylimit = quantitylimit;
    }
    
    @Column(name="DECLARECONDITION", length=4000)

    public String getDeclarecondition() {
        return this.declarecondition;
    }
    
    public void setDeclarecondition(String declarecondition) {
        this.declarecondition = declarecondition;
    }
    
    @Column(name="REREQ", length=4000)

    public String getRereq() {
        return this.rereq;
    }
    
    public void setRereq(String rereq) {
        this.rereq = rereq;
    }
    
    @Column(name="REFORM")

    public String getReform() {
        return this.reform;
    }
    
    public void setReform(String reform) {
        this.reform = reform;
    }
    
    @Column(name="ACCEPTJIGUAN", length=4000)

    public String getAcceptjiguan() {
        return this.acceptjiguan;
    }
    
    public void setAcceptjiguan(String acceptjiguan) {
        this.acceptjiguan = acceptjiguan;
    }
    
    @Column(name="DECIDEORG", length=4000)

    public String getDecideorg() {
        return this.decideorg;
    }
    
    public void setDecideorg(String decideorg) {
        this.decideorg = decideorg;
    }
    
    @Column(name="PROCESS", length=4000)

    public String getProcess() {
        return this.process;
    }
    
    public void setProcess(String process) {
        this.process = process;
    }
    
    @Column(name="INNERFLOW", length=4000)

    public String getInnerflow() {
        return this.innerflow;
    }
    
    public void setInnerflow(String innerflow) {
        this.innerflow = innerflow;
    }
    
    @Column(name="FLOWCHARTS")

    public String getFlowcharts() {
        return this.flowcharts;
    }
    
    public void setFlowcharts(String flowcharts) {
        this.flowcharts = flowcharts;
    }
    
    @Column(name="DECLAREDESC", length=2000)

    public String getDeclaredesc() {
        return this.declaredesc;
    }
    
    public void setDeclaredesc(String declaredesc) {
        this.declaredesc = declaredesc;
    }
    
    @Column(name="TIMELIMIT", length=50)

    public String getTimelimit() {
        return this.timelimit;
    }
    
    public void setTimelimit(String timelimit) {
        this.timelimit = timelimit;
    }
    
    @Column(name="PROMISELIMITTIME", length=50)

    public String getPromiselimittime() {
        return this.promiselimittime;
    }
    
    public void setPromiselimittime(String promiselimittime) {
        this.promiselimittime = promiselimittime;
    }
    
    @Column(name="ZHENGJIANMINGCHENG", length=1000)

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
    
    @Column(name="LEGALTYPE", length=50)

    public String getLegaltype() {
        return this.legaltype;
    }
    
    public void setLegaltype(String legaltype) {
        this.legaltype = legaltype;
    }
    
    @Column(name="CHARGEINFO", length=500)

    public String getChargeinfo() {
        return this.chargeinfo;
    }
    
    public void setChargeinfo(String chargeinfo) {
        this.chargeinfo = chargeinfo;
    }
    
    @Column(name="NIANSHEN", length=4000)

    public String getNianshen() {
        return this.nianshen;
    }
    
    public void setNianshen(String nianshen) {
        this.nianshen = nianshen;
    }
    
    @Column(name="LEGALREMEDY", length=500)

    public String getLegalremedy() {
        return this.legalremedy;
    }
    
    public void setLegalremedy(String legalremedy) {
        this.legalremedy = legalremedy;
    }
    
    @Column(name="BUREAUTYPE", length=50)

    public String getBureautype() {
        return this.bureautype;
    }
    
    public void setBureautype(String bureautype) {
        this.bureautype = bureautype;
    }
    
    @Column(name="APPROVEOBJECT", length=50)

    public String getApproveobject() {
        return this.approveobject;
    }
    
    public void setApproveobject(String approveobject) {
        this.approveobject = approveobject;
    }
    
    @Column(name="APPROVEITEMTYPE", length=50)

    public String getApproveitemtype() {
        return this.approveitemtype;
    }
    
    public void setApproveitemtype(String approveitemtype) {
        this.approveitemtype = approveitemtype;
    }
    
    @Column(name="REMARK", length=100)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
@Temporal(TemporalType.DATE)
    @Column(name="HANDLETIME", length=7)

    public Date getHandletime() {
        return this.handletime;
    }
    
    public void setHandletime(Date handletime) {
        this.handletime = handletime;
    }
    
    @Column(name="HANDLEPERSON", length=50)

    public String getHandleperson() {
        return this.handleperson;
    }
    
    public void setHandleperson(String handleperson) {
        this.handleperson = handleperson;
    }
    
    @Column(name="STATUS", length=50)

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Column(name="HANDLESTATE", length=1)

    public String getHandlestate() {
        return this.handlestate;
    }
    
    public void setHandlestate(String handlestate) {
        this.handlestate = handlestate;
    }
    
    @Column(name="REFORMNAME", length=200)

    public String getReformname() {
        return this.reformname;
    }
    
    public void setReformname(String reformname) {
        this.reformname = reformname;
    }
    
    @Column(name="FLOWCHARTSNAME", length=200)

    public String getFlowchartsname() {
        return this.flowchartsname;
    }
    
    public void setFlowchartsname(String flowchartsname) {
        this.flowchartsname = flowchartsname;
    }
    
    @Column(name="DOCTYPEGUID")

    public String getDoctypeguid() {
        return this.doctypeguid;
    }
    
    public void setDoctypeguid(String doctypeguid) {
        this.doctypeguid = doctypeguid;
    }
    
    @Column(name="DOCTYPENAME")

    public String getDoctypename() {
        return this.doctypename;
    }
    
    public void setDoctypename(String doctypename) {
        this.doctypename = doctypename;
    }
    
    @Column(name="ISCHANGETOXZSP", length=1)

    public String getIschangetoxzsp() {
        return this.ischangetoxzsp;
    }
    
    public void setIschangetoxzsp(String ischangetoxzsp) {
        this.ischangetoxzsp = ischangetoxzsp;
    }
    
    @Column(name="ISCHANGETOSPJC", length=1)

    public String getIschangetospjc() {
        return this.ischangetospjc;
    }
    
    public void setIschangetospjc(String ischangetospjc) {
        this.ischangetospjc = ischangetospjc;
    }
    
    @Column(name="BASISFILE")

    public String getBasisfile() {
        return this.basisfile;
    }
    
    public void setBasisfile(String basisfile) {
        this.basisfile = basisfile;
    }
    
    @Column(name="BASISNAME", length=200)

    public String getBasisname() {
        return this.basisname;
    }
    
    public void setBasisname(String basisname) {
        this.basisname = basisname;
    }
    
    @Column(name="HANDLEPERSONGUID", length=50)

    public String getHandlepersonguid() {
        return this.handlepersonguid;
    }
    
    public void setHandlepersonguid(String handlepersonguid) {
        this.handlepersonguid = handlepersonguid;
    }
    
    @Column(name="ITEMCLASS", length=4000)

    public String getItemclass() {
        return this.itemclass;
    }
    
    public void setItemclass(String itemclass) {
        this.itemclass = itemclass;
    }
    
    @Column(name="ADDRESS", length=2000)

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="ACCEPTTIME", length=500)

    public String getAccepttime() {
        return this.accepttime;
    }
    
    public void setAccepttime(String accepttime) {
        this.accepttime = accepttime;
    }
    
    @Column(name="LINKTYPE", length=500)

    public String getLinktype() {
        return this.linktype;
    }
    
    public void setLinktype(String linktype) {
        this.linktype = linktype;
    }
    
    @Column(name="CATALOGSGUID", length=50)

    public String getCatalogsguid() {
        return this.catalogsguid;
    }
    
    public void setCatalogsguid(String catalogsguid) {
        this.catalogsguid = catalogsguid;
    }
    
    @Column(name="ADMINORGID", length=50)

    public String getAdminorgid() {
        return this.adminorgid;
    }
    
    public void setAdminorgid(String adminorgid) {
        this.adminorgid = adminorgid;
    }
    
    @Column(name="ISEXCHANGEBSDT", length=1)

    public String getIsexchangebsdt() {
        return this.isexchangebsdt;
    }
    
    public void setIsexchangebsdt(String isexchangebsdt) {
        this.isexchangebsdt = isexchangebsdt;
    }
    
    @Column(name="MANAGEMENT", length=50)

    public String getManagement() {
        return this.management;
    }
    
    public void setManagement(String management) {
        this.management = management;
    }
    
    @Column(name="TIMELIMITTYPE", length=50)

    public String getTimelimittype() {
        return this.timelimittype;
    }
    
    public void setTimelimittype(String timelimittype) {
        this.timelimittype = timelimittype;
    }
    
    @Column(name="PROMISELIMITTIMETYPE", length=50)

    public String getPromiselimittimetype() {
        return this.promiselimittimetype;
    }
    
    public void setPromiselimittimetype(String promiselimittimetype) {
        this.promiselimittimetype = promiselimittimetype;
    }
    
    @Column(name="DIRECTORY", length=50)

    public String getDirectory() {
        return this.directory;
    }
    
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    
    @Column(name="ACCOUNTABILITY", length=50)

    public String getAccountability() {
        return this.accountability;
    }
    
    public void setAccountability(String accountability) {
        this.accountability = accountability;
    }
    
    @Column(name="ISDELEGATE", length=50)

    public String getIsdelegate() {
        return this.isdelegate;
    }
    
    public void setIsdelegate(String isdelegate) {
        this.isdelegate = isdelegate;
    }
    
    @Column(name="DELEGATETYPE", length=50)

    public String getDelegatetype() {
        return this.delegatetype;
    }
    
    public void setDelegatetype(String delegatetype) {
        this.delegatetype = delegatetype;
    }
    
    @Column(name="NEED_CHARGE", length=1)

    public String getNeedCharge() {
        return this.needCharge;
    }
    
    public void setNeedCharge(String needCharge) {
        this.needCharge = needCharge;
    }
    
    @Column(name="CHARGE_NAME", length=2000)

    public String getChargeName() {
        return this.chargeName;
    }
    
    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }
    
    @Column(name="CHARGE_STANDARD", length=4000)

    public String getChargeStandard() {
        return this.chargeStandard;
    }
    
    public void setChargeStandard(String chargeStandard) {
        this.chargeStandard = chargeStandard;
    }
    
    @Column(name="CHARGE_BASIS", length=4000)

    public String getChargeBasis() {
        return this.chargeBasis;
    }
    
    public void setChargeBasis(String chargeBasis) {
        this.chargeBasis = chargeBasis;
    }
    
    @Column(name="SUPERVISION_FLAG", length=1)

    public String getSupervisionFlag() {
        return this.supervisionFlag;
    }
    
    public void setSupervisionFlag(String supervisionFlag) {
        this.supervisionFlag = supervisionFlag;
    }
    
    @Column(name="SUIT_ONLINE", length=1)

    public String getSuitOnline() {
        return this.suitOnline;
    }
    
    public void setSuitOnline(String suitOnline) {
        this.suitOnline = suitOnline;
    }
    
    @Column(name="ONLINE_REASON", length=4000)

    public String getOnlineReason() {
        return this.onlineReason;
    }
    
    public void setOnlineReason(String onlineReason) {
        this.onlineReason = onlineReason;
    }
    
    @Column(name="NEED_ANNUAL", length=1)

    public String getNeedAnnual() {
        return this.needAnnual;
    }
    
    public void setNeedAnnual(String needAnnual) {
        this.needAnnual = needAnnual;
    }
    
    @Column(name="ANNUAL_DATE", length=50)

    public String getAnnualDate() {
        return this.annualDate;
    }
    
    public void setAnnualDate(String annualDate) {
        this.annualDate = annualDate;
    }
    
    @Column(name="ANNUAL_TYPE", length=1)

    public String getAnnualType() {
        return this.annualType;
    }
    
    public void setAnnualType(String annualType) {
        this.annualType = annualType;
    }
    
    @Column(name="ANNUAL", length=2000)

    public String getAnnual() {
        return this.annual;
    }
    
    public void setAnnual(String annual) {
        this.annual = annual;
    }
    
    @Column(name="ISBSDTJC", length=1)

    public String getIsbsdtjc() {
        return this.isbsdtjc;
    }
    
    public void setIsbsdtjc(String isbsdtjc) {
        this.isbsdtjc = isbsdtjc;
    }
    
    @Column(name="TIMELIMITDESC", length=2000)

    public String getTimelimitdesc() {
        return this.timelimitdesc;
    }
    
    public void setTimelimitdesc(String timelimitdesc) {
        this.timelimitdesc = timelimitdesc;
    }
    
    @Column(name="PROMISELIMITTIMEDESC", length=2000)

    public String getPromiselimittimedesc() {
        return this.promiselimittimedesc;
    }
    
    public void setPromiselimittimedesc(String promiselimittimedesc) {
        this.promiselimittimedesc = promiselimittimedesc;
    }
    
    @Column(name="SPECIAL_PROGRAM_FLAG", length=1)

    public String getSpecialProgramFlag() {
        return this.specialProgramFlag;
    }
    
    public void setSpecialProgramFlag(String specialProgramFlag) {
        this.specialProgramFlag = specialProgramFlag;
    }

	@Override
	public String toString() {
		return "XzqlXzspBase [itemid=" + itemid + ", approveitemname="
				+ approveitemname + ", orderno=" + orderno + ", departmentid="
				+ departmentid + ", approveitembm=" + approveitembm
				+ ", approveitemcontent=" + approveitemcontent
				+ ", approveitembasis=" + approveitembasis + ", quantitylimit="
				+ quantitylimit + ", declarecondition=" + declarecondition
				+ ", rereq=" + rereq + ", reform=" + reform + ", acceptjiguan="
				+ acceptjiguan + ", decideorg=" + decideorg + ", process="
				+ process + ", innerflow=" + innerflow + ", flowcharts="
				+ flowcharts + ", declaredesc=" + declaredesc + ", timelimit="
				+ timelimit + ", promiselimittime=" + promiselimittime
				+ ", zhengjianmingcheng=" + zhengjianmingcheng + ", xiaoli="
				+ xiaoli + ", legaltype=" + legaltype + ", chargeinfo="
				+ chargeinfo + ", nianshen=" + nianshen + ", legalremedy="
				+ legalremedy + ", bureautype=" + bureautype
				+ ", approveobject=" + approveobject + ", approveitemtype="
				+ approveitemtype + ", remark=" + remark + ", handletime="
				+ handletime + ", handleperson=" + handleperson + ", status="
				+ status + ", handlestate=" + handlestate + ", reformname="
				+ reformname + ", flowchartsname=" + flowchartsname
				+ ", doctypeguid=" + doctypeguid + ", doctypename="
				+ doctypename + ", ischangetoxzsp=" + ischangetoxzsp
				+ ", ischangetospjc=" + ischangetospjc + ", basisfile="
				+ basisfile + ", basisname=" + basisname
				+ ", handlepersonguid=" + handlepersonguid + ", itemclass="
				+ itemclass + ", address=" + address + ", accepttime="
				+ accepttime + ", linktype=" + linktype + ", catalogsguid="
				+ catalogsguid + ", adminorgid=" + adminorgid
				+ ", isexchangebsdt=" + isexchangebsdt + ", management="
				+ management + ", timelimittype=" + timelimittype
				+ ", promiselimittimetype=" + promiselimittimetype
				+ ", directory=" + directory + ", accountability="
				+ accountability + ", isdelegate=" + isdelegate
				+ ", delegatetype=" + delegatetype + ", needCharge="
				+ needCharge + ", chargeName=" + chargeName
				+ ", chargeStandard=" + chargeStandard + ", chargeBasis="
				+ chargeBasis + ", supervisionFlag=" + supervisionFlag
				+ ", suitOnline=" + suitOnline + ", onlineReason="
				+ onlineReason + ", needAnnual=" + needAnnual + ", annualDate="
				+ annualDate + ", annualType=" + annualType + ", annual="
				+ annual + ", isbsdtjc=" + isbsdtjc + ", timelimitdesc="
				+ timelimitdesc + ", promiselimittimedesc="
				+ promiselimittimedesc + ", specialProgramFlag="
				+ specialProgramFlag + "]";
	}
   


}