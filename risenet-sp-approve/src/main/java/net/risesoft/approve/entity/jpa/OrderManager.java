/**
 * @Project Name:risenet-sp-approve
 * @File Name: OrderManager.java
 * @Package Name: net.risesoft.approve.entity.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月17日 上午11:18:13
 */
package net.risesoft.approve.entity.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
/**
 * @ClassName: OrderManager.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年3月17日 上午11:18:13
 * @version 
 * @since JDK 1.7
 */
@Entity
@Table(name="SPM_ORDERMANAGER")
@IdClass(PkOfManager.class)
public class OrderManager implements java.io.Serializable {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	
	private String approveitemguid;
	
	private String type;
	
	private int limit;

	
	@Id
	@Column(name="APPROVEITEMGUID", nullable=false, length=38)
	public String getApproveitemguid() {
		return approveitemguid;
	}

	public void setApproveitemguid(String approveitemguid) {
		this.approveitemguid = approveitemguid;
	}

	@Id
	@Column(name="TYPE", nullable=false, length=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="LIMIT")
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	

}

