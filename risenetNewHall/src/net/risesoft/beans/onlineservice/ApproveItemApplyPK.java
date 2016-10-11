package net.risesoft.beans.onlineservice;

import java.io.Serializable;

public class ApproveItemApplyPK implements Serializable{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 4130832639489697563L;
	private String ywlsh; 
	private Integer sjbbh;
	public String getYwlsh() {
		return ywlsh;
	}
	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}
	public Integer getSjbbh() {
		return sjbbh;
	}
	public void setSjbbh(Integer sjbbh) {
		this.sjbbh = sjbbh;
	}

	  public boolean equals(Object obj)
	  {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    ApproveItemApplyPK other = (ApproveItemApplyPK)obj;
	    if (this.ywlsh == null) {
	      if (other.ywlsh != null)
	        return false;
	    } else if (!this.ywlsh.equals(other.ywlsh))
	      return false;
	    if (this.sjbbh == null) {
	      if (other.sjbbh != null)
	        return false;
	    } else if (!this.sjbbh.equals(other.sjbbh))
	      return false;
	    return true;
	  }

	  public int hashCode()
	  {
	    int prime = 31;
	    int result = 1;
	    result = prime * result + (this.ywlsh == null ? 0 : this.ywlsh.hashCode());
	    result = prime * result + (this.sjbbh == null ? 0 : this.sjbbh.hashCode());
	    return result;
	  }
}
