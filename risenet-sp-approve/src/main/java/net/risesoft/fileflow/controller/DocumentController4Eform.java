package net.risesoft.fileflow.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.fileflow.service.Document4EformService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/document4Eform")
public class DocumentController4Eform {
	
	
	@Autowired
	private Document4EformService document4EformService;

	/**
	 * 跳转发送选人选项卡
	 * @param principalType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/choiceTab")
	public String choiceTab(String principalType, Model model) {
		model.addAttribute("principalType", principalType);
		return "/document/tabContent";
	}
	
	/**
	 * 新建公文
	 * @param processDefinitionKey
	 * @param depts
	 *            由于存在一人多岗问题，因此在这里需要知道对于当前人员在当前菜单具有授权的岗位，在新建流程实例的时候，
	 *            可以直接选取具有授权的部门，格式为“部门guid,部门guid”
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(@RequestParam String resourceId, @RequestParam(required = false) String depts, Model model) {
		String url = "document/documentEdit4Eform";
		try {
			model = document4EformService.add(resourceId, depts, model);
			url = (String) model.asMap().get("url");
		} catch (Exception e) {
			model.addAttribute("msg", "发生异常！");
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 启动流程
	 * @param processDefinitionKey
	 * @param processSerialNumber
	 * @param activitiUser
	 * @param documentTitle
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/startProcess", method = RequestMethod.POST)
	public Map<String, Object> startProcess(@RequestParam String processDefinitionKey, @RequestParam String processSerialNumber, @RequestParam String activitiUser, @RequestParam(required = false) String documentTitle) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = document4EformService.startProcess(processDefinitionKey, processSerialNumber, activitiUser, documentTitle);
		return map;
	}

	
	/**
	 * 查看公文详细信息
	 * 
	 * @param itembox
	 * @param taskId
	 * @param processInstanceId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String itembox, @RequestParam(required = false) String taskId, @RequestParam(required = false) String processInstanceId, Model model) throws Exception {
		try {
			model = document4EformService.edit(itembox, taskId, processInstanceId, model);
			return "document/documentEdit4Eform";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "config/exception";
	}
	
	/**
	 * 执行公文发送
	 * @param sponsorGuid//并行状态下的主办人guid
	 * @param taskId
	 * @param userChoice
	 * @param routeToTaskId
	 * @param formIds
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/forwarding")
	public Map<String, Object> forwarding(@RequestParam(required = false) String sponsorGuid,@RequestParam String taskId, @RequestParam String userChoice, @RequestParam String routeToTaskId, @RequestParam String formIds, HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = document4EformService.forwarding(sponsorGuid,taskId, userChoice, routeToTaskId, formIds, request);
		return map;
	}
	
	/**
	 * 发送时判定并行办理情况，如协办人未办理完成，则主办人办理时提示相关信息
	 * @param processDefinitionId
	 * @param taskId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/actRuTaskUserCount")
	public Map<String, Object> actRuTaskUserCount(@RequestParam String processDefinitionId, @RequestParam String taskId) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		ret_map = document4EformService.actRuTaskUserCount(processDefinitionId, taskId);
		return ret_map;
	}
	
	/**
	 * 跳转到用户和路由任务选取界面
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RequestMapping(value = "/docUserAndRoutaskChoise", method = RequestMethod.GET)
	public String docUserAndRoutaskChoise(@RequestParam String processDefinitionId, @RequestParam String processInstanceId, @RequestParam String taskId, @RequestParam(required = false, defaultValue = "") String taskDefKey, Model model) {
		model = document4EformService.docUserAndRoutaskChoise(processDefinitionId, processInstanceId, taskId, taskDefKey, model);
		return "document/doc-UserAndRoutaskChoise";
	}
	
	/**
	 * 跳转到用户选取界面
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RequestMapping(value = "/docUserChoise", method = RequestMethod.GET)
	public String docUserChoise(@RequestParam String routeToTaskId, @RequestParam String taskId, Model model) {
		model = document4EformService.docUserChoise(routeToTaskId, taskId, model);
		return "document/doc-UserChoise";
	}

	/**
	 * 签收时判定是否为一人多岗
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/claim/{taskId}", method = RequestMethod.GET)
	public Map<String, Object> claim(@PathVariable("taskId") String taskId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = document4EformService.claim(taskId);
		return map;
	}

	/**
	 * 获取本任务的所有可能的路由目的任务，如果目的任务不是用户任务，则继续往前搜索，直至找到用户任务
	 * 
	 * @param taskId
	 * @param sendDirectlyStr
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRouteToTasks")
	public Map<String, Object> getRouteToTasks(@RequestParam String taskId, @RequestParam(required = false) String sendDirectlyStr, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = document4EformService.getRouteToTasks(taskId, sendDirectlyStr);
		return map;
	}
	
	
	/**
	 * 流程办结
	 * 
	 * @param taskId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/complete")
	public Map<String, Object> complete(@RequestParam String taskId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = document4EformService.complete(taskId, request);
		return map;
	}


}