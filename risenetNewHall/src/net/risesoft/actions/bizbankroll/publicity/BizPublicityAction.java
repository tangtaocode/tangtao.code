package net.risesoft.actions.bizbankroll.publicity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.bizbankroll.BizApplicationBean;
import net.risesoft.beans.bizbankroll.BizComplainBean;
import net.risesoft.services.bizbankroll.dynamic.IBizDynamicService;
import net.risesoft.services.bizbankroll.publicity.IBizComplainService;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;
@Controller
public class BizPublicityAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -7532324821923910007L;
	private String projectName;
	private String slbh;
	private String projectType;
	private String sqdw;
	private String sqsj_S;
	private String sqsj_E;
	private String projectguid;
	private String message;
	private String url;
	private TreeMap<String, String> projectTypeMap;
	private BizComplainBean complainBean;
	@Resource
	IBizDynamicService bizDynamicService;
	@Resource 
	ICodeMapUtil codeMapUtil;
	@Resource
	IBizComplainService bizComplainService;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
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
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
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
	public String getSqdw() {
		return sqdw;
	}
	public void setSqdw(String sqdw) {
		this.sqdw = sqdw;
	}
	
	public String getProjectguid() {
		return projectguid;
	}
	public void setProjectguid(String projectguid) {
		this.projectguid = projectguid;
	}
	public BizComplainBean getComplainBean() {
		return complainBean;
	}
	public void setComplainBean(BizComplainBean complainBean) {
		this.complainBean = complainBean;
	}
	public TreeMap<String, String> getProjectTypeMap() {
		return projectTypeMap;
	}
	public void setProjectTypeMap(TreeMap<String, String> projectTypeMap) {
		this.projectTypeMap = projectTypeMap;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Action(value = "/bizPublicity/queryPage", results = { @Result(name = "success", location = "/WEB-INF/page/bizbankroll/publicity/queryPage.jsp") })
	public String morePolicy() {
		setProjectTypeMap(codeMapUtil.getKeyValueMap("select t.name value,t.guid as code from zjfc_xmlxinfo t where t.pguid=?", new String[]{"{AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA}","-1","-1"}));
		return SUCCESS;
	}

	@Action(value = "/bizPublicity/resultPage", results = { @Result(name = "success", location = "/WEB-INF/page/bizbankroll/publicity/resultPage.jsp") })
	public String queryPolicy() {
		int maxResult = 10;
		String whereSql = "";
		ArrayList<Object> param = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getProjectName())) {
			whereSql+=" and t.pro_name like ? ";
			param.add("%" + getProjectName().trim() + "%");
		}
		if (StringUtils.isNotBlank(getProjectType())) {
			whereSql+=" and t.xmlxroot = ? ";
			param.add(getProjectType().trim());
		}
		if (StringUtils.isNotBlank(getSlbh())) {
			whereSql+=" and t.slbh = ? ";
			param.add(getSlbh().trim());
		}
		if (StringUtils.isNotBlank(getSqdw())) {
			whereSql+=" and v.USERNAME like ? ";
			param.add("%" + getSqdw().trim() + "%");
		}
		if (StringUtils.isNotBlank(getSqsj_S())) {
			whereSql+=" and to_char(t.createtime,'yyyy-MM-dd') >= ? ";
			param.add(getSqsj_S().trim());
		}
		if (StringUtils.isNotBlank(getSqsj_E())) {
			whereSql+=" and to_char(t.createtime,'yyyy-MM-dd') <= ? ";
			param.add(getSqsj_E().trim());
		}
		//and sysdate<s.uptime+5
		String sql = "select t.guid,t.pro_name,t.slbh,d.department_name as zgbmid,v.USERNAME sbztid,x.name category from zjfc_application t,( " +
					" select case when t1.department_guid = '{00000000-0000-0000-0000-000000000000}' then " +
					" t2.department_name else t1.department_name end as department_name,t2.department_guid " +
					" from RISENET_DEPARTMENT t1, RISENET_DEPARTMENT t2 " +
					" where t1.department_guid = t2.superior_guid) d,VIEW_USER v,zjfc_sdapp s,zjfc_xmlxinfo x where " +
					" d.department_guid=t.zgbmid and s.appguid=t.guid  and t.sbztid=v.GUID and t.ispublic='1' and x.guid=t.xmlxroot" +
		whereSql+
		" order by t.sbztid,t.category,t.createtime desc";
		try {
			PageView<BizApplicationBean> pageView = new PageView<BizApplicationBean>(
					maxResult, getPage(),bizDynamicService.getDataRows(sql, GenericsUtils.listToArray(param)));
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(bizDynamicService.getMoreDynamic(sql.toString(), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "/bizPublicity/initComplain", results = { @Result(name = "success", location = "/WEB-INF/page/bizbankroll/publicity/Complain.jsp") })
	public String initComplain() {
		return SUCCESS;
	}
	@Action(value = "/bizPublicity/saveComplain",results={@Result(name="success",location="/WEB-INF/page/share/message.jsp")})
	public String saveComplain() {
		try {
			getComplainBean().setGuid(GUID.getGUID());
			getComplainBean().setComplaindate(new Date(System.currentTimeMillis()));
			getComplainBean().setProjectguid(getProjectguid());
			bizComplainService.save(getComplainBean());
			setMessage("投诉成功！");
			return SUCCESS;
		} catch (Exception e) {
			setMessage("投诉失败！");
			setUrl("/bizPublicity/initComplain.YS?projectguid="+getProjectguid());
			return SUCCESS;
		}
	}
}
