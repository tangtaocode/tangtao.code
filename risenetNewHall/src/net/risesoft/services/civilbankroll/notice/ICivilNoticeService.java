package net.risesoft.services.civilbankroll.notice;

import java.util.List;

import net.risesoft.beans.civilbankroll.CivilNoticeBean;
import net.risesoft.daos.base.IBaseDao;

public interface ICivilNoticeService extends IBaseDao<CivilNoticeBean>{
	public List<CivilNoticeBean> civilNoticeList(int rowNum)throws Exception;
}
