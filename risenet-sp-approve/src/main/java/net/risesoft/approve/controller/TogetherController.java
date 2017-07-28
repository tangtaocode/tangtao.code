/**
 * @Project Name:risenet-sp-approve
 * @File Name: TogetherController.java
 * @Package Name: net.risesoft.approve.controller
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年4月27日 下午6:00:10
 */
package net.risesoft.approve.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.TBdexDoctype;
import net.risesoft.approve.entity.jpa.together.XzqlXzspListspoint;
import net.risesoft.approve.service.CodeMapService;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.OnlineReceiveService;
import net.risesoft.approve.service.ScanningService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.TogetherWindowService;
import net.risesoft.approve.service.WindowApproveService;
import net.risesoft.approve.service.WindowManagerService;
import net.risesoft.approve.service.supervise.IGetNewSblshUtil;
import net.risesoft.approve.service.workflow.SpDocumentService;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.service.FormDefService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: TogetherController.java
 * @Description: 综合窗口Controller
 *
 * @author chenbingni
 * @date 2016年4月27日 下午6:00:10
 * @version
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/togetherWindow")
public class TogetherController {

	protected Logger log = LoggerFactory.getLogger(WindowController.class);

	@Autowired
	private FormDefService formDefService;
	
	@Autowired
	public ScanningService scanningService;

	@Resource(name = "windowManagerService")
	private WindowManagerService windowManagerService;

	@Resource(name = "spmApproveItemService")
	private SpmApproveItemService spmApproveItemService;

	@Resource(name = "togetherWindowService")
	private TogetherWindowService togetherWindowService;

	@Resource(name = "codeMapUtil")
	private CodeMapService codeMapUtil;

	@Resource(name = "onlineReceiveService")
	private OnlineReceiveService onlineReceiveService;

	@Resource(name = "getNewSblshUtil")
	private IGetNewSblshUtil getNewSblshUtil;

	@Autowired
	private WindowApproveService windowapproveservice;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Resource(name = "spDocumentService")
	private SpDocumentService spDocumentService;

	@Autowired
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;

	@Value("${risebpm7.shouFaYuan}")
	private String shouFaYuan;

	@RequestMapping(value = "/receive", method = RequestMethod.GET)
	public String showReceive(Model model) {
		return "together/receiveIndex";
	}

	@RequestMapping(value = "/shortcode", method = RequestMethod.GET)
	public String showShortcode() {
		return "together/shortcodeIndex";
	}

	/**
	 * 
	 * @MethodName: searchReceiveList
	 * @Description: 综合窗口收件事项列表
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return Map<String,Object> 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月9日 下午5:02:37
	 */
	@RiseLog(operateName = "获取综合窗口收件事项列表", operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/receiveList")
	public Map<String, Object> searchReceiveList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		String approvename = request.getParameter("approveItemName");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "20";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		// 获取人员信息
		String personId = ThreadLocalHolder.getPerson().getID();
		// 获取该人员绑定的窗口
		// List<String> list = windowManagerService
		// .findWindowGuidsByEmployeeid(personId);
		// String windowGuids = StringUtils.join(list.toArray());
		if (!StringX.isBlank(personId)) {
			// 等事项目录管理系统集成到系统中来以后再将xzql_xzsp_window表的数据循环查出来，现在默认取第一个
			// pager = spmApproveItemService.findByWindowGuid(windowGuids,pager,
			// approvename);
			pager = spmApproveItemService.findByWindowGuid(personId, pager,
					approvename);
		}
		List<Map<String, Object>> manageMap = pager.getPageList();
		map.put("pager.pageNo", pager.getPageNo());
		map.put("pageSize", pager.getPageSize());
		map.put("pager.totalRows", pager.getTotalRows());
		map.put("rows", manageMap);

		return map;
	}

