package net.risesoft.approve.service.impl.workflow;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.workflow.SpDocumentService;
import net.risesoft.approve.service.workflow.SpWorkFlowService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.service.SpActivitiOptService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

@Service(value="spWorkFlowService")
public class SpWorkFlowServiceImpl implements SpWorkFlowService {

	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Autowired
	private SpActivitiOptService spActivitiOptService;
	
	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;
	
	@Autowired
	private SpDocumentService spDocumentServise;
	
	@Override
	public Model startProcess(String processDefinitionId,String instanceId,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		String tenantId = ThreadLocalHolder.getTenantId();
		String personId = person.getID(), deptId = person.getParentID(),processSerialNumber = GuidUtil.genGuid();
		String activitiUser = personId + SysVariables.COLON + deptId;
		String processDefinitionKey = processDefinitionId.split(SysVariables.COLON)[0];
		OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(instanceId);
		Task task = spActivitiOptService.startProcess(processSerialNumber, officeSpiDeclareinfo.getApproveItemName(), processDefinitionKey, activitiUser,officeSpiDeclareinfo.getApproveitemguid(),officeSpiDeclareinfo.getDeclaresn(),instanceId);
		List<String> startNodeIdList = workflowProcessDefinitionService.getNodeName(processDefinitionId, SysVariables.STARTEVENT);
		// 获取开始节点的目标节点Id，这里暂时还是只考虑目标节点只有一个的情况，如果有多个则需要根据配置进行选取，或者将其给用户，让用户选择
		List<Map<String, String>> targetIdList = workflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId, startNodeIdList.get(0));
		String taskDefKey = targetIdList.get(0).get(SysVariables.TASKDEFKEY);// 为了查找表单而获取taskDefKey
		String processInstanceId = task.getProcessInstanceId(),taskId = task.getId();
		//审批业务数据和工作流流程相关联
		officeSpiDeclareinfoService.updateOfficeSpiDeclareinfo(instanceId,processInstanceId);
		
		model.addAttribute("SPinstanceId", instanceId);
		model = spDocumentServise.genDocumentModel(SysVariables.TODO, processSerialNumber, tenantId,processDefinitionKey, processDefinitionId, processInstanceId, taskId, taskDefKey, activitiUser, model);
		model = spDocumentServise.menuControl(processDefinitionId,taskDefKey,taskId, model, SysVariables.ADD,instanceId);

		return model;
	}

}
