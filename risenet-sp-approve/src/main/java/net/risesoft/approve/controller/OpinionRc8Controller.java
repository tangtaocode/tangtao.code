package net.risesoft.approve.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.risesoft.common.util.CommentUtil;
import net.risesoft.common.util.DateConverter;
import net.risesoft.common.util.OpinionUtil;
import net.risesoft.fileflow.entity.jpa.Opinion;
import net.risesoft.fileflow.repository.jpa.OpinionRepository;
import net.risesoft.fileflow.service.OpinionService;
import net.risesoft.model.Department;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Organization;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/opinionRc8"})
public class OpinionRc8Controller
{
	private static final Logger log = LoggerFactory.getLogger(DateConverter.class);

	@Autowired
	private OpinionService opinionService;

	@Autowired
	private OpinionRepository opinionRepository;

	@RequestMapping({"/personCommentList"})
	@ResponseBody
	public List<Map<String, Object>> personCommentList(@RequestParam String processSerialNumber, @RequestParam Integer mainAndSub, @RequestParam String itembox, @RequestParam String readOnly, @RequestParam String activitiUser, @RequestParam String processInstanceId, @RequestParam String taskId, @RequestParam String category) {
		List<Map<String, Object>> ret_list = new ArrayList<Map<String, Object>>();
		OpinionUtil opinionUtil = new OpinionUtil();
		ret_list = opinionUtil.personCommentList(processSerialNumber, processInstanceId, taskId, itembox, activitiUser, mainAndSub, category, readOnly);
		return ret_list; }

	@RequestMapping({"/deptCommentList"})
	@ResponseBody
	public List<Map<String, Object>> deptCommentList(@RequestParam String processSerialNumber, @RequestParam Integer mainAndSub, @RequestParam String itembox, @RequestParam String readOnly, @RequestParam String activitiUser, @RequestParam String processInstanceId, @RequestParam String taskId, @RequestParam String category, @RequestParam String guids) {
		List<Map<String, Object>> ret_list = new ArrayList<Map<String, Object>>();
		OpinionUtil opinionUtil = new OpinionUtil();
		ret_list = opinionUtil.deptCommentList(processSerialNumber, processInstanceId, taskId, itembox, activitiUser, guids, mainAndSub, category, readOnly);
		return ret_list;
	}
	@RequestMapping({"/leaderCommentList"})
	@ResponseBody
	public List<Map<String, Object>> leaderCommentList(@RequestParam String processSerialNumber, @RequestParam Integer mainAndSub, @RequestParam String itembox, @RequestParam String readOnly, @RequestParam String activitiUser, @RequestParam String processInstanceId, @RequestParam String taskId, @RequestParam String category) {
		List<Map<String, Object>> ret_list = new ArrayList<Map<String, Object>>();
		OpinionUtil opinionUtil = new OpinionUtil();
		ret_list = opinionUtil.leaderCommentList(processSerialNumber, processInstanceId, taskId, itembox, activitiUser, mainAndSub, category, readOnly);
		return ret_list;
	}

	@RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	public String add(@RequestParam String processSerialNumber, @RequestParam String processInstanceId, @RequestParam String taskId, @RequestParam String AddEditPcDept, @RequestParam String category, @RequestParam String activitiUser, @RequestParam String opinionContent, @RequestParam Integer opinionType, @RequestParam(required=false) String guids, Model model) {
		Person person = ThreadLocalHolder.getPerson();
		String[] comment = CommentUtil.getComment();
		String commentStr = "";
		for (String option : comment) {
			commentStr = commentStr + "<option value=\"" + option + "\">" + option + "</option>";
		}
		model.addAttribute("processSerialNumber", processSerialNumber);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("AddEditPcDept", AddEditPcDept);
		model.addAttribute("category", category);
		model.addAttribute("userName", person.getName());
		model.addAttribute("opinionContent", opinionContent);
		model.addAttribute("opinionType", opinionType);
		model.addAttribute("guids", guids);
		model.addAttribute("activitiUser", activitiUser);
		model.addAttribute("commentStr", commentStr);

		return "tags/addComment";
	}

