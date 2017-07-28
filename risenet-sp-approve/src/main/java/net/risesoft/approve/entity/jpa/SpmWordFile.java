package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.risesoft.util.ConmonUtil;
/**
 *审批档案表
 * @author shenqiang
 *
 */
@Entity
@Table(name="SPM_WORD_FILE")
public class SpmWordFile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String guid;
	private String step;
	private String type;
	private byte[] content;
	private String writePerson;
	private Date writeTime;
	
	@Column(name="FILEPATH")
	private String filepath;
	
	@Column(name="STEP")
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	@Id
	@Column(name="GUID",unique=true,nullable=false, length=38)
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	@Column(name="CONTENT")
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	@Column(name="WRITEPERSON")
	public String getWritePerson() {
		return writePerson;
	}
	public void setWritePerson(String writePerson) {
		this.writePerson = writePerson;
	}
	@Column(name="WRITETIME")
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	
	
	
}
