package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;

public interface TongyipintaiServices {
	/**
	 * 窗口收件首页
	 */
	public List<Map<String,Object>> findAll(String approveItemName,String status);
	
	/**
	 * 根据事项id查询基本信息
	 */
	public SpmApproveitem findApproveItemByGuid(String guid);
	
	/**
	 * 根据name查询基本信息
	 */
	public SpmApproveitem findApproveItemByName(String name);
	
	/**
	 * 根据申办流水号获取申办信息
	 */
	public ShenBanProcess findShenbanBySblsh(String sblsh);
	
	/**
	 * 将基本信息插入office_spi_declareinfo 表中
	 */
	public void save(OfficeSpiDeclareinfo office);
}
