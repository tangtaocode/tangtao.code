package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 申报材料类
 * @author shenqiang
 *
 */
@Entity
@Table(name="EX_GDBS_SPJG")//申办数据表
public class SpjgData implements Serializable{

	private static final long serialVersionUID = 8939567518656184801L;

	@Id
	@Column(name = "SEQ")
	private String seq;
	
	@Column(name="SBLSH_SHORT")
	private String sblshShort;

	@Column(name="SBLSH")
	private String sblsh;

	@Column(name="ATTACH_NAME")
	private String attachName;

	@Column(name="ATTACH_ID")
	private String attachId;

	@Column(name="ATTACH_BODY")
	private byte[] attachBody;

	@Column(name="SAVE_TYPE")
	private String saveType;

	@Column(name="ATTACH_SIGN")
	private String attachSign;

	@Column(name="ATTACH_TYPE")
	private String attachType;

	@Column(name="ATTACH_PATH")
	private String attachPath;

	@Column(name="F_XZQHDM")
	private String fxzqhdm;

	@Column(name="VERSION")
	private String version;

	@Column(name="REC_FLAG")
	private String recFlag;

	@Column(name="D_ZZJGDM")
	private String dzzjgdm;
	
	@Column(name = "INSERTTIME")
	private Date inserttime;//入库时间
	
	@Column(name = "RESPONSETIME")
	private Date responsetime;//接口调用成功返回时间

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSblshShort() {
		return sblshShort;
	}

	public void setSblshShort(String sblshShort) {
		this.sblshShort = sblshShort;
	}

	public String getSblsh() {
		return sblsh;
	}

	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
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

	public byte[] getAttachBody() {
		return attachBody;
	}

	public void setAttachBody(byte[] attachBody) {
		this.attachBody = attachBody;
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

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getFxzqhdm() {
		return fxzqhdm;
	}

	public void setFxzqhdm(String fxzqhdm) {
		this.fxzqhdm = fxzqhdm;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRecFlag() {
		return recFlag;
	}

	public void setRecFlag(String recFlag) {
		this.recFlag = recFlag;
	}

	public String getDzzjgdm() {
		return dzzjgdm;
	}

	public void setDzzjgdm(String dzzjgdm) {
		this.dzzjgdm = dzzjgdm;
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
