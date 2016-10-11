package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
/**
 * 
  * @ClassName: BizFinanceBean
  * @Description: 科技创新扶持申请单位近三年财务状况
  * @author Comsys-zhangkun
  * @date Apr 10, 2013 4:31:41 PM
  *
 */
@Entity
@Table(name="ZJFC_KJ_FINANCE")
@DataTransferObject
public class BizFinanceBean implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -8542894527807311154L;
	private String guid;  //
	private String audittime;  //审计时间（前年，上一年，本年度至申报日止）"
	private Float generalincome;  //总收入
	private Float generalrise;  //总收入同期增长率（%）
	private Float businessincome;  //主营业务收入
	private Float softwareincome;  //软件产品销售收入（软件企业填）
	private Float businessrise;  //主营业务收入增长率（%）
	private Float clearprofit;  //净利润
	private Float businessprofit;  //其中主营业务利润
	private Float profitrise;  //主营业务利润增长率（%）
	private Float grosssales;  //产品销售总额
	private Float business;  //经营活动现金流量净额
	private Float cash;  //总现金流量净额
	private Float sell;  //销售商品、提供劳务收到的现金
	private Float businessinflow;  //经营活动现金流入总额
	private Float commodity;  //购进商品、接受劳务付出的现金
	private Float businessoutflow;  //经营活动现金流出总额
	private Float paytaxes;  //纳税总额
	private Float taxesrise;  //纳税总额同期增长率（%）
	private Float incometax;  //企业所得税
	private Float appreciationduty;  //增值税
	private Float businesstax;  //营业税
	private Float individualtax;  //个人所得税
	private Float constructiontax;  //建设税
	private Float education;  //教育费附加收入
	private Float stampduty;  //印花税
	private Float buildingtax;  //房产税
	private Float noyalty;  //城镇土地使用税
	private Float civilization;  //文化事业建设费收入
	private Float localeducation;  //地方教育附加收入
	private Float obtain;  //创汇总额（万美元）
	private Float funds;  //研发经费支出总额
	private Float aggregate;  //总支出
	private Float fixedassets;  //固定资产总额
	private Float totalassets;  //总资产
	private Float grossliabilities;  //负债总额
	private Float liabilities;  //资产负债率（%）
	private Float propertyright;  //产权比率（%）
	private String appguid;  //申请扶持项目GUID
	private Integer financeindex;  //排序
	private String userguid;  //操作用户
	private Date createtime;  //提交时间
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getAudittime() {
		return audittime;
	}
	public void setAudittime(String audittime) {
		this.audittime = audittime;
	}
	@Column
	public Float getGeneralincome() {
		return generalincome;
	}
	public void setGeneralincome(Float generalincome) {
		this.generalincome = generalincome;
	}
	@Column
	public Float getGeneralrise() {
		return generalrise;
	}
	public void setGeneralrise(Float generalrise) {
		this.generalrise = generalrise;
	}
	@Column
	public Float getBusinessincome() {
		return businessincome;
	}
	public void setBusinessincome(Float businessincome) {
		this.businessincome = businessincome;
	}
	@Column
	public Float getSoftwareincome() {
		return softwareincome;
	}
	public void setSoftwareincome(Float softwareincome) {
		this.softwareincome = softwareincome;
	}
	@Column
	public Float getBusinessrise() {
		return businessrise;
	}
	public void setBusinessrise(Float businessrise) {
		this.businessrise = businessrise;
	}
	@Column
	public Float getClearprofit() {
		return clearprofit;
	}
	public void setClearprofit(Float clearprofit) {
		this.clearprofit = clearprofit;
	}
	@Column
	public Float getBusinessprofit() {
		return businessprofit;
	}
	public void setBusinessprofit(Float businessprofit) {
		this.businessprofit = businessprofit;
	}
	@Column
	public Float getProfitrise() {
		return profitrise;
	}
	public void setProfitrise(Float profitrise) {
		this.profitrise = profitrise;
	}
	@Column
	public Float getGrosssales() {
		return grosssales;
	}
	public void setGrosssales(Float grosssales) {
		this.grosssales = grosssales;
	}
	@Column
	public Float getBusiness() {
		return business;
	}
	public void setBusiness(Float business) {
		this.business = business;
	}
	@Column
	public Float getCash() {
		return cash;
	}
	public void setCash(Float cash) {
		this.cash = cash;
	}
	@Column
	public Float getSell() {
		return sell;
	}
	public void setSell(Float sell) {
		this.sell = sell;
	}
	@Column
	public Float getBusinessinflow() {
		return businessinflow;
	}
	public void setBusinessinflow(Float businessinflow) {
		this.businessinflow = businessinflow;
	}
	@Column
	public Float getCommodity() {
		return commodity;
	}
	public void setCommodity(Float commodity) {
		this.commodity = commodity;
	}
	@Column
	public Float getBusinessoutflow() {
		return businessoutflow;
	}
	public void setBusinessoutflow(Float businessoutflow) {
		this.businessoutflow = businessoutflow;
	}
	@Column
	public Float getPaytaxes() {
		return paytaxes;
	}
	public void setPaytaxes(Float paytaxes) {
		this.paytaxes = paytaxes;
	}
	@Column
	public Float getTaxesrise() {
		return taxesrise;
	}
	public void setTaxesrise(Float taxesrise) {
		this.taxesrise = taxesrise;
	}
	@Column
	public Float getIncometax() {
		return incometax;
	}
	public void setIncometax(Float incometax) {
		this.incometax = incometax;
	}
	@Column
	public Float getAppreciationduty() {
		return appreciationduty;
	}
	public void setAppreciationduty(Float appreciationduty) {
		this.appreciationduty = appreciationduty;
	}
	@Column
	public Float getBusinesstax() {
		return businesstax;
	}
	public void setBusinesstax(Float businesstax) {
		this.businesstax = businesstax;
	}
	@Column
	public Float getIndividualtax() {
		return individualtax;
	}
	public void setIndividualtax(Float individualtax) {
		this.individualtax = individualtax;
	}
	@Column
	public Float getConstructiontax() {
		return constructiontax;
	}
	public void setConstructiontax(Float constructiontax) {
		this.constructiontax = constructiontax;
	}
	@Column
	public Float getEducation() {
		return education;
	}
	public void setEducation(Float education) {
		this.education = education;
	}
	@Column
	public Float getStampduty() {
		return stampduty;
	}
	public void setStampduty(Float stampduty) {
		this.stampduty = stampduty;
	}
	@Column
	public Float getBuildingtax() {
		return buildingtax;
	}
	public void setBuildingtax(Float buildingtax) {
		this.buildingtax = buildingtax;
	}
	@Column
	public Float getNoyalty() {
		return noyalty;
	}
	public void setNoyalty(Float noyalty) {
		this.noyalty = noyalty;
	}
	@Column
	public Float getCivilization() {
		return civilization;
	}
	public void setCivilization(Float civilization) {
		this.civilization = civilization;
	}
	@Column
	public Float getLocaleducation() {
		return localeducation;
	}
	public void setLocaleducation(Float localeducation) {
		this.localeducation = localeducation;
	}
	@Column
	public Float getObtain() {
		return obtain;
	}
	public void setObtain(Float obtain) {
		this.obtain = obtain;
	}
	@Column
	public Float getFunds() {
		return funds;
	}
	public void setFunds(Float funds) {
		this.funds = funds;
	}
	@Column
	public Float getAggregate() {
		return aggregate;
	}
	public void setAggregate(Float aggregate) {
		this.aggregate = aggregate;
	}
	@Column
	public Float getFixedassets() {
		return fixedassets;
	}
	public void setFixedassets(Float fixedassets) {
		this.fixedassets = fixedassets;
	}
	@Column
	public Float getTotalassets() {
		return totalassets;
	}
	public void setTotalassets(Float totalassets) {
		this.totalassets = totalassets;
	}
	@Column
	public Float getGrossliabilities() {
		return grossliabilities;
	}
	public void setGrossliabilities(Float grossliabilities) {
		this.grossliabilities = grossliabilities;
	}
	@Column
	public Float getLiabilities() {
		return liabilities;
	}
	public void setLiabilities(Float liabilities) {
		this.liabilities = liabilities;
	}
	@Column
	public Float getPropertyright() {
		return propertyright;
	}
	public void setPropertyright(Float propertyright) {
		this.propertyright = propertyright;
	}
	@Column
	public String getAppguid() {
		return appguid;
	}
	public void setAppguid(String appguid) {
		this.appguid = appguid;
	}
	@Column
	public Integer getFinanceindex() {
		return financeindex;
	}
	public void setFinanceindex(Integer financeindex) {
		this.financeindex = financeindex;
	}
	@Column
	public String getUserguid() {
		return userguid;
	}
	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	

}
