package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="ex_gdbs_tbcxsq")//
public class TbcxsqProcess implements Serializable{

	private static final long serialVersionUID = 2563552620915959947L;

	@EmbeddedId
	private TebiechengxushenqingUnits id;
	
	@Column(name = "SBLSH")
	private String sblsh;//申办流水号
	
	@Column(name = "SXBM")
	private String sxbm;//事项编码
	
	@Column(name = "SXBM_SHORT")
	private String sxbm_short;//事项编码简码
	
	@Column(name = "SXQXBM")
	private String sxbm_qx;//事项情形
	/*特别程序种类A延长审批（需要部门领导批示，延长不能超过10个工作日，多部门联合统一办理的，需本级政府负责人批准，延长不能超过15个工作日）
	B除延长审批之外，听证、招标、拍卖等法律法规规定的情况，延长时间不得超过法定时间）*/
	@Column(name = "TBCXZL")
	private String tbcxzl;
	
	/**YYYY-MM-DD hh24:mm:ss*/
	@Column(name = "TBCXKSRQ")
	private Date tbcxksrq;
	
	@Column(name = "TBCXPZR")
	private String tbcxpzr;//特别程序批准人
	
	@Column(name = "TBCXQDLY")
	private String tbcxqdlyhyj;//特别程序启动理由、原因或依据
	
	@Column(name = "SQNR")
	private String sqnr;//申请内容
	/*填写申请或规定特别程序延长的时限。当特别程序种类为A时，此值大于0小于等于10。当特别程序种类为B时，填写规定的时限，没有规定特别程序时限的，填写0*/
	
	@Column(name="TBCXSX")
	private int tbcxsx;//特别程序时限
	/*申请或规定特别程序延长时限的单位：G：工作日（不包含法定节假日）Z：自然日*/
	
	@Column(name = "TBCXSXDW")
	private String tbcxsxdw;
	
	@Column(name = "XZQHDM")
	private String xzqhdm;
	
	@Column(name = "BZ")
	private String bz;
	
	@Column(name = "BYZD")
	private String byzd;

	@Column(name="INSERTTIME")
	private Date inserttime;//入库时间
	
	private Date responsetime;//接口调用成功返回时间
	
	@Transient
	private String auditPersonId;
	
	@Transient
	private String auditPerson;

	public TebiechengxushenqingUnits getId() {
		return id;
	}

	public void setId(TebiechengxushenqingUnits id) {
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

	public String getSxbm_qx() {
		return sxbm_qx;
	}

	public void setSxbm_qx(String sxbm_qx) {
		this.sxbm_qx = sxbm_qx;
	}

	public String getTbcxzl() {
		return tbcxzl;
	}

	public void setTbcxzl(String tbcxzl) {
		this.tbcxzl = tbcxzl;
	}

	public Date getTbcxksrq() {
		return tbcxksrq;
	}

	public void setTbcxksrq(Date tbcxksrq) {
		this.tbcxksrq = tbcxksrq;
	}

	public String getTbcxpzr() {
		return tbcxpzr;
	}

	public void setTbcxpzr(String tbcxpzr) {
		this.tbcxpzr = tbcxpzr;
	}

	public String getTbcxqdlyhyj() {
		return tbcxqdlyhyj;
	}

	public void setTbcxqdlyhyj(String tbcxqdlyhyj) {
		this.tbcxqdlyhyj = tbcxqdlyhyj;
	}

	public String getSqnr() {
		return sqnr;
	}

	public void setSqnr(String sqnr) {
		this.sqnr = sqnr;
	}

	public int getTbcxsx() {
		return tbcxsx;
	}

	public void setTbcxsx(int tbcxsx) {
		this.tbcxsx = tbcxsx;
	}

	public String getTbcxsxdw() {
		return tbcxsxdw;
	}

	public void setTbcxsxdw(String tbcxsxdw) {
		this.tbcxsxdw = tbcxsxdw;
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

	public String getAuditPersonId() {
		return auditPersonId;
	}

	public void setAuditPersonId(String auditPersonId) {
		this.auditPersonId = auditPersonId;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}
	
	
	
	
	
	
	
	
}
