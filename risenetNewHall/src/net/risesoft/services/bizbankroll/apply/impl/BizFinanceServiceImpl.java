package net.risesoft.services.bizbankroll.apply.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.BizFinanceBean;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.apply.IBizFinanceService;
@Service
public class BizFinanceServiceImpl extends BaseDaoImpl<BizFinanceBean> implements IBizFinanceService{
	@Resource
	ISimpleJdbcDao<BizFinanceBean> simpleJdbcDao;
	public List<BizFinanceBean> getShareFinance(String userGUID) {
		String sql = "select max(t.guid) guid,t.audittime from zjfc_kj_finance t where t.userguid=? group by t.audittime";
		return simpleJdbcDao.queryForRow(sql, new String[]{userGUID,"-1","-1"}, BizFinanceBean.class);
	}

}
