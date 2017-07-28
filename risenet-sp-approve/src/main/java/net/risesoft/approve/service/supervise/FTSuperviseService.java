package net.risesoft.approve.service.supervise;
import net.risesoft.approve.entity.jpa.FtSupervise;
import net.risesoft.approve.entity.jpa.TShenpiguochengUnits;

/**
 * 审批数据接口
 * @author Administrator
 *
 */
public interface FTSuperviseService {
	
	/**
	 * 保存申办数据
	 * @param 
	 * @return
	 */
	public void saveShenBan(String processDefinitionId, String taskId,
			String processInstanceId);
	
	
	/**
	 * 保存网上预受理数据
	 * @param 
	 * @return
	 */
	public void saveWangshangyushouli(String instanceId);
	
	/**
	 * 保存受理数据
	 * @param 
	 * @return
	 */
	public void saveShouli(String processDefinitionId, String taskId,
			String processInstanceId,String routeToTaskId);
	
	/**
	 * 保存办结数据
	 * @param 
	 * @return
	 */
	public void saveBanjie(String processDefinitionId, String taskId,String processInstanceId);

	/**
	 * 保存审批结果数据
	 * @param 
	 * @return
	 */
	public void saveShenpiJieguo(String processDefinitionId, String taskId,String processInstanceId);
	
	/**
	 * 获取公共字段
	 */
	public FtSupervise getSuperviseData();
	
	
	/**
	 * 保存审批过程数据
	 * @param 
	 * @return
	 */
	public void saveShenpiguocheng(String processInstanceId,String taskId,String spbzh,String Syhjmc);

}
