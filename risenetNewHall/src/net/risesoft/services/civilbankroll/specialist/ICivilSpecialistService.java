package net.risesoft.services.civilbankroll.specialist;

import net.risesoft.beans.civilbankroll.CivilSpecialist;
import net.risesoft.daos.base.IBaseDao;

public interface ICivilSpecialistService extends IBaseDao<CivilSpecialist>{
	public CivilSpecialist findSpecia(String idCard,String key)throws Exception;
}
