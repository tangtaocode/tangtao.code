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
@Table(name="T_BDEX_DOCTYPE")
public class TBdexDoctype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String bguid;

	private String doctypecode;

	private String doctypename;

	private String memo;

	@Temporal(TemporalType.DATE)
	private Date xgdate;

	private BigDecimal xxnum;

	public TBdexDoctype() {
	}
	@Column(name="GUID", length=100)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column(name="BGUID", length=100)
	public String getBguid() {
		return this.bguid;
	}

	public void setBguid(String bguid) {
		this.bguid = bguid;
	}
	@Column(name="DOCTYPECODE", length=100)
	public String getDoctypecode() {
		return this.doctypecode;
	}

	public void setDoctypecode(String doctypecode) {
		this.doctypecode = doctypecode;
	}
	@Column(name="DOCTYPENAME", length=100)
	public String getDoctypename() {
		return this.doctypename;
	}

	public void setDoctypename(String doctypename) {
		this.doctypename = doctypename;
	}
	@Column(name="MEMO", length=100)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name="XGDATE")
	public Date getXgdate() {
		return this.xgdate;
	}

	public void setXgdate(Date xgdate) {
		this.xgdate = xgdate;
	}
	@Column(name="XXNUM", length=100)
	public BigDecimal getXxnum() {
		return this.xxnum;
	}

	public void setXxnum(BigDecimal xxnum) {
		this.xxnum = xxnum;
	}

}