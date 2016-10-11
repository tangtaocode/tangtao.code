package net.risesoft.database.simplejdbc.impl;

import net.risesoft.database.simplejdbc.Query;
import net.risesoft.database.simplejdbc.QueryTable;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月13日
 *
 */
public abstract class AbstractQueryTable implements QueryTable {

	protected String tableName;
	protected String alias = "";
	protected StringBuffer queryTables = new StringBuffer();
		
	@Override
	public QueryTable innerJoin(String jTableName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " INNER JOIN " + jTableName + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable innerJoin(Query viewName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " INNER JOIN " + viewName.makeDynamicSQL() + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable leftJoin(String jTableName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " LEFT JOIN " + jTableName + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable leftJoin(Query viewName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " LEFT JOIN " + viewName.makeDynamicSQL() + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable leftOuterJoin(String jTableName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " LEFT OUTER JOIN " + jTableName + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable leftOuterJoin(Query viewName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " LEFT OUTER JOIN " + viewName.makeDynamicSQL() + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable rightJoin(String jTableName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " RIGHT JOIN " + jTableName + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable rightJoin(Query viewName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " RIGHT JOIN " + viewName.makeDynamicSQL() + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable rightOuterJoin(String jTableName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " RIGHT OUTER JOIN " + jTableName + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable rightOuterJoin(Query viewName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " RIGHT OUTER JOIN " + viewName.makeDynamicSQL() + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable fullJoin(String jTableName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " FULL JOIN " + jTableName + " " + jAlias);
		return this;
	}

	@Override
	public QueryTable fullJoin(Query viewName, String jAlias) {
		// TODO Auto-generated method stub
		if(jAlias == null){
			jAlias = "";
		}
		queryTables.append(" " + this.tableName + " " + this.alias + " FULL JOIN " + viewName.makeDynamicSQL() + " " + jAlias);
		return this;
	}	

}
