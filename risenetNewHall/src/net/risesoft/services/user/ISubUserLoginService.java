package net.risesoft.services.user;

import net.risesoft.beans.user.SubUserInfo;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.daos.base.IBaseDao;

public interface ISubUserLoginService extends IBaseDao<SubUserInfo> {
	SubUserInfo getUserByLoginName(String loginName)throws Exception;
}
