package net.risesoft.approve.service.impl.workflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.business.engine.DataEngine;
import net.business.engine.Template;
import net.risesoft.api.org.GroupManager;
import net.risesoft.api.org.PositionManager;
import net.risesoft.approve.entity.jpa.MatterAuditRole;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.repository.jpa.MatterAuditRoleRepository;
import net.risesoft.approve.service.MatterAuditRoleService;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.OnlineReceiveService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.gdbs.IShouLiService;
import net.risesoft.approve.service.supervise.BujiaoGaozhiService;
import net.risesoft.approve.service.supervise.FTSuperviseService;
import net.risesoft.approve.service.workflow.SpDocumentService;
import net.risesoft.approve.service.workflow.SpWorkflowProcessInstanceService;
import net.risesoft.common.util.BpmUtil;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.common.util.ListUtil;
import net.risesoft.common.util.MyUtil;
import net.risesoft.common.util.RiseBpmUtil;
import net.risesoft.fileflow.activiti.handler.CommonOpt;
import net.risesoft.fileflow.entity.jpa.FormDef;
import net.risesoft.fileflow.entity.jpa.ObjectPermission;
import net.risesoft.fileflow.entity.jpa.ProcessData;
import net.risesoft.fileflow.entity.jpa.ReminderDefine;
import net.risesoft.fileflow.service.EformTaskBindService;
import net.risesoft.fileflow.service.EntrustService;
import net.risesoft.fileflow.service.FileDocumentService;
import net.risesoft.fileflow.service.FormDefService;
import net.risesoft.fileflow.service.FormTaskBindService;
import net.risesoft.fileflow.service.ObjectPermissionService;
import net.risesoft.fileflow.service.OpinionNewService;
import net.risesoft.fileflow.service.ProcessDataService;
import net.risesoft.fileflow.service.ReminderDefineService;
import net.risesoft.fileflow.service.RoleService;
import net.risesoft.fileflow.service.SpActivitiOptService;
import net.risesoft.fileflow.service.TaskConfService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.model.Group;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.model.Position;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.SysConstant;
import net.risesoft.util.SysVariables;
import net.risesoft.utilx.FTSuperviseUtil;
import net.risesoft.utilx.SpButtonUtil;
import net.risesoft.utilx.SpNewButtonUtil;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.common.base.Strings;

@Service(value = "spDocumentService")
public class SpDocumentServiceImpl implements SpDocumentService {

	private static final Logger log = LoggerFactory.getLogger(SpDocumentServiceImpl.class);

	@Autowired
	private WorkflowTaskService workflowTaskService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private FormDefService formDefService;

	@Autowired
	private FormTaskBindService formTaskBindService;

	@Autowired
	private EformTaskBindService eformTaskBindService;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Autowired
	private SpWorkflowProcessInstanceService spWorkflowProcessInstanceService;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private OnlineReceiveService onlineReceiveService;

	@Autowired
	private TaskService taskService;


	@Autowired
	private ProcessDataService processDataService;

	@Autowired
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;

	@Autowired
	private FTSuperviseService fTSuperviseService;

	@Autowired
	private EntrustService entrustService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private ReminderDefineService reminderDefineService;

	@Autowired
	private TaskConfService taskConfService;

	@Autowired
	private SpActivitiOptService spActivitiOptService;
	
	@Autowired
	private SpmApproveItemService spmApproveItemService;
	
	@Autowired
	private OpinionNewService opinionService;
	
	@Autowired
	private FileDocumentService fileDocumentService;
	
	@Autowired
	private MatterAuditRoleService matterAuditRoleService;
	
	@Autowired
	private BujiaoGaozhiService bujiaoGaozhiService;
	
	@Autowired
	private IShouLiService shouliService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ObjectPermissionService objectPermissionService;
	
	@Autowired
	protected RuntimeService runtimeService;
	
	@Autowired
	private MatterAuditRoleRepository matterAuditRoleRepository;

	@Value("${risebpm7.shouFaYuan}")
	private String shouFaYuan;
	
	@Value("${risebpm7.piZhunRole}")
	private String piZhunRole;
	
	@Value("${risebpm7.gaizhangJz}")
	private String gaizhangJz;

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	@Override
	public Map<String, Object> startProcess(String processDefinitionKey, String processSerialNumber, String activitiUser, String documentTitle, String SPinstanceId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 修改网上申报表的状态
			if (StringUtils.isNotBlank(status)) {
				onlineReceiveService.updateStatus(status, SPinstanceId);
			}
			
			OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(SPinstanceId);
			Task task = spActivitiOptService.startProcess(processSerialNumber, documentTitle, processDefinitionKey, activitiUser, officeSpiDeclareinfo.getApproveitemguid(), officeSpiDeclareinfo.getDeclaresn(), SPinstanceId);
			String processInstanceId = task.getProcessInstanceId();
			String taskId = task.getId();
			// 如果用户在启动流程之前先保存了意见，这是意见数据表中之前保存的数据的taskId和processInstanceId仍为空，此时需要根据processSerialNumber查询数据并给taskId和processInstanceId赋值
			opinionService.update(processSerialNumber, processInstanceId, taskId);
			fileDocumentService.update(processSerialNumber, processInstanceId, taskId);
			
			// 审批业务数据和工作流流程相关联
			officeSpiDeclareinfoService.updateOfficeSpiDeclareinfo(SPinstanceId, processInstanceId);
			map.put("processInstanceId", processInstanceId);
			map.put("taskId", taskId);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> complete(String taskId, String processInstanceId,  String processDefinitionId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Person person = ThreadLocalHolder.getPerson();
			String userIds = person.getID();
			String userAndDeptId = BpmUtil.genActivitiUser(person.getID(), person.getParentID());
			String documentTitle = Strings.nullToEmpty(((String) request.getParameter(SysVariables.DOCUMENTTITLE)));
			List<String> routeToTaskList = workflowProcessInstanceService.getCertainTaskTargetsId(taskId, SysVariables.ENDEVENT);
			String routeToTaskId = "";
			if (routeToTaskList.size() > 0) {
				routeToTaskId = routeToTaskList.get(0);
			}
			if (StringUtils.isNotBlank(routeToTaskId)) {
				Task task = workflowTaskService.getTaskByTaskId(taskId);
				taskId = task.getId();
				identityService.setAuthenticatedUserId(userIds);
				Map<String, Object> variables = CommonOpt.setVariables(userAndDeptId, person.getName(), routeToTaskId, RiseBpmUtil.stringToList(userAndDeptId, ","), documentTitle, "");
				variables.put("user4Complete", person.getName());
				variables.put("actionName4SpecialComplete", "办结");
				
				/**
				 * 保存办结过程数据
				 */
				fTSuperviseService.saveBanjie(processDefinitionId, taskId, processInstanceId);
				
				taskService.complete(taskId, variables, false);

				// 修改网上申报表的状态
				//onlineReceiveService.updateStatus("已办结", officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId));
				
				map.put("success", true);
				map.put("msg", "办结成功!");

			} else {
				map.put("success", false);
				map.put("msg", "当前节点不能办结");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "办结失败!");
		}
		return map;
	}

