package net.risesoft.approve.entity.jpa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ZKROLLMACHINE
 * 考勤机表
 * @author Administrator
 *
 */
@Entity
@Table(name="KQ_ROLLMACHINE")
public class ZkRollMachine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String guid;			//"GUID"  VARCHAR2(38 BYTE) NULL,					主键
	private String ip;			//	"IP" VARCHAR2(50 BYTE) NULL ,					考勤机ip
	private int port;			//"PORT" VARCHAR2(20 BYTE) NULL,					考勤机端口
	private String department;  //"DEPARTMENT"  VARCHAR2(50 BYTE) NULL ,			大厅名称
	private int devicenumber;         //"DEVICENUMBER"	 	VARCHAR2(20 BYTE) NULL,				考勤机编号
	private int ismainhall;		//"ISMAIN"       VARCHAR2(10 BYTE) NULL,  是否设置为主厅，“0”代表主厅，“1”代表分厅
	
	@Id
	@Column(name="GUID",length=38)
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
	@Column(name="PORT",length=20)
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
			
	@Column(name="IP",length=50)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name="DEPARTMENT",length=50)
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Column(name="DEVICENUMBER",length=20)
	public int getDevicenumber() {
		return devicenumber;
	}

	public void setDevicenumber(int devicenumber) {
		this.devicenumber = devicenumber;
	}
	
	@Column(name="ISMAINHALL")
	public int getIsmainhall() {
		return ismainhall;
	}
	public void setIsmainhall(int ismainhall) {
		this.ismainhall = ismainhall;
	}
	
	
}
