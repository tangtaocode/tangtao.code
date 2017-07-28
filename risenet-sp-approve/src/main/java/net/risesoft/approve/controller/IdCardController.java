package net.risesoft.approve.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.utilx.MinzuUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/idcard")
public class IdCardController{
	//身份证首页
	@RiseLog(operateName = "打开身份证首页",operateType = "查看")
	@RequestMapping(value="/index")
	public String index()
	{
		return "/idcard/newidcard";
	}
	
	@RiseLog(operateName = "获取身份证信息",operateType = "查看")
	@RequestMapping(value="/find",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> find(HttpServletRequest request, Model model)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		String nation = request.getParameter("nation");
		int id = Integer.parseInt(nation);
		String name = MinzuUtil.getName(id);
		map.put("name", name);
		return map;
	}
}