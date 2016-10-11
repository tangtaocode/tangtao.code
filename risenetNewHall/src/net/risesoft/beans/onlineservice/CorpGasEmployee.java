package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CORP_GAS_EMPLOYEE")
public class CorpGasEmployee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -236749334094413208L;
	private String guid;//场站信息数量
	private String name;//场站名称
	private String position;//场站地址
	private String pro_quanlification;//1坐标
	private String id_no;//1坐标
	private String social_security_no;//2坐标
	private String pro_qua_type;//2坐标
	private String certification_no;//3坐标
	private String work_type;//3坐标
	private String employee_type;//4坐标
	private String creater;//4坐标
	private String created_time;//自有房屋产权号
	private String security_exam;//租用租凭合同号
	private String major;//开工日期
	private String job;//竣工日期
	private String spe_orp_cert_no;//消防验收意见书编号
	private String form_no;//供气能力
	private String sb_guid;//设计单位
	
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Column
	public String getPro_quanlification() {
		return pro_quanlification;
	}
	public void setPro_quanlification(String pro_quanlification) {
		this.pro_quanlification = pro_quanlification;
	}
	@Column
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	@Column
	public String getSocial_security_no() {
		return social_security_no;
	}
	public void setSocial_security_no(String social_security_no) {
		this.social_security_no = social_security_no;
	}
	@Column
	public String getPro_qua_type() {
		return pro_qua_type;
	}
	public void setPro_qua_type(String pro_qua_type) {
		this.pro_qua_type = pro_qua_type;
	}
	@Column
	public String getCertification_no() {
		return certification_no;
	}
	public void setCertification_no(String certification_no) {
		this.certification_no = certification_no;
	}
	@Column
	public String getWork_type() {
		return work_type;
	}
	public void setWork_type(String work_type) {
		this.work_type = work_type;
	}
	@Column
	public String getEmployee_type() {
		return employee_type;
	}
	public void setEmployee_type(String employee_type) {
		this.employee_type = employee_type;
	}
	@Column
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	@Column
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	@Column
	public String getSecurity_exam() {
		return security_exam;
	}
	public void setSecurity_exam(String security_exam) {
		this.security_exam = security_exam;
	}
	@Column
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	@Column
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	@Column
	public String getSpe_orp_cert_no() {
		return spe_orp_cert_no;
	}
	public void setSpe_orp_cert_no(String spe_orp_cert_no) {
		this.spe_orp_cert_no = spe_orp_cert_no;
	}
	@Column
	public String getSb_guid() {
		return sb_guid;
	}
	public void setSb_guid(String sbGuid) {
		sb_guid = sbGuid;
	}
	@Column
	public String getForm_no() {
		return form_no;
	}
	public void setForm_no(String formNo) {
		form_no = formNo;
	}	
}
