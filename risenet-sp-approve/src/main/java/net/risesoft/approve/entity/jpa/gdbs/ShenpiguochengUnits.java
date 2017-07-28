package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;

import javax.persistence.Column;



/**
 * TShenpiguochengId entity.
 * 
 * @author MyEclipse Persistence Tools
 */


public class ShenpiguochengUnits  implements Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "SBLSH_SHORT")
	private String sblsh_short;//申办流水号(简短)
	
	@Column(name = "SPHJDM")
     private String sphjdm;	//	"SJBBH" NUMBER(4) DEFAULT 1  NOT NULL ,	数据版本号
	public String getSblsh_short() {
		return sblsh_short;
	}
	public void setSblsh_short(String sblsh_short) {
		this.sblsh_short = sblsh_short;
	}
	public String getSphjdm() {
		return sphjdm;
	}
	public void setSphjdm(String sphjdm) {
		this.sphjdm = sphjdm;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sblsh_short == null) ? 0 : sblsh_short.hashCode());
		result = prime * result + ((sphjdm == null) ? 0 : sphjdm.hashCode());
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
		ShenpiguochengUnits other = (ShenpiguochengUnits) obj;
		if (sblsh_short == null) {
			if (other.sblsh_short != null)
				return false;
		} else if (!sblsh_short.equals(other.sblsh_short))
			return false;
		if (sphjdm == null) {
			if (other.sphjdm != null)
				return false;
		} else if (!sphjdm.equals(other.sphjdm))
			return false;
		return true;
	}
	public ShenpiguochengUnits(String sblsh_short, String sphjdm) {
		super();
		this.sblsh_short = sblsh_short;
		this.sphjdm = sphjdm;
	}
	public ShenpiguochengUnits() {
	}

	
	
    // Constructors

     
    
  

   



}