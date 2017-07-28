package net.risesoft.fileflow.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.fileflow.service.ProcInstanceRelationshipService;
import net.risesoft.fileflow.service.ReminderDefineService;
import net.risesoft.fileflow.service.SpWorklistService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.util.SysVariables;
import net.risesoft.utilx.StringX;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sp/worklist")
public class SpWorklistController {

	@Resource(name="spWorklistService")
	private SpWorklistService spWorklistService;
	
	@Autowired
	protected HistoryService historyService;
	
	@Autowired
	protected TaskService taskService;
	
	@Resource(name="officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Resource(name="spmApproveItemService")
	private SpmApproveItemService spmApproveItemService;
	
	@Resource(name="reminderDefineService")
	private ReminderDefineService reminderDefineService;
	
	@Autowired
	private ProcInstanceRelationshipService procInstanceRelationshipService;
	
	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;
	
	
	/**
	 * 显示暂停件列表
	 */
	@RiseLog(operateName = "打开暂停件列表",operateType = "查看")
	@RequestMapping(value = "/pause", method = RequestMethod.GET)
	public String pauseShow() {
		return "worklist/itembox/pause";
	}
	
	/**
	 * 显示特别程序审核列表
	 */
	@RiseLog(operateName = "打开特别程序审核列表",operateType = "查看")
	@RequestMapping(value = "/teBieChengXu", method = RequestMethod.GET)
	public String teBieChengXuShow() {
		return "worklist/itembox/teBieChengXu";
	}
	
	/**
	 * 显示待办列表
	 */
	@RiseLog(operateName = "打开待办件列表",operateType = "查看")
	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public String todoShow() {
		return "worklist/itembox/todo";
	}

	/**
	 * 显示在办列表
	 */
	@RiseLog(operateName = "打开在办件列表",operateType = "查看")
	@RequestMapping(value = "/doing", method = RequestMethod.GET)
	public String doingShow() {
		return "worklist/itembox/doing";
	}

	/**
	 * 显示办结件列表
	 */
	@RiseLog(operateName = "打开办结件列表",operateType = "查看")
	@RequestMapping(value = "/done", method = RequestMethod.GET)
	public String doneShow() {
		return "worklist/itembox/done";
	}
	
	/**
	 * 显示所有件列表
	 */
	@RiseLog(operateName = "打开所有件列表",operateType = "查看")
	@RequestMapping(value = "/allDocument", method = RequestMethod.GET)
	public String allDocumentShow() {
		return "worklist/itembox/allDocument";
	}

	/**
	 * 特别程序审核列表
	 */
	@RiseLog(operateName = "获取特别程序审核列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/teBieChengXu/list")
	public Map<String, Object> teBieChengXuList(@RequestParam(required = false)String documentTitle,
			@RequestParam(required = false)String taskSender,
			@RequestParam(required = false)String startTime,
			@RequestParam(required = false)String endTime,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "30";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		map = spWorklistService.teBieChengXuList(documentTitle,taskSender,startTime,endTime,pager);
		return map;
	}
	
	/**
	 * 暂停件列表
	 */
	@RiseLog(operateName = "获取暂停件列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/pause/list")
	public Map<String, Object> pauseList(@RequestParam(required = false)String pauseDocumentTitle,
			@RequestParam(required = false)String taskSender,
			@RequestParam(required = false)String startTime,
			@RequestParam(required = false)String endTime,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "30";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		map = spWorklistService.pauseList(pauseDocumentTitle,taskSender,startTime,endTime,pager);
		return map;
	}
	
	/**
	 * 待办列表
	 */
	@RiseLog(operateName = "获取待办件列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/todo/list")
	public Map<String, Object> todoList(@RequestParam(required = false)String todoDocumentTitle,
			@RequestParam(required = false)String taskName,
			@RequestParam(required = false)String taskSender,
			@RequestParam(required = false)String startTime,
			@RequestParam(required = false)String endTime,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "30";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		map = spWorklistService.todoList(todoDocumentTitle,taskName,taskSender,startTime,endTime,pager);
		return map;
	}

	/**
	 * 获取在办列表
	 */
	@RiseLog(operateName = "获取在办件列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/doing/list")
	public Map<String, Object> doingList(@RequestParam(required = false)String doingDocumentTitle,
			@RequestParam(required = false)String taskName,
			@RequestParam(required = false)String taskSender,
			@RequestParam(required = false)String taskAssignee,
			@RequestParam(required = false)String startTime,
			@RequestParam(required = false)String endTime,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "30";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		map = spWorklistService.doingList(doingDocumentTitle,taskName,taskSender,taskAssignee,startTime,endTime,pager);
		return map;
	}

	/**
	 * 办结件列表
	 */
	@RiseLog(operateName = "获取办结件列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/done/list")
	public Map<String, Object> doneList(@RequestParam(required = false)String doneDocumentTitle,
			@RequestParam(required = false)String startTime,
			@RequestParam(required = false)String endTime,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "30";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		map = spWorklistService.doneList(doneDocumentTitle,startTime,endTime,pager);
		return map;
	}
	
	/**
	 * 所有件列表
	 */
	@RiseLog(operateName = "获取所有件列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/allDocument/list")
	public Map<String, Object> allDocumentList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pageSize = "30";
			pageNo = "1";
		}
		Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		//map = spWorklistService.allDocumentList(pager);
		map = spWorklistService.allDocumentList2(pager);
		return map;
	}
	
	/**
	 * 待办件查找
	 */
	@RiseLog(operateName = "待办件查找",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/todo/findTodoList")
	public Map<String, Object> findTodoList(@RequestParam(required = false)String todocumentTitle,
			@RequestParam(required = false)String taskName,
			@RequestParam(required = false)String uploadtimeStart,
			@RequestParam(required = false)String uploadtimeStart2,int page,int rows,String sord) {
		return spWorklistService.findTodoList(todocumentTitle,taskName,uploadtimeStart,uploadtimeStart2,page, rows, sord);
	}
	
	/**
	 * 跳转到历程页面
	 * @param processInstanceId
	 * @param model
	 * @return
	 */
	@RiseLog(operateName = "打开历程页面",operateType = "查看")
	@RequestMapping(value = "/history/show", method = RequestMethod.GET)
	public String historyShow(@RequestParam String processInstanceId,@RequestParam(required = false) String SPinstanceId,Model model) {
		try {
			model = spWorklistService.getBureauName(processInstanceId,SPinstanceId,model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "worklist/itembox/history";
	}
	
	/**
	 * 历程列表
	 * @param processInstanceId
	 * @return
	 */
	@RiseLog(operateName = "获取历程列表",operateType = "查看")
	@ResponseBody
	@RequestMapping(value = "/history/{processInstanceId}/json")
	public Map<String, Object> history(@PathVariable("processInstanceId") String processInstanceId) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		try {
			ret_map=spWorklistService.history(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret_map;
	}
	
	/**
	 * 历程信息
	 * @param processInstanceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInformation/{processInstanceId}/json")
	public Map<String, Object> getInformation(@PathVariable("processInstanceId") String processInstanceId) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			//获取全部任务节点
			ProcessDefinitionEntity entity = workflowProcessDefinitionService.findProcessDefinitionByPid(processInstanceId);
			list = workflowProcessDefinitionService.getBpmList(entity.getId(),true);
			String allTaskNames="";
			for(int i = 1;i<list.size();i++){
				String str = (String) list.get(i).get("taskDefName");
				allTaskNames = allTaskNames+SysVariables.COMMA+str;
			}
			allTaskNames = allTaskNames.substring(1);
			
			//获取历史任务节点
			ret_map=spWorklistService.history(processInstanceId);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>>  map = (List<Map<String, Object>>) ret_map.get("rows");
			String historyTaskNames = "";
			for(int i = 0;i<map.size();i++){
				String str = (String) map.get(i).get("name");
				historyTaskNames = historyTaskNames+SysVariables.COMMA+str;
			}
			historyTaskNames = historyTaskNames.substring(1);
			ret_map.put("allTaskNames", allTaskNames);
			ret_map.put("historyTaskNames", historyTaskNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret_map;
	}
	
	/**
	 * 跳转到历史办件列表页面
	 * @param processInstanceId
	 * @param model
	 * @return
	 */
	@RiseLog(operateName = "打开历史办件页面",operateType = "查看")
	@RequestMapping(value = "/historyDocument/show")
	public String historyDocumentShow() {
		return "worklist/itembox/historyDocument";
	}
	/**
	 * 相关信息
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "/relativeMessage")
	public String relativeMessage() {
		return "worklist/itembox/relativeMessage";
	}
	
	  /**
	   * 导出待办excel
	   * @param response
	   * @param request
	   * @param isPage
	   * @param pager
	   * @param todoDocumentTitle
	   * @param taskName
	   * @param taskSender
	   * @param startTime
	   * @param endTime
	   * @return
	   */
	  @RiseLog(operateName = "导出待办excel",operateType = "查看")
	  @RequestMapping(value="/todoExcel")
	  public String todoExcel(HttpServletResponse response,HttpServletRequest request,String isPage,Pager pager,
			  	String todoDocumentTitle,String taskName,String taskSender,String startTime,String endTime){
		 return spWorklistService.todoExcel(response, request, isPage, pager, todoDocumentTitle, taskName, taskSender, startTime, endTime);
	  }
	  
	  /**
	   * 导出在办excel
	   * @param response
	   * @param request
	   * @param isPage
	   * @param pager
	   * @param todoDocumentTitle
	   * @param taskName
	   * @param taskSender
	   * @param startTime
	   * @param endTime
	   * @return
	   */
	  @RiseLog(operateName = "导出在办excel",operateType = "查看")
	  @RequestMapping(value="/doingExcel")
	  public String doingExcel(HttpServletResponse response,HttpServletRequest request,String isPage,Pager pager,
			  	String doingDocumentTitle,String taskAssignee,String taskName,String taskSender,String startTime,String endTime){
		  return spWorklistService.doingExcel(response, request, isPage, pager, doingDocumentTitle, taskAssignee, taskName, taskSender, startTime, endTime);
	  }

}
