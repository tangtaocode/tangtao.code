package net.risesoft.service;


import java.util.Map;

import net.risesoft.annotation.DataSource;
import net.risesoft.model.RiseEmployee;


public interface IEmployeeService {

	/**
	 * 
	  * @MethodName: getEmployeebyGuid
	  * @Description: TODO 根据人员GUID获取人员信息
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return RiseEmployee    返回类型
	  * @throws
	  * 
	  * @Author kun
	  * @date 2015年7月2日  下午5:13:15
	 */
	@DataSource("xzspDataSource")
	public RiseEmployee getEmployeebyGuid(String employee_guid);
	
	/**
	 * 
	  * @MethodName: getUserBureau
	  * @Description: TODO 根据人员ID查询部门ID和名称
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author kun
	  * @date Dec 11, 2014  9:02:24 PM
	 */
	@DataSource("xzspDataSource")
	public Map<String, Object> getUserBureau(String departmentGuid);
	
	
}
