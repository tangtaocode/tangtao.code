package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.jdbc.core.RowMapper;


/**
 * RisenetEmployeeUnits entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class RisenetEmployeeUnits  implements Serializable {


	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// Fields
	
	     private String employeeGuid;		//"EMPLOYEE_GUID" CHAR(38 BYTE) NOT NULL ,
	     private String departmentGuid;		//"DEPARTMENT_GUID" CHAR(38 BYTE) NOT NULL ,
	     private String bureauGuid;
	    public String getEmployeeGuid() {
	        return this.employeeGuid;
	    }
	    
	    public RisenetEmployeeUnits() {
		}
	
		public RisenetEmployeeUnits(String employeeGuid, String departmentGuid,
				String bureauGuid) {
			this.employeeGuid = employeeGuid;
			this.departmentGuid = departmentGuid;
			this.bureauGuid = bureauGuid;
		}
	
		public String getBureauGuid() {
			return bureauGuid;
		}
	
		public void setBureauGuid(String bureauGuid) {
			this.bureauGuid = bureauGuid;
		}
	
		public void setEmployeeGuid(String employeeGuid) {
	        this.employeeGuid = employeeGuid;
	    }
	
	
	    public String getDepartmentGuid() {
	        return this.departmentGuid;
	    }
	    
	    public void setDepartmentGuid(String departmentGuid) {
	        this.departmentGuid = departmentGuid;
	    }
	   
	
	
	
	   public boolean equals(Object other) {
	         if ( (this == other ) ) return true;
			 if ( (other == null ) ) return false;
			 if ( !(other instanceof RisenetEmployeeUnits) ) return false;
			 RisenetEmployeeUnits castOther = ( RisenetEmployeeUnits ) other; 
	         
			 return ( (this.getEmployeeGuid()==castOther.getEmployeeGuid()) || ( this.getEmployeeGuid()!=null && castOther.getEmployeeGuid()!=null && this.getEmployeeGuid().equals(castOther.getEmployeeGuid()) ) )
	 && ( (this.getDepartmentGuid()==castOther.getDepartmentGuid()) || ( this.getDepartmentGuid()!=null && castOther.getDepartmentGuid()!=null && this.getDepartmentGuid().equals(castOther.getDepartmentGuid()) ) );
	   }
	   
	   public int hashCode() {
	         int result = 17;
	         
	         result = 37 * result + ( getEmployeeGuid() == null ? 0 : this.getEmployeeGuid().hashCode() );
	         result = 37 * result + ( getDepartmentGuid() == null ? 0 : this.getDepartmentGuid().hashCode() );
	         return result;
	   }
	
	
	@Override
	public String toString() {
		return "RisenetEmployeeUnits [employeeGuid=" + employeeGuid
				+ ", departmentGuid=" + departmentGuid + "]";
	}


   



}