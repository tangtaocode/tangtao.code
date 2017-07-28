package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EX_GDBS_BJ")//申办数据表
public class BanjieProcess implements Serializable{

	private static final long serialVersionUID = 4905867550195892669L;
	
	@Column(name = "SBLSH")
	private String sblsh;//申办流水号
	
	@Id
	@Column(name = "SBLSH_SHORT")
	private String sblsh_short;//申办流水号(简短)
	
	@Column(name = "SXBM")
	private String sxbm;//事项编码
	
	@Column(name = "SXBM_SHORT")
	private String sxbm_short;//事项编码简码
	
	@Column(name = "SXQXBM")
	private String sxbm_qx;//事项情形
	
	@Column(name = "BJBMMC")
	private String bjbmmc;//办结部门名称
	
	@Column(name = "BJBMZZJGDM")
	private String bjbmzzjgdm;//办结部门组织机构代码
	
	@Column(name = "ZFHTTYY")
	private String zfhthyy;
	@Column(name = "XZQHDM")
	private String xzqhdm;//行政区划代码
	/*0：出证办结 1：退回办结 2：作废办结 3：删除办结 4：转报办结 5：补正不来办结 6：办结*/
	@Column(name = "BJJGDM")
	private String bjjgdm;//办理结果代码
	
	@Column(name = "BJJGMS")
	private String bjjgms;//办结结果描述
	
	@Column(name = "ZJGZMC")
	private String zjgzmc;//证件盖章名称
	
	@Column(name = "ZJBH")
	private String zjbh;//证件编号
	@Column(name = "ZJYXQX")
	private String zjyxqx;//证件有效期限
	
	@Column(name = "FZGZDW")
	private String fzgzdw;//发证盖章单位
	
	@Column(name = "SFJE")
	private double sfje;//收费金额办理结果是0（出证办结）时必须填写。
	
	@Column(name = "JEDWDM")
	private String jedwdm;//金额单位代码
	
	@Column(name = "BJSJ")
	private Date bjsj;//办结时间
	@Column(name = "BZ")
	private String bz;
	@Column(name = "BYZD")
	private String byzd;
	@Column(name = "INSERTIME")
	private Date inserttime;//入库时间
	
	@Column(name = "RESPONSETIME")
	private Date responsetime;//接口调用成功返回时间
	
	public String getSblsh() {
		return sblsh;
	}
	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
	}
	public String getSblsh_short() {
		return sblsh_short;
	}
	public void setSblsh_short(String sblsh_short) {
		this.sblsh_short = sblsh_short;
	}
	public String getSxbm() {
		return sxbm;
	}
	
	
	public String getZfhthyy() {
		return zfhthyy;
	}
	public void setZfhthyy(String zfhthyy) {
		this.zfhthyy = zfhthyy;
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
	public String getSxbm_qx() {
		return sxbm_qx;
	}
	public void setSxbm_qx(String sxbm_qx) {
		this.sxbm_qx = sxbm_qx;
	}
	public String getBjbmmc() {
		return bjbmmc;
	}
	public void setBjbmmc(String bjbmmc) {
		this.bjbmmc = bjbmmc;
	}
	public String getBjbmzzjgdm() {
		return bjbmzzjgdm;
	}
	public void setBjbmzzjgdm(String bjbmzzjgdm) {
		this.bjbmzzjgdm = bjbmzzjgdm;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getBjjgdm() {
		return bjjgdm;
	}
	public void setBjjgdm(String bjjgdm) {
		this.bjjgdm = bjjgdm;
	}
	public String getBjjgms() {
		return bjjgms;
	}
	public void setBjjgms(String bjjgms) {
		this.bjjgms = bjjgms;
	}
	public String getZjgzmc() {
		return zjgzmc;
	}
	public void setZjgzmc(String zjgzmc) {
		this.zjgzmc = zjgzmc;
	}
	public String getZjbh() {
		return zjbh;
	}
	public void setZjbh(String zjbh) {
		this.zjbh = zjbh;
	}
	public String getZjyxqx() {
		return zjyxqx;
	}
	public void setZjyxqx(String zjyxqx) {
		this.zjyxqx = zjyxqx;
	}
	public String getFzgzdw() {
		return fzgzdw;
	}
	public void setFzgzdw(String fzgzdw) {
		this.fzgzdw = fzgzdw;
	}
	public double getSfje() {
		return sfje;
	}
	public void setSfje(double sfje) {
		this.sfje = sfje;
	}
	public String getJedwdm() {
		return jedwdm;
	}
	public void setJedwdm(String jedwdm) {
		this.jedwdm = jedwdm;
	}
	public Date getBjsj() {
		return bjsj;
	}
	public void setBjsj(Date bjsj) {
		this.bjsj = bjsj;
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
