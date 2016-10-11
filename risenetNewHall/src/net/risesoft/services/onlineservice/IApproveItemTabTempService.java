package net.risesoft.services.onlineservice;

import java.util.List;

import net.risesoft.beans.onlineservice.ApproveItemTabTemp;
import net.risesoft.daos.base.IBaseDao;
/**
 * 
  * @ClassName: IApproveItemTabTempService
  * @Description: 办事指南表格系在service接口
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:14:06 PM
  *
 */
public interface IApproveItemTabTempService extends IBaseDao<ApproveItemTabTemp>{
	public List<ApproveItemTabTemp> getMoreApproveItemTabTemp(String sql,Object[] params);
	public int getDataRows(String sql,Object[] params)throws Exception;
}
