package net.risesoft.approve.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jodd.http.HttpRequest;
import net.risesoft.approve.entity.jpa.ManagerSet;
import net.risesoft.approve.entity.jpa.WindowManager;
import net.risesoft.approve.entity.jpa.WindowPersonSet;
import net.risesoft.approve.service.WindowManagerService;
import net.risesoft.model.Department;
import net.risesoft.model.OrgUnit;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//窗口管理
@Controller
@RequestMapping(value="/hallmanager")
public class WindowManagerController {
	@Resource(name="windowManagerService")
	private WindowManagerService windowManagerService;
	
	@RiseLog(operateName = "打开窗口批准页面",operateType = "查看")
	@RequestMapping(value="/windowapprove")
	public String getback(){
		return "/windowmanager/windowapprove";
	}
	
	//窗口批准首页
	@RiseLog(operateName = "获取窗口信息",operateType = "查看")
	@RequestMapping(value="/windowapprovepage",method=RequestMethod.POST)
	@ResponseBody
	  public Map<String,Object> homepage(String windowhallname){
		  Map<String,Object> map=new HashMap<String, Object>();
		  List<WindowManager> isresult=windowManagerService.find(windowhallname);
		  map.put("rows", isresult);
		  return map;
		  
	  }
	
	//选择实体大厅窗口后进行批准
	@RiseLog(operateName = "批准窗口信息",operateType = "修改")
	@RequestMapping(value="/choice")
	public String choice(String guid,String type,Model model){
		model.addAttribute("guid",guid);
		model.addAttribute("type",type);
		return "/windowmanager/entitywindow";
	}

