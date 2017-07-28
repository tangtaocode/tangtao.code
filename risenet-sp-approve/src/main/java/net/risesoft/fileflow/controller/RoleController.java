package net.risesoft.fileflow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.risesoft.fileflow.service.ObjectPermissionService;
import net.risesoft.fileflow.service.RoleService;
import net.risesoft.fileflow.service.TaskConfService;
import net.risesoft.model.Role;
import net.risesoft.util.RisesoftUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private ObjectPermissionService objectPermissionService;

	@Autowired
	private TaskConfService taskConfService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "config/show")
	public String tenantList() {
		return "config/role/roleList";
	}

	@ResponseBody
	@RequestMapping("/findAll")
	public String findAll(String id) {
		List<Role> listRole = RisesoftUtil.getRoleManager().getRoleByParentID(id);
		JSONArray jsonArray = new JSONArray();
		for (Role role : listRole) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("id", role.getID());
			jsonObject.accumulate("name", role.getName());
			jsonObject.accumulate("pid", id);
			if ("role".equals(role.getType())) {
				jsonObject.accumulate("isParent", false);
			} else {
				boolean isP = RisesoftUtil.getRoleManager().getRoleByParentID(role.getID()).size() > 0 ? true : false;
				if (isP) {
					jsonObject.accumulate("chkDisabled", true);
				}
				jsonObject.accumulate("isParent", isP);
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();

	}

	@ResponseBody
	@RequestMapping("/findPermUser")
	public List<Map<String, Object>> findPermUser(@RequestParam(required = false) String processDefinitionId, @RequestParam(required = false) String taskDefKey, @RequestParam(required = false) Integer principalType, @RequestParam(required = false) Boolean isPerm, @RequestParam(required = false) String id) {
		List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
		if (StringUtils.isBlank(processDefinitionId)) {
			return item;
		} else {
			item = roleService.findPermUser(processDefinitionId, taskDefKey, principalType, isPerm, id);
			return item;
		}
	}
	
	/**
	 * 发送选人界面的搜索，目前只针对部门
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @param principalType
	 * @param isPerm
	 * @param id
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findPermUserByName")
	public List<Map<String, Object>> findPermUserByName(@RequestParam(required = false) String processDefinitionId,
			@RequestParam(required = false) String taskDefKey,
			@RequestParam(required = false) Integer principalType,
			@RequestParam(required = false) Boolean isPerm,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String name) {
		List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
		if (StringUtils.isBlank(processDefinitionId)) {
			return item;
		} else {
			//item = roleService.findPermUserByName(processDefinitionId, taskDefKey, principalType, isPerm, id,name);
			return item;
		}
	}
	
	/**
	 * 委办局树查找
	 * @param name
	 * @param treeType
	 * @param nodeId
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bureauTreeSearch")
	public List<Map<String, Object>> bureauTreeSearch(String name,String nodeId) {
		List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
		//item = roleService.bureauTreeSearch(name, nodeId);
		return item;
	}

}
