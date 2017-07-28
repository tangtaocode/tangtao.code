package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the T_BDEX_DOCTYPE database table.
 * 
 */
@Entity
@Table(name="T_BDEX_DOCTYPEINFO")
public class TBdexDocTypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String doctypeguid;

	private String infocode;

	private String infomemo;
	
	private String infocoment;

	private String glguid;

	public TBdexDocTypeInfo() {
	}
	@Column(name="GUID", length=100)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column(name="DOCTYPEGUID", length=100)
	public String getDoctypeguid() {
		return doctypeguid;
	}
	public void setDoctypeguid(String doctypeguid) {
		this.doctypeguid = doctypeguid;
	}
	
	@Column(name="INFOCODE", length=100)
	public String getInfocode() {
		return infocode;
	}
	public void setInfocode(String infocode) {
		this.infocode = infocode;
	}
	
	@Column(name="INFOMEMO", length=100)
	public String getInfomemo() {
		return infomemo;
	}
	public void setInfomemo(String infomemo) {
		this.infomemo = infomemo;
	}
	
	@Column(name="INFOCOMENT", length=100)
	public String getInfocoment() {
		return infocoment;
	}
	public void setInfocoment(String infocoment) {
		this.infocoment = infocoment;
	}
	@Column(name="GLGUID", length=100)
	public String getGlguid() {
		return glguid;
	}
	public void setGlguid(String glguid) {
		this.glguid = glguid;
	}

}