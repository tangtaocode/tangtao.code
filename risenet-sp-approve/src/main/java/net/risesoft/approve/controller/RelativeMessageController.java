//package net.risesoft.approve.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//
//
//
//
//import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
//import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
//import net.risesoft.approve.service.RelativeMessageService;
//import net.risesoft.tenant.annotation.RiseLog;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//@Controller
//@RequestMapping("/sp/relativeMessage")
//public class RelativeMessageController {
//	
//	@Autowired
//	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
//	
//	@Autowired
//	private RelativeMessageService relativeMessageService;
//	
//	/**
//	 * 获取数据来源
//	 * @param 
//	 * @return
//	 */
//	@RiseLog(operateName = "获取数据来源",operateType = "查看")
//	@ResponseBody
//	@RequestMapping(value = "/getDataSource")
//	public Map<String, Object>  getDataSource(){
//		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
//		Map<String, Object> ret_map = new HashMap<String, Object>();
//		String str[] = {"织网工程","证照库","资料库","注册信息"};
//		for(int i = 0;i<str.length;i++){
//			Map<String, Object> map = new HashMap<>();
//			map.put("dataSource", str[i]);
//			items.add(map);
//		}
//		ret_map.put("rows", items);
//		return ret_map;
//	}
//	
//	/**
//	 * 打开相关信息页面
//	 * @param 
//	 * @return
//	 */
//	@RiseLog(operateName = "打开相关信息页面",operateType = "查看")
//	@RequestMapping(value = "/showRelativeMessage")
//	public String showRelativeMessage(@RequestParam String SPinstanceId,Model model) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(SPinstanceId);
//			
//			//织网工程相关信息
//			List<Map<String, Object>> zwgcListMap = new ArrayList<Map<String,Object>>();
//			zwgcListMap = relativeMessageService.getZhiWangGongCheng(SPinstanceId);
//			map.put("rows", zwgcListMap);
//			
//			//证照库相关信息
//			List<Map<String, Object>> zzkListMap = new ArrayList<Map<String,Object>>();
//			zzkListMap = relativeMessageService.getZhengZhaoKu(SPinstanceId);
//			map.put("rows", zzkListMap);
//			
//			//资料库相关信息
//			List<Map<String, Object>> zlkListMap = new ArrayList<Map<String,Object>>();
//			zlkListMap = relativeMessageService.getZiLiaoKu(SPinstanceId);
//			map.put("rows", zlkListMap);
//			
//			//注册信息
//			List<Map<String, Object>> zcxxListMap = new ArrayList<Map<String,Object>>();
//			zcxxListMap = relativeMessageService.getZhuCeXinXi(SPinstanceId);
//			map.put("rows", zcxxListMap);
//			
//			model.addAttribute("type", officeSpiDeclareinfo.getDeclareType());//证件代码类型
//			model.addAttribute("zwgcMap", zwgcListMap.size()>0?zwgcListMap.get(0):null);
//			model.addAttribute("zzkMap", zzkListMap.size()>0?zzkListMap.get(0):null);
//			model.addAttribute("zlkMap", zlkListMap);
//			model.addAttribute("zcxxMap", zcxxListMap.size()>0?zcxxListMap.get(0):null);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("SPinstanceId", SPinstanceId);
//		return "worklist/itembox/relativeMessage";
//	}
//	
//	/**
//	 * 相关信息
//	 * @param 
//	 * @return
//	 */
//	@RiseLog(operateName = "获取相关信息",operateType = "查看")
//	@ResponseBody
//	@RequestMapping(value = "/getRelativeMessage")
//	public Map<String, Object> getRelativeMessage(@RequestParam String SPinstanceId,@RequestParam String dataSource,Model model) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			OfficeSpiDeclareinfo officeSpiDeclareinfo = officeSpiDeclareinfoService.findByGuid(SPinstanceId);
//			
//			if("织网工程".equals(dataSource)){
//				//织网工程相关信息
//				List<Map<String, Object>> zwgcListMap = new ArrayList<Map<String,Object>>();
//				zwgcListMap = relativeMessageService.getZhiWangGongCheng(SPinstanceId);
//				map.put("rows", zwgcListMap);
//			}else if("证照库".equals(dataSource)){
//				//证照库相关信息
//				List<Map<String, Object>> zzkListMap = new ArrayList<Map<String,Object>>();
//				zzkListMap = relativeMessageService.getZhengZhaoKu(SPinstanceId);
//				map.put("rows", zzkListMap);
//			}else if("资料库".equals(dataSource)){
//				//资料库相关信息
//				List<Map<String, Object>> zlkListMap = new ArrayList<Map<String,Object>>();
//				zlkListMap = relativeMessageService.getZiLiaoKu(SPinstanceId);
//				map.put("rows", zlkListMap);
//			}else if("注册信息".equals(dataSource)){
//				//注册信息
//				List<Map<String, Object>> zcxxListMap = new ArrayList<Map<String,Object>>();
//				zcxxListMap = relativeMessageService.getZhuCeXinXi(SPinstanceId);
//				map.put("rows", zcxxListMap);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return map;
//	}
//	
//	
//}
