package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;



/**
 * TShenpiguochengId entity.
 * 
 * @author MyEclipse Persistence Tools
 */


public class TShenpiguochengUnits  implements Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ywlsh;	//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,	业务流水号
     private Long sjbbh;	//	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,	数据版本号
     private String dqhjmc; // 	"DQHJMC" VARCHAR2(50 BYTE) NOT NULL ,			当前环节名称


    // Constructors

    /** default constructor */
    public TShenpiguochengUnits() {
    }

    
    /** full constructor */
    public TShenpiguochengUnits(String ywlsh, Long sjbbh, String dqhjmc) {
        this.ywlsh = ywlsh;
        this.sjbbh = sjbbh;
        this.dqhjmc = dqhjmc;
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

    @Column(name="DQHJMC", nullable=false, length=50)

    public String getDqhjmc() {
        return this.dqhjmc;
    }
    
    public void setDqhjmc(String dqhjmc) {
        this.dqhjmc = dqhjmc;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TShenpiguochengUnits) ) return false;
		 TShenpiguochengUnits castOther = ( TShenpiguochengUnits ) other; 
         
		 return ( (this.getYwlsh()==castOther.getYwlsh()) || ( this.getYwlsh()!=null && castOther.getYwlsh()!=null && this.getYwlsh().equals(castOther.getYwlsh()) ) )
 && ( (this.getSjbbh()==castOther.getSjbbh()) || ( this.getSjbbh()!=null && castOther.getSjbbh()!=null && this.getSjbbh().equals(castOther.getSjbbh()) ) )
 && ( (this.getDqhjmc()==castOther.getDqhjmc()) || ( this.getDqhjmc()!=null && castOther.getDqhjmc()!=null && this.getDqhjmc().equals(castOther.getDqhjmc()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getYwlsh() == null ? 0 : this.getYwlsh().hashCode() );
         result = 37 * result + ( getSjbbh() == null ? 0 : this.getSjbbh().hashCode() );
         result = 37 * result + ( getDqhjmc() == null ? 0 : this.getDqhjmc().hashCode() );
         return result;
   }


@Override
public String toString() {
	return "TShenpiguochengUnits [ywlsh=" + ywlsh + ", sjbbh=" + sjbbh
			+ ", dqhjmc=" + dqhjmc + "]";
}   

   



}