package net.risesoft.actions.investment;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.investment.InvestmentLink;
import net.risesoft.beans.onlineservice.ApproveItem;
import net.risesoft.services.investment.IInvestmentService;
import net.risesoft.services.onlineservice.IApproveItemService;
/**
 * 
  * @ClassName: InvestmentAction
  * @Description: 投资审批
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 4:54:49 PM
  *
 */
@Controller
public class InvestmentAction extends BaseActionSupport {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 2092879484849443592L;
	private String linkGUID;//环节GUID
	private String linkInfo;//组合环节名称
	private String method;
	@Resource
	private IInvestmentService investmentService; //投资审批service
	@Resource
	private IApproveItemService approveItemService;//事项服务service
	private String appCount;//查询出的事项总数
	private String departmentName;//部门名称
	private ApproveItem approveItem;//事项
	private String approveItemGUID;//事项GUID
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public ApproveItem getApproveItem() {
		return approveItem;
	}

	public void setApproveItem(ApproveItem approveItem) {
		this.approveItem = approveItem;
	}

	public String getApproveItemGUID() {
		return approveItemGUID;
	}

	public void setApproveItemGUID(String approveItemGUID) {
		this.approveItemGUID = approveItemGUID;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getLinkGUID() {
		return linkGUID;
	}

	public void setLinkGUID(String linkGUID) {
		this.linkGUID = linkGUID;
	}

	public String getLinkInfo() {
		return linkInfo;
	}

	public void setLinkInfo(String linkInfo) {
		this.linkInfo = linkInfo;
	}
	
	public String getAppCount() {
		return appCount;
	}

	public void setAppCount(String appCount) {
		this.appCount = appCount;
	}

	/**
	 * 
	  * @Title: investmentInit
	  * @Description: 初始化政府投资首页
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/investment/investmentInitAction", results = { 
				@Result(name = "success", location = "/WEB-INF/page/investment/index.jsp"),
				@Result(name = "gov", location = "/WEB-INF/page/investment/govInvest.jsp"),
				@Result(name = "enterprise", location = "/WEB-INF/page/investment/enterpriseInvest.jsp")})
	public String investmentInit() throws Exception {
		if(StringUtils.isNotBlank(getMethod())){
			if("gov".equals(getMethod())){
				return "gov";
			}else if("enterprise".equals(getMethod())){
				setApproveItem(approveItemService.getGuideInfo(getApproveItemGUID()));
				getApproveItem().setBureauName(getDepartmentName());
				return "enterprise";
			}
		}
		return SUCCESS;
	}
	/**
	 * 
	  * @Title: findApproveItemByLinkCode
	  * @Description: 根据环节GUID查询环节所对应的事项
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/investment/findApproveItemByLinkCode", results = { @Result(name = "success", location = "/WEB-INF/page/investment/resultApprove.jsp") })
	public String findApproveItemByLinkCode() {
		try {
			InvestmentLink link = investmentService.find(getLinkGUID());
			setLinkInfo(link.getLinkname());
			link = investmentService.find(getLinkGUID());
			setLinkInfo(link.getLinkname()+"阶段："+linkInfo);
			int maxResult = 13;
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct b.itemid,b.approveitemname,b.handletime,");
			sql.append(" case when d.department_name like '%街道办事处' then '街道办事处' else d.department_name end ");
			sql.append(" bureauName,e.approveplace ");
			sql.append(" from xzql_xzsp_base b,xzql_item_depart_org o,risenet_department d,xzql_xzsp_extend e,xzql_xzsp_province p ");
			sql.append(" where e.itemid(+)=b.itemid and b.itemid=o.itemid and e.isprovince='1' and o.departid=d.department_guid ");
			sql.append(" and e.approveitemstatus='运行' and p.itemid(+)=b.itemid and p.investtachecatalogcode1=? ");
			sql.append(" order by b.approveitemname");
			int count = approveItemService.getDataRows(sql.toString(), new String[]{getLinkGUID()});
			setAppCount(count+"");
			  PageView<ApproveItem> pageView = new PageView<ApproveItem>(maxResult,
						getPage(),count);
			  pageView.setRecords(approveItemService.getMoreApproveItem(sql.toString(), new String[]{getLinkGUID(),pageView.getStartIndex()+"",pageView.getEndIndex()+""}));
			  getRequest().setAttribute("pageView", pageView);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
}
