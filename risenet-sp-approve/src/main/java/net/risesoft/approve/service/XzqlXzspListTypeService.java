package net.risesoft.approve.service;
import java.util.List;
import java.util.Map;
public interface XzqlXzspListTypeService {
	//根据页面传来的事项id去查询相应的材料类型
	public List<Map<String,Object>> find(String approveitemguid);
	
	//根据页面传来的类型id去查找相应的材料
	public List<Map<String,Object>> findform(String listguid);
}
