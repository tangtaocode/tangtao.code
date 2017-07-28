package net.risesoft.approve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;
import net.risesoft.approve.service.CodeMapService;
import net.risesoft.approve.service.TongyipintaiServices;
import net.risesoft.approve.service.WindowApproveService;
import net.risesoft.approve.service.declareannex.DeclareannexService;
import net.risesoft.approve.service.supervise.IGetNewSblshUtil;
import net.risesoft.approve.service.workflow.SpDocumentService;
import net.risesoft.approve.service.workflow.SpWorkFlowService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.util.SysVariables;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/windowTodo")
public class WindowNewController {

	protected Logger log = LoggerFactory.getLogger(WindowController.class);
	
	@Resource
	private TongyipintaiServices tongyipintaiServices;
	private static Integer formEngineType = RisesoftCommonUtil.formEngineType;

	@Autowired
	private WindowApproveService windowapproveservice;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Autowired
	private DeclareannexService declareannexService;
	@Resource(name = "getNewSblshUtil")
	private IGetNewSblshUtil getNewSblshUtil;

	@Resource(name = "codeMapUtil")
	private CodeMapService codeMapUtil;

	@Resource(name = "spWorkFlowService")
	private SpWorkFlowService spWorkFlowService;

	@Resource(name = "spDocumentService")
	private SpDocumentService spDocumentService;

	// 跳到窗口收件首页
	@RiseLog(operateName = "统一窗口待办首页",operateType = "查看")
	@RequestMapping(value = "/index")
	public String index() {
		return "/approve/windowToDoList";
	}

	@RiseLog(operateName = "获取窗口收件列表",operateType = "查看")
	@RequestMapping(value = "/windowToDoList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cksjlist(String approveItemName,String status) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> spmlist = tongyipintaiServices.findAll(approveItemName,status);
			int pagetotal = spmlist.size();
			map.put("pager.pageNo", 1);
			map.put("pageSize", 20);
			map.put("pager.totalRows", pagetotal);
			map.put("rows", spmlist);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 窗口收件开始受理
	 * 
	 * @param model
	 * @param value
	 * @param guid
	 * @return
	 */
	@RiseLog(operateName = "窗口人员查看信息,办件分发",operateType = "增加")
	@RequestMapping(value = "/newInstanceAction")
	public String newInstanceAction(Model model, String sblsh,String name) {
		String tenantId = ThreadLocalHolder.getTenantId();
		Person person = ThreadLocalHolder.getPerson();
		String activitiUser = person.getID() + SysVariables.COLON + person.getParentID();
		// 查询事项基本信息
		SpmApproveitem approveItem = tongyipintaiServices.findApproveItemByName(name);
		
		//查询基本信息
		ShenBanProcess shenban = tongyipintaiServices.findShenbanBySblsh(sblsh);
		// 保存事项基本信息到office_spi_declareinfo表
		String instanceGuid = new GUID().toString();
		String processDefinitionKey = approveItem.getWorkflowguid();
		String instanceId = instanceGuid;
		// String url = "document/doc-deptChoice";用于一人多岗事进行部门选取的页面，暂时不考虑
		if (StringUtils.isNotBlank(processDefinitionKey)) {
			try {
				// 流程未启动，这里根据流程定义Key（例如luohubanwen）获取流程定义Id（例如luohubanwen:2:23105）
				String processDefinitionId = workflowProcessInstanceService.getProcessDefinitionId(processDefinitionKey);
				// 窗口收件先保存数据到审批主表
				OfficeSpiDeclareinfo office = new OfficeSpiDeclareinfo();
				office.setGuid(instanceGuid);
				office.setApproveitemguid(approveItem.getApproveitemguid());
				office.setApproveItemName(approveItem.getApproveitemname());
				office.setBureauguid(approveItem.getAdminbureauguid());
				office.setZhengjiandaima(shenban.getSqrzjhm());
				office.setDeclarerPerson(shenban.getSqrmc());
				office.setDeclarerlxr(shenban.getLxrxm());
				office.setDataFromtype(shenban.getTjfs());
				office.setXiangmumingcheng(shenban.getSbxmmc());
				office.setAddress("深圳市水务局");
				office.setDeclareType(shenban.getSqrlx());
				office.setDeclarerlxrIdtype(shenban.getLxrzjlx());
				office.setDeclarerlxrid(shenban.getLxrsfzjhm());
				office.setDeclarerMobile(shenban.getLxrsj());
				//office.setDeclarerTel(shenban.getsq);
				// 获取业务编号
				office.setDeclaresn(sblsh);
				// 修改统一流水号为调用市接口产生的流水号
				//String newSblsh = getNewSblshUtil.getSblshByApproveItemGuid(guid);
				office.setSblsh(sblsh);
				windowapproveservice.save(office);
				// 启动工作流
				// model=spWorkFlowService.startProcess(processDefinitionKey,
				// instanceId, model);
				model.addAttribute("SPinstanceId", instanceId);

				// 获取开始节点的目标节点Id，这里暂时还是只考虑目标节点只有一个的情况，如果有多个则需要根据配置进行选取，或者将其给用户，让用户选择
				List<String> startNodeIdList = workflowProcessDefinitionService.getNodeName(processDefinitionId, SysVariables.STARTEVENT);
				List<Map<String, String>> targetIdList = workflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId, startNodeIdList.get(0));
				String taskDefKey = targetIdList.get(0).get(SysVariables.TASKDEFKEY);
				// model = spDocumentServise.genDocumentModel(SysVariables.ADD,
				// GuidUtil.genGuid(),tenantId, processDefinitionKey, "", "",
				// "", taskDefKey, activitiUser, model);
				model = spDocumentService.newMenuControl(processDefinitionId, taskDefKey, "", model, SysVariables.ADD,instanceId);
				if (formEngineType == 1) {
					model = spDocumentService.genDocumentModel(SysVariables.ADD, GuidUtil.genGuid(), tenantId, processDefinitionKey, "", "", "", taskDefKey, activitiUser, model);
					return "document/documentEdit";
				} else if (formEngineType == 2) {
					model = spDocumentService.genDocumentModel4Eform(SysVariables.ADD, GuidUtil.genGuid(), tenantId, processDefinitionKey, "", "", "", taskDefKey, activitiUser, model);
					return "document/documentEdit4Eform";
				} else if (formEngineType == 3) {
					model = spDocumentService.genDocumentModel4Eform(SysVariables.ADD, GuidUtil.genGuid(), tenantId, processDefinitionKey, "", "", "", taskDefKey, activitiUser, model);
					return "document/documentEdit4Excel";
				}
			} catch (Exception e) {
				log.error("===================窗口收件流程启动出错:" + approveItem.getApproveitemname() + "没有绑定流程或者事项绑定流程出错！===========================");
				e.printStackTrace();
			}
		}
		return "/500";
	}
}
