package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * TBujiaogaozhiId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable

public class TBujiaogaozhiUnits  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String ywlsh;			//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,		业务流水号
     private Long sjbbh;			//	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,		数据版本号


    // Constructors

    /** default constructor */
    public TBujiaogaozhiUnits() {
    }

    
    /** full constructor */
    public TBujiaogaozhiUnits(String ywlsh, Long sjbbh) {
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

    public Long getSjbbh() {
        return this.sjbbh;
    }
    
    public void setSjbbh(Long sjbbh) {
        this.sjbbh = sjbbh;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TBujiaogaozhiUnits) ) return false;
		 TBujiaogaozhiUnits castOther = ( TBujiaogaozhiUnits ) other; 
         
		 return ( (this.getYwlsh()==castOther.getYwlsh()) || ( this.getYwlsh()!=null && castOther.getYwlsh()!=null && this.getYwlsh().equals(castOther.getYwlsh()) ) )
 && ( (this.getSjbbh()==castOther.getSjbbh()) || ( this.getSjbbh()!=null && castOther.getSjbbh()!=null && this.getSjbbh().equals(castOther.getSjbbh()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getYwlsh() == null ? 0 : this.getYwlsh().hashCode() );
         result = 37 * result + ( getSjbbh() == null ? 0 : this.getSjbbh().hashCode() );
         return result;
   }   





}