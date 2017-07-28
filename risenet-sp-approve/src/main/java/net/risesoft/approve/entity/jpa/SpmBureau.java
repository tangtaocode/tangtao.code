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
 * AbstractSpmBureau entity provides the base persistence definition of the
 * SpmBureau entity.
 * 局-部门表（审批委办局）
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SPM_BUREAU")
public  class SpmBureau  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String bureauguid;						//	"BUREAUGUID" CHAR(38 BYTE) NOT NULL ,			委办局GUID
     private String departmentGuids;				//	"DEPARTMENT_GUIDS" VARCHAR2(4000 BYTE) NULL ,	委办局下面所有部门departmentguid
     private Long bureausn;							//	"BUREAUSN" NUMBER(4) NULL ,						委办局编号
     private String bureauname;						//	"BUREAUNAME" VARCHAR2(100 BYTE) NOT NULL ,		委办局名称
     private String bureaucnshortname;				//	"BUREAUCNSHORTNAME" VARCHAR2(100 BYTE) NULL ,	委办局中文简称
     private String bureaucnfullname;				//	"BUREAUCNFULLNAME" VARCHAR2(200 BYTE) NULL ,	委办局中文全称	
     private String bureauenshortname;				//	"BUREAUENSHORTNAME" CHAR(2 BYTE) NULL ,			委办局英文简称
     private String bureauenfullname;				//	"BUREAUENFULLNAME" VARCHAR2(200 BYTE) NULL ,	委办局英文全称
     private String bureauconsultingtel;			//	"BUREAUCONSULTINGTEL" VARCHAR2(200 BYTE) NULL , 委办局咨询电话
     private String bureauaddress;					//	"BUREAUADDRESS" VARCHAR2(250 BYTE) NULL ,		委办局办公地址
     private String bureauzip;						//	"BUREAUZIP" CHAR(6 BYTE) NULL ,					委办局邮编
     private String bureautel;						//	"BUREAUTEL" VARCHAR2(100 BYTE) NULL ,			委办局办公电话
     private String bureaufax;						//	"BUREAUFAX" VARCHAR2(100 BYTE) NULL ,			委办局办公传真
     private String bureauemail;					//	"BUREAUEMAIL" VARCHAR2(100 BYTE) NULL ,			委办局电子邮箱
     private String bureaudesc;						//	"BUREAUDESC" CLOB NULL ,						说明	
     private Long bureautabindex;					//	"BUREAUTABINDEX" NUMBER(3) NULL ,				委办局排序号
     private String serviceboardguid;				// 	"SERVICEBOARDGUID" CHAR(38 BYTE) NULL ,
     private String flag;							// 	"FLAG" CHAR(1 BYTE) NULL ,						是否入驻大厅
     private String principal;						// 	"PRINCIPAL" VARCHAR2(10 BYTE) NULL ,
     private String windowsnum;						// 	"WINDOWSNUM" VARCHAR2(80 BYTE) NULL ,			服务窗口号
     private Long senatestar;						// 	"SENATESTAR" NUMBER NULL ,						服务评价星级
     private String isstreet;						// 	"ISSTREET" CHAR(1 BYTE) DEFAULT 0  NULL ,		街道:1,委办局:0
     private String orderpermission;				// 	"ORDERPERMISSION" VARCHAR2(1 BYTE) NULL ,
     private Long ordernum;							// 	"ORDERNUM" NUMBER NULL ,
     private Long isexternal;						// 	"ISEXTERNAL" NUMBER(1) DEFAULT 0  NULL ,		外部审批单位（比如市规划局）置为1
     private Long isstatic;							// 	"ISSTATIC" NUMBER(1) DEFAULT 1  NULL ,			是否事项统计
     private String leaderguids;					// 	"LEADERGUIDS" VARCHAR2(2000 BYTE) NULL ,		部门领导GUID
     private String leadernames;					// 	"LEADERNAMES" VARCHAR2(2000 BYTE) NULL ,		部门领导
     private String institutioncode;				// 	"INSTITUTIONCODE" VARCHAR2(9 BYTE) NULL ,		组织机构代码
     private Date createdate;						// 	"CREATEDATE" DATE DEFAULT sysdate  NULL ,		入驻时间
     private Date updatetime;						// 	"UPDATETIME" DATE NULL ,						最后更新时间
     private String districtcode;					// 	"DISTRICTCODE" VARCHAR2(12 BYTE) NULL ,			行政区划代码
     private String superiorinstituioncode;			// 	"SUPERIORINSTITUIONCODE" VARCHAR2(9 BYTE) NULL ,上级部门组织机构代码
     private String superiorname;					// 	"SUPERIORNAME" VARCHAR2(400 BYTE) NULL ,		上级部门名称
     private String ispublic;						// 	"ISPUBLIC" CHAR(1 BYTE) DEFAULT '1'  NULL ,		0外网不公示，1外网公示
     private String division;						// 	"DIVISION" VARCHAR2(256 BYTE) NULL ,			行政区划名称
     private String duties;							// 	"DUTIES" VARCHAR2(4000 BYTE) NULL ,				机构职能
     private String orglabel;						// 	"ORGLABEL" VARCHAR2(50 BYTE) NULL ,				机构标签
     private String orgtype;						// 	"ORGTYPE" VARCHAR2(50 BYTE) NULL ,				机构类型。一级政府、政府部门
     private String extendprop;						// 	"EXTENDPROP" VARCHAR2(4000 BYTE) NULL 			扩展属性
     private String istoxzsp;   					//ISTOXZSP	是否同步到行政审批
     private String parentguid;                    //PARENTGUID
    // Constructors

    public String getIstoxzsp() {
		return istoxzsp;
	}

	public void setIstoxzsp(String istoxzsp) {
		this.istoxzsp = istoxzsp;
	}

	public String getParentguid() {
		return parentguid;
	}

	public SpmBureau(String bureauguid, String departmentGuids, Long bureausn,
			String bureauname, String bureaucnshortname,
			String bureaucnfullname, String bureauenshortname,
			String bureauenfullname, String bureauconsultingtel,
			String bureauaddress, String bureauzip, String bureautel,
			String bureaufax, String bureauemail, String bureaudesc,
			Long bureautabindex, String serviceboardguid, String flag,
			String principal, String windowsnum, Long senatestar,
			String isstreet, String orderpermission, Long ordernum,
			Long isexternal, Long isstatic, String leaderguids,
			String leadernames, String institutioncode, Date createdate,
			Date updatetime, String districtcode,
			String superiorinstituioncode, String superiorname,
			String ispublic, String division, String duties, String orglabel,
			String orgtype, String extendprop, String istoxzsp,
			String parentguid) {
		super();
		this.bureauguid = bureauguid;
		this.departmentGuids = departmentGuids;
		this.bureausn = bureausn;
		this.bureauname = bureauname;
		this.bureaucnshortname = bureaucnshortname;
		this.bureaucnfullname = bureaucnfullname;
		this.bureauenshortname = bureauenshortname;
		this.bureauenfullname = bureauenfullname;
		this.bureauconsultingtel = bureauconsultingtel;
		this.bureauaddress = bureauaddress;
		this.bureauzip = bureauzip;
		this.bureautel = bureautel;
		this.bureaufax = bureaufax;
		this.bureauemail = bureauemail;
		this.bureaudesc = bureaudesc;
		this.bureautabindex = bureautabindex;
		this.serviceboardguid = serviceboardguid;
		this.flag = flag;
		this.principal = principal;
		this.windowsnum = windowsnum;
		this.senatestar = senatestar;
		this.isstreet = isstreet;
		this.orderpermission = orderpermission;
		this.ordernum = ordernum;
		this.isexternal = isexternal;
		this.isstatic = isstatic;
		this.leaderguids = leaderguids;
		this.leadernames = leadernames;
		this.institutioncode = institutioncode;
		this.createdate = createdate;
		this.updatetime = updatetime;
		this.districtcode = districtcode;
		this.superiorinstituioncode = superiorinstituioncode;
		this.superiorname = superiorname;
		this.ispublic = ispublic;
		this.division = division;
		this.duties = duties;
		this.orglabel = orglabel;
		this.orgtype = orgtype;
		this.extendprop = extendprop;
		this.istoxzsp = istoxzsp;
		this.parentguid = parentguid;
	}

	public void setParentguid(String parentguid) {
		this.parentguid = parentguid;
	}

	/** default constructor */
    public SpmBureau() {
    }

	/** minimal constructor */
    public SpmBureau(String bureauguid, String bureauname) {
        this.bureauguid = bureauguid;
        this.bureauname = bureauname;
    }
    
    /** full constructor */
    public SpmBureau(String bureauguid, String departmentGuids, Long bureausn, String bureauname, String bureaucnshortname, String bureaucnfullname, String bureauenshortname, String bureauenfullname, String bureauconsultingtel, String bureauaddress, String bureauzip, String bureautel, String bureaufax, String bureauemail, String bureaudesc, Long bureautabindex, String serviceboardguid, String flag, String principal, String windowsnum, Long senatestar, String isstreet, String orderpermission, Long ordernum, Long isexternal, Long isstatic, String leaderguids, String leadernames, String institutioncode, Date createdate, Date updatetime, String districtcode, String superiorinstituioncode, String superiorname, String ispublic, String division, String duties, String orglabel, String orgtype, String extendprop) {
        this.bureauguid = bureauguid;
        this.departmentGuids = departmentGuids;
        this.bureausn = bureausn;
        this.bureauname = bureauname;
        this.bureaucnshortname = bureaucnshortname;
        this.bureaucnfullname = bureaucnfullname;
        this.bureauenshortname = bureauenshortname;
        this.bureauenfullname = bureauenfullname;
        this.bureauconsultingtel = bureauconsultingtel;
        this.bureauaddress = bureauaddress;
        this.bureauzip = bureauzip;
        this.bureautel = bureautel;
        this.bureaufax = bureaufax;
        this.bureauemail = bureauemail;
        this.bureaudesc = bureaudesc;
        this.bureautabindex = bureautabindex;
        this.serviceboardguid = serviceboardguid;
        this.flag = flag;
        this.principal = principal;
        this.windowsnum = windowsnum;
        this.senatestar = senatestar;
        this.isstreet = isstreet;
        this.orderpermission = orderpermission;
        this.ordernum = ordernum;
        this.isexternal = isexternal;
        this.isstatic = isstatic;
        this.leaderguids = leaderguids;
        this.leadernames = leadernames;
        this.institutioncode = institutioncode;
        this.createdate = createdate;
        this.updatetime = updatetime;
        this.districtcode = districtcode;
        this.superiorinstituioncode = superiorinstituioncode;
        this.superiorname = superiorname;
        this.ispublic = ispublic;
        this.division = division;
        this.duties = duties;
        this.orglabel = orglabel;
        this.orgtype = orgtype;
        this.extendprop = extendprop;
    }

   
    // Property accessors
    @Id
   	@Column(name = "BUREAUGUID", length = 38, nullable = false)
   	@GeneratedValue(generator = "uuid")
   	@GenericGenerator(name = "uuid", strategy = "assigned") 

    public String getBureauguid() {
        return this.bureauguid;
    }
    
    public void setBureauguid(String bureauguid) {
        this.bureauguid = bureauguid;
    }
    
    @Column(name="DEPARTMENT_GUIDS", length=4000)

    public String getDepartmentGuids() {
        return this.departmentGuids;
    }
    
    public void setDepartmentGuids(String departmentGuids) {
        this.departmentGuids = departmentGuids;
    }
    
    @Column(name="BUREAUSN", unique=true, precision=4, scale=0)

    public Long getBureausn() {
        return this.bureausn;
    }
    
    public void setBureausn(Long bureausn) {
        this.bureausn = bureausn;
    }
    
    @Column(name="BUREAUNAME", unique=true, nullable=false, length=100)

    public String getBureauname() {
        return this.bureauname;
    }
    
    public void setBureauname(String bureauname) {
        this.bureauname = bureauname;
    }
    
    @Column(name="BUREAUCNSHORTNAME", length=100)

    public String getBureaucnshortname() {
        return this.bureaucnshortname;
    }
    
    public void setBureaucnshortname(String bureaucnshortname) {
        this.bureaucnshortname = bureaucnshortname;
    }
    
    @Column(name="BUREAUCNFULLNAME", length=200)

    public String getBureaucnfullname() {
        return this.bureaucnfullname;
    }
    
    public void setBureaucnfullname(String bureaucnfullname) {
        this.bureaucnfullname = bureaucnfullname;
    }
    
    @Column(name="BUREAUENSHORTNAME", length=2)

    public String getBureauenshortname() {
        return this.bureauenshortname;
    }
    
    public void setBureauenshortname(String bureauenshortname) {
        this.bureauenshortname = bureauenshortname;
    }
    
    @Column(name="BUREAUENFULLNAME", length=200)

    public String getBureauenfullname() {
        return this.bureauenfullname;
    }
    
    public void setBureauenfullname(String bureauenfullname) {
        this.bureauenfullname = bureauenfullname;
    }
    
    @Column(name="BUREAUCONSULTINGTEL", length=200)

    public String getBureauconsultingtel() {
        return this.bureauconsultingtel;
    }
    
    public void setBureauconsultingtel(String bureauconsultingtel) {
        this.bureauconsultingtel = bureauconsultingtel;
    }
    
    @Column(name="BUREAUADDRESS", length=250)

    public String getBureauaddress() {
        return this.bureauaddress;
    }
    
    public void setBureauaddress(String bureauaddress) {
        this.bureauaddress = bureauaddress;
    }
    
    @Column(name="BUREAUZIP", length=6)

    public String getBureauzip() {
        return this.bureauzip;
    }
    
    public void setBureauzip(String bureauzip) {
        this.bureauzip = bureauzip;
    }
    
    @Column(name="BUREAUTEL", length=100)

    public String getBureautel() {
        return this.bureautel;
    }
    
    public void setBureautel(String bureautel) {
        this.bureautel = bureautel;
    }
    
    @Column(name="BUREAUFAX", length=100)

    public String getBureaufax() {
        return this.bureaufax;
    }
    
    public void setBureaufax(String bureaufax) {
        this.bureaufax = bureaufax;
    }
    
    @Column(name="BUREAUEMAIL", length=100)

    public String getBureauemail() {
        return this.bureauemail;
    }
    
    public void setBureauemail(String bureauemail) {
        this.bureauemail = bureauemail;
    }
    
    @Column(name="BUREAUDESC")

    public String getBureaudesc() {
        return this.bureaudesc;
    }
    
    public void setBureaudesc(String bureaudesc) {
        this.bureaudesc = bureaudesc;
    }
    
    @Column(name="BUREAUTABINDEX", precision=3, scale=0)

    public Long getBureautabindex() {
        return this.bureautabindex;
    }
    
    public void setBureautabindex(Long bureautabindex) {
        this.bureautabindex = bureautabindex;
    }
    
    @Column(name="SERVICEBOARDGUID", length=38)

    public String getServiceboardguid() {
        return this.serviceboardguid;
    }
    
    public void setServiceboardguid(String serviceboardguid) {
        this.serviceboardguid = serviceboardguid;
    }
    
    @Column(name="FLAG", length=1)

    public String getFlag() {
        return this.flag;
    }
    
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    @Column(name="PRINCIPAL", length=10)

    public String getPrincipal() {
        return this.principal;
    }
    
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    
    @Column(name="WINDOWSNUM", length=80)

    public String getWindowsnum() {
        return this.windowsnum;
    }
    
    public void setWindowsnum(String windowsnum) {
        this.windowsnum = windowsnum;
    }
    
    @Column(name="SENATESTAR", precision=22, scale=0)

    public Long getSenatestar() {
        return this.senatestar;
    }
    
    public void setSenatestar(Long senatestar) {
        this.senatestar = senatestar;
    }
    
    @Column(name="ISSTREET", length=1)

    public String getIsstreet() {
        return this.isstreet;
    }
    
    public void setIsstreet(String isstreet) {
        this.isstreet = isstreet;
    }
    
    @Column(name="ORDERPERMISSION", length=1)

    public String getOrderpermission() {
        return this.orderpermission;
    }
    
    public void setOrderpermission(String orderpermission) {
        this.orderpermission = orderpermission;
    }
    
    @Column(name="ORDERNUM", precision=22, scale=0)

    public Long getOrdernum() {
        return this.ordernum;
    }
    
    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }
    
    @Column(name="ISEXTERNAL", precision=1, scale=0)

    public Long getIsexternal() {
        return this.isexternal;
    }
    
    public void setIsexternal(Long isexternal) {
        this.isexternal = isexternal;
    }
    
    @Column(name="ISSTATIC", precision=1, scale=0)

    public Long getIsstatic() {
        return this.isstatic;
    }
    
    public void setIsstatic(Long isstatic) {
        this.isstatic = isstatic;
    }
    
    @Column(name="LEADERGUIDS", length=2000)

    public String getLeaderguids() {
        return this.leaderguids;
    }
    
    public void setLeaderguids(String leaderguids) {
        this.leaderguids = leaderguids;
    }
    
    @Column(name="LEADERNAMES", length=2000)

    public String getLeadernames() {
        return this.leadernames;
    }
    
    public void setLeadernames(String leadernames) {
        this.leadernames = leadernames;
    }
    
    @Column(name="INSTITUTIONCODE", length=9)

    public String getInstitutioncode() {
        return this.institutioncode;
    }
    
    public void setInstitutioncode(String institutioncode) {
        this.institutioncode = institutioncode;
    }
