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

import net.risesoft.approve.entity.jpa.StuffdataLog;

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
public interface StuffdataLogRepository extends JpaRepository<StuffdataLog, String>,JpaSpecificationExecutor<StuffdataLog> {
	public List<StuffdataLog> findByGuid(String guid);
	
	@Query("from StuffdataLog t where t.zbguid=?1 order by t.lognum desc ")
	public List<StuffdataLog> fintVersion(String stuffdataguid);
}

