package net.risesoft.approve.service.impl.declareannex;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.service.declareannex.DeclareannexService;
import net.risesoft.utilx.StringX;
import net.sf.json.JSONObject;

@Service(value="declareannexService")
public class DeclareannexServiceImpl implements DeclareannexService{
	@Resource(name="routerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int hasDeclareannexType(String itemGuid) {
		String sql = "select  count(1) from spm_declareannextype t where t.approveitemguid=? order by t.typeorder";
		int res = jdbcTemplate.queryForObject(sql,new Object[]{itemGuid},Integer.class);
		return res;
	}
	//根据事项id查找材料类型
	@Override
	public String findDeclareannexTypeByItemGuid(String itemGuid) {
		StringBuffer sb = new StringBuffer();
		String sql = "select t.approveitemname from spm_approveitem t where t.approveitemguid=?";
		String itemName = jdbcTemplate.queryForObject(sql, String.class, itemGuid);
		sql = "select  t.declareannextypeguid,t.declareannextypename from spm_declareannextype t where t.approveitemguid=? order by t.typeorder";
		List<Map<String,Object>> typeList = jdbcTemplate.queryForList(sql,new Object[]{itemGuid});
		sb.append("[{id:'"+itemGuid+"',name:'"+itemName+"',isParent:'true'},");
		if(typeList!=null && typeList.size()>0){//如果存在材料分类
			for(int i=0;i<typeList.size();i++){
				String id = StringX.getNullString(typeList.get(i).get("declareannextypeguid"));
				String name = StringX.getNullString(typeList.get(i).get("declareannextypename"));
				if(i==typeList.size()-1){
					sb.append("{id:'"+id+"',name:'"+name+"',parentId:'"+itemGuid+"'}");
				}else{
					sb.append("{id:'"+id+"',name:'"+name+"',parentId:'"+itemGuid+"'},");
				}
			}
		}
		sb.append("]");
		JSONObject  json= new JSONObject();
		json.put("treeNodes", sb.toString());
		return json.toString();
	}


	

}
