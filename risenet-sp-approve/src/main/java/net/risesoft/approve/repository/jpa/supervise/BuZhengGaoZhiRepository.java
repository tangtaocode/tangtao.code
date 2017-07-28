package net.risesoft.approve.repository.jpa.supervise;

import net.risesoft.approve.entity.jpa.gdbs.BuZhengGaoZhiProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
////持久层操作补齐补正表
public interface BuZhengGaoZhiRepository extends JpaRepository<BuZhengGaoZhiProcess, String>,JpaSpecificationExecutor<BuZhengGaoZhiProcess> {
	
	
}
