package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.base.Pager;


public interface XzqlXzspListsService {
	
	/*
	 * 获取itemid
	 * 
	 */
	public List<Map<String, Object>> findById(String itemid);
	
	/*
	 * 获取材料类别
	 * 
	 */
	public Pager findTableById(String itemid,Pager page);
	
	/*
	 * 获取材料详细信息
	 * 
	 */
	public List<Map<String, Object>> findmaterialById(String itemid,String typeguid);
	
	
	/*
	 * 修改关联材料关联证照
	 * 
	 */
	public int rmupdate(String ID,String MATERIALLX,String MATERIALNAME,String DESCRIBE,String STUFFDEFINENAME,String DOCTYPENAME,String DOCTYPEGUID,String ORDERNO);
		
	
	/*
	 * 删除
	 * 
	 */
	public int deleteById(String id);
	
	/*
	 * 添加
	 * 
	 */
	public int add(String id,String itemid,String version,String materiallx,String  materialname,String describe,int orderno);
	
	/*
	 * 修改材料分类
	 * 
	 */
	public int mupdate(String typeguid,String typename,String  orderno,String  banben);
		
	/*
	 * 删除已选中材料勾选项
	 * 
	 */
	public int materialdel(String itemid,String version);
	
	/*
	 * 修改材料勾选项
	 * 
	 */
	public int materialupdata(String typeguid,String itemid,String version,String  listguid);
	
	/*
	 * 删除分类
	 * 
	 */
	public int materialdelete(String typeguid,String version);
	
	/*
	 * 分类新增
	 * 
	 */
	public int materialinsert(String TYPEGUID,String ITEMID,String TYPENAME,String ORDERNO,String version);
	
	
	/*
	 * 删除已选中材料勾选项
	 * 
	 */
	public int stuffdel(String LISTID);
	
	/*
	 * 多选框材料树列表
	 * 
	 */
	public String findStuffTree();
	
	/*
	 * 绑定关联材料
	 * 
	 */
	public int stuffupdate(String STUFFDEFINEID,String CLTYPEID,String LISTID);
	
	
	/*
	 * 多选框zhengz树列表
	 * 
	 */
	public String finddoctypeTree();
	
	/*
	 * 绑定关联证照
	 * 
	 */
	public int doctypeupdate(String ID,String ALLNAME,String ALLID);
	
}
