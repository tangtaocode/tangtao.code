package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="ZJFC_YJR")
public class BizIntroducerBean implements Serializable {
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -3531224160687965957L;
	private String guid;  //
	private String category_id;  //项目类别ID
	private String category;  //项目类别名称
	private String sbztid;  //申报主体
	private String yjrno;  //引荐人申请标识(不能重复)，引荐人编号
	private Date subtime;  //提交时间
	private String state;  //状态，1未提交，2提交待审批，3审批通过，4审批不通过
	private Date updatetime;  //更新时间
	private String response;  //回复
	private String provision;  //细则条款ID
	private String zgbmid;  //业务主管部门id
	private String xmlxroot;//很节点大项
	private String provisionInfo;
	private String stateStr;//状态value
	private String isSb;//是否已申报
	@Transient
	public String getIsSb() {
		return isSb;
	}
	public void setIsSb(String isSb) {
		this.isSb = isSb;
	}
	@Transient
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	@Transient
	public String getProvisionInfo() {
		return provisionInfo;
	}
	public void setProvisionInfo(String provisionInfo) {
		this.provisionInfo = provisionInfo;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	@Column
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Column
	public String getSbztid() {
		return sbztid;
	}
	public void setSbztid(String sbztid) {
		this.sbztid = sbztid;
	}
	@Column
	public String getYjrno() {
		return yjrno;
	}
	public void setYjrno(String yjrno) {
		this.yjrno = yjrno;
	}
	@Column
	public Date getSubtime() {
		return subtime;
	}
	public void setSubtime(Date subtime) {
		this.subtime = subtime;
	}
	@Column
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Column
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}

	@Column
	public String getProvision() {
		return provision;
	}
	public void setProvision(String provision) {
		this.provision = provision;
	}
	@Column
	public String getZgbmid() {
		return zgbmid;
	}
	public void setZgbmid(String zgbmid) {
		this.zgbmid = zgbmid;
	}
	@Column
	public String getXmlxroot() {
		return xmlxroot;
	}
	public void setXmlxroot(String xmlxroot) {
		this.xmlxroot = xmlxroot;
	}
	

}
