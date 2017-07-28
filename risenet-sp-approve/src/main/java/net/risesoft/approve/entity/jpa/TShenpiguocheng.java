package net.risesoft.approve.entity.jpa;

import java.io.Serializable;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TShenpiguocheng entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_SHENPIGUOCHENG")



public class TShenpiguocheng  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private TShenpiguochengUnits id;	//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,	业务流水号
     									// 	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,	数据版本号
     									// 	"SPBZH" NUMBER(5) NOT NULL ,			审批步骤号
     
    
     private String spsxbh;					//	"SPSXBH" VARCHAR2(100 BYTE) NOT NULL ,			审批事项编号	
     private String spsxzxbh;				//	"SPSXZXBH" VARCHAR2(3 BYTE) NOT NULL ,			审批事项子项编号
     private String yxtywlsh;				//	"YXTYWLSH" VARCHAR2(100 BYTE) NOT NULL ,		原系统业务流水号
     private String sprxm;					//	"SPRXM" VARCHAR2(60 BYTE) NOT NULL ,			审批人姓名
     private String sprzwdm;				//	"SPRZWDM" VARCHAR2(5 BYTE) NOT NULL ,			审批人职务代码
     private String sprzwmc;				//	"SPRZWMC" VARCHAR2(50 BYTE) NOT NULL ,			审批人职务名称
     private String spyj;					//	"SPYJ" VARCHAR2(2000 BYTE) NOT NULL ,			审批意见
     private Date spsj;					//	"SPSJ" TIMESTAMP(6)  NOT NULL ,					审批时间
     private String bz;						//	"BZ" VARCHAR2(200 BYTE) NULL ,					备注
     private String byzda;					//	"BYZDA" VARCHAR2(100 BYTE) NULL ,				备用字段
     private String byzdb;					//	"BYZDB" VARCHAR2(100 BYTE) NULL ,				备用字段
     private String byzdc;					//	"BYZDC" VARCHAR2(500 BYTE) NULL ,				备用字段
     private String byzdd;					//	"BYZDD" TIMESTAMP(6)  NULL ,					备用字段
     private String isget;					// 	"ISGET" CHAR(1 BYTE) DEFAULT 0  NULL ,
     private String datasource;				// 	"DATASOURCE" VARCHAR2(50 BYTE) DEFAULT 'XZSP'  NULL ,数据来源:XZSP为罗湖区行政审批系统，RLZYJ为人力资源局
     private String syhjmc;					// 	"SYHJMC" VARCHAR2(50 BYTE) NULL ,				上一环节名称
     private Long spbzh;					//	"SPBZH" NUMBER(5) NOT NULL ,			审批步骤号				
     private Long hjsx;						// 	"HJSX" NUMBER(10) NULL ,						环节时限
     private String hjsxdw;					// 	"HJSXDW" CHAR(1 BYTE) DEFAULT 'G'  NULL ,		环节时限单位
     private Long sssx;						// 	"SSSX" NUMBER(10) NULL ,						所剩天数。即还有几天到期，超期即为负数
     private Long ejjcjg;					// 	"EJJCJG" NUMBER(10) NULL ,						监察状态（0，正常；1，预警；2，黄牌；5，红牌）
     private String zrdept;					// 	"ZRDEPT" VARCHAR2(50 BYTE) NULL ,				责任单位:监察对象，环节出牌责任单位，比如“罗湖区城管局
     private String jctime;					// 	"JCTIME" TIMESTAMP(6)  NULL ,					监察时间
     private String departid;				// 	"DEPARTID" VARCHAR2(60 BYTE) NULL ,				组织机构代码	
     private String isexchangebsdt;			// 	"ISEXCHANGEBSDT" CHAR(1 BYTE) DEFAULT 0  NULL , 是否同步至办事大厅中间库：0未交换、1交换成功、2交换失败
     private String serialnum;				// 	"SERIALNUM" VARCHAR2(50 BYTE) NULL 				29位统一申办流水号[根据市标准生成]	


    // Constructors

    /** default constructor */
    public TShenpiguocheng() {
    }


   
    // Property accessors
    @EmbeddedId
    
    public TShenpiguochengUnits getId() {
        return this.id;
    }
    
    public void setId(TShenpiguochengUnits id) {
        this.id = id;
    }
    
    @Column(name="SPBZH", nullable=false, precision=5, scale=0)

    public Long getSpbzh() {
        return this.spbzh;
    }
    
    public void setSpbzh(Long spbzh) {
        this.spbzh = spbzh;
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
    
    @Column(name="SPRXM", nullable=false, length=60)

    public String getSprxm() {
        return this.sprxm;
    }
    
    public void setSprxm(String sprxm) {
        this.sprxm = sprxm;
    }
    
    @Column(name="SPRZWDM", nullable=false, length=5)

    public String getSprzwdm() {
        return this.sprzwdm;
    }
    
    public void setSprzwdm(String sprzwdm) {
        this.sprzwdm = sprzwdm;
    }
    
    @Column(name="SPRZWMC", nullable=false, length=50)

    public String getSprzwmc() {
        return this.sprzwmc;
    }
    
    public void setSprzwmc(String sprzwmc) {
        this.sprzwmc = sprzwmc;
    }
    
    @Column(name="SPYJ", nullable=false, length=2000)

    public String getSpyj() {
        return this.spyj;
    }
    
    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }
    
    @Column(name="SPSJ", nullable=false)

    public Date getSpsj() {
        return this.spsj;
    }
    
    public void setSpsj(Date spsj) {
        this.spsj = spsj;
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
    
    @Column(name="SYHJMC", length=50)

    public String getSyhjmc() {
        return this.syhjmc;
    }
    
    public void setSyhjmc(String syhjmc) {
        this.syhjmc = syhjmc;
    }
    
    @Column(name="HJSX", precision=10, scale=0)

    public Long getHjsx() {
        return this.hjsx;
    }
    
    public void setHjsx(Long hjsx) {
        this.hjsx = hjsx;
    }
    
    @Column(name="HJSXDW", length=1)

    public String getHjsxdw() {
        return this.hjsxdw;
    }
    
    public void setHjsxdw(String hjsxdw) {
        this.hjsxdw = hjsxdw;
    }
    
    @Column(name="SSSX", precision=10, scale=0)

    public Long getSssx() {
        return this.sssx;
    }
    
    public void setSssx(Long sssx) {
        this.sssx = sssx;
    }
    
    @Column(name="EJJCJG", precision=10, scale=0)

    public Long getEjjcjg() {
        return this.ejjcjg;
    }
    
    public void setEjjcjg(Long ejjcjg) {
        this.ejjcjg = ejjcjg;
    }
    
    @Column(name="ZRDEPT", length=50)

    public String getZrdept() {
        return this.zrdept;
    }
    
    public void setZrdept(String zrdept) {
        this.zrdept = zrdept;
    }
    
    @Column(name="JCTIME")

    public String getJctime() {
        return this.jctime;
    }
    
    public void setJctime(String jctime) {
        this.jctime = jctime;
    }
    
    @Column(name="DEPARTID", length=60)

    public String getDepartid() {
        return this.departid;
    }
    
    public void setDepartid(String departid) {
        this.departid = departid;
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