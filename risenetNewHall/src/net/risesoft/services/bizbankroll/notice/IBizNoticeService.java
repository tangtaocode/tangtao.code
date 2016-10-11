package net.risesoft.services.bizbankroll.notice;

import java.util.List;

import net.risesoft.beans.bizbankroll.BizNoticeBean;
import net.risesoft.daos.base.IBaseDao;

public interface IBizNoticeService extends IBaseDao<BizNoticeBean>{
	public List<BizNoticeBean> getBizNoticeList(int rowNum)throws Exception;
	public List<BizNoticeBean> getBizPolicyList(int rowNum)throws Exception;
	
}
