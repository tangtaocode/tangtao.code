/**
 * @Project Name:risenet-sp-approve
 * @File Name: XzqlXzspListspointRepository.java
 * @Package Name: net.risesoft.approve.repository.jpa.together
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年5月17日 上午11:35:15
 */
package net.risesoft.approve.repository.jpa.together;

import net.risesoft.approve.entity.jpa.together.XzqlXzspListspoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName: XzqlXzspListspointRepository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年5月17日 上午11:35:15
 * @version 
 * @since JDK 1.7
 */
public interface XzqlXzspListspointRepository extends
		JpaRepository<XzqlXzspListspoint, String>,
		JpaSpecificationExecutor<XzqlXzspListspoint> {
	
	@Query("from XzqlXzspListspoint t where t.listsguid=?1 ")
	public XzqlXzspListspoint findByListsguid(String id);

}

