package net.risesoft.services.bizbankroll.apply;


import net.risesoft.beans.bizbankroll.BizProjectTypeInfo;
import net.risesoft.daos.base.IBaseDao;

public interface IBizProjectTypeInfoService extends IBaseDao<BizProjectTypeInfo>{
	public BizProjectTypeInfo findPtiByGuid(String guid,String rootGuid)throws Exception;
	public String findPathName(String guid)throws Exception;
	public String findDepartGuidByTypeGuid(String guid)throws Exception;
	public String findProvision(String guid)throws Exception;
	public BizProjectTypeInfo getXztkcontentByType(String projectTypeGUID) throws Exception;
}

