package net.risesoft.approve.service;

import java.util.List;

import net.risesoft.approve.entity.jpa.SpmTemplate;

public interface SpmTemplateService {
	/**
	 * 根据模板id找模板
	 * @param guid
	 * @return
	 */
	public SpmTemplate findOne(String guid);
	/**
	 * 根据事项id找模板
	 * @param approveItemGuid
	 * @return
	 */
	public List<SpmTemplate> findByApproveItemGuid(String approveItemGuid);
	/**
	 * 查询所有模板
	 * @return
	 */
	public List<SpmTemplate> findAll();
	/**
	 * 保存或修改
	 * @param spmTemplate
	 * @return
	 */
	public SpmTemplate saveOrUpdate(SpmTemplate spmTemplate);
	/**
	 * 根据guid删除模板
	 * @param guid
	 */
	public void delete(String guid);
}
