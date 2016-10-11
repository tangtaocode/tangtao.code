package net.risesoft.utils.base;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import net.risesoft.beans.base.CodeMap;
import net.risesoft.daos.base.IBaseDao;

public interface ICodeMapUtil extends IBaseDao<CodeMap>{
	public void init() throws Exception;
	public TreeMap<String, String> getKeyValueMap(String sql,Object[] params);
	public TreeMap<String, String> getMapByType(String pType);
	public List<CodeMap> getCodeMapList(String sql);
	public List<CodeMap> getCodeMapList(String sql,String[] parem);
	public List<CodeMap> getListByType(String pType);
	public String getDeclaresnByBureauGuid(String bureauGuid);
	public Date caculateEndDate(Date beginDate, int day);
	public String findSlbh(String typeGuid);
}
