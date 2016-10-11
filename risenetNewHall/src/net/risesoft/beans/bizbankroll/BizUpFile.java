package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZJFC_UPFILE")
public class BizUpFile implements Serializable{
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -766285356504226L;
	private String guid;  //表格GUID
	private String filename;  //文件名称
	private Date uptime;  //上传时间
	private String upuser;  //上传人GUID
	private String filetype;  //材料类型，1基本材料，2项目材料
	private String xmlxguid;  //申报项目GUID
	private String clguid;  //材料guid
	private String windowspath;  //WINDOWS文件路径
	private String linuxpath;  //linux文件路径
	private String isupdate;  //0
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Column
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	@Column
	public String getUpuser() {
		return upuser;
	}
	public void setUpuser(String upuser) {
		this.upuser = upuser;
	}
	@Column
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	@Column
	public String getXmlxguid() {
		return xmlxguid;
	}
	public void setXmlxguid(String xmlxguid) {
		this.xmlxguid = xmlxguid;
	}
	@Column
	public String getClguid() {
		return clguid;
	}
	public void setClguid(String clguid) {
		this.clguid = clguid;
	}
	@Column
	public String getWindowspath() {
		return windowspath;
	}
	public void setWindowspath(String windowspath) {
		this.windowspath = windowspath;
	}
	@Column
	public String getLinuxpath() {
		return linuxpath;
	}
	public void setLinuxpath(String linuxpath) {
		this.linuxpath = linuxpath;
	}
	@Column
	public String getIsupdate() {
		return isupdate;
	}
	public void setIsupdate(String isupdate) {
		this.isupdate = isupdate;
	}

}
