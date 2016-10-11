package net.risesoft.services.law.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import net.risesoft.beans.base.QueryResult;
import net.risesoft.beans.onlineservice.LawsBean;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.law.ILawsService;
/**
 * @政策法规
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
@Service
public class LawsServiceImpl extends BaseDaoImpl<LawsBean> implements ILawsService {
	public List<LawsBean> getLawsList(int rowNum) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("subtime", "desc");
		map.put("id", "asc"); 
		return getScrollData(0, rowNum,map).getResultList();
	}

	public QueryResult<LawsBean> getMoreLaws(int pageFirstRow, int maxRow,
			String[] whereSql, Object[] param) {
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("subtime", "desc");
		map.put("id", "asc"); 
		return getScrollData(pageFirstRow, maxRow,whereSql,param,map);
	}

	public LawsBean getLawsBeanById(String id) {
		return find(id);
	}
	
	
	
}
