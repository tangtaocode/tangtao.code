package net.risesoft.services.bizbankroll.apply;

import java.util.List;

import net.risesoft.beans.bizbankroll.BizShareholderBean;
import net.risesoft.daos.base.IBaseDao;

public interface IBizShareholderService extends IBaseDao<BizShareholderBean>{
	public List<BizShareholderBean> getShareholderList(String userGUID)throws Exception;
}
