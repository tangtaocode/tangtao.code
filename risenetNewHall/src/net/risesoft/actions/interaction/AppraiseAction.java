package net.risesoft.actions.interaction;


import java.sql.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.interaction.AppraiseBean;
import net.risesoft.beans.onlineservice.BusinessInfo;
import net.risesoft.services.interaction.IAppraiseService;
import net.risesoft.services.interaction.IDynamicStateService;

@Controller
@ParentPackage("default")//继承json包，用于返回json
public class AppraiseAction extends BaseActionSupport {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 9201152763793350079L;
	@Resource
	private IAppraiseService appraiseService;
	@Resource 
	private IDynamicStateService dynamicStateService;
	private AppraiseBean appraiseBean;
	private BusinessInfo businessInfo;
	private String declaresn;
	private String cardId;
	private String message;
	private String method;
	private String senateguid;
	private String satisfaction;
	private String yawp;
	private String yawpdescribe;
	private String problem;
	@JSON
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDeclaresn() {
		return declaresn;
	}

	public void setDeclaresn(String declaresn) {
		this.declaresn = declaresn;
	}



	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public AppraiseBean getAppraiseBean() {
		return appraiseBean;
	}

	public void setAppraiseBean(AppraiseBean appraiseBean) {
		this.appraiseBean = appraiseBean;
	}
	
	public BusinessInfo getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(BusinessInfo businessInfo) {
		this.businessInfo = businessInfo;
	}

	public String getSenateguid() {
		return senateguid;
	}

	public void setSenateguid(String senateguid) {
		this.senateguid = senateguid;
	}

	public String getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getYawp() {
		return yawp;
	}

	public void setYawp(String yawp) {
		this.yawp = yawp;
	}

	public String getYawpdescribe() {
		return yawpdescribe;
	}

	public void setYawpdescribe(String yawpdescribe) {
		this.yawpdescribe = yawpdescribe;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	
	@Action(value = "/interaction/interactionQuery", results = {
			@Result(name = "success",location = "/WEB-INF/page/interaction/resultPage.jsp"),
			@Result(name = "input",type="json")})
	public String getbusiInfo() {
		if (StringUtils.isNotBlank(getDeclaresn())
				&& StringUtils.isNotBlank(getCardId())&&StringUtils.isNotBlank(getMethod())&&"search".equals(getMethod())) {
			getAppraise();
			return SUCCESS;
		} else if(StringUtils.isNotBlank(getMethod())&&"doUpdate".equals(getMethod())){
			try {
				appraiseBean.setIssenate("1");
				appraiseBean.setSenatedatetime(new Date(System.currentTimeMillis()));
				appraiseBean.setZhengjiandaima(businessInfo.getCardId());
				appraiseBean.setType("3");
				appraiseBean.setStatedescription(businessInfo.getBlzt());
				appraiseService.update(appraiseBean);
				setCardId(businessInfo.getCardId());
				setDeclaresn(appraiseBean.getDeclaresn());
				setMessage("评价成功");
				return INPUT;
			} catch (Exception e) {
				e.printStackTrace();
				setMessage("评价失败");
				return INPUT;
			}
			
		}
		setMessage("未找到对应的业务信息。");
		return INPUT;
		
	}
	private void getAppraise(){
		String whereSql = "";
		String[] param = new String[4];
		whereSql += " and d.declaresn=? and d.zhengjiandaima=?";
		param[0] = getDeclaresn();
		param[1] = getCardId();
		param[2] = "-1";
		param[3] = "-1";
		String sql = "select d.approveitemname spsxmc,d.declaresn yxtywlsh,d.declarerperson sqdwhsqrxm,b.bureaucnfullname sljgmc,"
				+ "s.statedescription blzt,to_char(s.isfinished) isfinished,d.approveitemguid,d.zhengjiandaima cardId,d.employeedeptname,to_char(d.declaredatetime,'yyyy-MM-dd') slsj "
				+ " from office_spi_declareinfo d,spi_supervise s,spm_bureau b "
				+ " where d.declaresn=s.declaresn and s.bureauguid=b.bureauguid and s.state not in('0','20') "
				+ whereSql;
		try {
			setBusinessInfo(dynamicStateService.getBusinessInfo(sql, param));
			setAppraiseBean(appraiseService.getAppraiseBean(getDeclaresn()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
