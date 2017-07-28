/**
 * @Project Name:risenet-sp-approve
 * @File Name: SharestuffService.java
 * @Package Name: net.risesoft.approve.service
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月11日 下午2:32:42
 */
package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: SharestuffService.java
 * @Description: 材料共享
 *
 * @author chenbingni
 * @date 2016年3月11日 下午2:32:42
 * @version 
 * @since JDK 1.7
 */
public interface SharestuffService {
	
	/**
	 * 
	  * @MethodName: findByInstanceguidAndDeclareannexguid
	  * @Description: 窗口收件：根据instanceguid和材料guid查找共享材料
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<SPMwssbcl>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月11日  下午2:38:03
	 */
	public List<Map<String, Object>> findByInstanceguidAndDeclareannexguid(String processInstanceId, String declareannexguid);
	
	/**
	 * 
	  * @MethodName: findBySbguidAndDeclareannexguid
	  * @Description: 网上收件：根据sb_approveinstance表的guid和材料guid查找共享材料
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月26日  下午6:06:38
	 */
	public List<Map<String, Object>> findBySbguidAndDeclareannexguid(String instanceguid, String declareannexguid);
	
	/**
	 * 
	  * @MethodName: findXtByStuffguid
	  * @Description: 查找stuffdataguid名目下的共享材料附件列表
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<StuffdataXt>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月19日  下午4:02:22
	 */
	public List<Map<String, Object>> findXtByStuffguid(String stuffdataguid);
	
	/**
	 * 
	  * @MethodName: updateStuffdataByColumnName
	  * @Description: 根据guid将ShareStuffdata表的columnName列的值更改为columnValue
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return int    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月23日  上午10:37:40
	 */
	public int updateStuffdataByColumnName(String guid, String columnName, String columnValue);
	
	/**
	 * 
	  * @MethodName: saveWuxiao
	  * @Description: 保存认证为无效的原因
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： guid和无效的原因
	  * @return boolean    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月24日  下午6:35:31
	 */
	public int saveWuxiao(String stuffdataguid, String remark);
	
	/**
	 * 
	  * @MethodName: upload
	  * @Description: 给某个共享材料上传附件
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return void    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月28日  下午2:38:39
	 */
	public void upload(MultipartFile file, String stuffdataguid);
	
	/**
	 * 
	  * @MethodName: delXt
	  * @Description: 删除共享材料附件
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return boolean    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月1日  上午11:37:02
	 */
	public boolean delXt(String xtguid);
	
	/**
	 * 
	  * @MethodName: delXt
	  * @Description: 删除共享材料
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return boolean    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月1日  上午11:37:02
	 */
	public boolean delStuffdata(String stuffdataguid);
	
	/**
	 * 
	  * @MethodName: copyFromStuffToWssbcl
	  * @Description: 窗口收件：将共享的认证有效材料加载到网上申办材料表中
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return void    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月5日  上午11:38:54
	 */
	public void copyFromStuffToWssbcl(String instanceguid, String declareannexguid);
	
	/**
	 * 
	  * @MethodName: findValidatasBySbguidAndDeclareannexguid
	  * @Description: 寻找认证有效的共享材料列表
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月5日  下午2:37:41
	 */
	public List<Map<String, Object>> findValidatasByInstanceguidAndDeclareannexguid(String processInstanceId, String declareannexguid);
	
	/**
	 * 
	  * @MethodName: download
	  * @Description: 共享材料：下载附件
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return void    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月7日  下午2:59:19
	 */
	public void download(String id, HttpServletResponse response,HttpServletRequest request);
	
	/**
	 * 
	  * @MethodName: updateStuffdata
	  * @Description: Stuffdata整行修改
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return int    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月5日  下午2:37:42
	 */
	public int updateStuffdata(HttpServletRequest request);
	
	/**
	 * 
	  * @MethodName: tongbu
	  * @Description: 共享材料修改后从31同步到84
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： String procedureName 存储过程的名字, String guid 存储过程的参数
	  * @return void    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年7月27日  下午12:02:37
	 */
	public void tongbu(String procedureName, String guid);

}

