package net.risesoft.approve.controller;


import java.util.List;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.service.impl.BusinessTypeServiceImpl;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("certmanage")
public class CertManageController {

	@Autowired
	public BusinessTypeServiceImpl businessService;
	
	@RequestMapping(value = "/buraecode", method = RequestMethod.GET)
	public String certShow(){
		return "certmanage/buraecode";
	}
	
	
	
	@RequestMapping(value = "/certinfo", method = RequestMethod.GET)
	public String certInfo(){
		return "certmanage/certinfo";
	}
	
	@RequestMapping(value = "/businessType", method = RequestMethod.GET)
	public String businessTypeManage() {
		return "certmanage/businessTypeManage";
	}
	@RequestMapping(value = "/window", method = RequestMethod.GET)
	public String goPage(HttpServletRequest req) {
		String itemId=req.getParameter("itemId");
		req.setAttribute("itemId", itemId);
		return "certmanage/windowContent";
	}
	
	@RequestMapping(value="/businessTree",method = RequestMethod.POST)
	@ResponseBody
	public String selectTree(){
		String tree="";
		try{
			tree=businessService.selectTree();
		
		}catch(Exception e){
			String err="";
			err="【CertManageController调用businessService.selectTree()】出错！";
			tree="错误";
			System.out.println(err);
			e.printStackTrace();
		}
		return tree;
	}
	/**
	 * 选中左边的业务类型  会根据业务类型的id和部门的id找出唯一的数据填在右边的form表中
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/createForm",method = RequestMethod.POST)
	@ResponseBody
	public String selectDept(HttpServletRequest req){
		String deptId=req.getParameter("deptId");
		String docutypeGid=req.getParameter("docutypeGid");
		List o=businessService.getDeptInfo(deptId,docutypeGid);
		String  t=o.get(0).toString().replaceAll("=", ":");
		JSONObject  json= new JSONObject();
		json.put("form", t);
		return json.toString();
	}

	
	/**
	 * 保存材料名称
	 */
	@RequestMapping(value="/saveMaterial",method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest req){
		Person person  =  ThreadLocalHolder.getPerson();
		String doctypeguid=req.getParameter("itemId");//事项ItemId
		String name=req.getParameter("name");//新增的材料名称
		String materialDis=req.getParameter("materialDis");//新增的材料描述
		String flag=businessService.save(name,doctypeguid,person,materialDis);
		JSONObject returnData=new JSONObject();
		if("SUCCESS".equals(flag)){
			returnData.put("info","保存成功");
		}else{
			returnData.put("info","保存失败");
		}
		return returnData.toString();
	}
}
