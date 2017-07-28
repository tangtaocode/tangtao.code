package net.risesoft.fileflow.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.approve.entity.jpa.FtSupervise;
import net.risesoft.approve.entity.jpa.TBujiaogaozhiUnits;
import net.risesoft.approve.service.MaterialListService;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.supervise.BujiaoGaozhiService;
import net.risesoft.approve.service.supervise.FTSuperviseService;
import net.risesoft.approve.service.workflow.SpDocumentService;
import net.risesoft.approve.service.workflow.SpRollBackTaskService;
import net.risesoft.approve.service.workflow.SpWorkflowProcessInstanceService;
import net.risesoft.common.util.WorkdayUtils;
import net.risesoft.fileflow.entity.jpa.RejectReason;
import net.risesoft.fileflow.service.EntrustService;
import net.risesoft.fileflow.service.RejectReasonService;
import net.risesoft.fileflow.service.ReminderDefineService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.utilx.DateX;
import net.risesoft.utilx.FTSuperviseUtil;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sp/buttonOperation")
public class SpButtonOperationController {

	protected Logger log = LoggerFactory.getLogger(SpButtonOperationController.class);

	@Autowired
	protected TaskService taskService;
	
	@Autowired
	private EntrustService entrustService;
	
	@Autowired
	protected RuntimeService runtimeService;
	
	@Autowired
	private WorkflowTaskService workflowTaskService;
	
	@Autowired
	private SpWorkflowProcessInstanceService spWorkflowProcessInstanceService;

	@Resource(name="reminderDefineService")
	private ReminderDefineService reminderDefineService;
	
	@Resource(name="bujiaoGaozhiService")
	private BujiaoGaozhiService bujiaoGaozhiService;
	
