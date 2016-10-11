package net.risesoft.database.simplejdbc.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.jdbc.core.JdbcTemplate;

import net.risesoft.database.simplejdbc.Operate;
import net.risesoft.database.simplejdbc.OperateColumn;
import net.risesoft.database.simplejdbc.SimpleDatabaseTables;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月6日
 *
 */
public abstract class SimpleOperate extends OracleSQLHandler implements Operate, OperateColumn {
	protected JdbcTemplate jdbcTemplate;
	protected String tableName;
	protected TreeMap<String, Object> rowData;
	protected Set<String> primaryKeys;
	private Set<String> allColumns;		
	
	public void reconciledColumns(){		
		if(this.allColumns == null){
			this.allColumns = new HashSet<String>();
			this.primaryKeys = new HashSet<String>();
			List<String> cols = SimpleDatabaseTables.getTableColumns(this.tableName);	
			this.allColumns.addAll(cols);
//			for(int i = 0; i < cols.size(); i ++){
//				this.allColumns.add(cols.get(i));
//			}
			List<String> pks = SimpleDatabaseTables.getTablePrimaryKey(this.tableName);
			for(int i = 0; i < pks.size(); i ++){
				this.primaryKeys.add(pks.get(i));
			}
		}
		if(this.rowData != null){
			Iterator<String> itr = this.rowData.keySet().iterator();
			Set<String> notExistCol = new HashSet<String>();
			while(itr.hasNext()){
				String key = itr.next();
				if(!isColumn(key)){
					notExistCol.add(key);					
				}
			}
			Iterator<String> itr2 = notExistCol.iterator();
			while(itr2.hasNext()){
				this.rowData.remove(itr2.next());
			}
		}
	}
	
	protected boolean isColumn(String columnName){
		if(columnName == null){
			return false;
		}
		return this.allColumns.contains(columnName.toLowerCase());
	}

}
