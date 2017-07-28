package net.risesoft.approve.repository.jpa.gdbs;


import net.risesoft.approve.entity.jpa.gdbs.TbcxjgProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//持久层操作特别程序结果表
public interface TebiechengxujieguoRepository extends JpaRepository<TbcxjgProcess, String>,JpaSpecificationExecutor<TbcxjgProcess> {
	
	
}
