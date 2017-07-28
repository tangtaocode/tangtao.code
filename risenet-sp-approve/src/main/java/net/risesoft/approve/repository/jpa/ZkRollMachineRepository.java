package net.risesoft.approve.repository.jpa;
import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.jpa.ZkRollMachine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ZkRollMachineRepository extends JpaRepository<ZkRollMachine, String>,JpaSpecificationExecutor<ZkRollMachine>{
	//通过用户选择的大厅找到相应的ip端口

	@Query("from ZkRollMachine t where t.department=?1")
	public ZkRollMachine findByDepartment(String department);
	
	//查询所有的大厅和街道名称
	@Query("select t.department from ZkRollMachine t")
	public List<String> findallname();
	
	@Query("select t.department from ZkRollMachine t where t.department=?1 and t.ismainhall=?2")
	public List<String> findonehall(String department,int ismainhall);
}
