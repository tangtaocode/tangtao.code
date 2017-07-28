/**
 * @Project Name:risenet-sp-approve
 * @File Name: OrderService.java
 * @Package Name: net.risesoft.approve.service
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月24日 下午4:15:50
 */
package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OrderManager;
import net.risesoft.model.Person;

/**
 * @ClassName: OrderService.java
 * @Description: 预约
 *
 * @author chenbingni
 * @date 2015年12月24日 下午4:15:50
 * @version 
 * @since JDK 1.7
 */
public interface OrderService {
	
	/**
	 * 
	  * @MethodName: findOrderManage
	  * @Description: 预约管理列表查询
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月16日  上午10:03:55
	 */
	public Pager findOrderManage(String bureau, String approve, Pager page);
	/**
	 * 
	  * @MethodName: findByUserID
	  * @Description: 窗口人员根据起始时间、回复状态搜索预约信息
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2015年12月24日  下午4:17:50
	 */
	public List<Map<String, Object>> findByUserID(Person person, String orderPerson, String orderId, String ishf, String beginDate, String endDate, String slstate);
	
	/**
	 * 
	  * @MethodName: findOrderByGuid
	  * @Description: 根据预约主键查找改预约的详细信息，包括回复情况、受理情况等
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月8日  下午2:38:22
	 */
	public List<Map<String, Object>> findOrderByGuid(String orderGuid);
	
	/**
	 * 
	  * @MethodName: saveHuifu
	  * @Description: 存储回复
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return boolean    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月8日  下午4:56:48
	 */
	public boolean saveHuifu(Person person, String orderGuid, String hfnr);
	
	/**
	 * 
	  * @MethodName: changeSlstate
	  * @Description: 在预约列表中点击“开始受理”以后更改该预约的slstate
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return void    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月10日  下午5:04:57
	 */
	public int saveSlstate(String orderGuid);
	
	/**
	 * 
	  * @MethodName: findManageByGuid
	  * @Description: 查找某个事项的预约配置（是否可预约）
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月16日  下午3:07:02
	 */
	public Map<String, Object> findManageByGuid(String itemguid);
	
	/**
	 * 
	  * @MethodName: searchManageByGuid
	  * @Description: 查找某事项的预约配置（每小时可预约多少人、特殊时段可以预约多少人）
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月17日  上午10:19:15
	 */
	public List<Map<String, Object>> searchManageByGuid(String itemguid);
	
	/**
	 * 
	  * @MethodName: saveManage
	  * @Description: 保存某事项的预约配置（每小时可预约多少人、特殊时段可以预约多少人）
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return boolean    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月18日  上午10:46:46
	 */
	public boolean saveManage(OrderManager manager);
	
	/**
	 * 
	  * @MethodName: changePermission
	  * @Description: 保存某事项的预约许可
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return boolean    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月18日  上午10:48:11
	 */
	public boolean changePermission(String itemguid, String orderpermission);
	

}

