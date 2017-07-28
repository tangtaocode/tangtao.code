package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * AbstractTBdexDocinfo entity provides the base persistence definition of the
 * TBdexDocinfo entity.
 * 证照主表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_BDEX_DOCINFO")
public  class TBdexDocinfo  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields


     private String guid;				// 	"GUID" VARCHAR2(38 BYTE) NOT NULL ,
     private String id;					//	"ID" VARCHAR2(128 BYTE) NULL ,					检索流水号
     private String docinfoid;			//	"DOCINFOID" VARCHAR2(100 BYTE) NULL ,			证照信息唯一id,在各单位中的具体证照，批复的具体编号（唯一），关联下表中的docinfoid
     private String docid;				//	"DOCID" VARCHAR2(20 BYTE) NULL ,				证照类别id,查询的证照类别
     private String businessid;			//	"BUSINESSID" VARCHAR2(100 BYTE) NULL ,			受理业务唯一流水号,产生此证照的业务的唯一流水号
     private String admindivision;		//	"ADMINDIVISION" VARCHAR2(6 BYTE) NULL ,			发证对象所在的行政区域,默认值：440303
     private String organcode;			//	"ORGANCODE" VARCHAR2(100 BYTE) NULL ,			证照主体组织机构代码,假如发证对象是企业，必须填写企业的组织机构代码，只需9位代码
     private String docstatus;			//	"DOCSTATUS" VARCHAR2(1 BYTE) NULL ,				证照状态，V 默认有效的，D 已注销的，E 已过期的，C 已作废的
     private String docname;			//	"DOCNAME" VARCHAR2(100 BYTE) NULL ,				证照名称,证照名称
     private String docno;				//	"DOCNO" VARCHAR2(100 BYTE) NULL ,				证照编号,证照编号
     private String validityperiod;		//	"VALIDITYPERIOD" VARCHAR2(100 BYTE) NULL ,		证照有效期,证照有效期
     private String producedate;		//	"PRODUCEDATE" VARCHAR2(100 BYTE) NULL ,			发（出）证日期,发证日期
     private String produceorgan;		//	"PRODUCEORGAN" VARCHAR2(100 BYTE) NULL ,		发证单位,发证单位
     private String docowner;			//	"DOCOWNER" VARCHAR2(100 BYTE) NULL ,			证照主体,证照主体对象，即证照是发给那家企业或个人的
     private String content;			//	"CONTENT" VARCHAR2(200 BYTE) NULL ,				证照内容概要,摘录证照内容中关键的信息，直接文本展示，在需要换行的地方以<br>标识换行'
     private Long othercontents;		//	"OTHERCONTENTS" NUMBER NULL ,					主要内容的记录数,0没有其他内容假如存在详细的证照内容（XML表示），填写具体的记录数量
     private String isexistattachment;	// 	"ISEXISTATTACHMENT" VARCHAR2(1 BYTE) NULL ,		是否存在附件,N没有附件、Y 存在附件
     private String remark;				// 	"REMARK" VARCHAR2(200 BYTE) NULL ,				备注	
     private String by1;				// 	"BY1" VARCHAR2(100 BYTE) NULL ,					备用字段1,填写发证单位9位的“组织机构代码
     private String by2;				// 	"BY2" VARCHAR2(100 BYTE) NULL ,					备用字段2
     private String by3;				// 	"BY3" VARCHAR2(100 BYTE) NULL ,					备用字段3
     private String querystatus;		// 	"QUERYSTATUS" VARCHAR2(1 BYTE) NULL ,			交换处理状态,交换标识，默认为0
     private String state;				// 	"STATE" VARCHAR2(5 BYTE) NULL ,					证照状态。0待导出，1已导出，2从证照系统读取过来的，当然不需要导出
     private String doctypeguid;		// 	"DOCTYPEGUID" VARCHAR2(38 BYTE) NULL ,			证照类别主键，关联t_bdex_doctype表
     private String jkstate;			// 	"JKSTATE" VARCHAR2(5 BYTE) NULL ,				如果主表发送成功，而细表失败。主细表数据都不删除。置主表状态字段JKSTATE为1
     private String instanceguid;		// 	"INSTANCEGUID" CHAR(38 BYTE) NULL ,				工作流主键	
     private String ztxm;				// 	"ZTXM" VARCHAR2(500 BYTE) NULL ,				证照主体姓名。法定代表人/自然人/主要负责人
     private String zcaddr;				// 	"ZCADDR" VARCHAR2(500 BYTE) NULL ,				注册地址。单位/企业/机构注册地址
     private String jyaddr;				// 	"JYADDR" VARCHAR2(500 BYTE) NULL ,				经营(生产)地址。单位/企业/机构实际经营地址
     private String zzlx;				// 	"ZZLX" VARCHAR2(100 BYTE) NULL ,				证照类型。证照类型代码：1许可证2营业证3营业执照4税务登记证5资格证6批准文件
     private Long issenttotj;			// 	"ISSENTTOTJ" NUMBER(1) DEFAULT 0  NULL 			是否发送至太极：默认是0：否，1发送成功，2发送失败


    // Constructors

    /** default constructor */
    public TBdexDocinfo() {
    }

	/** minimal constructor */
    public TBdexDocinfo(String guid) {
        this.guid = guid;
    }
    

   
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
    
    @Column(name="ID", length=128)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="DOCINFOID", length=100)

    public String getDocinfoid() {
        return this.docinfoid;
    }
    
    public void setDocinfoid(String docinfoid) {
        this.docinfoid = docinfoid;
    }
    
    @Column(name="DOCID", length=20)

    public String getDocid() {
        return this.docid;
    }
    
    public void setDocid(String docid) {
        this.docid = docid;
    }
    
    @Column(name="BUSINESSID", length=100)

    public String getBusinessid() {
        return this.businessid;
    }
    
    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }
    
    @Column(name="ADMINDIVISION", length=6)

    public String getAdmindivision() {
        return this.admindivision;
    }
    
    public void setAdmindivision(String admindivision) {
        this.admindivision = admindivision;
    }
    
    @Column(name="ORGANCODE", length=100)

    public String getOrgancode() {
        return this.organcode;
    }
    
    public void setOrgancode(String organcode) {
        this.organcode = organcode;
    }
    
    @Column(name="DOCSTATUS", length=1)

    public String getDocstatus() {
        return this.docstatus;
    }
    
    public void setDocstatus(String docstatus) {
        this.docstatus = docstatus;
    }
    
    @Column(name="DOCNAME", length=100)

    public String getDocname() {
        return this.docname;
    }
    
    public void setDocname(String docname) {
        this.docname = docname;
    }
    
    @Column(name="DOCNO", length=100)

    public String getDocno() {
        return this.docno;
    }
    
    public void setDocno(String docno) {
        this.docno = docno;
    }
    
    @Column(name="VALIDITYPERIOD", length=100)

    public String getValidityperiod() {
        return this.validityperiod;
    }
    
    public void setValidityperiod(String validityperiod) {
        this.validityperiod = validityperiod;
    }
    
    @Column(name="PRODUCEDATE", length=100)

    public String getProducedate() {
        return this.producedate;
    }
    
    public void setProducedate(String producedate) {
        this.producedate = producedate;
    }
    
    @Column(name="PRODUCEORGAN", length=100)

    public String getProduceorgan() {
        return this.produceorgan;
    }
    
    public void setProduceorgan(String produceorgan) {
        this.produceorgan = produceorgan;
    }
    
    @Column(name="DOCOWNER", length=100)

    public String getDocowner() {
        return this.docowner;
    }
    
    public void setDocowner(String docowner) {
        this.docowner = docowner;
    }
    
    @Column(name="CONTENT", length=200)

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="OTHERCONTENTS", precision=22, scale=0)

    public Long getOthercontents() {
        return this.othercontents;
    }
    
    public void setOthercontents(Long othercontents) {
        this.othercontents = othercontents;
    }
    
    @Column(name="ISEXISTATTACHMENT", length=1)

    public String getIsexistattachment() {
        return this.isexistattachment;
    }
    
    public void setIsexistattachment(String isexistattachment) {
        this.isexistattachment = isexistattachment;
    }
    
    @Column(name="REMARK", length=200)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="BY1", length=100)

    public String getBy1() {
        return this.by1;
    }
    
    public void setBy1(String by1) {
        this.by1 = by1;
    }
    
    @Column(name="BY2", length=100)

    public String getBy2() {
        return this.by2;
    }
    
    public void setBy2(String by2) {
        this.by2 = by2;
    }
    
    @Column(name="BY3", length=100)

    public String getBy3() {
        return this.by3;
    }
    
    public void setBy3(String by3) {
        this.by3 = by3;
    }
    
    @Column(name="QUERYSTATUS", length=1)

    public String getQuerystatus() {
        return this.querystatus;
    }
    
    public void setQuerystatus(String querystatus) {
        this.querystatus = querystatus;
    }
    
    @Column(name="STATE", length=5)

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="DOCTYPEGUID", length=38)

    public String getDoctypeguid() {
        return this.doctypeguid;
    }
    
    public void setDoctypeguid(String doctypeguid) {
        this.doctypeguid = doctypeguid;
    }
    
    @Column(name="JKSTATE", length=5)

    public String getJkstate() {
        return this.jkstate;
    }
    
    public void setJkstate(String jkstate) {
        this.jkstate = jkstate;
    }
    
    @Column(name="INSTANCEGUID", length=38)

    public String getInstanceguid() {
        return this.instanceguid;
    }
    
    public void setInstanceguid(String instanceguid) {
        this.instanceguid = instanceguid;
    }
    
    @Column(name="ZTXM", length=500)

    public String getZtxm() {
        return this.ztxm;
    }
    
    public void setZtxm(String ztxm) {
        this.ztxm = ztxm;
    }
    
    @Column(name="ZCADDR", length=500)

    public String getZcaddr() {
        return this.zcaddr;
    }
    
    public void setZcaddr(String zcaddr) {
        this.zcaddr = zcaddr;
    }
    
    @Column(name="JYADDR", length=500)

    public String getJyaddr() {
        return this.jyaddr;
    }
    
    public void setJyaddr(String jyaddr) {
        this.jyaddr = jyaddr;
    }
    
    @Column(name="ZZLX", length=100)

    public String getZzlx() {
        return this.zzlx;
    }
    
    public void setZzlx(String zzlx) {
        this.zzlx = zzlx;
    }
    
    @Column(name="ISSENTTOTJ", precision=1, scale=0)

    public Long getIssenttotj() {
        return this.issenttotj;
    }
    
    public void setIssenttotj(Long issenttotj) {
        this.issenttotj = issenttotj;
    }

	@Override
	public String toString() {
		return "TBdexDocinfo [guid=" + guid + ", id=" + id + ", docinfoid="
				+ docinfoid + ", docid=" + docid + ", businessid=" + businessid
				+ ", admindivision=" + admindivision + ", organcode="
				+ organcode + ", docstatus=" + docstatus + ", docname="
				+ docname + ", docno=" + docno + ", validityperiod="
				+ validityperiod + ", producedate=" + producedate
				+ ", produceorgan=" + produceorgan + ", docowner=" + docowner
				+ ", content=" + content + ", othercontents=" + othercontents
				+ ", isexistattachment=" + isexistattachment + ", remark="
				+ remark + ", by1=" + by1 + ", by2=" + by2 + ", by3=" + by3
				+ ", querystatus=" + querystatus + ", state=" + state
				+ ", doctypeguid=" + doctypeguid + ", jkstate=" + jkstate
				+ ", instanceguid=" + instanceguid + ", ztxm=" + ztxm
				+ ", zcaddr=" + zcaddr + ", jyaddr=" + jyaddr + ", zzlx="
				+ zzlx + ", issenttotj=" + issenttotj + "]";
	}
   
    


}