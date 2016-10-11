package net.risesoft.beans.risefile;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="RISENET_FILE")
public class RisenetFile implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 28345371628962106L;
	private String fileguid;  //
	private String filename;  //
	private String filenameext;  //
	private String titile;  //
	private String majorname;  //
	private Integer majorversion;  //
	private Integer minversion;  //
	private String appname;  //
	private String fileboxname;  //
	private String appinstguid;  //
	private String creatorguid;  //
	private Date createdate;  //
	private Date lastmodified;  //
	private String realfullpath;  //
	private Double filesize;  //
	private String corrguid;
	private String windowPath;
	private String newName;

	@Transient
	public String getWindowPath() {
		return windowPath;
	}
	public void setWindowPath(String windowPath) {
		this.windowPath = windowPath;
	}
	@Transient
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	@Id
	public String getFileguid() {
		return fileguid;
	}
	public void setFileguid(String fileguid) {
		this.fileguid = fileguid;
	}
	@Column
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Column
	public String getFilenameext() {
		return filenameext;
	}
	public void setFilenameext(String filenameext) {
		this.filenameext = filenameext;
	}
	@Column
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	@Column
	public String getMajorname() {
		return majorname;
	}
	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}
	@Column
	public Integer getMajorversion() {
		return majorversion;
	}
	public void setMajorversion(Integer majorversion) {
		this.majorversion = majorversion;
	}
	@Column
	public Integer getMinversion() {
		return minversion;
	}
	public void setMinversion(Integer minversion) {
		this.minversion = minversion;
	}
	@Column
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	@Column
	public String getFileboxname() {
		return fileboxname;
	}
	public void setFileboxname(String fileboxname) {
		this.fileboxname = fileboxname;
	}
	@Column
	public String getAppinstguid() {
		return appinstguid;
	}
	public void setAppinstguid(String appinstguid) {
		this.appinstguid = appinstguid;
	}
	@Column
	public String getCreatorguid() {
		return creatorguid;
	}
	public void setCreatorguid(String creatorguid) {
		this.creatorguid = creatorguid;
	}
	@Column
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column
	public Date getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	@Column
	public String getRealfullpath() {
		return realfullpath;
	}
	public void setRealfullpath(String realfullpath) {
		this.realfullpath = realfullpath;
	}
	@Column
	public void setFilesize(Double filesize) {
		this.filesize = filesize;
	}
	public void setCorrguid(String corrguid) {
		this.corrguid = corrguid;
	}
	@Column
	public String getCorrguid() {
		return corrguid;
	}
	public Double getFilesize() {
		return filesize;
	}
	

}
