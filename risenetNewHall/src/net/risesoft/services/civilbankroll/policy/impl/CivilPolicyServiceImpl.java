package net.risesoft.services.civilbankroll.policy.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import net.risesoft.beans.civilbankroll.CivilPolicyBean;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.civilbankroll.policy.ICivilPolicyService;
@Service
public class CivilPolicyServiceImpl extends BaseDaoImpl<CivilPolicyBean> implements ICivilPolicyService{

	public List<CivilPolicyBean> civilPolicyList(int rowNum) {
		LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("createtime", "desc");
		return getScrollData(0, rowNum, orderMap).getResultList();
	}

}
