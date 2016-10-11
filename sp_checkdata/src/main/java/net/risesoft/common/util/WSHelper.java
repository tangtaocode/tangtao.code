package net.risesoft.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import egov.appservice.ac.exception.AccessControlException;
import egov.appservice.ac.exception.AccessManagerException;
import egov.appservice.ac.model.Operation;
import egov.appservice.ac.model.Resource;
import egov.appservice.ac.service.AccessControlService;
import egov.appservice.ac.service.AccessGrantService;
import egov.appservice.ac.service.DomainManager;
import egov.appservice.ac.service.ResourceManager;
import egov.appservice.asf.exception.ServiceClientException;
import egov.appservice.asf.service.ServiceClient;
import egov.appservice.asf.service.ServiceClientFactory;
import egov.appservice.org.exception.OrgRelationManageException;
import egov.appservice.org.exception.OrgRelationRetrieveException;
import egov.appservice.org.exception.OrgUnitManageException;
import egov.appservice.org.model.Department;
import egov.appservice.org.model.Group;
import egov.appservice.org.model.Person;
import egov.appservice.org.model.Role;
import egov.appservice.org.service.DepartmentManager;
import egov.appservice.org.service.GroupManager;
import egov.appservice.org.service.OrganizationManager;
import egov.appservice.org.service.PersonManager;
import egov.appservice.org.service.RoleManager;

/**
 * 
 * RC7服务调用帮助类
 * 
 * @author sugobin
 * 
 */
public class WSHelper {

	private static List<Resource> resources;

	private static WSHelper instance;

	public synchronized static WSHelper getInstance() {
		resources = new ArrayList<Resource>();
		if (instance == null) {
			instance = new WSHelper();
		}
		return instance;
	}

	/**
	 * 获取指定Actor对象针对某一种operation的所能访问的资源
	 * 
	 * @param actorUID
	 *            操作者唯一标识
	 * @param opretionKey
	 *            操作类型
	 * @param resourceUID
	 *            资源范围
	 * @param properties
	 *            扩展属性
	 * @return
	 */
	public Map<String, List<Resource>> getResources(String actorUID, String opretionKey, String resourceUID, Map<String, String> properties) {
		Map<String, List<Resource>> map = null;
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		AccessControlService acs;
		try {
			acs = (AccessControlService) sc.getServiceByName("ac.AccessControlService");
			map = acs.getResources(actorUID, opretionKey, resourceUID, properties);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		}
		return map;

	}

	/**
	 * 判断Actor对Resource是否有指定操作
	 * 
	 * @param actorUID
	 *            操作者唯一标识
	 * @param opretionKey
	 *            操作类型
	 * @param resourceUID
	 *            资源唯一标识
	 * @param properties
	 *            扩展属性
	 * @return
	 */
	public boolean hasPermission(String actorUID, String resourceUID, String opretionKey) {
		boolean hasPermission = false;
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		AccessControlService acs;
		try {
			acs = (AccessControlService) sc.getServiceByName("ac.AccessControlService");
			hasPermission = acs.hasPermission(actorUID, resourceUID, opretionKey);
		} catch (AccessControlException e) {
			e.printStackTrace();
		} catch (ServiceClientException e) {
			e.printStackTrace();
		}
		return hasPermission;

	}

	/**
	 * 获得指定Actor对象对指定Resource的操作
	 * 
	 * @param actorUID
	 *            操作者唯一标识
	 * @param resourceUID
	 *            资源唯一标识
	 * @param properties
	 *            扩展属性
	 * @return
	 */
	public List<Operation> getOperations(String actorUID, String resourceUID, Map<String, String> properties) {
		List<Operation> operations = null;
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		AccessControlService acs;
		try {
			acs = (AccessControlService) sc.getServiceByName("ac.AccessControlService");
			operations = acs.getOperations(actorUID, resourceUID, properties);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		}
		return operations;

	}

	/**
	 * 获取用户能访问的资源
	 * 
	 * @param actorUID
	 *            操作者唯一标识
	 * @param opretionKey
	 *            操作类型
	 * @param resourceUID
	 *            资源唯一标识
	 * @param properties
	 *            扩展属性
	 * @return
	 */
	public List<Resource> getSubResources(String actorUID, String opretionKey, String resourceUID, Map<String, String> properties) {
		List<Resource> resources = null;
//		ServiceClient sc = ServiceClientFactory.getServiceClient();
		AccessControlService acs;
		acs = RC7Finder.getServiceManagerClient("ac.AccessControlService");
		//sc.getServiceByName("ac.AccessControlService");
		resources = acs.getSubResources(actorUID, opretionKey, resourceUID, properties);
		return resources;

	}

