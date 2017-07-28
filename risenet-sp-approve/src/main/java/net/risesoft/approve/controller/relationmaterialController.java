package net.risesoft.approve.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.SharestuffService;
import net.risesoft.approve.service.XzqlXzspListsService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/relationmaterial")
public class relationmaterialController {
	
	@Autowired
	private XzqlXzspListsService xzqlXzspListsService;
	
	@Resource(name="sharestuffService")
	private SharestuffService sharestuffService;

	@RiseLog(operateName = "打开关联材料共享页面",operateType = "查看")
	@RequestMapping("/relationmaterial")
	public String index(){		
		return "/materialshare/relationmaterial";		
	}
	
	/*
	 * 跳转到材料共享树页面
	 * 
	 */
	@RiseLog(operateName = "打开材料共享树页面",operateType = "查看")
	@RequestMapping("/stufftree")
	public ModelAndView stufftree(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/materialshare/stufftree");
		mav.addObject("ID",request.getParameter("ID"));
		mav.addObject("CLTYPEID",request.getParameter("CLTYPEID"));
		return mav;
	}
	
	
	/*
	 * 跳转到证照共享树页面
	 * 
	 */
	@RiseLog(operateName = "打开证照共享树页面",operateType = "查看")
	@RequestMapping("/doctypetree")
	public ModelAndView doctypetree(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/materialshare/doctypetree");
		mav.addObject("ID",request.getParameter("ID"));
		return mav;
	}
	
