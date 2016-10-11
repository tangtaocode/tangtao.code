package net.risesoft.beans.investment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: InvestmentLink
  * @Description: 投资审批javabean
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:02:15 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_TZSP")
public class InvestmentLink implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 4398639767446180234L;
	private String guid;  //主键
	private String linkname;  //名称
	private String pguid;  //父级GUID
	private String type;  //类型，1投资类型，2投资阶段，3投资环节
	private String linkcode;  //环节名称
	private Integer orderindex;  //排序
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getLinkname() {
		return linkname;
	}
	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}
	@Column
	public String getPguid() {
		return pguid;
	}
	public void setPguid(String pguid) {
		this.pguid = pguid;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column
	public String getLinkcode() {
		return linkcode;
	}
	public void setLinkcode(String linkcode) {
		this.linkcode = linkcode;
	}
	@Column
	public Integer getOrderindex() {
		return orderindex;
	}
	public void setOrderindex(Integer orderindex) {
		this.orderindex = orderindex;
	}

}
