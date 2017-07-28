package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TBujiaoshouli entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_BUJIAOSHOULI"

)

public class TBujiaoshouli  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private TBujiaoshouliUnits id;		//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,业务流水号
     									// 	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,			数据版本号
     private String spsxbh;				//	"SPSXBH" VARCHAR2(100 BYTE) NOT NULL ,			审批事项编号
     private String spsxzxbh;			//	"SPSXZXBH" VARCHAR2(3 BYTE) NOT NULL ,			审批事项子项编号	
     private String yxtywlsh;			//	"YXTYWLSH" VARCHAR2(100 BYTE) NOT NULL ,		原系统业务流水号
     private String sldwcbrxm;			//	"SLDWCBRXM" VARCHAR2(60 BYTE) NOT NULL ,		补交受理单位办理人姓名
     private String bjsljtdd;			//	"BJSLJTDD" VARCHAR2(400 BYTE) NOT NULL ,		补交受理具体地点
     private Date bjsj;				//	"BJSJ" TIMESTAMP(6)  NOT NULL ,					补交时间
     private String bjclqd;				//	"BJCLQD" VARCHAR2(2000 BYTE) NOT NULL ,			补交材料清单
     private String bz;					//	"BZ" VARCHAR2(200 BYTE) NULL ,					备注
     private String byzda;				//	"BYZDA" VARCHAR2(100 BYTE) NULL ,				备用字段
     private String byzdb;				//	"BYZDB" VARCHAR2(100 BYTE) NULL ,				备用字段
     private String byzdc;				//	"BYZDC" VARCHAR2(500 BYTE) NULL ,				备用字段
     private String byzdd;				//	"BYZDD" TIMESTAMP(6)  NULL ,					备用字段
     private String isget;				//	"ISGET" CHAR(1 BYTE) DEFAULT 0  NULL ,			
     private String datasource;			//	"DATASOURCE" VARCHAR2(50 BYTE) DEFAULT 'XZSP'  NULL ,数据来源:XZSP为罗湖区行政审批系统，RLZYJ为人力资源局。
     private String departid;			//	"DEPARTID" VARCHAR2(60 BYTE) NULL ,				组织机构代码
     private String isexchangebsdt;		//	"ISEXCHANGEBSDT" CHAR(1 BYTE) DEFAULT 0  NULL ,是否同步至办事大厅中间库：0未交换、1交换成功、2交换失败
     private String serialnum;			//	"SERIALNUM" VARCHAR2(50 BYTE) NULL, 				29位统一申办流水号[根据市标准生成]


    // Constructors

     public TBujiaoshouli(){};
    
    /** full constructor */
    public TBujiaoshouli(TBujiaoshouliUnits id, String spsxbh, String spsxzxbh, String yxtywlsh, String sldwcbrxm, String bjsljtdd, Date bjsj, String bjclqd, String bz, String byzda, String byzdb, String byzdc, String byzdd, String isget, String datasource, String departid, String isexchangebsdt, String serialnum) {
        this.id = id;
        this.spsxbh = spsxbh;
        this.spsxzxbh = spsxzxbh;
        this.yxtywlsh = yxtywlsh;
        this.sldwcbrxm = sldwcbrxm;
        this.bjsljtdd = bjsljtdd;
        this.bjsj = bjsj;
        this.bjclqd = bjclqd;
        this.bz = bz;
        this.byzda = byzda;
        this.byzdb = byzdb;
        this.byzdc = byzdc;
        this.byzdd = byzdd;
        this.isget = isget;
        this.datasource = datasource;
        this.departid = departid;
        this.isexchangebsdt = isexchangebsdt;
        this.serialnum = serialnum;
    }

   
    // Property accessors
    @EmbeddedId
    
  
    public TBujiaoshouliUnits getId() {
        return this.id;
    }
    
    public void setId(TBujiaoshouliUnits id) {
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
    
    @Column(name="SLDWCBRXM", nullable=false, length=60)

    public String getSldwcbrxm() {
        return this.sldwcbrxm;
    }
    
    public void setSldwcbrxm(String sldwcbrxm) {
        this.sldwcbrxm = sldwcbrxm;
    }
    
    @Column(name="BJSLJTDD", nullable=false, length=400)

    public String getBjsljtdd() {
        return this.bjsljtdd;
    }
    
    public void setBjsljtdd(String bjsljtdd) {
        this.bjsljtdd = bjsljtdd;
    }
    
    @Column(name="BJSJ", nullable=false)

    public Date getBjsj() {
        return this.bjsj;
    }
    
    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }
    
    @Column(name="BJCLQD", nullable=false, length=2000)

    public String getBjclqd() {
        return this.bjclqd;
    }
    
    public void setBjclqd(String bjclqd) {
        this.bjclqd = bjclqd;
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