	@RiseLog(operateName = "获取事项列表", operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/getListByWordKey")
	public JSONObject getListByWordKey(HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		String inp = req.getParameter("q");
		List<JSONObject> list = togetherWindowService.findItemSuggestion(inp
				.toLowerCase());
		map.put("list", list);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	/**
	 * 
	 * @MethodName: searchShortcodeList
	 * @Description: 事项简码列表
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return Map<String,Object> 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月9日 下午5:02:12
	 */
	@RiseLog(operateName = "获取事项简码列表", operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/shortcodeList")
	public Map<String, Object> searchShortcodeList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		Pager pager = new Pager(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		pager = togetherWindowService.findApproveShortList(request, pager);
		List<Map<String, Object>> manageMap = pager.getPageList();
		map.put("pager.pageNo", pager.getPageNo());
		map.put("pageSize", pager.getPageSize());
		map.put("pager.totalRows", pager.getTotalRows());
		map.put("rows", manageMap);

		return map;
	}

	/**
	 * 
	 * @MethodName: editShort
	 * @Description: 编辑事项简码页面初始化
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return String 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月9日 下午5:04:18
	 */
	@RiseLog(operateName = "编辑事项简码页面初始化", operateType = "查看")
	@RequestMapping(value = "/editShort", method = RequestMethod.GET)
	public String editShort(Model model, HttpServletRequest request) {
		model.addAttribute("itemid", request.getParameter("ITEMID"));
		model.addAttribute("bureauname", request.getParameter("BUREAUNAME"));
		model.addAttribute("approveitemname",
				request.getParameter("APPROVEITEMNAME"));
		if (request.getParameter("METHOD").equals("add")) {
			model.addAttribute("shortcode", "");
		} else {
			model.addAttribute("shortcode", request.getParameter("SHORTCODE"));
		}

		model.addAttribute("method", request.getParameter("METHOD"));
		return "together/editShort";
	}

	/**
	 * 
	 * @MethodName: saveShort
	 * @Description: 保存事项短编码
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return Map<String,Object> 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月16日 下午4:38:50
	 */
	@RiseLog(operateName = "保存事项简码", operateType = "增加")
	@ResponseBody
	@RequestMapping(value = "/saveShort", method = RequestMethod.POST)
	public Map<String, Object> saveShort(String itemid, String shortcode,
			String method) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		result = togetherWindowService.saveShortCode(itemid, shortcode, method);
		if (result > 0) {
			map.put("msg", "保存成功！");
		} else {
			map.put("msg", "保存失败！");
		}
		return map;
	}

	/**
	 * 
	 * @MethodName: handle
	 * @Description: 使用其他系统的事项在审批平台新增收件之前输入证件号码
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return String 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月16日 下午4:38:07
	 */
	@RiseLog(operateName = "填写综合窗口收件处理信息", operateType = "增加")
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public String handle(Model model, String guid) {
		model.addAttribute("guid", guid);
		return "together/orgCode";
	}

	/**
	 * 
	 * @MethodName: newReceive
	 * @Description: 使用其他系统的事项在审批平台新增收件
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param：String guid 事项guid,String value 证件号,String method 方法（新增/修改）,String
	 *               instanceGuid（实例guid）, String username刷身份证带过来的姓名
	 * @return String 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月16日 下午4:35:46
	 */
	@RiseLog(operateName = "打开收件页面", operateType = "查看")
	@RequestMapping(value = "/newReceive")
	public String newReceive(Model model, String guid, String value,
			String method, String instanceGuid) {
		if (StringX.isBlank(instanceGuid)) {
			instanceGuid = new GUID().toString();
			model.addAttribute("status", "0");
		}
		model.addAttribute("instanceGuid", instanceGuid);
		model.addAttribute("code", value);
		model.addAttribute("method", method);

		String tenantId = ThreadLocalHolder.getTenantId();
		Person person = ThreadLocalHolder.getPerson();
		String activitiUser = person.getID() + SysVariables.COLON
				+ person.getParentID();
		model.addAttribute("activitiUser", activitiUser);

		// 查询事项基本信息
		model.addAttribute("guid", guid);
		
		SpmApproveitem approveItem = windowapproveservice
				.findApproveItemByGuid(guid);
		model.addAttribute("approvePlace", approveItem.getApproveplace());
		String processDefinitionKey = approveItem.getWorkflowguid();
		if (StringUtils.isNotBlank(processDefinitionKey)) {
			try {
				// 保存数据到审批主表
				if (StringX.isBlank(instanceGuid)) {
					OfficeSpiDeclareinfo office = new OfficeSpiDeclareinfo();
					office.setGuid(instanceGuid);
	
					office.setApproveitemguid(guid);
	
					office.setApproveItemName(approveItem.getApproveitemname());
	
					office.setBureauguid(approveItem.getAdminbureauguid());
	
					office.setZhengjiandaima(value);
	
					// office.setDeclarerPerson(username);
	
					// office.setDeclarerlxr(username);
	
					// 获取业务编号
	
					office.setDeclaresn(codeMapUtil
							.getDeclaresnByBureauGuid(approveItem
									.getAdminbureauguid()));
					// 修改统一流水号为调用市接口产生的流水号
	
					windowapproveservice.save(office);
				}
				model.addAttribute("processSerialNumber", GuidUtil.genGuid());

				model.addAttribute("processDefinitionKey", processDefinitionKey);

				model.addAttribute("tenantId", tenantId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "together/newReceive";
	}

	/**
	 * 跳转到用户选取界面
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	@RiseLog(operateName = "打开用户选取界面", operateType = "查看")
	@RequestMapping(value = "/departmentChoise", method = RequestMethod.GET)
	public String departmentChoise(@RequestParam String instanceGuid,
			@RequestParam String guid, @RequestParam String documentTitle,
			Model model) {
		model.addAttribute("instanceGuid", instanceGuid);
		model.addAttribute("guid", guid);
		model.addAttribute("documentTitle", documentTitle);// 用于短信提醒
		model.addAttribute("userName", ThreadLocalHolder.getPerson().getName());// 用于短信提醒
		return "together/doc-DepartmentChoise";
	}

	/**
	 * 获取发送人
	 * 
	 * @param userChoice
	 * @return
	 */
	@RiseLog(operateName = "获取发送人", operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/getActivitiUser", method = RequestMethod.POST)
	public Map<String, Object>

	getActivitiUser(@RequestParam String userChoice) {
		Map<String, Object> map = new HashMap<>();
		List<String> userAndDeptIdList = new ArrayList<String>();
		List<Person> personList = RisesoftUtil.getRoleManager().getPersonsByID("",
				 shouFaYuan);
		String[] userChoices = userChoice.split(SysVariables.COLON);
		for (int i = 0; i < personList.size(); i++) {
			OrgUnit org = RisesoftUtil.getPersonManager().getParent(
					ThreadLocalHolder.getTenantId(), personList.get(i).getID());
			if (org.getID().equals(userChoices[1])) {
				String userAndDeptIds = personList.get(i).getID() + ":"
						+ org.getID();

				userAndDeptIdList.add(userAndDeptIds);
			}
		}
		map.put("activitiUser", userAndDeptIdList);
		return map;
	}

	/**
	 * 
	 * @MethodName: lists
	 * @Description: 使用其他系统的事项在审批平台新增收件时，所收的材料列表
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return String 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月16日 下午5:03:57
	 */
	@RequestMapping(value = "/lists")
	public String lists(HttpServletRequest request) {

		return "together/lists";
	}

	/**
	 * 
	 * @MethodName: getClLists
	 * @Description: 非审批平台事项的材料列表
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return Map<String,Object> 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月17日 下午4:57:02
	 */
	@ResponseBody
	@RequestMapping(value = "/getClLists", method = RequestMethod.POST)
	public Map<String, Object> getClLists(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		listmap = togetherWindowService.findClList(request);
		map.put("rows", listmap);
		return map;
	}

	/**
	 * 
	 * @MethodName: getPointIndex
	 * @Description: 收件要点配置首页
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return String 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月17日 下午12:11:19
	 */
	@RequestMapping(value = "/pointIndex")
	public String getPointIndex() {
		return "together/pointIndex";
	}

	// 查看页面
	@RequestMapping(value = "/viewIndex")
	public String viewIndex() {
		return "together/viewList";
	}

	// 查看列表
	@ResponseBody
	@RequestMapping(value = "/viewList", method = RequestMethod.POST)
	public Map<String, Object> viewList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		Pager pager = new Pager(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		pager = togetherWindowService.findReceiveList(request, pager, "未受理");
		List<Map<String, Object>> manageMap = pager.getPageList();
		map.put("pager.pageNo", pager.getPageNo());
		map.put("pageSize", pager.getPageSize());
		map.put("pager.totalRows", pager.getTotalRows());
		map.put("rows", manageMap);
		return map;
	}

	// 办结页面
	@RequestMapping(value = "/endIndex")
	public String endIndex() {
		return "together/endList";
	}

	// 查看列表
	@ResponseBody
	@RequestMapping(value = "/endList", method = RequestMethod.POST)
	public Map<String, Object> endList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		Pager pager = new Pager(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		pager = togetherWindowService.findReceiveList(request, pager, "办结");
		List<Map<String, Object>> manageMap = pager.getPageList();
		map.put("pager.pageNo", pager.getPageNo());
		map.put("pageSize", pager.getPageSize());
		map.put("pager.totalRows", pager.getTotalRows());
		map.put("rows", manageMap);
		return map;
	}

	/**
	 * 
	 * @MethodName: editPoint
	 * @Description: 编辑收件要点
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return Map<String,Object> 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月17日 下午4:59:49
	 */
	@RequestMapping(value = "/editPoint")
	public String editPoint(String method, String itemid, String instanceGuid,
			Model model) {
		model.addAttribute("itemid", itemid);
		model.addAttribute("instanceGuid", instanceGuid);
		if ("add".equals(method)) {
			return "together/editAttachment";
		}
		return "together/editPoint";
	}

	/**
	 * 
	 * @MethodName: editBase
	 * @Description: 编辑基本信息
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return Map<String,Object> 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月17日 下午4:59:49
	 */
	@RequestMapping(value = "/editBase")
	public String editBase(String code, String instanceGuid, String itemid,
			Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		OfficeSpiDeclareinfo office = officeSpiDeclareinfoService
				.findByGuid(instanceGuid);
		if (office == null) {
			office = new OfficeSpiDeclareinfo();
			office.setGuid(instanceGuid);
			office.setZhengjiandaima(code);
			// 查询事项基本信息
			SpmApproveitem approveItem = windowapproveservice
					.findApproveItemByGuid(itemid);
			// 获取业务编号
			String declaresn = codeMapUtil.getDeclaresnByBureauGuid(approveItem
					.getAdminbureauguid());
			// 修改统一流水号为调用市接口产生的流水号
			String newSblsh = getNewSblshUtil.getSblshByApproveItemGuid(itemid);
			office.setDeclaresn(declaresn);
			office.setSblsh(newSblsh);
			office.setApproveitemguid(itemid);
			office.setApproveItemName(approveItem.getApproveitemname());
			office.setDeclareDateTime(new Date());

			togetherWindowService.saveTogetherReceive(office);
		}
		if (office.getXiangmumingcheng() == null) {

			office.setXiangmumingcheng(office.getApproveItemName());
		}
		model.addAttribute("declareDateTime",
				office.getDeclareDateTime() == null ? sdf.format(new Date())
						: sdf.format(office.getDeclareDateTime()));
		model.addAttribute("employeedeptname", ThreadLocalHolder.getPerson()
				.getName());
		model.addAttribute("office", office);
		return "together/editBase";
	}

	@ResponseBody
	@RequestMapping(value = "/getPoints")
	public Map<String, Object> getPoints(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> pointsmap = new ArrayList<Map<String, Object>>();
			pointsmap = togetherWindowService.findClPoint(request);
			map.put("rows", pointsmap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 
	 * @MethodName: editPointInit
	 * @Description: 具体某个材料的收件要点-编辑页面
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return ModelAndView 返回类型
	 * @throws
	 * 
	 * @Author chenbingni
	 * @date 2016年5月20日 下午4:13:28
	 */
	@RequestMapping(value = "/editPointInit")
	public ModelAndView editPointInit(HttpServletRequest request) {
		ModelAndView mav = null;
		String method = request.getParameter("method");
		if ("add".equals(method)) {
			mav = new ModelAndView("together/viewPoint");
		} else {
			mav = new ModelAndView("together/thePoint");
		}
		String id = request.getParameter("ID");
		String materialname = request.getParameter("MATERIALNAME");

		XzqlXzspListspoint point = togetherWindowService.findThePoint(id);
		// 查询材料要点
		Map<String, Object> map = togetherWindowService.findListType(id);
		if (point == null) {
			point = new XzqlXzspListspoint();
			point.setListsguid(id);
			point.setListsname(materialname);
		}
		mav.addObject("point", point);
		mav.addObject("map", map);
		return mav;
	}

	/**
	 * 保存材料要点
	 */
	@ResponseBody
	@RequestMapping(value = "/savePoint", method = RequestMethod.POST)
	public Map<String, Object> savePoint(XzqlXzspListspoint point,
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// 保存材料基本信息

			togetherWindowService.savePoint(point);
			// 保存材料提交类型

			togetherWindowService.saveListsType(point.getListsguid(), request);
			model.put("msg", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.put("msg", 0);
		}
		return model;
	}

	/**
	 * 保存收件
	 */
	@ResponseBody
	@RequestMapping(value = "/saveReceive", method = RequestMethod.POST)
	public Map<String, Object> saveReceive(OfficeSpiDeclareinfo office,
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = request.getParameter("dateTime");
		try {
			if (!StringX.isBlank(date)) {

				office.setDeclareDateTime(sdf.parse(date));
			}
			OfficeSpiDeclareinfo res = togetherWindowService
					.saveTogetherReceive(office);
			if (res != null) {
				model.put("msg", 1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.put("msg", 0);
		}
		return model;
	}

	@RequestMapping(value = "/lingzhengIndex")
	public String lingzhengIndex() {
		return "together/lingzheng/index";
	}

	/**
	 * 
	 * @param 综合窗口
	 *            -统一领证窗口
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lingzhengList", method = RequestMethod.POST)
	public Map<String, Object> lingzhengList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "20";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		pager = togetherWindowService.getLingzhengList(request, pager);
		List<Map<String, Object>> list = pager.getPageList();
		map.put("pager.pageNo", pager.getPageNo());
		map.put("pageSize", pager.getPageSize());
		map.put("pager.totalRows", pager.getTotalRows());
		map.put("rows", list);
		return map;
	}

	@RequestMapping(value = "/declareinfo")
	public String declareinfo(HttpServletRequest request, Model model) {
		if (!StringX.isBlank(request.getParameter("approveitemname"))) {
			model.addAttribute("approveitemname",
					request.getParameter("approveitemname"));
		}

		if (!StringX.isBlank(request.getParameter("approveitemguid"))) {
			model.addAttribute("approveitemguid",
					request.getParameter("approveitemguid"));
		}
		return "together/lingzheng/declareIndex";
	}

	/**
	 * 统一领证窗口-待扫描
	 * 
	 * @return
	 */
	@RequestMapping(value = "/readyScan")
	public String readyScan(HttpServletRequest request, Model model) {
		if (!StringX.isBlank(request.getParameter("approveitemname"))) {
			model.addAttribute("approve",
					request.getParameter("approveitemname"));
			model.addAttribute("approveitemname",
					request.getParameter("approveitemname"));
		}
//		if (!StringX.isBlank(request.getParameter("yxtywlsh"))) {
//			model.addAttribute("yxtywlsh", request.getParameter("yxtywlsh"));
//		}
		return "together/lingzheng/readyScanIndex";
	}

	/**
	 * 
	 * @param 综合窗口
	 *            -待扫描业务列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/scanList", method = RequestMethod.POST)
	public Map<String, Object> scanList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "20";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		pager = togetherWindowService.getScanList(request, pager);
		List<Map<String, Object>> list = pager.getPageList();
		map.put("pager.pageNo", pager.getPageNo());
		map.put("pageSize", pager.getPageSize());
		map.put("pager.totalRows", pager.getTotalRows());
		map.put("rows", list);
		return map;
	}

	// 加载证件类型
	@RiseLog(operateName = "加载证件类型", operateType = "查看")
	@RequestMapping(value = "/zjlxSelect", method = RequestMethod.GET)
	@ResponseBody
	public String wbjSelect() {
		List<Map<String, Object>> spmList = togetherWindowService
				.loadZjlx("有效证件类型");
		List<Map<String, Object>> listtemp = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : spmList) {
			Map<String, Object> maptemp = new HashMap<String, Object>();
			maptemp.put("key", map.get("KEY"));
			maptemp.put("value", map.get("VALUE"));
			listtemp.add(maptemp);
		}
		JSONArray json = JSONArray.fromObject(listtemp);
		// map.put("list", json.toString());
		return json.toString();
	}
	
	@RiseLog(operateName = "统一领证窗口-保存declareinfo信息", operateType = "查看")
	@RequestMapping(value = "/saveDeclareInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeclareInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		String guid = new GUID().toString();
		result = togetherWindowService.saveDeclareInfo(guid, request);
		if (result > 0) {
			map.put("msg", "保存成功！");
			String doctypeguid = spmApproveItemService.getDoctypeByGuid(request.getParameter("approveitemguid"));
			if(!StringX.isBlank(doctypeguid)) {
				doctypeguid = doctypeguid.substring(0, 38);
			}
			map.put("doctypeguid", doctypeguid);
			map.put("instanceguid", guid);
		} else {
			map.put("msg", "保存失败！");
		}
		return map;
	}
	
	/**
	 * * 
	 * @param 统一领证窗口-打开证照扫描页面
	 * @return
	 */
	@RiseLog(operateName = "打开证件扫描页面",operateType = "查看")
	@RequestMapping(value = "/goscan", method = RequestMethod.GET)
	public ModelAndView  goscan(HttpServletRequest req) {
		//得到当前审批事项instanceGuid,approveitemname
		 String instanceGuid = req.getParameter("instanceGuid");
         String doctypeguid = req.getParameter("doctypeguid");  
         String  DECLARESN = req.getParameter("certifyNumber");//证照编号
         String FAZDW =  req.getParameter("bureauName"); //办理单位
         String declarerperson =  req.getParameter("declarerperson"); //证照主体姓名
         String address =  req.getParameter("address"); //申请单位地址
	    //根据approveitemname查询到当前事项基本信息项ID
        
        TBdexDoctype doctypeInfo =  scanningService.findBybasicifnoID(doctypeguid);
	    ModelAndView mav = new ModelAndView("together/lingzheng/scanningforminfo");
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    mav.addObject("DECLARESN", DECLARESN);
	    mav.addObject("FAZDW", FAZDW);
		mav.addObject("approveitemname", doctypeInfo.getDoctypename());
		mav.addObject("doctypeguid", doctypeInfo.getGuid());
		mav.addObject("instanceGuid", instanceGuid);
		mav.addObject("zhengzztxm", declarerperson);
		mav.addObject("fazrq",sdf.format(new Date()));
		mav.addObject("address",address);
		return mav;
	}

	/**
	 * 统一领证窗口-待领取
	 * 
	 * @return
	 */
	// @RequestMapping(value = "/readyLingqu")
	// public String readyLingqu(HttpServletRequest request, Model model) {
	// if(!StringX.isBlank(request.getParameter("approveitemname"))) {
	// model.addAttribute("approveitemname",
	// request.getParameter("approveitemname"));
	// }
	// if(!StringX.isBlank(request.getParameter("yxtywlsh"))) {
	// model.addAttribute("yxtywlsh", request.getParameter("yxtywlsh"));
	// }
	// return "together/lingzheng/readyLqIndex";
	// }

	/**
	 * 
	 * @param 综合窗口
	 *            -待领取业务列表
	 * @return
	 */
	// @ResponseBody
	// @RequestMapping(value = "/lingquList",method=RequestMethod.POST)
	// public Map<String, Object> lingquList(HttpServletRequest request) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// String pageNo = request.getParameter("pager.pageNo");
	// String pageSize = request.getParameter("pager.pageSize");
	// if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
	// pageSize = "20";
	// pageNo = "1";
	// }
	// if(!StringX.isBlank(request.getParameter("approveitemname"))) {
	// map.put("approveitemname", request.getParameter("approveitemname"));
	// }
	// if(!StringX.isBlank(request.getParameter("yxtywlsh"))) {
	// map.put("yxtywlsh", request.getParameter("yxtywlsh"));
	// }
	// Pager pager = new Pager(Integer.parseInt(pageNo),
	// Integer.parseInt(pageSize));
	// pager = togetherWindowService.getLingquList(request,pager);
	// List<Map<String, Object>> list = pager.getPageList();
	// map.put("pager.pageNo", pager.getPageNo());
	// map.put("pageSize", pager.getPageSize());
	// map.put("pager.totalRows", pager.getTotalRows());
	// map.put("rows", list);
	// return map;
	// }

	/**
	 * 审批平台的业务输入业务编号进行判定
	 * 
	 * @return
	 */
	// @RequestMapping(value = "/inputYxtywlsh")
	// public String inputYxtywlsh(Model model, HttpServletRequest request) {
	// System.out.println(request.getParameter("approveitemguid"));
	// System.out.println(request.getParameter("approveitemname"));
	// model.addAttribute("approveitemguid",
	// request.getParameter("approveitemguid") );
	// model.addAttribute("approveitemname",
	// request.getParameter("approveitemname") );
	// return "together/lingzheng/orgCode";
	// }

	// @RequestMapping(value="/scanOrLingqu",method=RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> scanOrLingqu(HttpServletRequest request) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// String yxtywlsh = request.getParameter("yxtywlsh");
	// String code = togetherWindowService.scanOrLingqu(yxtywlsh);
	// String msg;
	// switch(code) {
	// case "0":
	// msg = "找不到该笔业务信息";
	// break;
	// case "1":
	// msg = "该业务尚未办结";
	// break;
	// case "2":
	// msg = "该业务尚未录入证照信息";
	// break;
	// case "3":
	// msg = "待扫描";
	// break;
	// case "4":
	// msg = "该业务尚未办结（异常）";
	// break;
	// case "5":
	// msg = "未领取";
	// break;
	// case "6":
	// msg = "已领取";
	// break;
	// default: msg="系统异常，请联系管理员！";
	// }
	// map.put("code", code);
	// map.put("msg", msg);
	//
	// return map;
	// }

}
