package net.risesoft.actions.civilbankroll.notice;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.civilbankroll.CivilNoticeBean;
import net.risesoft.services.civilbankroll.notice.ICivilNoticeService;
import net.risesoft.utils.base.GenericsUtils;
@Controller
public class CivilNoticeAction extends BaseActionSupport{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -247105014769267229L;
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private String title;
	private String guid;
	private CivilNoticeBean civilNoticeBean;
	@Resource
	private ICivilNoticeService civilNoticeService;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	

	public CivilNoticeBean getCivilNoticeBean() {
		return civilNoticeBean;
	}

	public void setCivilNoticeBean(CivilNoticeBean civilNoticeBean) {
		this.civilNoticeBean = civilNoticeBean;
	}

	@Action(value = "/civilNotice/queryPage", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/notice/queryPage.jsp") })
	public String morePolicy() {
		return SUCCESS;
	}

	@Action(value = "/civilNotice/resultPage", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/notice/resultPage.jsp") })
	public String queryPolicy() {
		int maxResult = 10;
		ArrayList<String> whereSql = new ArrayList<String>();
		ArrayList<Object> param = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getTitle())) {
			whereSql.add("o.title like ?");
			param.add("%" + getTitle() + "%");
		}

		try {
			PageView<CivilNoticeBean> pageView = new PageView<CivilNoticeBean>(
					maxResult, getPage());
			pageView.setQueryResult(civilNoticeService.getScrollData(pageView
					.getFirstResult(), maxResult, GenericsUtils
					.listToSArray(whereSql), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "/civilNotice/findNotice", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/notice/noticeInfo.jsp") })
	public String findNotice() {
		try {
			setCivilNoticeBean(civilNoticeService.find(getGuid()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
