package net.risesoft.fileflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.risesoft.common.util.WorkflowUtils;
import net.risesoft.fileflow.service.DeployService;
import net.risesoft.fileflow.service.ResourceService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowTraceService;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping(value = "/workflow")
public class DeployController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ProcessEngineConfigurationImpl processEngineConfiguration;

	@Autowired
	protected WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Autowired
	protected RepositoryService repositoryService;
	
	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected WorkflowTraceService traceService;

	protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();

	@Autowired
	ProcessEngineFactoryBean processEngine;
	
	@Resource(name="resourceService")
	private ResourceService resourceService;
	
	@Resource(name="deployService")
	private DeployService deployService;
	
	/**
	 * 显示流程定义列表
	 * 
	 * @return 视图名称
	 */
	@RequestMapping(value = "/process/list/show", method = RequestMethod.GET)
	public String showProcessList() {
		return "worklist/process/processlist";
	}

	/**
	 * 流程定义列表数据
	 * 
	 * @return JSON数据
	 */
	@RequestMapping(value = "/process/list")
	@ResponseBody
	public Map<String, Object> processList(@RequestParam(required = false) String procDefKey, @RequestParam(required = false) String procDefName, @RequestParam(required = false) int page, @RequestParam(required = false) int rows, @RequestParam(required = false) String sidx,
			@RequestParam(required = false) String sord) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

		if (page == 0) {
			page = 1;
		}

		String sql = "select RES.* from ACT_RE_PROCDEF RES WHERE 1=1";
		if ("mssql".equals(processEngineConfiguration.getDatabaseType())) {
			sql = "select top 100 percent RES.* from ACT_RE_PROCDEF RES WHERE 1=1";
		}

		if (StringUtils.isNotBlank(procDefKey)) {
			sql = sql + " and RES.KEY_ like '%" + procDefKey + "%'";
		}
		if (StringUtils.isNotBlank(procDefName)) {
			sql = sql + " and RES.NAME_ like '%" + procDefName + "%'";
		}
		sql = sql + " and RES.VERSION_ = (select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = RES.KEY_ ) order by RES.KEY_ asc";

		try {
			ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion().orderByDeploymentId().desc();
			List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage((page - 1) * rows, rows);

			long totalCount = processDefinitionList.size();
			for (ProcessDefinition processDefinition : processDefinitionList) {
				String deploymentId = processDefinition.getDeploymentId();
				Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", processDefinition.getId());
				map.put("deploymentId", processDefinition.getDeploymentId());
				map.put("name", processDefinition.getName());
				map.put("key", processDefinition.getKey());
				map.put("version", processDefinition.getVersion());
				map.put("resourceName", processDefinition.getResourceName());
				map.put("diagramResourceName", processDefinition.getDiagramResourceName());
				map.put("suspended", processDefinition.isSuspended());
				map.put("deploymentTime", DateFormatUtils.format(deployment.getDeploymentTime(), "yyyy-MM-dd HH:mm:ss"));
				items.add(map);
			}
			ret_map.put("currpage", page);
			//当totalCount<rows时，Math.ceil(totalCount / rows)为0.0，此时页面显示的时候出错，所以要+1
			ret_map.put("totalpages", Math.ceil(totalCount / rows) + 1);
			ret_map.put("total", totalCount);
			ret_map.put("rows", items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret_map;
	}

	/**
	 * 部署全部流程
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/redeploy/all")
	public String redeployAll() throws Exception {
		workflowProcessDefinitionService.deployAllFromClasspath();
		return "redirect:/workflow/process/list/show";
	}

	/**
	 * 流程配置的列表页面
	 * @return
	 */
	@RequestMapping(value = "config/bpmList")
	public String bpmList() {
		return "config/workflow/bpmList";
	}

	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param processDefinitionId
	 *            流程定义
	 * @param resourceType
	 *            资源类型(xml|image)
	 * @throws Exception
	 */
	@RequestMapping(value = "/resource/read")
	public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType, HttpServletResponse response) throws Exception {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
		if (resourceType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 读取图片资源
	 * 在在办件、待办件中使用processInstanceId，因为需要知道当前的活动节点并标红
	 * 但在流程部署列表页面中查看流程图时只有processDefinitionId，没有processInstanceId
	 * 基于以上两种情况，传递的参数不同，因此这里也分了两种情况
	 * @param resourceType
	 *            资源类型(xml|image)
	 * @param processInstanceId
	 *            流程实例ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/resource/process-instance")
	public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, @RequestParam("processDefinitionId") String processDefinitionId, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = null;
		ProcessDefinition processDefinition = null;
		if (StringUtils.isNotBlank(processInstanceId) || StringUtils.isNotBlank(processDefinitionId)) {
			if (StringUtils.isNotBlank(processInstanceId)) {
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
				processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
			} else if (StringUtils.isNotBlank(processDefinitionId)) {
				processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
			}
		}

		if (processDefinition != null) {
			String resourceName = "";
			if (resourceType.equals("image")) {
				resourceName = processDefinition.getDiagramResourceName();
			} else if (resourceType.equals("xml")) {
				resourceName = processDefinition.getResourceName();
			}
			resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		}
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 删除部署的流程，级联删除流程实例，如果统一流程部署多次，则每次只删掉一个版本
	 * 
	 * @param deploymentId
	 *            流程部署ID
	 */
	@RequestMapping(value = "/process/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam String deploymentId) {
		Map<String, Object> ret_map = new HashMap<String, Object>();

		try {
			deployService.deleteDeployment(deploymentId);;
			ret_map.put("success", true);
			ret_map.put("msg", "流程级联删除成功。");
		} catch (Exception e) {
			ret_map.put("success", false);
			ret_map.put("msg", "流程级联删除失败。");
			e.printStackTrace();
		}
		return ret_map;
	}

	/**
	 * 输出跟踪流程信息
	 * 在在办件、待办件中使用processInstanceId，因为需要知道当前的活动节点并标红
	 * 但在流程部署列表页面中查看流程图时只有processDefinitionId，没有processInstanceId
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/process/trace")
	@ResponseBody
	public List<Map<String, Object>> traceProcess(@RequestParam("pid") String processInstanceId, @RequestParam("processDefinitionId") String processDefinitionId) throws Exception {
		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(processInstanceId) || StringUtils.isNotBlank(processDefinitionId)) {
			if (StringUtils.isNotBlank(processInstanceId)) {
				activityInfos = traceService.traceProcess(processInstanceId);
			} else {
				activityInfos = traceService.traceProcess1(processDefinitionId);
			}
		}

		return activityInfos;
	}

	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "/process/trace/auto/{executionId}")
	public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response) throws Exception {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);

		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());

		DefaultProcessDiagramGenerator pdg = new DefaultProcessDiagramGenerator();
		InputStream imageStream = pdg.generateDiagram(bpmnModel, "png", activeActivityIds);

		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	@RequestMapping(value = "/deploy", produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> deploy(@RequestParam(value = "barfile", required = false) MultipartFile file) {
		HashMap<String, Object> ret_map = new HashMap<String, Object>();
		try {
			String fileName = file.getOriginalFilename();
			InputStream fileInputStream = file.getInputStream();
			Deployment deployment = null;

			String extension = FilenameUtils.getExtension(fileName);
			if (extension.equals("zip") || extension.equals("rar") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
			} else {
				deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
			}

			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

			for (ProcessDefinition processDefinition : list) {
				WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition);
				resourceService.createResource(processDefinition.getId(), processDefinition.getKey(), processDefinition.getName());
			}

			ret_map.put("success", true);
			ret_map.put("msg", "流程部署成功。");
		} catch (Exception e) {
			e.printStackTrace();
			ret_map.put("success", false);
			ret_map.put("msg", e.getMessage());
		}

		HttpHeaders headers = new HttpHeaders();
		MediaType mt = new MediaType("text", "html", Charset.forName("utf-8"));
		headers.setContentType(mt);
		ResponseEntity<HashMap<String, Object>> re = null;
		re = new ResponseEntity<HashMap<String, Object>>(ret_map, headers, HttpStatus.OK);
		return re;
	}

	@RequestMapping(value = "/process/convert-to-model/{processDefinitionId}")
	public String convertToModel(@PathVariable("processDefinitionId") String processDefinitionId) throws UnsupportedEncodingException, XMLStreamException {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modelNode = converter.convertToJson(bpmnModel);

		Model modelData = repositoryService.newModel();
		List<Model> list = repositoryService.createModelQuery().modelKey(processDefinition.getKey()).orderByLastUpdateTime().desc().listPage(0, 1);
		if (list.size() > 0) {
			modelData = list.get(0);
		} else {
			modelData.setKey(processDefinition.getKey());
			modelData.setName(processDefinition.getName());
			modelData.setCategory(processDefinition.getCategory());

			ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
			modelData.setMetaInfo(modelObjectNode.toString());

			repositoryService.saveModel(modelData);
		}
		repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

		return "worklist/process/model-list";
	}

	/**
	 * 挂起、激活流程实例
	 */
	@RequestMapping(value = "/switchSuspendOrActive")
	@ResponseBody
	public Map<String, Object> switchSuspendOrActive(@RequestParam String state, @RequestParam String processDefinitionId) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		try {
			if (state.equals("active")) {
				repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
				ret_map.put("success", true);
				ret_map.put("msg", "激活流程实例成功。");
			} else if (state.equals("suspend")) {
				repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
				ret_map.put("success", true);
				ret_map.put("msg", "挂起流程实例成功。");
			}
		} catch (Exception e) {
			ret_map.put("success", false);
			ret_map.put("msg", e.getMessage());
		}

		return ret_map;
	}

	/**
	 * 导出图片文件到硬盘
	 * 
	 * @return
	 */
	@RequestMapping(value = "export/diagrams")
	@ResponseBody
	public List<String> exportDiagrams() throws IOException {
		List<String> files = new ArrayList<String>();
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

		for (ProcessDefinition processDefinition : list) {
			files.add(WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition));
		}

		return files;
	}
	
	/**
	 * 发布为系统应用
	 * 作用1：生成流程对应的角色，并和流程绑定
	 * 作用2：发布为系统的应用，供desktop以及其他的系统使用
	 * @param processDefineKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/publishToSystemApp")
	public Map<String,Object> publishToSystemApp(String processDefineKey){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			deployService.publishToSystemApp(processDefineKey);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	

}