package net.risesoft.actions.onlineservice.webservice.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 获取证照材料共享(深圳市民用建筑施工图设计文件抽查（备案）意见书)webServices接口访问返回值
 * @author HJL
 * @date 2013-10-11
 *
 */
public class LicenseWebServiceBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2052727628547969939L;
	
	private String zjmc;//证照名称
	private String zjbh;//证照编号
	private String app_info;//工程名称
	private String app_unit;//建设单位
	private Date bjsj;//发证时间
	private String zjyxqx;//证照有效期限
	
	
	
	public String getZjmc() {
		return zjmc;
	}
	public void setZjmc(String zjmc) {
		this.zjmc = zjmc;
	}
	public String getZjbh() {
		return zjbh;
	}
	public void setZjbh(String zjbh) {
		this.zjbh = zjbh;
	}
	public String getApp_info() {
		return app_info;
	}
	public void setApp_info(String app_info) {
		this.app_info = app_info;
	}
	public String getApp_unit() {
		return app_unit;
	}
	public void setApp_unit(String app_unit) {
		this.app_unit = app_unit;
	}
	public String getZjyxqx() {
		return zjyxqx;
	}
	public void setZjyxqx(String zjyxqx) {
		this.zjyxqx = zjyxqx;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Date getBjsj() {
		return bjsj;
	}
	public void setBjsj(Date bjsj) {
		this.bjsj = bjsj;
	}
	
}
