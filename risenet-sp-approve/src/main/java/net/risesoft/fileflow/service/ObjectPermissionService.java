package net.risesoft.fileflow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.common.util.GuidUtil;
import net.risesoft.common.util.SysVariables;
import net.risesoft.fileflow.entity.jpa.ObjectPermission;
import net.risesoft.fileflow.repository.jpa.ObjectPermissionRepository;
import net.risesoft.model.OrgUnit;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ObjectPermissionService {

	@Autowired
	private ObjectPermissionRepository objectPermissionRepository;

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@Resource(name = "dynamicRoleMemberService")
	private DynamicRoleMemberService dynamicRoleMemberService;
	
	@Autowired
	private TaskConfService taskConfService;

	/**
	 * 判断当前taskDefKey所拥有的权限角色主体是否包含人员、部门、角色、岗位
	 * 
	 * @param objectGuid
	 * @return
	 */
	public Map<String, Object> getTabMap(String processDefinitionId, String taskDefKey) {
		String tenantId = ThreadLocalHolder.getTenantId();
		boolean isSelDept = taskConfService.isSelectedDept(processDefinitionId, taskDefKey);// 查询是否只显示到部门
		List<ObjectPermission> ObjectPermList = findByObjectGuidAndTenantId(processDefinitionId, taskDefKey);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("existPerson", false);
		map.put("existDepartment", false);
		map.put("existGroup", false);
		map.put("existPosition", false);
		for (ObjectPermission o : ObjectPermList) {
			if (o.getPrincipalType() == ObjectPermission.PRINCIPALTYPE_ROLE) {
				Integer personSize = RisesoftUtil.getRoleManager().getPersonsByID("",o.getPrincipalGuid()).size();
				Integer departmentSize = RisesoftUtil.getRoleManager().getOrgUnitsByID( "",o.getPrincipalGuid(), "Department").size();
				Integer organizationSize = RisesoftUtil.getRoleManager().getOrgUnitsByID("",o.getPrincipalGuid(), "Organization").size();
				Integer groupSize = RisesoftUtil.getRoleManager().getOrgUnitsByID("", o.getPrincipalGuid(), "Group").size();
				Integer positionSize = RisesoftUtil.getRoleManager().getOrgUnitsByID("", o.getPrincipalGuid(), "Position").size();
				if (personSize > 0&&!isSelDept) {
					map.put("existPerson", true);
				}
				if (departmentSize > 0 || organizationSize > 0) {
					map.put("existDepartment", true);
				}
				if (groupSize > 0&&!isSelDept) {
					map.put("existGroup", true);
				}
				if (positionSize > 0&&!isSelDept) {
					map.put("existPosition", true);
				}
			}
			if (o.getPrincipalType() == ObjectPermission.PRINCIPALTYPE_DYNAMICROLE) {
				List<OrgUnit> orgUnitList = dynamicRoleMemberService.getOrgUnitList(o.getPrincipalGuid());
				for (OrgUnit orgUnit : orgUnitList) {
					if (orgUnit.getOrgType().equals("Person")&&!isSelDept) {
						map.put("existPerson", true);
					}
					if (orgUnit.getOrgType().equals("Department")||orgUnit.getOrgType().equals("Organization")) {
						map.put("existDepartment", true);
					}
					if (orgUnit.getOrgType().equals("Group")&&!isSelDept) {
						map.put("existGroup", true);
					}
					if (orgUnit.getOrgType().equals("Position")&&!isSelDept) {
						map.put("existPosition", true);
					}
				}
			}
		}
		return map;
	}

	/*
	 * 根据id查找单个
	 */
	public ObjectPermission findOne(String id) {
		return objectPermissionRepository.findOne(id);
	}

	public void save(ObjectPermission objectPermission) {
		objectPermissionRepository.save(objectPermission);
	}

	/**
	 * 先查找租户自己设置的权限，如果为空，再找系统默认设置的权限
	 * @param objectGuid
	 * @return
	 */
	public List<ObjectPermission> findByObjectGuidAndtenantId(String objectGuid) {
		List<ObjectPermission> list = objectPermissionRepository.findByObjectGuidAndTenantId(objectGuid, ThreadLocalHolder.getTenantId());
		if (list.size() <= 0 || list == null) {
			list = objectPermissionRepository.findByObjectGuid(objectGuid);
			/*if (list.size() <= 0 || list == null) {
				list = objectPermissionRepository.findByObjectGuid(objectGuid.split(SysVariables.COLON)[0]);
			}*/
		}
		return list;
	}

	/**
	 * 获取具有权限的流程定义Id
	 * 由于对流程授权时，客体为“luohubanwen:1:8”或者“luohubanwen:1:8:singleinternalflow
	 * ”，当更新流程时，流程定义Id也会发生变化
	 * 此时流程分为两种情况，分别是部署新流程前存在的正在运行的流程和部署新流程后新启动的流程，例如存在以下四个流程定义Id：
	 * luohubanwen:1:
	 * 8、luohubanwen:2:8879、luohubanwen:3:98761、luohubanwen:4:234423
	 * ，其中luohubanwen:4:234423是最新部署的流程对应的流程定义Id
	 * 下面以部署luohubanwen:4:234423后，luohubanwen:3:98761对应的流程仍有在运行的分别进行讨论：
	 * 部署新流程前存在的正在运行的流程，即luohubanwen:3:98761对应的流程
	 * 如果之前该流程定义Id存在授权，则具有权限的流程定义Id就是当前流程定义Id
	 * 如果之前该流程定义Id不存在授权，则应查找luohubanwen:3:98761之前具有授权的流程
	 * 部署新流程后新启动的流程，即luohubanwen:4:234423新启动流程
	 * 如果该流程定义Id存在授权，则具有权限的流程定义Id就是当前流程定义Id
	 * 如果该流程定义Id不存在授权，则应查找luohubanwen:4:234423之前具有授权的流程
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	public String getPermProcessDefinitionId(String processDefinitionId) {
		String permProcessDefinitionId = "";
		if (StringUtils.isNotBlank(processDefinitionId)) {
			int count = 0;
			count = getLikePermCount(processDefinitionId);
			if (count > 0) {// 具有权限的流程定义Id就是当前流程定义Id的情况
				permProcessDefinitionId = processDefinitionId;
			} else {// 具有权限的流程定义Id不是当前流程定义Id
				permProcessDefinitionId = getLastPermProcessDefinitionId(processDefinitionId);// 获取当前流程定义Id前一个具有授权的流程定义Id
				if (StringUtils.isBlank(permProcessDefinitionId)) {
					permProcessDefinitionId = processDefinitionId;
				}
			}
		}
		return permProcessDefinitionId;
	}

	/**
	 * 获取当前流程定义Id前一个具有授权的流程定义Id
	 * 
	 * @param processDefinitionId
	 *            流程定义Id，例如luohubanwen:1:8
	 * @return
	 */
	public String getLastPermProcessDefinitionId(String processDefinitionId) {
		String permProcessDefinitionId = "";
		int count = 0;
		List<String> processDefintionList = workflowProcessDefinitionService.getProcessDefinitionIds(processDefinitionId.split(SysVariables.COLON)[0]);
		if (processDefintionList.size() > 0) {
			for (int i = 0; i < processDefintionList.size(); i++) {
				if (processDefinitionId.equals(processDefintionList.get(i))) {
					for (int j = i + 1; j < processDefintionList.size(); j++) {
						count = getLikePermCount(processDefintionList.get(j));
						if (count > 0) {
							permProcessDefinitionId = processDefintionList.get(j);
							break;
						}
					}
					break;
				}
			}
		}
		return permProcessDefinitionId;
	}

	/**
	 * 获取当前客体（processDefinitionId:taskDefKey或者只有processDefinitionId）
	 * 前一个具有授权的客体guid
	 * 
	 * @param processDefinitionId
	 *            流程定义Id，例如luohubanwen:1:8
	 * @param taskDefKey
	 *            流程定义的节点的Id，例如outerflow
	 * @return
	 */
	public String getLastPermObjectGuid(String processDefinitionId, String taskDefKey) {
		String permObjectGuid = "";
		if (StringUtils.isNotBlank(processDefinitionId)) {
			int count = 0;
			List<String> processDefintionList = workflowProcessDefinitionService.getProcessDefinitionIds(processDefinitionId.split(SysVariables.COLON)[0]);
			if (processDefintionList.size() > 0) {
				for (int i = 0; i < processDefintionList.size(); i++) {
					if (processDefinitionId.equals(processDefintionList.get(i))) {
						for (int j = i + 1; j < processDefintionList.size(); j++) {
							String tempObjectGuid = processDefintionList.get(j);
							if (StringUtils.isNotBlank(taskDefKey)) {
								tempObjectGuid = tempObjectGuid + SysVariables.COLON + taskDefKey;
							}
							count = getPermCount(tempObjectGuid);
							if (count > 0) {
								permObjectGuid = tempObjectGuid;
								break;
							}
						}
						break;
					}
				}
			}
		}
		return permObjectGuid;
	}

	/**
	 * 获取权限表中某一主体是否具有权限
	 * 
	 * @param processDefinitionId
	 *            流程定义Id，例如luohubanwen:1:8
	 * @return
	 */
	public int getPermCount(String objectGuid) {
		int count = 0;
		if (StringUtils.isNotBlank(objectGuid)) {
			count = objectPermissionRepository.getPermCount(objectGuid);
		}
		return count;
	}
	
	public int getPermCount(String objectGuid,String principalGuid,String tenantId) {
		int count = 0;
		if (StringUtils.isNotBlank(objectGuid)) {
			count = objectPermissionRepository.getPermCount(objectGuid, principalGuid, tenantId);
		}
		return count;
	}

	/**
	 * 获取权限表中某一主体是否具有权限
	 * 
	 * @param processDefinitionId
	 *            流程定义Id，例如luohubanwen:1:8
	 * @return
	 */
	public int getLikePermCount(String processDefinitionId) {
		int count = 0;
		if (StringUtils.isNotBlank(processDefinitionId)) {
			count = objectPermissionRepository.getLikePermCount(processDefinitionId + "%", ThreadLocalHolder.getTenantId());
		}
		return count;
	}

	/*
	 * 批量保存配置的权限
	 */
	@Transactional(readOnly = false)
	public void save(List<ObjectPermission> list) {
		if (list.size() > 0) {
			objectPermissionRepository.save(list);
		}
	}

	/**
	 * 查询已存在的权限数据
	 * 
	 * @param objectGuid
	 * @param objectType
	 * @param principalGuid
	 * @param principalType
	 * @param user_deptGuid
	 * @return
	 */
	public ObjectPermission getPerm(String objectGuid, Integer objectType, String principalGuid, Integer principalType, String user_deptGuid) {
		ObjectPermission entity = null;
		List<ObjectPermission> list = getPerm(objectGuid, "", objectType, principalGuid, principalType);
		if (list.size() > 0) {
			if (list.size() == 1) {
				entity = list.get(0);
			}
		}
		return entity;
	}

	/**
	 * 查询某一部门Id下所有具有权限的人员/部门guid
	 * 
	 * @param processDefinitionId
	 *            流程定义Id，例如luohubanwen:1:8
	 * @param taskDefKey
	 *            流程定义的节点的Id，例如outerflow
	 * @return
	 */
	public List<ObjectPermission> getPerm(String processDefinitionId, String taskDefKey, Integer principalType1, String deptId) {
		List<ObjectPermission> list = new ArrayList<ObjectPermission>();
		if (StringUtils.isBlank(processDefinitionId) || StringUtils.isBlank(taskDefKey) || StringUtils.isBlank(deptId) || principalType1 == null) {
			return list;
		}
		String objectGuid1 = processDefinitionId;
		String objectGuid2 = processDefinitionId;
		if (StringUtils.isNotBlank(taskDefKey)) {
			objectGuid2 = objectGuid2 + SysVariables.COLON + taskDefKey;
		}
		list = objectPermissionRepository.getPerm(objectGuid1, objectGuid2, principalType1);
		return list;
	}

	/**
	 * 查询具有某一流程定义Id对应的所有权限
	 * 
	 * @param processDefinitionId
	 *            流程定义Id，例如luohubanwen:1:8
	 * @return
	 */
	public List<ObjectPermission> getLikePerm(String objectGuid) {
		List<ObjectPermission> list = new ArrayList<ObjectPermission>();
		if (StringUtils.isNotBlank(objectGuid)) {
			list = objectPermissionRepository.getLikePerm(objectGuid + "%");
		}
		return list;
	}

	/**
	 * 查询具有某一流程定义Id对应的所有权限
	 * 
	 * @param objectGuid
	 *            客体guid，例如luohubanwen:1:8
	 * @return
	 */
	public List<ObjectPermission> getPerm(String objectGuid) {
		List<ObjectPermission> list = new ArrayList<ObjectPermission>();
		if (StringUtils.isNotBlank(objectGuid)) {
			list = objectPermissionRepository.findByObjectGuidAndTenantId(objectGuid, ThreadLocalHolder.getTenantId());
		}
		return list;
	}

	/**
	 * 查找流程节点的授权:
	 * 1.先查找租户有没有自己设置权限  
	 * 2.如果没有自己设置权限，就获取默认绑定的权限
	 * 3.如果前两个步骤没有找到，就是有问题了，现在先这样处理：查找针对全流程的授权
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @return
	 */
	public List<ObjectPermission> findByObjectGuidAndTenantId(String processDefinitionId, String taskDefKey) {
		List<ObjectPermission> list = objectPermissionRepository.findByObjectGuidAndTenantId(processDefinitionId + SysVariables.COLON + taskDefKey, ThreadLocalHolder.getTenantId());
		if (list.size() <= 0 || list == null) {
			list = objectPermissionRepository.findByObjectGuid(processDefinitionId + SysVariables.COLON + taskDefKey);
			if (list.size() <= 0 || list == null) {
				list = objectPermissionRepository.findByObjectGuid(processDefinitionId);
			}
		}
		return list;
	}

	public List<ObjectPermission> findByObjectGuid(String objectGuid) {
		return objectPermissionRepository.findByObjectGuid(objectGuid);
	}

	/**
	 * 用于角色、动态角色，这几个没有一人多岗问题，所以用不到user_deptGuid
	 * 
	 * @param processDefinitionId
	 * @param taskDefinitionKey
	 * @param objectType
	 * @param principalGuid
	 * @param principalType
	 * @return
	 */
	public List<ObjectPermission> getPerm(String processDefinitionId, String taskDefinitionKey, Integer objectType, String principalGuid, Integer principalType) {
		List<ObjectPermission> list = new ArrayList<ObjectPermission>();
		if (StringUtils.isBlank(processDefinitionId) || StringUtils.isBlank(taskDefinitionKey) || objectType == null || StringUtils.isBlank(principalGuid) || principalType == null) {
			return list;
		}
		String objectGuid1 = processDefinitionId;
		String objectGuid2 = processDefinitionId;
		if (StringUtils.isNotBlank(taskDefinitionKey)) {
			objectGuid2 = objectGuid2 + SysVariables.COLON + taskDefinitionKey;
		}
		list = objectPermissionRepository.getPerm(objectGuid1, objectGuid2, objectType, principalGuid, principalType);
		return list;
	}

	/*
	 * 删除权限
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		objectPermissionRepository.delete(id);
	}

	/**
	 * 将值设置到ObjectPermission中
	 * 
	 * @param id
	 * @param objectguid
	 * @param objectType
	 * @param permission
	 * @param principalguid
	 * @param principalname
	 * @param principalType
	 * @param user_deptGuid
	 * @return
	 */
	public ObjectPermission genObjectPermission(String id, String objectguid, Integer objectType, String principalguid, String principalname, Integer principalType, String user_deptGuid, String roleGroupId, String roleGroupName) {
		ObjectPermission objPermission = new ObjectPermission();
		if (StringUtils.isNotBlank(id)) {
			objPermission.setId(id);
		} else {
			objPermission.setId(GuidUtil.genGuid());
		}
		objPermission.setObjectGuid(objectguid);
		objPermission.setObjectType(objectType);
		objPermission.setPrincipalGuid(principalguid);
		objPermission.setPrincipalName(principalname);
		objPermission.setPrincipalType(principalType);
		objPermission.setTenantId(ThreadLocalHolder.getTenantId());
		return objPermission;
	}

	/**
	 * 复制授权 不论当前版本流程定义是否存在授权，都进行复制 当当前版本流程定义不存在授权时直接复制
	 * 当当前版本流程定义存在授权时需要查出已有的授权，并进行比对，如果某一授权已存在则不再复制
	 * 
	 * @param processDefinitionId
	 * @param taskDefKey
	 * @param isWhole
	 *            是否复制所有授权，true为复制，false为不复制
	 */
	public void copyPerm(String processDefinitionId, String taskDefKey, boolean isWhole) {
		if (StringUtils.isNotBlank(processDefinitionId)) {// processDefinitionId不能为空
			if (isWhole) {// 是否复制所有授权，true的时候表示是，此时不再管taskDefKey是否有值，直接全部复制
				String permProcessDefinitionId = getPermProcessDefinitionId(processDefinitionId);// 当前具有授权的流程定义Id
				if (StringUtils.isNotBlank(permProcessDefinitionId)) {// 如果当前具有授权的流程guid（permProcessDefinitionId）存在，则继续进行复制，如果不存在，则说明没有授权，不进行复制
					if (processDefinitionId.equals(permProcessDefinitionId)) {// 如果当前具有授权的流程guid与当前的流程guid相同，说明当前流程guid（processDefinitionId）已经存在授权，需要进行比较后再判断是否需要复制
						String lastPermProcessDefinitionId = getLastPermProcessDefinitionId(processDefinitionId);// 上一具有授权的流程定义Id
						if (StringUtils.isNotBlank(lastPermProcessDefinitionId)) {// 如果上一具有授权的流程定义Id（lastPermProcessDefinitionId）存在，则继续复制，如果不存在，则不进行复制
							List<ObjectPermission> copyObjectPermissionList = new ArrayList<ObjectPermission>();// 需要复制的授权数据
							List<ObjectPermission> lastObjectPermissionList = getLikePerm(lastPermProcessDefinitionId);// 上一具有授权的流程定义Id对应的授权
							List<ObjectPermission> objectPermissionList = getLikePerm(processDefinitionId);// 当前流程定义Id对应的客体对应的授权
							if (objectPermissionList.size() > 0) {// 如果当前流程定义Id对应的客体对应的授权存在，说明需要进行比较，复制不存在的内容
								for (ObjectPermission entity2 : lastObjectPermissionList) {
									int compareResult = -1;
									for (ObjectPermission entity1 : objectPermissionList) {
										String tempObjectGuid = entity2.getObjectGuid();
										tempObjectGuid = tempObjectGuid.replace(lastPermProcessDefinitionId, processDefinitionId);
										entity2.setObjectGuid(tempObjectGuid);// 当对流程授权进行比较时，由于entity1和entity2中客体的流程实例Id已经不相同，所以这里要先将它们换成相同的，才能进行比较
										compareResult = compareObjPerm(entity1, entity2, false);
										if (compareResult == 1) {
											break;
										}
									}
									if (compareResult == 0) {
										entity2.setId(GuidUtil.genGuid());
										copyObjectPermissionList.add(entity2);
									}
								}
							}
							save(copyObjectPermissionList);
						}
					} else {// 如果不相同，则说明当前流程guid（processDefinitionId）不存在授权，可以直接复制
						List<ObjectPermission> list = getLikePerm(permProcessDefinitionId);
						for (int i = 0; i < list.size(); i++) {
							ObjectPermission o = list.get(i);
							o.setId(GuidUtil.genGuid());
							String tempObjectGuid = o.getObjectGuid();
							tempObjectGuid = tempObjectGuid.replace(permProcessDefinitionId, processDefinitionId);
							o.setObjectGuid(tempObjectGuid);
							list.set(i, o);
						}
						save(list);
					}
				}
			} else {// 不是复制所有授权的情况
				String currentObjectGuid = processDefinitionId;// 客体guid
				if (StringUtils.isNotBlank(taskDefKey)) {
					currentObjectGuid = currentObjectGuid + SysVariables.COLON + taskDefKey;
				}
				String lastObjectGuid = getLastPermObjectGuid(processDefinitionId, taskDefKey);// 上一具有授权的客体Id
				if (StringUtils.isNotBlank(lastObjectGuid)) {// 该条件下说明之前存在授权的客体，如果不存在，则无法复制
					List<ObjectPermission> copyObjectPermissionList = new ArrayList<ObjectPermission>();// 需要复制的授权数据
					List<ObjectPermission> lastObjectPermissionList = getPerm(lastObjectGuid);// 上一具有授权的客体Id对应的授权
					List<ObjectPermission> objectPermissionList = getPerm(currentObjectGuid);// 当前流程定义Id对应的客体对应的授权
					if (objectPermissionList.size() > 0) {// 如果当前流程定义Id对应的客体对应的授权存在，说明需要进行比较，复制不存在的内容
						String lastPermProcessDefinitionId = lastObjectGuid;// 上一具有授权的流程定义Id
						if (StringUtils.isNotBlank(taskDefKey)) {
							lastPermProcessDefinitionId = lastObjectGuid.substring(0, lastObjectGuid.indexOf(taskDefKey) - 1);
						}
						for (ObjectPermission entity2 : lastObjectPermissionList) {
							int compareResult = -1;
							for (ObjectPermission entity1 : objectPermissionList) {
								String tempObjectGuid = entity2.getObjectGuid();
								tempObjectGuid = tempObjectGuid.replace(lastPermProcessDefinitionId, processDefinitionId);
								entity2.setObjectGuid(tempObjectGuid);// 当对流程授权进行比较时，由于entity1和entity2中客体的流程实例Id已经不相同，所以这里要先将它们换成相同的，才能进行比较
								compareResult = compareObjPerm(entity1, entity2, false);
								if (compareResult == 1) {
									break;
								}
							}
							if (compareResult == 0) {
								entity2.setId(GuidUtil.genGuid());
								copyObjectPermissionList.add(entity2);
							}
						}
					} else {// 如果当前流程定义Id对应的客体对应的授权不存在，说明可以直接复制
						for (int i = 0; i < lastObjectPermissionList.size(); i++) {
							ObjectPermission o = lastObjectPermissionList.get(i);
							o.setId(GuidUtil.genGuid());
							String tempObjectGuid = o.getObjectGuid();
							tempObjectGuid = tempObjectGuid.replace(lastObjectGuid, currentObjectGuid);
							o.setObjectGuid(tempObjectGuid);
							copyObjectPermissionList.add(o);
						}
					}
					save(copyObjectPermissionList);
				}
			}
		}
	}

	/**
	 * 比较两个授权数据是否相等 在这里首先比较主体Guid、主体类型、客体Guid、客体类型是否相同
	 * 根据isComparePerm设置，决定是否比较主体授权
	 * 
	 * @param entity1
	 *            第一条授权数据
	 * @param entity2
	 *            第一条授权数据
	 * @param isComparePerm
	 *            是否比较主体授权，true为比较，false为不比较
	 * @return -1表示出现错误，未进行比较，0表示不相等，1表示相等
	 */
	public int compareObjPerm(ObjectPermission entity1, ObjectPermission entity2, boolean isComparePerm) {
		int result = -1;
		if (entity1 != null && entity2 != null) {
			if (StringUtils.isNotBlank(entity1.getObjectGuid()) && entity1.getObjectType() != null && StringUtils.isNotBlank(entity1.getPrincipalGuid()) && entity1.getPrincipalType() != null && StringUtils.isNotBlank(entity2.getObjectGuid()) && entity2.getObjectType() != null && StringUtils.isNotBlank(entity2.getPrincipalGuid()) && entity2.getPrincipalType() != null) {
				if (entity1.getObjectGuid().equals(entity2.getObjectGuid()) && entity1.getObjectType() == entity2.getObjectType() && entity1.getPrincipalGuid().equals(entity2.getPrincipalGuid()) && entity1.getPrincipalType() == entity2.getPrincipalType()) {
					if (isComparePerm) {
						/*
						 * if (entity1.getPermission() ==
						 * entity2.getPermission()) { result = 1; } else {
						 */
						result = 0;
						/* } */
					} else {
						result = 1;
					}
				} else {
					result = 0;
				}
			}
		}
		return result;
	}

	@Transactional(readOnly = false)
	public void removePerm(String processDefinitionId) {
		List<ObjectPermission> objPermList = objectPermissionRepository.getLikePerm(processDefinitionId+"%");
		for (ObjectPermission objPerm : objPermList) {
			objectPermissionRepository.delete(objPerm);
		}
	}
}
