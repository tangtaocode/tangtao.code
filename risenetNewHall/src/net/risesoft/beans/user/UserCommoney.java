package net.risesoft.beans.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T_OUT_COMMONEY")
public class UserCommoney implements Serializable {

	private static final long serialVersionUID = 8514769862344193885L;
	private String guid; //
	private Double yysr; // 上一年度营业收入
	private Integer cyrs; // 上一年末从业人数
	private Double nsze; // 上一年度纳税总额
	private Integer isold; // 数据版本
	private Date createdate;

	@Id
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column
	public Double getYysr() {
		return yysr;
	}

	public void setYysr(Double yysr) {
		this.yysr = yysr;
	}

	@Column
	public Integer getCyrs() {
		return cyrs;
	}

	public void setCyrs(Integer cyrs) {
		this.cyrs = cyrs;
	}

	@Column
	public Double getNsze() {
		return nsze;
	}

	public void setNsze(Double nsze) {
		this.nsze = nsze;
	}

	@Column
	public Integer getIsold() {
		return isold;
	}

	public void setIsold(Integer isold) {
		this.isold = isold;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCommoney other = (UserCommoney) obj;
		if (this.guid == null) {
			if (other.guid != null)
				return false;
		} else if (!this.guid.equals(other.guid))
			return false;
		
		if (this.yysr == null) {
			if (other.yysr != null)
				return false;
		} else if (!this.yysr.equals(other.yysr))
			return false;

		if (this.cyrs == null) {
			if (other.cyrs != null)
				return false;
		} else if (!this.cyrs.equals(other.cyrs))
			return false;

		if (this.nsze == null) {
			if (other.nsze != null)
				return false;
		} else if (!this.nsze.equals(other.nsze))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ (this.guid == null ? 0 : this.guid.hashCode());
		result = prime * result
				+ (this.yysr == null ? 0 : this.yysr.hashCode());
		result = prime * result
				+ (this.cyrs == null ? 0 : this.cyrs.hashCode());
		result = prime * result
				+ (this.nsze == null ? 0 : this.nsze.hashCode());
		return result;
	}

}
