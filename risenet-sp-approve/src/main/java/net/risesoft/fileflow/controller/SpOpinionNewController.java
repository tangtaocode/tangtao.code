package net.risesoft.fileflow.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.common.util.CommentUtil;
import net.risesoft.fileflow.entity.jpa.OpinionNew;
import net.risesoft.fileflow.repository.jpa.OpinionNewRepository;
import net.risesoft.fileflow.service.SpOpinionNewService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sp/opinion4New")
public class SpOpinionNewController {
	
	@Resource(name="spOpinionNewService")
	private SpOpinionNewService opinionNewService;

	@Autowired
	private OpinionNewRepository opinionRepository;

	@RequestMapping(value = "/newOrModify/personalComment")
	public String personalComment(@RequestParam String SPinstanceId,@RequestParam String processSerialNumber, @RequestParam String processInstanceId, @RequestParam String taskId, @RequestParam String category, @RequestParam String id, Model model) {
		Person person = ThreadLocalHolder.getPerson();
		try {
			String commentStr = getCommentStr();
			OpinionNew opinion = null;
			if (StringUtils.isNotBlank(id)) {
				opinion = opinionNewService.findOne(id);
				if (opinion.getIsAgent() == 1)
					model.addAttribute("onlyAddAgentComment", true);
			} else {
				Integer count = 0;
				if (StringUtils.isNotBlank(taskId)) {
					count = opinionNewService.getCount4Personal(processSerialNumber, taskId, category, person.getID());
				} else {
					count = opinionNewService.getCount4Personal(processSerialNumber,category, person.getID());
				}
				if (count >= 1)
					model.addAttribute("onlyAddAgentComment", true);// 只能新增代录意见
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("SPinstanceId",SPinstanceId);//审批业务数据主键Id
			model.addAttribute("opinion", opinion);
			model.addAttribute("date", (opinion==null||opinion.getRealityDate()==null)?sdf.format(new Date()):sdf.format(opinion.getRealityDate()));
			model.addAttribute("userName", person.getName());
			model.addAttribute("commentStr", commentStr);
			model.addAttribute("processSerialNumber", processSerialNumber);
			model.addAttribute("processInstanceId", processInstanceId);
			model.addAttribute("taskId", taskId);
			model.addAttribute("category", category);
			return "eform/personalComment";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/500";
	}

	
	@ResponseBody
	@RequestMapping(value = "/personCommentList")
	public List<Map<String, Object>> personCommentList(@RequestParam String SPinstanceId,@RequestParam String processSerialNumber, @RequestParam Integer mainAndSub, @RequestParam String itembox, @RequestParam String readOnly, @RequestParam String activitiUser, @RequestParam String processInstanceId, @RequestParam String taskId, @RequestParam String category) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			listMap = opinionNewService.personCommentList(SPinstanceId,processSerialNumber, processInstanceId, taskId, itembox, activitiUser, mainAndSub, category, readOnly);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMap;
	}

	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> save(OpinionNew opinion) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean opinionSign = false;
			if(!("".equals(opinion.getSignTxt()))&&opinion.getSignTxt()!=null){
				opinionSign = opinionNewService.opinionSign(opinion.getCertTxt(), opinion.getSignTxt(), opinion.getDataTxt());
				if(!opinionSign){//签名失败
					opinion.setCertTxt("");
					opinion.setSignTxt("");
					opinion.setDataTxt("");
				}
			}else{
				opinionSign = true;
			}
			map.put("opinionSign", opinionSign);//true为签名成功，false为签名失败
			opinionNewService.saveOrUpdate(opinion);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取系统常用语
	 * @return
	 */
	private String getCommentStr() {
		String[] comment = CommentUtil.getComment();
		String commentStr = "";
		for (String option : comment) {
			commentStr += "<option value=\"" + option + "\">" + option + "</option>";
		}
		return commentStr;
	}
	
	/**
	 * 获取无纸化意见签名事项
	 * @param SPinstanceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSpmApproveitem",method=RequestMethod.POST)
	public Map<String, Object> getSpmApproveitem(@RequestParam String SPinstanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = opinionNewService.getSpmApproveitem(SPinstanceId);
		return map;
	}
	
}