	@Override
	public Map<String, Object> forwarding(String itembox,String sponsorGuid,String taskId, String userChoice,String routeToTaskId, String formIds, HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> userAndDeptIdList = new ArrayList<String>();
		try {
			Person person = ThreadLocalHolder.getPerson();
			String personId = person.getID();
			Task task = workflowTaskService.getTaskByTaskId(taskId);
			String documentTitle = Strings.nullToEmpty((String) taskService.getVariable(taskId, SysVariables.DOCUMENTTITLE));
			String processDefinitionId = task.getProcessDefinitionId();
			String processInstanceId = task.getProcessInstanceId();
			// 如果不是call activity
			String activityType = workflowProcessDefinitionService.getActivityProperty(task.getProcessDefinitionId(), routeToTaskId, SysVariables.TYPE);
			if(sponsorGuid!=null&&sponsorGuid!=""&&("shouFaYuan").equals(sponsorGuid)){//说明是有收发员角色，userChoice为部门
				List<Person> personList = RisesoftUtil.getRoleManager().getPersonsByID("", shouFaYuan);
				String[] userChoices = userChoice.split(SysVariables.COLON);
				for(int i = 0;i<personList.size();i++){
					OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), personList.get(i).getID());
					if(org.getID().equals(userChoices[1])){
						String userAndDeptIds = personList.get(i).getID() + ":" + org.getID();
						userAndDeptIdList.add(userAndDeptIds);
					}
				}
			}else if("wenmishenheUser".equals(userChoice)){ //文秘审核
				//List<Map<String, Object>> UserList =  roleService.findPermUser(processDefinitionId, routeToTaskId, 3, true, null);
				//获取角色组
				List<ObjectPermission> UserList = objectPermissionService.findByObjectGuidAndTenantId(processDefinitionId,routeToTaskId);
				for (ObjectPermission o : UserList) {
					if (o.getPrincipalType() == 1) {
						List<Person> persons = RisesoftUtil.getRoleManager().getPersonsByID(ThreadLocalHolder.getTenantId(), o.getPrincipalGuid());
						for (OrgUnit p : persons) {
							OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), p.getID());
							String userAndDeptIds = p.getID() + ":" + org.getID();  //用户ID:部门ID
							userAndDeptIdList.add(userAndDeptIds);
					}
				  }
				}
			}else if("gaizhangUser".equals(userChoice)){ //盖章用户组
				
				//判断是部门章还是局章
				String APPROVEITEMGUID = Strings.nullToEmpty((String) taskService.getVariable(taskId, SysVariables.APPROVEITEMGUID));
				SpmApproveitem spmApproveitem = spmApproveItemService.findByApproveitemguid(APPROVEITEMGUID);//根据事项id查询事项
				String sealtype = spmApproveitem.getSealType();   //2 盖部门章，1盖局章
				if(sealtype!=null&&"2".equals(sealtype)){  //如果是部门章
				List<ObjectPermission> UserList = objectPermissionService.findByObjectGuidAndTenantId(processDefinitionId,routeToTaskId);
				for (ObjectPermission o : UserList) {
					if (o.getPrincipalType() == 1) {
						List<Person> persons = RisesoftUtil.getRoleManager().getPersonsByID(ThreadLocalHolder.getTenantId(), o.getPrincipalGuid());
						for (OrgUnit p : persons) {
							OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), p.getID());
								String dapartid = spmApproveitem.getBureauguid();
								if(dapartid.equals(org.getID())){
									String userAndDeptIds = p.getID() + ":" + org.getID();
									userAndDeptIdList.add(userAndDeptIds);	
								}
					    }}
					}	
				  }else if(sealtype!=null&&"1".equals(sealtype)){  //如果是局章
					  List<Person> personList = RisesoftUtil.getRoleManager().getPersonsByID("", gaizhangJz);  //根据局章角色ID获取该角色下的用户组
						for(int i = 0;i<personList.size();i++){
							OrgUnit org = RisesoftUtil.getPersonManager().getParent(ThreadLocalHolder.getTenantId(), personList.get(i).getID());
							if(!personList.get(i).getID().equals("")){
								String userAndDeptIds = personList.get(i).getID() + ":" + org.getID();
								userAndDeptIdList.add(userAndDeptIds);
							}
						}
			   }
			}
			else{
				 
				
				userAndDeptIdList.addAll(this.parseUserChoice(userChoice, task.getProcessDefinitionId(), task.getTaskDefinitionKey(), task.getProcessInstanceId(), task.getId()));// 解析前台传过来的用户选取的人员
			}
			// 查看发送人（userAndDeptIdList）中是否有设置出差委托,如果有则替换，没有则不做处理
			userAndDeptIdList = entrustService.findUserIdList(userAndDeptIdList, processDefinitionId, routeToTaskId);
			// 得到要发送节点的multiInstance，PARALLEL表示并行，SEQUENTIAL表示串行
			String multiInstance = workflowProcessDefinitionService.getMultiinstanceType(task.getProcessDefinitionId(), routeToTaskId);

			Map<String, Object> variables = CommonOpt.setVariables(BpmUtil.genActivitiUser(personId, person.getParentID()), person.getName(), routeToTaskId, userAndDeptIdList, documentTitle, multiInstance);
			//判断当前环节是否为承办
			if("chengban".equals(task.getTaskDefinitionKey())){
				variables.put(SysVariables.CHENGBANRENYUAN, person.getID()+":"+person.getParentID());
			}
			/*
			 * 当类型是CALLACTIVITY时，说明通过call
			 * Activiti节点调用子流程，当子流程启动的时候，需要设置子流程中的processSerialNumber
			 * ，因此需要一些初始化参数，这里设置这些初始化参数，在监听器MyEventListener中根据起草人或其它条件 设置到子流程中
			 * 
			 * 另外，目前通过call Activiti节点调用子流程只适用于单人启动的情况，不使用于流程先启动，然后抢占式办理的情况
			 */
			if (SysVariables.CALLACTIVITY.equals(activityType)) {
				List<Map<String, Object>> initDataList = new ArrayList<Map<String, Object>>();
				String subProcessSerialNumbers = "";// 用来存储子流程的ProcessSerialNumber，后面查询意见等会用到
				for (int i = 0; i < userAndDeptIdList.size(); i++) {
					Map<String, Object> tempMap = new HashMap<String, Object>();
					tempMap.put(SysVariables.ACTIVITIUSER, userAndDeptIdList.get(i));
					String tempProcessSerialNumber = GuidUtil.genGuid();
					tempMap.put(SysVariables.PROCESSSERIALNUMBER, tempProcessSerialNumber);
					tempMap.put(SysVariables.PARENTFORMIDS, formIds);// 设置当前流程的表单Id，将其传给子流程
					tempMap.put(SysVariables.PARENTPROCESSDEFINITIONID, task.getProcessDefinitionId());
					tempMap.put(SysVariables.PARENTPROCESSSERIALNUMBER, taskService.getVariable(taskId, SysVariables.PROCESSSERIALNUMBER));
					subProcessSerialNumbers = MyUtil.genCustomStr(subProcessSerialNumbers, tempProcessSerialNumber);// 用来存储子流程的ProcessSerialNumber，后面查询意见等会用到
					initDataList.add(tempMap);
				}
				variables.put(SysVariables.INITDATALIST, initDataList);

				variables.put(SysVariables.SUBPROCESSSERIALNUMBERS, subProcessSerialNumbers);
				List<Map<String, Object>> parentFlowData = new ArrayList<Map<String, Object>>();// 存储父子流程中父流程的信息
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put(SysVariables.PARENTFORMIDS, formIds);// 设置当前流程的表单Id，将其传给子流程
				tempMap.put(SysVariables.PARENTPROCESSDEFINITIONID, task.getProcessDefinitionId());// 设置当前流程的processDefinitionId，将其传给子流程
				tempMap.put(SysVariables.PARENTTENANTID, person.getTenantID());// 设置当前流程的tenantId，将其传给子流程
				tempMap.put(SysVariables.PARENTPROCESSINSTANCEID, task.getProcessInstanceId());
				parentFlowData.add(tempMap);
				variables.put(SysVariables.PARENTFLOWDATA, parentFlowData);// 设置父子流程中父流程信息
			}
			// 并行的时候增加参数parallelSponsor，值为主办人员guid
			if (SysVariables.PARALLEL.equals(multiInstance)) {
				if (userAndDeptIdList.size() > 0) {
					for(int i = 0;i<userAndDeptIdList.size();i++){
						if(userAndDeptIdList.get(i).contains(sponsorGuid)){
							variables.put(SysVariables.PARALLELSPONSOR, userAndDeptIdList.get(i));// 第一个人给PARALLELSPONSOR，作为主办人
							break;
						}
					}
				}
			}
			
			/**
			 * 如果发起了补齐补正告知，插入补齐补正受理数据 
			 */
			if(SysVariables.bqbz.equals(itembox)){//补齐补正件受理
				OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByProcessInstanceId(processInstanceId);
				String isbqbzsl = bujiaoGaozhiService.isBuqibuzhengShouli(officeSpiDeclareinfo.getGuid());
				/*if(!"1".equals(isbqbzsl)){
					bujiaoGaozhiService.saveBuqibuzhengshouli(officeSpiDeclareinfo.getGuid());
				}*/
			}
			
			//保存过程数据
			this.saveGuoChengShuJu(taskId);
			
			//判断是否是主办办理，如果是，需要将协办未办理的的任务默认办理
			String zhuBan = request.getParameter("zhuBan");
			if(("true").equals(zhuBan)){
				List<Task> taskNextList1 =  taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
				for (Task taskNext : taskNextList1) {
					if(task.getId().equals(taskNext.getId())){
						taskService.complete(task.getId(), variables, false);//办结本次任务
					}else{
						taskService.complete(taskNext.getId(), null, false);//办结协办人任务
					}
				}
			}else{
				taskService.complete(task.getId(), variables, false);//办结本次任务
			}
			
			List<Task> taskNextList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
			// 设置下个任务节点任务实例的时限
			for (Task taskNext : taskNextList) {
				/**
				 * 并行状态下，如果受让人是主办人，则将主办人guid设为流程变量
				 */
				if (SysVariables.PARALLEL.equals(multiInstance)) {
					if(taskNext.getAssignee().split(SysVariables.COLON)[0].equals(sponsorGuid)){
						taskService.setVariableLocal(taskNext.getId(), SysVariables.ZHUBAN, sponsorGuid);
					}
				}
				
				HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).orderByTaskCreateTime().asc().taskDefinitionKey(task.getTaskDefinitionKey()).list().get(0);
				Date date = historicTaskInstance.getDueDate();
				if (null != date) {
					taskService.setDueDate(taskNext.getId(), date);
				} else {
					ReminderDefine rd = reminderDefineService.findByProcessDefinitionIdAndTaskDefKey(taskNext.getProcessDefinitionId(), taskNext.getTaskDefinitionKey());
					if (null != rd) {
						taskService.setDueDate(taskNext.getId(), reminderDefineService.getAdvanceDate(taskNext.getCreateTime(), rd.getDay_Type(), rd.getTaskDuration()));
					}
				}
			}
			map.put("nextTaskId", "");
			map.put("success", true);
			map.put("msg", "发送成功!");
			// 修改受理表状态
			//onlineReceiveService.updateStatus(taskNextList.get(0).getName(), officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId));
			
		} catch (Exception e) {
			log.error("公文发送失败！");
			map.put("success", false);
			map.put("msg", "发送失败!");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<String> parseUserChoice(String userChoice, String processDefinitionId, String taskDefKey, String processInstanceId, String taskId) {
		String userAndDeptIds = "";// 为了解决一人多岗问题，在流程中发送人使用的是“人员Id:部门Id”的形式
		String tenantId = ThreadLocalHolder.getTenantId();
		if (StringUtils.isNotBlank(userChoice)) {
			if ("sendToStartUser".equals(userChoice)) {
				String senderUserId = workflowTaskService.startProUser(processDefinitionId, processInstanceId);
				userAndDeptIds = senderUserId;
			} else if("chengbanrenUser".equals(userChoice)){
				String senderUserId = taskService.getVariable(taskId, SysVariables.CHENGBANRENYUAN).toString();
				userAndDeptIds = senderUserId;
			}
			else {
				List<String> hiddenUsers = new ArrayList<String>();
				String[] userChoices = userChoice.split(SysVariables.SEMICOLON);
				for (String s : userChoices) {
					String[] s2 = s.split(SysVariables.COLON);
					Integer principalType = Integer.parseInt(s2[0]);
					if (principalType == ObjectPermission.PRINCIPALTYPE_USER) {
						Person person = RisesoftUtil.getPersonManager().getPerson(tenantId, s2[1]);
						if (null == person) {
							continue;
						}
						if (!(s2[2].equals("undefined")) && StringUtils.isNotBlank(s2[2])) {
							OrgUnit orgUnit = RisesoftUtil.getOrgUnitManager().getOrgUnit( s2[2]);
							if (orgUnit.getOrgType().equals("Department") || orgUnit.getOrgType().equals("Organization")) {
								userAndDeptIds = this.addUserAndDeptIds(userAndDeptIds, s2[1], s2[2]);
							} else {
								userAndDeptIds = this.addUserAndDeptIds(userAndDeptIds, s2[1], orgUnit.getParentID());
							}
						} else {
							userAndDeptIds = this.addUserAndDeptIds(userAndDeptIds, s2[1], person.getParentID());
						}
					} else if (principalType == ObjectPermission.PRINCIPALTYPE_DEPARTMENT) {// 选取的是部门，获取部门下的所有人员
						boolean isSelDept = taskConfService.isSelectedDept(processDefinitionId, taskDefKey);// 查询是否只显示到部门
						if (!isSelDept) {
							List<Person> employeeList = RisesoftUtil.getDepartmentManager().getAllPersons( s2[1]);// risePersonService.findByParentID(s2[1]);
							for (Person entity : employeeList) {
								if (!hiddenUsers.contains(entity.getID())) {
									userAndDeptIds = this.addUserAndDeptIds(userAndDeptIds, entity.getID(), entity.getParentID());
								}
							}
						} else {// 只显示到部门(暂时没有)
							/*
							 * List<ObjectPermission> personPermList =
							 * objectPermissionService
							 * .getPerm(processDefinitionId, taskDefKey,
							 * ObjectPermission.PRINCIPALTYPE_USER,
							 * ObjectPermission.PERMISSION_READWRITE, s2[1]); if
							 * (personPermList != null && personPermList.size()
							 * > 0) { for (int i = 0; i < personPermList.size();
							 * i++) { ObjectPermission temp =
							 * personPermList.get(i); if (temp != null) {
							 * userAndDeptIds =
							 * addUserAndDeptIds(userAndDeptIds,
							 * temp.getPrincipalGuid(),
							 * temp.getUser_deptGuid()); } } }
							 */
						}

					} else if (principalType == ObjectPermission.PRINCIPALTYPE_GROUP) {// 用户组
						GroupManager groupManager = RisesoftUtil.getGroupManager();
						Group group = groupManager.getGroup( s2[2]);
						List<Person> list = groupManager.getPersons(s2[2]);
						for (Person entity : list) {
							userAndDeptIds = this.addUserAndDeptIds(userAndDeptIds, entity.getID(), group.getParentID());
						}
					} else if (principalType == ObjectPermission.PRINCIPALTYPE_POSITION) {// 岗位
						PositionManager positionManager = RisesoftUtil.getPositionManager();
						Position position = positionManager.getPosition(s2[2]);
						List<Person> list = RisesoftUtil.getPositionManager().getPersons(s2[2]);
						for (Person entity : list) {
							userAndDeptIds = this.addUserAndDeptIds(userAndDeptIds, entity.getID(), position.getParentID());
						}
					}
				}
			}
		}
		List<String> result = RiseBpmUtil.stringToList(userAndDeptIds, SysVariables.SEMICOLON);
		ListUtil.removeDuplicateWithOrder(result);
		return result;
	}

	@Override
	public String addUserAndDeptIds(String userAndDeptIds, String userId, String deptId) {
		if (StringUtils.isNotBlank(userAndDeptIds)) {// 由于串行、并行的时候人员存在顺序的，所以写在这里，保证人员顺序
			if (!userAndDeptIds.contains(userId + SysVariables.COLON + deptId)) {
				userAndDeptIds = userAndDeptIds + SysVariables.SEMICOLON + BpmUtil.genActivitiUser(userId, deptId);
			}
		} else {
			userAndDeptIds = BpmUtil.genActivitiUser(userId, deptId);
		}
		return userAndDeptIds;
	}

	@Override
	public Model genDocumentModel(String itembox, String processSerialNumber, String tenantId, String processDefinitionKey, String processDefinitionId, String processInstanceId, String taskId, String taskDefinitionKey, String activitiUser, Model model) {
		model.addAttribute("itembox", itembox);
		model.addAttribute(SysVariables.PROCESSSERIALNUMBER, processSerialNumber);
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("processDefinitionKey", processDefinitionKey);
		if (StringUtils.isBlank(processDefinitionId)) {// 新建流程的时候还不知道流程定义Id
			processDefinitionId = workflowProcessDefinitionService.getLatestProcessDefinitionId(processDefinitionKey);
		}
		model.addAttribute("processDefinitionId", processDefinitionId);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("taskDefKey", taskDefinitionKey);
		model.addAttribute("taskId", taskId);
		if(processInstanceId!=""&&processInstanceId!=null){
			//审批业务数据主键id，用于出证办结
			model.addAttribute("SPinstanceId", officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId));
			//审批事项id
			String approveitemguid = officeSpiDeclareinfoService.findApproveitemguidByProcessInstanceId(processInstanceId);
			if(approveitemguid!=null){
				SpmApproveitem spmApproveItem = spmApproveItemService.findByApproveitemguid(approveitemguid);
				//证件类型guid，用于出证办结
				model.addAttribute("doctypeguid", spmApproveItem.getDoctypeguid());
			}
		}
		
		List<String> formBindData = formTaskBindService.getFormBindDatas(processDefinitionId, taskDefinitionKey);

		String formIds = "";
		String formNames = "";
		String formUrls = "";
		String tableNames = "";
		String showOtherFlag = "";
		String formEdittypes = "";
		if (formBindData.size() > 0) {
			formIds = formBindData.get(0);
			List<String> formIdList = RiseBpmUtil.stringToList(formIds, SysVariables.COMMA);
			List<FormDef> formDefList = formDefService.findByFormIdList(formIdList);
			formNames = formDefService.findFormName(formIdList, formDefList);
			tableNames = formDefService.findTableName(formIdList, formDefList);
			showOtherFlag = formBindData.get(1);
		}

		model.addAttribute("formIds", formIds);
		model.addAttribute("formNames", formNames);
		model.addAttribute("tableNames", tableNames);
		model.addAttribute("showOtherFlag", showOtherFlag);
		model.addAttribute("formUrls", formUrls);
		model.addAttribute("formEdittypes", formEdittypes);
		model.addAttribute(SysVariables.ACTIVITIUSER, activitiUser);
		return model;
	}

	/**
	 * 新菜单控制
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @param taskId
	 * @param model
	 * @param itembox
	 * @param instanceId
	 * @return
	 */
	@Override
	public Model newMenuControl(String processDefinitionId, String taskDefKey, String taskId, Model model, String itembox,String instanceId) {
		Task task = workflowTaskService.getTaskByTaskId(taskId);
		//String instanceId=officeSpiDeclareinfoService.getGuidByProcessInstanceId(task.getProcessInstanceId());//审批主表ID
		String approveitemguid = officeSpiDeclareinfoService.findByGuid(instanceId).getApproveitemguid();
		SpNewButtonUtil buttonUtil = new SpNewButtonUtil();
		Map<String, Object> map = buttonUtil.showButton(taskId, itembox,instanceId);
		String[] buttonIds = (String[]) map.get("buttonIds");
		String[] buttonNames = (String[]) map.get("buttonNames");
		String[] buttonIconCls = (String[]) map.get("buttonIconCls");
		int[] buttonOrders = (int[]) map.get("buttonOrders");
		boolean[] isButtonShow = (boolean[]) map.get("isButtonShow");
		if(!taskDefKey.equals("kaishishouli")&&!taskDefKey.equals("chengban")){//当前操作若不是“kaishishouli”
			isButtonShow[15] = false;//屏蔽“补齐补正”按钮
			isButtonShow[24] = false;//屏蔽“补交告知说明”按钮
		}
		String button = "";
		boolean isPiZhunRole = false;
		Map<String, Object> vars = new HashMap<String, Object>();
		if (task != null) {
			vars = taskService.getVariables(task.getId());// 获取流程中当前任务的所有变量
		}
		//判断是否进行补齐补正受理
		int bqbzStatus = (Integer) vars.get(SysVariables.BUQIBUZHENG) == null ? 0 : (Integer) vars.get(SysVariables.BUQIBUZHENG);
			if(bqbzStatus==2){
			String res = bujiaoGaozhiService.isBuqibuzhengShouli(instanceId);
			model.addAttribute("bqbzStatus", res);//未进行补齐补正受理
			
		}
		/**
		 * 查找审核批准角色的人员，判断当前人员是否是审核批准人员，控制批准、不批准按钮的显示
		 */
		/*List<Person> personList = RisesoftUtil.getRoleManager().getPersonsByID(ThreadLocalHolder.getTenantId(), piZhunRole);
		if(personList!=null){
			for(int i = 0;i<personList.size();i++){
				if(personList.get(i).getID().equals(ThreadLocalHolder.getPerson().getID())){
					isPiZhunRole = true;
					break;
				}
			}
		}*/
		MatterAuditRole matterAuditRole = matterAuditRoleRepository.findByApproveItemGuidAndPersonGuid(approveitemguid, ThreadLocalHolder.getPerson().getID());
		if(matterAuditRole!=null){
			isPiZhunRole = true;
		}
		
		// 生成按钮数组
		List<Map<String, String>> routeToTasks = spWorkflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId, taskDefKey);
		
		if (isButtonShow[1] == false  && itembox.equals("todo")) {// 控制发送按钮的显示
		/*	for (Map<String, String> m : routeToTasks) {
				if ((vars.get(SysVariables.TEBIECHENGXU) != null && (Integer) vars.get(SysVariables.TEBIECHENGXU) == 1)) {
					isButtonShow[1] = false;// 显示“特别程序处理”按钮时，不显示发送按钮。
				} else {
					isButtonShow[1] = true;
				}
			}*/
			isButtonShow[1] = true;
		}
		if(isButtonShow[20]||isButtonShow[22]||isButtonShow[18]){//在暂停件显示“特别程序审核”，“补齐补正受理”，“特别程序结果”按钮时，显示“发送”按钮
			isButtonShow[1] = true;//发送
		}
		String routeToTasksStr = "";
		String danweineibanli = "";
		for (Map<String, String> m : routeToTasks) {
			/**
			 * 当显示“办理完成”[8]按钮时，说明是并行的协办人，串行的中间人，不显示其他任务。
			 * 当显示“签收”[9]按钮时，不显示路由任务
			 * 当显示“送下一人”[7]按钮时，说明是串行的中间人，不显示路由任务
			 * 在暂停件显示“特别程序审核”[20]按钮时，不显示路由任务 按钮
			 * 在暂停件显示“特别程序结果”[22]按钮时，不显示路由任务
			 * 在暂停件显示“补齐补正受理”[18]按钮时，不显示路由任务
			 */
			if(!isButtonShow[8]&&isButtonShow[9] == false&&isButtonShow[7] == false&&!isButtonShow[20]&&!isButtonShow[22]&&!isButtonShow[18]){
				if(m.get("taskDefName").contains("并行办理")||m.get("taskDefName").contains("串行办理")){
					danweineibanli = "<div data-options=\"iconCls:'icon-redo'\" name=\"easyuiLinkButton\" id=\"danweineibanli\">单位内办理</div>";
				}else if(m.get("taskDefName").contains("批准并送窗口")){
					if(isPiZhunRole){
						routeToTasksStr += "<div data-options=\"iconCls:'icon-redo'\" name=\"easyuiLinkButton\" id=\"" + m.get("taskDefKey") + "\">" + m.get("taskDefName") + "</div>";
					}
				}else{
					routeToTasksStr += "<div data-options=\"iconCls:'icon-redo'\" name=\"easyuiLinkButton\" id=\"" + m.get("taskDefKey") + "\">" + m.get("taskDefName") + "</div>";
				}
			}
		}
		routeToTasksStr = danweineibanli + routeToTasksStr;
		for (int i = 0; i < buttonOrders.length; i++) {
			int k = buttonOrders[i] - 1;
			if (isButtonShow[k]&&k!=0&&k!=2&&k!=1) {//保存k=0，返回k=1,不放在发送里面
				if(!isButtonShow[8]){//当“办理完成”按钮不显示时时,其他“补齐补正”，“特别程序申请”等配置按钮才显示
					routeToTasksStr += "<div data-options=\"iconCls:'icon-redo'\" name=\"easyuiLinkButton\" id=\"" + buttonIds[k] + "\">" + buttonNames[k] + "</div>";
				}
			}else if (isButtonShow[k]&&(k==0||k==2)) {//保存，返回以button形式显示
				button = button + "<td><a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" name=\"easyuiLinkButton\" data-options=\"iconCls:\'" + buttonIconCls[k] + "\',plain:true\" id=\"button" + buttonIds[k] + "\" >" + buttonNames[k] + "</a></td>";
			}
		}
		if(isButtonShow[8]){//当显示“办理完成”按钮时,其他“补齐补正”，“特别程序申请”等配置按钮不显示
			routeToTasksStr += "<div data-options=\"iconCls:'icon-redo'\" name=\"easyuiLinkButton\" id=\"" + buttonIds[8] + "\">" + buttonNames[8] + "</div>";
		}
		if (isButtonShow[1]) {
			button = button + "<td><a href=\"javascript:void(0);\" class=\"easyui-menubutton\" name=\"easyuiLinkButton\" data-options=\"menu:'#mm',iconCls:\'" + buttonIconCls[1] + "\',plain:true\" id=\"button" + buttonIds[1] + "\" >" + buttonNames[1] + "</a>" + "<div id=\"mm\" style=\"width:150px;\">" + routeToTasksStr + "</div>  " + "</td>";
		}
		if (isButtonShow[11]) {//办结按钮，打印办结单
			model.addAttribute("print", "banJieDan");
		}
		model.addAttribute("button", button);
		model.addAttribute("xieBan",  map.get("xieBan"));
		model.addAttribute("zhuBan",  map.get("zhuBan"));
		model.addAttribute("refuseClaimTip", map.get("refuseClaimTip"));
		return model;
	}
	
	@Override
	public Model menuControl(String processDefinitionId, String taskDefKey, String taskId, Model model, String itembox,String instanceId) {
		SpButtonUtil buttonUtil = new SpButtonUtil();
		Map<String, Object> map = buttonUtil.showButton(taskId, itembox,instanceId);
		String[] buttonIds = (String[]) map.get("buttonIds");
		String[] buttonNames = (String[]) map.get("buttonNames");
		String[] buttonIconCls = (String[]) map.get("buttonIconCls");
		int[] buttonOrders = (int[]) map.get("buttonOrders");
		boolean[] isButtonShow = (boolean[]) map.get("isButtonShow");
		String button = "";
		Task task = workflowTaskService.getTaskByTaskId(taskId);
		Map<String, Object> vars = new HashMap<String, Object>();
		if (task != null) {
			vars = taskService.getVariables(task.getId());// 获取流程中当前任务的所有变量
		}
		// 生成按钮数组
		for (int i = 0; i < buttonOrders.length; i++) {
			int k = buttonOrders[i] - 1;
			List<Map<String, String>> routeToTasks = spWorkflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId, taskDefKey);
			if (k == 1 && isButtonShow[k] == false && routeToTasks.size() > 0 && itembox.equals("todo")&&isButtonShow[7] ==false) {// 控制发送按钮的显示
				for (Map<String, String> m : routeToTasks) {
					if (m.get("taskDefName").equals("隐藏路由") || (vars.get(SysVariables.TEBIECHENGXU) != null && (Integer) vars.get(SysVariables.TEBIECHENGXU) == 1)) {
						isButtonShow[k] = false;// 显示“特别程序处理”按钮时，不显示发送按钮。
					} else {
						isButtonShow[k] = true;
					}
				}
			}
			if (isButtonShow[k]) {
				if (k == 1) {
					// List<Map<String, String>> routeToTasks =
					// spWorkflowProcessInstanceService.getCurrentTaskTargets(processDefinitionId,taskDefKey);
					String routeToTasksStr = "";
					for (Map<String, String> m : routeToTasks) {
						routeToTasksStr += "<div data-options=\"iconCls:'icon-redo'\" name=\"easyuiLinkButton\" id=\"" + m.get("taskDefKey") + "\">" + m.get("taskDefName") + "</div>";
					}
					button = button + "<td><a href=\"javascript:void(0);\" class=\"easyui-menubutton\" name=\"easyuiLinkButton\" data-options=\"menu:'#mm',iconCls:\'" + buttonIconCls[k] + "\',plain:true\" id=\"button" + buttonIds[k] + "\" >" + buttonNames[k] + "</a>" + "<div id=\"mm\" style=\"width:150px;\">" + routeToTasksStr + "</div>  " + "</td>";
				} else if (k == 12) {
					button = button + "<td><a href=\"javascript:void(0);\" class=\"easyui-menubutton\" name=\"easyuiLinkButton\" data-options=\"menu:'#mm',iconCls:\'" + buttonIconCls[k] + "\',plain:true\" id=\"button" + buttonIds[k] + "\" >" + buttonNames[k] + "</a>" + "<div id=\"mm\" style=\"width:150px;\"><div name=\"easyuiLinkButton\" id=\"sendToStartUser\">起草人</div><div name=\"easyuiLinkButton\" id=\"senderUser\">上一步发送人</div></div>  " + "</td>";
				} else {
					button = button + "<td><a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" name=\"easyuiLinkButton\" data-options=\"iconCls:\'" + buttonIconCls[k] + "\',plain:true\" id=\"button" + buttonIds[k] + "\" >" + buttonNames[k] + "</a></td>";
				}
			}
		}
		model.addAttribute("button", button);
		model.addAttribute("refuseClaimTip", map.get("refuseClaimTip"));
		return model;
	}

	@Override
	public List<List<Object>> genFixedColumns(List<List<Object>> columns, String processInstanceId, Integer tabIndex) {
		columns.add(addGernalData("id", SysVariables.VARCHAR, GuidUtil.genGuid()));
		columns.add(addGernalData("processInstanceId", SysVariables.VARCHAR, processInstanceId));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(SysVariables.DATETIME_PATTERN);
		String strDate = sdf.format(date);
		columns.add(addGernalData("createTime", SysVariables.TIMESTAMP, strDate));
		columns.add(addGernalData("updateTime", SysVariables.TIMESTAMP, strDate));
		columns.add(addGernalData("tabIndex", SysVariables.INTEGER, tabIndex));

		return columns;
	}

	private List<Object> addGernalData(String columnName, String columnType, Object columnValue) {
		List<Object> list = new ArrayList<Object>();
		list.add(columnName);
		list.add(columnType);
		list.add(columnValue);
		return list;
	}

	@Override
	public Model genDocumentModel4Eform(String itembox, String processSerialNumber, String tenantId, String processDefinitionKey, String processDefinitionId, String processInstanceId, String taskId, String taskDefinitionKey, String activitiUser, Model model) {
		model.addAttribute("itembox", itembox);
		model.addAttribute(SysVariables.PROCESSSERIALNUMBER, processSerialNumber);
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("processDefinitionKey", processDefinitionKey);
		if (StringUtils.isBlank(processDefinitionId)) {// 新建流程的时候还不知道流程定义Id
			processDefinitionId = workflowProcessDefinitionService.getLatestProcessDefinitionId(processDefinitionKey);
		}
		model.addAttribute("processDefinitionId", processDefinitionId);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("taskDefKey", taskDefinitionKey);
		model.addAttribute("taskId", taskId);
		if(processInstanceId!=""&&processInstanceId!=null){
			//审批业务数据主键id，用于出证办结
			model.addAttribute("SPinstanceId", officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId));
			//审批事项id
			String approveitemguid = officeSpiDeclareinfoService.findApproveitemguidByProcessInstanceId(processInstanceId);
			if(approveitemguid!=null){
				SpmApproveitem spmApproveItem = spmApproveItemService.findByApproveitemguid(approveitemguid);
				//证件类型guid，用于出证办结
				if(spmApproveItem!=null){
					model.addAttribute("doctypeguid", spmApproveItem.getDoctypeguid());
				}
			}
		}
		// 电子表单
		List<String> eformBindData = eformTaskBindService.getEformBindDatas(processDefinitionId, taskDefinitionKey);
		String formIds = "";
		String formNames = "";
		String formUrls = "";
		String tableNames = "";
		String showOtherFlag = "";
		String formEdittypes = "";
		// 电子表单
		if (eformBindData.size() > 0) {
			formIds = eformBindData.get(0);
			List<String> formIdList = RiseBpmUtil.stringToList(formIds, SysVariables.COMMA);
			for (int i = 0; i < formIdList.size(); i++) {
				List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
				String sql = "select * from templatedefine_sys t where t.TEMP_ID=?";
				listmap = jdbcTemplate.queryForList(sql, formIdList.get(i));
				formNames = RiseBpmUtil.genCustomStr(formNames, listmap.get(0).get("TEMPLATENAME").toString());
				tableNames = RiseBpmUtil.genCustomStr(tableNames, listmap.get(0).get("TEMPLATENAME").toString());
				long num = this.getData(formIdList.get(i), processInstanceId);
				String edittype = "1";
				if (num == 0) {
					edittype = "0";
				}
				formEdittypes = RiseBpmUtil.genCustomStr(formEdittypes, edittype);
			}
			showOtherFlag = eformBindData.get(1);
			formUrls = eformBindData.get(2);
		}
		model.addAttribute("formIds", formIds);
		model.addAttribute("formNames", formNames);
		model.addAttribute("tableNames", tableNames);
		model.addAttribute("showOtherFlag", showOtherFlag);
		model.addAttribute("formUrls", formUrls);
		model.addAttribute("formEdittypes", formEdittypes);
		model.addAttribute(SysVariables.ACTIVITIUSER, activitiUser);
		return model;
	}
	
	@Override
	public long getData( String tempId,  String processInstanceId) {
		List<Map<String, Object>> tablemap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> datamap = new ArrayList<Map<String, Object>>();
		DataEngine de = DataEngine.getInstance();
		Template template = de.getTemplate(Integer.parseInt(tempId));// 根据模板ID获取模板
		String fieldAlias = "";
		if(template.getFieldByName("guid")!=null){
			fieldAlias = template.getFieldByName("guid").getFieldAlias();// 获取模板的guid字段别名
		}
		fieldAlias = fieldAlias.replace(".", ":");
		String tableAlias = fieldAlias.split(":")[0];// 模板绑定的表的别名
		String tableSql = "select * from usertabledefine_sys t where t.alias=?";
		tablemap = jdbcTemplate.queryForList(tableSql, tableAlias);// 获取模板绑定的表
		if(tablemap.size()>0){
			String dataSql = "select * from " + tablemap.get(0).get("DBTABLENAME") + " t where t.guid=?";
			datamap = jdbcTemplate.queryForList(dataSql, processInstanceId);// 根据guid查询绑定的表中是否已有数据，有则将模板模式设为编辑模式(edittype=1)，没有则设为新增模式(edittype=0)
		}
		return datamap.size();
	}
	
	@Override
	public void saveGuoChengShuJu(String taskId) {
		/**
		 * 保存过程数据
		 */
		Task task = workflowTaskService.getTaskByTaskId(taskId);
		String processInstanceId = task.getProcessInstanceId();
		String processDefinitionId = task.getProcessDefinitionId();
		String sblsh = officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(processInstanceId);
		ProcessData processData = processDataService.getOne(processDefinitionId, task.getTaskDefinitionKey());
		if(processData!=null){
			if("22".equals(processData.getStatus())){//插入承办数据
				//获取当前任务（task）的前一个任务（task） 
				HistoricTaskInstance historictask = workflowTaskService.getPreviousTask(taskId);
				fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, SysConstant.ChengBan,"承办");
			}else if("23".equals(processData.getStatus())){//插入审核数据
				fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, SysConstant.ShenHe,"审核");
			}else if("24".equals(processData.getStatus())){//插入批准数据
				fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, SysConstant.PiZhun,"批准");
			}
		}else{
			shouliService.updateShouliStatus("已分发", sblsh);
		}
		//String PiZhun = (String) runtimeService.getVariable(processInstanceId, "PiZhun");//获取该件是否批准，yes为批准，no为不批准，null为未批准
		/*if(processData==null && PiZhun==null){//processData为空，插入申办、受理过程数据。
			//fTSuperviseService.saveShenBan(processDefinitionId, taskId, processInstanceId);
			//fTSuperviseService.saveShouli(processDefinitionId, taskId, processInstanceId, "0");
			//将过程数据状态改为承办“1”
			runtimeService.setVariable(processInstanceId, SysConstant.ProcessData, SysConstant.ChengBan);
		}else if(processData.equals(SysConstant.ChengBan)&&PiZhun==null){//processData为“1”，插入承办数据
			//获取当前任务（task）的前一个任务（task）
			HistoricTaskInstance historictask = workflowTaskService.getPreviousTask(taskId);
			fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, Long.parseLong(SysConstant.ChengBan),historictask!=null?historictask.getName():task.getName());
			//将过程数据状态改为审核“2”
			runtimeService.setVariable(processInstanceId, SysConstant.ProcessData, SysConstant.ShenHe);
		}else if(processData.equals(SysConstant.ShenHe)&&PiZhun==null){//processData为“2”，插入审核数据
			fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, Long.parseLong(SysConstant.ShenHe),"承办");
			//将过程数据状态改为批准“3”
			runtimeService.setVariable(processInstanceId, SysConstant.ProcessData, SysConstant.PiZhun);
		}else if(PiZhun!=null&&!(processData.equals(SysConstant.BanJie))){//processData为“3”，或者PiZhun不为null，插入批准数据
			if(processData.equals(SysConstant.ChengBan)){//补充承办、审核数据
				fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, Long.parseLong(SysConstant.ChengBan),"");
				fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, Long.parseLong(SysConstant.ShenHe),"承办");
			}else if(processData.equals(SysConstant.ShenHe)){//补充审核数据
				fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, Long.parseLong(SysConstant.ShenHe),"承办");
			}
			fTSuperviseService.saveShenpiguocheng(processInstanceId, taskId, Long.parseLong(SysConstant.PiZhun),"审核");
			runtimeService.setVariable(processInstanceId, SysConstant.ProcessData, SysConstant.BanJie);
		}*/
	}
}
