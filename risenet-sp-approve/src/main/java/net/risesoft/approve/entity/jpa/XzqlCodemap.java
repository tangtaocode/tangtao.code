package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * XzqlCodemap entity.
 * 数据字典表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XZQL_CODEMAP"
)

public class XzqlCodemap  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private XzqlCodemapUnits xzqlcodemapunits;	//"TYPE" VARCHAR2(50 BYTE) NOT NULL ,类别名称：如公开形式ʽ
     											//"CODE" VARCHAR2(50 BYTE) NOT NULL ,键内容：如：001、002
     private String id;							//"ID" VARCHAR2(50 BYTE) NULL ,		主键ID
     private String value;						//"VALUE" VARCHAR2(500 BYTE) NULL ,	ֵ值内容:如：文件、公开栏
     private Long orderno;						//"ORDERNO" NUMBER NULL ,			排序：用于显示的顺序
     private String remark;						//"REMARK" VARCHAR2(500 BYTE) NULL ,备注说明
     private String status;						//"STATUS" VARCHAR2(1 BYTE) DEFAULT 1  NOT NULL ,是否有效：1、有效，0、无效


    // Constructors

    /** default constructor */
    public XzqlCodemap() {
    }

	/** minimal constructor */
    public XzqlCodemap(XzqlCodemapUnits xzqlcodemapunits, String status) {
        this.xzqlcodemapunits = xzqlcodemapunits;
        this.status = status;
    }
    
    /** full constructor */
    public XzqlCodemap(XzqlCodemapUnits xzqlcodemapunits, String id, String value, Long orderno, String remark, String status) {
        this.xzqlcodemapunits = xzqlcodemapunits;
        this.id = id;
        this.value = value;
        this.orderno = orderno;
        this.remark = remark;
        this.status = status;
    }

   
    @EmbeddedId
    public XzqlCodemapUnits getxzqlcodemapunits() {
        return this.xzqlcodemapunits;
    }
    
    public void setxzqlcodemapunits(XzqlCodemapUnits xzqlcodemapunits) {
        this.xzqlcodemapunits = xzqlcodemapunits;
    }
    
    @Column(name="xzqlcodemapunits", length=50)

    public String getid() {
        return this.id;
    }
    
    public void setid(String id) {
        this.id = id;
    }
    
    @Column(name="VALUE", length=500)

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Column(name="ORDERNO", precision=22, scale=0)

    public Long getOrderno() {
        return this.orderno;
    }
    
    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }
    
    @Column(name="REMARK", length=500)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="STATUS", nullable=false, length=1)

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderno == null) ? 0 : orderno.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime
				* result
				+ ((xzqlcodemapunits == null) ? 0 : xzqlcodemapunits.hashCode());
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
		XzqlCodemap other = (XzqlCodemap) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderno == null) {
			if (other.orderno != null)
				return false;
		} else if (!orderno.equals(other.orderno))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (xzqlcodemapunits == null) {
			if (other.xzqlcodemapunits != null)
				return false;
		} else if (!xzqlcodemapunits.equals(other.xzqlcodemapunits))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XzqlCodemap [xzqlcodemapunits=" + xzqlcodemapunits + ", id="
				+ id + ", value=" + value + ", orderno=" + orderno
				+ ", remark=" + remark + ", status=" + status + "]";
	}
   
    







}