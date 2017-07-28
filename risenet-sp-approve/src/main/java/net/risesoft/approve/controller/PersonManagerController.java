package net.risesoft.approve.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.approve.entity.jpa.WindowPersonSet;
import net.risesoft.approve.service.PersonManagerService;
import net.risesoft.approve.service.WindowManagerService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//人员管理
@Controller
@RequestMapping(value="/personmanager")
public class PersonManagerController {
	
	@Resource(name="windowManagerService")
	private WindowManagerService windowManagerService;
	
	@Resource(name="personManagerService")
	private PersonManagerService personManagerService;
	//人员管理首页
	@RiseLog(operateName = "打开人员管理首页",operateType = "查看")
	@RequestMapping(value="/personinformation")
	public String page(){
		return "/personmanagerpage/index";
	}
	
	//人员管理首页数据
	@RiseLog(operateName = "获取人员数据",operateType = "查看")
	@RequestMapping(value="/personmanagerpage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> pagelist(String windowname){
		Map<String,Object> map=new HashMap<String,Object>();
		//当前登录名和id
		String usernameid=ThreadLocalHolder.getPerson().getID();
		List<WindowPersonSet> isresult=new ArrayList<WindowPersonSet>();
		isresult=personManagerService.findAll(usernameid,windowname);
		map.put("rows",isresult);
		/*查询出该管理员所添加的窗口人员
		//int size=personManagerService.sense(usernameid);
		List<WindowPersonSet> isresult=new ArrayList<WindowPersonSet>();
		//分厅管理员(只能查看自己所添加的窗口人员信息)
		if(size==1){
			isresult=personManagerService.findAll(usernameid,windowname);
			map.put("rows",isresult);
		}else if(null==windowname){
			//主厅管理员(能看到所有窗口人员信息)
			isresult=personManagerService.findAllList();
		}else{
				isresult=personManagerService.findAllList(windowname);
			}			
		map.put("rows",isresult);
		*/
		return map;
	}
	
	//修改手机号码
	@RiseLog(operateName = "修改手机号码",operateType = "修改")
	@RequestMapping(value="/editinformation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> edit(String employeeid,String value){
		Map<String,Object> map=new HashMap<String, Object>();
		String message=personManagerService.edit(employeeid,value);
		map.put("message",message);
		return map;		
	}
	
	//调整批准首页
	@RiseLog(operateName = "打开调整批准首页",operateType = "查看")
	@RequestMapping(value="/adjustapprove")
	public String approvepage(){
		return "/personmanagerpage/adjustapprove";
	}
	
	
	//调整批准首页(查询所有的窗口人员信息：只包含调整状态为0的数据)
	@RiseLog(operateName = "获取调整人员数据",operateType = "查看")
	@RequestMapping(value="/adjustpage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> adjustpage(){
		Map<String,Object> map=new HashMap<String, Object>();
		List<WindowPersonSet> isresult=windowManagerService.findAllList();
		map.put("rows",isresult);
		return map;
	}
	
	//对管理员发来的调整申请进行批准或者不批准操作并修改状态
	@RiseLog(operateName = "调整申请批准",operateType = "修改")
	@RequestMapping(value="/updatestatus",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> status(String employeeid,String windowhallname,String type){
		Map<String,Object> map=new HashMap<String, Object>();
		String message=windowManagerService.updatestatus(employeeid, windowhallname, type);
		map.put("message",message);
		return map;
	}
	

}
