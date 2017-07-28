package net.risesoft.approve.repository.jpa;

import java.util.List;
import net.risesoft.approve.entity.jpa.SPMwssbcl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SPMwssbclRepository extends JpaRepository<SPMwssbcl, String>,JpaSpecificationExecutor<SPMwssbcl>{

	List<SPMwssbcl> findByWorkflowinstanceguidAndDeclareannexguid(String workflowinstanceguid, String declareannexguid);
}
