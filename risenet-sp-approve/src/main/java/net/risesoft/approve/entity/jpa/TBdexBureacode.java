package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_BDEX_BUREACODE")
public class TBdexBureacode implements Serializable{

	private String bureaguid;
	private String bureacode;
	@Id
	@Column(name = "BUREAGUID", length = 38, nullable = false)
	public String getBureaguid() {
		return bureaguid;
	}
	public void setBureaguid(String bureaguid) {
		this.bureaguid = bureaguid;
	}
	@Column(name = "BUREACODE",  nullable = true)
	public String getBureacode() {
		return bureacode;
	}
	public void setBureacode(String bureacode) {
		this.bureacode = bureacode;
	}
	public TBdexBureacode(String bureaguid, String bureacode) {
		super();
		this.bureaguid = bureaguid;
		this.bureacode = bureacode;
	}
	public TBdexBureacode() {
		super();
	}
	
	
	
}
