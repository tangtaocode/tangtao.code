package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: WebApplyUpFile
  * @Description: 网上申报附件
  * @author Comsys-zhangkun
  * @date Jun 19, 2013 7:43:44 PM
  *
 */
@Entity
@Table(name="SPM_WSSBCL")
public class WebApplyUpFile implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -1221950001643388968L;
	private String guid;  //主键
	private String workflowinstance_guid;  //SB_APPROVEINSTANCE表guid。受理后即为工作流实例guid
	private String approveitemguid;  //事项guid
	private String declareannexguid;  //材料guid
	private String filename;  //文件名称
	private String rootpath;  //windows文件保存路径
	private Date updatetime;  //更新日期
	private String linuxpath;  //linux文件保存路径
	private String upuser;//上传用户
	private String file_name;//文件名（系统自动生成）
	
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getWorkflowinstance_guid() {
		return workflowinstance_guid;
	}
	public void setWorkflowinstance_guid(String workflowinstance_guid) {
		this.workflowinstance_guid = workflowinstance_guid;
	}
	@Column
	public String getApproveitemguid() {
		return approveitemguid;
	}
	public void setApproveitemguid(String approveitemguid) {
		this.approveitemguid = approveitemguid;
	}
	@Column
	public String getDeclareannexguid() {
		return declareannexguid;
	}
	public void setDeclareannexguid(String declareannexguid) {
		this.declareannexguid = declareannexguid;
	}
	@Column
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Column
	public String getRootpath() {
		return rootpath;
	}
	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}
	@Column
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Column
	public String getLinuxpath() {
		return linuxpath;
	}
	public void setLinuxpath(String linuxpath) {
		this.linuxpath = linuxpath;
	}
	@Column
	public String getUpuser() {
		return upuser;
	}
	public void setUpuser(String upuser) {
		this.upuser = upuser;
	}
	@Column
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

}
