package net.risesoft.approve.service;

import java.util.Map;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;

public interface OfficeSpiDeclareinfoService {
	
	/**
	 * 流程实例和审批实例绑定
	 * @param guid
	 * @param processInstanceId
	 */
	void updateOfficeSpiDeclareinfo(String guid,String processInstanceId);
	
	/**
	 * 根据流程实例Id获取业务流水号
	 * @param processInstanceId
	 * @return
	 */
	String getDeclaresnByProcessInstanceId(String processInstanceId);
	
	/**
	 * 根据guid获取事项
	 * @param declaresn
	 * @return
	 */
	OfficeSpiDeclareinfo findByGuid(String guid);
	
	/**
	 * 根据流程实例ID获取事项ID
	 * @param processInstanceId
	 * @return
	 */
	String findApproveitemguidByProcessInstanceId(String processInstanceId);
	
	/**
	 * 根据流程实例ID获取审批业务ID,用于材料清单，附件查询
	 * @param processInstanceId
	 * @return
	 */
	String getGuidByProcessInstanceId(String processInstanceId);
	
	/**
	 * 根据流程Id获取
	 * @param processInstanceId
	 * @return
	 */
	OfficeSpiDeclareinfo findByProcessInstanceId(String processInstanceId);
	
	/**
	 * 获取补交告知通知单
	 */
	public Map<String,Object> getAdviceForm(String instanceId);
	
	/**
	 * 获取审批受理单
	 */
	public Map<String,Object> getBanliDanForm(String processInstanceId);

	/**
	 * 获取审批办结单
	 * @param guid
	 * @return
	 */
	Map<String, Object> getBanJieDanForm(String guid);
}
