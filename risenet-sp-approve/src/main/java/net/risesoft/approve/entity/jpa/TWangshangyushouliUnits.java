package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * TWangshangyushouliId entity.
 * 网上预受理
 * @author
 */


public class TWangshangyushouliUnits  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String ywlsh;		//"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,		主键，调用函数createYWLSH()生成
     private String sjbbh;		//"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,	数据的版本号，默认为1


    // Constructors

    /** default constructor */
    public TWangshangyushouliUnits() {
    }

    
    /** full constructor */
    public TWangshangyushouliUnits(String ywlsh, String sjbbh) {
        this.ywlsh = ywlsh;
        this.sjbbh = sjbbh;
    }

   
    // Property accessors

    @Column(name="YWLSH", nullable=false, length=100)

    public String getYwlsh() {
        return this.ywlsh;
    }
    
    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    @Column(name="SJBBH", nullable=false, precision=4, scale=0)

    public String getSjbbh() {
        return this.sjbbh;
    }
    
    public void setSjbbh(String sjbbh) {
        this.sjbbh = sjbbh;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TWangshangyushouliUnits) ) return false;
		 TWangshangyushouliUnits castOther = ( TWangshangyushouliUnits ) other; 
         
		 return ( (this.getYwlsh()==castOther.getYwlsh()) || ( this.getYwlsh()!=null && castOther.getYwlsh()!=null && this.getYwlsh().equals(castOther.getYwlsh()) ) )
 && ( (this.getSjbbh()==castOther.getSjbbh()) || ( this.getSjbbh()!=null && castOther.getSjbbh()!=null && this.getSjbbh().equals(castOther.getSjbbh()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getYwlsh() == null ? 0 : this.getYwlsh().hashCode() );
         result = 37 * result + ( getSjbbh() == null ? 0 : this.getSjbbh().hashCode() );
         return result;
   }


@Override
public String toString() {
	return "TWangshangyushouliUnits [ywlsh=" + ywlsh + ", sjbbh=" + sjbbh + "]";
}   

   



}