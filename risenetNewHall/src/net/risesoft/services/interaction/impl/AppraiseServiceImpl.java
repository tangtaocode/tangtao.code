package net.risesoft.services.interaction.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import net.risesoft.beans.interaction.AppraiseBean;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.interaction.IAppraiseService;
@Service
public class AppraiseServiceImpl extends BaseDaoImpl<AppraiseBean> implements IAppraiseService{
	public AppraiseBean getAppraiseBean(String declaresn) {
		List<AppraiseBean> list = getScrollData(-1, -1, new String[]{" o.declaresn=?"}, new String[]{declaresn}).getResultList();
		if(list==null||list.size()==0)return null;
		else return list.get(0);
	}
	
}
