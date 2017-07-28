package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * SpmDeclareannexrelation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="SPM_DECLAREANNEXRELATION")



public class SpmDeclareannexrelation  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String id;						//主键 "ID" VARCHAR2(50) NOT NULL,	
     private String approveitemguid;		//"APPROVEITEMGUID" VARCHAR2(50) NOT NULL,	事项GUID
     private String declareannextypeguid;	//"DECLAREANNEXTYPEGUID" VARCHAR2(50)		材料类别GUID
     private String declareannexguid;		//"DECLAREANNEXGUID" VARCHAR2(50) NOT NULL,材料GUID


    // Constructors

    /** default constructor */
    public SpmDeclareannexrelation() {
    }

	/** minimal constructor */
    public SpmDeclareannexrelation(String id, String approveitemguid, String declareannexguid) {
        this.id = id;
        this.approveitemguid = approveitemguid;
        this.declareannexguid = declareannexguid;
    }
    
    /** full constructor */
    public SpmDeclareannexrelation(String id, String approveitemguid, String declareannextypeguid, String declareannexguid) {
        this.id = id;
        this.approveitemguid = approveitemguid;
        this.declareannextypeguid = declareannextypeguid;
        this.declareannexguid = declareannexguid;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ID", unique=true, nullable=false, length=50)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="APPROVEITEMGUID", nullable=false, length=50)

    public String getApproveitemguid() {
        return this.approveitemguid;
    }
    
    public void setApproveitemguid(String approveitemguid) {
        this.approveitemguid = approveitemguid;
    }
    
    @Column(name="DECLAREANNEXTYPEGUID", length=50)

    public String getDeclareannextypeguid() {
        return this.declareannextypeguid;
    }
    
    public void setDeclareannextypeguid(String declareannextypeguid) {
        this.declareannextypeguid = declareannextypeguid;
    }
    
    @Column(name="DECLAREANNEXGUID", nullable=false, length=50)

    public String getDeclareannexguid() {
        return this.declareannexguid;
    }
    
    public void setDeclareannexguid(String declareannexguid) {
        this.declareannexguid = declareannexguid;
    }
   








}