package net.risesoft.services.bizbankroll.apply;

import java.util.List;

import net.risesoft.beans.bizbankroll.BizFinanceBean;
import net.risesoft.daos.base.IBaseDao;

public interface IBizFinanceService extends IBaseDao<BizFinanceBean>{
	public List<BizFinanceBean> getShareFinance(String userGUID)throws Exception;
}
