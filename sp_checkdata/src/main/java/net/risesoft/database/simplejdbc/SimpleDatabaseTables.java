package net.risesoft.database.simplejdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.risesoft.common.util.ContextUtil;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月6日
 *
 */
public class SimpleDatabaseTables {
	private static Map<String, List<String>> primaryKeys = new HashMap<String, List<String>>();
	private static Map<String, List<String>> tableColumns = new HashMap<String, List<String>>();
	
	@SuppressWarnings("deprecation")
	public static List<String> getTablePrimaryKey(String tableName){		
		List<String> result =  new ArrayList<String>();
		if(tableName == null){
			return result;
		}
		String tn = tableName.toLowerCase();
		if(primaryKeys.containsKey(tn)){
			return primaryKeys.get(tn);
		}else{
			JdbcTemplate jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
			
			String sql = "select a.column_name from user_cons_columns a, user_constraints b where a.constraint_name = b.constraint_name and b.constraint_type = 'P' and lower(a.table_name)=?";
			List<Map<String, Object>> tpList = jdbcTemplate.queryForList(sql, tn);
			if(tpList.size() > 0){			
				result.add(ObjectUtils.toString(tpList.get(0).get("column_name")).toLowerCase());			
			}
			if(result.size() > 0){
				primaryKeys.put(tn, result);
			}
			return result;
		}		
	}
	
	@SuppressWarnings("deprecation")
	public static List<String> getTableColumns(String tableName){
		List<String> result = new ArrayList<String>();
		if(tableName == null){
			return result;
		}
		String tn = tableName.toLowerCase();
		if(tableColumns.containsKey(tn)){
			return tableColumns.get(tn);
		}else{
			JdbcTemplate jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
			
			String sql = "select column_name from USER_TAB_COLUMNS where lower(table_name)=?";
			List<Map<String, Object>> colList = jdbcTemplate.queryForList(sql, tn);
			if (colList.size() > 0) {
				for(Map<String, Object> map2:colList){
					String colName = ObjectUtils.toString(map2.get("column_name")).toLowerCase();
					result.add(colName);
				}
			}
			if(result.size() > 0){
				tableColumns.put(tn, result);
			}
			return result;
		}
	}

}
