package net.risesoft.approve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.workflow.SpDocumentService;
import net.risesoft.approve.service.workflow.SpWorkflowProcessInstanceService;
import net.risesoft.fileflow.entity.jpa.ObjectPermission;
import net.risesoft.fileflow.service.DynamicRoleMemberService;
import net.risesoft.fileflow.service.ObjectPermissionService;
import net.risesoft.fileflow.service.OpinionNewService;
import net.risesoft.fileflow.service.TaskConfService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.SysVariables;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sp/document")
public class SpDocumentController {
	
	@Autowired
	protected TaskService taskService;

	@Autowired
	protected RuntimeService runtimeService;
	
	@Autowired
	private WorkflowTaskService workflowTaskService;

	@Autowired
	private OpinionNewService opinionNewService;

	@Autowired
	private SpDocumentService spDocumentService;

	@Autowired
	private ObjectPermissionService objectPermissionService;

	@Autowired
	private SpWorkflowProcessInstanceService spWorkflowProcessInstanceService;

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private SpmApproveItemService spmApproveItemService;
	
	@Autowired
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Autowired
	private TaskConfService taskConfService;
	
	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;
	
	private static Integer formEngineType = RisesoftCommonUtil.formEngineType;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;
	@Resource(name="dynamicRoleMemberService")
	  private DynamicRoleMemberService dynamicRoleMemberService;
	/**
	 * 启动流程
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RiseLog(operateName = "启动流程",operateType = "增加")
	@ResponseBody
	@RequestMapping(value = "/startProcess", method = RequestMethod.POST)
	public Map<String, Object> startProcess(String processDefinitionKey, String processSerialNumber, String activitiUser, String documentTitle, String SPinstanceId,@RequestParam(required=false)String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = spDocumentService.startProcess(processDefinitionKey, processSerialNumber, activitiUser, documentTitle, SPinstanceId,status);
		return map;
	}

	/**
	 * 编辑流程
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RiseLog(operateName = "查看编辑流程",operateType = "查看")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String itembox, @RequestParam(required = false) String taskId, @RequestParam(required = false) String processInstanceId, Model model) throws Exception {
		try {
			model.addAttribute("print", "shouLiDan");//打印按钮控制，默认打印受理单
			Person person = ThreadLocalHolder.getPerson();
			String processSerialNumber = "", processDefinitionId = "", taskDefinitionKey = "", processDefinitionKey = "", activitiUser = "";
			if (itembox.equalsIgnoreCase(SysVariables.DONE) || itembox.equalsIgnoreCase("history")) {
				HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
				processSerialNumber = (String) historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName(SysVariables.PROCESSSERIALNUMBER).list().get(0).getValue();
				processDefinitionId = hpi.getProcessDefinitionId();
				processDefinitionKey = processDefinitionId.split(SysVariables.COLON)[0];
				model.addAttribute("print", "banJieDan");//办结件打印办结单
			} else {
				TaskEntity task = workflowTaskService.getTaskByTaskId(taskId);
				processSerialNumber = (String) taskService.getVariable(taskId, SysVariables.PROCESSSERIALNUMBER);
				processDefinitionId = task.getProcessDefinitionId();
				processInstanceId = task.getProcessInstanceId();
				taskDefinitionKey = task.getTaskDefinitionKey();
				processDefinitionKey = processDefinitionId.split(SysVariables.COLON)[0];
				activitiUser = task.getAssignee();
				boolean isContainEndEvent = workflowProcessInstanceService.isContainTaskTarget(taskId, SysVariables.ENDEVENT);// 是否包含end节点
				if(isContainEndEvent){
					model.addAttribute("print", "banJieDan");//打印办结单
				}
				if (itembox.equalsIgnoreCase(SysVariables.TODO))
					taskService.setVariableLocal(taskId, SysVariables.ISNEWTODO, 0);
			}
			
			String instanceId = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
			model = spDocumentService.newMenuControl(processDefinitionId, taskDefinitionKey, taskId, model, itembox,instanceId);
			
			System.out.println("---------------");
			System.out.println(formEngineType);   //1
			System.out.println("---------------");

			if (formEngineType == 1) {
				model = spDocumentService.genDocumentModel(itembox, processSerialNumber, person.getTenantID(), processDefinitionKey, processDefinitionId, processInstanceId, taskId, taskDefinitionKey, activitiUser, model);
				return "document/documentEdit";
			} else if (formEngineType == 2) {
				model = spDocumentService.genDocumentModel4Eform(itembox, processSerialNumber, person.getTenantID(), processDefinitionKey, processDefinitionId, processInstanceId, taskId, taskDefinitionKey, activitiUser, model);
				return "document/documentEdit4Eform";
			} else if (formEngineType == 3) {
				model = spDocumentService.genDocumentModel4Eform(itembox, processSerialNumber, person.getTenantID(), processDefinitionKey, processDefinitionId, processInstanceId, taskId, taskDefinitionKey, activitiUser, model);
				return "document/documentEdit4Excel";
			}
			//model = spDocumentServise.genDocumentModel(itembox, processSerialNumber, person.getTenantID(), processDefinitionKey, processDefinitionId, processInstanceId, taskId, taskDefinitionKey, activitiUser, model);
			//model.addAttribute("opinions", opinionService.getByProcessInstanceId(processInstanceId));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/500";
	}

	/**
	 * 获取本任务的所有可能的路由目的任务，如果目的任务不是用户任务，则继续往前搜索，直至找到用户任务
	 * 
	 * @param taskId
	 * @param sendDirectlyStr
	 * @param model
	 * @return
	 */
	@RiseLog(operateName = "获取目的任务",operateType = "查看")
	@RequestMapping(value = "/getRouteToTasks")
	@ResponseBody
	public Map<String, Object> getRouteToTasks(@RequestParam String taskId, @RequestParam(required = false) String sendDirectlyStr, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);

