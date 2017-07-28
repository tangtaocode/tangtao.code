package net.risesoft.approve.entity.jpa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * kq_UserInfo entity.
 * 考勤机人员表
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="KQ_USERINFO")

public class ZkUserInfo  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
     private String guid;						//"GUID" NVARCHAR2(38 BYTE) NOT NULL ,				主键
     private String enrollnumber;				//	"ENROLLNUMBER" NVARCHAR2(10 BYTE) NULL ,		人员编号	
     private String name;						//	"NAME" NVARCHAR2(50 BYTE) NULL ,				人员姓名
     private String privilege;					//	"PRIVILEGE" NVARCHAR2(100 BYTE) NULL ,			人员权限：0为普通用户、3为超级用户
     private String enabled;					//	"ENABLED " NVARCHAR2(10 BYTE) NULL ,				是否采用：若选择false就表示该用户不能打开
     private String password;					//	"PASSWORD " VARCHAR2(50 BYTE)NULL ,				人员密码
     private int devicenumber;         //"DEVICENUMBER"	 	VARCHAR2(20 BYTE) NULL,				考勤机编号

 
   

   
      @Id
      @Column(name="GUID",length=38)
	      public String getGuid() {
	  		return guid;
	  	}

	  	public void setGuid(String guid) {
	  		this.guid = guid;
	  	}
      
      
	  @Column(name="ENROLLNUMBER", length=10)
	public String getEnrollnumber() {
		return enrollnumber;
	}

	

	public void setEnrollnumber(String enrollnumber) {
		this.enrollnumber = enrollnumber;
	}

	 @Column(name="NAME", length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 @Column(name="PRIVILEGE", length=100)
	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}			

	@Column(name="ENABLED", length=10)
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	 @Column(name="PASSWORD", length=50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="DEVICENUMBER",length=20)
	public int getDevicenumber() {
		return devicenumber;
	}

	public void setDevicenumber(int devicenumber) {
		this.devicenumber = devicenumber;
	}
	
	

	
}