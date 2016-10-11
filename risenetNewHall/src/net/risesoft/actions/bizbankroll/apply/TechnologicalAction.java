package net.risesoft.actions.bizbankroll.apply;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.bizbankroll.BizFinanceBean;
import net.risesoft.beans.bizbankroll.BizHistorySubsidize;
import net.risesoft.beans.bizbankroll.BizProductBean;
import net.risesoft.beans.bizbankroll.BizProductSituation;
import net.risesoft.beans.bizbankroll.BizShareholderBean;
import net.risesoft.beans.bizbankroll.BizUnitBaseInfoBean;
import net.risesoft.beans.user.CompanyUser;
import net.risesoft.services.bizbankroll.apply.IBizFinanceService;
import net.risesoft.services.bizbankroll.apply.IBizHistorySubsidizeService;
import net.risesoft.services.bizbankroll.apply.IBizProductService;
import net.risesoft.services.bizbankroll.apply.IBizProductSituationService;
import net.risesoft.services.bizbankroll.apply.IBizShareholderService;
import net.risesoft.services.bizbankroll.apply.IBizUnitBaseInfoService;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.GUID;

@Controller
@ParentPackage("default")
//继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })
public class TechnologicalAction extends BaseActionSupport {
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 6327319171385071773L;

	private BizUnitBaseInfoBean unitBaseInfoBean;// 单位基本信息
	private String appGUID;// 项目GUID
	private String message;// 提示信息
	private String url;// 返回URL
	private String index;//列索引
	private BizProductSituation productSituation;// 主要产品情况
	private List<BizShareholderBean> shareholderList;// 主要股东信息，共享
	private List<BizHistorySubsidize> historySubsidizeList;// 历史资助，共享

	private List<BizShareholderBean> subShareholderList;// 批量提交股东信息集合，已提交
	private List<BizHistorySubsidize> subHistorySubsidizeList;// 批量提交历史资助，已提交

	private List<BizFinanceBean> financeList;// 批量提交近三年财务，已提交
	private List<BizFinanceBean> shareFinanceList;// 近三年财务，共享
	private List<BizProductBean> productList;// 主要产品，共享
	
	private List<BizProductBean> subProductList;// 批量提交主要产品，已提交

	@Resource
	private IBizUnitBaseInfoService unitBaseInfoService;
	@Resource
	private IBizShareholderService shareholderService;
	@Resource
	private IBizHistorySubsidizeService subsidizeService;
	@Resource
	private IBizFinanceService financeService;
	@Resource
	private IBizProductService productService;
	@Resource
	IBizProductSituationService situationService;
	@Resource
	IBizProductSituationService productSituationService;
	public BizProductSituation getProductSituation() {
		return productSituation;
	}

