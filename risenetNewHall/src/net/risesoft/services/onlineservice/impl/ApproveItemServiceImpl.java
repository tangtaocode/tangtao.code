package net.risesoft.services.onlineservice.impl;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.beans.govpublic.WindowsInfo;
import net.risesoft.beans.onlineservice.ApproveItem;
import net.risesoft.beans.onlineservice.ApproveItemLog;
import net.risesoft.beans.onlineservice.ApproveItemLogPK;
import net.risesoft.beans.onlineservice.ApproveItemProvince;
import net.risesoft.beans.onlineservice.ApproveItemProvinceLog;
import net.risesoft.beans.onlineservice.ApproveItemProvinceLogPK;
import net.risesoft.beans.onlineservice.ApproveItemTabTemp;
import net.risesoft.beans.onlineservice.GuideFile;
import net.risesoft.beans.onlineservice.GuideFileType;
import net.risesoft.beans.onlineservice.LawsBean;
import net.risesoft.beans.risefile.UpFileBean;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.onlineservice.IApproveItemExtendService;
import net.risesoft.services.onlineservice.IApproveItemLogService;
import net.risesoft.services.onlineservice.IApproveItemProvinceLogService;
import net.risesoft.services.onlineservice.IApproveItemProvinceService;
import net.risesoft.services.onlineservice.IApproveItemService;
import net.risesoft.services.onlineservice.IApproveItemTabTempService;
import net.risesoft.services.onlineservice.IGuideFileTypeService;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
/**
 * 
  * @ClassName: IApproveItemService
  * @Description: 事项信息service
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:13:49 PM
  *
 */
@Service
public class ApproveItemServiceImpl extends BaseDaoImpl<ApproveItem> implements IApproveItemService{
	@Resource
	private ISimpleJdbcDao<ApproveItem> itemSimpleJdbcDao;//查询事项服务类
	@Resource
	private ISimpleJdbcDao<LawsBean> lawsBeanSimpleJdbcDao;//法律法规服务类
	@Resource 
	private IGuideFileTypeService guideFileTypeService;//材料类型服务类
	@Resource
	private IApproveItemTabTempService approveItemTabTempService;//表格下载服务类
	@Resource
	private ISimpleJdbcDao<GuideFile> guidFileSimpleJdbcDao;//材料服务类
	@Resource
	private ISimpleJdbcDao<GuideFileType> guidFileTypeJdbcDao;  //材料类型服务类
	@Resource
	private IApproveItemProvinceService itemProvinceService;//事项省部分扩展信息服务类
	@Resource
	private ISimpleJdbcDao<UpFileBean> upFileSimpleJdbcDao;//文件服务类
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource
	private IApproveItemExtendService extendService;//事项区部分扩展信息
	@Resource
	ISimpleJdbcDao<WindowsInfo> windowsInfoService;
	@Resource
	private IApproveItemLogService approveItemLogService;
	@Resource 
	private IApproveItemProvinceLogService approveItemProvinceLogService;
	@Resource 
	private ISimpleJdbcDao<GuideFileType> guideFileTypeSimpleJdbcDao;
	@Resource
	private ISimpleJdbcDao<ApproveItemTabTemp> itemTabTempSimpleJdbcDao;
	private static Map<Integer,String> levelMap = new HashMap<Integer,String>();
	
