package net.risesoft.actions.userhome;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.onlineservice.Approveinstance;
import net.risesoft.beans.onlineservice.ApproveinstanceFeedBack;
import net.risesoft.beans.onlineservice.ApproveinstanceGcmc;
import net.risesoft.beans.onlineservice.GuideFile;
import net.risesoft.beans.onlineservice.ListtypeOfApproveinstance;
import net.risesoft.beans.onlineservice.WebApplyFileDoctype;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.onlineservice.IApproveinstanceService;
import net.risesoft.services.onlineservice.IListTypeOfApproveinstanceService;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;
/**
 * 
  * @ClassName: BusinessfollowAction
  * @Description: 办事跟踪
  * @author Comsys-zhangkun
  * @date Jun 8, 2013 11:38:12 AM
  *
 */
@Controller
@ParentPackage("default")  
@InterceptorRefs({@InterceptorRef("isLoginStack")})
public class BusinessfollowAction extends BaseActionSupport{

	
	private static final long serialVersionUID = 4246222609838326033L;
	@Resource
	private ISimpleJdbcDao<Approveinstance> instanceSimpleJdbcDao;
	@Resource
	private IApproveinstanceService inApproveinstanceService;
	@Resource
	private ISimpleJdbcDao<GuideFile> guidFileSimpleJdbcDao;
	@Resource
	private ISimpleJdbcDao<ApproveinstanceGcmc> gmcmSimpleJdbcDao;
	@Resource
	private ISimpleJdbcDao<WebApplyFileDoctype> webFileDocTypeSimpleJdbcDao;
	@Resource
	private IListTypeOfApproveinstanceService listTypeService;//申报业务与材料类别关联
	@Resource
	private ISimpleJdbcDao<ApproveinstanceFeedBack> feedBackSimpleJdbcDao;        //回复列表
	
	
	private Approveinstance approveinstance;
	private String query;
	private String guid;
	private Integer yscclcount;//已上传材料数量;
	private Integer wscclcount;//未上传材料数量
	private List<ApproveinstanceFeedBack> feedBackLists;
	
