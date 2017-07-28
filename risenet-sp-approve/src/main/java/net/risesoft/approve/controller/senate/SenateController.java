package net.risesoft.approve.controller.senate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.controller.materialManage.MaterialManageController;
import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.OnlineReceiveService;
import net.risesoft.approve.service.senate.SenateService;
import net.risesoft.fileflow.service.SpWorklistService;
import net.risesoft.model.Department;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.StringX;
import net.risesoft.utilx.database.Conn;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/senateManage")
public class SenateController {
	
	protected Logger log=LoggerFactory.getLogger(MaterialManageController.class);
	@Value("${risebpm7.topMenuGuid}")
	private String topMenuGuid;
	
	@javax.annotation.Resource(name="spWorklistService")
	private SpWorklistService spWorklistService;
	
	@javax.annotation.Resource(name = "onlineReceiveService")
	private OnlineReceiveService onlineReceiveService;
	
	@javax.annotation.Resource(name = "senateService")
	private SenateService senateService;
	
	/**
	 * 评价器测试senateSave
	 */
	@RequestMapping(value = "/senateTest")
	public String senateTest() {
		return "/senate/senateTest";
		
	}
	
	/**
	 * 评价保存
	 */
	@RequestMapping(value = "/senateSave")
	@ResponseBody
	public String senateSave(HttpServletRequest request) {
		String processInstanceId=request.getParameter("processInstanceId");
		String fromPage=request.getParameter("fromPage");
		JSONObject  json= new JSONObject();
		String msg="";
		int issenate = Integer.parseInt(senateService.getOneInt(fromPage,processInstanceId));
   	    if(issenate == 1){
   	    	msg="只能评价一次！";
   	    	json.put("msg", msg);
   			return json.toString();
   	    }
		try{
			senateService.insertSenate(fromPage,processInstanceId,request);
			msg="该事项评价成功！";
		}catch (Exception ex){
			ex.printStackTrace();
			msg="评价提交失败<br><center>"+ex.getLocalizedMessage()+"</center>";
		}
		json.put("msg", msg);
		return json.toString();
		
	}
	
