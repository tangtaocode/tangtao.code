package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ex_gdbs_tbcxjg")
public class TbcxjgProcess implements Serializable{

	private static final long serialVersionUID = 7296311483124235479L;

	@EmbeddedId
	private TebiechengxujieguoUnits id;
	
	@Column(name="SBLSH")
	private String sblsh;//申办流水号
	
	@Column(name="SXBM")
	private String sxbm;//事项编码
	
	@Column(name="SXBM_SHORT")
	private String sxbm_short;//事项编码简码
	
	@Column(name="SXQXBM")
	private String sxqxbm;//事项情形
	
	@Column(name="TBCXJG")
	private String tbcxjg;//特别程序结果
	
	@Column(name="JGCSRQ")
	private Date jgcsrq;//结果产生日期
	
	@Column(name="TBCXJSRQ")
	private Date tbcxjsrq;//特别程序结束日期
	
	@Column(name="TBCXSFJE")
	private String tbcxsfje;//特别程序收费金额
	
	@Column(name="JEDWDM")
	private String jedwdm;//金额单位代码
	
	@Column(name="XZQHDM")
	private String xzqhdm;
	
	@Column(name="BZ")
	private String bz;
	
	@Column(name="BYZD")
	private String byzd;

	@Column(name="INSERTTIME")
	private Date inserttime;//入库时间
	private Date responsetime;//接口调用成功返回时间
	
	
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

	public String getTbcxjg() {
		return tbcxjg;
	}
	public void setTbcxjg(String tbcxjg) {
		this.tbcxjg = tbcxjg;
	}
	public Date getJgcsrq() {
		return jgcsrq;
	}
	public void setJgcsrq(Date jgcsrq) {
		this.jgcsrq = jgcsrq;
	}
	public Date getTbcxjsrq() {
		return tbcxjsrq;
	}
	public void setTbcxjsrq(Date tbcxjsrq) {
		this.tbcxjsrq = tbcxjsrq;
	}
	public String getTbcxsfje() {
		return tbcxsfje;
	}
	public void setTbcxsfje(String tbcxsfje) {
		this.tbcxsfje = tbcxsfje;
	}
	public String getJedwdm() {
		return jedwdm;
	}
	public void setJedwdm(String jedwdm) {
		this.jedwdm = jedwdm;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
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
	public TebiechengxujieguoUnits getId() {
		return id;
	}
	public void setId(TebiechengxujieguoUnits id) {
		this.id = id;
	}
	public String getSxqxbm() {
		return sxqxbm;
	}
	public void setSxqxbm(String sxqxbm) {
		this.sxqxbm = sxqxbm;
	}
	
	
}
