package net.risesoft.approve.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.gdbs.ShenBanCailiaoProcess;
import net.risesoft.approve.repository.jpa.gdbs.ShenbancailiaoRepository;
import net.risesoft.approve.service.MaterialListService;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.OnlineReceiveService;
import net.risesoft.approve.service.supervise.BujiaoGaozhiService;
import net.risesoft.approve.service.supervise.FTSuperviseService;
import net.risesoft.approve.service.workflow.SpDocumentService;
import net.risesoft.approve.service.workflow.SpWorkFlowService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.service.SpActivitiOptService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/onlineApprove")
public class OnlineApproveController {

	protected Logger log = LoggerFactory.getLogger(OnlineApproveController.class);
	
	private static Integer formEngineType = RisesoftCommonUtil.formEngineType;

	@Resource(name = "onlineReceiveService")
	private OnlineReceiveService onlineReceiveService;

	@Resource(name = "materialListService")
	private MaterialListService materialListService;

	@Autowired
	private SpActivitiOptService activitiOptService;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Autowired
	private FTSuperviseService ftSuperviseService;
	@Autowired
	private BujiaoGaozhiService bujiaoGaozhiService;
	@Autowired
	private ShenbancailiaoRepository shenbancailiaoRepository;

	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Resource(name="spWorkFlowService")
	private SpWorkFlowService spWorkFlowService;
	
	@Resource(name="spDocumentService")
	private SpDocumentService spDocumentService;
	
	@RiseLog(operateName = "打开网上收件",operateType = "查看")
	@RequestMapping(value = "/onlineIndex", method = RequestMethod.GET)
	public String approveShow() {
		return "approve/onlineIndex";
	}

	@RequestMapping(value = "/approveclcs", method = RequestMethod.GET)
	public String approveclcsShow() {
		return "approve/approve-clcs";
	}

