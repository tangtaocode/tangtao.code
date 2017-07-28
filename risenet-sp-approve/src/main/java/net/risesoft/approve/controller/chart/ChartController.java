package net.risesoft.approve.controller.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.CodeMapService;
import net.risesoft.approve.service.chart.ChartService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;

import org.apache.commons.collections.map.LinkedMap;
import org.hibernate.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/chart")
public class ChartController {
	
	protected Logger log=LoggerFactory.getLogger(ChartController.class);
	
	@Autowired
	private ChartService chartService;
	
	@Resource(name="codeMapUtil")
	private CodeMapService codeMapUtil;
	
	//审批业务量统计
	@RequestMapping(value = "/bureauChart")
	public String index() {
		return "/echarts/businessList";
	}
	
	//部门业务统计 柱状图
	@RiseLog(operateName = "部门业务统计",operateType = "查看")
	@RequestMapping(value = "/bar",method = RequestMethod.GET)
	public String barChart(Model model,String year,String quart,String month) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> list = chartService.bureauChartGrahpic(year,quart,month);
		//局名称
		List<String> categories = new ArrayList<String>();
		//业务量
		List<String> values = new ArrayList<String>();
		/*if(StringX.isBlank(year)){
			//获取当前年份
			Date date = new Date();
			year = (Integer.parseInt(sdf.format(date))-1)+"";
		}*/
		for(Map<String,Object> str:list){
			//循环遍历list讲数据填充到横纵坐标
			categories.add(str.get("BUREAUNAME").toString());
			values.add(str.get("YWL").toString());
		}
		JSONArray Jsoncategories  = JSONArray.fromObject(categories);
		JSONArray jsonvalues = JSONArray.fromObject(values);
		model.addAttribute("categories", Jsoncategories);
		model.addAttribute("values", jsonvalues);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quart", quart);
	    return "/echarts/datagram";  
	}

	//按季度统计各部门业务量 柱状图
	@RiseLog(operateName = "部门季度业务量统计",operateType = "查看")
	@RequestMapping(value = "/byQuart",method = RequestMethod.GET)
	public String byQuartChart(Model model,String quart,String year,String month) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> list = chartService.bureauChartGrahpic(year,quart,month);
		//局名称
		List<String> categories = new ArrayList<String>();
		//业务量
		List<String> values = new ArrayList<String>(); 
		/*if(StringX.isBlank(year)){
			//获取当前年份
			Date date = new Date();
			year = (Integer.parseInt(sdf.format(date))-1)+"";
		}*/
		for(Map<String,Object> str:list){
			//循环遍历list讲数据填充到横纵坐标
			categories.add(str.get("BUREAUNAME").toString());
			values.add(str.get("YWL").toString());
		}
		JSONArray Jsoncategories  = JSONArray.fromObject(categories);
		JSONArray jsonvalues = JSONArray.fromObject(values);
		model.addAttribute("categories", Jsoncategories);
		model.addAttribute("values", jsonvalues);
		model.addAttribute("year", year);
		model.addAttribute("quart", StringX.getNumQuart(quart));
		model.addAttribute("type", "quart");
	    return "/echarts/datagram";  
	}
	//满意率饼状图
	@RiseLog(operateName = "满意率统计",operateType = "查看")
	@RequestMapping(value = "/pie",method = RequestMethod.GET)
	public String senatePie(Model model,String bureau,String quart,String year,String month) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		try{
		List<Map<String,Object>> list = chartService.senatePieList(bureau,year,quart,month);
		//业务量
		List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();

		for(Map<String,Object> str:list){
			Map<String,Object> bestMap = new HashMap<String,Object>();
			bestMap.put("value", StringX.getNullString(str.get("bestnum")));
			bestMap.put("name", "满意");
			values.add(bestMap);
			Map<String,Object> goodMap = new HashMap<String,Object>();
			goodMap.put("value", StringX.getNullString(str.get("goodnum")));
			goodMap.put("name", "一般");
			values.add(goodMap);
			Map<String,Object> badMap = new HashMap<String,Object>();
			badMap.put("value", StringX.getNullString(str.get("badnum")));
			badMap.put("name", "不满意");
			values.add(badMap);
		}
		JSONArray jsonvalues = JSONArray.fromObject(values);
		model.addAttribute("values", jsonvalues);
		model.addAttribute("year", year);
		model.addAttribute("quart", StringX.getNumQuart(quart));
		model.addAttribute("type", "quart");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	    return "/echarts/piegram";  
	}
	@RequestMapping(value = "/businessList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bureauChartList(String bureau,String year,String quart,String month,HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		try {
			if(StringX.isBlank(pageSize) || StringX.isBlank(pageNo)){
				pageSize="20";
				pageNo = "1";
			}
			Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
			pager = chartService.bureauChartList(bureau, year,quart,month,pager);
			List<Map<String,Object>> bureauChartList =  pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", bureauChartList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//季度业务统计趋势图
	@RequestMapping(value = "/line",method = RequestMethod.GET)
	public String lineChart(Model model,String dataSource,String year,String type) {
		try{
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			//网上办理率统计
			if(!StringX.isBlank(type) && "online".equals(type)){
				list = chartService.doOnlineData(dataSource,year);
			}else{
				//业务量统计
				list = chartService.quarterlyData(dataSource,year);
			}
			//季度
			List<String> categories = new ArrayList<String>();
			//数据
			List<String> data = new ArrayList<String>();
			
			for(Map<String,Object> str:list){
				//循环遍历list讲数据填充到横纵坐标
				data.add(str.get("SPRING").toString());
				data.add(str.get("SUMMER").toString());
				data.add(str.get("AUTUMN").toString());
				data.add(str.get("WINTER").toString());
				year = str.get("year").toString();
			}
			JSONArray Jsoncategories  = JSONArray.fromObject(categories);
			List<Map<String,Object>> listMap = codeMapUtil.getDataSourceList("对接系统");
			List<Map<String,Object>> listtemp = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:listMap){
				Map<String,Object> maptemp=new HashMap<String, Object>();
				maptemp.put("key", map.get("KEY"));
				maptemp.put("value", map.get("VALUE"));
				listtemp.add(maptemp);
			}
			JSONArray json = JSONArray.fromObject(listtemp);
			model.addAttribute("categories", Jsoncategories);
			model.addAttribute("values", JSONArray.fromObject(data));
			model.addAttribute("dataSource",dataSource);
			model.addAttribute("type",type);
			model.addAttribute("year",year);
			model.addAttribute("list", json.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	    return "/echarts/linegram";  
	}
	
}
