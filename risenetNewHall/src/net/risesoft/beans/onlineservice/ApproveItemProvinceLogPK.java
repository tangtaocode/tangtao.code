package net.risesoft.beans.onlineservice;

import java.io.Serializable;


/**
 * 
  * @ClassName: ApproveItemProvince
  * @Description: 审批事项省扩展信息
  * @author Comsys-zhangkun
  * @date Jun 13, 2013 4:41:54 PM
  *
 */

public class ApproveItemProvinceLogPK implements Serializable{
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -4347660305811125902L;
	private String itemid;  //事项ID
	private Integer version;  //数据版本号
	
	 public ApproveItemProvinceLogPK() {
			super();
		}

		public ApproveItemProvinceLogPK(String itemid, Integer version) {
			super();
			this.itemid = itemid;
			this.version = version;
		}

		public String getItemid() {
			return itemid;
		}

		public void setItemid(String itemid) {
			this.itemid = itemid;
		}

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}

		public boolean equals(Object obj)
		  {
		    if (this == obj)
		      return true;
		    if (obj == null)
		      return false;
		    if (getClass() != obj.getClass())
		      return false;
		    ApproveItemProvinceLogPK other = (ApproveItemProvinceLogPK)obj;
		    if (this.itemid == null) {
		      if (other.itemid != null)
		        return false;
		    } else if (!this.itemid.equals(other.itemid))
		      return false;
		    if (this.version == null) {
		      if (other.version != null)
		        return false;
		    } else if (!this.version.equals(other.version))
		      return false;
		    return true;
		  }

		  public int hashCode()
		  {
		    int prime = 31;
		    int result = 1;
		    result = prime * result + (this.itemid == null ? 0 : this.itemid.hashCode());
		    result = prime * result + (this.version == null ? 0 : this.version.hashCode());
		    return result;
		  }

}
