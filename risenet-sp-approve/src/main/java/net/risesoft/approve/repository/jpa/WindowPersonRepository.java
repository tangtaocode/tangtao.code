package net.risesoft.approve.repository.jpa;
import java.util.List;

import net.risesoft.approve.entity.jpa.WindowPersonSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
public interface WindowPersonRepository extends JpaRepository<WindowPersonSet, String>,JpaSpecificationExecutor<WindowPersonSet>{
	@Transactional(readOnly=false)
	@Modifying
	@Query("delete from WindowPersonSet t where t.windowname=?1 and t.enrollnumber=?2 and t.windowhallname=?3")	
	public void delete(String windowname,String enrollnumber,String windowhallname);
	
	//查找出该管理员所添加的窗口人员
	@Query("from WindowPersonSet where username=?1")
	public List<WindowPersonSet> findAll(String username);
	
	
	
	//根据用户输入的窗口人员姓名进行查找
	@Query("from WindowPersonSet where username=?1 and windowname=?2")
	public List<WindowPersonSet> findone(String username,String windowname);
	
	/**
	 * 
	  * @MethodName: findWindowGuidsByEmployeeid
	  * @Description: 根据员工Id查找其所在的实体窗口
	  * @warning：因为一个员工可能在多个窗口，所以返回List
	  * @param： (参数)
	  * @return List<String>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月29日  下午2:30:19
	 */
	@Query("select t.windowguid from WindowPersonSet t where t.employeeid=?1 ")
	public List<String> findWindowGuidsByEmployeeid(String employeeid);
	//根据管理员的guid和窗口人员姓名去查询
	@Query("from WindowPersonSet where usernameid=?1 and windowname=?2")
	public List<WindowPersonSet> findlistone(String usernameid,String windowname);
	
	
	//该管理员只能查询到自己所添加的员工
	@Query("from WindowPersonSet where usernameid=?1")
	public List<WindowPersonSet> findmanager(String usernameid);
	
	//修改手机号码
	@Transactional(readOnly=false)
	@Modifying
	@Query("update WindowPersonSet set phonenumber=?2 where employeeid=?1")
	public void edit(String employeeid,String value);

	//主厅管理员需要查询到的窗口人员
	@Query("from WindowPersonSet where windowhallname ='罗湖区行政服务大厅'and windowname=?1")
	public List<WindowPersonSet> findAlllist(String windowname); 
	
	@Query("from WindowPersonSet where windowhallname ='罗湖区行政服务大厅'")
	public List<WindowPersonSet> findAlllist(); 
	
	//查询需要调整的窗口人员信息(只包含"调整状态"有值的情况)
	@Query("from WindowPersonSet where adjuststatus is not null ")
	public List<WindowPersonSet> findAllad(); 
	
	//修改调整状态
	@Transactional(readOnly=false)
	@Modifying
	@Query("update WindowPersonSet set adjuststatus=?3 where employeeid=?1 and windowhallname=?2")
	public void updatstatus(String employeeid,String windowhallname, String type);
	

}
