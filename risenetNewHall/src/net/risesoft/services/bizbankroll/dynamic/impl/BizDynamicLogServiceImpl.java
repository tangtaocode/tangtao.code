package net.risesoft.services.bizbankroll.dynamic.impl;

import org.springframework.stereotype.Service;
import net.risesoft.beans.bizbankroll.BizApplicationLog;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.dynamic.IBizDynamicLogService;
 
@Service 
public class BizDynamicLogServiceImpl extends BaseDaoImpl<BizApplicationLog>
		implements IBizDynamicLogService {
}
