package net.risesoft.services.user.impl;

import org.springframework.stereotype.Service;

import net.risesoft.beans.user.PersonUser;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.user.IPersonUserService;
/**
 * 
  * @ClassName: PersonUserServiceImpl
  * @Description: 个人service
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:19:02 PM
  *
 */
@Service
public class PersonUserServiceImpl extends BaseDaoImpl<PersonUser> implements IPersonUserService {

}
