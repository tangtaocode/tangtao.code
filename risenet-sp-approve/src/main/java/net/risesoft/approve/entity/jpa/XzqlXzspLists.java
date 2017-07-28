package net.risesoft.approve.entity.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * XzqlXzspLists entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="XZQL_XZSP_LISTS")



public class XzqlXzspLists  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     //private String testid;			//主键          
     private String id;				//"ID" VARCHAR2(38) ,
     private String itemid;			//"ITEMID" VARCHAR2(38),事项ID
     private Long orderno;			//"ORDERNO" NUMBER(0),序号
     private String type;			//"TYPE" VARCHAR2(50),材料分类
     private String describe;		//"DESCRIBE" VARCHAR2(4000),材料描述
     private String materialname;	//"MATERIALNAME" VARCHAR2(4000)，材料名称
     private String doctypeguid;	//"DOCTYPEGUID"	 CLOB(4000)	,材料对应证照guid串
     private String materiallx;		//"MATERIALLX"	VARCHAR2(50),材料类型：0扫描件；1申请表格；
     private String doctypename;	//"DOCTYPENAME" CLOB(4000),材料对应证照NAME串


    // Constructors

    /** default constructor */
    public XzqlXzspLists() {
    }

	/** minimal constructor */
    public XzqlXzspLists(String id, String itemid) {
        //this.testid = testid;
        this.id = id;
        this.itemid = itemid;
    }
    
    /** full constructor */
    public XzqlXzspLists(String testid, String id, String itemid, Long orderno, String type, String describe, String materialname, String doctypeguid, String materiallx, String doctypename) {
        //this.testid = testid;
        this.id = id;
        this.itemid = itemid;
        this.orderno = orderno;
        this.type = type;
        this.describe = describe;
        this.materialname = materialname;
        this.doctypeguid = doctypeguid;
        this.materiallx = materiallx;
        this.doctypename = doctypename;
    }

   
    // Property accessors
   
    
    //@Column(name="TESTID", unique=true, nullable=false, length=50)

    /*public String getTestid() {
        return this.testid;
    }
    
    public void setTestid(String testid) {
        this.testid = testid;
    }*/
    @Id 
    @Column(name="ID",unique=true, nullable=false, length=50)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="ITEMID", nullable=false, length=50)

    public String getItemid() {
        return this.itemid;
    }
    
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }
    
    @Column(name="ORDERNO", precision=22, scale=0)

    public Long getOrderno() {
        return this.orderno;
    }
    
    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }
    
    @Column(name="TYPE", length=50)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="DESCRIBE", length=4000)

    public String getDescribe() {
        return this.describe;
    }
    
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    
    @Column(name="MATERIALNAME", length=4000)

    public String getMaterialname() {
        return this.materialname;
    }
    
    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }
    
    @Column(name="DOCTYPEGUID")

    public String getDoctypeguid() {
        return this.doctypeguid;
    }
    
    public void setDoctypeguid(String doctypeguid) {
        this.doctypeguid = doctypeguid;
    }
    
    @Column(name="MATERIALLX", length=50)

    public String getMateriallx() {
        return this.materiallx;
    }
    
    public void setMateriallx(String materiallx) {
        this.materiallx = materiallx;
    }
    
    @Column(name="DOCTYPENAME")

    public String getDoctypename() {
        return this.doctypename;
    }
    
    public void setDoctypename(String doctypename) {
        this.doctypename = doctypename;
    }
   








}