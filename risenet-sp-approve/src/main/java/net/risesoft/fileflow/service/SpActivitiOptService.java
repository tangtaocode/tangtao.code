package net.risesoft.fileflow.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.approve.service.supervise.FTSuperviseService;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.activiti.handler.CommonOpt;
import net.risesoft.fileflow.entity.jpa.ReminderDefine;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpActivitiOptService {

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	protected TaskService taskService;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;
	
	@Resource(name="reminderDefineService")
	private ReminderDefineService reminderDefineService;
	
	@Resource(name="fTSuperviseService")
	private FTSuperviseService fTSuperviseService;
	
	/**
	 * 启动流程，启动流程之所以根据tenantId写成两个而不是根据tenantId判断采用哪种startProcess，是为了要确保tenantId存在
	 * @param processSerialNumber
	 * @param documentTitle
	 * @param processDefinitionKey
	 * @param activitiUser
	 * @return
	 */
	public Task startProcess(String processSerialNumber,String documentTitle, String processDefinitionKey, String activitiUser,String approveItemGUID,String declaresn,String SPinstanceId) {
		// 在一个事务中保存。taskId为空则创建新流程。
		Person person = ThreadLocalHolder.getPerson();
		String tenantId=ThreadLocalHolder.getTenantId();
		Task task = null;
		//启动流程
		try {
			Map<String, Object> varMap = CommonOpt.setVariables(activitiUser, person.getName(), "", Arrays.asList(activitiUser), processSerialNumber,documentTitle, "");
			//int isBuqibuzheng=fTSuperviseService.isBuqibuzheng(SPinstanceId);
			varMap.put(SysVariables.BUQIBUZHENG,1);//"补齐补正状态"
			varMap.put(SysVariables.STARTOR, activitiUser);//任务由谁启动
			varMap.put(SysVariables.APPROVEITEMGUID, approveItemGUID);//把审批事项guid放入流程变量
			varMap.put(SysVariables.DECLARESN, declaresn);//把审批事项业务流水号放入流程变量
			ProcessInstance processInstance = startProcess(processDefinitionKey, varMap);
			//获取运行的任务节点，由于是刚启动，因此只有一个
			String processInstanceId = processInstance.getProcessInstanceId();
			task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
			String processDefinitionId=task.getProcessDefinitionId(),taskDefKey=task.getTaskDefinitionKey();
			ReminderDefine rd=reminderDefineService.findIdByProcessDefinitionIdAndTaskDefKeyAndTenantIdAndCustomId(processDefinitionId,taskDefKey, tenantId, approveItemGUID);
			if(null!=rd){
				taskService.setDueDate(task.getId(), reminderDefineService.getAdvanceDate(task.getCreateTime(), rd.getDay_Type(), rd.getTaskDuration()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//identityService.setAuthenticatedUserId(null);
		}
		return task;
	}
	/**
	 * 启动流程，由于可能会出现开始节点后存在多个节点的情况，这里单独写一个启动流程的方法
	 * @param processDefinitionKey 流程定义Key
	 * @param varMap 
	 * @return
	 */
	private ProcessInstance startProcess(String processDefinitionKey, Map<String, Object> varMap) {
		ProcessInstance processInstance = null;
		if (StringUtils.isNotBlank(processDefinitionKey)) {
			//流程未启动，这里根据流程定义Key（例如luohubanwen）获取流程定义Id（例如luohubanwen:2:23105）
			String processDefinitionId = workflowProcessInstanceService.getProcessDefinitionId(processDefinitionKey);
			//获取开始节点的名称
			List<String> startNodeIdList = workflowProcessDefinitionService.getNodeName(processDefinitionId, SysVariables.STARTEVENT);
			//获取开始节点的目标节点Id，这里暂时还是只考虑目标节点只有一个的情况，如果有多个则需要根据配置进行选取，或者将其给用户，让用户选择
			List<Map<String, String>> targetIdList = workflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId, startNodeIdList.get(0));
			varMap.put(SysVariables.ROUTETOTASKID, targetIdList.get(0).get(SysVariables.TASKDEFKEY));
			processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, varMap);
		}
		return processInstance;
	}
}
