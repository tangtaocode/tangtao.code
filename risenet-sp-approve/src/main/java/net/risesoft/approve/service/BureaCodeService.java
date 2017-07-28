package net.risesoft.approve.service;

import java.util.List;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.TBdexBureacode;
import net.risesoft.model.Person;

public interface BureaCodeService {
	
		public List findAll();
		/*public void update(String bureacode,String bureaguid);*/
		
		
		/**
		 * 查询组织机构代码显示列表
		 */
		public Pager findByUserID(Person person, String BUREANAME,String BUREACODE, Pager pager);


		/**
		 * 修改组织机构代码
		 */
		public int update(String BUREAGUID, String BUREACODE, String RC8_DEPATMENT_ID);

		/**
		 * 新增组织机构代码
		 */
		public int insert(String BUREAGUID, String BUREACODE, String RC8_DEPATMENT_ID);

		/**
		 * 删除组织机构代码
		 */
		public int delete(String BUREAGUID);
		
		/**
		 * RC8部门单选框列表
		 * 
		 */
		public String finddeptTree();
		
		
		/**
		 * 保存rc8部门信息
		 * 
		 */
		public int savedept(String ID,String ALLNAME,String ALLID);
		
}
