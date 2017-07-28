package net.risesoft.approve.repository.jpa;
import java.util.List;

import net.risesoft.approve.entity.jpa.WindowManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WindowManagerRepository extends JpaRepository<WindowManager, String>,JpaSpecificationExecutor<WindowManager>{
	
//通过页面出来的guid去找到相对应的申请人的信息将状态进行修改
	@Transactional(readOnly=false)
	@Modifying
	@Query("update WindowManager t set t.status=?2,t.approveperson=?3,t.approvetime=?4,t.entitywindowname=?1,t.windowguid=?6 where t.guid=?5")
	public void status(String entitywindowname,String type,String username,String approvetime,String guid,String windowguid);
	

	@Query("from WindowManager where applyperson=?1 and departmentdesk like ?2 order by applytime desc")
	public List<WindowManager> find(String applyperson,String departmentdesk);
	
	@Query("from WindowManager order by applytime desc")
	public List<WindowManager> findorder();
	
	@Query("from WindowManager where windowhallname=?1 order by applytime desc")
	public List<WindowManager> findorder(String windowhallname);
	
	//通过大厅管理员和所选择的分厅名称去查找审核通过的窗口名称
	@Query("from WindowManager t where t.applyperson=?1 and t.windowhallname=?2 and t.employeeid=?3 and t.status='2'")
	public List<WindowManager> findorder(String username,String windowhallname,String uersid);

}
