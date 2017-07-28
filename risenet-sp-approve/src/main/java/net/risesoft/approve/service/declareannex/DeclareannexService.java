package net.risesoft.approve.service.declareannex;


/**
 * 审批材料操作
 * @author shenqiang
 *
 */
public interface DeclareannexService {

	//判断事项是否有情形
	public int hasDeclareannexType(String itemGuid);
	//根据事项id查找材料类型
	public String findDeclareannexTypeByItemGuid(String itemGuid);
}
