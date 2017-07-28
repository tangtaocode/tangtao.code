package net.risesoft.approve.repository.jpa.supervise;


import net.risesoft.approve.entity.jpa.TShenban;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
////持久层操作申办表
public interface ShenbanRepository extends JpaRepository<TShenban, String>,JpaSpecificationExecutor<TShenban> {

	@Query("from TShenban t where t.yxtywlsh=?1")
	public TShenban findByYxtywlsh(String yxtywlsh);
	
}
