package net.risesoft.services.civilbankroll.apply.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.base.TreeNode;
import net.risesoft.beans.civilbankroll.CivilProjectType;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.civilbankroll.apply.ICivilProjectTypeService;
@Service
public class CivilProjectTypeServiceImpl extends BaseDaoImpl<CivilProjectType> implements ICivilProjectTypeService{
	@Resource
	ISimpleJdbcDao<TreeNode> simpleJdbcDao;
	public synchronized String findAcceptNum(String typeGUID) {
		String sql = "SELECT FUN_GETZJFCYWLSH('"+typeGUID+"') name FROM DUAL";
		return simpleJdbcDao.getBean(sql, TreeNode.class).getName();
	}

	public List<TreeNode> treeNodeList() {
		String sql = "select id,name,'0' pid,'true' isParent from msxm_type where status='1' and year=to_char(sysdate,'YYYY') union all "+
			" SELECT id,name,typeguid pid,'false' isParent FROM msxm_type_project where status='1' and typeguid in(select id from msxm_type where status='1' and year=to_char(sysdate,'YYYY'))";
		return simpleJdbcDao.queryForRow(sql, TreeNode.class);
	}
	
}
