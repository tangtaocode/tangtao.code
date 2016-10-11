package net.risesoft.services.user;

import net.risesoft.beans.user.CompanyUser;
import net.risesoft.beans.user.PersonUser;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.daos.base.IBaseDao;
/**
 * 
  * @ClassName: IUserLoginService
  * @Description: 用户登录接口
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:20:13 PM
  *
 */
public interface IUserLoginService extends IBaseDao<UserInfo>{
	public UserInfo getUserByLoginName(String loginName)throws Exception;
	public CompanyUser getCompanyUser(String loginGUID)throws Exception;
	public CompanyUser getOrgInfo(String OrgId)throws Exception;
	public PersonUser getPersonUser(String loginGUID)throws Exception;
	public String getLoginNameById(String guid)throws Exception;
	public String getPwdById(String guid) throws Exception;
}
