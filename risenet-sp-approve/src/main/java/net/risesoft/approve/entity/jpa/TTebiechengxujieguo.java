package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TTebiechengxujieguo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_TEBIECHENGXUJIEGUO"

)

public class TTebiechengxujieguo  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private TTebiechengxujieguoUnits id;	//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,业务流水号
     										// 	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,	数据版本号
     										// 	"XH" NUMBER(5) NOT NULL ,序号
     private String spsxbh;				//	"SPSXBH" VARCHAR2(100 BYTE) NULL ,					审批事项编号
     private String spsxzxbh;			//	"SPSXZXBH" VARCHAR2(3 BYTE) NULL ,					审批事项子项编号
     private String yxtywlsh;			//	"YXTYWLSH" VARCHAR2(100 BYTE) NULL ,				原系统业务流水号
     private String tbcxjg;				//	"TBCXJG" VARCHAR2(2000 BYTE) NULL 					特别程序结果
     private Date jgcsrq;				//	"JGCSRQ" TIMESTAMP(6)  NULL ,						结果产生日期
     private Date tbcxjsrq;			//	"TBCXJSRQ" TIMESTAMP(6)  NULL ,						特别程序结束日期
     private String tbcxsfje;			//	"TBCXSFJE" VARCHAR2(30 BYTE) NULL ,					特别程序收费金额
     private String bz;					//	"BZ" VARCHAR2(200 BYTE) NULL ,						备注
     private String byzda;				//	"BYZDA" VARCHAR2(100 BYTE) NULL ,					备用字段
     private String byzdb;				//	"BYZDB" VARCHAR2(100 BYTE) NULL ,					备用字段
     private String byzdc;				//	"BYZDC" VARCHAR2(500 BYTE) NULL ,					备用字段
     private String byzdd;				//	"BYZDD" TIMESTAMP(6)  NULL ,						备用字段
     private String isget;				//	"ISGET" CHAR(1 BYTE) DEFAULT 0  NULL ,
     private String datasource;			//	"DATASOURCE" VARCHAR2(50 BYTE) DEFAULT 'XZSP' NULL,	数据来源:XZSP为罗湖区行政审批系统，RLZYJ为人力资源局
     private String departid;			//	"DEPARTID" VARCHAR2(60 BYTE) NULL ,					组织机构代码
     private String isexchangebsdt;		//	"ISEXCHANGEBSDT" CHAR(1 BYTE) DEFAULT 0  NULL ,		是否同步至办事大厅中间库：0未交换、1交换成功、2交换失败
     private String serialnum;			//	"SERIALNUM" VARCHAR2(50 BYTE) NULL 					29位统一申办流水号[根据市标准生成]


    // Constructors

    /** default constructor */
    public TTebiechengxujieguo() {
    }

	/** minimal constructor */
    public TTebiechengxujieguo(TTebiechengxujieguoUnits id) {
        this.id = id;
    }
    

   
    // Property accessors
    @EmbeddedId
    
    public TTebiechengxujieguoUnits getId() {
        return this.id;
    }
    
    public void setId(TTebiechengxujieguoUnits id) {
        this.id = id;
    }
    
    @Column(name="SPSXBH", length=100)

    public String getSpsxbh() {
        return this.spsxbh;
    }
    
    public void setSpsxbh(String spsxbh) {
        this.spsxbh = spsxbh;
    }
    
    @Column(name="SPSXZXBH", length=3)

    public String getSpsxzxbh() {
        return this.spsxzxbh;
    }
    
    public void setSpsxzxbh(String spsxzxbh) {
        this.spsxzxbh = spsxzxbh;
    }
    
    @Column(name="YXTYWLSH", length=100)

    public String getYxtywlsh() {
        return this.yxtywlsh;
    }
    
    public void setYxtywlsh(String yxtywlsh) {
        this.yxtywlsh = yxtywlsh;
    }
    
    @Column(name="TBCXJG", length=2000)

    public String getTbcxjg() {
        return this.tbcxjg;
    }
    
    public void setTbcxjg(String tbcxjg) {
        this.tbcxjg = tbcxjg;
    }
    
    @Column(name="JGCSRQ")

    public Date getJgcsrq() {
        return this.jgcsrq;
    }
    
    public void setJgcsrq(Date jgcsrq) {
        this.jgcsrq = jgcsrq;
    }
    
    @Column(name="TBCXJSRQ")

    public Date getTbcxjsrq() {
        return this.tbcxjsrq;
    }
    
    public void setTbcxjsrq(Date tbcxjsrq) {
        this.tbcxjsrq = tbcxjsrq;
    }
    
    @Column(name="TBCXSFJE", length=30)

    public String getTbcxsfje() {
        return this.tbcxsfje;
    }
    
    public void setTbcxsfje(String tbcxsfje) {
        this.tbcxsfje = tbcxsfje;
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