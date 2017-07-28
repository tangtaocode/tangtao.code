package net.risesoft.approve.repository.jpa.gdbs;

import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ShouliRepository extends  JpaRepository<ShouliProcess, String>,JpaSpecificationExecutor<ShouliProcess> {

	@Modifying
	@Transactional(readOnly = false)
	@Query("update ShouliProcess t set t.status=?1 where t.sblshShort=?2")
	public int  updateStatus(String status,String sblshShort);
}
