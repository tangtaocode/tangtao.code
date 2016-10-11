package net.risesoft.actions.interaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.CodeMap;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.interaction.Consult;
import net.risesoft.services.interaction.IConsultService;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;
/**
 * 
  * @ClassName: ConsultAction
  * @Description: 网上咨询
  * @author Comsys-zhangkun
  * @date Jun 22, 2013 10:11:59 AM
  *
 */
@Controller
public class ConsultAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 5881372490307345122L;
	private String method;
	private List<CodeMap> departList = new ArrayList<CodeMap>();
	private String consultUser;//咨询人
	private String title;//咨询标题
	private String phone;//手机
	private String departGuid;
	private Date zxsj_S;//咨询时间开始
	private Date zxsj_E;//咨询时间结束
	private String guid;
	private Consult consult;
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource 
	private IConsultService consultService;
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<CodeMap> getDepartList() {
		return departList;
	}

	public void setDepartList(List<CodeMap> departList) {
		this.departList = departList;
	}

	public String getConsultUser() {
		return consultUser;
	}

	public void setConsultUser(String consultUser) {
		this.consultUser = consultUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getZxsj_S() {
		return zxsj_S;
	}

	public void setZxsj_S(Date zxsj_S) {
		this.zxsj_S = zxsj_S;
	}

	public Date getZxsj_E() {
		return zxsj_E;
	}

	public void setZxsj_E(Date zxsj_E) {
		this.zxsj_E = zxsj_E;
	}

	public ICodeMapUtil getCodeMapUtil() {
		return codeMapUtil;
	}

	public void setCodeMapUtil(ICodeMapUtil codeMapUtil) {
		this.codeMapUtil = codeMapUtil;
	}

	public String getDepartGuid() {
		return departGuid;
	}

	public void setDepartGuid(String departGuid) {
		this.departGuid = departGuid;
	}
	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Consult getConsult() {
		return consult;
	}

	public void setConsult(Consult consult) {
		this.consult = consult;
	}

	@Action(value = "/interaction/queryConsult", results = { 
			@Result(name = "input", location = "/WEB-INF/page/interaction/queryConsult.jsp"),
			@Result(name = "success", location = "/WEB-INF/page/interaction/resultConsult.jsp")})
	public String queryConsult(){
		if(StringUtils.isBlank(getMethod())){
			setDepartList(codeMapUtil.getListByType("罗湖区审批职能局"));
			return INPUT;
		}else{
			int maxResult = 10;
			ArrayList<String> whereSql = new ArrayList<String>();
			ArrayList<Object> param = new ArrayList<Object>();
			LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
			orderMap.put("consultationdate", "desc");
			if (StringUtils.isNotBlank(getTitle())) {
				whereSql.add("o.title like ?");
				param.add("%" + getTitle() + "%");
			}
			if(StringUtils.isNotBlank(getPhone())){
				whereSql.add("o.mobile = ?");
				param.add(getPhone());
			}
			if(StringUtils.isNotBlank(getConsultUser())){
				whereSql.add("o.username = ?");
				param.add(getConsultUser());
			}
			if(StringUtils.isNotBlank(getDepartGuid())){
				whereSql.add("o.bureau = ?");
				param.add(getDepartGuid());
			}
			if(getZxsj_S()!=null){
				whereSql.add("o.consultationdate > ?");
				param.add(getZxsj_S());
			}
			if(getZxsj_E()!=null){
				whereSql.add("o.consultationdate < ?");
				param.add(getZxsj_E());
			}
			try {
				PageView<Consult> pageView = new PageView<Consult>(maxResult,
						getPage());
				pageView.setQueryResult(consultService.getScrollData(pageView
						.getFirstResult(), maxResult, GenericsUtils.listToSArray(whereSql), GenericsUtils
						.listToArray(param),orderMap));
				pageView.addColumnMaps("bureau", codeMapUtil.getMapByType("罗湖区审批职能局和街道"));
				pageView.initColumn();
				getRequest().setAttribute("pageView", pageView);
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
	}
	@Action(value = "/interaction/findConsult", results = { 
			@Result(name = "input", location = "/WEB-INF/page/interaction/consult.jsp")})
	public String findConsultById(){
		try {
			Consult con = consultService.find(getGuid());
			con.setRecontent(WebUtil.filter(con.getRecontent()));
			setConsult(con);
			consult.setBureauname(codeMapUtil.getMapByType("罗湖区审批职能局和街道").get(consult.getBureau()));
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
