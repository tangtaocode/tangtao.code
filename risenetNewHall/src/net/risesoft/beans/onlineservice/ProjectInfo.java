package net.risesoft.beans.onlineservice;

import java.io.Serializable;


/**
 * @项目信息（一般项目和重大项目）
 * 
 * @author 黄勇
 * @Date 2013-11-20 上午15:50:24
 */

public class ProjectInfo implements Serializable{

	private static final long serialVersionUID = 5845286830162839049L;
	
	private String item_id;
	private String item_name;
	private String const_org;
	private String item_type;
	
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getConst_org() {
		return const_org;
	}
	public void setConst_org(String const_org) {
		this.const_org = const_org;
	}
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	
}
