package net.risesoft.approve.controller;

import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.risesoft.model.Department;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.model.Role;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/rc8Department"})
public class DepartmentRc8Controller {

	@Value("${risebpm7.systemName}")
	private String systemName;
	
	@Value("${risebpm7.systemCNName}")
	private String systemCNName;
	
	@Value("${risebpm7.rootGUID}")
	private String rootGUID;

	@RequestMapping({"/userChoicePage"})
	public String userChoise(@RequestParam String urd, @RequestParam(required=false) String chkStyle, Model model) {
		model = genUrl(urd, chkStyle, model);
		return "config/userChoice/userChoicePage";
	}

	@RequestMapping({"/userPermChoicePage"})
	public String userPermChoise(@RequestParam(required=false) String urd, @RequestParam(required=false) String chkStyle, @RequestParam(required=false) String hidePrType, Model model) {
		model = genUrl(urd, chkStyle, model);
		if (StringUtils.isNotBlank(hidePrType)) {
			String[] hidePrTypes = hidePrType.split(",");
			for (int i = 0; i < hidePrTypes.length; i++) {
				model.addAttribute("prtype" + hidePrTypes[i], Boolean.valueOf(false));
			}
		}
		return "config/userChoice/userPermChoicePage";
	}

	private Model genUrl(String urd, String chkStyle, Model model) {
		String personId = ThreadLocalHolder.getPerson().getID();
		try {
			OrgUnit orgUnit = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
			if (Integer.toString(2).equals(urd)) {
				model.addAttribute("url", "department/findDeptById");
				model.addAttribute("title1", "部门");

				model.addAttribute("rootGuid", orgUnit.getID());
				model.addAttribute("name", orgUnit.getName());
			} else if (Integer.toString(1).equals(urd)) {
				model.addAttribute("url", "role/findAll");
				model.addAttribute("title1", "角色");

				//Role role = getRootRole(this.systemName);
				//model.addAttribute("rootGuid", role != null ? role.getID() : "");
				//model.addAttribute("name", role != null ? role.getName() : "");
				model.addAttribute("rootGuid", this.rootGUID);
				model.addAttribute("name", this.systemCNName);
			} else if (Integer.toString(4).equals(urd)) {
				model.addAttribute("url", "dynamicRole/list");
				model.addAttribute("title1", "动态角色");

				model.addAttribute("rootGuid", "rootGuid");
				model.addAttribute("name", "动态角色");
			} else if (Integer.toString(3).equals(urd)) {
				model.addAttribute("url", "department/findDeptAndUserById");
				model.addAttribute("title1", "用户");
				model.addAttribute("rootGuid", orgUnit.getID());
				model.addAttribute("name", orgUnit.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!"radio".equals(chkStyle)) {
			chkStyle = "checkbox";
		}
		model.addAttribute("chkStyle", chkStyle);
		model.addAttribute("urd", urd);

		return model;
	}
	
	
	 public Role getRootRole(String systemName)
	  {
	    if ((systemName == null) || (systemName.trim().equals(""))) {
	      return null;
	    }
	    HttpClient client = new HttpClient();
	    client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
	    HttpMethod method = new GetMethod();
	    try
	    {
	      systemName = URLEncoder.encode(systemName, "UTF-8");
	      String url = RisesoftUtil.baseURL + "/role/getAllRole.json" ;
	     // url = "http://10.230.31.67:8000/platform" +url ;
	      method.setPath(url);
	      int httpCode = client.executeMethod(method);
	      if (httpCode == 200)
	      {
	        String response = new String(method.getResponseBodyAsString().getBytes("UTF-8"), "UTF-8");
	        Role role = (Role)RisesoftUtil.objectMapper.readValue(response, Role.class);
	        return role;
	      }
	    }
	    catch (UnsupportedEncodingException e)
	    {
	      e.printStackTrace();
	    }
	    catch (HttpException e1)
	    {
	      e1.printStackTrace();
	    }
	    catch (IOException e1)
	    {
	      e1.printStackTrace();
	    }
	    finally
	    {
	      method.releaseConnection();
	      ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
	    }
	    method.releaseConnection();
	    ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
	    
	    return null;
	  }
	


	@RequestMapping({"/findDeptAndUserById"})
	@ResponseBody
	public List<Map<String, Object>> findDeptAndUserById(@RequestParam String id) {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		items.addAll(genDeptTree(id, Integer.valueOf(0), true, true));
		List<Person> employees = RisesoftUtil.getDepartmentManager().getPersons(id);
		for (Person employee : employees) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", employee.getID());
			map.put("Pid", employee.getParentID());
			map.put("name", employee.getName());
			map.put("sex", Integer.valueOf(employee.getSex()));
			map.put("orgType", employee.getOrgType());
			map.put("isParent", "false");
			items.add(map);
		}
		return items;
	}

	public List<Map<String, Object>> genDeptTree(String deptGuid, Integer principalType, boolean isParent, boolean noCheck) {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		List<Department> deptList = RisesoftUtil.getDepartmentManager().getSubDepartments(deptGuid);
		List<OrgUnit> orgUnitList = new ArrayList<OrgUnit>();
		orgUnitList.addAll(deptList);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (OrgUnit entity : deptList) {
			if (RisesoftUtil.getOrgUnitManager().getParent(entity.getID()) != null) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", entity.getID());
				map.put("Pid", deptGuid);
				map.put("orgType", entity.getOrgType());
				map.put("name", entity.getName());
				if (principalType.intValue() > 0) {
					map.put("principalType", principalType);
				}
				map.put("isParent", Boolean.valueOf(isParent));
				map.put("nocheck", Boolean.valueOf(noCheck));
				listMap.add(map);
			}
		}
		items.addAll(listMap);
		return items;
	}

	@RequestMapping({"/findDeptById"})
	@ResponseBody
	public List<Map<String, Object>> findDeptById(@RequestParam String id) {
		return findDeptById(id, false);
	}

	public List<Map<String, Object>> findDeptById(String id, boolean isBureau) {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		List<Department> departments = RisesoftUtil.getDepartmentManager().getSubDepartments(id);
		for (Department department : departments) {
			Map<String, Object> map = new HashMap<String, Object>();
			if ((department != null) && 
					(RisesoftUtil.getOrgUnitManager().getParent(department.getID()) != null)) {
				map.put("id", department.getID());
				map.put("Pid", department.getParentID());
				map.put("name", department.getName());
				if (isBureau) {
					if (department.isBureau())
						map.put("isParent", "false");
					else {
						map.put("isParent", "true");
					}
				}
				else if (RisesoftUtil.getDepartmentManager().getSubDepartments(department.getID()).size() > 0)
					map.put("isParent", "true");
				else {
					map.put("isParent", "false");
				}

				map.put("halfCheck", "false");
				items.add(map);
			}
		}

		return items;
	}

	@RequestMapping({"/dummy"})
	@ResponseBody
	public Map<String, Object> dummy() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", Boolean.valueOf(true));
		return map;
	}

	@ResponseBody
	@RequestMapping({"/getPersionDepartment"})
	public Map<String, String> getPersionDepartment() {
		Map<String, String> map = new HashMap<String, String>();
		Person person = ThreadLocalHolder.getPerson();
		map.put("deptName", person.getOrganization().getName());
		return map;
	}
}