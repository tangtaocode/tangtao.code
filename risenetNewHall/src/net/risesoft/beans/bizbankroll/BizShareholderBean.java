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
  * @ClassName: BizShareholderBean
  * @Description: 科技创新扶持申请单位基本信息股东情况
  * @author Comsys-zhangkun
  * @date Apr 10, 2013 4:43:49 PM
  *
 */
@Entity
@Table(name="ZJFC_KJ_STOCKHOLDER")
@DataTransferObject
public class BizShareholderBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -8349301636289684426L;
	private String guid="";  //主键
	private String basicguid="";  //基础信息主键
	private Integer indexnum;  //序号
	private String stockholdername="";  //主要股东名称
	private String stockholdermoney="";  //股东出资额
	private String financialtype="";  //出资形式
	private String shareproportion="";  //所占股份比例
	private String userguid="";  //操作用户
	private Date createtime;  //提交时间
	private String oldguid="";
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
	public String getBasicguid() {
		return basicguid;
	}
	public void setBasicguid(String basicguid) {
		this.basicguid = basicguid;
	}
	@Column
	public Integer getIndexnum() {
		return indexnum;
	}
	public void setIndexnum(Integer indexnum) {
		this.indexnum = indexnum;
	}
	@Column
	public String getStockholdername() {
		return stockholdername;
	}
	public void setStockholdername(String stockholdername) {
		this.stockholdername = stockholdername;
	}
	@Column
	public String getStockholdermoney() {
		return stockholdermoney;
	}
	public void setStockholdermoney(String stockholdermoney) {
		this.stockholdermoney = stockholdermoney;
	}
	@Column
	public String getFinancialtype() {
		return financialtype;
	}
	public void setFinancialtype(String financialtype) {
		this.financialtype = financialtype;
	}
	@Column
	public String getShareproportion() {
		return shareproportion;
	}
	public void setShareproportion(String shareproportion) {
		this.shareproportion = shareproportion;
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
