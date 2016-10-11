/**
 * 
 */
package net.risesoft.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * 
 * @author dingzhaojun,wuqiang
 *
 */
@Entity
@Table(name="risenet_employee")
public class RiseEmployee implements Serializable {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 8409688693003423365L;

	@Id
	@Column(name="employee_guid",length=38,nullable = false)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="assigned")
	private String employee_guid;

	@Column(name="department_guid",length=38,nullable = false)
	private String department_guid;
	
	@Column(name="employee_loginname",columnDefinition="VARCHAR2(20)")
	private String employee_loginname;
	
	
	@Column(name="employee_name",columnDefinition="VARCHAR2(20)",nullable = false)
	private String employee_name;
	
	@Column(name="employee_email",columnDefinition="VARCHAR2(60)")
	private String employee_email;
	
	@Column(name="employee_jobtitles",columnDefinition="VARCHAR2(50)")
	private String employee_jobtitles;
	
	
	@Column(name="employee_status",columnDefinition="VARCHAR2(20)")
	private String employee_status;
	
	@Column(name="employee_sex",columnDefinition="VARCHAR2(2)")
	private String employee_sex;
	
	@Column(name="employee_country",columnDefinition="VARCHAR2(20)")
	private String employee_country;
	
	@Column(name="employee_province",columnDefinition="VARCHAR2(20)")
	private String employee_province;
	
	@Column(name="employee_officephone",columnDefinition="VARCHAR2(20)")
	private String employee_officephone;
	
	
	@Column(name="employee_mobile",columnDefinition="VARCHAR2(20)")
	private String employee_mobile;
	
	@Column(name="tabIndex",nullable=false)
	private Integer tabIndex;
	
	@Column(name="employee_isdeleted")
	private boolean employee_isdeleted;
	
	@Column(name="employee_isdeleteddesc",columnDefinition="VARCHAR2(200)")
	private String employee_isdeleteddesc;
	
	@Column(name="employee_issms",columnDefinition="VARCHAR2(2)")
	private String employee_issms;//个人是否设置短信标志
	@Transient
	private String rcid;
	@Transient
	private String bureauName;
	@Transient
	private String bureauGUID;
	
	
	public String getBureauName() {
		return bureauName;
	}

	public void setBureauName(String bureauName) {
		this.bureauName = bureauName;
	}

	public String getBureauGUID() {
		return bureauGUID;
	}

	public void setBureauGUID(String bureauGUID) {
		this.bureauGUID = bureauGUID;
	}

	public String getEmployee_guid() {
		return employee_guid;
	}

	public void setEmployee_guid(String employee_guid) {
		this.employee_guid = employee_guid;
	}

	public String getDepartment_guid() {
		return department_guid;
	}

	public void setDepartment_guid(String department_guid) {
		this.department_guid = department_guid;
	}

	public String getEmployee_loginname() {
		return employee_loginname;
	}

	public void setEmployee_loginname(String employee_loginname) {
		this.employee_loginname = employee_loginname;
	}


	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmployee_email() {
		return employee_email;
	}

	public void setEmployee_email(String employee_email) {
		this.employee_email = employee_email;
	}

	public String getEmployee_jobtitles() {
		return employee_jobtitles;
	}

	public void setEmployee_jobtitles(String employee_jobtitles) {
		this.employee_jobtitles = employee_jobtitles;
	}

	public String getEmployee_status() {
		return employee_status;
	}

	public void setEmployee_status(String employee_status) {
		this.employee_status = employee_status;
	}

	public String getEmployee_sex() {
		return employee_sex;
	}

	public void setEmployee_sex(String employee_sex) {
		this.employee_sex = employee_sex;
	}


	public String getEmployee_country() {
		return employee_country;
	}

	public void setEmployee_country(String employee_country) {
		this.employee_country = employee_country;
	}

	public String getEmployee_province() {
		return employee_province;
	}

	public void setEmployee_province(String employee_province) {
		this.employee_province = employee_province;
	}


	public String getEmployee_officephone() {
		return employee_officephone;
	}

	public void setEmployee_officephone(String employee_officephone) {
		this.employee_officephone = employee_officephone;
	}

	

	public String getEmployee_mobile() {
		return employee_mobile;
	}

	public void setEmployee_mobile(String employee_mobile) {
		this.employee_mobile = employee_mobile;
	}

	
	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}


	public boolean isEmployee_isdeleted() {
		return employee_isdeleted;
	}

	public void setEmployee_isdeleted(boolean employee_isdeleted) {
		this.employee_isdeleted = employee_isdeleted;
	}

	public String getEmployee_isdeleteddesc() {
		return employee_isdeleteddesc;
	}

	public void setEmployee_isdeleteddesc(String employee_isdeleteddesc) {
		this.employee_isdeleteddesc = employee_isdeleteddesc;
	}

	public String getRcid() {
		return rcid;
	}

	public void setRcid(String rcid) {
		this.rcid = rcid;
	}

	public String getEmployee_issms() {
		return employee_issms;
	}

	public void setEmployee_issms(String employee_issms) {
		this.employee_issms = employee_issms;
	}

	
}
