package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 外网申报材料表
 * @author Administrator
 *
 */
@Entity
@Table(name="SPM_WSSBCL")
public class SPMwssbcl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String guid;					//GUID CHAR(38) NOT NULL, 主键
	private String workflowinstanceguid;	//WORKFLOWINSTANCE_GUID	CHAR(38) NOT NULL,工作流实例ID
	private String approveitemgguid;		//APPROVEITEMGUID CHAR(38) NOT NULL,事项ID
	private String declareannexguid;		//DECLAREANNEXGUID CHAR(38) NOT NULL,材料ID
	private String filename;				//FILENAME VARCHAR2(255),文件名称
	private String rootpath;				//ROOTPATH VARCHAR2(2000),文件保存路径
	private Date updatetime;				//UPDATETIME DATE(7),更新日期
	private String linuxpath;				//LINUXPATH VARCHAR2(2000),linux文件保存路径
	private String upuser;					//UPUSER CHAR(38),上传用户ID
	private String declareannextypeguid;	//DECLAREANNEXTYPEGUID VARCHAR2(200),材料分类ID
	private String stuffdataxtguid;			//STUFFDATAXTGUID VARCHAR2(38),关联材料共享数据细表
	private Integer tableindex;				//TABLEINDEX NUMBER(),从1开始记录材料的顺序
	private String type;//材料来源分类：0普通材料、1证照、2共享材料
	
	@Id
	@Column(name="GUID",length=38,nullable=false)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="assigned")
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Column(name="WORKFLOWINSTANCE_GUID",length=38,nullable=false)
	public String getWorkflowinstanceguid() {
		return workflowinstanceguid;
	}
	public void setWorkflowinstanceguid(String workflowinstanceguid) {
		this.workflowinstanceguid = workflowinstanceguid;
	}
	
	@Column(name="APPROVEITEMGUID",length=38,nullable=false)
	public String getApproveitemgguid() {
		return approveitemgguid;
	}
	public void setApproveitemgguid(String approveitemgguid) {
		this.approveitemgguid = approveitemgguid;
	}
	
	@Column(name="DECLAREANNEXGUID",length=38,nullable=false)
	public String getDeclareannexguid() {
		return declareannexguid;
	}
	public void setDeclareannexguid(String declareannexguid) {
		this.declareannexguid = declareannexguid;
	}
	
	@Column(name="FILENAME",length=255)
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	@Column(name="ROOTPATH",length=2000)
	public String getRootpath() {
		return rootpath;
	}
	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}
	
	
	@Column(name="UPDATETIME",length=7)
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
	@Column(name="LINUXPATH",length=2000)
	public String getLinuxpath() {
		return linuxpath;
	}
	public void setLinuxpath(String linuxpath) {
		this.linuxpath = linuxpath;
	}
	
	
	@Column(name="UPUSER",length=38)
	public String getUpuser() {
		return upuser;
	}
	public void setUpuser(String upuser) {
		this.upuser = upuser;
	}
	
	
	@Column(name="DECLAREANNEXTYPEGUID",length=2000)
	public String getDeclareannextypeguid() {
		return declareannextypeguid;
	}
	public void setDeclareannextypeguid(String declareannextypeguid) {
		this.declareannextypeguid = declareannextypeguid;
	}
	
	@Column(name="STUFFDATAXTGUID",length=38)
	public String getStuffdataxtguid() {
		return stuffdataxtguid;
	}
	public void setStuffdataxtguid(String stuffdataxtguid) {
		this.stuffdataxtguid = stuffdataxtguid;
	}
	
	
	@Column(name="TABLEINDEX")
	public Integer getTableindex() {
		return tableindex;
	}
	public void setTableindex(Integer tableindex) {
		this.tableindex = tableindex;
	}
	@Override
	public String toString() {
		return "SPMwssbcl [guid=" + guid + ", workflowinstanceguid="
				+ workflowinstanceguid + ", approveitemgguid="
				+ approveitemgguid + ", declareannexguid=" + declareannexguid
				+ ", filename=" + filename + ", rootpath=" + rootpath
				+ ", updatetime=" + updatetime + ", linuxpath=" + linuxpath
				+ ", upuser=" + upuser + ", declareannextypeguid="
				+ declareannextypeguid + ", stuffdataxtguid=" + stuffdataxtguid
				+ ", tableindex=" + tableindex + "]";
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
				
	
	
	
	 
}
