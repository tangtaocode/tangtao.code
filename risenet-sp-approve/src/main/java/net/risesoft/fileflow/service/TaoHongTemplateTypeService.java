package net.risesoft.fileflow.service;

import java.util.List;

import net.risesoft.fileflow.entity.jpa.TaoHongTemplateType;

public interface TaoHongTemplateTypeService {
	/**
	 * 查找当前租户的所有模板类型
	 * @return
	 */
	List<TaoHongTemplateType> findByTenantId();
	
	/**
	 * 删除模板类型
	 * @param ids
	 */
	void removeTaoHongTemplateType(String[] ids);
	
	/**
	 * 根据id查抄模板
	 * @param id
	 * @return
	 */
	TaoHongTemplateType findOne(String id);
	
	/**
	 * 新增或修改模板
	 * @param t
	 * @return
	 */
	TaoHongTemplateType saveOrUpdate(TaoHongTemplateType t);
	
	/**
	 * 保存排序
	 * @param idAndTabIndexs
	 */
	void saveOrder(String[] idAndTabIndexs); 
}
