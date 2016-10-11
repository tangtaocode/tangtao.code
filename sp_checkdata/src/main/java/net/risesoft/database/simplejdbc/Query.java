package net.risesoft.database.simplejdbc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;

/***
 * 
 * @author Jackmen Wei 2014-05-09
 *
 */
public interface Query {
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	public Query unionAll(Query query);
	
	public QueryColumn createSpecialQueryColumn();
	
	public QueryTable createSpecialQueryTable(String tableName);
	
	public QueryTable createSpecialQueryTable(String tableName, String alias);
	
	public Condition createSpecialCondition();
	
	public Query queryColumns(Set<String> columns);
	
	public Query queryColumns(Map<String, String> columns);
	
	public Query queryTable(String tableName);
	
	public Query queryTables(Set<String> tableNames);
	
	public Query queryTables(Map<String, String> tableNameAlais);
	
	public Query queryConditionColumnNameAndValue(Map<String, Object> columnNameAndValue);
	
	public Query queryConditionColumnNameAndName(Map<String, String> columnNameAndName);
			
	public Query groupBy(List<String> columns);
	
	public Query gorupByRollup(List<String> columns);
	
	public Query ascOrderBy(String column);
	
	public Query descOrderBy(String column);
	
	public String makeDynamicSQL();
	
	public List<Object> getAllConditionValues();
	
	public Integer queryForCount();
	
	public Boolean queryIsExist();
			
	public List<Map<String, Object>> queryForList();
	
	public Map<String, Object> queryForMap();
	
	public <T> List<T> queryForListBean(Class<T> beanClass);
	
	public <T> T queryForEntityBean(Class<T> beanClass);	

}
