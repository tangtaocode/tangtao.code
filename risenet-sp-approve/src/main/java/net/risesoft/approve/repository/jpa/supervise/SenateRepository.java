package net.risesoft.approve.repository.jpa.supervise;


import net.risesoft.approve.entity.jpa.Senate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//持久层操作评价表
public interface SenateRepository extends JpaRepository<Senate, String>,JpaSpecificationExecutor<Senate> {
	
	
}
