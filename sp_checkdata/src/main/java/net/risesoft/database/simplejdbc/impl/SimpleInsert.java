package net.risesoft.database.simplejdbc.impl;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import net.risesoft.common.util.ComparatorIgnoreCase;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.database.simplejdbc.Insert;
/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月6日
 *
 */
public class SimpleInsert extends SimpleOperate implements Insert {

	@Override
	public Insert setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated method stub
		this.jdbcTemplate = jdbcTemplate;
		return this;
	}
		
	@Override
	public Insert setTableName(String tableName) {
		// TODO Auto-generated method stub
		this.tableName = tableName;		
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Insert setRowData(Map rowData) {
		// TODO Auto-generated method stub
		this.rowData = new TreeMap(new ComparatorIgnoreCase());
	    this.rowData.putAll(rowData);
	    return this;
	}
	
	@Override
	public int execute() {
		// TODO Auto-generated method stub
		if(tableName == null || rowData == null || rowData.isEmpty()){
			return 0;
		}		
		if(jdbcTemplate == null){
			jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		}
		super.reconciledColumns();
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName(tableName).usingColumns(rowData.keySet().toArray(new String[0]));
		return simpleJdbcInsert.execute(rowData);
	}	

}
