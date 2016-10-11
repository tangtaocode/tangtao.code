package net.risesoft.services.bizbankroll.notice.impl;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.BizNoticeBean;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.notice.IBizNoticeService;
@Service
public class BizNoticeServiceImpl extends BaseDaoImpl<BizNoticeBean> implements IBizNoticeService{
	@Override
	public List<BizNoticeBean> getBizNoticeList(int rowNum) {
		LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("starttime", "desc");
		return getScrollData(0, rowNum, new String[]{"o.endtime>=?","ntype=?"}, new Object[]{new Date(System.currentTimeMillis()),"0"}, orderMap).getResultList();
	}

	@Override
	public List<BizNoticeBean> getBizPolicyList(int rowNum) {
		LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("starttime", "desc");
		return getScrollData(0, rowNum, new String[]{"o.endtime>=?","ntype=?"}, new Object[]{new Date(System.currentTimeMillis()),"1"}, orderMap).getResultList();
	}

}
