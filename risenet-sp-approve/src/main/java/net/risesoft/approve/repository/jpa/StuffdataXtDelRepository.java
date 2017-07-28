/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffdataXtDelRepository.java
 * @Package Name: net.risesoft.approve.repository.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年4月1日 上午11:16:17
 */
package net.risesoft.approve.repository.jpa;

import net.risesoft.approve.entity.jpa.StuffdataXtDel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: StuffdataXtDelRepository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年4月1日 上午11:16:17
 * @version 
 * @since JDK 1.7
 */
public interface StuffdataXtDelRepository extends JpaRepository<StuffdataXtDel, String>,JpaSpecificationExecutor<StuffdataXtDel> {

}

