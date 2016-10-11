package net.risesoft.services.law;

import java.util.List;

import net.risesoft.beans.base.QueryResult;
import net.risesoft.beans.onlineservice.LawsBean;
import net.risesoft.daos.base.IBaseDao;

public interface ILawsService extends IBaseDao<LawsBean>{
	public List<LawsBean> getLawsList(int rowNum)throws Exception;
	public QueryResult<LawsBean> getMoreLaws(int pageFirstRow,int maxRow,String[] whereSql,Object[] param)throws Exception;
	public LawsBean getLawsBeanById(String id)throws Exception;
}
