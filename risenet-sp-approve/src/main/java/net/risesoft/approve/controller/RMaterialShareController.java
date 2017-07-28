package net.risesoft.approve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.service.XzqlXzspListsService;
import net.risesoft.approve.service.impl.BusinessTypeServiceImpl;
import net.risesoft.util.GUID;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/materialshareXzql")
public class RMaterialShareController {
	
	@Autowired
	private XzqlXzspListsService xzqlXzspListsService;
	@Autowired
	public BusinessTypeServiceImpl businessService;
	
	@RequestMapping("/relationmaterial")
	public String index(){		
		return "/materialshare/relationmaterial";		
	}
	@RequestMapping(value="/grid",method = RequestMethod.POST)
	@ResponseBody
	public String grid(HttpServletRequest req){
		String itemid  = req.getParameter("itemid");
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> list = xzqlXzspListsService.findById(itemid);
		map.put("rows",list);
		JSONObject json=JSONObject.fromObject(map);
		return json.toString();
	}
	@RequestMapping(value="/table",method = RequestMethod.POST)
	@ResponseBody
	public String table(HttpServletRequest req){
		String itemid  = req.getParameter("itemid");
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> list = xzqlXzspListsService.findById(itemid);
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest req){
		String id  = req.getParameter("id");
		Map<String,Object> map=new HashMap<String,Object>();
		int i = xzqlXzspListsService.deleteById(id);
		String msg = "";
		if(i>0){
			msg="删除成功！";
		}else{
			msg="删除失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("msg", msg);
		return json.toString();
	}
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest req){
		String MATERIALLX = req.getParameter("MATERIALLX");
		String MATERIALNAME = req.getParameter("MATERIALNAME");
		String DESCRIBE = req.getParameter("DESCRIBE");
		String ORDERNO = req.getParameter("ORDERNO");
		String id = new GUID().toString();
		/*int i = xzqlXzspListsService.add(id, doctypeguid, infocode, infomemo, glguid);
		String msg = "";
		if(i>0){
			msg="添加成功！";
		}else{
			msg="添加失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("id", guid);
		json.put("msg", msg);*/
		//return json.toString();
		return "";
	}
}
