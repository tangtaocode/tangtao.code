package net.risesoft.database.simplejdbc;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月5日
 *
 */
public interface Operate extends Execute, OperateColumn{
	
	public Operate setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	public Operate setTableName(String tableName);
	
	@SuppressWarnings("rawtypes")
	public Operate setRowData(Map rowData);

}
