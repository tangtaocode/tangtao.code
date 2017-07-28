package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * AbstractRisenetFile entity provides the base persistence definition of the
 * RisenetFile entity.
 * 窗口扫描的文件表
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RISENET_FILE")
public  class RisenetFile  implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String fileguid;				//	"FILEGUID" VARCHAR2(38 BYTE) NOT NULL ,
     private String filename;				//	"FILENAME" VARCHAR2(300 BYTE) NULL ,
     private String filenameext;			//	"FILENAMEEXT" VARCHAR2(10 BYTE) NULL ,
     private String titile;					//	"TITILE" VARCHAR2(300 BYTE) NULL ,
     private String majorname;				//	"MAJORNAME" VARCHAR2(100 BYTE) NULL ,
     private Long majorversion;				//	"MAJORVERSION" NUMBER(10) NULL ,
     private Long minversion;				//	"MINVERSION" NUMBER(10) NULL ,
     private String appname;				//	"APPNAME" VARCHAR2(100 BYTE) NULL ,
     private String fileboxname;			//	"FILEBOXNAME" VARCHAR2(100 BYTE) NULL ,
     private String appinstguid;			//	"APPINSTGUID" VARCHAR2(38 BYTE) NULL ,
     private String creatorguid;			//	"CREATORGUID" VARCHAR2(38 BYTE) NULL ,
     private Date createdate;				//	"CREATEDATE" DATE NULL ,
     private Date lastmodified;				//	"LASTMODIFIED" DATE NULL ,
     private String realfullpath;			// 	"REALFULLPATH" VARCHAR2(300 BYTE) NULL ,	
     private String contentuniquecode;		// 	"CONTENTUNIQUECODE" VARCHAR2(100 BYTE) NULL ,
     private String filetype;				// 	"FILETYPE" VARCHAR2(10 BYTE) NULL ,
     private String savetype;				// 	"SAVETYPE" VARCHAR2(10 BYTE) NULL ,
     private String prefile;				// 	"PREFILE" VARCHAR2(10 BYTE) NULL ,
     private String handles;				// 	"HANDLES" VARCHAR2(500 BYTE) NULL ,
     private String content;				// 	"CONTENT" BLOB NULL ,
     private Double filesize;				// 	"FILESIZE" NUMBER(15,4) NULL ,
     private String corrguid;				// 	"CORRGUID" CHAR(38 BYTE) NULL ,
     private String isback;					// 	"ISBACK" VARCHAR2(1 BYTE) DEFAULT 0  NULL ，是否备份
     

    // Constructors

    /** default constructor */
    public RisenetFile() {
    }

	/** minimal constructor */
    public RisenetFile(String fileguid) {
        this.fileguid = fileguid;
    }
    
    /** full constructor */
    public RisenetFile(String fileguid, String filename, String filenameext, String titile, String majorname, Long majorversion, Long minversion, String appname, String fileboxname, String appinstguid, String creatorguid, Date createdate, Date lastmodified, String realfullpath, String contentuniquecode, String filetype, String savetype, String prefile, String handles, String content, Double filesize, String corrguid, String isback) {
        this.fileguid = fileguid;
        this.filename = filename;
        this.filenameext = filenameext;
        this.titile = titile;
        this.majorname = majorname;
        this.majorversion = majorversion;
        this.minversion = minversion;
        this.appname = appname;
        this.fileboxname = fileboxname;
        this.appinstguid = appinstguid;
        this.creatorguid = creatorguid;
        this.createdate = createdate;
        this.lastmodified = lastmodified;
        this.realfullpath = realfullpath;
        this.contentuniquecode = contentuniquecode;
        this.filetype = filetype;
        this.savetype = savetype;
        this.prefile = prefile;
        this.handles = handles;
        this.content = content;
        this.filesize = filesize;
        this.corrguid = corrguid;
        this.isback = isback;
    }

   
    // Property accessors
    @Id
   	@Column(name = "FILEGUID", length = 38, nullable = false)
   	@GeneratedValue(generator = "uuid")
   	@GenericGenerator(name = "uuid", strategy = "assigned") 
   public String getFileguid() {
        return this.fileguid;
    }
    
    public void setFileguid(String fileguid) {
        this.fileguid = fileguid;
    }
    
    @Column(name="FILENAME", length=300)

    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    @Column(name="FILENAMEEXT", length=10)

    public String getFilenameext() {
        return this.filenameext;
    }
    
    public void setFilenameext(String filenameext) {
        this.filenameext = filenameext;
    }
    
    @Column(name="TITILE", length=300)

    public String getTitile() {
        return this.titile;
    }
    
    public void setTitile(String titile) {
        this.titile = titile;
    }
    
    @Column(name="MAJORNAME", length=100)

    public String getMajorname() {
        return this.majorname;
    }
    
    public void setMajorname(String majorname) {
        this.majorname = majorname;
    }
    
    @Column(name="MAJORVERSION", precision=10, scale=0)

    public Long getMajorversion() {
        return this.majorversion;
    }
    
    public void setMajorversion(Long majorversion) {
        this.majorversion = majorversion;
    }
    
    @Column(name="MINVERSION", precision=10, scale=0)

    public Long getMinversion() {
        return this.minversion;
    }
    
    public void setMinversion(Long minversion) {
        this.minversion = minversion;
    }
    
    @Column(name="APPNAME", length=100)

    public String getAppname() {
        return this.appname;
    }
    
    public void setAppname(String appname) {
        this.appname = appname;
    }
    
    @Column(name="FILEBOXNAME", length=100)

    public String getFileboxname() {
        return this.fileboxname;
    }
    
    public void setFileboxname(String fileboxname) {
        this.fileboxname = fileboxname;
    }
    
    @Column(name="APPINSTGUID", length=38)

    public String getAppinstguid() {
        return this.appinstguid;
    }
    
    public void setAppinstguid(String appinstguid) {
        this.appinstguid = appinstguid;
    }
    
    @Column(name="CREATORGUID", length=38)

    public String getCreatorguid() {
        return this.creatorguid;
    }
    
    public void setCreatorguid(String creatorguid) {
        this.creatorguid = creatorguid;
    }
