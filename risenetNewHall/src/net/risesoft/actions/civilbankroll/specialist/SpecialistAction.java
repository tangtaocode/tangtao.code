package net.risesoft.actions.civilbankroll.specialist;

import java.sql.Date;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.civilbankroll.CivilSpecialist;
import net.risesoft.services.civilbankroll.specialist.ICivilSpecialistService;
import net.risesoft.utils.base.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("json-default")
public class SpecialistAction extends BaseActionSupport {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 1160664878591129093L;

	private String method;
	private String idCard;
	private String key;
	private String message;
	private String url;
	private CivilSpecialist civilSpecialist;
	@Resource
	private ICivilSpecialistService civilSpecialistService;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@JSON
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public CivilSpecialist getCivilSpecialist() {
		return civilSpecialist;
	}

	public void setCivilSpecialist(CivilSpecialist civilSpecialist) {
		this.civilSpecialist = civilSpecialist;
	}

	@Action(value = "/civilApply/initSpec", results = { @Result(name = "success", location = "/WEB-INF/page/civilbankroll/specialist/initSpec.jsp") })
	public String initSpec() {
		return SUCCESS;
	}

	@Action(value = "/civilApply/saveSpec", results = {
			@Result(name = "success", location = "/WEB-INF/page/civilbankroll/specialist/specialist.jsp"),
			@Result(name = "view", location = "/WEB-INF/page/civilbankroll/specialist/viewSpecialist.jsp"),
			@Result(name = "message", location = "/WEB-INF/page/share/message.jsp"),
			@Result(name = "json", type = "json")})
	public String saveSpec() {
		try {
			if ("todo".equals(getMethod())) {
				CivilSpecialist specialist = civilSpecialistService.findSpecia(
						getIdCard(), getKey());
				if (specialist == null) {
					setMessage("0");
					return "json";
				} else {
					if(StringUtils.isBlank(specialist.getName())){
						specialist.setCardid(getIdCard());
						specialist.setUserkey(getKey());
					}
					setCivilSpecialist(specialist);
					if("2".equals(specialist.getStatus())||"3".equals(specialist.getStatus())){
						specialist.setResume(WebUtil.filter(specialist.getResume()));
						specialist.setHonor(WebUtil.filter(specialist.getHonor()));
						return "view";
					}else
						return SUCCESS;
				}

			} else {
				CivilSpecialist specialist = getCivilSpecialist();
				try {
					specialist.setUpdatedate(new Date(System.currentTimeMillis()));
					civilSpecialistService.update(specialist);
					setMessage("更新成功！");
					return "message";
				} catch (Exception e) {
					setMessage("更新失败！");
					setUrl("/civilApply/saveSpec.YS?idCard=" + specialist.getCardid()
							+ "&key=" + specialist.getUserkey()+"&method=todo");
					return "message";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

	}

}
