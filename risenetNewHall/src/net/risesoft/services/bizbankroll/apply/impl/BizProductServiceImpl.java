package net.risesoft.services.bizbankroll.apply.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.BizProductBean;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.apply.IBizProductService;
@Service
public class BizProductServiceImpl extends BaseDaoImpl<BizProductBean> implements IBizProductService{
	@Resource
	ISimpleJdbcDao<BizProductBean> simpleJdbcDao;
	public List<BizProductBean> getProductList(String userGUID) {
		String sql = "select max(t.guid) guid, t.productname, t.SCALE from ZJFC_KJ_PRODUCT t where t.userguid = ? group by t.productname, t.SCALE";
		return simpleJdbcDao.queryForRow(sql, new String[]{userGUID,"-1","-1"}, BizProductBean.class);
	}

}
