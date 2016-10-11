/**
 * @ProjectName:zjjHome
 * @FileName: IOneHomeService.java
 * @PackageName: net.risesoft.service
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 5, 2015 5:58:53 PM
 */
package net.risesoft.service;

import java.util.List;
import java.util.Map;

import net.risesoft.annotation.DataSource;
import net.risesoft.model.RiseEmployee;
import egov.appservice.ac.model.Resource;

/**
 * @ClassName: IOneHomeService.java
 * @Description: TODO
 *
 * @author tangtao
 * @date May 5, 2015 5:58:53 PM
 * @version 
 * @since JDK 1.6
 */
public interface IOneHomeService {
	
	/**
	 * 
	* @Title: handworkCheckData 
	* @Description: 手动校验审批办事过程数据
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public boolean handworkCheckData();
}

