package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @desctiption 网上申报证照信息
 * @author HJL
 *
 */
@Entity
@Table(name="SPM_WSSBCL_DOCTYPE")
public class WebApplyFileDoctype implements Serializable{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1346204771555427535L;
	
	private String guid;  //主键
	private String xmgguid;  //申报项目GUID
	private String clguid;  //材料GUID
	private String zzguid;  //证件GUID
	private String cardid;  //证件编号
	private Date createdate;  //创建时间
	private String createuser;  //创建人
	private String zzname;  //证件名称
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getXmgguid() {
		return xmgguid;
	}
	public void setXmgguid(String xmgguid) {
		this.xmgguid = xmgguid;
	}
	@Column
	public String getClguid() {
		return clguid;
	}
	public void setClguid(String clguid) {
		this.clguid = clguid;
	}
	@Column
	public String getZzguid() {
		return zzguid;
	}
	public void setZzguid(String zzguid) {
		this.zzguid = zzguid;
	}
	@Column
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	@Column
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	@Column
	public String getZzname() {
		return zzname;
	}
	public void setZzname(String zzname) {
		this.zzname = zzname;
	}


}
