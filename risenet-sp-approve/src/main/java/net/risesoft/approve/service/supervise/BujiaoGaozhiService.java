package net.risesoft.approve.service.supervise;

import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.jpa.SpmWordFile;
import net.risesoft.approve.entity.jpa.TBujiaogaozhi;
import net.risesoft.approve.entity.jpa.TBujiaogaozhiUnits;
import net.risesoft.approve.entity.jpa.TBujiaoshouli;


/**
 * 审批数据接口
 * @author Administrator
 *
 */
public interface BujiaoGaozhiService {

	/**
	 * 判断是否发起过补齐补正
	 */
	public int isBuqibuzheng(String instanceId);
	/**
	 * 判断是否补齐补正受理
	 */
	public String isBuqibuzhengShouli(String instanceId);
	/**
	 * 保存补齐补正数据
	 * @param 
	 * @return
	 */
	public void saveBuqibuzheng(String instanceId,String reason,String xbqids,String xbzids,int step);
	/**
	 * 保存补齐补正数据
	 * @param 
	 * @return
	 */
	public void saveBuqibuzheng(TBujiaogaozhi bujiaogaozhi);
	/**
	 * 保存补齐补正受理数据
	 * @param 
	 * @return
	 */
	/**
	 * 补交告知时间
	 * @param instanceId
	 * @throws Exception
	 */
	public String findBjgzTime(String sblsh_short);
	public void saveBuqibuzhengshouli(String instanceId) throws Exception;
	
	/**
	 * 保存补齐补正受理数据
	 * 
	 * @return
	 */

	public void saveBuqibuzhengshouli(TBujiaoshouli bean);
	
	/**
	 * 保存补正告知数据
	 * @param 
	 * @return
	 */
	public void saveBuZhengGaoZhi(String processInstanceId,String reason,String xbqids,String xbzids,String bz);
	
	/**
	 * 根据Id获取补交受理
	 * @param id
	 * @return
	 */
	public TBujiaogaozhi findById(TBujiaogaozhiUnits id);
	
	
	/**
	 * 获取补齐补正状态：开始时间、已用天数、到期时间
	 * @param id
	 */
	public Map<String, Object> getBuqibuzhengStatus(TBujiaogaozhiUnits id);
	
	/**
	 * 查询补齐补正材料
	 */
	public Map<String,Object> getBuqibuzhengLists(String ytjids,String xbqids,String xbzids); 
	
	/**
	 * 电子文件存档
	 */
	public SpmWordFile saveWordFile(SpmWordFile wordFile);
	
	/**
	 * 查询所有补齐补正件
	 */
	public List<Map<String,Object>> findAll(String name,String yxtywlsh,String declareperson);
	
	/**
	 * 补齐补正件统计
	 * @return
	 */
	long bqbzCount();
	
	/**
	 * 补交告知说明书
	 */
	public Map<String,Object> getAdviceRemark(String approveitemguid);
	
	/**
	 * 获取材料列表
	 */
	public List<Map<String,Object>> getLists(String approveitemguid);
	/**
	 * 获取材料类型
	 */
	public List<Map<String,Object>> getListType(String approveitemguid);
	
	/**
	 * 获取短信提醒信息
	 */
	public Map<String,Object> getSmsMessage(String instanceId,String xbqids,String sbzids);
}