	@RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	public String edit(@RequestParam String id, @RequestParam String activitiUser, @RequestParam String AddEditPcDept, @RequestParam String taskId, @RequestParam String category, @RequestParam(required=false) String guids, Model model) {
		String[] comment = CommentUtil.getComment();
		String commentStr = "";
		for (String option : comment) {
			commentStr = commentStr + "<option value=\"" + option + "\">" + option + "</option>";
		}
		Opinion opinion = new Opinion();
		if (StringUtils.isNotBlank(id)) {
			opinion = (Opinion)this.opinionRepository.findOne(id);
		}
		Person person = ThreadLocalHolder.getPerson();
		model.addAttribute("userName", person.getName());
		model.addAttribute("activitiUser", activitiUser);
		model.addAttribute("AddEditPcDept", AddEditPcDept);
		model.addAttribute("guids", guids);
		model.addAttribute("commentStr", commentStr);
		model.addAttribute("opinion", opinion);
		model.addAttribute("taskId", taskId);
		model.addAttribute("category", category);

		return "tags/editComment";
	}
	@RequestMapping({"/save"})
	@ResponseBody
	public Map<String, Object> save(@RequestParam String taskId, @RequestParam String opinionDate, @RequestParam String guids, @RequestParam String activitiUser, @RequestParam String category, @RequestParam String processInstanceId, @RequestParam String opinion, @RequestParam String processSerialNumber, @RequestParam Integer opinionType, @RequestParam String agentUserId, @RequestParam String agentUserName, @RequestParam String agentUserDeptId, @RequestParam Integer isAgent, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Person person = ThreadLocalHolder.getPerson();
		String tenantId = ThreadLocalHolder.getTenantId();
		String[] userIdAnddeptId = activitiUser.split(":");
		String userId = userIdAnddeptId[0];
		String deptId = userIdAnddeptId[1];
		//Organization organization = RisesoftUtil.getOrgUnitManager().getOrganization(tenantId, userId);
		String deptName = "";
		//if (deptId.equals(organization.getID())) {
			//deptName = organization.getName();
		//} else {
			Department riseDepartment = RisesoftUtil.getDepartmentManager().getDepartment(deptId);
			deptName = riseDepartment.getName();
		//}
		String agentUserDeptName = "";
		Integer isDepartmentOpinion = Integer.valueOf(0);

		OrgUnit bureau = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), userId);
		String currentBureauGuid = bureau.getID();
		String currentBureauName = bureau.getName();
		if (StringUtils.isNotBlank(guids)) {
			if ((guids.equals("startDept")) || (guids.equals("currentDept")))
				isDepartmentOpinion = Integer.valueOf(1);
			else if ((guids.equals("startBureau")) || (guids.equals("currentBureau"))) {
				isDepartmentOpinion = Integer.valueOf(2);
			}
		}
		if (agentUserName == "") {
			opinionDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			agentUserName = "";
			agentUserId = "";
			agentUserDeptName = "";
			agentUserDeptId = "";
		} else {
			Department AgentUserdepartment = RisesoftUtil.getDepartmentManager().getDepartment(agentUserDeptId);
			if (AgentUserdepartment != null) {
				agentUserDeptName = AgentUserdepartment.getName();
			} else {
				Organization org = RisesoftUtil.getOrganizationManager().getOrganization( agentUserDeptId);
				agentUserDeptName = org.getName();
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date opiDate = new Date();
		try {
			opiDate = sdf.parse(opinionDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			if (isDepartmentOpinion.intValue() == 2)
				this.opinionService.saveOpinion(processSerialNumber, processInstanceId, taskId, opinionType, tenantId, category, opinion, userId, person.getName(), agentUserId, agentUserName, agentUserDeptId, agentUserDeptName, opiDate, new Date(), isDepartmentOpinion, isAgent, currentBureauGuid, currentBureauName);
			else {
				this.opinionService.saveOpinion(processSerialNumber, processInstanceId, taskId, opinionType, tenantId, category, opinion, userId, person.getName(), agentUserId, agentUserName, agentUserDeptId, agentUserDeptName, opiDate, new Date(), isDepartmentOpinion, isAgent, deptId, deptName);
			}
			map.put("success", Boolean.valueOf(true));
			map.put("msg", "添加意见成功");
		} catch (Exception e) {
			map.put("success", Boolean.valueOf(false));
			map.put("msg", "添加意见失败");
			e.printStackTrace();
		}

		return map;
	}
	@RequestMapping({"/update"})
	@ResponseBody
	public Map<String, Object> update(@RequestParam String id, @RequestParam String taskId, @RequestParam String opinionDate, @RequestParam String activitiUser, @RequestParam String opinion, @RequestParam String agentUserId, @RequestParam String agentUserName, @RequestParam String agentUserDeptId, @RequestParam Integer isAgent, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.opinionService.update(id, taskId, opinionDate, activitiUser, opinion, agentUserId, agentUserName, agentUserDeptId, isAgent);
			map.put("success", Boolean.valueOf(true));
			map.put("msg", "编辑意见成功");
		} catch (Exception e) {
			map.put("success", Boolean.valueOf(false));
			map.put("msg", "编辑意见失败");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping({"/delete"})
	@ResponseBody
	public Map<String, Object> delete(@RequestParam String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.opinionService.delete(id);
			map.put("success", Boolean.valueOf(true));
			map.put("msg", "刪除意见成功");
		} catch (Exception e) {
			map.put("success", Boolean.valueOf(false));
			map.put("msg", "刪除意见失败");
			e.printStackTrace();
		}
		return map;
	}
	@ResponseBody
	@RequestMapping({"/checkSignOpinion"})
	public Map<String, Object> checkSignOpinion(@RequestParam(required=false) String taskId, @RequestParam(required=false) String processSerialNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int count = 0;
			if ((taskId != null) && (taskId != ""))
				count = this.opinionService.getCountByTaskId(taskId);
			else {
				count = this.opinionService.findByProcessSerialNumber(processSerialNumber).size();
			}
			if (count > 0)
				map.put("checkSignOpinion", Boolean.valueOf(true));
			else
				map.put("checkSignOpinion", Boolean.valueOf(false));
		}
		catch (Exception e) {
			log.error("查询" + taskId + "是否签写意见失败！");
			e.printStackTrace();
		}
		return map;
	}
}