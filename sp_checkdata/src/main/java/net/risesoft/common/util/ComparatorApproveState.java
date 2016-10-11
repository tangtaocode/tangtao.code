/**
 * @Project Name:LGOneHome
 * @File Name: ComparatorApproveState.java
 * @Package Name: net.risesoft.common.util
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年4月6日 下午6:16:56
 */

package net.risesoft.common.util;

import java.util.Comparator;

import net.risesoft.model.HomeTodoModel;

import org.apache.commons.collections.comparators.ComparatorChain;

/**
 * @ClassName: ComparatorApproveState.java
 * @Description:比较待办件的紧急程度
 *
 * @author tt
 * @date 2016年4月6日 下午6:16:56
 * @version 
 * @since JDK 1.6
 */
public class ComparatorApproveState implements Comparator {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Object arg0, Object arg1) {
		return (Integer.valueOf(((HomeTodoModel)arg1).getStatus()))-(Integer.valueOf(((HomeTodoModel) arg0).getStatus()));
	}
	
	  public boolean equals(Object obj) {
		    return compare(this, obj) == 0;
		  }

}
