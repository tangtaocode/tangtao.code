package net.risesoft.approve.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.jpa.ManagerSet;
import net.risesoft.approve.entity.jpa.WindowManager;
import net.risesoft.approve.entity.jpa.WindowPersonSet;
import net.risesoft.approve.repository.jpa.ManagerSetRepository;
import net.risesoft.approve.repository.jpa.WindowManagerRepository;
import net.risesoft.approve.repository.jpa.WindowPersonRepository;
import net.risesoft.approve.service.WindowManagerService;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;
@Service(value="windowManagerService")
public class WindowManagerImpl implements WindowManagerService{
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;	
	private JdbcTemplate jdbctemplate;
	
	@Autowired
	private WindowManagerRepository windowManagerRepository;
	
	@Autowired
	private ManagerSetRepository managerSetRepository;
	
	@Autowired
	private WindowPersonRepository windowpersonRepository;
	@PostConstruct
	private void afterIoc() {
		jdbctemplate = new JdbcTemplate(this.routerDataSource);
	} 
		@Override
		public int windowapprove() {
			String sql="select count(*) from DT_CKGL_MANAGER t where t.status='0'";
			int count=jdbctemplate.queryForObject(sql,Integer.class);			
			return count;
		}
		
		@Override
		public int adjustperson() {
			String sql="select count(*) from DT_CKGL_WINDOWPERSONADD t where t.adjuststatus='0'";
			int count=jdbctemplate.queryForObject(sql,Integer.class);
			return count;
		}
		
