package net.risesoft.approve.service.workflow;

import org.springframework.ui.Model;

public interface SpWorkFlowService {
	
	/**
	 * 启动流程
	 * @param processDefinitionId
	 */
	Model startProcess(String processDefinitionId,String instanceId,Model model);
}
