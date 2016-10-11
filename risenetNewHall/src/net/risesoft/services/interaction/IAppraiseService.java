package net.risesoft.services.interaction;

import net.risesoft.beans.interaction.AppraiseBean;
import net.risesoft.daos.base.IBaseDao;

public interface IAppraiseService extends IBaseDao<AppraiseBean>{
	public AppraiseBean getAppraiseBean(String declaresn)throws Exception;
}
