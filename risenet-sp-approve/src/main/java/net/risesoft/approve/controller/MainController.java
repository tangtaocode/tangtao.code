package net.risesoft.approve.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.risesoft.approve.service.OnlineReceiveService;
import net.risesoft.approve.service.WindowManagerService;
import net.risesoft.approve.service.supervise.BujiaoGaozhiService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.fileflow.service.SpWorklistService;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.model.Resource;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@Value("${risebpm7.topMenuGuid}")
	private String topMenuGuid;
	
	@javax.annotation.Resource(name="spWorklistService")
	private SpWorklistService spWorklistService;
	
	@javax.annotation.Resource(name = "onlineReceiveService")
	private OnlineReceiveService onlineReceiveService;
	
	@javax.annotation.Resource(name="windowManagerService")
	private WindowManagerService windowManagerService;
	
	@javax.annotation.Resource(name="bujiaoGaozhiService")
	private BujiaoGaozhiService bujiaoGaozhiService;
	
	@Value("${rc7.logout.url}")
	private String rc7LogoutUrl;

	@RiseLog(operateName = "退出登录",operateType = "退出")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (session != null) {
			session.invalidate();
		}
		map.put("rc7LogoutUrl", ContextUtil.getNewlogoutUrl(rc7LogoutUrl));
		map.put("success", true);
		return map;
	}
	
	@RiseLog(operateName = "获取菜单",operateType = "查看")
	@RequestMapping(value = "/getPermMenus")
	@ResponseBody
	public List<Map<String, Object>> getPermMenus(HttpServletRequest req) {
		Person person = ThreadLocalHolder.getPerson();
		String personID = person.getID();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		List<Resource> menuList = new ArrayList<Resource>();
		String id = req.getParameter("id");
		long teBieChengXuCount = 0;
		long pauseCount = 0;
		long todoCount = 0;
		long doingCount = 0;
		long doneCount = 0;
		long yslCount = 0;
		long bqbzCount = 0;
		int windowCount;
		if (StringUtils.isBlank(id)) {
			id = this.topMenuGuid;
		}
		List<Resource> menuLis = RisesoftUtil.getAccessControlService().getSubResources(ThreadLocalHolder.getTenantId(), personID, "BROWSE", id);
		menuList.addAll(menuLis);
		for (Resource menu : menuList) {
			Map<String, Object> map = new HashMap<String, Object>();
			String url = menu.getUrl();
			map.put("id", menu.getID());
			if (StringUtils.isBlank(url)) {
					map.put("text", menu.getName());
					map.put("url", "");
					map.put("isParent", true);
			} else {
				if(url.contains("/todo")){
					todoCount = spWorklistService.getTodoCount();
					map.put("text", menu.getName()+"("+todoCount+")");					
				}else if(url.contains("/windowapprove")){
					 windowCount=windowManagerService.windowapprove();
					 if(windowCount>0){
						 map.put("text", menu.getName()+"("+windowCount+")");
					 }else{
						 map.put("text", menu.getName());
					 }					
				}else if(url.contains("/adjustapprove")){
					windowCount=windowManagerService.adjustperson();	
					 if(windowCount>0){
						 map.put("text", menu.getName()+"("+windowCount+")");
					 }else{
						 map.put("text", menu.getName());
					 }	
				}else if(url.contains("/teBieChengXu")){
					teBieChengXuCount = spWorklistService.getTeBieChengXuCount();
					map.put("text", menu.getName()+"("+teBieChengXuCount+")");
				}else if(url.contains("/doing")){
					doingCount = spWorklistService.getDoingCount();
					map.put("text", menu.getName()+"("+doingCount+")");
				}else if(url.contains("/done")){
					doneCount = spWorklistService.getDoneCount();
					map.put("text", menu.getName()+"("+doneCount+")");
				}else if(url.contains("/pause")){
					pauseCount = spWorklistService.getPauseCount();
					map.put("text", menu.getName()+"("+pauseCount+")");
				}else if(url.contains("bjgz/index")){
					bqbzCount = bujiaoGaozhiService.bqbzCount();
					map.put("text", menu.getName()+"("+bqbzCount+")");
				}else if(url.contains("/allDocument")){
					map.put("text", menu.getName()+"("+(todoCount+doingCount+doneCount+pauseCount+teBieChengXuCount+bqbzCount)+")");
				}else if(url.contains("/onlineIndex")){
					try{
						yslCount = onlineReceiveService.undoYslCount(person);
					}catch(Exception e){
						e.printStackTrace();
					}											
					map.put("text", menu.getName()+"("+yslCount+")");
				}else{
					map.put("text", menu.getName());
				}
				map.put("url", url);
				map.put("isParent", false);
			}
			items.add(map);
		}
		return items;
	}

	/**
	 * 向url中添加参数
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unused")
	private String addParamToUrl(String url, String param) {
		if (url.contains("&") || url.contains("?")) {
			url = url + "&" + param;
		} else {
			url = url + "?" + param;
		}
		return url;
	}
	
	
	/**
	 * 审批登录
	 */
	@RiseLog(operateName = "获取人员登录信息",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/userinfo")
	public Map<String, Object> getUserInfo(HttpSession session) {
		Map<String,Object> model = new HashMap<String, Object>();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
		 String userName = (String) session.getAttribute("loginName");
		 Person person = (Person) session.getAttribute("loginPerson");
		 String orgname = person.getDN().split(",")[1].replace("ou=", "");
		model.put("orgName",orgname);
		model.put("userName",userName);
		return model;
		
	}
	
	/**
	 * 刷新菜单数据
	 */
	@RiseLog(operateName = "刷新菜单",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/refreshMenu")
	public Map<String, Object> refreshMenu() {
		Person person = ThreadLocalHolder.getPerson();
		long teBieChengXuCount = 0;
		long pauseCount = 0;
		long todoCount = 0;
		long doingCount = 0;
		long doneCount = 0;
		long yslCount = 0;
		long bqbzCount = 0;
		int windowCount = 0;
		
		todoCount = spWorklistService.getTodoCount();//待办件
		windowCount=windowManagerService.windowapprove();
		teBieChengXuCount = spWorklistService.getTeBieChengXuCount();//特别程序审核件
		doingCount = spWorklistService.getDoingCount();//在办件
		doneCount = spWorklistService.getDoneCount();//办结件
		pauseCount = spWorklistService.getPauseCount();//暂停件
		bqbzCount = bujiaoGaozhiService.bqbzCount();//补齐补正件
		yslCount = onlineReceiveService.undoYslCount(person);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("windowCount",windowCount);
		map.put("yslCount",yslCount);
		
		map.put("todoCount",todoCount);
		map.put("teBieChengXuCount",teBieChengXuCount);
		map.put("doingCount",doingCount);
		map.put("doneCount",doneCount);
		map.put("pauseCount",pauseCount);
		map.put("bqbzCount",bqbzCount);
		map.put("allCount",todoCount+teBieChengXuCount+doingCount+doneCount+pauseCount+bqbzCount);//所有件
		return map;
	}

}