	/**
	 * 审批服务评价保存
	 */
	@RequestMapping(value = "/spItemsSenateSave")
	@ResponseBody
	public String spItemsSenateSave(HttpServletRequest request) {
//		String processInstanceId=request.getParameter("processInstanceId");
		String svalue = null==request.getParameter("svalue")?"":request.getParameter("svalue");
		String zxCode = null==request.getParameter("declaresn")?"":request.getParameter("declaresn");
		String approveitemguid = null==request.getParameter("spApproveItemId")?"":request.getParameter("spApproveItemId");
		String type = null==request.getParameter("type")?"":request.getParameter("type");
		JSONObject  json= new JSONObject();
		String msg="";
		try {
			if("".equals(svalue) || "".equals(approveitemguid)){
				msg="评价失败！";
				json.put("msg", msg);
				return json.toString();
			}
			String sql="select issenate||','||to_char(tb.bjsj,'yyyy-MM-dd') bjsj from spm_senate t left join t_banjie tb on t.declaresn=tb.yxtywlsh and tb.sjbbh=1 where declaresn=? and type=?";
			List<Object> params = new ArrayList<Object>();
			params.add(zxCode);
			params.add(type);
			int issenate=0;
			String bjsj = "";
			String temp[]=new String[]{};
			String re = senateService.getOneString(sql,params);
			if("".equalsIgnoreCase(re)){
				msg="该业务不存在，请核对！";
			    json.put("msg", msg);
				return json.toString();
			}
			if(re.indexOf(",")>-1){
				temp=re.split(",");
			}
			if(temp.length>0){
				issenate=Integer.parseInt(temp[0]);
				bjsj=temp[1];
			}
			if(!"".equals(bjsj)){
				msg="业务已经办结不能评价！";
			    json.put("msg", msg);
				return json.toString();
			}
			if(issenate == 1){
				msg="只能评价一次！";
				json.put("msg", msg);
				return json.toString();
			}
			//保存审批服务评价开始
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("issenate", issenate);
			boolean result =senateService.saveSpItemSenate(request);
			if(!result){
				msg="内部出错，评价失败！";
			}else{
				msg="评价成功！";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			msg="内部出错，评价失败！";
		}
		
		json.put("msg", msg);
		return json.toString();
		
	}
	
	/**
	 * 服务事项评价
	 */
	@RequestMapping(value = "/spItemsSenate")
	public String spItemsSenate() {
		return "/senate/spItemsSenate";
	}
	
	/*
	 *服务事项评价list 
	 */
	@RequestMapping(value = "/spItemsSenateList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> spItemsSenateList(HttpServletRequest request) {
		String approveItemName = request.getParameter("approveItemName");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
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
			pager = senateService.findAllList(approveItemName, pager);
			listmap = pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", listmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 审批服务评价窗口
	 */
	@RequestMapping(value = "/startSenate")
	public String startSenate(HttpServletRequest request) {
		String approveItemId = request.getParameter("approveItemId");
		request.setAttribute("approveItemId", approveItemId);
		return "/senate/startSenateWin";
	}
	
	/**==============================以下为服务评价统计内容===================================**/
	/**
	 * 区级部门统计
	 */
	@RequestMapping(value = "/districtDptSenate")
	public String districtDptSenate(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/districtDptSenate";
	}
	
	/**
	 * 街道评价统计
	 */
	@RequestMapping(value = "/streetSenate")
	public String streetSenate(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/streetSenate";
	}
	
	/**
	 * 社区评价统计
	 */
	@RequestMapping(value = "/communitySenate")
	public String community(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/communitySenate";
	}
	
	/**
	 * 事项评价统计
	 */
	@RequestMapping(value = "/itemSenate")
	public String itemSenate(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/itemSenate";
	}
	
	/**
	 * 个人评价统计
	 */
	@RequestMapping(value = "/personnalSenate")
	public String personnalSenate(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/personnalSenate";
	}
	
	/**
	 * 窗口评价统计
	 */
	@RequestMapping(value = "/windowsSenate")
	public String windowsSenate(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/windowsSenate";
	}
	
	/*
	 *评价统计查询 
	 */
	@RequestMapping(value = "/senateList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> senateList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
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
			pager = senateService.senateList(request, pager);
			listmap = pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", listmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 区级部门统计详细信息
	 */
	@RequestMapping(value = "/districtDptDetail")
	public String districtDptDetail(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/districtDptDetail";
	}
	
	/**
	 * 街道统计详细信息页面
	 */
	@RequestMapping(value = "/streetDetail")
	public String streetDetail(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/streetDetail";
	}
	
	/**
	 * 街道统计详细信息页面
	 */
	@RequestMapping(value = "/communityDetail")
	public String communityDetail(HttpServletRequest request,Model model) {
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("personid", person.getID());
		return "/senate/communityDetail";
	}
	
	/*
	 *详细信息列表
	 */
	@RequestMapping(value = "/detailList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
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
			pager = senateService.detailList(request, pager);
			listmap = pager.getPageList();
			map.put("pager.pageNo", pager.getPageNo());
			map.put("pageSize", pager.getPageSize());
			map.put("pager.totalRows", pager.getTotalRows());
			map.put("rows", listmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	
	
	
	/**
	 * 区级部门下拉列表
	 */
	@RequestMapping(value = "/getSpmBureauList", method = RequestMethod.GET)
	@ResponseBody
	public String getSpmBureauList(){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
		try {
			listmap = senateService.getSpmBureauList();
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
	 * 根据付街道id获取社区下拉列表
	 */
	@RequestMapping(value = "/getCommunityList", method = RequestMethod.POST)
	@ResponseBody
	public String getSelectList(HttpServletRequest request){
		String parentid = request.getParameter("parentId");
		parentid="_d0XscAbYEeK1YqfYG2fcnw";
		List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
		JSONObject  json= new JSONObject();
		List<Department> deptList = new ArrayList<Department>();
		try {
			JSONObject jTemp = null;
			JSONArray jArray = new JSONArray();
			
			if("".equalsIgnoreCase(parentid)){
				resultmap=senateService.getCommunityList();
				for(Map<String, Object> map:resultmap){
					if(null!=map){
						jTemp =  new JSONObject();
						jTemp.put("id", map.get("VALUE"));
						jTemp.put("name", map.get("KEY"));
						jArray.add(jTemp);
					}
				}
			}else{
				deptList = RisesoftUtil.getDepartmentManager().getSubDepartments(parentid);
				for(Department dept:deptList){
					if(null!=dept.getName()&&!"社区工作站".equalsIgnoreCase(dept.getName())&&dept.getName().indexOf("社区")>0){
						jTemp =  new JSONObject();
						jTemp.put("id", dept.getID());
						jTemp.put("name", dept.getName());
						jArray.add(jTemp);
					}
				}
			}
			json.put("objectList", jArray);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return json.toString();
	}
	
	/**
	 * 街道下拉列表
	 */
	@RequestMapping(value = "/getStreetList", method = RequestMethod.GET)
	@ResponseBody
	public String getStreetList(){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
		try {
			listmap = senateService.getStreetList();
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
	 * 街道下拉列表
	 */
	@RequestMapping(value = "/getCommunityList", method = RequestMethod.GET)
	@ResponseBody
	public String getCommunityList(){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
		try {
			listmap = senateService.getCommunityList();
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
	 * 所有部门拉列表
	 */
	@RequestMapping(value = "/getAllBureauList", method = RequestMethod.GET)
	@ResponseBody
	public String getAllBureauList(){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
		try {
			listmap = senateService.getAllBureauList();
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
	 * 窗口拉列表
	 */
	@RequestMapping(value = "/getWindowsList", method = RequestMethod.GET)
	@ResponseBody
	public String getWindowsList(){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultmap = new ArrayList<Map<String, Object>>();
		try {
			listmap = senateService.getWindowsList();
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
	
	
	@RequestMapping(value = "/exportData", method = RequestMethod.GET)
	public void exportData(HttpServletRequest request,HttpServletResponse response)throws Exception {
		senateService.exportDataForExcel(request, response);
	}

}
