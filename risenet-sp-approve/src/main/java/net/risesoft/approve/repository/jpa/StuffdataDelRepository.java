/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffdataDelRepository.java
 * @Package Name: net.risesoft.approve.repository.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年4月1日 上午11:16:03
 */
package net.risesoft.approve.repository.jpa;

import net.risesoft.approve.entity.jpa.StuffdataDel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: StuffdataDelRepository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年4月1日 上午11:16:03
 * @version 
 * @since JDK 1.7
 */
public interface StuffdataDelRepository extends JpaRepository<StuffdataDel, String>,JpaSpecificationExecutor<StuffdataDel> {

}

