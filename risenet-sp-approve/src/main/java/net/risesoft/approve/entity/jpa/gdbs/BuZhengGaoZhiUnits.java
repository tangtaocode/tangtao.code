package net.risesoft.approve.entity.jpa.gdbs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * BuZhengGaoZhiId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class BuZhengGaoZhiUnits  implements Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private String sblsh_short;	//申办流水号简码
	/*数据版本号默认为1，如果报送数据后发生改变需要重报（误操作或者数据修改）的，以2、3、4……每次加1的方式填写版本号；前置机方式对接时需要使*/
	@Column(name = "VERSION")
	private String version;


	// Constructors

	/** default constructor */
	public BuZhengGaoZhiUnits() {
	}


	/** full constructor */
	public BuZhengGaoZhiUnits(String sblsh_short, String version) {
		this.sblsh_short = sblsh_short;
		this.version = version;
	}


	// Property accessors

	@Column(name="SBLSH_SHORT", nullable=false)
	public String getSblsh_short() {
		return this.sblsh_short;
	}

	public void setSblsh_short(String sblsh_short) {
		this.sblsh_short = sblsh_short;
	}

	@Column(name="SJBBH", nullable=false, precision=4, scale=0)

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}




	public boolean equals(Object other) {
		if ( (this == other ) ) return true;
		if ( (other == null ) ) return false;
		if ( !(other instanceof BuZhengGaoZhiUnits) ) return false;
		BuZhengGaoZhiUnits castOther = ( BuZhengGaoZhiUnits ) other; 

		return ( (this.getSblsh_short()==castOther.getSblsh_short()) || ( this.getSblsh_short()!=null && castOther.getSblsh_short()!=null && this.getSblsh_short().equals(castOther.getSblsh_short()) ) )
				&& ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) );
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + ( getSblsh_short() == null ? 0 : this.getSblsh_short().hashCode() );
		result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
		return result;
	}
}