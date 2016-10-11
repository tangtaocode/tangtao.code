package net.risesoft.services.bizbankroll.apply.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.BizHistorySubsidize;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.apply.IBizHistorySubsidizeService;
@Service
public class BizHistorySubsidizeServiceImpl extends BaseDaoImpl<BizHistorySubsidize> implements IBizHistorySubsidizeService{
	@Resource
	ISimpleJdbcDao<BizHistorySubsidize> simpleJdbcDao;
	public List<BizHistorySubsidize> getSubsidize(String userGUID) {
		String sql = " select max(t.guid) guid,t.Projectname,t.subsidizedate from zjfc_kj_subsidize t where t.userguid=? group by t.Projectname,t.subsidizedate";
		return simpleJdbcDao.queryForRow(sql, new String[]{userGUID,"-1","-1"}, BizHistorySubsidize.class);
	}

}
