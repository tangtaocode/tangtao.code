package net.risesoft.services.fileUtil.impl;

import java.util.List;

import javax.annotation.Resource;

import net.risesoft.beans.risefile.UpFileBean;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.fileUtil.IFileUtileService;

import org.springframework.stereotype.Service;

@Service
public class FileUtilServiceImpl implements IFileUtileService{
	@Resource
	private ISimpleJdbcDao<UpFileBean> simpleJdbcDao;

	public UpFileBean getFileBean(String sql,Object[] obj) {
		return simpleJdbcDao.getBean(sql, obj, UpFileBean.class);
	}

	public List<UpFileBean> getFileBeanList(String sql,Object[] obj) {
		return simpleJdbcDao.queryForRow(sql, obj, UpFileBean.class);
	}
	
	
}
