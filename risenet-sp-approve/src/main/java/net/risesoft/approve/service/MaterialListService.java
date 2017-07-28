package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;




public interface MaterialListService {

	
	
	/**
	 * 根据条件查询数据
	 */
	public List<Map<String, Object>>  findAll(String approveItemType,String bureauGuid,String approveName,String approveStatus);
	
	
	
	/**
	 * 从office_spi_declareinfo中查询材料清单
	 */
	public List<Map<String,Object>> findMaterialByOffice(String instanceId);
	
	
	/**
	 * 加载窗口收件材料清单
	 */
	//public List<Map<String,Object>> findMaterialByWindow(String processInstanceId);
	
	
	/**
	 * 获取事项approveItemGuid
	 */
	public Map<String,Object> findByapproveItemGuid(String instanceId);
	
	/**
	 * 获取办事流程
	 */
	public Map<String,Object> findByapproveServiceProcess(String instanceId);
	
	/**
	 * 获取申请材料
	 */
	public Map<String,Object> findByapproveMaterial(String approveItemGuid);
	
	/**
	 * 获取申请材料描述
	 */
	public Map<String,Object> findByapproveMaterialdescription(String approveItemGuid);
	
	/**
	 * 获取法律、法规
	 */
	public Map<String,Object> findByapproveLaws(String approveItemGuid);
	
	
	/**
	 * 加载办事指南
	 */
	public  Map<String,Object> findByBszn(String approveItemGuid);
	
	
	/**
	 * 添加回复
	 */
	public int updateReply(String feedback,String ytjids, String xbqids,String xbzids,String guid);
	
	/*
	 * 查看附件明细
	 */
	public List<Map<String,Object>> findAttachMentByInstanceId(String instanceId,String declareGuid);
}
