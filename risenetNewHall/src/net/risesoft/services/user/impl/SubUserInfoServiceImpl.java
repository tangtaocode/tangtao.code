package net.risesoft.services.user.impl;

import java.util.List;

import net.risesoft.beans.user.SubUserInfo;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.user.ISubUserLoginService;

import org.springframework.stereotype.Service;

@Service
public class SubUserInfoServiceImpl extends BaseDaoImpl<SubUserInfo> implements
		ISubUserLoginService {

	@Override
	public SubUserInfo getUserByLoginName(String username) throws Exception {
		List<SubUserInfo> list =  findByProperty("username", username);
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