	/**
	 * 跳转到网上收件材料清单
	 */
	@RiseLog(operateName = "打开网上收件材料清单",operateType = "查看")
	@RequestMapping(value = "/approveMaterial", method = RequestMethod.GET)
	public ModelAndView approveMaterialShow(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("approve/onlineMaterial");
		String yxtywlsh = request.getParameter("yxtywlsh");
		String instanceId = request.getParameter("instanceId");
		OfficeSpiDeclareinfo sbInstance = null;
		try{
		//判断是否进行网上预受理
			sbInstance = onlineReceiveService.isYushouli(yxtywlsh);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		int res = 0;//默认未进行预受理
		if(sbInstance!=null){
			if("1".equals(sbInstance.getReplystatus())||"预受理".equals(sbInstance.getHandleStatus())){//已回复或者是已进行预受理
				res = 1;
				mav.addObject("feedback",sbInstance.getFeedback());
			}
		}
		//判断是否已经发起补正告知
		int isBqbz = bujiaoGaozhiService.isBuqibuzheng(instanceId);
		mav.addObject("isBqbz", isBqbz);
		mav.addObject("userid", request.getParameter("userid"));
		mav.addObject("instanceId", request.getParameter("instanceId"));
		mav.addObject("workflowguid", request.getParameter("workflowguid"));
		mav.addObject("approveitemguid", request.getParameter("approveitemguid"));
		mav.addObject("result", res);
		return mav;
	}

	/**
	 * 网上收件材料清单办事指南
	 */
	@RiseLog(operateName = "打开网上收件材料清单办事指南",operateType = "查看")
	@RequestMapping(value = "/approveGuide", method = RequestMethod.GET)
	public ModelAndView approveGuide(HttpServletRequest request) {
		// List<Map<String,Object>> listmap = new
		// ArrayList<Map<String,Object>>();
		Map<String, Object> listmap = new HashMap<>();
		ModelAndView mav = new ModelAndView("approve/approveGuide");
		// approveItemGuid
		String instanceId = request.getParameter("instanceId");
		Map<String, Object> listmap2 = new HashMap<>();
		listmap2 = materialListService.findByapproveItemGuid(instanceId);
		String approveItemGuid = listmap2.get("APPROVEGUID").toString();
		mav.addObject("approveItemGuid", approveItemGuid);
		listmap = materialListService.findByBszn(approveItemGuid);
		String declareunittype = listmap.get("DECLAREUNITTYPE").toString();
		if (declareunittype.charAt(0) == '1') {
			declareunittype = "个人";
		}
		if (declareunittype.charAt(0) == '2') {
			declareunittype = "企业";
		}
		if (declareunittype.charAt(0) == '3') {
			declareunittype = "事业";
		}
		if (declareunittype.charAt(0) == '4') {
			declareunittype = "机关";
		}
		if (declareunittype.charAt(0) == '5') {
			declareunittype = "社团";
		}
		mav.addObject("DECLAREUNITTYPE", declareunittype);
		mav.addObject("APPROVEITEMNAME", listmap.get("ApproveItemName").toString());
		mav.addObject("DECLARECONDITION", listmap.get("DECLARECONDITION").toString());
		mav.addObject("APPROVEITEMTYPE", listmap.get("APPROVEITEMTYPE").toString());
		mav.addObject("BUREAUTYPE", listmap.get("BUREAUTYPE").toString());
		mav.addObject("CHARGEINFO", listmap.get("ChargeInfo"));
		mav.addObject("TIMELIMIT", listmap.get("TIMELIMIT").toString());
		mav.addObject("DECLAREDESC", listmap.get("DECLAREDESC").toString());
		mav.addObject("APPROVEITEMDESC", listmap.get("approveitemDesc"));
		mav.addObject("SUPERVISEUNIT", listmap.get("SuperviseUnit"));
		mav.addObject("TIMEIMITDESC", listmap.get("TimeLimitDesc"));
		// 办事流程
		Map<String, Object> listmap3 = new HashMap<>();
		listmap3 = materialListService.findByapproveServiceProcess(approveItemGuid);
		mav.addObject("THEFILENAME", listmap3.get("thefilename"));
		// 申请材料
		Map<String, Object> listmap4 = new HashMap<>();
		listmap4 = materialListService.findByapproveMaterial(approveItemGuid);
		mav.addObject("DECLAREANNEXNAME", listmap4.get("DECLAREANNEXNAME"));
		// 申请材料描述
		Map<String, Object> listmap5 = new HashMap<>();
		listmap5 = materialListService.findByapproveMaterialdescription(approveItemGuid);
		mav.addObject("DECLAREANNEXDESC", listmap5.get("DECLAREANNEXDESC"));
		// 申请材料描述
		Map<String, Object> listmap6 = new HashMap<>();
		listmap6 = materialListService.findByapproveLaws(approveItemGuid);
		mav.addObject("APPROVEITEMPOLICYNAME", listmap6.get("APPROVEITEMPOLICYNAME"));

		return mav;
	}
	
	
	
	/**
	 * 网上收件材料清单预受理
	 * @param request
	 * @param status
	 * @param instanceId
	 * @param processDefinitionKey
	 * @param model
	 * @return
	 */
	@RiseLog(operateName = "网上收件材料清单预受理",operateType = "查看")
	@RequestMapping(value = "/approveAdvanceAction", method = RequestMethod.POST)
	public String approveAdvanceAction( HttpServletRequest request,String status, String instanceId, String processDefinitionKey, Model model) {
		String ytjs = request.getParameter("ytjs");
		String xbqs = request.getParameter("xbqs");
		String xbzs = request.getParameter("xbzs");
		String isbqbzsl = request.getParameter("isbqbzsl");
		String tenantId=ThreadLocalHolder.getTenantId();
		Person person=ThreadLocalHolder.getPerson();
		String activitiUser=person.getID()+SysVariables.COLON+person.getParentID();
		// String url = "document/doc-deptChoice";用于一人多岗事进行部门选取的页面，暂时不考虑
		if (StringUtils.isNotBlank(processDefinitionKey)) {
			try {
				// 向审批主表中插入数据
				onlineReceiveService.insertSpiDeclareInfo(instanceId, ytjs, xbqs, xbzs);
				//model=spWorkFlowService.startProcess(processDefinitionId, instanceId, model);
				// 向网上预受理表中插入数据
				ftSuperviseService.saveWangshangyushouli(instanceId);
				//如果发起了补齐补正告知，插入补齐补正受理数据
				if("0".equals(isbqbzsl)){
					bujiaoGaozhiService.saveBuqibuzhengshouli(instanceId);
				}
				// 审批业务数据和工作流流程相关联
				//officeSpiDeclareinfoService.updateOfficeSpiDeclareinfo(instanceId,(model.asMap().get("processInstanceId")).toString());
				
				//修改网上申报表的状态
				//onlineReceiveService.updateStatus(status, instanceId);
				// 获取开始节点的目标节点Id，这里暂时还是只考虑目标节点只有一个的情况，如果有多个则需要根据配置进行选取，或者将其给用户，让用户选择
				// 判断用户是否有当前流程的启动角色，如果有则可以启动流程，否则不可以启动流程
				String processDefinitionId = workflowProcessDefinitionService.getLatestProcessDefinitionId(processDefinitionKey);
				List<String> startNodeIdList = workflowProcessDefinitionService.getNodeName(processDefinitionId, SysVariables.STARTEVENT);
				List<Map<String, String>> targetIdList = workflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId, startNodeIdList.get(0));
				String taskDefKey = targetIdList.get(0).get(SysVariables.TASKDEFKEY);
				model.addAttribute("SPinstanceId", instanceId);
				model.addAttribute("status", status);
				
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
				log.error("===================网上收件审批启动流程出错,审批WORKFLOWINSTANCE_GUID:" + instanceId + "===============");
				e.printStackTrace();
			}
		} else {
			model.addAttribute("msg", "流程定义不能为空");
		}
		return "document/documentEdit";
	}

