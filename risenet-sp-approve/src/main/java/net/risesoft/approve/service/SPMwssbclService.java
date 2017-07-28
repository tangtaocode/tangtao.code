package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import net.risesoft.approve.entity.jpa.SPMwssbcl;




public interface SPMwssbclService {
	
	
	/**
	 * 根据id查询附件
	 */
	public SPMwssbcl findOne(String id);
	
	/**
	 * 根据审批业务ID以及材料ID查询数据
	 */
	public List<SPMwssbcl> findByWorkflowinstanceguidAndDeclareannexguid(String workflowinstanceguid, String declareannexguid);

	/**
	 * 下载附件
	 * @param id
	 * @param response
	 */
	public void download(String id, HttpServletResponse response,HttpServletRequest request) throws Exception;

	/**
	 * 上传附件
	 * @param attachmentFile,processInstanceId
	 */
	public void upload(MultipartFile  attachmentFile,String declareannexguid, String processInstanceId);

	/**
	 * 删除附件
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 查找附件列表
	 * @param instanceId
	 * @param dECLAREANNEXGUID
	 * @return
	 */
	public List<Map<String, Object>> findSpMwssbclList(String instanceId,
			String dECLAREANNEXGUID,String version);

	/**
	 * 获取附件列表
	 * @param instanceId
	 * @param dECLAREANNEXGUID
	 * @return
	 */
	public List<Map<String, Object>> getSpMwssbclList(String instanceId,String dECLAREANNEXGUID,String version,HttpServletRequest request);


}
