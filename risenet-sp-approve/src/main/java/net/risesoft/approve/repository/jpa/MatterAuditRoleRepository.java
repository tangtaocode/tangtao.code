package net.risesoft.approve.repository.jpa;


import net.risesoft.approve.entity.jpa.MatterAuditRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MatterAuditRoleRepository extends JpaRepository<MatterAuditRole, String>,JpaSpecificationExecutor<MatterAuditRole> {


	/**
	 * 根据人员id和事项id删除
	 * @param personGuid
	 */
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from MatterAuditRole WHERE personGuid=?1 and approveItemGuid=?2")
	public void deleteByPersonGuidAndApproveItemGuid(String personGuid,String approveItemGuid);

	/**
	 * 根据人员id和事项id查询
	 * @param approveItemGuid
	 * @return
	 */
	public MatterAuditRole findByApproveItemGuidAndPersonGuid(String approveItemGuid,String personGuid);
}
