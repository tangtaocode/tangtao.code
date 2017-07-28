package net.risesoft.approve.entity.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;



/**
 * SpApprovewebUser entity.
 * 外网用户表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SP_APPROVEWEB_USER")
   


public class SpApprovewebUser  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String guid;			//"GUID" VARCHAR2(38) NOT NULL ,用户GUID主键
     private String loginname;		//"LOGINNAME"  VARCHAR2(255) NOT NULL,登陆名称
     private String password;		//"PASSWORD"	VARCHAR2(255) NOT NULL,登录密码
     private String usertype;		//"USERTYPE" VARCHAR2(255 ) NOT NULL ,用户类型
     private String cardid;			//"CARDID" VARCHAR2(255 BYTE) NOT NULL ,证件号码
     private String detailguid;		//"DETAILGUID" VARCHAR2(38 BYTE) NULL ,
     private String mobile;			//"MOBILE" VARCHAR2(255 BYTE) NOT NULL ,手机号码,
     private Date creattime;		//"CREATTIME" DATE DEFAULT sysdate  NULL ,注册时间
     private Long state;			//	"STATE" NUMBER DEFAULT 0  NULL ,账户状态
     private String nameremark;		//"NAMEREMARK" VARCHAR2(200 BYTE) NULL ,帐号说明
     private String sxlx;			//"SXLX" VARCHAR2(2 BYTE) NULL ,申请事项类型
     private String cardtype;		//"CARDTYPE" VARCHAR2(1 BYTE) NULL ,1身份证号，2护照号码,3组织机构代码'
     private String wsbsdtuid;		//"WSBSDTUID" VARCHAR2(50 BYTE) NULL ,网上办事大厅uid
     private String certid;			//"CERTID" VARCHAR2(50 BYTE) NULL ,用户证书唯一标识oid
     private Long certlogincount;	//"CERTLOGINCOUNT" NUMBER DEFAULT 0  NULL ,用户使用证书登录次数
     private String logintype;		//"LOGINTYPE" VARCHAR2(2 BYTE) NULL ,用户凭证标识即用户当前登录类型


    // Constructors

   
    
   
   
    // Property accessors
    @Id
 	@Column(name = "GUID", length = 38,unique=true, nullable = false)
 	@GeneratedValue(generator = "uuid")
 	@GenericGenerator(name = "uuid", strategy = "assigned")
    public String getGuid() {
        return this.guid;
    }
    
    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    @Column(name="LOGINNAME", unique=true, nullable=false)

    public String getLoginname() {
        return this.loginname;
    }
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    
    @Column(name="PASSWORD", nullable=false)

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="USERTYPE", nullable=false)

    public String getUsertype() {
        return this.usertype;
    }
    
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    
    @Column(name="CARDID", unique=true, nullable=false)

    public String getCardid() {
        return this.cardid;
    }
    
    public void setCardid(String cardid) {
        this.cardid = cardid;
    }
    
    @Column(name="DETAILGUID", length=38)

    public String getDetailguid() {
        return this.detailguid;
    }
    
    public void setDetailguid(String detailguid) {
        this.detailguid = detailguid;
    }
    
    @Column(name="MOBILE", nullable=false)

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATTIME", length=7)

    public Date getCreattime() {
        return this.creattime;
    }
    
    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }
    
    @Column(name="STATE", precision=22, scale=0)

    public Long getState() {
        return this.state;
    }
    
    public void setState(Long state) {
        this.state = state;
    }
    
    @Column(name="NAMEREMARK", length=200)

    public String getNameremark() {
        return this.nameremark;
    }
    
    public void setNameremark(String nameremark) {
        this.nameremark = nameremark;
    }
    
    @Column(name="SXLX", length=2)

    public String getSxlx() {
        return this.sxlx;
    }
    
    public void setSxlx(String sxlx) {
        this.sxlx = sxlx;
    }
    
    @Column(name="CARDTYPE", length=1)

    public String getCardtype() {
        return this.cardtype;
    }
    
    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }
    
    @Column(name="WSBSDTUID", length=50)

    public String getWsbsdtuid() {
        return this.wsbsdtuid;
    }
    
    public void setWsbsdtuid(String wsbsdtuid) {
        this.wsbsdtuid = wsbsdtuid;
    }
    
    @Column(name="CERTID", length=50)

    public String getCertid() {
        return this.certid;
    }
    
    public void setCertid(String certid) {
        this.certid = certid;
    }
    
    @Column(name="CERTLOGINCOUNT", precision=22, scale=0)

    public Long getCertlogincount() {
        return this.certlogincount;
    }
    
    public void setCertlogincount(Long certlogincount) {
        this.certlogincount = certlogincount;
    }
    
    @Column(name="LOGINTYPE", length=2)

    public String getLogintype() {
        return this.logintype;
    }
    
    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

	@Override
	public String toString() {
		return "SpApprovewebUser [guid=" + guid + ", loginname=" + loginname
				+ ", password=" + password + ", usertype=" + usertype
				+ ", cardid=" + cardid + ", detailguid=" + detailguid
				+ ", mobile=" + mobile + ", creattime=" + creattime
				+ ", state=" + state + ", nameremark=" + nameremark + ", sxlx="
				+ sxlx + ", cardtype=" + cardtype + ", wsbsdtuid=" + wsbsdtuid
				+ ", certid=" + certid + ", certlogincount=" + certlogincount
				+ ", logintype=" + logintype + "]";
	}
   
   
}