package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="OFFICE_SPI_DECLAREINFO")//审批主表
public class OfficeSpiDeclareinfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "WORKFLOWINSTANCE_GUID", length = 38, nullable = false)
	private String guid;//主键
	
	@Column(name="processInstanceId",length=38)
	private String processInstanceId;//工作流流程实例Id
	
	@Column(name="declaresn",length=14,nullable=false)
	private String declaresn;//原系统业务流水号
	
	@Column(name="APPROVEITEMGUID",length=38)
	private String approveitemguid;//事项guid
	
	@Column(name="APPROVEITEMNAME",length=250)
	private String approveItemName;//事项名称
	
	@Column(name="XIANGMUMINGCHENG",length=250)
	private String xiangmumingcheng;//事项名称
	
	@Column(name="bureauguid",length=38)
	private String bureauguid;//受理部门编号
	
	@Column(name="EMPLOYEEGUID",length=38)
	private String employeeGuid;//受理人guid
	
	@Column(name="DECLARERTYPE",length=600)
	private String declareType;//申报类型
	
	@Column(name="DECLARERUNIT",length=500)
	private String declarerUnit;//申报单位
	
	@Column(name="DECLARERPERSON",length=500)
	private String declarerPerson;//申请人(单位)
	
	@Column(name="DECLARERFAX",length=38)
	private String declarerfax;//传真
	
	@Column(name="DECLARERTEL",length=38)
	private String declarerTel;//联系人电话
	
	@Column(name="DECLARERMOBILE",length=500)
	private String declarerMobile;//联系人手机
	
	@Column(name="DECLARERLXR",length=500)
	private String declarerlxr;//联系人姓名
	
	@Column(name="DECLARERLXRIDTYPE",length=500)
	private String declarerlxrIdtype;//联系人证件类型
	
	@Column(name="DECLARERLXRID",length=500)
	private String declarerlxrid;//联系人证件代码
	
	@Column(name="DECLAREDATETIME")
	private Date declareDateTime;//登记受理时间
	
	@Column(name="DATAFROMTYPE")
	private String dataFromtype;//提交方式
	
	@Column(name="DECLAREANNEXGUIDS2",length=2000)
	private String declareAnnexGuids2;//已提交材料
	
	@Column(name="BHGSBCL2",length=2000)
	private String bhgsbcl2;//需补正材料
	
	@Column(name="NOTDECLAREANNEXGUIDS2",length=2000)
	private String notDeclareAnnexGuids2;//需补齐材料
	
	@Column(name="REPLYTIME",length=2000)
	private String replytime;//回复时间
	
	@Column(name="REPLYSTATUS",length=2000)
	private String replystatus;//回复状态
	@Column(name="FEEDBACK",length=2000)
	private String feedback;//回复状态
	@Column(name="address",length=500)
	private String address;
	@Column(name="HANDLESTATUS",length=500)
	private String handleStatus;
	@Column(name="zhengjiandaima",length=250)
	private String zhengjiandaima;
	@Column(name="sblsh",length=250)
	private String sblsh;
	@Column(name="NSJYSL")
	private String nsjysl;//年设计用水量
	
	@Transient
	private String sqrguid;//申请人guid
	@Transient
	private String userType;//申请人类型
	
	
	
	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getNsjysl() {
		return nsjysl;
	}

	public void setNsjysl(String nsjysl) {
		this.nsjysl = nsjysl;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getDeclarerfax() {
		return declarerfax;
	}

	public void setDeclarerfax(String declarerfax) {
		this.declarerfax = declarerfax;
	}

	public String getReplytime() {
		return replytime;
	}

	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}

	public String getReplystatus() {
		return replystatus;
	}

	public void setReplystatus(String replystatus) {
		this.replystatus = replystatus;
	}

	public String getDeclarerlxr() {
		return declarerlxr;
	}

	public void setDeclarerlxr(String declarerlxr) {
		this.declarerlxr = declarerlxr;
	}

	public String getDeclarerlxrIdtype() {
		return declarerlxrIdtype;
	}

	public void setDeclarerlxrIdtype(String declarerlxrIdtype) {
		this.declarerlxrIdtype = declarerlxrIdtype;
	}

	public String getDeclarerlxrid() {
		return declarerlxrid;
	}

	public void setDeclarerlxrid(String declarerlxrid) {
		this.declarerlxrid = declarerlxrid;
	}

	public String getDataFromtype() {
		return dataFromtype;
	}

	public void setDataFromtype(String dataFromtype) {
		this.dataFromtype = dataFromtype;
	}

	public String getXiangmumingcheng() {
		return xiangmumingcheng;
	}

	public void setXiangmumingcheng(String xiangmumingcheng) {
		this.xiangmumingcheng = xiangmumingcheng;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDeclaresn() {
		return declaresn;
	}

	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}

	public String getApproveitemguid() {
		return approveitemguid;
	}

	public void setApproveitemguid(String approveitemguid) {
		this.approveitemguid = approveitemguid;
	}

	public String getApproveItemName() {
		return approveItemName;
	}

	public void setApproveItemName(String approveItemName) {
		this.approveItemName = approveItemName;
	}

	public String getBureauguid() {
		return bureauguid;
	}

	public void setBureauguid(String bureauguid) {
		this.bureauguid = bureauguid;
	}

	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	public String getDeclarerUnit() {
		return declarerUnit;
	}

	public void setDeclarerUnit(String declarerUnit) {
		this.declarerUnit = declarerUnit;
	}

	public String getDeclarerPerson() {
		return declarerPerson;
	}

	public void setDeclarerPerson(String declarerPerson) {
		this.declarerPerson = declarerPerson;
	}

	public String getDeclarerTel() {
		return declarerTel;
	}

	public void setDeclarerTel(String declarerTel) {
		this.declarerTel = declarerTel;
	}

	public String getDeclarerMobile() {
		return declarerMobile;
	}

	public void setDeclarerMobile(String declarerMobile) {
		this.declarerMobile = declarerMobile;
	}

	public Date getDeclareDateTime() {
		return declareDateTime;
	}

	public void setDeclareDateTime(Date declareDateTime) {
		this.declareDateTime = declareDateTime;
	}

	public String getDeclareAnnexGuids2() {
		return declareAnnexGuids2;
	}

	public void setDeclareAnnexGuids2(String declareAnnexGuids2) {
		this.declareAnnexGuids2 = declareAnnexGuids2;
	}

	public String getBhgsbcl2() {
		return bhgsbcl2;
	}

	public void setBhgsbcl2(String bhgsbcl2) {
		this.bhgsbcl2 = bhgsbcl2;
	}

	public String getNotDeclareAnnexGuids2() {
		return notDeclareAnnexGuids2;
	}

	public void setNotDeclareAnnexGuids2(String notDeclareAnnexGuids2) {
		this.notDeclareAnnexGuids2 = notDeclareAnnexGuids2;
	}

	public String getSqrguid() {
		return sqrguid;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZhengjiandaima() {
		return zhengjiandaima;
	}

	public void setZhengjiandaima(String zhengjiandaima) {
		this.zhengjiandaima = zhengjiandaima;
	}

	public String getSblsh() {
		return sblsh;
	}

	public void setSblsh(String sblsh) {
		this.sblsh = sblsh;
	}

	public void setSqrguid(String sqrguid) {
		this.sqrguid = sqrguid;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
}
