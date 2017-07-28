/**
 * @Project Name:risenet-sp-approve
 * @File Name: StuffController.java
 * @Package Name: net.risesoft.approve.controller
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月19日 下午5:25:45
 */
package net.risesoft.approve.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SharestuffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: StuffController.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年3月19日 下午5:25:45
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/stuff")
public class StuffController {
	
	@Autowired
	private SharestuffService sharestuffService;
	
	
	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	/**
	 * 
	  * @MethodName: lookStuffdata
	  * @Description: 跳转查看共享材料
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月12日  上午11:54:41
	 */
	@RequestMapping(value = "/lookStuffdata")
	public String lookStuffdata(@RequestParam String instanceId,@RequestParam String DECLAREANNEXGUID, @RequestParam String type, Model model){
		model.addAttribute("instanceId", instanceId);
		model.addAttribute("DECLAREANNEXGUID", DECLAREANNEXGUID);
		model.addAttribute("type", type);
		return "stuff/stuffdata";
	}
	
	
	/**
	 * 
	  * @MethodName: stuffdataList
	  * @Description: 窗口收件时加载某个材料的可用共享材料
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return List<Map<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月12日  上午11:43:50
	 */
	@RequestMapping(value = "/stuffdataList")
	@ResponseBody
	public Map<String, Object> stuffdataList(@RequestParam String DECLAREANNEXGUID,@RequestParam String instanceId, @RequestParam String type){
		/*
		 * 共享材料
		 * 
		 */	
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> stuffdataList;
		if(type.equalsIgnoreCase("online")) {
			stuffdataList = sharestuffService.findBySbguidAndDeclareannexguid(instanceId,DECLAREANNEXGUID);
		}else {
			stuffdataList = sharestuffService.findByInstanceguidAndDeclareannexguid(instanceId,DECLAREANNEXGUID);
		}
		
		map.put("rows", stuffdataList);
		return map;
	}
	
	@RequestMapping(value = "/download")
	public Map<String, Object> download(String id, HttpServletResponse response,HttpServletRequest request,Model model) {
		sharestuffService.download(id, response, request);
		return null;
	}
	
	
	
	/**
	 * 
	  * @MethodName: upload
	  * @Description: 上传某个共享材料的附件
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return ResponseEntity<HashMap<String,Object>>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月28日  下午2:31:55
	 */
	@RequestMapping(value = "/uploadXt", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> upload(@RequestParam(required = false) MultipartFile  attachmentFile,@RequestParam String stuffdataguid) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			sharestuffService.upload(attachmentFile, stuffdataguid);
			map.put("success", true);
			map.put("msg", "上传附件成功");
			sharestuffService.tongbu("SYNCALLDATA", null);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "上传附件失败，请联系系统管理员！");
		}
		HttpHeaders headers = new HttpHeaders();
		MediaType mt = new MediaType("text", "html", Charset.forName("utf-8"));
		headers.setContentType(mt);
		ResponseEntity<HashMap<String, Object>> re = null;
		re = new ResponseEntity<HashMap<String, Object>>(map, headers, HttpStatus.OK);
		return re;
	}	
	
	
	@RequestMapping(value = "/saveStuffData")
	@ResponseBody
	public Map<String, Object> saveStuffData(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		String stuffdataguid = request.getParameter("stuffdataguid");
		String columnName = request.getParameter("columnName");
		String columnValue = request.getParameter("columnValue");
		int result = sharestuffService.updateStuffdataByColumnName(stuffdataguid, columnName, columnValue);
		if( result ==1) {
			map.put("id", "1");
			map.put("message", "修改成功！");
			sharestuffService.tongbu("SYNCALLDATA", null);
		}else {
			map.put("id", "0");
			map.put("message", "修改失败！");
		}
		return map;
	}
	
	/**
	 * 
	  * @MethodName: updateStuffdata
	  * @Description: stuffdata整行修改
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年5月5日  下午2:28:55
	 */
	@RequestMapping(value = "/updateStuffdata")
	@ResponseBody
	public Map<String, Object> updateStuffdata(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		int result = sharestuffService.updateStuffdata(request);
		if( result ==1) {
			map.put("id", "1");
			map.put("message", "修改成功！");
			sharestuffService.tongbu("SYNCALLDATA", null);
		}else {
			map.put("id", "0");
			map.put("message", "修改失败！");
		}
		return map;
	}
	
	@RequestMapping(value = "/wuxiao", method = RequestMethod.GET)
	public String wuxiao(HttpServletRequest request, Model model) {
		model.addAttribute("stuffdataguid", request.getParameter("stuffdataguid"));		
		return "/stuff/wuxiao";
		
	}
	
	@RequestMapping(value = "/saveWuxiao",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveWuxiao(String stuffdataguid, String remark) {
		int res = sharestuffService.saveWuxiao(stuffdataguid, remark);
		
		Map<String,Object> maptemp=new HashMap<String, Object>();

		if(res>0) {
			maptemp.put("msg", "true");
			sharestuffService.tongbu("SYNCALLDATA", null);
		}else {
			maptemp.put("msg", "false");
		}
		return maptemp;
	}
	
	/**
	 * 
	  * @MethodName: stuffdataXtList
	  * @Description: 根据共享材料表stuffdata的guid查找对应的共享材料附件表stuffdataxt的记录
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月19日  下午3:51:58
	 */
	@RequestMapping(value = "/stuffdataXtList")
	@ResponseBody
	public Map<String, Object> stuffdataXtList(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		String stuffdataguid = request.getParameter("parentId");
			
		List<Map<String, Object>> dataXtList = sharestuffService.findXtByStuffguid(stuffdataguid);
		
		map.put("rows", dataXtList);
		return map;
	}
	
	@RequestMapping(value = "/stuffXtInit", method = RequestMethod.GET)
	public String stuffXtInit(HttpServletRequest request, Model model) {
		model.addAttribute("stuffdataguid", request.getParameter("stuffdataguid"));		
		return "/stuff/stuffXt";
		
	}
	
	/**
	 * 
	  * @MethodName: delXt
	  * @Description: 删除共享材料附件
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月1日  上午11:37:36
	 */
	@RequestMapping(value = "/delXt")
	@ResponseBody
	public Map<String, Object> delXt(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		String xtguid = request.getParameter("xtguid");
			
		boolean result = sharestuffService.delXt(xtguid);
		
		if(result == true) {
			map.put("msg", "删除共享材料附件成功！");
			sharestuffService.tongbu("SYNCALLDATA", null);
		} else {
			map.put("msg", "删除共享材料附件失败！");
		}
		
		return map;
	}
	
	/**
	 * 
	  * @MethodName: delXt
	  * @Description: 删除共享材料附件
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return Map<String,Object>    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年4月1日  上午11:37:36
	 */
	@RequestMapping(value = "/delStuffdata")
	@ResponseBody
	public Map<String, Object> delStuffdata(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		String stuffdataguid = request.getParameter("stuffdataguid");
			
		boolean result = sharestuffService.delStuffdata(stuffdataguid);
		
		if(result) {
			map.put("msg", "删除共享材料成功！");
			sharestuffService.tongbu("SYNCALLDATA", null);
		} else {
			map.put("msg", "删除共享材料失败！");
		}
		
		return map;
	}

}

