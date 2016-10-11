package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * 
  * @ClassName: BizProductBean
  * @Description: TODO
  * @author Comsys-zhangkun
  * @date Apr 10, 2013 4:36:13 PM
  *
 */
@Entity
@Table(name="ZJFC_KJ_PRODUCT")
@DataTransferObject
public class BizProductBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -7130501542154937651L;
	private String guid="";  //
	private String appguid="";  //申请扶持项目GUID
	private String productname="";  //主要产品名称
	private String isautonomously="";  //是否具有自主知识产权
	private String scale="";  //占企业销售收入总额比例（%）
	private Integer indexnump;  //序号
	private String userguid="";  //操作用户
	private Date createtime;  //提交时间
	private String oldguid;
	@Transient
	public String getOldguid() {
		return oldguid;
	}
	public void setOldguid(String oldguid) {
		this.oldguid = oldguid;
	}
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getAppguid() {
		return appguid;
	}
	public void setAppguid(String appguid) {
		this.appguid = appguid;
	}
	@Column
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	@Column
	public String getIsautonomously() {
		return isautonomously;
	}
	public void setIsautonomously(String isautonomously) {
		this.isautonomously = isautonomously;
	}
	@Column
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	@Column
	public Integer getIndexnump() {
		return indexnump;
	}
	public void setIndexnump(Integer indexnump) {
		this.indexnump = indexnump;
	}
	@Column
	public String getUserguid() {
		return userguid;
	}
	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
