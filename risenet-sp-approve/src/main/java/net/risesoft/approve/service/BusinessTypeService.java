package net.risesoft.approve.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BusinessTypeService{
	//证件照管理页面左边的树结构
	public String selectTree();
	//点击树得到右边的表格
	public List getDeptInfo(String deptId,String docutypeGid);
}
