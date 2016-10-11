package net.risesoft.beans.correction;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="SPM_ONLINESUBMITDECLAREANNEX")
public class CorrectionBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 4834362032978707286L;
	private String guid;  //
	private String workflowinstance_guid;  //实例GUID
	private String declaresn;  //业务编号
	private String bureauguid;  //委办局GUID
	private String approveitemguid;  //事项GUID
	private String employeeguid;  //办理人GUID
	private String declareannexguid;  //申请材料GUID
	private String employeeloginname;  //办理人登录名
	private String idnumber;  //申请人证件号
	private String rootpath;  //文件存放地址
	private Integer issubmit;  //是否提交0：未提交，1：已提交，2审核通过
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getWorkflowinstance_guid() {
		return workflowinstance_guid;
	}
	public void setWorkflowinstance_guid(String workflowinstance_guid) {
		this.workflowinstance_guid = workflowinstance_guid;
	}
	@Column
	public String getDeclaresn() {
		return declaresn;
	}
	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}
	@Column
	public String getBureauguid() {
		return bureauguid;
	}
	public void setBureauguid(String bureauguid) {
		this.bureauguid = bureauguid;
	}
	@Column
	public String getApproveitemguid() {
		return approveitemguid;
	}
	public void setApproveitemguid(String approveitemguid) {
		this.approveitemguid = approveitemguid;
	}
	@Column
	public String getEmployeeguid() {
		return employeeguid;
	}
	public void setEmployeeguid(String employeeguid) {
		this.employeeguid = employeeguid;
	}
	@Column
	public String getDeclareannexguid() {
		return declareannexguid;
	}
	public void setDeclareannexguid(String declareannexguid) {
		this.declareannexguid = declareannexguid;
	}
	@Column
	public String getEmployeeloginname() {
		return employeeloginname;
	}
	public void setEmployeeloginname(String employeeloginname) {
		this.employeeloginname = employeeloginname;
	}
	@Column
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	@Column
	public String getRootpath() {
		return rootpath;
	}
	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}
	@Column
	public Integer getIssubmit() {
		return issubmit;
	}
	public void setIssubmit(Integer issubmit) {
		this.issubmit = issubmit;
	}

}
