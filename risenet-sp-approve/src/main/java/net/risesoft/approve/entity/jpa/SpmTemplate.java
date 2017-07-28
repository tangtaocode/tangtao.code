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
 * 模板配置 entity
 */
@Entity
@Table(name="SPM_TEMPLATE")
public  class SpmTemplate  implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String templateGuid;
	private String templateName;
	private String filePath;
	private String approveItemName;
	private String approveItemGuid;
	private String templateType;
	private Date uploadDate;
	private String certificateName;//证照名称
	private String executeSql;
	
	
	@Id
   	@Column(name = "TEMPLATEGUID", length = 38, nullable = false)
   	@GeneratedValue(generator = "uuid")
   	@GenericGenerator(name = "uuid", strategy = "assigned") 
	public String getTemplateGuid() {
		return templateGuid;
	}

	public void setTemplateGuid(String templateGuid) {
		this.templateGuid = templateGuid;
	}

	@Column(name="TEMPLATENAME")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name="FILEPATH")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="APPROVEITEMNAME")
	public String getApproveItemName() {
		return approveItemName;
	}

	public void setApproveItemName(String approveItemName) {
		this.approveItemName = approveItemName;
	}

	@Column(name="APPROVEITEMGUID")
	public String getApproveItemGuid() {
		return approveItemGuid;
	}

	public void setApproveItemGuid(String approveItemGuid) {
		this.approveItemGuid = approveItemGuid;
	}

	@Column(name="TEMPLATETYPE")
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	@Temporal(TemporalType.DATE)
    @Column(name="UPLOADDATE", length=7)
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name="CERTIFICATENAME")
	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	
	@Column(name="EXECUTE_SQL")
	public String getExecuteSql(){
		return executeSql;
	}
	
	public void setExecuteSql(String executeSql){
		this.executeSql = executeSql;
	}

	@Override
	public String toString() {
		return "SpmTemplate [templateGuid=" + templateGuid + ", templateName="
				+ templateName + ", filePath=" + filePath
				+ ", approveItemName=" + approveItemName + ", approveItemGuid="
				+ approveItemGuid + ", templateType=" + templateType
				+ ", uploadDate=" + uploadDate + ", certificateName="
				+ certificateName + "]";
	}
}