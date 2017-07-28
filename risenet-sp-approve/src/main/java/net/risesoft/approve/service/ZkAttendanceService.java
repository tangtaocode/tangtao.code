//package net.risesoft.approve.service;
//import java.util.List;
//import java.util.Map;
//
//import net.risesoft.approve.entity.base.Pager;
//import net.risesoft.approve.entity.jpa.KqGeneralLogData;
//import net.risesoft.approve.entity.jpa.ZkRollMachine;
//import net.risesoft.approve.entity.jpa.ZkUserInfo;
//public interface ZkAttendanceService {
//
//	
//	/**
//	 * 查询用户信息
//	 * @throws Exception 
//	 */
//	public void  findAllUserInfo(String ip) throws Exception;
//	
//	
//	/**
//	 * 读取该时间后的最新考勤数据
//	 * @throws Exception 
//	 */
//	public Pager findData(String date1,String date2,Pager pager,int devicenumber) ;
//	 
//	
//	/**
//	 * 将用户输入的ip、端口以大厅名称保存到实体类
//	 */
//	public void save(String ip,int port,String department,String hall);
//	
//	/**
//	 * 通过所选择的大厅查询对应的ip和端口
//	 */
//	public ZkRollMachine findBydepartment(String department);
//	
//	
//	/**
//	 * 考勤机列表
//	 */
//	public List<ZkRollMachine> findMachines();
//	
//	
//	/**
//	 * 删除设备
//	 */
//	public void delete(String department,String ip,String port);
//
//	
//	/**
//	 * 通过大厅名称去找到相应的设备编号
//	 */
//	public int finddevice(String windowhallname);
//	
//	/**
//	 * 管理员手动添加记录并保存到数据库
//	 */
//	public String saveedit(KqGeneralLogData kqGeneralLogData);
//	
//	/**
//	 * 通过大厅名称去找到相应的考勤设备编号
//	 */
//	public int findnumber(String windowhallname);
//	
//	
//	/**
//	 *查询已经绑定ip的所有大厅和街道名称 ：主厅管理员
//	 */
//	public  List<String> findallname();
//	
//	
//	/**
//	 * 查询单个大厅或街道：分厅或街道管理员
//	 */
//	public List<String> findonehall(String department,int ismainhall);
//	
//}
