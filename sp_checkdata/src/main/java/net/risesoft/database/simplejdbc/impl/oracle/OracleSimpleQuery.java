package net.risesoft.database.simplejdbc.impl.oracle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import net.risesoft.common.util.BeanPropertyUtils;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.database.simplejdbc.Query;
import net.risesoft.database.simplejdbc.impl.OracleSQLHandler;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月8日
 *
 */
public class OracleSimpleQuery extends OracleSQLHandler implements Query {
	
	private StringBuffer queryColumns = new StringBuffer();
	private StringBuffer tableNames;
	private Map<String, Object> columnNameAndValue;
	private Map<String, String> columnNameAndName;
	private List<Object> allConditionValues = new ArrayList<Object>();
	private StringBuffer groups;
	private StringBuffer orders = new StringBuffer();
	private JdbcTemplate jdbcTemplate;
	private List<Query> unionAll = new ArrayList<Query>();
	
	public OracleSimpleQuery(){
		super();
	}
	
	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated method stub
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Query unionAll(Query query){
		unionAll.add(query);
		return this;
	}
	
	@Override
	public Query queryColumns(Set<String> columns) {
		// TODO Auto-generated method stub
		if(columns != null && !columns.isEmpty()){
			Iterator<String> itr = columns.iterator();
			while(itr.hasNext()){
				this.queryColumns.append("," + itr.next());
			}
		}
		return this;
	}

