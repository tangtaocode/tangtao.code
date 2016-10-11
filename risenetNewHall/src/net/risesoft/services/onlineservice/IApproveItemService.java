package net.risesoft.services.onlineservice;


import java.util.List;

import net.risesoft.beans.onlineservice.ApproveItem;
import net.risesoft.beans.onlineservice.ApproveItemLog;
import net.risesoft.daos.base.IBaseDao;
/**
 * 
  * @ClassName: IApproveItemService
  * @Description: 事项信息service接口
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:13:49 PM
  *
 */
public interface IApproveItemService extends IBaseDao<ApproveItem>{
	public List<ApproveItem> getMoreApproveItem(String sql,Object[] params)throws Exception;
	public int getDataRows(String sql,Object[] params)throws Exception;
	public ApproveItem getGuideInfo(String itemGUID)throws Exception;
	public ApproveItemLog getGuideInfo(String itemGUID,String sjbbh)throws Exception;
}
