package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * RisenetEmployee entity.
 * 审批人员表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RISENET_EMPLOYEE"
    ,schema="RISERC8"
)

public class RisenetEmployee  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private RisenetEmployeeUnits id;			//"EMPLOYEE_GUID" CHAR(38 BYTE) NOT NULL ,
     											//"DEPARTMENT_GUID" CHAR(38 BYTE) NOT NULL ,   
     private String employeeLoginname;			//     "EMPLOYEE_LOGINNAME" VARCHAR2(255 BYTE) NULL ,
     private String employeePassword;			//     "EMPLOYEE_PASSWORD" VARCHAR2(20 BYTE) NULL ,
     private String employeeName;				//     "EMPLOYEE_NAME" VARCHAR2(255 BYTE) NOT NULL ,
     private String employeeEmail;				//     "EMPLOYEE_EMAIL" VARCHAR2(60 BYTE) NULL ,
     private String employeeJobtitles;			//     "EMPLOYEE_JOBTITLES" VARCHAR2(50 BYTE) NULL ,
     private String employeeProfessionaltitle;	//     "EMPLOYEE_PROFESSIONALTITLE" VARCHAR2(60 BYTE) NULL ,
     private String employeeStatus;				//     "EMPLOYEE_STATUS" VARCHAR2(20 BYTE) NULL ,
     private String employeeSex;				//     "EMPLOYEE_SEX" VARCHAR2(2 BYTE) NULL ,
     private Date employeeBirthday;				//     "EMPLOYEE_BIRTHDAY" DATE NULL ,
     private String employeeCountry;			//     "EMPLOYEE_COUNTRY" VARCHAR2(20 BYTE) NULL ,
     private String employeeProvince;			//     "EMPLOYEE_PROVINCE" VARCHAR2(20 BYTE) NULL ,
     private String employeeCity;				//     "EMPLOYEE_CITY" VARCHAR2(20 BYTE) NULL ,
     private String employeeOfficeaddress;		//     "EMPLOYEE_OFFICEADDRESS" VARCHAR2(100 BYTE) NULL ,
     private String employeeOfficephone;		//     "EMPLOYEE_OFFICEPHONE" VARCHAR2(20 BYTE) NULL ,
     private String employeeOfficezipcode;		//     "EMPLOYEE_OFFICEZIPCODE" VARCHAR2(6 BYTE) NULL ,
     private String employeeOfficefax;			//     "EMPLOYEE_OFFICEFAX" VARCHAR2(20 BYTE) NULL ,
     private String employeeHomephone;			//     "EMPLOYEE_HOMEPHONE" VARCHAR2(20 BYTE) NULL ,
     private String employeeHomeaddress;		//     "EMPLOYEE_HOMEADDRESS" VARCHAR2(100 BYTE) NULL ,
     private String employeeMobile;				//     "EMPLOYEE_MOBILE" VARCHAR2(20 BYTE) NULL ,
     private String employeePoliticstatus;		//     "EMPLOYEE_POLITICSTATUS" VARCHAR2(50 BYTE) NULL ,
     private String employeeAcademictitle;		//     "EMPLOYEE_ACADEMICTITLE" VARCHAR2(30 BYTE) NULL ,
     private String employeeEducationrecord;	//     "EMPLOYEE_EDUCATIONRECORD" VARCHAR2(10 BYTE) NULL ,
     private String employeePhoto;				//     "EMPLOYEE_PHOTO" BLOB NULL ,
     private String employeePhototype;			//     "EMPLOYEE_PHOTOTYPE" VARCHAR2(10 BYTE) NULL ,
     private Long tabindex;						//     "TABINDEX" NUMBER(8) NOT NULL ,
     private String employeeIsdepartmentmanager;//     "EMPLOYEE_ISDEPARTMENTMANAGER" VARCHAR2(10 BYTE) NULL ,
     private Long employeeIsdeleted;			//     "EMPLOYEE_ISDELETED" NUMBER(1) DEFAULT 0  NULL ,
     private String employeeIsdeleteddesc;		//     "EMPLOYEE_ISDELETEDDESC" VARCHAR2(200 BYTE) NULL ,
     private Long employeeIsalias;				//     "EMPLOYEE_ISALIAS" NUMBER(1) DEFAULT 0  NULL ,
     private Long smscount;						//     "SMSCOUNT" NUMBER(10) NULL ,
     private String rcidOa;						//     "RCID_OA" NVARCHAR2(255) NULL ,
     private String guidOa;						//     "GUID_OA" CHAR(38 BYTE) NULL 


    // Constructors

    /** default constructor */
    public RisenetEmployee() {
    }

	/** minimal constructor */
    public RisenetEmployee(RisenetEmployeeUnits id, String employeeName, Long tabindex) {
        this.id = id;
        this.employeeName = employeeName;
        this.tabindex = tabindex;
    }
    
    /** full constructor */
    public RisenetEmployee(RisenetEmployeeUnits id, String employeeLoginname, String employeePassword, String employeeName, String employeeEmail, String employeeJobtitles, String employeeProfessionaltitle, String employeeStatus, String employeeSex, Date employeeBirthday, String employeeCountry, String employeeProvince, String employeeCity, String employeeOfficeaddress, String employeeOfficephone, String employeeOfficezipcode, String employeeOfficefax, String employeeHomephone, String employeeHomeaddress, String employeeMobile, String employeePoliticstatus, String employeeAcademictitle, String employeeEducationrecord, String employeePhoto, String employeePhototype, Long tabindex, String employeeIsdepartmentmanager, Long employeeIsdeleted, String employeeIsdeleteddesc, Long employeeIsalias, Long smscount, String rcidOa, String guidOa) {
        this.id = id;
        this.employeeLoginname = employeeLoginname;
        this.employeePassword = employeePassword;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeJobtitles = employeeJobtitles;
        this.employeeProfessionaltitle = employeeProfessionaltitle;
        this.employeeStatus = employeeStatus;
        this.employeeSex = employeeSex;
        this.employeeBirthday = employeeBirthday;
        this.employeeCountry = employeeCountry;
        this.employeeProvince = employeeProvince;
        this.employeeCity = employeeCity;
        this.employeeOfficeaddress = employeeOfficeaddress;
        this.employeeOfficephone = employeeOfficephone;
        this.employeeOfficezipcode = employeeOfficezipcode;
        this.employeeOfficefax = employeeOfficefax;
        this.employeeHomephone = employeeHomephone;
        this.employeeHomeaddress = employeeHomeaddress;
        this.employeeMobile = employeeMobile;
        this.employeePoliticstatus = employeePoliticstatus;
        this.employeeAcademictitle = employeeAcademictitle;
        this.employeeEducationrecord = employeeEducationrecord;
        this.employeePhoto = employeePhoto;
        this.employeePhototype = employeePhototype;
        this.tabindex = tabindex;
        this.employeeIsdepartmentmanager = employeeIsdepartmentmanager;
        this.employeeIsdeleted = employeeIsdeleted;
        this.employeeIsdeleteddesc = employeeIsdeleteddesc;
        this.employeeIsalias = employeeIsalias;
        this.smscount = smscount;
        this.rcidOa = rcidOa;
        this.guidOa = guidOa;
    }

   
    // Property accessors
    @EmbeddedId
  public RisenetEmployeeUnits getId() {
        return this.id;
    }
    
    public void setId(RisenetEmployeeUnits id) {
        this.id = id;
    }
    
    @Column(name="EMPLOYEE_LOGINNAME")

    public String getEmployeeLoginname() {
        return this.employeeLoginname;
    }
    
    public void setEmployeeLoginname(String employeeLoginname) {
        this.employeeLoginname = employeeLoginname;
    }
    
    @Column(name="EMPLOYEE_PASSWORD", length=20)

    public String getEmployeePassword() {
        return this.employeePassword;
    }
    
    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }
    
    @Column(name="EMPLOYEE_NAME", nullable=false)

    public String getEmployeeName() {
        return this.employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    @Column(name="EMPLOYEE_EMAIL", length=60)

    public String getEmployeeEmail() {
        return this.employeeEmail;
    }
    
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
    
    @Column(name="EMPLOYEE_JOBTITLES", length=50)

    public String getEmployeeJobtitles() {
        return this.employeeJobtitles;
    }
    
    public void setEmployeeJobtitles(String employeeJobtitles) {
        this.employeeJobtitles = employeeJobtitles;
    }
    
    @Column(name="EMPLOYEE_PROFESSIONALTITLE", length=60)

    public String getEmployeeProfessionaltitle() {
        return this.employeeProfessionaltitle;
    }
    
    public void setEmployeeProfessionaltitle(String employeeProfessionaltitle) {
        this.employeeProfessionaltitle = employeeProfessionaltitle;
    }
    
    @Column(name="EMPLOYEE_STATUS", length=20)

    public String getEmployeeStatus() {
        return this.employeeStatus;
    }
    
    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
    
    @Column(name="EMPLOYEE_SEX", length=2)

    public String getEmployeeSex() {
        return this.employeeSex;
    }
    
    public void setEmployeeSex(String employeeSex) {
        this.employeeSex = employeeSex;
    }
@Temporal(TemporalType.DATE)
    @Column(name="EMPLOYEE_BIRTHDAY", length=7)

    public Date getEmployeeBirthday() {
        return this.employeeBirthday;
    }
    
    public void setEmployeeBirthday(Date employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }
    
    @Column(name="EMPLOYEE_COUNTRY", length=20)

    public String getEmployeeCountry() {
        return this.employeeCountry;
    }
    
    public void setEmployeeCountry(String employeeCountry) {
        this.employeeCountry = employeeCountry;
    }
    
    @Column(name="EMPLOYEE_PROVINCE", length=20)

    public String getEmployeeProvince() {
        return this.employeeProvince;
    }
    
    public void setEmployeeProvince(String employeeProvince) {
        this.employeeProvince = employeeProvince;
    }
    
    @Column(name="EMPLOYEE_CITY", length=20)

    public String getEmployeeCity() {
        return this.employeeCity;
    }
    
    public void setEmployeeCity(String employeeCity) {
        this.employeeCity = employeeCity;
    }
    
    @Column(name="EMPLOYEE_OFFICEADDRESS", length=100)

    public String getEmployeeOfficeaddress() {
        return this.employeeOfficeaddress;
    }
    
    public void setEmployeeOfficeaddress(String employeeOfficeaddress) {
        this.employeeOfficeaddress = employeeOfficeaddress;
    }
    
    @Column(name="EMPLOYEE_OFFICEPHONE", length=20)

    public String getEmployeeOfficephone() {
        return this.employeeOfficephone;
    }
    
    public void setEmployeeOfficephone(String employeeOfficephone) {
        this.employeeOfficephone = employeeOfficephone;
    }
    
    @Column(name="EMPLOYEE_OFFICEZIPCODE", length=6)

    public String getEmployeeOfficezipcode() {
        return this.employeeOfficezipcode;
    }
    
    public void setEmployeeOfficezipcode(String employeeOfficezipcode) {
        this.employeeOfficezipcode = employeeOfficezipcode;
    }
    
    @Column(name="EMPLOYEE_OFFICEFAX", length=20)

    public String getEmployeeOfficefax() {
        return this.employeeOfficefax;
    }
    
    public void setEmployeeOfficefax(String employeeOfficefax) {
        this.employeeOfficefax = employeeOfficefax;
    }
    
    @Column(name="EMPLOYEE_HOMEPHONE", length=20)

    public String getEmployeeHomephone() {
        return this.employeeHomephone;
    }
    
    public void setEmployeeHomephone(String employeeHomephone) {
        this.employeeHomephone = employeeHomephone;
    }
    
    @Column(name="EMPLOYEE_HOMEADDRESS", length=100)

    public String getEmployeeHomeaddress() {
        return this.employeeHomeaddress;
    }
    
    public void setEmployeeHomeaddress(String employeeHomeaddress) {
        this.employeeHomeaddress = employeeHomeaddress;
    }
    
    @Column(name="EMPLOYEE_MOBILE", length=20)

    public String getEmployeeMobile() {
        return this.employeeMobile;
    }
    
    public void setEmployeeMobile(String employeeMobile) {
        this.employeeMobile = employeeMobile;
    }
    
    @Column(name="EMPLOYEE_POLITICSTATUS", length=50)

    public String getEmployeePoliticstatus() {
        return this.employeePoliticstatus;
    }
    
    public void setEmployeePoliticstatus(String employeePoliticstatus) {
        this.employeePoliticstatus = employeePoliticstatus;
    }
    
    @Column(name="EMPLOYEE_ACADEMICTITLE", length=30)

    public String getEmployeeAcademictitle() {
        return this.employeeAcademictitle;
    }
    
    public void setEmployeeAcademictitle(String employeeAcademictitle) {
        this.employeeAcademictitle = employeeAcademictitle;
    }
    
    @Column(name="EMPLOYEE_EDUCATIONRECORD", length=10)

    public String getEmployeeEducationrecord() {
        return this.employeeEducationrecord;
    }
    
    public void setEmployeeEducationrecord(String employeeEducationrecord) {
        this.employeeEducationrecord = employeeEducationrecord;
    }
    
    @Column(name="EMPLOYEE_PHOTO")

    public String getEmployeePhoto() {
        return this.employeePhoto;
    }
    
    public void setEmployeePhoto(String employeePhoto) {
        this.employeePhoto = employeePhoto;
    }
    
    @Column(name="EMPLOYEE_PHOTOTYPE", length=10)

    public String getEmployeePhototype() {
        return this.employeePhototype;
    }
    
    public void setEmployeePhototype(String employeePhototype) {
        this.employeePhototype = employeePhototype;
    }
    
    @Column(name="TABINDEX", nullable=false, precision=8, scale=0)

    public Long getTabindex() {
        return this.tabindex;
    }
    
    public void setTabindex(Long tabindex) {
        this.tabindex = tabindex;
    }
    
    @Column(name="EMPLOYEE_ISDEPARTMENTMANAGER", length=10)

    public String getEmployeeIsdepartmentmanager() {
        return this.employeeIsdepartmentmanager;
    }
    
    public void setEmployeeIsdepartmentmanager(String employeeIsdepartmentmanager) {
        this.employeeIsdepartmentmanager = employeeIsdepartmentmanager;
    }
    
    @Column(name="EMPLOYEE_ISDELETED", precision=1, scale=0)

    public Long getEmployeeIsdeleted() {
        return this.employeeIsdeleted;
    }
    
    public void setEmployeeIsdeleted(Long employeeIsdeleted) {
        this.employeeIsdeleted = employeeIsdeleted;
    }
    
    @Column(name="EMPLOYEE_ISDELETEDDESC", length=200)

    public String getEmployeeIsdeleteddesc() {
        return this.employeeIsdeleteddesc;
    }
    
    public void setEmployeeIsdeleteddesc(String employeeIsdeleteddesc) {
        this.employeeIsdeleteddesc = employeeIsdeleteddesc;
    }
    
    @Column(name="EMPLOYEE_ISALIAS", precision=1, scale=0)

    public Long getEmployeeIsalias() {
        return this.employeeIsalias;
    }
    
    public void setEmployeeIsalias(Long employeeIsalias) {
        this.employeeIsalias = employeeIsalias;
    }
    
    @Column(name="SMSCOUNT", precision=10, scale=0)

    public Long getSmscount() {
        return this.smscount;
    }
    
    public void setSmscount(Long smscount) {
        this.smscount = smscount;
    }
    
    @Column(name="RCID_OA", length=510)

    public String getRcidOa() {
        return this.rcidOa;
    }
    
    public void setRcidOa(String rcidOa) {
        this.rcidOa = rcidOa;
    }
    
    @Column(name="GUID_OA", length=38)

    public String getGuidOa() {
        return this.guidOa;
    }
    
    public void setGuidOa(String guidOa) {
        this.guidOa = guidOa;
    }

	@Override
	public String toString() {
		return "RisenetEmployee [id=" + id + ", employeeLoginname="
				+ employeeLoginname + ", employeePassword=" + employeePassword
				+ ", employeeName=" + employeeName + ", employeeEmail="
				+ employeeEmail + ", employeeJobtitles=" + employeeJobtitles
				+ ", employeeProfessionaltitle=" + employeeProfessionaltitle
				+ ", employeeStatus=" + employeeStatus + ", employeeSex="
				+ employeeSex + ", employeeBirthday=" + employeeBirthday
				+ ", employeeCountry=" + employeeCountry
				+ ", employeeProvince=" + employeeProvince + ", employeeCity="
				+ employeeCity + ", employeeOfficeaddress="
				+ employeeOfficeaddress + ", employeeOfficephone="
				+ employeeOfficephone + ", employeeOfficezipcode="
				+ employeeOfficezipcode + ", employeeOfficefax="
				+ employeeOfficefax + ", employeeHomephone="
				+ employeeHomephone + ", employeeHomeaddress="
				+ employeeHomeaddress + ", employeeMobile=" + employeeMobile
				+ ", employeePoliticstatus=" + employeePoliticstatus
				+ ", employeeAcademictitle=" + employeeAcademictitle
				+ ", employeeEducationrecord=" + employeeEducationrecord
				+ ", employeePhoto=" + employeePhoto + ", employeePhototype="
				+ employeePhototype + ", tabindex=" + tabindex
				+ ", employeeIsdepartmentmanager="
				+ employeeIsdepartmentmanager + ", employeeIsdeleted="
				+ employeeIsdeleted + ", employeeIsdeleteddesc="
				+ employeeIsdeleteddesc + ", employeeIsalias="
				+ employeeIsalias + ", smscount=" + smscount + ", rcidOa="
				+ rcidOa + ", guidOa=" + guidOa + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((employeeAcademictitle == null) ? 0 : employeeAcademictitle
						.hashCode());
		result = prime
				* result
				+ ((employeeBirthday == null) ? 0 : employeeBirthday.hashCode());
		result = prime * result
				+ ((employeeCity == null) ? 0 : employeeCity.hashCode());
		result = prime * result
				+ ((employeeCountry == null) ? 0 : employeeCountry.hashCode());
		result = prime
				* result
				+ ((employeeEducationrecord == null) ? 0
						: employeeEducationrecord.hashCode());
		result = prime * result
				+ ((employeeEmail == null) ? 0 : employeeEmail.hashCode());
		result = prime
				* result
				+ ((employeeHomeaddress == null) ? 0 : employeeHomeaddress
						.hashCode());
		result = prime
				* result
				+ ((employeeHomephone == null) ? 0 : employeeHomephone
						.hashCode());
		result = prime * result
				+ ((employeeIsalias == null) ? 0 : employeeIsalias.hashCode());
		result = prime
				* result
				+ ((employeeIsdeleted == null) ? 0 : employeeIsdeleted
						.hashCode());
		result = prime
				* result
				+ ((employeeIsdeleteddesc == null) ? 0 : employeeIsdeleteddesc
						.hashCode());
		result = prime
				* result
				+ ((employeeIsdepartmentmanager == null) ? 0
						: employeeIsdepartmentmanager.hashCode());
		result = prime
				* result
				+ ((employeeJobtitles == null) ? 0 : employeeJobtitles
						.hashCode());
		result = prime
				* result
				+ ((employeeLoginname == null) ? 0 : employeeLoginname
						.hashCode());
		result = prime * result
				+ ((employeeMobile == null) ? 0 : employeeMobile.hashCode());
		result = prime * result
				+ ((employeeName == null) ? 0 : employeeName.hashCode());
		result = prime
				* result
				+ ((employeeOfficeaddress == null) ? 0 : employeeOfficeaddress
						.hashCode());
		result = prime
				* result
				+ ((employeeOfficefax == null) ? 0 : employeeOfficefax
						.hashCode());
		result = prime
				* result
				+ ((employeeOfficephone == null) ? 0 : employeeOfficephone
						.hashCode());
		result = prime
				* result
				+ ((employeeOfficezipcode == null) ? 0 : employeeOfficezipcode
						.hashCode());
		result = prime
				* result
				+ ((employeePassword == null) ? 0 : employeePassword.hashCode());
		result = prime * result
				+ ((employeePhoto == null) ? 0 : employeePhoto.hashCode());
		result = prime
				* result
				+ ((employeePhototype == null) ? 0 : employeePhototype
						.hashCode());
		result = prime
				* result
				+ ((employeePoliticstatus == null) ? 0 : employeePoliticstatus
						.hashCode());
		result = prime
				* result
				+ ((employeeProfessionaltitle == null) ? 0
						: employeeProfessionaltitle.hashCode());
		result = prime
				* result
				+ ((employeeProvince == null) ? 0 : employeeProvince.hashCode());
		result = prime * result
				+ ((employeeSex == null) ? 0 : employeeSex.hashCode());
		result = prime * result
				+ ((employeeStatus == null) ? 0 : employeeStatus.hashCode());
		result = prime * result + ((guidOa == null) ? 0 : guidOa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rcidOa == null) ? 0 : rcidOa.hashCode());
		result = prime * result
				+ ((smscount == null) ? 0 : smscount.hashCode());
		result = prime * result
				+ ((tabindex == null) ? 0 : tabindex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RisenetEmployee other = (RisenetEmployee) obj;
		if (employeeAcademictitle == null) {
			if (other.employeeAcademictitle != null)
				return false;
		} else if (!employeeAcademictitle.equals(other.employeeAcademictitle))
			return false;
		if (employeeBirthday == null) {
			if (other.employeeBirthday != null)
				return false;
		} else if (!employeeBirthday.equals(other.employeeBirthday))
			return false;
		if (employeeCity == null) {
			if (other.employeeCity != null)
				return false;
		} else if (!employeeCity.equals(other.employeeCity))
			return false;
		if (employeeCountry == null) {
			if (other.employeeCountry != null)
				return false;
		} else if (!employeeCountry.equals(other.employeeCountry))
			return false;
		if (employeeEducationrecord == null) {
			if (other.employeeEducationrecord != null)
				return false;
		} else if (!employeeEducationrecord
				.equals(other.employeeEducationrecord))
			return false;
		if (employeeEmail == null) {
			if (other.employeeEmail != null)
				return false;
		} else if (!employeeEmail.equals(other.employeeEmail))
			return false;
		if (employeeHomeaddress == null) {
			if (other.employeeHomeaddress != null)
				return false;
		} else if (!employeeHomeaddress.equals(other.employeeHomeaddress))
			return false;
		if (employeeHomephone == null) {
			if (other.employeeHomephone != null)
				return false;
		} else if (!employeeHomephone.equals(other.employeeHomephone))
			return false;
		if (employeeIsalias == null) {
			if (other.employeeIsalias != null)
				return false;
		} else if (!employeeIsalias.equals(other.employeeIsalias))
			return false;
		if (employeeIsdeleted == null) {
			if (other.employeeIsdeleted != null)
				return false;
		} else if (!employeeIsdeleted.equals(other.employeeIsdeleted))
			return false;
		if (employeeIsdeleteddesc == null) {
			if (other.employeeIsdeleteddesc != null)
				return false;
		} else if (!employeeIsdeleteddesc.equals(other.employeeIsdeleteddesc))
			return false;
		if (employeeIsdepartmentmanager == null) {
			if (other.employeeIsdepartmentmanager != null)
				return false;
		} else if (!employeeIsdepartmentmanager
				.equals(other.employeeIsdepartmentmanager))
			return false;
		if (employeeJobtitles == null) {
			if (other.employeeJobtitles != null)
				return false;
		} else if (!employeeJobtitles.equals(other.employeeJobtitles))
			return false;
		if (employeeLoginname == null) {
			if (other.employeeLoginname != null)
				return false;
		} else if (!employeeLoginname.equals(other.employeeLoginname))
			return false;
		if (employeeMobile == null) {
			if (other.employeeMobile != null)
				return false;
		} else if (!employeeMobile.equals(other.employeeMobile))
			return false;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		if (employeeOfficeaddress == null) {
			if (other.employeeOfficeaddress != null)
				return false;
		} else if (!employeeOfficeaddress.equals(other.employeeOfficeaddress))
			return false;
		if (employeeOfficefax == null) {
			if (other.employeeOfficefax != null)
				return false;
		} else if (!employeeOfficefax.equals(other.employeeOfficefax))
			return false;
		if (employeeOfficephone == null) {
			if (other.employeeOfficephone != null)
				return false;
		} else if (!employeeOfficephone.equals(other.employeeOfficephone))
			return false;
		if (employeeOfficezipcode == null) {
			if (other.employeeOfficezipcode != null)
				return false;
		} else if (!employeeOfficezipcode.equals(other.employeeOfficezipcode))
			return false;
		if (employeePassword == null) {
			if (other.employeePassword != null)
				return false;
		} else if (!employeePassword.equals(other.employeePassword))
			return false;
		if (employeePhoto == null) {
			if (other.employeePhoto != null)
				return false;
		} else if (!employeePhoto.equals(other.employeePhoto))
			return false;
		if (employeePhototype == null) {
			if (other.employeePhototype != null)
				return false;
		} else if (!employeePhototype.equals(other.employeePhototype))
			return false;
		if (employeePoliticstatus == null) {
			if (other.employeePoliticstatus != null)
				return false;
		} else if (!employeePoliticstatus.equals(other.employeePoliticstatus))
			return false;
		if (employeeProfessionaltitle == null) {
			if (other.employeeProfessionaltitle != null)
				return false;
		} else if (!employeeProfessionaltitle
				.equals(other.employeeProfessionaltitle))
			return false;
		if (employeeProvince == null) {
			if (other.employeeProvince != null)
				return false;
		} else if (!employeeProvince.equals(other.employeeProvince))
			return false;
		if (employeeSex == null) {
			if (other.employeeSex != null)
				return false;
		} else if (!employeeSex.equals(other.employeeSex))
			return false;
		if (employeeStatus == null) {
			if (other.employeeStatus != null)
				return false;
		} else if (!employeeStatus.equals(other.employeeStatus))
			return false;
		if (guidOa == null) {
			if (other.guidOa != null)
				return false;
		} else if (!guidOa.equals(other.guidOa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rcidOa == null) {
			if (other.rcidOa != null)
				return false;
		} else if (!rcidOa.equals(other.rcidOa))
			return false;
		if (smscount == null) {
			if (other.smscount != null)
				return false;
		} else if (!smscount.equals(other.smscount))
			return false;
		if (tabindex == null) {
			if (other.tabindex != null)
				return false;
		} else if (!tabindex.equals(other.tabindex))
			return false;
		return true;
	}
   
    
	






}