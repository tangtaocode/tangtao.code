package net.risesoft.beans.civilbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MSXM_FUNDINGRECODE")
public class CivilFundingRecode implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 3513420642132801279L;
	private String xmguid;  //项目guid
	private Integer fundingmoney;  //拨款金额（元）
	private String payeeperson;  //收款人
	private String bankname;  //开户银行
	private String bankcode;  //银行帐号
	private String fundingperson;  //拨款人
	private Date fundingtime;  //拨款时间
	private String beizhu;  //备注
	private String status;  //拨款状态：已拨款、已签收
	private Date payeetime;  //签收时间
	private String creatperson;  //创建人
	private Date creattime;  //创建时间
	private String guid;  //拨款GUID
	@Column
	public String getXmguid() {
		return xmguid;
	}
	public void setXmguid(String xmguid) {
		this.xmguid = xmguid;
	}
	@Column
	public Integer getFundingmoney() {
		return fundingmoney;
	}
	public void setFundingmoney(Integer fundingmoney) {
		this.fundingmoney = fundingmoney;
	}
	@Column
	public String getPayeeperson() {
		return payeeperson;
	}
	public void setPayeeperson(String payeeperson) {
		this.payeeperson = payeeperson;
	}
	@Column
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	@Column
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	@Column
	public String getFundingperson() {
		return fundingperson;
	}
	public void setFundingperson(String fundingperson) {
		this.fundingperson = fundingperson;
	}
	@Column
	public Date getFundingtime() {
		return fundingtime;
	}
	public void setFundingtime(Date fundingtime) {
		this.fundingtime = fundingtime;
	}
	@Column
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column
	public Date getPayeetime() {
		return payeetime;
	}
	public void setPayeetime(Date payeetime) {
		this.payeetime = payeetime;
	}
	@Column
	public String getCreatperson() {
		return creatperson;
	}
	public void setCreatperson(String creatperson) {
		this.creatperson = creatperson;
	}
	@Column
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}

}
