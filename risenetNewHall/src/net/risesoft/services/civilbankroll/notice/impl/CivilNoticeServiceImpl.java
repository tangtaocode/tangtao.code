package net.risesoft.services.civilbankroll.notice.impl;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import net.risesoft.beans.civilbankroll.CivilNoticeBean;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.civilbankroll.notice.ICivilNoticeService;
@Service
public class CivilNoticeServiceImpl extends BaseDaoImpl<CivilNoticeBean> implements ICivilNoticeService{

	public List<CivilNoticeBean> civilNoticeList(int rowNum) {
		LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("createtime", "desc");
		return getScrollData(0, rowNum, new String[]{"o.endtime>=?","o.status=?"}, new Object[]{new Date(System.currentTimeMillis()),"1"}, orderMap).getResultList();
	}

}
