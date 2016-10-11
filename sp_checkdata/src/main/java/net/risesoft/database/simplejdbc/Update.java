package net.risesoft.database.simplejdbc;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月5日
 *
 */
public interface Update extends Operate {	
	public Update setColumnAndValue(String columnName, Object value);
	public Update setColumnAndColumn(String columnName1, String columnName2);
	public Condition createSpecialCondition();
}
