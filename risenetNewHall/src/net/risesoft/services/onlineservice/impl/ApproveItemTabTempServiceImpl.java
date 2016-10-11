package net.risesoft.services.onlineservice.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import net.risesoft.beans.onlineservice.ApproveItemTabTemp;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.onlineservice.IApproveItemTabTempService;
/**
 * 
  * @ClassName: IApproveItemTabTempService
  * @Description: 办事指南表格系在service
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:14:06 PM
  *
 */
@Service
public class ApproveItemTabTempServiceImpl extends BaseDaoImpl<ApproveItemTabTemp> implements IApproveItemTabTempService{
	
	@Resource
	private ISimpleJdbcDao<ApproveItemTabTemp> itemSimpleJdbcDao;//查询事项服务类
	
	public List<ApproveItemTabTemp> getMoreApproveItemTabTemp(String sql,Object[] params) {
		List<ApproveItemTabTemp> appList = itemSimpleJdbcDao.queryForRow(sql, params,ApproveItemTabTemp.class);
		return appList;
	}
	public int getDataRows(String sql,Object[] params)throws Exception{
		return itemSimpleJdbcDao.countRows(sql,params);
	}
}
