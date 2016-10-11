package net.risesoft.services.bizbankroll.apply.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.BizShareholderBean;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.apply.IBizShareholderService;
@Service
public class BizShareholderServiceImpl extends BaseDaoImpl<BizShareholderBean> implements IBizShareholderService{
	@Resource
	ISimpleJdbcDao<BizShareholderBean> simpleJdbcDao;
	public List<BizShareholderBean> getShareholderList(String userGUID) {
		String sql = "select max(t.guid) guid,t.stockholdername,t.stockholdermoney from zjfc_kj_stockholder t where t.userguid=? group by t.stockholdername ,t.stockholdermoney";
		return simpleJdbcDao.queryForRow(sql, new String[]{userGUID,"-1","-1"}, BizShareholderBean.class);
	}
	
}
