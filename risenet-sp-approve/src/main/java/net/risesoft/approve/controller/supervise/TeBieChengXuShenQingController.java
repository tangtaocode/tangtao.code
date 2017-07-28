package net.risesoft.approve.controller.supervise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.approve.entity.jpa.gdbs.TbcxsqProcess;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.supervise.TeBieChengXuShenQingService;
import net.risesoft.model.Person;
import net.risesoft.tenant.annotation.RiseLog;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/sp/teBieChengXuShenQing")
public class TeBieChengXuShenQingController {
	
	@Resource(name="teBieChengXuShenQingService")
	private TeBieChengXuShenQingService teBieChengXuShenQingService;
	
	@Resource(name="spmApproveItemService")
	private SpmApproveItemService spmApproveItemService;
	
	@Resource(name="routerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Value("${risebpm7.teBieChengXuShenHe}")
	private String teBieChengXuShenHe;
	
	
	/**
	 * 跳转到特别程序申请页面
	 * @return
	 */
	@RiseLog(operateName = "打开特别程序申请页面",operateType = "查看")
	@RequestMapping(value="/show")
	public String teBieChengXuShenQing(String processInstanceId,String taskId,Model model){
		String SPinstanceId = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		try {
			Person person = ThreadLocalHolder.getPerson();
			//加载特别程序信息
			Map<String,Object> map = teBieChengXuShenQingService.loadTbcxsq(SPinstanceId);
			List<Person> persons = RisesoftUtil.getRoleManager().getPersonsByID("",teBieChengXuShenHe);
			model.addAttribute("person", persons);
			model.addAttribute("map", map);
			model.addAttribute("taskId", taskId);
			model.addAttribute("instanceId", SPinstanceId);
			return "approve/supervise/teBieChengXuShenQing";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/500";
	}
	

	/**
	 * 获取类型
	 * @param tt
	 * @return
	 */
	@RiseLog(operateName = "获取特别程序申请类型",operateType = "查看")
	@ResponseBody
	@RequestMapping(value="/getType",method=RequestMethod.POST)
	public List<Map<String,Object>> getType(String type){
		List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		if(type.equals("A")){
			 map.put("id","延长审批");
			 map.put("text", "延长审批");
			 list.add(map); 
		}else if(type.equals("B")){
			String[] str = {"专家鉴定","听证","招标","拍卖","检验","检测","检疫","公示","现场验收","其它"};
			for(int i = 0;i<str.length;i++){
				Map<String,Object> m=new HashMap<String, Object>();
				m.put("id",str[i]);
				m.put("text",str[i]);
				list.add(m); 
			}
		}
		return list;
	}
	
	
	/**
	 * 保存特别程序申请信息
	 * @param tt
	 * @return
	 */
	@RiseLog(operateName = "保存特别程序申请信息",operateType = "增加")
	@ResponseBody
	@RequestMapping(value="/saveOrModify",method=RequestMethod.POST)
	public Map<String,Object> saveOrModify(TbcxsqProcess tt,String instanceId,String taskId){
		Map<String,Object> map=new HashMap<String, Object>();
		map = teBieChengXuShenQingService.saveOrUpdate(tt,instanceId,taskId);
		return map;
	}

	/**
	 * 跳转特别程序审核页面
	 * @param taskId
	 * @return
	 */
	@RiseLog(operateName = "打开特别程序审核页面",operateType = "查看")
	@RequestMapping(value="/teBieChengXuShenQingShenHe")
	public String teBieChengXuShenQingChuLi(String processInstanceId,String taskId,Model model){
		String SPinstanceId = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		Map<String,Object> map  = teBieChengXuShenQingService.findTbcxsq(SPinstanceId);
		Map<String,Object> resMap = teBieChengXuShenQingService.loadTbcxsq(SPinstanceId);
		List<Person> person = RisesoftUtil.getRoleManager().getPersonsByID("",teBieChengXuShenHe);
		model.addAttribute("person", person);
		model.addAttribute("resMap", resMap);
		model.addAttribute("map", map);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("taskId", taskId);
		return "approve/supervise/teBieChengXuAudit";
	}
	
	/**
	 * 保存特别程序审核
	 * @return
	 */
	@RiseLog(operateName = "保存特别程序审核",operateType = "增加")
	@ResponseBody
	@RequestMapping(value="/saveTeBieChengXuShenHe")
	public Map<String,Object> saveTeBieChengXuShenHe(String processInstanceId,String taskId,String sjbbh,String type,Model model){
		Map<String,Object> map=new HashMap<String, Object>();
		String SPinstanceId = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		map = teBieChengXuShenQingService.saveTeBieChengXuShenHe(SPinstanceId,taskId,type);
		return map;
	}
	
	/**
	 * 特别程序结束
	 * @return
	 */
	@RiseLog(operateName = "打开特别程序结果页面",operateType = "查看")
	@RequestMapping(value="/teBieChengXuJieGuo")
	public String teBieChengXuJieShu(@RequestParam(required = false) String processInstanceId,@RequestParam(required = false) String taskId,Model model){
		//Map<String,Object> map=new HashMap<String, Object>();
		String SPinstanceId = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		Map<String,Object> resMap = teBieChengXuShenQingService.loadTbcxsq(SPinstanceId);
		String date=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		model.addAttribute("jgcsrq",date);//结果产生日期
		model.addAttribute("tbcxjsrq",date);//特别程序结束日期
		model.addAttribute("taskId",taskId);
		model.addAttribute("resMap", resMap);
		model.addAttribute("processInstanceId",processInstanceId);
		return "approve/supervise/teBieChengXuJieGuo";
	}
	
	/**
	 * 保存特别程序结果
	 * @return
	 */
	@RiseLog(operateName = "保存特别程序结果",operateType = "增加")
	@ResponseBody
	@RequestMapping(value="/saveTeBieChengXuJieGuo")
	public Map<String,Object> saveTeBieChengXuJieGuo(String sjbbh,String tbcxjg,String jgcsrq,String tbcxjsrq,String tbcxsfje,String bz,String taskId,String processInstanceId){
		Map<String,Object> map=new HashMap<String, Object>();
		String SPinstanceId = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		map = teBieChengXuShenQingService.saveTeBieChengXuJieGuo(SPinstanceId,sjbbh,tbcxjg,jgcsrq,tbcxjsrq,tbcxsfje,bz,taskId,processInstanceId);
		return map;
	}
	

}
