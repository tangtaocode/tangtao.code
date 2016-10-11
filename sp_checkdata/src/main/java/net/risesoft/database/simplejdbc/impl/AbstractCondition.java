package net.risesoft.database.simplejdbc.impl;

import java.util.ArrayList;
import java.util.List;

import net.risesoft.database.simplejdbc.Condition;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月13日
 *
 */
public abstract class AbstractCondition implements Condition {

	protected StringBuffer dynamicCondition = new StringBuffer();
	protected List<Object> dynamicConditionValues = new ArrayList<Object>();
	
	@Override
	public Condition parenthesisLeft() {
		// TODO Auto-generated method stub
		dynamicCondition.append("(");
		return this;
	}

	@Override
	public Condition parenthesisRight() {
		// TODO Auto-generated method stub
		dynamicCondition.append(")");
		return this;
	}

	@Override
	public Condition and() {
		// TODO Auto-generated method stub
		dynamicCondition.append(" AND");
		return this;
	}

	@Override
	public Condition or() {
		// TODO Auto-generated method stub
		dynamicCondition.append(" OR");
		return this;
	}
	
	@Override
	public Condition startWith(){
		// TODO Auto-generated method stub
		dynamicCondition.append(" START WITH");
		return this;
	}
	
	@Override
	public Condition connectByPrior(){
		// TODO Auto-generated method stub
		dynamicCondition.append(" CONNECT BY PRIOR");
		return this;
	}
	
	@Override
	public Condition equalTo(String columnName, Object columnValue) {
		// TODO Auto-generated method stub		
		return this.equalTo(columnName, columnValue, false);
	}
	
	@Override
	public Condition equalTo(String columnName, Object columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" " + columnName + " = " + columnNameOrValue);
		}else{
			dynamicCondition.append(" " + columnName + " = ?");
			dynamicConditionValues.add(columnNameOrValue);
		}
		return this;
	}

	@Override
	public Condition notEqualTo(String columnName, Object columnValue) {
		// TODO Auto-generated method stub		
		return this.notEqualTo(columnName, columnValue, false);
	}
	
	@Override
	public Condition notEqualTo(String columnName, Object columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" " + columnName + " <> " + columnNameOrValue);
		}else{
			dynamicCondition.append(" " + columnName + " <> ?");
			dynamicConditionValues.add(columnNameOrValue);
		}
		return this;
	}

	@Override
	public Condition greaterThan(String columnName, Object columnValue) {
		// TODO Auto-generated method stub		
		return this.greaterThan(columnName, columnValue, false);
	}
	
	@Override
	public Condition greaterThan(String columnName, Object columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" " + columnName + " > " + columnNameOrValue);
		}else{
			dynamicCondition.append(" " + columnName + " > ?");
			dynamicConditionValues.add(columnNameOrValue);
		}
		return this;
	}

	@Override
	public Condition lessThan(String columnName, Object columnValue) {
		// TODO Auto-generated method stub		
		return this.lessThan(columnName, columnValue, false);
	}
	
	@Override
	public Condition lessThan(String columnName, Object columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" " + columnName + " < " + columnNameOrValue);
		}else{
			dynamicCondition.append(" " + columnName + " < ?");
			dynamicConditionValues.add(columnNameOrValue);
		}
		return this;
	}

	@Override
	public Condition greaterAndEqual(String columnName, Object columnValue) {
		// TODO Auto-generated method stub
		return this.greaterAndEqual(columnName, columnValue, false);
	}
	
	@Override
	public Condition greaterAndEqual(String columnName, Object columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" " + columnName + " >= " + columnNameOrValue);
		}else{
			dynamicCondition.append(" " + columnName + " >= ?");
			dynamicConditionValues.add(columnNameOrValue);
		}
		return this;
	}

	@Override
	public Condition lessAndEqual(String columnName, Object columnValue) {
		// TODO Auto-generated method stub
		return this.lessAndEqual(columnName, columnValue, false);
	}
	
	@Override
	public Condition lessAndEqual(String columnName, Object columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" " + columnName + " <= " + columnNameOrValue);
		}else{
			dynamicCondition.append(" " + columnName + " <= ?");
			dynamicConditionValues.add(columnNameOrValue);
		}
		return this;
	}

	@Override
	public Condition like(String columnName, Object columnValue) {
		// TODO Auto-generated method stub
		return this.like(columnName, columnValue, false);
	}
	
	@Override
	public Condition like(String columnName, Object columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" " + columnName + " LIKE '%' || " + columnNameOrValue + " || '%'");
		}else{
			dynamicCondition.append(" " + columnName + " LIKE ?");
			dynamicConditionValues.add(columnNameOrValue);
		}
		return this;
	}

	@Override
	public Condition containIn(String columnName, String columnValue) {
		// TODO Auto-generated method stub
		return this.containIn(columnName, columnValue, false);
	}
	
	@Override
	public Condition containIn(String columnName, String columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" instr('" + columnName + "'," + columnNameOrValue + ",1,1) > 0");
		}else{
			dynamicCondition.append(" instr('" + columnName + "',?,1,1) > 0");
			dynamicConditionValues.add(columnNameOrValue);
		}
		return this;
	}

	@Override
	public Condition containBy(String columnName, String columnValue) {
		// TODO Auto-generated method stub
		return this.containBy(columnName, columnValue, false);
	}
	
	@Override
	public Condition containBy(String columnName, String columnNameOrValue, boolean bothColumn) {
		// TODO Auto-generated method stub
		if(bothColumn){
			dynamicCondition.append(" instr(" + columnName + ",'" + columnNameOrValue + "',1,1) > 0");
		}else{
			dynamicCondition.append(" instr(?,'" + columnNameOrValue + "',1,1) > 0");
			dynamicConditionValues.add(columnName);
		}
		return this;
	}

	@Override
	public Condition in(String columnName, Object[] columnValues) {
		// TODO Auto-generated method stub
		if(columnValues != null && columnValues.length > 0){
			dynamicCondition.append(" " + columnName + " IN (");
			for(int i = 0; i < columnValues.length; i ++){
				if(i == 0){
					dynamicCondition.append("?");
				}else{
					dynamicCondition.append(",?");
				}
				dynamicConditionValues.add(columnValues[i]);
			}
			dynamicCondition.append(")");
		}
		return this;
	}
	
	@Override
	public Condition notIn(String columnName, Object[] columnValues) {
		// TODO Auto-generated method stub
		if(columnValues != null && columnValues.length > 0){
			dynamicCondition.append(" " + columnName + " NOT IN (");
			for(int i = 0; i < columnValues.length; i ++){
				if(i == 0){
					dynamicCondition.append("?");
				}else{
					dynamicCondition.append(",?");
				}
				dynamicConditionValues.add(columnValues[i]);
			}
			dynamicCondition.append(")");
		}
		return this;
	}

	@Override
	public Condition isNull(String columnName) {
		// TODO Auto-generated method stub
		dynamicCondition.append(" " + columnName + " IS NULL");
		return this;
	}

	@Override
	public Condition isNotNull(String columnName) {
		// TODO Auto-generated method stub
		dynamicCondition.append(" " + columnName + " IS NOT NULL");
		return this;
	}

}
