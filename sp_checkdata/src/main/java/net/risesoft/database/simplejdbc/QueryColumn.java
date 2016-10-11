package net.risesoft.database.simplejdbc;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月7日
 *
 */
public interface QueryColumn {
	
	public QueryColumn parenthesisLeft();
	
	public QueryColumn parenthesisRight();
	
	public QueryColumn addQueryColumn(String columnName, String alias);
	
	public QueryColumn addQueryColumns(Set<String> columns);
	
	public QueryColumn addQueryColumns(Map<String, String> columns);
	
	public QueryColumn count(String columnName, String alias);
	
	public QueryColumn sum(String columnName, String alias);
	
	public QueryColumn max(String columnName, String alias);
	
	public QueryColumn min(String columnName, String alias);
		
	public QueryColumn avg(String columnName, String alias);
	
	public QueryColumn lower(String columnName, String alias);
	
	public QueryColumn upper(String columnName, String alias);
	
	public QueryColumn substr(String columnName, int offIndex, int endIndex, String alias);
	
	public QueryColumn getChar(String columnName, String alias);
	
	public QueryColumn getAscii(String columnName, String alias);
	
	public QueryColumn concat(String colName1, String colName2, String alias);
	
	public QueryColumn trim(String columnName, String alias);
	
	public QueryColumn lTrim(String columnName, String regex, String alias);
	
	public QueryColumn rTrim(String columnName, String regex, String alias);
	
	public QueryColumn round(String columnName, Integer decimal, String alias);
	
	public QueryColumn toChar(String columnName, String format, String alias);
	
	public QueryColumn toDate(String columnName, String format, String alias);
	
	public QueryColumn toNumber(String columnName, String format, String alias);
	
	public QueryColumn nvl(String colName1, String colName2, String alias);
	
	public QueryColumn nvl2(String columnName, String value1, String value2, String alias);
	
	public QueryColumn decode(String conidtion, String [][] keyValue, String defaultValue, String alias);
	
	public QueryColumn replace(String columnName, String search, String rep, String alias);
	
	public QueryColumn translate(String columnName, String from, String to, String alias);
	
	public QueryColumn rPad(String colName1, int index, String pad, String alias);
	
	public QueryColumn lPad(String colName1, int index, String pad, String alias);
	
	public QueryColumn length(String columnName, String alias);
	
	public QueryColumn trunc(String columnName, String format, String alias);
	
	public QueryColumn convert(String columnName, String dest, String source, String alias);
	
	public QueryColumn caseWhen(String columnName, String [][] whenExp, String defaultValue, String alias);	

}
