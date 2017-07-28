package net.risesoft.approve.controller.certificate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.certificate.CertificateBean;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.service.CodeMapService;
import net.risesoft.approve.service.WindowApproveService;
import net.risesoft.approve.service.certificate.CertificateService;
import net.risesoft.approve.service.declareannex.DeclareannexService;
import net.risesoft.approve.service.senate.SenateService;
import net.risesoft.approve.service.supervise.IGetNewSblshUtil;
import net.risesoft.approve.service.workflow.SpWorkFlowService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
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

@Controller
@RequestMapping(value = "/certificate")
public class CertificateController {
	
	protected Logger log=LoggerFactory.getLogger(CertificateController.class);
	
	@Autowired
	private CertificateService certificateService;

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
	
	@Resource(name="spWorkFlowService")
	private SpWorkFlowService spWorkFlowService;
	
	@Autowired
	private SenateService senateService;

	// 跳到窗口收件首页
	@RequestMapping(value = "/index")
	public String index() {
		return "/certificate/certificateIndex";
	}
	
	/**
	 * 证照领取或查看
	 */
	@RequestMapping(value = "/getCertificate", method = RequestMethod.GET)
	public String getCertificate(HttpServletRequest request){
		String readonly="";
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapd = new HashMap<String, Object>();
		String workflowinstance_guid = request.getParameter("workflowinstance_guid");
		String isrecieve = request.getParameter("isrecieve");
		map=certificateService.getDetailByWorkflowinstance_guid(workflowinstance_guid);
		mapd=certificateService.declareInfo(workflowinstance_guid);
		request.setAttribute("certificateMap", map);
		request.setAttribute("declareInfo", mapd);
		request.setAttribute("isrecieve", isrecieve);
		if("1".equals(isrecieve)){
			readonly="readonly='readonly'";
		}
		request.setAttribute("readonly", readonly);
		return "certificate/getCertificate";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> certificateList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		try {
			if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
				pageSize = "20";
				pageNo = "1";
			}
			Pager pager = new Pager();
			pager.setPageNo(Integer.parseInt(pageNo));
			pager.setPageSize(Integer.parseInt(pageSize));
			
			pager = certificateService.findAllList(request, pager);
			listmap = pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", listmap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String, Object> materialList(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String material_code = request.getParameter("material_code");
		String material_name = request.getParameter("material_name");
		
		try {
			
			Person person = ThreadLocalHolder.getPerson();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	//证照领取保存
	@RequestMapping(value = "/certificateSave", method = RequestMethod.POST)
	@ResponseBody
	public String certificateSave(HttpServletRequest request) {
		boolean result=certificateService.doEdit(request);
		JSONObject json= new JSONObject();
		if(result){
			json.put("msg", "证件领取信息更新成功。");
		}else{
			json.put("msg", "内部出错。。。");
		}
		return json.toString();
	}
	
	// 发送短信
	@RequestMapping(value = "/sendSms")
	public String sendSms(HttpServletRequest request) {
		String processInstanceId=request.getParameter("processInstanceId");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("processInstanceId", processInstanceId);
		senateService.sendSenateSms(paramMap);
		return null;
	}
	

}
