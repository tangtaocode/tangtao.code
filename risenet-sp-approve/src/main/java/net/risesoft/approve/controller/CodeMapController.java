package net.risesoft.approve.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.approve.service.CodeMapService;
import net.risesoft.approve.service.chart.ChartService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;

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
@RequestMapping(value = "/codeMap")
public class CodeMapController {
	
	protected Logger log=LoggerFactory.getLogger(CodeMapController.class);

	
	@Resource(name="codeMapUtil")
	private CodeMapService codeMapUtil;
	
	
	
	//季度业务统计趋势图
	@RiseLog(operateName = "打开季度业务统计趋势图",operateType = "查看")
	@RequestMapping(value = "/dataSource",method = RequestMethod.GET)
	@ResponseBody
	public String dataSource() {
		try{
			List<Map<String,Object>> listMap = codeMapUtil.getDataSourceList("对接系统");
			List<Map<String,Object>> listtemp = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:listMap){
				Map<String,Object> maptemp=new HashMap<String, Object>();
				maptemp.put("key", map.get("KEY"));
				maptemp.put("value", map.get("VALUE"));
				listtemp.add(maptemp);
			}
			JSONArray json = JSONArray.fromObject(listtemp);
			//map.put("list", json.toString());
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
	    return null;  
	}
	
}
