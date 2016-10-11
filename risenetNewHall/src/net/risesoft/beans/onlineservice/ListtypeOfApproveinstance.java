package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
  * @ClassName: Approveinstance
  * @Description: 网上申报
  * @author Comsys-zhangkun
  * @date Jun 19, 2013 7:43:32 PM
  *
 */
@Entity
@Table(name="SB_LISTTYPEOFINSTANCE")
public class ListtypeOfApproveinstance implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 2823448570778015934L;
	private String guid;  //主键
	private String sb_guid;  //申报ID
	private String listtypeids;  //材料类别guids
	
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getSb_guid() {
		return sb_guid;
	}
	public void setSb_guid(String sb_guid) {
		this.sb_guid = sb_guid;
	}
	@Column
	public String getListtypeids() {
		return listtypeids;
	}
	public void setListtypeids(String listtypeids) {
		this.listtypeids = listtypeids;
	}
	
	
}
