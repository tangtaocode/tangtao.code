package net.risesoft.approve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.jpa.TBdexDocTypeInfo;
import net.risesoft.approve.entity.jpa.TBdexDocTypeInfoModel;
import net.risesoft.approve.entity.jpa.TBdexDoctype;
import net.risesoft.approve.service.BdexDocTypeInfoService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.util.GUID;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

@Controller
@RequestMapping("/doctypeinfo")
public class DocTypeInfoController {
	@Autowired
	private BdexDocTypeInfoService bdexDocTypeInfoService;
	
	/*
	 * 跳转证照信息管理页面
	 * 
	 */
	@RiseLog(operateName = "打开证照信息管理页面",operateType = "查看")
	@RequestMapping("/findDocTypeInfo")
	public ModelAndView index(String guid,String docname){
		ModelAndView mav=new ModelAndView("businessLicensesManger/docTypeInfo");
		mav.addObject("guid",guid);
		mav.addObject("docname",docname);
		return mav;
	}
	
	/*
	 * 查询证照信息列表
	 * 
	 */
	@RiseLog(operateName = "查询证照信息列表",operateType = "查看")
	@RequestMapping(value="/findAllByGuid",method = RequestMethod.POST)
	@ResponseBody
	public String findAllByGuid(HttpServletRequest req){
		String docGuid  = req.getParameter("docGuid");
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> list = bdexDocTypeInfoService.findById(docGuid);
		map.put("rows",list);
		JSONObject json=JSONObject.fromObject(map);
		return json.toString();
	}
	
	/*
	 * 删除证照信息
	 * 
	 */
	@RiseLog(operateName = "删除证照信息",operateType = "删除")
	@RequestMapping(value="/deleteDoc",method = RequestMethod.POST)
	@ResponseBody
	public String deleteDoc(HttpServletRequest req){
		String guid = req.getParameter("guid");
		int i = bdexDocTypeInfoService.deleteDocType(guid);
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
	
	/*
	 * 修改证照信息
	 * 
	 */
	@RiseLog(operateName = "修改证照信息",operateType = "修改")
	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	public String update(HttpServletRequest req){
		String guid = req.getParameter("guid");
		String infocode = req.getParameter("infocode");
		String infomemo = req.getParameter("infomemo");
		String glguid = req.getParameter("glguid");
		int i = bdexDocTypeInfoService.update(guid, infocode, infomemo, glguid);
		String msg = "";
		if(i>0){
			msg="修改成功！";
		}else{
			msg="修改失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("id", i);
		json.put("msg", msg);
		return json.toString();
	}
	
	
	/*
	 * 新建证照信息
	 * 
	 */
	@RiseLog(operateName = "新建证照信息",operateType = "增加")
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest req){
		String infocode = req.getParameter("infocode");
		String infomemo = req.getParameter("infomemo");
		String glguid = req.getParameter("glguid");
		String doctypeguid = req.getParameter("doctypeguid");
		String guid = new GUID().toString();
		int i = bdexDocTypeInfoService.insert(guid, doctypeguid, infocode, infomemo, glguid);
		String msg = "";
		if(i>0){
			msg="添加成功！";
		}else{
			msg="添加失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("id", guid);
		json.put("msg", msg);
		return json.toString();
	}
}
