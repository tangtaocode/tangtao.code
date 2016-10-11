package net.risesoft.database.simplejdbc;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.risesoft.common.util.ComparatorIgnoreCase;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.database.simplejdbc.impl.SimpleInsert;
import net.risesoft.database.simplejdbc.impl.oracle.OracleSimpleDelete;
import net.risesoft.database.simplejdbc.impl.oracle.OracleSimpleQuery;
import net.risesoft.database.simplejdbc.impl.oracle.OracleSimpleUpdate;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月5日
 *
 */
public class SimpleJdbcCRUD {
	
	private static Logger log = LoggerFactory.getLogger(SimpleJdbcCRUD.class);
	private static SimpleJdbcCRUD jdbcCRUD = new SimpleJdbcCRUD();
	
	private SimpleJdbcCRUD(){}
	
	public static SimpleJdbcCRUD getInstance(){
		return jdbcCRUD;
	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static final Insert createInsert(String tableName, Map rowData){		
		JdbcTemplate jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		return getInstance().createInsert(jdbcTemplate, tableName, rowData);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Insert createInsert(JdbcTemplate jdbcTemplate, String tableName, Map rowData){
		if(tableName == null || rowData == null || rowData.isEmpty()){
			log.info("增加的内容为空");
		}
		Insert simpleInsert = new SimpleInsert();
		simpleInsert.setJdbcTemplate(jdbcTemplate);
		simpleInsert.setTableName(tableName);
		simpleInsert.setRowData(rowData);
		return simpleInsert;
	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static final Insert createInsert(JdbcTemplate jdbcTemplate, String tableName, Object bean) throws  Exception{
		if(tableName == null || bean == null ){
			log.info("增加的内容为空");
		}
		Map row=PropertyUtils.describe(bean);
		Insert simpleInsert = new SimpleInsert();
		simpleInsert.setJdbcTemplate(jdbcTemplate);
		simpleInsert.setTableName(tableName);
		simpleInsert.setRowData(row);
		return simpleInsert;
	}
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static final Update createUpdate(String tableName, Map rowData){		
		JdbcTemplate jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		return getInstance().createUpdate(jdbcTemplate, tableName, rowData);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Update createUpdate(JdbcTemplate jdbcTemplate, String tableName, Map rowData){
		if(tableName == null || rowData == null || rowData.isEmpty()){
			log.info("更新的内容为空");
		}		
		Update simpleUpdate = new OracleSimpleUpdate();//new SimpleUpdate();
		simpleUpdate.setJdbcTemplate(jdbcTemplate);
		simpleUpdate.setTableName(tableName);
		simpleUpdate.setRowData(rowData);
		return simpleUpdate;
	}	
	
	public static final Update createUpdate(){				
		Update simpleUpdate = new OracleSimpleUpdate();//new SimpleUpdate();
		return simpleUpdate;
	}
	
	public static final Delete createDelete(){
		JdbcTemplate jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		Delete simpleDelete = new OracleSimpleDelete();
		simpleDelete.setJdbcTemplate(jdbcTemplate);
		return simpleDelete;
	}
	
	public static final Delete createDelete(String tableName){
		JdbcTemplate jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		Delete simpleDelete = new OracleSimpleDelete();
		simpleDelete.setJdbcTemplate(jdbcTemplate);
		simpleDelete.setTableName(tableName);
		return simpleDelete;
	}
	
	public static final Delete createDelete(JdbcTemplate jdbcTemplate, String tableName){
		Delete simpleDelete = new OracleSimpleDelete();
		simpleDelete.setJdbcTemplate(jdbcTemplate);
		simpleDelete.setTableName(tableName);
		return simpleDelete;
	}
	
	public static final Query createQuery(){
		Query oracleSimpleQuery = new OracleSimpleQuery();
		return oracleSimpleQuery;
	}
	
	public static final Query createQuery(JdbcTemplate jdbcTemplate){
		Query oracleSimpleQuery = new OracleSimpleQuery();
		oracleSimpleQuery.setJdbcTemplate(jdbcTemplate);
		return oracleSimpleQuery;
	}		
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	public static final Operate createOperate(String tableName, Map rowData) {
		JdbcTemplate jdbcTemplate = ContextUtil.getBean(JdbcTemplate.class);
		return getInstance().createOperate(jdbcTemplate, tableName, rowData);		
	}
	
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public static final Operate createOperate(JdbcTemplate jdbcTemplate, String tableName, Map rowData) {
		if(tableName == null || rowData == null || rowData.isEmpty()){
			log.info("增加或更新的内容为空");
		}
		TreeMap<String, Object> treeMap = new TreeMap(new ComparatorIgnoreCase());
		treeMap.putAll(rowData);
		List<String> pKeys = SimpleDatabaseTables.getTablePrimaryKey(tableName);
		Map<String, Object> pKeyValue = new HashMap<String, Object>();
		for(int i = 0; i < pKeys.size(); i ++){
			String key = pKeys.get(i);
			pKeyValue.put(key, treeMap.get(key));
		}
		Query query = getInstance().createQuery(jdbcTemplate);
		query.queryTable(tableName);
		query.queryConditionColumnNameAndValue(pKeyValue);
//		List<Map<String, Object>> isExist = query.queryForList();
		Boolean isExist = query.queryIsExist();
		Operate operate = null;
		if(isExist){
			operate = new OracleSimpleUpdate();//new SimpleUpdate();
			operate.setJdbcTemplate(jdbcTemplate);
			operate.setTableName(tableName);
			operate.setRowData(rowData);
		}else{
			operate = new SimpleInsert();
			operate.setJdbcTemplate(jdbcTemplate);
			operate.setTableName(tableName);
			operate.setRowData(rowData);
		}
		return operate;
	}

}
