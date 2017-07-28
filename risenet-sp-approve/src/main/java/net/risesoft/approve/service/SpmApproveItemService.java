package net.risesoft.approve.service;

import java.util.List;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.SpmApproveitem;


public interface SpmApproveItemService {
	//根据事项id获取一个对象
	public SpmApproveitem findByApproveitemguid(String approveitemguid);
	

	/**
	 * 
	  * @MethodName: findByWindowGuid
	  * @Description: 根据窗口guid查找该窗口可以办理的全部事项
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<SpmApproveitem>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月29日  上午10:55:18
	 */
	public Pager findByWindowGuid(String personId, Pager pager, String approvename);
	
	/**
	 * 
	 * @param 通过事项的主键区查找该事项发出的证照类型
	 * @return
	 */
	public String getDoctypeByGuid(String guid);
	/**
	 * 获取所有事项
	 * @return
	 */
	public List<SpmApproveitem> findAll();
}
