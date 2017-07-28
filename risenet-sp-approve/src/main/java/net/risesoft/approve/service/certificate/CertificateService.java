package net.risesoft.approve.service.certificate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.certificate.CertificateBean;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.approve.entity.jpa.SpmApproveitem;


public interface CertificateService {
	/**
	 * 证照领取列表
	 */
	public Pager findAllList(HttpServletRequest request,Pager pager);
	
	/**
	 * 获取证照领取详细信息
	 */
	public Map<String, Object> getDetailByWorkflowinstance_guid(String workflowinstance_guid);
	
	public Map<String, Object> declareInfo(String workflowinstance_guid);
	
	//证照领取
	public boolean doEdit(HttpServletRequest request);
	
	//获取单个值
	public String getOneString(String sql,Object[] param);
	
	/**
	 * 跟踪评价回复关系生成类。
	 * 循环生成0000-9999之间数字。用完后删掉0000重新生成0000，循环使用。
	 *
	 */
	public String getreno();
	
	/**
	 * 根据事项id查询基本信息
	 */
	public SpmApproveitem findApproveItemByGuid(String guid);
	
	/**
	 * 将基本信息插入office_spi_declareinfo 表中
	 */
	public void save(OfficeSpiDeclareinfo office);
}
