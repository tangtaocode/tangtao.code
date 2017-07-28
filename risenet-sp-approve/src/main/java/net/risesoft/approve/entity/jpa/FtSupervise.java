package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

public class FtSupervise implements Serializable{
    /**
	 * 审批基础类  提供公共字段
	 */
	private static final long serialVersionUID = 1L;
	private String ywlsh;
	private String spsxbh;
	private String spsxzxbh;
	private String yxtywlsh;
	private String spsxmc;
	private String datasource;
	private String isexchangebsdt;
	private String serialnum;
	
	public String getYwlsh() {
		return ywlsh;
	}
	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}
	public String getSpsxbh() {
		return spsxbh;
	}
	public void setSpsxbh(String spsxbh) {
		this.spsxbh = spsxbh;
	}
	public String getSpsxzxbh() {
		return spsxzxbh;
	}
	public void setSpsxzxbh(String spsxzxbh) {
		this.spsxzxbh = spsxzxbh;
	}
	public String getYxtywlsh() {
		return yxtywlsh;
	}
	public void setYxtywlsh(String yxtywlsh) {
		this.yxtywlsh = yxtywlsh;
	}
	public String getSpsxmc() {
		return spsxmc;
	}
	public void setSpsxmc(String spsxmc) {
		this.spsxmc = spsxmc;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getIsexchangebsdt() {
		return isexchangebsdt;
	}
	public void setIsexchangebsdt(String isexchangebsdt) {
		this.isexchangebsdt = isexchangebsdt;
	}
	public String getSerialnum() {
		return serialnum;
	}
	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}

}