	static{
		levelMap.put(1, "一,1,a,(1),(a),(1),(a),(1)");
		levelMap.put(2, "二,2,b,(2),(b),(2),(b),(2)");
		levelMap.put(3, "三,3,c,(3),(c),(3),(c),(3)");
		levelMap.put(4, "四,4,d,(4),(d),(4),(d),(4)");
		levelMap.put(5, "五,5,e,(5),(e),(5),(e),(5)");
		levelMap.put(6, "六,6,f,(6),(f),(6),(f),(6)");
		levelMap.put(7, "七,7,g,(7),(g),(7),(g),(7)");
		levelMap.put(8, "八,8,h,(8),(h),(8),(h),(8)");
		levelMap.put(9, "九,9,y,(9),(y),(9),(y),(9)");
		levelMap.put(10, "十,10,j,(10),(j),(10),(j),(10)");
		levelMap.put(11, "十一,11,k,(11),(k),(11),(k),(11)");
		levelMap.put(12, "十二,12,l,(12),(l),(12),(l),(12)");
		levelMap.put(13, "十三,13,m,(13),(m),(13),(m),(13)");
		levelMap.put(14, "十四,14,n,(14),(n),(14),(n),(14)");
		levelMap.put(15, "十五,15,o,(15),(o),(15),(o),(15)");
		levelMap.put(16, "十六,16,p,(16),(p),(16),(p),(16)");
		levelMap.put(17, "十七,17,q,(17),(q),(17),(q),(17)");
		levelMap.put(18, "十八,18,r,(18),(r),(18),(r),(18)");
		levelMap.put(19, "十九,19,s,(19),(s),(19),(s),(19)");
		levelMap.put(20, "二十,20,t,(20),(t),(20),(t),(20)");
	}
	@SuppressWarnings("unchecked")
	public List<ApproveItem> getMoreApproveItem(String sql,Object[] params) {
		List<ApproveItem> appList = itemSimpleJdbcDao.queryForRow(sql, params,ApproveItem.class);
		return appList;
	}
	

