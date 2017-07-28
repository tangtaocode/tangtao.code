package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;

import javax.persistence.Column;



/**
 * TTebiechengxushenqingId entity.
 * 
 * @author MyEclipse Persistence Tools
 */


public class TebiechengxushenqingUnits  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String sblsh_short;		//	"YWLSH" VARCHAR2(100 BYTE) NOT NULL ,	业务流水号
     private String xh;			//	"XH" NUMBER(5) NOT NULL ,				序号


    // Constructors

    /** default constructor */
    public TebiechengxushenqingUnits() {
    }

    
    /** full constructor */
    public TebiechengxushenqingUnits(String sblsh_short, String xh) {
        this.sblsh_short = sblsh_short;
        
        this.xh = xh;
    }

   
    // Property accessors


    

    @Column(name="XH", nullable=false, precision=5, scale=0)
    public String getXh() {
        return this.xh;
    }
    
    
    @Column(name="SBLSH_SHORT", nullable=false, precision=5, scale=0)
    public String getSblsh_short() {
		return sblsh_short;
	}


	public void setSblsh_short(String sblsh_short) {
		this.sblsh_short = sblsh_short;
	}


	public void setXh(String xh) {
        this.xh = xh;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sblsh_short == null) ? 0 : sblsh_short.hashCode());
		result = prime * result + ((xh == null) ? 0 : xh.hashCode());
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
		TebiechengxushenqingUnits other = (TebiechengxushenqingUnits) obj;
		if (sblsh_short == null) {
			if (other.sblsh_short != null)
				return false;
		} else if (!sblsh_short.equals(other.sblsh_short))
			return false;
		if (xh == null) {
			if (other.xh != null)
				return false;
		} else if (!xh.equals(other.xh))
			return false;
		return true;
	}
   
	



   
   



}