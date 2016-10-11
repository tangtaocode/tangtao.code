package net.risesoft.services.bizbankroll.apply;

import java.util.List;

import net.risesoft.beans.bizbankroll.BizProductBean;
import net.risesoft.daos.base.IBaseDao;

public interface IBizProductService extends IBaseDao<BizProductBean>{
	public List<BizProductBean> getProductList(String userGUID)throws Exception;
}
