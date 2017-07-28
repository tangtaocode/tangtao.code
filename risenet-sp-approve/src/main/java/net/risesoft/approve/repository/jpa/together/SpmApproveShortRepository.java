/**
 * @Project Name:risenet-sp-approve
 * @File Name: SpmApproveShortRepository.java
 * @Package Name: net.risesoft.approve.repository.jpa.together
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年5月9日 下午3:15:37
 */
package net.risesoft.approve.repository.jpa.together;

import net.risesoft.approve.entity.jpa.together.SpmApproveShort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: SpmApproveShortRepository.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年5月9日 下午3:15:37
 * @version 
 * @since JDK 1.7
 */
public interface SpmApproveShortRepository extends JpaRepository<SpmApproveShort, String>,JpaSpecificationExecutor<SpmApproveShort> {
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("update SpmApproveShort t set t.shortcode =?2 where t.itemid=?1 ")
	public int updateByItemid(String itemid, String shortcode);

}

