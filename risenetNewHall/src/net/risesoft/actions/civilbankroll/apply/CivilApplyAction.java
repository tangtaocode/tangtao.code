package net.risesoft.actions.civilbankroll.apply;


import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;
import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.TreeNode;
import net.risesoft.beans.civilbankroll.CivilApplicationBean;
import net.risesoft.beans.civilbankroll.CivilProjectType;
import net.risesoft.beans.risefile.UpFileBean;
import net.risesoft.services.civilbankroll.apply.ICivilProjectTypeService;
import net.risesoft.services.civilbankroll.dynamic.ICivilDynamicService;
import net.risesoft.services.fileUtil.IFileUtileService;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.WebUtil;
@Controller
@ParentPackage("default")  //继承json包，用于返回json
@InterceptorRefs({@InterceptorRef("isLoginStack")})
public class CivilApplyAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -8642138241506005010L;
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private CivilApplicationBean applicationBean;
	private String guid;//项目GUID
	private String xztkcontent; //细则条款信息
	private String typeGUID;// 资助项目类型GUID
	private String modeType;//文件上传模块
	private String message;
	private String correctionGUID; //上传材料GUID
	private String projectFK;//上传材料的项目关联主键
	private String slbh;//受理编号
	private String url;
	private List<UpFileBean> upFileList; //上传文件列表
	
	@Resource
	ICivilDynamicService civilDynamicService;
	@Resource
	ICivilProjectTypeService projectTypeService;
	@Resource
	private IFileUtileService fileUtileService;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@JSON
	public String getSlbh() {
		return slbh;
	}
	public void setSlbh(String slbh) {
		this.slbh = slbh;
	}
	

	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getCorrectionGUID() {
		return correctionGUID;
	}
	public void setCorrectionGUID(String correctionGUID) {
		this.correctionGUID = correctionGUID;
	}
	public String getProjectFK() {
		return projectFK;
	}
	public void setProjectFK(String projectFK) {
		this.projectFK = projectFK;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getTypeGUID() {
		return typeGUID;
	}
	public void setTypeGUID(String typeGUID) {
		this.typeGUID = typeGUID;
	}
	
	public String getModeType() {
		return modeType;
	}
	public void setModeType(String modeType) {
		this.modeType = modeType;
	}
	public List<UpFileBean> getUpFileList() {
		return upFileList;
	}
	public void setUpFileList(List<UpFileBean> upFileList) {
		this.upFileList = upFileList;
	}
	@JSON
	public String getXztkcontent() {
		return xztkcontent;
	}
	public void setXztkcontent(String xztkcontent) {
		this.xztkcontent = xztkcontent;
	}
	
	public CivilApplicationBean getApplicationBean() {
		return applicationBean;
	}
	public void setApplicationBean(CivilApplicationBean applicationBean) {
		this.applicationBean = applicationBean;
	}
/**
 * 
  *
  * @Title: viewApply
  * @Description: 查看申请信息
  * @param @return    设定文件
  * @return String    返回类型
  * @throws
 */
	@Action(value = "/civilApply/viewApply", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/civilbankroll/apply/viewApply.jsp")})
	@LoginRequired(module="civilApply")
	public String viewApply(){
		try {
			CivilApplicationBean bean = civilDynamicService.find(getGuid());
			setApplicationBean(bean);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	  * @Title: initApply
	  * @Description: 初始化申请界面
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/civilApply/initApplyAdd", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/civilbankroll/apply/applyAdd.jsp")})
	@LoginRequired(module="civilApply")
	public String initApplyAdd(){
		try {
			CivilApplicationBean bean = civilDynamicService.find(getGuid());
			if(bean==null){
				bean = new CivilApplicationBean();
				bean.setXmguid(GUID.getGUID());
			}else{
				bean.setProjectdes(WebUtil.nobrtr(bean.getProjectdes()));
			}
			setApplicationBean(bean);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 
	  * @Title: saveApply
	  * @Description: 保存申请信息
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/civilApply/saveApply", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/share/message.jsp"),
				@Result(name = "error" ,location = "/WEB-INF/page/civilbankroll/apply/applyAdd.jsp")})
	@LoginRequired(module="civilApply")
	public String saveApply(){
		try {
			CivilApplicationBean bean = getApplicationBean();
			if("2".equals(bean.getPressor_state())){
				bean.setCreatetime(new Date(System.currentTimeMillis()));
				setMessage("提交成功");
			}else{
				setMessage("暂存成功");
			}
			if(StringUtils.isBlank(bean.getSlbh())){
				bean.setSlbh(projectTypeService.findAcceptNum("{0A0A017E-FFFF-FFFF-992E-3300FFFFFF8E}"));
			}else{
				bean.setUpdatetime(new Date(System.currentTimeMillis()));
			}
			bean.setCreatetuser(getUserGUID());
			bean.setSbztid(getUserGUID());
			civilDynamicService.saveOrUpdate(bean);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			setMessage("申报失败！");
			setUrl("/civilApply/initApplyAdd.YS");
			return ERROR;
		}
		
	}
	
	
	/**
	  * @Title: asyncProjectType
	  * @Description: 异步获取树节点
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/civilApply/asyncProjectType", 
			results = {@Result(name = "success" ,type="json")})
	@LoginRequired(module="civilApply")
	public String asyncProjectType(){
		try {
			List<TreeNode> projectList = projectTypeService.treeNodeList();
			outJsonArray(projectList, null);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	/**
	 * 
	  * @Title: getProjectInfo
	  * @Description: 组装细则条款和材料清单
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/civilApply/getProjectInfo", 
			results = {@Result(name = "success" ,type="json")})
	@LoginRequired(module="civilApply")
	public String projectInfo(){
		try {
			CivilProjectType info = projectTypeService.find(getTypeGUID());
			String xztk = "<font class=\"fileFont\">（一）、项目内容：</font></br>"+WebUtil.filter(info.getContent())+"</br>"+
					"<font class=\"fileFont\">（二）、网上申报需提交材料：</font></br>"+WebUtil.filter(info.getOnlinelists())+"</br>"+
					"<font class=\"fileFont\">（三）、窗口受理需提交材料：</font></br>"+WebUtil.filter(info.getBeizhu())+"</br>"+
					"<font class=\"fileFont\">（四）、备注：</font></br>"+WebUtil.filter(info.getBeizhu());
			setXztkcontent(xztk);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "/civilApply/initFileUpload", results = { @Result(name = "success", location = "/WEB-INF/page/share/upFilePage.jsp") })
	@LoginRequired(module="civilApply")
	public String initFileUpload() {
		try {
			// 查询需上传的材料清单
			String sql = "select ID fileGUID,NAME||'  <font color=\"red\">【'||decode(filetype,'1','电子版','2','扫描件','其它')||'】</font>' fileName," +
					" ISTEMP hasTemp,'"+guid+"' projectFK,'/civilApply/initFileUpPanel' url ,'civilApply' modeType" +
					" from msxm_filetemp where typeguid=? and sbzttype=? order by tab_index";
			List<UpFileBean> upFileList = fileUtileService.getFileBeanList(sql,new String[]{getTypeGUID(),getUserType(),"-1","-1"});
			// 查询材料清单中每项材料已上传的材料
			sql = "select t.attachmentguid fileGUID,t.file_name filename,'civilApply' modeType from msxm_attachment t where t.xmguid=? and t.cllxguid=?";
			for (UpFileBean file : upFileList) {
				file.setFileName(WebUtil.filter(file.getFileName()));
				file.setAlreadyUpFile(fileUtileService.getFileBeanList(sql,
						new String[]{guid,file.getFileGUID(),"-1","-1"}));
			}
			setUpFileList(upFileList);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Action(value = "/civilApply/initFileUpPanel", results = { @Result(name = "success", location = "/WEB-INF/page/share/upFilePanel.jsp") })
	@LoginRequired(module="civilApply")
	public String initFileUpPanel() {
		return SUCCESS;
	}
	
	
}
