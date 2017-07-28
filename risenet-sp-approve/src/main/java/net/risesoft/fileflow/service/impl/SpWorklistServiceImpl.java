package net.risesoft.fileflow.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.common.util.RiseBpmUtil;
import net.risesoft.common.util.SqlPaginationUtil;
import net.risesoft.fileflow.entity.jpa.Opinion;
import net.risesoft.fileflow.entity.jpa.OpinionNew;
import net.risesoft.fileflow.entity.jpa.RejectReason;
import net.risesoft.fileflow.entity.jpa.Reminder;
import net.risesoft.fileflow.entity.jpa.ReminderDefine;
import net.risesoft.fileflow.service.OpinionNewService;
import net.risesoft.fileflow.service.OpinionService;
import net.risesoft.fileflow.service.ProcInstanceRelationshipService;
import net.risesoft.fileflow.service.RejectReasonService;
import net.risesoft.fileflow.service.ReminderDefineService;
import net.risesoft.fileflow.service.ReminderService;
import net.risesoft.fileflow.service.SpWorklistService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftCommonUtil;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.utilx.StringX;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.runtime.NativeProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.common.base.Strings;

@Service(value = "spWorklistService")
public class SpWorklistServiceImpl implements SpWorklistService {

	@Autowired
	ProcessEngineConfigurationImpl processEngineConfiguration;

	@Autowired
	protected TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private WorkflowTaskService workflowTaskService;

	@Autowired
	protected HistoryService historyService;

	@Autowired
	private ReminderService reminderService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private OpinionService opinionService;
	
	@Autowired
	private OpinionNewService opinionNewService;

	@Autowired
	private ReminderDefineService reminderDefineService;
	
	@Autowired
	private SpmApproveItemService spmApproveItemService;
	
	@Autowired
	private ProcInstanceRelationshipService procInstanceRelationshipService;

	@Resource(name = "defaultDataSource")
	private DataSource defaultDataSource;

	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;

