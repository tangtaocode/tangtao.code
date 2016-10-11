/**
 * @Project Name:LGOneHome
 * @File Name: ISmsService.java
 * @Package Name: net.risesoft.service
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月9日 下午1:03:00
 */

package net.risesoft.service;

import java.util.List;
import java.util.Map;

import net.risesoft.annotation.DataSource;
import net.risesoft.common.base.Pager;
import net.risesoft.model.RiseEmployee;
import net.risesoft.model.personset.SmsModel;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName: ISmsService.java
 * @Description:短信接口服务类
 *
 * @author tt
 * @date 2015年12月9日 下午1:03:00
 * @version 
 * @since JDK 1.6
 */
public interface ISmsService {
	/**
	 * 
	* @Title: getSmsDataList 
	* @Description: 获取短信所有信息
	* @param @return    设定文件 
	* @return Pager    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public Pager getSmsDataList(Pager pager,SmsModel smsmodel);
	/**
	 * 
	* @Title: createInsertSms 
	* @Description: 更新或插入短信信息
	* @param @param rowdata    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public int createInsertSms( Map rowdata);
	/**
	 * 
	* @Title: updateSms 
	* @Description: 修改短信设置
	* @param @param rowdata
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public void updateSms(Map rowdata);
	/**
	 * 
	* @Title: deleteSms 
	* @Description: 删除短信
	* @param @param rowdata    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public int deleteSms(Map rowdata);
	/**
	 * 
	* @Title: getSmsByid 
	* @Description: 根据guid获取短信信息
	* @param @param guid
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public Map getSmsByid(String guid);
	/**
	 * 
	* @Title: getSmsPersonList 
	* @Description: 获取短信人员列表
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public List<Map<String,Object>> getSmsPersonList();
}
