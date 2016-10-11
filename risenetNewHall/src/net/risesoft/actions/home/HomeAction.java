package net.risesoft.actions.home;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.CodeMap;
import net.risesoft.beans.govpublic.WebStarRrating;
import net.risesoft.beans.onlineservice.BureauBean;
import net.risesoft.services.govpublic.IBureauService;
import net.risesoft.services.govpublic.IWebStarRratingService;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.WebUtil;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

/**
 * 
 * @ClassName: HomeAction
 * @Description: 审批大厅首页Action
 * @author Comsys-zhangkun
 * @date May 15, 2013 2:33:27 PM
 * 
 */

@Controller
@ParentPackage("default")
public class HomeAction extends BaseActionSupport {
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 6374442653916693089L;
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource
	private IBureauService bureauService;
	@Resource
	private IWebStarRratingService webStarRratingService;
	private String streetGuid;
	private BureauBean bureau;
	private List<CodeMap> menuList = new ArrayList<CodeMap>();
	private String appraise;

	public String getAppraise() {
		return appraise;
	}

	public void setAppraise(String appraise) {
		this.appraise = appraise;
	}

	public BureauBean getBureau() {
		return bureau;
	}

	public void setBureau(BureauBean bureau) {
		this.bureau = bureau;
	}

	public String getStreetGuid() {
		return streetGuid;
	}

	public void setStreetGuid(String streetGuid) {
		this.streetGuid = streetGuid;
	}

	public List<CodeMap> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<CodeMap> menuList) {
		this.menuList = menuList;
	}

	/***************************************************************************
	 * @author zhangkun
	 * @Description: 大厅首页数据初始化
	 * 
	 **************************************************************************/
	@Action(value = "/home/homeAction", results = { @Result(name = "initHome", location = "/WEB-INF/page/home/index.jsp") })
	public String initHome() {
		return "initHome";
	}

	/**
	 * 
	 * @Title: findCommunity
	 * @Description: 根据街道ID查找社区
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/home/findCommunity", results = { @Result(name = "initHome", location = "/WEB-INF/page/home/community.jsp") })
	public String findCommunity() {
		try {
			setBureau(bureauService.find(getStreetGuid()));
			String sql = "select d.department_guid code,d.department_name value from risenet_department t,risenet_department d "
					+ "where t.superior_guid=? and t.department_name='社区工作站'"
					+ "and d.superior_guid=t.department_guid";
			setMenuList(codeMapUtil.getCodeMapList(sql, new String[] {
					getStreetGuid(), "-1", "-1" }));
			return "initHome";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	/**
	 * 
	 * @Title: starRrating
	 * @Description: 网站星级评价
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/home/starRrating", results = { @Result(name = "success", type = "json") })
	public String starRrating() {
		try {
			WebStarRrating wsr = new WebStarRrating();
			wsr.setGuid(GUID.getGUID());
			wsr.setIp(WebUtil.getUserIp(getRequest()));
			wsr.setAppraise(getAppraise());
			wsr.setAppraisetime(new Date(System.currentTimeMillis()));
			webStarRratingService.save(wsr);
			outJson("{'message':'您的评分为" + getAppraise() + "分，感谢您的参与。'}", null);
		} catch (Exception e) {
			outJson("{'message':'评价失败，内部错误：" + e.getMessage() + "'}", null);
		}
		return null;
	}
}
