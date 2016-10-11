package net.risesoft.services.webservice.jShare;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.actions.onlineservice.webservice.beans.WebServiceBean;
import net.risesoft.beans.base.KeyValue;

/**
 * 住建局WebService接口访问的接口
 * @author HJL
 *
 */
public interface RequestService 
{
	/**
	 * @description 审查合格书webServices接口访问接口
	 * @param qualifiedbookid 审查合格书编号
	 * @date 2013-10-14
	 * @author HJL
	 * @return 
	 */
	public List<KeyValue> investigateWebService(String qualifiedbookid);
	
	
	/**
	 * @description 保函信息webServices接口访问接口
	 * @param gcbh 工程编号
	 * @date 2013-10-14
	 * @author HJL
	 * @return 
	 */
	public List<KeyValue> backletterWebService(String gcbh);
	
	
	/**
	 * @description 证照材料共享(深圳市民用建筑施工图设计文件抽查（备案）意见书)webServices接口访问接口
	 * @param zjbh 证照编号
	 * @date 2013-10-14
	 * @author HJL
	 * @return 
	 */
	public List<KeyValue> licenseWebService(String zjbh);
	
	
	/**
	 * @description 材料共享（直接发包审批决定书文号）WebService访问
	 * @param bwbh 直接发包审批决定书文号
	 * @date 2013-10-14
	 * @author HJL
	 * @return 
	 */
	public List<KeyValue> decisionWebService(String bwbh);
	
	
	/**
	 * @Description 项目总监、建造师信息回填
	 * @Author hjl
	 * @Date 2013-11-05
	 * @return 
	 */
	public List<WebServiceBean> backfill(HttpServletRequest request);
	
	/**
	 * 施工企业资质证书验证
	 * @param zzzsh  资质证书号
	 * @return
	 */
	public List<KeyValue> getSgqyzzzs(String zzzsh);
	
	/**
	 * 监理单位资质证书验证
	 * @param zzzsh  资质证书号
	 * @return
	 */
	public List<KeyValue> getJldwzzzs(String zzzsh);
}
