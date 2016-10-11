package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
  * @ClassName: Approveinstance
  * @Description: 网上申报
  * @author Comsys-zhangkun
  * @date Jun 19, 2013 7:43:32 PM
  *
 */
@Entity
@Table(name="SB_APPROVEINSTANCE")
public class Approveinstance implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 2823448570778015934L;
	private String guid;  //主键
	private String approveguid;  //事项ID
	private String status;  //办理状态
	private String userid;  //该字段执行sb_approveinstanceupdate触发器动作。新增一次，以后不能更改此字段。因为如果窗口人员修改改成窗口人员id可能有问题。
	private Date submittime;  //提交时间
	private Integer issubmit;  //提交状态
	private String deptguid;  //社区或科室GUID
	private String declaresn;  //受理编号
	private String sjtybh;//市局统一编号
	private String feedback;  //
	private String ytjids;  //已提交
	private String xbqids;  //需补齐
	private String xbzids;  //需补正（用于回复）
	private String hfstate;  //0未回复，1已回复
	private String bureauguid;	//局或街道GUID
	private String formname;//
	private Integer dataform;
	private String typeguids;//材料分类guid
	private List<GuideFile> fileList;
	
	/**
	 * 外网申报标识  
	 * add by Jon
	 */
	private String dataFlag;       
	
	private String userMobile;    
	
	private String lxrName;  
	

	private String initstr;
	
	private String gcmc;
	
	private String xmmc;
	private String xmbh;
	private String gcbh;
	private String jsdw;
	
	
	@Transient
	public List<GuideFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<GuideFile> fileList) {
		this.fileList = fileList;
	}
	@Column
	public Integer getDataform() {
		return dataform;
	}
	public void setDataform(Integer dataform) {
		this.dataform = dataform;
	}
	@Column
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getApproveguid() {
		return approveguid;
	}
	public void setApproveguid(String approveguid) {
		this.approveguid = approveguid;
	}
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Column
	public Date getSubmittime() {
		return submittime;
	}
	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}
	@Column
	public Integer getIssubmit() {
		return issubmit;
	}
	public void setIssubmit(Integer issubmit) {
		this.issubmit = issubmit;
	}
	@Column
	public String getDeptguid() {
		return deptguid;
	}
	public void setDeptguid(String deptguid) {
		this.deptguid = deptguid;
	}
	@Column
	public String getDeclaresn() {
		return declaresn;
	}
	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}
	@Column
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	@Column
	public String getYtjids() {
		return ytjids;
	}
	public void setYtjids(String ytjids) {
		this.ytjids = ytjids;
	}
	@Column
	public String getXbqids() {
		return xbqids;
	}
	public void setXbqids(String xbqids) {
		this.xbqids = xbqids;
	}
	@Column
	public String getXbzids() {
		return xbzids;
	}
	public void setXbzids(String xbzids) {
		this.xbzids = xbzids;
	}
	@Column
	public String getHfstate() {
		return hfstate;
	}
	public void setHfstate(String hfstate) {
		this.hfstate = hfstate;
	}
	@Column
	public String getBureauguid() {
		return bureauguid;
	}
	public void setBureauguid(String bureauguid) {
		this.bureauguid = bureauguid;
	}
	@Column
	public String getTypeguids() {
		return typeguids;
	}
	public void setTypeguids(String typeguids) {
		this.typeguids = typeguids;
	}
	@Column
	public String getSjtybh() {
		return sjtybh;
	}
	public void setSjtybh(String sjtybh) {
		this.sjtybh = sjtybh;
	}
	@Column
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}
	@Transient
	public String getInitstr() {
		return initstr;
	}
	public void setInitstr(String initstr) {
		this.initstr = initstr;
	}
	@Column
	public String getGcmc() {
		return gcmc;
	}
	public void setGcmc(String gcmc) {
		this.gcmc = gcmc;
	}
	@Column
	public String getXmmc() {
		return xmmc;
	}
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	@Column
	public String getGcbh() {
		return gcbh;
	}
	public void setGcbh(String gcbh) {
		this.gcbh = gcbh;
	}
	@Column
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	@Column
	public String getLxrName() {
		return lxrName;
	}
	public void setLxrName(String lxrName) {
		this.lxrName = lxrName;
	}
	@Column
	public String getJsdw() {
		return jsdw;
	}
	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}
	@Column
	public String getXmbh() {
		return xmbh;
	}
	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}
	
}
