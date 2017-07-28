/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffdataRepository.java
 * @Package Name: net.risesoft.approve.repository.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月19日 上午11:55:34
 */
package net.risesoft.approve.repository.jpa;

import java.util.List;

import net.risesoft.approve.entity.jpa.StuffdataXt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName: StuffdataRepository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年3月19日 上午11:55:34
 * @version 
 * @since JDK 1.7
 */
public interface StuffdataXtRepository extends JpaRepository<StuffdataXt, String>,JpaSpecificationExecutor<StuffdataXt> {
	
	@Query("from StuffdataXt t where t.stuffdataguid=?1 ")
	public List<StuffdataXt> findXtByStuffguid(String stuffdataguid);
	
	public StuffdataXt findByGuid(String guid);

}