	/**
	 * 
	  * @Title: initBusinessfollow
	  * @Description: 查询个人业务办理情况
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/businessfollow/initBusinessfollow", 
			results = {@Result(name = "input" ,location = "/WEB-INF/page/userhome/index.jsp"),
			@Result(name = "success" ,location = "/WEB-INF/page/userhome/busiResult.jsp")})
	@LoginRequired(module="approveItem")
	public String initBusinessfollow(){
		int maxResult = 10;
		ArrayList<Object> param = new ArrayList<Object>();
		String sql = "select t.guid,t.declaresn, t.approveguid, s.approveitemname formname,t.status,t.submittime,t.issubmit,"+
				"t.hfstate,t.INITSTR,t.GCMC"+
				" from sb_approveinstance t, spm_approveitem s "+
				" where t.approveguid = s.approveitemguid "+
				" and t.dataform=0 "+
				" and t.userid=? order by t.submittime desc";
		try {
			param.add(getUserGUID());
			PageView<Approveinstance> pageView = new PageView<Approveinstance>(maxResult,
					getPage(),instanceSimpleJdbcDao.countRows(sql.toString(), GenericsUtils.listToArray(param)));
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			List<Approveinstance> approveInstanceLists = instanceSimpleJdbcDao.queryForRow(sql.toString(), GenericsUtils.listToArray(param),Approveinstance.class);
			
			/**
			 * 获取工程名称
			 * update by Jon
			 */
			/*if(null != approveInstanceLists && approveInstanceLists.size()>0){
				for(Iterator<Approveinstance> it = approveInstanceLists.iterator();it.hasNext();){
					Approveinstance as = it.next();
					if(StringUtils.isNotBlank(as.getInitstr())){
						as.setGongchengmingcheng(getApproveInstanceGcmc(as.getInitstr(),as.getGuid()));
					}
				}
			}*/
			pageView.setRecords(approveInstanceLists);
			getRequest().setAttribute("pageView", pageView);
			if(StringUtils.isBlank(getQuery())){
				return INPUT;
			}else{
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}
	//
	@Action(value = "/businessfollow/findBusinessResponse", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/userhome/reponse.jsp")})
	@LoginRequired(module="approveItem")
	public String userBusinessInfo(){
		try {
			Approveinstance ins = inApproveinstanceService.find(getGuid());
			ins.setFeedback(WebUtil.filter(ins.getFeedback()));
			String sql = "SELECT GUID,FEEDUSER,CONTENT,to_char(FEEDBACKTIME,'yyyy-dd-mm hh:mm:ss') AS feedbacktime FROM SB_APPROVEINSTANCE_FEEDBACK WHERE APPROVEINSTANCEGUID=? order by feedbacktime desc";
			feedBackLists = feedBackSimpleJdbcDao.queryForRow(sql, new String[]{ins.getGuid(),"-1","-1"}, ApproveinstanceFeedBack.class);
			sql = "select t.materialname,t.describe,'1' type,null bzyj from xzql_xzsp_lists t " +
					" where t.id in("+WebUtil.strToParam(ins.getXbqids(),",")+") union all "+
					" select t.materialname,t.describe,'0' type, l.bzyj from xzql_xzsp_lists t " +
					" ,(select * from spm_bzyj_log l1 where not exists (select 1 from spm_bzyj_log l2 where l1.declareannexguid = l2.declareannexguid "+
					" and l1.workflowinstance_guid=l2.workflowinstance_guid and l2.state <> '0' and l2.version > l1.version) and "+
					" l1.workflowinstance_guid = '"+ins.getGuid()+
					"') l where t.id = l.declareannexGuid(+) and t.id in("+WebUtil.strToParam(ins.getXbzids(),",")+")";
			List<GuideFile> fileList = guidFileSimpleJdbcDao.queryForRow(sql, new String[]{"-1","-1"},GuideFile.class);
			ins.setFileList(fileList);
			setApproveinstance(ins);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 办事跟踪查看
	 * @return
	 */
	@Action(value = "/businessfollow/findBusinessCLResponse", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/userhome/clreponse.jsp")})
	@LoginRequired(module="approveItem")
	public String userCLInfo(){
		try {
			Approveinstance ins = inApproveinstanceService.find(getGuid());
			String sql = "select t.id,t.materialname,t.describe from xzql_xzsp_lists t " +
					" where t.id in("+WebUtil.strToParam(ins.getYtjids(),",")+")";
			List<GuideFile> fileList = guidFileSimpleJdbcDao.queryForRow(sql, new String[]{"-1","-1"},GuideFile.class);
			sql="select t.clguid from spm_wssbcl_doctype t where t.xmgguid=? and t.cardid is not null";
			List<WebApplyFileDoctype> wftList=webFileDocTypeSimpleJdbcDao.queryForRow(sql, new String[]{ins.getGuid(),"-1","-1"}, WebApplyFileDoctype.class);
			for(int i=0;i<fileList.size();i++){
				for(int a=0;a<wftList.size();a++){
					if(fileList.get(i).getId().equals(wftList.get(a).getClguid())){
						fileList.remove(i);
						break;
					}
				}
			}
			ins.setFileList(fileList);
			setApproveinstance(ins);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 打印预受理回执
	 * @return
	 */
	
	@Action(value = "/businessfollow/pringtHz", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/userhome/printHz.jsp")})
	@LoginRequired(module="approveItem")
	public String printHz(){
		try {
			Approveinstance ins = inApproveinstanceService.find(getGuid());
			//查询材料上传情况
			String sql ="";
			String guids="";
			ListtypeOfApproveinstance loa=listTypeService.find(ins.getGuid());
			if(loa!=null&&loa.getListtypeids()!=null){
				String[] list= loa.getListtypeids().split(",");
				for(int i=0;i<list.length;i++){
					guids=guids+"'"+list[i]+"'";
					if(i!=list.length-1){
						guids=guids+",";
					}
				}
				sql=" select l.materialname,decode(w.declareannexguid,null,'0','1') type from xzql_xzsp_lists l,SPM_WSSBCL w"
				 +" where l.id in (select t.listguid from xzql_xzsp_listoftype t where t.typeguid in ("+guids+")) and w.declareannexguid(+) = l.id"
				 +" and w.workflowinstance_guid(+)=? order by l.orderno";
				ins.setFileList(guidFileSimpleJdbcDao.queryForRow(sql, new String[]{ins.getGuid(),"-1","-1"}, GuideFile.class));
			}else{
				sql = "select l.materialname, decode(w.declareannexguid,null,'0','1') type" +
						" from xzql_xzsp_lists l, SPM_WSSBCL w where w.declareannexguid(+) = l.id  " +
						"and l.itemid = ? and w.workflowinstance_guid(+) = ?";
				ins.setFileList(guidFileSimpleJdbcDao.queryForRow(sql, new String[]{ins.getApproveguid(),ins.getGuid(),"-1","-1"}, GuideFile.class));
			}
			int yscclcount=0;//已上传材料数量;
			int wscclcount=0;
			for(int i=0;i<ins.getFileList().size();i++){
				if(ins.getFileList().get(i).getType().equals("1")){
					yscclcount++;
				}else{
					wscclcount++;
				}
			}
			setYscclcount(yscclcount);
			setWscclcount(wscclcount);
			setApproveinstance(ins);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 获取工程名称
	 * @return
	 */
	/*public String getApproveInstanceGcmc(String instr,String guid){
		String[] params = instr.split(":");
		if(null!=params && params.length>0){
			String sql = "SELECT " +params[1] + " FROM "+ params[0] +" WHERE GUID=?";
			ApproveinstanceGcmc gcmcInstance = gmcmSimpleJdbcDao.getBean(sql, new String[]{guid,"-1","-1"}, ApproveinstanceGcmc.class);
			if(null != gcmcInstance){
				return gcmcInstance.getGongchengmingcheng() == null ? "" : gcmcInstance.getGongchengmingcheng();
			}
		}
		return " ";
	}*/
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public Integer getYscclcount() {
		return yscclcount;
	}
	public void setYscclcount(Integer yscclcount) {
		this.yscclcount = yscclcount;
	}
	public Integer getWscclcount() {
		return wscclcount;
	}
	public void setWscclcount(Integer wscclcount) {
		this.wscclcount = wscclcount;
	}
	public Approveinstance getApproveinstance() {
		return approveinstance;
	}
	public void setApproveinstance(Approveinstance approveinstance) {
		this.approveinstance = approveinstance;
	}
	public List<ApproveinstanceFeedBack> getFeedBackLists() {
		return feedBackLists;
	}
	public void setFeedBackLists(List<ApproveinstanceFeedBack> feedBackLists) {
		this.feedBackLists = feedBackLists;
	}
}
