package net.risesoft.services.civilbankroll.dynamic;

import java.util.List;

import net.risesoft.beans.bizbankroll.Statistical;
import net.risesoft.beans.civilbankroll.CivilApplicationBean;
import net.risesoft.daos.base.IBaseDao;

public interface ICivilDynamicService extends IBaseDao<CivilApplicationBean>{
	public List<CivilApplicationBean> homeDynamicList(int rowNum)throws Exception;
	public Statistical getStatistical()throws Exception;
	public int getDataRows(String sql,Object[] obj)throws Exception; 
	public List<CivilApplicationBean> moreDynamic(String sql,Object[] obj)throws Exception;
	
}
