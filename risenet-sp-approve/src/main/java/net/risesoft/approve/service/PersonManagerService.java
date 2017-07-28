package net.risesoft.approve.service;
import java.util.List;

import net.risesoft.approve.entity.jpa.WindowPersonSet;

public interface PersonManagerService {
	//只能查询到该管理员所添加的窗口人员,通过窗口人员姓名去查找
	public List<WindowPersonSet> findAll(String usernameid,String windowname);
	
	//修改手机号码
	public String edit(String employeeid,String value);
	
	//判断当前登录名是否为分厅管理员
	public int sense(String usernameid);
	
	//查询所有的窗口人员信息
	public List<WindowPersonSet> findAllList(String windowname);
	
	public List<WindowPersonSet> findAllList();
}