@Temporal(TemporalType.DATE)
    @Column(name="CREATEDATE", length=7)

    public Date getCreatedate() {
        return this.createdate;
    }
    
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
@Temporal(TemporalType.DATE)
    @Column(name="LASTMODIFIED", length=7)

    public Date getLastmodified() {
        return this.lastmodified;
    }
    
    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }
    
    @Column(name="REALFULLPATH", length=300)

    public String getRealfullpath() {
        return this.realfullpath;
    }
    
    public void setRealfullpath(String realfullpath) {
        this.realfullpath = realfullpath;
    }
    
    @Column(name="CONTENTUNIQUECODE", length=100)

    public String getContentuniquecode() {
        return this.contentuniquecode;
    }
    
    public void setContentuniquecode(String contentuniquecode) {
        this.contentuniquecode = contentuniquecode;
    }
    
    @Column(name="FILETYPE", length=10)

    public String getFiletype() {
        return this.filetype;
    }
    
    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
    
    @Column(name="SAVETYPE", length=10)

    public String getSavetype() {
        return this.savetype;
    }
    
    public void setSavetype(String savetype) {
        this.savetype = savetype;
    }
    
    @Column(name="PREFILE", length=10)

    public String getPrefile() {
        return this.prefile;
    }
    
    public void setPrefile(String prefile) {
        this.prefile = prefile;
    }
    
    @Column(name="HANDLES", length=500)

    public String getHandles() {
        return this.handles;
    }
    
    public void setHandles(String handles) {
        this.handles = handles;
    }
    
    @Column(name="CONTENT")

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="FILESIZE", precision=15, scale=4)

    public Double getFilesize() {
        return this.filesize;
    }
    
    public void setFilesize(Double filesize) {
        this.filesize = filesize;
    }
    
    @Column(name="CORRGUID", length=38)

    public String getCorrguid() {
        return this.corrguid;
    }
    
    public void setCorrguid(String corrguid) {
        this.corrguid = corrguid;
    }
    
    @Column(name="ISBACK", length=1)

    public String getIsback() {
        return this.isback;
    }
    
    public void setIsback(String isback) {
        this.isback = isback;
    }

	@Override
	public String toString() {
		return "RisenetFile [fileguid=" + fileguid + ", filename=" + filename
				+ ", filenameext=" + filenameext + ", titile=" + titile
				+ ", majorname=" + majorname + ", majorversion=" + majorversion
				+ ", minversion=" + minversion + ", appname=" + appname
				+ ", fileboxname=" + fileboxname + ", appinstguid="
				+ appinstguid + ", creatorguid=" + creatorguid
				+ ", createdate=" + createdate + ", lastmodified="
				+ lastmodified + ", realfullpath=" + realfullpath
				+ ", contentuniquecode=" + contentuniquecode + ", filetype="
				+ filetype + ", savetype=" + savetype + ", prefile=" + prefile
				+ ", handles=" + handles + ", content=" + content
				+ ", filesize=" + filesize + ", corrguid=" + corrguid
				+ ", isback=" + isback + "]";
	}
   
    







}