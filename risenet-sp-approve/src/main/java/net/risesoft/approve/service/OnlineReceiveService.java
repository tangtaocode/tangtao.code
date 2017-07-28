package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.OfficeSpiDeclareinfo;
import net.risesoft.model.Person;

public interface OnlineReceiveService {

	
	/**
	 * 加载网上收件列表
	 */
	public Pager findByUserID(Person person,String status,String ishf,String approveName,Pager pager) ;
	public Pager findByUserIDNew(Person person,String ishf,String status,String approveName,Pager pager);
	
	//更新网上预受理状态
	public void updateStatus(String status,String guid);
	//向office_spi_declareinfo表插入数据
	public void insertSpiDeclareInfo(String guid,String ytjs,String xbqs,String xbzs);
	
	//查询是否已经网上预受理
	public OfficeSpiDeclareinfo isYushouli(String yxtywlsh);
	
	//预受理未处理数目
	public int undoYslCount(Person person);
	
	//得到当前的申请人
	public List<Map<String, Object>> getDeclarerperson(String userid);
	
	//得到共享证照数据
	public List<Map<String, Object>> getGXCaiLiaoList(String declarerperson);
	
	//得到附件列表
	public List<Map<String, Object>> getFujianList(String instanceguid);
	
	//删除共享证照
	public int deleteGXZZ(String instanceguid);
	
	//删除附件
	public int onDeleteFJ(String appinstguid);
	
	//更新并修改共享证照
	public int updateGXZZ(String instanceguid,String docname,String producedate,String zzyxq);
	//查询材料列表
	public List<Map<String,Object>> findListsByTypeGuid(String typeGuid,String instanceId,String itemId);
	//查找材料类型
	public String getTypeName(String typeGuid,String itemId);
	//根据材料ID查出集合
	public List<Map<String, Object>> getPanDuanList(String declareannexguid);
	
	//下载附件
	public void download(String id, HttpServletResponse response,HttpServletRequest request) throws Exception;
	
}
