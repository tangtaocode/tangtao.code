package net.risesoft.actions.civilbankroll.policy;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.civilbankroll.CivilPolicyBean;
import net.risesoft.services.civilbankroll.policy.ICivilPolicyService;
import net.risesoft.utils.base.GenericsUtils;

@Controller
public class CivilPolicyAction extends BaseActionSupport {
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 5496317841148236671L;
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private String title;
	private String guid;
	private CivilPolicyBean civilPolicyBean;
	@Resource
	private ICivilPolicyService civilPolicyService;

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

	
	public CivilPolicyBean getCivilPolicyBean() {
		return civilPolicyBean;
	}

	public void setCivilPolicyBean(CivilPolicyBean civilPolicyBean) {
		this.civilPolicyBean = civilPolicyBean;
	}

	@Action(value = "/civilPolicy/queryPage", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/policy/queryPage.jsp") })
	public String morePolicy() {
		return SUCCESS;
	}

	@Action(value = "/civilPolicy/resultPage", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/policy/resultPage.jsp") })
	public String queryPolicy() {
		int maxResult = 10;
		ArrayList<String> whereSql = new ArrayList<String>();
		ArrayList<Object> param = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getTitle())) {
			whereSql.add("o.title like ?");
			param.add("%" + getTitle() + "%");
		}

		try {
			PageView<CivilPolicyBean> pageView = new PageView<CivilPolicyBean>(
					maxResult, getPage());
			pageView.setQueryResult(civilPolicyService.getScrollData(pageView
					.getFirstResult(), maxResult, GenericsUtils
					.listToSArray(whereSql), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "/civilPolicy/findPolicy", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/policy/lawInfo.jsp") })
	public String findPolicy() {
		try {
			setCivilPolicyBean(civilPolicyService.find(getGuid()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

}
