package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ZJFC_XMLXINFO")
public class BizProjectType implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	private static final long serialVersionUID = -891297309821644884L;
	private String guid;  //主键
	private String pguid;  //父节点ID
	private String name;  //项目类型名称
	private Integer tab_index;  //排序号
	private String departmentguid;  //主管部门GUID
	private String contactp;  //咨询电话
	private String status;  //0未开放，1已开放
	private String isyx;  //0无效，1有效
	private Date uptime;
	
	@Column
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getPguid() {
		return pguid;
	}
	public void setPguid(String pguid) {
		this.pguid = pguid;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public Integer getTab_index() {
		return tab_index;
	}
	public void setTab_index(Integer tab_index) {
		this.tab_index = tab_index;
	}
	@Column
	public String getDepartmentguid() {
		return departmentguid;
	}
	public void setDepartmentguid(String departmentguid) {
		this.departmentguid = departmentguid;
	}
	@Column
	public String getContactp() {
		return contactp;
	}
	public void setContactp(String contactp) {
		this.contactp = contactp;
	}
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column
	public String getIsyx() {
		return isyx;
	}
	public void setIsyx(String isyx) {
		this.isyx = isyx;
	}
	
}
