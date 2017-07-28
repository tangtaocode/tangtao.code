package net.risesoft.approve.repository.jpa;


import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OfficeSpiDeclareinfoRepository extends JpaRepository<OfficeSpiDeclareinfo, String>,JpaSpecificationExecutor<OfficeSpiDeclareinfo> {
	
	@Query("select t.declaresn from OfficeSpiDeclareinfo t where t.processInstanceId=?1")
	public String  getDeclaresnByProcessInstanceId(String processInstanceId);
	
	@Query("from OfficeSpiDeclareinfo t where t.guid=?1")
	public OfficeSpiDeclareinfo findByGuid(String guid);
	
	@Query("select t.approveitemguid from OfficeSpiDeclareinfo t where t.processInstanceId=?1")
	public String  findApproveitemguidByProcessInstanceId(String processInstanceId);
	
	@Query("select t.guid from OfficeSpiDeclareinfo t where t.processInstanceId=?1")
	public String  getGuidByProcessInstanceId(String processInstanceId);
	
	public OfficeSpiDeclareinfo findByProcessInstanceId(String processInstanceId);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("update OfficeSpiDeclareinfo t set t.handleStatus=?1 where t.guid=?2")
	public int  updateStatus(String status,String processInstanceId);
}
