package net.risesoft.actions.onlineservice;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.CodeMap;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.onlineservice.ApproveItem;
import net.risesoft.beans.onlineservice.ApproveItemClassify;
import net.risesoft.beans.onlineservice.ApproveItemExtend;
import net.risesoft.beans.onlineservice.ApproveItemLog;
import net.risesoft.beans.onlineservice.ApproveItemTabTemp;
import net.risesoft.beans.onlineservice.SearchKeyWord;
import net.risesoft.beans.wssb.ProceedingForm;
import net.risesoft.services.onlineservice.IApproveItemExtendService;
import net.risesoft.services.onlineservice.IApproveItemService;
import net.risesoft.services.onlineservice.IApproveItemTabTempService;
import net.risesoft.services.onlineservice.IOnlineService;
import net.risesoft.services.onlineservice.ISearchKeyWordService;
import net.risesoft.services.wssb.ProceedingFormService;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;
/**
 * 
  * @ClassName: ApproveItemAction
  * @Description: 事项查询
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 4:57:12 PM
  *
 */
@Controller
@ParentPackage("default")//继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })//权限拦截
public class ApproveItemAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -7001326776892906033L;
	
	@Resource
	private ICodeMapUtil codeMapUtil;//数据字典
	@Resource
	private IApproveItemTabTempService approveItemTabTempService;//事项表格下载服务类
	@Resource
	private IApproveItemService approveItemService;//事项服务
	@Resource
	private IOnlineService onlineService;
	@Resource 
	private ISearchKeyWordService searchKeyWordService;//关键字搜索
	@Resource
	private IApproveItemExtendService approveItemExtendService;
	@Resource
	private ProceedingFormService proceedingFormService;//网上申报绑定表单查询
	private List<ProceedingForm> formList;//表单列表
	private List<CodeMap> departMap;//部门
	private String departmentName;//部门名称
	private String approveName;//事项名称
	private String departGUID;//部门GUID
	private String approveItemType;//事项分类
	private String approveType; //s事项类型 行政许可，非行政许可等
	private String isOnlineApply;//是否可以网上申报
	private String tableTempName;//表格下载名称
	private String streetApp;//街道是否去重复事项
	private String module;//调用action的模块
	private ApproveItem approveItem;//事项
	private ApproveItemLog approveItemLog;
	private String approveItemGUID;//事项GUID
	private List<ApproveItemTabTemp> appItemTabTempList;//事项表格下载
	private List<CodeMap> streetList;//街道集合
	private String linkId;//政府投资项目环节ID
	private ApproveItemClassify itemClassify;
	private String twoLevel;
	private String orderType;//排序类型
	private String ascOrDesc;//升序或降序
	private String sjbbh;
	private ApproveItemExtend approveItemExtend;
	private List<ApproveItem> minApproveItem;//小类事项
	
	
	private static List<String> sckApproveItemLists_yz = new ArrayList<String>();
	static{
		sckApproveItemLists_yz.add("{0A0102A1-0000-0000-6A01-9A85FFFFFF8E}");//业主委员会招标公告
		sckApproveItemLists_yz.add("{0A009FA8-0000-0000-68C9-3CDB0000004C}");//业主委员会物业招标文件备案
		sckApproveItemLists_yz.add("{0A0102A1-0000-0000-68F9-BCD4FFFFFF8C}");//业主委员会物业招标投标情况报告备案
	
	}
	
	//市场科所有的事项  ---不包括业主业主委员会的事项
	private static List<String> sckApproveItemLists = new ArrayList<String>();
	
	static{
		sckApproveItemLists.add("{0A009FA8-FFFF-FFFF-8CDA-148A0000004B}");
		sckApproveItemLists.add("{0A009FA8-0000-0000-72DA-20CF000003A3}");
		sckApproveItemLists.add("{0A009FA8-FFFF-FFFF-8CCA-A01400000030}");
		sckApproveItemLists.add("{7F000001-FFFF-FFFF-A669-F731FFFFFF88}");
		//sckApproveItemLists.add("{0A0102A1-0000-0000-6A01-9A85FFFFFF8E}");//业主委员会招标公告
		sckApproveItemLists.add("{0A0102A1-0000-0000-68FD-F8BAFFFFFF8D}");
		sckApproveItemLists.add("{7F000001-FFFF-FFFF-A669-F75700000079}");
		sckApproveItemLists.add("{0A009FA8-FFFF-FFFF-8836-09C6000004FF}");
		sckApproveItemLists.add("{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}");
		//sckApproveItemLists.add("{0A009FA8-0000-0000-68C9-3CDB0000004C}");//业主委员会物业招标文件备案
		sckApproveItemLists.add("{AC1E060B-FFFF-FFFF-A889-921DFFFFFFF5}");
		sckApproveItemLists.add("{7F000001-FFFF-FFFF-A669-F7190000007F}");
		sckApproveItemLists.add("{0A009FA8-FFFF-FFFF-8CEF-5B70FFFFFF88}");
	//	sckApproveItemLists.add("{0A0102A1-0000-0000-68F9-BCD4FFFFFF8C}");//业主委员会物业招标投标情况报告备案
		sckApproveItemLists.add("{AC1E060B-FFFF-FFFF-A884-FE18FFFFFFE8}");
		sckApproveItemLists.add("{0A0102A1-0000-0000-707B-65A500000061}");
		sckApproveItemLists.add("{0A0102A1-0000-0000-7074-D1D10000005D}");
	}
	
	public ApproveItemExtend getApproveItemExtend() {
		return approveItemExtend;
	}
	public void setApproveItemExtend(ApproveItemExtend approveItemExtend) {
		this.approveItemExtend = approveItemExtend;
	}
	public String getSjbbh() {
		return sjbbh;
	}
	public void setSjbbh(String sjbbh) {
		this.sjbbh = sjbbh;
	}
	public String getAscOrDesc() {
		return ascOrDesc;
	}
	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}
	public String getTwoLevel() {
		return twoLevel;
	}
	public void setTwoLevel(String twoLevel) {
		this.twoLevel = twoLevel;
	}
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public ApproveItemClassify getItemClassify() {
		return itemClassify;
	}
	public void setItemClassify(ApproveItemClassify itemClassify) {
		this.itemClassify = itemClassify;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getApproveItemGUID() {
		return approveItemGUID;
	}
	public void setApproveItemGUID(String approveItemGUID) {
		this.approveItemGUID = approveItemGUID;
	}
	public ApproveItem getApproveItem() {
		return approveItem;
	}
	public void setApproveItem(ApproveItem approveItem) {
		this.approveItem = approveItem;
	}
	public String getApproveItemType() {
		return approveItemType;
	}
	public void setApproveItemType(String approveItemType) {
		this.approveItemType = approveItemType;
	}
	public String getDepartGUID() {
		return departGUID;
	}
	public void setDepartGUID(String departGUID) {
		this.departGUID = departGUID;
	}
	
	public List<CodeMap> getDepartMap() {
		return departMap;
	}
	public void setDepartMap(List<CodeMap> departMap) {
		this.departMap = departMap;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}

	
	public String getApproveType() {
		return approveType;
	}
	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}
	public List<ApproveItemTabTemp> getAppItemTabTempList() {
		return appItemTabTempList;
	}
	public void setAppItemTabTempList(List<ApproveItemTabTemp> appItemTabTempList) {
		this.appItemTabTempList = appItemTabTempList;
	}
	
	public String getTableTempName() {
		return tableTempName;
	}
	public void setTableTempName(String tableTempName) {
		this.tableTempName = tableTempName;
	}
	
	public String getStreetApp() {
		return streetApp;
	}
	public void setStreetApp(String streetApp) {
		this.streetApp = streetApp;
	}
	public String getIsOnlineApply() {
		return isOnlineApply;
	}
	public void setIsOnlineApply(String isOnlineApply) {
		this.isOnlineApply = isOnlineApply;
	}
	
	public ApproveItemLog getApproveItemLog() {
		return approveItemLog;
	}
	public void setApproveItemLog(ApproveItemLog approveItemLog) {
		this.approveItemLog = approveItemLog;
	}
	public List<CodeMap> getStreetList() {
		return streetList;
	}
	public void setStreetList(List<CodeMap> streetList) {
		this.streetList = streetList;
	}
	
	public List<ProceedingForm> getFormList() {
		return formList;
	}
	public void setFormList(List<ProceedingForm> formList) {
		this.formList = formList;
	}
	public List<ApproveItem> getMinApproveItem() {
		return minApproveItem;
	}
	public void setMinApproveItem(List<ApproveItem> minApproveItem) {
		this.minApproveItem = minApproveItem;
	}
	/**
	 * 
	  *
	  * @Title: query
	  * @Description: 分页查询事项信息
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/approve/approveQueryResult", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/onlineservice/resultApprove.jsp"),
			//@Result(name = "zwgk" ,location = "/WEB-INF/page/govpublic/resultApprove.jsp"),
			@Result(name = "imageSerch" ,location = "/WEB-INF/page/onlineservice/appClassify.jsp"),
			//@Result(name = "depart" ,location = "/WEB-INF/page/onlineservice/resultApproveDepart.jsp")
			})
	public String query(){
		setDepartMap(codeMapUtil.getListByType("罗湖区审批职能局"));
		int maxResult = 10;
		StringBuffer sql = new StringBuffer();
		ArrayList<Object> param = new ArrayList<Object>();
//		sql.append("select distinct b.itemid,b.approveitemname,b.handletime,e.applicationform,e.applicationformurl,");
//		if("true".equals(getStreetApp())){
//			sql.append(" case when d.department_name like '%街道办事处' then '街道办事处' else d.department_name end ");
//		}else{
//			sql.append(" d.department_name ");
//		}
//		sql.append(" bureauName,e.approveplace ");
//		sql.append(" from xzql_xzsp_base b,xzql_item_depart_org o,risenet_department d,xzql_xzsp_extend e,xzql_xzsp_province p ");
//		sql.append(" where e.itemid(+)=b.itemid and b.itemid=o.itemid and o.departid=d.department_guid ");
//		sql.append(" and e.approveitemstatus='运行' and p.isprovince='1' and p.itemid(+)=b.itemid");
		
		sql.append("select distinct b.itemid,b.approveitemname,b.handletime,e.applicationform,b.bureautype bureautype1,");
		sql.append("p.onlineapplyingwebsite applicationformurl,b.approveobject,e.provinceordercode,c.value bureautype,");
		sql.append(" d.bureauName, case when (p.approveplace = '0' and p.onlineapplyingservice='1') or " +
				"(p.approveplace = '1' and p.onlineapplyingwebsite is not null) then '1' else '0' end approveplace "); 
		sql.append(" from xzql_xzsp_base b,spm_bureau d,xzql_xzsp_extend e,xzql_xzsp_province p ,xzql_codemap c ");
		sql.append(" where e.itemid=b.itemid and b.adminorgid=d.bureauguid and c.type='行政审批-事项分类'  and b.bureautype=c.code");
		sql.append(" and e.approveitemstatus='运行' and p.isprovince='1' and p.itemid=b.itemid ");
		try {
			if(StringUtils.isNotBlank(getDepartGUID())){
				sql.append(" and d.bureauguid=? ");
				param.add(getDepartGUID());
			}
			if(StringUtils.isNotBlank(getApproveItemType())){
				sql.append(" and instr(b.itemclass,?)>0");
				param.add(getApproveItemType());
				setItemClassify(onlineService.find(getApproveItemType()));
				setTwoLevel(getItemClassify().getPid());
			}
			if(StringUtils.isNotBlank(getApproveName())){
				sql.append(" and b.approveitemname like ?");
				param.add("%"+getApproveName()+"%");
			}
			if(StringUtils.isNotBlank(getLinkId())){
				sql.append(" and p.investtachecatalogcode1=? ");
				param.add(getLinkId());
			}
			if(StringUtils.isNotBlank(getIsOnlineApply())){
				if("0".equals(getIsOnlineApply())){//可在线申报
					sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='1') or (p.approveplace = '1' and p.onlineapplyingwebsite is not null) )");
				}else if("1".equals(getIsOnlineApply())){//不可在线申报
					sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='0') or (p.approveplace = '1' and p.onlineapplyingwebsite is null) or p.approveplace='2' or p.approveplace is null)");
				}else{
					sql.append(" and (e.approveplace = ?) ");
					param.add(getIsOnlineApply());
				}
			}
			if(StringUtils.isNotBlank(getApproveType())){
				sql.append(" and b.bureautype = ?");
				param.add(getApproveType());
			}
			if(StringUtils.isBlank(getOrderType())||"orderCode".equals(getOrderType()))
				sql.append(" order by e.provinceordercode asc");
			else if("itemName".equals(getOrderType())){
				sql.append(" order by b.approveitemname "+getAscOrDesc());
			}else if("departName".equals(getOrderType())){
				sql.append(" order by d.bureauName "+getAscOrDesc());
			}else if("itemType".equals(getOrderType())){
				sql.append(" order by b.bureautype "+getAscOrDesc());
			}
			PageView<ApproveItem> pageView = new PageView<ApproveItem>(maxResult,
					getPage(),approveItemService.getDataRows(sql.toString(), GenericsUtils.listToArray(param)));
			//如果查询到数据则记录关键词
			if(pageView.getTotalRecord()>0&&StringUtils.isNotBlank(getApproveName())){
				updateSearchKey(getApproveName(),"1");
			}
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(approveItemService.getMoreApproveItem(sql.toString(), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	/*	if(StringUtils.isNotBlank(getModule())&&"zwgk".equals(getModule())){
			return "zwgk";
		}else*/ 
		if(StringUtils.isNotBlank(getModule())&&"imageSerch".equals(getModule())){
			return "imageSerch";
		}else{
			return SUCCESS;
		}/*else if(StringUtils.isNotBlank(getModule())&&"depart".equals(getModule())){
			return "depart";
		}*/
	}
	
	/**
	  * @Title: query
	  * @Description: 分页查询所有大类以及没有大类事项的信息(区分大小类)
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @author hjl
	  * @throws
	 */
	@Action(value = "/approve/approveQueryResultDX", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/onlineservice/resultApproveDX.jsp")})
	public String queryDX(){
		setDepartMap(codeMapUtil.getListByType("罗湖区审批职能局"));
		int maxResult = 10;
		StringBuffer sql = new StringBuffer();
		ArrayList<Object> param = new ArrayList<Object>();
		
		sql.append(" select * from ((select guid itemid,'1' type ,name approveitemname,null handletime,null applicationform,null bureautype1,null ");
		sql.append(" applicationformurl,null approveobject,null provinceordercode,null bureautype,bureauName,null approveplace from ");
		sql.append(" xzql_xzsp_catalogs c,spm_bureau b where c.pid = b.bureauguid ");
		if(StringUtils.isNotBlank(getDepartGUID())){
			sql.append(" and PID=? ");
			param.add(getDepartGUID());
		}
//		if(StringUtils.isNotBlank(getIsOnlineApply())){
//			if("0".equals(getIsOnlineApply())){//可在线申报
//				sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='1') or (p.approveplace = '1' and p.onlineapplyingwebsite is not null) )");
//			}else if("1".equals(getIsOnlineApply())){//不可在线申报
//				sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='0') or (p.approveplace = '1' and p.onlineapplyingwebsite is null) or p.approveplace='2' or p.approveplace is null)");
//			}else{
//				sql.append(" and (e.approveplace = ?) ");
//				param.add(getIsOnlineApply());
//			}
//		}
		sql.append(" )union all ");
		sql.append(" (select distinct b.itemid,'2' type ,b.approveitemname,b.handletime,e.applicationform,b.bureautype bureautype1,");
		sql.append(" p.onlineapplyingwebsite applicationformurl,b.approveobject,e.provinceordercode,c.value bureautype,");
		sql.append(" d.bureauName, case when (p.approveplace = '0' and p.onlineapplyingservice='1') or ");
		sql.append(" (p.approveplace = '1' and p.onlineapplyingwebsite is not null) then '1' else '0' end approveplace "); 
		sql.append(" from xzql_xzsp_base b,spm_bureau d,xzql_xzsp_extend e,xzql_xzsp_province p ,xzql_codemap c ");
		sql.append(" where e.itemid=b.itemid and b.adminorgid=d.bureauguid and c.type='行政审批-事项分类'  and b.bureautype=c.code");
		sql.append(" and e.approveitemstatus='运行' and p.isprovince='1' and p.itemid=b.itemid ");
		sql.append(" and b.catalogsguid is null ");
		try {
			if(StringUtils.isNotBlank(getDepartGUID())){
				sql.append(" and d.bureauguid=? ");
				param.add(getDepartGUID());
			}
			if(StringUtils.isNotBlank(getApproveItemType())){
				sql.append(" and instr(b.itemclass,?)>0");
				param.add(getApproveItemType());
				setItemClassify(onlineService.find(getApproveItemType()));
				setTwoLevel(getItemClassify().getPid());
			}
			if(StringUtils.isNotBlank(getLinkId())){
				sql.append(" and p.investtachecatalogcode1=? ");
				param.add(getLinkId());
			}
//			if(StringUtils.isNotBlank(getIsOnlineApply())){
//				if("0".equals(getIsOnlineApply())){//可在线申报
//					sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='1') or (p.approveplace = '1' and p.onlineapplyingwebsite is not null) )");
//				}else if("1".equals(getIsOnlineApply())){//不可在线申报
//					sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='0') or (p.approveplace = '1' and p.onlineapplyingwebsite is null) or p.approveplace='2' or p.approveplace is null)");
//				}else{
//					sql.append(" and (e.approveplace = ?) ");
//					param.add(getIsOnlineApply());
//				}
//			}
			if(StringUtils.isNotBlank(getApproveType())){
				sql.append(" and b.bureautype = ?");
				param.add(getApproveType());
			}
			sql.append("))tt");
			if(StringUtils.isNotBlank(getApproveName())){
				sql.append(" where tt.approveitemname like ?");
				param.add("%"+getApproveName()+"%");
			}
			if(StringUtils.isBlank(getOrderType())||"orderCode".equals(getOrderType()))
				sql.append(" order by type, provinceordercode asc");
			else if("itemName".equals(getOrderType())){
				sql.append(" order by type, approveitemname "+getAscOrDesc());
			}else if("departName".equals(getOrderType())){
				sql.append(" order by type, bureauName "+getAscOrDesc());
			}else if("itemType".equals(getOrderType())){
				sql.append(" order by type, bureautype "+getAscOrDesc());
			}
			PageView<ApproveItem> pageView = new PageView<ApproveItem>(maxResult,
					getPage(),approveItemService.getDataRows(sql.toString(), GenericsUtils.listToArray(param)));
//			//如果查询到数据则记录关键词
//			if(pageView.getTotalRecord()>0&&StringUtils.isNotBlank(getApproveName())){
//				updateSearchKey(getApproveName(),"1");
//			}
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(approveItemService.getMoreApproveItem(sql.toString(), GenericsUtils.listToArray(param)));
			
			
			/*查询所有事项小类总数 start*/
			StringBuffer allMinSQL = new StringBuffer();
			ArrayList<Object> allMinParams = new ArrayList<Object>();
			allMinSQL.append(" select distinct b.itemid,b.approveitemname,b.handletime,e.applicationform,b.bureautype bureautype1,");
			allMinSQL.append(" p.onlineapplyingwebsite applicationformurl,b.approveobject,e.provinceordercode,c.value bureautype,");
			allMinSQL.append(" d.bureauName, case when (p.approveplace = '0' and p.onlineapplyingservice='1') or " );
			allMinSQL.append(" (p.approveplace = '1' and p.onlineapplyingwebsite is not null) then '1' else '0' end approveplace "); 
			allMinSQL.append(" from xzql_xzsp_base b,spm_bureau d,xzql_xzsp_extend e,xzql_xzsp_province p ,xzql_codemap c ");
			allMinSQL.append(" where e.itemid=b.itemid and b.adminorgid=d.bureauguid and c.type='行政审批-事项分类'  and b.bureautype=c.code");
			allMinSQL.append(" and e.approveitemstatus='运行' and p.isprovince='1' and p.itemid=b.itemid ");
			if(StringUtils.isNotBlank(getDepartGUID())){
				allMinSQL.append(" and d.bureauguid=? ");
				allMinParams.add(getDepartGUID());
			}
			if(StringUtils.isNotBlank(getApproveItemType())){
				allMinSQL.append(" and instr(b.itemclass,?)>0");
				allMinParams.add(getApproveItemType());
				setItemClassify(onlineService.find(getApproveItemType()));
				setTwoLevel(getItemClassify().getPid());
			}
			if(StringUtils.isNotBlank(getApproveName())){
				allMinSQL.append(" and b.approveitemname like ?");
				allMinParams.add("%"+getApproveName()+"%");
			}
			if(StringUtils.isNotBlank(getLinkId())){
				allMinSQL.append(" and p.investtachecatalogcode1=? ");
				allMinParams.add(getLinkId());
			}
			if(StringUtils.isNotBlank(getIsOnlineApply())){
				if("0".equals(getIsOnlineApply())){//可在线申报
					allMinSQL.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='1') or (p.approveplace = '1' and p.onlineapplyingwebsite is not null) )");
				}else if("1".equals(getIsOnlineApply())){//不可在线申报
					allMinSQL.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='0') or (p.approveplace = '1' and p.onlineapplyingwebsite is null) or p.approveplace='2' or p.approveplace is null)");
				}else{
					allMinSQL.append(" and (e.approveplace = ?) ");
					allMinParams.add(getIsOnlineApply());
				}
			}
			if(StringUtils.isNotBlank(getApproveType())){
				allMinSQL.append(" and b.bureautype = ?");
				allMinParams.add(getApproveType());
			}
			if(StringUtils.isBlank(getOrderType())||"orderCode".equals(getOrderType()))
				allMinSQL.append(" order by e.provinceordercode asc");
			else if("itemName".equals(getOrderType())){
				allMinSQL.append(" order by b.approveitemname "+getAscOrDesc());
			}else if("departName".equals(getOrderType())){
				allMinSQL.append(" order by d.bureauName "+getAscOrDesc());
			}else if("itemType".equals(getOrderType())){
				allMinSQL.append(" order by b.bureautype "+getAscOrDesc());
			}
			int allMinCount = approveItemService.getDataRows(allMinSQL.toString(), GenericsUtils.listToArray(allMinParams));
			getRequest().setAttribute("allMinCount", allMinCount);
			/*查询所有事项小类总数 end*/
			
			
			getRequest().setAttribute("pageView", pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(getModule())&&"zwgk".equals(getModule())){
			return "zwgk";
		}else if(StringUtils.isNotBlank(getModule())&&"imageSerch".equals(getModule())){
			return "imageSerch";
		}else if(StringUtils.isNotBlank(getModule())&&"depart".equals(getModule())){
			return "depart";
		}else{
			return SUCCESS;
		}
	}
	
	/**
	  * @Title: query
	  * @Description: 分页查询所有可在线申报的大类以及没有大类事项的信息(区分大小类)
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @author hjl
	  * @throws
	 */
	@Action(value = "/approve/queryOnlineItems", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/onlineservice/resultApproveDX.jsp")})
	public String queryOnlineItems(){
		setDepartMap(codeMapUtil.getListByType("罗湖区审批职能局"));
		int maxResult = 10;
		ArrayList<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ((select guid itemid,nvl(orderno,0) orderno, '1' type , name approveitemname, null handletime, null applicationform, null bureautype1,");
		sql.append(" null  applicationformurl, null approveobject, null provinceordercode, null bureautype, bureauName,");
		sql.append(" null approveplace from xzql_xzsp_catalogs c, spm_bureau b where c.pid = b.bureauguid and c.guid in");
		sql.append(" (select distinct(b.catalogsguid) from xzql_xzsp_base b, spm_bureau d, xzql_xzsp_extend e, xzql_xzsp_province p ,");
		sql.append(" xzql_codemap c where e.itemid=b.itemid and b.adminorgid=d.bureauguid and c.type='行政审批-事项分类' and b.bureautype=c.code");
		sql.append(" and e.approveitemstatus='运行' and p.isprovince='1' and p.itemid=b.itemid ");
		sql.append(" and b.catalogsguid is not null");
		if("0".equals(getIsOnlineApply())){//可在线申报
			sql.append(" and((p.approveplace = '0' and p.onlineapplyingservice='1' ) or ( p.approveplace = '1' and p.onlineapplyingwebsite is not null))");
		}
		if(StringUtils.isNotBlank(getApproveName())){
			sql.append(" and b.approveitemname like ?");
			param.add("%"+getApproveName()+"%");
		}
		if(StringUtils.isNotBlank(getApproveType())){
			sql.append(" and b.bureautype = ?");
			param.add(getApproveType());
		}
		if(StringUtils.isNotBlank(getDepartGUID())){
			sql.append(" and d.bureauguid=? ");
			param.add(getDepartGUID());
		}
		
		sql.append(" ))union all ");
		sql.append(" (select distinct b.itemid,b.orderno,'2' type ,b.approveitemname,b.handletime,e.applicationform,b.bureautype bureautype1,");
		sql.append(" p.onlineapplyingwebsite applicationformurl,b.approveobject,e.provinceordercode,c.value bureautype,");
		sql.append(" d.bureauName, case when (p.approveplace = '0' and p.onlineapplyingservice='1') or ");
		sql.append(" (p.approveplace = '1' and p.onlineapplyingwebsite is not null) then '1' else '0' end approveplace "); 
		sql.append(" from xzql_xzsp_base b,spm_bureau d,xzql_xzsp_extend e,xzql_xzsp_province p ,xzql_codemap c ");
		sql.append(" where e.itemid=b.itemid and b.adminorgid=d.bureauguid and c.type='行政审批-事项分类'  and b.bureautype=c.code");
		sql.append(" and e.approveitemstatus='运行' and p.isprovince='1' and p.itemid=b.itemid ");
		sql.append(" and b.catalogsguid is null ");
		try{
			if(StringUtils.isNotBlank(getDepartGUID())){
				sql.append(" and d.bureauguid=? ");
				param.add(getDepartGUID());
			}
			if(StringUtils.isNotBlank(getApproveItemType())){
				sql.append(" and instr(b.itemclass,?)>0");
				param.add(getApproveItemType());
				setItemClassify(onlineService.find(getApproveItemType()));
				setTwoLevel(getItemClassify().getPid());
			}
			if(StringUtils.isNotBlank(getApproveName())){
				sql.append(" and b.approveitemname like ?");
				param.add("%"+getApproveName()+"%");
			}
			if(StringUtils.isNotBlank(getLinkId())){
				sql.append(" and p.investtachecatalogcode1=? ");
				param.add(getLinkId());
			}
			if(StringUtils.isNotBlank(getIsOnlineApply())){
				if("0".equals(getIsOnlineApply())){//可在线申报
					sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='1') or (p.approveplace = '1' and p.onlineapplyingwebsite is not null) )");
				}else if("1".equals(getIsOnlineApply())){//不可在线申报
					sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='0') or (p.approveplace = '1' and p.onlineapplyingwebsite is null) or p.approveplace='2' or p.approveplace is null)");
				}else{
					sql.append(" and (e.approveplace = ?) ");
					param.add(getIsOnlineApply());
				}
			}
			if(StringUtils.isNotBlank(getApproveType())){
				sql.append(" and b.bureautype = ?");
				param.add(getApproveType());
			}
			sql.append(" )) order by type,bureauName,orderno asc");
//			if(StringUtils.isBlank(getOrderType())||"orderCode".equals(getOrderType()))
//				sql.append(" ) order by type, provinceordercode asc");
//			else if("itemName".equals(getOrderType())){
//				sql.append(" ) order by type, approveitemname "+getAscOrDesc());
//			}else if("departName".equals(getOrderType())){
//				sql.append(" ) order by type, bureauName "+getAscOrDesc());
//			}else if("itemType".equals(getOrderType())){
//				sql.append(" ) order by type, bureautype "+getAscOrDesc());
//			}
			
			PageView<ApproveItem> pageView = new PageView<ApproveItem>(maxResult,
					getPage(),approveItemService.getDataRows(sql.toString(), GenericsUtils.listToArray(param)));
			
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(approveItemService.getMoreApproveItem(sql.toString(), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
			
			
			/*查询所有事项小类总数 start*/
			StringBuffer allMinSQL = new StringBuffer();
			ArrayList<Object> allMinParams = new ArrayList<Object>();
			allMinSQL.append(" select distinct b.itemid,b.orderno,b.approveitemname,b.handletime,e.applicationform,b.bureautype bureautype1,");
			allMinSQL.append(" p.onlineapplyingwebsite applicationformurl,b.approveobject,e.provinceordercode,c.value bureautype,");
			allMinSQL.append(" d.bureauName, case when (p.approveplace = '0' and p.onlineapplyingservice='1') or " );
			allMinSQL.append(" (p.approveplace = '1' and p.onlineapplyingwebsite is not null) then '1' else '0' end approveplace "); 
			allMinSQL.append(" from xzql_xzsp_base b,spm_bureau d,xzql_xzsp_extend e,xzql_xzsp_province p ,xzql_codemap c ");
			allMinSQL.append(" where e.itemid=b.itemid and b.adminorgid=d.bureauguid and c.type='行政审批-事项分类'  and b.bureautype=c.code");
			allMinSQL.append(" and e.approveitemstatus='运行' and p.isprovince='1' and p.itemid=b.itemid ");
			if(StringUtils.isNotBlank(getDepartGUID())){
				allMinSQL.append(" and d.bureauguid=? ");
				allMinParams.add(getDepartGUID());
			}
			if(StringUtils.isNotBlank(getApproveItemType())){
				allMinSQL.append(" and instr(b.itemclass,?)>0");
				allMinParams.add(getApproveItemType());
				setItemClassify(onlineService.find(getApproveItemType()));
				setTwoLevel(getItemClassify().getPid());
			}
			if(StringUtils.isNotBlank(getApproveName())){
				allMinSQL.append(" and b.approveitemname like ?");
				allMinParams.add("%"+getApproveName()+"%");
			}
			if(StringUtils.isNotBlank(getLinkId())){
				allMinSQL.append(" and p.investtachecatalogcode1=? ");
				allMinParams.add(getLinkId());
			}
			if(StringUtils.isNotBlank(getIsOnlineApply())){
				if("0".equals(getIsOnlineApply())){//可在线申报
					allMinSQL.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='1') or (p.approveplace = '1' and p.onlineapplyingwebsite is not null) )");
				}else if("1".equals(getIsOnlineApply())){//不可在线申报
					allMinSQL.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='0') or (p.approveplace = '1' and p.onlineapplyingwebsite is null) or p.approveplace='2' or p.approveplace is null)");
				}else{
					allMinSQL.append(" and (e.approveplace = ?) ");
					allMinParams.add(getIsOnlineApply());
				}
			}
			if(StringUtils.isNotBlank(getApproveType())){
				allMinSQL.append(" and b.bureautype = ?");
				allMinParams.add(getApproveType());
			}
			allMinSQL.append(" order by type,bureauName,orderno asc");
