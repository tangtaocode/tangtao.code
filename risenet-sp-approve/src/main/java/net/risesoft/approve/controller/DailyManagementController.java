package net.risesoft.approve.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.Score;
import net.risesoft.approve.repository.jpa.ManagerSetRepository;
import net.risesoft.approve.repository.jpa.ZkAttendanceRepository;
import net.risesoft.approve.service.DailyManagementService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dailymanagement")
public class DailyManagementController {
	
	@Resource(name = "dailymanagementservice")
	private DailyManagementService dailymanagementservice;

	@Autowired
	private ManagerSetRepository zkmanagerSetRepository;
	
	@Autowired
	private ZkAttendanceRepository zkattendanceRepository;
	//日常管理
	@RiseLog(operateName = "打开日常管理页面",operateType = "查看")
	@RequestMapping(value = "/daily")
	public String dailyManger(Model model) {
		 //获取当前登录用户的id
		 String employid=ThreadLocalHolder.getPerson().getID();
		  //先判断当前用户是否为管理员
				 String departmentname=zkmanagerSetRepository.find(employid);
		  model.addAttribute("departmentname",departmentname);
		return "/dailyManagement/dailyManage";
	}
	//删除
	@RiseLog(operateName = "删除日常信息",operateType = "删除")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(String ScoreGuid){
		dailymanagementservice.delectScore(ScoreGuid);
		Map<String,Object> map=new HashMap<String, Object>();
		String message="删除成功";
		map.put("message", message);
		return map;
	}
	//判断是否需要新增
	@RiseLog(operateName = "判断是否需要新增日常信息",operateType = "查看")
	@RequestMapping(value = "/findis",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findis(String userGuid,String time,String isname){
		String message="";
		boolean is;
		String applytime;
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd ");
		if(isname==null){
		if(time!=null){
			applytime=time;
		}else{
			applytime=sdf.format(new Date());
		}
		  is=dailymanagementservice.findis(applytime,userGuid);
		 if(is){
			  message = "已经添加了一条数据";
			 map.put("message", message);
			 map.put("is", is);
		 }
		}
		return map;
	}
	//增加
	@RiseLog(operateName = "打开新增日常信息页面",operateType = "查看")
	@RequestMapping(value = "/add")
	public String add(String name,String userGuid,Model model,String enrollnumber){
		Score score = new Score();
		String scoreguid;
 		scoreguid=new GUID().toString();
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd ");
			 String applytime=sdf.format(new Date());
			
			
			score.setTime(applytime);
			score.setName(name);
			score.setEnrollnumber(enrollnumber);
			score.setScoreguid(scoreguid);
			score.setUserguid(userGuid);
			score.setCdzt(10);
			score.setLmwmyy(10);
			score.setMdgp(10);
			score.setZztfx(10);
			score.setNwzjqk(10);
			score.setPyq(10);
			score.setYxltksj(10);
			model.addAttribute("score",score);
		return "/dailyManagement/review";
	}
	//修改
	@RiseLog(operateName = "打开修改日常信息页面",operateType = "查看")
	@RequestMapping(value = "/updateScore")
	public String updateScore(String reviewid,String name,Model model,String userGuid){
		Score score = new Score();
	
			score=dailymanagementservice.find(reviewid);
			
		model.addAttribute("score",score);
		model.addAttribute("name",name);
		return "/dailyManagement/review";
	}
	// 查询窗口人员
	@RiseLog(operateName = "查询窗口人员",operateType = "查看")
	@RequestMapping(value ="/findUsers",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findUsers(String userName,String department,String sel,String enrollnumber,HttpServletRequest request) {
		 //获取当前登录用户的id
		 String employid=ThreadLocalHolder.getPerson().getID();
		  //先判断当前用户是否为管理员
				 String departmentname=zkmanagerSetRepository.find(employid);
				 //如果传进来的部门为空则代表是第一次进来
				 if(sel==null){
					 //是分厅管理员
					 if(departmentname!=null){
						 sel=departmentname;
					 }else if(departmentname==null){
						 //主厅管理员
						 sel="罗湖区行政服务大厅";
					 }
				 }
		try {
			String pageNo = request.getParameter("pager.pageNo");
			String pageSize = request.getParameter("pager.pageSize");
			Pager pager = new Pager(Integer.parseInt(pageNo),
					Integer.parseInt(pageSize));
			Map<String, Object> map = new HashMap<String, Object>();
				
			try {
				pager = dailymanagementservice.findWindowPerson(pager,userName,department,sel,enrollnumber);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Map<String, Object>> spmList = pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
			map.put("sel", sel);
			return map;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//查询考勤记录
	@RiseLog(operateName = "查询考勤记录",operateType = "查看")
	@RequestMapping(value ="/findRecord",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findRecord(String userId,String date,HttpServletRequest request) {
		// 获取用户名
		try {
			String pageNo = request.getParameter("pager.pageNo");
			String pageSize = request.getParameter("pager.pageSize");
  			Pager pager = new Pager(Integer.parseInt(pageNo),
					Integer.parseInt(pageSize));
			Map<String, Object> map = new HashMap<String, Object>();
			
			try {
				pager = dailymanagementservice.findRecord(pager,date,userId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Map<String, Object>> spmList = pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
			return map;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
		//保存
	@RiseLog(operateName = "保存新增日常信息",operateType = "增加")
		@RequestMapping(value ="/savepf",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> savepf(Score score,String scoreguid){
			String message ="";
			Map<String,Object> map=new HashMap<String, Object>();
			try {
				dailymanagementservice.saveScore(score,scoreguid);
			/*	score.setUserguid(listmap);*/
				message ="保存成功";
				map.put("message",message);
				return map;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message ="保存失败";
			}
			return map;
		}
		//月考评表
	@RiseLog(operateName = "获取月考评表",operateType = "查看")
		@RequestMapping(value ="/monthReview")
		public String mouthReview(Model model){
		//获取当前登录用户的id
		 String employid=ThreadLocalHolder.getPerson().getID();
		  //先判断当前用户是否为管理员
				 String departmentname=zkmanagerSetRepository.find(employid);
		  model.addAttribute("departmentname",departmentname);
			
			return  "/dailyManagement/monthReview";
		}
		//查看备注
	@RiseLog(operateName = "查看备注",operateType = "查看")
		@RequestMapping(value ="/findRemarks")
		public String findRemarks(String remarks,Model model){
			Score score = new Score();
			score.setRemarks(remarks);
			model.addAttribute("score",score);
			return  "/dailyManagement/findRemarks";
		}
		//奖惩管理
	@RiseLog(operateName = "打开奖惩管理页面",operateType = "查看")
		@RequestMapping(value ="/rewardsPunishment")
		public String rewardsPunishment(){
			return "/attendance/rewardsPunishmentManage";
		}
		
		//查看月度考评
	@RiseLog(operateName = "获取月度考评",operateType = "查看")
		@RequestMapping(value ="/searchmonth",method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> searchmonth(String monthguid,String time){
			Map<String,Object> map=new HashMap<String, Object>();
			int monthavg=dailymanagementservice.findperson(monthguid, time);
			map.put("monthavg", monthavg);
			return map;
		}
		//添加奖惩记录
	@RiseLog(operateName = "打开添加奖惩记录页面",operateType = "查看")
		@RequestMapping(value ="/addjcjl")
		public String addjcjl(String ScoreGuid,String name,String enrollnumber,Model model){
			Score score=new Score();
			score=dailymanagementservice.find(ScoreGuid);
			model.addAttribute("score",score);
			return "/dailyManagement/rewardsPunishmentManage";
		}
		//保存奖惩记录
	@RiseLog(operateName = "保存奖惩记录",operateType = "增加")
		@RequestMapping(value ="/savejcjl",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> savejcjl(String scoreGuid,Integer rewards,Integer punishment,Integer complaints,Integer praise){
		String message ="";
		Map<String,Object> map=new HashMap<String, Object>();
		if(StringX.isBlank(rewards)){
			rewards=0;
		}
		if(StringX.isBlank(punishment)){
			punishment=0;
		}
		if(StringX.isBlank(complaints)){
			complaints=0;
		}
		if(StringX.isBlank(praise)){
			praise=0;
		}
		
		punishment=-punishment;
		complaints=-complaints;
		try {
			
			dailymanagementservice.addjcjl(scoreGuid,rewards, punishment, complaints, praise);
			message ="保存成功";
			map.put("message",message);

			
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message ="保存失败";
		}
		return map;
		}
		//查看考凭记录
	@RiseLog(operateName = "查看考凭记",operateType = "查看")
		@RequestMapping(value ="/findjc")
		public String findjc(String scoreGuid,Model model){
			Score score=new Score();
			score=dailymanagementservice.find(scoreGuid);
			model.addAttribute("score",score);
			return "/dailyManagement/assessmentRecords";

		}
	
	@RequestMapping(value="/findMonthReview",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findMonthReview(String userName,String date,String sel,String department,String enrollnumber,HttpServletRequest request){
		 String employid=ThreadLocalHolder.getPerson().getID();
		  //先判断当前用户是否为管理员
				 String departmentname=zkmanagerSetRepository.find(employid);
				 //如果传进来的部门为空则代表是第一次进来
				 if(sel==null){
					 //是分厅管理员
					 if(departmentname!=null){
						 sel=departmentname;
					 }else if(departmentname==null){
						 //主厅管理员
						 sel="罗湖区行政服务大厅";
					 }
				 }
		try {
			String pageNo = request.getParameter("pager.pageNo");
			String pageSize = request.getParameter("pager.pageSize");
			Pager pager = new Pager(Integer.parseInt(pageNo),
					Integer.parseInt(pageSize));
			Map<String, Object> map = new HashMap<String, Object>();
				
			try {
				pager = dailymanagementservice.findDatePerson(pager,date,userName,department,enrollnumber,sel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Map<String, Object>> spmList = pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
			return map;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value="/findMonthRecord",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findMonthRecord(String userId,String date,HttpServletRequest request) {
		// 获取用户名
		try {
			String pageNo = request.getParameter("pager.pageNo");
			String pageSize = request.getParameter("pager.pageSize");
  			Pager pager = new Pager(Integer.parseInt(pageNo),
					Integer.parseInt(pageSize));
			Map<String, Object> map = new HashMap<String, Object>();
			
			try {
				pager = dailymanagementservice.findMonthRecord(pager,date,userId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Map<String, Object>> spmList = pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
			return map;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//导出excel考勤记录
	 @RequestMapping(value="/excel")
	  public String excel(HttpServletResponse response,String isPage,String date,Pager pager,String userName,String department,String sel,String enrollnumber,int istype,HttpServletRequest request){
		 String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		 if("false".equals(isPage)){
			  pager.setPageSize(-1);
			  pager.setPageNo(-1);
		  }else{
			  pager = new Pager(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize));
		  }
		 
		 WritableSheet sheet = null;
		 try{
			  WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
			  if(istype==1){
			  if(workbook != null){
					sheet = workbook.createSheet("sheet1", 0);												
	 				sheet.addCell(new Label(0,0,"姓名"));
					sheet.addCell(new Label(1,0,"部门"));
					sheet.addCell(new Label(2,0,"工号"));
				}
	  			pager = dailymanagementservice.findWindowPerson(pager,userName,department,sel,enrollnumber);
			  
			    List<Map<String, Object>> userList=pager.getPageList();
				for(int i = 0; i < userList.size(); i++){
					int row = i+1;
					Map<String,Object> map= userList.get(i);
					String name=(String)map.get("NAME");
					String department1=(String)map.get("DEPARTMENT");
					String enrollnumber1=(String)map.get("ENROLLNUMBER");
				    
					sheet.addCell(new Label(0,row,name));
					sheet.addCell(new Label(1,row,department1));
					sheet.addCell(new Label(2,row,enrollnumber1));
					
				}
			  }else if(istype==2){
				  if(workbook != null){
						sheet = workbook.createSheet("sheet1", 0);												
		 				sheet.addCell(new Label(0,0,"姓名"));
						sheet.addCell(new Label(1,0,"部门"));
						sheet.addCell(new Label(2,0,"工号"));
						sheet.addCell(new Label(3,0,"月份"));
						sheet.addCell(new Label(4,0,"月度评分"));
					}
				  pager = dailymanagementservice.findDatePerson(pager,date,userName,department,enrollnumber,sel);
				  
				    List<Map<String, Object>> userList=pager.getPageList();
					for(int i = 0; i < userList.size(); i++){
						int row = i+1;
						Map<String,Object> map= userList.get(i);
						String name=(String)map.get("NAME");
						String department1=(String)map.get("DEPARTMENT");
						String enrollnumber1=(String)map.get("ENROLLNUMBER");
						String dated=(String)map.get("DATED");
						Object ob=map.get("YDPF");
						String ydpf=ob.toString();
						sheet.addCell(new Label(0,row,name));
						sheet.addCell(new Label(1,row,department1));
						sheet.addCell(new Label(2,row,enrollnumber1));
						sheet.addCell(new Label(3,row,dated));
						sheet.addCell(new Label(4,row,ydpf));
					}
			  }
				response.reset();
				response.setHeader("Content-disposition","attachment; filename=" + new String("窗口人员信息.xls".getBytes("GBK"),"iso-8859-1"));  
				response.setContentType("application/vnd.ms-excel;charset=GBK");
				workbook.write();
				workbook.close();
		  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  
		return null;
	 }
	//子类导出excel考勤记录
	@RequestMapping(value="/excel1")
	 public String excel1(HttpServletResponse response,String isPage,Pager pager,String date,String userid,HttpServletRequest request){
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if("false".equals(isPage)){
			  pager.setPageSize(-1);
			  pager.setPageNo(-1);
		  }else{
			  pager = new Pager(Integer.parseInt(pageNo),
						Integer.parseInt(pageSize));
		  }
		 WritableSheet sheet = null;
		 try{
			
			  WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());			  
			  if(workbook != null){
					sheet = workbook.createSheet("sheet1", 0);												
	 				sheet.addCell(new Label(0,0,"姓名"));
					sheet.addCell(new Label(1,0,"内务整洁情况"));
					sheet.addCell(new Label(2,0,"游戏、聊天、看手机"));
					sheet.addCell(new Label(3,0,"迟到、早退"));
					sheet.addCell(new Label(4,0,"没带工牌"));
					sheet.addCell(new Label(5,0,"着装、头发、鞋"));
					sheet.addCell(new Label(6,0,"评议器"));
					sheet.addCell(new Label(7,0,"礼貌文明用语"));
					sheet.addCell(new Label(8,0,"总计"));
					sheet.addCell(new Label(9,0,"时间"));
					sheet.addCell(new Label(10,0,"备注"));
				}
			  pager =dailymanagementservice.findMonthRecord(pager, date, userid);
			    List<Map<String, Object>> userList=pager.getPageList();
				for(int i = 0; i < userList.size(); i++){
					int row = i+1;
					Map<String,Object> map= userList.get(i);
					String name=(String)map.get("NAME");
					String nwzjqk=map.get("NWZJQK").toString();
					String yxltksj=map.get("YXLTKSJ").toString();
					String cdzt=map.get("CDZT").toString();
					String mdgp=map.get("MDGP").toString();
					String zztfx=map.get("ZZTFX").toString();
					String pyq=map.get("PYQ").toString();
					String remarks=(String)map.get("REMARKS");
					String lmwmyy=map.get("LMWMYY").toString();
					Object ob=map.get("HEJI");
					String heji=ob.toString();
					String time=(String)map.get("TIME");
					sheet.addCell(new Label(0,row,name));
					sheet.addCell(new Label(1,row,nwzjqk));
					sheet.addCell(new Label(2,row,yxltksj));
					sheet.addCell(new Label(3,row,cdzt));
					sheet.addCell(new Label(4,row,mdgp));
					sheet.addCell(new Label(5,row,zztfx));
					sheet.addCell(new Label(6,row,pyq));
					sheet.addCell(new Label(7,row,lmwmyy));
					sheet.addCell(new Label(8,row,heji));
					sheet.addCell(new Label(9,row,time));
					sheet.addCell(new Label(10,row,remarks));
				}
		 
				response.reset();
				response.setHeader("Content-disposition","attachment; filename=" + new String("窗口人员信息.xls".getBytes("GBK"),"iso-8859-1"));  
				response.setContentType("application/vnd.ms-excel;charset=GBK");
				workbook.write();
				workbook.close();
		  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		 return null;
	 }
	
}
