package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * TBujiaogaozhi entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_BUJIAOGAOZHI"

)

public class TBujiaogaozhi  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private TBujiaogaozhiUnits id;			//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,		业务流水号
     										// 	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,		数据版本号
     private String spsxbh;				//	"SPSXBH" VARCHAR2(100 BYTE) NOT NULL ,		审批事项编号
     private String spsxzxbh;			//	"SPSXZXBH" VARCHAR2(3 BYTE) NOT NULL ,		审批事项子项编号	
     private String yxtywlsh;			//	"YXTYWLSH" VARCHAR2(100 BYTE) NOT NULL ,	原系统业务流水号
     private String bjgzfcr;			//	"BJGZFCR" VARCHAR2(60 BYTE) NOT NULL ,		补交告知发出人
     private String bjgzyy;				//	"BJGZYY" VARCHAR2(2000 BYTE) NOT NULL ,		补交告知原因	
     private String bjclgzqd;			//	"BJCLGZQD" VARCHAR2(2000 BYTE) NOT NULL ,	补交材料告知清单
     private Date bjgzsj;				//	"BJGZSJ" TIMESTAMP(6)  NOT NULL ,			补交告知时间
     private String bz;					//	"BZ" VARCHAR2(200 BYTE) NULL ,				备注
     private String byzda;				//	"BYZDA" VARCHAR2(100 BYTE) NULL ,			备用字段
     private String byzdb;				//	"BYZDB" VARCHAR2(100 BYTE) NULL ,			备用字段
     private String byzdc;				//	"BYZDC" VARCHAR2(500 BYTE) NULL ,			备用字段
     private String byzdd;				//	"BYZDD" TIMESTAMP(6)  NULL ,				备用字段
     private String isget;				//	"ISGET" CHAR(1 BYTE) DEFAULT 0  NULL ,
     private String datasource;			//	"DATASOURCE" VARCHAR2(50 BYTE) DEFAULT 'XZSP'  NULL ,数据来源:XZSP为罗湖区行政审批系统，RLZYJ为人力资源局。
     private String departid;			//	"DEPARTID" VARCHAR2(60 BYTE) NULL ,			组织机构代码
     private Long step;					//	"STEP" NUMBER(3) NULL ,						审批环节步骤号
     private String isexchangebsdt;		//	"ISEXCHANGEBSDT" CHAR(1 BYTE) DEFAULT 0  NULL ,是否同步至办事大厅中间库：0未交换、1交换成功、2交换失败
     private String serialnum;			//	"SERIALNUM" VARCHAR2(50 BYTE) NULL 			29位统一申办流水号[根据市标准生成]
     private String ywlsh;
     private String bjgzfcrguid;
     // Constructors

     
    /** default constructor */
    public TBujiaogaozhi() {
    }

    @Column(name="BJGZFCRGUID", nullable=false, length=100)
    public String getBjgzfcrguid() {
		return bjgzfcrguid;
	}


	public void setBjgzfcrguid(String bjgzfcrguid) {
		this.bjgzfcrguid = bjgzfcrguid;
	}


	@Transient
	public String getYwlsh() {
		return ywlsh;
	}

	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}

	/** minimal constructor */
    public TBujiaogaozhi(TBujiaogaozhiUnits id, String spsxbh, String spsxzxbh, String yxtywlsh, String bjgzfcr, String bjgzyy, String bjclgzqd, Date bjgzsj) {
        this.id = id;
        this.spsxbh = spsxbh;
        this.spsxzxbh = spsxzxbh;
        this.yxtywlsh = yxtywlsh;
        this.bjgzfcr = bjgzfcr;
        this.bjgzyy = bjgzyy;
        this.bjclgzqd = bjclgzqd;
        this.bjgzsj = bjgzsj;
    }
    
    /** full constructor */
    public TBujiaogaozhi(TBujiaogaozhiUnits id, String spsxbh, String spsxzxbh, String yxtywlsh, String bjgzfcr, String bjgzyy, String bjclgzqd, Date bjgzsj, String bz, String byzda, String byzdb, String byzdc, String byzdd, String isget, String datasource, String departid, Long step, String isexchangebsdt, String serialnum) {
        this.id = id;
        this.spsxbh = spsxbh;
        this.spsxzxbh = spsxzxbh;
        this.yxtywlsh = yxtywlsh;
        this.bjgzfcr = bjgzfcr;
        this.bjgzyy = bjgzyy;
        this.bjclgzqd = bjclgzqd;
        this.bjgzsj = bjgzsj;
        this.bz = bz;
        this.byzda = byzda;
        this.byzdb = byzdb;
        this.byzdc = byzdc;
        this.byzdd = byzdd;
        this.isget = isget;
        this.datasource = datasource;
        this.departid = departid;
        this.step = step;
        this.isexchangebsdt = isexchangebsdt;
        this.serialnum = serialnum;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ywlsh", column=@Column(name="YWLSH", nullable=false, length=100) ), 
        @AttributeOverride(name="sjbbh", column=@Column(name="SJBBH", nullable=false, precision=4, scale=0) ) } )

    public TBujiaogaozhiUnits getId() {
        return this.id;
    }
    
    public void setId(TBujiaogaozhiUnits id) {
        this.id = id;
    }
    
    @Column(name="SPSXBH", nullable=false, length=100)

    public String getSpsxbh() {
        return this.spsxbh;
    }
    
    public void setSpsxbh(String spsxbh) {
        this.spsxbh = spsxbh;
    }
    
    @Column(name="SPSXZXBH", nullable=false, length=3)

    public String getSpsxzxbh() {
        return this.spsxzxbh;
    }
    
    public void setSpsxzxbh(String spsxzxbh) {
        this.spsxzxbh = spsxzxbh;
    }
    
    @Column(name="YXTYWLSH", nullable=false, length=100)

    public String getYxtywlsh() {
        return this.yxtywlsh;
    }
    
    public void setYxtywlsh(String yxtywlsh) {
        this.yxtywlsh = yxtywlsh;
    }
    
    @Column(name="BJGZFCR", nullable=false, length=60)

    public String getBjgzfcr() {
        return this.bjgzfcr;
    }
    
    public void setBjgzfcr(String bjgzfcr) {
        this.bjgzfcr = bjgzfcr;
    }
    
    @Column(name="BJGZYY", nullable=false, length=2000)

    public String getBjgzyy() {
        return this.bjgzyy;
    }
    
    public void setBjgzyy(String bjgzyy) {
        this.bjgzyy = bjgzyy;
    }
    
    @Column(name="BJCLGZQD", nullable=false, length=2000)

    public String getBjclgzqd() {
        return this.bjclgzqd;
    }
    
    public void setBjclgzqd(String bjclgzqd) {
        this.bjclgzqd = bjclgzqd;
    }
    
    @Column(name="BJGZSJ", nullable=false)
    public Date getBjgzsj() {
        return this.bjgzsj;
    }
    
    public void setBjgzsj(Date bjgzsj) {
        this.bjgzsj = bjgzsj;
    }
    
    @Column(name="BZ", length=200)

    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }
    
    @Column(name="BYZDA", length=100)

    public String getByzda() {
        return this.byzda;
    }
    
    public void setByzda(String byzda) {
        this.byzda = byzda;
    }
    
    @Column(name="BYZDB", length=100)

    public String getByzdb() {
        return this.byzdb;
    }
    
    public void setByzdb(String byzdb) {
        this.byzdb = byzdb;
    }
    
    @Column(name="BYZDC", length=500)

    public String getByzdc() {
        return this.byzdc;
    }
    
    public void setByzdc(String byzdc) {
        this.byzdc = byzdc;
    }
    
    @Column(name="BYZDD")

    public String getByzdd() {
        return this.byzdd;
    }
    
    public void setByzdd(String byzdd) {
        this.byzdd = byzdd;
    }
    
    @Column(name="ISGET", length=1)

    public String getIsget() {
        return this.isget;
    }
    
    public void setIsget(String isget) {
        this.isget = isget;
    }
    
    @Column(name="DATASOURCE", length=50)

    public String getDatasource() {
        return this.datasource;
    }
    
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
    
    @Column(name="DEPARTID", length=60)

    public String getDepartid() {
        return this.departid;
    }
    
    public void setDepartid(String departid) {
        this.departid = departid;
    }
    
    @Column(name="STEP", precision=3, scale=0)

    public Long getStep() {
        return this.step;
    }
    
    public void setStep(Long step) {
        this.step = step;
    }
    
    @Column(name="ISEXCHANGEBSDT", length=1)

    public String getIsexchangebsdt() {
        return this.isexchangebsdt;
    }
    
    public void setIsexchangebsdt(String isexchangebsdt) {
        this.isexchangebsdt = isexchangebsdt;
    }
    
    @Column(name="SERIALNUM", length=50)

    public String getSerialnum() {
        return this.serialnum;
    }
    
    public void setSerialnum(String serialnum) {
        this.serialnum = serialnum;
    }
   








}