package net.risesoft.database.simplejdbc.impl.oracle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.JdbcTemplate;

import net.risesoft.common.util.ComparatorIgnoreCase;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.database.simplejdbc.Update;
import net.risesoft.database.simplejdbc.impl.SimpleOperate;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月6日
 *
 */
public class OracleSimpleUpdate extends SimpleOperate implements Update {	

	private List<Object> values = new ArrayList<Object>();
	private Map<String, Object> otherCol = new TreeMap<String, Object>();
	private Map<String, String> bothCol = new TreeMap<String, String>();
	
	public OracleSimpleUpdate(){
		super();
	}
	
	@Override
	public Update setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated method stub
		this.jdbcTemplate = jdbcTemplate;
		return this;
	}
		
	@Override
	public Update setTableName(String tableName) {
		// TODO Auto-generated method stub
		this.tableName = tableName;		
		return this;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Update setRowData(Map rowData) {
		// TODO Auto-generated method stub
		this.rowData = new TreeMap(new ComparatorIgnoreCase());
	    this.rowData.putAll(rowData);
	    return this;
	}
	
	@Override
	public Update setColumnAndValue(String columnName, Object value){
		// TODO Auto-generated method stub
		otherCol.put(columnName, value);
		return this;
	}
	
	@Override
	public Update setColumnAndColumn(String columnName1, String columnName2){
		// TODO Auto-generated method stub
		bothCol.put(columnName1, columnName2);
		return this;
	}
	
	@Override
	public int execute() {
		// TODO Auto-generated method stub
		if(!this.otherCol.isEmpty()){
			if(this.rowData == null){
				this.setRowData(this.otherCol);
			}else{
				this.rowData.putAll(this.otherCol);
			}
		}
		this.reconciledMyColumns();
		
		if(jdbcTemplate == null){
			jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(super.tableName);
		sql.append(" SET");
		sql.append(this.buildColumns());
		sql.append(" WHERE").append(this.buildCondition());
		log.debug("UPDATE_SQL=" + sql.toString());
		return jdbcTemplate.update(sql.toString(), this.values.toArray());
	}
	
	private void reconciledMyColumns(){
		super.reconciledColumns();
		if(!this.bothCol.isEmpty()){
			Iterator<String> itr = this.bothCol.keySet().iterator();
			List<String> notExist = new ArrayList<String>();
			while(itr.hasNext()){
				String key = itr.next();
//				String value = this.bothCol.get(key);
//				if(value == null){
//					continue;
//				}
				if(!super.isColumn(key)){//可能包含表达式 && super.isColumn(value)){
					notExist.add(key);
				}
			}
			for(int i = 0; i < notExist.size(); i ++){
				this.bothCol.remove(notExist.get(i));
			}
		}
	}
	
	private String buildColumns(){
		StringBuffer columns = new StringBuffer();
		
		int count = 0;
		if(!(this.rowData == null || this.rowData.isEmpty())){
			Iterator<String> itr = this.rowData.keySet().iterator();
			
			while(itr.hasNext()){
				String key = itr.next();
				if(super.primaryKeys.contains(key.toLowerCase())){
					continue;
				}
				if(count == 0){
					columns.append(" " + key + " = ?");
				}else{
					columns.append("," + key + " = ?");
				}
				values.add(this.rowData.get(key));
				count ++;
			}
		}
		if(!this.bothCol.isEmpty()){
			Iterator<String> itr2 = this.bothCol.keySet().iterator();
			while(itr2.hasNext()){
				String key = itr2.next();
				String value = this.bothCol.get(key);
				if(super.primaryKeys.contains(key.toLowerCase())){
					continue;
				}
				if(count == 0){
					columns.append(" " + key + " = " + value);
				}else{
					columns.append("," + key + " = " + value);
				}
				count ++;
			}
		}
		return columns.toString();
	}
	
	private String buildCondition(){
		StringBuffer condition = new StringBuffer();
		condition.append(super.makeSQLCondition());
		values.addAll(super.getConditionValues());
		Iterator<String> itr = super.primaryKeys.iterator();
		while(itr.hasNext()){
			String key = itr.next();
			if(this.rowData != null && this.rowData.containsKey(key)){
				condition.append(" AND " + key + " = ?");
				values.add(this.rowData.get(key));
			}
		}
		return condition.toString();
	}

}
