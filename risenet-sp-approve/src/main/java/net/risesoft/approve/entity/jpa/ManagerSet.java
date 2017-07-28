package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分厅管理人员设置
 * @author Administrator
 *
 */
@Entity
@Table(name="DT_CKGL_ADDMANAGER")
public class ManagerSet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String guid;				//主键			"GUID" NVARCHAR2(38 BYTE) NOT NULL
	private String name;				//管理人员姓名		"NAME" NVARCHAR2(50 BYTE) NULL ,
	private String employeeid;			//员工Id		EMPLOYEEID NVARCHAR2(100 BYTE) NULL,
	private String windowhallname;		//分厅名称			"WINDOWHALLNAME" 	NVARCHAR2(100 BYTE) NULL
	private String departmentdesk;		//所属部门和科室		"DEPARTMENTDESK"    NVARCHAR2(100 BYTE) NULL
	
	

	@Id
	@Column(name = "GUID", length = 38, nullable = false)
	public String getGuid() {
		return guid;
	}	

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Column(name="NAME",length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="WINDOWHALLNAME",length=100)
	public String getWindowhallname() {
		return windowhallname;
	}

	public void setWindowhallname(String windowhallname) {
		this.windowhallname = windowhallname;
	}

	@Column(name="DEPARTMENTDESK",length=100)
	public String getDepartmentdesk() {
		return departmentdesk;
	}

	public void setDepartmentdesk(String departmentdesk) {
		this.departmentdesk = departmentdesk;
	}
	
	@Column(name="EMPLOYEEID",length=100)
	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	
	
}
