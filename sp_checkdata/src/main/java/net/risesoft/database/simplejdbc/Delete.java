package net.risesoft.database.simplejdbc;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月8日
 *
 */
public interface Delete extends Execute {
	
	public Delete setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	public Delete setTableName(String tableName);
	
	public Condition createSpecialCondition();
}
