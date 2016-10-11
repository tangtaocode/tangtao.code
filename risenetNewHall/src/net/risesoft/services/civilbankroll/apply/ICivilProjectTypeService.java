package net.risesoft.services.civilbankroll.apply;

import java.util.List;

import net.risesoft.beans.base.TreeNode;
import net.risesoft.beans.civilbankroll.CivilProjectType;
import net.risesoft.daos.base.IBaseDao;

public interface ICivilProjectTypeService extends IBaseDao<CivilProjectType>{
	public String findAcceptNum(String typeGUID)throws Exception;
	public List<TreeNode> treeNodeList()throws Exception;
}
