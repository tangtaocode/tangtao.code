package net.risesoft.beans.civilbankroll;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MSXM_ORGANIZATION_FILETEMP")
public class CivilOrgFile implements Serializable{
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 7757794356021911828L;
	private String guid;  //
	private String organizationtype;  //申报机构
	private String evaluationtask;  //评审任务
	private String declarecondition;  //申报条件
	private String onlinefile;  //网上申报需递交资料
	private String windowsfile;  //窗口申报需递交资料
	private String remarks;  //备注

	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getOrganizationtype() {
		return organizationtype;
	}
	public void setOrganizationtype(String organizationtype) {
		this.organizationtype = organizationtype;
	}
	@Column
	public String getEvaluationtask() {
		return evaluationtask;
	}
	public void setEvaluationtask(String evaluationtask) {
		this.evaluationtask = evaluationtask;
	}
	@Column
	public String getDeclarecondition() {
		return declarecondition;
	}
	public void setDeclarecondition(String declarecondition) {
		this.declarecondition = declarecondition;
	}
	@Column
	public String getOnlinefile() {
		return onlinefile;
	}
	public void setOnlinefile(String onlinefile) {
		this.onlinefile = onlinefile;
	}
	@Column
	public String getWindowsfile() {
		return windowsfile;
	}
	public void setWindowsfile(String windowsfile) {
		this.windowsfile = windowsfile;
	}
	@Column
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