	@Override
	public Query queryColumns(Map<String, String> columns) {
		// TODO Auto-generated method stub
		if(columns != null && !columns.isEmpty()){
			Iterator<String> itr = columns.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				String asName = columns.get(key);
				if(asName == null){
					asName = "";
				}				
				this.queryColumns.append("," + key + " " + asName);
			}
		}
		return this;
	}
	
	@Override
	public Query queryTable(String tableName) {
		// TODO Auto-generated method stub
		if(tableName != null && tableName.length() > 0){
			this.tableNames = new StringBuffer();
			this.tableNames.append(" " + tableName);
		}
		return this;
	}

	@Override
	public Query queryTables(Set<String> tableNameSet) {
		// TODO Auto-generated method stub
		if(tableNameSet != null && !tableNameSet.isEmpty()){
			this.tableNames = new StringBuffer();
			Iterator<String> itr = tableNameSet.iterator();
			int count = 0;
			while(itr.hasNext()){
				if(count == 0){
					this.tableNames.append(" " + itr.next());
				}else{
					this.tableNames.append("," + itr.next());
				}
				count ++;
			}
		}
		return this;
	}
	
	@Override
	public Query queryTables(Map<String, String> tableNameAlais){
		if(tableNameAlais != null && !tableNameAlais.isEmpty()){
			this.tableNames = new StringBuffer();
			Iterator<String> itr = tableNameAlais.keySet().iterator();
			int count = 0;
			while(itr.hasNext()){
				String key = itr.next();
				String asName = tableNameAlais.get(key);
				if(asName == null){
					asName = "";
				}
				if(count == 0){
					this.tableNames.append(" " + key + " " + asName);
				}else{
					this.tableNames.append("," + key + " " + asName);
				}
				count ++;
			}
		}
		return this;
	}

	@Override
	public Query queryConditionColumnNameAndValue(Map<String, Object> columnNameAndValue) {
		// TODO Auto-generated method stub
		this.columnNameAndValue = columnNameAndValue;
		return this;
	}
	
	@Override
	public Query queryConditionColumnNameAndName(Map<String, String> columnNameAndName) {
		// TODO Auto-generated method stub
		this.columnNameAndName = columnNameAndName;
		return this;
	}

	@Override
	public Query groupBy(List<String> columns) {
		// TODO Auto-generated method stub
		if(columns != null && columns.size() > 0){
			this.groups = new StringBuffer(" GROUP BY");
			for(int i = 0; i < columns.size(); i ++){
				String col = columns.get(i);
				if(i == 0){
					this.groups.append(" " + col);
				}else{
					this.groups.append("," + col);
				}
			}
		}
		return this;
	}

	@Override
	public Query gorupByRollup(List<String> columns) {
		// TODO Auto-generated method stub
		if(columns != null && columns.size() > 0){
			this.groups = new StringBuffer(" GROUP BY ROLLUP(");
			for(int i = 0; i < columns.size(); i ++){
				String col = columns.get(i);
				if(i == 0){
					this.groups.append(" " + col);
				}else{
					this.groups.append("," + col);
				}
			}
			this.groups.append(")");
		}
		return this;
	}

	@Override
	public Query ascOrderBy(String column) {
		// TODO Auto-generated method stub
		if(column != null && column.length() > 0){
			if(this.orders.length() == 0){
				this.orders.append(" ORDER BY");
				this.orders.append(" " + column);
			}else{
				this.orders.append("," + column);
			}
		}
		return this;
	}
	
	@Override
	public Query descOrderBy(String column) {
		// TODO Auto-generated method stub
		if(column != null && column.length() > 0){
			if(this.orders.length() == 0){
				this.orders.append(" ORDER BY");
				this.orders.append(" " + column + " DESC");
			}else{
				this.orders.append("," + column + " DESC");
			}
		}
		return this;
	}
	
	@Override
	public Integer queryForCount(){
		String sql = makeDynamicSQL();
		if(sql == null){
			return null;
		}
		return jdbcTemplate.queryForList(sql, this.allConditionValues.toArray(new Object[0])).size();
	}
	
	@Override
	public Boolean queryIsExist(){
		String sql = makeDynamicSQL();
		if(sql == null){
			return null;
		}
		return jdbcTemplate.queryForList(sql, this.allConditionValues.toArray(new Object[0])).size() > 0;
	}

	@Override
	public List<Map<String, Object>> queryForList() {
		// TODO Auto-generated method stub
		String sql = makeDynamicSQL();
		if(sql == null){
			return null;
		}
		return jdbcTemplate.queryForList(sql, this.allConditionValues.toArray(new Object[0]));
	}

	@Override
	public Map<String, Object> queryForMap() {
		// TODO Auto-generated method stub
		String sql = makeDynamicSQL();	
		if(sql == null){
			return null;
		}
		try{
			return jdbcTemplate.queryForMap(sql, this.allConditionValues.toArray(new Object[0]));
		}catch(EmptyResultDataAccessException e){
			log.error(e.toString());
			return null;
		}
	}

	@Override
	public <T> List<T> queryForListBean(Class<T> beanClass) {
		// TODO Auto-generated method stub
		String sql = makeDynamicSQL();	
		if(sql == null){
			return null;
		}
		List<Map<String, Object>> tempList = jdbcTemplate.queryForList(sql, this.allConditionValues.toArray(new Object[0]));
		List<T> result = new ArrayList<T>();
		if(tempList.size() > 0){
			for(int i = 0; i < tempList.size(); i ++){
				Map<String, Object> beanMap = tempList.get(i);
				T bean = BeanPropertyUtils.populate(beanClass, beanMap);
				result.add(bean);
			}			
		}
		return result;
	}

	@Override
	public <T> T queryForEntityBean(Class<T> beanClass) {
		// TODO Auto-generated method stub
		String sql = makeDynamicSQL();	
		if(sql == null){
			return null;
		}
		try{
			Map<String, Object> beanMap = jdbcTemplate.queryForMap(sql, this.allConditionValues.toArray(new Object[0]));
			T bean = BeanPropertyUtils.populate(beanClass, beanMap);
			return bean;
		}catch(EmptyResultDataAccessException e){
			log.error(e.toString());
			return null;
		}
	}
	
	@Override
	public String makeDynamicSQL(){		
		if(jdbcTemplate == null){
			jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		this.queryColumns.append(super.makeSQLQueryColumn());
		if(this.queryColumns.length() > 0){
			sql.append(this.queryColumns.substring(1));
		}else{
			sql.append("*");
		}
		sql.append(" FROM ");
		
		if(this.tableNames != null && this.tableNames.length() >= 0){
			sql.append(this.tableNames + this.makeSQLQueryTable());
		}else if(super.makeSQLQueryTable().length() > 0){
			sql.append(super.makeSQLQueryTable().substring(1));
		}
		
		sql.append(" WHERE ");
		sql.append(super.makeSQLCondition());
		this.allConditionValues.addAll(super.getConditionValues());
		if(this.columnNameAndName != null && !this.columnNameAndName.isEmpty()){
			Iterator<String> itr = this.columnNameAndName.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				sql.append(" AND " + key + " = " + this.columnNameAndName.get(key));
			}
		}
		if(this.columnNameAndValue != null && !this.columnNameAndValue.isEmpty()){
			Iterator<String> itr = this.columnNameAndValue.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				sql.append(" AND " + key + " = ?");
				this.allConditionValues.add(this.columnNameAndValue.get(key));
			}
		}
		for(int i = 0; i < unionAll.size(); i ++){
			Query query = unionAll.get(i);
			this.queryColumns.append(query.makeDynamicSQL());
			this.allConditionValues.addAll(query.getAllConditionValues());
		}
		if(this.groups != null && this.groups.length() > 0){
			sql.append(this.groups);
		}
		if(this.orders.length() > 0){
			sql.append(this.orders);
		}
		log.debug("QUERY_SQL=" + sql.toString());
		return sql.toString();
	}	
	
	@Override
	public List<Object> getAllConditionValues(){
		return this.allConditionValues;
	}

}
