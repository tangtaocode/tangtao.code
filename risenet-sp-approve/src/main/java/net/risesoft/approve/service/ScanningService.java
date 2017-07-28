package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.TBdexDocTypeInfo;
import net.risesoft.approve.entity.jpa.TBdexDoctype;
import net.risesoft.model.Person;

public interface ScanningService {

	
	/**
	* 根据条件查询数据
	*/
	public Pager findByUserID(Person person,String param,Pager page,String code,String applyName);
	
	/**
	 * 保存证照信息
	 */
	public String save(String id);
	
	/**
	 * 页面初始化
	 * 
	 * @return
	 */
	public String getSelecction();
	
	
	
	/**
	 * 根据approveitemname查询到当前事项基本信息项ID
	 * 
	 * @return
	 */
	public TBdexDoctype findBybasicifnoID(String APPROVEITEMNAME);

	
	/**
	 * 根据doctypeguid查询到当前事项基本信息项
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findbasicinfo(String doctypeguid);

	
	/**
	 * 根据instanceGuid查询到当前事项基本信息项
	 * 
	 * @return
	 */
	public String findByinstanceID(String instanceGuid);
	
	/**
	 * 保存当前事项基本信息项
	 * 
	 * @return
	 */
	public void savedocdate(String instanceGuid, String infocode, String infocon);
	
	public void savedocdateNew(HttpServletRequest req);
	
	
	/**
	 * 保存当前事项必须信息项
	 * 
	 * @return
	 */
	public void savedocinfodate(Map<String, Object> infomap);

	
	
}
