package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CORP_GAS_STATION")
public class CorpGasStation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -236749334094413208L;
	private String czsize;//场站信息数量
	private String czname;//场站名称
	private String address;//场站地址
	private String coordinate1x;//1坐标
	private String coordinate1y;//1坐标
	private String coordinate2x;//2坐标
	private String coordinate2y;//2坐标
	private String coordinate3x;//3坐标
	private String coordinate3y;//3坐标
	private String coordinate4x;//4坐标
	private String coordinate4y;//4坐标
	private String propertyno;//自有房屋产权号
	private String rentalno;//租用租凭合同号
	private String startdate;//开工日期
	private String enddate;//竣工日期
	private String acceptanceno;//消防验收意见书编号
	private String supply;//供气能力
	private String designcorp;//设计单位
	private String constructioncorp;//施工单位
	private String gassource;//燃气来源
	private String secorg;//安评机构
	private String volume;//总容积
	private String brief;//场站简介
	private String install_type;//安装形式
	private String station_type;//场站类型   
	private String guid;//主键
	private String sb_guid;//
	private String form_no;
	private String no_type;
	
	private ArrayList<CorpGasStaeqm> eqmList = new ArrayList<CorpGasStaeqm>();
	
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column(name="")
	public String getCzsize() {
		return czsize;
	}
	public void setCzsize(String czsize) {
		this.czsize = czsize;
	}
	@Column
	public String getCzname() {
		return czname;
	}
	public void setCzname(String czname) {
		this.czname = czname;
	}
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column
	public String getCoordinate1x() {
		return coordinate1x;
	}
	public void setCoordinate1x(String coordinate1x) {
		this.coordinate1x = coordinate1x;
	}
	@Column
	public String getCoordinate1y() {
		return coordinate1y;
	}
	public void setCoordinate1y(String coordinate1y) {
		this.coordinate1y = coordinate1y;
	}
	@Column
	public String getCoordinate2x() {
		return coordinate2x;
	}
	public void setCoordinate2x(String coordinate2x) {
		this.coordinate2x = coordinate2x;
	}
	@Column
	public String getCoordinate2y() {
		return coordinate2y;
	}
	public void setCoordinate2y(String coordinate2y) {
		this.coordinate2y = coordinate2y;
	}
	@Column
	public String getCoordinate3x() {
		return coordinate3x;
	}
	public void setCoordinate3x(String coordinate3x) {
		this.coordinate3x = coordinate3x;
	}
	@Column
	public String getCoordinate3y() {
		return coordinate3y;
	}
	public void setCoordinate3y(String coordinate3y) {
		this.coordinate3y = coordinate3y;
	}
	@Column
	public String getCoordinate4x() {
		return coordinate4x;
	}
	public void setCoordinate4x(String coordinate4x) {
		this.coordinate4x = coordinate4x;
	}
	@Column
	public String getCoordinate4y() {
		return coordinate4y;
	}
	public void setCoordinate4y(String coordinate4y) {
		this.coordinate4y = coordinate4y;
	}
	@Column
	public String getPropertyno() {
		return propertyno;
	}
	public void setPropertyno(String propertyno) {
		this.propertyno = propertyno;
	}
	@Column
	public String getRentalno() {
		return rentalno;
	}
	public void setRentalno(String rentalno) {
		this.rentalno = rentalno;
	}
	@Column
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	@Column
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	@Column
	public String getAcceptanceno() {
		return acceptanceno;
	}
	public void setAcceptanceno(String acceptanceno) {
		this.acceptanceno = acceptanceno;
	}
	@Column
	public String getSupply() {
		return supply;
	}
	public void setSupply(String supply) {
		this.supply = supply;
	}
	@Column
	public String getDesigncorp() {
		return designcorp;
	}
	public void setDesigncorp(String designcorp) {
		this.designcorp = designcorp;
	}
	@Column
	public String getConstructioncorp() {
		return constructioncorp;
	}
	public void setConstructioncorp(String constructioncorp) {
		this.constructioncorp = constructioncorp;
	}
	@Column
	public String getGassource() {
		return gassource;
	}
	public void setGassource(String gassource) {
		this.gassource = gassource;
	}
	@Column
	public String getSecorg() {
		return secorg;
	}
	public void setSecorg(String secorg) {
		this.secorg = secorg;
	}
	@Column
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	@Column
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	@Column
	public String getInstall_type() {
		return install_type;
	}
	public void setInstall_type(String installType) {
		install_type = installType;
	}
	@Column
	public String getStation_type() {
		return station_type;
	}
	public void setStation_type(String stationType) {
		station_type = stationType;
	}
	@Column
	public ArrayList<CorpGasStaeqm> getEqmList() {
		return eqmList;
	}
	public void setEqmList(ArrayList<CorpGasStaeqm> eqmList) {
		this.eqmList = eqmList;
	}
	public String getSb_guid() {
		return sb_guid;
	}
	public void setSb_guid(String sbGuid) {
		sb_guid = sbGuid;
	}
	public String getForm_no() {
		return form_no;
	}
	public void setForm_no(String formNo) {
		form_no = formNo;
	}
	@Column
	public String getNo_type() {
		return no_type;
	}
	public void setNo_type(String noType) {
		no_type = noType;
	}	
}
