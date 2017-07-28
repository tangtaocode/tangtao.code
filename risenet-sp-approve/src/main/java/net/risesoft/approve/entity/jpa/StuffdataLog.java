/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffdataLog.java
 * @Package Name: net.risesoft.approve.entity.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月23日 下午2:55:34
 */
package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.risesoft.util.GUID;

/**
 * @ClassName: StuffdataLog.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年3月23日 下午2:55:34
 * @version 
 * @since JDK 1.7
 */
@Entity
@Table(name = "SS_STUFFDATALOG")
public class StuffdataLog implements Serializable {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	private String guid;//主键
	private String stuffdefineid;//材料定义类型主键
	private String stuffdataname;//材料数据名称，如张三的身份证
	private String ownertype;//所属主体类型，1个人，2企业，3机关事业单位
	private String ownerguid;//主体主键
	private String state;//材料数据状态，1待认证、2认证有效、3无效
	private Date limitbegin;//有效期开始日期
	private Date limitend;//有效期结束日期
	private String limitforever;//永久有效，1表示永久有效，0和空表示非永久有效
	private Date gxtime;//更新日期
	private Integer tabindex;//排序号
	private String certifyperson;//认证人
	
	private String zbguid;//原ss_stuffdata表主键
	private int lognum;//版本号
	private String remark;//认证为无效的原因
	
	public StuffdataLog() {
		
	}
	
	public StuffdataLog(Stuffdata stuff) {
		this.guid = new GUID().toString();
		this.stuffdefineid = stuff.getStuffdefineid();
		this.stuffdataname = stuff.getStuffdataname();
		this.ownertype = stuff.getOwnertype();
		this.ownerguid = stuff.getOwnerguid();
		this.state = stuff.getState();
		this.limitbegin = stuff.getLimitbegin();
		this.limitend = stuff.getLimitend();
		this.limitforever = stuff.getLimitforever();
		this.tabindex = stuff.getTabindex();
		this.remark = stuff.getRemark();
		this.zbguid = stuff.getGuid();
	}
	
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getStuffdefineid() {
		return stuffdefineid;
	}
	public void setStuffdefineid(String stuffdefineid) {
		this.stuffdefineid = stuffdefineid;
	}
	@Column
	public String getStuffdataname() {
		return stuffdataname;
	}
	public void setStuffdataname(String stuffdataname) {
		this.stuffdataname = stuffdataname;
	}
	@Column
	public String getOwnertype() {
		return ownertype;
	}
	public void setOwnertype(String ownertype) {
		this.ownertype = ownertype;
	}
	@Column
	public String getOwnerguid() {
		return ownerguid;
	}
	public void setOwnerguid(String ownerguid) {
		this.ownerguid = ownerguid;
	}
	@Column
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column
	public Date getLimitbegin() {
		return limitbegin;
	}
	public void setLimitbegin(Date limitbegin) {
		this.limitbegin = limitbegin;
	}
	@Column
	public Date getLimitend() {
		return limitend;
	}
	public void setLimitend(Date limitend) {
		this.limitend = limitend;
	}
	@Column
	public String getLimitforever() {
		return limitforever;
	}
	public void setLimitforever(String limitforever) {
		this.limitforever = limitforever;
	}
	@Column
	public Date getGxtime() {
		return gxtime;
	}
	public void setGxtime(Date gxtime) {
		this.gxtime = gxtime;
	}
	@Column
	public Integer getTabindex() {
		return tabindex;
	}
	public void setTabindex(Integer tabindex) {
		this.tabindex = tabindex;
	}
	@Column
	public String getCertifyperson() {
		return certifyperson;
	}
	public void setCertifyperson(String certifyperson) {
		this.certifyperson = certifyperson;
	}
	@Column
	public String getZbguid() {
		return zbguid;
	}
	public void setZbguid(String zbguid) {
		this.zbguid = zbguid;
	}
	@Column
	public int getLognum() {
		return lognum;
	}
	public void setLognum(int lognum) {
		this.lognum = lognum;
	}
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

