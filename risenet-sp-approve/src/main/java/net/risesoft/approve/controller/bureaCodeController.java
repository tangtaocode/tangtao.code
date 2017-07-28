package net.risesoft.approve.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.BdexDocInfoService;
import net.risesoft.approve.service.BureaCodeService;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/bureaCode")
public class bureaCodeController {
	protected Logger log = LoggerFactory.getLogger(bureaCodeController.class);
	@Autowired
	private BureaCodeService bureaCodeService;
	@Autowired
	private BdexDocInfoService bdexDocInfoService;
	
	
	@RequestMapping("/findAllbureaCode")
	public String index(){		
		return "businessLicensesManger/findAllbureaCode";  	
	}
	
	
	/**
	 * 跳转到组织机构代码页面
	 */
	@RiseLog(operateName = "打开组织机构代码页面",operateType = "查看")
	@RequestMapping(value = "/bureaCodeinfo", method = RequestMethod.GET)
	public String materialShow(){
		return "businessLicensesManger/bureaCodeinfo";
	}
	
	
	
	/**
	 * 组织机构代码显示列表
	 */
	@RiseLog(operateName = "获取组织机构代码列表",operateType = "查看")
	@RequestMapping(value = "/bureaCodeList")
	@ResponseBody
	public Map<String, Object> bureaCodeList(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String BUREANAME = request.getParameter("BUREANAME");
		String BUREACODE = request.getParameter("BUREACODE");
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		try {
			if (StringX.isBlank(pageSize) || StringX.isBlank(pageNo)) {
				pageSize = "20";
				pageNo = "1";
			}
			Pager pager = new Pager();
			pager.setPageNo(Integer.parseInt(pageNo));
			pager.setPageSize(Integer.parseInt(pageSize));
			Person person = ThreadLocalHolder.getPerson();
			pager = bureaCodeService.findByUserID(person, BUREANAME,BUREACODE, pager);
			List<Map<String, Object>> spmList = pager.getPageList();
			
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	
	/**
	 * * 
	 * @param  修改组织机构代码
	 * @return
	 */
	@RiseLog(operateName = "修改组织机构代码",operateType = "修改")
	@RequestMapping(value="/bureacodeupdata")
	@ResponseBody
	public String update(HttpServletRequest req){
		String BUREACODE = req.getParameter("BUREACODE");
		String RC8_DEPARTMENT_ID = req.getParameter("RC8_DEPARTMENT_ID");
		String BUREAGUID = req.getParameter("BUREAGUID");
		int i = bureaCodeService.update(BUREAGUID, BUREACODE, RC8_DEPARTMENT_ID);
		String msg = "";
		if(i>0){
			msg="修改成功！";
		}else{
			msg="修改失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("BUREAGUID", BUREAGUID);
		json.put("msg", msg);
		return json.toString();
	}
	
	
	/**
	 * 跳转到证照共享树页面
	 * 
	 */
	@RiseLog(operateName = "打开证照共享树页面",operateType = "查看")
	@RequestMapping("/depttree")
	public ModelAndView depttree(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/businessLicensesManger/depttree");
		mav.addObject("ID",request.getParameter("ID"));
		return mav;
	}
	
	/**
	 * rc8部门表树单选框列表
	 * 
	 */
	@RiseLog(operateName = "获取rc8部门表树单选框列表",operateType = "查看")
	@RequestMapping(value="/rc8depttreelist",method = RequestMethod.POST)
	@ResponseBody
	public String selectTree(){
		String tree="";
		try{
			tree=bureaCodeService.finddeptTree();
		
		}catch(Exception e){
			String err="";
			err="【调用businessService.selectTree()】出错！";
			tree="错误";
			System.out.println(err);
			e.printStackTrace();
		}
		return tree;
	}
	
	
	/**
	 * 关联证照rc8部门ID
	 * 
	 */
	@RiseLog(operateName = "关联证照rc8部门ID",operateType = "增加")
	@RequestMapping("/savedeptinfo")
	@ResponseBody
	public String savedeptinfo(HttpServletRequest req){
		String ID = req.getParameter("ID");
		String ALLNAME = req.getParameter("ALLNAME");
		String ALLID = req.getParameter("ALLID");
		int i = bureaCodeService.savedept(ID, ALLNAME, ALLID);
		String msg = "";
		if(i>0){
			msg="关联成功！";
		}else{
			msg="提交成功！";
		}
		JSONObject  json= new JSONObject();
		json.put("i", i);
		json.put("msg", msg);
		return json.toString();
	}

	/**
	 * * 
	 * @param  新增组织机构代码
	 * @return
	 */
	@RiseLog(operateName = "新增组织机构代码",operateType = "增加")
	@RequestMapping(value="/materialsave",method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest req){
		String ID = new GUID().toString();
		String CODE = req.getParameter("CODE");
		String STUFFDEFINENAME = req.getParameter("STUFFDEFINENAME");
		String ISCHECKVALID = req.getParameter("ISCHECKVALID");
		String TABINDEX = req.getParameter("TABINDEX");
		String STATE = req.getParameter("STATE");
		String DEFINETIME = req.getParameter("DEFINETIME");
		int i = bureaCodeService.insert(ID,CODE, STUFFDEFINENAME);
		boolean success = false;
		if(i>0){
			success = true;
		}
		JSONObject  json= new JSONObject();
		json.put("STUFFDEFINEID", ID);
		json.put("success", success);
		return json.toString();
	}
	
	
	/**
	 * 材料删除
	 */
	@RiseLog(operateName = "删除材料",operateType = "删除")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String materialdel(HttpServletRequest req){
		String BUREAGUID = req.getParameter("BUREAGUID");
		int i = bureaCodeService.delete(BUREAGUID);
		boolean success = false;
		if(i>0){
			success = true;
		}
		JSONObject  json= new JSONObject();
		json.put("success", success);
		return json.toString();
	}
	
	
	
	/**
	 * * 
	 * @param  查询组织机构列表
	 * @return
	 */
	@RiseLog(operateName = "查询组织机构列表",operateType = "查看")
	@RequestMapping(value="/bcjSelect",method = RequestMethod.GET)
	@ResponseBody
	public String bcjSelect(){
		List<Map<String,Object>> spmList = bureaCodeService.findAll();
		List<Map<String,Object>> listtemp=new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : spmList) {
			Map<String,Object> maptemp=new HashMap<String, Object>();
			//String name = bdexDocInfoService.findById(map.get("bureaguid").toString());
			//maptemp.put("key",bdexDocinfo.getDocname());
			maptemp.put("guid",map.get("BUREAGUID"));
			maptemp.put("DEPARTMENT_NAME",map.get("DEPARTMENT_NAME"));
			maptemp.put("value", map.get("BUREACODE")==null?"":map.get("BUREACODE"));
			listtemp.add(maptemp);
		}
		JSONArray json = JSONArray.fromObject(listtemp);
		return json.toString();
	}
	
	/**
	 * * 
	 * @param  修改组织机构代码
	 * @return
	 */
	/*
	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest req){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		String code=req.getParameter("code");
		if(code.length()>0 && code !=null){
			JSONObject jsonObject = JSONObject.fromObject(code);
		    Iterator it = jsonObject.keys();  
		       // 遍历jsonObject数据，添加到Map对象  
		       while (it.hasNext())  
		       {  
		           String key = String.valueOf(it.next());  
		           String value = (String) jsonObject.get(key);
		           bureaCodeService.update(value, key);
		       } 
		}else{
			map.put("success", false);
		}
		return map;
	}*/
}
