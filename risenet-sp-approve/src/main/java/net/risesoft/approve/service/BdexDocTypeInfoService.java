package net.risesoft.approve.service;

import java.util.List;

public interface BdexDocTypeInfoService {
	
	/*
	 * 查询证照信息列表
	 * 
	 */
	public List findById(String id);
	
	
	/*
	 * 新建证照信息
	 * 
	 */
	public int insert(String guid,String doctypeguid,String infocode,String infomemo,String glguid);
	
	/*
	 * 修改证照信息
	 * 
	 */
	public int update(String guid,String infocode,String infomemo,String glguid);
	
	/*
	 * 删除证照信息
	 * 
	 */
	public int deleteDocType(String guid);
}
