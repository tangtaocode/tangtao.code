package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="ZJFC_APPLICATION")
public class BizApplicationBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1557275624809164041L;
	private String guid;  //主键
	private String category_id;  //项目类别ID
	private String category;  //项目类别名称
	private String provision;  //细则条款
	private String pro_name;  //申请资助项目名称
	private String state;  //状态[提交之后默认为1](1处理中，2成功，3失败)
	private String pressor_state;  //1未提交，2已提交，20部门初审，30补齐补正，40不予受理，50不予扶持，60科室评审，70分管局领导，80局长，90分管区领导，100批准办结
	private Date createtime;  //创建时间
	private String createtuser;  //创建人员ID
	private String createtusername;  //创建人员姓名
	private Date updatetime;  //更新时间
	private String updateuser;  //更新人员ID
	private String updateusername;  //更新人员姓名
	private String sbztid;  //申报主体
	private Integer sqje;  //项目申请金额（万元）
	private String zgbmid;  //业务主管部门id
	private Date zzclsj;  //提交纸质材料时间
	private Integer sdje;  //审定会议最终审定金额(万元)
	private String slbh;  //受理编号
	private String bqremark;  //补齐材料说明
	private String isfinished;  //是否办结。0未办结，1办结
	private String enterprises_affirms;  //项目简介
	private String ispublic;  //是否公示，0未公示，1，已公示
	private String xmlxroot;  //大项 项目类型GUID
	private String sqsj;//业务动态显示
	private String according;//依据条款
	private Date zcsj;
	@Column
	public Date getZcsj() {
		return zcsj;
	}
	public void setZcsj(Date zcsj) {
		this.zcsj = zcsj;
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
	public String getProvision() {
		return provision;
	}
	public void setProvision(String provision) {
		this.provision = provision;
	}
	@Column
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	@Column
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column
	public String getPressor_state() {
		return pressor_state;
	}
	public void setPressor_state(String pressor_state) {
		this.pressor_state = pressor_state;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column
	public String getCreatetuser() {
		return createtuser;
	}
	public void setCreatetuser(String createtuser) {
		this.createtuser = createtuser;
	}
	@Column
	public String getCreatetusername() {
		return createtusername;
	}
	public void setCreatetusername(String createtusername) {
		this.createtusername = createtusername;
	}
	@Column
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Column
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	@Column
	public String getUpdateusername() {
		return updateusername;
	}
	public void setUpdateusername(String updateusername) {
		this.updateusername = updateusername;
	}
	@Column
	public String getSbztid() {
		return sbztid;
	}
	public void setSbztid(String sbztid) {
		this.sbztid = sbztid;
	}
	@Column
	public Integer getSqje() {
		return sqje;
	}
	public void setSqje(Integer sqje) {
		this.sqje = sqje;
	}
	@Column
	public String getZgbmid() {
		return zgbmid;
	}
	public void setZgbmid(String zgbmid) {
		this.zgbmid = zgbmid;
	}
	@Column
	public Date getZzclsj() {
		return zzclsj;
	}
	public void setZzclsj(Date zzclsj) {
		this.zzclsj = zzclsj;
	}
	@Column
	public Integer getSdje() {
		return sdje;
	}
	public void setSdje(Integer sdje) {
		this.sdje = sdje;
	}
	@Column
	public String getSlbh() {
		return slbh;
	}
	public void setSlbh(String slbh) {
		this.slbh = slbh;
	}
	@Column
	public String getBqremark() {
		return bqremark;
	}
	public void setBqremark(String bqremark) {
		this.bqremark = bqremark;
	}
	@Column
	public String getIsfinished() {
		return isfinished;
	}
	public void setIsfinished(String isfinished) {
		this.isfinished = isfinished;
	}
	@Column
	public String getEnterprises_affirms() {
		return enterprises_affirms;
	}
	public void setEnterprises_affirms(String enterprises_affirms) {
		this.enterprises_affirms = enterprises_affirms;
	}
	@Column
	public String getIspublic() {
		return ispublic;
	}
	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}
	@Column
	public String getXmlxroot() {
		return xmlxroot;
	}
	public void setXmlxroot(String xmlxroot) {
		this.xmlxroot = xmlxroot;
	}
	@Transient
	public String getSqsj() {
		return sqsj;
	}
	public void setSqsj(String sqsj) {
		this.sqsj = sqsj;
	}
	@Transient
	public String getAccording() {
		return according;
	}
	public void setAccording(String according) {
		this.according = according;
	}

}
