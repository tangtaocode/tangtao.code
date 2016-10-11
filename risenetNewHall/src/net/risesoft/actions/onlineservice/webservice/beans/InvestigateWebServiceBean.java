package net.risesoft.actions.onlineservice.webservice.beans;

import java.io.Serializable;

/**
 * @description 审查合格书webServices接口访问返回值
 * @author HJL
 * @date 2013-10-08
 */
public class InvestigateWebServiceBean implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3724361755315030315L;
	
	private String queryresult;//查询状态 0：无结果，1：成功返回，2：失败
	private String bacode;//审查合格书编号
	private String prjname;//工程名称
	private String prjadd;//工程地址
	private String prjtype;//工程类别
	private String prjlevel;//工程等级
	private String prjscale;//工程规模
	private String jsdw;//建设单位
	private String kcdw;//勘察单位
	private String sjdw;//设计单位
	private String scdw;//审查机构
	private String errorinfo;//服务端查询出错描述
	
	public String getQueryresult() {
		return queryresult;
	}
	public void setQueryresult(String queryresult) {
		this.queryresult = queryresult;
	}
	public String getBacode() {
		return bacode;
	}
	public void setBacode(String bacode) {
		this.bacode = bacode;
	}
	public String getPrjname() {
		return prjname;
	}
	public void setPrjname(String prjname) {
		this.prjname = prjname;
	}
	public String getPrjadd() {
		return prjadd;
	}
	public void setPrjadd(String prjadd) {
		this.prjadd = prjadd;
	}
	public String getPrjtype() {
		return prjtype;
	}
	public void setPrjtype(String prjtype) {
		this.prjtype = prjtype;
	}
	public String getPrjlevel() {
		return prjlevel;
	}
	public void setPrjlevel(String prjlevel) {
		this.prjlevel = prjlevel;
	}
	public String getPrjscale() {
		return prjscale;
	}
	public void setPrjscale(String prjscale) {
		this.prjscale = prjscale;
	}
	public String getJsdw() {
		return jsdw;
	}
	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}
	public String getKcdw() {
		return kcdw;
	}
	public void setKcdw(String kcdw) {
		this.kcdw = kcdw;
	}
	public String getSjdw() {
		return sjdw;
	}
	public void setSjdw(String sjdw) {
		this.sjdw = sjdw;
	}
	public String getScdw() {
		return scdw;
	}
	public void setScdw(String scdw) {
		this.scdw = scdw;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getErrorinfo() {
		return errorinfo;
	}
	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}
	
}
