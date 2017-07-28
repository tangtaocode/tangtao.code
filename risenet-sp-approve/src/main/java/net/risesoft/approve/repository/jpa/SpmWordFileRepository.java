package net.risesoft.approve.repository.jpa;
import net.risesoft.approve.entity.jpa.SpmWordFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//持久层操作管理员信息表
public interface SpmWordFileRepository extends JpaRepository<SpmWordFile, String>,JpaSpecificationExecutor<SpmWordFile>{
	
}
