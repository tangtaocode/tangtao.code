package net.risesoft.beans.onlineservice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
  * @ClassName: TempSblsh
  * @Description: 网上申报
  * @author Jon
  *
 */
public class TempSblsh {
	private String guid;
	private String approveitemid;
	private Date curdate;
	private int curvalue;
	private String approveitemmsn;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getApproveitemid() {
		return approveitemid;
	}
	public void setApproveitemid(String approveitemid) {
		this.approveitemid = approveitemid;
	}
	public Date getCurdate() {
		return curdate;
	}
	public void setCurdate(Date curdate) {
		this.curdate = curdate;
	}
	public int getCurvalue() {
		return curvalue;
	}
	public void setCurvalue(int curvalue) {
		this.curvalue = curvalue;
	}
	public String getApproveitemmsn() {
		return approveitemmsn;
	}
	public void setApproveitemmsn(String approveitemmsn) {
		this.approveitemmsn = approveitemmsn;
	}
	
}
