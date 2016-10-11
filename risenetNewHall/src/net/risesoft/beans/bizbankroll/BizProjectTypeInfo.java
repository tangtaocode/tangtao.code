package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.risesoft.beans.risefile.UpFileBean;

@Entity
@Table(name="ZJFC_XZTK")
public class BizProjectTypeInfo implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -1949762480998747227L;
	private String guid;  //主键
	private String xmlxguid;  //项目类型主键
	private String xztkcontent;  //细则条款内容
	private Date uptime;  //更新时间
	private String upperguid;  //更新人员主键
	private String typePathName;//扶持类别的全路径
	private List<UpFileBean> upFileList;//附件清单
	private String departName;//主管部门名称
	private String typeName;//扶持类型名称
	private String rootGuid;//扶持类型大类GUID；
	private String zgbm;//受理单位或科室GUID
	@Transient
	public String getZgbm() {
		return zgbm;
	}
	public void setZgbm(String zgbm) {
		this.zgbm = zgbm;
	}
	@Transient
	public String getRootGuid() {
		return rootGuid;
	}
	public void setRootGuid(String rootGuid) {
		this.rootGuid = rootGuid;
	}
	@Transient
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Transient
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	@Transient
	public List<UpFileBean> getUpFileList() {
		return upFileList;
	}
	public void setUpFileList(List<UpFileBean> upFileList) {
		this.upFileList = upFileList;
	}
	@Transient
	public String getTypePathName() {
		return typePathName;
	}
	public void setTypePathName(String typePathName) {
		this.typePathName = typePathName;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getXmlxguid() {
		return xmlxguid;
	}
	public void setXmlxguid(String xmlxguid) {
		this.xmlxguid = xmlxguid;
	}
	@Column
	public String getXztkcontent() {
		return xztkcontent;
	}
	public void setXztkcontent(String xztkcontent) {
		this.xztkcontent = xztkcontent;
	}
	@Column
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	@Column
	public String getUpperguid() {
		return upperguid;
	}
	public void setUpperguid(String upperguid) {
		this.upperguid = upperguid;
	}

}
