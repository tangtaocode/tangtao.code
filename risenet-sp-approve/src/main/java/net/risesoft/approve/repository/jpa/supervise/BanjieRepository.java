package net.risesoft.approve.repository.jpa.supervise;


import net.risesoft.approve.entity.jpa.gdbs.BanjieProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//持久层操作办结表
public interface BanjieRepository extends JpaRepository<BanjieProcess, String>,JpaSpecificationExecutor<BanjieProcess> {
	
	
}
