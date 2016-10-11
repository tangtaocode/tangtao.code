package net.risesoft.beans.base;

import java.io.Serializable;
/**
 * 
  * @ClassName: TreeNode
  * @Description: 树结构javabean
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:01:49 PM
  *
 */
public class TreeNode implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 5730512985978670259L;
	private String id;
	private String pid;
	private String name;
	private String depart;
	private String isParent;
	private String ename;
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

}
