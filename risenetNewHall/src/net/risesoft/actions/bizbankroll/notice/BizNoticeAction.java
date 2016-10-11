package net.risesoft.actions.bizbankroll.notice;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.bizbankroll.BizNoticeBean;
import net.risesoft.services.bizbankroll.notice.IBizNoticeService;
import net.risesoft.utils.base.GenericsUtils;
@Controller
public class BizNoticeAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 4581305777187416665L;
	private String title;
	private String guid;
	private String ntype;
	private BizNoticeBean bizNoticeBean;
	@Resource
	private IBizNoticeService bizNoticeService;

	public String getNtype() {
		return ntype;
	}

	public void setNtype(String ntype) {
		this.ntype = ntype;
	}

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

	


	public BizNoticeBean getBizNoticeBean() {
		return bizNoticeBean;
	}

	public void setBizNoticeBean(BizNoticeBean bizNoticeBean) {
		this.bizNoticeBean = bizNoticeBean;
	}

	@Action(value = "/bizNotice/queryPage", results = { 
			@Result(name = "notice", location = "/WEB-INF/page/bizbankroll/notice/queryPage.jsp"),
			@Result(name = "law", location = "/WEB-INF/page/bizbankroll/policy/queryPage.jsp")})
	public String morePolicy() {
		if("0".equals(getNtype())){
			return "notice";
		}else if("1".equals(getNtype())){
			return "law";
		}
		return SUCCESS;
	}

	@Action(value = "/bizNotice/resultPage", results = { 
			@Result(name = "notice", location = "/WEB-INF/page/bizbankroll/notice/resultPage.jsp"),
			@Result(name = "law", location = "/WEB-INF/page/bizbankroll/policy/resultPage.jsp")})
	public String queryPolicy() {
		int maxResult = 10;
		ArrayList<String> whereSql = new ArrayList<String>();
		ArrayList<Object> param = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getTitle())) {
			whereSql.add("o.title like ?");
			param.add("%" + getTitle() + "%");
		}
		if (StringUtils.isNotBlank(getNtype())) {
			whereSql.add("o.ntype = ?");
			param.add(getNtype());
		}
		try {
			PageView<BizNoticeBean> pageView = new PageView<BizNoticeBean>(
					maxResult, getPage());
			pageView.setQueryResult(bizNoticeService.getScrollData(pageView
					.getFirstResult(), maxResult, GenericsUtils
					.listToSArray(whereSql), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * request.setAttribute("pageView", pageView)中key尽量为pageView，不然需要修改代码
		 */
		
		if("0".equals(getNtype())){
			return "notice";
		}else if("1".equals(getNtype())){
			return "law";
		}
		return SUCCESS;
	}

	@Action(value = "/bizNotice/findNotice", results = { 
			@Result(name = "notice", location = "/WEB-INF/page/bizbankroll/notice/noticeInfo.jsp") ,
			@Result(name = "law", location = "/WEB-INF/page/bizbankroll/policy/lawInfo.jsp") })
	public String findNotice() {
		try {
			setBizNoticeBean(bizNoticeService.find(getGuid()));
			if("0".equals(getNtype())){
				return "notice";
			}else if("1".equals(getNtype())){
				return "law";
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	

}
