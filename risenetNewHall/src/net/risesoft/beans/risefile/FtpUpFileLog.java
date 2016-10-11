package net.risesoft.beans.risefile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ZJFC_LOGFILE")
public class FtpUpFileLog implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -5325678841734006821L;
	private String guid;  //主键
	private String filepath;  //文件路径
	private String uppath;  //上传路径
	private String isUpload;//是否已经推送,0 未推送，1已推送，-1推送失败
	
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	@Column
	public String getUppath() {
		return uppath;
	}
	public void setUppath(String uppath) {
		this.uppath = uppath;
	}
	@Column
	public String getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

}
