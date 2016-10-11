package net.risesoft.actions.interaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.interaction.Complain;
import net.risesoft.services.interaction.IComplainService;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;
/**
 * 
  * @ClassName: ComplainAction
  * @Description: 网上投诉
  * @author Comsys-zhangkun
  * @date Jun 22, 2013 10:12:19 AM
  *
 */
@Controller
public class ComplainAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -3742928191928968719L;
	private String method;
	private String complainUser;//咨询人
	private String title;//咨询标题
	private String phone;//手机
	private Date tssj_S;//咨询时间开始
	private Date tssj_E;//咨询时间结束
	private String guid;
	private Complain complain;
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource 
	private IComplainService complainService;
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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

	public ICodeMapUtil getCodeMapUtil() {
		return codeMapUtil;
	}

	public void setCodeMapUtil(ICodeMapUtil codeMapUtil) {
		this.codeMapUtil = codeMapUtil;
	}

	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	

	public String getComplainUser() {
		return complainUser;
	}

	public void setComplainUser(String complainUser) {
		this.complainUser = complainUser;
	}

	public Date getTssj_S() {
		return tssj_S;
	}

	public void setTssj_S(Date tssj_S) {
		this.tssj_S = tssj_S;
	}

	public Date getTssj_E() {
		return tssj_E;
	}

	public void setTssj_E(Date tssj_E) {
		this.tssj_E = tssj_E;
	}

	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	@Action(value = "/interaction/queryComplain", results = { 
			@Result(name = "input", location = "/WEB-INF/page/interaction/queryComplain.jsp"),
			@Result(name = "success", location = "/WEB-INF/page/interaction/resultComplain.jsp")})
	public String queryComplain(){
		if(StringUtils.isBlank(getMethod())){
			return INPUT;
		}else{
			int maxResult = 10;
			ArrayList<String> whereSql = new ArrayList<String>();
			ArrayList<Object> param = new ArrayList<Object>();
			LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
			orderMap.put("complaindate", "desc");
			if (StringUtils.isNotBlank(getTitle())) {
				whereSql.add("o.title like ?");
				param.add("%" + getTitle() + "%");
			}
			if(StringUtils.isNotBlank(getPhone())){
				whereSql.add("o.mobile = ?");
				param.add(getPhone());
			}
			if(StringUtils.isNotBlank(getComplainUser())){
				whereSql.add("o.username = ?");
				param.add(getComplainUser());
			}
			if(getTssj_S()!=null){
				whereSql.add("o.complaindate > ?");
				param.add(getTssj_S());
			}
			if(getTssj_E()!=null){
				whereSql.add("o.complaindate < ?");
				param.add(getTssj_E());
			}
			try {
				PageView<Complain> pageView = new PageView<Complain>(maxResult,
						getPage());
				pageView.setQueryResult(complainService.getScrollData(pageView
						.getFirstResult(), maxResult, GenericsUtils.listToSArray(whereSql), GenericsUtils
						.listToArray(param),orderMap));
				getRequest().setAttribute("pageView", pageView);
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
	}
	@Action(value = "/interaction/findComplain", results = { 
			@Result(name = "input", location = "/WEB-INF/page/interaction/complain.jsp")})
	public String findComplainById(){
		try {
			setComplain(complainService.find(getGuid()));
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
