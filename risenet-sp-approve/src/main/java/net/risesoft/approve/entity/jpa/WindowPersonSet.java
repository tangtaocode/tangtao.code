package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 窗口和人员工号绑定表
 * @author Administrator
 *
 */
@Entity
@Table(name="DT_CKGL_WINDOWPERSONADD")
public class WindowPersonSet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String guid;		//"GUID" NVARCHAR2(38 BYTE) NOT NULL ,		主键
	private String enrollnumber;		//"ENROLLNUMBER" NVARCHAR2(10 BYTE) NULL ,      窗口人员工号
	private String entitywindowname;//"entitywindowname" NVARCHAR2(100 BYTE) NULL ,实体窗口名称
	private int devicenumber;		//"DEVICENUMBER" NVARCHAR2(20 BYTE) NULL ,设备编号(考勤机编号)
	private String windowname;	//WINDOWNAME  NVARCHAR2(15 BYTE) NULL ,窗口人员姓名  ,
	private String phonenumber;	//"PHONENUMBER" NVARCHAR2(20 BYTE) NULL ,手机号码 
	private String ismanager;//ISMANAGER   NVARCHAR2(10 BYTE) NOT NULL ,是否为窗口负责人 ，1为是，0为否
	private String departmentdesk;//DEPAREMENTDESK NVARCHAR2(100 BYTE)  NULL ,所在部门和科室,
	private String windowhallname;//WINDOWHALLNAME NVARCHAR2(100 BYTE) NULL,所在窗口所属的分厅名称
	private String username;		//USERNAME NVARCHAR2(15 BYTE) NULL,大厅管理员
	private String windowguid;//实体窗口guid  WINDOWGUID NVARCHAR2(38 BYTE) NULL,
	private String employeeid;//窗口员工Id		EMPLOYEEID NVARCHAR2(100 BYTE) NULL,
	private String usernameid;//管理员的id		USERNAMEID NVARCHAR2(100 BYTE) NULL
	private String adjustcause;	//调整原因		ADJUSTCAUST NVARCHAR2(300 BYTE) NULL
	private String adjuststatus;//调整状态		ADJUSTSTATUS NVARCHAR2(10 BYTE) NOT NULL,O为申请中,1为未批准,2为已批准
	
	
	@Column(name="WINDOWGUID",length=38)
	public String getWindowguid() {
		return windowguid;
	}
	public void setWindowguid(String windowguid) {
		this.windowguid = windowguid;
	}
	@Column(name="EMPLOYEEID",length=100)
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	@Column(name="WINDOWNAME",length=15)
	public String getWindowname() {
		return windowname;
	}
	public void setWindowname(String windowname) {
		this.windowname = windowname;
	}
	
	@Column(name="ISMANAGER",length=10)
	public String getIsmanager() {
		return ismanager;
	}
	public void setIsmanager(String ismanager) {
		this.ismanager = ismanager;
	}
	
	@Column(name="DEPARTMENTDESK",length=20)
	public String getDepartmentdesk() {
		return departmentdesk;
	}
	public void setDepartmentdesk(String departmentdesk) {
		this.departmentdesk = departmentdesk;
	}
	@Id
	@Column(name="GUID",length=38)
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Column(name="ENROLLNUMBER",length=10)
	public String getEnrollnumber() {
		return enrollnumber;
	}
	public void setEnrollnumber(String enrollnumber) {
		this.enrollnumber = enrollnumber;
	}
	
	@Column(name="WINDOWHALLNAME",length=100)
	public String getWindowhallname() {
		return windowhallname;
	}
	public void setWindowhallname(String windowhallname) {
		this.windowhallname = windowhallname;
	}
	
	@Column(name="USERNAME",length=15)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name="ENTITYWINDOWNAME",length=100)
	public String getEntitywindowname() {
		return entitywindowname;
	}
	public void setEntitywindowname(String entitywindowname) {
		this.entitywindowname = entitywindowname;
	}
	
	@Column(name="PHONENUMBER",length=20)
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	@Column(name="USERNAMEID",length=100)
	public String getUsernameid() {
		return usernameid;
	}
	public void setUsernameid(String usernameid) {
		this.usernameid = usernameid;
	}
	
	@Column(name="ADJUSTCAUSE",length=300)
	public String getAdjustcause() {
		return adjustcause;
	}
	public void setAdjustcause(String adjustcause) {
		this.adjustcause = adjustcause;
	}
	
	@Column(name="ADJUSTSTATUS",length=10)
	public String getAdjuststatus() {
		return adjuststatus;
	}
	public void setAdjuststatus(String adjuststatus) {
		this.adjuststatus = adjuststatus;
	}
	
	@Column(name="DEVICENUMBER",length=20)
	public int getDevicenumber() {
		return devicenumber;
	}
	public void setDevicenumber(int devicenumber) {
		this.devicenumber = devicenumber;
	}
	
	

}
