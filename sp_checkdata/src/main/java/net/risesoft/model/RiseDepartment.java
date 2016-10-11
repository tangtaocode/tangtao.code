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

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author dingzhaojun,wuqiang
 *
 */
@Entity
@Table(name="risenet_department")
public class RiseDepartment implements Serializable {
	

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -6106283504979617973L;

	@Id
	@Column(name="department_guid",length=38,nullable = false)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="assigned")
	private String department_guid;

	@Column(name="superior_guid",length=38,nullable = false)
	private String superior_guid;
	
	@Column(name="department_name",columnDefinition="VARCHAR2(200)",nullable = false)
	private String department_name;
	
	@Column(name="department_description",columnDefinition="VARCHAR2(4000)")
	private String department_description;
	
	@Column(name="department_alias",columnDefinition="VARCHAR2(200)")
	private String department_alias;
	
	@Column(name="department_shortdn",columnDefinition="VARCHAR2(200)")
	private String department_shortdn;
	
	@Column(name="department_givenname",columnDefinition="VARCHAR2(200)")
	private String department_givenname;
	
	@Column(name="department_hierarchy",columnDefinition="VARCHAR2(2000)")
	private String department_hierarchy;
	
	@Column(name="department_country",columnDefinition="VARCHAR2(200)")
	private String department_country;
	
	@Column(name="department_state",columnDefinition="VARCHAR2(200)")
	private String department_state;
	
	@Column(name="department_city",columnDefinition="VARCHAR2(200)")
	private String department_city;
	
	@Column(name="department_zipcode",columnDefinition="VARCHAR2(12)")
	private String department_zipcode;
	
	@Column(name="department_address",columnDefinition="VARCHAR2(255)")
	private String department_address;
	
	@Column(name="department_office",columnDefinition="VARCHAR2(255)")
	private String department_office;
	
	@Column(name="department_managerguid",columnDefinition="VARCHAR2(38)")
	private String department_managerguid;
	
	@Column(name="department_phone",columnDefinition="VARCHAR2(200)")
	private String department_phone;
	
	@Column(name="department_fax",columnDefinition="VARCHAR2(200)")
	private String department_fax;
	
	@Column(name="tabIndex",nullable=false)
	private Integer tabIndex;
	
	@Column(name="department_isdeleted")
	private boolean department_isdeleted;
	
	@Column(name="department_isdeleteddesc",columnDefinition="VARCHAR2(200)")
	private String department_isdeleteddesc;
	
	@Column(name="isbureau")
	private Integer isbureau;
	
	@Column(name="dispatcher_guids",columnDefinition="VARCHAR2(1000)")
	private String dispatcher_guids;
	
	@Column(name="department_manager",length=200)
	private String department_manager;
	
	@Column(name="department_supermanager",length=200)
	private String department_supermanager;
	
	@Column(name="department_level",precision=22)
	private Integer department_level;
	
	@Column(name="isdepment",precision=22)
	private Integer isdepment;

	public String getDepartment_guid() {
		return department_guid;
	}

	public void setDepartment_guid(String department_guid) {
		this.department_guid = department_guid;
	}

	public String getSuperior_guid() {
		return superior_guid;
	}

	public void setSuperior_guid(String superior_guid) {
		this.superior_guid = superior_guid;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getDepartment_description() {
		return department_description;
	}

	public void setDepartment_description(String department_description) {
		this.department_description = department_description;
	}

	public String getDepartment_alias() {
		return department_alias;
	}

	public void setDepartment_alias(String department_alias) {
		this.department_alias = department_alias;
	}

	public String getDepartment_shortdn() {
		return department_shortdn;
	}

	public void setDepartment_shortdn(String department_shortdn) {
		this.department_shortdn = department_shortdn;
	}

	public String getDepartment_givenname() {
		return department_givenname;
	}

	public void setDepartment_givenname(String department_givenname) {
		this.department_givenname = department_givenname;
	}

	public String getDepartment_hierarchy() {
		return department_hierarchy;
	}

	public void setDepartment_hierarchy(String department_hierarchy) {
		this.department_hierarchy = department_hierarchy;
	}

	public String getDepartment_country() {
		return department_country;
	}

	public void setDepartment_country(String department_country) {
		this.department_country = department_country;
	}

	public String getDepartment_state() {
		return department_state;
	}

	public void setDepartment_state(String department_state) {
		this.department_state = department_state;
	}

	public String getDepartment_city() {
		return department_city;
	}

	public void setDepartment_city(String department_city) {
		this.department_city = department_city;
	}

	public String getDepartment_zipcode() {
		return department_zipcode;
	}

	public void setDepartment_zipcode(String department_zipcode) {
		this.department_zipcode = department_zipcode;
	}

	public String getDepartment_address() {
		return department_address;
	}

	public void setDepartment_address(String department_address) {
		this.department_address = department_address;
	}

	public String getDepartment_office() {
		return department_office;
	}

	public void setDepartment_office(String department_office) {
		this.department_office = department_office;
	}

	public String getDepartment_managerguid() {
		return department_managerguid;
	}

	public void setDepartment_managerguid(String department_managerguid) {
		this.department_managerguid = department_managerguid;
	}

	public String getDepartment_phone() {
		return department_phone;
	}

	public void setDepartment_phone(String department_phone) {
		this.department_phone = department_phone;
	}

	public String getDepartment_fax() {
		return department_fax;
	}

	public void setDepartment_fax(String department_fax) {
		this.department_fax = department_fax;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isDepartment_isdeleted() {
		return department_isdeleted;
	}

	public void setDepartment_isdeleted(boolean department_isdeleted) {
		this.department_isdeleted = department_isdeleted;
	}

	public String getDepartment_isdeleteddesc() {
		return department_isdeleteddesc;
	}

	public void setDepartment_isdeleteddesc(String department_isdeleteddesc) {
		this.department_isdeleteddesc = department_isdeleteddesc;
	}

	public Integer getIsbureau() {
		return isbureau;
	}

	public void setIsbureau(Integer isbureau) {
		this.isbureau = isbureau;
	}

	public String getDispatcher_guids() {
		return dispatcher_guids;
	}

	public void setDispatcher_guids(String dispatcher_guids) {
		this.dispatcher_guids = dispatcher_guids;
	}

	public String getDepartment_manager() {
		return department_manager;
	}

	public void setDepartment_manager(String department_manager) {
		this.department_manager = department_manager;
	}

	public String getDepartment_supermanager() {
		return department_supermanager;
	}

	public void setDepartment_supermanager(String department_supermanager) {
		this.department_supermanager = department_supermanager;
	}

	public Integer getDepartment_level() {
		return department_level;
	}

	public void setDepartment_level(Integer department_level) {
		this.department_level = department_level;
	}

	public Integer getIsdepment() {
		return isdepment;
	}

	public void setIsdepment(Integer isdepment) {
		this.isdepment = isdepment;
	}
	
}
