package net.risesoft.approve.repository.jpa.supervise;


import net.risesoft.approve.entity.jpa.TWangshangyushouli;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//持久层操作网上预受理表
public interface WangshangyushouliRepository extends JpaRepository<TWangshangyushouli, String>,JpaSpecificationExecutor<TWangshangyushouli> {
	
}
