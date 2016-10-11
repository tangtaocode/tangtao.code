package net.risesoft.actions.civilbankroll.publicity;

import java.sql.Date;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.civilbankroll.CivilApplicationBean;
import net.risesoft.beans.civilbankroll.CivilComplainBean;
import net.risesoft.services.civilbankroll.dynamic.ICivilDynamicService;
import net.risesoft.services.civilbankroll.publicity.ICivilComplainService;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;
@Controller
public class CivilPublicityAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -4434402653805144765L;
	private String projectName;
	private String slbh;
	private String sszt;
	private String sqsj_S;
	private String sqsj_E;
	private String projectguid;
	private String message;
	private String url;
	private CivilComplainBean civilComplainBean;
	@Resource
	ICivilDynamicService civilDynamicService;
	@Resource 
	ICodeMapUtil codeMapUtil;
	@Resource
	ICivilComplainService civilComplainService;
	
	
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
	public String getProjectguid() {
		return projectguid;
	}
	public void setProjectguid(String projectguid) {
		this.projectguid = projectguid;
	}
	
	
	public CivilComplainBean getCivilComplainBean() {
		return civilComplainBean;
	}
	public void setCivilComplainBean(CivilComplainBean civilComplainBean) {
		this.civilComplainBean = civilComplainBean;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Action(value = "/civilPublicity/queryPage", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/publicity/queryPage.jsp") })
	public String morePolicy() {
		return SUCCESS;
	}

	@Action(value = "/civilPublicity/resultPage", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/publicity/resultPage.jsp") })
	public String queryPolicy() {
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
		if (StringUtils.isNotBlank(getSszt())) {
			whereSql+=" and t.sszt like ? ";
			param.add("%" + getSszt().trim() + "%");
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
		String sql = "select t.xmguid, t.xmname, t.slbh, decode(t.fctype,'1','购买服务','2','配套扶持') fctype, " +
 		"t.xmlxname, v.USERNAME sbztid  from msxm_application t, VIEW_USER v," +
 		"(select mt.name,mp.id from msxm_type mt,msxm_type_project mp where mt.id=mp.typeguid) pro " +
 		"where t.sbztid = v.GUID and t.xmlxguid=pro.id(+) " +
 		"and t.ispublic = '1' "+
			whereSql+
		" order by t.sbztid, t.fctype, t.xmlxname, t.createtime desc";
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
	
	@Action(value = "/civilPublicity/initComplain", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/publicity/Complain.jsp") })
	public String initComplain() {
		return SUCCESS;
	}
	@Action(value = "/civilPublicity/saveComplain",results={@Result(name="success",location="/WEB-INF/page/share/message.jsp")})
	public String saveComplain() {
		try {
			getCivilComplainBean().setGuid(GUID.getGUID());
			getCivilComplainBean().setComplaindate(new Date(System.currentTimeMillis()));
			getCivilComplainBean().setProjectguid(getProjectguid());
			civilComplainService.save(getCivilComplainBean());
			setMessage("投诉成功！");
			return SUCCESS;
		} catch (Exception e) {
			setMessage("投诉失败！");
			setUrl("/civilPublicity/initComplain.YS?projectguid="+getProjectguid());
			return SUCCESS;
		}
	}
}
