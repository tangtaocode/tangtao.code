package net.risesoft.approve.entity.jpa;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TShouli entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_SHOULI")
    


public class TShouli  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private TShouliUnits id;	//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,		主键，调用函数createYWLSH()生成
     							// 	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,		数据版本号
     
    
     private String spsxbh;					//	"SPSXBH" VARCHAR2(100 BYTE) NOT NULL ,		审批事项编号
     private String spsxzxbh;				//	"SPSXZXBH" VARCHAR2(3 BYTE) NOT NULL ,		审批事项子项编号
     private String yxtywlsh;				//	"YXTYWLSH" VARCHAR2(100 BYTE) NOT NULL ,	原系统业务流水号	
     private String spsxmc;					//	"SPSXMC" VARCHAR2(200 BYTE) NOT NULL ,		审批事项名称
     private Long gdblsx;					//	"GDBLSX" NUMBER(8) NOT NULL ,				规定办理时限
     private String gdblsxdw;				//	"GDBLSXDW" VARCHAR2(1 BYTE) NOT NULL ,		规定办理时限的单位
     private String gdsf;					//	"GDSF" VARCHAR2(1000 BYTE) NOT NULL ,		规定收费
     private String xmmc;					//	"XMMC" VARCHAR2(400 BYTE) NOT NULL ,		项目名称
     private String flgdgxd;				//	"FLGDGXD" VARCHAR2(20 BYTE) NOT NULL ,		法律规定管辖地	
     private String fsywgxd;				//	"FSYWGXD" VARCHAR2(20 BYTE) NOT NULL ,		发生业务管辖地
     private String sqdwhsqrxm;				//	"SQDWHSQRXM" VARCHAR2(200 BYTE) NOT NULL ,	申请单位或申请人名称
     private String sqdwjbrxm;				//	"SQDWJBRXM" VARCHAR2(60 BYTE) NOT NULL ,	申请单位经办人姓名
     private String sqdwlxdh;				//	"SQDWLXDH" VARCHAR2(40 BYTE) NULL ,			申请单位联系电话
     private String sqdwjbrsj;				//	"SQDWJBRSJ" VARCHAR2(30 BYTE) NULL ,		申请单位经办人手机
     private String sqdwjbryj;				//	"SQDWJBRYJ" VARCHAR2(80 BYTE) NULL ,		申请单位经办人邮件
     private String sljgzzjgdm;				// 	"SLJGZZJGDM" VARCHAR2(9 BYTE) NOT NULL ,	受理机关组织机构代码
     private String sljgmc;					// 	"SLJGMC" VARCHAR2(100 BYTE) NOT NULL ,		受理机关名称
     private String sldwcbrxm;				// 	"SLDWCBRXM" VARCHAR2(60 BYTE) NOT NULL ,	受理单位承办人姓名
     private String sldwcbrgh;				// 	"SLDWCBRGH" VARCHAR2(50 BYTE) NULL ,		受理单位承办人工号
     private String sljtdd;					// 	"SLJTDD" VARCHAR2(300 BYTE) NULL ,			受理具体地点
     private Date slsj;					// 	"SLSJ" TIMESTAMP(6)  NOT NULL ,				受理时间
     private String hzbh;					// 	"HZBH" VARCHAR2(100 BYTE) NOT NULL ,		回执编号
     private String slzlqd;					// 	"SLZLQD" VARCHAR2(2000 BYTE) NOT NULL ,		受理资料清单
     private String slyf;					// 	"SLYF" VARCHAR2(1 BYTE) DEFAULT 0  NOT NULL ,受理与否
     private String bslyy;					// 	"BSLYY" VARCHAR2(2000 BYTE) NULL ,			不受理原因
     private String tjfs;					// 	"TJFS" VARCHAR2(1 BYTE) DEFAULT 0  NOT NULL ,提交方式
     private String bz;						// 	"BZ" VARCHAR2(200 BYTE) NULL ,				备注
     private String byzda;					// 	"BYZDA" VARCHAR2(100 BYTE) NULL ,			备用字段
     private String byzdb;					// 	"BYZDB" VARCHAR2(100 BYTE) NULL ,			备用字段
     private String byzdc;					// 	"BYZDC" VARCHAR2(500 BYTE) NULL ,			备用字段
     private String byzdd;					// 	"BYZDD" TIMESTAMP(6)  NULL ,				备用字段
     private String isget;					// 	"ISGET" CHAR(1 BYTE) DEFAULT 0  NULL , 
     private String datasource;				// 	"DATASOURCE" VARCHAR2(50 BYTE) DEFAULT 'XZSP'  NULL ,数据来源:XZSP为罗湖区行政审批系统，RLZYJ为人力资源局
     private String syhjmc;					// 	"SYHJMC" VARCHAR2(50 BYTE) NULL ,			上一环节名称	
     private String dqhjmc;					// 	"DQHJMC" VARCHAR2(50 BYTE) NULL ,			当前环节名称
     private Long hjsx;						// 	"HJSX" NUMBER(10) NULL ,					环节时限
     private String hjsxdw;					// 	"HJSXDW" CHAR(1 BYTE) DEFAULT 'G'  NULL ,	环节时限单位
     private Long sssx;						// 	"SSSX" NUMBER(10) NULL ,					所剩天数。即还有几天到期，超期即为负数
     private Long ejjcjg;					// 	"EJJCJG" NUMBER(10) NULL ,					监察状态（0，正常；1，预警；2，黄牌；5，红牌）
     private String zrdept;					// 	"ZRDEPT" VARCHAR2(50 BYTE) NULL ,			责任单位:监察对象，环节出牌责任单位，比如“罗湖区城管局
     private String jctime;					// 	"JCTIME" TIMESTAMP(6)  NULL ,				监察时间
     private String departid;				// 	"DEPARTID" VARCHAR2(60 BYTE) NULL ,			组织机构代码
     private String isexchangebsdt;			// 	"ISEXCHANGEBSDT" CHAR(1 BYTE) DEFAULT 0  NULL ,是否同步到网上办事大厅：0未同步、1同步成功、2同步失败
     private String serialnum;				// 	"SERIALNUM" VARCHAR2(50 BYTE) NULL 			29位统一申办流水号[根据市标准生成]



   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ywlsh", column=@Column(name="YWLSH", nullable=false, length=100) ), 
        @AttributeOverride(name="sjbbh", column=@Column(name="SJBBH", nullable=false, precision=4, scale=0) ) } )

    public TShouliUnits getId() {
        return this.id;
    }
    
    public void setId(TShouliUnits id) {
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
    
    @Column(name="SPSXMC", nullable=false, length=200)

    public String getSpsxmc() {
        return this.spsxmc;
    }
    
    public void setSpsxmc(String spsxmc) {
        this.spsxmc = spsxmc;
    }
    
    @Column(name="GDBLSX", nullable=false, precision=8, scale=0)

    public Long getGdblsx() {
        return this.gdblsx;
    }
    
    public void setGdblsx(Long gdblsx) {
        this.gdblsx = gdblsx;
    }
    
    @Column(name="GDBLSXDW", nullable=false, length=1)

    public String getGdblsxdw() {
        return this.gdblsxdw;
    }
    
    public void setGdblsxdw(String gdblsxdw) {
        this.gdblsxdw = gdblsxdw;
    }
    
    @Column(name="GDSF", nullable=false, length=1000)

    public String getGdsf() {
        return this.gdsf;
    }
    
    public void setGdsf(String gdsf) {
        this.gdsf = gdsf;
    }
    
    @Column(name="XMMC", nullable=false, length=400)

    public String getXmmc() {
        return this.xmmc;
    }
    
    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }
    
    @Column(name="FLGDGXD", nullable=false, length=20)

    public String getFlgdgxd() {
        return this.flgdgxd;
    }
    
    public void setFlgdgxd(String flgdgxd) {
        this.flgdgxd = flgdgxd;
    }
    
    @Column(name="FSYWGXD", nullable=false, length=20)

    public String getFsywgxd() {
        return this.fsywgxd;
    }
    
    public void setFsywgxd(String fsywgxd) {
        this.fsywgxd = fsywgxd;
    }
    
    @Column(name="SQDWHSQRXM", nullable=false, length=200)

    public String getSqdwhsqrxm() {
        return this.sqdwhsqrxm;
    }
    
    public void setSqdwhsqrxm(String sqdwhsqrxm) {
        this.sqdwhsqrxm = sqdwhsqrxm;
    }
    
    @Column(name="SQDWJBRXM", nullable=false, length=60)

    public String getSqdwjbrxm() {
        return this.sqdwjbrxm;
    }
    
    public void setSqdwjbrxm(String sqdwjbrxm) {
        this.sqdwjbrxm = sqdwjbrxm;
    }
    
    @Column(name="SQDWLXDH", length=40)

    public String getSqdwlxdh() {
        return this.sqdwlxdh;
    }
    
    public void setSqdwlxdh(String sqdwlxdh) {
        this.sqdwlxdh = sqdwlxdh;
    }
    
    @Column(name="SQDWJBRSJ", length=30)

    public String getSqdwjbrsj() {
        return this.sqdwjbrsj;
    }
    
    public void setSqdwjbrsj(String sqdwjbrsj) {
        this.sqdwjbrsj = sqdwjbrsj;
    }
    
    @Column(name="SQDWJBRYJ", length=80)

    public String getSqdwjbryj() {
        return this.sqdwjbryj;
    }
    
    public void setSqdwjbryj(String sqdwjbryj) {
        this.sqdwjbryj = sqdwjbryj;
    }
    
    @Column(name="SLJGZZJGDM", nullable=false, length=9)

    public String getSljgzzjgdm() {
        return this.sljgzzjgdm;
    }
    
    public void setSljgzzjgdm(String sljgzzjgdm) {
        this.sljgzzjgdm = sljgzzjgdm;
    }
    
    @Column(name="SLJGMC", nullable=false, length=100)

    public String getSljgmc() {
        return this.sljgmc;
    }
    
    public void setSljgmc(String sljgmc) {
        this.sljgmc = sljgmc;
    }
    
    @Column(name="SLDWCBRXM", nullable=false, length=60)

    public String getSldwcbrxm() {
        return this.sldwcbrxm;
    }
    
    public void setSldwcbrxm(String sldwcbrxm) {
        this.sldwcbrxm = sldwcbrxm;
    }
    
    @Column(name="SLDWCBRGH", length=50)

    public String getSldwcbrgh() {
        return this.sldwcbrgh;
    }
    
    public void setSldwcbrgh(String sldwcbrgh) {
        this.sldwcbrgh = sldwcbrgh;
    }
    
    @Column(name="SLJTDD", length=300)

    public String getSljtdd() {
        return this.sljtdd;
    }
    
    public void setSljtdd(String sljtdd) {
        this.sljtdd = sljtdd;
    }
    
    @Column(name="SLSJ", nullable=false)

    public Date getSlsj() {
        return this.slsj;
    }
    
    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }
    
    @Column(name="HZBH", nullable=false, length=100)

    public String getHzbh() {
        return this.hzbh;
    }
    
    public void setHzbh(String hzbh) {
        this.hzbh = hzbh;
    }
    
    @Column(name="SLZLQD", nullable=false, length=2000)

    public String getSlzlqd() {
        return this.slzlqd;
    }
    
    public void setSlzlqd(String slzlqd) {
        this.slzlqd = slzlqd;
    }
    
    @Column(name="SLYF", nullable=false, length=1)

    public String getSlyf() {
        return this.slyf;
    }
    
    public void setSlyf(String slyf) {
        this.slyf = slyf;
    }
    
    @Column(name="BSLYY", length=2000)

    public String getBslyy() {
        return this.bslyy;
    }
    
    public void setBslyy(String bslyy) {
        this.bslyy = bslyy;
    }
    
    @Column(name="TJFS", nullable=false, length=1)

    public String getTjfs() {
        return this.tjfs;
    }
    
    public void setTjfs(String tjfs) {
        this.tjfs = tjfs;
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
    
    @Column(name="DQHJMC", length=50)

    public String getDqhjmc() {
        return this.dqhjmc;
    }
    
    public void setDqhjmc(String dqhjmc) {
        this.dqhjmc = dqhjmc;
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