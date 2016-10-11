package net.risesoft.services.civilbankroll.specialist.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.risesoft.beans.civilbankroll.CivilSpecialist;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.civilbankroll.specialist.ICivilSpecialistService;
@Service
public class CivilSpecialistServiceImpl extends BaseDaoImpl<CivilSpecialist> implements ICivilSpecialistService{

	public CivilSpecialist findSpecia(String idCard, String key) {
		List<CivilSpecialist> list = getScrollData(-1, -1, new String[]{"cardid=?","userkey=?"}, new String[]{idCard,key}).getResultList();
		if(list!=null&&list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}

}