	@Resource(name = "rejectReasonService")
	private RejectReasonService rejectReasonService;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.defaultDataSource);
	}
	
	private static Integer formEngineType = RisesoftCommonUtil.formEngineType;

	@Override
	public Map<String, Object> teBieChengXuList(String title,
			String tasksender, String startTime, String endTime,Pager pager) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		try {
			int pageNum = pager.getPageNo();
			int pageSize= pager.getPageSize();
			String userId = ThreadLocalHolder.getPerson().getID();
			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Task> pauseList = null;
			long totalCount = 0;
			Date endDate = null, startDate = null;
			if (StringUtils.isBlank(title))
				title = "";
			if (StringUtils.isBlank(tasksender))
				tasksender = "";
			if (StringUtils.isBlank(endTime)) {
				endDate = new Date();
			} else {
				endDate = sdf.parse(endTime + " 23:59:59");
			}
			
			if (StringUtils.isBlank(startTime)) {
				pauseList = taskService.createTaskQuery().suspended().processVariableValueLike("teBieChengXuState", "特别程序待审").processVariableValueLike("teBieChengXuAuditId", "%" + userId + "%").processVariableValueLike("documentTitle", "%" + title + "%").taskCreatedBefore(endDate).or().processVariableValueLike("teBieChengXuSenderName", "%" + tasksender + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").endOr().orderByTaskCreateTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				totalCount = taskService.createTaskQuery().suspended().processVariableValueLike("teBieChengXuState", "特别程序待审").processVariableValueLike("teBieChengXuAuditId", "%" + userId + "%").processVariableValueLike("documentTitle", "%" + title + "%").taskCreatedBefore(endDate).or().processVariableValueLike("teBieChengXuSenderName", "%" + tasksender + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").endOr().count();
			} else {
				startDate = sdf.parse(startTime + " 00:00:00");
				pauseList = taskService.createTaskQuery().suspended().processVariableValueLike("teBieChengXuState", "特别程序待审").processVariableValueLike("teBieChengXuAuditId", "%" + userId + "%").processVariableValueLike("documentTitle", "%" + title + "%").taskCreatedAfter(startDate).taskCreatedBefore(endDate).or().processVariableValueLike("teBieChengXuSenderName", "%" + tasksender + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").endOr().orderByTaskCreateTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				totalCount = taskService.createTaskQuery().suspended().processVariableValueLike("teBieChengXuState", "特别程序待审").processVariableValueLike("teBieChengXuAuditId", "%" + userId + "%").processVariableValueLike("documentTitle", "%" + title + "%").taskCreatedAfter(startDate).taskCreatedBefore(endDate).or().processVariableValueLike("teBieChengXuSenderName", "%" + tasksender + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").endOr().count();
			}

			Collection<String> taskIds = new ArrayList<String>();
			for (Task task : pauseList) {
				taskIds.add(task.getId());
			}
			// 查找所有已经存在的催办数据
			List<Reminder> reminderList = reminderService.findAllByTaskId(taskIds);
			Map<String, Object> reminderMap = new HashMap<String, Object>();
			for (Reminder reminder : reminderList) {
				reminderMap.put(reminder.getTaskId(), reminder);
			}
			// 保存已经查找过的催办定义，其key为“processDefinitionId:taskDefinitionKey”，其value为null表示查找过但没有催办信息，为ReminderDefine则是对应的催办定义
			Map<String, ReminderDefine> remDefMap = new HashMap<String, ReminderDefine>();
			// 根据流程的业务ID查询实体并关联
			for (Task task : pauseList) {
				Map<String, Object> map = new HashMap<String, Object>();
				String processInstanceId = task.getProcessInstanceId();
				Map<String, Object> vars = taskService.getVariables(task.getId());
				String documentTitle = Strings.nullToEmpty((String) vars.get(SysVariables.DOCUMENTTITLE));
				if (StringX.isBlank(documentTitle)) {
					documentTitle = task.getDescription();
				}
				String taskSender = Strings.nullToEmpty((String) vars.get(SysVariables.TASKSENDER));
				//特别程序申请人
				String teBieChengXuSenderName = Strings.nullToEmpty((String) vars.get(SysVariables.TEBIECHENGXUSENDERNAME));
				String processSerialNumber = Strings.nullToEmpty((String) vars.get(SysVariables.PROCESSSERIALNUMBER));
				String declaresn = Strings.nullToEmpty((String) vars.get(SysVariables.DECLARESN));
				map.put("processInstanceId", processInstanceId);
				String processDefinitionId = task.getProcessDefinitionId();
				map.put("processDefinitionKey", processDefinitionId.split(SysVariables.COLON)[0]);
				map.put(SysVariables.PROCESSSERIALNUMBER, processSerialNumber);
				map.put("processDefinitionId", processDefinitionId);
				map.put("taskId", task.getId());
				map.put("taskDefinitionKey", task.getTaskDefinitionKey());
				map.put("taskName", task.getName());
				map.put("taskCreateTime", task.getCreateTime() == null ? "" : sdf.format(task.getCreateTime()));
				map.put("taskAssignee", task.getAssignee() == null ? "" : task.getAssignee());
				map.put(SysVariables.TASKSENDER, teBieChengXuSenderName!=""?teBieChengXuSenderName:taskSender);//如果特别程序申请人不为空，则显示特别程序申请人
				map.put(SysVariables.DOCUMENTTITLE, documentTitle);
				map.put("declaresn", declaresn);
				
				//办理状态
				String state = Strings.nullToEmpty((String) vars.get(SysVariables.TEBIECHENGXUSTATE));
				map.put("state", state!=null&&state!=""?state:task.getName());
				
				// 下面是催办提醒功能
				remDefMap = reminderDefineService.getReminderDefineMap(processDefinitionId, task.getTaskDefinitionKey(), remDefMap);
				map = reminderDefineService.setReminderDefStatus(task, remDefMap, map);
				if (reminderMap.containsKey(task.getId())) {// 由于页面是点击查看，所以这里放入taskId
					map.put("taskId4Reminder", task.getId());
				} else {
					map.put("taskId4Reminder", "");
				}
				items.add(map);
			}
			ret_map.put("pager.pageNo", pager.getPageNo());
			ret_map.put("pageSize", pager.getPageSize());
			ret_map.put("pager.totalRows", (int)totalCount);
			ret_map.put("rows", items);
			ret_map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			ret_map.put("success", false);
			ret_map.put("msg", "特别程序审核列表异常");
		}
		return ret_map;
	}
	
	@Override
	public Map<String, Object> pauseList(String pauseDocumentTitle, String tasksender, String startTime, String endTime, Pager pager) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		try {
			int pageNum = pager.getPageNo();
			int pageSize= pager.getPageSize();
			String userId = ThreadLocalHolder.getPerson().getID();
			OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId);
			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			OrgUnit bureau = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), userId);
			String user = userId + ":" + org.getID();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Task> pauseList = null;
			long totalCount = 0;
			Date endDate = null, startDate = null;
			if (StringUtils.isBlank(pauseDocumentTitle))
				pauseDocumentTitle = "";
			if (StringUtils.isBlank(tasksender))
				tasksender = "";
			if (StringUtils.isBlank(endTime)) {
				endDate = new Date();
			} else {
				endDate = sdf.parse(endTime + " 23:59:59");
			}
			
			if (StringUtils.isBlank(startTime)) {
				pauseList = taskService.createTaskQuery().suspended().or().taskInvolvedUser(user).processVariableValueLike("teBieChengXuSenderId", "%" + userId + "%").endOr().processVariableValueLike("documentTitle", "%" + pauseDocumentTitle + "%").taskCreatedBefore(endDate).or().processVariableValueLike("teBieChengXuSenderName", "%" + tasksender + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").endOr().processVariableValueLike("taskSender", "%" + tasksender + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").orderByTaskCreateTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				totalCount = taskService.createTaskQuery().suspended().or().taskInvolvedUser(user).processVariableValueLike("teBieChengXuSenderId", "%" + userId + "%").endOr().processVariableValueLike("documentTitle", "%" + pauseDocumentTitle + "%").taskCreatedBefore(endDate).processVariableValueLike("taskSender", "%" + tasksender + "%").count();
			} else {
				startDate = sdf.parse(startTime + " 00:00:00");
				pauseList = taskService.createTaskQuery().suspended().or().taskInvolvedUser(user).processVariableValueLike("teBieChengXuSenderId", "%" + userId + "%").endOr().processVariableValueLike("documentTitle", "%" + pauseDocumentTitle + "%").taskCreatedAfter(startDate).taskCreatedBefore(endDate).processVariableValueLike("taskSender", "%" + tasksender + "%").orderByTaskCreateTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				totalCount = taskService.createTaskQuery().suspended().or().taskInvolvedUser(user).processVariableValueLike("teBieChengXuSenderId", "%" + userId + "%").endOr().processVariableValueLike("documentTitle", "%" + pauseDocumentTitle + "%").taskCreatedAfter(startDate).taskCreatedBefore(endDate).processVariableValueLike("taskSender", "%" + tasksender + "%").count();
			}

			Collection<String> taskIds = new ArrayList<String>();
			for (Task task : pauseList) {
				taskIds.add(task.getId());
			}
			// 查找所有已经存在的催办数据
			List<Reminder> reminderList = reminderService.findAllByTaskId(taskIds);
			Map<String, Object> reminderMap = new HashMap<String, Object>();
			for (Reminder reminder : reminderList) {
				reminderMap.put(reminder.getTaskId(), reminder);
			}
			// 保存已经查找过的催办定义，其key为“processDefinitionId:taskDefinitionKey”，其value为null表示查找过但没有催办信息，为ReminderDefine则是对应的催办定义
			Map<String, ReminderDefine> remDefMap = new HashMap<String, ReminderDefine>();
			// 根据流程的业务ID查询实体并关联
			for (Task task : pauseList) {
				Map<String, Object> map = new HashMap<String, Object>();
				String processInstanceId = task.getProcessInstanceId();
				Map<String, Object> vars = taskService.getVariables(task.getId());
				String documentTitle = Strings.nullToEmpty((String) vars.get(SysVariables.DOCUMENTTITLE));
				if (StringX.isBlank(documentTitle)) {
					documentTitle = task.getDescription();
				}
				String taskSender = Strings.nullToEmpty((String) vars.get(SysVariables.TASKSENDER));
				//特别程序申请人
				String teBieChengXuSenderName = Strings.nullToEmpty((String) vars.get(SysVariables.TEBIECHENGXUSENDERNAME));
				String processSerialNumber = Strings.nullToEmpty((String) vars.get(SysVariables.PROCESSSERIALNUMBER));
				String declaresn = Strings.nullToEmpty((String) vars.get(SysVariables.DECLARESN));
				map.put("processInstanceId", processInstanceId);
				String processDefinitionId = task.getProcessDefinitionId();
				map.put("processDefinitionKey", processDefinitionId.split(SysVariables.COLON)[0]);
				map.put(SysVariables.PROCESSSERIALNUMBER, processSerialNumber);
				map.put("processDefinitionId", processDefinitionId);
				map.put("taskId", task.getId());
				map.put("taskDefinitionKey", task.getTaskDefinitionKey());
				map.put("taskName", task.getName());
				map.put("taskCreateTime", task.getCreateTime() == null ? "" : sdf.format(task.getCreateTime()));
				map.put("taskAssignee", task.getAssignee() == null ? "" : task.getAssignee());
				map.put(SysVariables.TASKSENDER, taskSender);//如果特别程序申请人不为空，则显示特别程序申请人
				map.put(SysVariables.DOCUMENTTITLE, documentTitle);
				map.put("declaresn", declaresn);
				String state = "";
				//办理状态————特别程序
				String teBieChengXuState = Strings.nullToEmpty((String) vars.get(SysVariables.TEBIECHENGXUSTATE));
				if(teBieChengXuState!=null&&teBieChengXuState!=""){
					state = teBieChengXuState;
				}
				//办理状态————补齐补正，一个任务特别程序申请后又补齐补正，或者补齐补正后又特别程序申请，办理状态会冲突~~~~~
				String buQiBuZhengState = Strings.nullToEmpty((String) vars.get(SysVariables.BUQIBUZHENGSTATE));
				if(buQiBuZhengState!=null&&buQiBuZhengState!=""){
					state = buQiBuZhengState;//补齐补正告知
					OfficeSpiDeclareinfo osdf = officeSpiDeclareinfoService.findByProcessInstanceId(processInstanceId);
					if("2".equals(osdf.getReplystatus())){//已经补齐补正
						state = "补齐补正待受理";
					}
				}
				map.put("state", state!=null&&state!=""?state:task.getName());
				// 下面是催办提醒功能
				remDefMap = reminderDefineService.getReminderDefineMap(processDefinitionId, task.getTaskDefinitionKey(), remDefMap);
				map = reminderDefineService.setReminderDefStatus(task, remDefMap, map);
				if (reminderMap.containsKey(task.getId())) {// 由于页面是点击查看，所以这里放入taskId
					map.put("taskId4Reminder", task.getId());
				} else {
					map.put("taskId4Reminder", "");
				}
				items.add(map);
			}
			ret_map.put("pager.pageNo", pager.getPageNo());
			ret_map.put("pageSize", pager.getPageSize());
			ret_map.put("pager.totalRows", (int)totalCount);
			ret_map.put("rows", items);
			ret_map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			ret_map.put("success", false);
			ret_map.put("msg", "暂停件列表异常");
		}
		return ret_map;
	}

	@Override
	public Map<String, Object> todoList(String todoDocumentTitle, String taskName,
			String tasksender, String startTime, String endTime, Pager pager) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		try {
			int pageNum = pager.getPageNo();
			int pageSize= pager.getPageSize();
			String userId = ThreadLocalHolder.getPerson().getID();
			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Task> todoList = null;
			long totalCount = 0;
			Date endDate = null, startDate = null;
			if (StringUtils.isBlank(todoDocumentTitle))
				todoDocumentTitle = "";
			if (StringUtils.isBlank(taskName))
				taskName = "";
			if (StringUtils.isBlank(tasksender))
				tasksender = "";
			if (StringUtils.isBlank(endTime)) {
				endDate = new Date();
			} else {
				endDate = sdf.parse(endTime + " 23:59:59");
			}
			OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId);
			String user = userId + ":" + org.getID();
			if (StringUtils.isBlank(startTime)) {
				//todoList = taskService.createTaskQuery().active().taskUnassigned().taskInvolvedUser(user).or().taskUnassigned().taskAssigneeLike("%" + userId + "%").endOr().processVariableValueLike("documentTitle", "%" + todoDocumentTitle + "%").taskCreatedBefore(endDate).taskNameLike("%" + taskName + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").endOr().orderByTaskCreateTime().desc().listPage((page - 1) * rows, rows);
																//查找受让人或候选人为user的件
				todoList = taskService.createTaskQuery().active().taskCandidateOrAssigned(user).processVariableValueLike("documentTitle", "%" + todoDocumentTitle + "%").taskCreatedBefore(endDate).taskNameLike("%" + taskName + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").orderByTaskCreateTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				totalCount = taskService.createTaskQuery().active().taskCandidateOrAssigned(user).processVariableValueLike("documentTitle", "%" + todoDocumentTitle + "%").taskCreatedBefore(endDate).taskNameLike("%" + taskName + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").count();
			} else {
				startDate = sdf.parse(startTime + " 00:00:00");
				todoList = taskService.createTaskQuery().active().taskCandidateOrAssigned(user).processVariableValueLike("documentTitle", "%" + todoDocumentTitle + "%").taskCreatedAfter(startDate).taskCreatedBefore(endDate).taskNameLike("%" + taskName + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").orderByTaskCreateTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				totalCount = taskService.createTaskQuery().active().taskCandidateOrAssigned(user).processVariableValueLike("documentTitle", "%" + todoDocumentTitle + "%").taskCreatedAfter(startDate).taskCreatedBefore(endDate).taskNameLike("%" + taskName + "%").processVariableValueLike("taskSender", "%" + tasksender + "%").count();
			}
			Collection<String> taskIds = new ArrayList<String>();
			for (Task task : todoList) {
				taskIds.add(task.getId());
			}
			// 查找所有已经存在的催办数据
			List<Reminder> reminderList = reminderService.findAllByTaskId(taskIds);
			Map<String, Object> reminderMap = new HashMap<String, Object>();
			for (Reminder reminder : reminderList) {
				reminderMap.put(reminder.getTaskId(), reminder);
			}
			// 保存已经查找过的催办定义，其key为“processDefinitionId:taskDefinitionKey”，其value为null表示查找过但没有催办信息，为ReminderDefine则是对应的催办定义
			Map<String, ReminderDefine> remDefMap = new HashMap<String, ReminderDefine>();
			// 根据流程的业务ID查询实体并关联
			for (Task task : todoList) {
				Map<String, Object> map = new HashMap<String, Object>();
				String processInstanceId = task.getProcessInstanceId();
				Map<String, Object> vars = taskService.getVariables(task.getId());
				String documentTitle = Strings.nullToEmpty((String) vars.get(SysVariables.DOCUMENTTITLE));
				String taskSender = Strings.nullToEmpty((String) vars.get(SysVariables.TASKSENDER));
				String processSerialNumber = Strings.nullToEmpty((String) vars.get(SysVariables.PROCESSSERIALNUMBER));
				String declaresn = Strings.nullToEmpty((String) vars.get(SysVariables.DECLARESN));
				String approveitemguid = Strings.nullToEmpty((String) vars.get(SysVariables.APPROVEITEMGUID));
				int isNewTodo=taskService.getVariableLocal(task.getId(), SysVariables.ISNEWTODO)==null?1:0;
				//int isNewTodo=vars.get(SysVariables.ISNEWTODO)==null?1:(int)vars.get(SysVariables.ISNEWTODO);
				map.put("processInstanceId", processInstanceId);
				String processDefinitionId = task.getProcessDefinitionId();
				map.put("processDefinitionKey", processDefinitionId.split(SysVariables.COLON)[0]);
				map.put(SysVariables.PROCESSSERIALNUMBER, StringUtils.isNotBlank(processSerialNumber) ? processSerialNumber : processInstanceId);
				map.put("processDefinitionId", processDefinitionId);
				map.put("taskId", task.getId());
				map.put("taskDefinitionKey", task.getTaskDefinitionKey());
				map.put("taskName", task.getName());
				map.put("description", task.getDescription());
				map.put("taskCreateTime", task.getCreateTime() == null ? "" : sdf.format(task.getCreateTime()));
				map.put("taskAssignee", task.getAssignee() == null ? "" : task.getAssignee());
				map.put(SysVariables.TASKSENDER, taskSender);
				map.put(SysVariables.DOCUMENTTITLE, documentTitle);
				map.put(SysVariables.DECLARESN, declaresn);
				map.put(SysVariables.APPROVEITEMGUID, approveitemguid);
				map.put(SysVariables.ISNEWTODO,isNewTodo);
				// 下面是催办提醒功能
				remDefMap = reminderDefineService.getReminderDefineMap(processDefinitionId, task.getTaskDefinitionKey(), remDefMap);
				map = reminderDefineService.setReminderDefStatus(task, remDefMap, map);
				if (reminderMap.containsKey(task.getId())) {// 由于页面是点击查看，所以这里放入taskId
					map.put("taskIdReminder", task.getId());
				} else {
					map.put("taskIdReminder", "");
				}
				items.add(map);
			}
			ret_map.put("pager.pageNo", pager.getPageNo());
			ret_map.put("pageSize", pager.getPageSize());
			ret_map.put("pager.totalRows", (int)totalCount);
			ret_map.put("rows", items);
			ret_map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			ret_map.put("success", false);
			ret_map.put("msg", "待办件列表异常");
		}
		return ret_map;
	}

	@Override
	public Map<String, Object> doingList(String doingDocumentTitle, String taskName, String taskSender, String taskAssignee, String startTime, String endTime, Pager pager) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		try {
			String userId = ThreadLocalHolder.getPerson().getID();
			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			int pageNum = pager.getPageNo();
			int pageSize= pager.getPageSize();
			long totalCount = 0;
			String sql = "";
			String countSql = "";
			String pagedSql = "";
			String baseSql1 = "";
			String baseSql2 = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isBlank(taskName))
				taskName = "";
			if (StringUtils.isBlank(doingDocumentTitle))
				doingDocumentTitle = "";
			if (StringUtils.isBlank(taskAssignee))
				taskAssignee = "";
			if (StringUtils.isBlank(taskSender))
				taskSender = "";
			Date endDate = null;
			if (StringUtils.isBlank(endTime)) {
				endDate = new Date();
				endTime = sdf.format(endDate);
			}
			// 由于存在一人多岗问题，这里USER_ID_使用like
			if ("mssql".equals(processEngineConfiguration.getDatabaseType())) {
				baseSql1 = "SELECT DISTINCT t.PROC_INST_ID_,t.START_TIME_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ " + "WHERE ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST" + " WHERE end_time_ IS NULL) GROUP BY t.PROC_INST_ID_ , t.START_TIME_";
				baseSql2 = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE" + " ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST WHERE" + " end_time_ IS NULL)";
				sql = "SELECT DISTINCT RES.* , P.KEY_ AS ProcessDefinitionKey, P.ID_ AS ProcessDefinitionId, P.NAME_ AS ProcessDefinitionName, P.VERSION_ AS ProcessDefinitionVersion" + ", P.DEPLOYMENT_ID_ AS DeploymentId,rr.START_TIME_ FROM ACT_RU_EXECUTION RES INNER JOIN ACT_RE_PROCDEF P ON RES.PROC_DEF_ID_ = P.ID_" + " LEFT JOIN (" + baseSql1 + ") rr on res.PROC_INST_ID_=rr.PROC_INST_ID_ WHERE RES.PARENT_ID_ IS NULL AND" + " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ") ORDER BY rr.START_TIME_ DESC";
				countSql = " select count(*) FROM ACT_RU_EXECUTION RES INNER JOIN ACT_RE_PROCDEF P ON RES.PROC_DEF_ID_ = P.ID_" + " LEFT JOIN (" + baseSql1 + ") rr on res.PROC_INST_ID_=rr.PROC_INST_ID_ WHERE RES.PARENT_ID_ IS NULL AND" + " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ")";
				pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql, (pageNum - 1) * pageSize, pageSize);
			} else if ("mysql".equals(processEngineConfiguration.getDatabaseType())) {
				baseSql1 = "SELECT DISTINCT t.PROC_INST_ID_,t.START_TIME_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ " + "WHERE ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST" + " WHERE end_time_ IS NULL)";
				baseSql2 = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE" + " ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST WHERE" + " end_time_ IS NULL)";
				if (StringUtils.isBlank(startTime)) {
					sql = "SELECT DISTINCT RES.Id_,res.rev_,res.proc_inst_id_,res.parent_id_,res.proc_def_id_,res.super_exec_,res.act_id_,res.is_active_,res.is_concurrent_,res.is_scope_,res.is_event_scope_,res.cached_ent_state_,"
							+ " res.tenant_id_,res.lock_time_,  " + "P.KEY_ AS ProcessDefinitionKey, P.ID_ AS ProcessDefinitionId, P.NAME_ AS ProcessDefinitionName, P.VERSION_ AS ProcessDefinitionVersion, P.DEPLOYMENT_ID_ AS DeploymentId, START_TIME_ "
							+ " FROM ACT_RU_TASK rt,act_ru_variable arv, ACT_RU_EXECUTION RES ,ACT_RE_PROCDEF P " + ",(" + baseSql1 + ") rr " + "where res.proc_inst_id_ = rt.proc_inst_id_ and rt.name_ like '%" + taskName + "%'"
							+ " and rt.CREATE_TIME_ <= '" + endTime + " 23:59:59' and arv.proc_inst_id_=res.proc_inst_id_  and (arv.NAME_ = 'documentTitle' AND arv.TEXT_ like '%" + doingDocumentTitle + "%')" 
							+ " and RES.PROC_DEF_ID_ = P.ID_ and res.PROC_INST_ID_=rr.PROC_INST_ID_ and RES.PARENT_ID_ IS NULL  AND" + " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ") ORDER BY START_TIME_ DESC";
				}else{
					sql = "SELECT DISTINCT RES.Id_,res.rev_,res.proc_inst_id_,res.parent_id_,res.proc_def_id_,res.super_exec_,res.act_id_,res.is_active_,res.is_concurrent_,res.is_scope_,res.is_event_scope_,res.cached_ent_state_,"
							+ " res.tenant_id_,res.lock_time_,  " + "P.KEY_ AS ProcessDefinitionKey, P.ID_ AS ProcessDefinitionId, P.NAME_ AS ProcessDefinitionName, P.VERSION_ AS ProcessDefinitionVersion, P.DEPLOYMENT_ID_ AS DeploymentId, rr.START_TIME_ "
							+ " FROM ACT_RU_TASK rt,act_ru_variable arv, ACT_RU_EXECUTION RES ,ACT_RE_PROCDEF P " + ",(" + baseSql1 + ") rr " + "where res.proc_inst_id_ = rt.proc_inst_id_ and rt.name_ like '%" + taskName + "%'"
							+ " and rt.CREATE_TIME_ >= '" + startTime + " 00:00:00' and rt.CREATE_TIME_ <= '" + endTime + " 23:59:59' and arv.proc_inst_id_=res.proc_inst_id_  and (arv.NAME_ = 'documentTitle' AND arv.TEXT_ like '%" + doingDocumentTitle + "%')" 
							+ " and RES.PROC_DEF_ID_ = P.ID_ and res.PROC_INST_ID_=rr.PROC_INST_ID_ and RES.PARENT_ID_ IS NULL  AND" + " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ") ORDER BY rr.START_TIME_ DESC";
				}
				countSql = " select count(*) FROM (" + sql + ") ss";
				pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql,  (pageNum - 1) * pageSize, pageSize);
			} else if ("oracle".equals(processEngineConfiguration.getDatabaseType())) {
				baseSql1 = "SELECT DISTINCT t.PROC_INST_ID_,t.START_TIME_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ " + "WHERE ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST" + " WHERE end_time_ IS NULL) GROUP BY t.PROC_INST_ID_,t.START_TIME_ ORDER BY t.START_TIME_ DESC";
				baseSql2 = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE" + " ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST WHERE" + " end_time_ IS NULL)";
				if (StringUtils.isBlank(startTime)) {
					sql = "SELECT DISTINCT RES.Id_,res.rev_,res.proc_inst_id_,res.parent_id_,res.proc_def_id_,res.super_exec_,res.act_id_,res.is_active_,res.is_concurrent_,res.is_scope_,res.is_event_scope_,res.cached_ent_state_,res.tenant_id_,res.lock_time_,  " + "P.KEY_ AS ProcessDefinitionKey, P.ID_ AS ProcessDefinitionId, P.NAME_ AS ProcessDefinitionName, P.VERSION_ AS ProcessDefinitionVersion, P.DEPLOYMENT_ID_ AS DeploymentId,to_char(rr.START_TIME_,'YYYY-MM-DD HH24:MI:SS') START_TIME_,sp.declaresn business_key_,sp.approveitemname name_ " + "FROM office_spi_declareinfo sp,ACT_RU_TASK rt,act_ru_variable arv, ACT_RU_EXECUTION RES ,ACT_RE_PROCDEF P " + ",(" + baseSql1 + ") rr " + "where res.proc_inst_id_ = rt.proc_inst_id_ and rt.name_ like '%" + taskName + "%'"
							+ "and rt.CREATE_TIME_ <= to_date('" + endTime + " 23:59:59','yyyy-mm-dd hh24:mi:ss')" + "and arv.proc_inst_id_=res.proc_inst_id_  and (arv.NAME_ = 'documentTitle' AND arv.TEXT_ like '%" + doingDocumentTitle + "%')" + "and RES.PROC_DEF_ID_ = P.ID_ and res.PROC_INST_ID_=rr.PROC_INST_ID_(+) and RES.PARENT_ID_ IS NULL and res.proc_inst_id_=sp.processinstanceid(+) AND" + " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ") ORDER BY START_TIME_ DESC";
				} else {
					sql = "SELECT DISTINCT RES.Id_,res.rev_,res.proc_inst_id_,res.parent_id_,res.proc_def_id_,res.super_exec_,res.act_id_,res.is_active_,res.is_concurrent_,res.is_scope_,res.is_event_scope_,res.cached_ent_state_,res.tenant_id_,res.lock_time_,  " + "P.KEY_ AS ProcessDefinitionKey, P.ID_ AS ProcessDefinitionId, P.NAME_ AS ProcessDefinitionName, P.VERSION_ AS ProcessDefinitionVersion, P.DEPLOYMENT_ID_ AS DeploymentId,to_char(rr.START_TIME_,'yyyy-mm-dd hh24:mi:ss') START_TIME_,sp.declaresn business_key_,sp.approveitemname name_ " + "FROM office_spi_declareinfo sp,ACT_RU_TASK rt,act_ru_variable arv, ACT_RU_EXECUTION RES ,ACT_RE_PROCDEF P " + ",(" + baseSql1 + ") rr " + "where res.proc_inst_id_ = rt.proc_inst_id_ and rt.name_ like '%" + taskName
							+ "%' and rt.CREATE_TIME_ >= to_date('" + startTime + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and rt.CREATE_TIME_ <= to_date('" + endTime + " 23:59:59','yyyy-mm-dd hh24:mi:ss')" + " and arv.proc_inst_id_=res.proc_inst_id_  and (arv.NAME_ = 'documentTitle' AND  arv.TEXT_ like '%" + doingDocumentTitle + "%')" + " and RES.PROC_DEF_ID_ = P.ID_ and res.PROC_INST_ID_=rr.PROC_INST_ID_(+) and RES.PARENT_ID_ IS NULL and res.proc_inst_id_=sp.processinstanceid(+) AND" + " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ") ORDER BY START_TIME_ DESC";
				}
				countSql = " select count(*) FROM (" + sql + ")";
				pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql, (pageNum - 1) * pageSize, pageSize);
			}
			/************
			 OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId);
			 String user=userId+":"+org.getID();
			 if(StringUtils.isBlank(doingDocumentTitle))
			 doingDocumentTitle="";
			 List<HistoricTaskInstance> process2 = historyService.createHistoricTaskInstanceQuery().unfinished().processVariableValueLike("taskSenderId","%"+userId+"%").list();

			 List<ProcessInstance> list1 = runtimeService.createProcessInstanceQuery().variableValueLike("documentTitle","%"+doingDocumentTitle+"%").variableValueLike("user", "%"+taskSender+"%").listPage( (page - 1) * rows, rows);
			 totalCount = runtimeService.createProcessInstanceQuery().variableValueLike("documentTitle","%"+doingDocumentTitle+"%").variableValueLike("user","%"+taskSender+"%").count();
			 List<ProcessInstance> process1 =runtimeService.createProcessInstanceQuery().variableValueLike("users","%"+userId+"%").list();
			 //todoList = taskService.createTaskQuery().processInstanceIdIn(processInstanceIds)
			 **************/
			NativeProcessInstanceQuery query = runtimeService.createNativeProcessInstanceQuery().sql(pagedSql).parameter("userId", userId + ":%");
			List<ProcessInstance> list = query.list();
			NativeProcessInstanceQuery countQuery = runtimeService.createNativeProcessInstanceQuery().sql(countSql).parameter("userId", userId + ":%");
			totalCount = countQuery.count();
			for (ProcessInstance processInstance : list) {
				String processInstanceId = processInstance.getProcessInstanceId();
				List<Task> taskList = workflowTaskService.getTaskByProcessInstanceId(processInstanceId);
				String declaresn=Strings.nullToEmpty((String) taskService.getVariable(taskList.get(0).getId(), SysVariables.DECLARESN));
				String existAssigneeName = "";// 保存正在办理当前流程的人员的中文名称，多个之间用中文顿号分隔
				String existAssigneeId = "";// 保存正在办理当前流程的人员的“人员guid:部门guid”，多个之间用英文逗号分隔
				String approveName = processInstance.getName();// 事项名称
				if (taskList != null && taskList.size() > 0) {
					/**
					 * 当并行的时候，会获取到多个task，为了并行时当前办理人显示多人，而不是显示多条记录，需要分开分别进行处理
					 */
					if (taskList.size() == 1) {
						if (taskList.get(0) == null) {
							continue;
						}
						String assignee = taskList.get(0).getAssignee();
						if (StringUtils.isNotBlank(assignee)) {
							existAssigneeId = assignee;
							Person employee2 = RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(), assignee.split(SysVariables.COLON)[0]);
							if (employee2 != null) {
								existAssigneeName = employee2.getName();
							}
						}
					} else {
						for (Task task : taskList) {
							String assignee = task.getAssignee();
							if (StringUtils.isNotBlank(assignee)) {
								existAssigneeId = RiseBpmUtil.genCustomStr(existAssigneeId, task.getAssignee(), SysVariables.COMMA);// 并行时，领导选取时存在顺序，因此这里也存在顺序
								Person employee2 = RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(), assignee.split(SysVariables.COLON)[0]);
								if (employee2 != null) {
									existAssigneeName = RiseBpmUtil.genCustomStr(existAssigneeName, employee2.getName(), "、");// 并行时，领导选取时存在顺序，因此这里也存在顺序
								}
							}
						}
					}
					Task task = taskList.get(0);
					Map<String, Object> record = setTodoElement(task, existAssigneeId, existAssigneeName);
					Reminder re = reminderService.findByTaskIdAndSenderId(task.getId(),ThreadLocalHolder.getPerson().getID());
					if (null != re) {
						record.put("taskId4Reminder", task.getId());
					} else {
						record.put("taskId4Reminder", "");
					}
					record.put("declaresn", declaresn);
					// 如果文件标题为空 则用事项名称替换
					if (StringX.isBlank(record.get("documentTitle"))) {
						record.put("documentTitle", approveName);
					}
					items.add(record);
				} else {
					List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType(SysVariables.CALLACTIVITY).list();
					if (historicActivityInstanceList != null && historicActivityInstanceList.size() > 0) {
						Map<String, Object> record = setTodoElement(historicActivityInstanceList.get(0), existAssigneeId, existAssigneeName);
						record.put("declaresn", declaresn);
						items.add(record);
					}
				}
			}
			ret_map.put("pager.pageNo", pager.getPageNo());
			ret_map.put("pageSize", pager.getPageSize());
			ret_map.put("pager.totalRows", (int)totalCount);
			ret_map.put("rows", items);
			ret_map.put("success", true);
		} catch (Exception e) {
			ret_map.put("success", false);
			ret_map.put("msg", "在办件列表异常");
			e.printStackTrace();
		}
		return ret_map;
	}

	@Override
	public Map<String, Object> doneList(String doneDocumentTitle, String startTime, String endTime, Pager pager) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		try {
			int pageNum = pager.getPageNo();
			int pageSize= pager.getPageSize();
			String userId = ThreadLocalHolder.getPerson().getID();
			OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId);
			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = null, endDate = null;
			List<HistoricProcessInstance> historicProcessInstanceList = null;
			long totalCount = 0;
			String user = userId + ":" + org.getID();
			if (StringUtils.isBlank(doneDocumentTitle))
				doneDocumentTitle = "";
			if (StringUtils.isBlank(endTime)) {
				endDate = new Date();
			} else {
				endDate = sdf.parse(endTime + " 23:59:59");
			}
			/**1*/
			String baseSql = "";
			String sql = "";
			String countSql = "";
			String pagedSql = "";
			if ("oracle".equals(processEngineConfiguration.getDatabaseType())) {
				baseSql = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity' AND m.USER_ID_ LIKE #{userId}))";
				sql = "SELECT distinct RES.Id_,res.proc_inst_id_,res.proc_def_id_,res.start_time_,res.end_time_,res.duration_,res.start_user_id_,res.start_act_id_,res.end_act_id_,res.super_process_instance_id_,t.declaresn business_key_,res.delete_reason_,res.tenant_id_,res.name_" + " FROM ACT_HI_PROCINST RES,office_spi_declareinfo t WHERE RES.END_TIME_ IS NOT NULL AND ( res.PROC_INST_ID_ IN (" + baseSql + ") ) and res.proc_inst_id_=t.processinstanceid(+) ORDER BY RES.END_TIME_ DESC";
				countSql = "SELECT COUNT(*) FROM ACT_HI_PROCINST RES WHERE RES.END_TIME_ IS NOT NULL AND ( res.PROC_INST_ID_ IN (" + baseSql + "))";
				pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql,(pageNum - 1) * pageSize, pageSize);
			}
			/**1*/
			if (StringUtils.isBlank(startTime)) {
				if(doneDocumentTitle == ""){
					historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().involvedUser(user).startedBefore(endDate).finished().orderByProcessInstanceEndTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				}else{
					historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().involvedUser(user).startedBefore(endDate).variableValueLike("documentTitle", "%" + doneDocumentTitle + "%").finished().orderByProcessInstanceEndTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				}
				totalCount = historyService.createHistoricProcessInstanceQuery().involvedUser(user).startedBefore(endDate).variableValueLike("documentTitle", "%" + doneDocumentTitle + "%").finished().count();
			} else {
				startDate = sdf.parse(startTime + " 00:00:00");
				if(doneDocumentTitle == ""){
					historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().involvedUser(user).startedAfter(startDate).startedBefore(endDate).finished().orderByProcessInstanceEndTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				}else{
					historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().involvedUser(user).startedAfter(startDate).startedBefore(endDate).variableValueLike("documentTitle", "%" + doneDocumentTitle + "%").finished().orderByProcessInstanceEndTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				}
				totalCount = historyService.createHistoricProcessInstanceQuery().involvedUser(user).startedAfter(startDate).startedBefore(endDate).variableValueLike("documentTitle", "%" + doneDocumentTitle + "%").finished().count();
			}
			/**2*
			NativeHistoricProcessInstanceQuery query = historyService.createNativeHistoricProcessInstanceQuery().sql(pagedSql).parameter("userId", userId + ":%");
			List<HistoricProcessInstance> historicProcessInstanceList1 = query.list();
			NativeHistoricProcessInstanceQuery countQuery = historyService.createNativeHistoricProcessInstanceQuery().sql(countSql).parameter("userId", userId + ":%");
			//totalCount = countQuery.count();
			*/
			for (HistoricProcessInstance processInstance : historicProcessInstanceList) {

				if (processInstance != null) {
					String processInstanceId = processInstance.getId();
					// 罗湖项目特点，子流程被删除的办结件不在办结件列表里面显示
					// 所以下面首先判断是不是被删除的，如果是则判断是不是子流程，如果是被删除且是子流程，则跳过
					String regex = "delete:+(.*)";
					String deleteReason = processInstance.getDeleteReason();
					if (StringUtils.isNotBlank(deleteReason) && deleteReason.matches(regex) && procInstanceRelationshipService.isSubProcessInstance(processInstanceId)) {
						continue;
					}
					// 再做一次查询获取带流程变量的指定processInstanceId对应的流程实例，之所以这里再做一次查询是因为之前查询的时候如果带着流程变量查询，统一流程实例Id将会多条记录
					HistoricProcessInstance entity = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).includeProcessVariables().singleResult();
					if (entity != null) {
						Map<String, Object> map = new HashMap<String, Object>();
						Map<String, Object> props = entity.getProcessVariables();
						String documentTitle = Strings.nullToEmpty((String) props.get(SysVariables.DOCUMENTTITLE));
						String processSerialNumber = Strings.nullToEmpty((String) props.get(SysVariables.PROCESSSERIALNUMBER));
						String declaresn = Strings.nullToEmpty((String) props.get(SysVariables.DECLARESN));
						String processDefinitionId = entity.getProcessDefinitionId();
						map.put(SysVariables.PROCESSSERIALNUMBER, processSerialNumber);
						map.put(SysVariables.DOCUMENTTITLE, documentTitle);
						map.put("processDefinitionId", processDefinitionId);
						map.put("processDefinitionKey", processDefinitionId.split(SysVariables.COLON)[0]);
						map.put("processInstanceId", processInstanceId);
						map.put("startTime", entity.getStartTime() == null ? "" : sdf.format(entity.getStartTime()));
						map.put("endTime", entity.getEndTime() == null ? "" : sdf.format(entity.getEndTime()));
						map.put("deleteReason", entity.getDeleteReason());
						map.put("taskDefinitionKey", "chuangkourenyuanbanjie");
						map.put("declaresn", declaresn);
						items.add(map);
					}
				}
			}
			ret_map.put("pager.pageNo", pager.getPageNo());
			ret_map.put("pageSize", pager.getPageSize());
			ret_map.put("pager.totalRows", (int)totalCount);
			ret_map.put("rows", items);
			ret_map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			ret_map.put("success", false);
			ret_map.put("msg", "办结件列表异常");
		}
		return ret_map;
	}

	@Override
	public Map<String, Object> allDocumentList(Pager pager) {
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		String userId = ThreadLocalHolder.getPerson().getID();
		Map<String, Object> ret_map = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		String todoSql = "";
		String doingSql = "";
		String doneSql = "";
		String sql = "";
		String countSql = "";
		String pagedSql = "";
		long totalCount = 0;
		
		try {
			if ("oracle".equals(processEngineConfiguration.getDatabaseType())) {
				todoSql = "select a.* from (select distinct RES.* from ACT_RU_TASK RES, ACT_RE_PROCDEF D, ACT_RU_IDENTITYLINK I,office_spi_declareinfo t, ACT_RU_EXECUTION are "
					   + " WHERE RES.PROC_DEF_ID_ = D.ID_ and I.TASK_ID_ = RES.ID_ and RES.Proc_Inst_Id_=t.processinstanceid and RES.Proc_Inst_Id_=are.proc_inst_id_ AND (are.SUSPENSION_STATE_ = 1)"
					   + " and (RES.ASSIGNEE_ like '%"+userId+"%' or (RES.ASSIGNEE_ is null and I.USER_ID_ like '%"+userId+"%'))) a order by a.create_time_ desc";
				
				doingSql = "SELECT DISTINCT RES.Id_,res.rev_,res.proc_inst_id_,res.parent_id_,res.proc_def_id_,res.super_exec_,"
		               + " res.act_id_,res.is_active_,res.is_concurrent_,res.is_scope_,res.is_event_scope_,res.cached_ent_state_,res.tenant_id_,res.lock_time_,"
		               + " P.KEY_ AS ProcessDefinitionKey,P.ID_ AS ProcessDefinitionId,P.NAME_ AS ProcessDefinitionName,P.VERSION_ AS ProcessDefinitionVersion,"
		               + " P.DEPLOYMENT_ID_ AS DeploymentId,to_char(rr.START_TIME_, 'yyyy-mm-dd hh24:mi:ss') START_TIME_,sp.declaresn business_key_,sp.approveitemname name_"
		               + " FROM office_spi_declareinfo sp,ACT_RU_TASK rt,act_ru_variable arv,ACT_RU_EXECUTION RES,ACT_RE_PROCDEF P,(SELECT DISTINCT t.PROC_INST_ID_, t.START_TIME_"
		               + " FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_ =m.PROC_INST_ID_ WHERE ((t.ASSIGNEE_ LIKE '%"+userId+"%' AND t.END_TIME_ IS NOT NULL) OR"
		               + " (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_ = 'callActivity' AND m.USER_ID_ LIKE '%"+userId+"%')) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_"
		               + " FROM ACT_HI_PROCINST WHERE end_time_ IS NULL) GROUP BY t.PROC_INST_ID_, t.START_TIME_ ORDER BY t.START_TIME_ DESC) rr where res.proc_inst_id_ = rt.proc_inst_id_ and rt.name_ like '%%'"
		               + " and rt.CREATE_TIME_ <=to_date('2016-08-10 23:59:59', 'yyyy-mm-dd hh24:mi:ss') and arv.proc_inst_id_ = res.proc_inst_id_ and (arv.NAME_ = 'documentTitle' AND arv.TEXT_ like '%%')"
		               + " and RES.PROC_DEF_ID_ = P.ID_ and res.PROC_INST_ID_ = rr.PROC_INST_ID_ and RES.PARENT_ID_ IS NULL and res.proc_inst_id_ = sp.processinstanceid(+)"
		               + " AND (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_ ="
		               + " m.PROC_INST_ID_ WHERE ((t.ASSIGNEE_ LIKE '%"+userId+"%' AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_ = 'callActivity' AND"
		               + " m.USER_ID_ LIKE '%"+userId+"%')) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST WHERE end_time_ IS NULL)) ORDER BY START_TIME_ DESC";
				
				doneSql = "SELECT distinct RES.Id_,res.proc_inst_id_,res.proc_def_id_,res.start_time_,res.end_time_,res.duration_,res.start_user_id_,res.start_act_id_,res.end_act_id_,res.super_process_instance_id_,"
					   + " t.declaresn business_key_,res.delete_reason_,res.tenant_id_,res.name_ FROM ACT_HI_PROCINST RES, office_spi_declareinfo t WHERE RES.END_TIME_ IS NOT NULL AND (res.PROC_INST_ID_ IN"
					   + " (SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_ = m.PROC_INST_ID_ WHERE ((t.ASSIGNEE_ LIKE '%"+userId+"%' AND t.END_TIME_ IS NOT NULL) OR"
					   + " (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_ = 'callActivity' AND m.USER_ID_ LIKE '%"+userId+"%')))) and res.proc_inst_id_ = t.processinstanceid(+) ORDER BY RES.END_TIME_ DESC";
				
				//sql = "";
				//countSql = "";
				//List<Map<String,Object>> listMap = jdbcTemplate.queryForList(doneSql);
				
				//String baseSql = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity' AND m.USER_ID_ LIKE #{userId}))";
				
				//sql = "SELECT distinct RES.Id_,res.proc_inst_id_,res.proc_def_id_,res.start_time_,res.end_time_,res.duration_,res.start_user_id_,res.start_act_id_,res.end_act_id_,res.super_process_instance_id_,t.declaresn business_key_,res.delete_reason_,res.tenant_id_,res.name_" + " FROM ACT_HI_PROCINST RES,office_spi_declareinfo t WHERE RES.END_TIME_ IS NOT NULL AND ( res.PROC_INST_ID_ IN (" + baseSql + ") ) and res.proc_inst_id_=t.processinstanceid(+) ORDER BY RES.END_TIME_ DESC";
				
				//countSql = "SELECT COUNT(*) FROM ACT_HI_PROCINST RES WHERE RES.END_TIME_ IS NOT NULL AND ( res.PROC_INST_ID_ IN (" + baseSql + "))";
				
				String baseSql = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE m.USER_ID_ LIKE #{userId}";
				sql = "SELECT distinct RES.Id_,res.proc_inst_id_,res.proc_def_id_,res.start_time_,res.end_time_,res.duration_,res.start_user_id_,res.start_act_id_,res.end_act_id_,res.super_process_instance_id_,t.declaresn business_key_,res.delete_reason_,res.tenant_id_,res.name_" + " FROM ACT_HI_PROCINST RES,office_spi_declareinfo t WHERE ( res.PROC_INST_ID_ IN (" + baseSql + ") ) and res.proc_inst_id_=t.processinstanceid(+) ORDER BY RES.END_TIME_ DESC";
				countSql = "SELECT COUNT(*) FROM ACT_HI_PROCINST RES WHERE ( res.PROC_INST_ID_ IN (" + baseSql + "))";
				pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql,(pageNum - 1) * pageSize, pageSize);
				
				
				OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = null, endDate = null;
				List<HistoricProcessInstance> historicProcessInstanceList = null;
				String user = userId + ":" + org.getID();
				//historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().involvedUser(user).finished().orderByProcessInstanceEndTime().desc().listPage((pageNum - 1) * pageSize, pageSize);
				String doneDocumentTitle="";
				totalCount = historyService.createHistoricProcessInstanceQuery().involvedUser(user).finished().count();
				items = jdbcTemplate.queryForList(pagedSql);
				//pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql, (pageNum - 1) * pageSize, pageSize);
				System.out.println("pageSql///"+pagedSql+"///////");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ret_map.put("pager.pageNo", pager.getPageNo());
		ret_map.put("pageSize", pager.getPageSize());
		ret_map.put("pager.totalRows", (int)totalCount);
		ret_map.put("rows", items);
		ret_map.put("success", true);
		return ret_map;
	}
	public Map<String, Object> allDocumentList2(Pager pager) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		int totalCount = 0;
		
		Map<String, Object> ret_mapTemp = this.doingList(null, null, null, null, null, null, pager);
		List<Map<String, Object>> itemsTemp=(List<Map<String, Object>>) ret_mapTemp.get("rows");
		items.addAll(itemsTemp);
		totalCount+=(int)ret_mapTemp.get("pager.totalRows");
		
		ret_mapTemp=this.doneList(null, null, null, pager);
		itemsTemp=(List<Map<String, Object>>) ret_mapTemp.get("rows");
		items.addAll(itemsTemp);
		totalCount+=(int)ret_mapTemp.get("pager.totalRows");
		
		ret_mapTemp=this.pauseList(null, null, null, null, pager);
		itemsTemp=(List<Map<String, Object>>) ret_mapTemp.get("rows");
		items.addAll(itemsTemp);
		totalCount+=(int)ret_mapTemp.get("pager.totalRows");
		
		ret_mapTemp=this.todoList(null, null, null, null, null, pager);
		itemsTemp=(List<Map<String, Object>>) ret_mapTemp.get("rows");
		items.addAll(itemsTemp);
		totalCount+=(int)ret_mapTemp.get("pager.totalRows");
		
		ret_map.put("pager.pageNo", pager.getPageNo());
		ret_map.put("pageSize", pager.getPageSize());
		ret_map.put("pager.totalRows", (int)totalCount);
		ret_map.put("rows", items);
		
		ret_map.put("success", true);
		return ret_map;
	}

	/**
	 * @param task
	 * @param assigneeId
	 *            当前办理人Id:部门Id，多个之间用英文逗号分开
	 * @param assigneeName
	 *            当前办理人中文名称，多个之间用中文顿号分开
	 * @return
	 */
	public Map<String, Object> setTodoElement(Task task, String assigneeId, String assigneeName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, ReminderDefine> remDefMap = new HashMap<String, ReminderDefine>();
		Map<String, Object> record = new HashMap<String, Object>();
		Map<String, Object> props = taskService.getVariables(task.getId());
		record.put("processInstanceId", task.getProcessInstanceId());
		String processDefinitionId = task.getProcessDefinitionId();
		record.put("processDefinitionKey", processDefinitionId.split(SysVariables.COLON)[0]);
		String processSerialNumber = (String) props.get(SysVariables.PROCESSSERIALNUMBER);

		record.put("taskAssignee", assigneeName);
		record.put(SysVariables.PROCESSSERIALNUMBER, processSerialNumber);
		record.put("processDefinitionId", processDefinitionId);
		record.put(SysVariables.DOCUMENTTITLE, Strings.nullToEmpty((String) props.get(SysVariables.DOCUMENTTITLE)));
		String declaresn = Strings.nullToEmpty((String) props.get(SysVariables.DECLARESN));
		record.put(SysVariables.TASKSENDER, (String) props.get(SysVariables.TASKSENDER));
		record.put("taskDefinitionKey", task.getTaskDefinitionKey());
		record.put("taskId", task.getId());
		record.put("taskName", task.getName());
		record.put("taskCreateTime", task.getCreateTime() == null ? "" : sdf.format(task.getCreateTime()));
		record.put("taskAssigneeId", assigneeId);
		record.put("declaresn", declaresn);

		// 下面是催办提醒功能
		remDefMap = reminderDefineService.getReminderDefineMap(processDefinitionId, task.getTaskDefinitionKey(), remDefMap);
		record = reminderDefineService.setReminderDefStatus(task, remDefMap, record);
		return record;
	}

	/**
	 * 
	 * @param task
	 * @param assigneeId
	 *            当前办理人Id:部门Id，多个之间用英文逗号分开
	 * @param assigneeName
	 *            当前办理人中文名称，多个之间用中文顿号分开
	 * @return
	 */
	public Map<String, Object> setTodoElement(HistoricActivityInstance historicActivityInstance, String assigneeId, String assigneeName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> record = new HashMap<String, Object>();
		Map<String, Object> props = runtimeService.getVariables(historicActivityInstance.getExecutionId());
		record.put("processInstanceId", historicActivityInstance.getProcessInstanceId());
		String processDefinitionId = historicActivityInstance.getProcessDefinitionId();
		record.put("processDefinitionKey", processDefinitionId.split(SysVariables.COLON)[0]);
		String processSerialNumber = Strings.nullToEmpty((String) props.get(SysVariables.PROCESSSERIALNUMBER));
		String documentTitle = Strings.nullToEmpty((String) props.get(SysVariables.DOCUMENTTITLE));
		String declaresn = Strings.nullToEmpty((String) props.get(SysVariables.DECLARESN));
		String approveItemGUID = Strings.nullToEmpty((String) props.get(SysVariables.APPROVEITEMGUID));
		record.put("taskAssignee", "子流程办理");
		record.put(SysVariables.PROCESSSERIALNUMBER, processSerialNumber);
		record.put("processDefinitionId", processDefinitionId);
		record.put(SysVariables.DOCUMENTTITLE, documentTitle);
		record.put(SysVariables.TASKSENDER, (String) props.get(SysVariables.TASKSENDER));
		record.put("taskDefinitionKey", historicActivityInstance.getActivityId());
		record.put("taskId", historicActivityInstance.getId());
		record.put("taskName", historicActivityInstance.getActivityName());
		record.put("taskCreateTime", historicActivityInstance.getStartTime() == null ? "" : sdf.format(historicActivityInstance.getStartTime()));
		record.put("taskAssigneeId", assigneeId);
		record.put(SysVariables.DECLARESN, declaresn);
		record.put(SysVariables.APPROVEITEMGUID, approveItemGUID);
		return record;
	}

	@Override
	public long getTeBieChengXuCount() {
		String userId = ThreadLocalHolder.getPerson().getID();
		long totalCount = taskService.createTaskQuery().suspended().processVariableValueLike("teBieChengXuState", "特别程序待审").processVariableValueLike("teBieChengXuAuditId", "%" + userId + "%").count();
		return totalCount;
	}
	
	@Override
	public long getPauseCount() {
		String userId = ThreadLocalHolder.getPerson().getID();
		OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId);
		String user = userId + ":" + org.getID();
		long totalCount = taskService.createTaskQuery().suspended().or().taskInvolvedUser(user).processVariableValueLike("teBieChengXuSenderId", "%" + userId + "%").endOr().count();
		return totalCount;
	}

	@Override
	public long getTodoCount() {
		String userId = ThreadLocalHolder.getPerson().getID();
		OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId);
		String user = userId + ":" + org.getID();
		long totalCount = taskService.createTaskQuery().active().taskCandidateOrAssigned(user).count();
		return totalCount;
	}

	@Override
	public long getDoingCount() {
		Person person = ThreadLocalHolder.getPerson();
		String userId = person.getID();
		long totalCount = 0;
		String sql = "";
		String countSql = "";
		String baseSql1 = "";
		String baseSql2 = "";
		// 由于存在一人多岗问题，这里USER_ID_使用like
		if ("mssql".equals(processEngineConfiguration.getDatabaseType())) {
			baseSql1 = "SELECT DISTINCT t.PROC_INST_ID_,t.START_TIME_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ " + "WHERE ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST" + " WHERE end_time_ IS NULL) GROUP BY t.PROC_INST_ID_ , t.START_TIME_";
			baseSql2 = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE" + " ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST WHERE" + " end_time_ IS NULL)";
			countSql = " select count(*) FROM ACT_RU_EXECUTION RES INNER JOIN ACT_RE_PROCDEF P ON RES.PROC_DEF_ID_ = P.ID_" + " LEFT JOIN (" + baseSql1 + ") rr on res.PROC_INST_ID_=rr.PROC_INST_ID_ WHERE RES.PARENT_ID_ IS NULL AND" + " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ")";
		} else if ("mysql".equals(processEngineConfiguration.getDatabaseType())) {
			baseSql1 = "SELECT DISTINCT t.PROC_INST_ID_,t.START_TIME_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ " + "WHERE ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST" + " WHERE end_time_ IS NULL) GROUP BY t.PROC_INST_ID_ ,t.START_TIME_ ";
			baseSql2 = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE" + " ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST WHERE" + " end_time_ IS NULL)";
			
			sql = "SELECT DISTINCT RES.Id_,res.rev_,res.proc_inst_id_,res.parent_id_,res.proc_def_id_,res.super_exec_,res.act_id_,res.is_active_,res.is_concurrent_,res.is_scope_,res.is_event_scope_,res.cached_ent_state_,res.tenant_id_,res.lock_time_,  P.KEY_ AS ProcessDefinitionKey, P.ID_ AS ProcessDefinitionId, P.NAME_ AS ProcessDefinitionName, P.VERSION_ AS ProcessDefinitionVersion, P.DEPLOYMENT_ID_ AS DeploymentId,rr.START_TIME_ FROM ACT_RU_EXECUTION RES ,ACT_RE_PROCDEF P " + ",(" + baseSql1 + ") rr where RES.PROC_DEF_ID_ = P.ID_ and res.PROC_INST_ID_=rr.PROC_INST_ID_ and RES.PARENT_ID_ IS NULL AND"
					+ " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ")";
			countSql = " select count(*) FROM (" + sql + ") qq";
			
			//countSql = " select count(*) FROM ACT_RU_EXECUTION RES INNER JOIN ACT_RE_PROCDEF P ON RES.PROC_DEF_ID_ = P.ID_" + " LEFT JOIN (" + baseSql1 + ") rr on res.PROC_INST_ID_=rr.PROC_INST_ID_ WHERE RES.PARENT_ID_ IS NULL AND" + " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ")";
		} else if ("oracle".equals(processEngineConfiguration.getDatabaseType())) {
			baseSql1 = "SELECT DISTINCT t.PROC_INST_ID_,t.START_TIME_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ " + "WHERE ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST" + " WHERE end_time_ IS NULL) GROUP BY t.PROC_INST_ID_,t.START_TIME_ ORDER BY t.START_TIME_ DESC";
			baseSql2 = "SELECT DISTINCT t.proc_inst_id_ FROM ACT_HI_ACTINST t LEFT JOIN act_ru_identitylink m ON t.PROC_INST_ID_=m.PROC_INST_ID_ WHERE" + " ((t.ASSIGNEE_ LIKE #{userId} AND t.END_TIME_ IS NOT NULL) OR (t.ASSIGNEE_ IS NULL AND t.ACT_TYPE_='callActivity'" + " AND m.USER_ID_ LIKE #{userId})) AND t.proc_inst_id_ IN (SELECT DISTINCT proc_inst_id_ FROM ACT_HI_PROCINST WHERE" + " end_time_ IS NULL)";
			sql = "SELECT DISTINCT RES.Id_,res.rev_,res.proc_inst_id_,res.parent_id_,res.proc_def_id_,res.super_exec_,res.act_id_,res.is_active_,res.is_concurrent_,res.is_scope_,res.is_event_scope_,res.cached_ent_state_,res.tenant_id_,res.lock_time_,  P.KEY_ AS ProcessDefinitionKey, P.ID_ AS ProcessDefinitionId, P.NAME_ AS ProcessDefinitionName, P.VERSION_ AS ProcessDefinitionVersion, P.DEPLOYMENT_ID_ AS DeploymentId,to_char(rr.START_TIME_,'yyyy-mm-dd hh24:mi:ss') START_TIME_,sp.declaresn business_key_,sp.approveitemname name_ FROM office_spi_declareinfo sp,ACT_RU_EXECUTION RES ,ACT_RE_PROCDEF P " + ",(" + baseSql1 + ") rr where RES.PROC_DEF_ID_ = P.ID_ and res.PROC_INST_ID_=rr.PROC_INST_ID_(+) and RES.PARENT_ID_ IS NULL and res.proc_inst_id_=sp.processinstanceid(+) AND"
					+ " (RES.SUSPENSION_STATE_ = 1) AND res.PROC_INST_ID_ IN (" + baseSql2 + ") ORDER BY START_TIME_ DESC";
			countSql = " select count(*) FROM (" + sql + ")";
		}
		NativeProcessInstanceQuery countQuery = runtimeService.createNativeProcessInstanceQuery().sql(countSql).parameter("userId", userId + ":%");
		totalCount = countQuery.count();
		return totalCount;
	}

	@Override
	public long getDoneCount() {
		Person person = ThreadLocalHolder.getPerson();
		String userId = person.getID();
		OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), userId);
		String user = userId + ":" + org.getID();
		long totalCount = historyService.createHistoricProcessInstanceQuery().involvedUser(user).finished().count();
		return totalCount;
	}

	@Override
	public Map<String, Object> findTodoList(String todocumentTitle, String taskName, String uploadtimeStart, String uploadtimeStart2, int page, int rows, String sord) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		try {
			String userId = ThreadLocalHolder.getPerson().getID();
			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			String sql = "", countSql = "", pagedSql = "";
			// 由于存在一人多岗问题，这里USER_ID_使用like
			if ("mssql".equals(processEngineConfiguration.getDatabaseType())) {
				sql = " select a.* from ( select distinct  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D" + " on RES.PROC_DEF_ID_= D.ID_  left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_" + "  WHERE (RES.SUSPENSION_STATE_=1  and RES.ASSIGNEE_ like '" + userId + "%'" + " or (RES.ASSIGNEE_ is null and I.USER_ID_ like '" + userId + "%' ))) a order by a.create_time_ desc";
				countSql = " select count(*) from ( select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D" + " on RES.PROC_DEF_ID_= D.ID_  left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_" + "  WHERE (RES.SUSPENSION_STATE_=1  and RES.ASSIGNEE_ like '" + userId + "%'" + " or (RES.ASSIGNEE_ is null and I.USER_ID_ like '" + userId + "%' ) )) a";
				pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql, (page - 1) * rows, rows);
			} else if ("mysql".equals(processEngineConfiguration.getDatabaseType())) {
				sql = " select a.* from ( select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D" + " on RES.PROC_DEF_ID_= D.ID_  left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_" + "  WHERE (RES.SUSPENSION_STATE_=1  and RES.NAME_ like '%" + taskName + "%' and RES.ASSIGNEE_ like '" + userId + "%' or (RES.ASSIGNEE_ is null and I.USER_ID_ like '" + userId + "%' ) )) a order by a.create_time_ desc";
				countSql = " select count(*) from ( select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D" + " on RES.PROC_DEF_ID_= D.ID_  left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_" + "  WHERE (RES.SUSPENSION_STATE_=1  and RES.ASSIGNEE_ like '" + userId + "%'" + " or (RES.ASSIGNEE_ is null and I.USER_ID_ like '" + userId + "%' ) )) a ";
				pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql, (page - 1) * rows, rows);
			} else if ("oracle".equals(processEngineConfiguration.getDatabaseType())) {
				if (uploadtimeStart == null || uploadtimeStart == "") {
					uploadtimeStart = "0001-01-01";
				}
				if (!(uploadtimeStart2 == null || uploadtimeStart2 == "")) {
					sql = " select distinct a.*,t.declaresn form_key_ from ( select distinct RES.Id_,res.rev_,res.execution_id_,res.proc_inst_id_,res.proc_def_id_,res.name_,res.parent_task_id_,res.description_,res.task_def_key_,res.owner_,res.assignee_,res.delegation_,res.priority_,res.create_time_,res.due_date_,res.category_,res.suspension_state_,res.tenant_id_ from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D" + " on RES.PROC_DEF_ID_= D.ID_  left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ left join act_hi_varinst V on V.PROC_INST_ID_ = RES.PROC_INST_ID_" + "  WHERE (RES.SUSPENSION_STATE_=1  and RES.NAME_ like '%" + taskName + "%' and  RES.CREATE_TIME_ >= to_date('" + uploadtimeStart + " 00:00:00','yyyy-mm-dd hh24:mi:ss') AND RES.CREATE_TIME_ <= to_date('" + uploadtimeStart2
							+ " 23:59:59','yyyy-mm-dd hh24:mi:ss') and V.NAME_ = 'documentTitle' AND V.TEXT_ like '%" + todocumentTitle + "%' AND RES.ASSIGNEE_ like '" + userId + "%'" + ")) a,office_spi_declareinfo t where t.processinstanceid(+)=a.proc_inst_id_ order by a.create_time_ desc";
					countSql = " select count(*) from ( select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D" + " on RES.PROC_DEF_ID_= D.ID_  left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ left join act_hi_varinst V on V.PROC_INST_ID_ = RES.PROC_INST_ID_" + "  WHERE (RES.SUSPENSION_STATE_=1  and RES.NAME_ like '%" + taskName + "%'  and  RES.CREATE_TIME_ >= to_date('" + uploadtimeStart + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and RES.CREATE_TIME_ <= to_date('" + uploadtimeStart2 + " 23:59:59','yyyy-mm-dd hh24:mi:ss') and V.NAME_ = 'documentTitle' AND V.TEXT_ like '%" + todocumentTitle + "%' AND RES.ASSIGNEE_ like '" + userId + "%'" + " )) a ";
				} else {
					sql = " select distinct a.*,t.declaresn form_key_ from ( select distinct RES.Id_,res.rev_,res.execution_id_,res.proc_inst_id_,res.proc_def_id_,res.name_,res.parent_task_id_,res.description_,res.task_def_key_,res.owner_,res.assignee_,res.delegation_,res.priority_,res.create_time_,res.due_date_,res.category_,res.suspension_state_,res.tenant_id_ from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D" + " on RES.PROC_DEF_ID_= D.ID_  left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ left join act_hi_varinst V on V.PROC_INST_ID_ = RES.PROC_INST_ID_" + "  WHERE (RES.SUSPENSION_STATE_=1  and RES.NAME_ like '%" + taskName + "%' and RES.CREATE_TIME_ >= to_date('" + uploadtimeStart + " 00:00:00','yyyy-mm-dd hh24:mi:ss') AND V.NAME_ = 'documentTitle' AND V.TEXT_ like '%"
							+ todocumentTitle + "%' AND RES.ASSIGNEE_ like '" + userId + "%'" + " )) a,office_spi_declareinfo t where t.processinstanceid(+)=a.proc_inst_id_ order by a.create_time_ desc";
					countSql = " select count(*) from ( select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D" + " on RES.PROC_DEF_ID_= D.ID_  left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ left join act_hi_varinst V on V.PROC_INST_ID_ = RES.PROC_INST_ID_" + "  WHERE (RES.SUSPENSION_STATE_=1  and RES.NAME_ like '%" + taskName + "%'  and RES.CREATE_TIME_ >= to_date('" + uploadtimeStart + " 00:00:00','yyyy-mm-dd hh24:mi:ss') AND V.NAME_ = 'documentTitle' AND V.TEXT_ like '%" + todocumentTitle + "%' AND RES.ASSIGNEE_ like '" + userId + "%'" + " )) a ";
				}
				pagedSql = SqlPaginationUtil.generatePagedSql(defaultDataSource, sql, (page - 1) * rows, rows);
			}
			NativeTaskQuery todoQuery = taskService.createNativeTaskQuery().sql(pagedSql);
			List<Task> todoList = todoQuery.list();
			NativeTaskQuery countQuery = taskService.createNativeTaskQuery().sql(countSql);
			long totalCount = countQuery.count();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Collection<String> taskIds = new ArrayList<String>();
			for (Task task : todoList) {
				taskIds.add(task.getId());
			}
			// 查找所有已经存在的催办数据
			List<Reminder> reminderList = reminderService.findAllByTaskId(taskIds);
			Map<String, Object> reminderMap = new HashMap<String, Object>();
			for (Reminder reminder : reminderList) {
				reminderMap.put(reminder.getTaskId(), reminder);
			}
			// 保存已经查找过的催办定义，其key为“processDefinitionId:taskDefinitionKey”，其value为null表示查找过但没有催办信息，为ReminderDefine则是对应的催办定义
			Map<String, ReminderDefine> remDefMap = new HashMap<String, ReminderDefine>();
			// 根据流程的业务ID查询实体并关联
			for (Task task : todoList) {
				Map<String, Object> map = new HashMap<String, Object>();
				String processInstanceId = task.getProcessInstanceId();
				Map<String, Object> vars = taskService.getVariables(task.getId());
				String documentTitle = Strings.nullToEmpty((String) vars.get(SysVariables.DOCUMENTTITLE));
				String taskSender = Strings.nullToEmpty((String) vars.get(SysVariables.TASKSENDER));
				String processSerialNumber = Strings.nullToEmpty((String) vars.get(SysVariables.PROCESSSERIALNUMBER));
				map.put("processInstanceId", processInstanceId);
				String processDefinitionId = task.getProcessDefinitionId();
				if (StringUtils.isNotBlank(processDefinitionId)) {
					map.put("processDefinitionKey", processDefinitionId.split(SysVariables.COLON)[0]);
				}
				map.put(SysVariables.PROCESSSERIALNUMBER, StringUtils.isNotBlank(processSerialNumber) ? processSerialNumber : processInstanceId);
				map.put("processDefinitionId", processDefinitionId);
				map.put("taskId", task.getId());
				map.put("taskDefinitionKey", task.getTaskDefinitionKey());
				map.put("taskName", task.getName());
				map.put("taskCreateTime", task.getCreateTime() == null ? "" : sdf.format(task.getCreateTime()));
				map.put("taskAssignee", task.getAssignee() == null ? "" : task.getAssignee());
				map.put(SysVariables.TASKSENDER, taskSender);
				map.put(SysVariables.DOCUMENTTITLE, documentTitle);
				// String
				// declaresn=officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(processInstanceId);
				map.put("declaresn", task.getFormKey());
				// 下面是催办提醒功能
				remDefMap = reminderDefineService.getReminderDefineMap(processDefinitionId, task.getTaskDefinitionKey(), remDefMap);
				map = reminderDefineService.setReminderDefStatus(task, remDefMap, map);
				if (reminderMap.containsKey(task.getId())) {// 由于页面是点击查看，所以这里放入taskId
					map.put("taskId4Reminder", task.getId());
				} else {
					map.put("taskId4Reminder", "");
				}
				items.add(map);
			}
			ret_map.put("success", true);
			ret_map.put("currpage", page);
			ret_map.put("totalpages", Math.ceil(totalCount / rows) + 1);
			ret_map.put("total", totalCount);
			ret_map.put("rows", items);
		} catch (Exception e) {
			e.printStackTrace();
			ret_map.put("success", false);
			ret_map.put("msg", "待办件列表异常");
		}
		return ret_map;
	}

	@Override
	public Model getBureauName(String processInstanceId,String SPinstanceId,Model model) {
		String bureauName = "";
		// 根据当前流程实例查找其是否有子流程实例或者父流程实例，当前只适用于向上或者向下查找一层
		List<String> relateProcInstanceIdList = procInstanceRelationshipService.getAllRelateProcessInstanceIds(processInstanceId);
		// 当是父子流程时，存在多个历程，历程的第一个要显示当前用户所在的部门的历程，所以这里首先删除查询出的当前流程实例的Id，然后将其放在第一个
		for (String s : relateProcInstanceIdList) {
			if (!s.equals(processInstanceId)) {
				processInstanceId = processInstanceId + SysVariables.COMMA + s;
			}
		}
		/*-----下面代码是获取当前流程实例起草人所在部门名称，用来在历程列表的表头显示部门-----*/
		String[] processInstanceIds = processInstanceId.split(SysVariables.COMMA);
		for (int i = 0; i < processInstanceIds.length; i++) {
			HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceIds[i]);
			List<HistoricTaskInstance> results = query.listPage(0, 1);
			String currentBureauName = "历程列表";
			// 这里根据起草人获取单位的中文名称
			if (results.size() > 0) {
				String startEmployeeGuid = results.get(0).getAssignee();
				if (StringUtils.isBlank(startEmployeeGuid)) {
					List<IdentityLink> identityList = taskService.getIdentityLinksForTask(results.get(0).getId());
					startEmployeeGuid = identityList.get(0).getUserId();
				}
				if (startEmployeeGuid.contains(SysVariables.COMMA)) {
					startEmployeeGuid = startEmployeeGuid.substring(0, startEmployeeGuid.indexOf(","));
				}
				// 为了解决一人多岗问题，现在assignee、user等采用的是“人员id:部门id”（startEmployeeGuid采用这种形式）
				currentBureauName = RisesoftUtil.getDepartmentManager().getDepartment(startEmployeeGuid.split(":")[1]).getName();
			}
			if (StringUtils.isNotBlank(bureauName)) {
				bureauName = bureauName + SysVariables.COMMA + currentBureauName;
			} else {
				bureauName = currentBureauName;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(SysVariables.DATETIME_PATTERN);
			
			//获取事项的到期时间
			String approveitemguid="";
			SpmApproveitem spmApproveitem = null;
			if(processInstanceId==""||processInstanceId==null){//没有启动流程
				OfficeSpiDeclareinfo officeSpiDeclareinfo=officeSpiDeclareinfoService.findByGuid(SPinstanceId);
				spmApproveitem=spmApproveItemService.findByApproveitemguid(officeSpiDeclareinfo.getApproveitemguid());
			}else{
				approveitemguid = officeSpiDeclareinfoService.findApproveitemguidByProcessInstanceId(processInstanceId);
				spmApproveitem=spmApproveItemService.findByApproveitemguid(approveitemguid);
			}
			Integer timelimit=0;
			if(spmApproveitem!=null){
				 timelimit=spmApproveitem.getTimelimit();
			}
			String startTime = "";
			String endTime ="";
			if(results.size()>0){//启动流程之后
				startTime = sdf.format(results.get(0).getCreateTime());//流程开始时间
				endTime=sdf.format(reminderDefineService.getAdvanceDate(results.get(0).getCreateTime(), 2, timelimit));

			}else{//没有启动流程时
				startTime = sdf.format(new Date());
				endTime=sdf.format(reminderDefineService.getAdvanceDate(new Date(), 2, timelimit));
			}
			if(i==0){
				processInstanceId+=SysVariables.SEMICOLON+startTime+SysVariables.SEMICOLON+endTime;
			}else{
				processInstanceId+=SysVariables.SEMICOLON+startTime+SysVariables.SEMICOLON+endTime+SysVariables.COMMA;
			}
		}
		model.addAttribute("bureauName", bureauName);
		model.addAttribute("processInstanceId", processInstanceId);
		return model;
	}
	
	@Override
	public Map<String, Object> history(String processInstanceId) {
		Map<String, Object> ret_map = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 由于需要获取call Activity类型的节点，将查询方法改为如下
						List<HistoricActivityInstance> results = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
						// long totalCount =
						// historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).count();
						for (int i = 0; i < results.size(); i++) {
							HistoricActivityInstance hai = results.get(i);
							if (hai == null) {
								continue;
							}
							String type = hai.getActivityType();
							if (type.contains(SysVariables.STARTEVENT) || type.contains(SysVariables.GATEWAY)) {// 过滤掉开始节点和结束节点和所有GateWay类型节点
								continue;
							}
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", hai.getId());
							// 收件人
							map.put("assignee", "");
							// 任务名称
							map.put("name", hai.getActivityName());
							// 意见
							map.put("opinion", "");
							if (type.contains(SysVariables.ENDEVENT)) {
								HistoricVariableInstanceQuery htiq = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId);
								// 发送人
								String user4Complete = (String) htiq.variableName("user4Complete").singleResult().getValue();
								map.put("taskSender", user4Complete);
								// 动作名称
								String actionName = (String) htiq.variableName("actionName4SpecialComplete").singleResult().getValue();
								if (StringUtils.isNotBlank(actionName))
									map.put("actionName", actionName);
								// 办结描述
								RejectReason rejectReason = rejectReasonService.findByProcessInstanceIdAndActionSignLike(processInstanceId, "办结");
								if (null != rejectReason)
									map.put("description", rejectReason.getRejectReason());
								map.put("name", "结束");
							} else {
								// 动作名称
								map.put("actionName", hai.getActivityName());
								String description = historyService.createHistoricTaskInstanceQuery().taskId(hai.getTaskId()).singleResult().getDescription();
								if (StringUtils.isBlank(description)) {
									HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery().taskId(hai.getTaskId()).variableName("actionName").singleResult();
									if (hvi != null) {
										if (StringUtils.isNotBlank((String) hvi.getValue()))
											map.put("actionName", (String) hvi.getValue());
									}
								} else {
									map.put("actionName", description);
								}
								if (i == 1) {
									map.put("actionName", "新建");
								}
								// 发送人
								String taskSender = (String) historyService.createHistoricVariableInstanceQuery().taskId(hai.getTaskId()).variableName(SysVariables.TASKSENDER).singleResult().getValue();
								map.put("taskSender", taskSender);
								// 收件人
								String assignee = hai.getAssignee();
								if (StringUtils.isNotBlank(assignee)) {
									Person employee = RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(), assignee.split(":")[0]);
									if (employee != null) {
										/**
										 * 2.如果该任务的流程变量zhuBan不为空，说明该任务的受让人是主办人。
										 */
										HistoricVariableInstance zhuBan = historyService.createHistoricVariableInstanceQuery().taskId(hai.getTaskId()).variableName(SysVariables.ZHUBAN).singleResult();
										if(zhuBan!=null){
											map.put("assignee", employee.getName()+"（主办）");
										}else{
											map.put("assignee", employee.getName());
										}
									}
								}
							// 意见
							ArrayList<Integer> opinionTypeList = new ArrayList<Integer>();
							opinionTypeList.add(1);// 个人意见
							opinionTypeList.add(2);// 部门（科长）意见
							opinionTypeList.add(3);// 领导意见
							if(formEngineType == 1){
								List<Opinion> opinionList = opinionService.findByTaskIdAndUserIdAndOpinionType(hai.getTaskId(), (assignee == null || assignee == "") ? "" : assignee.split(SysVariables.COLON)[0], opinionTypeList);
								String opinionStr = "";
								for (Opinion opinion : opinionList) {
									opinionStr += opinion.getContent();
								}
								map.put("opinion", opinionStr);
							}else if(formEngineType == 2){//电子表单新意见
								List<OpinionNew> opinionNewList = opinionNewService.findByTaskIdAndUserIdAndOpinionType(hai.getTaskId(), (assignee == null || assignee == "") ? "" : assignee.split(SysVariables.COLON)[0], opinionTypeList);
								String opinionStr = "";
								for (OpinionNew opinion : opinionNewList) {
									opinionStr += opinion.getContent();
								}
								map.put("opinion", opinionStr);
							}
							// 描述
							HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery().taskId(hai.getTaskId()).variableName("description").singleResult();
							map.put("description", hvi == null ? "" : hvi.getValue());
							}
							map.put("startTime", hai.getStartTime() == null ? sdf.format(new Date()) : sdf.format(hai.getStartTime()));
							map.put("endTime", hai.getEndTime() == null ? "" : sdf.format(hai.getEndTime()));
							if (hai.getEndTime() == null) {
								map.put("time", "");
							} else {
								Date d1 = hai.getEndTime();
								Date d2 = hai.getStartTime();
								long time = d1.getTime() - d2.getTime();
								time = time / 1000;
								int s = (int) (time % 60);
								int m = (int) (time / 60 % 60);
								int h = (int) (time / 3600 % 24);
								int d = (int) (time / 86400);
								String str = d + " 天  " + h + " 小时 " + m + " 分 " + s + " 秒 ";
								map.put("time", str);
							}
							items.add(map);
						}
						ret_map.put("success", true);
						ret_map.put("rows", items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret_map;
	}

	@Override
	public String todoExcel(HttpServletResponse response,
			HttpServletRequest request, String isPage, Pager pager,
			String todoDocumentTitle, String taskName, String taskSender,
			String startTime, String endTime) {
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pager.setPageSize(30);
			pager.setPageNo(1);
		}else{
			pager.setPageSize(Integer.parseInt(pageSize));
			pager.setPageNo(Integer.parseInt(pageNo));
		}
		if("false".equals(isPage)){//导出全部
			int todoCount = (int)this.getTodoCount();
			pager.setPageSize(todoCount);
			pager.setPageNo(1);
		}
		WritableSheet sheet = null;
		try{
			  WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
			  if(workbook != null){
					sheet = workbook.createSheet("sheet1", 0);												
	 				sheet.addCell(new Label(0,0,"业务流水号"));
					sheet.addCell(new Label(1,0,"文件标题"));
					sheet.addCell(new Label(2,0,"任务名称"));
					sheet.addCell(new Label(3,0,"开始时间"));
					sheet.addCell(new Label(4,0,"时限"));
					sheet.addCell(new Label(5,0,"发送人"));
				}
	  			Map<String, Object> map = this.todoList(todoDocumentTitle, taskName, taskSender, startTime, endTime, pager);
	  			@SuppressWarnings("unchecked")
				List<Map<String, Object>>  todoList = (List<Map<String, Object>>) map.get("rows");
	  			for(int i = 0; i < todoList.size(); i++){
					int row = i+1;
					Map<String,Object> todoMap= todoList.get(i);
					String declaresn = (String)todoMap.get("declaresn");
					String documentTitle = (String)todoMap.get("documentTitle");
					String taskname = (String)todoMap.get("taskName");
					String taskCreateTime = (String)todoMap.get("taskCreateTime");
					String taskDuration = (String)todoMap.get("taskDuration");
					String tasksender = (String)todoMap.get("taskSender");
					
					sheet.addCell(new Label(0,row,declaresn));
					sheet.addCell(new Label(1,row,documentTitle));
					sheet.addCell(new Label(2,row,taskname));
					sheet.addCell(new Label(3,row,taskCreateTime));
					sheet.addCell(new Label(4,row,taskDuration));		
					sheet.addCell(new Label(5,row,tasksender));	
				}
				response.reset();
				response.setHeader("Content-disposition","attachment; filename=" + new String("待办件信息列表.xls".getBytes("GBK"),"iso-8859-1"));  
				response.setContentType("application/vnd.ms-excel;charset=GBK");
				workbook.write();
				workbook.close();
		}catch(Exception e){
			 e.printStackTrace();
		}
		return null;
	}

	@Override
	public String doingExcel(HttpServletResponse response,
			HttpServletRequest request, String isPage, Pager pager,
			String doingDocumentTitle, String taskAssignee, String taskName,
			String taskSender, String startTime, String endTime) {
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
			pager.setPageSize(30);
			pager.setPageNo(1);
		}else{
			pager.setPageSize(Integer.parseInt(pageSize));
			pager.setPageNo(Integer.parseInt(pageNo));
		}
		if("false".equals(isPage)){//导出全部
			int todoCount = (int)this.getTodoCount();
			pager.setPageSize(todoCount);
			pager.setPageNo(1);
		}
		WritableSheet sheet = null;
		try{
			  WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
			  if(workbook != null){
					sheet = workbook.createSheet("sheet1", 0);												
	 				sheet.addCell(new Label(0,0,"业务流水号"));
					sheet.addCell(new Label(1,0,"文件标题"));
					sheet.addCell(new Label(2,0,"任务名称"));
					sheet.addCell(new Label(3,0,"开始时间"));
					sheet.addCell(new Label(4,0,"到期时间"));
					sheet.addCell(new Label(5,0,"发送人"));
					sheet.addCell(new Label(6,0,"当前办理人"));
				}
	  			Map<String, Object> map = this.doingList(doingDocumentTitle, taskName, taskSender, taskAssignee, startTime, endTime, pager);
	  			@SuppressWarnings("unchecked")
				List<Map<String, Object>>  doingList = (List<Map<String, Object>>) map.get("rows");
	  			for(int i = 0; i < doingList.size(); i++){
					int row = i+1;
					Map<String,Object> doingMap= doingList.get(i);
					String declaresn = (String)doingMap.get("declaresn");
					String documentTitle = (String)doingMap.get("documentTitle");
					String taskname = (String)doingMap.get("taskName");
					String taskCreateTime = (String)doingMap.get("taskCreateTime");
					String taskDueDate = (String)doingMap.get("taskDueDate");
					String tasksender = (String)doingMap.get("taskSender");
					String taskassignee = (String)doingMap.get("taskAssignee");
					
					sheet.addCell(new Label(0,row,declaresn));
					sheet.addCell(new Label(1,row,documentTitle));
					sheet.addCell(new Label(2,row,taskname));
					sheet.addCell(new Label(3,row,taskCreateTime));
					sheet.addCell(new Label(4,row,taskDueDate));		
					sheet.addCell(new Label(5,row,tasksender));	
					sheet.addCell(new Label(6,row,taskassignee));	
				}
				response.reset();
				response.setHeader("Content-disposition","attachment; filename=" + new String("在办件信息列表.xls".getBytes("GBK"),"iso-8859-1"));  
				response.setContentType("application/vnd.ms-excel;charset=GBK");
				workbook.write();
				workbook.close();
		}catch(Exception e){
			 e.printStackTrace();
		}
		return null;
	}

	

}
