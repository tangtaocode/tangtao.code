/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffdataXtLogRepository.java
 * @Package Name: net.risesoft.approve.repository.jpa
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月28日 下午4:30:40
 */
package net.risesoft.approve.repository.jpa;

import java.util.List;

import net.risesoft.approve.entity.jpa.StuffdataXtLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName: StuffdataXtLogRepository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年3月28日 下午4:30:40
 * @version 
 * @since JDK 1.7
 */
public interface StuffdataXtLogRepository extends JpaRepository<StuffdataXtLog, String>,JpaSpecificationExecutor<StuffdataXtLog> {
	
	@Query("from StuffdataXtLog t where t.stuffdatalogguid = ?1 order by t.version desc")
	public List<StuffdataXtLog> fintVersion(String stuffdatalogguid);

}

