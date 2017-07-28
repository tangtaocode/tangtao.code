package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.jpa.CodeMap;

/*
 * 数据字典接口
 */
public interface CodeMapService {
	public String getDeclaresnByBureauGuid(String bureauGuid);
	//获取数据来源list
	public List<Map<String,Object>> getDataSourceList(String type);
}
