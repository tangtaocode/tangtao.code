package net.risesoft.database.simplejdbc.impl.oracle;

import org.springframework.jdbc.core.JdbcTemplate;

import net.risesoft.common.util.ContextUtil;
import net.risesoft.database.simplejdbc.Delete;
import net.risesoft.database.simplejdbc.impl.OracleSQLHandler;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月8日
 *
 */
public class OracleSimpleDelete extends OracleSQLHandler implements Delete {

	private JdbcTemplate jdbcTemplate;
	private String tableName;
	
	public OracleSimpleDelete(){
		super();
	}
	
	@Override
	public int execute() {
		// TODO Auto-generated method stub
		if(this.tableName == null || this.tableName.length() == 0){
			return 0;
		}
		if(jdbcTemplate == null){
			jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ");
		sql.append(this.tableName);
		sql.append(" WHERE ")
		.append(super.makeSQLCondition());
		log.debug("DELETE_SQL=" + sql.toString());
		return jdbcTemplate.update(sql.toString(), super.getConditionValues().toArray(new Object[0]));
	}

	@Override
	public Delete setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated method stub
		this.jdbcTemplate = jdbcTemplate;
		return this;
	}

	@Override
	public Delete setTableName(String tableName) {
		// TODO Auto-generated method stub
		this.tableName = tableName;
		return this;
	}

}
