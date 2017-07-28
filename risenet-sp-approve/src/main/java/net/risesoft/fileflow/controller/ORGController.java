package net.risesoft.fileflow.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.risesoft.model.OrgUnit;
import net.risesoft.model.Organization;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app/org")
public class ORGController {
	
	@Value("${risebpm7.shouFaYuan}")
	private String shouFaYuan;
	
	/**
	 * 组织结构(多种树)
	 * turn to the organization index page
	 * @return
	 */
	@RequestMapping(value="/orgStructure")
	public String orgStructure(String treeType,Model model){
		model.addAttribute("treeType", treeType);
		return "/app/orgStructure";
	}
	
	/**
	 * 委办局树
	 * turn to the organization index page
	 * @return
	 */
	@RequestMapping(value="/orgBureau")
	public String orgBureau(String treeType,Model model){
		model.addAttribute("treeType", treeType);
		return "/matterAuditRole/orgBureau";
	}
	
	/**
	 * 组织机构列表 get the list of organization
	 * 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public List<OrgUnit> list() {
		String tenantId = ThreadLocalHolder.getTenantId();
		List<OrgUnit> orgList = new ArrayList<OrgUnit>();
		orgList.addAll(RisesoftUtil.getOrganizationManager().getAllOrganizations());
		return orgList;
	} 
	
	/**
	 * 获取委办局 
	 * 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/getBureau")
	public List<OrgUnit> getBureau() {
		Person person = ThreadLocalHolder.getPerson();
		List<OrgUnit> orgList = new ArrayList<OrgUnit>();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		orgList.add(org);
		return orgList;
	} 

	/**
	 * 获取机构树子节点 get the children node of the current node
	 * 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/getTree")
	public List<Map<String, Object>> getTree(String ID, String treeType) {
		List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
		String tenantId = ThreadLocalHolder.getTenantId();
		List<OrgUnit> orgUnitList =  RisesoftUtil.getOrgUnitManager().getSubTree(ID,treeType);
		for(int i = 0;i<orgUnitList.size();i++){
			List<OrgUnit> orgUnit = RisesoftUtil.getOrgUnitManager().getSubTree(orgUnitList.get(i).getID(), treeType);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", orgUnitList.get(i).getID());
			map.put("customID", orgUnitList.get(i).getCustomID());
			map.put("name", orgUnitList.get(i).getName());
			map.put("orgType", orgUnitList.get(i).getOrgType());
			map.put("parentID", orgUnitList.get(i).getParentID());
			map.put("DN", orgUnitList.get(i).getDN());
			if("Person".equals(orgUnitList.get(i).getOrgType())){
				Person person=RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(),orgUnitList.get(i).getID());
				map.put("sex", person.getSex());
				map.put("duty", person.getDuty());
			}
			if(orgUnit.size()>0){
				map.put("isParent", true);
			}
			item.add(map);
		}
		return item;
	}
	
	/**
	 * 获取收发员部门树,只显示收发员所在的部门
	 * 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeptTree")
	public List<OrgUnit> getDeptTree() {
		String tenantId = ThreadLocalHolder.getTenantId();
		List<OrgUnit> orgUnitList = new ArrayList<OrgUnit>();
		List<Person> personList = RisesoftUtil.getRoleManager().getPersonsByID("",shouFaYuan);
		for(int i = 0;i<personList.size();i++){
			Person person= RisesoftUtil.getPersonManager().getPerson(tenantId, personList.get(i).getID());
			recursionUpToOrg(tenantId,person.getParentID(), orgUnitList,false);
		}
		/*List<OrgUnit> orgUnitList =  RisesoftUtil.getOrgUnitManager().getSubTree(tenantId,ID,treeType);
		for(int i = 0;i<orgUnitList.size();i++){
			List<OrgUnit> orgUnit = RisesoftUtil.getOrgUnitManager().getSubTree(tenantId, orgUnitList.get(i).getID(), treeType);
			if(orgUnit.size()>0){
				orgUnitList.get(i).setDescription("parent");
			}
		}*/
		return orgUnitList;
	}
	
	private void recursionUpToOrg(String tenantId,String parentID, List<OrgUnit> orgUnitList,boolean isParent) {
		OrgUnit parent = (OrgUnit)getParent(tenantId,parentID);
		if(isParent){//判定是否是父节点
			parent.setDescription("parent");
		}
		if(orgUnitList.size()==0){
			orgUnitList.add(parent);
		}else{
			String add = "true";
			for(int i = 0;i<orgUnitList.size();i++){
				if(orgUnitList.get(i).getID().equals(parent.getID())){
					add = "false";
					break;
				}
			}
			if(add == "true"){
				orgUnitList.add(parent);
			}
		}
		/*if(orgUnitList.size()==0){
			orgUnitList.add(parent);
		}else{
			if (!orgUnitList.contains(parent)) {
				orgUnitList.add(parent);
			}
		}*/
		if (parent.getOrgType().equals("Department")) {
			recursionUpToOrg(tenantId,parent.getParentID(), orgUnitList,true);
		}
	}
	
	public OrgUnit getParent(String tenantId,String parentID) {
		Organization  parent = RisesoftUtil.getOrganizationManager().getOrganization(parentID);
		return parent.getID() != null ? parent : RisesoftUtil.getDepartmentManager().getDepartment(parentID);
	}
	
	@ResponseBody
	@RequestMapping(value = "/treeSearch")
	public List<Map<String, Object>> treeSearch(String name, String treeType) {
		String tenantId = ThreadLocalHolder.getTenantId();
		List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
		List<OrgUnit> orgUnitList = RisesoftUtil.getOrgUnitManager().treeSearch(name,treeType);
		for(int i = 0;i<orgUnitList.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", orgUnitList.get(i).getID());
			map.put("customID", orgUnitList.get(i).getCustomID());
			map.put("name", orgUnitList.get(i).getName());
			map.put("orgType", orgUnitList.get(i).getOrgType());
			map.put("parentID", orgUnitList.get(i).getParentID());
			map.put("DN", orgUnitList.get(i).getDN());
			if("Person".equals(orgUnitList.get(i).getOrgType())){
				Person person=RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(),orgUnitList.get(i).getID());
				map.put("sex", person.getSex());
				map.put("duty", person.getDuty());
			}
			item.add(map);
		}
		return item;
	}

}