	public int getDataRows(String sql, Object[] params) {
		return itemSimpleJdbcDao.countRows(sql,params);
	}
	private String typeStr(String str){
		String type = "";
		if (str.charAt(0) == '1') {
			type += "个人  ";
		}
		if (str.charAt(1) == '1') {
			type += "企业  ";
		}
		if (str.charAt(2) == '1') {
			type += "事业  ";
		}
		if (str.charAt(3) == '1') {
			type += "机关  ";
		}
		if (str.charAt(4) == '1') {
			type += "社团  ";
		}
		if (str.charAt(5) == '1') {
			type += "境外机关、组织、个人";
		}
		return type;
	}
	public ApproveItem getGuideInfo(String itemGUID)throws Exception {
		//查询事项
		ApproveItem app = find(itemGUID);
		
		app.setMaterialStr(app.getMaterialStr()==null ? "" : app.getMaterialStr().replace("\r\n", "<br>"));
		//通过主管部门ID查询名称
		app.setAdminorgid(codeMapUtil.getMapByType("罗湖区审批职能局和街道").get(app.getAdminorgid()));
		//开始过滤部分文字换行标示
		app.setDeclarecondition(WebUtil.filter(app.getDeclarecondition()));
		app.setChargeinfo(WebUtil.filter(app.getChargeinfo()));
		app.setDeclaredesc(WebUtil.filter(app.getDeclaredesc()));
		app.setApproveitembasis(WebUtil.filter(app.getApproveitembasis()));

		
		//查询窗口办理流程  lusihui
		app.setWindowFlow(upFileSimpleJdbcDao.countRows("select f.attachmentguid from xzql_xzsp_file f where f.itemid=? and f.filetype='11'", new String[]{itemGUID}));
		app.setPowerFlow(upFileSimpleJdbcDao.countRows("select f.attachmentguid from xzql_xzsp_file f where f.itemid=? and f.filetype='13'", new String[]{itemGUID}));
	
		
		//区部分扩展信息
		app.setExtend(extendService.find(itemGUID));
		
		//查询省部分扩展信息
		ApproveItemProvince itemProvince = itemProvinceService.find(itemGUID);
		if(itemProvince!=null){
			itemProvince.setWindowworkflow(WebUtil.filter(itemProvince.getWindowworkflow()));
			itemProvince.setWindowaddress(WebUtil.filter(itemProvince.getWindowaddress()));
			itemProvince.setServiceobject(typeStr(app.getApproveobject()));
			app.setItemProvince(itemProvince);
		}
		
		//查询不存在分类的材料
		String sql = "select l.id,l.materialname,l.describe from xzql_xzsp_lists l where not exists(select 1 from xzql_xzsp_listoftype o where l.id=o.listguid) and l.itemid=? order by l.orderno";
		List<GuideFile> guideFileList = guidFileSimpleJdbcDao.queryForRow(sql, new String[]{itemGUID,"-1","-1"}, GuideFile.class);
		for(GuideFile f:guideFileList){
			f.setMaterialname(WebUtil.filter(f.getMaterialname()));
			if(StringUtils.isNotBlank(f.getDescribe())){
				f.setDescribe(WebUtil.filter(f.getDescribe()));
			}else{
				f.setDescribe(null);
			}
		}
		app.setGuideFileList(guideFileList);
		
		
		
		//新增树形排序--Jon
		//查询材料类型
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (")
			.append(" SELECT level,typeguid,itemid,typename,supertypeguid ")
			.append(" FROM XZQL_XZSP_LISTTYPE t  CONNECT BY PRIOR t.typeguid = t.supertypeguid")
			.append(" START WITH typeguid in (SELECT typeguid FROM XZQL_XZSP_LISTTYPE WHERE itemid =? AND supertypeguid IS NULL)")
			.append(" ORDER BY ORDERNO ASC )")
			.append(" WHERE itemid =?");
		
		List<GuideFileType> guideFileTypeList = guidFileTypeJdbcDao.queryForRow(sb.toString(), new String[]{itemGUID,itemGUID,"-1","-1"}, GuideFileType.class);
		
		if(null != guideFileTypeList && guideFileTypeList.size()>0){
			//String rootGUID = "";
			List<String> tempList = new ArrayList<String>();
			for(GuideFileType gf: guideFileTypeList){
				//获取root对象
				if(gf.getLevel() == 1){
					//rootGUID = gf.getTypeguid();
					tempList.add(gf.getTypeguid());
				}
			}
			int count = 0;
			for (GuideFileType gf : guideFileTypeList) {
				if (gf.getLevel() == 1) {
					count++;
					String levelStr = getLevelFlag(count,gf.getLevel() -1);
					gf.setLevelStr(levelStr);
					continue;
				}
			}
			
			for(String parentId: tempList){
					setLevelFlag(guideFileTypeList,parentId);
			}
		}
		
		//根据材料类型查找材料list
		sql = "select l.id,l.materialname,l.describe from xzql_xzsp_lists l,xzql_xzsp_listoftype t where l.id=t.listguid and t.typeguid=? and t.itemid=? order by l.orderno";
		for(GuideFileType gt:guideFileTypeList){
			List<GuideFile> fileList = guidFileSimpleJdbcDao.queryForRow(sql, new String[]{gt.getTypeguid(),itemGUID,"-1","-1"},GuideFile.class);
			for(GuideFile f:fileList){
				f.setMaterialname(WebUtil.filter(f.getMaterialname()));
				if(StringUtils.isNotBlank(f.getDescribe())){
					f.setDescribe(WebUtil.filter(f.getDescribe()));
				}else{
					f.setDescribe(null);
				}
				
			}
			gt.setGuideFileList(fileList);
		}
		app.setGuideFilTypeeList(guideFileTypeList);
		
		//查找办事依据法律法规
		sql = "select distinct z.id,z.title,z.publishtime,t.no,case when dbms_lob.getlength(z.content)>0 then '1' else '0' end status from XZQL_LAW_ITEM t,XZQL_LAW z where z.id=t.lawid and t.itemid=?  order by t.no";
		List<LawsBean> lawAppNexusList = lawsBeanSimpleJdbcDao.queryForRow(sql, new String[]{itemGUID,"-1","-1"}, LawsBean.class);
		app.setLawAppList(lawAppNexusList);
		
		//查询表格下载
		sql = "select t.attachmentguid,t.file_name from Xzql_Xzsp_Attachment t where t.itemid=?  order by t.sort";
		List<ApproveItemTabTemp> appTabTempList = itemTabTempSimpleJdbcDao.queryForRow(sql, new String[]{itemGUID,"-1","-1"}, ApproveItemTabTemp.class);
		app.setAppTabTempList(appTabTempList);
		
//		List<ApproveItemTabTemp> appTabTempList = approveItemTabTempService.getScrollData(-1, -1, new String[]{"o.itemid=?"}, new String[]{itemGUID}).getResultList();
//		app.setAppTabTempList(appTabTempList);
		
		//查询窗口信息
		sql = "select w.guid,w.name,w.address,w.phone,w.officehours,w.trafficguide,w.maptype,w.maplinks from xzql_xzsp_windowofitem t,xzql_xzsp_window w where w.guid=t.windowguid and t.itemid=? and w.guid not in('{AC1E060B-FFFF-FFFF-F186-4D5700000828}','{AC1E060B-FFFF-FFFF-F17F-A59300000826}','{AC100136-0000-0000-3ED1-8FA100000029}','{AC1E060B-FFFF-FFFF-F27D-A27600000825}')";
		app.setWindList(windowsInfoService.queryForRow(sql, new String[]{itemGUID,"-1","-1"}, WindowsInfo.class));
		return app;
	}
	
	
	/**
	 * 设置层级标识符
	 */
	public void setLevelFlag(List<GuideFileType> fileTypeLists,String parentId){
			int count = 1;
			for(int i=0;i<fileTypeLists.size();i++){
				GuideFileType temp = fileTypeLists.get(i);
				if(null != temp.getSupertypeguid() && temp.getSupertypeguid().equals(parentId)){
					String levelFlag = getLevelFlag(count,temp.getLevel() -1);
					temp.setLevelStr(levelFlag);
					count++;
					setLevelFlag(fileTypeLists,temp.getTypeguid());
				}
			}
	}
	
	/**
	 * 获取层级标识符
	 */
	public String getLevelFlag(Integer no,Integer deep){	
		String tempStr = this.levelMap.get(no);
		String[] args = tempStr.split(",");
		if(deep<= args.length){
			return args[deep];
		}
		return "";
	}
	
	public ApproveItemLog getGuideInfo(String itemGUID,String sjbbh)throws Exception {
		//查询事项
		ApproveItemLog app = approveItemLogService.find(new ApproveItemLogPK(itemGUID,Integer.parseInt(sjbbh)));
		//通过主管部门ID查询名称
		app.setAdminorgid(codeMapUtil.getMapByType("罗湖区审批职能局和街道").get(app.getAdminorgid()));
		//开始过滤部分文字换行标示
		app.setDeclarecondition(WebUtil.filter(app.getDeclarecondition()));
		app.setChargeinfo(WebUtil.filter(app.getChargeinfo()));
		app.setDeclaredesc(WebUtil.filter(app.getDeclaredesc()));
		app.setApproveitembasis(WebUtil.filter(app.getApproveitembasis()));
		//查询窗口办理流程
		app.setWindowFlow(upFileSimpleJdbcDao.countRows("select f.attachmentguid from XZQL_XZSP_FILELOG f where f.itemid=? and f.version=?", new String[]{itemGUID,sjbbh}));
		
		//区部分扩展信息
		app.setExtend(extendService.find(itemGUID));
		
		//查询省部分扩展信息
		ApproveItemProvinceLog itemProvince = approveItemProvinceLogService.find(new ApproveItemProvinceLogPK(itemGUID,Integer.parseInt(sjbbh)));
		if(itemProvince!=null){
			itemProvince.setWindowworkflow(WebUtil.filter(itemProvince.getWindowworkflow()));
			itemProvince.setWindowaddress(WebUtil.filter(itemProvince.getWindowaddress()));
			itemProvince.setServiceobject(typeStr(app.getApproveobject()));
			app.setItemProvince(itemProvince);
		}
		
		//查询不存在分类的材料
		String sql = "select l.id,l.materialname,l.describe from xzql_xzsp_listslog l where not exists(select 1 from xzql_xzsp_listoftype o where l.id=o.listguid) and l.itemid=? and l.version=? order by l.orderno";
		List<GuideFile> guideFileList = guidFileSimpleJdbcDao.queryForRow(sql, new String[]{itemGUID,sjbbh,"-1","-1"}, GuideFile.class);
		for(GuideFile f:guideFileList){
			f.setMaterialname(WebUtil.filter(f.getMaterialname()));
			if(StringUtils.isNotBlank(f.getDescribe())){
				f.setDescribe(WebUtil.filter(f.getDescribe()));
			}else{
				f.setDescribe(null);
			}
		}
		app.setGuideFileList(guideFileList);
		
		//查询材料类型
		sql = "select t.typeguid,t.typename from Xzql_Xzsp_Listtypelog t where t.itemid=? and t.version=? order by t.orderno";
		List<GuideFileType> guideFileTypeList = guideFileTypeSimpleJdbcDao.queryForRow(sql, new String[]{itemGUID,sjbbh,"-1","-1"}, GuideFileType.class);
		
		//根据材料类型查找材料list
		sql = "select l.id,l.materialname,l.describe from xzql_xzsp_listslog l,xzql_xzsp_listoftypelog t where l.id=t.listguid and t.version=l.version and t.typeguid=? and t.itemid=? and l.version=? order by l.orderno";
		for(GuideFileType gt:guideFileTypeList){
			List<GuideFile> fileList = guidFileSimpleJdbcDao.queryForRow(sql, new String[]{gt.getTypeguid(),itemGUID,sjbbh,"-1","-1"},GuideFile.class);
			for(GuideFile f:fileList){
				f.setMaterialname(WebUtil.filter(f.getMaterialname()));
				if(StringUtils.isNotBlank(f.getDescribe())){
					f.setDescribe(WebUtil.filter(f.getDescribe()));
				}else{
					f.setDescribe(null);
				}
			}
			gt.setGuideFileList(fileList);
		}
		app.setGuideFilTypeeList(guideFileTypeList);
		
		//查找办事依据法律法规
		sql = "select distinct z.id,z.title,z.publishtime,t.no,case when dbms_lob.getlength(z.content)>0 then '1' else '0' end status from Xzql_Law_Item_Log t,XZQL_LAW z where z.id=t.lawid and t.itemid=? and t.version=?  order by t.no";
		List<LawsBean> lawAppNexusList = lawsBeanSimpleJdbcDao.queryForRow(sql, new String[]{itemGUID,sjbbh,"-1","-1"}, LawsBean.class);
		app.setLawAppList(lawAppNexusList);
		
		//查询表格下载
		sql = "select t.attachmentguid,t.file_name from Xzql_Xzsp_Attachmentlog t where t.itemid=? and t.version=? order by t.file_name";
		List<ApproveItemTabTemp> appTabTempList = itemTabTempSimpleJdbcDao.queryForRow(sql, new String[]{itemGUID,sjbbh,"-1","-1"}, ApproveItemTabTemp.class);
		app.setAppTabTempList(appTabTempList);
		
		//查询窗口信息
		sql = "select w.guid,w.name,w.address,w.phone,w.officehours,w.trafficguide,w.maptype,w.maplinks from xzql_xzsp_windowofitem t,xzql_xzsp_window w where w.guid=t.windowguid and t.itemid=?";
		app.setWindList(windowsInfoService.queryForRow(sql, new String[]{itemGUID,"-1","-1"}, WindowsInfo.class));
		return app;
	}
}
