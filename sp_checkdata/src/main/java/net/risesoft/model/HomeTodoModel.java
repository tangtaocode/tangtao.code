/**
 * @ProjectName:zjjHome
 * @FileName: HomeTodoModel.java
 * @PackageName: net.risesoft.model
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 6, 2015 11:07:08 AM
 */
package net.risesoft.model;

import java.io.Serializable;

/**
 * @ClassName: HomeTodoModel.java
 * @Description: TODO
 *
 * @author kun
 * @date May 6, 2015 11:07:08 AM
 * @version 
 * @since JDK 1.6
 */
public class HomeTodoModel implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	private String guid;
	private String url;
	private String title;
	private String todoDate;
	private String tip;
	private String gongchengmingcheng;//工程名称
	private String statedescription;//当前审批状态
	
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	private String status;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTodoDate() {
		return todoDate;
	}
	public void setTodoDate(String todoDate) {
		this.todoDate = todoDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGongchengmingcheng() {
		return gongchengmingcheng;
	}
	public void setGongchengmingcheng(String gongchengmingcheng) {
		this.gongchengmingcheng = gongchengmingcheng;
	}
	public String getStatedescription() {
		return statedescription;
	}
	public void setStatedescription(String statedescription) {
		this.statedescription = statedescription;
	}
	
}

