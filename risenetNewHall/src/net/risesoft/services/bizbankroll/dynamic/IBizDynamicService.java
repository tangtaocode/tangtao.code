package net.risesoft.services.bizbankroll.dynamic;

import java.util.List;

import net.risesoft.beans.bizbankroll.BizApplicationBean;
import net.risesoft.beans.bizbankroll.Statistical;
import net.risesoft.daos.base.IBaseDao;

public interface IBizDynamicService extends IBaseDao<BizApplicationBean>{
	public List<BizApplicationBean> getHomeDynamicList(int rowNum)throws Exception;
	public Statistical getStatistical()throws Exception;
	public int getDataRows(String sql,Object[] obj)throws Exception; 
	public List<BizApplicationBean> getMoreDynamic(String sql,Object[] obj)throws Exception;
	
}
