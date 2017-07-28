package net.risesoft.approve.service.senate;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;

/**
 * 审批数据接口
 * @author Administrator
 *
 */
public interface SenateService {
	
	/**
	 * 保存评价
	 * @param 
	 * @return
	 */
	public void saveSenate(String processDefinitionId, String taskId,String processInstanceId,String routeToTaskId);
	
	public String getOneInt(String fromPage,String processInstanceId);
	
	public boolean insertSenate(String fromPage,String instanceGUID,HttpServletRequest request) throws Exception;
	
	public String getOneString(String sql,List<Object> params);
	
	public Pager findAllList(String approveItemName,Pager pager);
	
	public boolean saveSpItemSenate(HttpServletRequest request);
	
	public boolean notSatSMS(DataSource ds,Map<String,Object> paramMap);
	
	public boolean sendSenateSms(Map<String,Object> paramMap);
	
	public Pager senateList(HttpServletRequest request,Pager pager);
	
	public Pager detailList(HttpServletRequest request,Pager pager);
	
	public List<Map<String, Object>> getSpmBureauList();
	
	public List<Map<String, Object>> getStreetList();
	
	public List<Map<String, Object>> getAllBureauList();
	
	public List<Map<String, Object>> getCommunityList();
	
	public List<Map<String, Object>> getWindowsList();
	
	public void exportDataForExcel(HttpServletRequest req,HttpServletResponse response);
	
	

}
