package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * pdf文件，受理单，办结单
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="FF_PDFFILE")
public class PdfFile implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	 private String guid;					//	主键guid
	 private String processInstanceId;   //流程实例id
	 private String instanceGuid;	//审批主表（OFFICE_SPI_DECLAREINFO）业务数据guid
	 private byte[] content;	//文件内容
	 private String filePath;		//文件路径		
     private String fileName;		//文件名	
     private String fileType;		//文件类型，“shouLiDan”为受理单，“banJieDan”为办结单，“buQiBuZhengDan”为补齐补正单。
     private Date createDate;		//创建时间
     private Date modifyDate;		//修改时间
     private String userName;		//创建人姓名
     private String userId;			//创建人ID
     private String CertTxt;   		// 电子证书信息
 	 private String SignTxt;		// 文件签名结果
 	 private String DataTxt;		// 证书扩展项，唯一标识
	
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
	
	@Column(name="processInstanceId", length=100)
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	@Column(name="instanceGuid", length=100)
	public String getInstanceGuid() {
		return instanceGuid;
	}
	
	public void setInstanceGuid(String instanceGuid) {
		this.instanceGuid = instanceGuid;
	}
	
	@Lob
	@Column
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Column(name="filePath", length=200)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Column(name="fileName", length=200)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name="fileType", length=10)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="createDate", length=50)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="modifyDate", length=50)
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Column(name="userName", length=100)
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="userId", length=100)
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="CertTxt", length=2000)
	public String getCertTxt() {
		return CertTxt;
	}

	public void setCertTxt(String certTxt) {
		CertTxt = certTxt;
	}

	@Column(name="SignTxt", length=4000)
	public String getSignTxt() {
		return SignTxt;
	}

	public void setSignTxt(String signTxt) {
		SignTxt = signTxt;
	}

	@Column(name="DataTxt", length=1000)
	public String getDataTxt() {
		return DataTxt;
	}

	public void setDataTxt(String dataTxt) {
		DataTxt = dataTxt;
	}

	@Override
	public String toString() {
		return "PdfFile [guid=" + guid + ", processInstanceId="
				+ processInstanceId + ", instanceGuid=" + instanceGuid
				+ ", content=" + Arrays.toString(content) + ", filePath="
				+ filePath + ", fileName=" + fileName + ", fileType="
				+ fileType + ", createDate=" + createDate + ", modifyDate="
				+ modifyDate + ", userName=" + userName + ", userId=" + userId
				+ ", CertTxt=" + CertTxt + ", SignTxt=" + SignTxt
				+ ", DataTxt=" + DataTxt + ", getGuid()=" + getGuid()
				+ ", getProcessInstanceId()=" + getProcessInstanceId()
				+ ", getInstanceGuid()=" + getInstanceGuid()
				+ ", getContent()=" + Arrays.toString(getContent())
				+ ", getFilePath()=" + getFilePath() + ", getFileName()="
				+ getFileName() + ", getFileType()=" + getFileType()
				+ ", getCreateDate()=" + getCreateDate() + ", getModifyDate()="
				+ getModifyDate() + ", getUserName()=" + getUserName()
				+ ", getUserId()=" + getUserId() + ", getCertTxt()="
				+ getCertTxt() + ", getSignTxt()=" + getSignTxt()
				+ ", getDataTxt()=" + getDataTxt() + "]";
	}

	
}