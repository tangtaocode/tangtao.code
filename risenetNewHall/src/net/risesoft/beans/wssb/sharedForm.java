/**
 * @Project Name:risenetNewHall
 * @File Name: sharedForm.java
 * @Package Name: net.risesoft.beans.wssb
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2014,RiseSoft  All Rights Reserved.
 * @date May 26, 2014 10:04:02 AM
 */
package net.risesoft.beans.wssb;

import java.io.Serializable;
import java.sql.Date;

/**
 * @ClassName: sharedForm.java
 * @Description: TODO
 *
 * @author Administrator
 * @date May 26, 2014 10:04:02 AM
 * @version 
 * @since JDK 1.6
 */
public class sharedForm implements Serializable{

	private String guid;  
	private Date fromdata;  
	private String fromdataname;  
	private Date todata;  
	private String todataname;  
	private String sharedname;  
	private Date sharedfiled;  
	private String sharedtype;  
	private String tempid;
	private Date createdate;
	private String status;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Date getFromdata() {
		return fromdata;
	}
	public void setFromdata(Date fromdata) {
		this.fromdata = fromdata;
	}
	public String getFromdataname() {
		return fromdataname;
	}
	public void setFromdataname(String fromdataname) {
		this.fromdataname = fromdataname;
	}
	public Date getTodata() {
		return todata;
	}
	public void setTodata(Date todata) {
		this.todata = todata;
	}
	public String getTodataname() {
		return todataname;
	}
	public void setTodataname(String todataname) {
		this.todataname = todataname;
	}
	public String getSharedname() {
		return sharedname;
	}
	public void setSharedname(String sharedname) {
		this.sharedname = sharedname;
	}
	public Date getSharedfiled() {
		return sharedfiled;
	}
	public void setSharedfiled(Date sharedfiled) {
		this.sharedfiled = sharedfiled;
	}
	public String getSharedtype() {
		return sharedtype;
	}
	public void setSharedtype(String sharedtype) {
		this.sharedtype = sharedtype;
	}
	public String getTempid() {
		return tempid;
	}
	public void setTempid(String tempid) {
		this.tempid = tempid;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
