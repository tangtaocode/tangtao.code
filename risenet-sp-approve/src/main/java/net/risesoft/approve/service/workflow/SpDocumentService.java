package net.risesoft.approve.service.workflow;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;







import org.springframework.ui.Model;

public interface SpDocumentService {

	
	/**
	 * 启动流程
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	public Map<String, Object> startProcess(String processDefinitionKey, String processSerialNumber, String activitiUser, String documentTitle, String SPinstanceId,String status);
	
	/**
	 * 流程办结
	 * 
	 * @param taskId
	 * @param request
	 * @return
	 */
	public Map<String, Object> complete( String taskId,  String processInstanceId,  String processDefinitionId, HttpServletRequest request); 

	/**
	 * 发送
	 * @param sponsorGuid 主办人guid
	 * @param taskId 任务id
	 * @param userChoice 接收人
	 * @param routeToTaskId 任务路由
	 * @param formIds
	 * @param request
	 * @param model
	 * @return
	 */
	public Map<String, Object> forwarding(String itembox,String sponsorGuid, String taskId, String userChoice, String routeToTaskId, String formIds, HttpServletRequest request, Model model);
	
	/**
	 * 解析工作流发送时用户选取的人员/部门/角色/用户组/动态角色 解析部门时，遍历部门及其子部门，获取其下所有人员，并去除具有负权限的人员
	 * 
	 * @param userChoice
	 *            前台jsp页面用户选取的数据，每条数据之间用“;”分隔，其形式如下： 人员 类型:人员Id:部门Id 部门 类型:部门Id
	 *            角色/用户组/动态角色分为如下三种情况： 只选择了角色/用户组/动态角色 类型:角色Id 角色/用户组/动态角色下是部门
	 *            类型:角色Id:部门Id 角色/用户组/动态角色下是人员 类型:角色Id:人员Id:部门Id
	 * 
	 * @return userAndDeptIds，人员Id:人员所在部门Id(主要用于一人多岗)
	 */
	public List<String> parseUserChoice(String userChoice, String processDefinitionId, String taskDefKey, String processInstanceId, String taskId) ;
		
	/**
	 * 向userAndDeptIds中添加内容
	 * 
	 * @param userAndDeptIds
	 * @param userId
	 *            人员Guid
	 * @param deptId
	 *            人员所在部门Guid
	 * @return
	 */
	public String addUserAndDeptIds(String userAndDeptIds, String userId, String deptId) ;
	
	/**
	 * 将各个参数添加到model里面
	 * 
	 * @param itembox
	 * @param processSerialNumber
	 *            流程唯一标识，当点击新建按钮的时候就生成（此时流程还未启动），当流程启动时放入流程变量中，直到流程结束
	 * @param processDefinitionKey
	 *            流程定义Key，新建流程的时候还不知道流程定义Id
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @param taskId
	 * @param taskDefinitionKey
	 * @param model
	 * @return
	 */
	public Model genDocumentModel(String itembox, String processSerialNumber, String tenantId, String processDefinitionKey, String processDefinitionId, String processInstanceId, String taskId, String taskDefinitionKey, String activitiUser, Model model);
	
	/**
	 * 将各个参数添加到model里面
	 * 
	 * @param itembox
	 * @param processSerialNumber
	 *            流程唯一标识，当点击新建按钮的时候就生成（此时流程还未启动），当流程启动时放入流程变量中，直到流程结束
	 * @param processDefinitionKey
	 *            流程定义Key，新建流程的时候还不知道流程定义Id
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @param taskId
	 * @param taskDefinitionKey
	 * @param model
	 * @return
	 */
	public Model genDocumentModel4Eform(String itembox, String processSerialNumber, String tenantId, String processDefinitionKey, String processDefinitionId, String processInstanceId, String taskId, String taskDefinitionKey, String activitiUser, Model model);
	
	
	/**
	 * 生成页面按钮，例如发送、保存、转办等 所以按钮默认都是显示的，根据条件设置某个按钮为不显示
	 * @param taskId
	 * @param model
	 * @param itembox
	 * @param SpInstanceId 审批事项guid
	 * @return
	 */
	public Model menuControl(String processDefinitionId,String taskDefKey,String taskId, Model model, String itembox,String SpInstanceId);
	
	public List<List<Object>> genFixedColumns(List<List<Object>> columns, String processInstanceId, Integer tabIndex) ;

	/**
	 * 根据表单模板ID查询绑定的表是否有数据
	 * 
	 * @param tempId
	 * @param processInstanceId
	 * @return
	 */
	public long getData(String tempId, String processInstanceId);

	/**
	 * 发送菜单控制
	 * @param processDefinitionId
	 * @param taskDefinitionKey
	 * @param taskId
	 * @param model
	 * @param itembox
	 * @param approveitemguid
	 * @return
	 */
	public Model newMenuControl(String processDefinitionId,
			String taskDefinitionKey, String taskId, Model model,
			String itembox, String approveitemguid);

	/**
	 * 保存过程数据
	 * @param taskId
	 */
	void saveGuoChengShuJu(String taskId);
}
