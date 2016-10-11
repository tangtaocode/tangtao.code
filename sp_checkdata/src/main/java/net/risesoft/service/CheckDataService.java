/**   
* @Title: CheckDataService.java 
* @Package net.risesoft.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016年9月22日 上午9:20:00 
* @version V1.0   
*/
package net.risesoft.service;

import java.util.List;
import java.util.Map;

import net.risesoft.annotation.DataSource;
import net.risesoft.common.base.Pager;

/** 
* @ClassName: CheckDataService 
* @Description: 检查数据
* @author tangtao
* @date 2016年9月22日 上午9:20:00 
*  
*/
public interface CheckDataService {
	/**
	 * 
	* @Title: checkHourData 
	* @Description: 查询超时数据
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public Pager checkHourData(Pager pager,String sblsh,String sxmc);
	/**
	 * 
	* @Title: checkJianChaData 
	* @Description: 查询监察数据
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public Pager checkJianChaData(Pager pager,String sblsh,String sxmc);
	/**
	 * 
	* @Title: getProblemList 
	* @Description: 获取具体问题描述
	* @param @param pager
	* @param @param sblsh
	* @param @return    设定文件 
	* @return Pager    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public Pager getProblemList(Pager pager,String sblsh);
	/**
	 * 
	* @Title: getHourCounts 
	* @Description: 获取超时总数
	* @param @return    设定文件 
	* @return String  返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public String getHourCounts();
	/**
	 * 
	* @Title: getJianChaCounts 
	* @Description: 获取监察总数
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@DataSource("xzspDataSource")
	public String getJianChaCounts();
}
