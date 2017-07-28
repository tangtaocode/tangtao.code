package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * AbstractRisenetDepartment entity provides the base persistence definition of
 * the RisenetDepartment entity.
 * 科室-部门表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RISENET_DEPARTMENT")
public class RisenetDepartment  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
     private String departmentGuid;					//	"DEPARTMENT_GUID" CHAR(38 BYTE) NOT NULL ,
     private String superiorGuid;					//	"SUPERIOR_GUID" CHAR(38 BYTE) NOT NULL ,
     private String departmentName;					//	"DEPARTMENT_NAME" VARCHAR2(200 BYTE) NOT NULL ,
     private String departmentDescription;			//	"DEPARTMENT_DESCRIPTION" VARCHAR2(4000 BYTE) NULL ,
     private String departmentAlias;				//	"DEPARTMENT_ALIAS" VARCHAR2(200 BYTE) NULL ,	
     private String departmentShortdn;				//	"DEPARTMENT_SHORTDN" VARCHAR2(200 BYTE) NULL ,
     private String departmentGivenname;			//	"DEPARTMENT_GIVENNAME" VARCHAR2(200 BYTE) NULL ,
     private String departmentHierarchy;			//	"DEPARTMENT_HIERARCHY" VARCHAR2(2000 BYTE) NULL ,
     private String departmentCountry;				//	"DEPARTMENT_COUNTRY" VARCHAR2(200 BYTE) NULL ,
     private String departmentState;				//	"DEPARTMENT_STATE" VARCHAR2(200 BYTE) NULL ,
     private String departmentCity;					//	"DEPARTMENT_CITY" VARCHAR2(200 BYTE) NULL ,
     private String departmentZipcode;				//	"DEPARTMENT_ZIPCODE" VARCHAR2(12 BYTE) NULL ,
     private String departmentAddress;				//	"DEPARTMENT_ADDRESS" VARCHAR2(255 BYTE) NULL ,
     private String departmentOffice;				//	"DEPARTMENT_OFFICE" VARCHAR2(255 BYTE) NULL ,
     private String departmentManagerguid;			//	"DEPARTMENT_MANAGERGUID" CHAR(38 BYTE) NULL ,该部门负责人GUID
     private String departmentPhone;				//	"DEPARTMENT_PHONE" VARCHAR2(200 BYTE) NULL ,
     private String departmentFax;					// 	"DEPARTMENT_FAX" VARCHAR2(200 BYTE) NULL ,
     private Long tabindex;							// 	"TABINDEX" NUMBER(8) NOT NULL ,
     private Long departmentIsdeleted;				// 	"DEPARTMENT_ISDELETED" NUMBER(1) DEFAULT 0  NULL ,
     private String departmentIsdeleteddesc;		// 	"DEPARTMENT_ISDELETEDDESC" VARCHAR2(200 BYTE) NULL ,
     private Long isbureau;							// 	"ISBUREAU" NUMBER(10) NULL ,
     private String dispatcherGuids;				// 	"DISPATCHER_GUIDS" VARCHAR2(1000 BYTE) NULL ,
     private String departmentManager;				// 	"DEPARTMENT_MANAGER" CHAR(200 BYTE) NULL ,
     private String departmentSupermanager;			// 	"DEPARTMENT_SUPERMANAGER" CHAR(200 BYTE) NULL ,
     private Long departmentLevel;					// 	"DEPARTMENT_LEVEL" NUMBER(22) NULL ,
     private Long isdepment;						// 	"ISDEPMENT" NUMBER(10) NULL ,
     private Long isinstitutions;					// 	"ISINSTITUTIONS" NUMBER(1) NULL ,
     private String verticalmanageDepartmentguid;	// 	"VERTICALMANAGE_DEPARTMENTGUID" CHAR(38 BYTE) NULL ,垂直管理部门GUID，如街道的计生科的垂直管理部门为卫生人口计生局的医政科
     private String departmentSupermanagerguid;		// 	"DEPARTMENT_SUPERMANAGERGUID" CHAR(38 BYTE) NULL ,	分管局领导GUID
     private String guidOa;							// 	"GUID_OA" VARCHAR2(255 BYTE) NULL ,					oa系统的RC7 部门guid	
     private String rcidOa;							// 	"RCID_OA" VARCHAR2(255 BYTE) NULL ,					oa系统的RC7 部门RCID


    // Constructors

    /** default constructor */
    public RisenetDepartment() {
    }

	/** minimal constructor */
    public RisenetDepartment(String departmentGuid, String superiorGuid, String departmentName, Long tabindex) {
        this.departmentGuid = departmentGuid;
        this.superiorGuid = superiorGuid;
        this.departmentName = departmentName;
        this.tabindex = tabindex;
    }
    
    /** full constructor */
    public RisenetDepartment(String departmentGuid, String superiorGuid, String departmentName, String departmentDescription, String departmentAlias, String departmentShortdn, String departmentGivenname, String departmentHierarchy, String departmentCountry, String departmentState, String departmentCity, String departmentZipcode, String departmentAddress, String departmentOffice, String departmentManagerguid, String departmentPhone, String departmentFax, Long tabindex, Long departmentIsdeleted, String departmentIsdeleteddesc, Long isbureau, String dispatcherGuids, String departmentManager, String departmentSupermanager, Long departmentLevel, Long isdepment, Long isinstitutions, String verticalmanageDepartmentguid, String departmentSupermanagerguid, String guidOa, String rcidOa) {
        this.departmentGuid = departmentGuid;
        this.superiorGuid = superiorGuid;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.departmentAlias = departmentAlias;
        this.departmentShortdn = departmentShortdn;
        this.departmentGivenname = departmentGivenname;
        this.departmentHierarchy = departmentHierarchy;
        this.departmentCountry = departmentCountry;
        this.departmentState = departmentState;
        this.departmentCity = departmentCity;
        this.departmentZipcode = departmentZipcode;
        this.departmentAddress = departmentAddress;
        this.departmentOffice = departmentOffice;
        this.departmentManagerguid = departmentManagerguid;
        this.departmentPhone = departmentPhone;
        this.departmentFax = departmentFax;
        this.tabindex = tabindex;
        this.departmentIsdeleted = departmentIsdeleted;
        this.departmentIsdeleteddesc = departmentIsdeleteddesc;
        this.isbureau = isbureau;
        this.dispatcherGuids = dispatcherGuids;
        this.departmentManager = departmentManager;
        this.departmentSupermanager = departmentSupermanager;
        this.departmentLevel = departmentLevel;
        this.isdepment = isdepment;
        this.isinstitutions = isinstitutions;
        this.verticalmanageDepartmentguid = verticalmanageDepartmentguid;
        this.departmentSupermanagerguid = departmentSupermanagerguid;
        this.guidOa = guidOa;
        this.rcidOa = rcidOa;
    }

   
    // Property accessors
    @Id
	@Column(name = "DEPARTMENT_GUID", length = 38, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "assigned") 

    public String getDepartmentGuid() {
        return this.departmentGuid;
    }
    
    public void setDepartmentGuid(String departmentGuid) {
        this.departmentGuid = departmentGuid;
    }
    
    @Column(name="SUPERIOR_GUID", nullable=false, length=38)

    public String getSuperiorGuid() {
        return this.superiorGuid;
    }
    
    public void setSuperiorGuid(String superiorGuid) {
        this.superiorGuid = superiorGuid;
    }
    
    @Column(name="DEPARTMENT_NAME", nullable=false, length=200)

    public String getDepartmentName() {
        return this.departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    @Column(name="DEPARTMENT_DESCRIPTION", length=4000)

    public String getDepartmentDescription() {
        return this.departmentDescription;
    }
    
    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }
    
    @Column(name="DEPARTMENT_ALIAS", length=200)

    public String getDepartmentAlias() {
        return this.departmentAlias;
    }
    
    public void setDepartmentAlias(String departmentAlias) {
        this.departmentAlias = departmentAlias;
    }
    
    @Column(name="DEPARTMENT_SHORTDN", length=200)

    public String getDepartmentShortdn() {
        return this.departmentShortdn;
    }
    
    public void setDepartmentShortdn(String departmentShortdn) {
        this.departmentShortdn = departmentShortdn;
    }
    
    @Column(name="DEPARTMENT_GIVENNAME", length=200)

    public String getDepartmentGivenname() {
        return this.departmentGivenname;
    }
    
    public void setDepartmentGivenname(String departmentGivenname) {
        this.departmentGivenname = departmentGivenname;
    }
    
    @Column(name="DEPARTMENT_HIERARCHY", length=2000)

    public String getDepartmentHierarchy() {
        return this.departmentHierarchy;
    }
    
    public void setDepartmentHierarchy(String departmentHierarchy) {
        this.departmentHierarchy = departmentHierarchy;
    }
    
    @Column(name="DEPARTMENT_COUNTRY", length=200)

    public String getDepartmentCountry() {
        return this.departmentCountry;
    }
    
    public void setDepartmentCountry(String departmentCountry) {
        this.departmentCountry = departmentCountry;
    }
    
    @Column(name="DEPARTMENT_STATE", length=200)

    public String getDepartmentState() {
        return this.departmentState;
    }
    
    public void setDepartmentState(String departmentState) {
        this.departmentState = departmentState;
    }
    
    @Column(name="DEPARTMENT_CITY", length=200)

    public String getDepartmentCity() {
        return this.departmentCity;
    }
    
    public void setDepartmentCity(String departmentCity) {
        this.departmentCity = departmentCity;
    }
    
    @Column(name="DEPARTMENT_ZIPCODE", length=12)

    public String getDepartmentZipcode() {
        return this.departmentZipcode;
    }
    
    public void setDepartmentZipcode(String departmentZipcode) {
        this.departmentZipcode = departmentZipcode;
    }
    
    @Column(name="DEPARTMENT_ADDRESS")

    public String getDepartmentAddress() {
        return this.departmentAddress;
    }
    
    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress;
    }
    
    @Column(name="DEPARTMENT_OFFICE")

    public String getDepartmentOffice() {
        return this.departmentOffice;
    }
    
    public void setDepartmentOffice(String departmentOffice) {
        this.departmentOffice = departmentOffice;
    }
    
    @Column(name="DEPARTMENT_MANAGERGUID", length=38)

    public String getDepartmentManagerguid() {
        return this.departmentManagerguid;
    }
    
    public void setDepartmentManagerguid(String departmentManagerguid) {
        this.departmentManagerguid = departmentManagerguid;
    }
    
    @Column(name="DEPARTMENT_PHONE", length=200)

    public String getDepartmentPhone() {
        return this.departmentPhone;
    }
    
    public void setDepartmentPhone(String departmentPhone) {
        this.departmentPhone = departmentPhone;
    }
    
    @Column(name="DEPARTMENT_FAX", length=200)

    public String getDepartmentFax() {
        return this.departmentFax;
    }
    
    public void setDepartmentFax(String departmentFax) {
        this.departmentFax = departmentFax;
    }
    
    @Column(name="TABINDEX", nullable=false, precision=8, scale=0)

    public Long getTabindex() {
        return this.tabindex;
    }
    
    public void setTabindex(Long tabindex) {
        this.tabindex = tabindex;
    }
    
    @Column(name="DEPARTMENT_ISDELETED", precision=1, scale=0)

    public Long getDepartmentIsdeleted() {
        return this.departmentIsdeleted;
    }
    
    public void setDepartmentIsdeleted(Long departmentIsdeleted) {
        this.departmentIsdeleted = departmentIsdeleted;
    }
    
    @Column(name="DEPARTMENT_ISDELETEDDESC", length=200)

    public String getDepartmentIsdeleteddesc() {
        return this.departmentIsdeleteddesc;
    }
    
    public void setDepartmentIsdeleteddesc(String departmentIsdeleteddesc) {
        this.departmentIsdeleteddesc = departmentIsdeleteddesc;
    }
    
    @Column(name="ISBUREAU", precision=10, scale=0)

    public Long getIsbureau() {
        return this.isbureau;
    }
    
    public void setIsbureau(Long isbureau) {
        this.isbureau = isbureau;
    }
    
    @Column(name="DISPATCHER_GUIDS", length=1000)

    public String getDispatcherGuids() {
        return this.dispatcherGuids;
    }
    
    public void setDispatcherGuids(String dispatcherGuids) {
        this.dispatcherGuids = dispatcherGuids;
    }
    
    @Column(name="DEPARTMENT_MANAGER", length=200)

    public String getDepartmentManager() {
        return this.departmentManager;
    }
    
    public void setDepartmentManager(String departmentManager) {
        this.departmentManager = departmentManager;
    }
    
    @Column(name="DEPARTMENT_SUPERMANAGER", length=200)

    public String getDepartmentSupermanager() {
        return this.departmentSupermanager;
    }
    
    public void setDepartmentSupermanager(String departmentSupermanager) {
        this.departmentSupermanager = departmentSupermanager;
    }
    
    @Column(name="DEPARTMENT_LEVEL", precision=22, scale=0)

    public Long getDepartmentLevel() {
        return this.departmentLevel;
    }
    
    public void setDepartmentLevel(Long departmentLevel) {
        this.departmentLevel = departmentLevel;
    }
    
    @Column(name="ISDEPMENT", precision=10, scale=0)

    public Long getIsdepment() {
        return this.isdepment;
    }
    
    public void setIsdepment(Long isdepment) {
        this.isdepment = isdepment;
    }
    
    @Column(name="ISINSTITUTIONS", precision=1, scale=0)

    public Long getIsinstitutions() {
        return this.isinstitutions;
    }
    
    public void setIsinstitutions(Long isinstitutions) {
        this.isinstitutions = isinstitutions;
    }
    
    @Column(name="VERTICALMANAGE_DEPARTMENTGUID", length=38)

    public String getVerticalmanageDepartmentguid() {
        return this.verticalmanageDepartmentguid;
    }
    
    public void setVerticalmanageDepartmentguid(String verticalmanageDepartmentguid) {
        this.verticalmanageDepartmentguid = verticalmanageDepartmentguid;
    }
    
    @Column(name="DEPARTMENT_SUPERMANAGERGUID", length=38)

    public String getDepartmentSupermanagerguid() {
        return this.departmentSupermanagerguid;
    }
    
    public void setDepartmentSupermanagerguid(String departmentSupermanagerguid) {
        this.departmentSupermanagerguid = departmentSupermanagerguid;
    }
    
    @Column(name="GUID_OA")

    public String getGuidOa() {
        return this.guidOa;
    }
    
    public void setGuidOa(String guidOa) {
        this.guidOa = guidOa;
    }
    
    @Column(name="RCID_OA")

    public String getRcidOa() {
        return this.rcidOa;
    }
    
    public void setRcidOa(String rcidOa) {
        this.rcidOa = rcidOa;
    }

	@Override
	public String toString() {
		return "RisenetDepartment [departmentGuid=" + departmentGuid
				+ ", superiorGuid=" + superiorGuid + ", departmentName="
				+ departmentName + ", departmentDescription="
				+ departmentDescription + ", departmentAlias="
				+ departmentAlias + ", departmentShortdn=" + departmentShortdn
				+ ", departmentGivenname=" + departmentGivenname
				+ ", departmentHierarchy=" + departmentHierarchy
				+ ", departmentCountry=" + departmentCountry
				+ ", departmentState=" + departmentState + ", departmentCity="
				+ departmentCity + ", departmentZipcode=" + departmentZipcode
				+ ", departmentAddress=" + departmentAddress
				+ ", departmentOffice=" + departmentOffice
				+ ", departmentManagerguid=" + departmentManagerguid
				+ ", departmentPhone=" + departmentPhone + ", departmentFax="
				+ departmentFax + ", tabindex=" + tabindex
				+ ", departmentIsdeleted=" + departmentIsdeleted
				+ ", departmentIsdeleteddesc=" + departmentIsdeleteddesc
				+ ", isbureau=" + isbureau + ", dispatcherGuids="
				+ dispatcherGuids + ", departmentManager=" + departmentManager
				+ ", departmentSupermanager=" + departmentSupermanager
				+ ", departmentLevel=" + departmentLevel + ", isdepment="
				+ isdepment + ", isinstitutions=" + isinstitutions
				+ ", verticalmanageDepartmentguid="
				+ verticalmanageDepartmentguid
				+ ", departmentSupermanagerguid=" + departmentSupermanagerguid
				+ ", guidOa=" + guidOa + ", rcidOa=" + rcidOa + "]";
	}
   

}