//			if(StringUtils.isBlank(getOrderType())||"orderCode".equals(getOrderType()))
//				allMinSQL.append(" order by e.provinceordercode asc");
//			else if("itemName".equals(getOrderType())){
//				allMinSQL.append(" order by b.approveitemname "+getAscOrDesc());
//			}else if("departName".equals(getOrderType())){
//				allMinSQL.append(" order by d.bureauName "+getAscOrDesc());
//			}else if("itemType".equals(getOrderType())){
//				allMinSQL.append(" order by b.bureautype "+getAscOrDesc());
//			}
			
			int allMinCount = approveItemService.getDataRows(allMinSQL.toString(), GenericsUtils.listToArray(allMinParams));
			getRequest().setAttribute("allMinCount", allMinCount);
			/*查询所有事项小类总数 end*/
			
			
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	  * @Title: query
	  * @Description: 查询大类包含的小类
	  * @param  大类guid
	  * @author hjl
	 */
	@Action(value = "/approve/approveQueryResultX", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/onlineservice/resultApproveMinItems.jsp")})
	public String queryX(){
		ArrayList<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		try {
			param.add(approveItemGUID);
			sql.append(" select * from (select distinct b.itemid,nvl(b.orderno,0) orderno,b.approveitemname,b.handletime,e.applicationform,b.bureautype bureautype1,");
			sql.append(" p.onlineapplyingwebsite applicationformurl,b.approveobject,e.provinceordercode,c.value bureautype,");
			sql.append(" d.bureauName, case when (p.approveplace = '0' and p.onlineapplyingservice='1') or " );
			sql.append(" (p.approveplace = '1' and p.onlineapplyingwebsite is not null) then '1' else '0' end approveplace "); 
			sql.append(" from xzql_xzsp_base b,spm_bureau d,xzql_xzsp_extend e,xzql_xzsp_province p ,xzql_codemap c ");
			sql.append(" where e.itemid=b.itemid and b.adminorgid=d.bureauguid and c.type='行政审批-事项分类'  and b.bureautype=c.code");
			sql.append(" and e.approveitemstatus='运行' and p.isprovince='1' and p.itemid=b.itemid ");
			sql.append(" and b.catalogsguid = ? ");
			if(StringUtils.isNotBlank(getApproveName())){
				sql.append(" and b.approveitemname like ?");
				param.add("%"+getApproveName()+"%");
			}
			if(StringUtils.isNotBlank(getIsOnlineApply())){
				if("0".equals(getIsOnlineApply())){//可在线申报
					sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='1') or (p.approveplace = '1' and p.onlineapplyingwebsite is not null) )");
				}else if("1".equals(getIsOnlineApply())){//不可在线申报
					sql.append(" and ((p.approveplace = '0' and p.onlineapplyingservice='0') or (p.approveplace = '1' and p.onlineapplyingwebsite is null) or p.approveplace='2' or p.approveplace is null)");
				}else{
					sql.append(" and (e.approveplace = ?) ");
					param.add(getIsOnlineApply());
				}
			}
			
			sql.append(") order by orderno ");
			param.add(0);
			param.add(100);
			List<ApproveItem> xItems = approveItemService.getMoreApproveItem(sql.toString(), GenericsUtils.listToArray(param));
			
			getRequest().setAttribute("xItems", xItems);
			
			for(int i=0;i<xItems.size();i++)
			{
				//可在线申报，但数据库中没有相应的网址
				if("1".equals(xItems.get(i).getApproveplace())&&xItems.get(i).getApplicationformurl()==null){
					xItems.get(i).setApplicationformurl("/onlineService/initOnlineApply/approveItemGUID/"+xItems.get(i).getItemid()+".html");
				}
				
//				String applicationformurl = xItems.get(i).getApplicationformurl();
//				String approveplace = xItems.get(i).getApproveplace();
//				if("1".equals(approveplace)&&applicationformurl==null){
//					applicationformurl = "/onlineService/initOnlineApply/approveItemGUID/"+xItems.get(i).getItemid()+".html";
//				}
//				
//				getResponse().setCharacterEncoding("UTF-8");
//				if(i%2==1)getResponse().getWriter().write("<tr class='even'>");
//				else getResponse().getWriter().write("<tr class='odd'>");
//				
//				getResponse().getWriter().write("<td width='60%' style='border-right:none;border-left:none;border-top:none;padding-left:40px;'>"
//						+"<a target='_blank' href='/approve/findAppGuideInfo/approveItemGUID/"+xItems.get(i).getItemid()+".html'><img src='/images/home/tree/wj.png'/>"+xItems.get(i).getApproveitemname()+"</a></td>");
//				getResponse().getWriter().write("<td style='border-right:none;border-left:none;border-top:none;'>"
//						+xItems.get(i).getBureauName()+"</td>");
//				if(applicationformurl!=null&&!applicationformurl.equals("")){
//					getResponse().getWriter().write("<td width='14%' style='border-right:none;border-left:none;border-top:none;'>"
//							+"<a target='_blank' href='"+applicationformurl+"' class='BS_btnblue'>在线申报</a></td></tr>");
//				}else{
//					getResponse().getWriter().write("<td width='14%' style='border-right:none;border-left:none;border-top:none;'></td></tr>");
//				}
			}
			setMinApproveItem(xItems);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private void updateSearchKey(String id,String type){
		try {
			SearchKeyWord skw = searchKeyWordService.find(id);
			if(skw==null){
				skw = new SearchKeyWord();
				skw.setKeyword(id);
				skw.setSearchnum(1);
				skw.setType(type);
				searchKeyWordService.save(skw);
			}else{
				skw.setSearchnum(skw.getSearchnum()+1);
				searchKeyWordService.update(skw);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	  * @Title: findGuideInfo
	  * @Description: 根据guid查询办事指南信息
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/approve/findAppGuideInfo", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/onlineservice/appItemGuideInfo.jsp"),
					@Result(name = "zwgk" ,location = "/WEB-INF/page/govpublic/appItemGuideInfo.jsp"),
					@Result(name = "website" ,location = "/WEB-INF/page/govpublic/appItemWebSiteGuideInfo.jsp"),
					@Result(name = "weixinBszn",location = "/WEB-INF/page/govpublic/weiXinItemGuideInfo.jsp")})
	public String findGuideInfo(){
		try {
			setApproveItem(approveItemService.getGuideInfo(getApproveItemGUID()));
			setApproveItemExtend(approveItemExtendService.find(getApproveItemGUID()));
			setFormList(proceedingFormService.getFormsList("select t.guid,t.approveitemguid,t.formname,t.url,t.description,t.type,t.displayorder from proceedingforms t where t.type='0' and t.approveitemguid=?", new String[]{getApproveItemGUID(),"-1","-1"}));
			getApproveItem().setBureauName(getDepartmentName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(getModule()) && getModule().equals("szlh")){
			return "zwgk";
		}else if(StringUtils.isNotBlank(getModule()) && getModule().equals("website")){
			return "website";
		}else if(StringUtils.isNotBlank(getModule()) && getModule().equals("weixinBszn")){
			return "weixinBszn";
		}else{
			return SUCCESS;
		}
	}
	
	/**
	 * 市场科过滤掉“个人、社会住着、其他”，不能填报申报信息
	 * list="#{'4':'行政机关','5':'事业单位','7':'[国有企业]','2':'企业','6':'社会组织','1':'个人','3':'其它'}"
	 * @return
	 */
	@Action(value="/approve/checkUserType",interceptorRefs={ @InterceptorRef("isLoginStack") },results={@Result(name="success",type="json")})
	public String checkUserType(){
		String approveItemguid = getRequest().getParameter("approveItemguid");
		String flag = "0";
		for(Iterator<String> it = sckApproveItemLists.iterator();it.hasNext();){
			String temp = it.next();
			if(StringUtils.equals(approveItemguid, temp)){
				flag = "1";
				break;
			}
		}
		
		if(StringUtils.equals(flag, "1")){
			String userType = getUserType();
			if(null != userType && (userType.equals("1") || userType.equals("6") || userType.equals("3"))){
				outJson("{'message':'1'}", null);
			}else{
				outJson("{'message':'0'}", "");
			}
		}
		
		//判断是否是业主---limugui
	    flag = "0";
		for(Iterator<String> it = sckApproveItemLists_yz.iterator();it.hasNext();){
			String temp = it.next();
			if(StringUtils.equals(approveItemguid, temp)){
				flag = "1";
				break;
			}
		}
		
		if(StringUtils.equals(flag, "1")){
			String userType = getUserType();
			if(null != userType && (userType.equals("1") || userType.equals("6"))){
				outJson("{'message':'1'}", null);
			}else{
				outJson("{'message':'0'}", "");
			}
		}
		
		return null;
	}
	
	/**
	 * 
	  * @Title: findGuideInfo
	  * @Description: 根据guid，和数据版本号查询办事指南信息预览
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/approve/xzqlGuideInfoView", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/onlineservice/xzqlGuideInfo.jsp")})
	public String xzqlGuideInfoView(){
		try {
			setApproveItemLog(approveItemService.getGuideInfo(getApproveItemGUID(),getSjbbh()));
			setApproveItemExtend(approveItemExtendService.find(getApproveItemGUID()));
			getApproveItemLog().setBureauName(getDepartmentName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 
	  * @Title: downFileTemp
	  * @Description: 分页查询表格模板信息
	  * @param @return
	  * @param @throws UnsupportedEncodingException    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/approve/downAppItemFileTemp", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/onlineservice/resultApproveTab.jsp"),
			@Result(name = "zwgk" ,location = "/WEB-INF/page/govpublic/resultApproveTab.jsp")})
	public String downFileTemp() throws UnsupportedEncodingException{
		int maxResult = 10;
//		String sql="select o.attachmentguid,o.file_name,o.tabletype,a.approveitemname from XZQL_XZSP_ATTACHMENT o left join XZQL_XZSP_BASE a on o.itemid=a.itemid ";
		StringBuffer sql= new StringBuffer();
		sql.append(" select o.attachmentguid,o.file_name,o.tabletype,a.approveitemname,o.itemid from XZQL_XZSP_ATTACHMENT o left join XZQL_XZSP_BASE a on o.itemid=a.itemid where ");
		LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("approveitemname", "asc");
//		ArrayList<String> whereSql = new ArrayList<String>();
		ArrayList<Object> param = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getTableTempName())) {
//			whereSql.add(" o.file_name like ? ");
			sql.append(" o.file_name like ? ");
			param.add("%" + getTableTempName() + "%");
		}else{
			sql.append(" 1=1 ");
		}
		if(StringUtils.isNotBlank(getApproveName())){
//			whereSql.add(" o.approveitemname like ? ");
			sql.append(" and o.approveitemname like ? ");
			param.add("%" + getApproveName() + "%");
		}
		try {
			PageView<ApproveItemTabTemp> pageView = new PageView<ApproveItemTabTemp>(maxResult,
					getPage(),approveItemTabTempService.getDataRows(sql.toString(), GenericsUtils.listToArray(param)));
			//如果查询到数据则记录关键词
			if(pageView.getTotalRecord()>0&&StringUtils.isNotBlank(getApproveName())){
				updateSearchKey(getApproveName(),"1");
			}
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
		
		
			pageView.setRecords(approveItemTabTempService.getMoreApproveItemTabTemp(sql.toString(), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
			
			if(StringUtils.isNotBlank(getModule())){
				return "zwgk";
			}else{
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
}
