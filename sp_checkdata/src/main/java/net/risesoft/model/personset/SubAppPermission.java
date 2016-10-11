package net.risesoft.model.personset;

/*
 * Copyright (c) 2003 RiseSoft Co.,Ltd 
 * $Header$
 */


import java.util.List;


/**
 * 子系统授权条目对象，一个对象对应于Office_WorkflowAppPermission一条记录
 * @author 赵斌 zhaobin@risesoft.net zhaobin@wanneng.com
 * @version $Revision$, Created 2005-5-19 23:13:06
 */
public class SubAppPermission {

  private int object_type;
  private String object_guid;//对象guid对应文件夹栏目的文件夹guid
  private int role_type;//权限栏目角色类型比如在常量类/net/risesoft/common/constants/RoleConst.java
  private String role_guid;//栏目角色guid
  private String role_name;//角色名称
  private int permission;
  
  //将对应的object_type、role_type转换为所对应的人员GUID列表
  private List roleMembers;
  
 
  public int getObject_type() {
	return object_type;
}
public void setObject_type(int object_type) {
	this.object_type = object_type;
}
public String getObject_guid() {
	return object_guid;
}
public void setObject_guid(String object_guid) {
	this.object_guid = object_guid;
}
public int getRole_type() {
	return role_type;
}
public void setRole_type(int role_type) {
	this.role_type = role_type;
}
public String getRole_guid() {
	return role_guid;
}
public void setRole_guid(String role_guid) {
	this.role_guid = role_guid;
}
public String getRole_name() {
	return role_name;
}
public void setRole_name(String role_name) {
	this.role_name = role_name;
}
public int getPermission() {
	return permission;
}
public void setPermission(int permission) {
	this.permission = permission;
}
public List getRoleMembers() {
    return roleMembers;
  }
  public void setRoleMembers(List roleMembers) {
    this.roleMembers = roleMembers;
  }
}
