package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * XzqlXzspListtype entity.
 * 事项的材料类型表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XZQL_XZSP_LISTTYPE"
)

public class XzqlXzspListtype  implements Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private XzqlXzspListtypeUnits id;	//"TYPEGUID" VARCHAR2(50 BYTE) NOT NULL ,材料类型ID
     									//"ITEMID" VARCHAR2(50 BYTE) NOT NULL ,事项ID
     private String typename;			//"TYPENAME" VARCHAR2(500 BYTE) NOT NULL ,类型名称
     private Long orderno;				//"ORDERNO" NUMBER NULL ,			排序号
     private String supertypeguid;		//"SUPERTYPEGUID" VARCHAR2(50 BYTE) NULL 	父节点GUID


    // Constructors

    /** default constructor */
    public XzqlXzspListtype() {
    }

	/** minimal constructor */
    public XzqlXzspListtype(XzqlXzspListtypeUnits id, String typename) {
        this.id = id;
        this.typename = typename;
    }
    
    /** full constructor */
    public XzqlXzspListtype(XzqlXzspListtypeUnits id, String typename, Long orderno, String supertypeguid) {
        this.id = id;
        this.typename = typename;
        this.orderno = orderno;
        this.supertypeguid = supertypeguid;
    }

   
    // Property accessors
    @EmbeddedId
    public XzqlXzspListtypeUnits getId() {
        return this.id;
    }
    
    public void setId(XzqlXzspListtypeUnits id) {
        this.id = id;
    }
    
    @Column(name="TYPENAME", nullable=false, length=500)

    public String getTypename() {
        return this.typename;
    }
    
    public void setTypename(String typename) {
        this.typename = typename;
    }
    
    @Column(name="ORDERNO", precision=22, scale=0)

    public Long getOrderno() {
        return this.orderno;
    }
    
    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }
    
    @Column(name="SUPERTYPEGUID", length=50)

    public String getSupertypeguid() {
        return this.supertypeguid;
    }
    
    public void setSupertypeguid(String supertypeguid) {
        this.supertypeguid = supertypeguid;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderno == null) ? 0 : orderno.hashCode());
		result = prime * result
				+ ((supertypeguid == null) ? 0 : supertypeguid.hashCode());
		result = prime * result
				+ ((typename == null) ? 0 : typename.hashCode());
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
		XzqlXzspListtype other = (XzqlXzspListtype) obj;
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
		if (supertypeguid == null) {
			if (other.supertypeguid != null)
				return false;
		} else if (!supertypeguid.equals(other.supertypeguid))
			return false;
		if (typename == null) {
			if (other.typename != null)
				return false;
		} else if (!typename.equals(other.typename))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XzqlXzspListtype [id=" + id + ", typename=" + typename
				+ ", orderno=" + orderno + ", supertypeguid=" + supertypeguid
				+ "]";
	}
   


}