package net.risesoft.actions.law;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.onlineservice.LawsBean;
import net.risesoft.services.law.ILawsService;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

/**
 * @政策法规
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */

@SuppressWarnings("serial")
@Controller("LawsAction")
@Results( { @Result(name = "messages", location = "/WEB-INF/page/share/message.jsp")})
public class LawsAction extends BaseActionSupport {
	@Resource
	private ILawsService lawsService;
	@Resource
	private ICodeMapUtil codeMapUtil;
	private LawsBean lawsBean;
	private List<LawsBean> lawsList;
	private TreeMap<String, String> lawsTypeMap;
	private String lawsType;
	private String title;
	private String id;
	private String method;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LawsBean getLawsBean() {
		return lawsBean;
	}

	public void setLawsBean(LawsBean lawsBean) {
		this.lawsBean = lawsBean;
	}

	public List<LawsBean> getLawsList() {
		return lawsList;
	}

	public void setLawsList(List<LawsBean> lawsList) {
		this.lawsList = lawsList;
	}

	public String getLawsType() {
		return lawsType;
	}

	public void setLawsType(String lawsType) {
		this.lawsType = lawsType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Action(value = "/laws/lawsAction", results = { @Result(name = "findLaw", location = "/WEB-INF/page/govpublic/lawInfo.jsp") })
	public String selectLawsBean() throws Exception {
		lawsBean = lawsService.getLawsBeanById(getId());
		return "findLaw";
	}
	
	@Action(value = "/laws/moreLaws", 
			results = {@Result(name = "searchPage" ,location = "/WEB-INF/page/laws/queryPage.jsp")})
	public String moreLaws() {
			lawsTypeMap = codeMapUtil.getMapByType("行政权力_法规类型");
			return "searchPage";
	}
	@Action(value = "/laws/moreLawsQuery", 
			results = { @Result(name = "moreLaws", location = "/WEB-INF/page/laws/resultPage.jsp")})
	public String queryPage(){
		int maxResult = 10;
		ArrayList<String> whereSql = new ArrayList<String>();
		ArrayList<Object> param = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getTitle())) {
			whereSql.add("o.title like ?");
			param.add("%" + getTitle() + "%");
		}
		if (StringUtils.isNotBlank(getLawsType())) {
			whereSql.add("o.type= ? ");
			param.add(getLawsType());
		}
		try {
			PageView<LawsBean> pageView = new PageView<LawsBean>(maxResult,
					getPage());
			pageView.setQueryResult(lawsService.getMoreLaws(pageView
					.getFirstResult(), maxResult, GenericsUtils.listToSArray(whereSql), GenericsUtils
					.listToArray(param)));
			pageView.addColumnMaps("type", codeMapUtil.getMapByType("行政权力_法规类型"));
			pageView.initColumn();
			getRequest().setAttribute("pageView", pageView);
			return "moreLaws";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	public TreeMap<String, String> getLawsTypeMap() {
		return lawsTypeMap;
	}

	public void setLawsTypeMap(TreeMap<String, String> lawsTypeMap) {
		this.lawsTypeMap = lawsTypeMap;
	}
}
