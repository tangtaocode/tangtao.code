package net.risesoft.approve.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.MaterialShareService;
import net.risesoft.approve.service.SharestuffService;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.GUID;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
@RequestMapping("/materialshare")
public class MaterialshareController {
	protected Logger log = LoggerFactory.getLogger(MaterialshareController.class);
	
	@Resource(name="materialShareService")
	private MaterialShareService materialShareService;
	
	@Resource(name="sharestuffService")
	private SharestuffService sharestuffService;
	
	
	/**
	 * 跳转到材料定义页面
	 */
	@RiseLog(operateName = "打开材料定义页面",operateType = "查看")
	@RequestMapping(value = "/materialdefinition", method = RequestMethod.GET)
	public String materialShow(){
		return "materialshare/materialdefinition";
	}
	
	/**
	 * 跳转到材料新增页面
	 */
	@RiseLog(operateName = "打开材料新增页面",operateType = "查看")
	@RequestMapping(value = "/materialadd", method = RequestMethod.GET)
	public String materialadd(){
		return "materialshare/materialadd";
	}

	
	/**
	 * 材料定义显示列表
	 */
	@RiseLog(operateName = "获取材料定义列表",operateType = "查看")
	@RequestMapping(value = "/materialList")
	@ResponseBody
	public Map<String, Object> materialList(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String CODE = request.getParameter("CODE");
		String STUFFDEFINENAME = request.getParameter("STUFFDEFINENAME");
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
			pager = materialShareService.findByUserID(person, CODE, 
					STUFFDEFINENAME, pager);
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
	 * 材料定义修改
	 */
	@RiseLog(operateName = "修改材料定义",operateType = "修改")
	@RequestMapping(value="/materialupdata")
	@ResponseBody
	public String update(HttpServletRequest req){
		String ID = req.getParameter("STUFFDEFINEID");
		String CODE = req.getParameter("CODE");
		String STUFFDEFINENAME = req.getParameter("STUFFDEFINENAME");
		String ISCHECKVALID = req.getParameter("ISCHECKVALID");
		String TABINDEX = req.getParameter("TABINDEX");
		String STATE = req.getParameter("STATE");
		String DEFINETIME = req.getParameter("DEFINETIME");
		int i = materialShareService.update(ID, CODE, STUFFDEFINENAME, ISCHECKVALID,TABINDEX,STATE,DEFINETIME);
		String msg = "";
		if(i>0){
			msg="修改成功！";
			sharestuffService.tongbu("CHANGE_DEFINE", ID);
		}else{
			msg="修改失败！";
		}
		JSONObject  json= new JSONObject();
		json.put("STUFFDEFINEID", ID);
		json.put("msg", msg);
		return json.toString();
	}
	
	/**
	 * 材料定义新增
	 */
	@RiseLog(operateName = "新增材料定义",operateType = "增加")
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
		int i = materialShareService.insert(ID,CODE, STUFFDEFINENAME, ISCHECKVALID,TABINDEX,STATE,DEFINETIME);
		boolean success = false;
		if(i>0){
			success = true;
			sharestuffService.tongbu("CHANGE_DEFINE", ID);
		}
		JSONObject  json= new JSONObject();
		json.put("STUFFDEFINEID", ID);
		json.put("success", success);
		return json.toString();
	}
	
	
	/**
	 * 材料删除
	 */
	@RiseLog(operateName = "删除材料定义",operateType = "删除")
	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	@ResponseBody
	public String materialdel(HttpServletRequest req){
		String ID = req.getParameter("STUFFDEFINEID");
		int i = materialShareService.delete(ID);
		boolean success = false;
		if(i>0){
			success = true;
			sharestuffService.tongbu("CHANGE_DEFINE", ID);
		}
		JSONObject  json= new JSONObject();
		json.put("success", success);
		return json.toString();
	}
}
