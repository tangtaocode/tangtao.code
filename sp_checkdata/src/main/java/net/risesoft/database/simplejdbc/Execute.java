package net.risesoft.database.simplejdbc;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月7日
 *
 */
public interface Execute {
	
	public int execute();
	
	public Execute setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	public Execute setTableName(String tableName);

}
