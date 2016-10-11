package net.risesoft.actions.bizbankroll.apply;

import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.bizbankroll.BizProjectType;
import net.risesoft.beans.bizbankroll.BizProjectTypeInfo;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.bizbankroll.apply.IBizProjectTypeInfoService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("default")
public class ProjectTypeAction extends BaseActionSupport{
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -7234600474799999963L;
	
	@Resource
	private IBizProjectTypeInfoService projectTypeInfoService;
	@Resource 
	private ISimpleJdbcDao<BizProjectType> proTypeSimpleJdbcDao;
	private String id;
	private List<BizProjectType> projectList;
	private BizProjectTypeInfo projectTypeInfo;
	private String name;
	private String rootGuid;
	
	public String getRootGuid() {
		return rootGuid;
	}

	public void setRootGuid(String rootGuid) {
		this.rootGuid = rootGuid;
	}

	public List<BizProjectType> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<BizProjectType> projectList) {
		this.projectList = projectList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public BizProjectTypeInfo getProjectTypeInfo() {
		return projectTypeInfo;
	}

	public void setProjectTypeInfo(BizProjectTypeInfo projectTypeInfo) {
		this.projectTypeInfo = projectTypeInfo;
	}

	/**
	  * @Title: asyncProjectType
	  * @Description: 异步获取树节点
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/bizApply/asyncProjectType", 
			results = {@Result(name = "success" ,type="json")})
	public String asyncProjectType(){
		try {
			String sql = "select guid, pguid, name, status,uptime" +
			" from (select t.guid, t.pguid, t.name,t.uptime,t.tab_index, " +
			"case when (select count(1)  " +
			"from zjfc_xmlxinfo x  where x.pguid = t.guid) > 0 then '1' else '0' end status " +
			"from zjfc_xmlxinfo t where t.pguid=?) order by status,tab_index";
			List<BizProjectType> projectList = proTypeSimpleJdbcDao.queryForRow(sql, new String[]{getId(),"-1","-1"},BizProjectType.class);
			outJsonArray(projectList, "yyyy-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	  * @Title: projectType
	  * @Description: 申报页面初始化，查询顶级扶持类型
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/bizApply/projectType", 
			results = {@Result(name = "success" ,location="/WEB-INF/page/bizbankroll/apply/queryPage.jsp")})
	public String projectType(){
		String sql = "select guid, pguid, name, isEnd status, departmentguid depart,uptime " +
		"from (select t.departmentguid,t.guid, t.pguid, t.name,t.uptime,t.tab_index, " +
		"case when (select count(1)  " +
		"from zjfc_xmlxinfo x  where x.pguid = t.guid) > 0 then '1' else '0' end isEnd " +
		"from zjfc_xmlxinfo t where t.isyx='1' ) where pguid = '{AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA}' order by tab_index";
		try {
			setProjectList(proTypeSimpleJdbcDao.queryForRow(sql.toString(), null,BizProjectType.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 
	  * @Title: findApplyGuide
	  * @Description: 查找申报指南
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/bizApply/findApplyGuide", 
			results = {@Result(name = "success" ,location="/WEB-INF/page/bizbankroll/apply/applyGuide.jsp")})
	public String findApplyGuide(){
		try {
			setProjectTypeInfo(projectTypeInfoService.findPtiByGuid(getId(),getRootGuid()));
			return SUCCESS;
		} catch (Exception e) {
			 e.printStackTrace();
			return ERROR;
		}
	}
	
	
}
