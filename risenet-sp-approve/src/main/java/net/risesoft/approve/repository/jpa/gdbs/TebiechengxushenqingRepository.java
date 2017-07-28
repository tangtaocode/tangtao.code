package net.risesoft.approve.repository.jpa.gdbs;


import net.risesoft.approve.entity.jpa.gdbs.TbcxsqProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//持久层操作特别程序申请表
public interface TebiechengxushenqingRepository extends JpaRepository<TbcxsqProcess, String>,JpaSpecificationExecutor<TbcxsqProcess> {
	
	
}
