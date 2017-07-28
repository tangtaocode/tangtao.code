package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * AbstractTBdexDocinfoother entity provides the base persistence definition of
 * the TBdexDocinfoother entity.
 * 证照详细内容表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="T_BDEX_DOCINFOOTHER")
public  class TBdexDocinfoother  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields


     private String guid;				//	"GUID" VARCHAR2(38 BYTE) NOT NULL,			本表主键
     private String id;					//	"ID" VARCHAR2(128 BYTE) NOT NULL ,			检索流水号,对应T_BDEX_DOCINFO表的id字段 	
     private String docinfoid;			//	"DOCINFOID" VARCHAR2(100 BYTE) NULL ,		证照信息唯一id,在各单位中的具体证照，批复的具体ID号（唯一）
     private Long no;					//	"NO" NUMBER NULL ,							切割内容顺序号,按照切割的顺序，从1开始排列。如果证照内容超过2000，再插一行2开始
     private String othercontent;		//	"OTHERCONTENT" VARCHAR2(2000 BYTE) NULL ,	证照内容,证照内容，以XML的格式来展示
     private String admindivision;		//	"ADMINDIVISION" VARCHAR2(6 BYTE) NULL ,		发证对象所在的行政区域,罗湖区 440303
     private String zjjgdm;				//	"ZJJGDM" VARCHAR2(100 BYTE) NULL ,			发证对象组织机构代码,填写各单位9位的“组织机构代码
     private String remark;				//	"REMARK" VARCHAR2(200 BYTE) NULL ,			备注


    // Constructors

    /** default constructor */
    public TBdexDocinfoother() {
    }


   
    // Property accessors
    @Id
   	@Column(name = "GUID", length = 38, nullable = false)
   	@GeneratedValue(generator = "uuid")
   	@GenericGenerator(name = "uuid", strategy = "assigned") 
    public String getGuid() {
        return this.guid;
    }
    
    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    @Column(name="ID", nullable=false, length=128)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="DOCINFOID", length=100)

    public String getDocinfoid() {
        return this.docinfoid;
    }
    
    public void setDocinfoid(String docinfoid) {
        this.docinfoid = docinfoid;
    }
    
    @Column(name="NO", precision=22, scale=0)

    public Long getNo() {
        return this.no;
    }
    
    public void setNo(Long no) {
        this.no = no;
    }
    
    @Column(name="OTHERCONTENT", length=2000)

    public String getOthercontent() {
        return this.othercontent;
    }
    
    public void setOthercontent(String othercontent) {
        this.othercontent = othercontent;
    }
    
    @Column(name="ADMINDIVISION", length=6)

    public String getAdmindivision() {
        return this.admindivision;
    }
    
    public void setAdmindivision(String admindivision) {
        this.admindivision = admindivision;
    }
    
    @Column(name="ZJJGDM", length=100)

    public String getZjjgdm() {
        return this.zjjgdm;
    }
    
    public void setZjjgdm(String zjjgdm) {
        this.zjjgdm = zjjgdm;
    }
    
    @Column(name="REMARK", length=200)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Override
	public String toString() {
		return "TBdexDocinfoother [guid=" + guid + ", id=" + id
				+ ", docinfoid=" + docinfoid + ", no=" + no + ", othercontent="
				+ othercontent + ", admindivision=" + admindivision
				+ ", zjjgdm=" + zjjgdm + ", remark=" + remark + "]";
	}
   
   
}