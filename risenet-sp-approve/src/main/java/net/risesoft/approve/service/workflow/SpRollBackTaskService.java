package net.risesoft.approve.service.workflow;

import java.util.Map;

public interface SpRollBackTaskService {
	/**
	 * 流程转向操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点任务ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	public void rollBackTask(String currentTaskId, String activityId,Map<String, Object> variables) throws Exception;
}