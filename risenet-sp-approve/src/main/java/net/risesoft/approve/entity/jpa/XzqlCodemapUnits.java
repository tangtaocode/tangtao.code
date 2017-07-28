package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * XzqlCodemapId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable

public class XzqlCodemapUnits  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String type;	//"TYPE" VARCHAR2(50 BYTE) NOT NULL ,类别名称：如公开形式
     private String code;	//"CODE" VARCHAR2(50 BYTE) NOT NULL ,键内容：如：001、002


    // Constructors

    /** default constructor */
    public XzqlCodemapUnits() {
    }

    
    /** full constructor */
    public XzqlCodemapUnits(String type, String code) {
        this.type = type;
        this.code = code;
    }

   
    // Property accessors

    @Column(name="TYPE", nullable=false, length=50)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    @Column(name="CODE", nullable=false, length=50)

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof XzqlCodemapUnits) ) return false;
		 XzqlCodemapUnits castOther = ( XzqlCodemapUnits ) other; 
         
		 return ( (this.getType()==castOther.getType()) || ( this.getType()!=null && castOther.getType()!=null && this.getType().equals(castOther.getType()) ) )
 && ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getType() == null ? 0 : this.getType().hashCode() );
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         return result;
   }


@Override
public String toString() {
	return "XzqlCodemapUnits [type=" + type + ", code=" + code + "]";
}   

   



}