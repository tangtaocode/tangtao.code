/**
 * @Project Name:risenet-sp-approve
 * @File Name: XzqlXzspListsps.java
 * @Package Name: net.risesoft.approve.entity.jpa.together
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年5月17日 上午11:07:54
 */
package net.risesoft.approve.entity.jpa.together;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: XzqlXzspListsps.java
 * @Description: 材料提交要点：提交类型（原件/复印件等）、提交方式（校验/收取）、提交数量
 *
 * @author chenbingni
 * @date 2016年5月17日 上午11:07:54
 * @version 
 * @since JDK 1.7
 */
@Entity
@Table(name = "XZQL_XZSP_LISTSPS")
public class XzqlXzspListsps implements Serializable {
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	private String guid;//VARCHAR2(38) not null,
	private String tjlx;//VARCHAR2(2),
	private String tjfs;//VARCHAR2(2),
	private String tjsl;//NUMBER
	private String listsguid;//材料ID
	
	@Id
	@Column(name="GUID",length=38,nullable=false)
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column(name="TJLX")
	public String getTjlx() {
		return tjlx;
	}
	public void setTjlx(String tjlx) {
		this.tjlx = tjlx;
	}
	@Column(name="TJFS",length=50)
	public String getTjfs() {
		return tjfs;
	}
	public void setTjfs(String tjfs) {
		this.tjfs = tjfs;
	}
	@Column(name="TJSL")
	public String getTjsl() {
		return tjsl;
	}
	public void setTjsl(String tjsl) {
		this.tjsl = tjsl;
	}
	@Column(name="LISTSGUID",length=38)
	public String getListsguid() {
		return listsguid;
	}
	public void setListsguid(String listsguid) {
		this.listsguid = listsguid;
	}
	

}

