package net.risesoft.approve.service;


import net.risesoft.approve.entity.base.Pager;
import net.risesoft.model.Person;

public interface MaterialShareService {

	/**
	 * 查询材料定义列表
	 */
	public Pager findByUserID(Person person,String CODE,String STUFFDEFINENAME, Pager pager);

	
	/**
	 * 新增材料定义列表
	 */
	public int insert(String ID,String CODE,String STUFFDEFINENAME,String ISCHECKVALID,String TABINDEX,String STATE,String DEFINETIME);
	
	/**
	 * 修改材料定义列表
	 */
	public int update(String ID,String CODE,String STUFFDEFINENAME,String ISCHECKVALID,String TABINDEX,String STATE,String DEFINETIME);
	
	/**
	 * 删除材料定义列表
	 */
	public int delete(String ID);
	
}
