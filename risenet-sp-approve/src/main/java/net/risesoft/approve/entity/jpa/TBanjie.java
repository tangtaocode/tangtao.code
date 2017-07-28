package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TBanjie entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_BANJIE"

)

public class TBanjie  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private TBanjieUnits id;				//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,			业务流水号
     										// 	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,			数据版本号
     private String spsxbh;					//	"SPSXBH" VARCHAR2(100 BYTE) NULL ,				审批事项编号
     private String spsxzxbh;				//	"SPSXZXBH" VARCHAR2(3 BYTE) NOT NULL ,			审批事项子项编号
     private String yxtywlsh;				//	"YXTYWLSH" VARCHAR2(100 BYTE) NOT NULL ,		原系统业务流水号
     private String bjjgzzjgdm;				//	"BJJGZZJGDM" VARCHAR2(9 BYTE) NOT NULL ,		办结机关组织机构代码
     private String bjjgmc;					//	"BJJGMC" VARCHAR2(200 BYTE) NULL ,				办结机关名称
     private Date bjsj;					//	"BJSJ" TIMESTAMP(6)  NOT NULL ,					办结（打证）时间
     private String bjjg;					//	"BJJG" VARCHAR2(1 BYTE) NOT NULL ,				办结结果
     private String bljgms;					//	"BLJGMS" VARCHAR2(2000 BYTE) NULL ,				办理结果描述
     private String zfthyy;					//	"ZFTHYY" VARCHAR2(2000 BYTE) NULL ,				作废退回原因	
     private String zjmc;					//	"ZJMC" VARCHAR2(200 BYTE) NULL ,				证件/盖章名称
     private String zjbh;					//	"ZJBH" VARCHAR2(100 BYTE) NULL ,				证件编号
     private String zjyxqx;					//	"ZJYXQX" VARCHAR2(30 BYTE) NULL ,				证件有效期限
     private String fzdw;					// 	"FZDW" VARCHAR2(200 BYTE) NULL ,				发证/盖章单位
     private Double sfje;					// 	"SFJE" NUMBER(16,2) NOT NULL ,					收费金额
     private String jedwdm;					// 	"JEDWDM" VARCHAR2(20 BYTE) DEFAULT 'CNY'  NULL ,金额单位代码
     private String bz;						// 	"BZ" VARCHAR2(200 BYTE) NULL ,					备注
     private String byzda;					// 	"BYZDA" VARCHAR2(100 BYTE) NULL ,				备用字段
     private String byzdb;					// 	"BYZDB" VARCHAR2(100 BYTE) NULL ,				备用字段
     private String byzdc;					// 	"BYZDC" VARCHAR2(500 BYTE) NULL ,				备用字段
     private String byzdd;					// 	"BYZDD" TIMESTAMP(6)  NULL ,					备用字段
     private String isget;					// 	"ISGET" CHAR(1 BYTE) DEFAULT 0  NULL ,
     private String datasource;				// 	"DATASOURCE" VARCHAR2(50 BYTE) DEFAULT 'XZSP'  NULL ,XZSP为罗湖区行政审批系统，RLZYJ为人力资源局
     private String syhjmc;					// 	"SYHJMC" VARCHAR2(50 BYTE) NULL ,				上一环节名称
     private String dqhjmc;					// 	"DQHJMC" VARCHAR2(50 BYTE) NULL ,				当前环节名称
     private Long hjsx;						// 	"HJSX" NUMBER(10) NULL ,						环节时限
     private String hjsxdw;					// 	"HJSXDW" CHAR(1 BYTE) DEFAULT 'G'  NULL ,		环节时限单位
     private Long sssx;						// 	"SSSX" NUMBER(10) NULL ,						所剩天数。即还有几天到期，超期即为负数
     private Long ejjcjg;					// 	"EJJCJG" NUMBER(10) NULL ,						监察状态（0，正常；1，预警；2，黄牌；5，红牌）
     private String zrdept;					// 	"ZRDEPT" VARCHAR2(50 BYTE) NULL ,				责任单位:监察对象，环节出牌责任单位，比如“罗湖区城管局
     private String jctime;					// 	"JCTIME" TIMESTAMP(6)  NULL ,					监察时间
     private String departid;				// 	"DEPARTID" VARCHAR2(60 BYTE) NULL ,				组织机构代码
     private String xkyf;					// 	"XKYF" CHAR(1 BYTE) DEFAULT '0'  NULL ,			指本业务是否准予许可，默认为0-许可，1-不予许可
     private String byxkyy;					// 	"BYXKYY" VARCHAR2(2000 BYTE) NULL ,				不予许可原因
     private String isexchangebsdt;			//是否同步至办事大厅中间库：0未交换、1交换成功、2交换失败
     private String serialnum;				// 		


    // Constructors


   
    // Property accessors
    @EmbeddedId
  
    public TBanjieUnits getId() {
        return this.id;
    }
    
    public void setId(TBanjieUnits id) {
        this.id = id;
    }
    
    @Column(name="SPSXBH", length=100)

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
    
    @Column(name="BJJGZZJGDM", nullable=false, length=9)

    public String getBjjgzzjgdm() {
        return this.bjjgzzjgdm;
    }
    
    public void setBjjgzzjgdm(String bjjgzzjgdm) {
        this.bjjgzzjgdm = bjjgzzjgdm;
    }
    
    @Column(name="BJJGMC", length=200)

    public String getBjjgmc() {
        return this.bjjgmc;
    }
    
    public void setBjjgmc(String bjjgmc) {
        this.bjjgmc = bjjgmc;
    }
    
    @Column(name="BJSJ", nullable=false)

    public Date getBjsj() {
        return this.bjsj;
    }
    
    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }
    
    @Column(name="BJJG", nullable=false, length=1)

    public String getBjjg() {
        return this.bjjg;
    }
    
    public void setBjjg(String bjjg) {
        this.bjjg = bjjg;
    }
    
    @Column(name="BLJGMS", length=2000)

    public String getBljgms() {
        return this.bljgms;
    }
    
    public void setBljgms(String bljgms) {
        this.bljgms = bljgms;
    }
    
    @Column(name="ZFTHYY", length=2000)

    public String getZfthyy() {
        return this.zfthyy;
    }
    
    public void setZfthyy(String zfthyy) {
        this.zfthyy = zfthyy;
    }
    
    @Column(name="ZJMC", length=200)

    public String getZjmc() {
        return this.zjmc;
    }
    
    public void setZjmc(String zjmc) {
        this.zjmc = zjmc;
    }
    
    @Column(name="ZJBH", length=100)

    public String getZjbh() {
        return this.zjbh;
    }
    
    public void setZjbh(String zjbh) {
        this.zjbh = zjbh;
    }
    
    @Column(name="ZJYXQX", length=30)

    public String getZjyxqx() {
        return this.zjyxqx;
    }
    
    public void setZjyxqx(String zjyxqx) {
        this.zjyxqx = zjyxqx;
    }
    
    @Column(name="FZDW", length=200)

    public String getFzdw() {
        return this.fzdw;
    }
    
    public void setFzdw(String fzdw) {
        this.fzdw = fzdw;
    }
    
    @Column(name="SFJE", nullable=false, precision=16)

    public Double getSfje() {
        return this.sfje;
    }
    
    public void setSfje(Double sfje) {
        this.sfje = sfje;
    }
    
    @Column(name="JEDWDM", length=20)

    public String getJedwdm() {
        return this.jedwdm;
    }
    
    public void setJedwdm(String jedwdm) {
        this.jedwdm = jedwdm;
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
    
    @Column(name="XKYF", length=1)

    public String getXkyf() {
        return this.xkyf;
    }
    
    public void setXkyf(String xkyf) {
        this.xkyf = xkyf;
    }
    
    @Column(name="BYXKYY", length=2000)

    public String getByxkyy() {
        return this.byxkyy;
    }
    
    public void setByxkyy(String byxkyy) {
        this.byxkyy = byxkyy;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bjjg == null) ? 0 : bjjg.hashCode());
		result = prime * result + ((bjjgmc == null) ? 0 : bjjgmc.hashCode());
		result = prime * result
				+ ((bjjgzzjgdm == null) ? 0 : bjjgzzjgdm.hashCode());
		result = prime * result + ((bjsj == null) ? 0 : bjsj.hashCode());
		result = prime * result + ((bljgms == null) ? 0 : bljgms.hashCode());
		result = prime * result + ((byxkyy == null) ? 0 : byxkyy.hashCode());
		result = prime * result + ((byzda == null) ? 0 : byzda.hashCode());
		result = prime * result + ((byzdb == null) ? 0 : byzdb.hashCode());
		result = prime * result + ((byzdc == null) ? 0 : byzdc.hashCode());
		result = prime * result + ((byzdd == null) ? 0 : byzdd.hashCode());
		result = prime * result + ((bz == null) ? 0 : bz.hashCode());
		result = prime * result
				+ ((datasource == null) ? 0 : datasource.hashCode());
		result = prime * result
				+ ((departid == null) ? 0 : departid.hashCode());
		result = prime * result + ((dqhjmc == null) ? 0 : dqhjmc.hashCode());
		result = prime * result + ((ejjcjg == null) ? 0 : ejjcjg.hashCode());
		result = prime * result + ((fzdw == null) ? 0 : fzdw.hashCode());
		result = prime * result + ((hjsx == null) ? 0 : hjsx.hashCode());
		result = prime * result + ((hjsxdw == null) ? 0 : hjsxdw.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isexchangebsdt == null) ? 0 : isexchangebsdt.hashCode());
		result = prime * result + ((isget == null) ? 0 : isget.hashCode());
		result = prime * result + ((jctime == null) ? 0 : jctime.hashCode());
		result = prime * result + ((jedwdm == null) ? 0 : jedwdm.hashCode());
		result = prime * result
				+ ((serialnum == null) ? 0 : serialnum.hashCode());
		result = prime * result + ((sfje == null) ? 0 : sfje.hashCode());
		result = prime * result + ((spsxbh == null) ? 0 : spsxbh.hashCode());
		result = prime * result
				+ ((spsxzxbh == null) ? 0 : spsxzxbh.hashCode());
		result = prime * result + ((sssx == null) ? 0 : sssx.hashCode());
		result = prime * result + ((syhjmc == null) ? 0 : syhjmc.hashCode());
		result = prime * result + ((xkyf == null) ? 0 : xkyf.hashCode());
		result = prime * result
				+ ((yxtywlsh == null) ? 0 : yxtywlsh.hashCode());
		result = prime * result + ((zfthyy == null) ? 0 : zfthyy.hashCode());
		result = prime * result + ((zjbh == null) ? 0 : zjbh.hashCode());
		result = prime * result + ((zjmc == null) ? 0 : zjmc.hashCode());
		result = prime * result + ((zjyxqx == null) ? 0 : zjyxqx.hashCode());
		result = prime * result + ((zrdept == null) ? 0 : zrdept.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TBanjie other = (TBanjie) obj;
		if (bjjg == null) {
			if (other.bjjg != null)
				return false;
		} else if (!bjjg.equals(other.bjjg))
			return false;
		if (bjjgmc == null) {
			if (other.bjjgmc != null)
				return false;
		} else if (!bjjgmc.equals(other.bjjgmc))
			return false;
		if (bjjgzzjgdm == null) {
			if (other.bjjgzzjgdm != null)
				return false;
		} else if (!bjjgzzjgdm.equals(other.bjjgzzjgdm))
			return false;
		if (bjsj == null) {
			if (other.bjsj != null)
				return false;
		} else if (!bjsj.equals(other.bjsj))
			return false;
		if (bljgms == null) {
			if (other.bljgms != null)
				return false;
		} else if (!bljgms.equals(other.bljgms))
			return false;
		if (byxkyy == null) {
			if (other.byxkyy != null)
				return false;
		} else if (!byxkyy.equals(other.byxkyy))
			return false;
		if (byzda == null) {
			if (other.byzda != null)
				return false;
		} else if (!byzda.equals(other.byzda))
			return false;
		if (byzdb == null) {
			if (other.byzdb != null)
				return false;
		} else if (!byzdb.equals(other.byzdb))
			return false;
		if (byzdc == null) {
			if (other.byzdc != null)
				return false;
		} else if (!byzdc.equals(other.byzdc))
			return false;
		if (byzdd == null) {
			if (other.byzdd != null)
				return false;
		} else if (!byzdd.equals(other.byzdd))
			return false;
		if (bz == null) {
			if (other.bz != null)
				return false;
		} else if (!bz.equals(other.bz))
			return false;
		if (datasource == null) {
			if (other.datasource != null)
				return false;
		} else if (!datasource.equals(other.datasource))
			return false;
		if (departid == null) {
			if (other.departid != null)
				return false;
		} else if (!departid.equals(other.departid))
			return false;
		if (dqhjmc == null) {
			if (other.dqhjmc != null)
				return false;
		} else if (!dqhjmc.equals(other.dqhjmc))
			return false;
		if (ejjcjg == null) {
			if (other.ejjcjg != null)
				return false;
		} else if (!ejjcjg.equals(other.ejjcjg))
			return false;
		if (fzdw == null) {
			if (other.fzdw != null)
				return false;
		} else if (!fzdw.equals(other.fzdw))
			return false;
		if (hjsx == null) {
			if (other.hjsx != null)
				return false;
		} else if (!hjsx.equals(other.hjsx))
			return false;
		if (hjsxdw == null) {
			if (other.hjsxdw != null)
				return false;
		} else if (!hjsxdw.equals(other.hjsxdw))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isexchangebsdt == null) {
			if (other.isexchangebsdt != null)
				return false;
		} else if (!isexchangebsdt.equals(other.isexchangebsdt))
			return false;
		if (isget == null) {
			if (other.isget != null)
				return false;
		} else if (!isget.equals(other.isget))
			return false;
		if (jctime == null) {
			if (other.jctime != null)
				return false;
		} else if (!jctime.equals(other.jctime))
			return false;
		if (jedwdm == null) {
			if (other.jedwdm != null)
				return false;
		} else if (!jedwdm.equals(other.jedwdm))
			return false;
		if (serialnum == null) {
			if (other.serialnum != null)
				return false;
		} else if (!serialnum.equals(other.serialnum))
			return false;
		if (sfje == null) {
			if (other.sfje != null)
				return false;
		} else if (!sfje.equals(other.sfje))
			return false;
		if (spsxbh == null) {
			if (other.spsxbh != null)
				return false;
		} else if (!spsxbh.equals(other.spsxbh))
			return false;
		if (spsxzxbh == null) {
			if (other.spsxzxbh != null)
				return false;
		} else if (!spsxzxbh.equals(other.spsxzxbh))
			return false;
		if (sssx == null) {
			if (other.sssx != null)
				return false;
		} else if (!sssx.equals(other.sssx))
			return false;
		if (syhjmc == null) {
			if (other.syhjmc != null)
				return false;
		} else if (!syhjmc.equals(other.syhjmc))
			return false;
		if (xkyf == null) {
			if (other.xkyf != null)
				return false;
		} else if (!xkyf.equals(other.xkyf))
			return false;
		if (yxtywlsh == null) {
			if (other.yxtywlsh != null)
				return false;
		} else if (!yxtywlsh.equals(other.yxtywlsh))
			return false;
		if (zfthyy == null) {
			if (other.zfthyy != null)
				return false;
		} else if (!zfthyy.equals(other.zfthyy))
			return false;
		if (zjbh == null) {
			if (other.zjbh != null)
				return false;
		} else if (!zjbh.equals(other.zjbh))
			return false;
		if (zjmc == null) {
			if (other.zjmc != null)
				return false;
		} else if (!zjmc.equals(other.zjmc))
			return false;
		if (zjyxqx == null) {
			if (other.zjyxqx != null)
				return false;
		} else if (!zjyxqx.equals(other.zjyxqx))
			return false;
		if (zrdept == null) {
			if (other.zrdept != null)
				return false;
		} else if (!zrdept.equals(other.zrdept))
			return false;
		return true;
	}
   
    







}