	/**
	 * 递归获取用户能访问的资源
	 * 
	 * @param actorUID
	 *            操作者唯一标识
	 * @param opretionKey
	 *            操作类型
	 * @param resourceUID
	 *            资源唯一标识
	 * @param properties
	 *            扩展属性
	 * @return
	 */
	public List<Resource> getSubResourcesByRecursive(String actorUID, String opretionKey, String resourceUID, Map<String, String> properties) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		AccessControlService acs;
		try {
			acs = (AccessControlService) sc.getServiceByName("ac.AccessControlService");
			List<Resource> list = acs.getSubResources(actorUID, opretionKey, resourceUID, properties);
			for (Resource res : list) {
				resources.add(res);
				if (res.getUrl() == null) {
					List<Resource> lst = getSubResourcesByRecursiveChild(actorUID, opretionKey, res.getUID(), properties);
					for (Resource r : lst) {
						resources.add(r);
					}
				}
			}
		} catch (ServiceClientException e) {
			e.printStackTrace();
		}
		return resources;
	}

	/**
	 * 
	 * @param list
	 * @param actorUID
	 * @param opretionKey
	 * @param resourceUID
	 * @param properties
	 */
	public void recursiveResources(AccessControlService acs, List<Resources> list, String actorUID, String opretionKey, String resourceUID, Map<String, String> properties) {
		List<Resource> lst = acs.getSubResources(actorUID, opretionKey, resourceUID, properties);
		for (Resource res : lst) {
			Resources rs = new Resources();
			rs.setResource(res);
			if (StringUtils.isBlank(res.getUrl())) {
				rs.setHasChild(true);
				List<Resources> list1 = new ArrayList<Resources>();
				rs.setList(list1);
				recursiveResources(acs, list1, actorUID, opretionKey, res.getUID(), properties);
			} else {
				rs.setHasChild(false);
			}
			list.add(rs);
		}

	}

	/**
	 * 修改密码
	 * 
	 * @param rcid
	 *            用户的rc7id
	 * @param newpassword
	 *            新密码
	 */
	public void updatePassword(String rcid, String newpassword) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		PersonManager personManager;
		try {
			personManager = sc.getServiceByName("org.PersonManager");
			personManager.editPassword(rcid, newpassword);
		} catch (ServiceClientException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * 修改用户信息
	 * 
	 * @param person
	 *            用户
	 * @param isVersion
	 *            是否生成新版本
	 */
	public void updatePersonInfo(Person person, boolean isVersion) {		
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		PersonManager personManager;
		try {
			personManager = sc.getServiceByName("org.PersonManager");
			personManager.update(person, isVersion);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch(OrgUnitManageException oume){
			oume.printStackTrace();
		}
	}
	
	/***
	 * 
	 * @param personUID
	 * @return
	 */
	
	public Person getPerson(String personUID){
//		ServiceClient sc = ServiceClientFactory.getServiceClient();
//		PersonManager personManager;
//		Person person = null;
//		try {
//			
//			personManager = sc.getServiceByName("org.PersonManager");
//			person = personManager.getPerson(personUID);
//		} catch (ServiceClientException e) {
//			e.printStackTrace();
//		}
		PersonManager personManager;
		Person person = null;
		personManager = RC7Finder.getServiceManagerClient("org.PersonManager");
		person = personManager.getPerson(personUID);
		return person;
	}

	/**
	 * 递归获取用户能访问的资源
	 * 
	 * @param actorUID
	 *            操作者唯一标识
	 * @param opretionKey
	 *            操作类型
	 * @param resourceUID
	 *            资源唯一标识
	 * @param properties
	 *            扩展属性
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getAllResources(String actorUID, String opretionKey, String resourceUID, Map<String, String> properties) {
		Map map = new HashMap();
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		AccessControlService acs;
		try {
			acs = (AccessControlService) sc.getServiceByName("ac.AccessControlService");
			map = acs.getResources(actorUID, opretionKey, resourceUID, properties);
		} catch (ServiceClientException e) {

			e.printStackTrace();
		}
		List listKey = new ArrayList();
		List listValue = new ArrayList();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			listKey.add(key);
			listValue.add(map.get(key));
		}
		for (int i = 0; i < listKey.size(); i++) {
			System.out.println("Key:" + listKey.get(i));
			System.out.println("Value:" + listValue.get(i));
		}
		return map;
	}

	public List<Resource> getSubResourcesByRecursiveChild(String actorUID, String opretionKey, String resourceUID, Map<String, String> properties) {

		ServiceClient sc = ServiceClientFactory.getServiceClient();
		AccessControlService acs;
		List<Resource> list = null;
		try {
			acs = sc.getServiceByName("ac.AccessControlService");
			list = acs.getSubResources(actorUID, opretionKey, resourceUID, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 判断指定资源下是否有子资源
	 * 
	 * @param resourceUID
	 * @return
	 */
	public boolean hasSubResource(String resourceUID) {
		List<Resource> resources = null;
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		ResourceManager rm;
		try {
			rm = (ResourceManager) sc.getServiceByName("ac.ResourceManager");
			resources = rm.getSubResources(resourceUID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		}
		return resources.size() > 0;
	}

	/**
	 * 获取指定资源的父资源
	 * 
	 * @param resourceUID
	 * @return
	 */
	public Resource getParentResource(String resourceUID) {
		Resource resource = null;
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		ResourceManager rm;
		try {
			rm = (ResourceManager) sc.getServiceByName("ac.ResourceManager");
			resource = rm.getParentResource(resourceUID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		}
		return resource;
	}

	/**
	 * @Title: getAllPersons
	 * @Description: 通过rc7唯一标识，获取部门下全部人员
	 * @return List
	 * @param departmentUID
	 *            部门id
	 * @return
	 */
	public List<Person> getAllPersons(String departmentUID) {
		List<Person> list = new ArrayList<Person>();
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		DepartmentManager dm;
		try {
			dm = (DepartmentManager) sc.getServiceByName("org.DepartmentManager");
			if (!"".equals(departmentUID) && departmentUID != null) {

				list = dm.getAllPersons(departmentUID);
			}
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationRetrieveException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * @Title: getRoles
	 * @Description: 通过rc7中的唯一标识获取部门下直接角色 不递归
	 * @return List
	 * @param departmentUID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getRoles(String departmentUID) {
		List list = new ArrayList();
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		DepartmentManager dm;
		try {
			if (null != departmentUID && !"".equals(departmentUID)) {
				dm = (DepartmentManager) sc.getServiceByName("org.DepartmentManager");
				list = dm.getRoles(departmentUID);
			}
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationRetrieveException e) {

			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getNomRoles
	 * @Description: 通过rc7中的唯一标识 ，获取通用角色
	 * @return List
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getNomRoles() {
		List list = new ArrayList();
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		OrganizationManager dm;
		try {
			dm = (OrganizationManager) sc.getServiceByName("org.OrganizationManager");
			list = dm.getRoles("_klz3MAYsEeKqGut6st6AFw");
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationRetrieveException e) {

			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getPersonRolesMap
	 * @Description: 执行对人员角色的检索，并放到map中，KEY为人员ID，value为角色list
	 * @return Map<String,List<Role>>
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, List<Role>> getPersonRolesMap(List list) {
		Map<String, List<Role>> map = new HashMap<String, List<Role>>();
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		PersonManager pm;
		try {
			pm = (PersonManager) sc.getServiceByName("org.PersonManager");
			for (int i = 0; i < list.size(); i++) {
				Person person = (Person) list.get(i);
				String personUID = person.getUID();
				List<Role> rolesList = pm.getRoles(personUID);
				map.put(personUID, rolesList);
			}
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationRetrieveException e) {

			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @Title: getPersonRolesMap
	 * @Description: 执行对人员角色的检索，并放到map中，KEY为人员ID，value为角色list
	 * @return Map<String,List<Role>>
	 * @param list
	 * @return
	 */
	public List<Role> getPersonRoles(String personUID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		List<Role> rolesList = new ArrayList<Role>();
		PersonManager pm;
		try {
			pm = (PersonManager) sc.getServiceByName("org.PersonManager");
			rolesList = pm.getRoles(personUID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationRetrieveException e) {

			e.printStackTrace();
		}
		return rolesList;
	}

	/**
	 * @Title: savePersonRole
	 * @Description: 保存人员角色
	 * @return void
	 * @param personID
	 * @param roleID
	 */
	public void savePersonRole(String personID, String roleID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		RoleManager rm;
		try {
			rm = (RoleManager) sc.getServiceByName("org.RoleManager");
			rm.addPerson(roleID, personID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationManageException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @Title: deletePersonRole
	 * @Description: 删除人员角色
	 * @return void
	 * @param personID
	 * @param roleID
	 */
	public void deletePersonRole(String personID, String roleID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		RoleManager rm;
		try {
			rm = (RoleManager) sc.getServiceByName("org.RoleManager");
			rm.removePerson(roleID, personID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationManageException e) {

			e.printStackTrace();
		}
	}

	public List<Person> getPersonByRoleUID(String roleUID) {
		List<Person> list = new ArrayList<Person>();
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		RoleManager rm;
		try {
			rm = (RoleManager) sc.getServiceByName("org.RoleManager");
			list = rm.getPersons(roleUID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationRetrieveException e) {
			e.printStackTrace();
		}
		return list;
	}

	/***
	 * 用户判断该用户是否是单位管理员
	 * 
	 * @param rcid
	 * @return
	 */
	public int isAanWeiAdmin(String rcid) {
		// 开发环境
		List<Person> list = WSHelper.getInstance().getPersonByRoleUID("_TzTmcJIdEeKbS-Dwc2DNAg");
		// 正式环境
		// List<Person> list =
		// WSHelper.getInstance().getPersonByRoleUID("__zxu0KBzEeKom5hcWUgaUA");
		for (int i = 0; i < list.size(); i++) {
			Person person = list.get(i);

			if (rcid.equals(person.getUID()))
				return 1;
		}
		return 0;
	}

	/**
	 * 取得用户组的人员列表
	 * 
	 * @param groupid
	 * @return
	 */
	public List<Person> getPersonsByGroupId(String gruopid) {
		List<Person> list = new ArrayList<Person>();
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		GroupManager gm;
		try {
			gm = (GroupManager) sc.getServiceByName("org.GroupManager");
			list = gm.getPersons(gruopid);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgRelationRetrieveException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 建立指定的Actor对象集合和指定权限集合的关联关系
	 * 
	 * @param actorUIDs操作者唯一标识集合
	 * @param resourceUIDs资源唯一标识集合
	 * @param operationKeys操作类型集合
	 * @return
	 */
	public boolean grantActorPermission(String[] actorUIDs, String[] resourceUIDs, String[] operationKeys) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		boolean flag = false;
		try {
			AccessGrantService ags = sc.getServiceByName("ac.AccessGrantService");
			flag = ags.grantActorPermission(actorUIDs, resourceUIDs, operationKeys);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (AccessManagerException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 服务描述: 建立Actor对象和权限的关联关系
	 * 
	 * 参数说明:
	 * 
	 * actorUID: 操作者唯一标识
	 * 
	 * resourceUID: 资源唯一标识
	 * 
	 * operationKey:操作类型
	 * 
	 * @return boolean
	 */
	public boolean grantActorPermission(String actorUID, String resourceUID, String operationKey) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		boolean flag = false;
		try {
			AccessGrantService ags = sc.getServiceByName("ac.AccessGrantService");
			flag = ags.grantActorPermission(actorUID, resourceUID, operationKey);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (AccessManagerException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获得指定资源对象
	 * 
	 * @param resourceUID
	 *            资源唯一标识
	 */
	public Resource getResource(String resourceUID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			ResourceManager rm = sc.getServiceByName("ac.ResourceManager");
			return rm.getResource(resourceUID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得指定资源对象直接包含的对象
	 * 
	 * @param resourceUID
	 *            资源唯一标识
	 * @return 资源对象列表
	 */
	public List<Resource> getSubResources(String resourceUID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			ResourceManager rm = sc.getServiceByName("ac.ResourceManager");
			return rm.getSubResources(resourceUID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除权限
	 * 
	 * @param actorUID
	 * @param resourceUID
	 * @param operationKey
	 * @return
	 */
	public boolean deleteActorPermision(String actorUID, String resourceUID, String operationKey) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			AccessGrantService ags = sc.getServiceByName("ac.AccessGrantService");
			return ags.deleteActorPermision(actorUID, resourceUID, operationKey);
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取部门下的所有用户组
	 * 
	 * @param deptID
	 *            部门ID
	 */
	public List<Group> getGroupsByDeptID(String deptID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			DepartmentManager departmentManager = sc.getServiceByName("org.DepartmentManager");
			return departmentManager.getGroups(deptID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return null;
		} catch (OrgRelationRetrieveException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取用户组下面的人员
	 * 
	 * @param groupID
	 *            用户组ID
	 */
	public List<Person> getPersonsByGroupID(String groupID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			GroupManager groupManager = sc.getServiceByName("org.GroupManager");
			return groupManager.getPersons(groupID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return null;
		} catch (OrgRelationRetrieveException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 创建用户组
	 * 
	 * @param parentUID
	 *            父节点对象唯一标识
	 * @param groupName
	 *            用户组名称
	 * @param desc
	 *            权限说明
	 */
	public Group createGroup(String parentUID, String groupName, String desc) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			GroupManager groupManager = sc.getServiceByName("org.GroupManager");
			Group group = groupManager.create(parentUID);
			group.setName(groupName);
			group.setCreateTime(new Date());
			group.setDescription(desc);
			return groupManager.update(group);
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return null;
		} catch (OrgUnitManageException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 创建用户组
	 * 
	 * @param groupID
	 *            用户组唯一标识
	 * @param groupName
	 *            用户组名称
	 * @param desc
	 *            权限说明
	 */
	public void updateGroup(String groupID, String groupName, String desc) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			GroupManager groupManager = sc.getServiceByName("org.GroupManager");
			Group group = groupManager.getGroup(groupID);
			group.setName(groupName);
			group.setCreateTime(new Date());
			group.setDescription(desc);
			groupManager.update(group);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgUnitManageException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除用户组
	 */
	public void deleteGroup(String groupID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			GroupManager groupManager = sc.getServiceByName("org.GroupManager");
			groupManager.delete(groupID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
		} catch (OrgUnitManageException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 往用户组增加人员 groupUID: 用户组唯一标识 personUID: 人员唯一标识
	 */
	public void addPersonToGroup(String groupUID, String personUID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			GroupManager groupManager = sc.getServiceByName("org.GroupManager");
			groupManager.addPerson(groupUID, personUID);
		} catch (OrgRelationManageException e) {
			e.printStackTrace();
		} catch (ServiceClientException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除用户组人员 groupUID: 用户组唯一标识 personUID: 人员唯一标识
	 */
	public void removePersonToGroup(String groupUID, String personUID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			GroupManager groupManager = sc.getServiceByName("org.GroupManager");
			groupManager.removePerson(groupUID, personUID);
		} catch (OrgRelationManageException e) {
			e.printStackTrace();
		} catch (ServiceClientException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 getSubDepartments
	 * 
	 * 获取子部门
	 * 
	 * java.lang.String departmentUID
	 * 
	 * 
	 * java.util.List
	 * 
	 * 
	 * 
	 * 服务描述:
	 * 
	 * 参数说明:
	 * 
	 * departmentUID: 部门唯一标识
	 * 
	 * 
	 * 异常说明:
	 * 
	 * egov.appservice.org.exception.OrgRelationRetrieveException:
	 * 
	 * 
	 * 返回值说明: 部门对象列表
	 */
	public List<Department> getSubDepartments(String deptRcid) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			DepartmentManager departmentManager = sc.getServiceByName("org.DepartmentManager");
			return departmentManager.getSubDepartments(deptRcid);
		} catch (OrgRelationRetrieveException e) {
			e.printStackTrace();
			return null;
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取部门下的人员
	 * 
	 * @param deptRcid
	 * @return
	 */
	public List<Person> getPersonByDepartment(String deptRcid) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			DepartmentManager departmentManager = sc.getServiceByName("org.DepartmentManager");
			return departmentManager.getPersons(deptRcid);
		} catch (OrgRelationRetrieveException e) {
			e.printStackTrace();
			return null;
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取域对像的人员ID能访问的资源
	 * @param domainPersonID
	 * @return
	 */
	public List<Resource> getResourceByPersonId(String domainPersonID) {
		ServiceClient sc = ServiceClientFactory.getServiceClient();
		try {
			DomainManager dm = sc.getServiceByName("ac.DomainManager");
			return dm.getResources(domainPersonID);
		} catch (ServiceClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		WSHelper.getInstance().addPersonToGroup("_kj_ugLvNEeO1v5RDYyM9Kg", "_10sgYB_bEeK9qP_70Nt6Jw");
	}

}
