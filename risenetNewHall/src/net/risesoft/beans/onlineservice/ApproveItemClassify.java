package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
  * @ClassName: ApproveItemClassify
  * @Description: 事项分类
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:02:40 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_CLASSIFY")
public class ApproveItemClassify implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 2903692134000818349L;
	private String guid;  //主键
	private String pid;  //父及GUID
	private String name;  //名称
	private String createuser;  //创建人
	private Date createdate;  //时间
	private Integer levels;  //层级
	private Integer orderno;  //排序
	private String imagename;  //图片名称
	private List<ApproveItemClassify> chaildList = new ArrayList<ApproveItemClassify>();
	
	
	
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	@Column
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column
	public Integer getLevels() {
		return levels;
	}
	public void setLevels(Integer levels) {
		this.levels = levels;
	}
	@Column
	public Integer getOrderno() {
		return orderno;
	}
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	@Column
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	@Transient
	public List<ApproveItemClassify> getChaildList() {
		return chaildList;
	}
	public void setChaildList(List<ApproveItemClassify> chaildList) {
		this.chaildList = chaildList;
	}

}
