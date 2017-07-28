package net.risesoft.approve.service;
import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.SpmBureau;


public interface SpmBureauService {
	/**
	 * 根据条件查询所有数据
	 */
	public Pager findAll(String approveItemType,String bureauGuid,String approveName,String approveStatus,Pager page);
	
	/**
	 * 加载部门
	 */
	public List<Map<String,Object>> loadDepartMent(String isStreet,String isExtent);
	
	
	/**
	 * 绑定事项与流程
	 */
	public int bandWorkFlow(String workflowguid,String approveItemguid,String sealType);
	
	
	/**
	 * 表单跟事项绑定
	 * 
	 */
	 
	public Pager eformfindAll(String eformname,Pager page);
	
	
	/**
	 * 将模板ID,模板名称已经材料类型插入到PROCEEDINGFORMS 
	 */
	public int insert(String guid,String tempid,String templatename,String approveitemguid,String typename,String typeguid,String accessmethod,String listguid);
	
	
	/**
	 * 将数据插入到PROCEEDINGFORMS中之前先判断是否有重复插入
	 */
	//public void find(String tempid,String typeguid,String approveitemguid);
	/**
	 * 格局部门guid查询部门信息
	 * @param bureauGuid
	 * @return
	 */
	public SpmBureau findDepartMentById(String bureauGuid );
	

}
