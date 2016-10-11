package net.risesoft.beans.civilbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="MSXM_APPLICATION")
public class CivilApplicationBean implements Serializable{
	
	private static final long serialVersionUID = -919129280784772777L;
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private String xmguid;  //项目guid
	private String xmname;  //项目名称
	private String fctype;  //扶持方式1购买服务，2配套扶持
	private Integer fcmoney;  //扶持金额
	private String projectdes;  //项目简介
	private String xmlxguid;  //项目类型GUID
	private String xmlxname;  //项目类型名称
	private String pressor_state;  //项目状态：参照数据字典【民生创新-项目状态】
	private String sbztid;  //申报主体
	private Date zzclsj;  //提交纸质材料时间
	private String slbh;  //受理编号
	private String bqremark;  //补齐材料说明
	private Date dzbqtime;  //电子文档补齐时间，用于计算是否补齐超时。一次补齐。
	private String isfinished;  //是否办结。0未办结，1办结
	private Date finishtime;  //办结时间
	private String ispublic;  //是否公示，0未公示，1，公示中，2公示结束
	private Date createtime;  //创建时间
	private String createtuser;  //创建人员ID
	private String createtusername;  //创建人员姓名
	private Date updatetime;  //更新时间
	private String updateuser;  //更新人员ID
	private String updateusername;  //更新人员姓名
	private Date publictime;  //项目公示时间
	private String fzr;  //负责人
	private String address;  //联系地址
	private String phone;  //联系方式
	private Date sltime;  //受理时间
	private String contactmobile;  //联系手机
	private String sszt;  //实施主体
	private Date sskssj;  //实施开始时间
	private Date ssfbsj;  //实施发布时间
	private String ssstate;  //实施状态：0未实施，1实施中，2实施结束
	private Date ssjssj;  //实施结束时间
	private Date sendczj;  //送财政局时间
	private Date publicendtime;  //项目公示结束时间
	private String createtimeStr;
	private String stateValue;
	private String isSign;
	@Transient
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	@Transient
	public String getStateValue() {
		return stateValue;
	}
	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}
	@Transient
	public String getCreatetimeStr() {
		return createtimeStr;
	}
	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
	@Id
	public String getXmguid() {
		return xmguid;
	}
	public void setXmguid(String xmguid) {
		this.xmguid = xmguid;
	}
	@Column
	public String getXmname() {
		return xmname;
	}
	public void setXmname(String xmname) {
		this.xmname = xmname;
	}
	@Column
	public String getFctype() {
		return fctype;
	}
	public void setFctype(String fctype) {
		this.fctype = fctype;
	}
	@Column
	public Integer getFcmoney() {
		return fcmoney;
	}
	public void setFcmoney(Integer fcmoney) {
		this.fcmoney = fcmoney;
	}
	@Column
	public String getProjectdes() {
		return projectdes;
	}
	public void setProjectdes(String projectdes) {
		this.projectdes = projectdes;
	}
	@Column
	public String getXmlxguid() {
		return xmlxguid;
	}
	public void setXmlxguid(String xmlxguid) {
		this.xmlxguid = xmlxguid;
	}
	@Column
	public String getXmlxname() {
		return xmlxname;
	}
	public void setXmlxname(String xmlxname) {
		this.xmlxname = xmlxname;
	}
	@Column
	public Date getDzbqtime() {
		return dzbqtime;
	}
	public void setDzbqtime(Date dzbqtime) {
		this.dzbqtime = dzbqtime;
	}
	@Column
	public Date getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}
	@Column
	public Date getPublictime() {
		return publictime;
	}
	public void setPublictime(Date publictime) {
		this.publictime = publictime;
	}
	@Column
	public String getFzr() {
		return fzr;
	}
	public void setFzr(String fzr) {
		this.fzr = fzr;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column
	public Date getSltime() {
		return sltime;
	}
	public void setSltime(Date sltime) {
		this.sltime = sltime;
	}
	@Column
	public String getContactmobile() {
		return contactmobile;
	}
	public void setContactmobile(String contactmobile) {
		this.contactmobile = contactmobile;
	}
	@Column
	public String getSszt() {
		return sszt;
	}
	public void setSszt(String sszt) {
		this.sszt = sszt;
	}
	@Column
	public Date getSskssj() {
		return sskssj;
	}
	public void setSskssj(Date sskssj) {
		this.sskssj = sskssj;
	}
	@Column
	public Date getSsfbsj() {
		return ssfbsj;
	}
	public void setSsfbsj(Date ssfbsj) {
		this.ssfbsj = ssfbsj;
	}
	@Column
	public String getSsstate() {
		return ssstate;
	}
	public void setSsstate(String ssstate) {
		this.ssstate = ssstate;
	}
	@Column
	public Date getSsjssj() {
		return ssjssj;
	}
	public void setSsjssj(Date ssjssj) {
		this.ssjssj = ssjssj;
	}
	@Column
	public Date getSendczj() {
		return sendczj;
	}
	public void setSendczj(Date sendczj) {
		this.sendczj = sendczj;
	}
	@Column
	public Date getPublicendtime() {
		return publicendtime;
	}
	public void setPublicendtime(Date publicendtime) {
		this.publicendtime = publicendtime;
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
	public Date getZzclsj() {
		return zzclsj;
	}
	public void setZzclsj(Date zzclsj) {
		this.zzclsj = zzclsj;
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
	public String getIspublic() {
		return ispublic;
	}
	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}
	

}
