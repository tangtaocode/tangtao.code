package net.risesoft.beans.onlineservice;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
/**
 * 
  * @ClassName: ApproveItemTabTemp
  * @Description: 事项办事指南中表格下载
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:02:51 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_ATTACHMENT")
public class ApproveItemTabTemp implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 5629213281147527597L;
	private String attachmentguid;  //GUID
	private String itemid;  //事项GUID
	private String file_name;  //附件名称
	private byte[] attachment;  //附件
	private String approveitemname;  //审批事项名称
	private String tabletype;  //类型

	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	@Id
	public String getAttachmentguid() {
		return attachmentguid;
	}
	public void setAttachmentguid(String attachmentguid) {
		this.attachmentguid = attachmentguid;
	}
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name = "attachment", columnDefinition = "BLOB",nullable=true) 
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	@Column
	public String getApproveitemname() {
		return approveitemname;
	}
	public void setApproveitemname(String approveitemname) {
		this.approveitemname = approveitemname;
	}
	@Column
	public String getTabletype() {
		return tabletype;
	}
	public void setTabletype(String tabletype) {
		this.tabletype = tabletype;
	}

}
