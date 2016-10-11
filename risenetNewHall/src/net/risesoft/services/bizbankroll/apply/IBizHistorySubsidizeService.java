package net.risesoft.services.bizbankroll.apply;

import java.util.List;

import net.risesoft.beans.bizbankroll.BizHistorySubsidize;
import net.risesoft.daos.base.IBaseDao;

public interface IBizHistorySubsidizeService extends IBaseDao<BizHistorySubsidize>{
	public List<BizHistorySubsidize> getSubsidize(String userGUID)throws Exception;
}
