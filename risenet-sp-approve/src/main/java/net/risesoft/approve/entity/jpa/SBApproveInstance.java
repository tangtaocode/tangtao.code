package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 在线申办事项
 * @author Monarch
 *
 */
@Entity
@Table(name = "SB_APPROVEINSTANCE")
public class SBApproveInstance implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String guid;            //"GUID" VARCHAR2(38 BYTE) NOT NULL ,
	private String departmentname;  //"DEPARTMENTNAME" VARCHAR2(50 BYTE) NULL ,
	private String approveguid;		//"APPROVEGUID" VARCHAR2(38 BYTE) NULL ,
	private String formname;  		//"FORMNAME" VARCHAR2(200 BYTE) NULL ,
	private String status;  		//"STATUS" VARCHAR2(20 BYTE) NULL ,
	private String userid; 			//"USERID" VARCHAR2(50 BYTE) NULL ,  用户id
	private Date submittime;		//"SUBMITTIME" DATE DEFAULT sysdate  NOT NULL ,
	private Integer issubmit;		//"ISSUBMIT" NUMBER(3) NULL ,
	private Date feedbacktime;		//"FEEDBACKTIME" DATE NULL ,
	private String resultcontent;   //"RESULTCONTENT" CLOB NULL ,
	private Integer dataform; 		//"DATAFORM" NUMBER(3) DEFAULT 0  NULL ,
	private String endstatus; 		//"ENDSTATUS" VARCHAR2(20 BYTE) NULL ,
	private String deptguid;  		//"DEPTGUID" VARCHAR2(38 BYTE) NULL ,  社区或者科室ID
	private String declaresn; 		//"DECLARESN" CHAR(14 BYTE) NULL ,
	private String ckryid; 			//"CKRYID" VARCHAR2(38 BYTE) NULL ,
	private String feedback;		//"FEEDBACK" VARCHAR2(4000 BYTE) NULL ,
	private String yijids;			//"YTJIDS" VARCHAR2(4000 BYTE) NULL ,
	private String xbqids;			//"XBQIDS" VARCHAR2(4000 BYTE) NULL ,
	private String xbzids;			//"XBZIDS" VARCHAR2(4000 BYTE) NULL ,
	private String datastatus;		//"DATASTATUS" VARCHAR2(1 BYTE) DEFAULT 0  NULL ,
	private String hfstate;			//"HFSTATE" VARCHAR2(1 BYTE) DEFAULT 0  NULL ,
	private String bureauguid;		//"BUREAUGUID" VARCHAR2(38 BYTE) NULL ,
	private String sblsh;			//"SBLSH" VARCHAR2(50 BYTE) NULL 
	
	@Id
	@Column(name = "guid", length = 38, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "assigned")
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Column(name="DEPARTMENTNAME",length=50)
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	
	@Column(name="APPROVEGUID",length=38)
	public String getApproveguid() {
		return approveguid;
	}
	public void setApproveguid(String approveguid) {
		this.approveguid = approveguid;
	}
	
	@Column(name="FORMNAME",length=200)
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	
	@Column(name="STATUS",length=20)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="USERID",length=50)
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name="SUBMITTIME")
	public Date getSubmittime() {
		return submittime;
	}
	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}
	
	@Column(name="ISSUBMIT",length=3)
	public Integer getIssubmit() {
		return issubmit;
	}
	public void setIssubmit(Integer issubmit) {
		this.issubmit = issubmit;
	}
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name="FEEDBACKTIME")
	public Date getFeedbacktime() {
		return feedbacktime;
	}
	public void setFeedbacktime(Date feedbacktime) {
		this.feedbacktime = feedbacktime;
	}
	
	@Column(name="RESULTCONTENT",length=4000)
	public String getResultcontent() {
		return resultcontent;
	}
	public void setResultcontent(String resultcontent) {
		this.resultcontent = resultcontent;
	}
	
	@Column(name="DATAFORM",length=3)
	public Integer getDataform() {
		return dataform;
	}
	public void setDataform(Integer dataform) {
		this.dataform = dataform;
	}
	
	@Column(name="ENDSTATUS",length=20)
	public String getEndstatus() {
		return endstatus;
	}
	public void setEndstatus(String endstatus) {
		this.endstatus = endstatus;
	}
	
	@Column(name="DEPTGUID",length=38)
	public String getDeptguid() {
		return deptguid;
	}
	public void setDeptguid(String deptguid) {
		this.deptguid = deptguid;
	}
	
	@Column(name="DECLARESN",length=14)
	public String getDeclaresn() {
		return declaresn;
	}
	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}
	
	@Column(name="CKRYID",length=38)
	public String getCkryid() {
		return ckryid;
	}
	public void setCkryid(String ckryid) {
		this.ckryid = ckryid;
	}
	
	@Column(name="FEEDBACK",length=4000)
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	@Column(name="YTJIDS",length=4000)
	public String getYijids() {
		return yijids;
	}
	public void setYijids(String yijids) {
		this.yijids = yijids;
	}
	
	@Column(name="XBQIDS",length=4000)
	public String getXbqids() {
		return xbqids;
	}
	public void setXbqids(String xbqids) {
		this.xbqids = xbqids;
	}
	
	@Column(name="XBZIDS",length=4000)
	public String getXbzids() {
		return xbzids;
	}
	public void setXbzids(String xbzids) {
		this.xbzids = xbzids;
	}
	
	@Column(name="DATASTATUS",length=1)
	public String getDatastatus() {
		return datastatus;
	}
	public void setDatastatus(String datastatus) {
		this.datastatus = datastatus;
	}
	
	@Column(name="HFSTATE",length=1)
	public String getHfstate() {
		return hfstate;
	}
	public void setHfstate(String hfstate) {
		this.hfstate = hfstate;
	}
	
	@Column(name="BUREAUGUID",length=38)
	public String getBureauguid() {
		return bureauguid;
	}
	public void setBureauguid(String bureauguid) {
		this.bureauguid = bureauguid;
	}
	
	@Column(name="SBLSH",length=50)
	public String getSblsh() {
		return sblsh;
	}
	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
	}
	
	@Override
	public String toString() {
		return "SBApproveInstance [guid=" + guid + ", departmentname=" + departmentname + ", approveguid=" + approveguid
				+ ", formname=" + formname + ", status=" + status + ", userid=" + userid + ", submittime=" + submittime
				+ ", issubmit=" + issubmit + ", feedbacktime=" + feedbacktime + ", resultcontent=" + resultcontent
				+ ", dataform=" + dataform + ", endstatus=" + endstatus + ", deptguid=" + deptguid + ", declaresn="
				+ declaresn + ", ckryid=" + ckryid + ", feedback=" + feedback + ", yijids=" + yijids + ", xbqids="
				+ xbqids + ", xbzids=" + xbzids + ", datastatus=" + datastatus + ", hfstate=" + hfstate
				+ ", bureauguid=" + bureauguid + ", sblsh=" + sblsh + "]";
	}
	
	
}
