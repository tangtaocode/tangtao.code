package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="EX_GDBS_SBCL")//申办材料表
public class ShenBanCailiaoProcess implements Serializable {

	private static final long serialVersionUID = -5953448860924106802L;

	@Id
   	@Column(name = "SEQ")
	private String seq;//主键
	
	@Column(name = "SBLSH")
	private String sblsh;// 省级统一申办流水号
	
	@Column(name = "WJLX")
	private String wjlx;//文件类型 0：纸质， 1 电子
	
	@Column(name = "CLLX")
	private String cllx;//材料类型 0:原件;1:复印件;2:电子件
	
	@Column(name = "CLSL")
	private String clsl;//材料数量
	
	@Column(name = "ATTACH_NAME")
	private String attachName;//材料附件全称（含后缀）
	
	@Column(name = "ATTACH_ID")
	private String attachId;//材料附件唯一ID
	
	@Column(name = "SAVE_TYPE")
	private String saveType;//0：网盘；1：文件；3：数据库
	
	@Column(name = "ATTACH_SIGN")
	private String attachSign;//材料附件md5摘要，用于校验文件完整性
	
	@Column(name = "ATTACH_TYPE")
	private String seqattachType;//附件类型 如：xls、pdf、doc等
	
	@Column(name = "ATTACH_PATH")
	private String attachPath;//附件路径/网盘ID
	
	@Column(name = "STUFF_SEQ")
	private String stuffSeq;//根据市统一申办受理平台提供的事项材料编号
	
	@Column(name = "CLMC")
	private String clmc;//根据市统一申办受理平台提供的事项材料名称
	
	@Column(name = "SLBMZZJDDM")
	private String slbmzzjddm;//部门组织机构代码
	
	@Column(name = "XZQHDM")
	private String xzqhdm;//部门所在行政区划代码
	
	@Column(name = "VERSION")
	private String wersion;//数据版本号
	
	@Column(name = "REC_FLAG")
	private String recFlag;//接收标识
	
	@Column(name = "SBLSH_SHORT")
	private String sblshShort;//市级统一申办流水号
	
	@Column(name = "ATTACH_BODY")
	private Blob attachBody;//附件文件流
	
	@Column(name = "INSERTTIME")
	private Date insertTime;//入库时间
	
	@Column(name = "RESPONSETIME")  //接口调用时间
	private Date responseTime;
	
	public ShenBanCailiaoProcess() {
		super();
	}

	public ShenBanCailiaoProcess(String seq, String sblsh, String wjlx,
			String cllx, String clsl, String attachName, String attachId,
			String saveType, String attachSign, String seqattachType,
			String attachPath, String stuffSeq, String clmc, String slbmzzjddm,
			String xzqhdm, String wersion, String recFlag, String sblshShort,
			Blob attachBody, Date insertTime, Date responseTime) {
		super();
		this.seq = seq;
		this.sblsh = sblsh;
		this.wjlx = wjlx;
		this.cllx = cllx;
		this.clsl = clsl;
		this.attachName = attachName;
		this.attachId = attachId;
		this.saveType = saveType;
		this.attachSign = attachSign;
		this.seqattachType = seqattachType;
		this.attachPath = attachPath;
		this.stuffSeq = stuffSeq;
		this.clmc = clmc;
		this.slbmzzjddm = slbmzzjddm;
		this.xzqhdm = xzqhdm;
		this.wersion = wersion;
		this.recFlag = recFlag;
		this.sblshShort = sblshShort;
		this.attachBody = attachBody;
		this.insertTime = insertTime;
		this.responseTime = responseTime;
	}



	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSblsh() {
		return sblsh;
	}

	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
	}

	public String getWjlx() {
		return wjlx;
	}

	public void setWjlx(String wjlx) {
		this.wjlx = wjlx;
	}

	public String getCllx() {
		return cllx;
	}

	public void setCllx(String cllx) {
		this.cllx = cllx;
	}

	public String getClsl() {
		return clsl;
	}

	public void setClsl(String clsl) {
		this.clsl = clsl;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getAttachSign() {
		return attachSign;
	}

	public void setAttachSign(String attachSign) {
		this.attachSign = attachSign;
	}

	public String getSeqattachType() {
		return seqattachType;
	}

	public void setSeqattachType(String seqattachType) {
		this.seqattachType = seqattachType;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getStuffSeq() {
		return stuffSeq;
	}

	public void setStuffSeq(String stuffSeq) {
		this.stuffSeq = stuffSeq;
	}

	public String getClmc() {
		return clmc;
	}

	public void setClmc(String clmc) {
		this.clmc = clmc;
	}

	public String getSlbmzzjddm() {
		return slbmzzjddm;
	}

	public void setSlbmzzjddm(String slbmzzjddm) {
		this.slbmzzjddm = slbmzzjddm;
	}

	public String getXzqhdm() {
		return xzqhdm;
	}

	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}

	public String getWersion() {
		return wersion;
	}

	public void setWersion(String wersion) {
		this.wersion = wersion;
	}

	public String getRecFlag() {
		return recFlag;
	}

	public void setRecFlag(String recFlag) {
		this.recFlag = recFlag;
	}

	public String getSblshShort() {
		return sblshShort;
	}

	public void setSblshShort(String sblshShort) {
		this.sblshShort = sblshShort;
	}

	public Blob getAttachBody() {
		return attachBody;
	}

	public void setAttachBody(Blob attachBody) {
		this.attachBody = attachBody;
	}
	
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	

}
