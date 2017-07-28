package net.risesoft.approve.entity.jpa;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.risesoft.util.ConmonUtil;

@Entity
@Table(name="SPM_SENATE")
public class Senate {
	private String senateguid;
	private String declaresn;
	private String bureauguid;
	private String servicewindowno;
	private String bureauname;
	private String employeeguid;
	private String employeedeptname;
	private String declarerperson;
	private String declarertel;
	private String approveitemguid;
	private String approveitemname;
	private String satisfaction;
	private Date declaredatetime;
	private Date senatedatetime;
	private String workflowinstance_guid;
	private String problem;
	private String workflowguid;
	private String yawp;
	private String yawpdescribe;
	private String statedescription;
	private String zhengjiandaima;
	private String issenate;
	private String type;
	private String usermacaddr;
	private String isexchangejc;
	private String satisfaction_sl;
	private String issenate_sl;
	private Date senatedatetime_sl;
	private String processinstanceid;
	private String usermacaddr_sl;
	private String  type_sl;
	private String  department_id;
	private String windowid;
	private String windowname;
	
	@Id
	@Column(name="senateguid",unique=true,nullable=false, length=38)
	public String getSenateguid() {
		return senateguid;
	}
	public void setSenateguid(String senateguid) {
		this.senateguid = senateguid;
	}
	
