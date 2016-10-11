package net.risesoft.actions.onlineservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;



import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.CodeMap;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.onlineservice.ApproveItem;
import net.risesoft.beans.onlineservice.ApproveItemApply;
import net.risesoft.beans.onlineservice.ApproveItemClassify;
import net.risesoft.beans.onlineservice.ApproveItemTabTemp;
import net.risesoft.beans.onlineservice.ApproveItemsn;
import net.risesoft.beans.onlineservice.Approveinstance;
import net.risesoft.beans.onlineservice.ApproveinstanceGcmc;
import net.risesoft.beans.onlineservice.BureauBean;
import net.risesoft.beans.onlineservice.BusinessInfo;
import net.risesoft.beans.onlineservice.CorpGasEmployee;
import net.risesoft.beans.onlineservice.GuideFile;
import net.risesoft.beans.onlineservice.GuideFileType;
import net.risesoft.beans.onlineservice.ListtypeOfApproveinstance;
import net.risesoft.beans.onlineservice.PermitApplyUnit;
import net.risesoft.beans.onlineservice.PermitBean;
import net.risesoft.beans.onlineservice.ProjectInfo;
import net.risesoft.beans.onlineservice.SearchKeyWord;
import net.risesoft.beans.onlineservice.SpiDeclareInfo;
import net.risesoft.beans.onlineservice.TempSblsh;
import net.risesoft.beans.onlineservice.WebApplyFileDoctype;
import net.risesoft.beans.onlineservice.WebApplyUpFile;
import net.risesoft.beans.onlineservice.YtjclbzBean;
import net.risesoft.beans.onlineservice.YwbwProjectInfo;
import net.risesoft.beans.system.MessageConfig;
import net.risesoft.beans.wssb.ProceedingForm;
import net.risesoft.common.Common;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.dwr.approve.dao.ScjgkXmxxDao;
import net.risesoft.services.fileUtil.IFtpUpFileLogService;
import net.risesoft.services.govpublic.IBureauService;
import net.risesoft.services.interaction.IDynamicStateService;
import net.risesoft.services.onlineservice.IApproveItemApplyService;
import net.risesoft.services.onlineservice.IApproveItemService;
import net.risesoft.services.onlineservice.IApproveItemTabTempService;
import net.risesoft.services.onlineservice.IApproveinstanceService;
import net.risesoft.services.onlineservice.IGuideFileService;
import net.risesoft.services.onlineservice.IGuideFileTypeService;
import net.risesoft.services.onlineservice.IListTypeOfApproveinstanceService;
import net.risesoft.services.onlineservice.IOnlineService;
import net.risesoft.services.onlineservice.ISearchKeyWordService;
import net.risesoft.services.onlineservice.IWebApplyDocTypeService;
import net.risesoft.services.onlineservice.IWebApplyUpFileService;
import net.risesoft.services.system.ISendMessageService;
import net.risesoft.services.webservice.sShare.SblshDeal;
import net.risesoft.services.wssb.ProceedingFormService;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.DateFormatUtil;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
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
@ParentPackage("default")//继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })//权限拦截
public class OnlineServiceAction extends BaseActionSupport {
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 6374442653916693089L;
	private static final String STR_FORMAT = "0000";
	private String pid;//分类父级ID
	private String levels;//分类层级
	private List<ApproveItemClassify> classifyList = new ArrayList<ApproveItemClassify>();//事项分类集合
	private List<CodeMap> bureauList = new ArrayList<CodeMap>();//部门信息几个
	private Map<String, String> appTypeMap = new TreeMap<String, String>();//事项按照行政许可，非行政许可等分类
	private String method;//
	private String twoLevel;//二级分类GUID
	private String oneLevel;//一级分类GUID
	private String approveItemGUID;//事项GUID
	private List<GuideFileType> guideFilTypeeList;//材料类型
	private List<GuideFile> guideFileList;//存放没有材料类型的材料
	private List<GuideFile> guidenoFileList;//可材料共享的材料
	private List<ProceedingForm> formList;//表单列表
	private List<ApproveItemTabTemp> appTabTempList;//表格下载
	private ApproveItem approveItem;//事项Bean
	private String appInstanceGuid;//申报GUID
	private String guids;//材料类型guids
	private String fileGuid;//材料GUID
	private String departGUID;//部门或街道GUID
	private String communityGUID;//社区GUID
	private String typejsonString;//材料类型数据
	private Approveinstance approveinstance;//网上申报
	private String message;
	private String state;//申报表单状态，0表示新增，1表示修改
	private String yxtywlsh;//业务流水号
	private List<SearchKeyWord> keyWordList;//关键字
	private List<BusinessInfo> busiList = new ArrayList<BusinessInfo>();//办事业务
	private List<WebApplyUpFile> webUpFileList;
	private List<WebApplyFileDoctype> docTypeList;
	private WebApplyFileDoctype webApplyFileDoctype;
	private String cardid;//证件编号
	private List<ProjectInfo> projectList = new ArrayList<ProjectInfo>();//项目信息
	private String skqz_ysgxkzxh;//施工许可表单信息参数--原施工许可证序号
	private String skqz_gcbh;//施工许可表单信息参数--工程编号
	private PermitBean permit;//施工许可表单信息
	private String queryId;//当前查询id
	private String zbwjFlag;  //是否是招标文件标识
	private String GONGCHENGMINGCHENG;  //工程名称
	private String GONGCHENGBIANHAO;    //工程编号
	private String XIANGMUMINGCHENG;    //项目名称
	private String SGGK_xmmc;//修改后的项目名称
	private String SGGK_xmbh;//项目编号
	private String SGGK_zbr;//建设单位（招标人）
	private String stepFlag;            //步骤标识
	private String hasEMS;//快递是否填写
	
	
	@Resource
	private IApproveItemService approveItemService;
	@Resource
	private IOnlineService onlineService;
	@Resource
	private ISimpleJdbcDao<ApproveinstanceGcmc> gcmcApproveinstanceService;
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource
	private IWebApplyDocTypeService webApplyDocTypeService;
	@Resource
	private IGuideFileService guideFileService;
	@Resource 
	private IGuideFileTypeService guideFileTypeService;//材料类型服务类
	@Resource
	private IApproveItemTabTempService approveItemTabTempService;//表格下载服务类
	@Resource
	private ISimpleJdbcDao<GuideFile> guidFileSimpleJdbcDao;//材料服务类
	@Resource
	private IBureauService bureauService; // 部门服务类
	@Resource
	private ISendMessageService sendMessageService;//短信服务类
	@Resource
	private IApproveinstanceService instanceService;//申报业务服务类
	@Resource
	private IListTypeOfApproveinstanceService listTypeService;//申报业务与材料类别关联
	@Resource
	private IWebApplyUpFileService applyUpFileService;//网上申报已上传的材料
	@Resource
	private IDynamicStateService dynamicStateService;//业务查询服务类
	@Resource
	private ProceedingFormService proceedingFormService;//网上申报绑定表单查询
	@Resource 
	private ISearchKeyWordService searchKeyWordService;//关键字搜索
	@Resource
	private ISimpleJdbcDao<ApproveItemApply> itemApplySimpleJdbcDao;
	@Resource 
	private IApproveItemApplyService approveItemApplyService;
	@Resource
	private IFtpUpFileLogService ftpUpFileLogService;
	@Resource
	ISimpleJdbcDao<ApproveItemClassify> itemClassifySimpleJdbcDao;
	@Resource
	private ISimpleJdbcDao<WebApplyFileDoctype> webFileDocTypeSimpleJdbcDao;
	@Resource
	private ISimpleJdbcDao<CorpGasEmployee> corpGasEmployeeJdbcDao;
	@Resource
	private SblshDeal sblshDeal;//市局统一编号
	@Resource
	private ISimpleJdbcDao<PermitBean> permitJdbcDao;//施工许可表单信息服务
	@Resource
	private ISimpleJdbcDao<PermitApplyUnit> applyUnitDao;//施工许可--申请单位
	@Resource
	private ISimpleJdbcDao<YtjclbzBean> YtjclbzDao;//已提交材料标志

	@Resource
	private ISimpleJdbcDao<ApproveItemsn> approveItemsnDao;//已提交材料标志
	
	@Resource
	private ISimpleJdbcDao<TempSblsh> tempSblshDao;//已提交材料标志
	
