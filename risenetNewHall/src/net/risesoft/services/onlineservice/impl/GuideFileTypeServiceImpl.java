package net.risesoft.services.onlineservice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.onlineservice.GuideFileType;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.onlineservice.IGuideFileTypeService;
/**
 * 
  * @ClassName: IGuideFileTypeService
  * @Description: 办事指南材料类型service
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:14:50 PM
  *
 */
@Service
public class GuideFileTypeServiceImpl extends BaseDaoImpl<GuideFileType> implements IGuideFileTypeService{

	@Resource
	private ISimpleJdbcDao<GuideFileType> itemClassifySimpleJdbcDao;
	
	@Override
	public List<GuideFileType> getTypesList(String sql, Object[] param)
			throws Exception {
		
		return itemClassifySimpleJdbcDao.queryForRow(sql,param,GuideFileType.class);
	}

}
