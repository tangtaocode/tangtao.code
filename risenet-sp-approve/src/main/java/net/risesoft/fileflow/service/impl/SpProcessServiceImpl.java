package net.risesoft.fileflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.risesoft.fileflow.service.SpProcessService;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="spProcessService")
public class SpProcessServiceImpl implements SpProcessService {

	@Autowired
	protected RepositoryService repositoryService;
	
	@Override
	public List<Map<String, String>> processList() {
		List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
		try {
			ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion().orderByDeploymentId().desc();
			List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
			
			for (ProcessDefinition processDefinition : processDefinitionList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("key", processDefinition.getKey());
				map.put("name", processDefinition.getName());
				listMap.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMap;
	}

}
