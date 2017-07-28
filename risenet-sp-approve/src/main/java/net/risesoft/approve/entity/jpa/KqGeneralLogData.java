package net.risesoft.approve.entity.jpa;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * KQ_GENERALLOGDATA entity.
 * 考勤记录表
 * 
 * @author 
 */
@Entity
@Table(name="KQ_GENERALLOGDATA")

public class KqGeneralLogData  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

    
	 private String guid;						//	"GUID" NVARCHAR2(38 BYTE)NOT NULL ,				主键
     private String enrollnumber;				//	"ENROLLNUMBER" NVARCHAR2(100 BYTE) NULL ,		人员编号	
     private Date time;							//	"TIME" DATE() NULL ,				考勤时间串
     private String verifymode;					//	"VERIFYMODE" NVARCHAR2(20 BYTE) NULL ,			校验模式
     private String inOutMode;					//	"INOUTMODE" NVARCHAR2(20 BYTE) NULL ,				输入状态  
     private String type;						//"TYPE" NVARCHAR2(20 BYTE) NULL ,			类型：上午和下午、超过13点则为下午		
     private int devicenumber;         //"DEVICENUMBER"	 	VARCHAR2(20 BYTE) NULL,				考勤机编号
     private String statustype;			//"STATUSTYPE" VARCHAR2(20 BYTE) NULL,	状态类型：由管理员新增的考勤记录才有状态
 	 private String islate;				//"ISLATE" VARCHAR2(20 BYTE) NULL,是否迟到
   
 	@Column(name="ISLATE", length=50)
	public String getIslate() {
		return islate;
	}

	public void setIslate(String islate) {
		this.islate = islate;
	}

	/** default constructor */
    public KqGeneralLogData() { 		
    }
 	
    @Id
    @Column(name="GUID", length=38)
    public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
 	
   
	

	@Column(name="ENROLLNUMBER", length=100)
	public String getEnrollnumber() {
		return enrollnumber;
	}

	public void setEnrollnumber(String enrollnumber) {
		this.enrollnumber = enrollnumber;
	}
	
	
	@Column(name="TIME")
	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	@Column(name="VERIFYMODE", length=20)
	public String getVerifymode() {
		return verifymode;
	}

	public void setVerifymode(String verifymode) {
		this.verifymode = verifymode;
	}

	@Column(name="INOUTMODE", length=20)
	public String getInOutMode() {
		return inOutMode;
	}

	public void setInOutMode(String inOutMode) {
		this.inOutMode = inOutMode;
	}
	
	@Column(name="TYPE",length=10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="DEVICENUMBER",length=20)
	public int getDevicenumber() {
		return devicenumber;
	}

	public void setDevicenumber(int devicenumber) {
		this.devicenumber = devicenumber;
	}

	@Column(name="STATUSTYPE",length=20)	
	public String getStatustype() {
		return statustype;
	}

	public void setStatustype(String statustype) {
		this.statustype = statustype;
	}
	
	
}