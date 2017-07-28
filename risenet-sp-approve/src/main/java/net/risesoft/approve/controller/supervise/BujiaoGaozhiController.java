package net.risesoft.approve.controller.supervise;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.SpmWordFile;
import net.risesoft.approve.service.MaterialListService;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.OnlineReceiveService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.supervise.BujiaoGaozhiService;
import net.risesoft.approve.service.workflow.SpDocumentService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.FileToolUtil;
import net.risesoft.util.GUID;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.utilx.DateX;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/bjgz")
public class BujiaoGaozhiController {

	protected Logger log = LoggerFactory
			.getLogger(BujiaoGaozhiController.class);
	private static Integer formEngineType = RisesoftCommonUtil.formEngineType;
	@Resource(name = "onlineReceiveService")
	private OnlineReceiveService onlineReceiveService;
	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	@Autowired
	private BujiaoGaozhiService bujiaoGaozhiService;
	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;
	@Resource(name = "materialListService")
	private MaterialListService materialListService;
	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;
	@Resource(name = "spDocumentService")
	private SpDocumentService spDocumentService;
	
	@Autowired
	protected RuntimeService runtimeService;

	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "approve/supervise/bqbzList";
	}

	@RequestMapping(value = "/bjgzList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cksjlist(String name, String yxtywlsh,
			String declareperson) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> spmlist = bujiaoGaozhiService.findAll(
					name, yxtywlsh, declareperson);
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
	 * 补交告知开始处理
	 * 
	 * @param model
	 * @param value
	 * @param guid
	 * @return
	 */
	@RequestMapping(value = "/newInstanceAction")
	public String newInstanceAction(Model model, String processDefinitionKey,
			String guid) {
		String tenantId = ThreadLocalHolder.getTenantId();
		Person person = ThreadLocalHolder.getPerson();
		String activitiUser = person.getID() + SysVariables.COLON
				+ person.getParentID();
		// String url = "document/doc-deptChoice";用于一人多岗事进行部门选取的页面，暂时不考虑
		if (StringUtils.isNotBlank(processDefinitionKey)) {
			try {
				// 流程未启动，这里根据流程定义Key（例如luohubanwen）获取流程定义Id（例如luohubanwen:2:23105）
				String processDefinitionId = workflowProcessInstanceService
						.getProcessDefinitionId(processDefinitionKey);
				// 获取开始节点的目标节点Id，这里暂时还是只考虑目标节点只有一个的情况，如果有多个则需要根据配置进行选取，或者将其给用户，让用户选择
				List<String> startNodeIdList = workflowProcessDefinitionService
						.getNodeName(processDefinitionId,
								SysVariables.STARTEVENT);
				List<Map<String, String>> targetIdList = workflowProcessInstanceService
						.getCurrentTaskTargets(processDefinitionId,
								startNodeIdList.get(0));
				String taskDefKey = targetIdList.get(0).get(
						SysVariables.TASKDEFKEY);

				model = spDocumentService.newMenuControl(processDefinitionId,taskDefKey,"", model,SysVariables.bqbz, guid);
				model.addAttribute("SPinstanceId", guid);
				if (formEngineType == 1) {
					model = spDocumentService.genDocumentModel(
							SysVariables.ADD, GuidUtil.genGuid(), tenantId,
							processDefinitionKey, "", "", "", taskDefKey,
							activitiUser, model);
					return "document/documentEdit";
				} else if (formEngineType == 2) {
					model = spDocumentService.genDocumentModel4Eform(
							SysVariables.bqbz, GuidUtil.genGuid(), tenantId,
							processDefinitionKey, "", "", "", taskDefKey,
							activitiUser, model);
					return "document/documentEdit4Eform";
				} else if (formEngineType == 3) {
					model = spDocumentService.genDocumentModel4Eform(
							SysVariables.ADD, GuidUtil.genGuid(), tenantId,
							processDefinitionKey, "", "", "", taskDefKey,
							activitiUser, model);
					return "document/documentEdit4Excel";
				}
			} catch (Exception e) {
				log.error("===================窗口收件流程启动出错: 没有绑定流程或者事项绑定流程出错！===========================");
				e.printStackTrace();
			}
		}
		return "/500";
	}

	/**
	 * 一次性补交告知通知单
	 */
	@RiseLog(operateName = "补交告知通知单", operateType = "查看")
	@RequestMapping(value = "/adviceForm", method = RequestMethod.GET)
	public String getInform(String feedback,String instanceId, String method, Model model,
			String ytjids, String xbqids, String xbzids) {
		try {
			
			//向t_bujiaogaozhi表中插入数据
			//bujiaoGaozhiService.saveBuqibuzheng(instanceId,feedback,xbqids,xbzids,1);
			materialListService.updateReply(feedback, ytjids, xbqids, xbzids, instanceId);
			//挂起流程
			try {
				String processId = officeSpiDeclareinfoService.findByGuid(instanceId).getProcessInstanceId();
				if(!StringX.isBlank(processId)){
					runtimeService.suspendProcessInstanceById(processId);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//发送短信提醒
			Map<String,Object> smsMap = bujiaoGaozhiService.getSmsMessage(instanceId,xbqids,xbzids);
			if(smsMap!=null){
				RisesoftCommonUtil.getSendSmsManager().sms(StringX.getNullString(smsMap.get("mobile")), StringX.getNullString(smsMap.get("content")));
			}
			Person person = ThreadLocalHolder.getPerson();
			Map<String, Object> map = officeSpiDeclareinfoService.getAdviceForm(instanceId);
			
			map.put("ADVICETIME", DateX.getStandardDateText(new Date()));

			map.put("WORKNAME", person.getName());
			map.put("workTel",
					person.getOfficePhone() == null ? "无" : person
							.getOfficePhone());
			Map<String, Object> listsMap = bujiaoGaozhiService
					.getBuqibuzhengLists(ytjids, xbqids, xbzids);
			for (String key : listsMap.keySet()) {
				map.put(key, listsMap.get(key));
			}
			JSONObject json = JSONObject.fromObject(map);
			model.addAttribute("map", json);
			model.addAttribute("method", method);
			model.addAttribute("instanceId", instanceId);
			model.addAttribute("xbqids",xbqids);
			model.addAttribute("xbzids",xbzids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ("printAdvice".equals(method)) {// 一次性补交告知单
			return "approve/printAdviceForm";
		}
		return "approve/adviceOfficeForm";
	}

	
	/**
	 * 判断是否插入补齐补正受理数据
	 */
	@RiseLog(operateName = "判断是否插入补齐补正受理数据", operateType = "查看")
	@RequestMapping(value = "/isBqbzsl", method = RequestMethod.POST)
	@ResponseBody
	public String isBqbzsl(String instanceId) {
		String res = bujiaoGaozhiService.isBuqibuzhengShouli(instanceId);
		return res + "";
	}

	@RiseLog(operateName = "补正告知电子文件存档", operateType = "增加")
	@RequestMapping(value = "/saveDocument")
	@ResponseBody
	public void saveDocument(String instanceId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session,String istaohong,String printType)
			throws IOException {
		Person person = ThreadLocalHolder.getPerson();
		
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("currentDoc");
		
		if (!multipartFile.isEmpty()) {
			byte[] content = multipartFile.getBytes();
			/*String basePath="";
			if (RisesoftCommonUtil.print_banjieDan.equals(printType)) {//
				basePath = "banjie";
			} else if (RisesoftCommonUtil.print_buQiBuZhengDan
					.equals(printType)) {
				basePath = "buqibuzheng";
			} else {
				basePath = "shouli";
			}*/
			
			try {
				//文件保存路径 基础路径+实例id+类型+文件名称
				String filePath = RisesoftCommonUtil.printURL+instanceId+"\\"+printType+"\\word\\"+istaohong+"\\"+instanceId+RisesoftCommonUtil.print_word;
				File targetFile = new File(filePath);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				multipartFile.transferTo(targetFile);
				
				SpmWordFile wordFile = new SpmWordFile();
				wordFile.setContent(content);
				wordFile.setGuid(instanceId);
				wordFile.setType(istaohong);
				wordFile.setStep(printType);
				wordFile.setWritePerson(person.getID());
				wordFile.setWriteTime(new Date());
				wordFile.setFilepath(filePath);
				// 补正告知电子文件存档
				bujiaoGaozhiService.saveWordFile(wordFile);
				
				sb.append("true");
			} catch (Exception e) {
				sb.append("false");
				e.printStackTrace();
			}
		} else {
			sb.append("false");
		}
		out.print(new String(sb));
	}
	
	/**
	 * 一次性补交告知说明
	 */
	@RiseLog(operateName = "补交告知说明", operateType = "查看")
	@RequestMapping(value = "/adviceRemark", method = RequestMethod.GET)
	public String adviceRemark(String approveitemguid,String method,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		Map<String,Object> map = new HashMap<String, Object>();
		//需提交材料
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listtype = new ArrayList<Map<String,Object>>();
		if("instanceId".equals(method)){//这里参数为流程实例id
			OfficeSpiDeclareinfo osd = officeSpiDeclareinfoService.findByGuid(approveitemguid);
			approveitemguid = osd.getApproveitemguid();
		}
		try {
			map = bujiaoGaozhiService.getAdviceRemark(approveitemguid);
			list = bujiaoGaozhiService.getLists(approveitemguid);
			listtype = bujiaoGaozhiService.getListType(approveitemguid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("map",map);
		model.addAttribute("list",list);
		model.addAttribute("listtype",listtype);
		model.addAttribute("name", person.getName());
		model.addAttribute("tel", person.getOfficePhone());
		if ("printAdvice".equals(method)) {// 一次性补交告知单
			return "approve/printAdviceRemark";
		}
		return "approve/adviceRemark";
	}
	
	
}
