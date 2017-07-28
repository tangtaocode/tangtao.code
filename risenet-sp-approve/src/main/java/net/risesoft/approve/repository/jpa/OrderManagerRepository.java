/**
 * @Project Name:risenet-sp-approve
 * @File Name: OrderManagerRespository.java
 * @Package Name: net.risesoft.approve.repository.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月17日 下午2:16:23
 */
package net.risesoft.approve.repository.jpa;

import net.risesoft.approve.entity.jpa.OrderManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: OrderManagerRespository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年3月17日 下午2:16:23
 * @version 
 * @since JDK 1.7
 */
public interface OrderManagerRepository extends JpaRepository<OrderManager, String>,JpaSpecificationExecutor<OrderManager> {
	
	
}