@Temporal(TemporalType.DATE)
    @Column(name="CREATEDATE", length=7)

    public Date getCreatedate() {
        return this.createdate;
    }
    
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
@Temporal(TemporalType.DATE)
    @Column(name="UPDATETIME", length=7)

    public Date getUpdatetime() {
        return this.updatetime;
    }
    
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
    
    @Column(name="DISTRICTCODE", length=12)

    public String getDistrictcode() {
        return this.districtcode;
    }
    
    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }
    
    @Column(name="SUPERIORINSTITUIONCODE", length=9)

    public String getSuperiorinstituioncode() {
        return this.superiorinstituioncode;
    }
    
    public void setSuperiorinstituioncode(String superiorinstituioncode) {
        this.superiorinstituioncode = superiorinstituioncode;
    }
    
    @Column(name="SUPERIORNAME", length=400)

    public String getSuperiorname() {
        return this.superiorname;
    }
    
    public void setSuperiorname(String superiorname) {
        this.superiorname = superiorname;
    }
    
    @Column(name="ISPUBLIC", length=1)

    public String getIspublic() {
        return this.ispublic;
    }
    
    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
    }
    
    @Column(name="DIVISION", length=256)

    public String getDivision() {
        return this.division;
    }
    
    public void setDivision(String division) {
        this.division = division;
    }
    
    @Column(name="DUTIES", length=4000)

    public String getDuties() {
        return this.duties;
    }
    
    public void setDuties(String duties) {
        this.duties = duties;
    }
    
    @Column(name="ORGLABEL", length=50)

    public String getOrglabel() {
        return this.orglabel;
    }
    
    public void setOrglabel(String orglabel) {
        this.orglabel = orglabel;
    }
    
    @Column(name="ORGTYPE", length=50)

    public String getOrgtype() {
        return this.orgtype;
    }
    
    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }
    
    @Column(name="EXTENDPROP", length=4000)

    public String getExtendprop() {
        return this.extendprop;
    }
    
    public void setExtendprop(String extendprop) {
        this.extendprop = extendprop;
    }

	@Override
	public String toString() {
		return "SpmBureau [bureauguid=" + bureauguid + ", departmentGuids="
				+ departmentGuids + ", bureausn=" + bureausn + ", bureauname="
				+ bureauname + ", bureaucnshortname=" + bureaucnshortname
				+ ", bureaucnfullname=" + bureaucnfullname
				+ ", bureauenshortname=" + bureauenshortname
				+ ", bureauenfullname=" + bureauenfullname
				+ ", bureauconsultingtel=" + bureauconsultingtel
				+ ", bureauaddress=" + bureauaddress + ", bureauzip="
				+ bureauzip + ", bureautel=" + bureautel + ", bureaufax="
				+ bureaufax + ", bureauemail=" + bureauemail + ", bureaudesc="
				+ bureaudesc + ", bureautabindex=" + bureautabindex
				+ ", serviceboardguid=" + serviceboardguid + ", flag=" + flag
				+ ", principal=" + principal + ", windowsnum=" + windowsnum
				+ ", senatestar=" + senatestar + ", isstreet=" + isstreet
				+ ", orderpermission=" + orderpermission + ", ordernum="
				+ ordernum + ", isexternal=" + isexternal + ", isstatic="
				+ isstatic + ", leaderguids=" + leaderguids + ", leadernames="
				+ leadernames + ", institutioncode=" + institutioncode
				+ ", createdate=" + createdate + ", updatetime=" + updatetime
				+ ", districtcode=" + districtcode
				+ ", superiorinstituioncode=" + superiorinstituioncode
				+ ", superiorname=" + superiorname + ", ispublic=" + ispublic
				+ ", division=" + division + ", duties=" + duties
				+ ", orglabel=" + orglabel + ", orgtype=" + orgtype
				+ ", extendprop=" + extendprop + "]";
	}
   


}