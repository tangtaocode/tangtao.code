package net.risesoft.beans.wssb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 网上申报事项表单绑定信息
 * 
 * @author ys
 * @Date 2013-8-27 上午10:50:24
 */
@Entity
@Table(name="PROCEEDINGFORMS")
public class ProceedingForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8250454288898246094L;
	/**
	 * 
	 */
	
	
	private String approveitemguid; //事项guid
	private String formname;//表单名称
	private String url;//表单地址
	private String description;//描述
	private Integer type;//类型
	private Integer displayorder;
	private String guid;
	private String tableName;
	private Integer must;
	private String columnname;
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getApproveitemguid() {
		return approveitemguid;
	}
	public void setApproveitemguid(String approveitemguid) {
		this.approveitemguid = approveitemguid;
	}
	@Column
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	@Column
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Column
	public Integer getDisplayorder() {
		return displayorder;
	}
	public void setDisplayorder(Integer displayorder) {
		this.displayorder = displayorder;
	}
	@Column
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Column
	public Integer getMust() {
		return must;
	}
	public void setMust(Integer must) {
		this.must = must;
	}
	@Column
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	
}
