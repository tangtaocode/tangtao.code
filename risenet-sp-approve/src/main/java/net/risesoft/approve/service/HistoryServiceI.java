package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.model.Person;

public interface HistoryServiceI {

	
	/*
	 * 查询历史档案
	 */
	public Pager getPage(String applyName, String approveName,Pager pager,String yujing);
	
	public List<Map<String,Object>> getZhengzhaoByInstanceid(String instanceId, String declareannexguid);
	/*
	 * 证照资料查询
	 */
	public Pager selectPage(String applyName, String approveName, Pager pager,String serviceCode);
	
	
	/*
	 * 查找证照扫描信息
	 */
	public List<Map<String, Object>> getImage(String instanceGuid, Person person) ;
	
	
	/*
	 * 查找证照扫描图片信息
	 */
	public String getPhoto(String instanceGuid, HttpServletResponse rep);
	
	
	/*
	 * 查询证照信息统计分析按局统计
	 */
	public Pager getPageZzTongJi(Person person,Pager pager,String produceOrgan,String docTypeName,String nianfen);
	
	/*
	 * 查询证照信息统计分析按事项统计
	 */
	public Pager getPageZzTongJitwo(Person person,Pager pager,String produceOrgan,String docTypeName,String nianfen);
	
	/*
	 *查询统计集合
	 */
	public List<Map<String,Object>> getTongJiList(String sysnianfen);
	
	
	/*
	 * 得到需要导出的List
	 */
	public List<Map<String,Object>> getDaoChuList(String produceOrgan,String docTypeName,String nianfen,Person person);
	
}
