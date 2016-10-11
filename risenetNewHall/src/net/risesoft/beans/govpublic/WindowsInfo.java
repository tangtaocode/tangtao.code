package net.risesoft.beans.govpublic;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: WindowsInfo
  * @Description: 窗口信息
  * @author Comsys-zhangkun
  * @date Jun 22, 2013 7:37:33 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_WINDOW")
public class WindowsInfo implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -6923645636765669928L;
	private String guid;  //主键GUID
	private String windowcode;  //窗口代码
	private String name;  //窗口名称
	private String address;  //窗口地址
	private String phone;  //联系电话
	private String officehours;  //办公时间
	private String trafficguide;  //交通指引
	private String maptype;  //地图类型 	包括：1、地图上传；2、地图连接；
	private String maplinks;  //地图链接
	private String orgcode;  //所属机构
	private String mapname;  //地图图片名称
	private Date createtime;  //创建时间
	private String createperson;  //创建人
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getWindowcode() {
		return windowcode;
	}
	public void setWindowcode(String windowcode) {
		this.windowcode = windowcode;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column
	public String getOfficehours() {
		return officehours;
	}
	public void setOfficehours(String officehours) {
		this.officehours = officehours;
	}
	@Column
	public String getTrafficguide() {
		return trafficguide;
	}
	public void setTrafficguide(String trafficguide) {
		this.trafficguide = trafficguide;
	}
	@Column
	public String getMaptype() {
		return maptype;
	}
	public void setMaptype(String maptype) {
		this.maptype = maptype;
	}
	@Column
	public String getMaplinks() {
		return maplinks;
	}
	public void setMaplinks(String maplinks) {
		this.maplinks = maplinks;
	}
	@Column
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	@Column
	public String getMapname() {
		return mapname;
	}
	public void setMapname(String mapname) {
		this.mapname = mapname;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column
	public String getCreateperson() {
		return createperson;
	}
	public void setCreateperson(String createperson) {
		this.createperson = createperson;
	}
	

}
