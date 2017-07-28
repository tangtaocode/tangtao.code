package net.risesoft.approve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.risesoft.approve.service.SPISuperviseService;
import net.risesoft.tenant.annotation.RiseLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 审批办理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/Handle")
public class ApproveHandleController {
	@Autowired
	private SPISuperviseService spisuperviseservice;
	
	@RequestMapping("/register")
	public String index(){		
		return "/approvehandle/register";		
	}
	
	@RiseLog(operateName = "获取待办件列表",operateType = "查看")
	@RequestMapping(value="/dbjlist",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> dbjlist(){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			List<Map<String,Object>> list=spisuperviseservice.findAll();
			int pagetotal=list.size();
			map.put("pager.pageNo", 1);
			map.put("pager.size", 20);
			map.put("pager.totalrows",pagetotal);
			map.put("rows", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return map;
	}
	
}
	


