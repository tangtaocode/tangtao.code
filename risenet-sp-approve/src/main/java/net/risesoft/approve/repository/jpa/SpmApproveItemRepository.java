package net.risesoft.approve.repository.jpa;

import net.risesoft.approve.entity.jpa.SpmApproveitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SpmApproveItemRepository extends CrudRepository<SpmApproveitem, String>,JpaRepository<SpmApproveitem, String>,
JpaSpecificationExecutor<SpmApproveitem>{
	//根据事项id获取一个对象
	@Query("from SpmApproveitem t where t.approveitemguid=?1")
	public SpmApproveitem findByapproveitemguid(String approveitemguid);
	
	//根据事项id获取一个对象
	@Query("from SpmApproveitem t where t.approveitemname=?1")
	public SpmApproveitem findByapproveitemname(String approveitemname);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("update SpmApproveitem t set t.orderpermission=?1 WHERE t.approveitemguid=?2")
	public void updateOrderpermission(String orderpermission, String approveitemguid);
	
}