	//实体大厅首页数据
	@RiseLog(operateName = "获取实体窗口信息",operateType = "查看")
	@RequestMapping(value="/choicewindow",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> choicewindow(String name){
		Map<String,Object> map=new HashMap<String, Object>();
		List<Map<String,Object>> isresult=windowManagerService.choicewindow(name);
		map.put("rows", isresult);
		return map;
	}
	 //批准或者不批准后需要修改状态
	@RiseLog(operateName = "修改批准状态",operateType = "修改")
	  @RequestMapping(value="/windowapprovestatus",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> approvestatus(String entitywindowname,String type,String guid,String windowguid){
		  Map<String,Object> map=new HashMap<String, Object>();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  //获取当前系统时间以及批准人
		  String username=ThreadLocalHolder.getPerson().getName();
		  String approvetime=sdf.format(new Date());
		  try{
			  windowManagerService.change(entitywindowname,type,username,approvetime,guid,windowguid);
			 }catch(Exception e){
				 e.printStackTrace();
			 }		 
		  String message="操作成功";
		  map.put("message", message);
		  return map;
	  }
	  
	  
	  
	  
	  //窗口申请首页
	@RiseLog(operateName = "打开窗口申请首页",operateType = "查看")
	  @RequestMapping(value="/windowapply")
	  public String getapply(Model model){
		//获取当前用户的姓名和id
		String username=ThreadLocalHolder.getPerson().getName();
		String employeeid=ThreadLocalHolder.getPerson().getID();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(),employeeid); 
		 String departmentdesk=org.getName();
		 
		  //获取当前用户所在大厅的名称
		  ManagerSet managerSet=windowManagerService.findone(employeeid);
		  String windowhallname=managerSet.getWindowhallname();
		  model.addAttribute("username",username);
		  model.addAttribute("employeeid",employeeid);
		  model.addAttribute("departmentdesk",departmentdesk);
		  model.addAttribute("windowhallname",windowhallname);
		  return "/windowmanager/windowapply";
	  } 
	
	  //获取可选择的窗口编号
	@RiseLog(operateName = "获取窗口编号",operateType = "查看")
	  @RequestMapping(value="/getwindowhallnumber",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> gethallnumber(String windowhallname,String windownumber){
		  String message="";
		  String[] arr=new String[100];
		  Map<String,Object> map=new HashMap<String, Object>();
		  int isresult=windowManagerService.usable(windowhallname,windownumber);
		  //"ablenumber"为可以使用的窗口编号
		  int ablenumber=0;
		//该窗口编号已经被使用
		  if(isresult>=1){
			  System.out.println("窗口已经被占用");
			  //查询大厅下的所有已经使用的窗口编号
			  int numberlist=windowManagerService.findnumber(windowhallname);
			  ablenumber=numberlist+1;
			  message="该窗口已经被使用，建议使用:"+ablenumber+"号窗口";			 
		  }else{
			  //表示用户输入的窗口可以使用
			  ablenumber=Integer.valueOf(windownumber);
		  }		  
		 map.put("message", message);
		 return map;
	  }
	  
	  //窗口申请首页
	@RiseLog(operateName = "获取窗口申请信息列表",operateType = "查看")
	  @RequestMapping(value="/windowapplypage",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> applypage(){
		  Map<String,Object> map=new HashMap<String, Object>();
		  //用户登录时获取登录名和所属部门
		  OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
		  String username=ThreadLocalHolder.getPerson().getName();
		  String departmentdesk=org.getName();
		  //根据用户名和所属部门来查找该用户所提交过的申请
		  List<WindowManager> islist=windowManagerService.findapply(username,departmentdesk);
		  map.put("rows", islist);
		  return map;
	  }
	  
	  //发送窗口申请
	@RiseLog(operateName = "发送窗口申请",operateType = "发送")
	  @RequestMapping(value="/windowapplysend",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> approve(String employeeid,String proposer,String departmentdesk,String windowhallname){
		  Map<String,Object> map=new HashMap<String, Object>();
		  windowManagerService.applysend(employeeid,proposer,departmentdesk,windowhallname);
		  String message="成功发送申请";
		  map.put("message", message);
		  return map;
	  }
	  
	  
	  //窗口人员添加首页
	@RiseLog(operateName = "打开窗口人员添加首页",operateType = "查看")
	  @RequestMapping(value="/windowpersonadd")
	  public String addwindowperson(){
		  return "/windowmanager/windowpersonadd";
	  }
	  
	  //窗口人员首页数据
	@RiseLog(operateName = "获取窗口人员数据",operateType = "查看")
	  @RequestMapping(value="/personaddpage",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> getbacklist(String windowname){
		  Map<String,Object> map=new HashMap<String, Object>();
		  List<WindowPersonSet> islist=new ArrayList<WindowPersonSet>();
		  //拿到登录用户名，即为大厅管理员
		  String username=ThreadLocalHolder.getPerson().getName();
		  if(null==windowname||windowname.isEmpty()){
			   islist=windowManagerService.findAll(username);
		  }else{
			  	islist=windowManagerService.findAll(username,windowname);
		  }
		  map.put("rows",islist);
		  return map;
	  }	  
	  
	  //在添加窗口人员信息前先确认该窗口已通过申请
	@RiseLog(operateName = "确认窗口申请状态",operateType = "查看")
	  @RequestMapping(value="/testwindow",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> Validation(String username,String windowhallname,String windownumber){
		  Map<String,Object> map=new HashMap<String, Object>();
		  String message=windowManagerService.testwindow(username, windowhallname, windownumber);
		  map.put("message", message);
		  return map;
	  }

	  //发送调整申请
	@RiseLog(operateName = "打开窗口人员调整申请页面",operateType = "查看")
	  @RequestMapping(value="/edit")
	  public String edit(){
		  return "/windowmanager/sendedit";
	  }
	  
	  
	  //添加窗口人员详细信息
	@RiseLog(operateName = "打开添加窗口人员页面",operateType = "查看")
	  @RequestMapping(value="/adduser")
	  public String adduser(Model model){
		  //获取当前用户名即"申请人"
		  String username=ThreadLocalHolder.getPerson().getName();
		  String employeeid=ThreadLocalHolder.getPerson().getID(); 
		  //获取当前用户所在大厅的名称
		  ManagerSet managerSet=windowManagerService.findone(employeeid);
		  String windowhallname=managerSet.getWindowhallname();
		  //获取当前用户所在大厅的考勤机编号
		  int devicenumber=windowManagerService.finddevice(windowhallname);	
		  model.addAttribute("usernameid",employeeid);
		  model.addAttribute("username",username);
		  model.addAttribute("windowhallname",windowhallname);
		  model.addAttribute("devicenumber",devicenumber);
		  return "/windowmanager/adduser";
	  }
	  //通过大厅管理员和所选择的分厅名称去查找审核通过的窗口名称
	@RiseLog(operateName = "获取审核通过的窗口名称",operateType = "查看")
	  @RequestMapping(value="/findwindowname",method=RequestMethod.GET)
	  @ResponseBody
	  public String findwindowname(Model model){
		  Map<String,Object> map=new HashMap<String, Object>();
		//获取当前用户名即"申请人"
		  String username=ThreadLocalHolder.getPerson().getName();
		  String employeeid=ThreadLocalHolder.getPerson().getID(); 
		  //获取当前用户所在大厅的名称
		  ManagerSet managerSet=windowManagerService.findone(employeeid);
		  String windowhallname=managerSet.getWindowhallname();
	  /*//通过传来的大厅名称去找到相应的设备编号
		  int devicenumber=windowManagerService.finddevice(windowhallname);	 
		  model.addAttribute("devicenumber",devicenumber);*/
		  List<WindowManager> isresult=windowManagerService.findwindowname(username,windowhallname,employeeid);
		
		  List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		  for(WindowManager wm:isresult){
			  Map<String,Object> maptemp=new HashMap<String, Object>();
			  String entityname=wm.getEntitywindowname();
			  String windowguid=wm.getWindowguid();
			  maptemp.put("key",entityname);
			  maptemp.put("value",windowguid);
			  list.add(maptemp);
		  }
		  JSONObject json=new JSONObject();
		  json.put("list",list);
		  System.out.println(json.toString());
/*		  map.put("devicenumber",devicenumber);
		  map.put("isresult",isresult);*/
		  return json.toString();
	  }
	  //删除窗口人员
	@RiseLog(operateName = "删除窗口人员",operateType = "删除")
	  @RequestMapping(value="/windowsdelete",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> windowdelete(String windowname,String enrollnumber,String windowhallname){
		  Map<String,Object> map=new HashMap<String, Object>();
		  String message=windowManagerService.delete(windowname, enrollnumber, windowhallname);  
		  map.put("message", message);
		  return map;
		  
	  }
	  
	  
	  //保存窗口人员信息
	@RiseLog(operateName = "保存窗口人员信息",operateType = "增加")
	  @RequestMapping(value="/personadd",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> save(String employeeid,String name,String phonenumber,String enrollnumber,String windowhallname,String entitywindowname,String dept,String radio,String username,String usernameid,String windowguid,String devicenumber){
		  Map<String,Object> map=new HashMap<String,Object>();
		  String message=windowManagerService.saveinformation(employeeid,name,phonenumber,enrollnumber,windowhallname,entitywindowname,dept,radio,username,usernameid,windowguid,devicenumber);
		  map.put("message", message);
		  return map;
	  }
	  
	  //发送调整申请
	@RiseLog(operateName = "发送窗口人员调整申请",operateType = "发送")
	  @RequestMapping(value="/adjustapply",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> adjust(String content,String employeeid){
		  Map<String,Object> map=new HashMap<String,Object>();
		  String message=windowManagerService.saveadjust(content,employeeid);
		  map.put("message",message);
		  return map;				  
	  }
	  
	  
	//获取部门信息
	@RiseLog(operateName = "获取部门信息",operateType = "查看")
	  @RequestMapping(value="/userinfo",method=RequestMethod.POST)
	  @ResponseBody
	  	public Map<String,Object> userinfo( @RequestParam(required = false) String deptIds){
		  Map<String,Object> map=new HashMap<String, Object>();
		  OrgUnit org = RisesoftUtil.getDepartmentManager().getDepartment(deptIds);
		  OrgUnit org1 = RisesoftUtil.getDepartmentManager().getDepartment(deptIds);	 
		  String dept=org.getName()+","+org1.getName();
		  map.put("dept", dept);
		  return map;		  
	  	}
	  
	  //分厅管理人员添加首页
	@RiseLog(operateName = "打开分厅管理人员添加首页",operateType = "查看")
	  @RequestMapping(value="/addmanager")
	  public String addmanager(){
		  return "/windowmanager/addmanager";
	  }
	  
	  //管理人员首页数据
	@RiseLog(operateName = "获取分厅管理人员数据",operateType = "查看")
	  @RequestMapping(value="/managerindex",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> addpage(){
		  Map<String,Object> map=new HashMap<String, Object>();
		  List<ManagerSet> islist=windowManagerService.managerindex();
		  map.put("rows", islist);
		  return map;
	  }
	  
	  //保存管理员信息
	@RiseLog(operateName = "保存分厅管理人员信息",operateType = "增加")
	  @RequestMapping(value="/savemanager",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> savemanagerset(String proposer,String departmentdesk,String windowhallname,String employeeid){
		  Map<String,Object> map=new HashMap<String, Object>();
		  String message=windowManagerService.savemanager(proposer,departmentdesk,windowhallname,employeeid);
		  map.put("message", message);
		  return map;
	  }
	  
	  //删除管理人员信息
	@RiseLog(operateName = "删除分厅管理人员信息",operateType = "删除")
	  @RequestMapping(value="/managerdelete",method=RequestMethod.POST)
	  @ResponseBody
	  public Map<String,Object> deletemanager(String name,String employeeid){
		  Map<String,Object> map=new HashMap<String, Object>();
		  String message="";
		try {
			message = windowManagerService.delmanager(name,employeeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  map.put("message", message);
		  return map;
	  }
	  
/*	//获取审核通过的大厅名称
	  @RequestMapping(value="/gethallname",method=RequestMethod.GET)
	@ResponseBody
	  public String gethallname(){
		  List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		  List<Map<String,Object>> resultlist=new ArrayList<Map<String,Object>>();
		  //获取当前用户的id
		  String userid=ThreadLocalHolder.getPerson().getID();
		  //通过当前用户id去查询已经审核通过的“大厅名称”
		  list=windowManagerService.gethallname(userid);
		  for(Map<String,Object> map:list){
			  for(String s:map.keySet()){
				  System.out.println("K值为:"+s);
				  System.out.println("VALUE值:"+map.get(s));
				  Map<String,Object> maptemp=new HashMap<String, Object>();
				  maptemp.put("key",map.get(s));
				  maptemp.put("value",s);
				  resultlist.add(maptemp);
			  }			  			 
		  }
		  JSONObject json=new JSONObject();
		  json.put("list",resultlist);
		  System.out.println(json.toString());
		  return json.toString();
	  }*/

	  
}
