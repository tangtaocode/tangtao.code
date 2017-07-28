package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

public interface RisenetDepartmentService {
	
	
	/**
	 * SPM委办局列表
	 * 
	 */
	public List<Map<String, Object>> findAll();
	
	
	/**
	 * 查询所有证照类型
	 * @return
	 */
	public List<Map<String, Object>> findAllZZType();
	
	/**
	 * 待条件的查询所有证照类型
	 * @return
	 */
	public List<Map<String, Object>> findAllZZType(String produceOrgan);

}
