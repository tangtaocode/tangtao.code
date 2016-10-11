package net.risesoft.database.simplejdbc.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.risesoft.database.simplejdbc.Condition;
import net.risesoft.database.simplejdbc.QueryColumn;
import net.risesoft.database.simplejdbc.QueryTable;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月8日
 *
 */
public abstract class OracleSQLHandler {

	protected Logger log = LoggerFactory.getLogger(OracleSQLHandler.class);
	private SimpleQueryColumn qc;
//	private QueryTable qt;
	private SimpleCondition whereCondition;
//	private SimpleCondition onCondition;
//	private StringBuffer queryColumns = new StringBuffer();
	private StringBuffer queryTables = new StringBuffer();
	private List<SimpleQueryTable> simpleQueryTable = new ArrayList<SimpleQueryTable>();
//	private StringBuffer dynamicCondition = new StringBuffer();
//	private List<Object> dynamicConditionValues = new ArrayList<Object>();		
	
	public QueryColumn createSpecialQueryColumn(){
		if(qc == null)
			qc = new SimpleQueryColumn();
		return qc;
	}
	
	public QueryTable createSpecialQueryTable(String tableName){
		SimpleQueryTable sqt = new SimpleQueryTable(tableName);
		simpleQueryTable.add(sqt);
		return sqt;
	}
	
	public QueryTable createSpecialQueryTable(String tableName, String alias){
		SimpleQueryTable sqt = new SimpleQueryTable(tableName, alias);
		simpleQueryTable.add(sqt);
		return sqt;
	}
	
	public Condition createSpecialCondition(){
		if(whereCondition == null){
			whereCondition = new SimpleCondition();
		}
		return whereCondition;
	}
			
	protected String makeSQLQueryColumn(){
		if(qc != null){
			return qc.queryColumns.toString();
		}
		return "";
	}
	
	protected String makeSQLQueryTable(){
		if(simpleQueryTable.size() > 0){
			for(int i = 0; i < simpleQueryTable.size(); i ++){
				SimpleQueryTable sqt = simpleQueryTable.get(i);				
				if(sqt.onSimpleCondition.dynamicCondition.length() > 0){
					queryTables.append("," + sqt.queryTables + " ON " + sqt.onSimpleCondition.dynamicCondition);
				}else{
					queryTables.append("," + sqt.queryTables + " ON 1=1 ");
				}
			}			
		}
		return queryTables.toString();
	}
	
	protected String makeSQLCondition(){
		StringBuffer sql = new StringBuffer();
//		this.dynamicCondition.append(this.whereCondition.dynamicCondition);
//		this.dynamicConditionValues.addAll(this.whereCondition.dynamicConditionValues);
//		if(this.dynamicCondition.length() > 0){
//			sql.append(" " + this.dynamicCondition);
//		}else{
//			sql.append(" 1=1");
//		}
		if(this.whereCondition != null){
//			this.dynamicCondition.append(this.whereCondition.dynamicCondition);
//			this.dynamicConditionValues.addAll(this.whereCondition.dynamicConditionValues);
			if(this.whereCondition.dynamicCondition.length() > 0){
				sql.append(" " + this.whereCondition.dynamicCondition);
			}else{
				sql.append(" 1=1");
			}
		}else{
			sql.append(" 1=1");
		}
		return sql.toString();
	}
	
	protected List<Object> getConditionValues(){
		if(this.whereCondition != null){
			return this.whereCondition.dynamicConditionValues;
		}
		return new ArrayList<Object>();
	}
	
	class SimpleQueryColumn extends AbstractQueryColumn{
		
		private SimpleQueryColumn(){
			super();
		}						
	}
	
	class SimpleQueryTable extends AbstractQueryTable {
		private SimpleCondition onSimpleCondition;
		private SimpleQueryTable (String tableName){
			super();
			this.tableName = tableName;
		}
		
		private SimpleQueryTable (String tableName, String alias){
			super();
			this.tableName = tableName;
			this.alias = alias;
		}
						
		public Condition on(){
			onSimpleCondition = new SimpleCondition();
			return onSimpleCondition;
		}
	}
	
	class SimpleCondition extends AbstractCondition{
		private SimpleCondition (){
			super();
		}				
	}

}
