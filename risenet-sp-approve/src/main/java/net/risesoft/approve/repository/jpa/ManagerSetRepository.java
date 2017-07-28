package net.risesoft.approve.repository.jpa;
import java.util.List;

import net.risesoft.approve.entity.jpa.ManagerSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
//持久层操作管理员信息表
public interface ManagerSetRepository extends JpaRepository<ManagerSet, String>,JpaSpecificationExecutor<ManagerSet>{
	//通过用户id去判断是否属于管理员帐号
	@Query("select t.windowhallname from ManagerSet t where t.employeeid=?1")
	public String find(String employeeid);
	
	//删除管理员
	@Transactional(readOnly = false)
	@Modifying
	@Query("delete from ManagerSet where name=?1 and employeeid=?2")
	public void del(String name,String employeeid);
	
	@Query("from ManagerSet t where t.employeeid=?1")
	public ManagerSet findone(String employeeid);
	
}
