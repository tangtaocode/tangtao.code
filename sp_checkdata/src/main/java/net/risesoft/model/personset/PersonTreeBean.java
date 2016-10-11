/**
 * @Project Name:risenetzj
 * @File Name: PersonTreeBean.java
 * @Package Name: net.risesoft.optpermission.bean
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date Sep 2, 2015 4:17:35 PM
 */
package net.risesoft.model.personset;
/**
 * @ClassName: PersonTreeBean.java
 * @Description: 栏目树形结构bean
 *
 * @author tt
 * @date Sep 2, 2015 4:17:35 PM
 * @version 
 * @since JDK 1.6
 */
public class PersonTreeBean {
	private String id;
	private String name;
	private String pid;
	private String type;
	private int orderno;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
}
