package net.risesoft.approve.entity.jpa;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 申请窗口表:
 * @author Administrator
 *
 */
@Entity
@Table(name="DT_CKGL_MANAGER")
public class WindowManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String guid;		    //"GUID" NVARCHAR2(38 BYTE) NOT NULL ,		主键
	private String applyperson;	//"APPROVEPERSON" NVARCHAR2(30 BYTE)  NULL ,申请人
	private String employeeid;	//员工Id		EMPLOYEEID NVARCHAR2(100 BYTE) NULL,
	private String departmentdesk;   //"DEPAREMENTDESK" NVARCHAR2(100 BYTE)  NULL ,所在部门和科室,
	private String windowhallname;   //"WINDOWHALLNAME" NVARCHAR2(100 BYTE) NULL,所在窗口所属的分厅名称
	private String entitywindowname;	//"ENTITYWINDOWNAME" NVARCHAR2(100 BYTE) NULL,实体窗口名称
	private String windowguid;			//"WINDOWGUID" NVARCHAR2(38 BYTE) NULL,实体窗口GUID
	private String applytime;			//"APPLYTIME" NVARCHAR2(50 BYTE) NULL,申请时间
	private String approvetime;		//"APPROVETIME" NVARCHAR2(50 BYTE) NULL,批准时间
	private String status;			//"STATUS" NVARCHAR2(20 BYTE) NULL,状态 ，0为"审核中",1为"未批准"，2为"已批准"
	private String approveperson;	//"APPROVEPERSON" NVARCHAR2(30 BYTE) NULL,批准人
	
	@Id
	@Column(name="GUID",length=38)
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Column(name="APPLYPERSON",length=30)
	public String getApplyperson() {
		return applyperson;
	}
	public void setApplyperson(String applyperson) {
		this.applyperson = applyperson;
	}
	
	@Column(name="DEPARTMENTDESK",length=100)
	public String getDepartmentdesk() {
		return departmentdesk;
	}
	public void setDepartmentdesk(String departmentdesk) {
		this.departmentdesk = departmentdesk;
	}
	
	@Column(name="WINDOWHALLNAME",length=100)
	public String getWindowhallname() {
		return windowhallname;
	}
	public void setWindowhallname(String windowhallname) {
		this.windowhallname = windowhallname;
	}
	
	@Column(name="APPLYTIME",length=50)
	public String getApplytime() {
		return applytime;
	}
	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}
		
	@Column(name="APPROVETIME",length=50)
	public String getApprovetime() {
		return approvetime;
	}
	public void setApprovetime(String approvetime) {
		this.approvetime = approvetime;
	}
	
	@Column(name="STATUS",length=20)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="APPROVEPERSON",length=30)
	public String getApproveperson() {
		return approveperson;
	}
	public void setApproveperson(String approveperson) {
		this.approveperson = approveperson;
	}
	
	@Column(name="EMPLOYEEID",length=100)
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	
	@Column(name="ENTITYWINDOWNAME",length=100)
	public String getEntitywindowname() {
		return entitywindowname;
	}
	public void setEntitywindowname(String entitywindowname) {
		this.entitywindowname = entitywindowname;
	}
	
	@Column(name="WINDOWGUID",length=38)
	public String getWindowguid() {
		return windowguid;
	}
	public void setWindowguid(String windowguid) {
		this.windowguid = windowguid;
	}
	
	
}
