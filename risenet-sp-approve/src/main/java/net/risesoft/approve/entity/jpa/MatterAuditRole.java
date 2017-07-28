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


/**
 * 审批--事项审核批准角色
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="MATTER_AUDIT_ROLE")
public  class MatterAuditRole  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	 private String guid;					//	"personGuid" CHAR(38 BYTE) NOT NULL ,			主键guid
     private String personGuid;				//	"personGuid" CHAR(38 BYTE) NOT NULL ,			人员GUID
     private String personName;				//	"personName" VARCHAR2(50 BYTE) NULL ,			人员姓名
     private String bureauGuid;				//	"bureauGuid" VARCHAR2(50 BYTE) NULL ,			委办局Guid
     private String bureauName;				//	"bureauGuid" VARCHAR2(50 BYTE) NULL ,			委办局名称
     private String approveItemGuid;		//	"approveitemguid" VARCHAR2(100 BYTE) NOT NULL ,	事项guid
     private String approveItemName;		//	"approveItemName" VARCHAR2(100 BYTE) NULL ,	          事项名称
     private Date  createTime;				//	"createTime" Date(100 BYTE) NULL ,	                              创建时间
     private String  authorGuid;			//	"authorGuid" Date(100 BYTE) NULL ,	                              授权人guid
     private String  authorName;			//	"authorName" Date(100 BYTE) NULL ,	                              授权人姓名

   
    // Property accessors
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
   
    
    @Column(name="personGuid", length=50)
    public String getPersonGuid() {
  		return personGuid;
  	}

  	public void setPersonGuid(String personGuid) {
  		this.personGuid = personGuid;
  	}
    
    @Column(name="personName",length=50)
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name="bureauGuid", length=50)
	public String getBureauGuid() {
		return bureauGuid;
	}

	public void setBureauGuid(String bureauGuid) {
		this.bureauGuid = bureauGuid;
	}
	
	@Column(name="bureauName", length=50)
	public String getBureauName() {
		return bureauName;
	}

	public void setBureauName(String bureauName) {
		this.bureauName = bureauName;
	}
	 
    @Column(name="approveItemGuid", length=50)
    public String getApproveItemGuid() {
		return approveItemGuid;
	}

	

	public void setApproveItemGuid(String approveItemGuid) {
		this.approveItemGuid = approveItemGuid;
	}

    @Column(name="approveItemName", length=50)
    public String getApproveItemName() {
		return approveItemName;
	}

	public void setApproveItemName(String approveItemName) {
		this.approveItemName = approveItemName;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="createTime", length=50)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="authorGuid", length=50)
	public String getAuthorGuid() {
		return authorGuid;
	}

	public void setAuthorGuid(String authorGuid) {
		this.authorGuid = authorGuid;
	}

	@Column(name="authorName", length=50)
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "MatterAuditRole [guid=" + guid + ", personGuid=" + personGuid
				+ ", personName=" + personName + ", bureauGuid=" + bureauGuid
				+ ", bureauName=" + bureauName + ", approveItemGuid="
				+ approveItemGuid + ", approveItemName=" + approveItemName
				+ ", createTime=" + createTime + ", authorGuid=" + authorGuid
				+ ", authorName=" + authorName + "]";
	}

}