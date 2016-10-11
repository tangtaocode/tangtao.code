package net.risesoft.database.simplejdbc;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月8日
 *
 */
public interface Condition {
		
	public Condition parenthesisLeft();
	
	public Condition parenthesisRight();
	
	public Condition and();
	
	public Condition or();
	
	public Condition startWith();
	
	public Condition connectByPrior();
	
	public Condition equalTo(String columnName, Object columnValue);
	
	public Condition equalTo(String columnName, Object columnNameOrValue, boolean bothColumn);
	
	public Condition notEqualTo(String columnName, Object columnValue);
	
	public Condition notEqualTo(String columnName, Object columnNameOrValue, boolean bothColumn);
	
	public Condition greaterThan(String columnName, Object columnValue);
	
	public Condition greaterThan(String columnName, Object columnNameOrValue, boolean bothColumn);
	
	public Condition lessThan(String columnName, Object columnValue);
	
	public Condition lessThan(String columnName, Object columnNameOrValue, boolean bothColumn);
	
	public Condition greaterAndEqual(String columnName, Object columnValue);
	
	public Condition greaterAndEqual(String columnName, Object columnNameOrValue, boolean bothColumn);
	
	public Condition lessAndEqual(String columnName, Object columnValue);
	
	public Condition lessAndEqual(String columnName, Object columnNameOrValue, boolean bothColumn);
	
	public Condition like(String columnName, Object columnValue);
	
	public Condition like(String columnName, Object columnNameOrValue, boolean bothColumn);
	
	public Condition containIn(String columnName, String columnValue);
	
	public Condition containIn(String columnName, String columnNameOrValue, boolean bothColumn);
	
	public Condition containBy(String columnName, String columnValue);
	
	public Condition containBy(String columnName, String columnNameOrValue, boolean bothColumn);
	
	public Condition in(String columnName, Object [] columnValues);
	
	public Condition notIn(String columnName, Object [] columnValues);
	
	public Condition isNull(String columnName);
	
	public Condition isNotNull(String columnName);

}
