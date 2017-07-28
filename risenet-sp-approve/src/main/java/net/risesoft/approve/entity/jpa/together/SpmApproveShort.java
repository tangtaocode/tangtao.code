/**
 * @Project Name:risenet-sp-approve
 * @File Name: SpmApproveShort.java
 * @Package Name: net.risesoft.approve.entity.jpa.together
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年5月9日 下午3:10:19
 */
package net.risesoft.approve.entity.jpa.together;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: SpmApproveShort.java
 * @Description: 事项名称简码表
 *
 * @author chenbingni
 * @date 2016年5月9日 下午3:10:19
 * @version 
 * @since JDK 1.7
 */
@Entity
@Table(name = "SPM_APPROVE_SHORT")
public class SpmApproveShort implements Serializable {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	
	private String itemid;//事项主键    VARCHAR2(38) not null,
	private String shortcode;//事项简码     VARCHAR2(50) not null
	
	
	@Id
	@Column(name="ITEMID",length=38,nullable=false)
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	@Column(name="SHORTCODE",length=50,nullable=false)
	public String getShortcode() {
		return shortcode;
	}
	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
	
	

}