	@Resource
	private ISimpleJdbcDao<SpiDeclareInfo> spiDeclareInfoDao;   //受理表office_spi_declareinfo

	
	private static List<String> zbwjLists = new ArrayList<String>();
	static{
		zbwjLists.add("{0A009FA8-FFFF-FFFF-8836-09C6000004FF}");    //建设工程施工（监理）招标文件备案
		zbwjLists.add("{0A009FA8-0000-0000-68C9-3CDB0000004C}");    //深圳市物业管理招标文件备案
		zbwjLists.add("{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}");    //深圳市前期物业管理招标文件备案（内容与前期物业招标公告和招标组织形式备案一致）
		zbwjLists.add("{7F000001-FFFF-FFFF-A669-F75700000079}");    //建设工程服务（货物）招标文件备案
	}
	
	
	/**
	 * 
	  * @Title: initOnlineService
	  * @Description: 初始化在线办事首页
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/initOnlineService", results = { @Result(name = "initHome", location = "/WEB-INF/page/onlineservice/index.jsp") })
	public String initOnlineService() throws Exception {
		setBusiList(dynamicStateService.getHomeDynamic(20));
		setAppTypeMap(codeMapUtil.getMapByType("行政审批-事项分类"));
		LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("searchnum", "desc"); 
		setKeyWordList(searchKeyWordService.getScrollData(0, 8, orderMap).getResultList());
		if(StringUtils.isNotBlank(getTwoLevel())){
			setOneLevel(onlineService.find(onlineService.find(getTwoLevel()).getPid()).getGuid());
		}else{
			setOneLevel("{7F000001-FFFF-FFFF-AC41-AFE400000001}");
			setTwoLevel("{7F000001-FFFF-FFFF-AC41-AFEA00000003}");
		}
		return "initHome";
	}
	/**
	 * @Titile initResultPublicity
	 * @Description 初始化结果公示
	 * @return 
	 * @throws Exception
	 */
	@Action(value = "/onlineService/ResultPublicity", results = { @Result(name = "initPublicity", location = "/WEB-INF/page/onlineservice/resultPublicity.jsp") })
	public String initResultPublicity(){
		try{
			int maxResult = 10;
			String sql = "select * from (select distinct t.yxtywlsh,  t.sljgmc,t.sblsh, t.spsxmc, "
				+ " t.sqdwhsqrxm, to_char(t.slsj, 'yyyy-MM-dd') slsj, "
				+ " decode(b.bjjg,'0', '出证办结', '1',  '退回办结','2', '作废办结', '3', " 
				+ " '删除办结',  '4',  '转报办结', '5', "
				+ " '补交不来办结',  null,  '办理中',  '办结') blzt "
				+ " from (select *  from t_shouli s  where not exists (select 1 "
				+ " from t_shouli l where s.sjbbh < l.sjbbh "
				+ " and s.ywlsh = l.ywlsh)) t, office_spi_declareinfo d, "
				+ " T_BANJIE b  where d.declaresn(+) = t.yxtywlsh  and b.ywlsh(+) = t.ywlsh "
				+ " and t.yxtywlsh not in (select s.declaresn from spi_supervise_back s) " 
				+ " and b.bjjg is not null ";
			
			ArrayList<Object> param = new ArrayList<Object>();

			
			if (StringUtils.isNotBlank(yxtywlsh)) {
				sql += " and (t.yxtywlsh like ? or t.sblsh like ?)";
				param.add("%" + yxtywlsh.trim() + "%");
				param.add("%" + yxtywlsh.trim() + "%");
			}
			sql+= ") order by slsj desc";
			
			PageView<BusinessInfo> pageView = new PageView<BusinessInfo>(maxResult,getPage(),approveItemService.getDataRows(sql.toString(), GenericsUtils.listToArray(param)));
			
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			
			setBusiList(dynamicStateService.getResultPublicity(sql,GenericsUtils.listToArray(param)));
			
//			pageView.setRecords(approveItemService.getMoreApproveItem(sql.toString(), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
			return "initPublicity";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	  * @Title: findClassifyByPid
	  * @Description: 根据父级ID查询子分类
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/OnlineService/findClassifyByPid", results = { 
			@Result(name = "success", location = "/WEB-INF/page/onlineservice/levelTow.jsp"),
			@Result(name = "levels", location = "/WEB-INF/page/onlineservice/imageClassify.jsp"),
			@Result(name = "department", location = "/WEB-INF/page/onlineservice/departmentClassify.jsp"),
			@Result(name = "street", location = "/WEB-INF/page/onlineservice/streetClassify.jsp")})
	public String findClassifyByPid() throws Exception {
		try {
			if("images".equals(getMethod())){
				LinkedHashMap<String, String> orderMap = new LinkedHashMap<String,String>();
				orderMap.put("orderno", "asc");
				setClassifyList(onlineService.getScrollData(-1, -1, new String[]{"pid=?"}, new String[]{getPid()}, orderMap).getResultList());
				/*ApproveItemClassify c = onlineService.find(getPid());
				if(c.getLevels()==1){
					setClassifyList(onlineService.getScrollData(-1, -1, new String[]{"pid=?"}, new String[]{getPid()}, orderMap).getResultList());
				}else{
					String sql = "select * from (select distinct c.guid,c.pid,c.name,c.levels,c.imagename,c.orderno,'1' createuser "+
								"	from xzql_xzsp_classify c where  "+
								  "	exists (select 1 from xzql_xzsp_base t,xzql_xzsp_extend e "+
								  "	where  e.itemid(+)=t.itemid and (e.isprovince='1' or e.isprovince is null)  and instr(t.itemclass, c.guid) <> 0 "+
								  "	) union all "+
								  "	select distinct c.guid,c.pid,c.name,c.levels, "+
								  "	c.imagename || 'no' imagename,c.orderno, '0' createuser "+
								  "	 from xzql_xzsp_classify c where not exists ( "+
								  "	select 1 from  xzql_xzsp_base t,xzql_xzsp_extend e "+
								  "	where t.itemid=e.itemid(+) "+
								  "	and (e.isprovince='1' or e.isprovince is null)  "+
								  "	 and instr(t.itemclass, c.guid) <> 0)) where  pid=? order by orderno";
					setClassifyList(itemClassifySimpleJdbcDao.queryForRow(sql, new String[]{getPid(),"-1","-1"}, ApproveItemClassify.class));
				}*/
				
				/*住建局修改
				if("2".equals(getLevels())){
					return SUCCESS;
				}else if("3".equals(getLevels())){
					return "levels";
				}*/
				String sql="select distinct  t.guid,t.name,t.imagename,t.orderno,t1.pid from XZQL_XZSP_CLASSIFY t1,XZQL_XZSP_CLASSIFY t " +
						"inner join xzql_xzsp_base a on a.itemclass=t.guid where  t1.guid=t.pid order by t.orderno";
				
				List<ApproveItemClassify> classifyLists = itemClassifySimpleJdbcDao.queryForRow(sql, ApproveItemClassify.class);
				List<ApproveItemClassify> tempClassifyLists = new ArrayList<ApproveItemClassify>();
				if(null != classifyLists && classifyLists.size()>0){
					for(Iterator<ApproveItemClassify> it = classifyLists.iterator();it.hasNext();){
						ApproveItemClassify tempClassify = it.next();
						if(tempClassify.getName().equals("年检年审") || tempClassify.getName().equals("安全防护") 
									|| tempClassify.getName().equals("土地房产") || tempClassify.getName().equals("其他")
									|| tempClassify.getName().equals("开办企业")){
							tempClassifyLists.add(tempClassify);
						}
					}
					
					if(null != tempClassifyLists && tempClassifyLists.size()>0){
						for(Iterator<ApproveItemClassify> it = tempClassifyLists.iterator();it.hasNext();){
							classifyLists.remove(it.next());
						}
					}
				}
				setClassifyList(classifyLists);
				return "levels";
			}else if("department".equals(getMethod())){
				String sql = "select distinct b.bureauguid  as code, "+
				" b.bureaucnfullname as value,b.isstreet  status,b.bureautabindex "+
				" from spm_bureau b,xzql_xzsp_extend e, xzql_xzsp_base x "+
				" where b.flag = '1' and e.isprovince = '1' "+
				" and e.approveitemstatus = '运行' and x.itemid=e.itemid(+) "+
				" and x.adminorgid=b.bureauguid order by bureautabindex";
				setBureauList(codeMapUtil.getCodeMapList(sql));
				return "department";
			}else if("street".equals(getMethod())){
				setBureauList(codeMapUtil.getCodeMapList("select t.bureauguid as code,t.bureauname as value from spm_bureau t where isstreet='1' and  flag='1' order by bureautabindex"));
				return "street";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	/**
	 * 
	  * @Title: initStreetSpecial
	  * @Description: 初始化街道专栏首页
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/initStreetSpecial", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/street/index.jsp") })
	public String initStreetSpecial(){
		setBureauList(codeMapUtil.getCodeMapList("select r.department_guid code,r.department_name value from risenet_department r where r.superior_guid in( "+
				" select t.department_guid from risenet_department t where t.superior_guid=? and t.department_name=?) order by r.tabindex",new String[]{getPid(),"社区工作站","-1","-1"}));
		return SUCCESS;
	}
	
	/**
	 * 
	  * @Title: initOnlineApply
	  * @Description: 初始化在线申报页面,查找事项需上传的材料，查找事项所受的部门
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/initOnlineApply", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/OnlineApply.jsp") })
	@LoginRequired(module="approveItem")
	public String initOnlineApply(){
		try {
			setApproveItem(approveItemService.find(getApproveItemGUID()));
			Approveinstance  ins= null;
			if(StringUtils.isNotBlank(getAppInstanceGuid())){
				ins = instanceService.find(getAppInstanceGuid());
			}else{
				ins = new Approveinstance();
				ins.setGuid(GUID.getGUID());
				ins.setApproveguid(getApproveItemGUID());
				ins.setFormname(getApproveItem().getApproveitemname());
				ins.setDataform(0);
			}
			setApproveinstance(ins);
			//根据事项查找局或街道ID
			String sql = "select distinct d.department_guid code,d.department_name value " +
					"from xzql_item_depart_org o,risenet_department d " +
					"where d.department_guid=o.departid and o.itemid=?";
			setBureauList(codeMapUtil.getCodeMapList(sql,new String[]{getApproveItemGUID(),"-1","-1"}));
			List<ProceedingForm> formList = proceedingFormService.getFormsList("select t.guid,t.approveitemguid,t.formname,t.url,t.description,t.type,t.displayorder from proceedingforms t where t.type='0' and t.approveitemguid=?", new String[]{getApproveItemGUID(),"-1","-1"});
			ProceedingForm pf = hasEMs(getApproveItemGUID());
			if(pf!=null)
				formList.add(pf);
			setFormList(formList);
			setGuideFilTypeeList(guideFileTypeService.getTypesList("select t.typeguid,t.typename,t.supertypeguid from xzql_xzsp_listtype t where t.typeguid in(select xl.typeguid from xzql_xzsp_listoftype xl,xzql_xzsp_base xb where xl.itemid=xb.itemid and xb.itemid=?) order by t.orderno", new String[]{getApproveItemGUID(),"-1","-1"}));
			//如果为局事项，则list为1，查找科室ID
			if(bureauList.size()==1){
				setDepartGUID(bureauList.get(0).getCode());
				sql = " select d.department_guid code,d.department_name value " +
						"from xzql_item_depart_org o,risenet_department d " +
						"where o.departid=? and o.itemid=? and d.department_guid=o.orgid";
				List<CodeMap> clist = codeMapUtil.getCodeMapList(sql, new String[]{getDepartGUID(),getApproveItemGUID(),"-1","-1"});
				if(clist!=null&&clist.size()>0){
					setCommunityGUID(clist.get(0).getCode());
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Action(value = "/onlineService/buisView", results = { @Result(name = "success", location = "/WEB-INF/page/userhome/busiView.jsp") })
	@LoginRequired(module="approveItem")
	public String buisView(){
		try{
			setApproveItem(approveItemService.find(getApproveItemGUID()));
			Approveinstance  ins= null;
			if(StringUtils.isNotBlank(getAppInstanceGuid())){
				ins = instanceService.find(getAppInstanceGuid());
			}else{
				ins = new Approveinstance();
				ins.setGuid(GUID.getGUID());
				ins.setApproveguid(getApproveItemGUID());
				ins.setFormname(getApproveItem().getApproveitemname());
				ins.setDataform(0);
			}
			setApproveinstance(ins);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action(value = "/onlineService/buisDelete", results = { @Result(name = "success", type="redirect",location = "/businessfollow/initBusinessfollow.html") })
	@LoginRequired(module="approveItem")
	public String buisDelete(){
		try{
			setApproveItem(approveItemService.find(getApproveItemGUID()));
			Approveinstance  ins= null;
			if(StringUtils.isNotBlank(getAppInstanceGuid())){
				ins = instanceService.find(getAppInstanceGuid());
				instanceService.delete(ins);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询网上申报表单
	 * @return
	 */
	@Action(value = "/onlineService/getforms", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/forms.jsp") })
	@LoginRequired(module="approveItem")
	public String getFoorms(){
		try {
			int num=listTypeService.execute("select * from sb_listtypeofinstance t where t.guid=?",new String[]{ getAppInstanceGuid()});
			ListtypeOfApproveinstance ins=null;
			if(num<1){
				ins=new ListtypeOfApproveinstance();
				ins.setGuid(getAppInstanceGuid());
				ins.setListtypeids(getGuids());
				listTypeService.save(ins);
			}else{
				if(getGuids()!=null){
					ins=listTypeService.find(getAppInstanceGuid());
					ins.setListtypeids(getGuids());
					listTypeService.update(ins);
				}
			}
			String sql = "select t.guid from T_LG_YW_SENDEMAIL t where t.guid=?";
			List<CodeMap> tableList = codeMapUtil.getCodeMapList(sql, new String[]{getAppInstanceGuid(),"-1","-1"});
			if(tableList!=null&&tableList.size()>0){
				setHasEMS("1");
			}else{
				setHasEMS("0");
			}
			/**
			 * 施工许可变更:选择情形《在建工程建设单位、施工单位、监理单位发生变更的，应重新申请施工许可》加载施工许可的申请表
			 * 施工许可申请：直接发包
			 * 提前介入：直接发包
			 * 直接发包guid：{7F000001-FFFF-FFFF-FCCF-07D400000001}
			 * update by Jon
			 */
			if(getApproveItemGUID().equals("{7F000001-FFFF-FFFF-A669-F876FFFFFFA0}")
					&&ins.getListtypeids().indexOf("{AC1E060B-0000-0000-4945-D4B4000001A6}")!=-1){
				List<ProceedingForm> formList = proceedingFormService.getFormsList("select t.guid,t.approveitemguid,t.formname,t.url,t.description,t.type,t.displayorder from proceedingforms t where t.type=0 and t.approveitemguid='{7F000001-0000-0000-7185-AA7900000004}'");
				ProceedingForm pf = hasEMs(getApproveItemGUID());
				if(pf!=null)
					formList.add(pf);
				setFormList(formList);
			}else if((getApproveItemGUID().equals("{7F000001-0000-0000-7185-AA7900000004}") 
						|| getApproveItemGUID().equals("{0A0100A8-FFFF-FFFF-D635-E37A0000008C}"))
						&& ins.getListtypeids().indexOf("{7F000001-FFFF-FFFF-FB7F-856400000001}") !=-1 
						&& (null !=getStepFlag() && getStepFlag().equals("1"))){
				//List<ProceedingForm> formList
				List<ProceedingForm> formList = new ArrayList<ProceedingForm>();
				ProceedingForm tempProceedingForm = new ProceedingForm();
				tempProceedingForm.setApproveitemguid(getApproveItemGUID());
				tempProceedingForm.setDescription("直接发包申请表");
				tempProceedingForm.setFormname("直接发包申请表");
				tempProceedingForm.setGuid("{7F000001-FFFF-FFFF-FCCF-07D400000001}");
				tempProceedingForm.setUrl("/engine/gettemplate.jsp?temp_Id=1006");
				tempProceedingForm.setType(0);
				tempProceedingForm.setDisplayorder(99);
				formList.add(tempProceedingForm);
				ProceedingForm pf = hasEMs(getApproveItemGUID());
				if(pf!=null)
					formList.add(pf);
				setFormList(formList);
			}else if(getApproveItemGUID().equals("{7F000001-0000-0000-7185-AA7900000004}") 
					&& ins.getListtypeids().indexOf("{7F000001-FFFF-FFFF-FB7F-856400000001}") !=-1
					&& (null != getStepFlag() && getStepFlag().equals("2"))){
				List<ProceedingForm> formList = new ArrayList<ProceedingForm>();
				ProceedingForm tempProceedingForm = new ProceedingForm();
				tempProceedingForm.setApproveitemguid(getApproveItemGUID());
				tempProceedingForm.setDescription("施工许可申请表");
				tempProceedingForm.setFormname("施工许可申请表");
				tempProceedingForm.setGuid("{7F000001-FFFF-FFFF-FCCF-07D400000001}");
				//测试环境temp_Id=1143,正式环境tmep_Id=1227
				tempProceedingForm.setUrl("/engine/gettemplate.jsp?temp_Id=1227");
				tempProceedingForm.setType(0);
				tempProceedingForm.setDisplayorder(99);
				formList.add(tempProceedingForm);
				ProceedingForm pf = hasEMs(getApproveItemGUID());
				if(pf!=null)
					formList.add(pf);
				setFormList(formList);
			}else if(getApproveItemGUID().equals("{0A0100A8-FFFF-FFFF-D635-E37A0000008C}")
					&& ins.getListtypeids().indexOf("{7F000001-FFFF-FFFF-FB7F-856400000001}") !=-1
					&& (null != getStepFlag() && getStepFlag().equals("2"))){
				List<ProceedingForm> formList = new ArrayList<ProceedingForm>();
				ProceedingForm tempProceedingForm = new ProceedingForm();
				tempProceedingForm.setApproveitemguid(getApproveItemGUID());
				tempProceedingForm.setDescription("提前介入申请表");
				tempProceedingForm.setFormname("提前介入申请表");
				tempProceedingForm.setGuid("{7F000001-FFFF-FFFF-FCCF-07D400000001}");
				//测试环境temp_Id=1180,正式环境temp_Id=1229
				tempProceedingForm.setUrl("/engine/gettemplate.jsp?temp_Id=1229");
				tempProceedingForm.setType(0);
				tempProceedingForm.setDisplayorder(99);
				formList.add(tempProceedingForm);
				ProceedingForm pf = hasEMs(getApproveItemGUID());
				if(pf!=null)
					formList.add(pf);
				setFormList(formList);				
			}else{
				List<ProceedingForm> formList = proceedingFormService.getFormsList("select t.guid,t.approveitemguid,t.formname,t.url,t.description,t.type,t.displayorder from proceedingforms t where t.type='0' and t.approveitemguid=? order by t.displayorder", new String[]{getApproveItemGUID(),"-1","-1"}); 
				if(getApproveItemGUID().equals("{7F000001-0000-0000-7185-AA7900000004}")){
					if(null != formList && formList.size()>0){
						ProceedingForm temp = formList.get(0);
						//测试环境temp_Id=1178,正式环境:temp_Id=1228
						temp.setUrl("/engine/gettemplate.jsp?temp_Id=1228");
					}
				}else if(getApproveItemGUID().equals("{0A0100A8-FFFF-FFFF-D635-E37A0000008C}")){
					if(null != formList && formList.size()>0){
						ProceedingForm temp = formList.get(0);
						//测试环境temp_Id=1183,正式环境temp_Id=1230
						temp.setUrl("/engine/gettemplate.jsp?temp_Id=1230");
					}
				}				
				ProceedingForm pf = hasEMs(getApproveItemGUID());
				if(pf!=null)
					formList.add(pf);
				setFormList(formList);
			}
			
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	/**
	 * 
	  * @MethodName: hasEMs
	  * @Description: TODO 判断该事项是否提供快递服务，如果提供则返回表单对象，否则返回null
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： itemGUID 事项GUID
	  * @return ProceedingForm    返回类型
	  * @throws
	  * 
	  * @Author kun
	  * @date 2015年7月2日  上午9:46:02
	 */
	private ProceedingForm hasEMs(String itemGUID){
		String sql = "select t.SENDEMAIL code from xzql_xzsp_base t where t.itemid=?";
		List<CodeMap> SENDEMAIL = codeMapUtil.getCodeMapList(sql, new String[]{itemGUID,"-1","-1"});
		ProceedingForm tempProceedingForm = null;
		if(SENDEMAIL!=null&&SENDEMAIL.size()>0&&"1".equals(SENDEMAIL.get(0).getCode())){
			tempProceedingForm = new ProceedingForm();
			tempProceedingForm.setApproveitemguid(itemGUID);
			tempProceedingForm.setDescription("快递服务");
			tempProceedingForm.setFormname("快递服务");
			tempProceedingForm.setGuid("{7F000001-FFFF-FFFF-FCCF-07D400000001}");
			tempProceedingForm.setUrl("/engine/gettemplate.jsp?temp_Id=1241");
			tempProceedingForm.setType(0);
			tempProceedingForm.setDisplayorder(100);
		}
		return tempProceedingForm;
	}
	/**
	 * 
	  * @Title: isCommitForms
	  * @Description: 是否都提交表单
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/isCommitForms")
	@LoginRequired(module="approveItem")
	public String isCommitForms(){
		String sql= "";
		List<CodeMap> tableList = null;
		List<CodeMap> formList = null;
		try {
			if("{7F000001-FFFF-FFFF-A669-F75700000079}".equals(getApproveItemGUID())||"{0A009FA8-FFFF-FFFF-8836-09C6000004FF}".equals(getApproveItemGUID())){
				sql = "select guid code,s.approveguid value from SB_APPROVEINSTANCE s "
						+ "where s.xmbh is not null and s.xmmc is not null and s.gcmc is not null and s.jsdw is not null and s.guid=?";
				tableList = codeMapUtil.getCodeMapList(sql, new String[]{getAppInstanceGuid(),"-1","-1"});
				if(tableList.size()==0){
					outJson("{'message':'1','msg':'请填写申请表单'}", null);
					return null;
				}else{
					outJson("{'message':'0'}", null);
					return null;
				}
			}else if("{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}".equals(getApproveItemGUID())||"{0A009FA8-0000-0000-68C9-3CDB0000004C}".equals(getApproveItemGUID())){
				sql = "select guid code,s.approveguid value from SB_APPROVEINSTANCE s "
						+ "where s.xmmc is not null and s.gcmc is not null and s.jsdw is not null  and s.guid=?";
				tableList = codeMapUtil.getCodeMapList(sql, new String[]{getAppInstanceGuid(),"-1","-1"});
				if(tableList.size()==0){
					outJson("{'message':'1','msg':'请填写申请表单'}", null);
					return null;
				}else{
					outJson("{'message':'0'}", null);
					return null;
				}
			}else{
				sql="select distinct  to_char(o.temp_id) code,d.dbtablename value "+
				   "from (select distinct substr(s.fieldalias, 0, instr(s.fieldalias, '.', 1) - 1) tableSimpId, "+
				   "   s.temp_id          from TEMPLATEFIELDDEFINE_SYS s,              " +
				   "(select replace(t.url, '/engine/gettemplate.jsp?temp_Id=', '') tempId    " +             
				   "from proceedingforms t     " +           
				   "where t.approveitemguid =?   and t.type=0 ) p   " +    
				    "where s.temp_id = p.tempId) o,      " +
				    "usertabledefine_sys d  " +
				    "where d.alias = o.tableSimpId   "+
				    "and d.dbtablename <> 'SB_APPROVEINSTANCE' ";
				tableList = codeMapUtil.getCodeMapList(sql, new String[]{getApproveItemGUID(),"-1","-1"});
				if(tableList.size()==0){
					outJson("{'message':'0'}", null);
					return null;
				}else{
					for(int i=0;i<tableList.size();i++){
						sql="select '1' code,'1' value from "+"\""+tableList.get(i).getValue()+"\" "+
						" where guid=?";
						
						formList =codeMapUtil.getCodeMapList(sql, new String[]{getAppInstanceGuid(),"-1","-1"});
						if(formList.size()>0){
							outJson("{'message':'0'}", null); 
							return null;
						}
					}
					outJson("{'message':'1','msg':'请填写申请表单'}", null);
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			outJson("{'message':'1','msg':'程序内部错误："+e.getMessage()+"'}", null);
			return null;
		}
	}
	/**
	 * 
	  * @MethodName: hasSendemail
	  * @Description: TODO 是否需要快递
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return void    返回类型
	  * @throws
	  * 
	  * @Author kun
	  * @date May 20, 2015  4:23:37 PM
	 */
//	@Action(value = "/onlineService/hasSendemail")
//	@LoginRequired(module="approveItem")
//	public void hasSendemail(){
//		try {
//			String sql="select t.SENDEMAIL code from xzql_xzsp_base t where t.itemid=?";
//			
//			List<CodeMap> tableList = codeMapUtil.getCodeMapList(sql, new String[]{getApproveItemGUID(),"-1","-1"});
//			if(tableList!=null&&tableList.size()>0){
//				if("1".equals(tableList.get(0).getCode())){
//					outJson("{'message':'1'}", null);
//				}else{
//					outJson("{'message':'0'}", null);
//				}
//			}else{
//				outJson("{'message':'0'}", null);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			outJson("{'message':'1','msg':'程序内部错误："+e.getMessage()+"'}", null);
//		}
//	}
	
	/**
	 * @Description 获取直接发包-施工许可申请数据
	 * @author Jon
	 * 
	 */
	@Action(value="/onlineService/getSharedInfo")
	@LoginRequired(module="approveItem")
	public void getSharedInfo(){
		try {
			outJson("{'message':'1'}", null);
		} catch (Exception e) {
			e.printStackTrace();
			outJson("{'message':'0'}", null);
		}
	}
	
	/**
	 * 
	 */
	@Action(value = "/onlineService/getViewforms", results = { @Result(name = "success", location = "/WEB-INF/page/userhome/forms.jsp") })
	@LoginRequired(module="approveItem")
	public String getViewforms(){
		try {
			int num=listTypeService.execute("select * from sb_listtypeofinstance t where t.guid=?",new String[]{ getAppInstanceGuid()});
			ListtypeOfApproveinstance ins=null;
			if(num<1){
				ins=new ListtypeOfApproveinstance();
				ins.setGuid(getAppInstanceGuid());
				ins.setListtypeids(getGuids());
				listTypeService.save(ins);
			}else{
				if(getGuids()!=null){
					ins=listTypeService.find(getAppInstanceGuid());
					ins.setListtypeids(getGuids());
					listTypeService.update(ins);
				}
			}
			setFormList(proceedingFormService.getFormsList("select t.guid,t.approveitemguid,t.formname,t.url,t.description,t.type,t.displayorder from proceedingforms t where t.type='2' and t.approveitemguid=? order by t.displayorder", new String[]{getApproveItemGUID(),"-1","-1"}));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 查询事项分类
	 * 直接发包guid-{7F000001-FFFF-FFFF-FB7F-856400000001}
	 * @return
	 */
	@Action(value = "/onlineService/gettypes", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/selectType.jsp") })
	@LoginRequired(module="approveItem")
	public String getTypes(){
		try {
			List<GuideFileType> typelist= guideFileTypeService.getTypesList("select t.typeguid, t.typename, t.supertypeguid from xzql_xzsp_listtype t,xzql_xzsp_base b where t.itemid=? and t.itemid=b.itemid order by t.orderno", new String[]{getApproveItemGUID(),"-1","-1"});
			int num=listTypeService.execute("select * from sb_listtypeofinstance t where t.guid=?",new String[]{ getAppInstanceGuid()});
			if(num>0){
				ListtypeOfApproveinstance ins=listTypeService.find(getAppInstanceGuid());
				setGuids(ins.getListtypeids());
			}
			
			/**
			 * 施工许可、提前介入 直接发包处理
			 * update by Jon
			 */
			if(getApproveItemGUID().equals("{7F000001-0000-0000-7185-AA7900000004}")
					|| getApproveItemGUID().equals("{0A0100A8-FFFF-FFFF-D635-E37A0000008C}")){
				GuideFileType tempGuidFileType = new GuideFileType();
				tempGuidFileType.setTypeguid("{7F000001-FFFF-FFFF-FB7F-856400000001}");
				tempGuidFileType.setTypename("直接发包");;
				typelist.add(tempGuidFileType);
			}
			
			StringBuffer sb=new StringBuffer();
			sb.append("[");
			for(int i=0;i<typelist.size();i++){
				GuideFileType gft=typelist.get(i);
				sb.append("{id:\""+gft.getTypeguid());
				sb.append("\",pId:\""+gft.getSupertypeguid());
				sb.append("\",name:\""+gft.getTypename());
				sb.append("\"");
				if(getGuids()!=null){
					for(int a=0;a<getGuids().split(",").length;a++){
						if(getGuids().split(",")[a].equals(gft.getTypeguid())){
							sb.append(",checked:true,open:true");
						}
					}
				}
				sb.append("}");
				if(i!=typelist.size()-1){
					sb.append(",");
				}
			}
			sb.append("]");
			setTypejsonString(sb.toString());
			System.out.println(sb.toString());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 根据事项ID找到表格下载和办事指南
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/findInfo", results = { 
			@Result(name = "fileTableTemp", location = "/WEB-INF/page/onlineservice/appApply/tableTemp.jsp"),
			@Result(name = "appGuideInfo", location = "/WEB-INF/page/onlineservice/appApply/appGuideInfo.jsp")})
	@LoginRequired(module="approveItem")
	public String findInfo(){
		try {
			if(StringUtils.isNotBlank(getMethod())&&"fileTableTemp".equals(getMethod())){
				setAppTabTempList(approveItemTabTempService.getScrollData(-1, -1, new String[]{"o.itemid=?"}, new String[]{getApproveItemGUID()}).getResultList());
				return "fileTableTemp";
			}else if(StringUtils.isNotBlank(getMethod())&&"appGuideInfo".equals(getMethod())){
				setApproveItem(approveItemService.getGuideInfo(getApproveItemGUID()));
				return "appGuideInfo";
			}
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	/**
	 * 
	  * @Title: findCommunity
	  * @Description: 根据街道ID找到社区
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/findCommunity", results = { @Result(name = "success", type="json") })
	@LoginRequired(module="approveItem")
	public String findCommunity(){
		String sql = "select code,value from (select d.department_guid code,d.department_name value,d.tabindex " +
				"from  risenet_department d where  exists (select 1 from risenet_department t,xzql_item_depart_org o " +
				"where t.superior_guid=? and t.department_name='社区工作站' and o.orgid=t.department_guid and o.itemid=? " +
				"and d.superior_guid=t.department_guid) union all select d.department_guid code," +
				"d.department_name value,d.tabindex from xzql_item_depart_org o,risenet_department d " +
				"where d.department_guid=o.orgid(+) and d.department_name<>'社区工作站' " +
				"and d.superior_guid=? " +
				"and o.itemid=?) order by tabindex";
		outJsonArray(codeMapUtil.getCodeMapList(sql,new String[]{getDepartGUID(),getApproveItemGUID(),getDepartGUID(),getApproveItemGUID(),"-1","-1"}),null);
		return null;
	}
	
	/**
	 * 
	  * @Title: findFileByAppGuid
	  * @Description: 异步刷新上传材料清单
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/saveApply", results = { 
			@Result(name = "success",location="/WEB-INF/page/onlineservice/appApply/appSuccess.jsp")})
	@LoginRequired(module="approveItem") 
	public String saveApply(){
		try {
			//保存申报信息
			Approveinstance newIns = getApproveinstance();
			Approveinstance oldIns=instanceService.find(newIns.getGuid());
			ApproveItem approveItem = approveItemService.find(newIns.getApproveguid());
			if(oldIns==null){
				newIns.setDeclaresn(codeMapUtil.getDeclaresnByBureauGuid(newIns.getBureauguid()));
				//申报时不产生申报流水号（需要产生流水号时注销注释）
			//	if(newIns.getIssubmit()==1)
			//		newIns.setSjtybh(ScjgkXmxxDao.getSblshBySxbm(approveItem.getApproveitembm(), approveItem.getItemid()));
				newIns.setSubmittime(new Date(System.currentTimeMillis()));
				newIns.setUserid(getUserGUID());
				newIns.setDataFlag("0");
				newIns.setFormname(approveItem.getApproveitemname());
				newIns.setUserMobile((String)getSession().getAttribute(Common.userMobile));
				newIns.setLxrName((String)getSession().getAttribute(Common.lxrName));
				instanceService.saveOrUpdate(newIns);
			}else{
				if(StringUtils.isBlank(oldIns.getDeclaresn())){
					oldIns.setDeclaresn(codeMapUtil.getDeclaresnByBureauGuid(newIns.getBureauguid()));
				}
				//申报时不产生申报流水号（需要产生流水号时注销注释）
			//	if(StringUtils.isBlank(oldIns.getSjtybh())&&newIns.getIssubmit()==1){
			//		oldIns.setSjtybh(ScjgkXmxxDao.getSblshBySxbm(approveItem.getApproveitembm(), approveItem.getItemid()));
				//}
				if(StringUtils.isBlank(oldIns.getFormname())||!approveItem.getApproveitemname().equals(oldIns.getFormname()))
					oldIns.setFormname(approveItem.getApproveitemname());
				if(StringUtils.isBlank(oldIns.getUserid()))
					oldIns.setUserid(getUserGUID());
				if(StringUtils.isBlank(oldIns.getUserMobile()))
					oldIns.setUserMobile((String)getSession().getAttribute(Common.userMobile));
				if(StringUtils.isBlank(oldIns.getLxrName()))
					oldIns.setLxrName((String)getSession().getAttribute(Common.lxrName));
				if(StringUtils.isBlank(oldIns.getBureauguid()))
					oldIns.setBureauguid(newIns.getBureauguid());
				if(StringUtils.isBlank(oldIns.getDeptguid()))
					oldIns.setDeptguid(newIns.getDeptguid());
				if(StringUtils.isBlank(oldIns.getApproveguid())){
					oldIns.setApproveguid(newIns.getApproveguid());
				}
				oldIns.setSubmittime(new Date(System.currentTimeMillis()));
				oldIns.setStatus(newIns.getStatus());
				oldIns.setIssubmit(newIns.getIssubmit());
				oldIns.setHfstate("0");
				oldIns.setDataFlag("0");
				instanceService.saveOrUpdate(oldIns);
				setApproveinstance(oldIns);
			}
		
			//查询材料上传情况
			String sql ="";
			String guids="";
			ListtypeOfApproveinstance loa=listTypeService.find(newIns.getGuid());
			if(loa!=null&&loa.getListtypeids()!=null){
				String[] list= loa.getListtypeids().split(",");
				for(int i=0;i<list.length;i++){
					guids=guids+"'"+list[i]+"'";
					if(i!=list.length-1){
						guids=guids+",";
					}
				}
//				sql=" select l.materialname,decode(w.declareannexguid,null,'0','1') type from xzql_xzsp_lists l,SPM_WSSBCL w"
//				 +" where l.id in (select t.listguid from xzql_xzsp_listoftype t where t.typeguid in ("+guids+")) and w.declareannexguid(+) = l.id"
//				 +" and w.workflowinstance_guid(+)=? order by l.orderno";
				sql = "(select l.materialname, '1' \"type\" from xzql_xzsp_lists l where l.id in(select t.listguid "
					 +"from xzql_xzsp_listoftype t where t.typeguid in ("+guids+")) "
					 +"and (exists (select 1 from SPM_WSSBCL w where w.declareannexguid=l.id and w.workflowinstance_guid = ? ) "
					 +"or exists(select 1 from SPM_WSSBCL_DOCTYPE g where g.clguid=l.id and g.xmgguid = ? )))"
					 +"union all "
					 +"(select l.materialname, '0' \"type\" from xzql_xzsp_lists l where l.id in(select t.listguid "
					 +"from xzql_xzsp_listoftype t where t.typeguid in ("+guids+")) "
					 +"and (not exists (select 1 from SPM_WSSBCL w where w.declareannexguid=l.id and w.workflowinstance_guid= ? )"
					 +"and not exists(select 1 from SPM_WSSBCL_DOCTYPE g where g.clguid=l.id and g.xmgguid= ? )))"
					 ;
				String[] params = new String[]{newIns.getGuid(),newIns.getGuid(),newIns.getGuid(),newIns.getGuid(),"-1","-1"};
				getApproveinstance().setFileList(guidFileSimpleJdbcDao.queryForRow(sql, params, GuideFile.class));
			}else{
//				sql = "select l.materialname, decode(w.declareannexguid,null,'0','1') type" +
//						" from xzql_xzsp_lists l, SPM_WSSBCL w where w.declareannexguid(+) = l.id  " +
//						"and l.itemid = ? and w.workflowinstance_guid(+) = ?";
				sql = "select l.materialname, '1' \"type\" from xzql_xzsp_lists l where l.itemid = ? "
					 +"and (exists (select 1 from SPM_WSSBCL w where w.declareannexguid=l.id and w.workflowinstance_guid = ? ) "
					 +"or exists(select 1 from SPM_WSSBCL_DOCTYPE g where g.clguid=l.id and g.xmgguid = ? )) "
					 +"union all "
					 +"select l.materialname, '0' \"type\" from xzql_xzsp_lists l where l.itemid = ? "
					 +"and (not exists (select 1 from SPM_WSSBCL w where w.declareannexguid=l.id and w.workflowinstance_guid = ? )"
					 +"and not exists(select 1 from SPM_WSSBCL_DOCTYPE g where g.clguid=l.id and g.xmgguid = ? )) "
					 ;
				String[] params = new String[]{newIns.getApproveguid(),newIns.getGuid(),newIns.getGuid(),newIns.getApproveguid(),newIns.getGuid(),newIns.getGuid(),"-1","-1"};
				getApproveinstance().setFileList(guidFileSimpleJdbcDao.queryForRow(sql, params, GuideFile.class));
			}
			
			
			//插入省厅需要的申办数据
			//addShenban(ins.getGuid());
			
			//给窗口单位发送数据
			//sendMessage(ins.getApproveguid());
			/* 暂时停用
			sendMessage(ins.getBureauguid(),ins.getDeptguid());
			*/
			
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			outJson("{'message':'0'}", null);
			return null;
		}
	}
	
	private void sendMessage(String bureauguid,String deptguid){
		//发送短信判断局信息是委办局还是街道办事处，1为街道，0为委办局
		try {
			BureauBean bureauBean = bureauService.find(bureauguid);
			String sql = "select e.employee_mobile code,e.employee_name value from" +
						" RiseNet_UserGroupMembers u,risenet_employee e,risenet_department d " +
						" where u.usergroup_guid='{AC2003C8-0000-0000-209C-CEF100000002}' " +
						" and u.employee_guid=e.employee_guid " +
						" and e.department_guid=d.department_guid ";
			List<CodeMap> employeeList = null;
			if("0".equals(bureauBean.getIsstreet())){
				sql += " and d.superior_guid=?";
				employeeList = codeMapUtil.getCodeMapList(sql, new String[]{bureauguid,"-1","-1"});
			}else if("1".equals(bureauBean.getIsstreet())){
				sql += " and d.department_guid=?";
				employeeList = codeMapUtil.getCodeMapList(sql, new String[]{deptguid,"-1","-1"});
			}
			if(employeeList!=null&&employeeList.size()>0){
				MessageConfig config =  sendMessageService.findBeanBypro("smsno", "WSSB");
				String date = "";
				if(config!=null)date = DateFormatUtil.parseToString(codeMapUtil.caculateEndDate(new java.util.Date(), Integer.parseInt(config.getSendtype())));
				for(CodeMap em:employeeList){
					if(StringUtils.isNotBlank(em.getCode())){
						sendMessageService.sendMessage(em.getCode(), "WSSB", new String[]{date});
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String appitemGuid){
		try{
			String approveitemname= approveItemService.find(appitemGuid).getApproveitemname();
			String sql="select a.employee_mobile code from risenet_employee a where instr((select t.empid from xzql_xzsp_extend t where t.itemid = ?),a.employee_guid) > 0";
			List<CodeMap> employeeList = codeMapUtil.getCodeMapList(sql, new String[]{appitemGuid,"-1","-1"});
			String usertype=getUser().getUsertype();
			String name="";
			if(usertype.equals("1")){
				name=getUser().getPersonUser().getTrue_name();
			}else{
				name=getUser().getCompanyUser().getEname();
			}
			String [] par=new String[]{name,approveitemname};
			for(CodeMap em:employeeList){
				if(StringUtils.isNotBlank(em.getCode())){
					sendMessageService.sendMessage(em.getCode(), "WSSB", par);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	  * @Title: addShenban
	  * @Description: 申报成功向申办表中插入审批数据
	  * @param @param inisGUID    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	private void addShenban(String inisGUID){
		String sql = "select q.ywlsh,q.sblsh ,q.spsxbh ,q.spsxzxbh ,q.yxtywlsh ,q.spsxmc ,q.sqrlx ,q.sqrmc ," +
		"q.sqrzjhm ,q.lxrxm ,q.lxrzjlx ,q.lxrsfzjhm ,q.lxrsj," +
		"q.lxryx ,q.sbxmmc ,q.sbclqd ,q.tjfs ,q.sbhzh  ,q.sbjtwd ," +
		"q.xzqhdm ,q.departid,q.datasource,nvl(max(s.sjbbh),0)+1 sjbbh" +
		" from ( select func_createywlsh('430303'||b.institutioncode||e.ftcode,e.ftsubcode,a.declaresn) ywlsh, "+
			" t.approveitembm spsxbh, "+
			" e.ftsubcode spsxzxbh, "+
			" a.declaresn yxtywlsh, "+
			" a.sjtybh sblsh,"+
			" t.approveitemname spsxmc, "+
			" decode(u.usertype,'1','1','2','2','9') sqrlx, "+
			" u.USERNAME sqrmc, "+
			" u.CARDID sqrzjhm, "+
			" u.lxrxm, "+
			" decode(u.CARDTYPE,'1','10','2','20','3','50') lxrzjlx, "+
			" u.lxrsfzjhm, "+
			" u.MOBILE lxrsj, "+
			" u.email lxryx, "+
			" t.approveitemname sbxmmc, "+
			" func_getupfile(a.guid) sbclqd, "+
			" '1' tjfs, "+
			" a.declaresn sbhzh, "+
			" '广东省网上办事大厅深圳分厅住房和建设局窗口' sbjtwd, "+
			" '430303' xzqhdm, "+
			" b.institutioncode departid, "+
			" 'XZSP' datasource "+
			" from xzql_xzsp_base t, "+
			" xzql_xzsp_extend e, "+
			" sb_approveinstance a, "+
			" view_user u, "+
			" spm_bureau b "+
			" where  "+
			" t.itemid=e.itemid "+
			" and t.itemid=a.approveguid "+
			" and e.itemid=a.approveguid "+
			" and b.bureauguid=a.bureauguid "+
			" and a.userid=u.guid "+
			" and a.guid = ? ) q,t_shenban s "+
			" where s.ywlsh(+)=q.ywlsh group by  "+
			" q.ywlsh ,q.spsxbh ,q.spsxzxbh,q.sblsh ,q.yxtywlsh ,q.spsxmc ,q.sqrlx ,q.sqrmc ,q.sqrzjhm ,q.lxrxm ,q.lxrzjlx ,q.lxrsfzjhm ,q.lxrsj "+
			" ,q.lxryx ,q.sbxmmc ,q.sbclqd ,q.tjfs ,q.sbhzh  ,q.sbjtwd ,q.xzqhdm ,q.departid,q.datasource";
			try {
				ApproveItemApply aia = itemApplySimpleJdbcDao.getBean(sql, new String[]{inisGUID,"-1","-1"}, ApproveItemApply.class);
				if(aia!=null){
					aia.setSbsj(new Timestamp(System.currentTimeMillis()));
					if(aia.getSbclqd()==null||aia.getSbclqd()==""){
						aia.setSbclqd("无上传材料");
					}
					approveItemApplyService.save(aia);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	/**
	 * 
	  * @Title: findFileByAppGuid
	  * @Description: 异步刷新上传材料清单
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/findFileByAppGuid", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/fileTable.jsp") })
	@LoginRequired(module="approveItem")
	public String findFileByAppGuid(){
		try {
			setApproveItem(approveItemService.find(getApproveItemGUID()));
			//查询有材料分类的材料
			String sql ="";
			List<GuideFile> guideFileList=null;
			List<GuideFile> guidenoFileList=new ArrayList<GuideFile>();
			List<GuideFile> guideUploadList=new ArrayList<GuideFile>();
			ListtypeOfApproveinstance ins=listTypeService.find(getAppInstanceGuid());
			String guids="";
			if(ins!=null&&ins.getListtypeids()!=null){
				String[] list= ins.getListtypeids().split(",");
				for(int i=0;i<list.length;i++){
					guids=guids+"'"+list[i]+"'";
					if(i!=list.length-1){
						guids=guids+",";
					}
				}
				/**
				 * 直接发包，特殊处理
				 * 因为直接发包编号是手动生成的
				 * 当选择了直接发包，修改为施工许可
				 */
				if(getApproveItemGUID().equals("{7F000001-0000-0000-7185-AA7900000004}") 
						&& guids.indexOf("{7F000001-FFFF-FFFF-FB7F-856400000001}") != -1){
					guids = guids.replace("{7F000001-FFFF-FFFF-FB7F-856400000001}", "{BFA80D01-FFFF-FFFF-FCA5-74CEFFFFFFEF}");
				}else if(getApproveItemGUID().equals("{0A0100A8-FFFF-FFFF-D635-E37A0000008C}") 
							&& guids.indexOf("{7F000001-FFFF-FFFF-FB7F-856400000001}") !=-1){
					guids = guids.replace("{7F000001-FFFF-FFFF-FB7F-856400000001}", "{0A0100A8-FFFF-FFFF-D660-B2E6000000C5}");
				}
				sql="select l.id,l.materialname,l.describe,to_char(l.doctypeguid) doctypeguid,l.online_essential,d.code,d.name,d.share_code,d.code_model,d.isok from xzql_xzsp_lists l left join xzql_xzsp_doctype d on to_char(l.doctypeguid) = d.guid where l.id in ( select t.listguid from xzql_xzsp_listoftype t where t.typeguid in("+guids+")) order by l.orderno";
				guideFileList = guidFileSimpleJdbcDao.queryForRow(sql, GuideFile.class);
			}else{
				//查询不存在分类的材料
				sql = "select l.id,l.materialname,l.describe,to_char(l.doctypeguid) doctypeguid,online_essential,a.code,a.name,a.share_code,a.code_model,a.isok from xzql_xzsp_lists l,xzql_xzsp_doctype a where not exists(select 1 from xzql_xzsp_listoftype o where l.id=o.listguid) and to_char(l.doctypeguid)=a.guid(+) and l.itemid=?  order by l.orderno";
				guideFileList= guidFileSimpleJdbcDao.queryForRow(sql, new String[]{getApproveItemGUID(),"-1","-1"}, GuideFile.class);
			}
			
			//查询被标识为“已提交”的材料guid getApproveItemGUID()
		//	sql = "select t.ytjids,t.guid from sb_approveinstance t where t.guid = ? ";
		//	Object[] params = {getAppInstanceGuid(),"1","1"};
		//	YtjclbzBean bzBean = YtjclbzDao.getBean(sql, params, YtjclbzBean.class);
			
			//查询已填写的证照号
			sql = "select t.guid,t.zzname,t.cardid from SPM_WSSBCL_DOCTYPE t where t.xmgguid=? and t.clguid=?";
			for(int i=0;i<guideFileList.size();i++){
				GuideFile gf=guideFileList.get(i);
				gf.setFileList(applyUpFileService.getScrollData(-1, -1, new String[]{"declareannexguid=?","workflowinstance_guid=?"}, new String[]{gf.getId(),getAppInstanceGuid()}).getResultList());
				
				gf.setDoctypeList(webFileDocTypeSimpleJdbcDao.queryForRow(sql, new String[]{getAppInstanceGuid(),gf.getId(),"-1","-1"}, WebApplyFileDoctype.class));
				
				//存在共享材料列表时
				//@Date 2013-10-17
				if(gf.getDoctypeList()!=null&&gf.getDoctypeList().size()>0){
					gf.setGuid(gf.getDoctypeList().get(0).getGuid());
					gf.setCardid(gf.getDoctypeList().get(0).getCardid());
				}
				
				if(StringUtils.isNotBlank(gf.getDescribe())){
					gf.setDescribe(WebUtil.filter(gf.getDescribe()));
				}else{
					gf.setDescribe("0");
				}
				
				/***
				 * 注释掉，判断材料是否已提交
				 * 只要是上传的材料都能重新删除、修改
				 * update by Jon
				 */
				Approveinstance temp = instanceService.find(getAppInstanceGuid());
				if(gf.getDoctypeguid()!=null){//判断材料是否被标识为已提交
					/*if(bzBean!=null&&bzBean.getYtjids()!=null&&bzBean.getYtjids().indexOf(gf.getId())>0){//被标识为已提交
						gf.setBz("1");
					}else{//未被标志位已提交
						gf.setBz("0");
					}*/
					if(temp.getIssubmit()!=0&&temp.getIssubmit()!=1){
						gf.setBz("1");//判断是否转窗口收件后
					}else{
						gf.setBz("0");
					}
					guidenoFileList.add(gf);
//					guideFileList.remove(gf);
				}else{
					/*if(bzBean!=null&&bzBean.getYtjids()!=null&&bzBean.getYtjids().indexOf(gf.getId())>0){//被标识为已提交
						gf.setBz("0");
					}else{//未被标志位已提交
						gf.setBz("0");
					}*/
					if(temp.getIssubmit()!=0&&temp.getIssubmit()!=1){
						gf.setBz("1");//判断是否转窗口收件后
					}else{
						gf.setBz("0");
					}
					guideUploadList.add(gf);
				}
				
			}
			setGuidenoFileList(guidenoFileList);
			setGuideFileList(guideUploadList);
			
			
			/**
			 * 判读改事项是否属于招标文件
			 * 如果属于招标文件，当再次修改时，需要重新带出工程信息
			 * update by Jon
			 */
		//	boolean isZbwj = isZbwjApproveItem(getApproveItemGUID());
	//		setZbwjFlag(isZbwj==true ? "1" : "0");
			
//			Approveinstance  instance= null;
//			if(isZbwj && StringUtils.isNotBlank(getAppInstanceGuid())){
//				instance = instanceService.find(getAppInstanceGuid());
//				if(null != instance){
//					if(StringUtils.isBlank(getGONGCHENGMINGCHENG()) && StringUtils.isNotBlank(instance.getGcmc())){
//						setGONGCHENGMINGCHENG(instance.getGcmc());
//					}
//					if(StringUtils.isBlank(getGONGCHENGBIANHAO()) && StringUtils.isNotBlank(instance.getGcbh())){
//						setGONGCHENGBIANHAO(instance.getGcbh());
//					}
//					
//					if(StringUtils.isBlank(getXIANGMUMINGCHENG()) && StringUtils.isNotBlank(instance.getXmmc())){
//						setXIANGMUMINGCHENG(instance.getXmmc());
//					}
//					if(StringUtils.isBlank(getSGGK_xmmc()) && StringUtils.isNotBlank(instance.getXmmc())){
//						setSGGK_xmmc(instance.getXmmc());
//					}
//					if(StringUtils.isBlank(getSGGK_xmbh()) && StringUtils.isNotBlank(instance.getXmbh())){
//						setSGGK_xmbh(instance.getXmbh());
//					}
//					if(StringUtils.isBlank(getSGGK_zbr()) && StringUtils.isNotBlank(instance.getJsdw())){
//						setSGGK_zbr(instance.getJsdw());
//					}
//				}
//			}
			
			//查询材料类型
			/*LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("orderno", "asc");
			List<GuideFileType> guideFileTypeList = guideFileTypeService.getScrollData(-1, -1, new String[]{"o.itemid=?"}, new String[]{getApproveItemGUID()},orderMap).getResultList();
			//根据材料类型查找材料list
			sql = "select l.id,l.materialname,l.describe,to_char(l.doctypeguid) doctypeguid from xzql_xzsp_lists l,xzql_xzsp_listoftype t where l.id=t.listguid and t.typeguid=? and t.itemid=?  order by l.orderno";
			
			
			for(GuideFileType gt:guideFileTypeList){
				guideFileList = guidFileSimpleJdbcDao.queryForRow(sql, new String[]{gt.getTypeguid(),getApproveItemGUID(),"-1","-1"},GuideFile.class);
				//查询已填写的证照号
				sql = "select t.guid,t.zzname,t.cardid from SPM_WSSBCL_DOCTYPE t where t.xmgguid=? and t.clguid=?";
				for(GuideFile gf:guideFileList){
					gf.setFileList(applyUpFileService.getScrollData(-1, -1, new String[]{"declareannexguid=?","workflowinstance_guid=?"}, new String[]{gf.getId(),getAppInstanceGuid()}).getResultList());
					gf.setDoctypeList(webFileDocTypeSimpleJdbcDao.queryForRow(sql, new String[]{getAppInstanceGuid(),gf.getId(),"-1","-1"}, WebApplyFileDoctype.class));
					if(StringUtils.isNotBlank(gf.getDescribe())){
						gf.setDescribe(WebUtil.filter(gf.getDescribe()));
					}else{
						gf.setDescribe("0");
					}
				}
				gt.setGuideFileList(guideFileList);
			}
			setGuideFilTypeeList(guideFileTypeList);*/
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	/**
	  * @MethodName: isck
	  * @Description: 判断是否转窗口收件
	 */
	@Action(value = "/onlineService/sick")
	@LoginRequired(module="approveItem")
	public String isck(){
		Approveinstance temp = new Approveinstance();
		try {
			temp = instanceService.find(getAppInstanceGuid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(temp.getIssubmit()!=0&&temp.getIssubmit()!=1){
			outJson("{'message':'1'}", null);
		}else{
			outJson("{'message':'0'}", "");
		}
		return null;
	}
	
	public void selectGcInfo(){
		/**
		 * {0A009FA8-FFFF-FFFF-8836-09C6000004FF}  建设工程监理
		 */
		
	}
	
	
	/**
	 * @Description 保存共享材料信息
	 * @author HJL
	 * @date 2013-10-17
	 */
	@Action(value="/onlineService/newSaveDocType")
	@LoginRequired(module="approveItem")
	public void newSaveDocType(){
		String hadFind = findDocTypeByFileGuid();
		if(hadFind!="error"){
			String gxclguid = null;
			if(getDocTypeList()!=null&&getDocTypeList().size()>0){
				if(webApplyFileDoctype==null){
					webApplyFileDoctype = new WebApplyFileDoctype();
				}
				if(StringUtils.isBlank(webApplyFileDoctype.getGuid())){//新增
					webApplyFileDoctype.setGuid(GUID.getGUID());
					webApplyFileDoctype.setCreateuser(getUserGUID());
					webApplyFileDoctype.setCreatedate(new java.util.Date());
					gxclguid = webApplyFileDoctype.getGuid();
				}else{//修改
					webApplyFileDoctype.setCreatedate(getDocTypeList().get(0).getCreatedate());
					webApplyFileDoctype.setCreateuser(getDocTypeList().get(0).getCreateuser());
				}
				webApplyFileDoctype.setXmgguid(getAppInstanceGuid());
				webApplyFileDoctype.setClguid(getFileGuid());
				webApplyFileDoctype.setZzguid(getDocTypeList().get(0).getZzguid());
				webApplyFileDoctype.setCardid(getCardid());
				webApplyFileDoctype.setZzname(getDocTypeList().get(0).getClguid());
				
				try {
					webApplyDocTypeService.saveOrUpdate(webApplyFileDoctype);
					outJson("{'message':'1','gxclguid':'"+gxclguid+"'}", null);
				} catch (Exception e) {
					e.printStackTrace();
					outJson("{'message':'0'}", null);
				}
			}
		}else{
			outJson("{'message':'0'}", null);
		}
	}
	
	@Action(value = "/onlineService/findDocTypeByFileGuid", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/fileDocType.jsp") })
	@LoginRequired(module="approveItem")
	public String findDocTypeByFileGuid(){
		try {
			GuideFile guideFile = guideFileService.find(getFileGuid());
			String sql = "select c.guid,d.guid zzguid, d.name xmgguid, d.share_code clguid, d.code_model zzname,c.cardid,c.createdate,c.createuser  " +
					" from xzql_xzsp_doctype d,spm_wssbcl_doctype c " +
					" where d.guid in ("+WebUtil.strToParam(guideFile.getDoctypeguid(),",")+") " +
					" and c.zzguid(+)=d.guid and c.xmgguid(+)=? and c.clguid(+)=?";
			
			setDocTypeList(webFileDocTypeSimpleJdbcDao.queryForRow(sql, new String[]{getAppInstanceGuid(),getFileGuid(),"-1","-1"},WebApplyFileDoctype.class));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	@Action(value = "/onlineService/saveDocType", results = { @Result(name = "success", type="json") })
	@LoginRequired(module="approveItem")
	public String saveDocType(){
		try {
			if(StringUtils.isBlank(webApplyFileDoctype.getGuid())){
				webApplyFileDoctype.setGuid(GUID.getGUID());
				webApplyFileDoctype.setCreateuser(getUserGUID());
				webApplyFileDoctype.setCreatedate(new java.util.Date());
			}
			webApplyDocTypeService.saveOrUpdate(webApplyFileDoctype);
			outJson("{'message':'1'}", null);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			outJson("{'message':'0'}", null);
			return null;
		}
	}
	
	@Action(value = "/onlineService/deleteDocType", results = { @Result(name = "success", type="json") })
	@LoginRequired(module="approveItem")
	public String deleteDocType(){
		try {
			webApplyDocTypeService.delete(webApplyDocTypeService.find(getFileGuid()));
			outJson("{'message':'1'}", null);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			outJson("{'message':'0'}", null);
			return null;
		}
	}
	/**
	 * 
	  * @Title: initFileUpPanel
	  * @Description: 初始化上传材料面板
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/initFileUpPanel", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/upFilePanel.jsp") })
	@LoginRequired(module="approveItem")
	public String initFileUpPanel() {
		return SUCCESS;
	}
	
	
	/**
	 * @desciption 校验必须上传的文件是否都已上传
	 * @return
	 */
	@Action(value = "/onlineService/isUpFile", results = { @Result(name = "success", type="json") })
	@LoginRequired(module="approveItem")
	public String isUpFile() {
		try {
			List<WebApplyUpFile> list = applyUpFileService.findByProperty("workflowinstance_guid", getAppInstanceGuid());//已上传的材料
			findFileByAppGuid();//调用查询需要上传的材料的方法
			String sql = "select t.* from SPM_WSSBCL_DOCTYPE t where t.xmgguid=? ";
			//已填写的已实现接口的共享材料
			setDocTypeList(webFileDocTypeSimpleJdbcDao.queryForRow(sql, new String[]{getAppInstanceGuid(),"-1","-1"}, WebApplyFileDoctype.class));

			Map<String,String> upMap = new HashMap<String, String>();//已上传材料guid的map
			for(int i=0;i<list.size();i++)
			{
				upMap.put(list.get(i).getDeclareannexguid(),i+"");//把已上传材料guid存放到map
			}
			
			List<String> mustList = new ArrayList<String>();//必须上传的材料GUID（已实现接口的共享材料除外）
			List<String> mustShareList = new ArrayList<String>();//必须上传的已实现接口的共享材料
			if(guideFileList!=null){//没有材料类型的材料
				for(int i=0;i<guideFileList.size();i++)
				{
					if("1".equals(guideFileList.get(i).getOnline_essential())){//必须上传材料
						mustList.add(guideFileList.get(i).getId());
					}
				}
			}
			if(guidenoFileList!=null){//可材料共享的材料不为空
				for(int i=0;i<guidenoFileList.size();i++)
				{
					if("1".equals(guidenoFileList.get(i).getOnline_essential()))
					{
						if(guidenoFileList.get(i).getIsok().equals("1")){//已实现接口的共享材料
							mustShareList.add(guidenoFileList.get(i).getId());
						}else{//必须上传的材料GUID（已实现接口的共享材料除外）
							mustList.add(guidenoFileList.get(i).getId());
						}
					}
				}
			}
			
			//必须上传的已实现接口的共享材料非空，且必须上传的已实现接口的共享材料个数大于已上传的已实现接口的共享材料个数
			//if(mustShareList!=null&&mustShareList.size()>0&&mustShareList.size()>docTypeList.size()){
			//	outJson("{'message':'0'}", null);
			//	return null;
			//}
			Map<String,String> shareUpMap = null;
			//必须上传的已实现接口的共享材料非空，且必须上传的已实现接口的共享材料个数不大于已上传的已实现接口的共享材料个数
			if(mustShareList!=null&&mustShareList.size()>0){
				shareUpMap = new HashMap<String, String>();
				for(int i=0;i<docTypeList.size();i++){
					shareUpMap.put(docTypeList.get(i).getClguid(), i+"");
				}
				for(int i=0;i<mustShareList.size();i++)
				{
					if(!shareUpMap.containsKey(mustShareList.get(i))){//使用必须上传的已实现接口的共享材料guid去匹配Map中已上传材料guid，若有匹配不上的则返回
						//outJson("{'message':'0'}", null);
						//return null;
						mustList.add(mustShareList.get(i));
					}
				}
			}
			
			//必需上传的材料（已实现接口的共享材料除外）非空，且没有已上传材料
			if((mustList!=null&&mustList.size()>0)&&(list==null||list.size()<1)){
				outJson("{'message':'0'}", null);
				return null;
			}
			//必需上传的材料（已实现接口的共享材料除外）非空，且必须上传材料个数大于已上传上传材料个数
			if(mustList!=null&&list!=null&&mustList.size()>0&&list.size()>0&&mustList.size()>list.size()){
				outJson("{'message':'0'}", null);
				return null;
			}
			//必须上传的材料（已实现接口的共享材料除外）非空，且必须上传材料个数不大于已上传上传材料个数
			if(mustList!=null&&list!=null&&mustList.size()>0&&list.size()>0&&mustList.size()<=list.size()){
				for(int i=0;i<mustList.size();i++)
				{
					if(!upMap.containsKey(mustList.get(i))){//使用必须上传的材料guid（已实现接口的共享材料除外）去匹配Map中已上传材料guid，若有匹配不上的则返回
						outJson("{'message':'0'}", null);
						return null;
					}
				}
			}
			
			
			outJson("{'message':'1'}", null);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	public List<SearchKeyWord> getKeyWordList() {
		return keyWordList;
	}
	public void setKeyWordList(List<SearchKeyWord> keyWordList) {
		this.keyWordList = keyWordList;
	}
	
	/**
	 * 
	  * @Title: initFileUpPanel
	  * @Description: 初始化上传材料面板
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/onlineService/selectYwbwProjectInfo", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/selectYwbwProjectInfo.jsp") })
	public String selectYwbwProjectInfo() {	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try{					
			Map<String, String> dataSource = codeMapUtil.getMapByType("dataSource.properties");
			String url=dataSource.get("ywbw.database.url");
			String username=dataSource.get("ywbw.database.user");
			String pass=dataSource.get("ywbw.database.password");
			conn = DriverManager.getConnection(url,username,pass);
			YwbwProjectInfo pro = new YwbwProjectInfo();
			//项目经理SQL
			String mgrSql="(select distinct e.name mgr_name,"+
						"h.REG_CERT_NO mgr_REG_CERT_NO,"+
						"h.alt_cert_id,"+
						"h.ID_NUMBER as mgr_card,"+
						"nvl(e.mobile,decode(length(e.tel),11,e.tel,''))  as mgr_mobile,"+
						"c.prj_id "+
						"from emp_qual_history h, EMP_PERSINFO e,PRJ_BID_COMP c "+
						"where h.id_number = e.id_number "+
						"and (h.alt_cert_id = c.mgr_cert or h.id_number=c.mgr_cert ) "+ 
						"and c.mgr_name=e.name and h.tag_del = '0' ) mgr";
			String sql = "select t.PRJ_ID,t.ITEM_ID,t.PRJ_NAME,t.PRJ_LOCATION,t.CONST_ORG,t.INS_DEPT,t.PRJ_CLASSLEV,t.AUCT_RESP,t.AUCT_HANDLER,t.AUCT_TEL,t.AUCT_MOBILE," +
					"t.PART_VAL,t.FUND_GOV,t.FUND_NAT,t.FUND_COMM,t.FUNN_PRIV,t.FUND_FOR," +
					"t.FUND_OTH,t.FUND_FULFILL,t.PRJ_CLASS,t.BLDG_MEASURE,t.BLDG_BID_MEASURE,t.BLDG_UP_TYPE,t.BLDG_BASE_TYPE,t.BLDG_SIZE,t.BLDG_DEPTH,t.BLDG_BID_RANGE," +
					"t.PUB_SIZE,t.PUB_STRUCTURE,t.PUB_BID_RANGE,t.ADORN_MEASURE,t.ADORN_SUB,t.ADORN_BID_RANGE,t.OTH_MEASURE,t.OTH_SUB,t.OTH_BID_RANGE,t.PROSP_NAME," +
					"t.PROSP_LOG,t.DESIGN_NAME,t.DESIGN_LOG,t.SUP_ID,t.SUP_NAME,t.SUP_LOG,t.ISPREJ,t.APPL_METHOD,t.APPR_METHOD,t.QUAL_CHECK,t.APPR_DEPT,t.PROCESS,t.PRJ_TYPE,t.MEMO_BY," +
					"t.fund_gov,t.fund_nat,t.fund_comm,t.funn_priv,t.fund_for,t.fund_oth,t.bldg_up_type,t.bldg_base_type,"+
					"p.DOC_ID,p.PRJ_LIC_ID,p.item_name,pac.exp_start_date,pac.exp_end_date,pac.fb_const_price,t.agent_name,t.agent_qual,t.agent_cert,t.agent_resp,t.agent_handler,t.agent_tel,t.agent_moblie, " +
					"mgr.mgr_name,mgr.mgr_REG_CERT_NO,mgr.mgr_card,mgr.mgr_mobile "+
					"from PRJ_CONST_BIDS t,PRJ_PRJINFO p,PRJ_PACT_CHECKIN pac," +mgrSql+" "+
					"where t.item_id=p.item_id(+) and pac.prj_id(+)=t.prj_id and t.prj_id=mgr.prj_id(+) ";			
			if(getRequest().getParameter("method")==null)
				sql = "select t.*,p.item_name from PRJ_CONST_BIDS t,PRJ_PRJINFO p where t.item_id=p.item_id and 1<>1 ";
			
			System.out.println("sql===>"+sql);
			
			String temp = null;
			temp = getRequest().getParameter("projectid");
			if(temp!=null && !temp.equals("")){
				sql += "and t.PRJ_ID = '"+temp+"' ";
				getRequest().setAttribute("projectid", temp);
			}
			temp = getRequest().getParameter("projectname");
			if(temp!=null && !temp.equals("")){	            
				sql += "and t.PRJ_NAME = '"+temp+"' ";
				getRequest().setAttribute("projectname", temp);
			}
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	 
			if(rs.next()){
				pro.setPrj_id(isNull(rs.getString("PRJ_ID")));
				pro.setPrj_name(isNull(rs.getString("PRJ_NAME")));	
				pro.setItem_name(isNull(rs.getString("item_name")));
				pro.setItem_id(isNull(rs.getString("item_id")));
				pro.setPrj_location(isNull(rs.getString("prj_location")));
				pro.setConst_org(isNull(rs.getString("const_org")));
				pro.setIns_dept(isNull(rs.getString("ins_dept")));
				pro.setPrj_classlev(isNull(rs.getString("PRJ_CLASSLEV")));
				pro.setAuct_resp(isNull(rs.getString("AUCT_RESP")));
				pro.setAuct_handler(isNull(rs.getString("AUCT_HANDLER")));
				pro.setAuct_tel(isNull(rs.getString("AUCT_TEL")));
				pro.setAuct_mobile(isNull(rs.getString("AUCT_MOBILE")));
			/*	pro.setAgent_name(isNull(rs.getString("AGENT_NAME")));
				pro.setAgent_qual(isNull(rs.getString("AGENT_QUAL")));
				pro.setAgent_cert(isNull(rs.getString("agent_cert")));
				pro.setAgent_resp(isNull(rs.getString("agent_resp")));
				pro.setAgent_handler(isNull(rs.getString("agent_handler")));
				pro.setAgent_tel(isNull(rs.getString("agent_tel")));
				pro.setAgent_moblie(isNull(rs.getString("agent_moblie")));
				*/
				if(rs.getDate("exp_start_date")!=null){
					pro.setExp_start_date(isNull(sdf.format(rs.getDate("exp_start_date"))));
				}else{
					pro.setExp_start_date("");
				}
				if(rs.getDate("exp_end_date")!=null){
					pro.setExp_end_date(isNull(sdf.format(rs.getDate("exp_end_date"))));
				}else{
					pro.setExp_end_date("");
				}
				pro.setFb_const_price(isNull(rs.getString("fb_const_price")));
				pro.setDoc_id(isNull(rs.getString("doc_id")));
				pro.setPrj_lic_id(isNull(rs.getString("prj_lic_id")));
				pro.setPart_val(isNull(rs.getString("part_val")));	
				pro.setPrj_class(isNull(rs.getString("prj_class")));
				pro.setSup_id(isNull(rs.getString("sup_id")));
				pro.setSup_name(isNull(rs.getString("sup_name")));
				pro.setDesign_name(isNull(rs.getString("design_name")));
				pro.setProsp_name(isNull(rs.getString("prosp_name")));
				
				pro.setFund_gov(rs.getFloat("fund_gov"));
				pro.setFund_nat(rs.getFloat("fund_nat"));
				pro.setFund_comm(rs.getFloat("fund_comm"));
				pro.setFunn_priv(rs.getFloat("funn_priv"));
				pro.setFund_for(rs.getFloat("fund_for"));
				pro.setFund_oth(rs.getFloat("fund_oth"));
				pro.setBldg_up_type(rs.getString("bldg_up_type"));
				pro.setBldg_base_type(rs.getString("bldg_base_type"));
				//项目经理信息
				pro.setMgr_card(rs.getString("mgr_card"));
				pro.setMgr_mobile(rs.getString("mgr_mobile"));
				pro.setMgr_name(rs.getString("mgr_name"));
				pro.setMgr_reg_cert_no(rs.getString("mgr_reg_cert_no"));
				
				getRequest().setAttribute("YwbwProjectInfo", pro);	
			}					
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			  try{
				  if(rs!=null)
					  rs.close();
				  if(pstmt!=null)
					  pstmt.close();
			  	  if(conn!=null)
				  	  conn.close();
			  }catch(Exception ex){
				  ex.printStackTrace();
			  }
		  }		
		return SUCCESS;
	}
	
	/**
	 * @Titile selectAllProject
	 * @Description 初始化项目查询列表
	 * @return 
	 * @throws Exception
	 */
	@Action(value = "/onlineService/searchAllProject", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/searchAllProject.jsp") })
	public String selectAllProject(){
		try{
			int maxResult = 10;
			String sql = "select * from (select t.PRJ_ID item_id,t.prj_name item_name,T.const_org,'1' item_type " +
						"from EXAM_BIG_PRJ@ywbw t where t.tag_del='0' "+
						"UNION ALL "+ 
						"select item_id,item_name,const_org,'0' item_type " +
						"from prj_prjinfo@ywbw t where t.tag_del='0') tt where 1=1  ";
			
			ArrayList<Object> param = new ArrayList<Object>();
			
			//项目名称
			String temp = getRequest().getParameter("projectName");
			System.out.println("projectName==>"+temp);
			if(StringUtils.isNotBlank(temp)){
				sql += " and tt.ITEM_NAME like '%" + temp.trim() + "%' ";
				getRequest().setAttribute("projectName",temp);
			}
			//项目编号
			temp = getRequest().getParameter("projectId");
			if(StringUtils.isNotBlank(temp)){
				sql += " and tt.ITEM_ID like '%" + temp.trim() + "%' ";
				//param.add("%" + temp.trim() + "%");
				getRequest().setAttribute("projectId",temp);
			}
			//项目类型
			temp = getRequest().getParameter("projectType");
			if(StringUtils.isNotBlank(temp)){
				sql += " and tt.ITEM_TYPE = '"+temp.trim()+"' ";
				//param.add();
				getRequest().setAttribute("projectType",temp);
			}
			
			sql+= " order by ITEM_TYPE ";
			PageView<ProjectInfo> pageView = new PageView<ProjectInfo>(maxResult,getPage(),approveItemService.getDataRows(sql.toString(), GenericsUtils.listToArray(param)));
			
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			
			setProjectList(dynamicStateService.getResultProject(sql,GenericsUtils.listToArray(param)));
			
//			pageView.setRecords(approveItemService.getMoreApproveItem(sql.toString(), GenericsUtils.listToArray(param)));
			getRequest().setAttribute("pageView", pageView);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @desciption 招标文件备案-更新到sb表
	 * @return
	 */
	@Action(value = "/onlineService/saveSpiData", results = { @Result(name = "success", type="json") })
	@LoginRequired(module="approveItem")
	public void saveSpiData(){
		String hall_gcmc = getRequest().getParameter("hall_gcmc");
		String hall_gcbh = getRequest().getParameter("hall_gcbh");
		String hall_xmmc = getRequest().getParameter("hall_xmmc");
		String hall_xmbh = getRequest().getParameter("hall_xmbh");
		String hall_zbr = getRequest().getParameter("hall_zbr");
		hall_gcmc = hall_gcmc==null ? "":hall_gcmc;
		hall_gcbh = hall_gcbh==null? "" :hall_gcbh;
		hall_xmmc = hall_xmmc==null? "" :hall_xmmc;
		hall_xmbh = hall_xmbh==null? "" :hall_xmbh;
		hall_zbr = hall_zbr==null? "" :hall_zbr;
		String instanceguid = StringUtils.isNotBlank(getAppInstanceGuid()) ? getAppInstanceGuid() : new GUID().toString(); 
		
		//判断当前实例是否已经有数据
		try {
			String sql = "select gcmc,xmmc,gcbh from SB_TEMP_INSTANCE WHERE GUID=?";
			ApproveinstanceGcmc gcmcBean = gcmcApproveinstanceService.getBean(sql, new String[]{getAppInstanceGuid(),"-1","-1"}, ApproveinstanceGcmc.class);
			if(null != gcmcBean){
				sql = "UPDATE SB_TEMP_INSTANCE SET GCMC=?,GCBH=?,XMMC=? WHERE GUID=?";
				
				gcmcApproveinstanceService.update(sql, new Object[]{hall_gcmc,hall_gcbh,hall_xmmc,instanceguid});
			}else{
				sql = "INSERT INTO sb_temp_instance(GUID,GCMC,GCBH,XMMC) "
						+ "VALUES ('"+instanceguid+"','"+hall_gcmc+"','"+hall_gcbh+"','"+hall_xmmc+"')";
				
				gcmcApproveinstanceService.executeSql(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		/**
		 * 更新到SB_APPROVEINSTANCE
		 */
		/*
		String sb_sql = "UPDATE SB_APPROVEINSTANCE SET gcmc=?,gcbh=?,xmmc=? WHERE GUID =?";
		
		try{
			onlineService.execute(sb_sql, new String[]{hall_gcmc,hall_gcbh,hall_xmmc,getAppInstanceGuid()});
			
			
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
	
	
	
	@Action(value="/onlineService/getfddbr")
	public void Getfddbr(){
		String cardId=getRequest().getParameter("cardId");
		String qyguid=getRequest().getParameter("qyguid");
		String type=getRequest().getParameter("type");
		StringBuffer sb=new StringBuffer();
		String sql="select distinct t.name as position,t.id_no,i.name from corp_gas_employee t,corp_gas_info i where t.sb_guid=i.guid and t.id_no in ("+cardId+") and t.employee_type=? and t.sb_guid<>?";
		List<CorpGasEmployee> cge=corpGasEmployeeJdbcDao.queryForRow(sql, new String []{type,qyguid,"-1","-1"}, CorpGasEmployee.class);
		int count=cge.size();
		for(int i=0;i<count;i++){
			sb.append("姓名为："+cge.get(i).getPosition()+"的人员已经在"+cge.get(i).getName()+"任职<br>");
		}
		if(count>0){
			outJson("{'flag':'1','message':'"+sb.toString()+"'}",null);
		}else{
			outJson("{'flag':'0'}", null);
		}
	}
	
	//判断申请表填完了没有
	@Action(value="/onlineservice/checkOk")
	public void checkStep(){
		String approveItemGUID=getApproveItemGUID();
		String insGuid=getAppInstanceGuid();
		String sql="select t.tablename,t.guid,t.must,t.columnname from proceedingforms t where t.approveitemguid=? and t.type=0";
		List<ProceedingForm> list=new ArrayList<ProceedingForm>(); 
		try {
			list=proceedingFormService.getFormsList(sql, new String[] {approveItemGUID,"-1","-1"});
			for(int i=0;i<list.size();i++){
				if(list.get(i).getMust()!=null&&list.get(i).getTableName()!=null&&list.get(i).getColumnname()!=null){
					if(list.get(i).getMust()==1){
						sql="select a.guid from "+list.get(i).getTableName()+" a where "+list.get(i).getColumnname()+"=?";
						int count=corpGasEmployeeJdbcDao.countRows(sql, new String[]{insGuid,"-1","-1"});
						if(count<1){
							outJson("{'message':0}", null);
							return;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outJson("{'message':1}", null);
	}
	
	
	/**
	 * @description 执行施工许可表单信息查询
	 * @date 2013-12-04
	 * @author hjl
	 */
	@Action(value="/onlineService/doQueryPermitForm")
	public void queryPermitForm(){
//		String sql = "select s.sqdw,s.fddbr,s.sfzhm,b.zjbh,t.* from t_banjie b,office_spi_declareinfo d,zjsb_za_fwptsqb t,sgxkbgyqhdtfgzjysbzmx s where b.yxtywlsh=d.declaresn "
//					+"and d.workflowinstance_guid=t.guid and s.sb_guid(+) = t.guid";
		String sql = "select b.zjbh,t.* from t_banjie b,office_spi_declareinfo d,zjsb_za_fwptsqb t where b.yxtywlsh=d.declaresn "
					+"and d.workflowinstance_guid=t.guid and b.zjbh is not null";
		
		List<String> params = new ArrayList<String>();
		if(skqz_ysgxkzxh!=null&&!skqz_ysgxkzxh.equals("")){
			sql += " and b.zjbh = ? ";
			params.add(skqz_ysgxkzxh);
		}
		if(skqz_gcbh!=null&&!skqz_gcbh.equals("")){
			sql += " and t.gcbh = ? ";
			params.add(skqz_gcbh);
		}
		params.add("1");
		params.add("1");
		
		PermitBean permit = permitJdbcDao.getBean(sql, params.toArray(), PermitBean.class);
		//PermitBean permit = permitJdbcDao.getOneObject(sql, PermitBean.class, params.toArray());
		if(permit==null){
			permit = new PermitBean();
			StringBuffer msg = new StringBuffer();
			msg.append("根据 ");
			if(skqz_gcbh!=null&&!skqz_gcbh.equals("")){
				msg.append("“工程编号”");
			}
			if(skqz_ysgxkzxh!=null&&!skqz_ysgxkzxh.equals("")){
				msg.append("“原施工许可证序号”");
			}
			if(msg.length()>0){
				msg.append("，查无记录。");
			}else{
				msg.append("请输入“工程编号”或“原施工许可证序号”");
			}
			permit.setError(msg.toString());
		}else{
			sql = "select t.* from zjsb_za_fwptsqb_fb t,zjsb_za_fwptsqb a where t.fbwj = a.guid and a.gcbh = ? ";
			List<String> unitParams = new ArrayList<String>();
			unitParams.add(permit.getGcbh());
			unitParams.add("1");
			unitParams.add("100");
			List<PermitApplyUnit> applyUnit = applyUnitDao.queryForRow(sql, unitParams.toArray(), PermitApplyUnit.class);
			permit.setApplyUnit(applyUnit);
		}
		permit.setQueryId(queryId);
		outJson(permit, null);
	}
	
	@Action(value = "/onlineService/initUpFile", results = { @Result(name = "success", location = "/WEB-INF/page/onlineservice/appApply/initUpFile.jsp") })
	@LoginRequired(module="approveItem")
	public String initUpFile() throws Exception{
		String fileGuid=getFileGuid();
		String insGuid=getAppInstanceGuid();
		List<WebApplyUpFile> list=applyUpFileService.getScrollData(-1, -1, new String[]{"workflowinstance_guid=?","declareannexguid=?"}, new String[]{insGuid,fileGuid}).getResultList();
		setWebUpFileList(list);
		return SUCCESS;
	}
	
	
	/**
	 * null返回""
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		return (str == null) ? "" : str;
	}
	
	/**
	 * 格式化编号
	 * @param curval
	 * @return
	 */
	public static String formatBh(int curval){
		DecimalFormat  df = new DecimalFormat(STR_FORMAT);
		return df.format(curval);
	} 
	
	/**
	 * 获取事项编码
	 * @param approveItemguid
	 * @return
	 * @author Jon
	 */
	public String getApproveItemsn(String approveItemguid){
		String sql = "SELECT approveitemsn,APPROVEITEMGUID,APPROVEITEMNAME FROM SPM_APPROVEITEM WHERE APPROVEITEMGUID=?";
		ApproveItemsn approveItemsn = approveItemsnDao.getBean(sql,new String[]{approveItemguid,"-1","-1"},ApproveItemsn.class);
		
		return (approveItemsn.getApproveitemsn() == null) ? "" : approveItemsn.getApproveitemsn();
	}
	
	/**
	 * 中间表获取当前值
	 * @param approveItemguid
	 * @return
	 * @author Jon
	 */
	public TempSblsh getTempSblsh(String approveItemguid){
		String sql = "SELECT MAX(T.CURVALUE) AS CURVALUE,t.GUID FROM T_TEMP_SBLSH t  "
				+ " WHERE t.APPROVEITEMID=? AND to_char(t.CURDATE,'yyyy-mm-dd')=?"
				+ " GROUP BY GUID";
		
		String strDate = DateFormatUtil.dateConvertStr(new Date(), "yyyy-MM-dd");
		TempSblsh tempSblsh = tempSblshDao.getBean(sql, new Object[]{approveItemguid,strDate,"-1","-1"}, TempSblsh.class);
		return tempSblsh;
	}
	
	public void doSblshUpdate(TempSblsh tempSblsh){
		String sql = "UPDATE T_TEMP_SBLSH SET CURVALUE=? WHERE GUID=?";
		tempSblshDao.update(sql, new Object[]{tempSblsh.getCurvalue(),tempSblsh.getGuid()});
	}
	
	
	public void doSblshInsert(TempSblsh tempSblsh){
		String sql = "INSERT INTO t_temp_sblsh(GUID,APPROVEITEMID,approveItemMsn,CURVALUE) "
						+ "VALUES (func_newguid,'"+tempSblsh.getApproveitemid()+"','"+tempSblsh.getApproveitemmsn()+"',"
								+ "'"+tempSblsh.getCurvalue()+"')";
		tempSblshDao.executeSql(sql);
	}
	
	
	public boolean isZbwjApproveItem(String approveItemguid){
		if(zbwjLists.size() >0){
			for(Iterator<String> it = zbwjLists.iterator();it.hasNext();){
				if(StringUtils.equals(it.next(), approveItemguid)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	public ISimpleJdbcDao<PermitApplyUnit> getApplyUnitDao() {
		return applyUnitDao;
	}

	public void setApplyUnitDao(ISimpleJdbcDao<PermitApplyUnit> applyUnitDao) {
		this.applyUnitDao = applyUnitDao;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getZbwjFlag() {
		return zbwjFlag;
	}

	public void setZbwjFlag(String zbwjFlag) {
		this.zbwjFlag = zbwjFlag;
	}

	public PermitBean getPermit() {
		return permit;
	}

	public void setPermit(PermitBean permit) {
		this.permit = permit;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public List<BusinessInfo> getBusiList() {
		return busiList;
	}
	
	public List<WebApplyFileDoctype> getDocTypeList() {
		return docTypeList;
	}

	public List<WebApplyUpFile> getWebUpFileList() {
		return webUpFileList;
	}

	public void setWebUpFileList(List<WebApplyUpFile> webUpFileList) {
		this.webUpFileList = webUpFileList;
	}

	public void setDocTypeList(List<WebApplyFileDoctype> docTypeList) {
		this.docTypeList = docTypeList;
	}

	public WebApplyFileDoctype getWebApplyFileDoctype() {
		return webApplyFileDoctype;
	}

	public void setWebApplyFileDoctype(WebApplyFileDoctype webApplyFileDoctype) {
		this.webApplyFileDoctype = webApplyFileDoctype;
	}

	@JSON
	public String getMessage() {
		return message;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public void setBusiList(List<BusinessInfo> busiList) {
		this.busiList = busiList;
	}

	public Approveinstance getApproveinstance() {
		return approveinstance;
	}

	public void setApproveinstance(Approveinstance approveinstance) {
		this.approveinstance = approveinstance;
	}

	public String getDepartGUID() {
		return departGUID;
	}

	public String getTypejsonString() {
		return typejsonString;
	}

	public void setTypejsonString(String typejsonString) {
		this.typejsonString = typejsonString;
	}

	public void setDepartGUID(String departGUID) {
		this.departGUID = departGUID;
	}

	public String getCommunityGUID() {
		return communityGUID;
	}

	public void setCommunityGUID(String communityGUID) {
		this.communityGUID = communityGUID;
	}

	public String getFileGuid() {
		return fileGuid;
	}

	public void setFileGuid(String fileGuid) {
		this.fileGuid = fileGuid;
	}

	public String getAppInstanceGuid() {
		if(StringUtils.isNotBlank(appInstanceGuid)){
			return CheckOutGuid(appInstanceGuid);
		}else
			return appInstanceGuid;
	}
	public static String CheckOutGuid(String guid) {
		if (guid == null || guid.trim().length() != 38)
			return "";
		String regex = "[{][A-Z0-9]{8}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{12}[}]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(guid);
		boolean value = m.find();
		if (value)
			return guid;
		else
			return "";
	}
	public void setAppInstanceGuid(String appInstanceGuid) {
		this.appInstanceGuid = appInstanceGuid;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public List<ApproveItemTabTemp> getAppTabTempList() {
		return appTabTempList;
	}

	public void setAppTabTempList(List<ApproveItemTabTemp> appTabTempList) {
		this.appTabTempList = appTabTempList;
	}

	public String getApproveItemGUID() {
		return approveItemGUID;
	}

	public void setApproveItemGUID(String approveItemGUID) {
		this.approveItemGUID = approveItemGUID;
	}

	public String getTwoLevel() {
		return twoLevel;
	}

	public void setTwoLevel(String twoLevel) {
		this.twoLevel = twoLevel;
	}

	public String getOneLevel() {
		return oneLevel;
	}

	public void setOneLevel(String oneLevel) {
		this.oneLevel = oneLevel;
	}

	

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getAppTypeMap() {
		return appTypeMap;
	}

	public void setAppTypeMap(Map<String, String> appTypeMap) {
		this.appTypeMap = appTypeMap;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<ApproveItemClassify> getClassifyList() {
		return classifyList;
	}

	public void setClassifyList(List<ApproveItemClassify> classifyList) {
		this.classifyList = classifyList;
	}


	public List<ProceedingForm> getFormList() {
		return formList;
	}
	public void setFormList(List<ProceedingForm> formList) {
		this.formList = formList;
	}
	public List<GuideFileType> getGuideFilTypeeList() {
		return guideFilTypeeList;
	}

	public void setGuideFilTypeeList(List<GuideFileType> guideFilTypeeList) {
		this.guideFilTypeeList = guideFilTypeeList;
	}

	public List<GuideFile> getGuideFileList() {
		return guideFileList;
	}

	public void setGuideFileList(List<GuideFile> guideFileList) {
		this.guideFileList = guideFileList;
	}

	public List<CodeMap> getBureauList() {
		return bureauList;
	}

	public void setBureauList(List<CodeMap> bureauList) {
		this.bureauList = bureauList;
	}
	
	public ApproveItem getApproveItem() {
		return approveItem;
	}

	public void setApproveItem(ApproveItem approveItem) {
		this.approveItem = approveItem;
	}

	public String getGuids() {
		return guids;
	}

	public void setGuids(String guids) {
		this.guids = guids;
	}

	public List<GuideFile> getGuidenoFileList() {
		return guidenoFileList;
	}

	public void setGuidenoFileList(List<GuideFile> guidenoFileList) {
		this.guidenoFileList = guidenoFileList;
	}

	public String getYxtywlsh() {
		return yxtywlsh;
	}

	public void setYxtywlsh(String yxtywlsh) {
		this.yxtywlsh = yxtywlsh;
	}
	
	public List<ProjectInfo> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ProjectInfo> projectList) {
		this.projectList = projectList;
	}

	public String getSkqz_ysgxkzxh() {
		return skqz_ysgxkzxh;
	}

	public void setSkqz_ysgxkzxh(String skqz_ysgxkzxh) {
		this.skqz_ysgxkzxh = skqz_ysgxkzxh;
	}

	public String getSkqz_gcbh() {
		return skqz_gcbh;
	}

	public void setSkqz_gcbh(String skqz_gcbh) {
		this.skqz_gcbh = skqz_gcbh;
	}
	public String getGONGCHENGMINGCHENG() {
		return GONGCHENGMINGCHENG;
	}
	public void setGONGCHENGMINGCHENG(String gONGCHENGMINGCHENG) {
		GONGCHENGMINGCHENG = gONGCHENGMINGCHENG;
	}
	public String getGONGCHENGBIANHAO() {
		return GONGCHENGBIANHAO;
	}
	public void setGONGCHENGBIANHAO(String gONGCHENGBIANHAO) {
		GONGCHENGBIANHAO = gONGCHENGBIANHAO;
	}
	public String getXIANGMUMINGCHENG() {
		return XIANGMUMINGCHENG;
	}
	public void setXIANGMUMINGCHENG(String xIANGMUMINGCHENG) {
		XIANGMUMINGCHENG = xIANGMUMINGCHENG;
	}
	public String getStepFlag() {
		return stepFlag;
	}
	public void setStepFlag(String stepFlag) {
		this.stepFlag = stepFlag;
	}
	public String getSGGK_xmmc() {
		return SGGK_xmmc;
	}
	public void setSGGK_xmmc(String sGGKXmmc) {
		SGGK_xmmc = sGGKXmmc;
	}
	public String getSGGK_xmbh() {
		return SGGK_xmbh;
	}
	public void setSGGK_xmbh(String sGGKXmbh) {
		SGGK_xmbh = sGGKXmbh;
	}
	public String getSGGK_zbr() {
		return SGGK_zbr;
	}
	public void setSGGK_zbr(String sGGKZbr) {
		SGGK_zbr = sGGKZbr;
	}
	public String getHasEMS() {
		return hasEMS;
	}
	public void setHasEMS(String hasEMS) {
		this.hasEMS = hasEMS;
	}
}
