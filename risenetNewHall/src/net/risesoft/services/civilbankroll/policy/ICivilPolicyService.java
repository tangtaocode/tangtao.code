package net.risesoft.services.civilbankroll.policy;

import java.util.List;

import net.risesoft.beans.civilbankroll.CivilPolicyBean;
import net.risesoft.daos.base.IBaseDao;

public interface ICivilPolicyService extends IBaseDao<CivilPolicyBean>{
	public List<CivilPolicyBean> civilPolicyList(int rowNum)throws Exception;
	
}
