package net.risesoft.actions.bizbankroll.dynamic;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.bizbankroll.BizApplicationBean;
import net.risesoft.services.bizbankroll.dynamic.IBizDynamicService;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

@Controller
public class BizDynamicAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 7429402113718229823L;
	private String projectName;
	private String slbh;
	private String projectType;
	private String sqdw;
	private String sqsj_S;
	private String sqsj_E;
	private TreeMap<String, String> projectTypeMap;
	private 
	@Resource
	IBizDynamicService bizDynamicService;
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
	
	public TreeMap<String, String> getProjectTypeMap() {
		return projectTypeMap;
	}
	public void setProjectTypeMap(TreeMap<String, String> projectTypeMap) {
		this.projectTypeMap = projectTypeMap;
	}
	@Action(value = "/bizDynamic/queryPage", results = { @Result(name = "success", location = "/WEB-INF/page/bizbankroll/dynamic/queryPage.jsp") })
	public String morePolicy() {
		setProjectTypeMap(codeMapUtil.getKeyValueMap("select t.name value,t.guid as code from zjfc_xmlxinfo t where t.pguid=?", new String[]{"{AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA}","-1","-1"}));
		return SUCCESS;
	}

	@Action(value = "/bizDynamic/resultPage", results = { @Result(name = "success", location = "/WEB-INF/page/bizbankroll/dynamic/resultPage.jsp") })
	public String queryDynamic() {
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
			whereSql+=" and q.username like ? ";
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
		if(getUser()!=null){
			whereSql+=" and t.userguid = ? ";
			param.add(getUserGUID());
		}
		String sql = "select  t.SLBH,t.pro_name,t.department_name zgbmid,t.createtime," +
		" t.pressor_state,q.username sbztid from ( "+  
		" select distinct a.SLBH,a.pro_name,y.department_name,a.createtime,a.xmlxroot,decode(a.pressor_state,'2','办理中','20', "+
		" '部门初审','30','补齐补正','40','不予受理','50','不予扶持','60','科室评审','70','分管局领导审批','80', "+
		" '局长审批','90','分管区领导审批','100','批准办结','办理中') as pressor_state,a.sbztid as userguid from "+ 
		" zjfc_application a,(select case when t1.department_guid='{00000000-0000-0000-0000-000000000000}'  "+
		" then t2.department_name else t1.department_name end as department_name,t2.department_guid  "+
		" from RISENET_DEPARTMENT t1,RISENET_DEPARTMENT t2 where t1.department_guid=t2.superior_guid ) y "+ 
		" where a.zgbmid=y.department_guid and a.pressor_state!='1') t,  "+ 
		" ( select c.logonguid,c.ename as username from t_out_companyuser c union all "+ 
		" select p.logonguid ,p.true_name as username from t_out_personuser p) q where q.logonguid=t.userguid " +
		whereSql+
		"order by t.createtime desc";
		try {
			PageView<BizApplicationBean> pageView = new PageView<BizApplicationBean>(
					maxResult, getPage(),bizDynamicService.getDataRows(sql, GenericsUtils.listToArray(param)));
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(bizDynamicService.getMoreDynamic(sql.toString(), GenericsUtils.listToArray(param)));
			/**
			 * request.setAttribute("pageView", pageView)中key尽量为pageView，不然需要修改代码
			 */
			getRequest().setAttribute("pageView", pageView);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
}
