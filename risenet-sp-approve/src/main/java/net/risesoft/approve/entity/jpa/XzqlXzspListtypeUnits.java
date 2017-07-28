package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;



/**
 * XzqlXzspListtypeId entity.
 * 
 * @author MyEclipse Persistence Tools
 */


public class XzqlXzspListtypeUnits  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String typeguid;	//"TYPEGUID" VARCHAR2(50 BYTE) NOT NULL ,材料类型ID
     private String itemid;		//"ITEMID" VARCHAR2(50 BYTE) NOT NULL ,事项ID


    // Constructors

    /** default constructor */
    public XzqlXzspListtypeUnits() {
    }

    
    /** full constructor */
    public XzqlXzspListtypeUnits(String typeguid, String itemid) {
        this.typeguid = typeguid;
        this.itemid = itemid;
    }

   
    // Property accessors

    @Column(name="TYPEGUID", nullable=false, length=50)

    public String getTypeguid() {
        return this.typeguid;
    }
    
    public void setTypeguid(String typeguid) {
        this.typeguid = typeguid;
    }

    @Column(name="ITEMID", nullable=false, length=50)

    public String getItemid() {
        return this.itemid;
    }
    
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof XzqlXzspListtypeUnits) ) return false;
		 XzqlXzspListtypeUnits castOther = ( XzqlXzspListtypeUnits ) other; 
         
		 return ( (this.getTypeguid()==castOther.getTypeguid()) || ( this.getTypeguid()!=null && castOther.getTypeguid()!=null && this.getTypeguid().equals(castOther.getTypeguid()) ) )
 && ( (this.getItemid()==castOther.getItemid()) || ( this.getItemid()!=null && castOther.getItemid()!=null && this.getItemid().equals(castOther.getItemid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTypeguid() == null ? 0 : this.getTypeguid().hashCode() );
         result = 37 * result + ( getItemid() == null ? 0 : this.getItemid().hashCode() );
         return result;
   }


@Override
public String toString() {
	return "XzqlXzspListtypeUnits [typeguid=" + typeguid + ", itemid=" + itemid
			+ "]";
}   

   



}