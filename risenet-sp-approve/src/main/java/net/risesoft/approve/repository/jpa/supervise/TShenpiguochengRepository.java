package net.risesoft.approve.repository.jpa.supervise;


import java.util.List;

import net.risesoft.approve.entity.jpa.TShenpiguocheng;
import net.risesoft.approve.entity.jpa.TShenpiguochengUnits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TShenpiguochengRepository extends JpaRepository<TShenpiguocheng, String>,JpaSpecificationExecutor<TShenpiguocheng>{

	/**
	 * 根据业务流水号ywlsh，当前任务节点dqhjmc，查找过程数据
	 * @param ywlsh
	 * @param dqhjmc
	 * @return
	 */
	List<TShenpiguocheng> findById(TShenpiguochengUnits id);
}
