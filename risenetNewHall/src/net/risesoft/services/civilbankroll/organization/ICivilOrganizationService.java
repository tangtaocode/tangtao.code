package net.risesoft.services.civilbankroll.organization;

import net.risesoft.beans.civilbankroll.CivilOrganization;
import net.risesoft.daos.base.IBaseDao;

public interface ICivilOrganizationService extends IBaseDao<CivilOrganization>{
	public CivilOrganization findOrg(String orgCode,String key)throws Exception;
}
