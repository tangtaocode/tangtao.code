package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * SpmDeclareannex entity.
 * 事项材料类型状态表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SPM_DECLAREANNEX"
)

public class SpmDeclareannex  implements Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String declareannexguid;	//"DECLAREANNEXGUID" CHAR(38) NOT NULL,材料GUID
     private String approveitemguid;	//"APPROVEITEMGUID"	 CHAR(38) NOT NULL,	事项GUID
     private String declareannexsn;		//"DECLAREANNEXSN"	VARCHAR2(100),	
     private String declareannexname;	//"DECLAREANNEXNAME" VARCHAR2(3500) NOT NULL,材料名称
     private Long declareannextype;		//"DECLAREANNEXTYPE"  NUMBER(3) NOT NULL,	
     private String declareannexfilename;//"DECLAREANNEXFILENAME" VARCHAR2(200),
     private String declareannexdesc;	//"DECLAREANNEXDESC" CLOB(4000),
     private Long mustsubmitform;		//"MUSTSUBMITFORM" NUMBER(1),
     private Long declareannextabindex;	//"DECLAREANNEXTABINDEX"  NUMBER(3),
     private String declareannextypeguid;//"DECLAREANNEXTYPEGUID" CHAR(38),材料类别GUID
     private String doctypeguid;		 //"DOCTYPEGUID"  CLOB(4000),


    // Property accessors
    @Id
 	@Column(name = "DECLAREANNEXGUID", length = 38, nullable = false)
 	@GeneratedValue(generator = "uuid")
 	@GenericGenerator(name = "uuid", strategy = "assigned") 
    
    

    public String getDeclareannexguid() {
        return this.declareannexguid;
    }
    
    public void setDeclareannexguid(String declareannexguid) {
        this.declareannexguid = declareannexguid;
    }
    
    @Column(name="APPROVEITEMGUID", nullable=false, length=38)

    public String getApproveitemguid() {
        return this.approveitemguid;
    }
    
    public void setApproveitemguid(String approveitemguid) {
        this.approveitemguid = approveitemguid;
    }
    
    @Column(name="DECLAREANNEXSN", length=100)

    public String getDeclareannexsn() {
        return this.declareannexsn;
    }
    
    public void setDeclareannexsn(String declareannexsn) {
        this.declareannexsn = declareannexsn;
    }
    
    @Column(name="DECLAREANNEXNAME", nullable=false, length=3500)

    public String getDeclareannexname() {
        return this.declareannexname;
    }
    
    public void setDeclareannexname(String declareannexname) {
        this.declareannexname = declareannexname;
    }
    
    @Column(name="DECLAREANNEXTYPE", nullable=false, precision=3, scale=0)

    public Long getDeclareannextype() {
        return this.declareannextype;
    }
    
    public void setDeclareannextype(Long declareannextype) {
        this.declareannextype = declareannextype;
    }
    
    @Column(name="DECLAREANNEXFILENAME", length=200)

    public String getDeclareannexfilename() {
        return this.declareannexfilename;
    }
    
    public void setDeclareannexfilename(String declareannexfilename) {
        this.declareannexfilename = declareannexfilename;
    }
    
    @Column(name="DECLAREANNEXDESC")

    public String getDeclareannexdesc() {
        return this.declareannexdesc;
    }
    
    public void setDeclareannexdesc(String declareannexdesc) {
        this.declareannexdesc = declareannexdesc;
    }
    
    @Column(name="MUSTSUBMITFORM", precision=1, scale=0)

    public Long getMustsubmitform() {
        return this.mustsubmitform;
    }
    
    public void setMustsubmitform(Long mustsubmitform) {
        this.mustsubmitform = mustsubmitform;
    }
    
    @Column(name="DECLAREANNEXTABINDEX", precision=3, scale=0)

    public Long getDeclareannextabindex() {
        return this.declareannextabindex;
    }
    
    public void setDeclareannextabindex(Long declareannextabindex) {
        this.declareannextabindex = declareannextabindex;
    }
    
    @Column(name="DECLAREANNEXTYPEGUID", length=38)

    public String getDeclareannextypeguid() {
        return this.declareannextypeguid;
    }
    
    public void setDeclareannextypeguid(String declareannextypeguid) {
        this.declareannextypeguid = declareannextypeguid;
    }
    
    @Column(name="DOCTYPEGUID")

    public String getDoctypeguid() {
        return this.doctypeguid;
    }
    
    public void setDoctypeguid(String doctypeguid) {
        this.doctypeguid = doctypeguid;
    }

	@Override
	public String toString() {
		return "SpmDeclareannex [declareannexguid=" + declareannexguid
				+ ", approveitemguid=" + approveitemguid + ", declareannexsn="
				+ declareannexsn + ", declareannexname=" + declareannexname
				+ ", declareannextype=" + declareannextype
				+ ", declareannexfilename=" + declareannexfilename
				+ ", declareannexdesc=" + declareannexdesc
				+ ", mustsubmitform=" + mustsubmitform
				+ ", declareannextabindex=" + declareannextabindex
				+ ", declareannextypeguid=" + declareannextypeguid
				+ ", doctypeguid=" + doctypeguid + "]";
	}
   
    







}