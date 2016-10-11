package net.risesoft.actions.civilbankroll.dynamic;

import java.util.ArrayList;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.civilbankroll.CivilApplicationBean;
import net.risesoft.services.civilbankroll.dynamic.ICivilDynamicService;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

@Controller
public class CivilDynamicAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1639163157444094498L;
	private String projectName;
	private String slbh;
	private String sszt;
	private String sqsj_S;
	private String sqsj_E;
	private 
	@Resource
	ICivilDynamicService civilDynamicService;
	@Resource 
	ICodeMapUtil codeMapUtil;
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
	
	
	public String getSszt() {
		return sszt;
	}
	public void setSszt(String sszt) {
		this.sszt = sszt;
	}
	
	@Action(value = "/civilDynamic/queryPage", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/dynamic/queryPage.jsp") })
	public String morePolicy() {
		return SUCCESS;
	}

	@Action(value = "/civilDynamic/resultPage", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/dynamic/resultPage.jsp") })
	public String queryDynamic() {
		int maxResult = 10;
		String whereSql = "";
		ArrayList<Object> param = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getProjectName())) {
			whereSql+=" and a.xmname like ? ";
			param.add("%" + getProjectName().trim() + "%");
		}
		if (StringUtils.isNotBlank(getSlbh())) {
			whereSql+=" and a.slbh = ? ";
			param.add(getSlbh().trim());
		}
		if (StringUtils.isNotBlank(getSszt())) {
			whereSql+=" and a.sszt like ? ";
			param.add("%" + getSszt().trim() + "%");
		}
		if (StringUtils.isNotBlank(getSqsj_S())) {
			whereSql+=" and to_char(a.ssfbsj,'yyyy-MM-dd') >= ? ";
			param.add(getSqsj_S().trim());
		}
		if (StringUtils.isNotBlank(getSqsj_E())) {
			whereSql+=" and to_char(a.ssfbsj,'yyyy-MM-dd') <= ? ";
			param.add(getSqsj_E().trim());
		}
		if(getUser()!=null){
			whereSql+=" and a.sbztid = ? ";
			param.add(getUserGUID());
		}
		String sql = "select a.SLBH,o.name xmlxname,a.xmname,q.USERNAME sbztid,a.fzr, "+
				" to_char(a.SSFBSJ, 'yyyy-MM-dd') createtimeStr,a.SSZT "+
				" from msxm_application a,(select t.name, p.id, t.id as projectId "+
				" from msxm_type t, msxm_type_project p where t.id = p.typeguid) o, view_user q "+
				" where q.guid = a.sbztid and a.SSSTATE = '1' and o.id = a.xmlxguid  " +
		whereSql+
		" order by a.createtime desc";
		try {
			PageView<CivilApplicationBean> pageView = new PageView<CivilApplicationBean>(
					maxResult, getPage(),civilDynamicService.getDataRows(sql, GenericsUtils.listToArray(param)));
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(civilDynamicService.moreDynamic(sql.toString(), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
}
