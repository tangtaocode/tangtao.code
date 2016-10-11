package net.risesoft.services.onlineservice;

import java.util.List;

import net.risesoft.beans.onlineservice.GuideFileType;
import net.risesoft.beans.wssb.ProceedingForm;
import net.risesoft.daos.base.IBaseDao;
/**
 * 
  * @ClassName: IGuideFileTypeService
  * @Description: 办事指南材料类型service接口
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:14:50 PM
  *
 */
public interface IGuideFileTypeService extends IBaseDao<GuideFileType>{
	public List<GuideFileType> getTypesList(String sql,Object[] param)throws Exception;
}
