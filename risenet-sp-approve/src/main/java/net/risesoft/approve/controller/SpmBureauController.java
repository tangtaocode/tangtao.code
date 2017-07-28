
package net.risesoft.approve.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.SpmBureauService;
import net.risesoft.approve.service.XzqlXzspListTypeService;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.service.SpProcessService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.sun.jdi.Method;

@Controller
@RequestMapping("/bureau")
public class SpmBureauController {
	protected Logger log = LoggerFactory.getLogger(SpmBureauController.class);
	@Autowired
	private SpmBureauService spmBureauService;
	@Autowired
	private XzqlXzspListTypeService xzqlXzspListService;
	@Autowired
	private SpmApproveItemService spmApproveItemService;
	@Resource(name="spProcessService")
	private SpProcessService spProcessService;
	
	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;
	//跳转到委办局事项首页
	@RiseLog(operateName = "打开委办局事项首页",operateType = "查看")
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("Workflow_GUID","");
		model.addAttribute("bos","0");
		model.addAttribute("isProject","0");
		model.addAttribute("isExternal","0");
		return "/bureau/bureauList";
	}
	
	//跳转到街道事项首页
	@RiseLog(operateName = "打开街道事项首页",operateType = "查看")
	@RequestMapping(value="/streetindex",method=RequestMethod.GET)
	public String streetindex(Model model){
		model.addAttribute("Workflow_GUID","");
		model.addAttribute("bos","0");
		model.addAttribute("isProject","0");
		model.addAttribute("isExternal","0");
		return "/bureau/bureauStreetList";
	}
	
	@RiseLog(operateName = "打开流程时限配置页面",operateType = "查看")
	@ResponseBody
	@RequestMapping(value="/processDefineId")
	public Map<String,Object> getProcessDefineIdByApproveGuid(String approveItemGuid){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			SpmApproveitem  spmApproveitem=spmApproveItemService.findByApproveitemguid(approveItemGuid);
			if(null!=spmApproveitem&&spmApproveitem.getWorkflowguid()!=null){
				String processDefineId=spmApproveitem.getWorkflowguid();
				String processDefineKey=processDefineId.split(SysVariables.COLON)[0];
				map.put("success", true);
				map.put("processDefineId",processDefineId);
				map.put("processDefineKey",processDefineKey);
				map.put("processDefineName",workflowProcessDefinitionService.getLatestProcessDefinition(processDefineKey).getName());
			}else{
				map.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//加载委办局
	@RiseLog(operateName = "加载委办局",operateType = "查看")
	@RequestMapping(value="/wbjSelect",method = RequestMethod.GET)
	@ResponseBody
	public String wbjSelect(){
		List<Map<String,Object>> spmList = spmBureauService.loadDepartMent("0", "0");
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
	

	//加载工作流
	@RiseLog(operateName = "获取流程定义",operateType = "查看")
	@RequestMapping(value="/workFlowSelect",method = RequestMethod.GET)
	@ResponseBody
	public String workFlowSelect(){
		List<Map<String,String>> spmList = spProcessService.processList();
		List<Map<String,Object>> listtemp=new ArrayList<Map<String,Object>>();
		for(Map<String,String> map:spmList){
			Map<String,Object> maptemp=new HashMap<String, Object>();
			maptemp.put("key", map.get("name"));
			maptemp.put("value", map.get("key"));
			listtemp.add(maptemp);
		}
		JSONArray json = JSONArray.fromObject(listtemp);
		//map.put("list", json.toString());
		return json.toString();
	}
		
	/**
	 * 获取委办局事项列表
	 * @param bureauGUID 委办局
	 * @param approveItemName 事项名称
	 * @param approveItemType 事项类型
	 * @param approveItemStatus 事项状态
	 * @param pageNum 页数
	 * @param pageSize 每页显示
	 * @return
	 */
	@RiseLog(operateName = "获取委办局事项列表",operateType = "查看")
	@RequestMapping(value="/wbjList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> wbjList(String bureauGUID,String approveItemName,String approveItemType,String approveItemStatus,HttpServletRequest request){
		Map<String,Object> map=new HashMap<String,Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		try{
			if(StringX.isBlank(pageSize) || StringX.isBlank(pageNo)){
				pageSize="20";
				pageNo = "1";
			}
			Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
			pager = spmBureauService.findAll(approveItemType,bureauGUID,approveItemName,approveItemStatus,pager);
			List<Map<String,Object>> spmList =  pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	
	
	//获取街道事项列表
	@RiseLog(operateName = "获取街道事项列表",operateType = "查看")
	@RequestMapping(value="/streetList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> streetList(String bureauGUID,String approveItemName,String approveItemType,String approveItemStatus,HttpServletRequest request){
		Map<String,Object> map=new HashMap<String,Object>();
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		try{
			if(StringX.isBlank(pageSize) || StringX.isBlank(pageNo)){
				pageSize="20";
				pageNo = "1";
			}
			Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
			pager = spmBureauService.findAll(approveItemType,bureauGUID,approveItemName,approveItemStatus,pager);
			List<Map<String,Object>> spmList =  pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	//加街道事项的"全部下来列表"
	@RiseLog(operateName = "获取街道事项下拉列表",operateType = "查看")
	@RequestMapping(value="/streetSelect",method = RequestMethod.GET)
	@ResponseBody
	public String streetSelect(){
		List<Map<String,Object>> spmList = spmBureauService.loadDepartMent("1", "0");
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
	
	//流程管理
	@RiseLog(operateName = "获取街道事项下拉列表",operateType = "查看")
	@RequestMapping(value="/lcgl")
	public String lcgl(String approveGuid,Model model){
		try{
			SpmApproveitem approve = spmApproveItemService.findByApproveitemguid(approveGuid);
			model.addAttribute("approveItem",approve);
			return "/bureau/bandWorkflow";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/500";
	}
	
	//事项绑定流程
	@RiseLog(operateName = "保存事项绑定流程",operateType = "增加")
	@RequestMapping(value="/bandWorkflow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> bandWorkflow(SpmApproveitem approveitem){
		String workflowguid = approveitem.getWorkflowguid();
		String message="";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			int res = spmBureauService.bandWorkFlow(workflowguid, approveitem.getApproveitemguid(),approveitem.getSealType());
			if(res>=1){
				message = "保存成功！";
			}else{
				message = "保存失败！";
			}
		}catch(Exception e){
			message = "保存失败！";
			e.printStackTrace();
			model.put("message", message);
		}
		model.put("approveItem", approveitem);
		model.put("message", message);
		return model;
	}
	
	
	//表单与事项进行绑定
	@RiseLog(operateName = "打开表单与事项绑定页面",operateType = "查看")
	@RequestMapping(value="/eformbound")
	public String eformbound(String approveitemguid,Model model){
		//通过页面传来的guid去查找对应的事项
				SpmApproveitem approve = spmApproveItemService.findByApproveitemguid(approveitemguid);
				//根据页面传来的事项id去找到相应的材料类型、以及材料类型的gudi
				List<Map<String,Object>> list=xzqlXzspListService.find(approveitemguid);
				model.addAttribute("list", list);
				model.addAttribute("approve",approve);
				return "/bureau/eformitem";
	}
	
	//根据用户输入的表名称进行搜索
	@RiseLog(operateName = "搜索表单",operateType = "查看")
	@RequestMapping(value="/eformfind",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> find(String eformname,HttpServletRequest request){
		String pageNo = request.getParameter("pager.pageNo");
		String pageSize = request.getParameter("pager.pageSize");
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			if(StringX.isBlank(pageSize) || StringX.isBlank(pageNo)){
				pageSize="20";
				pageNo = "1";
			}
			Pager pager = new Pager(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
			pager = spmBureauService.eformfindAll(eformname, pager);
			List<Map<String,Object>> spmList =  pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", spmList);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
		}				
		return map;		
		}
	//通过类型id去查找相应的材料
	@RiseLog(operateName = "保存表单与事项绑定信息",operateType = "增加")
	@RequestMapping(value="/correspond",method=RequestMethod.POST)
	@ResponseBody
    public String correspond(String listguid){
		List<Map<String,Object>> list=xzqlXzspListService.findform(listguid);
		//该类型没有表单
			List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:list){
				String key = map.get("key").toString();
				String value = map.get("value").toString();
				map.put("key", value);
				map.put("value", key);
				resList.add(map);
			}
			JSONArray jsonArr = JSONArray.fromObject(resList);
			//采用的是QUI中的json格式
			String result = "{\"list\":"+jsonArr.toString()+"}";			
			return result;		
	}
	
	
	@RiseLog(operateName = "保存表单与事项绑定信息",operateType = "增加")
	@RequestMapping(value="/eformmatches",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> bound(String tempid,String templatename,String approveitemguid,String typename,String typeguid,String accessmethod,String listguid){
		Map<String,Object> map=new HashMap<String, Object>();
		String guid=new GUID().toString();
		String url="/engine/gettemplate.jsp?temp_Id="+tempid;
		String message="";
		//通过页面传来的类型名称以及事项名称去找到相应的事项id并插入到PROCEEDINGFORMS中
		try{
			//绑定之前先判断之前是否有绑定，如之前已绑定则进行更新操作
			//spmBureauService.find(url, typeguid, approveitemguid);			
			int isresult=spmBureauService.insert(guid,approveitemguid,templatename,url,typename,typeguid,accessmethod,listguid);
			if(isresult==1){
				message="插入成功";
			}
		}catch(Exception e){
			e.printStackTrace();
				message="插入失败";
		}
			map.put("message", message);		
			return map;
	}
		
	
}
