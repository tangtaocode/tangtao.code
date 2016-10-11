package net.risesoft.services.civilbankroll.organization.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.risesoft.beans.civilbankroll.CivilOrganization;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.civilbankroll.organization.ICivilOrganizationService;
@Service
public class CivilOrganizationServiceImpl extends BaseDaoImpl<CivilOrganization> implements ICivilOrganizationService{

	public CivilOrganization findOrg(String orgCode, String key) {
		List<CivilOrganization> list = getScrollData(-1, -1, new String[]{"",""}, new String[]{orgCode,key}).getResultList();
		if(list!=null&&list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}

}
