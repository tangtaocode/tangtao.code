/**
 * @Project Name:risenet-sp-approve
 * @File Name: XzqlXzspListspsRepository.java
 * @Package Name: net.risesoft.approve.repository.jpa.together
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年5月17日 上午11:33:45
 */
package net.risesoft.approve.repository.jpa.together;

import java.util.List;

import net.risesoft.approve.entity.jpa.together.XzqlXzspListsps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName: XzqlXzspListspsRepository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年5月17日 上午11:33:45
 * @version 
 * @since JDK 1.7
 */
public interface XzqlXzspListspsRepository extends JpaRepository<XzqlXzspListsps, String>, JpaSpecificationExecutor<XzqlXzspListsps> {
	
	@Query("from XzqlXzspListsps t where t.listsguid=?1 ")
	public List<XzqlXzspListsps> findByListsguid(String listsguid);

}

