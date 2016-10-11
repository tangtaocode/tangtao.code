package net.risesoft.beans.onlineservice;

import java.io.Serializable;


/**
 * @办事信息
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */

public class BusinessInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5845286830162839049L;
	
	private String yxtywlsh; //业务流水号
	private String sljgmc;//受理单位
	private String spsxmc;//审批事项名称
	private String sqdwhsqrxm;//申请单位或申请人
	private String slsj;//受理时间
	private String blzt;//办理状态
	private String cardId;
	private String approveitemguid;
	private String employeedeptname;
	private String isfinished;
	private String guid;
	private String approveguid;
	private String sblsh;//市里统一流水号

	public String getApproveguid() {
		return approveguid;
	}
	public void setApproveguid(String approveguid) {
		this.approveguid = approveguid;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getIsfinished() {
		return isfinished;
	}
	public void setIsfinished(String isfinished) {
		this.isfinished = isfinished;
	}
	public String getEmployeedeptname() {
		return employeedeptname;
	}
	public void setEmployeedeptname(String employeedeptname) {
		this.employeedeptname = employeedeptname;
	}
	public String getApproveitemguid() {
		return approveitemguid;
	}
	public void setApproveitemguid(String approveitemguid) {
		this.approveitemguid = approveitemguid;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getYxtywlsh() {
		return yxtywlsh;
	}
	public void setYxtywlsh(String yxtywlsh) {
		this.yxtywlsh = yxtywlsh;
	}
	public String getSljgmc() {
		return sljgmc;
	}
	public void setSljgmc(String sljgmc) {
		this.sljgmc = sljgmc;
	}
	public String getSpsxmc() {
		return spsxmc;
	}
	public void setSpsxmc(String spsxmc) {
		this.spsxmc = spsxmc;
	}
	public String getSqdwhsqrxm() {
		return sqdwhsqrxm;
	}
	public void setSqdwhsqrxm(String sqdwhsqrxm) {
		this.sqdwhsqrxm = sqdwhsqrxm;
	}
	public String getSlsj() {
		return slsj;
	}
	public void setSlsj(String slsj) {
		this.slsj = slsj;
	}
	public String getBlzt() {
		return blzt;
	}
	public void setBlzt(String blzt) {
		this.blzt = blzt;
	}
	public String getSblsh() {
		return sblsh;
	}
	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
	}
}
