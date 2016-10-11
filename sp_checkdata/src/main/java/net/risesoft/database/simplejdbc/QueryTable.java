package net.risesoft.database.simplejdbc;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月13日
 *
 */
public interface QueryTable {
	
	public QueryTable innerJoin(String jTableName, String jAlias);
	
	public QueryTable innerJoin(Query viewName, String jAlias);
	
	public QueryTable leftJoin(String jTableName, String jAlias);
	
	public QueryTable leftJoin(Query viewName, String jAlias);
	
	public QueryTable leftOuterJoin(String jTableName, String jAlias);
	
	public QueryTable leftOuterJoin(Query viewName, String jAlias);
	
	public QueryTable rightJoin(String jTableName, String jAlias);
	
	public QueryTable rightJoin(Query viewName, String jAlias);
	
	public QueryTable rightOuterJoin(String jTableName, String jAlias);
	
	public QueryTable rightOuterJoin(Query viewName, String jAlias);
	
	public QueryTable fullJoin(String jTableName, String jAlias);
	
	public QueryTable fullJoin(Query viewName, String jAlias);
	
	public Condition on();

}
