package net.risesoft.services.bizbankroll.apply;

import java.util.List;

import net.risesoft.beans.bizbankroll.BizIntroducerBean;
import net.risesoft.daos.base.IBaseDao;

public interface IBizIntroducerService extends IBaseDao<BizIntroducerBean>{
	public int getDataRows(String sql,Object[] obj); 
	public List<BizIntroducerBean> getMoreIntroducer(String sql, Object[] obj)throws Exception;
}
