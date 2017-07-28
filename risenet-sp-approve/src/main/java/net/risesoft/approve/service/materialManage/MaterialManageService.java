package net.risesoft.approve.service.materialManage;


import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.model.Person;

public interface MaterialManageService {

	/**
	 * 查询物料列表
	 */
	public Pager materialList(Person person,String material_code,String material_name, Pager pager);

	
	/**
	 * 新增物料
	 */
	public int insert(Person person,String ID,String material_code,String material_name,String material_num,String unit,String tabindexE);
	
	/**
	 * 修改物料信息
	 */
	public int update(Person person,String ID,String CODE,String STUFFDEFINENAME,String ISCHECKVALID,String TABINDEX,String STATE,String DEFINETIME);
	
	/**
	 * 删除物料
	 */
	public int delete(String ID);
	
	/**
	 * 查询领用列表
	 */
	public Pager materialUsingList(Person person,String material_code,String material_name, Pager pager);
	
	/**
	 * 修改领用信息
	 */
	public int usingUpdate(Person person,String ID,String using_num,String description,String using_name);
	
	/**
	 * 获取资产列表
	 */
	public List<Map<String, Object>> getMaterialList();
	
	/**
	 * 获取数据字典列表
	 */
	public List<Map<String, Object>> getDictionaryList();
	
	
	/**
	 * 资产领用保存
	 */
	public int usingInsert(Person person,String ID,String materialguid,String using_num,String using_name,String description);
	
	/**
	 * 领用删除
	 */
	public int usingDelete(String ID);
	
	/**
	 * 查询维修记录列表
	 */
	public Pager materialRepairList(Person person,String material_code,String material_name, Pager pager);
	
	/**
	 * 修改维修记录信息
	 */
	public int repairUpdate(Person person,String ID,String repair_num,String repair_funds,String repair_person,String repair_reason,String repair_time_start,String repair_time_done,String charge_person);
	
	/**
	 * 资产领用保存
	 */
	public int repairInsert(Person person,String ID,String materialguid,String repair_num,String repair_funds,String repair_person,String repair_reason,String repair_time_start,String repair_time_done,String charge_person);
	
	/**
	 * 资产领用删除
	 */
	public int repairDelete(String ID);
	
//==================数据字典===========================================
	/**
	 * 查询物料列表
	 */
	public Pager dictionaryList(Person person,String material_code,String material_name, Pager pager);

	
	/**
	 * 新增物料
	 */
	public int dictionaryInsert(Person person,String ID,String material_code,String material_name,String unit);
	
	/**
	 * 修改物料信息
	 */
	public int dictionaryUpdate(Person person,String materialguid, String material_name,String unit);
	
	/**
	 * 删除物料
	 */
	public int dictionaryDelete(String ID);
	
	/**
	 * 获取单位
	 */
	public String getUnit(String ID);
	
	/**
	 * 判断当前物料是否存在
	 */
	public int getMaterialCount(String ID);
	
	/**
	 * 判断当前物料是否存在
	 */
	public int updateMaterialNum(Person person,String ID,String num);
	
	/**
	 * 新增物料日志
	 */
	public int insertLog(Person person,String material_code,String material_name,String material_num,String unit);
	
	/**
	 * 删除物料
	 */
	public int logDelete(String code);
	
	/**
	 * 综合查询
	 */
	public Pager materialMixQuery(Person person, String startTime,String endTime, String material_code,String material_name, Pager pager);
	
	
	 
	
	
	
}