	/*
	 * 材料明细管理
	 * 
	 */
	@RiseLog(operateName = "获取材料明细管理信息",operateType = "查看")
	@RequestMapping(value="/materialinfo",method = RequestMethod.POST)
	@ResponseBody
	public String grid(HttpServletRequest req){
		String itemid  = req.getParameter("itemid");
		JSONObject json=null;
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		try{
			
			listmap = xzqlXzspListsService.findById(itemid);
			
			/*for (Map<String, Object> map2 : spmList) {
				System.out.println(map2);
			}*/
			map.put("rows",listmap);
			json=JSONObject.fromObject(map);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	  return json.toString();
		/*List<Map<String,Object>> list = xzqlXzspListsService.findById(itemid);
		map.put("rows",list);
		JSONObject json=JSONObject.fromObject(map);
		return json.toString();*/
	}
	
	/*
	 * 材料分类管理列表
	 * 
	 */
	@RiseLog(operateName = "获取材料分类管理列表",operateType = "查看")
	@RequestMapping(value="/table",method = RequestMethod.POST)
	@ResponseBody
	public String table(HttpServletRequest req){
		String itemid  = req.getParameter("itemid");
		String pageNo = req.getParameter("pager.pageNo");
		String pageSize = req.getParameter("pager.pageSize");
		JSONObject json=null;
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			if(StringX.isBlank(pageSize) || StringX.isBlank(pageNo)){
				pageSize="20";
				pageNo = "1";
			}
			Pager pager=new Pager();
			pager.setPageNo(Integer.parseInt(pageNo));
			pager.setPageSize(Integer.parseInt(pageSize));
			pager = xzqlXzspListsService.findTableById(itemid,pager);
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
		}
		return json.toString();
		/*String itemid  = req.getParameter("itemid");
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> list = xzqlXzspListsService.findTableById(itemid);
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();*/
	}
	
	
	/*
	 * 材料分类管理列表
	 * 
	 */
	@RiseLog(operateName = "获取材料分类管理列表",operateType = "查看")
	@RequestMapping(value="/materialtable",method = RequestMethod.POST)
	@ResponseBody
	public String materialtable(HttpServletRequest req){
		String itemid  = req.getParameter("itemid");
		String typeguid  = req.getParameter("typeguid");
		JSONObject json=null;
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		try{
			
			listmap = xzqlXzspListsService.findmaterialById(itemid,typeguid);
			map.put("rows",listmap);
			json=JSONObject.fromObject(map);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return json.toString();
		
	}
	
/*	@RequestMapping(value="/getdiv",method = RequestMethod.POST)
	@ResponseBody
	public String getdiv(HttpServletRequest req){
		String itemid  = req.getParameter("itemid");
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> list = xzqlXzspListTypeLogService.getDiv(itemid);
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}*/
	
	
	/*
	 * 材料明细删除
	 * 
	 */
	@RiseLog(operateName = "删除材料明细",operateType = "删除")
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
	
	/*
	 * 材料明细保存
	 * 
	 */
	@RiseLog(operateName = "保存材料明细",operateType = "增加")
	@RequestMapping(value="/rmsave",method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest req){
		String ITEMID = req.getParameter("ITEMID");
		String MATERIALLX = req.getParameter("MATERIALLX");
		String VERSION = req.getParameter("VERSION");
		String MATERIALNAME = req.getParameter("MATERIALNAME");
		String DESCRIBE = req.getParameter("DESCRIBE");
		String ORDERNO = req.getParameter("ORDERNO");
		int ordno=0;
		if(ORDERNO!=""){
			ordno = Integer.parseInt(ORDERNO);
		}	
		String id = new GUID().toString();
		int i = xzqlXzspListsService.add(id, ITEMID,VERSION,MATERIALLX, MATERIALNAME, DESCRIBE, ordno);
		String msg = "";
		if(i>0){
			msg="添加成功！";
		}else{
			msg="添加失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("id", id);
		json.put("msg", msg);
		return json.toString();
		//return "";
	}
	
	/*
	 * 材料明细修改
	 * 
	 */
	@RiseLog(operateName = "修改材料明细",operateType = "修改")
	@RequestMapping(value="/rmupdate",method = RequestMethod.POST)
	@ResponseBody
	public String update(HttpServletRequest req){
		String ID = req.getParameter("ID");
		String MATERIALLX = req.getParameter("MATERIALLX");
		String MATERIALNAME = req.getParameter("MATERIALNAME");
		String DESCRIBE = req.getParameter("DESCRIBE");
		
		String STUFFDEFINENAME = req.getParameter("STUFFDEFINENAME");
		String DOCTYPENAME = req.getParameter("DOCTYPENAME");
		String DOCTYPEGUID = req.getParameter("DOCTYPEGUID");
		String ORDERNO = req.getParameter("ORDERNO");

		int i = xzqlXzspListsService.rmupdate(ID, MATERIALLX, MATERIALNAME, DESCRIBE,STUFFDEFINENAME, DOCTYPENAME, DOCTYPEGUID, ORDERNO);
		String msg = "";
		if(i>0){
			msg="添加成功！";
		}else{
			msg="添加失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("id", ID);
		json.put("msg", msg);
		return json.toString();
	}
	
	/*
	 * 材料分类管理savecheck
	 * 
	 */
	@RiseLog(operateName = "保存材料分类",operateType = "增加")
	@RequestMapping(value="/savecheck",method = RequestMethod.POST)
	@ResponseBody
	public String savecheck(HttpServletRequest req){
		String checkid = req.getParameter("checkid");
		String itemid = req.getParameter("itemid");
		String version = req.getParameter("version");
		String typeguid = "";
		String listguid = "";
		String msg = "";
		int y = 0; 
		if(checkid!=""){
			//删除该类别所有选中状态
			int x = xzqlXzspListsService.materialdel(itemid, version); 
			if(x > 0){
			String[] ckidall = checkid.split(",");
	        for(int i= 0;i<= ckidall.length-1;i++)  
	        {  
	             if(ckidall[i] != "")  
	             {  
	             String cid=ckidall[i];  
	             String[] type = cid.split("_");
	             listguid = type[0];
	             typeguid= type[1];
	           //插入选中材料
	     		 y = xzqlXzspListsService.materialupdata(typeguid, itemid, version, listguid);     
	             }  
	          }	
			}
		}
		
		
		if(y>0){
			msg="保存成功！";
		}else{
			msg="保存失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("msg", msg);
		return json.toString();
	}
	
	/*
	 * 材料分类修改
	 * 
	 */
	@RiseLog(operateName = "修改材料分类",operateType = "修改")
	@RequestMapping(value="/materialupdate")
	@ResponseBody
	public String materialupdate(HttpServletRequest req){
		String TYPEGUID = req.getParameter("TYPEGUID");
		String TYPENAME = req.getParameter("TYPENAME");
		String ORDERNO = req.getParameter("ORDERNO");
		String BANBEN = req.getParameter("BANBEN");
		int i = xzqlXzspListsService.mupdate(TYPEGUID, TYPENAME, ORDERNO,BANBEN);
		String msg = "";
		if(i>0){
			msg="提交成功！";
		}else{
			msg="提交失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("msg", msg);
	return json.toString();

	}
	
	/*
	 * 材料分类新增
	 * 
	 */
	@RiseLog(operateName = "新增材料分类",operateType = "增加")
	@RequestMapping(value="/materialsave")
	@ResponseBody
	public String materialsave(HttpServletRequest req){
		String ITEMID = req.getParameter("ITEMID");
		String TYPENAME = req.getParameter("TYPENAME");
		String ORDERNO = req.getParameter("ORDERNO");
		String VERSION = req.getParameter("VERSION");
		String typeguid = new GUID().toString();
		int i = xzqlXzspListsService.materialinsert(typeguid,ITEMID,TYPENAME,ORDERNO,VERSION);
		String msg = "";
		if(i>0){
			msg="新增成功！";
		}else{
			msg="新增失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("typeguid", typeguid);
		json.put("banben", VERSION);
		json.put("msg", msg);
	return json.toString();

	}
	
	/*
	 * 材料分类删除
	 * 
	 */
	@RiseLog(operateName = "删除材料分类",operateType = "删除")
	@RequestMapping(value="/materialdelete")
	@ResponseBody
	public String materialdelete(HttpServletRequest req){
		String TYPEGUID = req.getParameter("TYPEGUID");
		String VERSION = req.getParameter("BANBEN");
		int i = xzqlXzspListsService.materialdelete(TYPEGUID,VERSION);
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
	 * 材料树多选框列表
	 * 
	 */
	@RiseLog(operateName = "打开材料树多选框列表",operateType = "查看")
	@RequestMapping(value="/stufftreelist",method = RequestMethod.POST)
	@ResponseBody
	public String selectstuffTree(){
		String tree="";
		try{
			tree=xzqlXzspListsService.findStuffTree();
		
		}catch(Exception e){
			String err="";
			err="【调用businessService.selectTree()】出错！";
			tree="错误";
			System.out.println(err);
			e.printStackTrace();
		}
		return tree;
	}
	

	/*
	 * 关联材料
	 * 
	 */
	@RiseLog(operateName = "关联材料",operateType = "增加")
	@RequestMapping("/savestufftype")
	@ResponseBody
	public String savestufftype(@RequestParam String ALLID,@RequestParam String CLTYPEID,@RequestParam String LISTID){
		String  STUFFDEFINEID = "";
		int y = 0 ;
		if(LISTID!=""&&LISTID!=null){
			//删除该绑定材料所有已选中状态
			int x = xzqlXzspListsService.stuffdel(LISTID);
			sharestuffService.tongbu("DEL_STUFFOFITEM", LISTID);
			//判断是否勾选
			if(ALLID!=""&&ALLID!=null){
			String[] ckidall = ALLID.split(",");
	        for(int i= 0;i<= ckidall.length-1;i++)  
	        {  
	        	STUFFDEFINEID = ckidall[i];
	             
	           //插入选中材料
	             y = xzqlXzspListsService.stuffupdate( STUFFDEFINEID, CLTYPEID,LISTID);   
	        }
	      }	
		}
		
		 
		String msg = "";
		if(y>0){
			msg="关联成功！";
			sharestuffService.tongbu("SYNCALLDATA", null);
		}else{
			msg="提交成功！";
		}
		JSONObject  json= new JSONObject();
		json.put("i", y);
		json.put("msg", msg);
		return json.toString();
	}
	
	/*
	 * 证照树多选框列表
	 * 
	 */
	@RiseLog(operateName = "打开证照树多选框列表",operateType = "查看")
	@RequestMapping(value="/doctypetreelist",method = RequestMethod.POST)
	@ResponseBody
	public String selectTree(){
		String tree="";
		try{
			tree=xzqlXzspListsService.finddoctypeTree();
		
		}catch(Exception e){
			String err="";
			err="【调用businessService.selectTree()】出错！";
			tree="错误";
			System.out.println(err);
			e.printStackTrace();
		}
		return tree;
	}
	
	
	/*
	 * 关联证照
	 * 
	 */
	@RiseLog(operateName = "关联证照",operateType = "增加")
	@RequestMapping("/savedoctype")
	@ResponseBody
	public String savedoctype(HttpServletRequest req){
		String ID = req.getParameter("ID");
		String ALLNAME = req.getParameter("ALLNAME");
		String ALLID = req.getParameter("ALLID");
		int i = xzqlXzspListsService.doctypeupdate(ID, ALLNAME, ALLID);
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
	
	
}
