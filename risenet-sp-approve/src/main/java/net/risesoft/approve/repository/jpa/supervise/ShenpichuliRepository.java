package net.risesoft.approve.repository.jpa.supervise;


import net.risesoft.approve.entity.jpa.gdbs.ShenpichuliProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//持久层操作审批处理表
public interface ShenpichuliRepository extends JpaRepository<ShenpichuliProcess, String>,JpaSpecificationExecutor<ShenpichuliProcess> {
	
	
}
