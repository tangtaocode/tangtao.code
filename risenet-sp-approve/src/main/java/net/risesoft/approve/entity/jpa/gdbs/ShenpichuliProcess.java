package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 审批处理
 * @author shenqiang
 *
 */
@Entity
@Table(name="EX_GDBS_SPCL")//申办数据表
public class ShenpichuliProcess implements Serializable{

	private static final long serialVersionUID = -7091479300082373052L;

	 @EmbeddedId
	 private ShenpiguochengUnits id;
	
	
	@Column(name = "SBLSH")
	private String sblsh;//申办流水号
	
	
	
	
	@Column(name = "SXBM")
	private String sxbm;//事项编码
	
	@Column(name = "SXBM_SHORT")
	private String sxbm_short;//事项编码简码
	
	@Column(name = "SXQXBM")
	private String sxqxbm;//事项情形
	
	@Column(name = "SPHJMC")
	private String sphjmc;//审批环节名称
	
	@Column(name = "SPBMMC")
	private String spbmmc;//审批部门名称
	
	@Column(name = "SPBMZZJDMD")
	private String spbmzzjgdm;//审批部门组织机构代码
	
	@Column(name = "XZQHDM")
	private String xzqhdm;//审批环节代码
	
	@Column(name = "SPRXM")
	private String sprxm;//审批人姓名
	
	@Column(name = "SPRZWDM")
	private String sprzwdm;//审批人职务代码
	
	@Column(name = "SPRZWMC")
	private String sprzwmc;//审批职务名称
	
	@Column(name = "SPYJ")
	private String spyj;//审批意见
	
	@Column(name = "SPSJ")
	private Date spsj;//审批时间
	
	@Column(name = "SPHJZTDM")
	private String sphjztdm;//审批环节状态代码
	
	@Column(name = "BZ")
	private String bz;
	
	@Column(name = "BYZD")
	private String byzd;
	
	@Column(name = "INSERTTIME")
	private Date inserttime;//入库时间
	
	@Column(name = "RESPONSETIME")
	private Date responsetime;//接口调用成功返回时间
	
	
	
	public ShenpiguochengUnits getId() {
		return id;
	}
	public void setId(ShenpiguochengUnits id) {
		this.id = id;
	}
	public String getSblsh() {
		return sblsh;
	}
	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
	}
	
	public String getSxbm() {
		return sxbm;
	}
	public void setSxbm(String sxbm) {
		this.sxbm = sxbm;
	}
	public String getSxbm_short() {
		return sxbm_short;
	}
	public void setSxbm_short(String sxbm_short) {
		this.sxbm_short = sxbm_short;
	}
	
	
	public String getSxqxbm() {
		return sxqxbm;
	}
	public void setSxqxbm(String sxqxbm) {
		this.sxqxbm = sxqxbm;
	}

	public String getSphjmc() {
		return sphjmc;
	}
	public void setSphjmc(String sphjmc) {
		this.sphjmc = sphjmc;
	}
	public String getSpbmmc() {
		return spbmmc;
	}
	public void setSpbmmc(String spbmmc) {
		this.spbmmc = spbmmc;
	}
	public String getSpbmzzjgdm() {
		return spbmzzjgdm;
	}
	public void setSpbmzzjgdm(String spbmzzjgdm) {
		this.spbmzzjgdm = spbmzzjgdm;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getSprxm() {
		return sprxm;
	}
	public void setSprxm(String sprxm) {
		this.sprxm = sprxm;
	}
	public String getSprzwdm() {
		return sprzwdm;
	}
	public void setSprzwdm(String sprzwdm) {
		this.sprzwdm = sprzwdm;
	}
	public String getSprzwmc() {
		return sprzwmc;
	}
	public void setSprzwmc(String sprzwmc) {
		this.sprzwmc = sprzwmc;
	}
	public String getSpyj() {
		return spyj;
	}
	public void setSpyj(String spyj) {
		this.spyj = spyj;
	}
	public Date getSpsj() {
		return spsj;
	}
	public void setSpsj(Date spsj) {
		this.spsj = spsj;
	}
	public String getSphjztdm() {
		return sphjztdm;
	}
	public void setSphjztdm(String sphjztdm) {
		this.sphjztdm = sphjztdm;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getByzd() {
		return byzd;
	}
	public void setByzd(String byzd) {
		this.byzd = byzd;
	}
	public Date getInserttime() {
		return inserttime;
	}
	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
	public Date getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(Date responsetime) {
		this.responsetime = responsetime;
	}
	
	
}
