package net.risesoft.database.simplejdbc.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.risesoft.database.simplejdbc.QueryColumn;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月13日
 *
 */
public abstract class AbstractQueryColumn implements QueryColumn {

	protected StringBuffer queryColumns = new StringBuffer();
	
	public QueryColumn parenthesisLeft(){
		queryColumns.append("(");
		return this;
	}
	
	public QueryColumn parenthesisRight(){
		queryColumns.append(")");
		return this;
	}
	
	public QueryColumn addQueryColumn(String columnName, String alias){
		if(alias == null){
			queryColumns.append("," + columnName);
		}else{
			queryColumns.append("," + columnName + " " + alias);
		}				
		return this;
	}
	
	public QueryColumn addQueryColumns(Set<String> columns){
		if(columns != null && !columns.isEmpty()){
			Iterator<String> itr = columns.iterator();
			while(itr.hasNext()){
				queryColumns.append("," + itr.next());
			}
		}
		return this;
	}
	
	public QueryColumn addQueryColumns(Map<String, String> columns){
		if(columns != null && !columns.isEmpty()){
			Iterator<String> itr = columns.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				String asName = columns.get(key);
				if(asName == null){
					asName = "";
				}				
				queryColumns.append("," + key + " " + asName);
			}
		}
		return this;
	}
	
	public QueryColumn count(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",COUNT(" + columnName + ")");
		}else{
			queryColumns.append(",COUNT(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn sum(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",SUM(" + columnName + ")");
		}else{
			queryColumns.append(",SUM(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn max(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",MAX(" + columnName + ")");
		}else{
			queryColumns.append(",MAX(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn min(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",MIN(" + columnName + ")");
		}else{
			queryColumns.append(",MIN(" + columnName + ") " + alias);
		}
		return this;
	}
		
	public QueryColumn avg(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",AVG(" + columnName + ")");
		}else{
			queryColumns.append(",AVG(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn lower(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",LOWER(" + columnName + ")");
		}else{
			queryColumns.append(",LOWER(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn upper(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",UPPER(" + columnName + ")");
		}else{
			queryColumns.append(",UPPER(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn substr(String columnName, int offIndex, int endIndex, String alias){
		if(alias == null){
			queryColumns.append(",SUBSTR(" + columnName + "," + offIndex + "," + endIndex + ")");
		}else{
			queryColumns.append(",SUBSTR(" + columnName + "," + offIndex + "," + endIndex + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn getChar(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",CHAR(" + columnName + ")");
		}else{
			queryColumns.append(",CHAR(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn getAscii(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",ASCII(" + columnName + ")");
		}else{
			queryColumns.append(",ASCII(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn concat(String colName1, String colName2, String alias){
		if(alias == null){
			queryColumns.append(",CONCAT(" + colName1 + "," + colName2 + ")");
		}else{
			queryColumns.append(",CONCAT(" + colName1 + "" + colName2 + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn trim(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",TRIM(" + columnName + ")");
		}else{
			queryColumns.append(",TRIM(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn lTrim(String columnName, String regex, String alias){
		if(alias == null){
			queryColumns.append(",LTRIM(" + columnName + ",'" + regex + "')");
		}else{
			queryColumns.append(",LTRIM(" + columnName + ",'" + regex + "') " + alias);
		}
		return this;
	}
	
	public QueryColumn rTrim(String columnName, String regex, String alias){
		if(alias == null){
			queryColumns.append(",RTRIM(" + columnName + ",'" + regex + "')");
		}else{
			queryColumns.append(",RTRIM(" + columnName + ",'" + regex + "') " + alias);
		}
		return this;
	}
	
	public QueryColumn round(String columnName, Integer decimal, String alias){		
		if(alias == null){
			if(decimal == null){
				queryColumns.append(",TRIM(" + columnName + ")");
			}else{
				queryColumns.append(",TRIM(" + columnName + "," + decimal + ")");
			}
		}else{
			if(decimal == null){
				queryColumns.append(",TRIM(" + columnName + ") " + alias);
			}else{
				queryColumns.append(",TRIM(" + columnName + "," + decimal + ") " + alias);
			}
			
		}
		return this;
	}
	
	public QueryColumn toChar(String columnName, String format, String alias){
		if(alias == null){
			if(format == null){
				queryColumns.append(",TO_CHAR(" + columnName + ")");
			}else{
				queryColumns.append(",TO_CHAR(" + columnName + ",'" + format + "')");
			}
		}else{
			if(format == null){
				queryColumns.append(",TO_CHAR(" + columnName + ") " + alias);
			}else{
				queryColumns.append(",TO_CHAR(" + columnName + ",'" + format + "') " + alias);
			}
			
		}
		return this;
	}
	
	public QueryColumn toDate(String columnName, String format, String alias){
		if(alias == null){
			if(format == null){
				queryColumns.append(",TO_DATE(" + columnName + ")");
			}else{
				queryColumns.append(",TO_DATE(" + columnName + ",'" + format + "')");
			}
		}else{
			if(format == null){
				queryColumns.append(",TO_DATE(" + columnName + ") " + alias);
			}else{
				queryColumns.append(",TO_DATE(" + columnName + ",'" + format + "') " + alias);
			}
			
		}
		return this;
	}
	
	public QueryColumn toNumber(String columnName, String format, String alias){
		if(alias == null){
			if(format == null){
				queryColumns.append(",TO_NUMBER(" + columnName + ")");
			}else{
				queryColumns.append(",TO_NUMBER(" + columnName + ",'" + format + "')");
			}
		}else{
			if(format == null){
				queryColumns.append(",TO_NUMBER(" + columnName + ") " + alias);
			}else{
				queryColumns.append(",TO_NUMBER(" + columnName + ",'" + format + "') " + alias);
			}
			
		}
		return this;
	}
	
	public QueryColumn nvl(String colName1, String colName2, String alias){
		if(alias == null){
			queryColumns.append(",NVL(" + colName1 + "," + colName2 + ")");
		}else{
			queryColumns.append(",NVL(" + colName1 + "," + colName2 + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn nvl2(String columnName, String value1, String value2, String alias){
		if(alias == null){
			queryColumns.append(",NVL2(" + columnName + "," + value1 + "," + value2 + ")");
		}else{
			queryColumns.append(",NVL2(" + columnName + "," + value1 + "," + value2 + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn decode(String conidtion, String [][] keyValue, String defaultValue, String alias){
		if(alias == null){
			alias = "";
		}
		if(keyValue == null){
			queryColumns.append(",DECODE(" + conidtion + "," + defaultValue + ") " + alias);
		}else{
			queryColumns.append(",DECODE(" + conidtion);
			for(int i = 0; i < keyValue.length; i ++){
				queryColumns.append("," + keyValue[i][0] + "," + keyValue[i][1]);
			}
			if(defaultValue != null){
				queryColumns.append("," + defaultValue);
			}
		}
		return this;
	}
	
	public QueryColumn replace(String columnName, String search, String rep, String alias){
		if(alias == null){
			queryColumns.append(",REPLACE(" + columnName + ",'" + search + "','" + rep + "')");
		}else{
			queryColumns.append(",REPLACE(" + columnName + ",'" + search + "','" + rep + "') " + alias);
		}
		return this;
	}
	
	public QueryColumn translate(String columnName, String from, String to, String alias){
		if(alias == null){
			queryColumns.append(",TRANSLATE(" + columnName + ",'" + from + "','" + to + "')");
		}else{
			queryColumns.append(",TRANSLATE(" + columnName + ",'" + from + "','" + to + "') " + alias);
		}
		return this;
	}
	
	public QueryColumn rPad(String colName1, int index, String pad, String alias){
		if(alias == null){
			if(pad == null){
				queryColumns.append(",RPAD(" + colName1 + "," + index + ")");
			}else{
				queryColumns.append(",RPAD(" + colName1 + "," + index + ",'" + pad + "')");
			}
		}else{
			if(pad == null){
				queryColumns.append(",RPAD(" + colName1 + "," + index + ") " + alias);
			}else{
				queryColumns.append(",RPAD(" + colName1 + "," + index + ",'" + pad + "') " + alias);
			}
			
		}
		return this;
	}
	
	public QueryColumn lPad(String colName1, int index, String pad, String alias){
		if(alias == null){
			if(pad == null){
				queryColumns.append(",LPAD(" + colName1 + "," + index + ")");
			}else{
				queryColumns.append(",LPAD(" + colName1 + "," + index + ",'" + pad + "')");
			}
		}else{
			if(pad == null){
				queryColumns.append(",LPAD(" + colName1 + "," + index + ") " + alias);
			}else{
				queryColumns.append(",LPAD(" + colName1 + "," + index + ",'" + pad + "') " + alias);
			}
			
		}
		return this;
	}
	
	public QueryColumn length(String columnName, String alias){
		if(alias == null){
			queryColumns.append(",LENGTH(" + columnName + ")");
		}else{
			queryColumns.append(",LENGTH(" + columnName + ") " + alias);
		}
		return this;
	}
	
	public QueryColumn trunc(String columnName, String format, String alias){
		if(alias == null){
			if(format == null){
				queryColumns.append(",TRUNC(" + columnName + ")");
			}else{
				queryColumns.append(",TRUNC(" + columnName + ",'" + format + "')");
			}
		}else{
			if(format == null){
				queryColumns.append(",TRUNC(" + columnName + ") " + alias);
			}else{
				queryColumns.append(",TRUNC(" + columnName + ",'" + format + "') " + alias);
			}
			
		}
		return this;
	}
	
	public QueryColumn convert(String columnName, String dest, String source, String alias){
		if(alias == null){
			queryColumns.append(",CONVERT(" + columnName + ",'" + dest + "','" + source + "')");
		}else{
			queryColumns.append(",CONVERT(" + columnName + ",'" + dest + "','" + source + "') " + alias);
		}
		return this;
	}
	
	public QueryColumn caseWhen(String columnName, String [][] whenExp, String defaultValue, String alias){
		if(whenExp != null){
			if(alias == null){
				alias = "";
			}
			queryColumns.append(",(CASE ");	
			for(int i = 0; i < whenExp.length; i ++){
				queryColumns.append(" WHEN " + whenExp[i][0] + " THEN " + whenExp[i][1]);	
			}
			if(defaultValue != null){
				queryColumns.append(" ELSE " + defaultValue);
			}
			queryColumns.append(" END) " + alias);
		}			
		return this;
	}

}
