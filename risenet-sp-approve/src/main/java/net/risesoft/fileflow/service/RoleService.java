package net.risesoft.fileflow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.fileflow.entity.jpa.ObjectPermission;
import net.risesoft.model.Department;
import net.risesoft.model.Group;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.model.Position;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	@Autowired
	private ObjectPermissionService objectPermissionService;

	@Autowired
	private TaskConfService taskConfService;

	@Resource(name = "dynamicRoleMemberService")
	private DynamicRoleMemberService dynamicRoleMemberService;

	public List<Map<String, Object>> findPermUser(String processDefinitionId, String taskDefKey, Integer principalType, Boolean isPerm, String id) {
		List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
		try {
			boolean isSelDept = taskConfService.isSelectedDept(processDefinitionId, taskDefKey);// 查询是否只显示到部门
			//改变principalType来展开部门下的用户组和岗位
			if(!isSelDept){
				if(StringUtils.isNotBlank(id)){
					Group group=RisesoftUtil.getGroupManager().getGroup(id);
					Position position=RisesoftUtil.getPositionManager().getPosition( id);
					if(group.getID()!=null){
						principalType=5;
					}else if(position.getID()!=null){
						principalType=6;
					}
				}
			}
			
			List<ObjectPermission> list = objectPermissionService.findByObjectGuidAndTenantId(processDefinitionId,taskDefKey);
			if (principalType == 2) {// 2 "部门"; 3 "人员";5"用户组";6 "岗位"
				if (StringUtils.isBlank(id)) {// 直接取角色下部门
					List<OrgUnit> deptList = new ArrayList<OrgUnit>();
					for (ObjectPermission o : list) {
						if (o.getPrincipalType() == 1) {
							deptList.addAll(RisesoftUtil.getRoleManager().getOrgUnitsByID(ThreadLocalHolder.getTenantId(), o.getPrincipalGuid(), "Department"));
							deptList.addAll(RisesoftUtil.getRoleManager().getOrgUnitsByID(ThreadLocalHolder.getTenantId(), o.getPrincipalGuid(), "Organization"));
						}
						if (o.getPrincipalType() == 4) {
							List<OrgUnit> orgUnitList = dynamicRoleMemberService.getOrgUnitList(o.getPrincipalGuid());
							for (OrgUnit orgUnit : orgUnitList) {
								if (orgUnit.getOrgType().equals("Department")) {
									deptList.add(orgUnit);
								}
							}
						}
					}
					for (OrgUnit org : deptList) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", org.getID());
						map.put("pId", id);
						map.put("name", org.getName());
						map.put("isPerm", true);
						map.put("isParent", true);
						if(isSelDept){
							if(org.getOrgType().equals("Department")){
								List<Department> deptListTemp=RisesoftUtil.getDepartmentManager().getSubDepartments(org.getID());
								if(deptListTemp.isEmpty())map.put("isParent", false);
							}
						}
						map.put("orgType", org.getOrgType()==null?"Organization":org.getOrgType());
						map.put("principalType", ObjectPermission.PRINCIPALTYPE_DEPARTMENT);
						if(item.contains(map))continue;//去重
						item.add(map);
					}
				} else {// 取部门下的部门或人员
					List<OrgUnit> orgList = new ArrayList<OrgUnit>();
					orgList.addAll(RisesoftUtil.getDepartmentManager().getSubDepartments( id));// 子部门
					orgList.addAll(RisesoftUtil.getDepartmentManager().getPersons( id));
					orgList.addAll(RisesoftUtil.getDepartmentManager().getGroups( id));
					orgList.addAll(RisesoftUtil.getDepartmentManager().getPositions( id));
					for (OrgUnit orgunit : orgList) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", orgunit.getID());
						map.put("pId", id);
						map.put("name", orgunit.getName());
						map.put("isPerm", true);
						map.put("orgType", orgunit.getOrgType());
						map.put("isParent", orgunit.getOrgType().equals("Department")||orgunit.getOrgType().equals("Group")||orgunit.getOrgType().equals("Position") ? true : false);
						if ("Department".equals(orgunit.getOrgType())) {
							map.put("principalType", ObjectPermission.PRINCIPALTYPE_DEPARTMENT);
						} else if ("Group".equals(orgunit.getOrgType())) {
							map.put("principalType", ObjectPermission.PRINCIPALTYPE_GROUP);
						} else if ("Person".equals(orgunit.getOrgType())) {
							Person person=RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(),orgunit.getID());
							map.put("sex", person.getSex());
							map.put("duty", person.getDuty());
							map.put("principalType", ObjectPermission.PRINCIPALTYPE_USER);
						} else if ("Position".equals(orgunit.getOrgType())) {
							map.put("principalType", ObjectPermission.PRINCIPALTYPE_POSITION);// 岗位
						}
						if(item.contains(map))continue;//去重
						item.add(map);
					}
				}
			} else if (principalType == 3) {// 人员
				List<OrgUnit> personList = new ArrayList<OrgUnit>();
				
				for (ObjectPermission o : list) {
					if (o.getPrincipalType() == 1) {
						List<Person> persons = RisesoftUtil.getRoleManager().getPersonsByID(ThreadLocalHolder.getTenantId(), o.getPrincipalGuid());
						personList.addAll(persons);
					}
					if (o.getPrincipalType() == 4) {
						List<OrgUnit> orgUnitList = dynamicRoleMemberService.getOrgUnitList(o.getPrincipalGuid());
						for (OrgUnit orgUnit : orgUnitList) {
							if (orgUnit.getOrgType().equals("Person")) {
								personList.add(orgUnit);
							}
						}
					}
				}
				String departId = ThreadLocalHolder.getPerson().getParentID();
				for (OrgUnit person : personList) {
					Person person1=RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(),person.getID());
					if(!"chengban".equals(taskDefKey)){
						if(departId.equals(person1.getParentID())){
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", person.getID());
							map.put("pId", id);
							map.put("name", person.getName() /* + "(" + p.getDN() + ")" */);
							map.put("isPerm", true);
							map.put("isParent", false);
							map.put("orgType", person1.getOrgType());
							map.put("sex", person1.getSex());
							map.put("duty", person1.getDuty());
							map.put("principalType", ObjectPermission.PRINCIPALTYPE_USER);
							if(item.contains(map))continue;//去重
							item.add(map);
						}
					}else{
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", person.getID());
						map.put("pId", id);
						map.put("name", person.getName() /* + "(" + p.getDN() + ")" */);
						map.put("isPerm", true);
						map.put("isParent", false);
						map.put("orgType", person1.getOrgType());
						map.put("sex", person1.getSex());
						map.put("duty", person1.getDuty());
						map.put("principalType", ObjectPermission.PRINCIPALTYPE_USER);
						if(item.contains(map))continue;//去重
						item.add(map);
					}
				}
			} else if (principalType == 5) {
				if (StringUtils.isBlank(id)) {// 角色下用户组列表
					List<OrgUnit> groupList = new ArrayList<OrgUnit>();
					for (ObjectPermission o : list) {
						if (o.getPrincipalType() == 1) {
							groupList.addAll(RisesoftUtil.getRoleManager().getOrgUnitsByID(ThreadLocalHolder.getTenantId(), o.getPrincipalGuid(), "Group"));
						}
						if (o.getPrincipalType() == 4) {
							List<OrgUnit> orgUnitList = dynamicRoleMemberService.getOrgUnitList(o.getPrincipalGuid());
							for (OrgUnit orgUnit : orgUnitList) {
								if (orgUnit.getOrgType().equals("Group")) {
									groupList.add(orgUnit);
								}
							}
						}
					}
					for (OrgUnit org : groupList) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", org.getID());
						map.put("pId", id);
						map.put("name", org.getName());
						map.put("isPerm", true);
						map.put("isParent", true);
						map.put("orgType", org.getOrgType());
						map.put("principalType", ObjectPermission.PRINCIPALTYPE_GROUP);
						if(item.contains(map))continue;//去重
						item.add(map);
					}
				} else {// 获取用户组下人员
					List<Person> persons = RisesoftUtil.getGroupManager().getPersons( id);
					for (Person p : persons) {
						Person person=RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(),p.getID());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", p.getID());
						map.put("pId", id);
						map.put("name", p.getName());
						map.put("isPerm", true);
						map.put("isParent", false);
						map.put("orgType", p.getOrgType());
						map.put("sex", person.getSex());
						map.put("duty", person.getDuty());
						map.put("principalType", ObjectPermission.PRINCIPALTYPE_USER);
						if(item.contains(map))continue;//去重
						item.add(map);
					}
				}
			} else if (principalType == 6) {
				if (StringUtils.isBlank(id)) {// 角色下岗位列表
					List<OrgUnit> positionList = new ArrayList<OrgUnit>();
					for (ObjectPermission o : list) {
						if (o.getPrincipalType() == 1) {
							positionList.addAll(RisesoftUtil.getRoleManager().getOrgUnitsByID(ThreadLocalHolder.getTenantId(), o.getPrincipalGuid(), "Position"));
						}
						if (o.getPrincipalType() == 4) {
							List<OrgUnit> orgUnitList = dynamicRoleMemberService.getOrgUnitList(o.getPrincipalGuid());
							for (OrgUnit orgUnit : orgUnitList) {
								if (orgUnit.getOrgType().equals("Position")) {
									positionList.add(orgUnit);
								}
							}
						}
					}
					for (OrgUnit org : positionList) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", org.getID());
						map.put("pId", id);
						map.put("name", org.getName());
						map.put("isPerm", true);
						map.put("isParent", true);
						map.put("orgType", org.getOrgType());
						map.put("principalType",ObjectPermission.PRINCIPALTYPE_POSITION);
						if(item.contains(map))continue;//去重
						item.add(map);
					}
				} else {// 获取岗位下人员
					List<Person> persons = RisesoftUtil.getPositionManager().getPersons(id);
					for (Person p : persons) {
						Person person=RisesoftUtil.getPersonManager().getPerson(ThreadLocalHolder.getTenantId(),p.getID());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", p.getID());
						map.put("pId", id);
						map.put("name", p.getName());
						map.put("isPerm", true);
						map.put("isParent", false);
						map.put("orgType", p.getOrgType());
						map.put("sex", person.getSex());
						map.put("principalType", ObjectPermission.PRINCIPALTYPE_USER);
						if(item.contains(map))continue;//去重
						item.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	
}
