package net.risesoft.approve.service;

import java.util.Map;

import net.risesoft.approve.entity.base.Pager;

public interface MatterAuditRoleService {

	/**
	 * 根据事项id获取审核批准角色列表
	 * @param pager
	 * @return
	 */
	Map<String, Object> getRoleList(String approveItemGuid,Pager pager);

	/**
	 * 保存审核批准角色
	 * @param personGuids
	 * @param personNames
	 * @param approveItemGuids
	 * @return
	 */
	void saveRole(String personGuids, String personNames,String approveItemGuids);

	/**
	 *  根据人员id和事项id删除审核批准角色
	 * @param personGuids
	 * @param approveItemGuid
	 */
	void deleteRole(String personGuids,String approveItemGuid);

}
