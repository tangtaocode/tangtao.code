/**
 * @Project Name:risenet-sp-approve
 * @File Name: SpmApproveShortService.java
 * @Package Name: net.risesoft.approve.service
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年5月9日 下午3:18:52
 */
package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.together.XzqlXzspListspoint;
import net.risesoft.approve.entity.jpa.together.XzqlXzspListsps;
import net.sf.json.JSONObject;

/**
 * @ClassName: SpmApproveShortService.java
 * @Description: 综合窗口Service
 *
 * @author chenbingni
 * @date 2016年5月9日 下午3:18:52
 * @version 
 * @since JDK 1.7
 */
public interface TogetherWindowService {
	
	/**
	 * 
	  * @MethodName: findApproveShortList
	  * @Description: 查找事项简码List
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param：request中可能包含部门（bureauGUID）和事项名称（approveItemName）
	  * @return Pager    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月9日  下午4:01:42
	 */
	public Pager findApproveShortList(HttpServletRequest request, Pager pager);
	
	/**
	 * 
	  * @MethodName: findItemSuggestion
	  * @Description: 根据文本框当前输入的inp去数据搜索以inp开头的事项简码提示在文本框下方
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： inp当前文本框输入的内容
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年7月19日  上午11:38:43
	 */
	public List<JSONObject> findItemSuggestion(String inp);
	
	/**
	 * 查看列表页面
	 */
	public Pager findReceiveList(HttpServletRequest request, Pager pager,String status);
	/**
	 * 
	  * @MethodName: saveShortCode
	  * @Description: 保存事项简码
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return int    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月9日  下午5:26:23
	 */
	public int saveShortCode(String itemid, String shortcode, String method);
	
	/**
	 * 
	  * @MethodName: findClList
	  * @Description: 查找非审批平台事项申报所需材料
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Paget    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月16日  下午5:52:15
	 */
	public List<Map<String, Object>> findClList(HttpServletRequest request);
	
	/**
	 * 
	  * @MethodName: getPointItemList
	  * @Description: 配置收件要点第一页-事项列表页
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Pager    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月17日  下午2:03:07
	 */
//	public Pager getPointItemList(HttpServletRequest request, Pager pager);
	
	/**
	 * 
	  * @MethodName: findClPoint
	  * @Description: 新增、修改某个材料的收件要点
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月17日  下午5:01:48
	 */
	public List<Map<String, Object>> findClPoint(HttpServletRequest request);
	
	/**
	 * 
	  * @MethodName: findThePoint
	  * @Description: 根据材料ＩＤ查找某个材料的收件要点
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return XzqlXzspListspoint    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月20日  下午4:24:11
	 */
	public XzqlXzspListspoint findThePoint(String id);
	
	/**
	 * 
	  * @MethodName: findPsList
	  * @Description: 根据材料ID查找某个材料的提交要点：提交类型、提交方式、提交数量
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<XzqlXzspListsps>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月20日  下午4:55:16
	 */
	public List<XzqlXzspListsps> findPsList(String id);
	
	/**
	 * 
	  * @MethodName: savePoint
	  * @Description: 保存材料要点
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return int
	  * @throws
	 */
	public XzqlXzspListspoint savePoint(XzqlXzspListspoint point);

	/**
	  * @MethodName: saveListsType
	  * @Description: 保存材料提交方式
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return int
	  * @throws
	 */
	public int saveListsType(String listguid,HttpServletRequest request);
	
	/**
	 * 查询材料提交方式
	 */
	public Map<String,Object> findListType(String listguid);
	
	/**
	 * 保存收件信息
	 */
	public OfficeSpiDeclareinfo saveTogetherReceive(OfficeSpiDeclareinfo office);
	
	/**
	 * 综合窗口-统一领证列表
	 * @param request
	 * @return
	 */
	public Pager getLingzhengList(HttpServletRequest request, Pager pager);
	
	/**
	 * 综合窗口-待扫描列表
	 * @param request
	 * @return
	 */
	public Pager getScanList(HttpServletRequest request, Pager pager);
	
	/**
	 * 加载证件类型
	 */
	public List<Map<String,Object>> loadZjlx(String type);
	
	/**
	 * 统一领证窗口-保存录入的其他系统的业务信息
	 * @param request（事项GUID、业务编号、事项名称、项目名称、申请人相关信息、联系人相关信息）
	 * @return
	 */
	public int saveDeclareInfo(String guid, HttpServletRequest request);
	
	/**
	 * 综合窗口-待领取
	 * @param request
	 * @return
	 */
//	public Pager getLingquList(HttpServletRequest request, Pager pager);
	
	/**
	 * 根据一个业务编号去判断该业务是否已办结、是否已录入证照信息、是否进行出证扫描、是否进行领取登记
	 * @param yxtywlsh
	 * @return
	 */
//	public String scanOrLingqu(String yxtywlsh);
}