	@RequestMapping(value = "/gotoProcess")
	public String gotoProcess(Model model,String status,String instanceId,String processDefinitionKey){
		String tenantId=ThreadLocalHolder.getTenantId();
		Person person=ThreadLocalHolder.getPerson();
		String activitiUser=person.getID()+SysVariables.COLON+person.getParentID();
		if (StringUtils.isNotBlank(processDefinitionKey)) {
			try {
				// 获取开始节点的目标节点Id，这里暂时还是只考虑目标节点只有一个的情况，如果有多个则需要根据配置进行选取，或者将其给用户，让用户选择
				// 判断用户是否有当前流程的启动角色，如果有则可以启动流程，否则不可以启动流程
				String processDefinitionId = workflowProcessDefinitionService.getLatestProcessDefinitionId(processDefinitionKey);
				List<String> startNodeIdList = workflowProcessDefinitionService.getNodeName(processDefinitionId, SysVariables.STARTEVENT);
				List<Map<String, String>> targetIdList = workflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId, startNodeIdList.get(0));
				String taskDefKey = targetIdList.get(0).get(SysVariables.TASKDEFKEY);
				model.addAttribute("SPinstanceId", instanceId);
				model.addAttribute("status", status);
				
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
				log.error("===================网上收件审批启动流程出错,审批WORKFLOWINSTANCE_GUID:" + instanceId + "===============");
				e.printStackTrace();
			}
		 } else {
			model.addAttribute("msg", "流程定义不能为空");
		}
		return "document/documentEdit";
	}
	/**
	 * 网上收件列表
	 */
	@RiseLog(operateName = "网上收件列表",operateType = "查看")
	@RequestMapping(value = "/approveList")
	@ResponseBody
	public Map<String, Object> approveList(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		String ishf = request.getParameter("ishf");
		String status = request.getParameter("status");
		String approveName = request.getParameter("approveName");
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		try {
			if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
				pageSize = "20";
				pageNo = "1";
			}
			Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
			Person person = ThreadLocalHolder.getPerson();
			pager = onlineReceiveService.findByUserID(person, ishf, status,
					approveName, pager);
			List<Map<String, Object>> spmList = pager.getPageList();
			
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}

	/**
	 * 网上收件材料清单列表
	 * 
	 * @param instanceId
	 * @param approveguid
	 * @return
	 * 
	 */
	@RiseLog(operateName = "网上收件材料清单列表",operateType = "查看")
	@RequestMapping(value = "/approveMaterialList")
	@ResponseBody
	public Map<String, Object> approveMaterialList(HttpServletRequest request,Model model) {
		// 待办id
		String instanceId = request.getParameter("instanceId");

		//事项名称
		String formname = request.getParameter("formname");

		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> reList = materialListService.findMaterialByOffice(instanceId);
		String declareannexguid = reList.get(0).get("DECLAREANNEXGUID").toString();
		if(declareannexguid == "" || declareannexguid == null){
			return null;
		}
//		else{
//			List<Map<String, Object>> getPanDuanList = onlineReceiveService.getPanDuanList(declareannexguid);
//			String idid = getPanDuanList.get(0).get("DECLAREANNEXGUID").toString();
//			for(int i=0;i<reList.size();i++){
//				reList.get(i).put("idid", idid);
//			}
//
//		}
		map.put("rows", reList);
		return map;
	}
	
	/**
	 * 查看附件明细
	 */
	@RiseLog(operateName = "获取附件列表",operateType = "查看")
	@RequestMapping(value = "/openFileList")
	@ResponseBody
	public Map<String,Object> openFile(String instanceId,String declareGuid){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> fileList = materialListService.findAttachMentByInstanceId(instanceId,declareGuid);
		map.put("rows", fileList);
		return map;
	} 

	/**
	 * 跳转到附件明细页面
	 */
	@RiseLog(operateName = "查看附件",operateType = "查看")
	@RequestMapping(value = "/openFile")
	public String openFileIndex(String instanceId,String declareGuid,Model model){
		model.addAttribute("instanceId", instanceId);
		model.addAttribute("DECLAREANNEXGUID", declareGuid);
		return "approve/attachmentList";
	} 
	/**
	 * 回复完后跳转到网上收件材料清单
	 */
	@RiseLog(operateName = "打开网上收件材料清单",operateType = "查看")
	@RequestMapping(value = "/replyAndapproveMaterial")
	public ModelAndView replyAndapproveMaterialShow(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("approve/onlineMaterial");
		/*String instanceId = request.getParameter("instanceId");
		mav.addObject("instanceId", instanceId);

		String feedback = request.getParameter("feedback");
		String ytjids = request.getParameter("ytjids");
		String xbqids = request.getParameter("xbqids");
		String xbzids = request.getParameter("xbzids");
		String message = "";
		try {
			//向t_bujiaogaozhi表中插入数据
			bujiaoGaozhiService.saveBuqibuzheng(instanceId,feedback,xbqids,xbzids,1);
			int reply = materialListService.updateReply(feedback, ytjids, xbqids, xbzids, instanceId);
			if (reply >= 1) {
				message = "回复成功！";
			} else {
				message = "回复失败，请重新填写回复内容！";
			}
		} catch (Exception e) {
			message = "保存失败！";
			e.printStackTrace();
		}
		mav.addObject("feedback", feedback);
		mav.addObject("message", message);*/
		return mav;
	}
	

	/**
	 * 返回当前申请人
	 * 
	 * 
	 */
	@RiseLog(operateName = "返回当前申请人",operateType = "查看")
	@RequestMapping(value = "/gxcailiao")
	public ModelAndView gxcailiao(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("approve/gxcailiao");
		String userid = request.getParameter("userid");
		List<Map<String, Object>> declarerpersonList = onlineReceiveService.getDeclarerperson(userid);
		String cardId = declarerpersonList.get(0).get("CARDID").toString();
		mav.addObject("cardId", cardId);
		mav.addObject("declareannexguid", request.getParameter("declareannexguid"));
		return mav;
	}
	
	/**
	 * 返回关联证照列表
	 * 
	 * 
	 */
	@RiseLog(operateName = "打开关联证照列表",operateType = "查看")
	@RequestMapping(value = "/gxcailiaoList")
	@ResponseBody
	public Map<String, Object> gxcailiaoList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		String cardId = request.getParameter("cardId");
		String declareannexguid = request.getParameter("declareannexguid");
		List<Map<String, Object>> getGXCaiLiaoList = onlineReceiveService.getGXCaiLiaoList(cardId);

		List<Map<String, Object>> getPanDuanList = onlineReceiveService.getPanDuanList(declareannexguid);
		map.put("rows", getGXCaiLiaoList);
		//JSONObject jsonObject = JSONObject.fromObject(map);
		//return jsonObject.toString();
		return map;
	}
	
	/**
	 * 返回附件列表
	 * 
	 * 
	 */
	@RiseLog(operateName = "返回附件列表",operateType = "查看")
	@RequestMapping(value = "/fujianList")
	@ResponseBody
	public Map<String, Object> fujianList(HttpServletRequest request){
		String instanceguid = request.getParameter("instanceguid");
		Map<String, Object> map = new HashMap<>();

		List<Map<String, Object>> getFujianList = onlineReceiveService.getFujianList(instanceguid);
		map.put("rows", getFujianList);
		return map;
	}
	
	/**
	 * 删除共享证照
	 * 
	 * 
	 */
	@RiseLog(operateName = "删除共享证照",operateType = "删除")
	@RequestMapping(value = "/deleteGXZZ")
	@ResponseBody
	public String deleteGXZZ(HttpServletRequest request){
		String instanceguid = request.getParameter("instanceguid");
		int a=0;
		a = onlineReceiveService.deleteGXZZ(instanceguid);
		String msg = "";
		if(a>0){
			msg="删除成功！";
		}else{
			msg="删除失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("msg", msg);
		return json.toString();

	}
	
	/**
	 * 删除附件
	 * 
	 * 
	 */
	@RiseLog(operateName = "删除附件",operateType = "删除")
	@RequestMapping(value = "/onDeleteFJ")
	@ResponseBody
	public String onDeleteFJ(HttpServletRequest request){
		String fileguid = request.getParameter("fileguid");
		int a=0;
		a = onlineReceiveService.onDeleteFJ(fileguid);
		String msg = "";
		if(a>0){
			msg="删除成功！";
		}else{
			msg="删除失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("msg", msg);
		return json.toString();

	}
	
	/**
	 * 修改更新关联证照
	 * 
	 * 
	 */
	@RiseLog(operateName = "修改更新关联证照",operateType = "修改")
	@RequestMapping(value = "/updateGXZZ")
	@ResponseBody
	public String updateGXZZ(HttpServletRequest request){
		String instanceguid = request.getParameter("instanceguid");
		String docname = request.getParameter("docname");
		String producedate = request.getParameter("producedate");
		String zzyxq = request.getParameter("zzyxq");
		

		int a = onlineReceiveService.updateGXZZ(instanceguid,docname,producedate,zzyxq);
		String msg = "";
		if(a>0){
			msg="修改成功！";
		}else{
			msg="修改失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("msg", msg);
		return json.toString();

	}
	
	/**
	 * 下载附件
	 * @param id
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RiseLog(operateName = "下载附件",operateType = "使用")
	@RequestMapping(value = "/download")
	public Map<String, Object> download(String id, HttpServletResponse response,HttpServletRequest request,Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try{
			onlineReceiveService.download(id, response, request);
		}catch(Exception e){
			map.put("success", "false");
			map.put("msg", "未找到文件，下载失败！");
		}
		return null;
	}
	@RiseLog(operateName = "查找申办材料",operateType = "修改")
	@RequestMapping(value = "/findclbyseqs")
	@ResponseBody
	public List<ShenBanCailiaoProcess> findClList(String sblsh,String sbclseqs){
		List<ShenBanCailiaoProcess> result=new ArrayList<ShenBanCailiaoProcess>();
		try {
			result=shenbancailiaoRepository.findByClids(sbclseqs, sblsh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
