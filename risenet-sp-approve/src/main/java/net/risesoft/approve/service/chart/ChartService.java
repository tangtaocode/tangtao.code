package net.risesoft.approve.service.chart;
import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.base.Pager;

public interface ChartService {
	/**
	 * 部门业务统计列表
	 * @param bureau 部门
	 * @param year 年度
	 * @param month 月份
	 * @param quart 季度
	 * @return
	 */
	public Pager bureauChartList(String bureau,String year,String quart,String month,Pager pager);
	
	/**
	 * 评价率
	 * @param bureau 部门
	 * @param year 年度
	 * @param month 月份
	 * @param quart 季度
	 * @return
	 */
	public List<Map<String,Object>> senatePieList(String bureau,String year,String quart,String month);
	
	/*
	 * 在办件、办结件统计
	 */
	public List<Map<String,Object>> todoAndBanjieList(String bureau,String year,String quart,String month);
	/**
	 * 柱状图
	 * @param bureau
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String,Object>> bureauChartGrahpic(String year,String quart,String month);
	
	/**
	 * 获取季度业务量统计
	 * @return
	 */
	public List<Map<String,Object>> quarterlyData(String depart,String year);
	
	//获取网上办理率统计数据
	public List<Map<String, Object>> doOnlineData(String depart,String year);
	
	//获取办结率统计数据
	public List<Map<String, Object>> banjieLvData(String bureau, String year,String quart, String month);
	
}
