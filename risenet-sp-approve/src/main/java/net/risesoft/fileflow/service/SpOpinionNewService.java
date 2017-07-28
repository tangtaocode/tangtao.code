package net.risesoft.fileflow.service;

import java.util.List;
import java.util.Map;

import net.risesoft.fileflow.entity.jpa.OpinionNew;


public interface SpOpinionNewService {

	OpinionNew findOne(String id);

	/**
	 * 保存意见
	 * 
	 * @param entities
	 */
	OpinionNew saveOrUpdate(OpinionNew entity);

	Integer getCount4Personal(String processSerialNumber, String taskId, String category, String userId);
	
	Integer getCount4Personal(String processSerialNumber,String category, String userId);
	
	/**
	 * 获取个人意见
	 * @param SPinstanceId
	 * @param processSerialNumber
	 * @param processInstanceId
	 * @param taskId
	 * @param itembox
	 * @param activitiUser
	 * @param mainAndSub
	 * @param category
	 * @param readOnly
	 * @return
	 */
	List<Map<String, Object>> personCommentList(String SPinstanceId,String processSerialNumber, String processInstanceId, String taskId, String itembox, String activitiUser, Integer mainAndSub, String category, String readOnly);


	/**
	 * 意见签名
	 * @param CertTxt
	 * @param SignTxt
	 * @param DataTxt
	 * @return
	 */
	public boolean opinionSign(String CertTxt,String SignTxt,String DataTxt);
	
	/**
	 * 意见验签
	 * @param CertTxt
	 * @param SignTxt
	 * @param DataTxt
	 * @return
	 */
	public String opinionCheck(String CertTxt,String SignTxt,String DataTxt);

	/**
	 * 获取无纸化意见签名事项
	 * @param sPinstanceId
	 * @return
	 */
	Map<String, Object> getSpmApproveitem(String sPinstanceId);
}
