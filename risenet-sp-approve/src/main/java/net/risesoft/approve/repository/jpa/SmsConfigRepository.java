package net.risesoft.approve.repository.jpa;


import net.risesoft.approve.entity.jpa.SmsConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//持久层操作短信配置表
public interface SmsConfigRepository extends JpaRepository<SmsConfig, String>,JpaSpecificationExecutor<SmsConfig> {
	
	
}