		@Override
		public void change(String entitywindowname,String type,String username,String approvetime,String guid,String windowguid) {
			try{
				windowManagerRepository.status(entitywindowname,type,username,approvetime,guid,windowguid);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
		//窗口批准首页数据
		@Override
		public List<WindowManager> find(String windowhallname) {
			if(null==windowhallname){
				return windowManagerRepository.findorder();
			}else{
				return windowManagerRepository.findorder(windowhallname);
			}
						
		}
		
		//审核通过后去查询相关实体大厅数据
		@Override
		public List<Map<String, Object>> choicewindow(String name) {
				String sql="select t.name,t.address,t.guid from XZQL_XZSP_WINDOW t ";
				List<Object> params=new ArrayList<Object>();
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				if(!StringX.isBlank(name)){
					sql += "where t.name like ?";
					params.add("%"+name+"%");
				}
				try {
					 list=jdbctemplate.queryForList(sql,params.toArray());
				} catch (DataAccessException e) {
					e.printStackTrace();
				}
			return list;
		}
		
		//去查询还有哪些窗口是可以使用的
		public int usable(String windowhallname,String windownumber) {
			String sql="SELECT count(*) FROM DT_CKGL_MANAGER t WHERE t.WINDOWHALLNAME=? and t.WINDOWNUMBER=?";
			List<Object> params=new ArrayList<Object>();
			params.add(windowhallname);
			params.add(windownumber);
			int isresult=0;
			try {
				
				 isresult=jdbctemplate.queryForObject(sql,Integer.class, params.toArray());
			} catch (DataAccessException e) {
				
				e.printStackTrace();
			}
			return isresult;
		}
		
		//查找大厅中最大的窗口编号
		@Override
		public int findnumber(String windowhallname) {
			String sql="SELECT MAX(TO_NUMBER(windownumber)) from DT_CKGL_MANAGER where WINDOWHALLNAME=?";
			/*List<Map<String,Object>> list=jdbctemplate.queryForList(sql,new String[]{windowhallname});*/
			int res = jdbctemplate.queryForObject(sql, new String[]{windowhallname}, Integer.class);
			return res;
		}
		
		//窗口申请首页数据
		@Override
		public List<WindowManager> findapply(String applyperson,String departmentdesk) {
			List<WindowManager> obj =  windowManagerRepository.find(applyperson,"%"+departmentdesk+"%");
			return obj;
		}
		
		//发送窗口申请
		@Override
		public void applysend(String employeeid,String proposer,String departmentdesk,String windowhallname) {

			  //获取当前系统时间
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String applytime=sdf.format(new Date());
			  WindowManager windowmanager=new WindowManager();
			  String guid=new GUID().toString();
			  windowmanager.setGuid(guid);
			  windowmanager.setEmployeeid(employeeid);
			  windowmanager.setApplyperson(proposer);
			  windowmanager.setApplytime(applytime);
			  //申请人发送申请时，都默认为'0'、即'未批准'
			  windowmanager.setStatus("0");
			  windowmanager.setDepartmentdesk(departmentdesk);
			  windowmanager.setWindowhallname(windowhallname);
			  windowManagerRepository.save(windowmanager);			
		}
		
		//添加窗口人员首页数据
		@Override
		public List<WindowPersonSet> findAll(String username) {
			List<WindowPersonSet> islist=windowpersonRepository.findAll(username);
			return islist;
		}
		
		//查询用户输入的窗口人员姓名
		@Override
		public List<WindowPersonSet> findAll(String username,String windowname) {		
			return windowpersonRepository.findone(username,windowname);
		}
		
		//将窗口人员信息保存到数据库
		@Override
		public String saveinformation(String employeeid,String name,String phonenumber,String enrollnumber,String windowhallname,String entitywindowname,String dept,String radio,String username,String usernameid,String windowguid,String devicenumber) {
			WindowPersonSet zk=new WindowPersonSet();
			 String guid=new GUID().toString();
			 zk.setGuid(guid);
			 zk.setEmployeeid(employeeid);
			 zk.setEnrollnumber(enrollnumber);
			 zk.setEntitywindowname(entitywindowname);
			 zk.setDepartmentdesk(dept);
			 zk.setWindowname(name);
			 zk.setPhonenumber(phonenumber);
			 zk.setIsmanager(radio);
			 zk.setWindowhallname(windowhallname);
			 zk.setUsername(username);
			 zk.setUsernameid(usernameid);
			 zk.setWindowguid(windowguid);
			 zk.setDevicenumber(Integer.valueOf(devicenumber));
			 String message="";
			  try{
				  windowpersonRepository.save(zk);
				  message="保存成功";
			  }catch(Exception e){
				  e.printStackTrace();
				  message="保存失败";
			  }
				   return message;			  		  						
		}
		
		//将调整原因插入到数据表中
		@Override
		public String saveadjust(String content, String employeeid){
			String sql="UPDATE DT_CKGL_WINDOWPERSONADD t set t.adjustcause=?,t.adjuststatus='0' where t.employeeid=?";
			String message="";
			try {
				int isresult=jdbctemplate.update(sql,new String[]{content,employeeid});
				message="成功发送调整申请";
			} catch (DataAccessException e) {
				e.printStackTrace();
				message="发送调整申请失败";
			}			
			return message;
		}
		
		//分厅管理员可以删除之前所添加的窗口人员
		@Override
		public String delete(String windowname, String enrollnumber,String windowhallname) {
			String message="";
			try {
				windowpersonRepository.delete(windowname, enrollnumber, windowhallname);
				message="删除成功";
			} catch (Exception e) {
				e.printStackTrace();
				message="删除成功";
			}
			return message;
		}
		
		//在添加窗口人员信息前先确认该窗口已通过申请
		@Override
		public String testwindow(String username,String windowhallname,String windownumber) {
			String sql="select t.status from DT_CKGL_MANAGER t where t.applyperson=? and t.windowhallname=? and t.windownumber=?";
			String message="";
			try {
				int isresult=jdbctemplate.queryForObject(sql, new String[]{username,windowhallname,windownumber},Integer.class);
				if(isresult==0){
					message="该窗口正在审核中";
				}else if(isresult==1){
					message="该窗口审核未通过";
				}
			} catch (DataAccessException e) {				
				e.printStackTrace();
				message="所选大厅与窗口编号不符";
			}
			return message;
		}
		
		//添加大厅管理员——首页
		@Override
		public String savemanager(String proposer,String departmentdesk,String windowhallname,String employeeid) {
			ManagerSet ms=new ManagerSet();
			String message="";
			String guid=new GUID().toString();
			ms.setGuid(guid);
			ms.setName(proposer);
			ms.setDepartmentdesk(departmentdesk);
			ms.setWindowhallname(windowhallname);
			ms.setEmployeeid(employeeid);
			try {
				managerSetRepository.save(ms);
				message="保存成功";
			} catch (Exception e) {
				e.printStackTrace();
				message="保存失败";
			}
			return message;
		}
		
		//管理员首页数据
		@Override
		public List<ManagerSet> managerindex() {
			List<ManagerSet> islist=new ArrayList<ManagerSet>();
			try {
				 islist=managerSetRepository.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return islist;
		}
		
		//删除管理员
		@Override
		public String delmanager(String name, String employeeid) {
			String message="";
			try {
				managerSetRepository.del(name,employeeid);
				message="删除成功";
			} catch (Exception e) {
				e.printStackTrace();
				message="删除失败";
			}
			return message;
		}
		//通过大厅管理员和所选择的分厅名称去查找审核通过的窗口名称
		@Override
		public List<WindowManager> findwindowname(String username,String windowhallname,String userid) {
			return windowManagerRepository.findorder(username,windowhallname,userid);
		}
		@Override
		public List<String> findWindowGuidsByEmployeeid(String employeeid) {			
			return windowpersonRepository.findWindowGuidsByEmployeeid(employeeid);
		}
		//查询所有的窗口人员信息(只包含"调整状态"为"0"的状态)
		@Override
		public List<WindowPersonSet> findAllList() {				
			return windowpersonRepository.findAllad();
		}
		
		//修改调整状态
		@Override
		public String updatestatus(String employeeid,String windowhallname, String type) {
			String message="";
			try {
				windowpersonRepository.updatstatus(employeeid, windowhallname, type);
				message="操作成功";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message="操作失败";
			}
			return message;
		}
		
		
		//通过传来的大厅名称去找到相应的设备编号
		@Override
		public int finddevice(String windowhallname) {
			String sql="select t.devicenumber from KQ_ROLLMACHINE t where t.department=?";
			int devicenumber=0;
				devicenumber = jdbctemplate.queryForObject(sql,new String[]{windowhallname},Integer.class);
			return devicenumber;
		}
		

		
		@Override
		public ManagerSet findone(String employeeid) {
			
			return managerSetRepository.findone(employeeid);
		}
		


}
