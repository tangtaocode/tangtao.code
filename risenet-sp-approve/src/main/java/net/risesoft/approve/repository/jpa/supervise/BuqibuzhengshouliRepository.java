package net.risesoft.approve.repository.jpa.supervise;


import net.risesoft.approve.entity.jpa.TBujiaoshouli;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
////持久层操作补齐补正受理表
public interface BuqibuzhengshouliRepository extends JpaRepository<TBujiaoshouli, String>,JpaSpecificationExecutor<TBujiaoshouli> {

}
