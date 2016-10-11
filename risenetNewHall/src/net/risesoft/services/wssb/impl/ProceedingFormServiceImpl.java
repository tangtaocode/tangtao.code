package net.risesoft.services.wssb.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import net.risesoft.beans.wssb.ProceedingForm;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.wssb.ProceedingFormService;

@Service
public class ProceedingFormServiceImpl implements ProceedingFormService{

	@Resource
	private ISimpleJdbcDao<ProceedingForm> simpleJdbcDao;
	
	@SuppressWarnings("unchecked")
	public List<ProceedingForm> getFormsList(String sql, Object[] param)
			throws Exception {
		List<ProceedingForm> list = simpleJdbcDao.queryForRow(sql, param,ProceedingForm.class);
		return list;
	}

	@Override
	public List<ProceedingForm> getFormsList(String sql) throws Exception {
		List<ProceedingForm> list = simpleJdbcDao.queryForRow(sql, ProceedingForm.class);
		return list;
	}
}
