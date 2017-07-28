package net.risesoft.approve.repository.jpa.supervise;



import net.risesoft.approve.entity.jpa.TBujiaogaozhi;
import net.risesoft.approve.entity.jpa.TBujiaogaozhiUnits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
////持久层操作补齐补正表
public interface BuqibuzhengRepository extends JpaRepository<TBujiaogaozhi, String>,JpaSpecificationExecutor<TBujiaogaozhi> {
	
	TBujiaogaozhi findById(TBujiaogaozhiUnits id);
}