	@Resource(name="officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Resource(name = "spRollBackTaskService")
	private SpRollBackTaskService spRollBackTaskService;
	
	@Autowired
	private FTSuperviseService fTSuperviseService;
	
	@Autowired
	private SpDocumentService spDocumentService;
	
	@Resource(name="rejectReasonService")
	private RejectReasonService rejectReasonService;
	
	@Resource(name="routerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "materialListService")
	private MaterialListService materialListService;
	/**
	 * 用于补齐补正告知(挂起流程)
	 * @param taskId
	 * @return
	 */
	@RiseLog(operateName = "补齐补正告知",operateType = "活动")
	@ResponseBody
	@RequestMapping(value="/buQiBuZheng/gaoZhi")
	public Map<String,Object> buQiBuZheng4GaoZhi(String taskId,String ytjids,String xbqids,String xbzids,String bz,String reason){
		Map<String,Object> map=new HashMap<String, Object>();
		String feedback = "需补齐材料";
		try {
			TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
			String instanceId=officeSpiDeclareinfoService.getGuidByProcessInstanceId(task.getProcessInstanceId());
			//FtSupervise ftSupervise = FTSuperviseUtil.getFTCode(jdbcTemplate, instanceId);
			if(null!=task.getDueDate()){
				taskService.setDueDate(task.getId(), reminderDefineService.getAdvanceDate(task.getDueDate(),2,15));
			}
			if(task.getSuspensionState()==1){
				//向t_bujiaogaozhi表中插入数据
				//bujiaoGaozhiService.saveBuqibuzheng(instanceId,feedback,xbqids,xbzids,1);
				bujiaoGaozhiService.saveBuZhengGaoZhi(task.getProcessInstanceId(), reason, xbqids, xbzids, bz);
				materialListService.updateReply(feedback, ytjids, xbqids, xbzids, instanceId);//向t_bujiaogaozhi表中插入数据
				taskService.setVariable(taskId, SysVariables.BUQIBUZHENGSTATE, "补齐补正告知");
				taskService.setVariable(taskId, SysVariables.BUQIBUZHENG, 2);
				taskService.setVariable(taskId, SysVariables.BuQiBuZhengUser, ThreadLocalHolder.getPerson().getID());
				runtimeService.suspendProcessInstanceById(task.getProcessInstanceId());
				map.put("success", true);
				map.put("msg", "补齐补正告知成功！");
				log.info("用户："+RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(), task.getAssignee().split(SysVariables.COLON)[0]).getName()+"在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"挂起流程："+task.getProcessInstanceId()+",当前任务在："+task.getTaskDefinitionKey()+",补齐补正天数为15天。");
			}else{
				map.put("success", false);
				map.put("msg", "补齐补正告知失败！已进行补齐补正告知");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "补齐补正失败！");
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 用于补齐补正受理(激活流程)
	 * @param taskId
	 * @return
	 */
	@RiseLog(operateName = "补齐补正受理",operateType = "活动")
	@ResponseBody
	@RequestMapping(value="/buQiBuZheng/shouLi")
	public Map<String,Object> buQiBuZheng4ShouLi(String taskId){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
			String instanceId=officeSpiDeclareinfoService.getGuidByProcessInstanceId(task.getProcessInstanceId());
			FtSupervise ftSupervise = FTSuperviseUtil.getFTCode(jdbcTemplate, instanceId);
			if(task.getSuspensionState()==2){
				String declaresn=officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(task.getProcessInstanceId());
				//查找补交告知时间
				//TBujiaogaozhiUnits tBujiaogaozhiUnits=new TBujiaogaozhiUnits(ftSupervise.getYwlsh(), new Long(1));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String bjgz = bujiaoGaozhiService.findBjgzTime(declaresn);
				Date bjgzsj= sdf.parse(bjgz);
				//补交受理时间
				Date BjsjDate=new Date();
				
				//保存补齐补正受理
				//int isbqbzsl = bujiaoGaozhiService.isBuqibuzhengShouli(instanceId);
				/*if(isbqbzsl==0){
					bujiaoGaozhiService.saveBuqibuzhengshouli(instanceId);
				}*/
				
				runtimeService.activateProcessInstanceById(task.getProcessInstanceId());
				//计算补齐补正用的时间
				WorkdayUtils workdayUtils=new WorkdayUtils();
				int buqibuzhengDayCount=workdayUtils.getWorkdayCount(bjgzsj, BjsjDate);
				taskService.setDueDate(task.getId(), reminderDefineService.getAdvanceDate(task.getCreateTime(),2,buqibuzhengDayCount));
				taskService.setVariable(taskId, SysVariables.BUQIBUZHENG, 0);
				map.put("success", true);
				map.put("msg", "补齐补正受理成功！");
				log.info("用户："+RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(), task.getAssignee().split(SysVariables.COLON)[0]).getName()+"在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"激活流程："+task.getProcessInstanceId()+",当前任务在："+task.getTaskDefinitionKey()+",补齐补正天数为15天。");
			}else{
				map.put("success", false);
				map.put("msg", "补齐补正受理失败！补齐补正状态已解除！");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "补齐补正受理失败！");
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 保存审批的科室领导批准的状态
	 * @param pizhun
	 * @param taskId
	 * @return
	 */
	@RiseLog(operateName = "批准",operateType = "活动")
	@ResponseBody
	@RequestMapping(value="/saveData4PiZhun")
	public Map<String,Object> saveData4PiZhun(String pizhun,String taskId){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			/**
			 * 保存"批准"到task的description字段，用来记录批准的状态
			 */
			TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
			
			//ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			//将该件是否批准的状态存到流程变量中，1.用来窗口人员办结时判定是否批准；2.用来判定该件是否已经批准过。
			runtimeService.setVariable(task.getProcessInstanceId(), "PiZhun", "yes");
			
			task.setDescription("批准");
			taskService.saveTask(task);
			//保存批准的状态到流程变量，用于控制"退回"按钮(批准之后不可以再退回)
			taskService.setVariable(taskId, "piZhun", true);
			/**
			 * 这里针对科长负责制(或者类似的当前配置"批准、不予批准"的节点只有一个目标节点)
			 */
			List<Map<String, String>> routeToTasks = spWorkflowProcessInstanceService.getCurrentTaskTargets(task.getProcessDefinitionId(),task.getTaskDefinitionKey());
			String taskDefkey=routeToTasks.get(0).get(SysVariables.TASKDEFKEY);
			map.put("routeToTaskId", taskDefkey);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	
	@RiseLog(operateName = "不予批准",operateType = "活动")
	@ResponseBody
	@RequestMapping(value="/saveData4BuYuPiZhun")
	public Map<String,Object> saveData4BuYuPiZhun(String buYuPiZhun,String taskId){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			/**
			 * 保存"不予批准"到task的description字段，用来记录不予批准的状态
			 */
			TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
			//将该件是否批准的状态存到流程变量中，1.用来窗口人员办结时判定是否批准；2.用来判定该件是否已经批准过。
			runtimeService.setVariable(task.getProcessInstanceId(), "PiZhun", "no");
			
			task.setDescription("不予批准");
			taskService.saveTask(task);
			/**
			 * 这里针对科长负责制(或者类似的当前配置"批准、不予批准"的节点只有一个目标节点)
			 */
			List<Map<String, String>> routeToTasks = spWorkflowProcessInstanceService.getCurrentTaskTargets(task.getProcessDefinitionId(),task.getTaskDefinitionKey());
			String taskDefkey=routeToTasks.get(0).get(SysVariables.TASKDEFKEY);
			map.put("routeToTaskId", taskDefkey);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 特别程序申请：挂起流程，设置特别程序申请时限
	 * @param taskId
	 * @return
	 */
	@RiseLog(operateName = "特别程序申请",operateType = "活动")
	@ResponseBody
	@RequestMapping(value="/teBieChengXuShenQing")
	public Map<String,Object> teBieChengXuShenQing(String taskId){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
			if(null!=task.getDueDate()){
				//查询特别程序的日期类型和天数
				taskService.setDueDate(task.getId(), reminderDefineService.getAdvanceDate(task.getDueDate(),2,15));
			}
			runtimeService.suspendProcessInstanceById(task.getProcessInstanceId());//挂起流程
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 特别程序结果：激活流程，设置流程节点时限
	 * @param taskId
	 * @return
	 */
	@RiseLog(operateName = "特别程序处理",operateType = "活动")
	@ResponseBody
	@RequestMapping(value="teBieChengXuShenQingChuLi")
	public Map<String,Object> teBieChengXuShenQingChuLi(String taskId){
		Map<String,Object> map=new HashMap<String, Object>();
		TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
		runtimeService.activateProcessInstanceById(task.getProcessInstanceId());
		if(null!=task.getDueDate()){
			//算出特别程序开始时间到现在的用时，再用时限日期减去未使用的天数，最后为当前任务的时限
			taskService.setDueDate(task.getId(), reminderDefineService.getAdvanceDate(task.getDueDate(),2,15));
		}
		
		return map;
	}
	
	/**
	 * 特殊办结意见填写
	 * 
	 * @param taskId
	 */
	@RiseLog(operateName = "特殊办结意见填写",operateType = "增加")
	@RequestMapping(value = "/specialComplete/show")
	public String show(String taskId, Model model) {
		TaskEntity task=workflowTaskService.getTaskByTaskId(taskId);
		RejectReason specialCompleteReason = rejectReasonService.findByProcessInstanceIdAndActionSign(task.getProcessInstanceId(), "特殊办结");
		if (specialCompleteReason != null) {
			model.addAttribute("specialCompleteReason", specialCompleteReason);
		} else {
			model.addAttribute("actionSign", "特殊办结");
			model.addAttribute("processInstanceId", task.getProcessInstanceId());
			model.addAttribute("documentTitle", (String)taskService.getVariable(taskId, "documentTitle"));
		}
		model.addAttribute("taskId",taskId);
		return "document/spSpecialCompleteReason";
	}

	/**
	 * 特殊办结
	 * 
	 * @param taskId
	 */
	@RiseLog(operateName = "特殊办结",operateType = "活动")
	@ResponseBody
	@RequestMapping(value = "/specialComplete")
	public Map<String, Object> specialComplete(String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> variables = taskService.getVariables(taskId);
			variables.put("user4Complete", ThreadLocalHolder.getPerson().getName());
			variables.put("actionName4SpecialComplete", "特殊办结");
			spRollBackTaskService.rollBackTask(taskId, "end", variables);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("办理人:" + ThreadLocalHolder.getPerson().getName() + ",进行特殊办结失败！！！");
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 办理完成，并行处理时使用
	 * 
	 * @param taskId
	 * @param processDefinitionKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/handleParallel")
	public Map<String, Object> handleParallel(@RequestParam String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//保存过程数据
			spDocumentService.saveGuoChengShuJu(taskId);
			taskService.complete(taskId);
			map.put("success", true);
			map.put("msg", "办理成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "办理失败");
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 送下一人，串行时使用
	 * 
	 * @param taskId
	 * @param model
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/handleSerial")
	public Map<String, Object> handleSerial(@RequestParam String taskId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String personId = ThreadLocalHolder.getPerson().getID();
			Task task = workflowTaskService.getTaskByTaskId(taskId);
			String processDefinitionId = task.getProcessDefinitionId();
			String taskDefinitionKey = task.getTaskDefinitionKey();
			Map<String, Object> vars = taskService.getVariables(task.getId());// 获取流程中当前任务的所有变量
			String documentTitle = (String) vars.get(SysVariables.DOCUMENTTITLE);
			saveTitle(taskId, documentTitle);
			List<String> vars_users = new ArrayList<String>();
			vars_users = (List<String>) vars.get(SysVariables.USERS);
			for (int i = 0; i < vars_users.size(); i++) {
				if (vars_users.get(i).contains(personId)) {
					// 获取当前串行节点的当前用户的下一个办理用户是否有出差委托设置
					String nextUserId = vars_users.get(i + 1).split(SysVariables.COMMA)[0];
					String delegateUserId = entrustService.findUserId(nextUserId, processDefinitionId, taskDefinitionKey);
					vars_users.set(i + 1, delegateUserId);
				}
			}
			vars.put(SysVariables.USERS, vars_users);
			//保存过程数据
			spDocumentService.saveGuoChengShuJu(taskId);
			
			taskService.complete(task.getId(), vars, false);

			map.put("success", true);
			map.put("msg", "办理成功!");
		} catch (Exception e) {
			map.put("msg", "办理失败!");
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 当保存表单或者发送时，标题有可能发生改变，因此需要重新保存一次
	 * 
	 * @param taskId
	 * @param documentTitle
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveTitle")
	public Map<String, Object> saveTitle(@RequestParam String taskId, @RequestParam String documentTitle) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			taskService.setVariable(taskId, SysVariables.DOCUMENTTITLE, documentTitle);
			boolean b=RisesoftCommonUtil.getTodoManager().updateTitle(taskId, ThreadLocalHolder.getPerson().getID(), documentTitle);
			log.info("##########更新超级待办标题："+(b?"成功":"失败！！！")+"######################");
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("reason", e.getMessage());
		}
		return map;
	}
	
}
