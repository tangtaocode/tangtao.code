package net.risesoft.utilx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







import net.risesoft.approve.service.supervise.BujiaoGaozhiService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.fileflow.entity.jpa.TaskConf;
import net.risesoft.fileflow.service.ProcInstanceRelationshipService;
import net.risesoft.fileflow.service.TaskConfService;
import net.risesoft.fileflow.service.WorkflowHistoryTaskService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.fileflow.service.WorkflowTaskService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.SysVariables;

import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public class SpNewButtonUtil {

	private WorkflowTaskService workflowTaskService;
	private TaskService taskService;
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;
	private TaskConfService taskConfService;
	private WorkflowProcessInstanceService workflowProcessInstanceService;
	private WorkflowHistoryTaskService workflowHistoryTaskService;
	private ProcInstanceRelationshipService procInstanceRelationshipService;
	private BujiaoGaozhiService bujiaoGaozhiService;

	public SpNewButtonUtil() {
		this.bujiaoGaozhiService = ContextUtil.getBean(BujiaoGaozhiService.class);
		this.workflowTaskService = ContextUtil.getBean(WorkflowTaskService.class);
		this.taskService = ContextUtil.getBean(TaskService.class);
		this.workflowProcessDefinitionService = ContextUtil.getBean(WorkflowProcessDefinitionService.class);
		this.taskConfService = ContextUtil.getBean(TaskConfService.class);
		this.workflowProcessInstanceService = ContextUtil.getBean(WorkflowProcessInstanceService.class);
		this.workflowHistoryTaskService = ContextUtil.getBean(WorkflowHistoryTaskService.class);
		this.procInstanceRelationshipService = ContextUtil.getBean(ProcInstanceRelationshipService.class);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public Map<String, Object> showButton(String taskId, String itembox,String instanceId) {
		// 下面几个判断对应的按钮分别是
		// boolean isButton01Show=true;//保存
		// boolean isButton02Show=true;//发送
		// boolean isButton03Show=true;//返回
		// boolean isButton04Show=true;//回退
		// boolean
		// isButton05Show=true;//转办，转办是把任务转给其他人操作，被转办人会在待办任务列表里看到这条任务，正常处理任务后，流程会继续向下运行。
		// boolean
		// isButton06Show=true;//协办，协办是把任务转给其他人操作，被转办人会在待办任务列表里看到这条任务，正常处理任务后，任务会返回给原执行人，流程不会发生变化。
		// boolean isButton07Show=true;//完成，协办状态下的完成按钮
		// boolean
		// isButton08Show=true;//送下一人，串行时如果有多个人，最后一个人显示发送按钮，不显示的是送下一人按钮，前面的人员都是不显示发送按钮，显示的是送下一人按钮
		// boolean isButton09Show=true;//办理完成，并行状态下，当多人并行处理且不是最后一个处理人或不需要人员选取时使用
		// boolean isButton10Show=true;//签收
		// boolean isButton11Show=true;//撤销签收 签收后才可以撤销签收
		// boolean isButton12Show=true;//办结，流程办结按钮，点击后整个流程将结束
		// boolean isButton13Show=true;//直接发送
		// boolean isButton14Show=true;//收回
		// boolean isButton15Show=true;//拒签 在任何用户都
		// 没有签收的情况下，其中一个用户可以拒签，拒签之后就不会出现在当前任务节点的签收用户列表中
		// boolean isButton16Show=true;//补齐补正按钮，用于审批流程，且整个流程只能补齐补正一次
		// boolean isButton17Show=true;//批准按钮 出现的时候，不出现发送按钮
		// boolean isButton18Show=true;//不予批准 出现的时候，不出现发送按钮
		// boolean isButton19Show=true;//补齐补正受理
		// boolean isButton20Show=true;//特别程序申请
		// boolean isButton21Show=true;//特别程序申请处理
		// boolean isButton22Show=true;//特殊办结
		Map<String, Object> map = new HashMap<String, Object>();
		String[] buttonIds = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22","23","24","25"};// 记录按钮的序号，这样方便调整按钮显示顺序
		String[] buttonNames = { "保存", "发送", "返回", "退回", "委托", "协商", "完成", "送下一人", "办理完成", "签收", "撤销签收", "办结", "直接发送", "收回", "拒签", "补齐补正", "批准并送窗口", "不批准并送窗口", "补齐补正受理", "特别程序申请", "特别程序审核", "特殊办结","特别程序结果","转外单位办理","补交告知说明"};
		String[] buttonIconCls = { "icon-save", "icon-redo", "icon-back", "icon-undo", "icon-redo", "icon-redo", "icon-ok", "icon-redo", "icon-ok", "icon-ok", "icon-cancel", "icon-ok", "icon-redo", "icon-redo", "icon-redo", "icon-save", "icon-ok", "icon-cancel", "icon-save", "icon-tip", "icon-tip", "icon-tip","icon-tip","icon-redo","icon-tip"};
		int[] buttonOrders = { 22, 20, 10, 11, 1, 13, 4, 5, 6, 7, 8, 9, 12, 14, 15, 16,25, 19, 17, 18, 2, 21,23,24,3 };// 记录按钮的显示顺序，对应buttonIds中的序号，按钮显示顺序就以此数组顺序显示
		boolean[] isButtonShow = new boolean[buttonIds.length];
		for (int i = 0; i < buttonIds.length; i++) {
			isButtonShow[i] = false;
		}
		Task task = workflowTaskService.getTaskByTaskId(taskId);
		Map<String, Object> vars = new HashMap<String, Object>();
		String vars_user = "";// 对应vars中的user变量
		Integer vars_buQiBuZheng = 0;
		Integer vars_teBieChengXu = 0;
		String vars_sponsorGuid = "";//当前运行的任务的主办人
		String taskSenderId = "";// 当前运行的任务的发送人
		String buQiBuZhengUser = "";//补齐补正的发起人
		boolean isPiZhun = false;// 审批过程中，领导或者相关人员是否已经批准了，如果批准了，则不出现退回按钮
		List<String> vars_users = new ArrayList<String>();
		// 对应vars中的users变量
		// 得到当前节点的multiInstance，PARALLEL表示并行，SEQUENTIAL表示串行
		String multiInstance = "";
		if (task != null) {
			vars = taskService.getVariables(task.getId());// 获取流程中当前任务的所有变量
			vars_users = (List<String>) vars.get(SysVariables.USERS);
			vars_user = (String) vars.get(SysVariables.USER);
			vars_sponsorGuid = (String) vars.get(SysVariables.PARALLELSPONSOR);//当前运行的任务的主办人
			vars_buQiBuZheng = (Integer) vars.get(SysVariables.BUQIBUZHENG) == null ? 0 : (Integer) vars.get(SysVariables.BUQIBUZHENG);
			vars_teBieChengXu = (Integer) vars.get(SysVariables.TEBIECHENGXU) == null ? 0 : (Integer) vars.get(SysVariables.TEBIECHENGXU);
			buQiBuZhengUser = (String) vars.get(SysVariables.BuQiBuZhengUser);//补齐补正的发起人
			taskSenderId = (String) vars.get(SysVariables.TASKSENDERID);
			multiInstance = workflowProcessDefinitionService.getMultiinstanceType(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		}

		Person person = ThreadLocalHolder.getPerson();
		String currentUserId = person.getID();//BpmUtil.genActivitiUser(person.getID(), person.getParentID());// 当前用户的id，格式为“用户Id:部门Id”
		if (SysVariables.TODO.equals(itembox))// 在待办件列表中打开公文显示按钮(itembox==todo表示在待办件列表)，在在办件列表和办结件列表里打开公文不显示任何按钮
		{
			/*if(vars_teBieChengXu == 1){//显示“特别程序处理”按钮
				isButtonShow[2] = true;//返回
				isButtonShow[20] = true;//特别程序处理
			}else{*/
			
				/**
				 * 特别程序按钮，0为不显示特别程序按钮，1为显示“特别程序处理”按钮，2为显示“特别程序结束”按钮,
				 * 由于特别程序申请也是用协办的方式处理，所以在任务返回给特别程序申请人的时候需要判定，该任务是特别程序还是协办处理返回的，
				 * 当vars_teBieChengXu不为2时，才显示协办的“完成”，“撤销签收”等按钮。
				 */
				isPiZhun = vars.get("piZhun") == null ? false : (boolean) vars.get("piZhun");
				int nrOfInstances = -1;// 并行任务的总个数
				int nrOfActiveInstances = -1;// 当前还没有完成的并行任务个数，对应vars中的nrOfActiveInstances变量
				int nrOfCompletedInstances = -1;// 已经完成的并行任务个数
				boolean isSequential = false;// 用来表示当前任务节点在串行状态下，当前用户是否显示发送按钮和送下一人按钮，当为true时，不显示发送按钮，显示的是送下一人按钮
				boolean isLastSequential = false;// 串行时是否是最后一个人员
				if (multiInstance.equals(SysVariables.SEQUENTIAL)) {// 在串行状态下且不是users里面的最后一个用户，isSequential为true
					isSequential = true;
					if (vars_users.size() > 0) {// users可能没有用户，或者只有一个用户，因此需要进行判断，对于没有用户，或者只有一个用户，都应显示发送按钮，送下一人按钮不显示
						// 串行处理时，当存在多个用户时，如果当前用户是users里面的最后一个，此时isLastSequential应为true
						if (vars_users.get(vars_users.size() - 1).contains(currentUserId)) {
							isLastSequential = true;
						}
					} else {
						isLastSequential = true;
					}
				}
				boolean isParallel = false;// 用来表示当前任务节点在并行状态下，true表示是并行状态，false表示不是并行状态
				boolean sponsorStatus = false;// 用来表示并行状态下当前节点是否存在主协办状态
				boolean isParallelSponsor = false;// 用来表示当前任务节点在并行状态下且是主办，true表示是并行状态下主办人员，false表示不是并行状态下的主办人员
				boolean isLastParallel = false;// 是否是并行状态下的最后一个人
				boolean isHandledParallel = true;// 并行办理的任务节点中是否有任务被办理过，默认值为true，即如果出现错误则认为有人办理过，防止显示按钮引起其它不可预知的问题
	
				if (multiInstance.equals(SysVariables.PARALLEL)) {// 在并行状态下且还有用户未办理完成，isParallel为true
					isParallel = true;
					sponsorStatus = taskConfService.getSponserStatus(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
					if (vars_sponsorGuid.contains(currentUserId))// 当前运行的任务的主办人
					{
						isParallelSponsor = true;
					}
					int iHandledParallel = workflowTaskService.isHandledParallel(taskId);
					if (iHandledParallel == 0) {
						isHandledParallel = false;
					}
					nrOfInstances = (int) vars.get(SysVariables.NROFINSTANCES);
					nrOfActiveInstances = (int) vars.get(SysVariables.NROFACTIVEINSTANCES);
					nrOfCompletedInstances = (int) vars.get(SysVariables.NROFCOMPLETEDINSTANCES);
					if (nrOfInstances > 0) {
						if (nrOfInstances == 1)// 如果只有一个人办理，那么他就是最后一个人
						{
							isLastParallel = true;
						} else {
							if (nrOfInstances == (nrOfCompletedInstances + 1)) {// 设置并行办理状态下是否是最后一个办理人员
								isLastParallel = true;
							}
						}
					}
				}
	
				if (task != null) {
					String assignee = task.getAssignee();
					String currentProcInstanceId = task.getProcessInstanceId();
					/*
					 * 是否签收，true表示签收了，false表示没有签收 如果未签收了，除了签收、拒签、返回按钮都不显示
					 * 因此下面每个按钮都需要判断isAssignee为true还是false
					 */
					boolean isAssignee = StringUtils.isNotBlank(assignee);
					boolean isContainEndEvent = workflowProcessInstanceService.isContainTaskTarget(taskId, SysVariables.ENDEVENT);// 是否包含end节点
					int pvmTransitionsCount = workflowProcessInstanceService.getPvmTransitionsCount(taskId);// 获取某个节点除去end节点的所有的输出线路的个数
	
					boolean isDirectlySend = false;// 是否是直接发送
					/*-----当前节点是否设置了直接发送人 -----*/
					String processDefinitionId = task.getProcessDefinitionId();
					String taskDefKey = task.getTaskDefinitionKey();
					isDirectlySend = taskConfService.getDirectlySendUser(processDefinitionId, taskDefKey);
					/*----- 下面是保存按钮的设置 -----*/
					// 如果未签收了，除了签收、拒签、返回按钮都不显示
					if (isAssignee) {
						//判断是否已经发起补正告知  0没有补齐补正过，1发起过补齐补正
						int isBqbz =bujiaoGaozhiService.isBuqibuzheng(instanceId);
						if(isBqbz<1 && "chengban".equals(task.getTaskDefinitionKey())){//批准或不批准过不出现补齐补正按钮。
							isButtonShow[15] = true;// 补齐补正
							isButtonShow[24] = true;//补交告知说明
						}
						if("chengban".equals(task.getTaskDefinitionKey())){
							isButtonShow[19] = true;// 特别程序申请
						}
						isButtonShow[0] = true;
					}
	
					/*----- 上面是保存按钮的设置 -----*/
	
					/*----- 下面是可以打开选人界面的发送按钮的设置 -----*/
					// DelegationState.PENDING ==
					// task.getDelegationState()：表示当前用户是在协办状态，发送按钮不再显示，完成按钮显示
					// isSequential：在串行状态下且不是users里面的最后一个用户，isSequential为true
					// pvmTransitionsCount>0表示存在发送节点
					// 当pvmTransitionsCount==1 &&
					// isContainEndEvent时，表示只有一个发送节点且是办结节点，此时不再显示发送按钮
					if (DelegationState.PENDING != task.getDelegationState() && isAssignee && pvmTransitionsCount > 0 && !(pvmTransitionsCount == 1 && isContainEndEvent)) {
						if (isParallel) {// 如果是在并行状态下，那么就要看是不是并行状态主办人，如果是则显示发送按钮，否则不显示
							if (sponsorStatus) {// sponsorStatus表示是否存在主协办，true表示存在，false表示不存在
								if (isParallelSponsor) {// isParallelSponsor：表示当前用户是并行状态下主办人员
									isButtonShow[1] = true;
									map.put("zhuBan","true");
								}
							} else {// 非主协办状态下，如果是最后一个处理人，发送按钮要显示，如果不是，要显示办理完成按钮
								if (isLastParallel) {
									isButtonShow[1] = true;
								}
							}
						} else if (isSequential) {// 如果在串行状态下，那么就要看是不是最后一个用户，如果是则显示发送按钮，否则不显示
							if (isLastSequential) {
								isButtonShow[1] = true;
							}
						} else {// 如果既不是并行也不是串行
							isButtonShow[1] = true;
						}
					}
					/*----- 上面是可以打开选人界面的发送按钮的设置 -----*/
	
					/*----- 下面是直接发送按钮的设置 -----*/
					if (isDirectlySend) {
						isButtonShow[1] = false;
						isButtonShow[12] = true;
					}
					/*----- 上面是直接发送按钮的设置 -----*/
	
					/*----- 下面是返回按钮的设置 -----*/
					// 改为easyui界面后，暂时不显示返回按钮
					isButtonShow[2] = true;
					/*----- 上面是返回按钮的设置 -----*/
	
					/*----- 下面是配置按钮的设置 -----*/
					if (isAssignee) {
						TaskConf taskConf = taskConfService.findOne(processDefinitionId, taskDefKey);
						if (taskConf != null) {
							String operation = taskConf.getOperation();
							if (StringUtils.isNotBlank(operation)) {
								if (taskConf.getOperation().contains("button04") && !isPiZhun) {
									int taskCount = workflowHistoryTaskService.getTaskListByProcessInstanceId(task.getProcessInstanceId()).size();
									String vars_parallelSponsor = (String) vars.get(SysVariables.PARALLELSPONSOR);
									if (isAssignee && taskCount > 1) {// 当新建的时候该按钮不显示，当回退到第一步时，该按钮不显示
										if (isParallel) {// 当并行处理时
											if (sponsorStatus) {// 在基本配置中是否配置了主协办，没有配置为false，配置了使用主协办为true，不使用主协办为false
												if (isParallelSponsor && !isHandledParallel)// 主协办状态下主办人在其他人没有办理的情况下可以退回
												{
													isButtonShow[3] = true;
												}
											} else {// 非主协办状态下在其他人没有办理的情况下可以退回
												if (!isHandledParallel)// 主协办状态下主办人在其他人没有办理的情况下可以退回
												{
													isButtonShow[3] = true;
												}
											}
										} else {// 不是并行处理
											isButtonShow[3] = true;
										}
									}
								}
								if (taskConf.getOperation().contains("button05")) {
									isButtonShow[4] = true;
								}
								if (taskConf.getOperation().contains("button06")) {
									isButtonShow[5] = true;
								}
								/*if (taskConf.getOperation().contains("button07")) {
									isButtonShow[16] = true;// 批准
									isButtonShow[17] = true;//不予批准
									isButtonShow[1] = false;
								}*/
								if (taskConf.getOperation().contains("button08")) {
									if (vars_buQiBuZheng == 1) {
										isButtonShow[15] = true;// 补齐补正
										isButtonShow[24] = true;// 补齐补正说明
									}
								}
								if (taskConf.getOperation().contains("button09")) {
									isButtonShow[19] = true;// 特别程序申请
								}
								if (taskConf.getOperation().contains("button10")) {
									isButtonShow[21] = true;// 特殊办结
								}
							}
						}
					}
					/*----- 上面是配置按钮的设置 -----*/
					/*----- 下面是协办状态下的完成按钮的设置 -----*/
					if (isAssignee) {
						if (DelegationState.PENDING == task.getDelegationState()&&vars_teBieChengXu!=2) {// 此时说明用户在协办状态，发送按钮不再显示，完成按钮显示
							isButtonShow[4] = false;// 协办状态下，不再允许转办
							isButtonShow[5] = false;// 协办状态下，不再允许协办
							isButtonShow[6] = true;//完成
						}
					}
	
					/*----- 上面是完成按钮的设置 -----*/
	
					/*----- 下面是送下一人状态下的完成按钮的设置 -----*/
					if (isSequential && !isLastSequential && isAssignee && !(DelegationState.PENDING == task.getDelegationState())) {
						isButtonShow[8] = true;//办理完成、送下一人
						isButtonShow[12] = false;//直接发送
					}
					/*----- 上面是送下一人按钮的设置 -----*/
	
					/*----- 下面是并行处理时办理完成按钮的设置 -----*/
					// 办理完成，并行状态下存在
					// 人员已经指定，在并行状态下并且不是并行状态下主办，同时用户设置了存在主协办
					if (isAssignee && isParallel && !(DelegationState.PENDING == task.getDelegationState())) {
						if (sponsorStatus) {// 在基本是否配置了主协办，没有配置为false，配置了使用主协办为true，不使用主协办为false
							if (!isParallelSponsor)// 如果不是主办人，显示办理完成按钮
							{
								map.put("xieBan", "true");
								isButtonShow[8] = true;
								isButtonShow[12] = false;
							}
						} else {// sponsorStatus，此时如果是最后一个处理人，上面的发送按钮要显示，如果不是，要显示办理完成按钮
							if (!isLastParallel) {
								map.put("xieBan", "true");
								isButtonShow[8] = true;//办理完成
								isButtonShow[12] = false;//直接发送
							}
						}
					}
	
					/*----- 上面是办理完成按钮的设置 -----*/
	
					/*----- 下面是签收按钮的设置 -----*/
					// 签收、拒签按钮
					if (!isAssignee)// 当user变量没有指定人员时，显示签收
					{
						isButtonShow[9] = true;//签收
						isButtonShow[12] = false;//直接发送
						/**暂时不用
						 * isButtonShow[14] = true;//拒签
						 */
						
						// 是否是最后一个拒签人员，如果是则提示是否拒签，如果拒签，则退回任务给发送人，发送人重新选择人员进行签收办理
						List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(taskId);
						if (identityLinkList.size() <= 1) {
							map.put("refuseClaimTip", "您为当前任务的最后一个签收人员，如果拒签的话将把任务退回给上一步发送人");
						}
					}
					/*----- 上面是签收按钮的设置 -----*/
	
					/*----- 下面是撤销签收按钮的设置 -----*/
					// 撤销签收
					if (isAssignee) {
						// 判断当前流程实例经过的任务节点数和当前流程实例是否存在父流程实例
						// 如果任务节点数为1且存在父流程实例，则是流程调用，此时显示拒签按钮
						// 否则是流程中发给多人等情况，不显示拒签按钮
						long count = workflowTaskService.getUserTaskCount(currentProcInstanceId);
						if (count == 1) {
							String superProcessInstanceId = procInstanceRelationshipService.getParentProcInstanceId(currentProcInstanceId);
							if (StringUtils.isNotBlank(superProcessInstanceId)) {// 是父子流程
								/**暂时不用
								 * isButtonShow[10] = true;//撤销签收
								 */
								
							}
						} else {
							List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(taskId);
							if (identityLinkList.size() > 2) {
								for (IdentityLink i : identityLinkList) {
									if (i.getUserId().contains(person.getID()) && IdentityLinkType.CANDIDATE.equals(i.getType())&&vars_teBieChengXu!=2) {
										/**暂时不用
										 * isButtonShow[10] = true;//撤销签收
										 */
										break;
									}
								}
							}
						}
					}
					/*----- 上面是撤销签收按钮的设置 -----*/
	
					/*----- 下面是办结按钮的设置 -----*/
					// 办结
					if (isAssignee && isContainEndEvent)// 当前节点的目标节点存在ENDEVENT类型节点时，显示办结按钮
					{
						if (isParallel) {// 如果是在并行状态下，那么就要看是不是并行状态主办人，如果是则显示办结按钮，否则不显示
							if (sponsorStatus) {// 是否设置了主协办状态
								if (isParallelSponsor) {// 如果是主办人，显示办结按钮
									 isButtonShow[11] = true;
								}
							} else {
								if (isLastParallel) {// 如果是最后一个处理人，显示办结按钮
									 isButtonShow[11] = true;
								}
							}
	
						} else if (isSequential) {// 如果在串行状态下，那么就要看是不是最后一个用户，如果是则显示办结按钮，否则不显示
							if (isLastSequential) {
							 isButtonShow[11] = true;
							}
						} else {// 如果既不是并行也不是串行
							isButtonShow[11] = true;//办结
						}
						isButtonShow[12] = false;//直接发送
					}
					/*----- 上面是办结按钮的设置 -----*/
	
				} else {// task为null，此时是新增
					isButtonShow[0] = true;
					isButtonShow[1] = true;
				}
			//}
		} else if (SysVariables.DOING.equals(itembox)) {// 在办件
			isButtonShow[2] = true;// 在办情况下，显示返回按钮
			isButtonShow[13] = false;// 在办情况下，收回按钮默认为不现实，当配置了收回按钮时，且当前节点的下一个节点满足回收的条件时才显示回收按钮
			// 下面是收回按钮
			String processDefinitionId = "";
			String processInstanceId = "";
			String taskDefKey = "";
			if (task != null) {
				processDefinitionId = task.getProcessDefinitionId();
				processInstanceId = task.getProcessInstanceId();
				taskDefKey = task.getTaskDefinitionKey();
				TaskConf taskConf = taskConfService.findOne(processDefinitionId, taskDefKey);
				String personId = person.getID();
				if (taskConf != null) {
					if (taskConf.getOperation()!=null&&taskConf.getOperation().contains("button14")) {

						if (multiInstance.equals(SysVariables.PARALLEL)) {
							System.out.println("================当前流程运行到的节点为并行==================");
							if (StringUtils.isNotBlank(taskSenderId)) {
								String[] userIdAndDeptId = taskSenderId.split(SysVariables.COLON);
								String userId = userIdAndDeptId[0];
								if (userId.equals(personId)) {
									if (workflowTaskService.isHandledParallel(taskId) == 0) {
										isButtonShow[13] = true;//收回
									}
								}
							}
						} else {
							/*
							 * 如果是串行的话，串行节点下的几个人物的发送人是以同一个人，
							 * 所以无法根据判断taskSenderId当前登陆的人员是否显示收回按钮
							 * 所以要查出当前运行节点的上一个节点
							 * ，然后查出上一个节点的发送人，比较当前登陆的人员是否和它相等，来判断是否显示回收按钮
							 */
							System.out.println("================当前流程运行到的节点为串行==================");
							HistoricTaskInstance hti = workflowTaskService.getPreviousTask(taskId);
							if (hti != null) {
								String userId = hti.getAssignee().split(SysVariables.COLON)[0];
								if (userId.equals(personId)) {
									isButtonShow[13] = true;//收回
								}
							}

						}

					}
				}
			}
			// 上面是收回按钮
		} else if (SysVariables.DONE.equals(itembox)) {// 办结件
			isButtonShow[2] = true;//返回
		} else if (SysVariables.ADD.equals(itembox)) {// 新建
			isButtonShow[0] = true;//保存
			isButtonShow[1] = true;//发送
			//判断是否已经发起补正告知  0没有补齐补正过，1发起过补齐补正
			int isBqbz =bujiaoGaozhiService.isBuqibuzheng(instanceId);
			/*if(isBqbz<1){
				isButtonShow[15] = true;// 补齐补正
			}*/
		}/*else if (SysVariables.PAUSE.equals(itembox)) {// 暂停件
			isButtonShow[0] = true;//保存
			isButtonShow[2] = true;//返回
			isButtonShow[1] = true;//发送
		} */else if (SysVariables.PAUSE.equals(itembox)||SysVariables.teBieChengXu.equals(itembox)) {
			if(vars_teBieChengXu == 1){//显示“特别程序审核”按钮
				String teBieChengXuAuditId = (String) vars.get(SysVariables.TEBIECHENGXUAUDITID);
				if(teBieChengXuAuditId!=null&&teBieChengXuAuditId.contains(ThreadLocalHolder.getPerson().getID())){
					isButtonShow[20] = true;//特别程序审核
				}else{
					isButtonShow[22] = true;//特别程序结果按钮
				}
				isButtonShow[2] = true;//返回
			}
			isButtonShow[2] = true;
			isButtonShow[1] = false;//发送
			if (vars_buQiBuZheng == 2){
				if(person.getID().equals(buQiBuZhengUser)){
					isButtonShow[18] = true;//补齐补正受理按钮
				}
			}	
			if (vars_teBieChengXu == 2)
				isButtonShow[22] = true;//特别程序结束按钮
		}

		map.put("buttonIds", buttonIds);
		map.put("buttonNames", buttonNames);
		map.put("buttonIconCls", buttonIconCls);
		map.put("isButtonShow", isButtonShow);
		map.put("buttonOrders", buttonOrders);

		return map;
	}
}