		try {
			// 由于下面通过.getCurrentTaskTargets(taskId);获取当前任务的下一步路由需要当前任务的taskId
			// 当流程未启动时，没有taskId，所以这里要先判断taskId是否存在
			// 如果不存在则通过getTask判断task是否为null，如果为null则需启动流程
			Task task = workflowTaskService.getTaskByTaskId(taskId);
			// 是否允许直接发送。例如对于领导，点击发送的时候可以直接将公文发送给上一发送人或者他指定的人员，而不需要进行人员选取。
			map.put("sendFlag", "");// 发送标志，当为空的时候表示没有任何特殊处理，当为sendDirectly表示当前节点直接发送，不需要弹出路由选取界面，当目标节点为直接发送时，sendFlag为targetSendDirectly
			if (StringUtils.isNotBlank(sendDirectlyStr)) {// 直接发送isSendDirectly为true
				map.put("sendFlag", sendDirectlyStr);
			} else {// 如果不是直接发送，则查询routeToTasks
				List<Map<String, String>> routeToTasks = spWorkflowProcessInstanceService.getCurrentTaskTargets(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
				map.put("routeToTasks", routeToTasks);
			}
			map.put("processDefinitionId", task.getProcessDefinitionId());
			map.put("taskId", taskId);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("reason", e.getMessage());
		}
		return map;
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
		try {
			Task task = workflowTaskService.getTaskByTaskId(taskId);
			String tenantId = ThreadLocalHolder.getTenantId();

			List<Map<String, String>> routeToTasks = spWorkflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId, task.getTaskDefinitionKey());
			String options = "";
			for (int i = 0; i < routeToTasks.size(); i++) {
				//options += "<option  owner='" + routeToTasks.get(i).get("multiInstance") + "' value='" + routeToTasks.get(i).get("taskDefKey") + "' >" + routeToTasks.get(i).get("taskDefName") + "</option>";
				//if(routeToTasks.get(i).get("taskDefName").equals("并行办理")||routeToTasks.get(i).get("taskDefName").equals("串行办理")){
					//options += "<input type=\"radio\" name=\"routeToTasks\" owner='" + routeToTasks.get(i).get("multiInstance") + "' value='" + routeToTasks.get(i).get("taskDefKey") + "' checked></input><font color=\"red\">"+routeToTasks.get(i).get("taskDefName")+"</font>";
					options += "<input type=\"radio\" name=\"routeToTasks\" style=\"display:none;\" owner='" + routeToTasks.get(i).get("multiInstance") + "' value='" + routeToTasks.get(i).get("taskDefKey") + "' checked></input>";
				//}
			}
			Map<String, Object> tabMap = objectPermissionService.getTabMap(processDefinitionId, routeToTasks.get(0).get("taskDefKey"));
			model.addAttribute("existPerson", tabMap.get("existPerson"));
			model.addAttribute("existDepartment", tabMap.get("existDepartment"));
			model.addAttribute("existGroup", tabMap.get("existGroup"));
			model.addAttribute("existPosition", tabMap.get("existPosition"));
			model.addAttribute("tenantId", tenantId);
			model.addAttribute("options", options);
			// 查找具有权限的流程定义Id
			model.addAttribute("processDefinitionId", processDefinitionId);
			boolean isSingle = taskConfService.isSingle(processDefinitionId, task.getTaskDefinitionKey());
			model.addAttribute("isSingle", isSingle);// 是否单人发送，true为只能选取单个人，false为可以选取多个人
			model.addAttribute("taskDefKey", task.getTaskDefinitionKey());
			model.addAttribute("processInstanceId", processInstanceId);
			model.addAttribute("taskId", taskId);
			model.addAttribute("isSponsorStatus", false);// 是否存在主协办，默认不存在
			String multiInstance = workflowProcessDefinitionService.getMultiinstanceType(processDefinitionId, task.getTaskDefinitionKey());// 获取要发送节点是否在并行状态
			if (multiInstance.equals(SysVariables.PARALLEL)) {
				boolean sponsorStatus = taskConfService.getSponserStatus(processDefinitionId, task.getTaskDefinitionKey());// 获取要发送节点是否存在主协办状态
				model.addAttribute("isSponsorStatus", sponsorStatus);
			}
			
			String documentTitle = (String) taskService.getVariable(taskId, SysVariables.DOCUMENTTITLE);
			model.addAttribute("documentTitle", documentTitle);
			model.addAttribute("userName", ThreadLocalHolder.getPerson().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "document/doc-UserAndRoutaskChoise";
	}

	/**
	 * 获取办结类型，出证办结、文件办结
	 * 
	 * @param taskId
	 * @param request
	 * @return
	 */
	@RiseLog(operateName = "获取办结类型",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/getBanjieType")
	public Map<String, Object> getBanjieType(@RequestParam String processInstanceId,Model model) {
		String approveitemguid=officeSpiDeclareinfoService.findApproveitemguidByProcessInstanceId(processInstanceId);//获取审批事项id
		Map<String, Object> map = new HashMap<String, Object>();
		//ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String PiZhun="";
		try {
			SpmApproveitem spmApproveitem = spmApproveItemService.findByApproveitemguid(approveitemguid);//根据事项id查询事项
			//String doctypeguid ="";
			String approveitemname="";
			if(spmApproveitem!=null){
				//doctypeguid = spmApproveitem.getDoctypeguid();//获取证照类型guid
				approveitemname=spmApproveitem.getApproveitemname();
			}
			PiZhun = (String) runtimeService.getVariable(processInstanceId, "PiZhun");
			/*if(doctypeguid!=null&&doctypeguid!=""){
				map.put("type", "证照");
			}else{
				map.put("type", "文件");
			}*/
			if(approveitemname.equals("取水许可")||approveitemname.equals("城市排水许可")){
				map.put("type", "证照");
			}else{
				map.put("type", "文件");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("PiZhun", "yes");
		return map;
	}
	
	/**
	 * 获取办结类型，出证办结、文件办结
	 * 
	 * @param taskId
	 * @param request
	 * @return
	 */
	@RiseLog(operateName = "判定是否批准",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/getPiZhun")
	public Map<String, Object> getPiZhun(@RequestParam String processInstanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String PiZhun = (String) runtimeService.getVariable(processInstanceId, "PiZhun");//获取该件是否批准，yes为批准，no为不批准
		map.put("PiZhun", PiZhun);
		return map;
	}

	/**
	 * 流程办结
	 * 
	 * @param taskId
	 * @param request
	 * @return
	 */
	@RiseLog(operateName = "流程办结",operateType = "活动")
	@ResponseBody
	@RequestMapping(value = "/complete")
	public Map<String, Object> complete(@RequestParam String taskId, @RequestParam String processInstanceId, @RequestParam String processDefinitionId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = spDocumentService.complete(taskId, processInstanceId, processDefinitionId, request);
		return map;
	}

	/**
	 * 跳转到用户选取部门界面
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RiseLog(operateName = "打开用户选取界面",operateType = "查看")
	@RequestMapping(value = "/departmentChoise", method = RequestMethod.GET)
	public String departmentChoise(@RequestParam String processDefinitionId, @RequestParam String processInstanceId, @RequestParam String taskDefKey, @RequestParam String taskId, Model model) {
		processDefinitionId = objectPermissionService.getPermProcessDefinitionId(processDefinitionId);
		model.addAttribute("existDepartment", true);

		model.addAttribute("processDefinitionId", processDefinitionId);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("taskId", taskId);
		String documentTitle = (String) taskService.getVariable(taskId, SysVariables.DOCUMENTTITLE);
		model.addAttribute("documentTitle", documentTitle);
		model.addAttribute("userName", ThreadLocalHolder.getPerson().getName());
		
		return "document/doc-DepartmentChoise";
	}
	
	
	@RequestMapping(value = "/choiceTab")
	public String choiceTab(String principalType, Model model) {
		model.addAttribute("principalType", principalType);
		return "/document/deptTabContent";
	}
	
	/**
	 * 跳转到用户选取界面
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RiseLog(operateName = "打开用户选取界面",operateType = "查看")
	@RequestMapping(value = "/docUserChoise", method = RequestMethod.GET)
	public String docUserChoise(@RequestParam String processDefinitionId, @RequestParam String processInstanceId, @RequestParam String taskDefKey, @RequestParam String taskId, Model model) {
		processDefinitionId = objectPermissionService.getPermProcessDefinitionId(processDefinitionId);
		Map<String, Object> tabMap = new HashMap<String, Object>();//objectPermissionService.getTabMap(processDefinitionId, taskDefKey);
		List<ObjectPermission> ObjectPermList = objectPermissionService.findByObjectGuidAndTenantId(processDefinitionId, taskDefKey);
		 boolean isSelDept = taskConfService.isSelectedDept(processDefinitionId, taskDefKey);
		for (ObjectPermission o : ObjectPermList) {
	      Integer organizationSize;
	      if (o.getPrincipalType().intValue() == 1) {
	    	Integer personSize = Integer.valueOf(RisesoftUtil.getRoleManager().getPersonsByID("",o.getPrincipalGuid()).size());
	        Integer departmentSize = Integer.valueOf(RisesoftUtil.getRoleManager().getOrgUnitsByID("", o.getPrincipalGuid(), "Department").size());
	        organizationSize = Integer.valueOf(RisesoftUtil.getRoleManager().getOrgUnitsByID("",o.getPrincipalGuid(), "Organization").size());
	        Integer groupSize = Integer.valueOf(RisesoftUtil.getRoleManager().getOrgUnitsByID("",o.getPrincipalGuid(), "Group").size());
	        Integer positionSize = Integer.valueOf(RisesoftUtil.getRoleManager().getOrgUnitsByID("",o.getPrincipalGuid(), "Position").size());
	        if ((personSize.intValue() > 0) && (!isSelDept)) {
	        	tabMap.put("existPerson", Boolean.valueOf(true));
	        }
	        if ((departmentSize.intValue() > 0) || (organizationSize.intValue() > 0)) {
	        	tabMap.put("existDepartment", Boolean.valueOf(true));
	        }
	        if ((groupSize.intValue() > 0) && (!isSelDept)) {
	        	tabMap.put("existGroup", Boolean.valueOf(true));
	        }
	        if ((positionSize.intValue() > 0) && (!isSelDept)) {
	        	tabMap.put("existPosition", Boolean.valueOf(true));
	        }
	      }
	      if (o.getPrincipalType().intValue() == 4) {
	        List<OrgUnit> orgUnitList = dynamicRoleMemberService.getOrgUnitList(o.getPrincipalGuid());
	        for (OrgUnit orgUnit : orgUnitList) {
	          if ((orgUnit.getOrgType().equals("Person")) && (!isSelDept)) {
	        	  tabMap.put("existPerson", Boolean.valueOf(true));
	          }
	          if ((orgUnit.getOrgType().equals("Department")) || (orgUnit.getOrgType().equals("Organization"))) {
	        	  tabMap.put("existDepartment", Boolean.valueOf(true));
	          }
	          if ((orgUnit.getOrgType().equals("Group")) && (!isSelDept)) {
	        	  tabMap.put("existGroup", Boolean.valueOf(true));
	          }
	          if ((orgUnit.getOrgType().equals("Position")) && (!isSelDept)) {
	        	  tabMap.put("existPosition", Boolean.valueOf(true));
	          }
	        }
	      }
	    }
        model.addAttribute("existPerson", tabMap.get("existPerson"));
		model.addAttribute("existDepartment", tabMap.get("existDepartment"));
		model.addAttribute("existGroup", tabMap.get("existGroup"));
		model.addAttribute("existPosition", tabMap.get("existPosition"));

		model.addAttribute("processDefinitionId", processDefinitionId);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("taskDefKey", taskDefKey);
		model.addAttribute("taskId", taskId);

		return "document/doc-UserChoise";
	}

	/**
	 * 发送
	 * @param sponsorGuid 主办人guid
	 * @param taskId 任务id
	 * @param userChoice 接收人
	 * @param routeToTaskId 任务路由
	 * @param formIds
	 * @param request
	 * @param model
	 * @return
	 */
	@RiseLog(operateName = "发送待办件",operateType = "发送")
	@ResponseBody
	@RequestMapping(value = "/forwarding")
	public Map<String, Object> forwarding(@RequestParam(required = false) String itembox,@RequestParam(required = false) String sponsorGuid,@RequestParam(required = false) String taskId, @RequestParam String userChoice, @RequestParam(required = false) String routeToTaskId, @RequestParam String formIds, HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = spDocumentService.forwarding(itembox,sponsorGuid,taskId, userChoice, routeToTaskId, formIds, request, model);
		return map;
	}

	/**
	 * 根据表单模板ID查询绑定的表是否有数据
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RiseLog(operateName = "查询模板绑定的表是否有数据",operateType = "查询")
	@ResponseBody
	@RequestMapping(value = "/getData")
	public Map<String, Object> getData(@RequestParam(required = false) String tempId, @RequestParam(required = false) String processInstanceId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		long num = spDocumentService.getData(tempId, processInstanceId);
		if(num==0){//根据流程实例processInstanceId，查询绑定的表是否已经存在数据，如果存在，模板为编辑模式
			map.put("edittype", "0");
		}else{
			map.put("edittype", "1");
		}
		return map;
	}

	/**
	 * 根据表单模板ID查询绑定的表是否有数据
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RiseLog(operateName = "保存审批处理信息",operateType = "保存")
	@ResponseBody
	@RequestMapping(value = "/saveShenpichuli")
	public Map<String, Object> saveShenpichuli(@RequestParam(required = false) String routeToTaskId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String xiangmumingcheng = request.getParameter("jbxx_xiangmumingcheng");
		String declarerperson = request.getParameter("jbxx_declarerperson");
		String address = request.getParameter("jbxx_address");
		String declarerlxr = request.getParameter("jbxx_declarerlxr");
		String declarertel = request.getParameter("jbxx_declarertel");
		String bureauname = request.getParameter("jbxx_bureauname");
		String acceptanceTime = request.getParameter("jbxx_acceptanceTime");
		String declaresn = request.getParameter("jbxx_declaresn");
		String employeedeptname = request.getParameter("jbxx_employeedeptname");
		String zixundianhua = request.getParameter("jbxx_zixundianhua");
		String limiTime = request.getParameter("jbxx_limiTime");
		String chengnuoriqi = request.getParameter("jbxx_chengnuoriqi");
		
		//ShenpichuliProcess sp = new ShenpichuliProcess();
		
		return map;
	}

}