package net.risesoft.approve.service.supervise;

import java.util.Map;

import net.risesoft.approve.entity.jpa.TTebiechengxujieguo;
import net.risesoft.approve.entity.jpa.gdbs.TbcxsqProcess;

public interface TeBieChengXuShenQingService {
	
	/**
	 * 保存或者更新特别程序申请
	 * @param tt
	 * @return
	 */
	Map<String, Object> saveOrUpdate(TbcxsqProcess tebiechengxushenqing,String instanceId,String taskId);
	
	/**
	 * 初始化信息
	 */
	public Map<String,Object> loadTbcxsq(String instanceGuid);
	
	/**
	 * 加载特别程序申请信息，返回一个对象
	 */
	public TTebiechengxujieguo findTbcxjg(String instanceId);
	
	/**
	 * 查找特别程序申请信息
	 */
	public Map<String, Object> findTbcxsq(String sPinstanceId);

	/**
	 * 保存特别程序结果
	 */
	Map<String, Object> saveTeBieChengXuJieGuo(String sPinstanceId,
			String sjbbh, String tbcxjg, String jgcsrq, String tbcxjsrq,
			String tbcxsfje, String bz, String taskId, String processInstanceId);

	/**
	 * 特别程序结束
	 */
	Map<String, Object> teBieChengXuJieShu(String processInstanceId,String taskId);

	/**
	 * 保存特别程序审核
	 */
	Map<String, Object> saveTeBieChengXuShenHe(String sPinstanceId,
			String taskId, String type);

	
}
