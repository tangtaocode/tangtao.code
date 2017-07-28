package net.risesoft.approve.service;

import java.util.Date;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.TBdexDoctype;

public interface BdocTypeService {
		public Pager findAll(String departmentGuid,String departmentName,Pager page );
		public int delete(String guid);
		public TBdexDoctype findByid(String guid);
		public int insert(String guid,String doctypecode,String doctypename,String bguid,String memo,Date date);
		public int update(String guid,String doctypecode,String doctypename,String bguid,String memo);
}
