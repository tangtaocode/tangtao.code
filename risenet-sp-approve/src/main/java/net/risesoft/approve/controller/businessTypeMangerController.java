package net.risesoft.approve.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.BdexDocTypeInfoService;
import net.risesoft.approve.service.BdocTypeService;
import net.risesoft.approve.service.RisenetDepartmentService;
import net.risesoft.tenant.annotation.RiseLog;
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

@Controller
@RequestMapping("/businesstypemanger")
public class businessTypeMangerController {
	protected Logger log = LoggerFactory.getLogger(businessTypeMangerController.class);
	@Autowired
	private RisenetDepartmentService departmentService;
	@Autowired 
	private BdocTypeService bdocTypeService;
	
	
	/*
	 * 证照类型管理页面
	 */
	@RiseLog(operateName = "打开证照类型管理页面",operateType = "查看")
	@RequestMapping("/findDocType")
	public String index(){		
		return "businessLicensesManger/blTypeManger";	  	
	}

	/*
	 * 加载出证部门
	 * 
	 */
	@RiseLog(operateName = "获取出证部门",operateType = "查看")
	@RequestMapping(value="/wbjSelect",method = RequestMethod.GET)
	@ResponseBody
	public String wbjSelect(){
		List<Map<String,Object>> spmList = departmentService.findAll();
		List<Map<String,Object>> listtemp=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:spmList){
			Map<String,Object> maptemp=new HashMap<String, Object>();
			maptemp.put("key", map.get("KEY"));
			maptemp.put("value", map.get("VALUE"));
			listtemp.add(maptemp);
		}
		JSONArray json = JSONArray.fromObject(listtemp);
		//map.put("list", json.toString());
		return json.toString();
	}
	
	
	/*
	 * 证照类型管理列表
	 */
	@RiseLog(operateName = "获取证照类型管理列表",operateType = "查看")
	@RequestMapping(value="/wbjList",method = RequestMethod.POST)
	@ResponseBody
	public String wbjList(HttpServletRequest req){
		String departmentGuid  = req.getParameter("departmentGuid");
		String departmentName = req.getParameter("departmentName");
		String pageNo = req.getParameter("pager.pageNo");
		String pageSize = req.getParameter("pager.pageSize");
		Map<String,Object> map=new HashMap<String,Object>();
		JSONObject json=null;
		try{
			if(StringX.isBlank(pageSize) || StringX.isBlank(pageNo)){
				pageSize="20";
				pageNo = "1";
			}
			Pager pager=new Pager();
			pager.setPageNo(Integer.parseInt(pageNo));
			pager.setPageSize(Integer.parseInt(pageSize));
			pager = bdocTypeService.findAll(departmentGuid,departmentName,pager);
			List<Map<String,Object>> spmList =  pager.getPageList();
			/*for (Map<String, Object> map2 : spmList) {
				System.out.println(map2);
			}*/
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			
			map.put("rows",spmList);
			json=JSONObject.fromObject(map);
			
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return json.toString();
	}
	
	/*
	 * 跳转至新增证照类型页面
	 *
	 */
	@RiseLog(operateName = "打开新增证照类型页面",operateType = "查看")
	@RequestMapping("/toadd")
	public String toadd(){
		
		return "businessLicensesManger/blTypeForm";	 
	}
	
	
	/*
	 * 新增证照类型
	 *
	 */
	@RiseLog(operateName = "保存新增证照类型",operateType = "增加")
	@RequestMapping(value="/doadd",method = RequestMethod.POST)
	@ResponseBody
	public int doadd(HttpServletRequest req){
		String doctypecode  = req.getParameter("doctypecode");
		String doctypename  = req.getParameter("doctypename");
		String departmentGuid  = req.getParameter("departmentGuid");
		String memo  = req.getParameter("memo");
		String guid = new GUID().toString();
		Date date = new Date();
		int i = bdocTypeService.insert(guid, doctypecode, doctypename, departmentGuid, memo, date);
		return i;
	}
	
	
	/*
	 * 删除证照类型
	 *
	 */
	@RiseLog(operateName = "删除证照类型",operateType = "删除")
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	@ResponseBody
	public int delete(HttpServletRequest req){
		String guids = req.getParameter("guids");
		String[] guid = guids.split(",");
		int j=0;
		for (int i = 0; i < guid.length; i++) {
			 j = bdocTypeService.delete(guid[i]);
		}
		//JSONObject json=JSONObject.fromObject(j);
		return j;
	}
	
	/*
	 * 加载出证部门
	 * 
	 */
	@RiseLog(operateName = "获取出证部门",operateType = "查看")
	@RequestMapping(value="/zztypeSelect",method = RequestMethod.GET)
	@ResponseBody
	public String zztypeSelect(){
		List<Map<String,Object>> spmList = departmentService.findAllZZType();
		List<Map<String,Object>> listtemp=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:spmList){
			Map<String,Object> maptemp=new HashMap<String, Object>();
			maptemp.put("key", map.get("KEY"));
			maptemp.put("value", map.get("VALUE"));
			listtemp.add(maptemp);
		}
		JSONArray json = JSONArray.fromObject(listtemp);
		//map.put("list", json.toString());
		return json.toString();
	}
	
}
