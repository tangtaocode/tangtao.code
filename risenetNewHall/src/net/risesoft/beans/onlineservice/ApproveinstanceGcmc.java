package net.risesoft.beans.onlineservice;

import java.io.Serializable;

public class ApproveinstanceGcmc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8135024270621197799L;
	
	private String guid;
	private String gcmc;
	private String xmmc;
	private String gcbh;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getGcmc() {
		return gcmc;
	}
	public void setGcmc(String gcmc) {
		this.gcmc = gcmc;
	}
	public String getXmmc() {
		return xmmc;
	}
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	public String getGcbh() {
		return gcbh;
	}
	public void setGcbh(String gcbh) {
		this.gcbh = gcbh;
	}
}
