package net.risesoft.actions.bizbankroll.apply;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;
import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.bizbankroll.BizApplicationBean;
import net.risesoft.beans.bizbankroll.BizApplicationLog;
import net.risesoft.beans.bizbankroll.BizIntroducerBean;
import net.risesoft.beans.bizbankroll.BizProjectTypeInfo;
import net.risesoft.beans.risefile.UpFileBean;
import net.risesoft.services.bizbankroll.apply.IBizIntroducerService;
import net.risesoft.services.bizbankroll.apply.IBizProjectTypeInfoService;
import net.risesoft.services.bizbankroll.dynamic.IBizDynamicLogService;
import net.risesoft.services.bizbankroll.dynamic.IBizDynamicService;
import net.risesoft.services.fileUtil.IFileUtileService;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.WebUtil;
@Controller
@ParentPackage("default")  //继承json包，用于返回json
@InterceptorRefs({@InterceptorRef("isLoginStack")})
public class BizApplyAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 3490204636872958410L;
	private BizApplicationBean applicationBean;
	private BizIntroducerBean introducerBean;
	private String guid;//项目GUID
	private String id;//资助项目类型GUID
	private String modeType;//文件上传模块
	private String correctionGUID; //上传材料GUID
	private String projectFK;//上传材料的项目关联主键
	private String rootGuid;//根节点id
	private String proTypeName;//扶持项目类型名称
	private String provision;//细则条款GUID
	private String step;//步骤号索引
	private String method;
	private String status;
	private List<UpFileBean> fileTempList;//材料模板
	private List<UpFileBean> upFileList; //上传文件列表
	private BizProjectTypeInfo projectTypeInfo;//细则条款
	
	@Resource
	IBizDynamicService bizDynamicService;
	@Resource
	private IFileUtileService fileUtileService;
	@Resource
	private IBizIntroducerService introducerService;
	@Resource
	private IBizProjectTypeInfoService projectTypeInfoService;
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource
	private IBizDynamicLogService dynamicLogService;
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getProTypeName() {
		return proTypeName;
	}
	public void setProTypeName(String proTypeName) {
		this.proTypeName = proTypeName;
	}
	

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProvision() {
		return provision;
	}
	public void setProvision(String provision) {
		this.provision = provision;
	}
	public String getRootGuid() {
		return rootGuid;
	}
	public void setRootGuid(String rootGuid) {
		this.rootGuid = rootGuid;
	}
	public BizApplicationBean getApplicationBean() {
		return applicationBean;
	}
	public void setApplicationBean(BizApplicationBean applicationBean) {
		this.applicationBean = applicationBean;
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
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
	
	public BizIntroducerBean getIntroducerBean() {
		return introducerBean;
	}
	public void setIntroducerBean(BizIntroducerBean introducerBean) {
		this.introducerBean = introducerBean;
	}
	
	
	public List<UpFileBean> getFileTempList() {
		return fileTempList;
	}
	public void setFileTempList(List<UpFileBean> fileTempList) {
		this.fileTempList = fileTempList;
	}
	
	public BizProjectTypeInfo getProjectTypeInfo() {
		return projectTypeInfo;
	}
	public void setProjectTypeInfo(BizProjectTypeInfo projectTypeInfo) {
		this.projectTypeInfo = projectTypeInfo;
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
	@Action(value = "/bizApply/viewApply", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/apply/viewApply.jsp")})
	@LoginRequired(module="bizApply")
	public String viewApply(){
		try {
			BizApplicationBean bean = bizDynamicService.find(getGuid());
			//bean.setAccording(getXztkcontentByGUID(bean.getProvision()));
			setApplicationBean(bean);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	  *
	  * @Title: viewIntroducer
	  * @Description: 查看引荐人申请信息
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/bizApply/viewIntroducer", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/apply/viewIntroducer.jsp")})
	@LoginRequired(module="bizApply")
	public String viewIntroducer(){
		try {
			BizIntroducerBean bean = introducerService.find(getGuid());
			//bean.setProvisionInfo(getXztkcontentByGUID(bean.getProvision()));
			setIntroducerBean(bean);
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
	@Action(value = "/bizApply/initApplyAdd", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/apply/apply.jsp")})
	@LoginRequired(module="bizApply")
	public String initApplyAdd(){
		try {
			setProjectTypeInfo(projectTypeInfoService.findPtiByGuid(getId(),getRootGuid()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Action(value = "/bizApply/applyAdd", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/apply/add.jsp")})
	@LoginRequired(module="bizApply")
	public String applyAdd(){
		try {
			BizApplicationBean bean = bizDynamicService.find(getGuid());
			if(bean==null){
				bean = new BizApplicationBean();
				bean.setXmlxroot(rootGuid);
				bean.setGuid(GUID.getGUID());
				bean.setCategory_id(id);
				BizProjectTypeInfo bpti = projectTypeInfoService.getXztkcontentByType(id);
				bean.setCategory(projectTypeInfoService.findPathName(id));
				bean.setProvision(bpti.getGuid());
				bean.setZgbmid(projectTypeInfoService.findDepartGuidByTypeGuid(id));
				bean.setCreatetuser(getUserGUID());
				bean.setSbztid(getUserGUID());
				bean.setAccording(WebUtil.filter(bpti.getXztkcontent()));
			}else{
				bean.setEnterprises_affirms(WebUtil.nobrtr(bean.getEnterprises_affirms()));
				bean.setAccording(WebUtil.filter(projectTypeInfoService.find(bean.getProvision()).getXztkcontent()));
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
	@Action(value = "/bizApply/saveApply")
	@LoginRequired(module="bizApply")
	public String saveApply(){
		try {
			if(StringUtils.isBlank(getMethod())){
				BizApplicationBean bean = getApplicationBean();
				if(StringUtils.isBlank(bean.getSlbh())){
					bean.setSlbh(codeMapUtil.findSlbh(bean.getXmlxroot()));
					bean.setZcsj(new Date());
				}else{
					bean.setUpdatetime(new Date());
				}
				bizDynamicService.saveOrUpdate(bean);
				BizApplicationLog beanLog = new BizApplicationLog();
				BeanUtils.copyProperties(beanLog, bean);
				beanLog.setHistoryguid(GUID.getGUID());
				beanLog.setGuid(bean.getGuid());
				beanLog.setLogDate(new Date());
				dynamicLogService.save(beanLog);
				outJson("{'message':'1'}", null);
				return null;
			}else{
				BizApplicationBean bean = bizDynamicService.find(guid);
				bean.setPressor_state(status);
				bizDynamicService.update(bean);
				outJson("{'message':'"+status+"'}", null);
				return null;
			}
			
		} catch (Exception e) {
			outJson("{'message':'0'}", null);
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 
	  * @Title: goStep
	  * @Description: 申报步骤
	  * @param @return    1用户信息，2申报信息，3上传材料，4完成申报
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/bizApply/goStep", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/apply/add.jsp")})
	@LoginRequired(module="bizApply")
	public String goStep(){
		if("1".equals(getStep())){
			
			return "userInfo";
		}else if("2".equals(getStep())){
			return "apply";
		}else if("3".equals(getStep())){
			return "fileUp";
		}else if("4".equals(getStep())){
			return "applyFinished";
		}
		return INPUT;
	}
	/**
	 * 
	  * @Title: initApply
	  * @Description: 初始化申请界面
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/bizApply/initApplyForIntr", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/apply/applyAdd.jsp")})
	@LoginRequired(module="bizApply")
	public String initApplyForIntr(){
		try {
			BizIntroducerBean bean = introducerService.find(getGuid());
			BizApplicationBean appBean = new BizApplicationBean();
			appBean.setGuid(bean.getGuid());
			appBean.setCategory_id(bean.getCategory_id());
			appBean.setCategory(bean.getCategory());
			appBean.setSbztid(bean.getSbztid());
			//appBean.setSlbh(projectTypeService.getAcceptNum(getRootNodeId()));
			appBean.setProvision(bean.getProvision());
			appBean.setXmlxroot(bean.getXmlxroot());
			//appBean.setAccording(getXztkcontentByGUID(bean.getProvision()));
			setApplicationBean(appBean);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	/**
	  *
	  * @Title: initIntroducer
	  * @Description: 引荐人申请页面初始化
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/bizApply/initIntroducer", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/apply/introducer.jsp")})
	@LoginRequired(module="bizApply")
	public String initIntroducer(){
		try {
			BizIntroducerBean bean = introducerService.find(getGuid());
			if(bean==null){
				bean = new BizIntroducerBean();
				bean.setGuid(applicationBean.getGuid());
				bean.setCategory_id(applicationBean.getCategory_id());
				bean.setProvision(applicationBean.getProvision());
				bean.setZgbmid(applicationBean.getZgbmid());
				bean.setXmlxroot(applicationBean.getXmlxroot());
				bean.setCategory(applicationBean.getCategory());
				bean.setYjrno(applicationBean.getSlbh());
			}
			//bean.setProvisionInfo(getXztkcontentByGUID(bean.getProvision()));
			setIntroducerBean(bean);
			String sql = "select t.guid fileGUID,t.name fileName,'"+bean.getGuid()+"' projectFK,'/bizApply/initFileUpPanel' url,'bizApply' modeType from zjfc_appcl t where t.xmlxguid=?";
			List<UpFileBean> upFileList = fileUtileService.getFileBeanList(sql,new String[]{bean.getXmlxroot(),"-1","-1"});
			// 查询材料清单中每项材料已上传的材料
			sql = "select t.guid fileGUID,t.filename,'bizApply' modeType from zjfc_upfile t where t.xmlxguid=? and t.clguid=?";
			for (UpFileBean file : upFileList) {
				file.setAlreadyUpFile(fileUtileService.getFileBeanList(sql,
						new String[]{bean.getGuid(),file.getFileGUID(),"-1","-1"}));
			}
			setUpFileList(upFileList);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	/**
	 * 
	  * @Title: saveIntroducer
	  * @Description: 保存引荐人申请信息
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/bizApply/saveIntroducer", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/share/message.jsp"),
			@Result(name = "error" ,location = "/WEB-INF/page/bizbankroll/apply/introducer.jsp")})
	@LoginRequired(module="bizApply")
	public String saveIntroducer(){
		try {
			BizIntroducerBean bean = getIntroducerBean();
			if("2".equals(bean.getState())){
				bean.setSubtime(new Date());
				//setMessage("提交成功");
			}else{
				//setMessage("暂存成功");
			}
			bean.setSbztid(getUserGUID());
			introducerService.saveOrUpdate(bean);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			//setMessage("申报失败！");
			//setUrl("/bizApply/initApplyAdd.YS");
			return ERROR;
		}
		
	}
	
	

	
	

	@Action(value = "/bizApply/initFileUpload", results = { @Result(name = "success", location = "/WEB-INF/page/share/upFilePage.jsp") })
	@LoginRequired(module="bizApply")
	public String initFileUpload() {
		try {
			// 查询需上传的材料清单
			String sql = "select t.guid fileGUID,t.name fileName,'"+guid+"' projectFK,'/bizApply/initFileUpPanel' url,'bizApply' modeType from zjfc_appcl t where t.xmlxguid=?";
			List<UpFileBean> upFileList = fileUtileService.getFileBeanList(sql,new String[]{getId(),"-1","-1"});
			// 查询材料清单中每项材料已上传的材料
			sql = "select t.guid fileGUID,t.filename,'bizApply' modeType from zjfc_upfile t where t.xmlxguid=? and t.clguid=?";
			for (UpFileBean file : upFileList) {
				file.setFileName(WebUtil.filter(file.getFileName()));
				file.setAlreadyUpFile(fileUtileService.getFileBeanList(sql,
						new String[]{guid,file.getFileGUID(),"-1","-1"}));
			}
			setUpFileList(upFileList);
			sql = "select guid fileGUID,bgname fileName,'bizFileTemp' modeType from ZJFC_BGXZ t where t.xmlbguid=?";
			List<UpFileBean> fileTemp = fileUtileService.getFileBeanList(sql,new String[]{getId(),"-1","-1"});
			setFileTempList(fileTemp);
			setModeType("bizApply");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Action(value = "/bizApply/initFileUpPanel", results = { @Result(name = "success", location = "/WEB-INF/page/share/upFilePanel.jsp") })
	@LoginRequired(module="bizApply")
	public String initFileUpPanel() {
		return SUCCESS;
	}
}
