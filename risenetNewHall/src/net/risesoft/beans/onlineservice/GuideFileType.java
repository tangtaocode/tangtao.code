package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
  * @ClassName: GuideFileType
  * @Description: 审批材料类型
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:04:10 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_LISTTYPE")
public class GuideFileType implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 479213055660241999L;
	private String typeguid;  //材料类型ID
	private String itemid;  //事项ID
	private String typename;  //类型名称
	private Integer orderno;  //排序号
	private String supertypeguid;  //父节点GUID
	private List<GuideFile> guideFileList;
	private Integer level;        //层级字段
	private String levelStr;     //层级标识字段
	
	@Transient
	public List<GuideFile> getGuideFileList() {
		return guideFileList;
	}
	public void setGuideFileList(List<GuideFile> guideFileList) {
		this.guideFileList = guideFileList;
	}
	@Id
	public String getTypeguid() {
		return typeguid;
	}
	public void setTypeguid(String typeguid) {
		this.typeguid = typeguid;
	}
	@Column
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	@Column
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	@Column
	public Integer getOrderno() {
		return orderno;
	}
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	@Column
	public String getSupertypeguid() {
		return supertypeguid;
	}
	public void setSupertypeguid(String supertypeguid) {
		this.supertypeguid = supertypeguid;
	}
	
	@Transient
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Transient
	public String getLevelStr() {
		return levelStr;
	}
	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}
}
