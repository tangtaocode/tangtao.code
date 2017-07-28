package net.risesoft.approve.service.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;

import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpWorkflowProcessInstanceService {

	private static final String ENDEVENT="endEvent";//流程图终结点的节点类型
	
	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;
	/**
	 * 获取当前任务节点的目标节点,如果目标节点是补齐补正或者退回的作用就不路由，用按钮来操作
	 * @param taskId 任务Id
	 * @return
	 */
	public List<Map<String, String>> getCurrentTaskTargets(String processDefinitionId,String taskDefKey)
	{
		List<Map<String, String>> targetTasks = new ArrayList<Map<String, String>>();
		List<PvmTransition> outTransitions = workflowProcessInstanceService.getPvmTransitions(processDefinitionId,taskDefKey);// 获取从某个节点出来的所有线路
		for (PvmTransition tr : outTransitions) {
			String name=(String)tr.getProperty("name");
			if(!("退回".equals(name))){
				PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
				if(!ac.getProperty("type").toString().equals(ENDEVENT)) {//endEvent，终节点的类型，由于发送路径选取时不要这种类型节点，这里要去掉
					Map<String, String> map = new HashMap<String, String>();
					if(StringUtils.isNotBlank(name)){
						map.put(SysVariables.TASKDEFNAME,name);
					}else{
						map.put(SysVariables.TASKDEFNAME, ac.getProperty("name").toString());//nodeName是流程图中节点的name
					}
					map.put(SysVariables.TASKDEFKEY, ac.getId());//nodeId是流程图中节点的Id
					if(null != ac.getProperty("multiInstance")){
						map.put(SysVariables.MULTIINSTANCE, ac.getProperty("multiInstance").toString());
					}
					targetTasks.add(map);
				}
			}
		}
		return targetTasks;
	}
	
	/**
	 * 获取当前任务节点的默认目标节点,如果目标节点是补齐补正或者退回的作用就不路由，用按钮来操作
	 * @param taskId 任务Id
	 * @return
	 */
	public List<Map<String, String>> getCurrentTaskDefultTargets(String processDefinitionId,String taskDefKey)
	{
		List<Map<String, String>> targetTasks = new ArrayList<Map<String, String>>();
		List<PvmTransition> outTransitions = workflowProcessInstanceService.getDefultPvmTransitions(processDefinitionId,taskDefKey);// 获取从某个节点出来的所有线路
		for (PvmTransition tr : outTransitions) {
			String name=(String)tr.getProperty("name");
			if(!("退回".equals(name))){
				PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
				if(!ac.getProperty("type").toString().equals(ENDEVENT)) {//endEvent，终节点的类型，由于发送路径选取时不要这种类型节点，这里要去掉
					Map<String, String> map = new HashMap<String, String>();
					if(StringUtils.isNotBlank(name)){
						map.put(SysVariables.TASKDEFNAME,name);
					}else{
						map.put(SysVariables.TASKDEFNAME, ac.getProperty("name").toString());//nodeName是流程图中节点的name
					}
					map.put(SysVariables.TASKDEFKEY, ac.getId());//nodeId是流程图中节点的Id
					if(null != ac.getProperty("multiInstance")){
						map.put(SysVariables.MULTIINSTANCE, ac.getProperty("multiInstance").toString());
					}
					targetTasks.add(map);
				}
			}
		}
		return targetTasks;
	}
	
}
