package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;


public interface RelativeMessageService {

	/**
	 * 获取织网工程相关信息
	 * @param officeSpiDeclareinfo
	 * @return
	 */
	List<Map<String, Object>> getZhiWangGongCheng(String SPinstanceId);

	/**
	 * 获取证照库相关信息
	 * @param SPinstanceId
	 * @return
	 */
	List<Map<String, Object>> getZhengZhaoKu(String SPinstanceId);

	/**
	 * 获取资料库相关信息
	 * @param SPinstanceId
	 * @return
	 */
	List<Map<String, Object>> getZiLiaoKu(String SPinstanceId);

	/**
	 * 注册信息
	 * @param sPinstanceId
	 * @return
	 */
	List<Map<String, Object>> getZhuCeXinXi(String SPinstanceId);

}
