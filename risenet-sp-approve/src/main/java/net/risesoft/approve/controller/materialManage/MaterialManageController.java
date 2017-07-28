package net.risesoft.approve.controller.materialManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.service.CodeMapService;
import net.risesoft.approve.service.materialManage.MaterialManageService;
import net.risesoft.approve.service.WindowApproveService;
import net.risesoft.fileflow.service.WorkflowProcessDefinitionService;
import net.risesoft.fileflow.service.WorkflowProcessInstanceService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/materialManage")
public class MaterialManageController {
	
	protected Logger log=LoggerFactory.getLogger(MaterialManageController.class);
	
	@Autowired
	private WindowApproveService windowapproveservice;

	@Autowired
	private WorkflowProcessInstanceService workflowProcessInstanceService;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Resource(name = "codeMapUtil")
	private CodeMapService codeMapUtil;
	
	@Resource(name="materialManageService")
	private MaterialManageService materialManageService;

	//================================物料管理==============================================
	// 固定资产管理页面
	@RequestMapping(value = "/materialManageIndex")
	public String materialManageIndex() {
		return "/materialManage/materialManageIndex";
	}

	/**
	 * 查询物料列表
	 */
	@RequestMapping(value = "/materialList")
	@ResponseBody
	public Map<String, Object> materialList(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String material_code = request.getParameter("material_code");
		String material_name = request.getParameter("material_name");
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
			pager = materialManageService.materialList(person, material_code, 
					material_name, pager);
			listmap = pager.getPageList();
			
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", listmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	/**
	 * 物料新增页面
	 */
	@RequestMapping(value = "/materialManageAdd", method = RequestMethod.GET)
	public String materialManageAdd(){
		return "materialManage/materialManageAdd";
	}
	
	/**
	 * 物料保存
	 */
	@RequestMapping(value="/materialManageSave",method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest req){
		String ID = new GUID().toString();
		String materialguid = req.getParameter("materialguid");
		String material_code = req.getParameter("material_code");
		String material_name = req.getParameter("material_name");
		String material_num = req.getParameter("material_num");
		String unit = req.getParameter("unit");
		String tabindex = req.getParameter("tabindex");
		Person person = ThreadLocalHolder.getPerson();
		int count =0;
		int i = 0;
		count=materialManageService.getMaterialCount(materialguid);
		if(count<1){
			i= materialManageService.insert(person,materialguid,material_code, material_name, material_num,unit,tabindex);
		}else{
			i=materialManageService.updateMaterialNum(person,materialguid,material_num);
		}
		materialManageService.insertLog(person,material_code,material_name,material_num,unit);
		boolean success = false;
		if(i>0){
			success = true;
		}
		JSONObject  json= new JSONObject();
		json.put("materialguid", ID);
		json.put("success", success);
		return json.toString();
	}
	
	/**
	 * 物料删除
	 */
	@RequestMapping(value = "/materialManageDelete")
	@ResponseBody
	public String delete(HttpServletRequest req){
		String materialguid = req.getParameter("materialguid");
		String material_code = req.getParameter("material_code");
		int i = materialManageService.delete(materialguid);
		boolean success = false;
		if(i>0){
			materialManageService.logDelete(material_code);
			success = true;
		}
		JSONObject  json= new JSONObject();
		json.put("success", success);
		return json.toString();
	}
	
	/**
	 * 物料修改
	 */
	@RequestMapping(value="/materialManageUpdata")
	@ResponseBody
	public String update(HttpServletRequest req){
		String materialguid = req.getParameter("materialguid");
		String material_code = req.getParameter("material_code");
		String material_name = req.getParameter("material_name");
		String material_num = req.getParameter("material_num");
		String unit = req.getParameter("unit");
		String tabindex = req.getParameter("tabindex");
		String definetime = req.getParameter("definetime");
		Person person = ThreadLocalHolder.getPerson();
		int i = materialManageService.update(person,materialguid, material_code, material_name, material_num,unit,tabindex,definetime);
		String msg = "";
		if(i>0){
			msg="修改成功！";
		}else{
			msg="修改失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("materialguid", materialguid);
		json.put("msg", msg);
		return json.toString();
	}
	
//================================资产维修==============================================	
	//资产领用页面
	@RequestMapping(value = "/materialUsing")
	public String materialManageUsing() {
		return "/materialManage/materialUsingIndex";
	}
	
	/**
	 * 资产领用列表
	 */
	@RequestMapping(value = "/materialUsingList")
	@ResponseBody
	public Map<String, Object> materialUsingList(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String material_code = request.getParameter("material_code");
		String material_name = request.getParameter("material_name");
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
			pager = materialManageService.materialUsingList(person, material_code,material_name, pager);
			listmap = pager.getPageList();
			
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", listmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	/**
	 * 资产领用修改
	 */
	@RequestMapping(value="/materialUsingUpdata")
	@ResponseBody
	public String materialUsingUpdata(HttpServletRequest req){
		String guid = req.getParameter("guid");
		String using_num = req.getParameter("using_num");
		String description = req.getParameter("description");
		String using_name = req.getParameter("using_name");
		Person person = ThreadLocalHolder.getPerson();
		int i = materialManageService.usingUpdate(person,guid, using_num, description, using_name);
		String msg = "";
		if(i>0){
			msg="修改成功！";
		}else{
			msg="修改失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("guid", guid);
		json.put("msg", msg);
		return json.toString();
	}
	
	/**
	 * 资产领用新增页面
	 */
	@RequestMapping(value = "/materialUsingAdd", method = RequestMethod.GET)
	public String materialUsingAdd(){
		return "materialManage/materialUsingAdd";
	}
	
	/**
	 * 初始化资产领用新增页面资产下拉列表
	 */
	@RequestMapping(value = "/getMaterialList", method = RequestMethod.GET)
	@ResponseBody
	public String getMaterialList(){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
		try {
			listmap = materialManageService.getMaterialList();
			for(Map<String,Object> map:listmap){
				Map<String,Object> maptemp=new HashMap<String, Object>();
				maptemp.put("key", map.get("KEY"));
				maptemp.put("value", map.get("VALUE"));
				resultmap.add(maptemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		JSONObject  json= new JSONObject();
		json.put("list", resultmap);
		System.out.println(json.toString());
		return json.toString();
	}
	
	/**
	 * 数据字典产下拉列表
	 */
	@RequestMapping(value = "/getDictionaryList", method = RequestMethod.GET)
	@ResponseBody
	public String getDictionaryList(){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
		try {
			listmap = materialManageService.getDictionaryList();
			for(Map<String,Object> map:listmap){
				Map<String,Object> maptemp=new HashMap<String, Object>();
				maptemp.put("key", map.get("KEY"));
				maptemp.put("value", map.get("VALUE"));
				resultmap.add(maptemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		JSONObject  json= new JSONObject();
		json.put("list", resultmap);
		System.out.println(json.toString());
		return json.toString();
	}
	
	/**
	 * 资产领用保存
	 */
	@RequestMapping(value="/materialUsingSave",method = RequestMethod.POST)
	@ResponseBody
	public String usingSave(HttpServletRequest req){
		String ID = new GUID().toString();
		String materialguid = req.getParameter("materialguid");
		String using_num = req.getParameter("using_num");
		String using_name = req.getParameter("using_name");
		String description = req.getParameter("description");
		Person person = ThreadLocalHolder.getPerson();
		int i = materialManageService.usingInsert(person,ID,materialguid, using_num, using_name,description);
		
		boolean success = false;
		if(i>0){
			materialManageService.updateMaterialNum(person, materialguid, "-"+using_num);
			success = true;
		}
		JSONObject  json= new JSONObject();
		json.put("materialguid", ID);
		json.put("success", success);
		return json.toString();
	}
	
	/**
	 * 领用删除
	 */
	@RequestMapping(value = "/materialUsingDelete")
	@ResponseBody
	public String usingDelete(HttpServletRequest req){
		String guid = req.getParameter("guid");
		int i = materialManageService.usingDelete(guid);
		boolean success = false;
		if(i>0){
			success = true;
		}
		JSONObject  json= new JSONObject();
		json.put("success", success);
		return json.toString();
	}

//================================资产维修==============================================
	//资产维修页面
	@RequestMapping(value = "/materialRepair")
	public String materialManageRepair() {
		return "/materialManage/materialRepairIndex";
	}

	/**
	 * 资产维修记录列表
	 */
	@RequestMapping(value = "/materialRepairList")
	@ResponseBody
	public Map<String, Object> materialRepairList(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String material_code = request.getParameter("material_code");
		String material_name = request.getParameter("material_name");
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
			pager = materialManageService.materialRepairList(person, material_code,material_name, pager);
			listmap = pager.getPageList();
			
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", listmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	/**
	 * 资产维修记录修改
	 */
	@RequestMapping(value="/materialRepairUpdata")
	@ResponseBody
	public String materialRepairUpdata(HttpServletRequest req){
		String guid = req.getParameter("guid");
		String repair_num = req.getParameter("repair_num");
		String repair_funds = req.getParameter("repair_funds");
		String repair_person = req.getParameter("repair_person");
		String repair_reason = req.getParameter("repair_reason");
		String repair_time_start = req.getParameter("repair_time_start");
		String repair_time_done = req.getParameter("repair_time_done");
		String charge_person = req.getParameter("charge_person");
		Person person = ThreadLocalHolder.getPerson();
		int i = materialManageService.repairUpdate(person,guid, repair_num, repair_funds, repair_person,repair_reason,repair_time_start,repair_time_done,charge_person);
		String msg = "";
		if(i>0){
			msg="修改成功！";
		}else{
			msg="修改失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("guid", guid);
		json.put("msg", msg);
		return json.toString();
	}
	
	/**
	 * 资产维修新增页面
	 */
	@RequestMapping(value = "/materialRepairAdd", method = RequestMethod.GET)
	public String materialRepariAdd(HttpServletRequest req){
		Person person = ThreadLocalHolder.getPerson();
		req.setAttribute("create_person", person.getName());
		return "materialManage/materialRepairAdd";
	}
	
	/**
	 * 资产维修保存
	 */
	@RequestMapping(value="/materialRepairSave",method = RequestMethod.POST)
	@ResponseBody
	public String repairSave(HttpServletRequest req){
		String ID = new GUID().toString();
		String materialguid = req.getParameter("materialguid");
		String repair_num = req.getParameter("repair_num");
		String repair_funds = req.getParameter("repair_funds");
		String repair_person = req.getParameter("repair_person");
		String repair_reason = req.getParameter("repair_reason");
		String repair_time_start = req.getParameter("repair_time_start");
		String repair_time_done = req.getParameter("repair_time_done");
		String charge_person = req.getParameter("charge_person");
		Person person = ThreadLocalHolder.getPerson();
		int i = materialManageService.repairInsert(person,ID,materialguid, repair_num, repair_funds,repair_person,repair_reason,repair_time_start,repair_time_done,charge_person);
		boolean success = false;
		if(i>0){
			success = true;
		}
		JSONObject  json= new JSONObject();
		json.put("materialguid", ID);
		json.put("success", success);
		return json.toString();
	}
	
	/**
	 *资产维修记录删除
	 */
	@RequestMapping(value = "/materialRepairDelete")
	@ResponseBody
	public String repairDelete(HttpServletRequest req){
		String guid = req.getParameter("guid");
		int i = materialManageService.repairDelete(guid);
		boolean success = false;
		if(i>0){
			success = true;
		}
		JSONObject  json= new JSONObject();
		json.put("success", success);
		return json.toString();
	}
	
	//综合查询
	@RequestMapping(value = "/materialQuery")
	public String materialQuery() {
		return "/materialManage/materialQueryIndex";
	}
	
	/**
	 * 综合查询
	 */
	@RequestMapping(value = "/materialMixQuery")
	@ResponseBody
	public Map<String, Object> materialMixQuery(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String startTime = request.getParameter("start_time");
		String endTime = request.getParameter("end_time");
		String material_code = request.getParameter("material_code");
		String material_name = request.getParameter("material_name");
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
			pager = materialManageService.materialMixQuery(person,startTime,endTime, material_code,material_name, pager);
			listmap = pager.getPageList();
			
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", listmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;
	}
	
	//================================资产数据字典==============================================
	
	// 固定资产管理页面
		@RequestMapping(value = "/materialDictionaryIndex")
		public String materialDictionaryIndex() {
			return "/materialManage/materialDictionary";
		}
		
		/**
		 * 数组字典查询
		 */
		@RequestMapping(value = "/dictionaryList")
		@ResponseBody
		public Map<String, Object> dictionaryList(HttpServletRequest request) {

			Map<String, Object> map = new HashMap<>();
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			String material_code = request.getParameter("material_code");
			String material_name = request.getParameter("material_name");
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
				pager = materialManageService.dictionaryList(person, material_code, 
						material_name, pager);
				listmap = pager.getPageList();
				
				map.put("pager.pageNo", pager.getPageNo());
				map.put("pageSize", pager.getPageSize());
				map.put("pager.totalRows", pager.getTotalRows());
				map.put("rows", listmap);
			} catch (Exception ex) {
				ex.printStackTrace();
				log.error(ex.getMessage());
			}
			return map;
		}
		
		/**
		 * 数据字典新增页面
		 */
		@RequestMapping(value = "/materialDictionaryAdd", method = RequestMethod.GET)
		public String materialDictionaryAdd(HttpServletRequest req){
			String materialCode="WLBM";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			materialCode+=df.format(new Date());
			req.setAttribute("wlbm", materialCode);
			return "materialManage/materialDictionaryAdd";
		}
		
		/**
		 * 数组字典保存
		 */
		@RequestMapping(value="/materialDictionarySave",method = RequestMethod.POST)
		@ResponseBody
		public String materialDictionarySave(HttpServletRequest req){
			String ID = new GUID().toString();
			String material_code = req.getParameter("material_code");
			String material_name = req.getParameter("material_name");
			String unit = req.getParameter("unit");
			Person person = ThreadLocalHolder.getPerson();
			int i = materialManageService.dictionaryInsert(person,ID,material_code, material_name,unit);
			boolean success = false;
			if(i>0){
				success = true;
			}
			JSONObject  json= new JSONObject();
			json.put("guid", ID);
			json.put("success", success);
			return json.toString();
		}
		
		/**
		 *数组字典删除
		 */
		@RequestMapping(value = "/materialDictionaryDelete")
		@ResponseBody
		public String materialDictionaryDelete(HttpServletRequest req){
			String materialguid = req.getParameter("materialguid");
			int i = materialManageService.dictionaryDelete(materialguid);
			boolean success = false;
			if(i>0){
				success = true;
			}
			JSONObject  json= new JSONObject();
			json.put("success", success);
			return json.toString();
		}
		
		/**
		 * 数组字典修改
		 */
		@RequestMapping(value="/materialDictionaryUpdata")
		@ResponseBody
		public String materialDictionaryUpdata(HttpServletRequest req){
			String materialguid = req.getParameter("guid");
			String material_name = req.getParameter("material_name");
			String unit = req.getParameter("unit");
			Person person = ThreadLocalHolder.getPerson();
			int i = materialManageService.dictionaryUpdate(person,materialguid, material_name,unit);
			String msg = "";
			if(i>0){
				msg="修改成功！";
			}else{
				msg="修改失败！";
			}
			JSONObject  json= new JSONObject();
			json.put("materialguid", materialguid);
			json.put("msg", msg);
			return json.toString();
		}
		
		/**
		 * 获取单位
		 */
		@RequestMapping(value="/getUnit")
		@ResponseBody
		public String getUnit(HttpServletRequest req){
			String materialguid = req.getParameter("materialguid");
			String result = materialManageService.getUnit(materialguid);
			return result;
		}
		
		// 固定资产管理页面
		@RequestMapping(value = "/selectDictionary")
		public String selectDictionary() {
			return "/materialManage/selectDictionary";
		}
		
		// 固定资产管理页面
		@RequestMapping(value = "/selectMaterial")
		public String selectMaterial() {
			return "/materialManage/selectMaterial";
		}

//========================================================================================

}
