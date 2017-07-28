/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffdataXtDel.java
 * @Package Name: net.risesoft.approve.entity.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年4月1日 上午11:10:15
 */
package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @ClassName: StuffdataXtDel.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年4月1日 上午11:10:15
 * @version 
 * @since JDK 1.7
 */
@Entity
@Table(name = "SS_STUFFDATAXT_DEL")
public class StuffdataXtDel implements Serializable {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	
	private String guid;//主键
	private String stuffdataguid;//关联材料数据主键
	private String filename;//附件名
	private String rootpath;//路径
	private String wssbclguid;//
	private String tableindex;//从1开始，记录材料顺序
	private String linuxpath;//Linux路径
	private String uper;//上传者
	private String upertype;//上传者类型：1外网用户、2审批工作者
	private Date uptime;//上传时间
	private String operater;//删除操作人
	private Date deltime;//删除时间
	
	public StuffdataXtDel() {
		
	}
	
	public StuffdataXtDel(StuffdataXt xt, String person, Date time) {
		this.guid = xt.getGuid();
		this.stuffdataguid = xt.getStuffdataguid();
		this.filename = xt.getFilename();
		this.rootpath = xt.getRootpath();
		this.wssbclguid = xt.getWssbclguid();
		this.tableindex = xt.getTableindex();
		this.linuxpath = xt.getLinuxpath();
		this.uper = xt.getUper();
		this.upertype = xt.getUpertype();
		this.uptime = xt.getUptime();
		this.operater = person;
		this.deltime = time;
	}
	
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Column
	public String getStuffdataguid() {
		return stuffdataguid;
	}
	public void setStuffdataguid(String stuffdataguid) {
		this.stuffdataguid = stuffdataguid;
	}
	
	@Column
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Column
	public String getRootpath() {
		return rootpath;
	}
	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}
	
	@Column
	public String getWssbclguid() {
		return wssbclguid;
	}
	public void setWssbclguid(String wssbclguid) {
		this.wssbclguid = wssbclguid;
	}
	
	@Column
	public String getTableindex() {
		return tableindex;
	}
	public void setTableindex(String tableindex) {
		this.tableindex = tableindex;
	}
	
	@Column
	public String getLinuxpath() {
		return linuxpath;
	}
	public void setLinuxpath(String linuxpath) {
		this.linuxpath = linuxpath;
	}
	
	@Column
	public String getUper() {
		return uper;
	}
	public void setUper(String uper) {
		this.uper = uper;
	}
	
	@Column
	public String getUpertype() {
		return upertype;
	}
	public void setUpertype(String upertype) {
		this.upertype = upertype;
	}
	
	@Column
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	@Column
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	@Column
	public Date getDeltime() {
		return deltime;
	}
	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

}

