package net.risesoft.approve.repository.jpa;


import net.risesoft.approve.entity.jpa.SBApproveInstance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface SBApproveInstanceRepository extends JpaRepository<SBApproveInstance, String>, JpaSpecificationExecutor<SBApproveInstance> {
	/**
	 * 网上预受理修改状态
	 * @param guid  主键
	 * @param Status 状态
	 * @return
	 */ 
	@Transactional(readOnly = false)
	@Modifying
	@Query("update SBApproveInstance t set t.status=?1 WHERE t.guid=?2")
	public void  updateStatus(String status,String guid);
}