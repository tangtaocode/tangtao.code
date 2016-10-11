package net.risesoft.beans.civilbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MSXM_ATTACHMENT")
public class CivilUpFile implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1802377161490174293L;
	private String attachmentguid;  //guid
	private String xmguid;  //项目guid
	private String file_name;  //附件名称
	private String type;  //类型：0申报提交材料,X信息发布附件
	private String windowspath;  //WINDOWS路径
	private String linuxpath;  //LINUX路径
	private Date createtime;  //上传时间
	private String upuserguid;  //上传用户
	private String cllxguid;  //材料类型GUID
	@Id
	public String getAttachmentguid() {
		return attachmentguid;
	}
	public void setAttachmentguid(String attachmentguid) {
		this.attachmentguid = attachmentguid;
	}
	@Column
	public String getXmguid() {
		return xmguid;
	}
	public void setXmguid(String xmguid) {
		this.xmguid = xmguid;
	}
	@Column
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column
	public String getUpuserguid() {
		return upuserguid;
	}
	public void setUpuserguid(String upuserguid) {
		this.upuserguid = upuserguid;
	}
	@Column
	public String getCllxguid() {
		return cllxguid;
	}
	public void setCllxguid(String cllxguid) {
		this.cllxguid = cllxguid;
	}

}
