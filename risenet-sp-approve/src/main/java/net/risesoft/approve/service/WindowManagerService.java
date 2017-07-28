package net.risesoft.approve.service;
import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.jpa.ManagerSet;
import net.risesoft.approve.entity.jpa.WindowManager;
import net.risesoft.approve.entity.jpa.WindowPersonSet;

//大厅管理——窗口管理
public interface WindowManagerService{
	
	
	//添加大厅管理员首页
	public String savemanager(String proposer,String departmentdesk,String windowhallname,String employeeid);
	
	//管理员首页数据
	public List<ManagerSet> managerindex();
	
	//删除管理员
	public String delmanager(String name,String employeeid);
	
	 // 窗口批准列表	 
	public int windowapprove();
	
	//窗口人员调整列别	
	public int adjustperson();
	
	 // 窗口批准首页数据
	public List<WindowManager> find(String windowhallname);
	
	//审核通过后去查询相关实体大厅数据
	public List<Map<String,Object>> choicewindow(String name);
	

	 // 修改审核状态
	public void change(String entitywindowname,String type,String username,String approvetime,String guid,String windowguid);
	
	
	 // 根据页面传来的"窗口所属大厅名称"以及"窗口编号"去确定窗口是否被使用	 
	public int usable(String windowhallname,String windownumber);
	
	
	//查询大厅中最大的窗口编号
	public int findnumber(String windowhallname);
	
	//窗口申请首页数据
	public List<WindowManager> findapply(String applyperson,String departdesk);
	
	
	
	//发送窗口申请
	public void applysend(String employeeid,String proposer,String departmentdesk,String windowhallname);
	
	//添加窗口人员首页数据,不同管理员查询到的窗口人员不一样
	public List<WindowPersonSet> findAll(String username);
	
	//查询所有的窗口人员信息(只包含"调整状态"为"0"的状态)
	public List<WindowPersonSet> findAllList();
	
	//修改"调整状态"
	public String updatestatus(String employeeid,String windowhallname,String type);

	
	//通过大厅管理员和所选择的分厅名称去查找审核通过的窗口名称
	public List<WindowManager> findwindowname(String username,String windowhallname,String userid);
	
	//通过传来的大厅名称去找到相应的设备编号
	public int finddevice(String windowhallname);
	
	//查询用户输入的窗口人员姓名
	public List<WindowPersonSet> findAll(String username,String windowname);
	
	//在添加窗口人员信息前先确认该窗口已通过申请
	public String testwindow(String username,String windowhallname,String windownumber);
	
	
	//将窗口人员信息保存到数据库
	public String saveinformation(String employeeid,String name,String phonenumber,String enrollnumber,String windowhallname,String entitywindowname,String dept,String radio,String username,String usernameid,String windowguid,String devicenumber);
	
	//将调整原因插入到数据表中
	public String saveadjust(String content,String employeeid);
	
	//分厅管理员可以删除之前所添加的窗口人员
	public String delete(String windowname,String enrollnumber,String windowhallname);
	
	/**
	 * 
	  * @MethodName: findWindowGuidByEmployeeid
	  * @Description: 根据员工Guid查找该员工被分配的实体窗口Guid
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<String>   实体窗口Guid列表，是xzql_xzsp_window的主键
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月29日  上午10:34:32
	 */
	public List<String> findWindowGuidsByEmployeeid(String employeeid);
	
	
	//返回当前用户所绑定的大厅名称
	public ManagerSet findone(String employeeid);

}
