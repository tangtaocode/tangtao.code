package net.risesoft.approve.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.service.MaterialListService;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SPMwssbclService;
import net.risesoft.approve.service.SharestuffService;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.utilx.StringX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@Controller
@RequestMapping("/wssbcl")
public class SPMwssbclController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SPMwssbclService spMwssbclService;
	
	@Autowired
	private MaterialListService materialListService;
	
	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Autowired
	private SharestuffService sharestuffService;
	
	/**
	 * 跳转材料页面
	 * @param SPinstanceId
	 * @return
	 */
	@RiseLog(operateName = "打开材料页面",operateType = "查看")
	@RequestMapping(value = "/forward")
	public String fileAttachment(@RequestParam String SPinstanceId,@RequestParam String itembox,Model model){
		model.addAttribute("SPinstanceId", SPinstanceId);
		model.addAttribute("itembox", itembox);
		return "config/attachment/fileAttachment";
	}
	
	/**
	 * 跳转查看附件页面
	 * @param SPinstanceId
	 * @return
	 */
	@RiseLog(operateName = "查看附件",operateType = "查看")
	@RequestMapping(value = "/lookFile")
	public String lookFile(@RequestParam String sblsh,@RequestParam String stuffSeq,@RequestParam String version,Model model){

		model.addAttribute("sblsh", sblsh);
		model.addAttribute("stuffSeq", stuffSeq);
		model.addAttribute("version", version);
		return "config/attachment/file";
	}
	
	/**
	 * 材料清单查询
	 * 
	 * @param processInstanceId
	 * @param processSerialNumber
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RiseLog(operateName = "获取材料清单",operateType = "查看")
	@RequestMapping(value = "/materialList")
	@ResponseBody
	public Map<String,Object> materialList(@RequestParam String SPinstanceId){
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> reList = new ArrayList<Map<String,Object>>();
		//窗口和网上统一从office_spi_declareinfo中获取材料
		reList = materialListService.findMaterialByOffice(SPinstanceId);
		//分组
		List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
		Map<Integer,String> stuffSeqs=new HashMap<Integer,String>();
		Map<String,List<Map<String, Object>>> attachList=new HashMap<String, List<Map<String,Object>>>();
		int mark=0;
		for (int i = 0; i < reList.size(); i++) {
			Map<String, Object> attach=new HashMap<String, Object>();
			attach.put("ATTACH_NAME", reList.get(i).get("ATTACH_NAME"));
			attach.put("ATTACH_ID", reList.get(i).get("ATTACH_ID"));
			attach.put("ATTACH_PATH", reList.get(i).get("ATTACH_PATH"));
			if (!stuffSeqs.containsValue(reList.get(i).get("STUFF_SEQ"))) {
				stuffSeqs.put(mark,(String)reList.get(i).get("STUFF_SEQ"));
				attachList.put((String)reList.get(i).get("STUFF_SEQ"), new ArrayList<Map<String,Object>>());
				attachList.get((String)reList.get(i).get("STUFF_SEQ")).add(attach);
				result.add(reList.get(i));
				mark++;
			}else {
				attachList.get((String)reList.get(i).get("STUFF_SEQ")).add(attach);
			}
		}
		for (int i = 0; i < result.size(); i++) {
			if (null==result.get(i).get("CLSL")||result.get(i).get("CLSL").toString().trim().toLowerCase().equals("null")) {
				result.get(i).put("CLSL", "");
			}
			result.get(i).put("attachList", attachList.get(stuffSeqs.get(i)));
		}

		map.put("rows", result); 
		return map;
	}
	
	/**
	 * 附件查询
	 * 
	 * @param processInstanceId
	 * @param processSerialNumber
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RiseLog(operateName = "获取附件列表",operateType = "查看")
	@RequestMapping(value = "/fileList")
	@ResponseBody
	public List<Map<String, Object>> fileList(@RequestParam String stuffSeq,@RequestParam String sblsh,@RequestParam String version,HttpServletRequest request){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = spMwssbclService.getSpMwssbclList(sblsh, stuffSeq,version,request);
		return list;
	}
	
	/**
	 * 下载附件
	 * @param id
	 * @param response
	 */
	@RiseLog(operateName = "下载附件",operateType = "使用")
	@RequestMapping(value = "/download")
	public String download(String id,String instanceId,String declareGuid,String processInstanceId,String itembox, HttpServletResponse response,HttpServletRequest request,Model model){
		try{
		spMwssbclService.download(id, response,request);
		}catch(Exception e){
			logger.error("附件下载失败或附件不存在！");
			model.addAttribute("instanceId", instanceId);
			model.addAttribute("DECLAREANNEXGUID", declareGuid);
			
			model.addAttribute("msg", "附件下载失败！");
			if(!StringX.isBlank(instanceId)){
				model.addAttribute("instanceId", instanceId);
				model.addAttribute("itembox", itembox);
				return "config/attachment/file";
			}else{
				return "approve/attachmentList";
			}
		}
		return null;
	}
	
	/**
	 * 上传附件
	 * 
	 * @param attachmentFile
	 *            附件内容
	 * @param processInstanceId
	 *            流程实例Id
	 * @param session
	 * @return
	 */
	@RiseLog(operateName = "上传附件",operateType = "增加")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> upload(@RequestParam(required = false) MultipartFile  attachmentFile,@RequestParam String declareannexguid, @RequestParam String processInstanceId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			spMwssbclService.upload(attachmentFile,declareannexguid,processInstanceId);
			map.put("success", true);
			map.put("msg", "上传附件成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "上传附件失败");
		}
		HttpHeaders headers = new HttpHeaders();
		MediaType mt = new MediaType("text", "html", Charset.forName("utf-8"));
		headers.setContentType(mt);
		ResponseEntity<HashMap<String, Object>> re = null;
		re = new ResponseEntity<HashMap<String, Object>>(map, headers, HttpStatus.OK);
		return re;
	}

	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	@RiseLog(operateName = "删除附件",operateType = "删除")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public HashMap<String, Object> delete(@RequestParam String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			spMwssbclService.delete(id);
			map.put("Result", "OK");
			map.put("msg", "删除附件成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Result", "ERROR");
			map.put("msg", e.getMessage());
		}

		return map;
	}
	
	
}
