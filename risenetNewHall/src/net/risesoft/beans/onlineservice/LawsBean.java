package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 政策法规bean
 * **/
@Entity
@Table(name="XZQL_LAW")
public class LawsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7350798555069066596L;
	private String id;  //
	private String title;  //
	private String type;  //
	private String content;  //
	private Date publishtime;  //
	private String status;  //
	private String createperson;  //
	private Date createtime;  //
	private String filename;  //
	private String ischangetoxzsp;  //

	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "TYPE", length=255)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="Title" ,length=255)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name="content", columnDefinition="CLOB", nullable=true) 
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public Date getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column
	public String getCreateperson() {
		return createperson;
	}
	public void setCreateperson(String createperson) {
		this.createperson = createperson;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Column
	public String getIschangetoxzsp() {
		return ischangetoxzsp;
	}
	public void setIschangetoxzsp(String ischangetoxzsp) {
		this.ischangetoxzsp = ischangetoxzsp;
	}
	
	
	


}
