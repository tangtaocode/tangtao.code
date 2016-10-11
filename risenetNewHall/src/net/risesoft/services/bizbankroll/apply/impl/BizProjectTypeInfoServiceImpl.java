package net.risesoft.services.bizbankroll.apply.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.BizProjectType;
import net.risesoft.beans.bizbankroll.BizProjectTypeInfo;
import net.risesoft.beans.risefile.UpFileBean;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.apply.IBizProjectTypeInfoService;
import net.risesoft.services.bizbankroll.apply.IBizProjectTypeService;
import net.risesoft.services.fileUtil.IFileUtileService;
import net.risesoft.utils.base.WebUtil;

@Service
public class BizProjectTypeInfoServiceImpl extends
		BaseDaoImpl<BizProjectTypeInfo> implements IBizProjectTypeInfoService {
	@Resource
	private IBizProjectTypeService projectTypeService;
	@Resource
	private IFileUtileService fileUtileService;
	public BizProjectTypeInfo findPtiByGuid(String guid,String rootGuid)throws Exception {
		
		BizProjectTypeInfo pti = getXztkcontentByType(guid);
		pti.setXztkcontent(WebUtil.filter(pti.getXztkcontent()));
		pti.setTypeName(projectTypeService.find(guid).getName());
		pti.setTypePathName(findPathName(guid));
		String sql = "select t.guid fileGUID,t.name fileName from zjfc_appcl t where t.xmlxguid=?";
		List<UpFileBean> upFileList = fileUtileService.getFileBeanList(sql,
				new String[] { guid, "-1", "-1" });
		int i = 1;
		for (UpFileBean bean : upFileList) {
			bean.setFileName(WebUtil.filter(bean.getFileName()));
			i++;
		}
		pti.setUpFileList(upFileList);
		sql = "select decode(b.bureaucnfullname,null,b.bureauname,b.bureaucnfullname) valCount from spm_bureau b,( "+
			" select distinct decode(d1.department_guid,'{00000000-0000-0000-0000-000000000000}'," +
			" d.department_guid,d1.department_guid) rootDepar "+
			" from zjfc_xmlxinfo t,risenet_department d,risenet_department d1 where t.guid = ? "+
			" and t.departmentguid=d.department_guid and d1.department_guid=d.superior_guid)o "+
			" where o.rootDepar = b.bureauguid";
		pti.setRootGuid(rootGuid);
		pti.setDepartName((String) projectTypeService.findObject(sql,
				new String[] { rootGuid }, Hibernate.STRING));
		return pti;
	}

	/**
	 * 
	 * @Title: getXztkcontentByType
	 * @Description: 递归获取细则条款
	 * @param
	 * @param projectTypeGUID
	 * @param
	 * @return 设定文件
	 * @return BizProjectTypeInfo 返回类型
	 * @throws
	 */
	public BizProjectTypeInfo getXztkcontentByType(String projectTypeGUID) throws Exception{
		BizProjectTypeInfo pti = findBeanBypro("xmlxguid", projectTypeGUID);
		if ((pti == null || StringUtils.isBlank(pti.getXztkcontent()))
				&& !"{AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA}"
						.equals(projectTypeGUID)) {
			return getXztkcontentByType(projectTypeService
					.find(projectTypeGUID).getPguid());
		} else {
			return pti;
		}
	}
	/**
	 * 
	  * @Title: findDepartByTypeGuid
	  * @Description: TODO
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public String findDepartGuidByTypeGuid(String guid)throws Exception{
		BizProjectType bpt = projectTypeService.find(guid);
		if(StringUtils.isBlank(bpt.getDepartmentguid())&&!"{AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA}".equals(bpt.getPguid())){
			return findDepartGuidByTypeGuid(bpt.getPguid());
		}else{
			return bpt.getDepartmentguid();
		}
	}
	public String findPathName(String guid)throws Exception{
		String sql = "select substr(pathstr,3,length(pathstr)) valCount  from ( "
			+ " SELECT distinct SYS_CONNECT_BY_PATH(name, ' > ') pathstr "
			+ " FROM zjfc_xmlxinfo z where z.guid=? "
			+ " START WITH guid in (select guid from zjfc_xmlxinfo t where t.pguid='{AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA}') "
			+ " CONNECT BY PRIOR guid= pguid)";
		return (String) projectTypeService.findObject(sql,
				new String[] { guid }, Hibernate.STRING);
	}
	public String findProvision(String guid){
		return findBeanBypro("xmlxguid", guid).getGuid();
	}

}
