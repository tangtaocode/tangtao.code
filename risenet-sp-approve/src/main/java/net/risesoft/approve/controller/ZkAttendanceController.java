//package net.risesoft.approve.controller;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import jxl.Workbook;
//import jxl.write.Label;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//import net.risesoft.approve.entity.base.Pager;
//import net.risesoft.approve.entity.jpa.KqGeneralLogData;
//import net.risesoft.approve.entity.jpa.ManagerSet;
//import net.risesoft.approve.entity.jpa.ZkRollMachine;
//import net.risesoft.approve.repository.jpa.ManagerSetRepository;
//import net.risesoft.approve.repository.jpa.WindowManagerRepository;
//import net.risesoft.approve.repository.jpa.WindowPersonRepository;
//import net.risesoft.approve.repository.jpa.ZkAttendanceRepository;
//import net.risesoft.approve.repository.jpa.ZklogdataRepository;
//import net.risesoft.approve.service.ZkAttendanceService;
//import net.risesoft.tenant.annotation.RiseLog;
//import net.risesoft.tenant.pojo.ThreadLocalHolder;
//import net.risesoft.util.GUID;
//import net.risesoft.utilx.ZkkqSDK;
//import net.sf.json.JSONObject;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//@RequestMapping(value="/zkAttendance")
//public class ZkAttendanceController
//{
//	
//  @Resource(name="zkAttendanceService")
//  private ZkAttendanceService zkAttendanceService;
//  
//  @Autowired
//	private ManagerSetRepository managerSetRepository;
//  @Autowired
//  private WindowManagerRepository windowManagerRepository;
//  
//  @Autowired
//  private ZkAttendanceRepository zkattendanceRepository; 
//  
//  
//  @Autowired
//  private WindowPersonRepository windowpersonRepository;
//  
//  @Autowired
//  private ZklogdataRepository zklogdataRepository;
//  
//  //考勤记录查询首页
//  @RiseLog(operateName = "打开考勤记录查询首页",operateType = "查看")
//  @RequestMapping(value="/index")
//  public String index(Model model)
//  {			 
//	//获取用户名
//		 //String username= ThreadLocalHolder.getPerson().getName();
//	  //获取当前登录用户的id
//	  String employid=ThreadLocalHolder.getPerson().getID();
//		 //先判断当前用户是否为管理员
//			 String departmentname=managerSetRepository.find(employid);	
//			 //通过大厅名称去查询该管理员是主厅还是分厅管理员
//			 ZkRollMachine zkrollmachine = zkAttendanceService.findBydepartment(departmentname);
//			 int ismainhall=zkrollmachine.getIsmainhall();
//			  model.addAttribute("ismainhall",ismainhall);
//			  model.addAttribute("departmentname",departmentname);
//			  return "/attendance/attendanceList";
//  }
//  
//
//  @RiseLog(operateName = "显示大厅与分厅",operateType = "查看")
//  @RequestMapping(value="/showindex",method=RequestMethod.POST)
//  @ResponseBody
//  public Map<String,Object> show()
//  {
//
//	  //获取用户名
//		 //String username= ThreadLocalHolder.getPerson().getName();
//	  //获取当前登录用户的id
//	  String employid=ThreadLocalHolder.getPerson().getID();
//	  Map<String,Object> map=new HashMap<String, Object>();
//		 //先获取当前登录人员所绑定的大厅名称
//			 String departmentname=managerSetRepository.find(employid);
//		//通过大厅名称去判断该用户是否为主厅管理员	 
//			 ZkRollMachine zkrollmachine=new ZkRollMachine();
//				zkrollmachine = zkAttendanceService.findBydepartment(departmentname);
//			 int ismainhall=zkrollmachine.getIsmainhall();
//			 //model.addAttribute("ismainhall",ismainhall);	
//			 List<String> list=new ArrayList<String>();
//			 if(ismainhall==0){
//				 //代表是主厅管理员、主厅管理员可以查询其他分厅和街道的窗口人员
//				  list = zkAttendanceService.findallname();
//				 // model.addAttribute("departmentname",123);
//			 }else{
//				 //代表是分厅或街道管理员，只能查询到自己所在大厅或街道的窗口人员
//				 list=zkAttendanceService.findonehall(departmentname,ismainhall);				 
//				 //model.addAttribute("departmentname",departmentname);
//			 }
//			  map.put("data",list);
//			  //判断当前用户
//			  return map;
//  }
//  //考勤机系统设置
//  @RiseLog(operateName = "打开考勤机系统设置",operateType = "查看")
//  @RequestMapping(value="/systemsetup")
//  public String set(){
//    return "/attendance/setup";
//  }
//
//  
//  @RequestMapping(value="/section")
//  @ResponseBody
//  public ZkRollMachine find(String num)
//  {
//    ZkRollMachine zkRollMachine = new ZkRollMachine();
//    String ip = zkRollMachine.getIp();
//    int port = zkRollMachine.getPort();
//    return zkRollMachine;
//  }
//  
//  
//  //管理员可以根据员工的申诉进行增加考勤记录
//  @RiseLog(operateName = "打开增加考勤记录页面",operateType = "查看")
//  	@RequestMapping(value="/add")
//  	public String addkq(){	
//  		return "/attendance/addrecord";
//  	}
//  	
//  	//管理员填写工号和考勤时间后点击："保存"
//	@RiseLog(operateName = "增加考勤记录",operateType = "增加")
//  	@RequestMapping(value="/addtime",method=RequestMethod.POST)
//  	@ResponseBody
//  	public Map<String,Object> add(String enrollnumber,String time,String windowhallname){
//  		Map<String,Object> map=new HashMap<String, Object>();
//  		String message;
//  		//先通过大厅名称去找到相应的设备编号
//  		int number=zkAttendanceService.finddevice(windowhallname);
//  		try {
//			Date datetime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
//			KqGeneralLogData kggenerallogdata=new KqGeneralLogData();
//			String guid=new GUID().toString();
//			kggenerallogdata.setGuid(guid);
//			kggenerallogdata.setDevicenumber(number);
//			kggenerallogdata.setEnrollnumber(enrollnumber);
//			kggenerallogdata.setTime(datetime);
//			//如果时间小于13：00则显示为上午、大于或等于13：00则显示为下午
//			if(datetime.getHours()<13){
//				kggenerallogdata.setType("上午");
//			}else{
//				kggenerallogdata.setType("下午");
//			}
//			kggenerallogdata.setStatustype("新增");
//			kggenerallogdata.setInOutMode("0");
//			kggenerallogdata.setVerifymode("1");
//			message=zkAttendanceService.saveedit(kggenerallogdata);
//		} catch (ParseException e) {			
//			e.printStackTrace();
//			message="添加失败";
//		}
//  		map.put("message",message);
//  		return map;
//  	}
//  
//  	
//  /**
//   * @param pageNum 页数
//   * @param pageSize 每页显示
//   * @param val1
//   * @param val2
//   * @param department
//   * @param request
//   * @return
//   */  	  	
//  //通过用户选择的部门以及时间进行查询
//	 @RiseLog(operateName = "获取考勤记录列表",operateType = "查看")
// @RequestMapping(value="/find",method=RequestMethod.POST)
//  @ResponseBody
//  public Map<String, Object> find(String val1, String val2, String department,HttpServletRequest request,Model model,String page,String rows)
//  {	  	 
//	  
//	  
//	  //获取用户名
//	  //		String username= ThreadLocalHolder.getPerson().getName();
//	  //		System.out.println(username);
//	 //获取当前登录用户的id
//	 String employid=ThreadLocalHolder.getPerson().getID();
//	  //先判断当前用户是否为管理员
//			 String departmentname=managerSetRepository.find(employid);
//				//若主厅管理员在选择了其他的分厅、则直接通过这个分厅名称去获取到考勤机		 
//			 	ZkRollMachine zkRollMachine =zkAttendanceService.findBydepartment(department);
//			 	 /*String ip="";
//			 	 int port=0;
//			 	 int devicenumber=0;
//			 	if(null==zkRollMachine){
//			 		ip="";
//			 		port=0;
//			 	}else{
//			 		ip = zkRollMachine.getIp();    	  
//			 		port = zkRollMachine.getPort();
//			 		devicenumber=zkRollMachine.getDevicenumber();
//			 	}*/
//			 	String ip = zkRollMachine.getIp();    	  
//		 		int port = zkRollMachine.getPort();
//		 		int devicenumber=zkRollMachine.getDevicenumber();
//					 ZkkqSDK sdk = new ZkkqSDK();
//					 Map<String,Object> map = new HashMap<String,Object>();
//				     boolean connFlag = sdk.connect(ip, port);
///*					 String pageNo=request.getParameter("pager.pageNo");
//					 String pageSize=request.getParameter("pager.pageSize");*/
//				      Pager pager = new Pager(Integer.parseInt(page),Integer.parseInt(rows));
//				      if (connFlag) {
//				          System.out.println("连接成功");
//
//				          try {
//				        	  //将考勤机的数据存放到实体类中(1:考勤用户实体类、考勤数据实体类)
//				        	  zkAttendanceService.findAllUserInfo(ip);
//							pager =zkAttendanceService.findData(val1,val2,pager,devicenumber);
//							List<Map<String,Object>> spmList =  pager.getPageList();							
//							map.put("currpage", pager.getPageNo());
//							map.put("totalpages", Math.ceil(pager.getTotalRows()/Integer.valueOf(rows)) + 1);							
//							map.put("total", pager.getTotalRows());														
//							map.put("rows", spmList);							
//						} catch (Exception e){
//							e.printStackTrace();
//						}				        
//				     }else{
//				    	 String message="连接失败、请确定考勤机是否连接正常";
//				    	 model.addAttribute("message",message);
//				    	 System.out.println("连接失败、请确定考勤机是否连接正常");
//				     }																			
//				return map;
//  }
//  
// //测试考勤机连接
// 	@RequestMapping(value="/testconn",method=RequestMethod.POST)
// 	@ResponseBody
// 		public Map<String,Object> test(String department){
// 		if(null==department){
// 			//则代表是分厅管理员
// 			String employid=ThreadLocalHolder.getPerson().getID();
// 			String truedepart=managerSetRepository.find(employid);
// 			department=truedepart;
// 		}
// 		ZkRollMachine zkRollMachine =zkAttendanceService.findBydepartment(department);
// 		 Map<String,Object> map = new HashMap<String,Object>();
// 		String message="";
// 		String ip;
//		int port;
//		try {
//			ip = zkRollMachine.getIp();    	  
//			port = zkRollMachine.getPort();
//			 ZkkqSDK sdk = new ZkkqSDK();	
//			 boolean connFlag = false;
//			 connFlag = sdk.connect(ip, port);
//		     if(connFlag){
//		    	 System.out.println("连接正常");
//		     }else{
//		    	 message="请检查考勤机是否连接正常";	    	 
//		     }		     
//		} catch (Exception e) {
//			e.printStackTrace();
//			message="请检查考勤机是否连接正常";
//		}
//		map.put("message",message);
// 		return map;
// 	}
//  //导出excel考勤记录
// 	@RiseLog(operateName = "导出excel考勤记录",operateType = "查看")
//  @RequestMapping(value="/excel")
//  public String excel(HttpServletResponse response,String isPage,Pager pager,String windowhallname,String starttime,String endtime){
//	  int number=zkAttendanceService.findnumber(windowhallname);
//	  if("false".equals(isPage)){
//		  pager.setPageSize(-1);
//		  pager.setPageNo(-1);
//	  }
//	  WritableSheet sheet = null;
//	  try{
//		  WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
//		  if(workbook != null){
//				sheet = workbook.createSheet("sheet1", 0);												
// 				sheet.addCell(new Label(0,0,"日期"));
//				sheet.addCell(new Label(1,0,"窗口编号"));
//				sheet.addCell(new Label(2,0,"类型"));
//				sheet.addCell(new Label(3,0,"姓名"));
//				sheet.addCell(new Label(4,0,"所属科室和部门"));
//				sheet.addCell(new Label(5,0,"工号"));
//				sheet.addCell(new Label(6,0,"打卡时间"));
//				sheet.addCell(new Label(7,0,"新增记录"));
//			}
//  			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//  			String education = "";
//			pager =zkAttendanceService.findData(starttime,endtime,pager,number);
//		    List<Map<String, Object>> userList=pager.getPageList();
//			for(int i = 0; i < userList.size(); i++){
//				int row = i+1;
//				Map<String,Object> map= userList.get(i);
//				String ordert=(String)map.get("ORDERT");
//				String entitywindowname=(String)map.get("ENTITYWINDOWNAME");
//				String type=(String)map.get("TYPE");
//			    String windowname=(String) map.get("WINDOWNAME");
//			    String departmentdesk=(String)map.get("DEPARTMENTDESK");
//				String enrollnumber=(String)map.get("ENROLLNUMBER");				
//				String time=(String)map.get("TIMEAA");
//				String statustype=(String)map.get("STATUSTYPE");
//				sheet.addCell(new Label(0,row,ordert));
//				sheet.addCell(new Label(1,row,entitywindowname));
//				sheet.addCell(new Label(2,row,type));
//				sheet.addCell(new Label(3,row,windowname));
//				sheet.addCell(new Label(4,row,departmentdesk));		
//				sheet.addCell(new Label(5,row,enrollnumber));	
//				sheet.addCell(new Label(6,row,time));
//				sheet.addCell(new Label(7,row,statustype));
//			}
//			response.reset();
//			response.setHeader("Content-disposition","attachment; filename=" + new String("用户信息.xls".getBytes("GBK"),"iso-8859-1"));  
//			response.setContentType("application/vnd.ms-excel;charset=GBK");
//			workbook.write();
//			workbook.close();
//	  
//	  }catch(Exception e){
//		  e.printStackTrace();
//	  }
//	  
//	return null;
//  }
//  
// 	@RiseLog(operateName = "获取考勤机信息列表",operateType = "查看")
//  @RequestMapping(value="/findMachines", method=RequestMethod.POST)
//  @ResponseBody
//  public Map<String, Object> machinesList()
//  {
//    Map<String,Object> map = new HashMap<String,Object>();
//    List<ZkRollMachine> list =zkAttendanceService.findMachines();
//    map.put("rows", list);
//    return map;
//  }
//
// 	 @RiseLog(operateName = "打开增加考勤机信息页面",operateType = "查看")
//  @RequestMapping(value="/popup", method=RequestMethod.GET)
//  public String popup()
//  {
//    return "/attendance/popup";
//  }
//
//  
//  //点击"保存"按钮、将ip、端口、部门保存到数据库
// 	@RiseLog(operateName = "保存考勤机设备信息",operateType = "增加")
//  @RequestMapping(value="/accept", method=RequestMethod.POST)
//  @ResponseBody
//  public Map<String, Object> accept(String ip, String port, String department,String hall)
//  {
//    String message = "";
//    Map<String, Object> map = new HashMap<String, Object>();
//
//    ZkkqSDK sdk = new ZkkqSDK();
//    boolean connFlag = false;
//    int result = 0;
//    try {
//      result = Integer.parseInt(port);
//      connFlag = sdk.connect(ip, result);
//    } catch (Exception e) {
//      e.printStackTrace();
//      message = "保存失败";
//    }
//
//    if (connFlag)
//    {
//      zkAttendanceService.save(ip, result, department,hall);
//      message = "保存成功";
//    } else {
//      message = "保存失败";
//    }
//    map.put("message", message);
//    return map;
//  }
//  
// 	//删除考勤机设备
// 	 @RiseLog(operateName = "删除考勤机设备信息",operateType = "删除")
//	  @RequestMapping(value="/deleteon", method=RequestMethod.GET)
//	  public void delete(String department, String ip, String port)
//	  {
//	    zkAttendanceService.delete(department, ip, port);
//	  }
// 	 
// 	 
// 	@RequestMapping(value="/showhallname", method=RequestMethod.GET)
// 	@ResponseBody
//	  public String showhall()
//	  {
//	    //返回已经绑定的所有大厅名称
// 		String sql="";
// 		return null;
//	  }
// 	 
//}