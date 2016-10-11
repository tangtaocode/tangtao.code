/**
 * @ProjectName:zjjHome
 * @FileName: OneHomeServiceImpl.java
 * @PackageName: net.risesoft.service.impl
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 5, 2015 5:59:35 PM
 */
package net.risesoft.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.service.IOneHomeService;

/**
 * @ClassName: OneHomeServiceImpl.java
 * @Description: TODO
 *
 * @author kun
 * @date May 5, 2015 5:59:35 PM
 * @version 
 * @since JDK 1.6
 */
@Service
public class OneHomeServiceImpl implements IOneHomeService{
	@javax.annotation.Resource
	private JdbcTemplate jdbcTemplate;

	/* (非 Javadoc) 
	* <p>Title: handworkCheckData</p> 
	* <p>Description: </p> 
	* @return 
	* @see net.risesoft.service.IOneHomeService#handworkCheckData() 
	*/
	@Override
	public boolean handworkCheckData() {
		boolean revalue = false;
		try {
			//检查所有审批过程数据
			String check_all_data = "call pack_shenpi_check.CHECK_ALL_DATA()";
			//更新检查后所有数据标志
			String update_data = "call pack_shenpi_updateflag.update_data()";
			//检查数据是否超期报送
			//String check_data_12hour = "call pack_shenpi_12hour_check.CHECK_DATA()";
			jdbcTemplate.execute(check_all_data);
			jdbcTemplate.execute(update_data);
			//jdbcTemplate.execute(check_data_12hour);
			revalue = true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			revalue= false;
		}
		return revalue;
	}
}

