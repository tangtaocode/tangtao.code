package net.risesoft.services.interaction;

import java.util.List;

import net.risesoft.beans.onlineservice.BusinessInfo;
import net.risesoft.beans.onlineservice.ProjectInfo;


public interface IDynamicStateService {
	public List<BusinessInfo> getMoreDynamic(String sql,Object[] param)throws Exception;
	public List<BusinessInfo> getHomeDynamic(int rowNum)throws Exception;
	public List<BusinessInfo> getResultPublicity(String sql,Object[] params)throws Exception;
	public List<ProjectInfo> getResultProject(String sql,Object[] params)throws Exception;
	public int getDataRows(String sql, Object[] params)throws Exception;
	public BusinessInfo getBusinessInfo(String sql, Object[] params)throws Exception;
}
