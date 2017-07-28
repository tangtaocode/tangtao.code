package net.risesoft.approve.service;
import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;


public interface WindowApproveService {
	/**
	 * 窗口收件首页
	 */
	public List<Map<String,Object>> findAll(String approveItemName);
	
	/**
	 * 根据事项id查询基本信息
	 */
	public SpmApproveitem findApproveItemByGuid(String guid);
	
	
	/**
	 * 将基本信息插入office_spi_declareinfo 表中
	 */
	public void save(OfficeSpiDeclareinfo office);
}
