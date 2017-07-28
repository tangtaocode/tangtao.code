package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TShenban entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_SHENBAN"
)

public class TShenban  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private TShenbanUnits id;			//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,		主键，调用函数createYWLSH()生成     									
     									// 	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,		数据的版本号，默认为1
     private String spsxbh;				//	"SPSXBH" VARCHAR2(100 BYTE) NOT NULL ,		行政区划+单位组织机构代码+3位流水号。审批事项编号由监察部门统一编号，不可为空
     private String spsxzxbh;			//	"SPSXZXBH" VARCHAR2(3 BYTE) NOT NULL ,		许可事项子项编号，由监察部门统一编号
     private String yxtywlsh;			//	"YXTYWLSH" VARCHAR2(100 BYTE) NOT NULL ,	本业务在原系统中的业务流水号，必须是唯一的	
     private String spsxmc;				//	"SPSXMC" VARCHAR2(200 BYTE) NOT NULL ,		本审批事项的名称，假如审批事项有子项，指审批事项子项名称
     private String sqrlx;				//	"SQRLX" VARCHAR2(2 BYTE) NOT NULL ,			申请人类型代码
     private String sqrmc;				//	"SQRMC" VARCHAR2(400 BYTE) NOT NULL ,		申请人为个人时，填写姓名，申请人为企业（机关单位）时，填写单位名称
     private String sqrzjhm;			//	"SQRZJHM" VARCHAR2(50 BYTE) NOT NULL ,		申请人为个人时，填写身份证件号码；申请人为企业（机关单位）时，填写组织机构代码或注册登记号
     private String lxrxm;				//	"LXRXM" VARCHAR2(200 BYTE) NOT NULL ,		如果是本人办理，联系人即为申请人；如果是企业（机关单位）办理，联系人为申请单位经办人
     private String lxrzjlx;			//	"LXRZJLX" VARCHAR2(2 BYTE) NULL ,			证件类型代码 
     private String lxrsfzjhm;			//	"LXRSFZJHM" VARCHAR2(50 BYTE) NULL ,		联系人身份证件号码。预留公民网页使用。
     private String lxrsj;				//	"LXRSJ" VARCHAR2(400 BYTE) NOT NULL ,		联系人手机号码。用于向经办人发送短信回执
     private String lxryx;				//	"LXRYX" VARCHAR2(400 BYTE) NULL ,			联系人邮箱
     private String sbxmmc;				//	"SBXMMC" VARCHAR2(2000 BYTE) NOT NULL ,		如果是行政审批事项，填写申请审批的项目具体名称，如果是社会事务服务事项，填写事项名称。
     private String sbclqd;				//	"SBCLQD" VARCHAR2(2000 BYTE) NOT NULL ,		申请时提交材料清单。多个材料时，可用半角分号隔开。
     private String tjfs;				//	"TJFS" VARCHAR2(2 BYTE) NULL ,   			申办资料提交方式，填写代码
     private String sbhzh;				// 	"SBHZH" VARCHAR2(50 BYTE) NOT NULL ,		申请人提出申办后获取的回执号，申办回执号可用于查询办事进度和结果，可以和申请信息编号一样。
     private Date sbsj;				// 	"SBSJ" TIMESTAMP(6)  NOT NULL ,				申请人提交申请时间
     private String sbjtwd;				// 	"SBJTWD" VARCHAR2(400 BYTE) NULL ,			网上申请时，对应申请人提出申办的网上办事窗口名称
     private String xzqhdm;				// 	"XZQHDM" VARCHAR2(12 BYTE) NOT NULL ,		申办业务发生所在行政区划代码
     private String ysblsh;				// 	"YSBLSH" VARCHAR2(50 BYTE) NULL ,			如果是补交补正，则对应原申办流水号。（预留，有待商榷）
     private String bz;					// 	"BZ" VARCHAR2(2000 BYTE) NULL ,				备注信息
     private String byzd;				// 	"BYZD" VARCHAR2(2000 BYTE) NULL ,			备用字段，用于扩展使用
     private String departid;			// 	"DEPARTID" VARCHAR2(60 BYTE) NULL ,			组织机构代码
     private String datasource;			// 	"DATASOURCE" VARCHAR2(50 BYTE) NULL ,		数据来源:如人力资源局为RLZYJ
     private String isget;				// 	"ISGET" CHAR(1 BYTE) DEFAULT 0  NULL ,		
     private String isexchangebsdt;		// 	"ISEXCHANGEBSDT" CHAR(1 BYTE) DEFAULT 0  NULL ,是否同步到网上办事大厅：0未同步、1同步成功、2同步失败
     private String serialnum;			// 	"SERIALNUM" VARCHAR2(50 BYTE) NULL ,		29位统一申办流水号[根据市标准生成]
     private String sblsh;				// 	"SBLSH" VARCHAR2(50 BYTE) NULL 
     	

    // Constructors

    /** default constructor */
    public TShenban() {
    }


   
    // Property accessors
    @EmbeddedId
    public TShenbanUnits getId() {
        return this.id;
    }
    
    public void setId(TShenbanUnits id) {
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
    
    @Column(name="SQRLX", nullable=false, length=2)

    public String getSqrlx() {
        return this.sqrlx;
    }
    
    public void setSqrlx(String sqrlx) {
        this.sqrlx = sqrlx;
    }
    
    @Column(name="SQRMC", nullable=false, length=400)

    public String getSqrmc() {
        return this.sqrmc;
    }
    
    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }
    
    @Column(name="SQRZJHM", nullable=false, length=50)

    public String getSqrzjhm() {
        return this.sqrzjhm;
    }
    
    public void setSqrzjhm(String sqrzjhm) {
        this.sqrzjhm = sqrzjhm;
    }
    
    @Column(name="LXRXM", nullable=false, length=200)

    public String getLxrxm() {
        return this.lxrxm;
    }
    
    public void setLxrxm(String lxrxm) {
        this.lxrxm = lxrxm;
    }
    
    @Column(name="LXRZJLX", length=2)

    public String getLxrzjlx() {
        return this.lxrzjlx;
    }
    
    public void setLxrzjlx(String lxrzjlx) {
        this.lxrzjlx = lxrzjlx;
    }
    
    @Column(name="LXRSFZJHM", length=50)

    public String getLxrsfzjhm() {
        return this.lxrsfzjhm;
    }
    
    public void setLxrsfzjhm(String lxrsfzjhm) {
        this.lxrsfzjhm = lxrsfzjhm;
    }
    
    @Column(name="LXRSJ", nullable=false, length=400)

    public String getLxrsj() {
        return this.lxrsj;
    }
    
    public void setLxrsj(String lxrsj) {
        this.lxrsj = lxrsj;
    }
    
    @Column(name="LXRYX", length=400)

    public String getLxryx() {
        return this.lxryx;
    }
    
    public void setLxryx(String lxryx) {
        this.lxryx = lxryx;
    }
    
    @Column(name="SBXMMC", nullable=false, length=2000)

    public String getSbxmmc() {
        return this.sbxmmc;
    }
    
    public void setSbxmmc(String sbxmmc) {
        this.sbxmmc = sbxmmc;
    }
    
    @Column(name="SBCLQD", nullable=false, length=2000)

    public String getSbclqd() {
        return this.sbclqd;
    }
    
    public void setSbclqd(String sbclqd) {
        this.sbclqd = sbclqd;
    }
    
    @Column(name="TJFS", length=2)

    public String getTjfs() {
        return this.tjfs;
    }
    
    public void setTjfs(String tjfs) {
        this.tjfs = tjfs;
    }
    
    @Column(name="SBHZH", nullable=false, length=50)

    public String getSbhzh() {
        return this.sbhzh;
    }
    
    public void setSbhzh(String sbhzh) {
        this.sbhzh = sbhzh;
    }
    
    @Column(name="SBSJ", nullable=false)

    public Date getSbsj() {
        return this.sbsj;
    }
    
    public void setSbsj(Date sbsj) {
        this.sbsj = sbsj;
    }
    
    @Column(name="SBJTWD", length=400)

    public String getSbjtwd() {
        return this.sbjtwd;
    }
    
    public void setSbjtwd(String sbjtwd) {
        this.sbjtwd = sbjtwd;
    }
    
    @Column(name="XZQHDM", nullable=false, length=12)

    public String getXzqhdm() {
        return this.xzqhdm;
    }
    
    public void setXzqhdm(String xzqhdm) {
        this.xzqhdm = xzqhdm;
    }
    
    @Column(name="YSBLSH", length=50)

    public String getYsblsh() {
        return this.ysblsh;
    }
    
    public void setYsblsh(String ysblsh) {
        this.ysblsh = ysblsh;
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
    
    @Column(name="SBLSH", length=50)

    public String getSblsh() {
        return this.sblsh;
    }
    
    public void setSblsh(String sblsh) {
        this.sblsh = sblsh;
    }

	@Override
	public String toString() {
		return "TShenban [id=" + id + ", spsxbh=" + spsxbh + ", spsxzxbh="
				+ spsxzxbh + ", yxtywlsh=" + yxtywlsh + ", spsxmc=" + spsxmc
				+ ", sqrlx=" + sqrlx + ", sqrmc=" + sqrmc + ", sqrzjhm="
				+ sqrzjhm + ", lxrxm=" + lxrxm + ", lxrzjlx=" + lxrzjlx
				+ ", lxrsfzjhm=" + lxrsfzjhm + ", lxrsj=" + lxrsj + ", lxryx="
				+ lxryx + ", sbxmmc=" + sbxmmc + ", sbclqd=" + sbclqd
				+ ", tjfs=" + tjfs + ", sbhzh=" + sbhzh + ", sbsj=" + sbsj
				+ ", sbjtwd=" + sbjtwd + ", xzqhdm=" + xzqhdm + ", ysblsh="
				+ ysblsh + ", bz=" + bz + ", byzd=" + byzd + ", departid="
				+ departid + ", datasource=" + datasource + ", isget=" + isget
				+ ", isexchangebsdt=" + isexchangebsdt + ", serialnum="
				+ serialnum + ", sblsh=" + sblsh + "]";
	}

	


}