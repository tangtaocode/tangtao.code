/**
 * @ProjectName:zjjHome
 * @FileName: IWebServiceTodoService.java
 * @PackageName: net.risesoft.service
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 9, 2015 5:35:43 PM
 */
package net.risesoft.service;

import java.util.List;
import java.util.Map;

import net.risesoft.annotation.DataSource;
import net.risesoft.model.RiseEmployee;

/**
 * @ClassName: IWebServiceTodoService.java
 * @Description: TODO
 *
 * @author kun
 * @date May 9, 2015 5:35:43 PM
 * @version 
 * @since JDK 1.6
 */
public interface IWebServiceTodoService {
	/**
	 * 
	  * @MethodName: oaTodoList
	  * @Description: TODO 获取旧OA系统的待办事项，调用接口后查询数据库中
	  * @param： (参数)
	  * @return： 返回类型 boolean
	  * @throws：
	  * 
	  * @Author kun
	  * @date May 9, 2015  3:43:38 PM
	 */
	@DataSource("homeDataSource")
	public boolean oaTodoList(RiseEmployee em);
	
	/**
	 * 
	  * @MethodName: WSisOK
	  * @Description: TODO 判断单个的webservicew是否可用
	  * @param： (参数)
	  * @return： 返回类型 boolean
	  * @throws：
	  * 
	  * @Author kun
	  * @date May 9, 2015  3:44:13 PM
	 */
	@DataSource("homeDataSource")
	public boolean WSisOK(String wsdlGUID);
	
	/**
	 * 
	  * @MethodName: WSisOK
	  * @Description: TODO 获取所有webService的连接状态，显示在首页
	  * @param： (参数)
	  * @return： 返回类型 String
	  * @throws：
	  * 
	  * @Author kun
	  * @date May 9, 2015  3:44:37 PM
	 */
	@DataSource("homeDataSource")
	public String WSisOK();
	
	/**
	 * 
	  * @MethodName: getAllWebService
	  * @Description: TODO 获取所有webservice配置
	  * @param： (参数)
	  * @return： 返回类型 List<Map<String,Object>>
	  * @throws：
	  * 
	  * @Author kun
	  * @date May 9, 2015  6:55:49 PM
	 */
	@DataSource("homeDataSource")
	public List<Map<String, Object>> getAllWebService();
	
	/**
	 * 
	  * @MethodName: updateService
	  * @Description: TODO 更新webservice配置
	  * @param： (参数)
	  * @return： 返回类型 List<Map<String,Object>>
	  * @throws：
	  * 
	  * @Author kun
	  * @date May 9, 2015  6:57:46 PM
	 */
	@DataSource("homeDataSource")
	public void updateService(Map<String, Object> webMap);
}

