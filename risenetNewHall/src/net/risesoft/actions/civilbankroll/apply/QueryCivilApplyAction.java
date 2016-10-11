package net.risesoft.actions.civilbankroll.apply;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.civilbankroll.CivilApplicationBean;
import net.risesoft.beans.civilbankroll.CivilFundingRecode;
import net.risesoft.services.civilbankroll.apply.ICivilFundingRecodeService;
import net.risesoft.services.civilbankroll.dynamic.ICivilDynamicService;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.DateFormatUtil;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("default")  //继承json包，用于返回json
@InterceptorRefs({@InterceptorRef("isLoginStack")})
public class QueryCivilApplyAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 7429402113718229823L;
	private String projectName;
	private String slbh;
	private String sqsj_S;
	private String sqsj_E;
	private String guid;
	private String message;
	private String status;
	private String singGUID;
	private String url;
	private TreeMap<String, String> projectStatus;
	private List<CivilFundingRecode> fundRecList;
	@Resource
	private ICivilDynamicService civilDynamicService;
	@Resource 
	private ICodeMapUtil codeMapUtil;
	@Resource 
	private ICivilFundingRecodeService civilFundingRecodeService;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getSlbh() {
		return slbh;
	}
	public void setSlbh(String slbh) {
		this.slbh = slbh;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getSqsj_S() {
		return sqsj_S;
	}
	public void setSqsj_S(String sqsj_S) {
		this.sqsj_S = sqsj_S;
	}
	public String getSqsj_E() {
		return sqsj_E;
	}
	public void setSqsj_E(String sqsj_E) {
		this.sqsj_E = sqsj_E;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@JSON
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public TreeMap<String, String> getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(TreeMap<String, String> projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public List<CivilFundingRecode> getFundRecList() {
		return fundRecList;
	}
	public void setFundRecList(List<CivilFundingRecode> fundRecList) {
		this.fundRecList = fundRecList;
	}
	
	public String getSingGUID() {
		return singGUID;
	}
	public void setSingGUID(String singGUID) {
		this.singGUID = singGUID;
	}
	@Action(value = "/civilApply/queryApplyFlow", results = { @Result(name = "success", location = "/WEB-INF/page/businessfollow/civilbankroll/resultPage.jsp") })
	@LoginRequired(module = "bizApply")
	public String queryApply() {
		setProjectStatus(codeMapUtil.getMapByType("民生创新-项目状态"));
		int maxResult = 10;
		String whereSql = "";
		ArrayList<Object> param = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getProjectName())) {
			whereSql+=" and t.xmname like ? ";
			param.add("%" + getProjectName().trim() + "%");
		}
		if (StringUtils.isNotBlank(getSlbh())) {
			whereSql+=" and t.slbh = ? ";
			param.add(getSlbh().trim());
		}
		if (StringUtils.isNotBlank(getStatus())) {
			whereSql+=" and t.pressor_state = ? ";
			param.add(getStatus());
		}
		if (StringUtils.isNotBlank(getSqsj_S())) {
			whereSql+=" and to_char(t.createtime,'yyyy-MM-dd') >= ? ";
			param.add(getSqsj_S().trim());
		}
		if (StringUtils.isNotBlank(getSqsj_E())) {
			whereSql+=" and to_char(t.createtime,'yyyy-MM-dd') <= ? ";
			param.add(getSqsj_E().trim());
		}
		if(getUser()!=null){
			whereSql+=" and t.sbztid = ? ";
			param.add(getUserGUID());
		}
		String sql = "select t.slbh,t.xmname,t.xmguid,t.xmlxname, t.fctype, t.fzr, c.value as stateValue," +
				" t.pressor_state,t.createtime,t.bqremark,decode(count(f.xmguid),0,'no','yes') as isSign from msxm_APPLICATION t,xzql_codemap c,msxm_fundingrecode f " +
				" where c.type='民生创新-项目状态' and c.code(+)=t.pressor_state and f.xmguid(+)=t.xmguid "+
		whereSql+
		" group by t.slbh,t.xmname,t.xmguid,t.xmlxname, t.fctype, t.fzr, c.value,t.pressor_state,t.createtime,t.bqremark order by t.createtime desc ";
		try {
			PageView<CivilApplicationBean> pageView = new PageView<CivilApplicationBean>(
					maxResult, getPage(),civilDynamicService.getDataRows(sql, GenericsUtils.listToArray(param)));
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(civilDynamicService.moreDynamic(sql.toString(), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "/civilApply/getResponseMess", results = { @Result(name = "success", location = "/WEB-INF/page/businessfollow/civilbankroll/response.jsp") })
	@LoginRequired(module = "civilApply")
	public String getMessages(){
		try {
			setMessage(WebUtil.filter(civilDynamicService.find(getGuid()).getBqremark()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Action(value = "/civilApply/querySignIn", results = { @Result(name = "success", location = "/WEB-INF/page/businessfollow/civilbankroll/moneySignIn.jsp") })
	@LoginRequired(module = "civilApply")
	public String moneySignIn(){
		try {
			setFundRecList(civilFundingRecodeService.findByProperty("xmguid", getGuid()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	@Action(value = "/civilApply/SignIn", results = { @Result(name = "success", type = "json") })
	@LoginRequired(module = "civilApply")
	public String SignIn(){
		try {
			CivilFundingRecode fr = civilFundingRecodeService.find(getSingGUID());
			fr.setStatus("已签收");
			fr.setPayeetime(new Date(System.currentTimeMillis()));
			civilFundingRecodeService.update(fr);
			setMessage(DateFormatUtil.parseToStrMin(new Date(System.currentTimeMillis())));
		} catch (Exception e) {
			setMessage("0");
		}
		return SUCCESS;
	}
}