	public void setProductSituation(BizProductSituation productSituation) {
		this.productSituation = productSituation;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public List<BizProductBean> getProductList() {
		return productList;
	}

	public void setProductList(List<BizProductBean> productList) {
		this.productList = productList;
	}

	public List<BizProductBean> getSubProductList() {
		return subProductList;
	}

	public void setSubProductList(List<BizProductBean> subProductList) {
		this.subProductList = subProductList;
	}

	public BizUnitBaseInfoBean getUnitBaseInfoBean() {
		return unitBaseInfoBean;
	}

	public void setUnitBaseInfoBean(BizUnitBaseInfoBean unitBaseInfoBean) {
		this.unitBaseInfoBean = unitBaseInfoBean;
	}

	public String getAppGUID() {
		return appGUID;
	}

	public void setAppGUID(String appGUID) {
		this.appGUID = appGUID;
	}

	public List<BizShareholderBean> getShareholderList() {
		return shareholderList;
	}

	public void setShareholderList(List<BizShareholderBean> shareholderList) {
		this.shareholderList = shareholderList;
	}

	public List<BizHistorySubsidize> getHistorySubsidizeList() {
		return historySubsidizeList;
	}

	public void setHistorySubsidizeList(
			List<BizHistorySubsidize> historySubsidizeList) {
		this.historySubsidizeList = historySubsidizeList;
	}

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

	public List<BizShareholderBean> getSubShareholderList() {
		return subShareholderList;
	}

	public void setSubShareholderList(
			List<BizShareholderBean> subShareholderList) {
		this.subShareholderList = subShareholderList;
	}

	public List<BizHistorySubsidize> getSubHistorySubsidizeList() {
		return subHistorySubsidizeList;
	}

	public void setSubHistorySubsidizeList(
			List<BizHistorySubsidize> subHistorySubsidizeList) {
		this.subHistorySubsidizeList = subHistorySubsidizeList;
	}

	public List<BizFinanceBean> getFinanceList() {
		return financeList;
	}

	public void setFinanceList(List<BizFinanceBean> financeList) {
		this.financeList = financeList;
	}

	public List<BizFinanceBean> getShareFinanceList() {
		return shareFinanceList;
	}

	public void setShareFinanceList(List<BizFinanceBean> shareFinanceList) {
		this.shareFinanceList = shareFinanceList;
	}

	/**
	 * 
	 * @Title: initBaseInfo
	 * @Description: 初始化单位基本信息页面
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/bizApply/initBaseInfo", results = { @Result(name = "success", location = "/WEB-INF/page/bizbankroll/apply/unitBaseInfoBean.jsp") })
	@LoginRequired(module = "bizApply")
	public String initBaseInfo() {
		try {
			BizUnitBaseInfoBean base = null;
			List<BizUnitBaseInfoBean> beanList = unitBaseInfoService
					.findByProperty("appguid", getAppGUID());
			if (beanList == null || beanList.size() == 0) {
				base = new BizUnitBaseInfoBean();
				base.setAppguid(getAppGUID());
				base.setGuid(GUID.getGUID());
				CompanyUser cuser = getUser().getCompanyUser();
				base.setDepartmentname(cuser.getEname());
				base.setLawper(cuser.getLawperson());
				base.setRegcode(cuser.getRegcode());
				base.setNationaltax(cuser.getNationaltax());
				base.setDepartmentadd(cuser.getAddress());
				base.setLocaltax(cuser.getLocaltax());
				base.setBankname(cuser.getBankname());
				base.setBankcode(cuser.getBanknum());
				//base.setFounddate(cuser.getRegdate());
				base.setRegmoney(cuser.getRegmoney());
				base.setBusinesssphere(cuser.getLimit());
			} else {
				base = beanList.get(0);
				setSubShareholderList(shareholderService.getScrollData(-1, -1,
						new String[] { "basicguid=?" },
						new String[] { base.getGuid() }).getResultList());
				setSubHistorySubsidizeList(subsidizeService.getScrollData(-1, -1,
						new String[] { "basicguid=?" },
						new String[] { base.getGuid() }).getResultList());
			}
			setShareholderList(shareholderService
					.getShareholderList(getUserGUID()));
			setHistorySubsidizeList(subsidizeService.getSubsidize(getUserGUID()));
			setUnitBaseInfoBean(base);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	/**
	 * 
	 * @Title: initBaseInfo
	 * @Description: 初始化单位近三年财务状况
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/bizApply/initFinanceSituation", results = { @Result(name = "success", location = "/WEB-INF/page/bizbankroll/apply/financeSituation.jsp") })
	@LoginRequired(module = "bizApply")
	public String initFinanceSituation() {
	try {
		LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("audittime", "asc");
		setFinanceList(financeService.getScrollData(-1, -1,
				new String[] { "appguid=?" }, new String[] { getAppGUID() },
				orderMap).getResultList());
		setProductList(productService.getProductList(getUserGUID()));
		setSubProductList(productService
				.findByProperty("appguid", getAppGUID()));
		List<BizProductSituation> proList = situationService.findByProperty(
				"appguid", getAppGUID());
		if (proList != null && proList.size() > 0) {
			setProductSituation(proList.get(0));
		} else {
			BizProductSituation bean = new BizProductSituation();
			bean.setGuid(GUID.getGUID());
			setProductSituation(bean);
		}
		return SUCCESS;
	} catch (Exception e) {
		e.printStackTrace();
		return ERROR;
	}
	}

	/**
	 * 
	 * 
	 * @Title: saveIntroducer
	 * @Description: 保存科技创新申报单位基本情况表
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/bizApply/savebaseInfoBean", results = {
			@Result(name = "success", location = "/WEB-INF/page/share/message.jsp"),
			@Result(name = "error", location = "/WEB-INF/page/bizbankroll/apply/unitBaseInfoBean.jsp") })
	@LoginRequired(module = "bizApply")
	public String savebaseInfoBean() {
		BizUnitBaseInfoBean bbean = getUnitBaseInfoBean();
		try {
			if (subShareholderList != null && subShareholderList.size() > 0) {
				for (BizShareholderBean bean : subShareholderList) {
					bean.setUserguid(getUserGUID());
					bean.setCreatetime(new Date(System.currentTimeMillis()));
					bean.setBasicguid(bbean.getGuid());
					shareholderService.saveOrUpdate(bean);
				}
			}
			if (subHistorySubsidizeList != null
					&& subHistorySubsidizeList.size() > 0) {
				for (BizHistorySubsidize bean : subHistorySubsidizeList) {
					bean.setUserguid(getUserGUID());
					bean.setCreatetime(new Date(System.currentTimeMillis()));
					bean.setBasicguid(bbean.getGuid());
					subsidizeService.saveOrUpdate(bean);
				}
			}
			bbean.setUserguid(getUserGUID());
			bbean.setCreatetime(new Date(System.currentTimeMillis()));
			unitBaseInfoService.saveOrUpdate(bbean);
			setMessage("保存成功，请填写单位近三年财务情况信息！");
			setUrl("/bizApply/initFinanceSituation.YS?appGUID="
					+ bbean.getAppguid());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			setMessage("单位基本信息保存失败！");
			setUrl("/bizApply/initBaseInfo.YS?appGUID=" + bbean.getGuid());
			return ERROR;
		}

	}

	@Action(value = "/bizApply/saveFinanceSituation", results = {
			@Result(name = "success", location = "/WEB-INF/page/share/message.jsp")})
	@LoginRequired(module = "bizApply")
	public String saveFinanceSituation() {
		try {
			BizProductSituation situation = getProductSituation();
			for(BizFinanceBean bean:financeList){
				bean.setAppguid(getAppGUID());
				bean.setCreatetime(new Date(System.currentTimeMillis()));
				bean.setUserguid(getUserGUID());
				financeService.saveOrUpdate(bean);
			}
			for(BizProductBean bean:subProductList){
				bean.setAppguid(getAppGUID());
				bean.setCreatetime(new Date(System.currentTimeMillis()));
				bean.setUserguid(getUserGUID());
				productService.saveOrUpdate(bean);
			}
			situation.setAppguid(getAppGUID());
			situation.setCreatetime(new Date(System.currentTimeMillis()));
			situation.setUserguid(getUserGUID());
			productSituationService.saveOrUpdate(situation);
			setMessage("单位近三年财务情况保存成功,历史申请信息可进入办事跟踪查看.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			setMessage("单位近三年财务情况保存失败！");
			setUrl("/bizApply/initFinanceSituation.YS?appGUID=" + getAppGUID());
			return SUCCESS;
		}

	}
	
	@Action(value = "/bizApply/shareFinanceSituation", results = {
			@Result(name = "success", location = "/WEB-INF/page/bizbankroll/apply/shareFinance.jsp")})
	@LoginRequired(module = "bizApply")
	public String shareFinanceSituation() {
		try {
			setShareFinanceList(financeService.getShareFinance(getUserGUID()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
