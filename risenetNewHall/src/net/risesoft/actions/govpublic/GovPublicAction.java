package net.risesoft.actions.govpublic;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.CodeMap;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.govpublic.WindowsInfo;
import net.risesoft.beans.onlineservice.BureauBean;
import net.risesoft.beans.onlineservice.LawsBean;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.govpublic.IBureauService;
import net.risesoft.services.govpublic.IWindowsInfoService;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

/**
 * 
  * @ClassName: GovPublicAction
  * @Description: 政务公开首页Action
  * @author Comsys-zhangkun
  * @date May 15, 2013 2:32:56 PM
  *
 */

@Controller
public class GovPublicAction extends BaseActionSupport {
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 6374442653916693089L;
	private List<CodeMap> bureauList = new ArrayList<CodeMap>();//部门集合
	private String method;
	private String departGUID;//部门GUID
	private List<WindowsInfo> windList = new ArrayList<WindowsInfo>();
	private BureauBean bureauBean;
	private String LawName;
	@Resource
	private ICodeMapUtil codeMapUtil;//映射 工具类
	@Resource
	private ISimpleJdbcDao<LawsBean> lawsBeanSimpleJdbcDao;
	@Resource 
	private IWindowsInfoService windowsInfoService;
	@Resource
	private IBureauService bureauService;//部门服务
	
	public List<WindowsInfo> getWindList() {
		return windList;
	}


	public void setWindList(List<WindowsInfo> windList) {
		this.windList = windList;
	}


	public List<CodeMap> getBureauList() {
		return bureauList;
	}


	public void setBureauList(List<CodeMap> bureauList) {
		this.bureauList = bureauList;
	}

	


	public String getLawName() {
		return LawName;
	}


	public void setLawName(String lawName) {
		LawName = lawName;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getDepartGUID() {
		return departGUID;
	}


	public void setDepartGUID(String departGUID) {
		this.departGUID = departGUID;
	}


	public BureauBean getBureauBean() {
		return bureauBean;
	}


	public void setBureauBean(BureauBean bureauBean) {
		this.bureauBean = bureauBean;
	}


	/****
	 * @author zhangkun
	 * @Description: 大厅首页数据初始化
	 * 
	 * ***/
	@Action(value = "/govpublic/initIndexPage", results = { @Result(name = "success", location = "/WEB-INF/page/govpublic/index.jsp")})
	public String initIndexPage() throws Exception {
		String sql = "select distinct b.bureauguid  as code, "+
					" b.bureauname as value,b.isstreet  status,b.bureautabindex "+
					" from spm_bureau b,xzql_xzsp_extend e, xzql_xzsp_base x "+
					" where b.flag = '1' and e.isprovince = '1' "+
					" and e.approveitemstatus = '运行' and x.itemid=e.itemid(+) "+
					" and x.adminorgid=b.bureauguid order by bureautabindex";
		setBureauList(codeMapUtil.getCodeMapList(sql));
		return SUCCESS;
	}
	/**
	 * 
	  * @Title: findWindows
	  * @Description: 根据局GUID查询办事窗口信息
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/govpublic/findWindows", results = { @Result(name = "success", location = "/WEB-INF/page/govpublic/windows.jsp")})
	public String findWindows() throws Exception {
		setWindList(windowsInfoService.findByProperty("orgcode", getDepartGUID()));
		setBureauBean(bureauService.find(getDepartGUID()));
		return SUCCESS;
	}
	/**
	 * 
	  * @Title: loadAddressMap
	  * @Description: 查询办事窗口单位在地图的位置
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/govpublic/loadAddressMap", results = { @Result(name = "success", location = "/WEB-INF/page/govpublic/map.jsp")})
	public String loadAddressMap() throws Exception {
		return SUCCESS;
	}
	/**
	 * 
	  * @Title: findLawByDepartGUID
	  * @Description: 根据GUID查询办事依据
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/govpublic/findLawByDepartGUID", results = { @Result(name = "success", location = "/WEB-INF/page/govpublic/reason.jsp")})
	public String findLawByDepartGUID() throws Exception {
		int maxResult = 10;
		ArrayList<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct z.ID, z.title,z.createtime ,case when dbms_lob.getlength(z.content)>0 then '1' else '0' end status");
		sql.append(" from XZQL_LAW_ITEM t, XZQL_LAW z,xzql_item_depart_org d ");
		sql.append(" where z.id = t.lawid ");
		sql.append(" and t.itemid =d.itemid ");
		sql.append(" and d.departid=? ");
		try {
			param.add(getDepartGUID());
			if(StringUtils.isNotBlank(getLawName())){
				sql.append(" and z.title like ?");
				param.add("%"+getLawName()+"%");
			}
			sql.append(" order by z.createtime desc");
			PageView<LawsBean> pageView = new PageView<LawsBean>(maxResult,
					getPage(),lawsBeanSimpleJdbcDao.countRows(sql.toString(), GenericsUtils.listToArray(param)));
			param.add(pageView.getStartIndex());
			param.add(pageView.getEndIndex());
			pageView.setRecords(lawsBeanSimpleJdbcDao.queryForRow(sql.toString(),GenericsUtils.listToArray(param),LawsBean.class));
			getRequest().setAttribute("pageView", pageView);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
