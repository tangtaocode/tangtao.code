package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TWangshangyushouli entity.
 * 网上预受理
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_WANGSHANGYUSHOULI"
)

public class TWangshangyushouli  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	
	

     private TWangshangyushouliUnits id;		//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,		主键，调用函数createYWLSH()生成
     											// 	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,		数据的版本号，默认为1
     private String spsxbh;						//	"SPSXBH" VARCHAR2(100 BYTE) NOT NULL ,		行政区划+单位组织机构代码+3位流水号。审批事项编号由监察部门统一编号
     private String spsxzxbh;					//	"SPSXZXBH" VARCHAR2(3 BYTE) NOT NULL ,		许可事项子项编号，由监察部门统一编号
     private String yxtywlsh;					//	"YXTYWLSH" VARCHAR2(100 BYTE) NOT NULL ,	本业务在原系统中的业务流水号，必须是唯一的
     private String spsxmc;						//	"SPSXMC" VARCHAR2(200 BYTE) NOT NULL ,		本审批事项的名称，假如审批事项有子项，指审批事项子项名称
     private String yslbmmc;					//	"YSLBMMC" VARCHAR2(400 BYTE) NOT NULL ,		预受理部门名称
     private String yslbmzzjgdm;				//	"YSLBMZZJGDM" VARCHAR2(9 BYTE) NOT NULL ,	预受理部门的组织机构代码，如是二级单位的填写二级单位的组织机构代码。
     private String xzqhdm;						//	"XZQHDM" VARCHAR2(12 BYTE) NOT NULL ,		预受理部门所在的行政区划代码。
     private String blrxm;						//	"BLRXM" VARCHAR2(200 BYTE) NOT NULL ,		业务办理人员姓名
     private String blrgh;						//	"BLRGH" VARCHAR2(50 BYTE) NULL ,			业务办理人员工号
     private String yslztdm;					//	"YSLZTDM" VARCHAR2(2 BYTE) NOT NULL ,		预受理状态代码
     private String bslyy;						//	"BSLYY" VARCHAR2(2000 BYTE) NULL ,			不受理时必须说明原因
     private String bjbzsm;						//	"BJBZSM" VARCHAR2(2000 BYTE) NULL ,			补交补正材料的详细说明
     private Date yslsj;						//	"YSLSJ" TIMESTAMP(6)  NOT NULL ,			部门完成预受理审核的时间	
     private String ysljtdd;					//	"YSLJTDD" VARCHAR2(400 BYTE) NULL ,			业务办理人员办公地点，精确到房间号或者窗口号。
     private String bz;							//	"BZ" VARCHAR2(2000 BYTE) NULL ,				备注信息
     private String byzd;						//	"BYZD" VARCHAR2(2000 BYTE) NULL ,			备用字段，用于扩展使用
     private String departid;					//	"DEPARTID" VARCHAR2(60 BYTE) NULL ,			组织机构代码
     private String datasource;					//	"DATASOURCE" VARCHAR2(50 BYTE) NULL ,		数据来源:如人力资源局为RLZYJ
     private String isget;						//	"ISGET" CHAR(1 BYTE) DEFAULT 0  NULL ,
     private String isexchangebsdt;				//	"ISEXCHANGEBSDT" CHAR(1 BYTE) DEFAULT 0  NULL ,是否同步到网上办事大厅：0未同步、1同步成功、2同步失败
     private String serialnum;					//	"SERIALNUM" VARCHAR2(50 BYTE) NULL 		29位统一申办流水号[根据市标准生成]

    // Constructors

    /** default constructor */
    public TWangshangyushouli() {
    }


   



	// Property accessors
    @EmbeddedId
    public TWangshangyushouliUnits getId() {
        return this.id;
    }
    
    public void setId(TWangshangyushouliUnits id) {
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
    
    @Column(name="YSLBMMC", nullable=false, length=400)

    public String getYslbmmc() {
        return this.yslbmmc;
    }
    
    public void setYslbmmc(String yslbmmc) {
        this.yslbmmc = yslbmmc;
    }
    
    @Column(name="YSLBMZZJGDM", nullable=false, length=9)

    public String getYslbmzzjgdm() {
        return this.yslbmzzjgdm;
    }
    
    public void setYslbmzzjgdm(String yslbmzzjgdm) {
        this.yslbmzzjgdm = yslbmzzjgdm;
    }
    
    @Column(name="XZQHDM", nullable=false, length=12)

    public String getXzqhdm() {
        return this.xzqhdm;
    }
    
    public void setXzqhdm(String xzqhdm) {
        this.xzqhdm = xzqhdm;
    }
    
    @Column(name="BLRXM", nullable=false, length=200)

    public String getBlrxm() {
        return this.blrxm;
    }
    
    public void setBlrxm(String blrxm) {
        this.blrxm = blrxm;
    }
    
    @Column(name="BLRGH", length=50)

    public String getBlrgh() {
        return this.blrgh;
    }
    
    public void setBlrgh(String blrgh) {
        this.blrgh = blrgh;
    }
    
    @Column(name="YSLZTDM", nullable=false, length=2)

    public String getYslztdm() {
        return this.yslztdm;
    }
    
    public void setYslztdm(String yslztdm) {
        this.yslztdm = yslztdm;
    }
    
    @Column(name="BSLYY", length=2000)

    public String getBslyy() {
        return this.bslyy;
    }
    
    public void setBslyy(String bslyy) {
        this.bslyy = bslyy;
    }
    
    @Column(name="BJBZSM", length=2000)

    public String getBjbzsm() {
        return this.bjbzsm;
    }
    
    public void setBjbzsm(String bjbzsm) {
        this.bjbzsm = bjbzsm;
    }
    
    @Column(name="YSLSJ", nullable=false)

    public Date getYslsj() {
        return this.yslsj;
    }
    
    public void setYslsj(Date yslsj) {
        this.yslsj = yslsj;
    }
    
    @Column(name="YSLJTDD", length=400)

    public String getYsljtdd() {
        return this.ysljtdd;
    }
    
    public void setYsljtdd(String ysljtdd) {
        this.ysljtdd = ysljtdd;
    }
    
    @Column(name="BZ", length=2000)

    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }
    
    @Column(name="BYZD", length=2000)

    public String getByzd() {
        return this.byzd;
    }
    
    public void setByzd(String byzd) {
        this.byzd = byzd;
    }
    
    @Column(name="DEPARTID", length=60)

    public String getDepartid() {
        return this.departid;
    }
    
    public void setDepartid(String departid) {
        this.departid = departid;
    }
    
    @Column(name="DATASOURCE", length=50)

    public String getDatasource() {
        return this.datasource;
    }
    
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
    
    @Column(name="ISGET", length=1)

    public String getIsget() {
        return this.isget;
    }
    
    public void setIsget(String isget) {
        this.isget = isget;
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
		result = prime * result + ((bjbzsm == null) ? 0 : bjbzsm.hashCode());
		result = prime * result + ((blrgh == null) ? 0 : blrgh.hashCode());
		result = prime * result + ((blrxm == null) ? 0 : blrxm.hashCode());
		result = prime * result + ((bslyy == null) ? 0 : bslyy.hashCode());
		result = prime * result + ((byzd == null) ? 0 : byzd.hashCode());
		result = prime * result + ((bz == null) ? 0 : bz.hashCode());
		result = prime * result
				+ ((datasource == null) ? 0 : datasource.hashCode());
		result = prime * result
				+ ((departid == null) ? 0 : departid.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isexchangebsdt == null) ? 0 : isexchangebsdt.hashCode());
		result = prime * result + ((isget == null) ? 0 : isget.hashCode());
		result = prime * result
				+ ((serialnum == null) ? 0 : serialnum.hashCode());
		result = prime * result + ((spsxbh == null) ? 0 : spsxbh.hashCode());
		result = prime * result + ((spsxmc == null) ? 0 : spsxmc.hashCode());
		result = prime * result
				+ ((spsxzxbh == null) ? 0 : spsxzxbh.hashCode());
		result = prime * result + ((xzqhdm == null) ? 0 : xzqhdm.hashCode());
		result = prime * result + ((yslbmmc == null) ? 0 : yslbmmc.hashCode());
		result = prime * result
				+ ((yslbmzzjgdm == null) ? 0 : yslbmzzjgdm.hashCode());
		result = prime * result + ((ysljtdd == null) ? 0 : ysljtdd.hashCode());
		result = prime * result + ((yslsj == null) ? 0 : yslsj.hashCode());
		result = prime * result + ((yslztdm == null) ? 0 : yslztdm.hashCode());
		result = prime * result
				+ ((yxtywlsh == null) ? 0 : yxtywlsh.hashCode());
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
		TWangshangyushouli other = (TWangshangyushouli) obj;
		if (bjbzsm == null) {
			if (other.bjbzsm != null)
				return false;
		} else if (!bjbzsm.equals(other.bjbzsm))
			return false;
		if (blrgh == null) {
			if (other.blrgh != null)
				return false;
		} else if (!blrgh.equals(other.blrgh))
			return false;
		if (blrxm == null) {
			if (other.blrxm != null)
				return false;
		} else if (!blrxm.equals(other.blrxm))
			return false;
		if (bslyy == null) {
			if (other.bslyy != null)
				return false;
		} else if (!bslyy.equals(other.bslyy))
			return false;
		if (byzd == null) {
			if (other.byzd != null)
				return false;
		} else if (!byzd.equals(other.byzd))
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
		if (serialnum == null) {
			if (other.serialnum != null)
				return false;
		} else if (!serialnum.equals(other.serialnum))
			return false;
		if (spsxbh == null) {
			if (other.spsxbh != null)
				return false;
		} else if (!spsxbh.equals(other.spsxbh))
			return false;
		if (spsxmc == null) {
			if (other.spsxmc != null)
				return false;
		} else if (!spsxmc.equals(other.spsxmc))
			return false;
		if (spsxzxbh == null) {
			if (other.spsxzxbh != null)
				return false;
		} else if (!spsxzxbh.equals(other.spsxzxbh))
			return false;
		if (xzqhdm == null) {
			if (other.xzqhdm != null)
				return false;
		} else if (!xzqhdm.equals(other.xzqhdm))
			return false;
		if (yslbmmc == null) {
			if (other.yslbmmc != null)
				return false;
		} else if (!yslbmmc.equals(other.yslbmmc))
			return false;
		if (yslbmzzjgdm == null) {
			if (other.yslbmzzjgdm != null)
				return false;
		} else if (!yslbmzzjgdm.equals(other.yslbmzzjgdm))
			return false;
		if (ysljtdd == null) {
			if (other.ysljtdd != null)
				return false;
		} else if (!ysljtdd.equals(other.ysljtdd))
			return false;
		if (yslsj == null) {
			if (other.yslsj != null)
				return false;
		} else if (!yslsj.equals(other.yslsj))
			return false;
		if (yslztdm == null) {
			if (other.yslztdm != null)
				return false;
		} else if (!yslztdm.equals(other.yslztdm))
			return false;
		if (yxtywlsh == null) {
			if (other.yxtywlsh != null)
				return false;
		} else if (!yxtywlsh.equals(other.yxtywlsh))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TWangshangyushouli [id=" + id + ", spsxbh=" + spsxbh
				+ ", spsxzxbh=" + spsxzxbh + ", yxtywlsh=" + yxtywlsh
				+ ", spsxmc=" + spsxmc + ", yslbmmc=" + yslbmmc
				+ ", yslbmzzjgdm=" + yslbmzzjgdm + ", xzqhdm=" + xzqhdm
				+ ", blrxm=" + blrxm + ", blrgh=" + blrgh + ", yslztdm="
				+ yslztdm + ", bslyy=" + bslyy + ", bjbzsm=" + bjbzsm
				+ ", yslsj=" + yslsj + ", ysljtdd=" + ysljtdd + ", bz=" + bz
				+ ", byzd=" + byzd + ", departid=" + departid + ", datasource="
				+ datasource + ", isget=" + isget + ", isexchangebsdt="
				+ isexchangebsdt + ", serialnum=" + serialnum + "]";
	}
   

    






}