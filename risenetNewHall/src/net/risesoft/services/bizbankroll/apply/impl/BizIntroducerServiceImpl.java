package net.risesoft.services.bizbankroll.apply.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.BizIntroducerBean;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.apply.IBizIntroducerService;
@Service
public class BizIntroducerServiceImpl extends BaseDaoImpl<BizIntroducerBean> implements IBizIntroducerService{
	
	@Resource
	ISimpleJdbcDao<BizIntroducerBean> simpleJdbcDao;
	public int getDataRows(String sql, Object[] obj) {
		return simpleJdbcDao.countRows(sql, obj);
	}
	public List<BizIntroducerBean> getMoreIntroducer(String sql, Object[] obj) {
		return simpleJdbcDao.queryForRow(sql, obj, BizIntroducerBean.class);
	}
}
