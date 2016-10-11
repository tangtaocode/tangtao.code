package net.risesoft.actions.bizbankroll.apply;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.bizbankroll.BizApplicationBean;
import net.risesoft.beans.bizbankroll.BizIntroducerBean;
import net.risesoft.services.bizbankroll.apply.IBizIntroducerService;
import net.risesoft.services.bizbankroll.dynamic.IBizDynamicService;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("default")  //继承json包，用于返回json
@InterceptorRefs({@InterceptorRef("isLoginStack")})
public class QueryApplyAction extends BaseActionSupport{

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
	private String guid;
	private String message;
	private String status;
	private TreeMap<String, String> projectTypeMap;
	private TreeMap<String, String> projectStatus;
	private 
	@Resource
	IBizDynamicService bizDynamicService;
	@Resource
	IBizIntroducerService introducerService;
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
	public String getSqdw() {
		return sqdw;
	}
	public void setSqdw(String sqdw) {
		this.sqdw = sqdw;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TreeMap<String, String> getProjectTypeMap() {
		return projectTypeMap;
	}
	public void setProjectTypeMap(TreeMap<String, String> projectTypeMap) {
		this.projectTypeMap = projectTypeMap;
	}

	public TreeMap<String, String> getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(TreeMap<String, String> projectStatus) {
		this.projectStatus = projectStatus;
	}
	@Action(value = "/queryApply/queryApply", results = { @Result(name = "success", location = "/WEB-INF/page/businessfollow/bizbankroll/resultPage.jsp") })
	@LoginRequired(module = "bizApply")
	public String queryApply() {
		setProjectTypeMap(codeMapUtil.getKeyValueMap("select t.name value,t.guid as code from zjfc_xmlxinfo t where t.pguid=?", new String[]{"{AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA}","-1","-1"}));
		setProjectStatus(codeMapUtil.getMapByType("产业扶持-项目状态"));
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
		if (StringUtils.isNotBlank(getStatus())) {
			whereSql+=" and t.pressor_state = ? ";
			param.add(getStatus());
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
		String sql = "select  t.guid,t.SLBH,t.pro_name,t.department_name zgbmid,to_char(t.createtime,'yyyy-MM-dd HH24:mi:ss') sqsj," +
		" t.pressor_state,t.BQREMARK,t.STATE,t.xmlxroot from ( "+  
		" select distinct a.guid,a.SLBH,a.pro_name,y.department_name,a.createtime,decode(a.pressor_state,'2','办理中','20', "+
		" '部门初审','30','补齐补正','40','不予受理','50','不予扶持','60','科室评审','70','分管局领导审批','80', "+
		" '局长审批','90','分管区领导审批','100','批准办结','办理中') as pressor_state,a.sbztid as userguid,a.BQREMARK,a.pressor_state STATE," +
		"decode(a.xmlxroot,'{0A150177-0000-0000-606F-2D240000019A}','1','0') xmlxroot from "+ 
		" zjfc_application a,(select case when t1.department_guid='{00000000-0000-0000-0000-000000000000}'  "+
		" then t2.department_name else t1.department_name end as department_name,t2.department_guid  "+
		" from RISENET_DEPARTMENT t1,RISENET_DEPARTMENT t2 where t1.department_guid=t2.superior_guid ) y "+ 
		" where a.zgbmid=y.department_guid) t,  "+ 
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

	
	@Action(value = "/queryApply/queryIntroducer", results = { @Result(name = "success", location = "/WEB-INF/page/businessfollow/introducer/resultPage.jsp") })
	@LoginRequired(module = "bizApply")
	public String queryIntroducer() {
		setProjectStatus(codeMapUtil.getMapByType("引荐人申请-项目状态"));
		int maxResult = 10;
		String whereSql = "";
		ArrayList<Object> param = new ArrayList<Object>();
		
		if (StringUtils.isNotBlank(getSlbh())) {
			whereSql+=" and t.slbh = ? ";
			param.add(getSlbh().trim());
		}
		if (StringUtils.isNotBlank(getStatus())) {
			whereSql+=" and t.pressor_state = ? ";
			param.add(getStatus());
		}
		if (StringUtils.isNotBlank(getSqsj_S())) {
			whereSql+=" and to_char(t.subtime,'yyyy-MM-dd') >= ? ";
			param.add(getSqsj_S().trim());
		}
		if (StringUtils.isNotBlank(getSqsj_E())) {
			whereSql+=" and to_char(t.subtime,'yyyy-MM-dd') <= ? ";
			param.add(getSqsj_E().trim());
		}
		if(getUser()!=null){
			whereSql+=" and t.sbztid = ? ";
			param.add(getUserGUID());
		}
		
		String sql = " select decode(t.RESPONSE,null,'0','1') response,t.guid,t.yjrno,x.name category,t.subtime," +
		"decode(t.state,'1','未提交','2','提交待审批','3','审批通过','4','审批不通过') as stateStr," +
		"t.state,t.isSb from (select y.*,decode(z.guid,null,'0','1') as isSb from ZJFC_YJR y," +
		"zjfc_application z where z.guid(+)=y.guid ) t,zjfc_xmlxinfo x " +
		" where x.guid=t.category_id " +
		whereSql+
		"order by t.subtime desc";
		try {
			PageView<BizIntroducerBean> pageView = new PageView<BizIntroducerBean>(
					maxResult, getPage(),introducerService.getDataRows(sql, GenericsUtils.listToArray(param)));
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(introducerService.getMoreIntroducer(sql.toString(), GenericsUtils.listToArray(param)));
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
	@Action(value = "/queryApply/getResponseMess", results = { @Result(name = "success", location = "/WEB-INF/page/businessfollow/bizbankroll/response.jsp") })
	@LoginRequired(module = "bizApply")
	public String getMessages(){
		try {
			setMessage(WebUtil.filter(bizDynamicService.find(getGuid()).getBqremark()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	@Action(value = "/queryApply/getIntroducerMess", results = { @Result(name = "success", location = "/WEB-INF/page/businessfollow/introducer/response.jsp") })
	@LoginRequired(module = "bizApply")
	public String getIntroducerMess(){
		try {
			setMessage(WebUtil.filter(introducerService.find(getGuid()).getResponse()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