	@Column(name="declaresn", length=100)
	public String getDeclaresn() {
		return declaresn;
	}
	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}
	
	@Column(name="bureauguid", length=38)
	public String getBureauguid() {
		return bureauguid;
	}
	public void setBureauguid(String bureauguid) {
		this.bureauguid = bureauguid;
	}
	
	@Column(name="servicewindowno", length=10)
	public String getServicewindowno() {
		return servicewindowno;
	}
	public void setServicewindowno(String servicewindowno) {
		this.servicewindowno = servicewindowno;
	}
	
	@Column(name="bureauname", length=100)
	public String getBureauname() {
		return bureauname;
	}
	public void setBureauname(String bureauname) {
		this.bureauname = bureauname;
	}
	
	@Column(name="employeeguid", length=38)
	public String getEmployeeguid() {
		return employeeguid;
	}
	public void setEmployeeguid(String employeeguid) {
		this.employeeguid = employeeguid;
	}
	
	@Column(name="employeedeptname", length=50)
	public String getEmployeedeptname() {
		return employeedeptname;
	}
	public void setEmployeedeptname(String employeedeptname) {
		this.employeedeptname = employeedeptname;
	}
	
	@Column(name="declarerperson", length=500)
	public String getDeclarerperson() {
		return declarerperson;
	}
	public void setDeclarerperson(String declarerperson) {
		this.declarerperson = declarerperson;
	}
	
	@Column(name="declarertel", length=200)
	public String getDeclarertel() {
		return declarertel;
	}
	public void setDeclarertel(String declarertel) {
		this.declarertel = declarertel;
	}
	
	@Column(name="approveitemguid", length=38)
	public String getApproveitemguid() {
		return approveitemguid;
	}
	public void setApproveitemguid(String approveitemguid) {
		this.approveitemguid = approveitemguid;
	}
	
	@Column(name="approveitemname", length=250)
	public String getApproveitemname() {
		return approveitemname;
	}
	public void setApproveitemname(String approveitemname) {
		this.approveitemname = approveitemname;
	}
	
	@Column(name="satisfaction", length=5)
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	
	@Column(name="declaredatetime")
	public Date getDeclaredatetime() {
		return declaredatetime;
	}
	public void setDeclaredatetime(Date declaredatetime) {
		this.declaredatetime = declaredatetime;
	}
	
	@Column(name="senatedatetime")
	public Date getSenatedatetime() {
		return senatedatetime;
	}
	public void setSenatedatetime(Date senatedatetime) {
		this.senatedatetime = senatedatetime;
	}
	
	@Column(name="workflowinstance_guid", length=38)
	public String getWorkflowinstance_guid() {
		return workflowinstance_guid;
	}
	public void setWorkflowinstance_guid(String workflowinstance_guid) {
		this.workflowinstance_guid = workflowinstance_guid;
	}
	
	@Column(name="problem", length=4000)
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	@Column(name="workflowguid", length=38)
	public String getWorkflowguid() {
		return workflowguid;
	}
	public void setWorkflowguid(String workflowguid) {
		this.workflowguid = workflowguid;
	}
	
	@Column(name="yawp", length=3)
	public String getYawp() {
		return yawp;
	}
	public void setYawp(String yawp) {
		this.yawp = yawp;
	}
	
	@Column(name="yawpdescribe", length=500)
	public String getYawpdescribe() {
		return yawpdescribe;
	}
	public void setYawpdescribe(String yawpdescribe) {
		this.yawpdescribe = yawpdescribe;
	}
	
	@Column(name="statedescription", length=30)
	public String getStatedescription() {
		return statedescription;
	}
	public void setStatedescription(String statedescription) {
		this.statedescription = statedescription;
	}
	
	@Column(name="zhengjiandaima", length=100)
	public String getZhengjiandaima() {
		return zhengjiandaima;
	}
	public void setZhengjiandaima(String zhengjiandaima) {
		this.zhengjiandaima = zhengjiandaima;
	}
	
	@Column(name="issenate", nullable=false, length=1)
	public String getIssenate() {
		return issenate;
	}
	public void setIssenate(String issenate) {
		this.issenate = issenate;
	}
	
	public static boolean isDeclareSNExist(String declareSN) throws Exception{
		if(declareSN==null)
			return false;
		
		if(ConmonUtil.isExist("office_spi_declareinfo","declareSN",declareSN))
			return true;
		
		return false;
	}
	
	@Column(name="type", length=38)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="type_sl", length=38)
	public String getType_sl() {
		return type_sl;
	}
	public void setType_sl(String type_sl) {
		this.type_sl = type_sl;
	}
	
	@Column(name="usermacaddr", length=17)
	public String getUsermacaddr() {
		return usermacaddr;
	}
	public void setUsermacaddr(String usermacaddr) {
		this.usermacaddr = usermacaddr;
	}
	
	/////////////////
	@Column(name="isexchangejc", length=1)
	public String getIsexchangejc() {
		return isexchangejc;
	}
	public void setIsexchangejc(String isexchangejc) {
		this.isexchangejc = isexchangejc;
	}
	@Column(name="satisfaction_sl", length=5)
	public String getSatisfaction_sl() {
		return satisfaction_sl;
	}
	public void setSatisfaction_sl(String satisfaction_sl) {
		this.satisfaction_sl = satisfaction_sl;
	}
	@Column(name="issenate_sl", length=1)
	public String getIssenate_sl() {
		return issenate_sl;
	}
	public void setIssenate_sl(String issenate_sl) {
		this.issenate_sl = issenate_sl;
	}
	@Column(name="senatedatetime_sl")
	public Date getSenatedatetime_sl() {
		return senatedatetime_sl;
	}
	public void setSenatedatetime_sl(Date senatedatetime_sl) {
		this.senatedatetime_sl = senatedatetime_sl;
	}
	@Column(name="processinstanceid", length=38)
	public String getProcessinstanceid() {
		return processinstanceid;
	}
	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}
	@Column(name="usermacaddr_sl", length=17)
	public String getUsermacaddr_sl() {
		return usermacaddr_sl;
	}
	public void setUsermacaddr_sl(String usermacaddr_sl) {
		this.usermacaddr_sl = usermacaddr_sl;
	}
	@Column(name="department_id", length=100)
	public String getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}
	@Column(name="windowid", length=100)
	public String getWindowid() {
		return windowid;
	}
	public void setWindowid(String windowid) {
		this.windowid = windowid;
	}
	@Column(name="windowname", length=250)
	public String getWindowname() {
		return windowname;
	}
	public void setWindowname(String windowname) {
		this.windowname = windowname;
	}
	
	
	
}
